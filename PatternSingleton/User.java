package com.company;

public class User {
    private String userName;
    private String userSurname;
    private String login;
    private String password;
    private static final String SPACE_STRING = " ";

    public User(String userName, String userSurname, String login, String password){
        this.userName = userName;
        this.userSurname = userSurname;
        this.login = login;
        this.password = password;
    }
    public String getUserName(){
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
    public void learnAboutUsersComputer(Computer computer){
        System.out.println("Characteristics: " + SPACE_STRING + computer.getId() + SPACE_STRING + computer.getName() +
                SPACE_STRING + computer.getOperatingSystem());
    }
}
