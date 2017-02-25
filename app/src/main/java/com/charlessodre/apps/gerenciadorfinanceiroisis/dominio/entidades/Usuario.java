package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by charl on 27/09/2016.
 */

public class Usuario extends EntidadeBase implements Serializable {



    //Constantes
    public static String  TABELA_NOME 		  = "TB_GF_USUARIO";

    public static String NM_USUARIO            = "NM_USUARIO";
    public static String CD_LOGIN_EMAIL        = "CD_LOGIN_EMAIL";
    public static String CD_SENHA_EMAIL        = "CD_SENHA_EMAIL";
    public static String DT_EXPIRACAO_SENHA    = "DT_EXPIRACAO_SENHA";
    public static String FL_LOGIN_ALTERNATIVO  = "FL_LOGIN_ALTERNATIVO";
    public static String CD_SENHA_ALTERNATIVA  = "CD_SENHA_ALTERNATIVA";


    //Atributos

    private String email;
    private String senha;
    private boolean loginAlternativo;
    private String senhaAlternativa;
    private Date dataExpiracaoSenha;



    //Propriedades

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isLoginAlternativo() {
        return loginAlternativo;
    }

    public void setLoginAlternativo(boolean loginAlternativo) {
        this.loginAlternativo = loginAlternativo;
    }


    public String getSenhaAlternativa() {
        return senhaAlternativa;
    }

    public void setSenhaAlternativa (String senhaAlternativa) {
        this.senhaAlternativa = senhaAlternativa;
    }

    public Date getDataExpiracaoSenha() {
        return dataExpiracaoSenha;
    }

    public void setDataExpiracaoSenha(Date dataExpiracaoSenha) {
        this.dataExpiracaoSenha = dataExpiracaoSenha;
    }


    //Métodos

    //Overrides
    @Override
    public String toString()
    {
        return super.getNome();
    }
}