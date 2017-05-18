package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import java.util.Date;


public class Receita extends EntidadeBase {


    //Constantes
    public static String TABELA_NOME = "TB_GF_RECEITA_CONTA";

    public static String NM_RECEITA = "NM_RECEITA";
    public static String VL_RECEITA = "VL_RECEITA";
    public static String DT_RECEITA = "DT_RECEITA";
    public static String FL_RECEITA_PAGA = "FL_RECEITA_PAGA";
    public static String FL_RECEITA_FIXA = "FL_RECEITA_FIXA";
    public static String NO_TOTAL_REPETICAO = "NO_TOTAL_REPETICAO";
    public static String NO_REPETICAO_ATUAL = "NO_REPETICAO_ATUAL";
    public static String NO_AM_RECEITA = "NO_AM_RECEITA";
    public static String ID_CONTA = "ID_CONTA";
    public static String ID_CATEGORIA_RECEITA = "ID_CATEGORIA_RECEITA";
    public static String ID_TIPO_REPETICAO = "ID_TIPO_REPETICAO";
    public static String DT_RECEBIMENTO = "DT_RECEBIMENTO";
    public static String ID_RECEITA_PAI = "ID_RECEITA_PAI";
    public static String NO_AM_RECEBIMENTO_RECEITA = "NO_AM_RECEBIMENTO_RECEITA";
    public static String FL_ALERTA_RECEITA = "FL_ALERTA_RECEITA";


    //Atributos

    private Double valor;
    private Date dataReceita;
    private boolean paga;
    private boolean fixa;
    private int totalRepeticao;
    private int repeticaoAtual;
    private int anoMes;
    private Conta conta;
    private CategoriaReceita categoriaReceita;
    private int idTipoRepeticao;
    private Date dataRecebimento;
    private long idPai;
    private boolean estornaRecebimentoReceita;
    private Integer anoMesRecebimentoReceita;
    private boolean alertar;



    //Propriedades

    public boolean isAlertar() {
        return alertar;
    }

    public void setAlertar(boolean alertar) {
        this.alertar = alertar;
    }

    public Integer getAnoMesRecebimentoReceita() {
        return anoMesRecebimentoReceita;
    }

    public void setAnoMesRecebimentoReceita(Integer anoMesRecebimentoReceita) {
        this.anoMesRecebimentoReceita = anoMesRecebimentoReceita;
    }

    public boolean isEstornaRecebimentoReceita() {
        return estornaRecebimentoReceita;
    }

    public void setEstornaRecebimentoReceita(boolean estornaRecebimentoReceita) {
        this.estornaRecebimentoReceita = estornaRecebimentoReceita;
    }
    public long getIdPai() {
        return idPai;
    }

    public void setIdPai(long idPai) {
        this.idPai = idPai;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public int getIdTipoRepeticao() {
        return idTipoRepeticao;
    }

    public void setIdTipoRepeticao(int idTipoRepeticao) {
        this.idTipoRepeticao = idTipoRepeticao;
    }

    public Conta getConta() {
        return conta == null ? new Conta() : conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public CategoriaReceita getCategoriaReceita() {
        return categoriaReceita == null ? new CategoriaReceita() : categoriaReceita;
    }

    public void setCategoriaReceita(CategoriaReceita categoriaReceita) {
        this.categoriaReceita = categoriaReceita;
    }


    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataReceita() {
        return dataReceita;
    }

    public void setDataReceita(Date dataReceita) {
        this.dataReceita = dataReceita;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public boolean isFixa() {
        return fixa;
    }

    public void setFixa(boolean fixa) {
        this.fixa = fixa;
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

    //Métodos

    public Receita clone()  {
        try {
            return (Receita) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



    //Overrides
    @Override
    public String toString() {
        return super.getNome();
    }


}
