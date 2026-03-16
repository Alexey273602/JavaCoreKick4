package by.kozhevnikov.task4.web.service;

import by.kozhevnikov.task4.web.model.Pizza;

import java.util.List;

public interface PizzaService {
  public void createPizza(String name, int size, int price);
  public List<Pizza> getAllPizzas();
  public Pizza getPizzaById(int id);
  public void updatePizza(int id, String name, int size, int price);
  public void deletePizza(int id);
}
