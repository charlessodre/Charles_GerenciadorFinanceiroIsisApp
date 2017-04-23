package com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadRegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadTransferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.AdapterSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Despesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Transferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgConfirmacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.fragmentos.frgTipoTransacaoDialog;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.LerHistoricoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.PermissionsUtil;

import java.util.ArrayList;
import java.util.Date;

public class actListaSMS extends actBaseListas implements AdapterView.OnItemClickListener, frgTipoTransacaoDialog.onDialogClick {

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

       // PermissionsUtil.askPermissions(this);

        //  this.getParametrosRecebidos();
        this.inicializaObjetos();

        // super.setAnoMesCalendar(this.addMes);
        this.atualizaListView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.sms = this.adapterSMS.getItem(position);

      /*  Intent it = new Intent(this, actCadRegraImportacaoSMS.class);
        it.putExtra(actCadRegraImportacaoSMS.PARAM_IMP_SMS, this.sms);

        startActivityForResult(it, 0);*/

        this.exibePopUpImportacaoDialog();


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
        super.setColorStatusBar(R.color.corTelaListaSMS);
        this.lstListaSMS = (ListView) findViewById(R.id.lstListaSMS);
        this.lstListaSMS.setOnItemClickListener(this);

        this.adapterSMS = new AdapterSMS(this, R.layout.item_sms);

    }

    private void atualizaListView() {

        this.adapterSMS.clear();
        this.adapterSMS.addAll(LerHistoricoSMS.getSMSDetails(this));

        this.lstListaSMS.setAdapter(this.adapterSMS);

    }

    protected void exibePopUpImportacaoDialog() {

        frgTipoTransacaoDialog dlg = new frgTipoTransacaoDialog();

        dlg.show(getSupportFragmentManager(), frgConfirmacaoDialog.NOME_FRAGMENTO);
    }

    private void getParametrosRecebidos() {
       /* Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey(actListaSMS.PARAM_IMP_SMS_ANO_MES))) {

            this.addMes = bundle.getInt(actListaSMS.PARAM_IMP_SMS_ANO_MES);

        }else
        {
            this.addMes = DateUtils.getCurrentYearAndMonth();
        }
*/
    }

    @Override
    public void onDialogClick(frgTipoTransacaoDialog dialog, int opcaoSelecionada) {

        Double valor = 0.0;
        Date data = this.sms.getData();
        ArrayList<Double> valores = NumberUtis.getCurrencyInStringWithRegEx(this.sms.getMensagem(), "$");
        ArrayList<Date> datas = DateUtils.getDatesInStringWithRegEx(this.sms.getMensagem());
        Date dataInclusao = DateUtils.getCurrentDatetime();
        int noAnoMEs = DateUtils.getYearAndMonth(data);
        int index = NumberUtis.getIndexLastNumberFindInString(this.sms.getMensagem());
        String descricao = "";

        if (index > 0)
            descricao = this.sms.getMensagem().substring(index, this.sms.getMensagem().length()).trim();

        if (valores.size() > 0)
            valor = valores.get(0);

        if (datas.size() > 0)
            data = datas.get(0);


        if (opcaoSelecionada == Constantes.TipoTransacao.RECEITA) {

            Receita receita = new Receita();

            receita.setValor(valor);
            receita.setDataReceita(data);
            receita.setAnoMes(noAnoMEs);
            receita.setDataInclusao(dataInclusao);
            receita.setNome(descricao);

            Intent it = new Intent(this, actCadReceita.class);
            it.putExtra(actReceita.PARAM_RECEITA_OBJ, receita);
            startActivity(it);


        } else if (opcaoSelecionada == Constantes.TipoTransacao.DESPESA) {

            Despesa despesa = new Despesa();

            despesa.setValor(valor);
            despesa.setDataDespesa(data);
            despesa.setAnoMes(noAnoMEs);
            despesa.setDataInclusao(dataInclusao);
            despesa.setNome(descricao);

            Intent it = new Intent(this, actCadDespesa.class);
            it.putExtra(actDespesa.PARAM_DESPESA, despesa);
            startActivity(it);

        } else if (opcaoSelecionada == Constantes.TipoTransacao.TRANSFERENCIA) {

            Transferencia transferencia = new Transferencia();

            transferencia.setValor(valor);
            transferencia.setDataTransferencia(data);
            transferencia.setAnoMes(noAnoMEs);
            transferencia.setDataInclusao(dataInclusao);

            Intent it = new Intent(this, actCadTransferencia.class);
            it.putExtra(actTransferencia.PARAM_TRANSFERENCIA, transferencia);
            startActivity(it);

        } else if (opcaoSelecionada == frgTipoTransacaoDialog.OPCAO_REGRA_IMPORTACAO) {
            RegraImportacaoSMS regraImportacaoSMS = new RegraImportacaoSMS();

            regraImportacaoSMS.setDescricaoReceitaDespesa(descricao);
            regraImportacaoSMS.setTextoPesquisa1(descricao);
            regraImportacaoSMS.setNoTelefone(sms.getNumero());
            regraImportacaoSMS.setAtivo(true);
            regraImportacaoSMS.setDataInclusao(dataInclusao);

            Intent it = new Intent(this, actCadRegraImportacaoSMS.class);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            it.putExtra(actCadRegraImportacaoSMS.PARAM_REGRA_IMP_SMS, regraImportacaoSMS);

            startActivity(it);

        }


    }
}
