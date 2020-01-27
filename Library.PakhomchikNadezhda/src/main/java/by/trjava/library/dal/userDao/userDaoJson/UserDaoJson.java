package by.trjava.library.dal.userDao.userDaoJson;

import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.bean.user.User;
import by.trjava.library.dal.userDao.UserDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class UserDaoJson implements UserDao {
    private File file;

    public UserDaoJson(String baseFile) {
        file = new File(baseFile);
    }

    public void addUsers(List<User> users) throws DAOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, users);
        } catch (IOException ex) {
            throw new DAOException("The list of users wasn't added to file!", ex);
        }
    }

    public void addUser(User user) throws DAOException {
        try {
            List<User> users = getAllUsers();
            users.add(user);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, users);
        } catch (IOException ex) {
            throw new DAOException("The user wasn't added to file!", ex);
        }
    }

    public List<User> getAllUsers() throws DAOException {
        List<User> users;
        try {
            ObjectMapper mapper = new ObjectMapper();
            users = mapper.readValue(file, new TypeReference<List<User>>() {
            });
        } catch (IOException ex) {
            throw new DAOException("Extracting from file is impossible!", ex);
        }
        return users;
    }

    public User getUserById(String id) throws DAOException {
        User userById = null;
        try {
            List<User> users;
            ObjectMapper mapper = new ObjectMapper();
            users = mapper.readValue(file, new TypeReference<List<User>>() {
            });
            for (User user : users) {
                if (user.getId().equals(id)) {
                    userById = user;
                    break;
                }
            }
        } catch (IOException ex) {
            throw new DAOException("The user wasn't found! " + id, ex);
        }
        return userById;
    }

    public List<User> getUserBySurname(String surname) throws DAOException {
        List<User> usersBySurname = new ArrayList<User>();
        try {
            List<User> users;
            ObjectMapper mapper = new ObjectMapper();
            users = mapper.readValue(file, new TypeReference<List<User>>() {
            });
            for (User user : users) {
                if (user.getSurname().equals(surname)) {
                    usersBySurname.add(user);
                }
            }
        } catch (IOException ex) {
            throw new DAOException("The user wasn't found! " + surname, ex);
        }
        return usersBySurname;
    }

    public boolean deleteUser(User user) throws DAOException {
        boolean wasRemoved = false;
        try {
            List<User> users = getAllUsers();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).equals(user)) {
                    users.remove(users.get(i));
                    wasRemoved = true;
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, users);
        } catch (IOException ex) {
            throw new DAOException("The user wasn't deleted!", ex);
        }
        return wasRemoved;
    }
}
