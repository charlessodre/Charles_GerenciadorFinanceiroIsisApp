package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * Created by charl on 18/09/2016.
 */
public class ArrayAdapterHelper {

    public static ArrayAdapter<String> fillSpinnerString(Context ctx, Spinner spinner)
    {
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        return arrayAdapter;

    }

    public static ArrayAdapter<String> fillListViewString(Context ctx, ListView listView)
    {
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_expandable_list_item_1);


        listView.setAdapter(arrayAdapter);

        return arrayAdapter;

    }
}
