package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import android.database.Cursor;
import android.database.SQLException;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by charl on 27/09/2016.
 */

public class Transferencia extends EntidadeBase implements Serializable {

    //Constantes
    public static String  TABELA_NOME 		  = "TB_GF_TRANSFERENCIA";

    public static String  NO_TOTAL_REPETICAO   =  "NO_TOTAL_REPETICAO";
    public static String  NO_REPETICAO_ATUAL   =  "NO_REPETICAO_ATUAL";
    public static String  NO_AM_TRANSFERENCIA  =  "NO_AM_TRANSFERENCIA";
    public static String  VL_TRANSFERENCIA     =  "VL_TRANSFERENCIA";
    public static String  ID_CONTA_ORIGEM      =  "ID_CONTA_ORIGEM";
    public static String  ID_CONTA_DESTINO     =  "ID_CONTA_DESTINO";
    public static String  DT_TRANSFERENCIA     =  "DT_TRANSFERENCIA";
    public static String  FL_ALERTA_TRANSFERENCIA = "FL_ALERTA_TRANSFERENCIA";
    public static String  FL_TRANSFERENCIA_EFETIVADA = "FL_TRANSFERENCIA_EFETIVADA";

    //Atributos

    private int totalRepeticao;
    private int repeticaoAtual;
    private int anoMes;
    private Double valor;
    private Conta contaOrigem;
    private Conta contaDestino;
    private Date dataTransferencia;
    private boolean alertaTranferencia;
    private boolean efetivada;


    //Propriedades

    public boolean isAlertaTranferencia() {
        return alertaTranferencia;
    }

    public void setAlertaTranferencia(boolean alertaTranferencia) {
        this.alertaTranferencia = alertaTranferencia;
    }

    public boolean isEfetivada() {
        return efetivada;
    }

    public void setEfetivada(boolean efetivada) {
        this.efetivada = efetivada;
    }


    public Date getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(Date dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getTotalRepeticao() {
        return totalRepeticao;
    }

    public void setTotalRepeticao(int totalRepeticao) {
        this.totalRepeticao = totalRepeticao;
    }

    public int getRepeticaoAtual() {
        return repeticaoAtual;
    }

    public void setRepeticaoAtual(int repeticaoAtual) {
        this.repeticaoAtual = repeticaoAtual;
    }

    public int getAnoMes() {
        return anoMes;
    }

    public void setAnoMes(int anoMes) {
        this.anoMes = anoMes;
    }


    public Conta getContaOrigem() {
        return contaOrigem == null ? new Conta() : contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino == null ? new Conta() : contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    //Métodos

    //Overrides
    @Override
    public String toString()
    {
        return super.getNome();
    }

   }
