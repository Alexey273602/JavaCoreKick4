package by.kozhevnikov.web.dao;

import by.kozhevnikov.web.model.Pizza;

import java.util.List;

public interface PizzaDao {

  void create(Pizza pizza);

  List<Pizza> findAll();

  Pizza findById(int id);

  void update(Pizza pizza);

  void delete(int id);
}
