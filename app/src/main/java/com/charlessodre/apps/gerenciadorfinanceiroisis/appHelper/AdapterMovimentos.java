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
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Despesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Transferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.NumberUtis;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by charl on 19/09/2016.
 */
public class AdapterMovimentos extends ArrayAdapter<Object> {

    private final LayoutInflater inflater;
    private Context context;
    private int resource = 0;
    private String symbol;
    private Date data;
    private int corRecebida = 0;
    private int corPendente = 0;
    private String textoRecebido;
    private String textoPendente;
    private String descricaoTransacao;
    private String descricaoMovimento;
    private String categoriaTransacao;
    private String descricaoContaMovimento;
    private int colorCircle = -1;
    private int colorIcone = -1;
    private int noIcone = -1;
    private Double valor = 0.0;
    private boolean efetivada;
    private boolean fixo = false;
    private int totalRepeticao = 0;
    private int repeticaoAtual = 0;
    private Receita receita;
    private Despesa despesa;
    private Transferencia transferencia;
    private Conta conta = null;
    private Conta contaDestino = null;


    public AdapterMovimentos(Context applicationContext, int layoutResource) {

        super(applicationContext, layoutResource);
        this.context = applicationContext;
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = layoutResource;
        this.symbol = NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();

        this.corRecebida = ColorHelper.getColor(this.context, R.color.corResolvido);
        this.corPendente = ColorHelper.getColor(this.context, R.color.corPendencia);
        this.textoPendente = this.context.getResources().getString(R.string.lblPendente);

    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {

        return null;
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

            viewHolder.imgMovimento = (ImageView) view.findViewById(R.id.imgMovimentoItem);
            viewHolder.txtNomeMovimento = (TextView) view.findViewById(R.id.txtNomeMovimentoItem);
            viewHolder.txtTipoMovimento = (TextView) view.findViewById(R.id.txtTipoMovimentoItem);
            viewHolder.txtStatusMovimento = (TextView) view.findViewById(R.id.txtStatusMovimentoItem);
            viewHolder.txtValorMovimento = (TextView) view.findViewById(R.id.txtValorMovimentoItem);
            viewHolder.txtDataMovimento = (TextView) view.findViewById(R.id.txtDataMovimentoItem);
            viewHolder.imgCirculo = (ImageView) view.findViewById(R.id.imgMovimentoItemCir);
            viewHolder.txtContaMovimentoItem = (TextView) view.findViewById(R.id.txtContaMovimentoItem);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Object movimento = getItem(position);

        if (movimento instanceof Despesa) {
            despesa = (Despesa) movimento;

            colorCircle = R.color.corTelaDespesas;
            descricaoTransacao = this.context.getResources().getString(R.string.lblDespesa);
            noIcone = despesa.getCategoriaDespesa().getNoIcone();
            descricaoMovimento = despesa.getNome();
            valor = despesa.getValor();
            data = despesa.getDataDespesa();
            categoriaTransacao = despesa.getCategoriaDespesa().getNome();
            efetivada = despesa.isPaga();
            fixo = despesa.isFixa();
            totalRepeticao = despesa.getTotalRepeticao();
            repeticaoAtual = despesa.getRepeticaoAtual();
            conta = despesa.getConta();
            descricaoContaMovimento = conta.getNome();
            this.textoRecebido = this.context.getResources().getString(R.string.lblPago);

            viewHolder.imgMovimento.setImageResource(noIcone);

        } else if (movimento instanceof Receita) {
            receita = (Receita) movimento;

            colorCircle = R.color.corTelaReceitas;
            descricaoTransacao = this.context.getResources().getString(R.string.lblReceita);
            noIcone = receita.getCategoriaReceita().getNoIcone();
            descricaoMovimento = receita.getNome();
            valor = receita.getValor();
            data = receita.getDataReceita();
            categoriaTransacao = receita.getCategoriaReceita().getNome();
            efetivada = receita.isPaga();
            fixo = receita.isFixa();
            totalRepeticao = receita.getTotalRepeticao();
            repeticaoAtual = receita.getRepeticaoAtual();
            conta = receita.getConta();
            descricaoContaMovimento = conta.getNome();
            this.textoRecebido = this.context.getResources().getString(R.string.lblRecebido);

            viewHolder.imgMovimento.setImageResource(noIcone);

        } else if (movimento instanceof Transferencia) {

            transferencia = (Transferencia) movimento;

            colorCircle = R.color.corTelaTransferencias;
            descricaoTransacao = this.context.getResources().getString(R.string.lblTransferencia);
            valor = transferencia.getValor();
            data = transferencia.getDataTransferencia();

            if (transferencia.getContaOrigem().getId() == transferencia.getId()) {
                noIcone = corRecebida;
                descricaoMovimento = this.context.getResources().getString(R.string.lblEntrada);
            } else {

                noIcone = corPendente;
                descricaoMovimento = this.context.getResources().getString(R.string.lblSaida);
            }

            conta = transferencia.getContaOrigem();
            contaDestino = transferencia.getContaDestino();

            descricaoContaMovimento = descricaoContaMovimento + " | " + this.context.getResources().getString(R.string.lblOrigem);

            categoriaTransacao = this.context.getResources().getString(R.string.lblDestino) +" "+ contaDestino.getNome();

            viewHolder.imgMovimento.setImageResource(R.drawable.ic_import_export_black_24dp);

            this.textoRecebido = this.context.getResources().getString(R.string.lblEfetivada);
            efetivada = true;

        }

        Drawable circle = viewHolder.imgCirculo.getDrawable();
        circle.setColorFilter(ColorHelper.getColor(this.context, colorCircle), PorterDuff.Mode.MULTIPLY);

        String tipoMovimento = categoriaTransacao;


            if (efetivada) {
                viewHolder.txtStatusMovimento.setText(this.textoRecebido);
                viewHolder.txtStatusMovimento.setTextColor(this.corRecebida);
                viewHolder.txtValorMovimento.setTextColor(this.corRecebida);
            } else {
                viewHolder.txtStatusMovimento.setText(this.textoPendente);
                viewHolder.txtStatusMovimento.setTextColor(this.corPendente);
                viewHolder.txtValorMovimento.setTextColor(this.corPendente);
            }

        if ((movimento instanceof Transferencia) == false) {

            if (fixo) {
                tipoMovimento = tipoMovimento + " | " + this.context.getResources().getString(R.string.lblFixa);

            } else if (totalRepeticao > 0) {
                String repeticao = repeticaoAtual + " " + this.context.getResources().getString(R.string.lblDe) + " " + totalRepeticao;

                tipoMovimento = tipoMovimento + " | " + repeticao;
            }

        }
        viewHolder.txtTipoMovimento.setText(tipoMovimento);

        descricaoContaMovimento = conta.getNome() + " | " + descricaoTransacao;

        viewHolder.txtNomeMovimento.setText(descricaoMovimento);

        viewHolder.txtContaMovimentoItem.setText(descricaoContaMovimento);

        viewHolder.txtValorMovimento.setText(this.symbol + " " + NumberUtis.getFormartCurrency(valor));

        viewHolder.txtDataMovimento.setText(String.valueOf(DateUtils.getWeekNameAndDay(data)));

        return view;
    }


    public static class ViewHolder {
        ImageView imgCirculo;
        ImageView imgMovimento;
        TextView txtNomeMovimento;
        TextView txtTipoMovimento;
        TextView txtStatusMovimento;
        TextView txtValorMovimento;
        TextView txtDataMovimento;
        TextView txtContaMovimentoItem;

    }


}
