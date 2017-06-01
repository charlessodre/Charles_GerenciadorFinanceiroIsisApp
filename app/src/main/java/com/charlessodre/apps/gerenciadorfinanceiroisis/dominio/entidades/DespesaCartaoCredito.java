package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import java.util.Date;

/**
 * Created by charl on 20/06/2017.
 */

public class DespesaCartaoCredito extends EntidadeBase  {


    //Constantes
    public static String  TABELA_NOME 			        = "TB_GF_DESPESA_CARTAO_CREDITO";

    public static final String NM_DESPESA               = "NM_DESPESA";
    public static final String DT_DESPESA               = "DT_DESPESA";
    public static final String VL_DESPESA               = "VL_DESPESA";
    public static final String DT_PAGAMENTO             = "DT_PAGAMENTO";
    public static final String VL_PAGAMENTO             = "VL_PAGAMENTO";
    public static final String NO_TOTAL_REPETICAO       = "NO_TOTAL_REPETICAO";
    public static final String NO_REPETICAO_ATUAL       = "NO_REPETICAO_ATUAL";
    public static final String FL_DESPESA_PAGA          = "FL_DESPESA_PAGA";
    public static final String FL_DEPESA_FIXA           = "FL_DEPESA_FIXA";
    public static final String FL_ALERTA_DESPESA        = "FL_ALERTA_DESPESA";
    public static final String NO_AM_DESPESA            = "NO_AM_DESPESA";
    public static final String ID_FATURA_CARTAO_CREDITO = "ID_FATURA_CARTAO_CREDITO";
    public static final String ID_CARTAO_CREDITO        = "ID_CARTAO_CREDITO";
    public static final String ID_CATEGORIA_DESPESA     = "ID_CATEGORIA_DESPESA";
    public static final String ID_SUB_CATEGORIA_DESPESA = "ID_SUB_CATEGORIA_DESPESA";
    public static final String ID_TIPO_REPETICAO        = "ID_TIPO_REPETICAO";
    public static final String NO_AM_PAGAMENTO_DESPESA  = "NO_AM_PAGAMENTO_DESPESA";
    public static final String ID_DESPESA_PAI = "ID_DESPESA_PAI";

    //Colunas lógicas
    public static final String VL_TOTAL_DESPESAS_CARTAO_LG      = "VL_TOTAL_DESPESAS_CARTAO";

    //Atributos

    private Date dataDespesa;
    private Double valorDespesa;
    private boolean paga;
    private boolean fixa;
    private int totalRepeticao;
    private int repeticaoAtual;
    private int anoMesDespesa;
    private CartaoCredito cartaoCredito;
    private FaturaCartaoCredito faturaCartaoCredito;
    private CategoriaDespesa categoriaDespesa;
    private Date dataPagamento;
    private Double valorPagamento;
    private boolean alertar;
    private SubCategoriaDespesa subCategoriaDespesa;
    private int idTipoRepeticao;
    private boolean estornaPagamento;
    private long idPai;
    private Integer anoMesPagamento;


    //Propriedades

    public Integer getAnoMesPagamento() {
        return anoMesPagamento;
    }

    public void setAnoMesPagamento(Integer anoMesPagamento) {
        this.anoMesPagamento = anoMesPagamento;
    }

    public long getIdPai() {
        return idPai;
    }

    public void setIdPai(long idPai) {
        this.idPai = idPai;
    }

    public boolean isEstornaPagamento() {
        return estornaPagamento;
    }

    public void setEstornaPagamento(boolean estornaPagamento) {
        this.estornaPagamento = estornaPagamento;
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
        return valorDespesa;
    }

    public void setValor(Double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public Date getDataDespesa() {
        return dataDespesa;
    }

    public void setDataDespesa(Date dataDespesa) {
        this.dataDespesa = dataDespesa;
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

    public int getAnoMesDespesa() {
        return anoMesDespesa;
    }

    public void setAnoMesDespesa(int anoMesDespesa) {
        this.anoMesDespesa = anoMesDespesa;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito == null ? new CartaoCredito() : cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public FaturaCartaoCredito getFaturaCartaoCredito() {
        return faturaCartaoCredito == null ? new FaturaCartaoCredito() : faturaCartaoCredito;
    }

    public void setFaturaCartaoCredito(FaturaCartaoCredito faturaCartaoCredito) {
        this.faturaCartaoCredito = faturaCartaoCredito;
    }


    public SubCategoriaDespesa getSubCategoriaDespesa() {
        return subCategoriaDespesa == null ? new SubCategoriaDespesa() : subCategoriaDespesa;
    }

    public void setSubCategoriaDespesa(SubCategoriaDespesa subCategoriaDespesa) {
        this.subCategoriaDespesa = subCategoriaDespesa;
    }


    public CategoriaDespesa getCategoriaDespesa() {
        return categoriaDespesa == null ? new CategoriaDespesa() : categoriaDespesa;
    }

    public void setCategoriaDespesa(CategoriaDespesa categoriaDespesa) {
        this.categoriaDespesa = categoriaDespesa;
    }

    public int getIdTipoRepeticao() {
        return idTipoRepeticao;
    }

    public void setIdTipoRepeticao(int idTipoRepeticao) {
        this.idTipoRepeticao = idTipoRepeticao;
    }


    //Métodos
    public DespesaCartaoCredito clone()  {
        try {
            return (DespesaCartaoCredito) super.clone();
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
