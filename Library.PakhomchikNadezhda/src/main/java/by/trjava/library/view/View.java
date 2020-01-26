package by.trjava.library.view;

import by.trjava.library.controller.Controller;

// I would rename it to LibraryApplication
public class View {

    public static void main(String[] args) {

        // Avoid commented code, just remove it
//        String parse = Parser.getParameterValue("SIGN_IN;\n" +
//                "login=null\n" +
//                "password=fish111\n", "login");
//        System.out.println(parse);

        Controller controller = new Controller();

        // Just a tip - better not use "1", make it just request, response. "1", "2", ..., should be used
        // only where it makes sense.
        // Since it is MVC, then request can be renamed to requestModel and response to responseModel
        // Rename ViewService to View. Make getRequestWithParameters non static.
        // View view = new View()

        // You would usually have a loop in order to be able to execute many commands without restarting the Application
        // To emulate MVC and the app behavior I would make it work like this:
        //
        // while (True) {
        //   RequestModel requestModel = view.getRequestWithParameters();
        //   if requestModel.getCommand() == CommandName.EXIT:
        //     break;
        //
        //   String responseModel = controller.executeRequest(requestModel);
        //   view.print(responseModel)
        // }


        String request1 = ViewService.getRequestWithParameters();
        String response1 = controller.executeRequest(request1);

        // This goes into the view.print(responseModel)
        System.out.println(response1);

    }
}
