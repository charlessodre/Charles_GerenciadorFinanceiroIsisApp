package com.charlessodre.apps.gerenciadorfinanceiroisis.Charts;

import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by charl on 06/04/2017.
 */

public class ColumnChart extends BaseChart {

    //Constructor
    public ColumnChart(Activity activity, ColumnChartView chart, boolean onValueTouchListenerEnable) {

        this.chart = chart;
        this.data = new ColumnChartData();
        this.activity = activity;

        if(onValueTouchListenerEnable)
            this.chart.setOnValueTouchListener(new ValueTouchListener());

    }

    //Attributes
    private ColumnChartView chart;
    private ColumnChartData data;
    private Activity activity;
    private String axisXName;
    private String axisYName;
    private ListDataElements listDataElements;

    public ListDataElements getListDataElements() {
        return listDataElements;
    }

    public void setListDataElements(ListDataElements listDataElements) {
        this.listDataElements = listDataElements;
    }


    public String getAxisXName() {
        return axisXName;
    }

    public void setAxisXName(String axisXName) {
        this.axisXName = axisXName;
    }

    public String getAxisYName() {
        return axisYName;
    }

    public void setAxisYName(String axisYName) {
        this.axisYName = axisYName;
    }

    //Properties
    public ColumnChartView getChart() {
        return chart;
    }

    public ColumnChartData getData() {
        return data;
    }

    public Activity getActivity() {
        return activity;
    }


    //Methods
    public void generateData() {

        int numSubcolumns = 1;
        int numColumns = this.listDataElements.getDataElementsChart().size();

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;

        for (DataElementChart dataElement : this.listDataElements.getDataElementsChart()) {

            values = new ArrayList<SubcolumnValue>();

            for (int j = 0; j < numSubcolumns; ++j) {

                SubcolumnValue subcolumnValue = new SubcolumnValue();
                subcolumnValue.setValue(dataElement.getValue());
                subcolumnValue.setColor(dataElement.getColor());
                subcolumnValue.setLabel(dataElement.getLabel());

                values.add(subcolumnValue);
            }

            Column column = new Column(values);
            column.setHasLabels(super.isHasLabels());
            column.setHasLabelsOnlyForSelected(super.isHasLabelForSelected());

            columns.add(column);
        }

     /*   for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            Column column = new Column(values);
            column.setHasLabels(super.isHasLabels());
            column.setHasLabelsOnlyForSelected(super.isHasLabelForSelected());
            columns.add(column);
        }*/

        this.data = new ColumnChartData(columns);

        if (super.isHasAxes()) {

            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);

            if (super.isHasAxesNames()) {
                axisX.setName(this.getAxisXName());
                axisY.setName(this.getAxisYName());
            }
            this.data.setAxisXBottom(axisX);
            this.data.setAxisYLeft(axisY);

        } else {
            this.data.setAxisXBottom(null);
            this.data.setAxisYLeft(null);
        }

        this.chart.setColumnChartData(this.data);

    }

    public void reset() {
        super.setHasAxes(true);
        super.setHasAxesNames(true);
        super.setHasLabels(false);
        super.setHasLabelForSelected(false);

        this.chart.setValueSelectionEnabled(super.isHasLabelForSelected());

    }


    //Class
    public class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
           // Toast.makeText(activity, "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

}
