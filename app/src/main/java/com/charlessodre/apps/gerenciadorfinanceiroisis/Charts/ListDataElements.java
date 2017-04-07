package com.charlessodre.apps.gerenciadorfinanceiroisis.Charts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by charl on 06/04/2017.
 */

public class ListDataElements implements Serializable {

    ArrayList<DataElementChart> dataElementChartList = null;

    public ListDataElements() {
        this.dataElementChartList = new ArrayList<DataElementChart>();

    }

    public ArrayList<DataElementChart> getDataElementsChart()
    {
        return this.dataElementChartList;
    }

}
