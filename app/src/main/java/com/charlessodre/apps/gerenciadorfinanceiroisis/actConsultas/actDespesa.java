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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Despesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DecimalHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class actDespesa extends actBaseListas implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {

    //Objetos Tela
    private AdapterDespesa adpDespesa;
    private ListView lstDespesas;
    private RepositorioDespesa repositorioDespesa;
    private Despesa despesa;
    private FloatingActionButton fabAdd;
    private ImageButton btnEsquerda;
    private ImageButton btnDireita;
    private TextView txtNomeMes;
    private TextView txtValorTotalDespesaRod;

    //Atributos
    private boolean itemSelecionado = false;
    private ArrayList<Despesa> listaDespesas;
    private int addMes = 0;

    //Contantes
    public static final String PARAM_DESPESA = "DESPESA";
    public static final String PARAM_DESPESA_ANO_MES = "ANO_MES";


    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_despesa);
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
            case R.id.btnEsquerdaDespesa:
                super.setAddMesCalendar(-1);
                this.setNomeMes();
                this.atualizaListView();

                break;
            case R.id.btnDireitaDespesa:
                super.setAddMesCalendar(1);
                this.setNomeMes();

                this.atualizaListView();
                break;
            case R.id.btnAdicionarDespesa:

                Intent it = new Intent(this, actCadDespesa.class);
                startActivityForResult(it, 0);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // if(!this.itemSelecionado) {
        this.despesa = this.adpDespesa.getItem(position);

        Intent it = new Intent(this, actCadDespesa.class);
        it.putExtra(PARAM_DESPESA, this.despesa);

        startActivityForResult(it, 0);
        //  }else {
        //    this.itemSelecionado = false;

        //}

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // MessageBoxHelper.showInfo(this,"Long","Click");

        // this.despesa = this.adpDespesa.getItem(position);
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

        super.setMenuHome(this.getString(R.string.title_despesas));
        super.setColorStatusBar(R.color.corTelaDespesas);

        this.lstDespesas = (ListView) findViewById(R.id.lstDespesas);
        this.lstDespesas.setOnItemClickListener(this);
        this.lstDespesas.setOnItemLongClickListener(this);

        this.fabAdd = (FloatingActionButton) findViewById(R.id.btnAdicionarDespesa);
        this.fabAdd.setOnClickListener(this);

        this.btnDireita = (ImageButton) this.findViewById(R.id.btnDireitaDespesa);
        this.btnEsquerda = (ImageButton) this.findViewById(R.id.btnEsquerdaDespesa);
        this.btnDireita.setOnClickListener(this);
        this.btnEsquerda.setOnClickListener(this);

        this.txtNomeMes = (TextView) findViewById(R.id.txtNomeMesDespesa);
        this.txtValorTotalDespesaRod = (TextView) findViewById(R.id.txtValorTotalDespesaRod);

        this.repositorioDespesa = new RepositorioDespesa(this);

        this.adpDespesa = new AdapterDespesa(this, R.layout.item_despesa);

        this.fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.corTelaDespesas)));

        this.lstDespesas.setOnScrollListener(new AbsListView.OnScrollListener() {
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

        this.listaDespesas = this.repositorioDespesa.buscaPorAnoMes(anoMes);
        this.adpDespesa.clear();
        this.adpDespesa.addAll(this.listaDespesas);

        this.lstDespesas.setAdapter(this.adpDespesa);

        double valorTotal = this.repositorioDespesa.getValorTotalDespesas(anoMes,false);

        this.txtValorTotalDespesaRod.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol() + " " + DecimalHelper.getFormartCurrency(valorTotal));

    }

    private void setNomeMes() {

        this.txtNomeMes.setText(super.getNomeMesFormatado());
    }

    private void getParametrosRecebidos()
    {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actDespesa.PARAM_DESPESA_ANO_MES))) {

            this.addMes = bundle.getInt(actDespesa.PARAM_DESPESA_ANO_MES);

        }else
        {
            this.addMes = DateUtils.getCurrentYearAndMonth();
        }

    }

}
