package by.kozhevnikov.web.dao;

import by.kozhevnikov.web.model.User;

public interface UserDao {

  boolean create(User user);

  User findByUsername(String username);
}
