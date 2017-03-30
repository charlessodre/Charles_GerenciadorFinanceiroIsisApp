package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;

import java.util.ArrayList;


public class frgTipoTransacaoDialog extends DialogFragment {

    public static final int OPCAO_REGRA_IMPORTACAO = 3;

    public interface onDialogClick {
        public void onDialogClick(frgTipoTransacaoDialog dialog, int opcaoSelecionada);
    }

    private LayoutInflater inflater;

    private int tipoTransacao;

    // Use this instance of the interface to deliver action events
    private onDialogClick mListener;

    //Constantes
    public static final String NOME_FRAGMENTO = "FRAG_TRANSACAO_DIALOG";
    public static final String PARAM_TIPO_TRANSACAO = "PARAM_TIPO_TRANSACAO";

    // Override the Fragment.onAttach() method to instantiate the frgTipoTransacaoDialog
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host Context implements the callback interface
        try {
            // Instantiate the frgTipoTransacaoDialog so we can send events to the host
            mListener = (onDialogClick) context;
        } catch (ClassCastException e) {
            // The Context doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " deve implementar -> frgTipoTransacaoDialog.onDialogClick");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.inflater = getActivity().getLayoutInflater();

        this.inicializaObjetos();

        ArrayList<String> opcoes = RegraImportacaoSMS.getTipoTransacao();

        opcoes.add(OPCAO_REGRA_IMPORTACAO,getString(R.string.title_regra_imp_sms));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.title_importar_sms);

        builder.setItems(opcoes.toArray(new CharSequence[opcoes.size()]), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogClick(frgTipoTransacaoDialog.this, which);
            }
        });

        return builder.create();
    }

    private void inicializaObjetos() {
        Bundle argument = getArguments();

        if ((argument != null) && (argument.containsKey(frgTipoTransacaoDialog.PARAM_TIPO_TRANSACAO))) {

            this.tipoTransacao = argument.getInt(frgTipoTransacaoDialog.PARAM_TIPO_TRANSACAO);
        }

    }
}
