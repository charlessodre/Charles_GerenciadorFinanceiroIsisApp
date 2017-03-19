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
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadRegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterRegraImpSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioConta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios.RepositorioRegraImpSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DecimalHelper;

import java.text.NumberFormat;
import java.util.Locale;

public class actRegraImportacaoSMS extends actBaseListas implements AdapterView.OnItemClickListener, View.OnClickListener {

    //Objetos Tela
    private ListView lstRegraImpSMS;
    private FloatingActionButton fabAdd;


    //Atributos
    private AdapterRegraImpSMS adpRegraImpSMS;
    private RepositorioRegraImpSMS repositorioRegraImpSMS;
    private RegraImportacaoSMS regraImpSMS;
    private int addMes = 0;


    //Eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_regra_importacao_sms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getParametrosRecebidos();
        this.inicializaObjetos();

        super.setAnoMesCalendar(this.addMes);
        this.atualizaListView();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAdicionarRegraImpSMS:

                Intent it = new Intent(this, actCadRegraImportacaoSMS.class);
                startActivityForResult(it, 0);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.regraImpSMS = this.adpRegraImpSMS.getItem(position);

        Intent it = new Intent(this, actCadRegraImportacaoSMS.class);
        it.putExtra(actCadRegraImportacaoSMS.PARAM_REGRA_IMP_SMS, this.regraImpSMS);

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

        super.setMenuHome(this.getString(R.string.title_regra_imp_sms));
        super.setColorStatusBar(R.color.corTelaRegaImportacaoSMS);

        this.lstRegraImpSMS = (ListView) findViewById(R.id.lstRegrasImpSMS);
        this.lstRegraImpSMS.setOnItemClickListener(this);

        this.fabAdd = (FloatingActionButton) findViewById(R.id.btnAdicionarRegraImpSMS);
        this.fabAdd.setOnClickListener(this);

        this.repositorioRegraImpSMS = new RepositorioRegraImpSMS(this);

        this.adpRegraImpSMS = new AdapterRegraImpSMS(this, R.layout.item_regra_imp_sms);

        this.fabAdd.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.corTelaRegaImportacaoSMS)));

        this.lstRegraImpSMS.setOnScrollListener(new AbsListView.OnScrollListener() {
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

        this.adpRegraImpSMS.clear();
        this.adpRegraImpSMS.addAll(this.repositorioRegraImpSMS.buscaTodos());

        this.lstRegraImpSMS.setAdapter(this.adpRegraImpSMS);

    }

    private void getParametrosRecebidos()
    {
        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actCadRegraImportacaoSMS.PARAM_REGRA_IMP_SMS_ANO_MES))) {

            this.addMes = bundle.getInt(actCadRegraImportacaoSMS.PARAM_REGRA_IMP_SMS_ANO_MES);

        }else
        {
            this.addMes = DateUtils.getCurrentYearAndMonth();
        }

    }

}
