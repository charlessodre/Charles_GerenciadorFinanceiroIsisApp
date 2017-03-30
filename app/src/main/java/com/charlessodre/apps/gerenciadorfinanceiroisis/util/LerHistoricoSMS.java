package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.ArrayAdapter;

import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SMS;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by charl on 11/03/2017.
 */

public class LerHistoricoSMS {

    public void getSMSDetailsTeste(Context context) {

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

    public static ArrayList<SMS> getSMSDetails(Context context) {

        return getSMSDetails(context,0);

    }

    public static ArrayList<SMS> getSMSDetails(Context context, int tipoSMSSelection)
    {

        ArrayList<SMS> listaSMS = new ArrayList<SMS>();

        Uri uri = Uri.parse(SMS.URI);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, SMS.DATE);

        int tipoSMS = 0;

        if (cursor.moveToLast()) {

            for (int i = 0; i < cursor.getCount(); i++) {


                tipoSMS = cursor.getInt(cursor.getColumnIndexOrThrow(SMS.TYPE));

                if(tipoSMSSelection==0 || tipoSMS == tipoSMSSelection) {

                    SMS sms = new SMS();

                    sms.setMensagem(cursor.getString(cursor.getColumnIndexOrThrow(SMS.BODY)).toString());
                    sms.setNumero(cursor.getString(cursor.getColumnIndexOrThrow(SMS.ADDRESS)).toString());
                    sms.setData(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndexOrThrow(SMS.DATE))));
                    sms.setTipoSMS(SMS.getTipoSMS(tipoSMS));
                    sms.setId(i);

                    listaSMS.add(sms);
                }

                cursor.moveToPrevious();
            }
        }

        cursor.close();

        return listaSMS;
    }


}
