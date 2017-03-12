package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

/**
 * Created by charl on 27/09/2016.
 */

public class RegraImportacaoSMS extends EntidadeBase {


    //Constantes
    public static String TABELA_NOME = "TB_GF_SUB_REGRA_IMPORT_SMS";

    public static String NM_REGRA_IMPORTACAO = "NM_REGRA_IMPORTACAO";
    public static String NO_TELEFONE = "NO_TELEFONE";
    public static String DS_TEXTO_PESQUISA = "DS_TEXTO_PESQUISA";
    public static String ID_TIPO_TRANSACAO = "ID_TIPO_TRANSACAO";
    public static String ID_CATEGORIA_RECEITA = "ID_CATEGORIA_RECEITA";
    public static String ID_CATEGORIA_DESPESA = "ID_CATEGORIA_DESPESA";
    public static String ID_CONTA_ORIGEM = "ID_CONTA_ORIGEM";
    public static String ID_CONTA_DESTINO = "ID_CONTA_DESTINO";
    public static String ID_SUB_CATEGORIA_DESPESA = "ID_SUB_CATEGORIA_DESPESA";


    //Atributos
    private Conta contaOrigem;
    private Conta contaDestino;
    private String noTelefone;
    private String textoPesquisa;
    private CategoriaDespesa categoriaDespesa;
    private SubCategoriaDespesa subCategoriaDespesa;
    private CategoriaReceita categoriaReceita;
    private int idTipoTransacao;


    //Propriedades
    public int getIdTipoTransacao() {
        return idTipoTransacao;
    }

    public void setIdTipoTransacao(int idTipoTransacao) {
        this.idTipoTransacao = idTipoTransacao;
    }

    public String getTextoPesquisa() {
        return textoPesquisa;
    }

    public void setTextoPesquisa(String textoPesquisa) {
        this.textoPesquisa = textoPesquisa;
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

}
