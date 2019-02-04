package com.jojos.home;

import com.jojos.home.files.File4Fun;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * todo: create javadoc
 * <p>
 * Created by karanikasg@gmail.com.
 */
public class Main {

    public static void main(String[] args) {
        File4Fun.demonstrate();


        String validPrice = makeValidPriceOr("1234.75 EUR", "[^0-9]*(\\d*(\\.\\d*)).*");
        System.out.println(validPrice);

    }

    private static String makeValidPriceOr(String input, String expression) {
//        Pattern pattern = Pattern.compile("^.*((\\d*)(\\.\\d))*$.*");
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "0.00";
    }

}