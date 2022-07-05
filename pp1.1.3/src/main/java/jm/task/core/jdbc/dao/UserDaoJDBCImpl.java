package jm.task.core.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {

  Connection connection = Util.getConnection();

  public UserDaoJDBCImpl() {

  }

  public void createUsersTable() {
    try {
      PreparedStatement statement = connection.prepareStatement("""
            CREATE TABLE IF NOT EXISTS `Users` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                           `name` VARCHAR(30) NOT NULL,
                           `last_name` VARCHAR(30) NOT NULL,
                           `age` TINYINT NOT NULL,
                           PRIMARY KEY (`id`));
          """);
      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException("Cannot create table Users");
    }
  }

  public void dropUsersTable() {
    try {
      PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS Users");
      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException("Cannot drop table Users");
    }
  }

  public void saveUser(String name, String lastName, byte age) {
    try {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO `Users` (name, last_name, age) values (?,?,?)");
      statement.setString(1, name);
      statement.setString(2, lastName);
      statement.setInt(3, age);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void removeUserById(long id) {
    try {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE `id` = ?");
      statement.setLong(1, id);
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Cannot delete user");
    }
  }

  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    try {
      PreparedStatement statement = connection.prepareStatement("SELECT * from Users");
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        User user = User.builder()
            .id(resultSet.getLong("id"))
            .age((byte) resultSet.getInt("age"))
            .name(resultSet.getString("name"))
            .lastName(resultSet.getString("last_name"))
            .build();
        users.add(user);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Cannot get users");
    }
    return users;
  }

  public void cleanUsersTable() {
    try {
      PreparedStatement statement = connection.prepareStatement("TRUNCATE table Users");
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Cannot clear table");
    }
  }
}
