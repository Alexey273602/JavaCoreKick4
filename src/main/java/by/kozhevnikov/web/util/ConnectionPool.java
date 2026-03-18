package by.kozhevnikov.web.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

  static final Logger logger = LogManager.getLogger(ConnectionPool.class);
  private static final int POOL_SIZE = 4;
  private static final Queue<Connection> connections = new LinkedList<>();
  private static final ReentrantLock lock = new ReentrantLock();
  private static boolean initialized = false;

  static {
    initPool();
  }

  private static void initPool() {
    lock.lock();
    try {
      if (initialized) return;

      try {
        Class.forName("org.postgresql.Driver");
        for (int i = 0; i < POOL_SIZE; i++) {
          Connection conn = DriverManager.getConnection(
                  DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASS
          );
          connections.offer(conn);
        }
        initialized = true;
      } catch (Exception e) {
        logger.error(e);
      }
    } finally {
      lock.unlock();
    }
  }

  public static Connection getConnection() throws SQLException {
    lock.lock();
    try {
      Connection conn = connections.poll();
      if (conn != null && !conn.isClosed()) {
        return conn;
      }
    } finally {
      lock.unlock();
    }

    return DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASS);
  }

  public static void releaseConnection(Connection conn) {
    if (conn != null && !connections.contains(conn)) {
      lock.lock();
      try {
        if (!conn.isClosed()) {
          connections.offer(conn);
        }

      } catch (SQLException e) {
        logger.error(e);
      } finally {
        lock.unlock();
      }
    }
  }
}
