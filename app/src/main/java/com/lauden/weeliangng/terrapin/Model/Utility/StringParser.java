package com.lauden.weeliangng.terrapin.Model.Utility;

/**
 * Created by WeeLiang Ng on 6/3/2018.
 */

public class StringParser {

   public static String between(String value, String a, String b) {
        // Return a substring between the two strings.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        int posB = value.indexOf(b);
        if (posB == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= posB) {
            return "";
        }
        return value.substring(adjustedPosA, posB);
    }

   public  static String before(String value, String a) {
        // Return substring containing all characters before a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        return value.substring(0, posA);
    }

   public  static String after(String value, String a) {
        // Returns a substring containing all characters after a string.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= value.length()) {
            return "";
        }
        return value.substring(adjustedPosA);
    }

    public static void main(String[] args) {

        // Test this string.
        final String test = "DEFINE:A=TWO";

        // Call between, before and after methods.
        System.out.println(between(test, "DEFINE:", "="));
        System.out.println(between(test, ":", "="));

        System.out.println(before(test, ":"));
        System.out.println(before(test, "="));

        System.out.println(after(test, ":"));
        System.out.println(after(test, "DEFINE:"));
        System.out.println(after(test, "="));
    }
}