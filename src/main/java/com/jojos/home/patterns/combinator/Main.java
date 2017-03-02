package com.jojos.home.patterns.combinator;


import static com.jojos.home.patterns.combinator.UserValidation.emailIsValid;
import static com.jojos.home.patterns.combinator.UserValidation.nameIsValid;
/**
 * @author karanikasg@gmail.com.
 */
public class Main {

    public static void main(String[] args) {
        User lazarus = new User("Lazarus", 34, "lazarus@gmail.com");

        boolean isValid = lazarus.isValid();

        System.out.println(isValid);





        User gregor = new User("Gregor", 30, "nicemail@gmail.com");
        InitialUserValidation isNameValidFunction = user -> !user.name.trim().isEmpty();
        InitialUserValidation isEmailValidFunction = user -> user.email.contains("@");

        isValid = isNameValidFunction.apply(gregor) && isEmailValidFunction.apply(gregor);
        System.out.println(isValid);






        User maria = new User("  ", 30, "maria@gmail.com");

        isValid = emailIsValid().and(nameIsValid()).apply(maria);
        System.out.println(isValid);

    }

}
