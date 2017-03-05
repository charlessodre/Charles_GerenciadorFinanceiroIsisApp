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
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SubCategoriaDespesa;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by charl on 27/09/2016.
 */

public class AdapterSubCategoriaDespesa extends ArrayAdapter<SubCategoriaDespesa> {

    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;

    public AdapterSubCategoriaDespesa(Context applicationContext, int resource) {

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

            viewHolder.imgCirculo = (ImageView) view.findViewById(R.id.imgSubCategoria);
            viewHolder.imgIconeSubCategoria = (ImageView) view.findViewById(R.id.imgIconeSubCategoria);
            viewHolder.txtNomeSubCategoria = (TextView) view.findViewById(R.id.txtNomeSubCategoria);

            view.setTag(viewHolder);
            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        SubCategoriaDespesa subCategoriaDespesa = getItem(position);

        String nome;
        int color;

        if (subCategoriaDespesa == null) {
            nome = context.getResources().getString(R.string.lblNovaSubCategoria);
            viewHolder.imgIconeSubCategoria.setVisibility(View.VISIBLE);
            color = ColorHelper.getColor(this.context, R.color.novoItemSpinner);

        } else {

            nome = subCategoriaDespesa.getNome();
            color = ColorHelper.getColor(this.context, subCategoriaDespesa.getNoCor());
        }

        Drawable circle = viewHolder.imgCirculo.getDrawable();
        circle.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        viewHolder.txtNomeSubCategoria.setText(nome);
        //viewHolder.txtNomeSubCategoria.setTextColor(color);

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
        ImageView imgCirculo;
        ImageView imgIconeSubCategoria;
        TextView txtNomeSubCategoria;
    }


}

