package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import android.support.annotation.NonNull;

import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by charl on 27/09/2016.
 */

public class RegraImportacaoSMS extends EntidadeBase {

    //Constantes
    public static String TABELA_NOME = "TB_GF_SUB_REGRA_IMPORT_SMS";

    public static String NM_REGRA_IMPORTACAO = "NM_REGRA_IMPORTACAO";
    public static String NO_TELEFONE = "NO_TELEFONE";
    public static String DS_TEXTO_PESQUISA_1 = "DS_TEXTO_PESQUISA_1";
    public static String DS_TEXTO_PESQUISA_2 = "DS_TEXTO_PESQUISA_2";
    public static String ID_TIPO_TRANSACAO = "ID_TIPO_TRANSACAO";
    public static String ID_CATEGORIA_RECEITA = "ID_CATEGORIA_RECEITA";
    public static String ID_CATEGORIA_DESPESA = "ID_CATEGORIA_DESPESA";
    public static String ID_CONTA_ORIGEM = "ID_CONTA_ORIGEM";
    public static String ID_CONTA_DESTINO = "ID_CONTA_DESTINO";
    public static String ID_SUB_CATEGORIA_DESPESA = "ID_SUB_CATEGORIA_DESPESA";
    public static String DS_RECEITA_DESPESA = "DS_RECEITA_DESPESA";


    //Atributos
    private Conta contaOrigem;
    private Conta contaDestino;
    private String noTelefone;
    private String textoPesquisa1;
    private String textoPesquisa2;
    private CategoriaDespesa categoriaDespesa;
    private SubCategoriaDespesa subCategoriaDespesa;
    private CategoriaReceita categoriaReceita;
    private int idTipoTransacao;
    private String descricaoReceitaDespesa;


    //Propriedades


    public String getDescricaoReceitaDespesa() {
        return descricaoReceitaDespesa;
    }

    public void setDescricaoReceitaDespesa(String descricaoReceitaDespesa) {
        this.descricaoReceitaDespesa = descricaoReceitaDespesa;
    }

    public int getIdTipoTransacao() {
        return idTipoTransacao;
    }

    public void setIdTipoTransacao(int idTipoTransacao) {
        this.idTipoTransacao = idTipoTransacao;
    }

    public String getTextoPesquisa1() {
        return textoPesquisa1;
    }

    public void setTextoPesquisa1(String textoPesquisa1) {
        this.textoPesquisa1 = textoPesquisa1;
    }

    public String getTextoPesquisa2() {
        return textoPesquisa2;
    }

    public void setTextoPesquisa2(String textoPesquisa2) {
        this.textoPesquisa2 = textoPesquisa2;
    }



    public String getNoTelefone() {
        return noTelefone;
    }

    public void setNoTelefone(String noTelefone) {
        this.noTelefone = noTelefone;
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

    public CategoriaReceita getCategoriaReceita() {
        return categoriaReceita == null ? new CategoriaReceita() : categoriaReceita;
    }

    public void setCategoriaReceita(CategoriaReceita categoriaReceita) {
        this.categoriaReceita = categoriaReceita;
    }
    //Métodos

    //Overrides
    @Override
    public String toString() {
        return super.getNome();
    }

    public static ArrayList<String> getTipoTransacao()
    {
        ArrayList<String> tipoTransacao = new ArrayList<String>();

        tipoTransacao.add("Receita");
        tipoTransacao.add("Despesa");
        tipoTransacao.add("Transferência");

        return  tipoTransacao;
    }

    public static String getNomeTipoTransacao(int idTipoTransacao)
    {
       switch (idTipoTransacao) {

           case Constantes.TipoTransacao.RECEITA:
               return "Receita";
           case Constantes.TipoTransacao.DESPESA:
               return "Despesa";
           default:
               return "Transferência";

       }
    }

   /*@Override
    public boolean equals(Object objeto) {

        boolean iguais = false;

        if (objeto!=null && objeto instanceof SMS){
            SMS sms = (SMS) objeto;

            if(sms.getNumero().indexOf(this.getNoTelefone())>=0 && sms.getMensagem().indexOf(this.getTextoPesquisa1())>=0)
            {
                iguais = true;
            }
        }

        return iguais;
    }
    */


}
