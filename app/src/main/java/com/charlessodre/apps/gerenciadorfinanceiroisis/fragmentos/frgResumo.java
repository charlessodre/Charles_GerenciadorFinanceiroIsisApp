package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DecimalHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;

import java.text.NumberFormat;
import java.util.Locale;

import static com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actReceita.PARAM_RECEITA_ANO_MES;


/**
 * A simple {@link Fragment} subclass.
 */
public class frgResumo extends Fragment {

    //Objetos Tela
    private TextView txtValorContaAcumulado;
    private TextView txtValorReceitaAcumulado;
    private TextView txtValorDespesasAcumulado;

    private LinearLayout lnlResumoConta;
    private LinearLayout lnlResumoReceitas;
    private LinearLayout lnlResumoDespesas;


    //Atributos
    private View rootView;
    private int anoMes = DateUtils.getCurrentYearAndMonth();
    private RepositorioConta repositorioConta;
    private RepositorioReceita repositorioReceita;
    private RepositorioDespesa repositorioDespesa;


    //Constantes
    public static final String NOME_FRAGMENTO = "FRAG_RESUMO";
    public static final int PARAM_ANO_MES = 0;

    //Construtores
    public frgResumo() {
        // Required empty public constructor
    }



    //Eventos
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.frg_resumo, container, false);

        this.inicializaObjetos();

        return this.rootView;
    }

    private void inicializaObjetos() {

        this.txtValorContaAcumulado = (TextView) this.rootView.findViewById(R.id.txtValorContaAcumulado);
        this.txtValorReceitaAcumulado = (TextView) this.rootView.findViewById(R.id.txtValorReceitaAcumulado);
        this.txtValorDespesasAcumulado = (TextView) this.rootView.findViewById(R.id.txtValorDespesasAcumulado);

        this.lnlResumoConta = (LinearLayout) this.rootView.findViewById(R.id.lnlResumoConta);
        this.lnlResumoReceitas = (LinearLayout) this.rootView.findViewById(R.id.lnlResumoReceitas);
        this.lnlResumoDespesas = (LinearLayout) this.rootView.findViewById(R.id.lnlResumoDespesas);

        this.lnlResumoConta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent it = new Intent(getActivity(), actConta.class);
                it.putExtra(actConta.PARAM_CONTA_ANO_MES, anoMes);
                startActivityForResult(it, 0);

            }
        });

        this.lnlResumoReceitas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent it = new Intent(getContext(), actReceita.class);
                it.putExtra(actReceita.PARAM_RECEITA_ANO_MES, anoMes);
                startActivityForResult(it, 0);


            }
        });

        this.lnlResumoDespesas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                 Intent it = new Intent(getContext(), actDespesa.class);
                it.putExtra(actDespesa.PARAM_DESPESA_ANO_MES, anoMes);
                 startActivityForResult(it, 0);


            }
        });

        this.repositorioConta = new RepositorioConta(this.getContext());
        this.repositorioReceita = new RepositorioReceita(this.getContext());
        this.repositorioDespesa = new RepositorioDespesa(this.getContext());

        this.atualizaResumo(this.anoMes);

    }

    public void atualizaResumo(int anoMes) {
       this.anoMes = anoMes;

        if(this.getContext() != null) {

            double valorAcumuladoContas = this.repositorioConta.getValorTotal();
            double valorAcumuladoReceitas = this.repositorioReceita.getValorTotalRecebido(this.anoMes,true);
            double valorAcumuladoDespesas = this.repositorioDespesa.getValorTotalDespesas(this.anoMes,true);

            if (valorAcumuladoContas < 0)
                this.txtValorContaAcumulado.setTextColor(ColorHelper.getColor(this.getContext(), R.color.corPendencia));
            else if (valorAcumuladoContas > 0)
                this.txtValorContaAcumulado.setTextColor(ColorHelper.getColor(this.getContext(), R.color.corResolvido));

            String symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

            this.txtValorContaAcumulado.setText(symbol+ " " + DecimalHelper.getFormartCurrency(valorAcumuladoContas));
            this.txtValorReceitaAcumulado.setText(symbol + " " + DecimalHelper.getFormartCurrency(valorAcumuladoReceitas));
            this.txtValorDespesasAcumulado.setText(symbol + " " + DecimalHelper.getFormartCurrency(valorAcumuladoDespesas));

        }
    }

}
