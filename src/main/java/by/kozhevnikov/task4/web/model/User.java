package by.kozhevnikov.task4.web.model;

public class User {
  private int id;
  private String username;
  private String passwordHash;
  private String salt;

  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.passwordHash = password;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getPlainPassword() {
    return passwordHash;
  }
}