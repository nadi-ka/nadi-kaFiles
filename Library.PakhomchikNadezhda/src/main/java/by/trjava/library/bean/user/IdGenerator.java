package by.trjava.library.bean.user;

public class IdGenerator {
    private long lastId = 0;
    private static IdGenerator instance = new IdGenerator();

    private IdGenerator() {};

    public static IdGenerator getInstance() {
        return instance;
    }

    public long getNextId() {
        return ++lastId;
    }
}
