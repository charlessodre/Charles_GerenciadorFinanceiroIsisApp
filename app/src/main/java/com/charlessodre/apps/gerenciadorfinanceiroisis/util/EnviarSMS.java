package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

/**
 * Created by charl on 11/03/2017.
 */

public class EnviarSMS extends AppCompatActivity {

    public void Enviar(String numeroTelefone, String mensagem)
    {
        //Envia a Mensagem
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numeroTelefone, null, mensagem, null, null);
    }


}
