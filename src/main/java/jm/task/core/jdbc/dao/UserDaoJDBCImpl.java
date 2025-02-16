package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static String nameTable = "users";
    private static String INSERT_INTO_PREPARED = "INSERT INTO " + nameTable + " (name, lastname, age) VALUES(?, ?, ?)";
    private static String DELETE_USER_BY_ID = "DELETE FROM " + nameTable + " WHERE id = ?";
    private static String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS users ";
    private static String CLEAN_TABLE = "DELETE FROM users ";
    private static String query = "SELECT * FROM users ";

    public UserDaoJDBCImpl() { // default implementation ignored
    }

    public void createUsersTable() {
        Util utilConnection = new Util();
        try (Connection connection = utilConnection.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = """
                    CREATE TABLE users (
                        id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
                        name VARCHAR(50) NOT NULL,
                        lastName VARCHAR(50) NOT NULL,
                        age SMALLINT NOT NULL
                    )
                    """;
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");

            if (resultSet.next() && resultSet.getString(1).equals("users")) {
                System.out.println("Таблица уже существет");
            } else {
                statement.executeUpdate(sql);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании таблицы", e);
        }


    }

    public void dropUsersTable() {
        Util utilConnection = new Util();
        try (Connection connection = utilConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE_IF_EXISTS);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Util connection = new Util();
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(INSERT_INTO_PREPARED)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            System.out.println("User с именем - " + name + " добавлен в базу данных");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        Util connection = new Util();
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(DELETE_USER_BY_ID)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        Util connection = new Util();
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        Util utilConnection = new Util();
        try (Connection connection = utilConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(CLEAN_TABLE);
            System.out.println("Таблица очищена");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
