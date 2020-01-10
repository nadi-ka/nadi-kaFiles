package by.trjava.library.bean.book;


// Unique ids is not a trivial topic. This appoach works only within one startup of the app. If you restart the app,
// while keeping the same json file, the ids will be skrewed.
// Suppose you start the app and add two books: {id: 1, name: "War and Peace"}, {id: 2, name: "Capital" }
// Then you stop the app and after some time come again and add one more book "Anna Karenina". Then you get:
// {id: 1, name: "War and Peace"}, {id: 2, name: "Capital" }, {id: 1, name: "Anna Karenina" }
// This is a crucial bug.

// Databases can handle ids for you. In your case (json file) you either need to store index (complicated approach)
// or use GUIDs aka UUID (simple approach) - read here https://habr.com/ru/post/265437/.
// I suggest using UUID, it will make your life simpler. There is already an implementation in java:
// https://makeinjava.com/generate-unique-identifier-uuid-guid-java-example,
// https://www.journaldev.com/17182/java-uuid-generator-java-guid. So you can remove your custom generators.
// If you go with UUID you will have one generator for all. Here is about the probablility of generating not unique
// guid: https://stackoverflow.com/a/39776

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
