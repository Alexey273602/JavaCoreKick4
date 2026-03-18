package by.kozhevnikov.web.service;

import by.kozhevnikov.web.model.Pizza;

import java.util.List;

public interface PizzaService {
  void createPizza(String name, int size, int price);

  List<Pizza> findAll();

  Pizza findById(int id);

  void updatePizza(int id, String name, int size, int price);

  void deletePizza(int id);
}
