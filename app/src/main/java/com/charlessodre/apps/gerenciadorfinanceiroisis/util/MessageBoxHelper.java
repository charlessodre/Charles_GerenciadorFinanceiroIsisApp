package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by charl on 10/09/2016.
 */
public class MessageBoxHelper {

    public static void showInfo(Context ctx, String title, String msg)
    {
        show(ctx, title, msg, android.R.drawable.ic_dialog_info);
    }

    public static void showAlert(Context ctx, String title, String msg)
    {
        show(ctx, title, msg, android.R.drawable.ic_dialog_alert);
    }

    public static void show(Context ctx, String title, String msg)
    {
        show(ctx, title, msg, 0);
    }

    public static void show(Context ctx, String title, String msg, int iconId)
    {
       /* AlertDialog.Builder dlg = new AlertDialog.Builder(ctx);
        dlg.setIcon(iconId);
        dlg.setTitle(title);
        dlg.setMessage(msg);
        dlg.setNeutralButton("OK", null);

        dlg.show();*/

        show(ctx, title, msg, iconId,null);
    }

    public static void show(Context ctx,String title, String msg, int iconId, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", okListener)
                .setCancelable(false)
                .create()
                .show();
    }

}
