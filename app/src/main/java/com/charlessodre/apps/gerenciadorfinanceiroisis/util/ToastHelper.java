package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by charl on 13/09/2016.
 */
public class ToastHelper {

    public static void showToastShort(Context context, String mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
