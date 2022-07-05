package jm.task.core.jdbc;

import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) throws NoSuchFileException {
        // реализуйте алгоритм здесь
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Ivan1", "Ivanov", (byte) 25);
        service.saveUser("Ivan2", "Ivanov", (byte) 35);
        service.saveUser("Ivan3", "Ivanov", (byte) 45);
        service.saveUser("Ivan4", "Ivanov", (byte) 55);
        service.getAllUsers().forEach(System.out::println);
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
