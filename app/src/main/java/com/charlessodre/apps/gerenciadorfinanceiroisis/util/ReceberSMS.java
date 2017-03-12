package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

/**
 * Created by charl on 11/03/2017.
 */

public class ReceberSMS extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        SmsMessage[] mensagem = null;
        String pacote = "";

        if(bundle != null)
        {

            //Recuperar mensagens
            Object pdus[]= (Object[]) bundle.get("pdus");

            SmsMessage smsMessage[] = new SmsMessage[pdus.length];

            for(int i=0 ; i < pdus.length; i++)
            {
                smsMessage[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                String celular = smsMessage[i].getDisplayOriginatingAddress();
                String mensagemCorpo = smsMessage[i].getDisplayMessageBody();

                Toast.makeText(context,"Celular: " + smsMessage[i].getDisplayOriginatingAddress() + "  Mensagem: " + smsMessage[i].getDisplayMessageBody() , Toast.LENGTH_LONG).show();
                Log.i("DebugCharles",celular + " - "+mensagemCorpo);


            }
        }
    }

}
