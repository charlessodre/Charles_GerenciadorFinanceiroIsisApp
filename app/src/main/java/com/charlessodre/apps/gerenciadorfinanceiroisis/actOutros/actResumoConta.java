package com.charlessodre.apps.gerenciadorfinanceiroisis.actOutros;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actBaseListas;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Despesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Transferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.ActionBarHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;

import java.text.NumberFormat;
import java.util.Locale;

public class actResumoConta extends actBaseListas implements View.OnClickListener {

//Objetos Tela


    private TextView lblTipoConta;
    private TextView lblReceitasQtdTotal;
    private TextView lblReceitasValorTotal;
    private TextView lblReceitasQtdConfirmadas;
    private TextView lblReceitaValorConfirmadas;

    private TextView lblDespesasQtdTotal;
    private TextView lblDespesasValorTotal;
    private TextView lblDespesasQtdConfirmadas;
    private TextView lblDespesasValorConfirmadas;

    private TextView lblTransferenciasEntradaQtd;
    private TextView lblTransferenciasSaidaQtd;
    private TextView lblTransferenciasValorEntrada;
    private TextView lblTransferenciasValorSaida;

    private ImageView imgTipoConta;
    private TextView txtNomeMes;

    private ImageButton btnEsquerda;
    private ImageButton btnDireita;

    //Atributos
    private Receita receita;
    private Conta conta;
    private Despesa despesa;
    private Transferencia transferencia;
    private int addMes = 0;


    private RepositorioTransferencia repositorioTransferencia;
    private RepositorioReceita repositorioReceita;
    private RepositorioConta repositorioConta;
    private RepositorioDespesa repositorioDespesa;

    //Constantes
    public static final String PARAM_CONTA = "CONTA";
    public static final String PARAM_CONTA_ANO_MES = "ANO_MES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_resumo_conta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getParametrosRecebidos();
        this.inicializaObjetos();
        super.setAnoMesCalendar(this.addMes);
        this.setNomeMes();
        this.preencheCampos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnEsquerdaConta:
                super.setAddMesCalendar(-1);
                this.setNomeMes();
                this.preencheCampos();

                break;
            case R.id.btnDireitaConta:
                super.setAddMesCalendar(1);
                this.setNomeMes();
                this.preencheCampos();
                break;
            case R.id.btnAdicionarConta:

                Intent it = new Intent(this, actCadConta.class);
                startActivityForResult(it, 0);
                break;
        }
    }


    //Métodos
    protected void inicializaObjetos() {


        this.lblTipoConta = (TextView) findViewById(R.id.lblTipoConta);
        this.lblReceitasQtdTotal = (TextView) findViewById(R.id.lblReceitasQtdTotal);
        this.lblReceitasValorTotal = (TextView) findViewById(R.id.lblReceitasValorTotal);
        this.lblReceitasQtdConfirmadas = (TextView) findViewById(R.id.lblReceitasQtdTotal);
        this.lblReceitaValorConfirmadas = (TextView) findViewById(R.id.lblReceitasValorTotal);

        this.lblDespesasQtdTotal = (TextView) findViewById(R.id.lblDespesasQtdTotal);
        this.lblDespesasValorTotal = (TextView) findViewById(R.id.lblDespesasValorTotal);
        this.lblDespesasQtdConfirmadas = (TextView) findViewById(R.id.lblDespesasQtdTotal);
        this.lblDespesasValorConfirmadas = (TextView) findViewById(R.id.lblDespesasValorTotal);


        this.lblTransferenciasEntradaQtd = (TextView) findViewById(R.id.lblTransferenciasEntradaQtd);
        this.lblTransferenciasSaidaQtd = (TextView) findViewById(R.id.lblTransferenciasSaidaQtd);

        this.lblTransferenciasValorEntrada = (TextView) findViewById(R.id.lblTransferenciasValorEntrada);
        this.lblTransferenciasValorSaida = (TextView) findViewById(R.id.lblTransferenciasValorSaida);

        this.imgTipoConta = (ImageView) findViewById(R.id.imgTipoConta);
        this.txtNomeMes = (TextView) findViewById(R.id.txtNomeMesConta);

        this.btnDireita = (ImageButton) this.findViewById(R.id.btnDireitaConta);
        this.btnEsquerda = (ImageButton) this.findViewById(R.id.btnEsquerdaConta);
        this.btnDireita.setOnClickListener(this);
        this.btnEsquerda.setOnClickListener(this);

        this.repositorioDespesa = new RepositorioDespesa(this);
        this.repositorioReceita = new RepositorioReceita(this);
        this.repositorioTransferencia = new RepositorioTransferencia(this);

        super.setMenuHome(this.getString(R.string.title_contas));
        super.setColorStatusBar(R.color.corTelaContas);

    }

    private void setNomeMes() {
        this.txtNomeMes.setText(super.getNomeMesFormatado());

    }


    private void preencheCampos() {

        int receitasQtdTotal = 0;
        int receitasQtdConfirmadas = 0;

        Double receitaValorTotal = 0.0;
        Double receitaValorConfirmadas = 0.0;

        int despesasQtdTotal = 0;
        int despesasQtdConfirmadas = 0;

        Double despesasValorTotal = 0.0;
        Double despesasValorConfirmadas = 0.0;

        int transferenciaQtdTotal = 0;
        Double transferenciaValorTotal = 0.0;

        int transferenciaEntradaQtd = 0;
        Double transferenciaEntradaValor = 0.0;

        int transferenciaSaidaQtd = 0;
        Double transferenciaSaidaValor = 0.0;

        String symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

        despesasQtdTotal = this.repositorioDespesa.getQtdDespesaContaMes(this.conta.getId(), super.getAnoMes(), false);
        despesasValorTotal = this.repositorioDespesa.getValorTotalDespesasContaMes(this.conta.getId(), super.getAnoMes(), false);

        despesasQtdConfirmadas = this.repositorioDespesa.getQtdDespesaContaMes(this.conta.getId(), super.getAnoMes(), true);
        despesasValorConfirmadas = this.repositorioDespesa.getValorTotalDespesasContaMes(this.conta.getId(), super.getAnoMes(), true);

        receitasQtdTotal = this.repositorioReceita.getQtdReceitaContaMes(this.conta.getId(), super.getAnoMes(), false);
        receitaValorTotal = this.repositorioReceita.getValorTotalRecebidoContaMes(this.conta.getId(), super.getAnoMes(), false);

        receitasQtdConfirmadas = this.repositorioReceita.getQtdReceitaContaMes(this.conta.getId(), super.getAnoMes(), true);
        receitaValorConfirmadas = this.repositorioReceita.getValorTotalRecebidoContaMes(this.conta.getId(), super.getAnoMes(), true);

        transferenciaEntradaQtd = this.repositorioTransferencia.getQtdTransferenciaEntradaContaMes(this.conta.getId(), super.getAnoMes());
        transferenciaSaidaQtd = this.repositorioTransferencia.getQtdTransferenciaSaidaContaMes(this.conta.getId(), super.getAnoMes());

        //transferenciaQtdTotal = transferenciaEntradaQtd + transferenciaSaidaQtd;

        transferenciaSaidaValor = this.repositorioTransferencia.getValorTransferenciaSaidaContaMes(this.conta.getId(), super.getAnoMes());
        transferenciaEntradaValor = this.repositorioTransferencia.getValorTransferenciaEntradaContaMes(this.conta.getId(), super.getAnoMes());

        //  transferenciaValorTotal = transferenciaEntradaValor + transferenciaSaidaValor;

        this.imgTipoConta.setImageResource(Conta.getImagemTipoConta(conta.getCdTipoConta()));
        this.lblTipoConta.setText(Conta.getTipoContas(this).get(this.conta.getCdTipoConta()));

        this.lblReceitasQtdTotal.setText(String.valueOf(receitasQtdTotal));
        this.lblReceitasQtdConfirmadas.setText(String.valueOf(receitasQtdConfirmadas));
        this.lblReceitasValorTotal.setText(symbol + " " + NumberUtis.getFormartCurrency(receitaValorTotal));

        this.lblReceitaValorConfirmadas.setText(symbol + " " + NumberUtis.getFormartCurrency(receitaValorConfirmadas));

        this.lblDespesasQtdTotal.setText(String.valueOf(despesasQtdTotal));
        this.lblDespesasValorTotal.setText(symbol + " " + NumberUtis.getFormartCurrency(despesasValorTotal));
        this.lblDespesasQtdConfirmadas.setText(String.valueOf(despesasQtdConfirmadas));
        this.lblDespesasValorConfirmadas.setText(symbol + " " + NumberUtis.getFormartCurrency(despesasValorConfirmadas));

        this.lblTransferenciasEntradaQtd.setText(String.valueOf(transferenciaEntradaQtd));
        this.lblTransferenciasSaidaQtd.setText(String.valueOf(transferenciaSaidaQtd));

        this.lblTransferenciasValorEntrada.setText(symbol + " " + NumberUtis.getFormartCurrency(transferenciaEntradaValor));
        this.lblTransferenciasValorSaida.setText(symbol + " " + NumberUtis.getFormartCurrency(transferenciaSaidaValor));

    }

    private void getParametrosRecebidos() {

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(PARAM_CONTA))) {
            this.conta = (Conta) bundle.getSerializable(PARAM_CONTA);

        } else {

            this.conta = new Conta();
        }

        if ((bundle != null) && (bundle.containsKey(PARAM_CONTA_ANO_MES))) {

            this.addMes = bundle.getInt(PARAM_CONTA_ANO_MES);

        } else {
            this.addMes = DateUtils.getCurrentYearAndMonth();
        }

    }

}
