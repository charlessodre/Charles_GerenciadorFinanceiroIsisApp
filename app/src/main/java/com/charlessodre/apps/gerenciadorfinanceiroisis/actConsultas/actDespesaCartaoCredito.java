package com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadDespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterDespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.DespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class actDespesaCartaoCredito extends actBaseListas implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {

    //Objetos Tela
    private AdapterDespesaCartaoCredito adpDespesaCartao;
    private ListView lstDespesasCartao;
    private RepositorioDespesaCartaoCredito repositorioDespesa;
    private DespesaCartaoCredito despesaCartao;
    private FloatingActionButton fabAdd;
    private ImageButton btnEsquerda;
    private ImageButton btnDireita;
    private TextView txtNomeMes;
    private TextView txtValorTotalDespesaRod;

    //Atributos
    private boolean itemSelecionado = false;
    private ArrayList<DespesaCartaoCredito> listaDespesasCartaoCredito;
    private int addMes = 0;
    private CartaoCredito cartaoCredito;

    //Contantes
    public static final String PARAM_CARTAO_CREDITO = "PARAM_CARTAO_CREDITO";
    public static final String PARAM_DESPESA_CARTAO_CREDITO = "DESPESA_CARTAO_CREDITO";
     public static final String PARAM_DESPESA_CARTAO_CREDITO_ANO_MES = "ANO_MES";


    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_despesa_cartao_credito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getParametrosRecebidos();
        this.inicializaObjetos();
        super.setAnoMesCalendar(this.addMes);
        this.atualizaListView();
        this.setNomeMes();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnEsquerdaDespesaCartao:
                super.setAddMesCalendar(-1);
                this.setNomeMes();
                this.atualizaListView();

                break;
            case R.id.btnDireitaDespesaCartao:
                super.setAddMesCalendar(1);
                this.setNomeMes();

                this.atualizaListView();
                break;
            case R.id.btnAdicionarDespesaCartaoCredito:

                Intent it = new Intent(this, actCadDespesaCartaoCredito.class);
                startActivityForResult(it, 0);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // if(!this.itemSelecionado) {
        this.despesaCartao = this.adpDespesaCartao.getItem(position);

        Intent it = new Intent(this, actCadDespesaCartaoCredito.class);
        it.putExtra(PARAM_DESPESA_CARTAO_CREDITO, this.despesaCartao);

        startActivityForResult(it, 0);
        //  }else {
        //    this.itemSelecionado = false;

        //}

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // MessageBoxHelper.showInfo(this,"Long","Click");

        // this.despesaCartao = this.adpDespesaCartao.getItem(position);
        // this.itemSelecionado = true;
        // view.setBackgroundColor(R.color.colorAccent);


        return false;
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

        super.setMenuHome(this.getString(R.string.title_despesas_cartao_credito));
        super.setColorStatusBar(R.color.corTelaDespesasCartaoCredito);

        this.lstDespesasCartao = (ListView) findViewById(R.id.lstDespesasCartao);
        this.lstDespesasCartao.setOnItemClickListener(this);
        this.lstDespesasCartao.setOnItemLongClickListener(this);

        this.fabAdd = (FloatingActionButton) findViewById(R.id.btnAdicionarDespesaCartaoCredito);
        this.fabAdd.setOnClickListener(this);

        this.btnDireita = (ImageButton) this.findViewById(R.id.btnDireitaDespesaCartao);
        this.btnEsquerda = (ImageButton) this.findViewById(R.id.btnEsquerdaDespesaCartao);
        this.btnDireita.setOnClickListener(this);
        this.btnEsquerda.setOnClickListener(this);

        this.txtNomeMes = (TextView) findViewById(R.id.txtNomeMesDespesaCartao);
        this.txtValorTotalDespesaRod = (TextView) findViewById(R.id.txtValorTotalDespesaRod);

        this.repositorioDespesa = new RepositorioDespesaCartaoCredito(this);

        this.adpDespesaCartao = new AdapterDespesaCartaoCredito(this, R.layout.item_despesa_cartao_credito);

        this.fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.corTelaDespesasCartaoCredito)));

        this.lstDespesasCartao.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i > 0) {
                    fabAdd.hide();

                } else {
                    fabAdd.show();
                }
            }
        });


    }

    private void atualizaListView() {

        int anoMes = DateUtils.getYearAndMonth(super.getCalendar().getTime());

        this.listaDespesasCartaoCredito = this.repositorioDespesa.get(this.cartaoCredito.getId(),anoMes,true);
        this.adpDespesaCartao.clear();
        this.adpDespesaCartao.addAll(this.listaDespesasCartaoCredito);

        this.lstDespesasCartao.setAdapter(this.adpDespesaCartao);

        double valorTotal = this.repositorioDespesa.getValorTotalDespesa(this.cartaoCredito.getId(),anoMes,true);

        this.txtValorTotalDespesaRod.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol() + " " + NumberUtis.getFormartCurrency(valorTotal));

    }

    private void setNomeMes() {

        this.txtNomeMes.setText(super.getNomeMesFormatado());
    }

    private void getParametrosRecebidos()
    {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actDespesaCartaoCredito.PARAM_DESPESA_CARTAO_CREDITO_ANO_MES))) {

            this.addMes = bundle.getInt(actDespesaCartaoCredito.PARAM_DESPESA_CARTAO_CREDITO_ANO_MES);

        }else
        {
            this.addMes = DateUtils.getCurrentYearAndMonth();
        }

        if ((bundle != null) && (bundle.containsKey(actDespesaCartaoCredito.PARAM_CARTAO_CREDITO))) {

            this.cartaoCredito =(CartaoCredito) bundle.getSerializable(actDespesaCartaoCredito.PARAM_CARTAO_CREDITO);

        }

    }

}
