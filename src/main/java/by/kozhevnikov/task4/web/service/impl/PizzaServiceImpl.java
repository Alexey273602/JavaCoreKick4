package by.kozhevnikov.task4.web.service.impl;

import by.kozhevnikov.task4.web.dao.PizzaDAO;
import by.kozhevnikov.task4.web.model.Pizza;

import java.util.List;

public class PizzaServiceImpl {
  private final PizzaDAO pizzaDAO = new PizzaDAO();

  public void createPizza(String name, int size, int price) {
    Pizza pizza = new Pizza();
    pizza.setName(name);
    pizza.setSize(size);
    pizza.setPrice(price);
    pizzaDAO.create(pizza);
  }

  public List<Pizza> getAllPizzas() {
    return pizzaDAO.findAll();
  }

  public Pizza getPizzaById(int id) {
    return pizzaDAO.findById(id);
  }

  public void updatePizza(int id, String name, int size, int price) {
    Pizza pizza = pizzaDAO.findById(id);
    if (pizza != null) {
      pizza.setName(name);
      pizza.setSize(size);
      pizza.setPrice(price);
      pizzaDAO.update(pizza);
    }
  }

  public void deletePizza(int id) {
    pizzaDAO.delete(id);
  }
}
