package com.charlessodre.apps.gerenciadorfinanceiroisis.Charts;

import java.io.Serializable;

import lecho.lib.hellocharts.util.ChartUtils;

/**
 * Created by charl on 06/04/2017.
 */

public class DataElementChart implements Serializable{

    private int color = ChartUtils.pickColor();;
    private String label ="";
    private float value = 0;

    public DataElementChart(String label, float value, int color)
    {
        this.label = label;
        this.value = value;
        this.color = color;

    }

    public DataElementChart(String label, float value)
    {
        this.label = label;
        this.value = value;

    }

    public DataElementChart( float value)
    {
        this.value = value;
    }

    public DataElementChart(float value, int color)
    {
        this.value = value;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }

    public float getValue() {
        return value;
    }


}
