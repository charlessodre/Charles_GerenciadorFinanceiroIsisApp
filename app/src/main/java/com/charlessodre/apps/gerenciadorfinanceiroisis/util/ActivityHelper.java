package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by charl on 04/10/2016.
 */

public class ActivityHelper {

    public static void showPopUp(Activity activity)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int Width=dm.widthPixels;
        int height = dm.heightPixels;

        activity.getWindow().setLayout((int)(Width*.9),(int)(height*.5));
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}


