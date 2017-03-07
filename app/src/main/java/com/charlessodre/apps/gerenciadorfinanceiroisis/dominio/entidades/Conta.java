package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import android.content.Context;
import android.content.res.Resources;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by charl on 10/09/2016.
 */
public class Conta extends EntidadeBase implements Serializable {


    //Constantes
    public static String TABELA_NOME = "TB_GF_CONTA";
    public static String NM_CONTA = "NM_CONTA";
    public static String NO_COR = "NO_COR";
    public static String CD_TIPO_CONTA = "CD_TIPO_CONTA";
    public static String VL_SALDO = "VL_SALDO";
    public static String NO_AM_CONTA = "NO_AM_CONTA";
    public static String RECEITAS_PREVISTAS = "RECEITAS_PREVISTAS";
    public static String DESPESAS_PREVISTAS = "DESPESAS_PREVISTAS";
    public static String SALDO_PREVISTO = "SALDO_PREVISTO";
    public static String FL_EXIBIR_SOMA = "FL_EXIBIR_SOMA";
    public static String NO_COR_ICONE = "NO_COR_ICONE";

    //Atributos
    private int noCor;
    private int cdTipoConta;
    private Double valorSaldo;
    private int anoMes;
    private double saldoPrevisto;
    private double receitasPrevistas;
    private double despesasPrevistas;
    private int noCorIcone;


    //Propriedades


    public int getNoCorIcone() {
        return noCorIcone;
    }

    public void setNoCorIcone(int noCorIcone) {
        this.noCorIcone = noCorIcone;
    }

    public double getReceitasPrevistas() {
        return receitasPrevistas;
    }

    public void setReceitasPrevistas(double receitasPrevistas) {
        this.receitasPrevistas = receitasPrevistas;
    }

    public double getDespesasPrevistas() {
        return despesasPrevistas;
    }

    public void setDespesasPrevistas(double despesasPrevistas) {
        this.despesasPrevistas = despesasPrevistas;
    }

    public double getSaldoPrevisto() {

       return(this.valorSaldo +  this.receitasPrevistas) - this.despesasPrevistas ;

    }


    public int getAnoMes() {
        return anoMes;
    }

    public void setAnoMes(int anoMes) {
        this.anoMes = anoMes;
    }


    public int getCdTipoConta() {
        return cdTipoConta;
    }

    public void setCdTipoConta(int cdTipoConta) {
        this.cdTipoConta = cdTipoConta;
    }

    public int getNoCor() {
        return noCor;
    }

    public void setNoCor(int noCor) {
        this.noCor = noCor;
    }

    public Double getValorSaldo() {
        return valorSaldo;
    }

    public void setValorSaldo(Double valorSaldo) {
        this.valorSaldo = valorSaldo;
    }


    //Métodos

    public Double getvalorSaldoPrevisto()
    {
        return  null;
    }

    public static ArrayList<String> getTipoContas(Context context)
    {
        ArrayList<String>tipoConta = new ArrayList<String>();

        tipoConta.add(context.getString(R.string.lblContaCorrente));
        tipoConta.add(context.getString(R.string.lblContaPoupanca));
        tipoConta.add(context.getString(R.string.lblDinheiro));
        tipoConta.add(context.getString(R.string.lblInvestimento));
        tipoConta.add(context.getString(R.string.lblOutros));

        return tipoConta;
    }

    //Overrides
    @Override
    public String toString()
    {
        return super.getNome();
    }
}
