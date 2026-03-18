package by.kozhevnikov.web.controller;

import by.kozhevnikov.web.model.Pizza;
import by.kozhevnikov.web.service.impl.PizzaServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PizzaServlet", urlPatterns = {"/pizzas", "/pizzas/*"})
public class PizzaController extends HttpServlet {

  static final Logger logger = LogManager.getLogger(PizzaController.class);

  private final PizzaServiceImpl pizzaService = new PizzaServiceImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getPathInfo();

    if (path == null || "/".equals(path)) {
      List<Pizza> pizzas = pizzaService.findAll();
      req.setAttribute("pizzas", pizzas);
      req.getRequestDispatcher("/welcome.jsp").forward(req, resp);
    } else if (path.startsWith("/edit/")) {
      try {
        int id = Integer.parseInt(path.substring(6));
        Pizza pizza = pizzaService.findById(id);
        req.setAttribute("editPizza", pizza);
        List<Pizza> pizzas = pizzaService.findAll();
        req.setAttribute("pizzas", pizzas);
        req.getRequestDispatcher("/welcome.jsp").forward(req, resp);
      } catch (NumberFormatException e) {
        resp.sendRedirect(req.getContextPath() + "/pizzas");
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String action = req.getParameter("action");

    try {
      if ("create".equals(action)) {
        String name = req.getParameter("name");
        int size = Integer.parseInt(req.getParameter("size"));
        int price = Integer.parseInt(req.getParameter("price"));
        pizzaService.createPizza(name, size, price);
      } else if ("update".equals(action)) {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int size = Integer.parseInt(req.getParameter("size"));
        int price = Integer.parseInt(req.getParameter("price"));
        pizzaService.updatePizza(id, name, size, price);
      } else if ("delete".equals(action)) {
        int id = Integer.parseInt(req.getParameter("id"));
        pizzaService.deletePizza(id);
      }
    } catch (Exception e) {
      logger.error(e);
    }

    resp.sendRedirect(req.getContextPath() + "/pizzas");
  }
}
