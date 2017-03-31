package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.RegraImportacaoSMS;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by charl on 24/09/2016.
 */

public class RepositorioRegraImpSMS extends RepositorioBase implements IRepositorio<RegraImportacaoSMS, Long> {


    //Construtor
    public RepositorioRegraImpSMS(Context context) {
        super(context, RegraImportacaoSMS.TABELA_NOME);
    }

    //Métodos

    @Override
    public int altera(RegraImportacaoSMS item) {
        try {
            super.openConnectionWrite();
            return super.update(preencheContentValues(item), item.getId());
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro));
        } finally {
            super.closeConnection();
        }

    }

    @Override
    public long insere(RegraImportacaoSMS item) {
        try {
            super.openConnectionWrite();
            return super.insert(preencheContentValues(item));
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro));
        } finally {
            super.closeConnection();
        }
    }

    @Override
    public int exclui(RegraImportacaoSMS item) {
        try {
            super.openConnectionWrite();
            return super.delete(item.getId());
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro));
        } finally {
            super.closeConnection();
        }
    }

    @Override
    public RegraImportacaoSMS get(Long id) {
        return null;
    }

    private ContentValues preencheContentValues(RegraImportacaoSMS regraImportacaoSMS) {

        ContentValues values = new ContentValues();


        values.put(RegraImportacaoSMS.NM_REGRA_IMPORTACAO, regraImportacaoSMS.getNome());
        values.put(RegraImportacaoSMS.DS_TEXTO_PESQUISA_1, regraImportacaoSMS.getTextoPesquisa1());
        values.put(RegraImportacaoSMS.DS_TEXTO_PESQUISA_2, regraImportacaoSMS.getTextoPesquisa2());
        values.put(RegraImportacaoSMS.NO_TELEFONE, regraImportacaoSMS.getNoTelefone());
        values.put(RegraImportacaoSMS.FL_ATIVO, BooleanUtils.parseBooleanToint(regraImportacaoSMS.isAtivo()));
        values.put(RegraImportacaoSMS.DT_INCLUSAO, regraImportacaoSMS.getDataInclusao().getTime());
        values.put(RegraImportacaoSMS.ID_CATEGORIA_DESPESA, regraImportacaoSMS.getCategoriaDespesa().getId());
        values.put(RegraImportacaoSMS.ID_CATEGORIA_RECEITA, regraImportacaoSMS.getCategoriaReceita().getId());
        values.put(RegraImportacaoSMS.ID_SUB_CATEGORIA_DESPESA, regraImportacaoSMS.getSubCategoriaDespesa().getId());
        values.put(RegraImportacaoSMS.ID_CONTA_ORIGEM, regraImportacaoSMS.getContaOrigem().getId());
        values.put(RegraImportacaoSMS.ID_CONTA_DESTINO, regraImportacaoSMS.getContaDestino().getId());
        values.put(RegraImportacaoSMS.ID_TIPO_TRANSACAO, regraImportacaoSMS.getIdTipoTransacao());
        values.put(RegraImportacaoSMS.DS_RECEITA_DESPESA, regraImportacaoSMS.getDescricaoReceitaDespesa());

        if (regraImportacaoSMS.getDataAlteracao() != null)
            values.put(RegraImportacaoSMS.DT_ALTERACAO, regraImportacaoSMS.getDataAlteracao().getTime());

        return values;
    }

    private ArrayList<RegraImportacaoSMS> preencheObjeto(SQLiteDatabase transaction,Cursor cursor) {
        ArrayList<RegraImportacaoSMS> arrayList = new ArrayList<RegraImportacaoSMS>();

        RepositorioCategoriaReceita repCategoriaReceita = new RepositorioCategoriaReceita(super.getContext());
        RepositorioConta repConta = new RepositorioConta(super.getContext());
        RepositorioCategoriaDespesa repCategoriaDespesa = new RepositorioCategoriaDespesa(super.getContext());
        RepositorioSubCategoriaDespesa repSubCategoriaDespesa = new RepositorioSubCategoriaDespesa(super.getContext());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                RegraImportacaoSMS regraImportacaoSMS = new RegraImportacaoSMS();


                regraImportacaoSMS.setId(cursor.getLong(cursor.getColumnIndex(RegraImportacaoSMS.ID)));
                regraImportacaoSMS.setNome(cursor.getString(cursor.getColumnIndex(RegraImportacaoSMS.NM_REGRA_IMPORTACAO)));
                regraImportacaoSMS.setNoTelefone(cursor.getString(cursor.getColumnIndex(RegraImportacaoSMS.NO_TELEFONE)));
                regraImportacaoSMS.setTextoPesquisa1(cursor.getString(cursor.getColumnIndex(RegraImportacaoSMS.DS_TEXTO_PESQUISA_1)));
                regraImportacaoSMS.setTextoPesquisa2(cursor.getString(cursor.getColumnIndex(RegraImportacaoSMS.DS_TEXTO_PESQUISA_2)));
                regraImportacaoSMS.setIdTipoTransacao(cursor.getInt(cursor.getColumnIndex(RegraImportacaoSMS.ID_TIPO_TRANSACAO)));

                regraImportacaoSMS.setCategoriaReceita(repCategoriaReceita.getCatergoriaReceita(transaction, cursor.getLong(cursor.getColumnIndex(RegraImportacaoSMS.ID_CATEGORIA_RECEITA))));
                regraImportacaoSMS.setContaOrigem(repConta.get(transaction, cursor.getLong(cursor.getColumnIndex(RegraImportacaoSMS.ID_CONTA_ORIGEM))));
                regraImportacaoSMS.setContaDestino(repConta.get(transaction, cursor.getLong(cursor.getColumnIndex(RegraImportacaoSMS.ID_CONTA_DESTINO))));

                regraImportacaoSMS.setCategoriaDespesa(repCategoriaDespesa.get(transaction,cursor.getLong(cursor.getColumnIndex(RegraImportacaoSMS.ID_CATEGORIA_DESPESA))));
                regraImportacaoSMS.setSubCategoriaDespesa(repSubCategoriaDespesa.get(transaction, cursor.getLong(cursor.getColumnIndex(RegraImportacaoSMS.ID_SUB_CATEGORIA_DESPESA))));

                regraImportacaoSMS.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(RegraImportacaoSMS.FL_ATIVO))));

                regraImportacaoSMS.setDataAlteracao(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(RegraImportacaoSMS.DT_ALTERACAO))));
                regraImportacaoSMS.setDataInclusao(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(RegraImportacaoSMS.DT_INCLUSAO))));
                regraImportacaoSMS.setDescricaoReceitaDespesa(cursor.getString(cursor.getColumnIndex(RegraImportacaoSMS.DS_RECEITA_DESPESA)));

                arrayList.add(regraImportacaoSMS);

            } while (cursor.moveToNext());
        }

        return arrayList;

    }

    public ArrayList<RegraImportacaoSMS> buscaTodos() {
        try {

            super.openConnectionRead();

            Cursor cursor = super.selectAll(RegraImportacaoSMS.NO_TELEFONE + " , " + RegraImportacaoSMS.NM_REGRA_IMPORTACAO);

            return this.preencheObjeto(super.getTransaction(),cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public RegraImportacaoSMS get(SQLiteDatabase transaction, Long id) {
        String where = RegraImportacaoSMS.ID + "=" + id;

        RegraImportacaoSMS regraImportacaoSMS = new RegraImportacaoSMS();

        try {


            Cursor cursor = super.select(transaction, where);

            ArrayList<RegraImportacaoSMS> arrayList = this.preencheObjeto(transaction, cursor);

            if (arrayList.size() > 0)
                regraImportacaoSMS = arrayList.get(0);

            return regraImportacaoSMS;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        }
    }

}