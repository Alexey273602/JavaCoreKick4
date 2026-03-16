package by.kozhevnikov.task4.web.dao;


import by.kozhevnikov.task4.web.model.User;
import by.kozhevnikov.task4.web.utils.DatabaseUtil;

import java.sql.*;


public class UserDAO {
  public boolean create(User user) throws SQLException {
    try (Connection conn = DatabaseUtil.getConnection()) {
      PreparedStatement pstmt = conn.prepareStatement(
              "INSERT INTO users (username, password_hash, salt) VALUES (?, ?, ?)",
              Statement.RETURN_GENERATED_KEYS
      );
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
      }
      return rows > 0;
    }
  }

  public User findByUsername(String username) throws SQLException {
    try (Connection conn = DatabaseUtil.getConnection()) {
      PreparedStatement pstmt = conn.prepareStatement(
              "SELECT * FROM users WHERE username = ?"
      );
      pstmt.setString(1, username);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          User user = new User();
          user.setId(rs.getInt("id"));
          user.setUsername(rs.getString("username"));
          user.setPasswordHash(rs.getString("password_hash"));
          user.setSalt(rs.getString("salt"));
          return user;
        }
      }
    }
    return null;
  }

}