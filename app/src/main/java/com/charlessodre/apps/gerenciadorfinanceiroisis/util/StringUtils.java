package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

/**
 * Created by charl on 13/09/2016.
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String string) {

        return string == null || string.length() == 0;
    }

    public static String upperCaseFirstLetter(String string) {

        StringBuilder sb = new StringBuilder(string);

        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

        return sb.toString();


    }
}
