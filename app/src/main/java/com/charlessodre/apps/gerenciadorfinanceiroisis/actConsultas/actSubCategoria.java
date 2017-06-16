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
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterSubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SubCategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioSubCategoriaDespesa;

public class actSubCategoria extends actBaseListas implements AdapterView.OnItemClickListener, View.OnClickListener {

    //Atributos
    private ListView lstSubCategorias;
    private FloatingActionButton fabAdd;
    private  RepositorioSubCategoriaDespesa repositorioSubCategoriaDespesa;
    private AdapterSubCategoriaDespesa adpSubCategoriaDespesa;
    private SubCategoriaDespesa subCategoriaDespesa;

    public static final String PARAM_SUB_CATEGORIA = "SUB_CATEGORIA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sub_categoria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.inicializaObjetos();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAdicionarSubCategoria:

                this.getCadastraSubCategoriaDespesa();

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            this.getEditaSubCategoriaDespesa(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            this.preencheDadosSubCategoria();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id== android.R.id.home)
        {
            this.finish();

        }

        return true;
    }


    @Override
    protected void inicializaObjetos() {

        super.setMenuHome(this.getString(R.string.lblCategoriaDespesas));
        super.setColorStatusBar(R.color.corTelaDespesas);
        super.setColorActionBar(R.color.corTelaDespesas);

        this.lstSubCategorias = (ListView) findViewById(R.id.lstSubCategorias);
        this.lstSubCategorias.setOnItemClickListener(this);
        this.fabAdd = (FloatingActionButton) findViewById(R.id.btnAdicionarSubCategoria);
        this.fabAdd.setOnClickListener(this);

       this.fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.corTelaDespesas)));
        this.lstSubCategorias.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void preencheDadosSubCategoria() {

        this.repositorioSubCategoriaDespesa = new RepositorioSubCategoriaDespesa(this);

        this.adpSubCategoriaDespesa = new AdapterSubCategoriaDespesa(this, R.layout.item_image_view);

        this.adpSubCategoriaDespesa.addAll(repositorioSubCategoriaDespesa.getAll());

        this.lstSubCategorias.setAdapter(this.adpSubCategoriaDespesa);

    }

    private void getEditaSubCategoriaDespesa(int position){

        this.subCategoriaDespesa = this.adpSubCategoriaDespesa.getItem(position);

        Intent it = new Intent(this, actCadCategoriaReceita.class);
        it.putExtra(PARAM_SUB_CATEGORIA, this.subCategoriaDespesa);

        startActivityForResult(it, 0);
    }

    private void getCadastraSubCategoriaDespesa() {

        Intent it = new Intent(this, actCadCategoriaReceita.class);
        startActivityForResult(it, 0);
    }



}
