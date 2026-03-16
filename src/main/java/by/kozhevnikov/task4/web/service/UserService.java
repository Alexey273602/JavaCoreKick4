package by.kozhevnikov.task4.web.service;

import by.kozhevnikov.task4.web.model.User;

public interface UserService {
  public boolean register(User user);
  public boolean login(String username, String password);
  public User getUserByUsername(String username);
}
