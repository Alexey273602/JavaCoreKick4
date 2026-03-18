package by.kozhevnikov.web.dao.impl;

import by.kozhevnikov.web.dao.PizzaDao;
import by.kozhevnikov.web.model.Pizza;
import by.kozhevnikov.web.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaDaoImpl implements PizzaDao {
  static final Logger logger = LogManager.getLogger(PizzaDaoImpl.class);

  private static final String TABLE_NAME = "pizzas";
  private static final String SQL_CREATE = "INSERT INTO " + TABLE_NAME + " (name, size, price) VALUES (?, ?, ?)";
  private static final String SQL_FIND_ALL = "SELECT * FROM " + TABLE_NAME + " ORDER BY id";
  private static final String SQL_FIND_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
  private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET name = ?, size = ?, price = ? WHERE id = ?";
  private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

  @Override
  public void create(Pizza pizza) {
    Connection conn = null;
    try {
      conn = ConnectionPool.getConnection();
      try (PreparedStatement stmt = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, pizza.getName());
        stmt.setInt(2, pizza.getSize());
        stmt.setInt(3, pizza.getPrice());
        stmt.executeUpdate();

        try (ResultSet rs = stmt.getGeneratedKeys()) {
          if (rs.next()) {
            pizza.setId(rs.getInt(1));
          }
        }
        logger.info("{} created", pizza);
      }
    } catch (SQLException e) {
      ConnectionPool.releaseConnection(conn);
      logger.error("create failed: {}", e.getMessage(), e);
      throw new RuntimeException("Create pizza failed", e);
    }
  }

  @Override
  public List<Pizza> findAll() {
    List<Pizza> pizzas = new ArrayList<>();
    Connection conn = null;

    try {
      conn = ConnectionPool.getConnection();
      try (PreparedStatement stmt = conn.prepareStatement(SQL_FIND_ALL);
           ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
          Pizza pizza = new Pizza();
          pizza.setId(rs.getInt("id"));
          pizza.setName(rs.getString("name"));
          pizza.setSize(rs.getInt("size"));
          pizza.setPrice(rs.getInt("price"));
          pizzas.add(pizza);
          logger.info("{} find", pizza);
        }
      }
    } catch (SQLException e) {
      ConnectionPool.releaseConnection(conn);
      logger.error("findAll failed: {}", e.getMessage(), e);
      throw new RuntimeException("Find all pizzas failed", e);
    }
    return pizzas;
  }

  @Override
  public Pizza findById(int id) {
    Connection conn = null;
    try {
      conn = ConnectionPool.getConnection();
      try (PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_ID)) {
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            Pizza pizza = new Pizza();
            pizza.setId(rs.getInt("id"));
            pizza.setName(rs.getString("name"));
            pizza.setSize(rs.getInt("size"));
            pizza.setPrice(rs.getInt("price"));
            logger.info("{} find", pizza);
            return pizza;
          }
        }
      }
    } catch (SQLException e) {
      ConnectionPool.releaseConnection(conn);
      logger.error("findById failed: {}", e.getMessage(), e);
      throw new RuntimeException("Find pizza by ID failed", e);
    }
    return null;
  }

  @Override
  public void update(Pizza pizza) {
    Connection conn = null;
    try {
      conn = ConnectionPool.getConnection();
      try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
        stmt.setString(1, pizza.getName());
        stmt.setInt(2, pizza.getSize());
        stmt.setInt(3, pizza.getPrice());
        stmt.setInt(4, pizza.getId());
        stmt.executeUpdate();
        logger.info("{} updated", pizza);
      }
    } catch (SQLException e) {
      ConnectionPool.releaseConnection(conn);
      logger.error("update failed: {}", e.getMessage(), e);
      throw new RuntimeException("Update pizza failed", e);
    }
  }

  @Override
  public void delete(int id) {
    Connection conn = null;
    try {
      conn = ConnectionPool.getConnection();
      try (PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
        logger.info("Pizza id={} deleted", id);
      }
    } catch (SQLException e) {
      ConnectionPool.releaseConnection(conn);
      logger.error("delete failed: {}", e.getMessage(), e);
      throw new RuntimeException("Delete pizza failed", e);
    }
  }
}
