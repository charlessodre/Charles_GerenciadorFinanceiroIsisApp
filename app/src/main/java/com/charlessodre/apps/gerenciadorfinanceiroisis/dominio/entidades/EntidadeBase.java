package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import android.content.Context;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by charl on 10/09/2016.
 */
public abstract class EntidadeBase implements Serializable, Cloneable {

    public EntidadeBase()
    {
        id = 0;
    }

    //Constantes
    public static String ID = "_id";
    public static String FL_ATIVO = "FL_ATIVO";
    public static String DT_INCLUSAO = "DT_INCLUSAO";
    public static String DT_ALTERACAO = "DT_ALTERACAO";
    public static String FL_EXIBIR = "FL_EXIBIR";
    public static String NO_ORDEM_EXIBICAO = "NO_ORDEM_EXIBICAO";

    //Atributos
    private long id;
    private String nome;
    private Date dataInclusao;
    private Date dataAlteracao;
    private boolean ativo;
    private  boolean exibir;
    private int ordemExibicao;

    //Propriedades
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isExibir() {
        return exibir;
    }

    public void setExibir(boolean exibir) {
        this.exibir = exibir;
    }

    public int getOrdemExibicao() {
        return ordemExibicao;
    }

    public void setOrdemExibicao(int ordemExibicao) {
        this.ordemExibicao = ordemExibicao;
    }

    //Métodos


}
