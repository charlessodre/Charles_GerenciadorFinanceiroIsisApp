package com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by charl on 25/09/2016.
 */

public class AdapterSMS extends ArrayAdapter<SMS> {

    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;

    public AdapterSMS(Context applicationContext, int resource) {

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

            viewHolder.txtTelefone = (TextView) view.findViewById(R.id.txtTelefone);
            viewHolder.txtData = (TextView) view.findViewById(R.id.txtData);
            viewHolder.txtTipoSMS = (TextView) view.findViewById(R.id.txtTipoSMS);
            viewHolder.txtMensagem = (TextView) view.findViewById(R.id.txtMensagem);

            view.setTag(viewHolder);
            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        SMS sms = getItem(position);

        viewHolder.txtTelefone.setText(sms.getNumero());
        viewHolder.txtData.setText(DateUtils.dateToString(sms.getData()));
        viewHolder.txtMensagem.setText(sms.getMensagem());
        viewHolder.txtTipoSMS.setText(sms.getTipoSMS());


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
        TextView txtTelefone;
        TextView txtData;
        TextView txtTipoSMS;
        TextView txtMensagem;
    }


}
