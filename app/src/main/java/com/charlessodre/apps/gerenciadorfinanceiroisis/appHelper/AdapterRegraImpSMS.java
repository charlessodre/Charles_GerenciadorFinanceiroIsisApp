package com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by charl on 25/09/2016.
 */

public class AdapterRegraImpSMS extends ArrayAdapter<RegraImportacaoSMS> {


    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;

    public AdapterRegraImpSMS(Context applicationContext, int resource) {

        super(applicationContext, resource);

        this.context = applicationContext;
        this.resource = resource;
        this.symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View currentView, ViewGroup viewGroup) {
        return getCustomView(pos, currentView, viewGroup);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        View view = null;

        if (convertView == null) {

            view = inflater.inflate(this.resource, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.txtNumTelefone = (TextView) view.findViewById(R.id.txtNumTelefone);
            viewHolder.txtNomeRegra = (TextView) view.findViewById(R.id.txtNomeRegra);
            viewHolder.txtStatusRegra = (TextView) view.findViewById(R.id.txtStatusRegra);
            viewHolder.txtTipoTransacao = (TextView) view.findViewById(R.id.txtTipoTransacao);

            view.setTag(viewHolder);
            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        RegraImportacaoSMS regraImportacaoSMS = getItem(position);

        viewHolder.txtNomeRegra.setText(regraImportacaoSMS.getNome());
        viewHolder.txtNumTelefone.setText(regraImportacaoSMS.getNoTelefone());
        viewHolder.txtTipoTransacao.setText(regraImportacaoSMS.getIdTipoTransacao());

        if (regraImportacaoSMS.isAtivo())
            viewHolder.txtStatusRegra.setText("Ativa");
        else
            viewHolder.txtStatusRegra.setText("Inativa");
        return view;
    }

    public int getIndexFromElement(long id) {
        for (int i = 0; i < this.getCount(); i++) {
            if (this.getItem(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public static class ViewHolder {
        TextView txtNumTelefone;
        TextView txtNomeRegra;
        TextView txtTipoTransacao;
        TextView txtStatusRegra;
}


}
