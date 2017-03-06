package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import java.io.Serializable;

/**
 * Created by charl on 24/09/2016.
 */

public class CategoriaReceita extends EntidadeBase implements Serializable {


    //Constantes
    public static String TABELA_NOME = "TB_GF_CATEGORIA_RECEITA";
    public static String NM_CATEGORIA = "NM_CATEGORIA";
    public static String NO_COR = "NO_COR";
    public static String NO_ICONE = "NO_ICONE";
    public static String NO_COR_ICONE = "NO_COR_ICONE";

    //Atributos
    private int noCor;
    private int noIcone;
    private int noCorIcone;



    //Propriedades
    public int getNoCor() {
        return noCor;
    }

    public void setNoCor(int noCor) {
        this.noCor = noCor;
    }

    public int getNoIcone() {
        return noIcone;
    }

    public void setNoIcone(int noIcone) {
        this.noIcone = noIcone;
    }

    public int getNoCorIcone() {
        return noCorIcone;
    }

    public void setNoCorIcone(int noCorIcone) {
        this.noCorIcone = noCorIcone;
    }

//Métodos

    //Overrides
    @Override
    public String toString()
    {
        return super.getNome();
    }

}
