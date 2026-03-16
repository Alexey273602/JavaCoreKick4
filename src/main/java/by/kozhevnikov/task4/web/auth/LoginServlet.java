package by.kozhevnikov.task4.web.auth;

import by.kozhevnikov.task4.web.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private final UserServiceImpl userService = new UserServiceImpl();

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String username = req.getParameter("username");
    String password = req.getParameter("password");

    if (userService.login(username, password)) {
      HttpSession session = req.getSession();
      session.setAttribute("user", userService.getUserByUsername(username));
      resp.sendRedirect("welcome.jsp");
    } else {
      resp.sendRedirect("login.jsp?error=invalid");
    }
  }
}
