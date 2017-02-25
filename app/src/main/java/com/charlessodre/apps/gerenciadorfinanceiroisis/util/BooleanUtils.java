package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

/**
 * Created by charl on 19/09/2016.
 */
public class BooleanUtils {

    public static boolean parseIntToBoolean(int value)
    {
        return value == 1;
    }

    public static int parseBooleanToint(boolean value)
    {
        return value? 1:0;
    }
}
