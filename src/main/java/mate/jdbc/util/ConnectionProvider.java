package mate.jdbc.util;

import mate.jdbc.exeption.DataProcessingException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {
  private static final String FILE_NAME = "database.properties";

  public Connection getConnection() throws SQLException {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    Properties property = new Properties();
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream stream = classLoader.getResourceAsStream(FILE_NAME);
    try {
      property.load(stream);
    } catch (IOException e) {
      throw new DataProcessingException(e.getMessage(), e);
    }

    String url = property.getProperty("jdbc.url");
    String username = property.getProperty("jdbc.username");
    String password = property.getProperty("jdbc.password");
    Connection connection = DriverManager.getConnection(url, username, password);

    return connection;
  }
}
