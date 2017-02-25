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
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmaExclusaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ArrayAdapterHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.MessageBoxHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.TextWatcherPay;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DecimalHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.StringUtils;

public class actCadConta extends actBaseCadastros implements frgConfirmaExclusaoDialog.onDialogClick, frgConfirmacaoDialog.onDialogClick {

    //Atributos
    private EditText edtNome;
    private Spinner spnTipoConta;
    private EditText edtSaldo;
    private CheckBox cbxExibirSomaResumo;
    private Conta conta;
    private TextWatcherPay textWatcher;
    private RepositorioConta repositorioConta;
    private boolean exclusao;

    //Contantes
    public static final String PARAM_CONTA = "CONTA";

    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_conta);

        inicializaObjetos();
        carregaSpinnerTipoConta();
        preencheCampos();
        super.adicionaFragOrdemExibicaoCor(this.conta.getOrdemExibicao(), this.conta.getNoCor(), R.id.frag_container_cad_conta);

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

        this.excluiConta();
    }

    //Métodos
    @Override
    protected void inicializaObjetos() {
        this.edtNome = (EditText) findViewById(R.id.edtNomeConta);

        this.spnTipoConta = (Spinner) findViewById(R.id.spnTipoConta);
        this.edtSaldo = (EditText) findViewById(R.id.edtSaldoInicial);

        this.cbxExibirSomaResumo = (CheckBox) findViewById(R.id.cbxExibirSomaResumo);

        textWatcher = new TextWatcherPay(this.edtSaldo, "%.2f");

        edtSaldo.addTextChangedListener(textWatcher);

        ActionBarHelper.menuCancel(getSupportActionBar(), this.getString(R.string.lblConta));

        int corTela = ColorHelper.getColor(this, R.color.corTelaContas);

        ActionBarHelper.setBackgroundColor(getSupportActionBar(), corTela);

        ActionBarHelper.setStatusBarColor(this.getWindow(), corTela);


    }

    private void salva() {

        this.conta.setNome(this.edtNome.getText().toString());
        this.conta.setExibir(this.cbxExibirSomaResumo.isChecked());
        this.conta.setAtivo(true);
        this.conta.setCdTipoConta(this.spnTipoConta.getSelectedItemPosition());
        this.conta.setValorSaldo(textWatcher.getValueWithoutMask());

        this.conta.setOrdemExibicao(super.getNumOrdemExibicao());
        this.conta.setNoCor(super.getNumCor());

        this.repositorioConta = new RepositorioConta(this);

        if (this.conta.getId() == 0) {

            this.conta.setDataInclusao(DateUtils.getCurrentDatetime());
            this.conta.setAnoMes(DateUtils.getCurrentYearAndMonth());
            repositorioConta.insere(this.conta);
        } else {
            this.conta.setDataAlteracao(DateUtils.getCurrentDatetime());
            repositorioConta.altera(this.conta);
        }

    }

    private void verificaDependentesExclusao() {

        try {
            RepositorioReceita repositorioReceita = new RepositorioReceita(this);

            int qtdeReceitas = repositorioReceita.getQtdReceitaConta(this.conta.getId());
            boolean mostraMsgDialog = false;

            if (qtdeReceitas >= 1) {
                mostraMsgDialog = true;

            } else {

                RepositorioDespesa repositorioDespesa = new RepositorioDespesa(this);

                int qtdeDespesas = repositorioDespesa.getQtdDespesaConta(this.conta.getId());

                if (qtdeDespesas >= 1) {
                    mostraMsgDialog = true;

                } else {
                    int qtdeTransferencias = 0;

                    RepositorioTransferencia repositorioTransferencia = new RepositorioTransferencia(this);

                    qtdeTransferencias = repositorioTransferencia.getQtdTransferenciaConta(this.conta.getId());

                    if (qtdeTransferencias >= 1) {
                        mostraMsgDialog = true;
                    }
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

    private void excluiConta() {

        try {
            this.repositorioConta = new RepositorioConta(this);
            this.repositorioConta.exclui(this.conta);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));
            this.finish();
        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private void excluiDependencias() {

        try {
            this.repositorioConta = new RepositorioConta(this);
            this.repositorioConta.excluiComDependentes(this.conta);

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

        return retorno;

    }

    private void carregaSpinnerTipoConta() {

        ArrayAdapter arrayAdapter = ArrayAdapterHelper.fillSpinnerString(this, this.spnTipoConta);
        arrayAdapter.addAll(Conta.getTipoContas(this));

    }

    private void preencheCampos() {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actCadConta.PARAM_CONTA))) {
            this.conta = (Conta) bundle.getSerializable(actCadConta.PARAM_CONTA);

            this.edtNome.setText(this.conta.getNome());

            this.edtSaldo.setText(DecimalHelper.getFormartCurrency(this.conta.getValorSaldo()));

            this.spnTipoConta.setSelection(this.conta.getCdTipoConta());
            this.cbxExibirSomaResumo.setChecked(this.conta.isExibir());

        } else
            this.conta = new Conta();
    }


}
