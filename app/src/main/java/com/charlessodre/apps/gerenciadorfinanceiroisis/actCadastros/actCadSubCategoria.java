package com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmaExclusaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActivityHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.MessageBoxHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioSubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.StringUtils;

public class actCadSubCategoria extends actBaseCadastros implements frgConfirmaExclusaoDialog.onDialogClick, frgConfirmacaoDialog.onDialogClick {

    //Atributos
    private EditText edtNomeSubCategoria;
    private SubCategoriaDespesa subCategoriaDespesa;
    private RepositorioSubCategoriaDespesa repositoriosubCategoriaDespesa;

    //Constantes
    public static final String PARAM_SUB_CATEGORIA = "SUB_CATEGORIA";

    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_cad_sub_categoria);

        ActivityHelper.showPopUp(this);

        inicializaObjetos();
        preencheDados();

        super.adicionaFragOrdemExibicaoCor(this.subCategoriaDespesa.getOrdemExibicao(), this.subCategoriaDespesa.getNoCor(), R.id.frag_container_cad_sub_categoria);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);

        if (this.subCategoriaDespesa.getId() == 0) {
            //Opção Excluir
            menu.getItem(0).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        } else if (id == R.id.menu_salvar) {

            if (this.validaCamposTela()) {
                this.salva();

                ToastHelper.showToastShort(this, this.getString(R.string.msg_salvar_sucesso));
                this.finish();
            }
        } else if (id == R.id.menu_excluir) {
            this.verificaDependentesExclusao();

        }

        return true;
    }

    @Override
    public void onDialogClick(frgConfirmaExclusaoDialog dialog, int opcaoConfirmacao, String textoDigitado) {

        if (opcaoConfirmacao == Constantes.OpcoesConfirmacao.NAO)
            dialog.dismiss();
        else {
            if (!textoDigitado.equals(this.getString(R.string.lblSim)))
                ToastHelper.showToastLong(this, this.getString(R.string.msg_excluir_digite_text));
            else {
                this.excluiComDependentes();

            }

        }
    }

    @Override
    public void onDialogClick(frgConfirmacaoDialog dialog) {
        this.exclui();
    }

    //Métodos
    @Override
    protected void inicializaObjetos() {

        this.edtNomeSubCategoria = (EditText) findViewById(R.id.edtNomeSubCategoria);

        ActionBarHelper.menuCancel(getSupportActionBar(), this.getString(R.string.lblSubCategoria));

        int cor = ColorHelper.getColor(this, R.color.corTelaDespesas);

        ActionBarHelper.setBackgroundColor(getSupportActionBar(), cor);
        ActionBarHelper.setStatusBarColor(this.getWindow(), cor);

    }

    private void salva() {

        this.subCategoriaDespesa.setNome(this.edtNomeSubCategoria.getText().toString());
        this.subCategoriaDespesa.setAtivo(true);
        this.subCategoriaDespesa.setDataInclusao(DateUtils.getCurrentDatetime());

        this.subCategoriaDespesa.setOrdemExibicao(super.getNumOrdemExibicao());
        this.subCategoriaDespesa.setNoCor(super.getNumCor());

        //this.subCategoriaDespesa.setIdCategoriaDespesa();

        this.repositoriosubCategoriaDespesa = new RepositorioSubCategoriaDespesa(this);

        if (this.subCategoriaDespesa.getId() == 0)
            repositoriosubCategoriaDespesa.insere(this.subCategoriaDespesa);
        else
            repositoriosubCategoriaDespesa.altera(this.subCategoriaDespesa);
    }

    private void exclui() {
        try {
            this.repositoriosubCategoriaDespesa = new RepositorioSubCategoriaDespesa(this);
            this.repositoriosubCategoriaDespesa.exclui(this.subCategoriaDespesa);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));

            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private boolean validaCamposTela() {
        boolean retorno = true;

        if (StringUtils.isNullOrEmpty(this.edtNomeSubCategoria.getText().toString())) {
            this.edtNomeSubCategoria.setError(this.getString(R.string.msg_preenchimento_obrigatorio));

            retorno = false;
        }

        return retorno;

    }

    private void preencheDados() {

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actCadSubCategoria.PARAM_SUB_CATEGORIA))) {
            this.subCategoriaDespesa = (SubCategoriaDespesa) bundle.getSerializable(actCadSubCategoria.PARAM_SUB_CATEGORIA);

            if (this.subCategoriaDespesa.getId() != 0) {
                this.edtNomeSubCategoria.setText(this.subCategoriaDespesa.getNome());
            }

        }
        //else {this.subCategoriaDespesa = new SubCategoriaDespesa();   }
    }

    private void verificaDependentesExclusao() {

        try {
            RepositorioDespesa repositorioDespesa = new RepositorioDespesa(this);

            int qtdeDespesas = 0;

            qtdeDespesas = repositorioDespesa.getQtdDespesaSubCategoria(this.subCategoriaDespesa.getId());

            boolean mostraMsgDialog = true;

            if (qtdeDespesas >= 1) {
                super.exibeExclusaoDialog();
            } else {
                super.exibePopUpConfirmacaoDialog(this.getString(R.string.msg_excluir));
            }
        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }

    private void excluiComDependentes() {
        try {

            RepositorioSubCategoriaDespesa repositorioSubCategoriaDespesa = new RepositorioSubCategoriaDespesa(this);

            repositorioSubCategoriaDespesa.excluiComDependentes(this.subCategoriaDespesa);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));

            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

}
