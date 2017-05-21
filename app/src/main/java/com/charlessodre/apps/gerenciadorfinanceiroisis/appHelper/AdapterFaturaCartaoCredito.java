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
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.FaturaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by charl on 19/09/2016.
 */
public class AdapterFaturaCartaoCredito extends ArrayAdapter<FaturaCartaoCredito> {

    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;
    private Date data;
    private int corSaldoNegativo = 0;
    private int corSaldoPositivo = 0;
    private String textoFatura;

    public void setData(Date data) {
        this.data = data;
    }


    public AdapterFaturaCartaoCredito(Context applicationContext, int layoutResource) {

        super(applicationContext, layoutResource);
        this.context = applicationContext;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = layoutResource;
        this.symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();
        this.corSaldoNegativo = ColorHelper.getColor(this.context, R.color.corPendencia);
        this.corSaldoPositivo = ColorHelper.getColor(this.context, R.color.corResolvido);
        this.textoFatura = this.context.getResources().getString(R.string.lblFatura);
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {

        return getCustomDropDownView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View currentView, ViewGroup viewGroup) {

        View row;

        if (this.resource == R.layout.item_fatura_simples) {
            row = getCustomDropDownView(pos, currentView, viewGroup);
        } else {
            row = getCustomView(pos, currentView, viewGroup);
        }

        return row;
    }

    public View getCustomDropDownView(int position, View convertView, ViewGroup parent) {

        ViewHolderSimple viewHolderSimple = null;
        View view = null;

        if (convertView == null) {

            view = inflater.inflate(this.resource, parent, false);

            viewHolderSimple = new ViewHolderSimple();

            viewHolderSimple.txtNomeFatura = (TextView) view.findViewById(R.id.txtNomeFatura);

            view.setTag(viewHolderSimple);

            convertView = view;

        } else {
            viewHolderSimple = (ViewHolderSimple) convertView.getTag();
            view = convertView;
        }

        FaturaCartaoCredito faturaCartaoCredito = getItem(position);

        viewHolderSimple.txtNomeFatura.setText(this.textoFatura + ": " + String.valueOf(DateUtils.getDateFormat(faturaCartaoCredito.getDataFatura(),"dd MMM yyyy")));

        return view;

    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        View view = null;

        if (convertView == null) {

            view = inflater.inflate(this.resource, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imgFatura = (ImageView) view.findViewById(R.id.imgCartaoItem);
            viewHolder.txtNomeFatura = (TextView) view.findViewById(R.id.txtNomeCartaoItem);
            viewHolder.txtValorFaturaFechada = (TextView) view.findViewById(R.id.txtValorFaturaFechada);
            viewHolder.txtValorFaturaAberta = (TextView) view.findViewById(R.id.txtValorFaturaAberta);
            viewHolder.txtSaldoPrevistoData = (TextView) view.findViewById(R.id.txtSaldoPrevistoDataItem);
            viewHolder.imgCirculo = (ImageView) view.findViewById(R.id.imgCartaoItemCir);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

       // FaturaCartaoCredito faturaCartaoCredito = getItem(position);

       // Drawable circle = viewHolder.imgCirculo.getDrawable();
       // circle.setColorFilter(ColorHelper.getColor(this.context, faturaCartaoCredito.getNoCor()), PorterDuff.Mode.MULTIPLY);


       // String texto = String.valueOf(DateUtils.getLastDayOfMonth(this.data)) + "/" + String.valueOf(DateUtils.getMonthNameShort(this.data));
       // texto = texto + " (" + this.textoPreviso + ")";

        //viewHolder.imgFatura.setImageResource(CartaoCredito.getImagemBandeiraCartao(faturaCartaoCredito.getNoBandeiraCartao()));
        //viewHolder.txtNomeFatura.setText(faturaCartaoCredito.getNome());
       // viewHolder.txtSaldoAtual.setText(this.symbol + " " + NumberUtis.getFormartCurrency(faturaCartaoCredito.getValorSaldo()));
      //  viewHolder.txtSaldoPrevistoData.setText(texto);
       // viewHolder.txtSaldoPrevistoValor.setText(this.symbol + " " + NumberUtis.getFormartCurrency(faturaCartaoCredito.getSaldoPrevisto()));

      //  if (faturaCartaoCredito.getNoCorIcone() != 0)
       //     viewHolder.imgFatura.setColorFilter(ColorHelper.getColor(this.context, faturaCartaoCredito.getNoCorIcone()));


      /*  if (faturaCartaoCredito.getValorSaldo() < 0) {
            viewHolder.txtSaldoAtual.setTextColor(this.corSaldoNegativo);
        } else {
            viewHolder.txtSaldoAtual.setTextColor(this.corSaldoPositivo);
        }

        if (faturaCartaoCredito.getSaldoPrevisto() < 0) {
            viewHolder.txtSaldoPrevistoValor.setTextColor(this.corSaldoNegativo);
        } else {
            viewHolder.txtSaldoPrevistoValor.setTextColor(this.corSaldoPositivo);
        }
*/
        return view;
    }


    public static class ViewHolder {
        ImageView imgCirculo;
        ImageView imgFatura;
        TextView txtNomeFatura;
        TextView txtValorFaturaFechada;
        TextView txtSaldoPrevistoData;
        TextView txtValorFaturaAberta;

    }

    public int getIndexFromElement(long id) {
        for (int i = 0; i < this.getCount(); i++) {
            if (this.getItem(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public static class ViewHolderSimple {
        ImageView imgFatura;
        TextView txtNomeFatura;
    }


}
