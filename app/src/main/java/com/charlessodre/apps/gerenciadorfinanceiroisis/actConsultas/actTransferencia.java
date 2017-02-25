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

import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Transferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

public class actTransferencia extends actBaseListas implements AdapterView.OnItemClickListener, View.OnClickListener {

    //Objetos Tela
    private ListView lstTransferencias;
    private FloatingActionButton fabAdd;
    private ImageButton btnEsquerda;
    private ImageButton btnDireita;
    private TextView txtNomeMes;

    //Atributos
    private AdapterTransferencia adapterTransferencia;
    private RepositorioTransferencia repositorioTransferencia;
    private Transferencia transferencia;
    private int addMes = 0;

    //Constantes
    public static final String PARAM_TRANSFERENCIA = "TRANSFERENCIA";
    public static final String PARAM_TRANSFERENCIA_ANO_MES = "ANO_MES";

    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transferencia);

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
            case R.id.btnEsquerdaTransferencias:
                super.setAddMesCalendar(-1);
                this.setNomeMes();
                this.atualizaListView();

                break;
            case R.id.btnDireitaTransferencias:
                super.setAddMesCalendar(1);
                this.setNomeMes();
                this.atualizaListView();
                break;
            case R.id.btnAdicionarTransferencia:

                Intent it = new Intent(this, actCadTransferencia.class);
                startActivityForResult(it, 0);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.transferencia = this.adapterTransferencia.getItem(position);

        Intent it = new Intent(this, actCadTransferencia.class);
        it.putExtra(PARAM_TRANSFERENCIA, this.transferencia);

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

        super.setMenuHome(this.getString(R.string.title_transferencia));
        super.setColorStatusBar(R.color.corTelaTransferencias);

        this.lstTransferencias = (ListView) findViewById(R.id.lstTransferencias);
        this.lstTransferencias.setOnItemClickListener(this);

        this.fabAdd = (FloatingActionButton) findViewById(R.id.btnAdicionarTransferencia);
        this.fabAdd.setOnClickListener(this);

        this.btnDireita = (ImageButton) this.findViewById(R.id.btnDireitaTransferencias);
        this.btnEsquerda = (ImageButton) this.findViewById(R.id.btnEsquerdaTransferencias);
        this.btnDireita.setOnClickListener(this);
        this.btnEsquerda.setOnClickListener(this);

        this.txtNomeMes = (TextView) findViewById(R.id.txtNomeMesTransferencias);

        this.repositorioTransferencia = new RepositorioTransferencia(this);

        this.adapterTransferencia = new AdapterTransferencia(this, R.layout.item_transferencia);

        this.fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.corTelaTransferencias)));

        this.lstTransferencias.setOnScrollListener(new AbsListView.OnScrollListener() {
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

        //this.adapterTransferencia.setData(this.calendar.getTime());
        this.adapterTransferencia.clear();
        this.adapterTransferencia.addAll(this.repositorioTransferencia.buscaPorAnoMes(anoMes));

        this.lstTransferencias.setAdapter(this.adapterTransferencia);
    }


    private void setNomeMes()
    {
        this.txtNomeMes.setText(super.getNomeMesFormatado());

    }

    private void getParametrosRecebidos()
    {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actTransferencia.PARAM_TRANSFERENCIA_ANO_MES))) {

            this.addMes = bundle.getInt(actTransferencia.PARAM_TRANSFERENCIA_ANO_MES);

        }else
        {
            this.addMes = DateUtils.getCurrentYearAndMonth();
        }

    }
}
