package by.trjava.library.view;

import by.trjava.library.controller.command.CommandName;
import by.trjava.library.controller.model.RequestModel;
import by.trjava.library.view.scanner.DataScanner;


// In my opinion this is view, you do not need ViewService then. I would also make public methods non-static and
// work with the object of View in the main method.
class ViewService {
    private final static char delimiter = ';';

    private static String getCommandFromClient() {
        // Add info about the EXIT command
        System.out.println("Please, choose the operation you want to perform from the next list of operations:\n" +
                "sign_in,\n" + "register,\n" + "set_permission_administrator,\n" + "update_user,\n" +
                "delete_user,\n" + "find_user_by_id,\n" + "find_user_by_surname,\n\n" + "add_book\n" +
                "update_book\n" + "delete_book_by_id\n" + "read_book\n" + "find_book.");
        String command = DataScanner.readStringFromConsole().trim().toUpperCase();
        String resultCommand;
        if (command.contains(" ")) {
            resultCommand = command.replaceAll(" ", "_");
            return resultCommand;
        }
        return command;
    }

    private static String getParametersForSignIn() {
        String login;
        String password;
        String request;
        System.out.println("Please, enter your login.");
        login = DataScanner.readStringFromConsole();
        System.out.println("Please, enter your password.");
        password = DataScanner.readStringFromConsole();
        request = delimiter + "\nlogin=" + login + "\npassword=" + password + "\n";
        return request;
    }

    private static String getParametersForRegister() {
        String surname;
        String name;
        String login;
        String password;
        String request;
        System.out.println("Please, enter your surname.");
        surname = DataScanner.readStringFromConsole();
        System.out.println("Please, enter your name.");
        name = DataScanner.readStringFromConsole();
        System.out.println("Please, enter your login. Not less, then 4 symbols.");
        login = DataScanner.readStringFromConsole();
        System.out.println("Please, enter your password. Not less, then 4 symbols.");
        password = DataScanner.readStringFromConsole();
        request = delimiter + "newSurname=" + surname + "\nnewName=" + name + "\nnewLogin=" + login +
                "\nnewPassword=" + password + "\n";
        return request;
    }

    private static String getParametersForSetPermissionDeleteFindById() {
        String userId;
        String userWhoPerformLogin;
        String userWhoPerformPassword;
        String request;
        System.out.println("Please, enter the user's id.");
        userId = DataScanner.readStringFromConsole();
        System.out.println("Please, enter your login (for this operation you must have administrator rights).");
        userWhoPerformLogin = DataScanner.readStringFromConsole();
        System.out.println("Please, enter your password.");
        userWhoPerformPassword = DataScanner.readStringFromConsole();
        request = delimiter + "id=" + userId + "\nlogin=" + userWhoPerformLogin +
                "\npassword=" + userWhoPerformPassword + "\n";
        return request;
    }

    private static String getParametersForUserUpdate() {
        String currentLoginAndPassword = getParametersForSignIn();
        String newParameters = getParametersForRegister();
        String request;
        request = currentLoginAndPassword + newParameters.substring(1);
        return request;
    }

    private static String getParametersForFindUserBySurname() {
        String surnameToFind;
        String userWhoPerformLoginAndPassword;
        String request;
        System.out.println("Please, enter the surname of the user.");
        surnameToFind = DataScanner.readStringFromConsole();
        userWhoPerformLoginAndPassword = getParametersForSignIn();
        request = delimiter + "surname=" + surnameToFind + "\n" + userWhoPerformLoginAndPassword.substring(1);
        return request;
    }

    private static String getParametersForAddBook() {
        // Do not create local variable on top. It was done in some old languages, but currently considered as an
        // old fashioned style. The general recommendation is to create the variables near the first usage.
        // So, just:
        // String bookCategory = DataScanner...
        // String author = DataScanner.readString...
        // ...
        // This recommendation relates also to all other methods
        String bookCategory;
        String author;
        String title;
        String yearOfEdition;
        String prise;
        String isPopular;
        String description;
        String userWhoPerform;
        String request;
        System.out.println("Please, enter the category of the book. You can choose from the next:\n" +
                "    NOVEL,\n" +
                "    FAIRYTALE,\n" +
                "    FANTASY,\n" +
                "    BIOGRAPHY,\n" +
                "    HISTORY,\n" +
                "    SCIENCE,\n" +
                "    ROMANCE,\n" +
                "    POETRY;");
        bookCategory = DataScanner.readStringFromConsole().toUpperCase();
        System.out.println("Please, enter the author of the book in format Surname N. For this operation you must " +
                "have the administrator's permission.");
        author = DataScanner.readStringFromConsole();
        System.out.println("Please, enter the title of the book.");
        title = DataScanner.readStringFromConsole();
        System.out.println("Please, enter the year of edition.");
        yearOfEdition = DataScanner.readStringFromConsole();
        System.out.println("Please, enter the prise of the book.");
        prise = DataScanner.readStringFromConsole();
        System.out.println("Please, enter if the book is popular (true, false).");
        isPopular = DataScanner.readStringFromConsole();
        System.out.println("Please, enter the description of the book.");
        description = DataScanner.readStringFromConsole();
        userWhoPerform = getParametersForSignIn();
        request = delimiter + "category=" + bookCategory + "\nauthor=" + author + "\ntitle=" + title +
                "\nyear=" + yearOfEdition + "\nprise=" + prise + "\npopular=" + isPopular + "\ndescription=" +
                description + "\n" + userWhoPerform.substring(1);
        return request;
    }

    private static String getParametersForUpdateBook() {
        String bookId;
        String newBookData;
        String request;
        System.out.println("Please, enter the Id of the book, you want to update. For this operation you must " +
                "have the administrator's permission.");
        bookId = DataScanner.readStringFromConsole();
        newBookData = getParametersForAddBook();
        request = delimiter + "id=" + bookId + "\n" + newBookData.substring(1);
        return request;
    }

    private static String getParametersForDeleteBookById() {
        String bookId;
        String userWhoPerform;
        String request;
        System.out.println("Please, enter the Id of the book, you want to delete. For this operation you must " +
                "have the administrator's permission.");
        bookId = DataScanner.readStringFromConsole();
        userWhoPerform = getParametersForSignIn();
        request = userWhoPerform + "id=" + bookId + "\n";
        return request;
    }

    private static String getParametersForFindBook() {
        String parameterToSearch;
        String request;
        System.out.println("How do you want to search the book:\n" +
                "in case 'By author', please, enter '1',\n" +
                "in case 'By title', please, enter '2',\n" +
                "in case 'By id', please, enter '3'.");
        parameterToSearch = DataScanner.readStringFromConsole();
        switch (parameterToSearch) {
            case "1":
                System.out.println("Please, enter the author.");
                String author = DataScanner.readStringFromConsole();
                request = delimiter + "author=" + author + "\n";
                break;
            case "2":
                System.out.println("Please, enter the title");
                String title = DataScanner.readStringFromConsole();
                request =  delimiter + "title=" + title + "\n";
                break;
            case "3":
                String bookId;
                System.out.println("Please, enter the Id of the book, you want to find.");
                bookId = DataScanner.readStringFromConsole();
                request = delimiter + "id=" + bookId + "\n";
                break;
            default:
                request = "" + delimiter;
        }
        return request;
    }

    private static String getParametersForReadBook() {
        String bookId;
        String request;
        System.out.println("Please, enter the id of the book, you want to read");
        bookId = DataScanner.readStringFromConsole();
        request = delimiter + "id=" + bookId + "\n";
        return request;
    }

    // Why not return CommandName and parameters here. You can pack it into a RequestModel class
    public static RequestModel getRequestWithParameters() {
        String command = getCommandFromClient();
        RequestModel request;
        switch (command) {
            case "SIGN_IN":
                // Use it here and in all the other cases
                request = new RequestModel(CommandName.SIGN_IN, getParametersForSignIn());
                break;
            case "REGISTER":
                request = command + getParametersForRegister();
                break;
            case "SET_PERMISSION_ADMINISTRATOR":
            case "DELETE_USER":
            case "FIND_USER_BY_ID":
                request = command + getParametersForSetPermissionDeleteFindById();
                break;
            case "UPDATE_USER":
                request = command + getParametersForUserUpdate();
                break;
            case "FIND_USER_BY_SURNAME":
                request = command + getParametersForFindUserBySurname();
                break;
            case "ADD_BOOK":
                request = command + getParametersForAddBook();
                break;
            case "UPDATE_BOOK":
                request = command + getParametersForUpdateBook();
                break;
            case "DELETE_BOOK_BY_ID":
                request = command + getParametersForDeleteBookById();
                break;
            case "FIND_BOOK":
                request = command + getParametersForFindBook();
                break;
            case "READ_BOOK":
                request = command + getParametersForReadBook();
                break;
            // Add EXIT command to allow exiting from the while(True) loop
            case "EXIT":
                request = "EXIT";
                break;
            default:
                request = command + delimiter;
        }
        return request;
    }
}
