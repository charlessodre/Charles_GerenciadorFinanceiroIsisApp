package com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;

import java.util.ArrayList;


public class frgLancamentosDialog extends DialogFragment {

    public interface onDialogClick {
        public void onDialogClick(frgLancamentosDialog dialog, int opcaoSelecionada, int tipoMensagem);
    }

    private LayoutInflater inflater;

    private int tipoLancamento;
    private int titulo;
    private int tipoMensagem;

    // Use this instance of the interface to deliver action events
    private onDialogClick mListener;

    //Constantes
    public static final String NOME_FRAGMENTO = "FRAG_LANCAMENTOS_DIALOG";
    public static final String PARAM_TIPO_LANCAMENTO = "PARAM_TIPO_TRANSACAO";
    public static final String PARAM_TIPO_MSG = "PARAM_TIPO_MSG";

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
                    + " deve implementar -> frgLancamentosDialog.onDialogClick");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.inflater = getActivity().getLayoutInflater();

        this.inicializaObjetos();

        ArrayList<String> opcoes = this.tipoMensagem == Constantes.TipoMensagem.EXCLUSAO ? this.getOpcoesExlusao() : this.getOpcoesAlteracao();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(this.titulo);

        builder.setItems(opcoes.toArray(new CharSequence[opcoes.size()]), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
               mListener.onDialogClick(frgLancamentosDialog.this,which, tipoMensagem);
            }
        });

        return builder.create();
    }

    private void inicializaObjetos() {
        Bundle argument = getArguments();

        if ((argument != null) && (argument.containsKey(frgLancamentosDialog.PARAM_TIPO_LANCAMENTO))) {
            this.tipoLancamento = argument.getInt(frgLancamentosDialog.PARAM_TIPO_LANCAMENTO);

        }
        if ((argument != null) && (argument.containsKey(frgLancamentosDialog.PARAM_TIPO_MSG))) {

            this.tipoMensagem = argument.getInt(frgLancamentosDialog.PARAM_TIPO_MSG);

            if (this.tipoMensagem == Constantes.TipoMensagem.EXCLUSAO)
                this.titulo = R.string.title_exclusao_lancamentos;
            else
                this.titulo = R.string.title_alteracao_lancamentos;
        }

    }

    public ArrayList<String> getOpcoesExlusao() {
        ArrayList<String> opcoesExclusao = new ArrayList<String>();

        opcoesExclusao.add(getString(R.string.msg_excluir_atual));
        opcoesExclusao.add(getString(R.string.msg_excluir_atual_proximas));
        opcoesExclusao.add(getString(R.string.msg_excluir_todas));
        opcoesExclusao.add(getString(R.string.lblCancelar));

        return opcoesExclusao;
    }

    public ArrayList<String> getOpcoesAlteracao() {
        ArrayList<String> opcoesAlteracao = new ArrayList<String>();

        opcoesAlteracao.add(getString(R.string.msg_alterar_atual));
        opcoesAlteracao.add(getString(R.string.msg_alterar_atual_proximas));
        opcoesAlteracao.add(getString(R.string.msg_alterar_todas));
        opcoesAlteracao.add(getString(R.string.lblCancelar));

        return opcoesAlteracao;
    }
}
