package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlessodre.apps.gerenciadorfinanceiroisis.Charts.ColumnChart;
import com.charlessodre.apps.gerenciadorfinanceiroisis.Charts.DataElementChart;
import com.charlessodre.apps.gerenciadorfinanceiroisis.Charts.ListDataElements;
import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

import java.util.List;

import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class frgGraficoBarras extends Fragment {

    //Constantes
    public static final String NOME_FRAGMENTO = "FRAG_GRAF_BARRAS";
    public static final String LISTA_ELEMENTOS_GRAFICO = "LISTA_ELEMENTOS_GRAFICO";

    //Atributos
    private ColumnChart columnChart =null;
    private ListDataElements listDataElements= null;
    private View rootView;


    public frgGraficoBarras() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.rootView = inflater.inflate(R.layout.frg_grafico_barra, container, false);

        this.getParametrosRecebidos();
        this.inicializaObjetos();

        this.columnChart.generateData();

        return this.rootView;
    }

    private void inicializaObjetos() {

        this.columnChart = new ColumnChart(this.getActivity(),(ColumnChartView) this.rootView.findViewById(R.id.chart),this.listDataElements);

    }

    protected void getParametrosRecebidos() {
        Bundle argument = getArguments();

        if ((argument != null) && (argument.containsKey(LISTA_ELEMENTOS_GRAFICO))) {
            this.listDataElements = (ListDataElements) argument.getSerializable(LISTA_ELEMENTOS_GRAFICO);
        }


    }


}
