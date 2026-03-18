package by.kozhevnikov.web.dao.impl;

import by.kozhevnikov.web.dao.UserDao;
import by.kozhevnikov.web.model.User;
import by.kozhevnikov.web.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class UserDaoImpl implements UserDao {
  static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

  private static final String TABLE_NAME = "users";
  private static final String SQL_CREATE = "INSERT INTO " + TABLE_NAME + " (username, password_hash, salt) VALUES (?, ?, ?)";
  private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";

  @Override
  public boolean create(User user) {
    Connection conn = null;
    try {
      conn = ConnectionPool.getConnection();
      try (PreparedStatement pstmt = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPasswordHash());
        pstmt.setString(3, user.getSalt());

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
          try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) {
              user.setId(rs.getInt(1));
            }
          }
          logger.info("User {} created", user.getUsername());
          return true;
        }
      }
    } catch (SQLException e) {
      ConnectionPool.releaseConnection(conn);
      logger.error("create user failed: {}", e.getMessage(), e);
      return false;
    }
    return false;
  }

  @Override
  public User findByUsername(String username) {
    Connection conn = null;
    try {
      conn = ConnectionPool.getConnection();
      try (PreparedStatement pstmt = conn.prepareStatement(SQL_FIND_BY_USERNAME)) {
        pstmt.setString(1, username);
        try (ResultSet rs = pstmt.executeQuery()) {
          if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPasswordHash(rs.getString("password_hash"));
            user.setSalt(rs.getString("salt"));
            logger.info("User {} found", username);
            return user;
          }
        }
      }
    } catch (SQLException e) {
      ConnectionPool.releaseConnection(conn);
      logger.error("findByUsername failed: {}", e.getMessage(), e);
    }
    return null;
  }
}
