package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {

    public static void main(String[] args) {
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
        Util.closeConndection();
    }
}
