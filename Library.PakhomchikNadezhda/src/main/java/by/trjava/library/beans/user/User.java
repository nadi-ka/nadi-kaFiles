package by.trjava.library.beans.user;

import by.trjava.library.beans.idGenerator.IdGenerator;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private UserRole userRole;
    private String surname;
    private String name;
    private String login;
    private String password;

    public User() {
    }

    public User(String surname, String name, String login, String password) {
        IdGenerator instance = IdGenerator.getInstance();
        this.id = instance.generateId();
        this.userRole = UserRole.USER;
        this.surname = surname;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        User user = (User) object;
        return (id == user.id || (id != null) && id.equals(user.getId())) &&
                (userRole == user.userRole || (userRole != null) && userRole.equals(user.getUserRole())) &&
                (name == user.name || (name != null) && name.equals(user.getName())) &&
                (surname == user.surname || (surname != null) && surname.equals(user.getSurname())) &&
                (login == user.login || (login != null) && login.equals(user.getLogin())) &&
                (password == user.password || (password != null) && password.equals(user.getPassword()));
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((id == null ? 0 : id.hashCode()));
        result = prime * result + ((userRole == null) ? 0 : userRole.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "User[id: " + id + ", userRole: " + userRole + ", surname: " + surname + ", name: " + name +
                ", login: " + login + "]";
    }
}
