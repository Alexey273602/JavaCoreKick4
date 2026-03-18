package by.kozhevnikov.web.service;

import by.kozhevnikov.web.model.User;

public interface UserService {
  boolean register(User user);

  boolean login(String username, String password);

  User findByUsername(String username);
}
