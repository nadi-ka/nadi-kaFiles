package by.trjava.library.bean.book;

public class IdGeneratorBook {
    private long lastId = 0;
    private static IdGeneratorBook instance = new IdGeneratorBook();

    private IdGeneratorBook() {};

    public static IdGeneratorBook getInstance() {
        return instance;
    }

    public long getNextId() {
        return ++lastId;
    }
}
