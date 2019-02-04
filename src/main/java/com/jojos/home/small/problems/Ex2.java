package com.jojos.home.small.problems;

/**
 * @author karanikasg@gmail.com
 */
public class Ex2<T extends Runnable, String> {
    String s;
    public void test(T t) {
        t.run();
    }
}
