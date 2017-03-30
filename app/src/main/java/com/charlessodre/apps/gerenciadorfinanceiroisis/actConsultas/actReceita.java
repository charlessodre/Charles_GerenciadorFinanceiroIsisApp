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

import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class actReceita extends actBaseListas implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {

    //Objetos Tela
    private AdapterReceita adpReceita;
    private ListView lstReceitas;
    private RepositorioReceita repositorioReceita;
    private Receita receita;
    private FloatingActionButton fabAdd;
    private ImageButton btnEsquerda;
    private ImageButton btnDireita;
    private TextView txtNomeMes;
    private TextView txtValorTotalReceitaRod;

    //Atributos
    private boolean itemSelecionado = false;
    private ArrayList<Receita> listaReceitas;
    private int addMes = 0;


    //Contantes
    public static final String PARAM_RECEITA_OBJ = "RECEITA";
    public static final String PARAM_RECEITA_ANO_MES = "ANO_MES";


    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_receita);
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
            case R.id.btnEsquerdaReceita:
                super.setAddMesCalendar(-1);
                this.setNomeMes();
                this.atualizaListView();

                break;
            case R.id.btnDireitaReceita:
                super.setAddMesCalendar(1);
                this.setNomeMes();

                this.atualizaListView();
                break;
            case R.id.btnAdicionarReceita:

                Intent it = new Intent(this, actCadReceita.class);
                startActivityForResult(it, 0);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // if(!this.itemSelecionado) {
        this.receita = this.adpReceita.getItem(position);

        Intent it = new Intent(this, actCadReceita.class);
        it.putExtra(PARAM_RECEITA_OBJ, this.receita);

        startActivityForResult(it, 0);
        //  }else {
        //    this.itemSelecionado = false;

        //}

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // MessageBoxHelper.showInfo(this,"Long","Click");

        // this.receita = this.adpReceita.getItem(position);
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

        super.setMenuHome(this.getString(R.string.title_receitas));
        super.setColorStatusBar(R.color.corTelaReceitas);

        this.lstReceitas = (ListView) findViewById(R.id.lstReceitas);
        this.lstReceitas.setOnItemClickListener(this);
        this.lstReceitas.setOnItemLongClickListener(this);

        this.fabAdd = (FloatingActionButton) findViewById(R.id.btnAdicionarReceita);
        this.fabAdd.setOnClickListener(this);

        this.btnDireita = (ImageButton) this.findViewById(R.id.btnDireitaReceita);
        this.btnEsquerda = (ImageButton) this.findViewById(R.id.btnEsquerdaReceita);
        this.btnDireita.setOnClickListener(this);
        this.btnEsquerda.setOnClickListener(this);

        this.txtNomeMes = (TextView) findViewById(R.id.txtNomeMesReceita);
        this.txtValorTotalReceitaRod = (TextView) findViewById(R.id.txtValorTotalReceitaRod);

        this.repositorioReceita = new RepositorioReceita(this);

        this.adpReceita = new AdapterReceita(this, R.layout.item_receita);

        this.fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.corTelaReceitas)));

        this.lstReceitas.setOnScrollListener(new AbsListView.OnScrollListener() {
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

        this.listaReceitas = this.repositorioReceita.buscaPorAnoMes(anoMes);
        this.adpReceita.clear();
        this.adpReceita.addAll(this.listaReceitas);

        this.lstReceitas.setAdapter(this.adpReceita);

        double valorTotal = this.repositorioReceita.getValorTotalRecebido(anoMes,false);

        this.txtValorTotalReceitaRod.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol() + " " + NumberUtis.getFormartCurrency(valorTotal));

    }

    private void setNomeMes() {

        this.txtNomeMes.setText(super.getNomeMesFormatado());
    }

    private void getParametrosRecebidos()
    {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actReceita.PARAM_RECEITA_ANO_MES))) {

            this.addMes = bundle.getInt(actReceita.PARAM_RECEITA_ANO_MES);

        }else
        {
            this.addMes = DateUtils.getCurrentYearAndMonth();
        }

    }

}