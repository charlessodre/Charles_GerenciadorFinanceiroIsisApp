package com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmaExclusaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ArrayAdapterHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.MessageBoxHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.StringUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.TextWatcherPay;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;

public class actCadCartaoCredito extends actBaseCadastros implements frgConfirmaExclusaoDialog.onDialogClick, frgConfirmacaoDialog.onDialogClick {

    //Atributos
    private AdapterConta adapterConta;
    private CartaoCredito cartaoCredito;
    private TextWatcherPay textWatcher;
    private RepositorioCartaoCredito repositorioCartaoCredito;
    private boolean exclusao;


    //Objetos Tela
    private EditText edtNome;
    private Spinner spnBandeiraCartaoCredito;
    private Spinner spnContaAssociada;
    private Spinner spnDiaFechamentoFaturaCartao;
    private Spinner spnDiaVencimentoCartao;
    private EditText edtValorLimiteCartaoCredito;
    private CheckBox cbxExibirSomaResumo;
    private CheckBox cbxAtivo;


    //CartaoCreditontes
    public static final String PARAM_CARTAO_CREDITO = "CARTAO_CREDITO";

    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_cad_cartao_credito);

        this.inicializaObjetos();
        this.carregaSpinnersGerais();
        this.carregaSpinnerConta();
        this.preencheCampos();
        super.adicionaFragOrdemExibicaoCor(this.cartaoCredito.getOrdemExibicao(), this.cartaoCredito.getNoCor(), R.id.frag_container_cad_cartao_cred);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);

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

                    try {
                        this.salva();
                        ToastHelper.showToastShort(this, this.getString(R.string.msg_salvar_sucesso));
                        this.finish();
                    } catch (Exception ex) {

                        MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
                    }
                }

                break;

            case R.id.menu_excluir:

                if (validaCamposTela()) {
                    this.verificaDependentesExclusao();
                }

                break;

            default:
                break;
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

        this.excluiCartaoCredito();
    }

    //Métodos
    @Override
    protected void inicializaObjetos() {
        this.edtNome = (EditText) findViewById(R.id.edtNomeCartaoCredito);

        this.spnBandeiraCartaoCredito = (Spinner) findViewById(R.id.spnBandeiraartaoCredito);
        this.spnContaAssociada  =  (Spinner) findViewById(R.id.spnContaAssociada);
        this.spnDiaFechamentoFaturaCartao  =  (Spinner) findViewById(R.id.spnDiaFechamentoFaturaCartao);
        this.spnDiaVencimentoCartao  =  (Spinner) findViewById(R.id.spnDiaVencimentoCartao);

        this.edtValorLimiteCartaoCredito = (EditText) findViewById(R.id.edtValorLimiteCartaoCredito);

        this.cbxExibirSomaResumo = (CheckBox) findViewById(R.id.cbxExibirSomaResumo);
        this.cbxAtivo = (CheckBox) findViewById(R.id.cbxAtivo);

        textWatcher = new TextWatcherPay(this.edtValorLimiteCartaoCredito, "%.2f");

        edtValorLimiteCartaoCredito.addTextChangedListener(textWatcher);

        super.setMenuCancel(this.getString(R.string.lblCartaoCredito));
        super.setColorStatusBar(R.color.corTelaCartaoCredito);
        super.setColorActionBar(R.color.corTelaCartaoCredito);


    }

    private void salva() {

        this.cartaoCredito.setNome(this.edtNome.getText().toString());
        this.cartaoCredito.setValorLimite(textWatcher.getValueWithoutMask());
        this.cartaoCredito.setExibiSomaResumo(this.cbxExibirSomaResumo.isChecked());

        this.cartaoCredito.setNoBandeiraCartao(this.spnBandeiraCartaoCredito.getSelectedItemPosition());
        this.cartaoCredito.setNoDiaFechamentoFatura(this.spnDiaFechamentoFaturaCartao.getSelectedItemPosition());
        this.cartaoCredito.setNoDiaVencimentoFatura(this.spnDiaVencimentoCartao.getSelectedItemPosition());

        Conta contaAssociada = this.adapterConta.getItem(this.spnContaAssociada.getSelectedItemPosition());
        this.cartaoCredito.setContaAssociada(contaAssociada);

        this.cartaoCredito.setOrdemExibicao(super.getNumOrdemExibicao());
        this.cartaoCredito.setNoCor(super.getNumCor());
        this.cartaoCredito.setAtivo(this.cbxAtivo.isChecked());

        this.repositorioCartaoCredito = new RepositorioCartaoCredito(this);

        if (this.cartaoCredito.getId() == 0) {

            this.cartaoCredito.setDataInclusao(DateUtils.getCurrentDatetime());
           // this.cartaoCredito.setAnoMesFatura(DateUtils.getCurrentYearAndMonth());
            repositorioCartaoCredito.insere(this.cartaoCredito);
        } else {
            this.cartaoCredito.setDataAlteracao(DateUtils.getCurrentDatetime());
            repositorioCartaoCredito.altera(this.cartaoCredito);
        }

    }

    private void verificaDependentesExclusao() {

        boolean mostraMsgDialog = false;

        try {



             /*   RepositorioDespesa repositorioDespesa = new RepositorioDespesa(this);

                int qtdeDespesas = repositorioDespesa.getQtdDespesaCartaoCredito(this.cartaoCredito.getId());

                if (qtdeDespesas >= 1) {
                    mostraMsgDialog = true;

                } else {
                    int qtdeTransferencias = 0;

                    RepositorioTransferencia repositorioTransferencia = new RepositorioTransferencia(this);

                    qtdeTransferencias = repositorioTransferencia.getQtdTransferenciaCartaoCredito(this.cartaoCredito.getId());

                    if (qtdeTransferencias >= 1) {
                        mostraMsgDialog = true;
                    }
                }*/


            if (mostraMsgDialog) {
                super.exibeExclusaoDialog();
            } else {
                super.exibePopUpConfirmacaoDialog(this.getString(R.string.msg_excluir));
            }
        }catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }

    }

    private void excluiCartaoCredito() {

        try {
            this.repositorioCartaoCredito = new RepositorioCartaoCredito(this);
            this.repositorioCartaoCredito.exclui(this.cartaoCredito);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));
            this.finish();
        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private void excluiDependencias() {

        try {
            this.repositorioCartaoCredito = new RepositorioCartaoCredito(this);
            this.repositorioCartaoCredito.excluiComDependentes(this.cartaoCredito);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));
            this.finish();
        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private boolean validaCamposTela() {
        boolean retorno = true;

        if (StringUtils.isNullOrEmpty(this.edtNome.getText().toString())) {
            this.edtNome.setError(this.getString(R.string.msg_preenchimento_obrigatorio));

            retorno = false;
        }

        if(this.spnContaAssociada.getCount() < 1)
        {
            MessageBoxHelper.show(this,"", this.getString(R.string.msg_cadastrar_conta));
            retorno = false;
        }
        return retorno;

    }

    private void carregaSpinnersGerais() {

        ArrayAdapter arrayAdapterBandeira = ArrayAdapterHelper.fillSpinnerString(this, this.spnBandeiraCartaoCredito);
        arrayAdapterBandeira.addAll(CartaoCredito.getBandeiraCartao(this));

        ArrayAdapter arrayAdapterDiaFechamento = ArrayAdapterHelper.fillSpinnerString(this, this.spnDiaFechamentoFaturaCartao);
        arrayAdapterDiaFechamento.addAll(DateUtils.getDiasDoMes());

        ArrayAdapter arrayAdapterDiaVencimentoCartao= ArrayAdapterHelper.fillSpinnerString(this, this.spnDiaVencimentoCartao);
        arrayAdapterDiaVencimentoCartao.addAll(DateUtils.getDiasDoMes());

         }



    private void carregaSpinnerConta() {

        RepositorioConta repositorioConta = new RepositorioConta(this);

        this.adapterConta = new AdapterConta(this, R.layout.item_conta_simples);
        this.adapterConta.addAll(repositorioConta.buscaTodos());
        this.spnContaAssociada.setAdapter(this.adapterConta);

    }


    private void preencheCampos() {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actCadCartaoCredito.PARAM_CARTAO_CREDITO))) {
            this.cartaoCredito = (CartaoCredito) bundle.getSerializable(actCadCartaoCredito.PARAM_CARTAO_CREDITO);

            this.edtNome.setText(this.cartaoCredito.getNome());

            this.edtValorLimiteCartaoCredito.setText(NumberUtis.getFormartCurrency(this.cartaoCredito.getValorLimite()));

            this.spnBandeiraCartaoCredito.setSelection(this.cartaoCredito.getNoBandeiraCartao());
            this.cbxExibirSomaResumo.setChecked(this.cartaoCredito.isExibiSomaResumo());

        } else
            this.cartaoCredito = new CartaoCredito();
    }


}
