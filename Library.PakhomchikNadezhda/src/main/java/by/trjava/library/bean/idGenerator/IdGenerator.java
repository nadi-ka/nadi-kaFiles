package by.trjava.library.bean.idGenerator;

import java.util.UUID;

public class IdGenerator {
    private String id;
    private static IdGenerator instance = new IdGenerator();

    private IdGenerator() {}

    public static IdGenerator getInstance() {
        return instance;
    }

    public String generateId() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }
}
