package com.company;

public class Computer {
    private final int id;
    private final String name;
    private final String operatingSystem;
    private static Computer computer;
    private static final String SPACE_STRING = " ";

    private Computer(int id, String name, String operatingSystem){
        this.id = id;
        this.name = name;
        this.operatingSystem = operatingSystem;
    }

    public static Computer getComputer(){
        if (computer == null){
            computer = new Computer(100, "MSI", "Windows");
        }
        return computer;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }
    public void buyComputer(String anyName, String anySurname) {
        System.out.printf("From this moment i'm a computer of the user: %s %s \n", anyName, anySurname);
    }

    public void upload(String anyLogin, String anyPassword) {
        System.out.printf("Password %s is ok. Hello, user %s!\n", anyPassword, anyLogin);
    }

    @Override
    public String toString(){
        return ("Characteristics of the computer: " + getId() + SPACE_STRING + getName() + SPACE_STRING +
                getOperatingSystem());
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Computer computer = (Computer) obj;
        return id == computer.id
                && (name == computer.name
                || (name != null &&name.equals(computer.getName())))
                && (operatingSystem == computer.operatingSystem
                || (operatingSystem != null && operatingSystem.equals(computer.getOperatingSystem())
        ));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + id;
        result = prime * result + ((operatingSystem == null) ? 0 : operatingSystem.hashCode());
        return result;
    }


}
