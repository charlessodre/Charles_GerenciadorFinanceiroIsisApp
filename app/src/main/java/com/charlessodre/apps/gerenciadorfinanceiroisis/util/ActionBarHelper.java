package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

/**
 * Created by charl on 11/09/2016.
 */
public class ActionBarHelper extends AppCompatActivity {

    public static  void menuCancel(ActionBar actionBar, String titulo)
    {

        actionBar.setHomeAsUpIndicator(R.drawable.ic_clear_white_24dp);
        //actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle(titulo);
    }

    public static  void menuHome(ActionBar actionBar, String titulo)
    {

        //actionBar.setHomeAsUpIndicator(null);
       // actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowCustomEnabled(true);
      //  actionBar.setDisplayShowTitleEnabled(true);
       // actionBar.setTitle(titulo);
        actionBar.setDisplayUseLogoEnabled(false);
    }


    public static void setBackgroundColor(ActionBar actionBar, int color)
    {
        actionBar.setBackgroundDrawable(new ColorDrawable(color));
    }

    public static void setStatusBarColor(Window window, String colorString)
    {
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.setStatusBarColor(ContextCompat.getColor(this, R.color.deep_orange_500));

        window.setStatusBarColor(Color.parseColor(colorString));

    }
    public static void setStatusBarColor(Window window, int color)
    {
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.setStatusBarColor(ContextCompat.getColor(this, R.color.deep_orange_500));

        window.setStatusBarColor(color);

    }

}
