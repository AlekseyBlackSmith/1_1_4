package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

        service.createUsersTable();
        service.saveUser("Name1", "LastName1", (byte)10);
        service.saveUser("Name2", "LastName2", (byte)20);
        service.saveUser("Name3", "LastName3", (byte)30);
        service.saveUser("Name4", "LastName4", (byte)40);

        service.removeUserById(2);


        System.out.println(service.getAllUsers());

        service.cleanUsersTable();

        System.out.println(service.getAllUsers());

        service.dropUsersTable();

    }
}
