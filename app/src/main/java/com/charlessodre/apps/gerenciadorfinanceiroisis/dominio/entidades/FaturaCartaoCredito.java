package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import java.util.Date;

/**
 * Created by charl on 18/05/2017.
 */

public class FaturaCartaoCredito extends EntidadeBase  {


    //Constantes
    public static String  TABELA_NOME 			  = "TB_GF_FATURA_CARTAO_CREDITO";

    public static final String DT_FATURA                = "DT_FATURA";
    public static final String VL_FATURA                = "VL_FATURA";
    public static final String DT_PAGAMENTO             = "DT_PAGAMENTO";
    public static final String VL_PAGAMENTO             = "VL_PAGAMENTO";
    public static final String FL_FATURA_PAGA           = "FL_FATURA_PAGA";
    public static final String FL_FATURA_FECHADA           = "FL_FATURA_FECHADA";
    public static final String FL_ALERTA_FECHAMENTO_FATURA = "FL_ALERTA_FECHAMENTO_FATURA";
    public static final String NO_AM_FATURA            = "NO_AM_FATURA";
    public static final String NO_AM_PAGAMENTO_FATURA  = "NO_AM_PAGAMENTO_FATURA";
    public static final String ID_CARTAO_CREDITO       = "ID_CARTAO_CREDITO";


    //Atributos

    private Date dataFatura;
    private Double valorFatura;
    private Date dataPagamento;
    private Double valorPagamento;
    private boolean paga;
    private boolean fechada;
    private int anoMesFatura;
    private Integer anoMesPagamento;
    private CartaoCredito cartaoCredito;
    private boolean alertar;

    //Propriedades

    public Integer getAnoMesPagamento() {
        return anoMesPagamento;
    }

    public void setAnoMesPagamento(Integer anoMesPagamento) {
        this.anoMesPagamento = anoMesPagamento;
    }

    public boolean isAlertar() {
        return alertar;
    }

    public void setAlertar(boolean alertar) {
        this.alertar = alertar;
    }

    public Double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(Double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getValor() {
        return valorFatura;
    }

    public void setValor(Double valorDespesa) {
        this.valorFatura = valorDespesa;
    }

    public Date getDataFatura() {
        return dataFatura;
    }

    public void setDataFatura(Date dataFatura) {
        this.dataFatura = dataFatura;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public boolean isFechada() {
        return fechada;
    }

    public void setFechada(boolean fechada) {
        this.fechada = fechada;
    }

    public int getAnoMesFatura() {
        return anoMesFatura;
    }

    public void setAnoMesFatura(int anoMesFatura) {
        this.anoMesFatura = anoMesFatura;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito == null ? new CartaoCredito() : cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }


    //Métodos
    public FaturaCartaoCredito clone()  {
        try {
            return (FaturaCartaoCredito) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    //Overrides
    @Override
    public String toString()
    {
        return super.getNome();
    }
}
