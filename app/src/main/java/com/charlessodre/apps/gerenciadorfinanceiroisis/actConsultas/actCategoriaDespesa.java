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

import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioCategoriaDespesa;


public class actCategoriaDespesa extends actBaseListas implements AdapterView.OnItemClickListener, View.OnClickListener {

    //Atributos
    private AdapterCategoriaDespesa adpCategoriaDespesa;
    private ListView lstCategorias;
    private FloatingActionButton fabAdd;

    public static final String PARAM_CATEGORIA_DEPESA = "CATEGORIA_DEPESA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_categoria_despesa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.inicializaObjetos();
        this.preencheDados();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAdicionarCategoriaDespesa:
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

        super.setMenuHome(this.getString(R.string.lblCategoriaDespesas));
        super.setColorStatusBar(R.color.corTelaDespesas);
        super.setColorActionBar(R.color.corTelaDespesas);

        this.lstCategorias = (ListView) findViewById(R.id.lstCategoriasDespesa);
        this.lstCategorias.setOnItemClickListener(this);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.btnAdicionarCategoriaDespesa);
        this.fabAdd.setOnClickListener(this);


        this.fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.corTelaDespesas)));

        this.lstCategorias.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void preencheDados() {

        RepositorioCategoriaDespesa repositorioCategoriaDespesa = new RepositorioCategoriaDespesa(this);

        this.adpCategoriaDespesa = new AdapterCategoriaDespesa(this, R.layout.item_categoria);

        adpCategoriaDespesa.addAll(repositorioCategoriaDespesa.getAll());

        this.lstCategorias.setAdapter(this.adpCategoriaDespesa);

    }

    private void getActivitAlteracao(int position) {

        CategoriaDespesa categoriaDespesa = this.adpCategoriaDespesa.getItem(position);

        Intent it = new Intent(this, actCadCategoriaDespesa.class);
        it.putExtra(PARAM_CATEGORIA_DEPESA, categoriaDespesa);

        startActivityForResult(it, 0);
    }

    private void getActivitCadastro() {

        Intent it = new Intent(this, actCadCategoriaDespesa.class);
        startActivityForResult(it, 0);
    }

}
