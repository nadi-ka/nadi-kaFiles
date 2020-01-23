package by.trjava.library.view;

import by.trjava.library.controller.Controller;

public class View {

    public static void main(String[] args) {

//        String parse = Parser.getParameterValue("SIGN_IN;\n" +
//                "login=null\n" +
//                "password=fish111\n", "login");
//        System.out.println(parse);

        Controller controller = new Controller();
        String request1 = ViewService.getRequestWithParameters();
        String response1 = controller.executeRequest(request1);
        System.out.println(response1);

    }
}
