package com.charlessodre.apps.gerenciadorfinanceiroisis.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by charl on 25/09/2016.
 */

public class AdapterImagem extends ArrayAdapter<Integer> {


    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;

    public AdapterImagem(Context applicationContext, int resource) {
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

            viewHolder.imgCategoria = (ImageView) view.findViewById(R.id.imgIcone);

            view.setTag(viewHolder);

            convertView = view;

        } else

        {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        viewHolder.imgCategoria.setImageResource(getItem(position));

        return view;
    }


    public int getIndexFromElement(long id) {
        for (int i = 0; i < this.getCount(); i++) {
            /*if (this.getItem(i).getId() == id) {
                return i;
            }*/
        }
        return -1;
    }

    public static class ViewHolder {
        ImageView imgCategoria;

    }

}
