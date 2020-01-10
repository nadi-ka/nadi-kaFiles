package by.trjava.library.bean.user;

// If you go forward with UUID approach you can remove this class.
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
