package by.trjava.library.controller.parser;

public class Parser {

    public static String getParameterValue(String request, String parameterToGetFromRequest) {
        String stringToFindAfter = parameterToGetFromRequest + "=";
        int parameterFirstIndex = request.indexOf(stringToFindAfter) + stringToFindAfter.length();
        String substringFromFistParameterIndex = request.substring(parameterFirstIndex);
        String parameterValue = substringFromFistParameterIndex.substring(0,
                substringFromFistParameterIndex.indexOf('\n'));

        return parameterValue;
    }
}
