package by.kozhevnikov.web.controller;

import by.kozhevnikov.web.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

  static final Logger logger = LogManager.getLogger(LoginController.class);

  private final UserServiceImpl userService = new UserServiceImpl();

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String username = req.getParameter("username");
    String password = req.getParameter("password");

    if (userService.login(username, password)) {
      HttpSession session = req.getSession();
      session.setAttribute("user", userService.findByUsername(username));
      resp.sendRedirect("welcome.jsp");
      logger.info("Login successful");
    } else {
      resp.sendRedirect("login.jsp?error=invalid");
    }
  }
}
