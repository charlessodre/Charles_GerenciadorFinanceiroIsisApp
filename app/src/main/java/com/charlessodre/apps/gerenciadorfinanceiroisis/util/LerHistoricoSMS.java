package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.Date;

/**
 * Created by charl on 11/03/2017.
 */

public class LerHistoricoSMS {

    public static void getSMSDetails(Context context)

    {

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("*********SMS Log History*************** :");

        Uri uri = Uri.parse("content://sms");

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);


        if (cursor.moveToFirst())

        {

            for (int i = 0; i < cursor.getCount(); i++)

            {

                String body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();

                String number = cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();

                String date = cursor.getString(cursor.getColumnIndexOrThrow("date")).toString();

                Date smsDayTime = new Date(Long.valueOf(date));

                String type = cursor.getString(cursor.getColumnIndexOrThrow("type")).toString();

                String typeOfSMS = null;

                switch (Integer.parseInt(type))

                {

                    case 1:

                        typeOfSMS = "INBOX";

                        break;

                    case 2:

                        typeOfSMS = "SENT";

                        break;

                    case 3:

                        typeOfSMS = "DRAFT";

                        break;

                }


                stringBuffer.append("\n  Phone Number:— " + number +

                        " \n Message Type:— " + typeOfSMS +

                        " \n Message Date:— " + smsDayTime +

                        " \n Message Body:— " + body);

                stringBuffer.append("\n ———————————- ");

                cursor.moveToNext();

            }

            MessageBoxHelper.show(context, "", stringBuffer.toString());

        }

        cursor.close();

    }


}
