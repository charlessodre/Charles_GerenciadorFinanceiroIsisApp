package com.charlessodre.apps.gerenciadorfinanceiroisis.Charts;

import android.widget.Toast;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.SubcolumnValue;

/**
 * Created by charl on 06/04/2017.
 */

public abstract class BaseChart {


    //Attributes
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;


    //Properties
    public boolean isHasAxes() {
        return hasAxes;
    }

    public void setHasAxes(boolean hasAxes) {
        this.hasAxes = hasAxes;
    }

    public boolean isHasAxesNames() {
        return hasAxesNames;
    }

    public void setHasAxesNames(boolean hasAxesNames) {
        this.hasAxesNames = hasAxesNames;
    }

    public boolean isHasLabels() {
        return hasLabels;
    }

    public void setHasLabels(boolean hasLabels) {
        this.hasLabels = hasLabels;
    }

    public boolean isHasLabelForSelected() {
        return hasLabelForSelected;
    }

    public void setHasLabelForSelected(boolean hasLabelForSelected) {
        this.hasLabelForSelected = hasLabelForSelected;
    }



}
