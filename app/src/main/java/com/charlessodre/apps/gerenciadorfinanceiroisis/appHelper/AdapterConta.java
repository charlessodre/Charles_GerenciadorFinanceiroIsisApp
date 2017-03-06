package com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DecimalHelper;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by charl on 19/09/2016.
 */
public class AdapterConta extends ArrayAdapter<Conta> {

    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;
    private Date data;
    private int corSaldoNegativo = 0;
    private String textoPreviso;

    public void setData(Date data) {
        this.data = data;
    }


    public AdapterConta(Context applicationContext, int layoutResource) {

        super(applicationContext, layoutResource);
        this.context = applicationContext;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = layoutResource;
        this.symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();
        this.corSaldoNegativo = ColorHelper.getColor(this.context, R.color.corPendencia);
        this.textoPreviso = this.context.getResources().getString(R.string.lblPrevisto);
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {

        return getCustomDropDownView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View currentView, ViewGroup viewGroup) {

        View row;

        if (this.resource == R.layout.item_conta_simples) {
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

            viewHolderSimple.imgConta = (ImageView) view.findViewById(R.id.imgConta);
            viewHolderSimple.imgCirculo = (ImageView) view.findViewById(R.id.imgCategoriaCir);
            viewHolderSimple.txtNomeConta = (TextView) view.findViewById(R.id.txtNomeConta);




            view.setTag(viewHolderSimple);

            convertView = view;

        } else {
            viewHolderSimple = (ViewHolderSimple) convertView.getTag();
            view = convertView;
        }


        Conta conta = getItem(position);


        int color = ColorHelper.getColor(this.context, conta.getNoCor());
        Drawable circle = viewHolderSimple.imgCirculo.getDrawable();
        circle.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        viewHolderSimple.imgConta.setImageResource(getImagemTipoConta(conta.getCdTipoConta()));
        //viewHolderSimple.txtNomeConta.setTextColor(ColorHelper.getColor(this.context, conta.getNoCor()));
        viewHolderSimple.txtNomeConta.setText(conta.getNome());

       // viewHolderSimple.imgConta.setColorFilter(ContextCompat.getColor(context,R.color.white));

        return view;

    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        View view = null;

        if (convertView == null) {

            view = inflater.inflate(this.resource, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imgConta = (ImageView) view.findViewById(R.id.imgContaItem);
            viewHolder.txtNomeConta = (TextView) view.findViewById(R.id.txtNomeContaItem);
            viewHolder.txtSaldoAtual = (TextView) view.findViewById(R.id.txtSaldoAtualItem);
            viewHolder.txtSaldoPrevistoData = (TextView) view.findViewById(R.id.txtSaldoPrevistoDataItem);
            viewHolder.txtSaldoPrevistoValor = (TextView) view.findViewById(R.id.txtSaldoPrevistoValorItem);
            viewHolder.imgCirculo = (ImageView) view.findViewById(R.id.imgContaItemCir);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Conta conta = getItem(position);

        int color = ColorHelper.getColor(this.context, conta.getNoCor());
        Drawable circle = viewHolder.imgCirculo.getDrawable();
        circle.setColorFilter(color, PorterDuff.Mode.MULTIPLY);


        String texto = String.valueOf(DateUtils.getLastDayOfMonth(this.data)) + "/" + String.valueOf(DateUtils.getMonthNameShort(this.data));
        texto = texto + " (" + this.textoPreviso + ")";

        viewHolder.imgConta.setImageResource(getImagemTipoConta(conta.getCdTipoConta()));
        //viewHolder.txtNomeConta.setTextColor(color);
        viewHolder.txtNomeConta.setText(conta.getNome());
        viewHolder.txtSaldoAtual.setText(this.symbol + " " + DecimalHelper.getFormartCurrency(conta.getValorSaldo()));
        viewHolder.txtSaldoPrevistoData.setText(texto);
        viewHolder.txtSaldoPrevistoValor.setText(this.symbol + " " + DecimalHelper.getFormartCurrency(conta.getSaldoPrevisto()));


        if (conta.getValorSaldo() < 0) {
            viewHolder.txtSaldoAtual.setTextColor(this.corSaldoNegativo);
        }

        if (conta.getSaldoPrevisto() < 0) {
            viewHolder.txtSaldoPrevistoValor.setTextColor(this.corSaldoNegativo);
        }

        return view;
    }


    public static class ViewHolder {
        ImageView imgCirculo;
        ImageView imgConta;
        TextView txtNomeConta;
        TextView txtSaldoAtual;
        TextView txtSaldoPrevistoData;
        TextView txtSaldoPrevistoValor;

    }

    public int getIndexFromElement(long id) {
        for(int i = 0; i < this.getCount(); i++) {
            if(this.getItem(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public static class ViewHolderSimple {
        ImageView imgConta;
        ImageView imgCirculo;
        TextView txtNomeConta;
    }

    public static int getImagemTipoConta(int idTipoConta) {

        switch (idTipoConta) {

            case 0:

                return R.drawable.ic_conta_corrente_24dp;

            case 1:
                return R.drawable.ic_poupanca_24dp;

            case 2:
                return R.drawable.ic_dinheiro_24dp;

            case 3:

                return R.drawable.ic_investimento_24dp;

            default:
                return R.drawable.ic_conta_outros_24dp;
        }

    }
}
