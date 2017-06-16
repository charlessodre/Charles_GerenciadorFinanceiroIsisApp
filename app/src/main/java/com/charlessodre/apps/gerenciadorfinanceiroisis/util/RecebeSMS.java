package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ProcessaRegraImportacao;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioRegraImpSMS;

import java.util.ArrayList;


/**
 * Created by charl on 11/03/2017.
 */

public class RecebeSMS extends BroadcastReceiver {

    RepositorioRegraImpSMS repositorioRegraImpSMS;

    @Override
    public void onReceive(Context context, Intent intent) {
        //  String format = intent.getStringExtra("format");

        Bundle bundle = intent.getExtras();

        SmsMessage[] mensagem = null;

        if (bundle != null) {
            //Recuperar mensagens
            Object pdus[] = (Object[]) bundle.get("pdus");

            SmsMessage smsMessage[] = new SmsMessage[pdus.length];

            this.repositorioRegraImpSMS = new RepositorioRegraImpSMS(context);

            ArrayList<RegraImportacaoSMS> regraLista = this.repositorioRegraImpSMS.getAll();

            for (int i = 0; i < pdus.length; i++) {
                SMS sms = new SMS();

                smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                sms.setNumero(smsMessage[i].getDisplayOriginatingAddress().replace("+", ""));
                sms.setMensagem(smsMessage[i].getDisplayMessageBody());
                sms.setData(DateUtils.getCurrentDatetime());

                ////TODO: Verificar implementação
                ProcessaRegraImportacao.verificaSeExisteRegra(context, sms);
            }
        }

        //Executa o serviço que vai ficar verificando o recebimento de SMS quando o App estiver fechado.
        //TODO: Verificar implementação
        // Intent serviceIntent = new Intent(context, RecebeSMS.class);
        // context.startService(serviceIntent);
    }

   /* public void onReceiveTEste(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        SmsMessage[] mensagem = null;
        String pacote = "";

        if (bundle != null) {

            //Recuperar mensagens
            Object pdus[] = (Object[]) bundle.get("pdus");

            SmsMessage smsMessage[] = new SmsMessage[pdus.length];

            for (int i = 0; i < pdus.length; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);


                String celular = smsMessage[i].getDisplayOriginatingAddress();
                String mensagemCorpo = smsMessage[i].getDisplayMessageBody();

                Toast.makeText(context, "Celular: " + smsMessage[i].getDisplayOriginatingAddress() + "  Mensagem: " + smsMessage[i].getDisplayMessageBody(), Toast.LENGTH_LONG).show();
                Log.i("DebugCharles", celular + " - " + mensagemCorpo);


            }
        }
    }
    */
}
