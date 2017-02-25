package com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Transferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DecimalHelper;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by charl on 19/09/2016.
 */
public class AdapterTransferencia extends ArrayAdapter<Transferencia> {

    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;

    public AdapterTransferencia(Context applicationContext, int layoutResource) {
        super(applicationContext, layoutResource);
        this.context = applicationContext;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = layoutResource;
        this.symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

    }

    /*  @Override
      public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
          return getCustomView(position, cnvtView, prnt);
      }
  */
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

            viewHolder.txtContaOrigemItem = (TextView) view.findViewById(R.id.txtContaOrigemItem);
            viewHolder.txtContaDestinoItem = (TextView) view.findViewById(R.id.txtContaDestinoItem);
            viewHolder.txtValorTransferenciaItem = (TextView) view.findViewById(R.id.txtValorTransferenciaItem);
            viewHolder.txtDataTransferenciaIItem = (TextView) view.findViewById(R.id.txtDataTransferenciaIItem);
            viewHolder.imgCirculo = (ImageView) view.findViewById(R.id.imgTransfItemCir);

            view.setTag(viewHolder);
            convertView = view;


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Transferencia transferencia = getItem(position);


        int color = ColorHelper.getColor(this.context, R.color.blue_300);
        Drawable circle = viewHolder.imgCirculo.getDrawable();
        circle.setColorFilter(color, PorterDuff.Mode.MULTIPLY);


        viewHolder.txtContaOrigemItem.setText(transferencia.getContaOrigem().getNome());
        viewHolder.txtContaDestinoItem.setText(transferencia.getContaDestino().getNome());

        viewHolder.txtValorTransferenciaItem.setText(this.symbol + " " + DecimalHelper.getFormartCurrency(transferencia.getValor()));

        viewHolder.txtDataTransferenciaIItem.setText(String.valueOf(DateUtils.getWeekNameAndDay(transferencia.getDataTransferencia())));

        return view;
    }


    public static class ViewHolder {
        ImageView imgCirculo;
        TextView txtContaOrigemItem;
        TextView txtContaDestinoItem;
        TextView txtValorTransferenciaItem;
        TextView txtDataTransferenciaIItem;
    }

}
