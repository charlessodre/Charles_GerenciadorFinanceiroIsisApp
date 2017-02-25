package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;


public class frgConfirmacaoDialog extends DialogFragment {

    public frgConfirmacaoDialog() {

    }

    //Atributos
    private LayoutInflater inflater;
    private String titulo;
    private String mensagem;


    // Use this instance of the interface to deliver action events
    private onDialogClick mListener;

    //Constantes
    public static final String NOME_FRAGMENTO = "FRAG_ALERT_DIALOG";
    public static final String PARAM_TITULO = "PARAM_TITULO";
    public static final String PARAM_MSG = "PARAM_MSG";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.inflater = getActivity().getLayoutInflater();

        this.inicializaObjetos();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(this.titulo);
        builder.setMessage(this.mensagem);

        builder.setPositiveButton(R.string.lblSim, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogClick(frgConfirmacaoDialog.this);
            }
        });

        builder.setNegativeButton(R.string.lblNao, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the frgConfirmacaoDialog
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host Context implements the callback interface
        try {
            // Instantiate the frgConfirmacaoDialog so we can send events to the host
            mListener = (onDialogClick) context;
        } catch (ClassCastException e) {
            // The Context doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " deve implementar -> frgConfirmacaoDialog.onDialogClick");
        }
    }

    public interface onDialogClick {
        public void onDialogClick(frgConfirmacaoDialog dialog);
    }

    private void inicializaObjetos() {
        Bundle argument = getArguments();

        if ((argument != null) && (argument.containsKey(frgConfirmacaoDialog.PARAM_TITULO))) {
            this.titulo = argument.getString(frgConfirmacaoDialog.PARAM_TITULO);
        }

        if ((argument != null) && (argument.containsKey(frgConfirmacaoDialog.PARAM_MSG))) {
            this.mensagem = argument.getString(frgConfirmacaoDialog.PARAM_MSG);
        }

    }

}
