package by.trjava.library.view;

import by.trjava.library.controller.Controller;
import by.trjava.library.view.viewService.ViewService;
public class LibraryApplication {

    public static void main(String[] args) {

        Controller controller = new Controller();
        ViewService viewService = new ViewService();

        while (true) {
            String request = viewService.getRequestWithParameters();
            String response = controller.executeRequest(request);
            System.out.println(response);
            if (request.equals("EXIT;")) {
                break;
            }
        }
    }
}
