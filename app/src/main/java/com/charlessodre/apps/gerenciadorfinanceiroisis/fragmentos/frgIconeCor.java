package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ImageHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.AdapterColorsCicle;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.AdapterImagem;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class frgIconeCor extends Fragment {

    public static final String NOME_FRAGMENTO = "FRAG_ICONE_COR";
    public static final String PARAM_ICONE = "ICONE";
    public static final String PARAM_COR = "PARAM_COR";

    private Spinner spnCor;
    private Spinner spnIcone;
    private ArrayList<Integer> colors;


    public frgIconeCor() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_icone_cor, container, false);

        this.inicializaObjetos(rootView);
        this.carregarSpinnerCor();
        this.carregaSpinnerIcones();;
        this.preencherCampos();

        return rootView;
    }


    private void carregarSpinnerCor() {

        AdapterColorsCicle adapter = new AdapterColorsCicle(this.getContext(), R.layout.item_circulo );
        adapter.addAll(this.colors);
        spnCor.setAdapter(adapter);

    }

    private void carregaSpinnerIcones() {

        AdapterImagem adpIcones = new AdapterImagem(this.getContext(), R.layout.item_image_view);
         adpIcones.addAll(ImageHelper.getImagensCategorias());
        this.spnIcone.setAdapter(adpIcones);

    }


    public void inicializaObjetos(View view) {
        this.spnIcone = (Spinner) view.findViewById(R.id.spnIcone);
        this.spnCor = (Spinner) view.findViewById(R.id.spnCor);

        this.colors = ColorHelper.getDefaultListColorByID();
    }

    protected void preencherCampos() {
        Bundle argument = getArguments();

        if ((argument != null) && (argument.containsKey(PARAM_ICONE))) {
            this.spnIcone.setSelection(this.colors.indexOf(argument.getInt(PARAM_ICONE)));
        }

        if ((argument != null) && (argument.containsKey(PARAM_COR))) {

            this.spnCor.setSelection(this.colors.indexOf(argument.getInt(PARAM_COR)));
        }
    }
}
