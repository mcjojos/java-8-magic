package com.jojos.home.patterns.combinator;

import java.util.function.Function;

/**
 * @author karanikasg@gmail.com.
 */
public interface UserValidation extends Function<User, Boolean> {
    static UserValidation nameIsValid() {
        return user -> !user.name.trim().isEmpty();
    }

    static UserValidation emailIsValid() {
        return user -> user.email.contains("@");
    }

    default UserValidation and(UserValidation other) {
        return user -> this.apply(user) && other.apply(user);
    }

}
