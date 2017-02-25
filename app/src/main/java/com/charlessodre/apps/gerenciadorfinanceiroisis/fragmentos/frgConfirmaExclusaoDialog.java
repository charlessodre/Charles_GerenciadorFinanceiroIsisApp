package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;



public class frgConfirmaExclusaoDialog extends DialogFragment {

    private LayoutInflater inflater;
    private int titulo;
    private TextView txtTextoConfirmaExclusao;
    private EditText edtConfirmaExclusao;
    private View rootView;
    private Button btnConfimExcNao;
    private Button btnConfimExcSIM;



    public interface onDialogClick {
        public void onDialogClick(frgConfirmaExclusaoDialog dialog, int opcaoConfirmacao, String textoDigitado );
    }

    // Use this instance of the interface to deliver action events
    private onDialogClick mListener;

    //Constantes
    public static final String NOME_FRAGMENTO = "FRAG_CONFIM_EXCLUSAO_DIALOG";

    // Override the Fragment.onAttach() method to instantiate the frgLancamentosDialog
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host Context implements the callback interface
        try {
            // Instantiate the frgLancamentosDialog so we can send events to the host
            mListener = (onDialogClick) context;
        } catch (ClassCastException e) {
            // The Context doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " deve implementar -> frg_confirma_exclusao_dialog.onDialogClick");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.frg_confirma_exclusao_dialog, container, false);
        this.inicializaObjetos();
        return this.rootView;
    }

    private void inicializaObjetos() {

        this.titulo = R.string.title_confirma_exclusao;

        this.txtTextoConfirmaExclusao = (TextView) this.rootView.findViewById(R.id.txtTextoConfirmaExclusao);
        this.edtConfirmaExclusao = (EditText) this.rootView.findViewById(R.id.edtConfirmaExclusao);

        this.btnConfimExcNao = (Button) this.rootView.findViewById(R.id.btnConfimExcNao);
        this.btnConfimExcSIM= (Button) this.rootView.findViewById(R.id.btnConfimExcSIM);

        this.btnConfimExcNao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onDialogClick(frgConfirmaExclusaoDialog.this, Constantes.OpcoesConfirmacao.NAO, edtConfirmaExclusao.getText().toString());

            }
        });

        this.btnConfimExcSIM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onDialogClick(frgConfirmaExclusaoDialog.this,Constantes.OpcoesConfirmacao.SIM,edtConfirmaExclusao.getText().toString());
            }
        });

    }


}
