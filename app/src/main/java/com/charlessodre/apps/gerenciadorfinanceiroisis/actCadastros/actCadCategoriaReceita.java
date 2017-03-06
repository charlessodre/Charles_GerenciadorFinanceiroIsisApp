package com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actCategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmaExclusaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ImageHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.AdapterImagem;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.MessageBoxHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.StringUtils;

import java.util.ArrayList;

public class actCadCategoriaReceita extends actBaseCadastros implements frgConfirmaExclusaoDialog.onDialogClick, frgConfirmacaoDialog.onDialogClick {

    //Atributos
    private EditText edtNomeReceita;
    private CategoriaReceita categoriaReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_categoria_receita);

        this.inicializaObjetos();
        this.preencheDados();
        super.adicionaFragOrdemExibicaoCor(this.categoriaReceita.getOrdemExibicao(), this.categoriaReceita.getNoCor(), R.id.frag_container_cad_categoria_receita);
        super.adicionaFragIconeCor(this.categoriaReceita.getNoCorIcone(), this.categoriaReceita.getNoCor(), R.id.frag_container_cad_categoria_receita_icone_cor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);

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
                this.excluiDependencias();

            }

        }
    }

    @Override
    public void onDialogClick(frgConfirmacaoDialog dialog) {
        this.exclui();
    }

    //Métodos
    @Override
    protected  void inicializaObjetos() {

        this.edtNomeReceita = (EditText) findViewById(R.id.edtNomeCategoriaReceita);

        ActionBarHelper.menuCancel(getSupportActionBar(), this.getString(R.string.lblCategoriaReceita));

        int corTela = ColorHelper.getColor(this, R.color.corTelaReceitas);

        ActionBarHelper.setBackgroundColor(getSupportActionBar(), corTela);

        ActionBarHelper.setStatusBarColor(this.getWindow(), corTela);


    }

    private boolean validaCamposTela() {
        boolean retorno = true;

        if (StringUtils.isNullOrEmpty(this.edtNomeReceita.getText().toString())) {
            this.edtNomeReceita.setError(this.getString(R.string.msg_preenchimento_obrigatorio));

            retorno = false;
        }

        return retorno;

    }

    private void preencheDados() {

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actCategoriaReceita.PARAM_CATEGORIA_RECEITA))) {

            this.categoriaReceita = (CategoriaReceita) bundle.getSerializable(actCategoriaReceita.PARAM_CATEGORIA_RECEITA);

            this.edtNomeReceita.setText(this.categoriaReceita.getNome());

        } else {
            this.categoriaReceita = new CategoriaReceita();
        }
    }

    private void exclui() {

        try {
            RepositorioCategoriaReceita repositorioCategoriaReceita = new RepositorioCategoriaReceita(this);

            repositorioCategoriaReceita.exclui(this.categoriaReceita);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));

            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private void excluiDependencias() {

        try {
            RepositorioCategoriaReceita repositorioCategoriaReceita = new RepositorioCategoriaReceita(this);

            repositorioCategoriaReceita.excluiComDependentes(this.categoriaReceita);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));

            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private void salva() {

        this.categoriaReceita.setNome(this.edtNomeReceita.getText().toString());
        this.categoriaReceita.setAtivo(true);
        this.categoriaReceita.setDataInclusao(DateUtils.getCurrentDatetime());

        this.categoriaReceita.setNoIcone(super.getNumIcone());

        this.categoriaReceita.setNoCorIcone(super.getNumCorIcone());

        this.categoriaReceita.setOrdemExibicao(super.getNumOrdemExibicao());

        this.categoriaReceita.setNoCor(super.getNumCor());

        RepositorioCategoriaReceita repositorioCategoriaReceita = new RepositorioCategoriaReceita(this);

        if (this.categoriaReceita.getId() == 0)
            repositorioCategoriaReceita.insere(this.categoriaReceita);
        else
            repositorioCategoriaReceita.altera(this.categoriaReceita);

    }

    private void verificaDependentesExclusao() {

        try {
            RepositorioReceita repositorioReceita = new RepositorioReceita(this);

            int qtdeReceitas = 0;

            qtdeReceitas = repositorioReceita.getQtdReceitaCategoria(this.categoriaReceita.getId());

            if (qtdeReceitas >= 1) {

                super.exibeExclusaoDialog();
            } else {
                super.exibePopUpConfirmacaoDialog(this.getString(R.string.msg_excluir));
            }
        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }


}
