package com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadRegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.LerHistoricoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.PermissionsUtil;

public class actListaSMS extends actBaseListas implements AdapterView.OnItemClickListener {

    //Objetos Tela
    private ListView lstListaSMS;

    //Atributos
    private AdapterSMS adapterSMS;
    private SMS sms;
    private int addMes = 0;

    //Eventos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lista_sms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PermissionsUtil.askPermissions(this);

      //  this.getParametrosRecebidos();
        this.inicializaObjetos();

       // super.setAnoMesCalendar(this.addMes);
        this.atualizaListView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.sms = this.adapterSMS.getItem(position);

        Intent it = new Intent(this, actCadRegraImportacaoSMS.class);
        it.putExtra(actCadRegraImportacaoSMS.PARAM_IMP_SMS, this.sms);

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

        super.setMenuHome(this.getString(R.string.title_lista_sms));

        super.setColorStatusBar(R.color.corTelaRegaImportacaoSMS);

        this.lstListaSMS = (ListView) findViewById(R.id.lstListaSMS);
        this.lstListaSMS.setOnItemClickListener(this);

        this.adapterSMS = new AdapterSMS(this, R.layout.item_sms);


    }

    private void atualizaListView() {

        this.adapterSMS.clear();
        this.adapterSMS.addAll(LerHistoricoSMS.getSMSDetails(this));

        this.lstListaSMS.setAdapter(this.adapterSMS);

    }

    private void getParametrosRecebidos()
    {
       /* Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actListaSMS.PARAM_IMP_SMS_ANO_MES))) {

            this.addMes = bundle.getInt(actListaSMS.PARAM_IMP_SMS_ANO_MES);

        }else
        {
            this.addMes = DateUtils.getCurrentYearAndMonth();
        }
*/
    }

}
