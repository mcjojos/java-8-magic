package com.jojos.home.small.problems;

/**
 *
 * Print all possible permutations of a String.
 *
 * Try each of the letters in turn as the first letter and then find all the permutations
 * of the remaining letters using a recursive call.
 *
 * The base case is when the input is an empty string
 *
 * @author karanikasg@gmail.com
 */
public class Permutation {

    public void permutation(String str) {
        permutation(str, "");
    }

    private void permutation(String str, String prefix) {
        if (str.length() == 0) {
            System.out.println(prefix);
        } else {
            for (int i = 0; i < str.length(); i++) {
                permutation(str.substring(0, i) + str.substring(i + 1), prefix + str.charAt(i));
            }
        }
    }


}
