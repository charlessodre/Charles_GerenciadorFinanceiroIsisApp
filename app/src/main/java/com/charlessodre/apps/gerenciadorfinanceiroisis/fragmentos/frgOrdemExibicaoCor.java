package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.AdapterColorsCicle;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class frgOrdemExibicaoCor extends Fragment {

    public static final String NOME_FRAGMENTO = "FRAG_ORDEM_EXIBICAO_COR";
    public static final String PARAM_ORDEM_EXIBICAO = "ORDEM_EXIBICAO";
    public static final String PARAM_COR = "PARAM_COR";

    private Spinner spnCor;
    private EditText edtOrdemExibicao;
    private ArrayList<Integer> colors;


    public frgOrdemExibicaoCor() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_ordem_exibicao, container, false);

        this.inicializaObjetos(rootView);
        this.carregarSpinnerCor();
        this.preencherCampos();

        return rootView;
    }


    private void carregarSpinnerCor() {

        AdapterColorsCicle adapter = new AdapterColorsCicle(this.getContext(), R.layout.item_circulo );
        adapter.addAll(this.colors);
        spnCor.setAdapter(adapter);



    }

    public void inicializaObjetos(View view) {
        this.edtOrdemExibicao = (EditText) view.findViewById(R.id.edtOrdemExibicao);
        this.spnCor = (Spinner) view.findViewById(R.id.spnCor);

        this.colors = ColorHelper.getDefaultListColorByID();
    }

    protected void preencherCampos() {
        Bundle argument = getArguments();

        if ((argument != null) && (argument.containsKey(PARAM_ORDEM_EXIBICAO))) {
            this.edtOrdemExibicao.setText(String.valueOf( argument.getInt(PARAM_ORDEM_EXIBICAO)));
        }

        if ((argument != null) && (argument.containsKey(PARAM_COR))) {

            this.spnCor.setSelection(this.colors.indexOf(argument.getInt(PARAM_COR)));
        }
    }
}
