package by.kozhevnikov.web.service.impl;

import by.kozhevnikov.web.dao.impl.PizzaDaoImpl;
import by.kozhevnikov.web.model.Pizza;
import by.kozhevnikov.web.service.PizzaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PizzaServiceImpl implements PizzaService {

  static final Logger logger = LogManager.getLogger(PizzaServiceImpl.class);

  private final PizzaDaoImpl pizzaDaoImpl = new PizzaDaoImpl();

  public void createPizza(String name, int size, int price) {
    Pizza pizza = new Pizza();
    pizza.setName(name);
    pizza.setSize(size);
    pizza.setPrice(price);
    pizzaDaoImpl.create(pizza);
  }

  public List<Pizza> findAll() {
    return pizzaDaoImpl.findAll();
  }

  public Pizza findById(int id) {
    return pizzaDaoImpl.findById(id);
  }

  public void updatePizza(int id, String name, int size, int price) {
    Pizza pizza = pizzaDaoImpl.findById(id);
    if (pizza != null) {
      pizza.setName(name);
      pizza.setSize(size);
      pizza.setPrice(price);
      pizzaDaoImpl.update(pizza);
    }
  }

  public void deletePizza(int id) {
    pizzaDaoImpl.delete(id);
  }
}
