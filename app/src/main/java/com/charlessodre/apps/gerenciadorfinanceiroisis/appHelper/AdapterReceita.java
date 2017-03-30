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
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by charl on 19/09/2016.
 */
public class AdapterReceita extends ArrayAdapter<Receita>  {

    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;
    private int corRecebida = 0;
    private int corPendente = 0;
    private String textoRecebido;
    private String textoPendente;


    public AdapterReceita(Context applicationContext, int layoutResource) {
        super(applicationContext, layoutResource);
        this.context = applicationContext;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = layoutResource;
        this.symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();
        this.corRecebida = ColorHelper.getColor(this.context, R.color.corResolvido);
        this.corPendente = ColorHelper.getColor(this.context, R.color.corPendencia);
        this.textoRecebido = this.context.getResources().getString(R.string.lblRecebido);
        this.textoPendente = this.context.getResources().getString(R.string.lblNaoRecebido);
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

            viewHolder.imgReceita = (ImageView) view.findViewById(R.id.imgReceitaItem);
            viewHolder.txtNomeReceita = (TextView) view.findViewById(R.id.txtNomeReceitaItem);
            viewHolder.txtTipoReceita = (TextView) view.findViewById(R.id.txtTipoReceitaItem);
            viewHolder.txtStatusReceita = (TextView) view.findViewById(R.id.txtStatusReceitaItem);
            viewHolder.txtValorReceita = (TextView) view.findViewById(R.id.txtValorReceitaItem);
            viewHolder.txtDataReceita = (TextView) view.findViewById(R.id.txtDataReceitaItem);
            viewHolder.imgCirculo = (ImageView) view.findViewById(R.id.imgReceitaItemCir);
            viewHolder.txtContaReceitaItem = (TextView) view.findViewById(R.id.txtContaReceitaItem);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Receita receita = getItem(position);

        int color = ColorHelper.getColor(this.context, receita.getCategoriaReceita().getNoCor());
        Drawable circle = viewHolder.imgCirculo.getDrawable();
        circle.setColorFilter(color, PorterDuff.Mode.MULTIPLY);


        viewHolder.imgReceita.setImageResource(receita.getCategoriaReceita().getNoIcone());

        //  viewHolder.txtNomeDespesa.setTextColor(color);
        viewHolder.txtNomeReceita.setText(receita.getNome());

        viewHolder.txtContaReceitaItem.setText(receita.getConta().getNome());

        if (receita.isPaga()) {
            viewHolder.txtStatusReceita.setText(this.textoRecebido);
            viewHolder.txtStatusReceita.setTextColor(this.corRecebida);
            viewHolder.txtValorReceita.setTextColor(this.corRecebida);
        } else {
            viewHolder.txtStatusReceita.setText(this.textoPendente);
            viewHolder.txtStatusReceita.setTextColor(this.corPendente);
        }


        String tipoReceita = receita.getCategoriaReceita().getNome();

        if (receita.isFixa()) {
            tipoReceita = tipoReceita + " | " + this.context.getResources().getString(R.string.lblFixa);
            ;
        } else if (receita.getTotalRepeticao() > 0) {
            String repeticao = receita.getRepeticaoAtual() + " " + this.context.getResources().getString(R.string.lblDe) + " " + receita.getTotalRepeticao();

            tipoReceita = tipoReceita + " | " + repeticao;
        }

        viewHolder.txtTipoReceita.setText(tipoReceita);

        viewHolder.txtValorReceita.setText(this.symbol + " " + NumberUtis.getFormartCurrency(receita.getValor()));

        viewHolder.txtDataReceita.setText(String.valueOf(DateUtils.getWeekNameAndDay(receita.getDataReceita())));

        return view;
    }

    public static class ViewHolder {
        ImageView imgCirculo;
        ImageView imgReceita;
        TextView txtNomeReceita;
        TextView txtTipoReceita;
        TextView txtStatusReceita;
        TextView txtValorReceita;
        TextView txtDataReceita;
        TextView txtContaReceitaItem;

    }

}
