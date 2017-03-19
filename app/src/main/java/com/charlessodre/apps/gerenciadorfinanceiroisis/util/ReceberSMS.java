package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadRegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioRegraImpSMS;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by charl on 11/03/2017.
 */

public class ReceberSMS extends BroadcastReceiver {

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

            ArrayList<RegraImportacaoSMS> regraLista = this.repositorioRegraImpSMS.buscaTodos();

            for (int i = 0; i < pdus.length; i++) {
                SMS sms = new SMS();

                smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                sms.setNumero(smsMessage[i].getDisplayOriginatingAddress());
                sms.setMensagem(smsMessage[i].getDisplayMessageBody());

                getValorSMS(sms.getMensagem());

                boolean existeRegra = false;
                RegraImportacaoSMS regraImportacaoEncontrada = null;

                for (RegraImportacaoSMS regra : regraLista) {

                    if (regra.equals(sms)) {
                        existeRegra = true;
                        regraImportacaoEncontrada = regra;
                        break;
                    }
                }


                if (existeRegra) {
                    Toast.makeText(context, "ACHEI A REGRA", Toast.LENGTH_LONG).show();

                    processaRegraImportacao(regraImportacaoEncontrada, sms);


                } else {

                    //Abre Cadastro Nova Regra
                    Intent it = new Intent(context, actCadRegraImportacaoSMS.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    it.putExtra(actCadRegraImportacaoSMS.PARAM_IMP_SMS, sms);

                    context.startActivity(it);
                }

            }
        }
    }


    public void processaRegraImportacao(RegraImportacaoSMS regraImportacaoSMS, SMS sms) {
        if (regraImportacaoSMS.getIdTipoTransacao() == Constantes.TipoTransacao.RECEITA) {
            Receita receita = new Receita();

            receita.setCategoriaReceita(regraImportacaoSMS.getCategoriaReceita());
            receita.setConta(regraImportacaoSMS.getContaOrigem());

            receita.setNome(sms.getMensagem());
            receita.setValor(10.0);
            receita.setDataReceita(sms.getData());
            receita.setAnoMes(DateUtils.getYearAndMonth(sms.getData()));


        } else if (regraImportacaoSMS.getIdTipoTransacao() == Constantes.TipoTransacao.DESPESA) {

        } else if (regraImportacaoSMS.getIdTipoTransacao() == Constantes.TipoTransacao.TRANSFERENCIA) {

        }

    }

    public Double getValorSMS(String mensagem) {

        String simboloMonerario = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();
        Double valor = 0.0;
        String separadorDecimal = ",";
        int indiceSimbolo = mensagem.indexOf(simboloMonerario) + simboloMonerario.length();

        String valores = "";

        for (int i=indiceSimbolo; i <= mensagem.length(); i++)
        {
            String caractere = mensagem.substring(indiceSimbolo,i);


        }





        return valor;

    }

    public void onReceiveTEste(Context context, Intent intent) {

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
}
