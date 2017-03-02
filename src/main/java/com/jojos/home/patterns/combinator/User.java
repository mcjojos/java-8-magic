package com.jojos.home.patterns.combinator;

/**
 * @author karanikasg@gmail.com.
 */
public class User {
    public final String name;
    public final int age;
    public final String email;

    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public boolean isValid() {
        return isNameValid() && isEmailValid();
    }

    private boolean isNameValid() {
        return !name.trim().isEmpty();
    }

    private boolean isEmailValid() {
        return email.contains("@");
    }

}
