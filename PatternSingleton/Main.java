package com.company;

public class Main {

    public static void main(String[] args) {
        User user1 = new User("Andrey", "Cann", "AndreyUser", "Java333");
        User user2 = new User("David", "Bush", "DavidUser", "Python111");
        User user3 = new User("Anna", "Karenina", "AnnaUser", "Ruby555");

        Computer computer = Computer.getComputer();
        Computer computer1 = Computer.getComputer();
        Computer computer2 = Computer.getComputer();
        //As we've used Pattern Singleton, all created computers have link to the same object;

        user1.learnAboutUsersComputer(computer);
        computer.buyComputer(user1.getUserName(), user1.getUserSurname());
        computer.upload(user1.getLogin(), user1.getPassword());
        System.out.println();

        user2.learnAboutUsersComputer(computer1);
        computer.buyComputer(user2.getUserName(), user2.getUserSurname());
        computer.upload(user2.getLogin(), user2.getPassword());
        System.out.println();

        user3.learnAboutUsersComputer(computer2);
        computer.buyComputer(user3.getUserName(), user3.getUserSurname());
        computer.upload(user3.getLogin(), user3.getPassword());

        //At the end let's perform checking for "is equals". We expect to return true;

        System.out.println();
        System.out.println(computer1.equals(computer));
        System.out.println(computer2.equals(computer));


    }
}
