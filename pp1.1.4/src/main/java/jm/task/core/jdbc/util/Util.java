package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Util {

    private static final SessionFactory sessionFactory = createSessionFactory();

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionFactory createSessionFactory() {
        Configuration configuration = null;
        StandardServiceRegistryBuilder builder = null;
        try {
            configuration = new Configuration().addAnnotatedClass(User.class);
            builder = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties());
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return configuration.buildSessionFactory(builder.build());
    }

    public static void closeConndection() {
        sessionFactory.close();
    }

    public static Connection getConnection() {
        Properties properties = readProps();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s",
                    e.getSQLState(),
                    e.getMessage());
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
        String basePath = Objects
                .requireNonNull(PropertiesUtil.class.getResource("/")).getPath();
        try (InputStream input = new FileInputStream(basePath +
                "config.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        throw new RuntimeException("Property file not found");
    }
}
