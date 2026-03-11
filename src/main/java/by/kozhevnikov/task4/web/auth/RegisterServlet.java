package by.kozhevnikov.task4.web.auth;

import by.kozhevnikov.task4.web.model.User;
import by.kozhevnikov.task4.web.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet ("/register")
public class RegisterServlet extends HttpServlet {
  private UserService userService = new UserService();

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws IOException {
    req.setCharacterEncoding("UTF-8");

    String username = req.getParameter("username");
    String password = req.getParameter("password");

    User user = new User(username, password);
    if (userService.register(user)) {
      resp.sendRedirect("index.jsp?msg=registered");
    } else {
      resp.sendRedirect("register.jsp?error=duplicate");
    }
  }
}
