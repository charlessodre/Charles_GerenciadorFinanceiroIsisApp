package com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterMovimentos;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ColorHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Despesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Transferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgBotaoAddTransacao;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.FragmentHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class actMovimentos extends actBaseListas implements AdapterView.OnItemClickListener, View.OnClickListener {


    //Objetos Tela
    private ListView lstMovimentos;
    private ImageButton btnEsquerda;
    private ImageButton btnDireita;
    private TextView txtNomeMes;
    private LinearLayout lnlfragBarraNavegacaoMovimento;
    private LinearLayout lnlrodapeMovimento;
    private TextView txtValorTotalMovimentosRod;
    private TextView txtTextoSaldo;
    private LinearLayout frag_container_botao_add;

    //Atributos
    private AdapterMovimentos adpMovimento;
    private Receita receita;
    private Conta conta;
    private Despesa despesa;
    private Transferencia transferencia;
    private int addMes = 0;
    private int anoMesAtual = DateUtils.getCurrentYearAndMonth();

    private frgBotaoAddTransacao fragmentoBotaoAdd;


    private RepositorioTransferencia repositorioTransferencia;
    private RepositorioReceita repositorioReceita;
    private RepositorioDespesa repositorioDespesa;
    private RepositorioConta repositorioConta;


    //Constantes
    public static final String PARAM_CONTA = "CONTA";
    public static final String PARAM_ANO_MES = "ANO_MES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_movimentos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getParametrosRecebidos();
        this.inicializaObjetos();

        super.setAnoMesCalendar(this.addMes);
        this.atualizaListView();
        this.setNomeMes();
        this.adicionaFragBotaoAdd();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnEsquerdaMov:
                super.setAddMesCalendar(-1);
                this.setNomeMes();
                this.setTextoRodapeSaldo();
                this.atualizaListView();

                break;
            case R.id.btnDireitaMov:
                super.setAddMesCalendar(1);
                this.setNomeMes();
                this.setTextoRodapeSaldo();
                this.atualizaListView();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Object obj = this.adpMovimento.getItem(position);
        Intent it = null;

        if (obj instanceof Despesa) {
            it = new Intent(this, actCadDespesa.class);
            it.putExtra(actCadDespesa.PARAM_DESPESA, (Despesa) obj);

        } else if (obj instanceof Receita) {
            it = new Intent(this, actCadReceita.class);
            it.putExtra(actCadReceita.PARAM_RECEITA, (Receita) obj);


        } else if (obj instanceof Transferencia) {
            it = new Intent(this, actCadTransferencia.class);
            it.putExtra(actCadTransferencia.PARAM_TRANSFERENCIA, (Transferencia) obj);
        }

        startActivityForResult(it, 0);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.atualizaListView();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        return true;
    }

    //Métodos
    @Override
    protected void inicializaObjetos() {

        this.lnlfragBarraNavegacaoMovimento = (LinearLayout) findViewById(R.id.lnlfragBarraNavegacaoMovimento);
        this.lnlrodapeMovimento = (LinearLayout) findViewById(R.id.rodapeMovimento);

        String titulo = this.getString(R.string.title_movimentos);
        int corTela = R.color.corTelaContas;

        if (this.conta != null) {
            titulo = titulo + " - " + this.conta.getNome();
            corTela = this.conta.getNoCor();
        }

        super.setMenuHome(titulo);

        super.setColorStatusBar(corTela);
        super.setColorActionBar(corTela);
        this.lnlfragBarraNavegacaoMovimento.setBackgroundColor(ColorHelper.getColor(this, corTela));
        this.lnlrodapeMovimento.setBackgroundColor(ColorHelper.getColor(this, corTela));

        this.lstMovimentos = (ListView) findViewById(R.id.lstMovimento);
        this.lstMovimentos.setOnItemClickListener(this);

        this.btnDireita = (ImageButton) this.findViewById(R.id.btnDireitaMov);
        this.btnEsquerda = (ImageButton) this.findViewById(R.id.btnEsquerdaMov);
        this.btnDireita.setOnClickListener(this);
        this.btnEsquerda.setOnClickListener(this);

        this.txtNomeMes = (TextView) findViewById(R.id.txtNomeMesMov);

        this.txtValorTotalMovimentosRod = (TextView) findViewById(R.id.txtValorTotalMovimentosRod);
        this.txtTextoSaldo = (TextView) findViewById(R.id.txtTextoSaldo);

        this.frag_container_botao_add = (LinearLayout) findViewById(R.id.frag_container_botao_add);


        this.lstMovimentos.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i>0) {
                    frag_container_botao_add.setVisibility(View.GONE);


                } else {
                    frag_container_botao_add.setVisibility(View.VISIBLE);
                }
            }
        });

        this.repositorioConta = new RepositorioConta(this);
        this.repositorioDespesa = new RepositorioDespesa(this);
        this.repositorioReceita = new RepositorioReceita(this);
        this.repositorioTransferencia = new RepositorioTransferencia(this);

        this.adpMovimento = new AdapterMovimentos(this, R.layout.item_movimento);


    }

    private void atualizaListView() {

        int anoMes = DateUtils.getYearAndMonth(super.getCalendar().getTime());
        List<Object> movimentos;

        this.adpMovimento.clear();

        if (this.conta != null) {
            movimentos = this.getMovimentoConta();
        }
        else
            movimentos = this.getTodosMovimento();


        this.adpMovimento.addAll(movimentos);

        this.lstMovimentos.setAdapter(this.adpMovimento);

        Double valorTotal = 0.0;

        if (this.conta == null)
            valorTotal = this.repositorioConta.getSaldoAtual(super.getAnoMes(),false);
        else
            valorTotal = this.repositorioConta.getSaldoAtual(this.conta.getId(), super.getAnoMes(),false);

        this.txtValorTotalMovimentosRod.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol() + " " + NumberUtis.getFormartCurrency(valorTotal));

    }

    private void setNomeMes() {
        this.txtNomeMes.setText(super.getNomeMesFormatado());

    }

    private void setTextoRodapeSaldo(){

        if( super.getAnoMes() > this.anoMesAtual)
            this.txtTextoSaldo.setText(this.getResources().getString(R.string.lblSaldoPrevisto));
        else
            this.txtTextoSaldo.setText(this.getResources().getString(R.string.lblSaldoAtual));
    }

    private void getParametrosRecebidos() {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actMovimentos.PARAM_CONTA))) {

            this.conta = (Conta) bundle.getSerializable(actMovimentos.PARAM_CONTA);

        }

        if ((bundle != null) && (bundle.containsKey(actMovimentos.PARAM_ANO_MES))) {

            this.addMes = bundle.getInt(actMovimentos.PARAM_ANO_MES);

        } else {
            this.addMes = DateUtils.getCurrentYearAndMonth();
        }

    }

    private List<Object> getMovimentoConta() {

        List<Object> movimentos = new ArrayList<Object>();

        ArrayList<Despesa> despesas = this.repositorioDespesa.buscaPorContaAnoMes(this.conta.getId(), super.getAnoMes());
        ArrayList<Receita> receitas = this.repositorioReceita.buscaPorContaAnoMes(this.conta.getId(), super.getAnoMes());
        ArrayList<Transferencia> transferencias = this.repositorioTransferencia.buscaPorContaAnoMes(this.conta.getId(), super.getAnoMes());

        movimentos.addAll(despesas);
        movimentos.addAll(receitas);
        movimentos.addAll(transferencias);

        return movimentos;
    }

    private List<Object> getTodosMovimento() {

        List<Object> movimentos = new ArrayList<Object>();

        ArrayList<Despesa> despesas = this.repositorioDespesa.buscaPorAnoMes(super.getAnoMes());
        ArrayList<Receita> receitas = this.repositorioReceita.buscaPorAnoMes(super.getAnoMes());
        ArrayList<Transferencia> transferencias = this.repositorioTransferencia.buscaPorAnoMes(super.getAnoMes());

        movimentos.addAll(despesas);
        movimentos.addAll(receitas);
        movimentos.addAll(transferencias);

        return movimentos;
    }


    private void adicionaFragBotaoAdd() {


        this.fragmentoBotaoAdd = (frgBotaoAddTransacao) FragmentHelper.findFragmentByTag(getSupportFragmentManager(), fragmentoBotaoAdd.NOME_FRAGMENTO);

        //Verifica se o fragmento já foi adicionado.
        if (this.fragmentoBotaoAdd == null) {
            this.fragmentoBotaoAdd = fragmentoBotaoAdd.newInstance();

            Bundle argument = new Bundle();

            FragmentHelper.addFragment(getSupportFragmentManager(), this.fragmentoBotaoAdd, fragmentoBotaoAdd.NOME_FRAGMENTO, R.id.frag_container_botao_add);
        }
    }

}
