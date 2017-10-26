package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import android.content.Context;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

import java.util.ArrayList;

/**
 * Created by charl on 01/05/2017.
 */

public class CartaoCredito extends EntidadeBase {

    //Constantes
    public static String TABELA_NOME = "TB_GF_CARTAO_CREDITO";

    public static String NM_CARTAO = "NM_CARTAO";
    public static String VL_LIMITE = "VL_LIMITE";
    public static String NO_DIA_FECHAMENTO_FATURA = "NO_DIA_FECHAMENTO_FATURA";
    public static String NO_DIA_VENCIMENTO_FATURA = "NO_DIA_VENCIMENTO_FATURA";
    public static String NO_BANDEIRA_CARTAO = "NO_BANDEIRA_CARTAO";
    public static String NO_COR = "NO_COR";
    public static String FL_ALERTA_VENCIMENTO = "FL_ALERTA_VENCIMENTO";
    public static String ID_CONTA_ASSOCIADA = "ID_CONTA_ASSOCIADA";
    public static String FL_EXIBIR_SOMA = "FL_EXIBIR_SOMA";
    public static String FL_AGRUPAR_DESPESAS = "FL_AGRUPAR_DESPESAS";
    public static String RECEITAS_PREVISTAS = "RECEITAS_PREVISTAS";
    public static String DESPESAS_PREVISTAS = "DESPESAS_PREVISTAS";

    public static String VL_TAXA_JUROS_ROTATIVO = "VL_TAXA_JUROS_ROTATIVO";
    public static String VL_TAXA_JUROS_FINANCIAMENTO = "VL_TAXA_JUROS_FINANCIAMENTO";

    //Colunas lógicas
    public static final String VL_TOTAL_DESPESA_LG      = "VL_TOTAL_DESPESA";
    public static final String DESPESAS_TOTAL_PREVISTAS = "DESPESAS_TOTAL_PREVISTAS";


    //Atributos
    private int noCor;
    private Double valorLimite;
    private int noDiaFechamentoFatura;
    private int noDiaVencimentoFatura;
    private int noBandeiraCartao;
    private boolean altertaVencimento;
    private Conta contaAssociada;
    private boolean exibiSomaResumo;
    private boolean agruparDespesas;
    private double receitasPrevistas;
    private double despesasPrevistas;
    private double despesasTotalPrevistas = 0;

    private Double valorTaxaJurosFinaciamento;
    private Double valorTaxaJurosRotativo;

    //Propriedades

    public double getDespesasTotalPrevistas() {
        return despesasTotalPrevistas;
    }

    public void setDespesasTotalPrevistas(double despesasTotalPrevistas) {
        this.despesasTotalPrevistas = despesasTotalPrevistas;
    }

    public Double getValorTaxaJurosFinaciamento() {
        return valorTaxaJurosFinaciamento;
    }

    public void setValorTaxaJurosFinaciamento(Double valorTaxaJurosFinaciamento) {
        this.valorTaxaJurosFinaciamento = valorTaxaJurosFinaciamento;
    }

    public Double getValorTaxaJurosRotativo() {
        return valorTaxaJurosRotativo;
    }

    public void setValorTaxaJurosRotativo(Double valorTaxaJurosRotativo) {
        this.valorTaxaJurosRotativo = valorTaxaJurosRotativo;
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

    public double getLimiteDisponivel() {

      return    (this.valorLimite +  this.receitasPrevistas) - this.despesasTotalPrevistas ;
    }
    public boolean isAgruparDespesas() {
        return agruparDespesas;
    }

    public void setAgruparDespesas(boolean agruparDespesas) {
        this.agruparDespesas = agruparDespesas;
    }

    public boolean isExibiSomaResumo() {
        return exibiSomaResumo;
    }

    public void setExibiSomaResumo(boolean exibiSomaResumo) {
        this.exibiSomaResumo = exibiSomaResumo;
    }

    public int getNoCor() {
        return noCor;
    }

    public void setNoCor(int noCor) {
        this.noCor = noCor;
    }

    public Double getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(Double valorLimite) {
        this.valorLimite = valorLimite;
    }

    public int getNoDiaFechamentoFatura() {
        return noDiaFechamentoFatura;
    }

    public void setNoDiaFechamentoFatura(int noDiaFechamentoFatura) {
        this.noDiaFechamentoFatura = noDiaFechamentoFatura;
    }

    public int getNoDiaVencimentoFatura() {
        return noDiaVencimentoFatura;
    }

    public void setNoDiaVencimentoFatura(int noDiaVencimentoFatura) {
        this.noDiaVencimentoFatura = noDiaVencimentoFatura;
    }

    public int getNoBandeiraCartao() {
        return noBandeiraCartao;
    }

    public void setNoBandeiraCartao(int noBandeiraCartao) {
        this.noBandeiraCartao = noBandeiraCartao;
    }

    public boolean isAltertaVencimento() {
        return altertaVencimento;
    }

    public void setAltertaVencimento(boolean altertaVencimento) {
        this.altertaVencimento = altertaVencimento;
    }

    public Conta getContaAssociada() {
        return contaAssociada == null? new Conta(): contaAssociada;
    }

    public void setContaAssociada(Conta contaAssociada) {
        this.contaAssociada = contaAssociada;
    }

    //Métodos

    public static ArrayList<String> getBandeiraCartao(Context context) {
        ArrayList<String> bandeirasCartao = new ArrayList<String>();

        bandeirasCartao.add(context.getString(R.string.lblCartaoVisa));
        bandeirasCartao.add(context.getString(R.string.lblCartaoMasterCard));
        bandeirasCartao.add(context.getString(R.string.lblCartaoHiperCard));
        bandeirasCartao.add(context.getString(R.string.lblCartaoAmericanExpress));
        bandeirasCartao.add(context.getString(R.string.lblCartaoDinersClub));
        bandeirasCartao.add(context.getString(R.string.lblCartaoBNDES));
        bandeirasCartao.add(context.getString(R.string.lblCartaoAlelo));
        bandeirasCartao.add(context.getString(R.string.lblCartaoSodexo));
        bandeirasCartao.add(context.getString(R.string.lblCartaoOutros));

        return bandeirasCartao;
    }


    public static int getImagemBandeiraCartao(int noBandeiraCartao) {

        switch (noBandeiraCartao) {

            case 0:

                return R.drawable.cartao_visa_64;

            case 1:
                return R.drawable.cartao_mastercard_64;

            case 2:
                return R.drawable.cartao_hipercard_64;

            case 3:

                return R.drawable.cartao_americanexpress_64;

            case 4:

                return R.drawable.cartao_dinners_club_64;

            case 5:

                return R.drawable.cartao_bndes_64;

            case 6:

                return R.drawable.cartao_alelo_64;

            case 7:

                return R.drawable.cartao_sodexo_64;

            default:
                return R.drawable.cartao_outros_64;
        }

    }


    //Overrides
    @Override
    public String toString() {
        return super.getNome();
    }
}

