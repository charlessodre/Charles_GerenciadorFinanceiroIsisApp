package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.Date;

/**
 * Created by charl on 05/10/2016.
 */
public class DateListenerSelect implements DatePickerDialog.OnDateSetListener {

    private String dateString;
    private Date date;
    private TextView textView;

public DateListenerSelect(TextView textView){

    this.textView = textView;
    }

    public DateListenerSelect(){

    }

    public String getDateString() {
        return dateString;
    }

    public Date getDate() {
        return date;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        this.dateString = DateUtils.dateToString(year, monthOfYear, dayOfMonth);
        this.date = DateUtils.getDate(year, monthOfYear, dayOfMonth);

        if(this.textView != null)
            this.textView.setText(this.dateString);

    }

}


