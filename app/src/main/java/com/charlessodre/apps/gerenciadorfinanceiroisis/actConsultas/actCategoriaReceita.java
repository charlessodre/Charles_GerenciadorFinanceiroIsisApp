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

import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadCategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterCategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.ImageHelper;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCategoriaReceita;


public class actCategoriaReceita extends actBaseListas  implements AdapterView.OnItemClickListener, View.OnClickListener  {

    //Atributos
    private AdapterCategoriaReceita adpCategoriaReceita;
    private ListView lstCategorias;
    private FloatingActionButton fabAdd;

    public static final String PARAM_CATEGORIA_RECEITA = "PARAM_CATEGORIA_RECEITA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_categoria_receita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.inicializaObjetos();
        this.preencheDados();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAdicionarCategoriaReceita:
                this.getActivitCadastro();

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.getActivitAlteracao(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        this.preencheDados();
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
    protected void inicializaObjetos() {

        super.setMenuHome(this.getString(R.string.lblCategoriaReceitas));
        super.setColorStatusBar(R.color.corTelaReceitas);
        super.setColorActionBar(R.color.corTelaReceitas);

        this.lstCategorias = (ListView) findViewById(R.id.lstCategoriasReceita);
        this.lstCategorias.setOnItemClickListener(this);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.btnAdicionarCategoriaReceita);
        this.fabAdd.setOnClickListener(this);

        this.fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.corTelaReceitas)));

        this.lstCategorias.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void preencheDados() {

        RepositorioCategoriaReceita repositorioCategoriaReceita = new RepositorioCategoriaReceita(this);

        this.adpCategoriaReceita = new AdapterCategoriaReceita(this, R.layout.item_categoria);

        adpCategoriaReceita.addAll(repositorioCategoriaReceita.buscaTodos());

        this.lstCategorias.setAdapter(this.adpCategoriaReceita);

    }

    private void getActivitAlteracao(int position) {

        CategoriaReceita categoriaReceita = this.adpCategoriaReceita.getItem(position);

        Intent it = new Intent(this, actCadCategoriaReceita.class);
        it.putExtra(PARAM_CATEGORIA_RECEITA, categoriaReceita);

        startActivityForResult(it, 0);
    }

    private void getActivitCadastro() {

        Intent it = new Intent(this, actCadCategoriaReceita.class);
        startActivityForResult(it, 0);
    }

}
