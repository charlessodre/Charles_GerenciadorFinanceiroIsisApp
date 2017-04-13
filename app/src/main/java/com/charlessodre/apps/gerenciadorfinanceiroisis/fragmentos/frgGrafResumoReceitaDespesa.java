package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.Charts.ColumnChart;
import com.charlessodre.apps.gerenciadorfinanceiroisis.Charts.DataElementChart;
import com.charlessodre.apps.gerenciadorfinanceiroisis.Charts.ListDataElements;
import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;

import java.text.NumberFormat;
import java.util.Locale;

import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class frgGrafResumoReceitaDespesa extends Fragment {

    //Constantes
    public static final String NOME_FRAGMENTO = "FRAG_GRAF_BARRAS";
    public static final String PARAM_ANO_MES = "PARAM_ANO_MES";
    public static final String PARAM_SOMENTE_CONFIRMADAS = "PARAM_SOMENTE_CONFIRMADAS";

    //Objetos Tela
    private ColumnChart columnChart = null;
    private ListDataElements listDataElements = null;
    private View rootView;
    private TextView txtSaldo;
    private TextView txtDespesaTotal;
    private TextView txtReceitaTotal;
    private TextView txtTituloGrafico;


    //Atributos
    private int anoMes = DateUtils.getCurrentYearAndMonth();
    public boolean somenteConfirmadas = false;
    private RepositorioReceita repositorioReceita;
    private RepositorioDespesa repositorioDespesa;

    //Atributos
    public frgGrafResumoReceitaDespesa() {
        // Required empty public constructor
    }


    public static frgGrafResumoReceitaDespesa newInstance(int anoMes, boolean somenteConfirmadas) {

        frgGrafResumoReceitaDespesa novoFragmento = new frgGrafResumoReceitaDespesa();

        Bundle args = new Bundle();

        args.putInt(PARAM_ANO_MES, anoMes);
        args.putBoolean(PARAM_SOMENTE_CONFIRMADAS, somenteConfirmadas);

        novoFragmento.anoMes = anoMes;
        novoFragmento.somenteConfirmadas = somenteConfirmadas;

        novoFragmento.setArguments(args);

        return novoFragmento;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.rootView = inflater.inflate(R.layout.frg_graf_resumo_receita_despesa, container, false);

        this.getParametrosRecebidos();
        this.inicializaObjetos();

        return this.rootView;
    }

    private void inicializaObjetos() {


        this.txtSaldo = (TextView) this.rootView.findViewById(R.id.txtSaldo);
        this.txtDespesaTotal = (TextView) this.rootView.findViewById(R.id.txtDespesaTotal);
        this.txtReceitaTotal = (TextView) this.rootView.findViewById(R.id.txtReceitaTotal);
        this.txtTituloGrafico = (TextView) this.rootView.findViewById(R.id.txtTituloGrafico);

        //Instancia o grafico.
        this.columnChart = new ColumnChart(this.getActivity(), (ColumnChartView) this.rootView.findViewById(R.id.chart));

        if (this.somenteConfirmadas)
            this.txtTituloGrafico.setText(this.getContext().getString(R.string.title_receitas_x_despesas_confirmadas));

        this.listDataElements = new ListDataElements();

        this.repositorioReceita = new RepositorioReceita(this.getContext());
        this.repositorioDespesa = new RepositorioDespesa(this.getContext());

        Double valorAcumuladoReceitas = 0.0;
        Double valorAcumuladoDespesas = 0.0;
        Double valorSaldo = 0.0;

        String symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

        valorAcumuladoReceitas = this.repositorioReceita.getValorTotalRecebido(this.anoMes, this.somenteConfirmadas);
        valorAcumuladoDespesas = this.repositorioDespesa.getValorTotalDespesas(this.anoMes, this.somenteConfirmadas);
        valorSaldo = valorAcumuladoReceitas - valorAcumuladoDespesas;

        this.txtDespesaTotal.setText(symbol + " " + NumberUtis.getFormartCurrency(valorAcumuladoDespesas));
        this.txtReceitaTotal.setText(symbol + " " + NumberUtis.getFormartCurrency(valorAcumuladoReceitas));
        this.txtSaldo.setText(symbol + " " + NumberUtis.getFormartCurrency(valorSaldo));

        this.txtDespesaTotal.setTextColor(ColorHelper.getColor(this.getContext(), R.color.corPendencia));
        this.txtReceitaTotal.setTextColor(ColorHelper.getColor(this.getContext(), R.color.corResolvido));

        if (valorSaldo < 0) {
            this.txtSaldo.setTextColor(ColorHelper.getColor(this.getContext(), R.color.corPendencia));
        } else {
            this.txtSaldo.setTextColor(ColorHelper.getColor(this.getContext(), R.color.corResolvido));
        }

        this.listDataElements.getDataElementsChart().add(new DataElementChart(valorAcumuladoReceitas.floatValue(), ColorHelper.getColor(this.getContext(), R.color.corTelaReceitas)));
        this.listDataElements.getDataElementsChart().add(new DataElementChart(valorAcumuladoDespesas.floatValue(), ColorHelper.getColor(this.getContext(), R.color.corTelaDespesas)));

        this.columnChart.setListDataElements(this.listDataElements);
        this.columnChart.setHasAxes(false);
        this.columnChart.setHasLabelForSelected(false);
        this.columnChart.setHasLabels(false);


        this.columnChart.generateData();


    }


    protected void getParametrosRecebidos() {
        Bundle argument = getArguments();

        if ((argument != null) && (argument.containsKey(PARAM_ANO_MES))) {
            this.anoMes = argument.getInt(PARAM_ANO_MES);
        }

        if ((argument != null) && (argument.containsKey(PARAM_SOMENTE_CONFIRMADAS))) {
            this.somenteConfirmadas = argument.getBoolean(PARAM_SOMENTE_CONFIRMADAS);
        }

    }


}
