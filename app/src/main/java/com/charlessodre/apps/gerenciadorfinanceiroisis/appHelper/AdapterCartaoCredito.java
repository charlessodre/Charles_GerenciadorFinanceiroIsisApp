package com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actCadastros.actCadDespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.actConsultas.actDespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by charl on 19/09/2016.
 */
public class AdapterCartaoCredito extends ArrayAdapter<CartaoCredito> {

    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;
    private Date data;
    private int corSaldoNegativo = 0;
    private int corSaldoPositivo = 0;
    private String textoPreviso;

    public void setData(Date data) {
        this.data = data;
    }


    public AdapterCartaoCredito(Context applicationContext, int layoutResource) {

        super(applicationContext, layoutResource);
        this.context = applicationContext;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = layoutResource;
        this.symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();
        this.corSaldoNegativo = ColorHelper.getColor(this.context, R.color.corPendencia);
        this.corSaldoPositivo = ColorHelper.getColor(this.context, R.color.corResolvido);
        this.textoPreviso = this.context.getResources().getString(R.string.lblPrevisto);
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {

        return getCustomDropDownView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View currentView, ViewGroup viewGroup) {

        View row;

        if (this.resource == R.layout.item_cartao_credito_simples) {
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

            viewHolderSimple.imgCartao = (ImageView) view.findViewById(R.id.imgCartao);
            //viewHolderSimple.imgCirculo = (ImageView) view.findViewById(R.id.imgCirculo);
            viewHolderSimple.txtNomeCartao = (TextView) view.findViewById(R.id.txtNomeCartao);

            view.setTag(viewHolderSimple);

            convertView = view;

        } else {
            viewHolderSimple = (ViewHolderSimple) convertView.getTag();
            view = convertView;
        }


        CartaoCredito cartaoCredito = getItem(position);

        // Drawable circle = viewHolderSimple.imgCirculo.getDrawable();
        // circle.setColorFilter(ColorHelper.getColor(this.context, cartaoCredito.getNoCor()), PorterDuff.Mode.MULTIPLY);

        viewHolderSimple.imgCartao.setImageResource(CartaoCredito.getImagemBandeiraCartao(cartaoCredito.getNoBandeiraCartao()));

        viewHolderSimple.txtNomeCartao.setText(cartaoCredito.getNome());

        // if (cartaoCredito.getNoCorIcone() != 0)
        //    viewHolderSimple.imgFatura.setColorFilter(ColorHelper.getColor(this.context, conta.getNoCorIcone()));

        return view;

    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        View view = null;

       final CartaoCredito cartaoCredito = getItem(position);

        if (convertView == null) {

            view = inflater.inflate(this.resource, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imgCartao = (ImageView) view.findViewById(R.id.imgCartaoItem);
            viewHolder.txtNomeCartao = (TextView) view.findViewById(R.id.txtNomeCartaoItem);
            viewHolder.txtValorFaturaFechada = (TextView) view.findViewById(R.id.txtValorFaturaFechada);
            viewHolder.txtValorFaturaAberta = (TextView) view.findViewById(R.id.txtValorFaturaAberta);
            viewHolder.txtSaldoPrevistoData = (TextView) view.findViewById(R.id.txtSaldoPrevistoDataItem);
            viewHolder.imgCirculo = (ImageView) view.findViewById(R.id.imgCartaoItemCir);


            viewHolder.imgBtnAddDespesas = (ImageView) view.findViewById(R.id.imgBtnAddDespesas);
            viewHolder.imgBtnListaDespesas = (ImageView) view.findViewById(R.id.imgBtnListaDespesas);
            viewHolder.imgBtnEdit = (ImageView) view.findViewById(R.id.imgBtnEdit);

            viewHolder.imgBtnAddDespesas.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent it = new Intent(getContext(), actCadDespesaCartaoCredito.class);
                    it.putExtra(actCadDespesaCartaoCredito.PARAM_CARTAO_CREDITO_ID, cartaoCredito.getId());
                    getContext().startActivity(it);
                }
            });

            viewHolder.imgBtnListaDespesas.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent it = new Intent(getContext(), actDespesaCartaoCredito.class);
                    it.putExtra(actDespesaCartaoCredito.PARAM_CARTAO_CREDITO, cartaoCredito);
                    getContext().startActivity(it);
                }
            });


            viewHolder.imgBtnEdit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent it = new Intent(getContext(), actCadCartaoCredito.class);
                    it.putExtra(actCadCartaoCredito.PARAM_CARTAO_CREDITO, cartaoCredito);
                    getContext().startActivity(it);
                }
            });

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }



        Drawable circle = viewHolder.imgCirculo.getDrawable();
        circle.setColorFilter(ColorHelper.getColor(this.context, cartaoCredito.getNoCor()), PorterDuff.Mode.MULTIPLY);


        // String texto = String.valueOf(DateUtils.getLastDayOfMonth(this.data)) + "/" + String.valueOf(DateUtils.getMonthNameShort(this.data));
        // texto = texto + " (" + this.textoPreviso + ")";

        viewHolder.imgCartao.setImageResource(CartaoCredito.getImagemBandeiraCartao(cartaoCredito.getNoBandeiraCartao()));
        viewHolder.txtNomeCartao.setText(cartaoCredito.getNome());
        viewHolder.txtValorFaturaFechada.setText(this.symbol + " " + NumberUtis.getFormartCurrency(cartaoCredito.getDespesasPrevistas()));
        //  viewHolder.txtSaldoPrevistoData.setText(texto);
        // viewHolder.txtSaldoPrevistoValor.setText(this.symbol + " " + NumberUtis.getFormartCurrency(cartaoCredito.getSaldoPrevisto()));

        //  if (cartaoCredito.getNoCorIcone() != 0)
        //     viewHolder.imgFatura.setColorFilter(ColorHelper.getColor(this.context, cartaoCredito.getNoCorIcone()));


      /*  if (cartaoCredito.getValorSaldo() < 0) {
            viewHolder.txtSaldoAtual.setTextColor(this.corSaldoNegativo);
        } else {
            viewHolder.txtSaldoAtual.setTextColor(this.corSaldoPositivo);
        }

        if (cartaoCredito.getSaldoPrevisto() < 0) {
            viewHolder.txtSaldoPrevistoValor.setTextColor(this.corSaldoNegativo);
        } else {
            viewHolder.txtSaldoPrevistoValor.setTextColor(this.corSaldoPositivo);
        }
*/
        return view;
    }


    public static class ViewHolder {
        ImageView imgCirculo;
        ImageView imgCartao;
        TextView txtNomeCartao;
        TextView txtValorFaturaFechada;
        TextView txtSaldoPrevistoData;
        TextView txtValorFaturaAberta;

        ImageView imgBtnAddDespesas;
        ImageView imgBtnListaDespesas;
        ImageView imgBtnEdit;


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
        ImageView imgCartao;
        ImageView imgCirculo;
        TextView txtNomeCartao;
    }


}
