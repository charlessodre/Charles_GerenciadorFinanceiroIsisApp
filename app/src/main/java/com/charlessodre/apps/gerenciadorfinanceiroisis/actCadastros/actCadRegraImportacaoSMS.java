package com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterCategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterSubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;

import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioRegraImpSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioSubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ArrayAdapterHelper;

import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import com.charlessodre.apps.gerenciadorfinanceiroisis.util.MessageBoxHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.StringUtils;

import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;


public class actCadRegraImportacaoSMS extends actBaseCadastros implements CompoundButton.OnCheckedChangeListener,  frgConfirmacaoDialog.onDialogClick, Spinner.OnItemSelectedListener {

    //Objetos Tela
    private Spinner spnCategoriaDespesa;
    private Spinner spnSubCategoriaDespesa;
    private Spinner spnContaOrigem;
    private Spinner spnContaDestino;
    private Spinner spnTipoTransacao;
    private Spinner spnCategoriaReceita;

    private EditText edtNomeRegraImpSMS;
    private EditText edtNumeroSMS;
    private EditText edtTexto1NoSMS;
    private EditText edtTexto2NoSMS;
    private EditText edtTextoDescReceita;
    private EditText edtTextoDescDespesa;


    private CheckBox cbxStatus;


    private LinearLayout lnlDespesa;
    private LinearLayout lnlReceita;
    private LinearLayout lnlTransferencia;

    //Atributos
    private RegraImportacaoSMS regraImportacaoSMS;
    private RepositorioRegraImpSMS repositorioRegraImpSMS;
    private AdapterConta adapterConta;
    private AdapterCategoriaDespesa adapterCategoriaDespesa;
    private AdapterSubCategoriaDespesa adapterSubCategoriaDespesa;
    private AdapterCategoriaReceita adapterCategoriaReceita;

    //Constantes
    public static final String PARAM_REGRA_IMP_SMS = "REGRA_IMP_SMS";
    public static final String PARAM_REGRA_IMP_SMS_ANO_MES = "ANO_MES";
    public static final String PARAM_IMP_SMS = "IMP_SMS";


    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_regra_importacao_sms);

        this.inicializaObjetos();
        this.carregaSpinnerConta();
        this.carregaSpinnerTipoTransacao();
        this.carregaSpinnerCategoriaReceita();
        this.carregaSpinnerCategoriaDespesa();

        this.getParametrosRecebidos();
        this.preencheDados();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);

        if (this.regraImportacaoSMS.getId() == 0) {
            menu.findItem(R.id.menu_excluir).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                this.finish();
                break;

            case R.id.menu_salvar:

                if (validaCamposTela()) {

                    this.salvaAtual();
                }

                break;

            case R.id.menu_excluir:

                super.exibePopUpConfirmacaoDialog(this.getString(R.string.msg_excluir));
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

        @Override
    public void onDialogClick(frgConfirmacaoDialog dialog) {
        this.excluiAtual();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (parent.getId() == R.id.spnCategoriaDespesa) {
            long idCategoria = ((CategoriaDespesa) parent.getSelectedItem()).getId();
            this.carregaSpinnerSubCategoriaDespesa(idCategoria);

            this.spnSubCategoriaDespesa.setSelection(this.adapterSubCategoriaDespesa.getIndexFromElement(this.regraImportacaoSMS.getSubCategoriaDespesa().getId()));

        } else if (parent.getId() == R.id.spnTipoTransacao) {
            if (position == Constantes.TipoTransacao.RECEITA) {
                this.lnlReceita.setVisibility(View.VISIBLE);
                this.lnlDespesa.setVisibility(View.GONE);
                this.lnlTransferencia.setVisibility(View.GONE);

            } else if (position == Constantes.TipoTransacao.DESPESA) {

                this.lnlReceita.setVisibility(View.GONE);
                this.lnlDespesa.setVisibility(View.VISIBLE);
                this.lnlTransferencia.setVisibility(View.GONE);

            } else {
                this.lnlReceita.setVisibility(View.GONE);
                this.lnlDespesa.setVisibility(View.GONE);
                this.lnlTransferencia.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Métodos
    @Override
    protected void inicializaObjetos() {

        this.edtNomeRegraImpSMS = (EditText) findViewById(R.id.edtNomeRegraImpSMS);
        this.edtNumeroSMS = (EditText) findViewById(R.id.edtNumeroSMS);
        this.edtTexto1NoSMS = (EditText) findViewById(R.id.edtTexto1NoSMS);
        this.edtTexto2NoSMS = (EditText) findViewById(R.id.edtTexto2NoSMS);
        this.edtTextoDescDespesa = (EditText) findViewById(R.id.edtTextoDescDespesa);
        this.edtTextoDescReceita = (EditText) findViewById(R.id.edtTextoDescReceita);
        this.spnContaOrigem = (Spinner) findViewById(R.id.spnContaOrigem);
        this.spnContaDestino = (Spinner) findViewById(R.id.spnContaDestino);
        this.spnCategoriaDespesa = (Spinner) findViewById(R.id.spnCategoriaDespesa);
        this.spnSubCategoriaDespesa = (Spinner) findViewById(R.id.spnSubCategoriaDespesa);
        this.spnCategoriaReceita = (Spinner) findViewById(R.id.spnCategoriaReceita);

        this.spnTipoTransacao = (Spinner) findViewById(R.id.spnTipoTransacao);
        this.cbxStatus = (CheckBox) findViewById(R.id.cbxStatus);

        this.lnlReceita = (LinearLayout) findViewById(R.id.lnlReceita);
        this.lnlTransferencia = (LinearLayout) findViewById(R.id.lnlTransferencia);
        this.lnlDespesa = (LinearLayout) findViewById(R.id.lnlDespesa);


        this.spnCategoriaDespesa.setOnItemSelectedListener(this);
        this.spnTipoTransacao.setOnItemSelectedListener(this);


        ActionBarHelper.menuCancel(getSupportActionBar(), this.getString(R.string.lblRegraImpSMS));

        int corTela = ColorHelper.getColor(this, R.color.corTelaRegraImportacaoSMS);

        ActionBarHelper.setBackgroundColor(getSupportActionBar(), corTela);

        ActionBarHelper.setStatusBarColor(this.getWindow(), corTela);

    }


    private void carregaSpinnerTipoTransacao() {
        ArrayAdapter arrayAdapter = ArrayAdapterHelper.fillSpinnerString(this, this.spnTipoTransacao);
        arrayAdapter.addAll(RegraImportacaoSMS.getTipoTransacao());
    }

    //Campos de Despesa
    private void carregaSpinnerCategoriaDespesa() {

        RepositorioCategoriaDespesa repositorioCategoriaDespesa = new RepositorioCategoriaDespesa(this);

        this.adapterCategoriaDespesa = new AdapterCategoriaDespesa(this, R.layout.item_categoria);

        this.adapterCategoriaDespesa.addAll(repositorioCategoriaDespesa.buscaTodos());

        this.spnCategoriaDespesa.setAdapter(this.adapterCategoriaDespesa);
    }

    private void carregaSpinnerSubCategoriaDespesa(Long idCategoriaDespesa) {

        RepositorioSubCategoriaDespesa repositorioSubCategoriaDespesa = new RepositorioSubCategoriaDespesa(this);

        this.adapterSubCategoriaDespesa = new AdapterSubCategoriaDespesa(this, R.layout.item_sub_categoria);

        this.adapterSubCategoriaDespesa.addAll(repositorioSubCategoriaDespesa.buscaPorIdCategoriaDespesa(idCategoriaDespesa));

        this.spnSubCategoriaDespesa.setAdapter(this.adapterSubCategoriaDespesa);
    }


    //Campos de Receita
    private void carregaSpinnerCategoriaReceita() {
        RepositorioCategoriaReceita repositorioCategoriaReceita = new RepositorioCategoriaReceita(this);

        this.adapterCategoriaReceita = new AdapterCategoriaReceita(this, R.layout.item_categoria);

        this.adapterCategoriaReceita.addAll(repositorioCategoriaReceita.buscaTodos());

        this.spnCategoriaReceita.setAdapter(this.adapterCategoriaReceita);
    }

    private void carregaSpinnerConta() {

        RepositorioConta repositorioConta = new RepositorioConta(this);

        this.adapterConta = new AdapterConta(this, R.layout.item_conta_simples);

        this.adapterConta.addAll(repositorioConta.buscaTodos());

        this.spnContaOrigem.setAdapter(this.adapterConta);

        this.spnContaDestino.setAdapter(this.adapterConta);

        if (this.adapterConta.getCount() > 1)
            this.spnContaDestino.setSelection(1);
    }

    private void getParametrosRecebidos() {

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actCadRegraImportacaoSMS.PARAM_REGRA_IMP_SMS))) {

            this.regraImportacaoSMS = (RegraImportacaoSMS) bundle.getSerializable(actCadRegraImportacaoSMS.PARAM_REGRA_IMP_SMS);

        }

        /*else if ((bundle != null) && (bundle.containsKey(actCadRegraImportacaoSMS.PARAM_IMP_SMS)))
        {
           SMS sms =  (SMS) bundle.getSerializable(actCadRegraImportacaoSMS.PARAM_IMP_SMS);
            this.regraImportacaoSMS = new RegraImportacaoSMS();

            this.regraImportacaoSMS.setNoTelefone(sms.getNumero());
            this.regraImportacaoSMS.setTextoPesquisa1(sms.getMensagem());
        }*/
        else {
            this.regraImportacaoSMS = new RegraImportacaoSMS();
        }
    }

    private void preencheDados() {
        //   Bundle bundle = getIntent().getExtras();

        //  if ((bundle != null) && (bundle.containsKey(actCadRegraImportacaoSMS.PARAM_REGRA_IMP_SMS))) {

        //this.regraImportacaoSMS = (RegraImportacaoSMS) bundle.getSerializable(actCadRegraImportacaoSMS.PARAM_REGRA_IMP_SMS);

        this.edtNomeRegraImpSMS.setText(this.regraImportacaoSMS.getNome());
        this.edtNumeroSMS.setText(this.regraImportacaoSMS.getNoTelefone());
        this.edtTexto1NoSMS.setText(this.regraImportacaoSMS.getTextoPesquisa1());
        this.edtTexto2NoSMS.setText(this.regraImportacaoSMS.getTextoPesquisa2());
        this.spnContaOrigem.setSelection(this.adapterConta.getIndexFromElement(this.regraImportacaoSMS.getContaOrigem().getId()));
        this.cbxStatus.setChecked(this.regraImportacaoSMS.isAtivo());

        int idTipoTransacao = this.regraImportacaoSMS.getIdTipoTransacao();

        this.spnTipoTransacao.setSelection(idTipoTransacao);

        //Receita
        if (idTipoTransacao == Constantes.TipoTransacao.RECEITA) {

            int index = this.adapterCategoriaReceita.getIndexFromElement(this.regraImportacaoSMS.getCategoriaReceita().getId());

            this.spnCategoriaReceita.setSelection(index);

            this.edtTextoDescReceita.setText(this.regraImportacaoSMS.getDescricaoReceitaDespesa());
            this.edtTextoDescDespesa.setText(this.regraImportacaoSMS.getDescricaoReceitaDespesa());

        } else if (idTipoTransacao == Constantes.TipoTransacao.DESPESA) //Despesa
        {
            int indexCategoria = this.adapterCategoriaDespesa.getIndexFromElement(this.regraImportacaoSMS.getCategoriaDespesa().getId());

            this.spnCategoriaDespesa.setSelection(indexCategoria);

            this.edtTextoDescDespesa.setText(this.regraImportacaoSMS.getDescricaoReceitaDespesa());
            this.edtTextoDescReceita.setText(this.regraImportacaoSMS.getDescricaoReceitaDespesa());

        } else //Transferência
        {
            int index = this.adapterConta.getIndexFromElement(this.regraImportacaoSMS.getContaDestino().getId());
            this.spnContaDestino.setSelection(index);
        }


        //} else {
        //   this.regraImportacaoSMS = new RegraImportacaoSMS();
        //}
    }

    private boolean validaCamposTela() {
        boolean retorno = true;

        if (StringUtils.isNullOrEmpty(this.edtNomeRegraImpSMS.getText().toString())) {
            this.edtNomeRegraImpSMS.setError(this.getString(R.string.msg_preenchimento_obrigatorio));

            retorno = false;
        }

        if (StringUtils.isNullOrEmpty(this.edtNumeroSMS.getText().toString())) {
            this.edtNumeroSMS.setError(this.getString(R.string.msg_preenchimento_obrigatorio));

            retorno = false;
        }

        if (StringUtils.isNullOrEmpty(this.edtTexto1NoSMS.getText().toString())) {
            this.edtTexto1NoSMS.setError(this.getString(R.string.msg_preenchimento_obrigatorio));

            retorno = false;
        }


        return retorno;

    }

    private void preencheDadosSalvar() {

        this.regraImportacaoSMS.setNome(this.edtNomeRegraImpSMS.getText().toString());
        this.regraImportacaoSMS.setNoTelefone(this.edtNumeroSMS.getText().toString());
        this.regraImportacaoSMS.setTextoPesquisa1(this.edtTexto1NoSMS.getText().toString());
        this.regraImportacaoSMS.setTextoPesquisa2(this.edtTexto2NoSMS.getText().toString());
        this.regraImportacaoSMS.setAtivo(this.cbxStatus.isChecked());

        Conta contaOrigem = this.adapterConta.getItem(this.spnContaOrigem.getSelectedItemPosition());
        this.regraImportacaoSMS.setContaOrigem(contaOrigem);

        int idTipoTransacao = this.spnTipoTransacao.getSelectedItemPosition();

        this.regraImportacaoSMS.setIdTipoTransacao(idTipoTransacao);

        //Receita
        if (idTipoTransacao == Constantes.TipoTransacao.RECEITA) {

            CategoriaReceita categoriaReceita = this.adapterCategoriaReceita.getItem(this.spnCategoriaReceita.getSelectedItemPosition());

            this.regraImportacaoSMS.setCategoriaReceita(categoriaReceita);

            this.regraImportacaoSMS.setDescricaoReceitaDespesa(this.edtTextoDescReceita.getText().toString());


        } else if (idTipoTransacao == Constantes.TipoTransacao.DESPESA) //Despesa
        {
            CategoriaDespesa categoriaDespesa = this.adapterCategoriaDespesa.getItem(this.spnCategoriaDespesa.getSelectedItemPosition());

            if (this.adapterSubCategoriaDespesa.getCount() > 0) {
                SubCategoriaDespesa subCategoriaDespesa = this.adapterSubCategoriaDespesa.getItem(this.spnSubCategoriaDespesa.getSelectedItemPosition());
                this.regraImportacaoSMS.setSubCategoriaDespesa(subCategoriaDespesa);
            }

            this.regraImportacaoSMS.setCategoriaDespesa(categoriaDespesa);

            this.regraImportacaoSMS.setDescricaoReceitaDespesa(this.edtTextoDescDespesa.getText().toString());


        } else //Transferência
        {
            this.regraImportacaoSMS.setContaDestino(this.adapterConta.getItem(this.spnContaDestino.getSelectedItemPosition()));
        }

    }

    private void salvaAtual() {

        this.preencheDadosSalvar();

        try {

            this.repositorioRegraImpSMS = new RepositorioRegraImpSMS(this);

            if (this.regraImportacaoSMS.getId() == 0) {
                this.regraImportacaoSMS.setDataInclusao(DateUtils.getCurrentDatetime());
                this.repositorioRegraImpSMS.insere(this.regraImportacaoSMS);
            } else {
                this.regraImportacaoSMS.setDataAlteracao(DateUtils.getCurrentDatetime());
                this.repositorioRegraImpSMS.altera(this.regraImportacaoSMS);
            }

            ToastHelper.showToastShort(this, this.getString(R.string.msg_salvar_sucesso));
            this.finish();


        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }

    private void excluiAtual() {

        try {

            this.repositorioRegraImpSMS = new RepositorioRegraImpSMS(this);

            this.repositorioRegraImpSMS.exclui(this.regraImportacaoSMS);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));

            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }


}