package by.kozhevnikov.task4.web.service.impl;


import by.kozhevnikov.task4.web.dao.UserDAO;
import by.kozhevnikov.task4.web.model.User;
import by.kozhevnikov.task4.web.utils.PasswordUtil;

public class UserServiceImpl {
  private final UserDAO userDAO = new UserDAO();

  public boolean register(User user) {
    try {
      String plainPassword = user.getPlainPassword();
      String salt = PasswordUtil.generateSalt();
      String hashedPassword = PasswordUtil.hashPassword(plainPassword, salt);

      user.setPasswordHash(hashedPassword);
      user.setSalt(salt);

      return userDAO.create(user);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean login(String username, String password) {
    try {
      User user = userDAO.findByUsername(username);
      if (user != null) {
        return PasswordUtil.verifyPassword(password, user.getPasswordHash(), user.getSalt());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public User getUserByUsername(String username) {
    try {
      return userDAO.findByUsername(username);
    } catch (Exception e) {
      return null;
    }

  }

}