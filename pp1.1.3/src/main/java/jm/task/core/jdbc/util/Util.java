package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import org.apache.logging.log4j.util.PropertiesUtil;

public class Util {
  // реализуйте настройку соеденения с БД


  public static Connection getConnection()  {
    Properties properties = readProps();
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(properties.getProperty("db.url"),
          properties.getProperty("db.user"), properties.getProperty("db.password"));
    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (conn != null) {
      System.out.println("Connected to the database!");
    } else {
      throw new RuntimeException("Failed to make connection!");
    }
    return conn;
  }

  private static Properties readProps() {
    String basePath = Objects.requireNonNull(PropertiesUtil.class.getResource("/")).getPath();
    try (InputStream input = new FileInputStream(basePath + "config.properties")) {

      Properties prop = new Properties();
      prop.load(input);
      return prop;
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    throw new RuntimeException("Property file not found");
  }
}
