package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by charl on 20/10/2016.
 */

public class TipoRepeticao {

    public static final int DIARIA = 0;
    public static final int SEMANAL = 1;
    public static final int QUINZENAL = 2;
    public static final int MENSAL = 3;
    public static final int TRIMESTRAL = 4;
    public static final int SEMESTRAL = 5;
    public static final int ANUAL = 6;


    public static ArrayList<String> getTipoRepeticao()
    {
        ArrayList<String> tipoRepeticao = new ArrayList<String>();

        tipoRepeticao.add("Diária");
        tipoRepeticao.add("Semanal");
        tipoRepeticao.add("Quinzenal");
        tipoRepeticao.add("Mensal");
        tipoRepeticao.add("Trimestral");
        tipoRepeticao.add("Semestral");
        tipoRepeticao.add("Anual");

        return  tipoRepeticao;
    }

    public static Date getDataRepeticao(int tipoRepeticao, Date data) {

        if (tipoRepeticao == TipoRepeticao.SEMANAL) {
            data = DateUtils.getDateAddDays(data, 7);

        } else if (tipoRepeticao == TipoRepeticao.QUINZENAL) {
            data = DateUtils.getDateAddDays(data, 15);

        } else if (tipoRepeticao == TipoRepeticao.MENSAL) {

            data = DateUtils.getDateAddMonth(data, 1);

        } else if (tipoRepeticao == TipoRepeticao.TRIMESTRAL) {
            data = DateUtils.getDateAddMonth(data, 3);

        } else if (tipoRepeticao == TipoRepeticao.SEMESTRAL) {
            data = DateUtils.getDateAddMonth(data, 6);
        } else if (tipoRepeticao == TipoRepeticao.ANUAL) {

            data = DateUtils.getDateAddYears(data, 1);
        }

        return data;


    }
}
