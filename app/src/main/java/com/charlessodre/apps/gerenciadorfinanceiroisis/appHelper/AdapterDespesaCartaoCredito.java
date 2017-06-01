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
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Despesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.DespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by charl on 02/01/2017.
 */
public class AdapterDespesaCartaoCredito extends ArrayAdapter<DespesaCartaoCredito>  {

    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;
    private int corRecebida = 0;
    private int corPendente = 0;
    private String textoRecebido;
    private String textoPendente;


    public AdapterDespesaCartaoCredito(Context applicationContext, int layoutResource) {
        super(applicationContext, layoutResource);
        this.context = applicationContext;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = layoutResource;
        this.symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();
        this.corRecebida = ColorHelper.getColor(this.context, R.color.corResolvido);
        this.corPendente = ColorHelper.getColor(this.context, R.color.corPendencia);
        this.textoRecebido = this.context.getResources().getString(R.string.lblPago);
        this.textoPendente = this.context.getResources().getString(R.string.lblPendente);
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

            viewHolder.imgDespesa = (ImageView) view.findViewById(R.id.imgDespesaItem);
            viewHolder.txtNomeDespesa = (TextView) view.findViewById(R.id.txtNomeDespesaItem);
            viewHolder.txtTipoDespesa = (TextView) view.findViewById(R.id.txtTipoDespesaItem);
            viewHolder.txtStatusDespesa = (TextView) view.findViewById(R.id.txtStatusDespesaItem);
            viewHolder.txtValorDespesa = (TextView) view.findViewById(R.id.txtValorDespesaItem);
            viewHolder.txtDataDespesa = (TextView) view.findViewById(R.id.txtDataDespesaItem);
            viewHolder.imgCirculo = (ImageView) view.findViewById(R.id.imgDespesaItemCir);
            viewHolder.txtContaDespesaItem = (TextView) view.findViewById(R.id.txtContaDespesaItem);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        DespesaCartaoCredito despesa = getItem(position);

        int color = ColorHelper.getColor(this.context, despesa.getCategoriaDespesa().getNoCor());
        Drawable circle = viewHolder.imgCirculo.getDrawable();
        circle.setColorFilter(color, PorterDuff.Mode.MULTIPLY);


        viewHolder.imgDespesa.setImageResource(despesa.getCategoriaDespesa().getNoIcone());

        viewHolder.txtNomeDespesa.setText(despesa.getNome());

       // viewHolder.txtContaDespesaItem.setText(despesa.getConta().getNome());

        if (despesa.isPaga()) {
            viewHolder.txtStatusDespesa.setText(this.textoRecebido);
            viewHolder.txtStatusDespesa.setTextColor(this.corRecebida);
            viewHolder.txtValorDespesa.setTextColor(this.corRecebida);
        } else {
            viewHolder.txtStatusDespesa.setText(this.textoPendente);
            viewHolder.txtStatusDespesa.setTextColor(this.corPendente);
            viewHolder.txtValorDespesa.setTextColor(this.corPendente);
        }


        String tipoDespesa = despesa.getCategoriaDespesa().getNome();

        if (despesa.isFixa()) {
            tipoDespesa = tipoDespesa + " | " + this.context.getResources().getString(R.string.lblFixa);
            ;
        } else if (despesa.getTotalRepeticao() > 0) {
            String repeticao = despesa.getRepeticaoAtual() + " " + this.context.getResources().getString(R.string.lblDe) + " " + despesa.getTotalRepeticao();

            tipoDespesa = tipoDespesa + " | " + repeticao;
        }

        viewHolder.txtTipoDespesa.setText(tipoDespesa);

        viewHolder.txtValorDespesa.setText(this.symbol + " " + NumberUtis.getFormartCurrency(despesa.getValor()));

        viewHolder.txtDataDespesa.setText(String.valueOf(DateUtils.getWeekNameAndDay(despesa.getDataDespesa())));

        return view;
    }

    public static class ViewHolder {
        ImageView imgCirculo;
        ImageView imgDespesa;
        TextView txtNomeDespesa;
        TextView txtTipoDespesa;
        TextView txtStatusDespesa;
        TextView txtValorDespesa;
        TextView txtDataDespesa;
        TextView txtContaDespesaItem;

    }

}
