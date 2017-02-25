package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import java.text.DecimalFormat;

/**
 * Created by charl on 24/09/2016.
 */

public class DecimalHelper {

    /** Get String Pattern formatting style like "###,##0.00"
     */
    public static String getPatternCurrency (){ return "###,##0.00"; }

    public static String getFormartCurrency(Double value)
    {
        return new DecimalFormat(getPatternCurrency()).format(value);
    }
}
