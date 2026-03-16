package by.kozhevnikov.task4.web.dao;

import by.kozhevnikov.task4.web.model.Pizza;
import by.kozhevnikov.task4.web.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PizzaDAO {
  public void create(Pizza pizza) {
    String sql = "INSERT INTO pizzas (name, size, price) VALUES (?, ?, ?)";
    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      stmt.setString(1, pizza.getName());
      stmt.setInt(2, pizza.getSize());
      stmt.setInt(3, pizza.getPrice());
      stmt.executeUpdate();

      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        pizza.setId(rs.getInt(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Pizza> findAll() {
    List<Pizza> pizzas = new ArrayList<>();
    String sql = "SELECT * FROM pizzas ORDER BY id";

    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Pizza pizza = new Pizza();
        pizza.setId(rs.getInt("id"));
        pizza.setName(rs.getString("name"));
        pizza.setSize(rs.getInt("size"));
        pizza.setPrice(rs.getInt("price"));
        pizzas.add(pizza);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return pizzas;
  }

  public Pizza findById(int id) {
    String sql = "SELECT * FROM pizzas WHERE id = ?";
    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        Pizza pizza = new Pizza();
        pizza.setId(rs.getInt("id"));
        pizza.setName(rs.getString("name"));
        pizza.setSize(rs.getInt("size"));
        pizza.setPrice(rs.getInt("price"));
        return pizza;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void update(Pizza pizza) {
    String sql = "UPDATE pizzas SET name = ?, size = ?, price = ? WHERE id = ?";
    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, pizza.getName());
      stmt.setInt(2, pizza.getSize());
      stmt.setInt(3, pizza.getPrice());
      stmt.setInt(4, pizza.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void delete(int id) {
    String sql = "DELETE FROM pizzas WHERE id = ?";
    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
