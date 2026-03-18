package by.kozhevnikov.web.service.impl;


import by.kozhevnikov.web.dao.impl.UserDaoImpl;
import by.kozhevnikov.web.model.User;
import by.kozhevnikov.web.service.UserService;
import by.kozhevnikov.web.util.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

  static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

  private final UserDaoImpl userDAO = new UserDaoImpl();

  public boolean register(User user) {
    try {
      String plainPassword = user.getPlainPassword();
      String salt = PasswordUtil.generateSalt();
      String hashedPassword = PasswordUtil.hashPassword(plainPassword, salt);

      user.setPasswordHash(hashedPassword);
      user.setSalt(salt);

      return
              userDAO.create(user);
    } catch (Exception e) {
      logger.error(e);
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
      logger.error(e);
    }
    return false;
  }

  public User findByUsername(String username) {
    try {
      return userDAO.findByUsername(username);
    } catch (Exception e) {
      return null;
    }

  }

}