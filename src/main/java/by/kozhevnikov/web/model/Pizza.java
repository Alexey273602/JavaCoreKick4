package by.kozhevnikov.web.model;

public class Pizza {
  private int id;
  private String name;
  private int size;
  private int price;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Pizza{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", size=").append(size);
    sb.append(", price=").append(price);
    sb.append('}');
    return sb.toString();
  }
}
