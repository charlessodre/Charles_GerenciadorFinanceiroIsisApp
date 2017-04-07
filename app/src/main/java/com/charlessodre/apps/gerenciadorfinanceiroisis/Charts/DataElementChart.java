package com.charlessodre.apps.gerenciadorfinanceiroisis.Charts;

import java.io.Serializable;

/**
 * Created by charl on 06/04/2017.
 */

public class DataElementChart implements Serializable{

    private int color;
    private String label;
    private int value;

    public DataElementChart(String label, int value, int color)
    {
        this.label = label;
        this.value = value;
        this.color = color;

    }


    public int getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }


}
