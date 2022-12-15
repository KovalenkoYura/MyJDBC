package mate.jdbc.entity;

public class Manufacturer {
  private Long id;
  private String name;
  private String country;
  private boolean isDeleted;

  public Manufacturer(String name, String country, boolean isDeleted) {
    this.name = name;
    this.country = country;
    this.isDeleted = isDeleted;
  }

  public Manufacturer(Long id, String name, String country, boolean isDeleted) {
    this.id = id;
    this.name = name;
    this.country = country;
    this.isDeleted = isDeleted;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }

  @Override
  public String toString() {
    return "Manufacturer{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", country='" + country + '\'' +
        ", isDeleted=" + isDeleted +
        '}';
  }
}
