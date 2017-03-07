package com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmaExclusaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.AdapterImagem;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ImageHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterSubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.MessageBoxHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioSubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.StringUtils;

import java.util.ArrayList;

public class actCadCategoriaDespesa extends actBaseCadastros implements AdapterView.OnItemClickListener, frgConfirmaExclusaoDialog.onDialogClick, frgConfirmacaoDialog.onDialogClick {

    //Atributos
    private EditText edtNomeDespesa;
    private CategoriaDespesa categoriaDespesa;
    private ListView lstSubCategorias;
    private AdapterSubCategoriaDespesa adpSubCategoriaDespesa;
    private LinearLayout lnlSubcategorias;

    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_categoria_despesa);

        this.inicializaObjetos();
        this.preencheDados();
        this.carregaSpinnerSubCategoria();
        super.adicionaFragOrdemExibicaoCor(this.categoriaDespesa.getOrdemExibicao(), this.categoriaDespesa.getNoCor(), R.id.frag_container_cad_categoria_despesa);
        super.adicionaFragIconeCor(this.categoriaDespesa.getNoIcone(), this.categoriaDespesa.getNoCorIcone(), R.id.frag_container_cad_categoria_despesa_icone_cor);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        SubCategoriaDespesa subCategoriaDespesa = this.adpSubCategoriaDespesa.getItem(position);

        if (subCategoriaDespesa == null) {
            subCategoriaDespesa = new SubCategoriaDespesa();
            subCategoriaDespesa.setIdCategoriaDespesa(this.categoriaDespesa.getId());
        }

        Intent it = new Intent(this, actCadSubCategoria.class);
        it.putExtra(actCadSubCategoria.PARAM_SUB_CATEGORIA, subCategoriaDespesa);
        startActivityForResult(it, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        this.carregaSpinnerSubCategoria();
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
    protected  void inicializaObjetos() {

        this.edtNomeDespesa = (EditText) findViewById(R.id.edtNomeCategoriaDespesa);
        this.lstSubCategorias = (ListView) findViewById(R.id.lstSubCategorias);
        this.lnlSubcategorias = (LinearLayout) findViewById(R.id.lnlSubcategorias);

        ActionBarHelper.menuCancel(getSupportActionBar(), this.getString(R.string.lblCategoriaDespesa));

        int corTela = ColorHelper.getColor(this, R.color.corTelaDespesas);

        ActionBarHelper.setBackgroundColor(getSupportActionBar(), corTela);

        ActionBarHelper.setStatusBarColor(this.getWindow(), corTela);

        this.lstSubCategorias.setOnItemClickListener(this);

    }


    private void carregaSpinnerSubCategoria() {

        this.adpSubCategoriaDespesa = new AdapterSubCategoriaDespesa(this, R.layout.item_sub_categoria);

        if (this.categoriaDespesa != null) {

            RepositorioSubCategoriaDespesa repositorioSubCategoriaDespesa = new RepositorioSubCategoriaDespesa(this);

            this.adpSubCategoriaDespesa.addAll(repositorioSubCategoriaDespesa.buscaPorIdCategoriaDespesa(this.categoriaDespesa.getId()));
        }
        this.adpSubCategoriaDespesa.insert(null, this.adpSubCategoriaDespesa.getCount());

        this.lstSubCategorias.setAdapter(this.adpSubCategoriaDespesa);

    }

    private boolean validaCamposTela() {
        boolean retorno = true;

        if (StringUtils.isNullOrEmpty(this.edtNomeDespesa.getText().toString())) {
            this.edtNomeDespesa.setError(this.getString(R.string.msg_preenchimento_obrigatorio));

            retorno = false;
        }

        return retorno;

    }

    private void preencheDados() {

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actCategoriaDespesa.PARAM_CATEGORIA_DEPESA))) {

            this.categoriaDespesa = (CategoriaDespesa) bundle.getSerializable(actCategoriaDespesa.PARAM_CATEGORIA_DEPESA);

            this.edtNomeDespesa.setText(this.categoriaDespesa.getNome());

        } else {
            this.categoriaDespesa = new CategoriaDespesa();
            this.lnlSubcategorias.setVisibility(View.INVISIBLE);
        }
    }

    private void exclui() {

        try {

            RepositorioCategoriaDespesa repositorioCategoriaDespesa = new RepositorioCategoriaDespesa(this);

            repositorioCategoriaDespesa.exclui(this.categoriaDespesa);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));

            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private void excluiComDependentes() {
        try {

            RepositorioCategoriaDespesa repositorioCategoriaDespesa = new RepositorioCategoriaDespesa(this);

            repositorioCategoriaDespesa.excluiComDependentes(this.categoriaDespesa);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));

            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private void salva() {

        this.categoriaDespesa.setNome(this.edtNomeDespesa.getText().toString());
        this.categoriaDespesa.setAtivo(true);
        this.categoriaDespesa.setDataInclusao(DateUtils.getCurrentDatetime());

        this.categoriaDespesa.setNoIcone(super.getNumIcone());

        this.categoriaDespesa.setNoCorIcone(super.getNumCorIcone());

        this.categoriaDespesa.setOrdemExibicao(super.getNumOrdemExibicao());

        this.categoriaDespesa.setNoCor(super.getNumCor());

        RepositorioCategoriaDespesa repositorioCategoriaDespesa = new RepositorioCategoriaDespesa(this);

        if (this.categoriaDespesa.getId() == 0)
            repositorioCategoriaDespesa.insere(this.categoriaDespesa);
        else
            repositorioCategoriaDespesa.altera(this.categoriaDespesa);

    }

    private void verificaDependentesExclusao() {

        try {
            RepositorioDespesa repositorioDespesa = new RepositorioDespesa(this);

            int qtdeDespesas = 0;

            qtdeDespesas = repositorioDespesa.getQtdDespesaCategoria(this.categoriaDespesa.getId());

            boolean mostraMsgDialog = false;

            if (qtdeDespesas >= 1) {
                mostraMsgDialog = true;

            } else {

                int qtdeCategoriaDespesa = 0;

                RepositorioSubCategoriaDespesa repositorioSubCategoriaDespesa = new RepositorioSubCategoriaDespesa(this);
                qtdeCategoriaDespesa = repositorioSubCategoriaDespesa.getQtdSubCategoria(this.categoriaDespesa.getId());

                if (qtdeCategoriaDespesa >= 1 ) {
                    mostraMsgDialog = true;
                }

            }

            if (mostraMsgDialog) {
                super.exibeExclusaoDialog();
            } else {
                super.exibePopUpConfirmacaoDialog(this.getString(R.string.msg_excluir));
            }
        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }
}
