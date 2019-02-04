package com.jojos.home.codility.nineteen.jan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author karanikasg@gmail.com
 */
public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        String solution1 = solution.solution(1, 6);
        System.out.println(solution1);
    }

    public String solution(int A, int B) {
        List<String> result = new ArrayList<>(A + B);
        while (A > 0 || B > 0) {
            if (A == 0) {
                while (B > 0) {
                    result.add("b");
                    B--;
                }
            } else if (B == 0) {
                while (A > 0) {
                    result.add("a");
                    A--;
                }
            }
            else {
                if (A > B) {
                    result.addAll(Arrays.asList("a", "a", "b"));
                    A -= 2;
                    B--;
                } else if (B > A) {
                    result.addAll(Arrays.asList("b", "b", "a"));
                    B -= 2;
                    A--;
                } else if (A == B) {
                    result.addAll(Arrays.asList("a", "a", "b", "b"));
                    B -= 2;
                    A -= 2;
                }
            }
        }

        return result.stream().collect(Collectors.joining());
    }


    public String initialSolution(int A, int B) {
        char[] a = fillWith('a', A);
        char[] b = fillWith('b', B);

        char[] result = new char[A + B];
        if (A == B) {
            for (int i = 0; i < A * 2; i+=2) {
                result[i] = a[i / 2];
                result[i + 1] = b[i / 2];
            }
        } else if (A > B) {
            int numberOfTimesTwoMustBePresent = A - (B+1);
            int ai = 0;
            int bi = 0;
            int ri = 0;
            for (int i = 0; i < numberOfTimesTwoMustBePresent; i++) {
                result[i * 3] = 'a';
                result[i * 3 + 1] = 'a';
                result[i * 3 + 2] = 'b';
                ri = i * 3 + 2;
            }
//            for (int i = 0; i < numberOfTimesTwoMustBePresent*3; i+=3) {
//                result[i * 3] = a[i];
//                result[i * 3 + 1] = a[i + 1];
//                result[i * 3 + 2] = b[i];
//            }
            for (int i = 0; i < A - numberOfTimesTwoMustBePresent * 2; i++) {
                int resultSpot = numberOfTimesTwoMustBePresent * 3 + i;
                result[ri++] = 'a';
                if (resultSpot + 1 < A+B) {
                    result[resultSpot + 1] = b[i / 2];
                }
            }
        } else {
            int numberOfTimesTwoMustBePresent = B - (A+1);
            for (int i = 0; i < numberOfTimesTwoMustBePresent*3; i+=3) {
                result[i] = b[i];
                result[i + 1] = b[i + 1];
                if (i < a.length) {
                    result[i + 2] = a[i];
                }
            }
            for (int i = 0; i < A * 2; i+=2) {
                int resultSpot = numberOfTimesTwoMustBePresent * 3 + i;
                result[resultSpot] = b[i / 2];
                if (resultSpot + 1 < A + B) {
                    result[resultSpot + 1] = a[i / 2];
                }
            }
        }
        return String.valueOf(result);


    }

    private char[] fillWith(char x, int times) {
        char[] result = new char[times];
        for (int i = 0; i < times; i++) {
            result[i] = x;
        }
        return result;
    }

    private int findMax(int x, int y) {
        if (x > y) {
            return x;
        }
        return y;
    }

    private int findMin(int x, int y) {
        if (x < y) {
            return x;
        }
        return y;
    }
}
