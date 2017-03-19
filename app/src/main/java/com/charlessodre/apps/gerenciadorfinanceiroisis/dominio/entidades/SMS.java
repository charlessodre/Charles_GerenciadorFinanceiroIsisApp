package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by charl on 19/03/2017.
 */

public class SMS implements Serializable{

    //Constantes
    public  final static String URI = "content://sms";
    public final static String BODY = "body";
    public final static String ADDRESS = "address";
    public final static String DATE = "date";
    public final static String TYPE = "type";

    public final static  int INBOX = 1;
    public final static int SENT = 2;
    public final static int DRAFT = 3;


    //Atributos
    private long id;
    private String mensagem;
    private String numero;
    private Date data;
    private String tipoSMS;

    //Propriedades
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipoSMS() {
        return tipoSMS;
    }

    public void setTipoSMS(String tipoSMS) {
        this.tipoSMS = tipoSMS;
    }


    //Métodos
    public static String getTipoSMS(int type) {
        switch (type) {
            case SMS.INBOX:
                return "INBOX";

            case SMS.SENT:
                return "SENT";

            case SMS.DRAFT:
                return "DRAFT";

            default:
                return "";

            }
    }

}
