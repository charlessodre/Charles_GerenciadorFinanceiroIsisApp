package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Despesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Transferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioRegraImpSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioTransferencia;

import java.util.ArrayList;
import java.util.Date;


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
                sms.setData(DateUtils.getCurrentDatetime());

                boolean existeRegra = false;
                RegraImportacaoSMS regraImportacaoEncontrada = null;
                String textoPesquisa1 = "";
                String textoPesquisa2 = "";

                for (RegraImportacaoSMS regra : regraLista) {

                    textoPesquisa1 = regra.getTextoPesquisa1().trim();
                    textoPesquisa2 = regra.getTextoPesquisa2().trim();

                    if (textoPesquisa2.length() > 1) {

                        if (sms.getMensagem().contains(textoPesquisa1) && sms.getMensagem().contains(textoPesquisa2)) {
                            existeRegra = true;
                            regraImportacaoEncontrada = regra;
                            break;
                        }

                    } else if (sms.getMensagem().contains(textoPesquisa1)) {
                        existeRegra = true;
                        regraImportacaoEncontrada = regra;
                        break;
                    }
                }


                if (existeRegra) {
                    Toast.makeText(context, "ACHEI A REGRA", Toast.LENGTH_LONG).show();

                    processaRegraImportacao(context, regraImportacaoEncontrada, sms);

                    Toast.makeText(context, "Regra Processada com sucesso!", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(context, "Não existe Regra", Toast.LENGTH_LONG).show();

                    //Abre Cadastro Nova Regra
                    // Intent it = new Intent(context, actCadRegraImportacaoSMS.class);
                    // it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // it.putExtra(actCadRegraImportacaoSMS.PARAM_IMP_SMS, sms);

                    // context.startActivity(it);
                }

            }
        }
    }


    public void processaRegraImportacao(Context context, RegraImportacaoSMS regraImportacaoSMS, SMS sms) {


        Double valor = 0.0;
        Date data = sms.getData();
        ArrayList<Double> valores = NumberUtis.getCurrencyInStringWithRegEx(sms.getMensagem(), "$");
        ArrayList<Date> datas = DateUtils.getDatesInStringWithRegEx(sms.getMensagem());
        Date dataInclusao = DateUtils.getCurrentDatetime();
        int noAnoMEs = DateUtils.getYearAndMonth(data);
        Conta contaOrigem = regraImportacaoSMS.getContaOrigem();
        String descReceitaDespesa = regraImportacaoSMS.getDescricaoReceitaDespesa();

        if (valores.size() > 0)
            valor = valores.get(0);

        if (datas.size() > 0)
            data = datas.get(0);

        if (regraImportacaoSMS.getIdTipoTransacao() == Constantes.TipoTransacao.RECEITA) {

            Receita receita = new Receita();

            receita.setCategoriaReceita(regraImportacaoSMS.getCategoriaReceita());
            receita.setConta(contaOrigem);
            receita.setNome(descReceitaDespesa);
            receita.setValor(valor);
            receita.setDataReceita(data);
            receita.setAnoMes(noAnoMEs);
            receita.setDataInclusao(dataInclusao);

            RepositorioReceita repositorioReceita = new RepositorioReceita(context);

            repositorioReceita.insere(receita);

            Toast.makeText(context, "Receita Cadastrada com Sucesso!", Toast.LENGTH_LONG).show();


        } else if (regraImportacaoSMS.getIdTipoTransacao() == Constantes.TipoTransacao.DESPESA) {

            Despesa despesa = new Despesa();

            despesa.setNome(descReceitaDespesa);
            despesa.setValor(valor);
            despesa.setDataDespesa(data);
            despesa.setAnoMes(noAnoMEs);
            despesa.setConta(contaOrigem);
            despesa.setCategoriaDespesa(regraImportacaoSMS.getCategoriaDespesa());
            despesa.setSubCategoriaDespesa(regraImportacaoSMS.getSubCategoriaDespesa());
            despesa.setDataInclusao(dataInclusao);

            RepositorioDespesa repositorioDespesa = new RepositorioDespesa(context);

            repositorioDespesa.insere(despesa);

            Toast.makeText(context, "Despesa Cadastrada com Sucesso!", Toast.LENGTH_LONG).show();

        } else if (regraImportacaoSMS.getIdTipoTransacao() == Constantes.TipoTransacao.TRANSFERENCIA) {

            Transferencia transferencia = new Transferencia();

            transferencia.setValor(valor);
            transferencia.setDataTransferencia(data);
            transferencia.setAnoMes(noAnoMEs);
            transferencia.setContaOrigem(regraImportacaoSMS.getContaOrigem());
            transferencia.setContaDestino(regraImportacaoSMS.getContaDestino());
            transferencia.setDataInclusao(dataInclusao);

            RepositorioTransferencia repositorioTransferencia = new RepositorioTransferencia(context);

            repositorioTransferencia.insere(transferencia);

            Toast.makeText(context, "Transferencia Cadastrada com Sucesso!", Toast.LENGTH_LONG).show();

        }

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
