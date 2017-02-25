package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

/**
 * Created by charl on 18/09/2016.
 */
public class AdapterColorsCicle extends ArrayAdapter<Integer> {
    private final LayoutInflater inflater;
    private Context context;

    public AdapterColorsCicle(Context applicationContext, int resource) {
        super(applicationContext, resource);

        context = applicationContext;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        View view = null;

        if (convertView == null) {

            view = inflater.inflate(R.layout.item_circulo, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imgCirculo = (ImageView) view.findViewById(R.id.imageView);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        //Obtém a cor referente a esta posição
        int color = getColor(position);
        // Obtém a referência ao circlo
        Drawable circle = viewHolder.imgCirculo.getDrawable();
        //Atribui a cor
        circle.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        return view;
    }

    @Override
    public Integer getItem(int position) {
        return super.getItem(position);
    }

    public int getColor(int position) {
        return Color.parseColor(this.context.getResources().getString(getItem(position)));
    }

    public static class ViewHolder {
        ImageView imgCirculo;
    }
}