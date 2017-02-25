package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;


public class frgBarraNavegacaoTop extends Fragment implements View.OnClickListener {

    private ImageButton btnEsquerda;
    private ImageButton btnDireita;
    private View rootView;
    public static final String NOME_FRAGMENTO = "FRAG_BARRA_NAVEG_TOP";
    public static final String PARAM_BACKGROUD_COLOR = "PARAM_BACKGROUD_COLOR";


    public frgBarraNavegacaoTop() {
        // Required empty public constructor
    }

  /*  public frgBarraNavegacaoTop(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.frag_barra_navegacao_top, container, false);

        this.inicializaComponentes();

        return this.rootView;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnEsquerda:
                ToastHelper.showToastShort(v.getContext(), "Vc clicou Esqueda");
                break;
            case R.id.btnDireita:
                ToastHelper.showToastShort(v.getContext(), "Vc clicou Direita");
                break;
        }

    }

    public void inicializaComponentes() {

        this.btnDireita = (ImageButton) this.rootView.findViewById(R.id.btnDireita);
        this.btnEsquerda = (ImageButton) this.rootView.findViewById(R.id.btnEsquerda);

        this.btnDireita.setOnClickListener(this);
        this.btnEsquerda.setOnClickListener(this);

        this.setBackgroundColor();
    }

    public void setBackgroundColor() {

        Bundle argument = getArguments();

        if ((argument != null) && (argument.containsKey(frgBarraNavegacaoTop.PARAM_BACKGROUD_COLOR))) {
            int backgroundColor =  argument.getInt(frgBarraNavegacaoTop.PARAM_BACKGROUD_COLOR);

            LinearLayout linearLayout = (LinearLayout) this.rootView.findViewById(R.id.lnlfragBarraNavegacaoTop);
            linearLayout.setBackgroundColor(ContextCompat.getColor(this.getContext(), backgroundColor));
        }


    }
}
