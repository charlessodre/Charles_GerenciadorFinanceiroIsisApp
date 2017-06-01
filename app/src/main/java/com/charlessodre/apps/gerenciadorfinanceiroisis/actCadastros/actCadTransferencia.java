package com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateListenerShow;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.MessageBoxHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.TextWatcherPay;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ToastHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Transferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.StringUtils;

import java.util.Date;

public class actCadTransferencia extends actBaseCadastros implements frgConfirmacaoDialog.onDialogClick {

    //Objetos Tela
    private EditText edtDataTransferencia;
    private EditText edtValorTransferencia;
    private Spinner spnContaOrigem;
    private Spinner spnContaDestino;
    private TextWatcherPay textWatcher;
    private DateListenerShow dateListenerShow;

    //Atributos
    private Transferencia transferencia;
    private RepositorioTransferencia repositorioTransferencia;
    private AdapterConta adapterContaOrigem;
    private AdapterConta adapterContaDestino;
    private Date dataTransferencia;

    //Contantes
    public static final String PARAM_TRANSFERENCIA = "TRANSFERENCIA";


    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_transferencia);

        inicializaObjetos();
        carregaSpinnerContas();
        preencheDados();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);

        if (this.transferencia.getId() == 0) {
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
                    this.salva();

                    ToastHelper.showToastShort(this, this.getString(R.string.msg_salvar_sucesso));

                    this.finish();
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
    public void onDialogClick(frgConfirmacaoDialog dialog) {
        this.exclui();
    }


    //Métodos
    @Override
    protected void inicializaObjetos() {

        this.edtDataTransferencia = (EditText) findViewById(R.id.edtDataTransferencia);
        this.spnContaOrigem = (Spinner) findViewById(R.id.spnContaOrigem);
        this.spnContaDestino = (Spinner) findViewById(R.id.spnContaDestino);
        this.edtDataTransferencia = (EditText) findViewById(R.id.edtDataTransferencia);
        this.edtValorTransferencia = (EditText) findViewById(R.id.edtValorTransferencia);

        this.textWatcher = new TextWatcherPay(this.edtValorTransferencia, "%.2f");
        this.edtValorTransferencia.addTextChangedListener(this.textWatcher);

        this.dateListenerShow = new DateListenerShow(this, this.edtDataTransferencia, false);

        this.edtDataTransferencia.setText(DateUtils.getCurrentDateShort());


        ActionBarHelper.menuCancel(getSupportActionBar(), this.getString(R.string.lblTransferencias));

        int corTela = ColorHelper.getColor(this, R.color.corTelaTransferencias);

        ActionBarHelper.setBackgroundColor(getSupportActionBar(), corTela);

        ActionBarHelper.setStatusBarColor(this.getWindow(), corTela);

    }

    private void carregaSpinnerContas() {
        RepositorioConta repositorioConta = new RepositorioConta(this);

        this.adapterContaOrigem = new AdapterConta(this, R.layout.item_conta_simples);
        this.adapterContaDestino = new AdapterConta(this, R.layout.item_conta_simples);

        this.adapterContaOrigem.addAll(repositorioConta.buscaTodos());
        this.adapterContaDestino.addAll(repositorioConta.buscaTodos());

        this.spnContaOrigem.setAdapter(this.adapterContaOrigem);
        this.spnContaDestino.setAdapter(this.adapterContaDestino);

        if (this.adapterContaDestino.getCount() > 1)
            this.spnContaDestino.setSelection(1);
    }

    private void preencheDados() {

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actCadTransferencia.PARAM_TRANSFERENCIA))) {
            this.transferencia = (Transferencia) bundle.getSerializable(actCadTransferencia.PARAM_TRANSFERENCIA);

            if (transferencia.getId() > 1) {

                this.spnContaOrigem.setSelection(this.adapterContaOrigem.getIndexFromElement(this.transferencia.getContaOrigem().getId()));
                this.spnContaDestino.setSelection(this.adapterContaDestino.getIndexFromElement(this.transferencia.getContaDestino().getId()));

                this.spnContaOrigem.setEnabled(false);
                this.spnContaDestino.setEnabled(false);
            }

            this.edtValorTransferencia.setText(NumberUtis.getFormartCurrency(this.transferencia.getValor()));
            this.edtDataTransferencia.setText(DateUtils.dateToString(this.transferencia.getDataTransferencia()));


        } else {
            this.transferencia = new Transferencia();
        }


    }

    private boolean validaCamposTela() {

        boolean retorno = true;

        if(this.spnContaOrigem.getCount() < 1)
        {
            MessageBoxHelper.show(this,"", this.getString(R.string.msg_cadastrar_conta));
            retorno = false;
        }

        if (StringUtils.isNullOrEmpty(this.edtDataTransferencia.getText().toString())) {
            this.edtDataTransferencia.setError(this.getString(R.string.msg_preenchimento_obrigatorio));

            retorno = false;
        }

        if (this.textWatcher.getValueWithoutMask() <= 0) {
            this.edtValorTransferencia.setError(this.getString(R.string.msg_valor_maior_zero));

            retorno = false;
        }

        if (this.spnContaOrigem.getSelectedItemPosition() == this.spnContaDestino.getSelectedItemPosition()) {

            ToastHelper.showToastLong(this, this.getString(R.string.msg_salvar_transferencia));
            retorno = false;
        }

        return retorno;

    }


    private void exclui() {

        try {
            this.repositorioTransferencia = new RepositorioTransferencia(this);

            this.repositorioTransferencia.exclui(this.transferencia);

            ToastHelper.showToastShort(this, this.getString(R.string.msg_excluir_sucesso));

            this.finish();

        } catch (Exception ex) {

            MessageBoxHelper.showAlert(this, this.getResources().getString(R.string.lblErro), ex.getMessage());
        }
    }

    private void salva() {


        this.repositorioTransferencia = new RepositorioTransferencia(this);

        this.transferencia.setDataInclusao(DateUtils.getCurrentDatetime());
        this.dataTransferencia = this.dateListenerShow.getDateListenerSelect().getDate();

        if (this.dataTransferencia == null)
            this.dataTransferencia = DateUtils.getCurrentDatetime();

        this.transferencia.setDataTransferencia(this.dataTransferencia);
        this.transferencia.setAnoMes(DateUtils.getYearAndMonth(this.dataTransferencia));

        this.transferencia.setValor(this.textWatcher.getValueWithoutMask());

        Conta contaOrigem = this.adapterContaOrigem.getItem(this.spnContaOrigem.getSelectedItemPosition());
        Conta contaDestino = this.adapterContaDestino.getItem(this.spnContaDestino.getSelectedItemPosition());

        this.transferencia.setContaDestino(contaDestino);
        this.transferencia.setContaOrigem(contaOrigem);

        if (this.transferencia.getId() == 0)
            this.repositorioTransferencia.insere(this.transferencia);
        else {
            this.transferencia.setDataAlteracao(DateUtils.getCurrentDatetime());
            this.repositorioTransferencia.altera(this.transferencia);
        }

    }

}
