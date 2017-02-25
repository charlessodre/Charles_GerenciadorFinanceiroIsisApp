package com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.StringUtils;

import java.util.Calendar;

public abstract class actBaseListas extends AppCompatActivity {

    //Atributos
    private Calendar calendar;

    //Propriedades
    public Calendar getCalendar() {
        return calendar;
    }

    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.calendar = Calendar.getInstance();

    }

    //Métodos
    protected abstract void inicializaObjetos();

    protected void setMenuHome(String titulo)
    {
        ActionBarHelper.menuHome(getSupportActionBar(), titulo);
    }

    protected void setColorStatusBar(int color)
    {
        ActionBarHelper.setStatusBarColor(this.getWindow(), ColorHelper.getColor(this, color));

    }

    protected void setColorActionBar(int color)
    {
        ActionBarHelper.setBackgroundColor(getSupportActionBar(), Color.parseColor(getResources().getString(color)));
    }

    protected void setAddMesCalendar(int mesAdd)
    {
        this.calendar.add(Calendar.MONTH,mesAdd);
    }

    protected void setAnoMesCalendar(int anoMes)
    {
        int ano =  Integer.parseInt(String.valueOf(anoMes).substring(0,4));

        int mes = Integer.parseInt(String.valueOf(anoMes).substring(4));

        this.calendar.set(ano,mes,0);
    }


    protected String getNomeMesFormatado()
    {
        String nomeMes;

        nomeMes = DateUtils.getMonthNameLong( this.calendar.getTime());
        nomeMes = nomeMes+"/"+this.calendar.get(Calendar.YEAR);

        return StringUtils.upperCaseFirstLetter( nomeMes);

    }

    protected int getAnoMes()
    {
        return DateUtils.getYearAndMonth(this.calendar.getTime());
    }


}
