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
import android.widget.ListView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

public class actCartaoCredito extends actBaseListas implements AdapterView.OnItemClickListener, View.OnClickListener {

    //Objetos Tela
    private ListView lstCartao;
    private FloatingActionButton fabAdd;



    //Atributos
    private AdapterCartaoCredito adapterCartaoCredito;
    private CartaoCredito cartaoCredito;
    private RepositorioCartaoCredito repositorioCartaoCredito;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cartao_credito);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getParametrosRecebidos();
        this.inicializaObjetos();
        this.atualizaListView();
    }

    @Override
    public void onClick(View v) {

        Intent it= null;

        switch (v.getId()) {

              case R.id.btnAdicionarCartao:

                  it = new Intent(this, actCadCartaoCredito.class);
                  startActivityForResult(it, 0);

                break;



        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.cartaoCredito = this.adapterCartaoCredito.getItem(position);

        Intent it = new Intent(this, actCadCartaoCredito.class);
        it.putExtra(actCadCartaoCredito.PARAM_CARTAO_CREDITO, this.cartaoCredito);

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

        super.setMenuHome(this.getString(R.string.title_cartao_credito));
        super.setColorStatusBar(R.color.corTelaCartaoCredito);
        super.setColorActionBar(R.color.corTelaCartaoCredito);


        this.repositorioCartaoCredito = new RepositorioCartaoCredito(this);
        this.adapterCartaoCredito = new AdapterCartaoCredito(this, R.layout.item_cartao_credito);

        this.fabAdd = (FloatingActionButton) findViewById(R.id.btnAdicionarCartao);
        this.fabAdd.setOnClickListener(this);
        this.fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.corTelaCartaoCredito)));





        this.lstCartao = (ListView) findViewById(R.id.lstCartao);
        this.lstCartao.setOnItemClickListener(this);

        this.lstCartao.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        this.adapterCartaoCredito.setData(super.getCalendar().getTime());
        this.adapterCartaoCredito.clear();
        this.adapterCartaoCredito.addAll(this.repositorioCartaoCredito.getSaldoCartao(anoMes,false));

        this.lstCartao.setAdapter(this.adapterCartaoCredito);


    }
    private void getParametrosRecebidos()
    {
        //Bundle bundle = getIntent().getExtras();

    }

}
