package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
  static Connection connection = null;
  static Statement statement = null;
  public static Connection getMySqlConnection() throws SQLException {
    String host = "localhost";
    String port = "3306";
    connection =DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/world_cup", "root", "root");
    return connection;
  }

  public static ResultSet executeQueries(String query) throws SQLException {
    ResultSet resultSet = null;
    try {
      connection = getMySqlConnection();
      statement = connection.createStatement();
      resultSet = statement.executeQuery(query);
      return resultSet;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      //connection.close();
    }
    return resultSet;
  }
}
