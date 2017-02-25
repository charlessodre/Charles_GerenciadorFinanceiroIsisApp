package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import java.io.Serializable;

/**
 * Created by charl on 27/09/2016.
 */

public class SubCategoriaDespesa extends EntidadeBase implements Serializable

{


    //Constantes
    public static String TABELA_NOME = "TB_GF_SUB_CATEGORIA_DESPESA";
    public static String NM_SUB_CATEGORIA = "NM_SUB_CATEGORIA";
    public static String NO_COR = "NO_COR";
    public static String ID_CATEGORIA_DESPESA = "ID_CATEGORIA_DESPESA";


    //Atributos
    private int noCor;
    private long idCategoriaDespesa;


    //Propriedades
    public long getIdCategoriaDespesa() {
        return idCategoriaDespesa;
    }

    public void setIdCategoriaDespesa(long idCategoriaDespesa) {
        this.idCategoriaDespesa = idCategoriaDespesa;
    }

    public int getNoCor() {
        return noCor;
    }

    public void setNoCor(int noCor) {
        this.noCor = noCor;
    }

//Métodos

    //Overrides
    @Override
    public String toString() {
        return super.getNome();
    }

}
