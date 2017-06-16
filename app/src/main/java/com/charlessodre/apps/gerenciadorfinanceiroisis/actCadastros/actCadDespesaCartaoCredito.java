package com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterFaturaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterSubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.DespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.FaturaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.TipoRepeticao;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioSubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioFaturaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgLancamentosDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateListenerShow;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.MessageBoxHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.StringUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.TextWatcherPay;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class actCadDespesaCartaoCredito extends actBaseCadastros implements CompoundButton.OnCheckedChangeListener, frgLancamentosDialog.onDialogClick, frgConfirmacaoDialog.onDialogClick, Spinner.OnItemSelectedListener {

    //Objetos Tela
    private EditText edtNome;
    private Spinner spnCategoriaDespesa;
    private Spinner spnSubCategoriaDespesa;
    private Spinner spnCartaoCredito;
    private Spinner spnFaturaCartaoCredito;
    private EditText edtDataDespesa;
    private EditText edtValorDespesa;
    private EditText edtTotalRepeticao;
    private CheckBox cbxParcelada;
    private CheckBox cbxFixa;
    private TextWatcherPay twValorDespesa;
    private TextView txtParcelas;
    private TextView txtValorParcela;

    //Atributos
    private DespesaCartaoCredito despesaCartaoCredito;
    private RepositorioDespesaCartaoCredito repositorioDespesaCartaoCredito;
    private RepositorioFaturaCartaoCredito repositorioFaturaCartaoCredito;
    private RepositorioSubCategoriaDespesa repositorioSubCategoriaDespesa;
    private DateListenerShow dataDespesaListenerShow;
    private AdapterCartaoCredito adapterCartaoCredito;
    private AdapterFaturaCartaoCredito adapterFaturaCartaoCredito;
    private AdapterCategoriaDespesa adapterCategoriaDespesa;
    private AdapterSubCategoriaDespesa adapterSubCategoriaDespesa;
    private Date dataDespesa;
    private String symbol;

    //Contantes
    public static final String DESPESA_CARTAO_CREDITO = "DESPESA_CARTAO_CREDITO";
    public static final String PARAM_CARTAO_CREDITO_ID = "PARAM_CARTAO_CREDITO_ID";

    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_despesa_cartao_credito);

        inicializaObjetos();
        carregaSpinnerCartaoCredito();
        carregaSpinnerCategoriaDespesa();

        getParametrosRecebidos();
        preencheDados();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);

        if (this.despesaCartaoCredito.getId() == 0) {
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
                this.cbxParcelada.setChecked(false);
                this.edtTotalRepeticao.setEnabled(false);

            }

        } else if (buttonView.getId() == R.id.cbxParcelada) {
            if (this.cbxParcelada.isChecked()) {
                this.cbxFixa.setChecked(false);
                this.edtTotalRepeticao.setEnabled(true);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.spnCategoriaDespesa) {
            long idCategoria = ((CategoriaDespesa) parent.getSelectedItem()).getId();
            this.carregaSpinnerSubCategoriaDespesa(idCategoria);

            this.spnSubCategoriaDespesa.setSelection(this.adapterSubCategoriaDespesa.getIndexFromElement(this.despesaCartaoCredito.getSubCategoriaDespesa().getId()));
        } else if (parent.getId() == R.id.spnCartaoCredito) {

            long idCartao = ((CartaoCredito) parent.getSelectedItem()).getId();
            this.carregaSpinnerFatura(idCartao);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Métodos
    @Override
    protected void inicializaObjetos() {
        this.edtNome = (EditText) findViewById(R.id.edtNomeDespesa);
        this.spnCategoriaDespesa = (Spinner) findViewById(R.id.spnCategoriaDespesa);
        this.spnCartaoCredito = (Spinner) findViewById(R.id.spnCartaoCredito);
        this.spnSubCategoriaDespesa = (Spinner) findViewById(R.id.spnSubCategoriaDespesa);
        this.spnFaturaCartaoCredito = (Spinner) findViewById(R.id.spnFaturaCartaoCredito);

        this.spnCategoriaDespesa.setOnItemSelectedListener(this);
        this.spnFaturaCartaoCredito.setOnItemSelectedListener(this);

        this.edtDataDespesa = (EditText) findViewById(R.id.edtDataDespesa);
        this.edtValorDespesa = (EditText) findViewById(R.id.edtValorDespesa);
        this.edtTotalRepeticao = (EditText) findViewById(R.id.edtTotalRepeticao);

        this.cbxFixa = (CheckBox) findViewById(R.id.cbxFixa);
        this.cbxParcelada = (CheckBox) findViewById(R.id.cbxParcelada);
        this.txtParcelas = (TextView) findViewById(R.id.txtParcelas);

        this.cbxFixa.setOnCheckedChangeListener(this);
        this.cbxParcelada.setOnCheckedChangeListener(this);

        this.txtValorParcela = (TextView) findViewById(R.id.txtValorParcela);

        this.twValorDespesa = new TextWatcherPay(this.edtValorDespesa, "%.2f");
        this.edtValorDespesa.addTextChangedListener(this.twValorDespesa);

        ActionBarHelper.menuCancel(getSupportActionBar(), this.getString(R.string.lblDespesaCartaoCredito));

        int corTela = ColorHelper.getColor(this, R.color.corTelaDespesasCartaoCredito);

        ActionBarHelper.setBackgroundColor(getSupportActionBar(), corTela);

        ActionBarHelper.setStatusBarColor(this.getWindow(), corTela);

        this.dataDespesaListenerShow = new DateListenerShow(this, this.edtDataDespesa, false);

        this.edtDataDespesa.setText(DateUtils.getCurrentDateShort());

        this.symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();
        this.edtTotalRepeticao.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) {
                    int qtdParcelas = Integer.parseInt(edtTotalRepeticao.getText().toString());
                    Double valorDespesa = twValorDespesa.getValueWithoutMask();

                    if (qtdParcelas > 0 && valorDespesa > 0) {

                        double valorParcela = valorDespesa / qtdParcelas;
                        txtValorParcela.setText(symbol + " " + NumberUtis.getFormartCurrency(valorParcela));
                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void carregaSpinnerCartaoCredito() {

        RepositorioCartaoCredito repositorioCartaoCredito = new RepositorioCartaoCredito(this);
        ArrayList<CartaoCredito> cartoes = repositorioCartaoCredito.getAll();

        this.adapterCartaoCredito = new AdapterCartaoCredito(this, R.layout.item_cartao_credito_simples);

        this.adapterCartaoCredito.addAll(cartoes);

        this.spnCartaoCredito.setAdapter(this.adapterCartaoCredito);

        if (cartoes.size() > 0)
            this.carregaSpinnerFatura(cartoes.get(0).getId());
    }


    private void carregaSpinnerFatura(Long idCartaoCredito) {

        if (this.repositorioFaturaCartaoCredito == null)
            this.repositorioFaturaCartaoCredito = new RepositorioFaturaCartaoCredito(this);

        this.adapterFaturaCartaoCredito = new AdapterFaturaCartaoCredito(this, R.layout.item_fatura_simples);

        int anoMes = DateUtils.getCurrentYearAndMonthAddMonth(-1);

        this.adapterFaturaCartaoCredito.addAll(repositorioFaturaCartaoCredito.getProximasFaturasCartaoCredito(idCartaoCredito, anoMes));

        this.spnFaturaCartaoCredito.setAdapter(this.adapterFaturaCartaoCredito);
    }


    private void carregaSpinnerCategoriaDespesa() {

        RepositorioCategoriaDespesa repositorioCategoriaDespesa = new RepositorioCategoriaDespesa(this);

        this.adapterCategoriaDespesa = new AdapterCategoriaDespesa(this, R.layout.item_categoria);

        this.adapterCategoriaDespesa.addAll(repositorioCategoriaDespesa.getAll());

        this.spnCategoriaDespesa.setAdapter(this.adapterCategoriaDespesa);
    }

    private void carregaSpinnerSubCategoriaDespesa(Long idCategoriaDespesa) {

        if (this.repositorioSubCategoriaDespesa == null)
            this.repositorioSubCategoriaDespesa = new RepositorioSubCategoriaDespesa(this);

        this.adapterSubCategoriaDespesa = new AdapterSubCategoriaDespesa(this, R.layout.item_sub_categoria);

        this.adapterSubCategoriaDespesa.addAll(repositorioSubCategoriaDespesa.buscaPorIdCategoriaDespesa(idCategoriaDespesa));

        this.spnSubCategoriaDespesa.setAdapter(this.adapterSubCategoriaDespesa);
    }

    private void getParametrosRecebidos() {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actCadDespesaCartaoCredito.DESPESA_CARTAO_CREDITO))) {
            this.despesaCartaoCredito = (DespesaCartaoCredito) bundle.getSerializable(actCadDespesaCartaoCredito.DESPESA_CARTAO_CREDITO);

        } else {
            this.despesaCartaoCredito = new DespesaCartaoCredito();
        }

        if ((bundle != null) && (bundle.containsKey(actCadDespesaCartaoCredito.PARAM_CARTAO_CREDITO_ID))) {

            long idCartao = 0;
            idCartao = bundle.getLong(actCadDespesaCartaoCredito.PARAM_CARTAO_CREDITO_ID);

            this.spnCartaoCredito.setSelection(this.adapterCartaoCredito.getIndexFromElement(idCartao));

        }

    }

    private void preencheDados() {

        if( this.despesaCartaoCredito.getId()>0) {
            this.edtNome.setText(this.despesaCartaoCredito.getNome());

            this.edtValorDespesa.setText(NumberUtis.getFormartCurrency(this.despesaCartaoCredito.getValor()));

            this.spnCategoriaDespesa.setSelection(this.adapterCategoriaDespesa.getIndexFromElement(this.despesaCartaoCredito.getCategoriaDespesa().getId()));
            this.spnCartaoCredito.setSelection(this.adapterCartaoCredito.getIndexFromElement(this.despesaCartaoCredito.getCartaoCredito().getId()));


            this.edtDataDespesa.setText(DateUtils.dateToString(this.despesaCartaoCredito.getDataDespesa()));

            if (this.despesaCartaoCredito.getTotalRepeticao() > 0 || this.despesaCartaoCredito.isFixa()) {

                this.edtTotalRepeticao.setText(String.valueOf(this.despesaCartaoCredito.getTotalRepeticao()));
                this.cbxParcelada.setChecked(true);
                this.cbxFixa.setChecked(this.despesaCartaoCredito.isFixa());

                if (!this.despesaCartaoCredito.isFixa()) {
                    this.txtParcelas.setVisibility(View.VISIBLE);
                    this.txtParcelas.setText(this.despesaCartaoCredito.getRepeticaoAtual() + "/" + this.despesaCartaoCredito.getTotalRepeticao());
                    this.edtDataDespesa.setEnabled(false);
                }
            }

            this.cbxParcelada.setEnabled(false);
            this.edtTotalRepeticao.setEnabled(false);
            this.cbxFixa.setEnabled(false);
        }

    }

    private boolean validaCamposTela() {
        boolean retorno = true;

        if (StringUtils.isNullOrEmpty(this.edtNome.getText().toString())) {
            this.edtNome.setError(this.getString(R.string.msg_preenchimento_obrigatorio));

            retorno = false;
        }

        if (this.twValorDespesa.getValueWithoutMask() <= 0) {
            this.edtValorDespesa.setError(this.getString(R.string.msg_valor_maior_zero));

            retorno = false;
        }

        if (this.spnCartaoCredito.getCount() < 1) {
            MessageBoxHelper.show(this, "", this.getString(R.string.msg_cadastrar_cartao_credito));
            retorno = false;
        }

        if (this.cbxParcelada.isChecked() && StringUtils.isNullOrEmpty(this.edtTotalRepeticao.getText().toString())) {
            // MessageBoxHelper.show(this,"", this.getString(R.string.msg_qtd_parcela_maior_um));
            this.edtTotalRepeticao.setError(this.getString(R.string.msg_qtd_parcela_maior_um));
            retorno = false;
        }

        return retorno;

    }

    private void getTipoSalvamento() {

        if (this.despesaCartaoCredito.getTotalRepeticao() > 0 || this.despesaCartaoCredito.isFixa()) {

            super.exibePopUpLancamento(Constantes.TipoLancamento.LANCAMENTO_RECEITA, Constantes.TipoMensagem.ALTERACAO);

        } else {
            this.salvaAtual();
        }

    }

    private void preencheDadosSalvar() {

        this.despesaCartaoCredito.setNome(this.edtNome.getText().toString());

        this.despesaCartaoCredito.setValor(this.twValorDespesa.getValueWithoutMask());

        this.dataDespesa = this.dataDespesaListenerShow.getDateListenerSelect().getDate();

        if (this.dataDespesa == null)
            this.dataDespesa = DateUtils.getCurrentDatetime();

        this.despesaCartaoCredito.setDataDespesa(this.dataDespesa);
        this.despesaCartaoCredito.setAnoMesDespesa(DateUtils.getYearAndMonth(this.dataDespesa));

        CartaoCredito cartaoCredito = this.adapterCartaoCredito.getItem(this.spnCartaoCredito.getSelectedItemPosition());
        FaturaCartaoCredito faturaCartaoCredito = this.adapterFaturaCartaoCredito.getItem(this.spnFaturaCartaoCredito.getSelectedItemPosition());
        CategoriaDespesa categoriaDespesa = this.adapterCategoriaDespesa.getItem(this.spnCategoriaDespesa.getSelectedItemPosition());

        this.despesaCartaoCredito.setCartaoCredito(cartaoCredito);
        this.despesaCartaoCredito.setFaturaCartaoCredito(faturaCartaoCredito);
        this.despesaCartaoCredito.setCategoriaDespesa(categoriaDespesa);

        if (this.adapterSubCategoriaDespesa.getCount() > 0) {
            SubCategoriaDespesa subCategoriaDespesa = this.adapterSubCategoriaDespesa.getItem(this.spnSubCategoriaDespesa.getSelectedItemPosition());
            this.despesaCartaoCredito.setSubCategoriaDespesa(subCategoriaDespesa);
        }

        if (this.despesaCartaoCredito.getId() == 0) {
            if (this.cbxParcelada.isChecked()) {

                int totalRepeticao = 1;

                if (!StringUtils.isNullOrEmpty(this.edtTotalRepeticao.getText().toString()))
                    totalRepeticao = Integer.parseInt(this.edtTotalRepeticao.getText().toString());

                this.despesaCartaoCredito.setTotalRepeticao(totalRepeticao);
                this.despesaCartaoCredito.setRepeticaoAtual(1);
                this.despesaCartaoCredito.setIdTipoRepeticao(TipoRepeticao.MENSAL);

            }

            this.despesaCartaoCredito.setFixa(this.cbxFixa.isChecked());
        }

        this.despesaCartaoCredito.setOrdemExibicao(super.getNumOrdemExibicao());

    }

    private void salvaAtual() {

        this.preencheDadosSalvar();

        try {

            this.repositorioDespesaCartaoCredito = new RepositorioDespesaCartaoCredito(this);

            if (this.despesaCartaoCredito.getId() == 0) {
                this.despesaCartaoCredito.setDataInclusao(DateUtils.getCurrentDatetime());
                repositorioDespesaCartaoCredito.insere(this.despesaCartaoCredito);
            } else {
                this.despesaCartaoCredito.setDataAlteracao(DateUtils.getCurrentDatetime());
                repositorioDespesaCartaoCredito.altera(this.despesaCartaoCredito);
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

            this.repositorioDespesaCartaoCredito = new RepositorioDespesaCartaoCredito(this);

            this.despesaCartaoCredito.setDataAlteracao(DateUtils.getCurrentDatetime());
            repositorioDespesaCartaoCredito.alteraProximas(this.despesaCartaoCredito);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_salvar_sucesso));
            this.finish();


        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }

    private void salvaTodas() {

        this.preencheDadosSalvar();

        try {

            this.repositorioDespesaCartaoCredito = new RepositorioDespesaCartaoCredito(this);

            this.despesaCartaoCredito.setDataAlteracao(DateUtils.getCurrentDatetime());
            repositorioDespesaCartaoCredito.alteraTodas(this.despesaCartaoCredito);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_salvar_sucesso));
            this.finish();


        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private void getTipoExclusao() {

        if (this.despesaCartaoCredito.getTotalRepeticao() > 0 || this.despesaCartaoCredito.isFixa()) {

            super.exibePopUpLancamento(Constantes.TipoLancamento.LANCAMENTO_RECEITA, Constantes.TipoMensagem.EXCLUSAO);

        } else {
            super.exibePopUpConfirmacaoDialog(this.getString(R.string.msg_excluir));

        }


    }

    private void excluiAtual() {

        try {

            this.repositorioDespesaCartaoCredito = new RepositorioDespesaCartaoCredito(this);

            this.repositorioDespesaCartaoCredito.exclui(this.despesaCartaoCredito);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));

            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }

    private void excluiPendentes() {


        try {

            this.repositorioDespesaCartaoCredito = new RepositorioDespesaCartaoCredito(this);

            this.repositorioDespesaCartaoCredito.excluiProximas(this.despesaCartaoCredito);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));
            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }

    private void excluiTodas() {

        try {

            this.repositorioDespesaCartaoCredito = new RepositorioDespesaCartaoCredito(this);

            this.repositorioDespesaCartaoCredito.excluiTodas(this.despesaCartaoCredito);
            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));
            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }


}
