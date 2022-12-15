package mate.jdbc.service.impl;

import mate.jdbc.entity.Manufacturer;
import mate.jdbc.exeption.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.service.ManufacturerService;
import mate.jdbc.util.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerServiceImpl implements ManufacturerService {
  private static final String SQL_SAVE = "INSERT INTO public.manufacturers(name, country, is_deleted) VALUES (?, ?, ?)";
  private static final String SQL_FIND_BY_ID = "SELECT * FROM public.manufacturers WHERE id = ?";
  private static final String SQL_GET_ALL = "SELECT * FROM public.manufacturers";
  private static final String SQL_DELETE = "DELETE FROM public.manufacturers WHERE id = ?";
  private static final String SQL_UPDATE = "UPDATE public.manufacturers SET name=?, country=?, is_deleted=? WHERE id=?";

  @Override
  public Manufacturer save(Manufacturer manufacturer) {
    ConnectionProvider provider = new ConnectionProvider();
    try (Connection connection = provider.getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS)) {

      statement.setString(1, manufacturer.getName());
      statement.setString(2, manufacturer.getCountry());
      statement.setBoolean(3, manufacturer.isDeleted());
      statement.execute();
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          manufacturer.setId(generatedKeys.getLong(1));
        } else {
          throw new SQLException("Creating user failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      throw new DataProcessingException(e.getMessage(), e);
    }
    return manufacturer;
  }

  @Override
  public Optional<Manufacturer> get(Long id) {
    ConnectionProvider provider = new ConnectionProvider();
    Manufacturer manufacturer = null;
    try (Connection connection = provider.getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {

      statement.setLong(1, id);
      try (ResultSet rs = statement.executeQuery()) {

        while (rs.next()) {
          long manufacturerId = rs.getLong("id");
          String manufacturerName = rs.getString("name");
          String manufacturerCountry = rs.getString("country");
          boolean manufacturerIsDeleted = rs.getBoolean("is_deleted");
          manufacturer = new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry, manufacturerIsDeleted);
        }
      }
    } catch (SQLException e) {
      throw new DataProcessingException(e.getMessage(), e);
    }
    return Optional.of(manufacturer);
  }

  @Override
  public List<Manufacturer> getAll() {
    ConnectionProvider provider = new ConnectionProvider();
    List<Manufacturer> manufacturers = new ArrayList<>();
    try (Connection connection = provider.getConnection();
         Statement statement = connection.createStatement();
         ResultSet rs = statement.executeQuery(SQL_GET_ALL)) {

      while (rs.next()) {
        long manufacturerId = rs.getLong("id");
        String manufacturerName = rs.getString("name");
        String manufacturerCountry = rs.getString("country");
        boolean manufacturerIsDeleted = rs.getBoolean("is_deleted");

        manufacturers.add(new Manufacturer(manufacturerId, manufacturerName, manufacturerCountry, manufacturerIsDeleted));
      }

    } catch (SQLException e) {
      throw new DataProcessingException(e.getMessage(), e);
    }
    return manufacturers;
  }

  @Override
  public Manufacturer update(Manufacturer manufacturer) {
    ConnectionProvider provider = new ConnectionProvider();
    try (Connection connection = provider.getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

      statement.setLong(4, manufacturer.getId().longValue());
      statement.setString(1, manufacturer.getName());
      statement.setString(2, manufacturer.getCountry());
      statement.setBoolean(3, manufacturer.isDeleted());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new DataProcessingException(e.getMessage(), e);
    }
    return manufacturer;
  }

  @Override
  public boolean delete(Long id) {
    ConnectionProvider provider = new ConnectionProvider();
    try (Connection connection = provider.getConnection();
         PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setLong(1, id);
      return statement.execute();
    } catch (SQLException e) {
      throw new DataProcessingException(e.getMessage(), e);
    }
  }
}
