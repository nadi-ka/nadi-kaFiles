package by.epam.trjava.runner;

import by.epam.trjava.connect.ConnectionPool;
import by.epam.trjava.connect.ConnectionPoolException;

public class Runner {

    public static void main(String[] args) {
        ConnectionPool connectionPool = new ConnectionPool();
        try {
            connectionPool.initializePoolData();
            connectionPool.takeConnection();
        } catch (ConnectionPoolException ex) {
            ex.printStackTrace();
        }
    }
}
