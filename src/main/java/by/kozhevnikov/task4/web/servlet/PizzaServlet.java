package by.kozhevnikov.task4.web.servlet;

import by.kozhevnikov.task4.web.model.Pizza;
import by.kozhevnikov.task4.web.service.impl.PizzaServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet({"/", "/pizzas/*"})
public class PizzaServlet extends HttpServlet {
  private final PizzaServiceImpl pizzaService = new PizzaServiceImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String path = req.getPathInfo();
    if (path == null || "/".equals(path)) {
      List<Pizza> pizzas = pizzaService.getAllPizzas();
      req.setAttribute("pizzas", pizzas);
      req.getRequestDispatcher("/index.jsp").forward(req, resp);
    } else if (path.startsWith("/edit/")) {
      int id = Integer.parseInt(path.substring(6));
      Pizza pizza = pizzaService.getPizzaById(id);
      req.setAttribute("editPizza", pizza);
      List<Pizza> pizzas = pizzaService.getAllPizzas();
      req.setAttribute("pizzas", pizzas);
      req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String action = req.getParameter("action");

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

    resp.sendRedirect("/");
  }
}
