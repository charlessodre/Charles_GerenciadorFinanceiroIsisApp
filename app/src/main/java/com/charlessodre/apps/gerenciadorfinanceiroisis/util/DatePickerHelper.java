package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;

/**
 * Created by charl on 05/10/2016.
 */

public class DatePickerHelper {


    public static void showDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener)
    {
        Calendar calendar = Calendar.getInstance();

        int year =  calendar.get(Calendar.YEAR);
        int month =  calendar.get(Calendar.MONTH);
        int day =  calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(context,onDateSetListener, year, month, day);

        dlg.show();
    }

}
