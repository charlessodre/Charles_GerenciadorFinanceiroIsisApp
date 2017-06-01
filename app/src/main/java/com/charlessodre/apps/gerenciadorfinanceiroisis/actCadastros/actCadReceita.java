package com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgLancamentosDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterCategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ArrayAdapterHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateListenerShow;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.MessageBoxHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.TextWatcherPay;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.TipoRepeticao;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.StringUtils;

import java.util.Date;

public class actCadReceita extends actBaseCadastros implements CompoundButton.OnCheckedChangeListener, frgLancamentosDialog.onDialogClick, frgConfirmacaoDialog.onDialogClick {


    //Contantes
    public static final String PARAM_RECEITA = "RECEITA";
    //Objetos Tela
    private EditText edtNome;
    private Spinner spnCategoriaReceita;
    private Spinner spnContaReceita;
    private Spinner spnTipoRepeticao;
    private EditText edtDataReceita;
    private EditText edtValorReceita;
    private EditText edtTotalRepeticao;
    private CheckBox cbxReceitaRecebida;
    private CheckBox cbxRepetir;
    private CheckBox cbxFixa;
    private TextWatcherPay textWatcher;
    private TextView txtParcelas;
    //Atributos
    private Receita receita;
    private RepositorioReceita repositorioReceita;
    private DateListenerShow dateListenerShow;
    private AdapterConta adapterConta;
    private AdapterCategoriaReceita adapterCategoriaReceita;
    private Date dataReceita;

    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_receita);

        inicializaObjetos();
        carregaSpinnerConta();
        carregaSpinnerCategoriaReceita();
        carregaSpinnerTipoRepeticao();

        preencheDados();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);

        if (this.receita.getId() == 0) {
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

                    this.getTipoSalvamento();
                }

                break;

            case R.id.menu_excluir:

                this.getTipoExclusao();
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.cbxFixa) {
            if (this.cbxFixa.isChecked()) {
                this.cbxRepetir.setChecked(false);
                this.edtTotalRepeticao.setEnabled(false);
                this.spnTipoRepeticao.setEnabled(false);
            }

        } else if (buttonView.getId() == R.id.cbxRepetir) {
            if (this.cbxRepetir.isChecked()) {
                this.cbxFixa.setChecked(false);
                this.edtTotalRepeticao.setEnabled(true);
                this.spnTipoRepeticao.setEnabled(true);

            }
        }
    }

    @Override
    public void onDialogClick(frgLancamentosDialog dialog, int opcaoSelecionada, int tipoMensagem) {

        if (tipoMensagem == Constantes.TipoMensagem.EXCLUSAO) {
            if (opcaoSelecionada == Constantes.OpcaoExclusaoAlteracao.ATUAL) {
                this.excluiAtual();

            } else if (opcaoSelecionada == Constantes.OpcaoExclusaoAlteracao.PENDENTES) {
                this.excluiPendentes();

            } else if (opcaoSelecionada == Constantes.OpcaoExclusaoAlteracao.TODAS) {
                this.excluiTodas();
            } else if (opcaoSelecionada == Constantes.OpcaoExclusaoAlteracao.CANCELAR) {
                dialog.dismiss();
            }
        } else if (tipoMensagem == Constantes.TipoMensagem.ALTERACAO) {

            if (opcaoSelecionada == Constantes.OpcaoExclusaoAlteracao.ATUAL) {
                this.salvaAtual();

            } else if (opcaoSelecionada == Constantes.OpcaoExclusaoAlteracao.PENDENTES) {
                this.salvaProximas();

            } else if (opcaoSelecionada == Constantes.OpcaoExclusaoAlteracao.TODAS) {
                this.salvaTodas();
            } else if (opcaoSelecionada == Constantes.OpcaoExclusaoAlteracao.CANCELAR) {
                dialog.dismiss();
            }
        }
    }

    @Override
    public void onDialogClick(frgConfirmacaoDialog dialog) {
        this.excluiAtual();

    }

    //Métodos
    @Override
    protected void inicializaObjetos() {
        this.edtNome = (EditText) findViewById(R.id.edtNomeReceita);
        this.spnCategoriaReceita = (Spinner) findViewById(R.id.spnCategoriaReceita);
        this.spnContaReceita = (Spinner) findViewById(R.id.spnContaReceita);
        this.spnTipoRepeticao = (Spinner) findViewById(R.id.spnTipoRepeticao);
        this.edtDataReceita = (EditText) findViewById(R.id.edtDataReceita);
        this.edtValorReceita = (EditText) findViewById(R.id.edtValorReceita);
        this.edtTotalRepeticao = (EditText) findViewById(R.id.edtTotalRepeticao);
        this.cbxReceitaRecebida = (CheckBox) findViewById(R.id.cbxReceitaRecebida);
        this.cbxFixa = (CheckBox) findViewById(R.id.cbxFixa);
        this.cbxRepetir = (CheckBox) findViewById(R.id.cbxRepetir);
        this.txtParcelas = (TextView) findViewById(R.id.txtParcelas);

        this.cbxFixa.setOnCheckedChangeListener(this);
        this.cbxRepetir.setOnCheckedChangeListener(this);

        this.spnTipoRepeticao.setEnabled(false);

        this.textWatcher = new TextWatcherPay(this.edtValorReceita, "%.2f");
        this.edtValorReceita.addTextChangedListener(this.textWatcher);

        ActionBarHelper.menuCancel(getSupportActionBar(), this.getString(R.string.lblReceita));

        int corTela = ColorHelper.getColor(this, R.color.corTelaReceitas);

        ActionBarHelper.setBackgroundColor(getSupportActionBar(), corTela);

        ActionBarHelper.setStatusBarColor(this.getWindow(), corTela);

        this.dateListenerShow = new DateListenerShow(this, this.edtDataReceita, false);

        this.edtDataReceita.setText(DateUtils.getCurrentDateShort());

    }

    private void carregaSpinnerConta() {
        RepositorioConta repositorioConta = new RepositorioConta(this);

        this.adapterConta = new AdapterConta(this, R.layout.item_conta_simples);

        this.adapterConta.addAll(repositorioConta.buscaTodos());

        this.spnContaReceita.setAdapter(this.adapterConta);
    }

    private void carregaSpinnerCategoriaReceita() {
        RepositorioCategoriaReceita repositorioCategoriaReceita = new RepositorioCategoriaReceita(this);

        this.adapterCategoriaReceita = new AdapterCategoriaReceita(this, R.layout.item_categoria);

        this.adapterCategoriaReceita.addAll(repositorioCategoriaReceita.buscaTodos());

        this.spnCategoriaReceita.setAdapter(this.adapterCategoriaReceita);
    }

    private void carregaSpinnerTipoRepeticao() {
        ArrayAdapter arrayAdapter = ArrayAdapterHelper.fillSpinnerString(this, this.spnTipoRepeticao);
        arrayAdapter.addAll(TipoRepeticao.getTipoRepeticao());
    }

    private void preencheDados() {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actCadReceita.PARAM_RECEITA))) {
            this.receita = (Receita) bundle.getSerializable(actCadReceita.PARAM_RECEITA);

            this.edtNome.setText(this.receita.getNome());

            this.edtValorReceita.setText(NumberUtis.getFormartCurrency(this.receita.getValor()));

            this.spnCategoriaReceita.setSelection(this.adapterCategoriaReceita.getIndexFromElement(this.receita.getCategoriaReceita().getId()));
            this.spnContaReceita.setSelection(this.adapterConta.getIndexFromElement(this.receita.getConta().getId()));

            this.edtDataReceita.setText(DateUtils.dateToString(this.receita.getDataReceita()));
            this.cbxReceitaRecebida.setChecked(this.receita.isPaga());


            if (this.receita.getTotalRepeticao() > 0 || this.receita.isFixa()) {

                this.spnTipoRepeticao.setSelection(this.receita.getIdTipoRepeticao());
                this.edtTotalRepeticao.setText(String.valueOf(this.receita.getTotalRepeticao()));
                this.cbxRepetir.setChecked(true);
                this.cbxFixa.setChecked(this.receita.isFixa());

                if (!this.receita.isFixa()) {
                    this.txtParcelas.setVisibility(View.VISIBLE);
                    this.txtParcelas.setText(this.receita.getRepeticaoAtual() + "/" + this.receita.getTotalRepeticao());
                    this.spnTipoRepeticao.setSelection(this.receita.getIdTipoRepeticao());
                    this.edtDataReceita.setEnabled(false);
                }
            }

            this.cbxRepetir.setEnabled(false);
            this.edtTotalRepeticao.setEnabled(false);
            this.spnTipoRepeticao.setEnabled(false);
            this.cbxFixa.setEnabled(false);


        } else {
            this.receita = new Receita();
        }


    }

    private boolean validaCamposTela() {
        boolean retorno = true;

        if (StringUtils.isNullOrEmpty(this.edtNome.getText().toString())) {
            this.edtNome.setError(this.getString(R.string.msg_preenchimento_obrigatorio));

            retorno = false;
        }

        if (this.textWatcher.getValueWithoutMask() <= 0) {
            this.edtValorReceita.setError(this.getString(R.string.msg_valor_maior_zero));

            retorno = false;
        }

        if(this.spnContaReceita.getCount() < 1)
        {
            MessageBoxHelper.show(this,"", this.getString(R.string.msg_cadastrar_conta));
            retorno = false;
        }
        return retorno;

    }

    private void getTipoSalvamento() {

        if (this.receita.getTotalRepeticao() > 0 || this.receita.isFixa()) {

            super.exibePopUpLancamento(Constantes.TipoLancamento.LANCAMENTO_RECEITA, Constantes.TipoMensagem.ALTERACAO);

        } else {
            this.salvaAtual();
        }

    }

    private void preencheDadosSalvar() {

        this.receita.setNome(this.edtNome.getText().toString());

        this.receita.setValor(this.textWatcher.getValueWithoutMask());

        this.dataReceita = this.dateListenerShow.getDateListenerSelect().getDate();

        if (this.dataReceita == null) {

            if (this.receita.getDataReceita() != null)
                this.dataReceita = this.receita.getDataReceita();
            else
                this.dataReceita = DateUtils.getCurrentDatetime();
        }

        this.receita.setDataReceita(this.dataReceita);
        this.receita.setAnoMes(DateUtils.getYearAndMonth(this.dataReceita));

        Conta conta = this.adapterConta.getItem(this.spnContaReceita.getSelectedItemPosition());

        CategoriaReceita categoriaReceita = this.adapterCategoriaReceita.getItem(this.spnCategoriaReceita.getSelectedItemPosition());

        this.receita.setConta(conta);
        this.receita.setCategoriaReceita(categoriaReceita);

        if (this.receita.getId() == 0) {
            if (this.cbxRepetir.isChecked()) {

                int totalRepeticao = 1;

                if (!StringUtils.isNullOrEmpty(this.edtTotalRepeticao.getText().toString()))
                    totalRepeticao = Integer.parseInt(this.edtTotalRepeticao.getText().toString());

                this.receita.setTotalRepeticao(totalRepeticao);
                this.receita.setRepeticaoAtual(1);
                this.receita.setIdTipoRepeticao(this.spnTipoRepeticao.getSelectedItemPosition());
            }

            this.receita.setFixa(this.cbxFixa.isChecked());
        }

        if (!this.receita.isPaga() && this.cbxReceitaRecebida.isChecked()) {
            this.receita.setPaga(true);
            this.receita.setDataRecebimento(DateUtils.getCurrentDatetime());

        } else if (!this.cbxReceitaRecebida.isChecked() && this.receita.getId() != 0 && this.receita.getDataRecebimento() != null) {

            this.receita.setEstornaRecebimentoReceita(true);
            this.receita.setPaga(false);
            this.receita.setDataRecebimento(null);

        }

        this.receita.setOrdemExibicao(super.getNumOrdemExibicao());

    }

    private void salvaAtual() {

        this.preencheDadosSalvar();

        try {

            this.repositorioReceita = new RepositorioReceita(this);

            if (this.receita.getId() == 0) {
                this.receita.setDataInclusao(this.dataReceita);
                repositorioReceita.insere(this.receita);
            } else {
                this.receita.setDataAlteracao(DateUtils.getCurrentDatetime());
                repositorioReceita.altera(this.receita);
            }

            ToastHelper.showToastShort(this, this.getString(R.string.msg_salvar_sucesso));
            this.finish();


        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }

    private void salvaProximas() {

        this.preencheDadosSalvar();

        try {

            this.repositorioReceita = new RepositorioReceita(this);

            this.receita.setDataAlteracao(DateUtils.getCurrentDatetime());
            repositorioReceita.alteraProximas(this.receita);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_salvar_sucesso));
            this.finish();


        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }

    private void salvaTodas() {

        this.preencheDadosSalvar();

        try {

            this.repositorioReceita = new RepositorioReceita(this);

            this.receita.setDataAlteracao(DateUtils.getCurrentDatetime());
            repositorioReceita.alteraTodas(this.receita);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_salvar_sucesso));
            this.finish();


        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private void getTipoExclusao() {

        if (this.receita.getTotalRepeticao() > 0 || this.receita.isFixa()) {

            super.exibePopUpLancamento(Constantes.TipoLancamento.LANCAMENTO_RECEITA, Constantes.TipoMensagem.EXCLUSAO);

        } else {
            super.exibePopUpConfirmacaoDialog(this.getString(R.string.msg_excluir));

        }


    }

    private void excluiAtual() {

        try {

            this.repositorioReceita = new RepositorioReceita(this);

            this.repositorioReceita.exclui(this.receita);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));

            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }

    private void excluiPendentes() {


        try {

            this.repositorioReceita = new RepositorioReceita(this);

            this.repositorioReceita.excluiProximas(this.receita);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));
            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }

    private void excluiTodas() {

        try {

            this.repositorioReceita = new RepositorioReceita(this);

            this.repositorioReceita.excluiTodas(this.receita);
            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));
            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }


}
