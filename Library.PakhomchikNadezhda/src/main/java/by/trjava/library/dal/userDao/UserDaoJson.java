package by.trjava.library.dal.userDao;

import by.trjava.library.dal.exeptionDao.DAOException;
import by.trjava.library.bean.user.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJson implements UserDao {
    private final static String baseFile = "D:\\Workspace\\user\\user.json";

    File file = new File(baseFile);

    public void addUsers(List<User> users) throws DAOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Generate unique user ids
            mapper.writeValue(file, users);
        } catch (IOException ex) {
            throw new DAOException("The list of users wasn't added to file! " + baseFile);
        }
    }

    public void addUser(User user) throws DAOException {
        try {
            List<User> users = getAllUsers();
            // Generate unique user id
            users.add(user);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, users);
        } catch (IOException ex) {
            throw new DAOException("The user wasn't added to file! " + baseFile);
        }
    }

    public List<User> getAllUsers() throws DAOException {
        List<User> users;
        try {
            ObjectMapper mapper = new ObjectMapper();
            users = mapper.readValue(file, new TypeReference<List<User>>() {
            });
        } catch (IOException ex) {
            throw new DAOException("Extracting from file is impossible! " + baseFile);
        }
        return users;
    }

    public User getUserById(long id) throws DAOException, NullPointerException {
        User userById = null;
        try {
            List<User> users;
            ObjectMapper mapper = new ObjectMapper();
            users = mapper.readValue(file, new TypeReference<List<User>>() {
            });
            for (User user : users) {
                if (user.getId() == id) {
                    userById = user;
                    break;
                }
            }
        } catch (IOException ex) {
            throw new DAOException("Extracting from file is impossible! " + baseFile);
        }
        return userById;
    }

    public List<User> getUserBySurname(String surname) throws DAOException, NullPointerException {
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
            throw new DAOException("Extracting from file is impossible! " + baseFile);
        }
        return usersBySurname;
    }

    public boolean deleteUserById(long id) throws DAOException {
        boolean wasRemoved = false;
        try {
            List<User> users = getAllUsers();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId() == id) {
                    users.remove(users.get(i));
                    wasRemoved = true;
                }
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file, users);
        } catch (IOException ex) {
            throw new DAOException("The user wasn't added to file! " + baseFile);
        }
        return wasRemoved;
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
            throw new DAOException("The user wasn't added to file! " + baseFile);
        }
        return wasRemoved;
    }
}
