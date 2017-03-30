package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by charl on 24/09/2016.
 */

public class NumberUtis {

    /** Get String Pattern formatting style like "###,##0.00"
     */
    public static String getPatternCurrency (){ return "###,##0.00"; }

    public static String getFormartCurrency(Double value)
    {
        return new DecimalFormat(getPatternCurrency()).format(value);
    }


    public static ArrayList<Double> getCurrencyInStringWithRegEx(String strigWithCurrency, String currencySymbol)  {
        ArrayList<Double> allMatches = new  ArrayList<Double>();
        String clearString = strigWithCurrency.replace(" ", "");
        Pattern pattern = Pattern.compile("[" +currencySymbol + "][\\d.,]+");
        Matcher matcher = pattern.matcher(clearString);

        while (matcher.find()) {

            String value = matcher.group();
            String valueClear =  value.replace(currencySymbol,"").replace(".","").replace(",",".");
            allMatches.add(Double.parseDouble(valueClear));
        }

        return allMatches;
    }

    public static int getIndexLastNumberFindInString(String string)
    {
        int lastIndex = -1;

        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            //System.out.print("Start index: " + matcher.start());
            //System.out.print(" End index: " + matcher.end());
           // System.out.println(" Found: " + matcher.group());

            lastIndex = matcher.end();
        }

        return lastIndex;
    }

    public static int getIndexFirtNumberFindInString(String string)
    {
        int firtIndex = -1;

        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            //System.out.print("Start index: " + matcher.start());
            //System.out.print(" End index: " + matcher.end());
            // System.out.println(" Found: " + matcher.group());

            firtIndex = matcher.start();
            break;
        }

        return firtIndex;
    }
}
