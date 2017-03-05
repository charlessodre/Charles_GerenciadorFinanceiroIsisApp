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
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaReceita;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by charl on 25/09/2016.
 */

public class AdapterCategoriaReceita extends ArrayAdapter<CategoriaReceita> {


    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;

    public AdapterCategoriaReceita(Context applicationContext, int resource) {
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

            viewHolder.imgCategoria = (ImageView) view.findViewById(R.id.imgCategoria);
            viewHolder.txtNomeCategoria = (TextView) view.findViewById(R.id.txtNomeCategoria);
            viewHolder.imgCirculo = (ImageView) view.findViewById(R.id.imgCategoriaCir);

            view.setTag(viewHolder);
            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        CategoriaReceita categoriaReceita = getItem(position);

        int color = ColorHelper.getColor(this.context, categoriaReceita.getNoCor());
        Drawable circle = viewHolder.imgCirculo.getDrawable();
        circle.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        viewHolder.imgCategoria.setImageResource(categoriaReceita.getNoIcone());
        //  viewHolder.txtNomeCategoria.setTextColor(ColorHelper.getColor(this.context,categoriaReceita.getNoCor()));
        viewHolder.txtNomeCategoria.setText(categoriaReceita.getNome());

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
        ImageView imgCategoria;
        TextView txtNomeCategoria;
    }

}
