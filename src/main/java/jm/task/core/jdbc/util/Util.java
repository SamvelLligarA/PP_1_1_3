package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/db-kata";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final Connection connection;

    private static final SessionFactory sessionFactory;

    public Util() throws RuntimeException {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    static {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(getHibernateProperties())
                    .build();

            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(User.class) // Добавляем сущность User
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", JDBC_DRIVER);
        properties.put("hibernate.connection.url", URL);
        properties.put("hibernate.connection.username", USER);
        properties.put("hibernate.connection.password", PASSWORD);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

