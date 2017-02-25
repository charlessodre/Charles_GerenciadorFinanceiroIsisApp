package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

/**
 * Created by charl on 05/10/2016.
 */

public class DateListenerShow implements View.OnClickListener, View.OnFocusChangeListener
{
    private Context context;
    private DateListenerSelect dateListenerSelect;



    public DateListenerShow(Context context)
    {
        this.context = context;
        this.dateListenerSelect = new DateListenerSelect();
    }

    public DateListenerShow(Context context, TextView textView, boolean showKeyBoard)
    {
        this.context = context;
        this.dateListenerSelect = new DateListenerSelect(textView);

        textView.setOnClickListener(  this );
        textView.setOnFocusChangeListener( this );

        if(!showKeyBoard)
            textView.setInputType(InputType.TYPE_NULL);
    }


    public DateListenerSelect getDateListenerSelect() {

        return this.dateListenerSelect;
    }



    @Override
    public void onClick(View v) {
        DatePickerHelper.showDatePickerDialog(this.context,this.dateListenerSelect);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus)
            DatePickerHelper.showDatePickerDialog(this.context,this.dateListenerSelect);
    }

}
