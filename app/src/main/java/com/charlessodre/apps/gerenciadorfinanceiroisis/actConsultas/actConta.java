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

import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actOutros.actResumoConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;

import java.text.NumberFormat;
import java.util.Locale;

public class actConta extends actBaseListas implements AdapterView.OnItemClickListener, View.OnClickListener {

    //Objetos Tela
    private ListView lstContas;
    private ImageButton btnEsquerda;
    private ImageButton btnDireita;
    private TextView txtNomeMes;
    private TextView txtValorTotalContasRod;
    private FloatingActionButton fabAdd;

    //Atributos
    private AdapterConta adpConta;
    private RepositorioConta repositorioConta;
    private Conta conta;
    private int addMes = 0;


    //Constantes
    public static final String PARAM_CONTA_ANO_MES = "ANO_MES";

    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_conta);

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
            case R.id.btnEsquerdaConta:
                super.setAddMesCalendar(-1);
                this.setNomeMes();
                this.atualizaListView();


                break;
            case R.id.btnDireitaConta:
                super.setAddMesCalendar(1);
                this.setNomeMes();
                this.atualizaListView();

                break;
            case R.id.btnAdicionarConta:

                Intent it = new Intent(this, actCadConta.class);
                startActivityForResult(it, 0);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.conta = this.adpConta.getItem(position);

        Intent it = new Intent(this, actResumoConta.class);
        it.putExtra(actResumoConta.PARAM_CONTA, this.conta);
        it.putExtra(actResumoConta.PARAM_CONTA_ANO_MES, super.getAnoMes());

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

        super.setMenuHome(this.getString(R.string.title_contas));
        super.setColorStatusBar(R.color.corTelaContas);

        this.lstContas = (ListView) findViewById(R.id.lstContas);
        this.lstContas.setOnItemClickListener(this);

        this.fabAdd = (FloatingActionButton) findViewById(R.id.btnAdicionarConta);
        this.fabAdd.setOnClickListener(this);

        this.btnDireita = (ImageButton) this.findViewById(R.id.btnDireitaConta);
        this.btnEsquerda = (ImageButton) this.findViewById(R.id.btnEsquerdaConta);
        this.btnDireita.setOnClickListener(this);
        this.btnEsquerda.setOnClickListener(this);

        this.txtNomeMes = (TextView) findViewById(R.id.txtNomeMesConta);
        this.txtValorTotalContasRod = (TextView) findViewById(R.id.txtValorTotalContasRod);


        this.repositorioConta = new RepositorioConta(this);

        this.adpConta = new AdapterConta(this, R.layout.item_conta);

        this.fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.corTelaContas)));

        this.lstContas.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i>0) {
                    fabAdd.hide();

                } else {
                    fabAdd.show();
                }
            }
        });

    }

    private void atualizaListView() {

        int anoMes = DateUtils.getYearAndMonth(super.getCalendar().getTime());

        this.adpConta.setData(super.getCalendar().getTime());
        this.adpConta.clear();
        this.adpConta.addAll(this.repositorioConta.getSaldoContaAnoMes(anoMes));

        this.lstContas.setAdapter(this.adpConta);

        this.txtValorTotalContasRod.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol() + " " + NumberUtis.getFormartCurrency(this.repositorioConta.getValorTotal(anoMes,true)));
    }

    private void setNomeMes() {
               this.txtNomeMes.setText(super.getNomeMesFormatado());

    }

    private void getParametrosRecebidos()
    {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actConta.PARAM_CONTA_ANO_MES))) {

            this.addMes = bundle.getInt(actConta.PARAM_CONTA_ANO_MES);

        }else
        {
            this.addMes = DateUtils.getCurrentYearAndMonth();
        }

    }


}
