package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;

import java.util.ArrayList;

/**
 * Created by charl on 24/09/2016.
 */

public class RepositorioCategoriaReceita extends RepositorioBase implements IRepositorio<CategoriaReceita, Long> {


    //Construtor
    public RepositorioCategoriaReceita(Context context) {
        super(context, CategoriaReceita.TABELA_NOME);
    }

    @Override
    public int altera(CategoriaReceita item) {
        try {
            super.openConnectionWrite();
            return super.update(preencheContentValues(item), item.getId());
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_categoria_receita));
        } finally {
            super.closeConnection();
        }
    }

    @Override
    public long insere(CategoriaReceita item) {
        try {
            super.openConnectionWrite();
            return super.insert(preencheContentValues(item));
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_categoria_receita));
        } finally {
            super.closeConnection();
        }
    }

    @Override
    public int exclui(CategoriaReceita item) {
        try {
            super.openConnectionWrite();
            return super.delete(item.getId());
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_categoria_receita));
        } finally {
            super.closeConnection();
        }
    }

    @Override
    public CategoriaReceita get(Long id) {

        String where = CategoriaReceita.ID + "=" + id;

        CategoriaReceita categoriaReceita = new CategoriaReceita();
        Cursor cursor = null;

        try {
            super.openConnectionWrite();
            cursor = super.select(where);

            ArrayList<CategoriaReceita> arrayList = this.preencheObjeto(cursor);

            if (arrayList.size() > 0)
                categoriaReceita = arrayList.get(0);

            return categoriaReceita;


        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_categoria_receita));
        } finally {
            if (cursor != null)
                cursor.close();
            super.closeConnection();
        }
    }

    //Métodos
    private ContentValues preencheContentValues(CategoriaReceita categoriacategoriaReceitapesa) {

        ContentValues values = new ContentValues();

        values.put(CategoriaReceita.NM_CATEGORIA, categoriacategoriaReceitapesa.getNome());

        values.put(CategoriaReceita.FL_ATIVO, BooleanUtils.parseBooleanToint(categoriacategoriaReceitapesa.isAtivo()));
        values.put(CategoriaReceita.FL_EXIBIR, BooleanUtils.parseBooleanToint(categoriacategoriaReceitapesa.isExibir()));
        values.put(CategoriaReceita.DT_INCLUSAO, categoriacategoriaReceitapesa.getDataInclusao().getTime());
        values.put(CategoriaReceita.NO_ORDEM_EXIBICAO, categoriacategoriaReceitapesa.getOrdemExibicao());

        values.put(CategoriaReceita.NO_COR, categoriacategoriaReceitapesa.getNoCor());
        values.put(CategoriaReceita.NO_ICONE, categoriacategoriaReceitapesa.getNoIcone());
        values.put(CategoriaReceita.NO_COR_ICONE, categoriacategoriaReceitapesa.getNoCorIcone());


        if (categoriacategoriaReceitapesa.getDataAlteracao() != null)
            values.put(CategoriaReceita.DT_ALTERACAO, categoriacategoriaReceitapesa.getDataAlteracao().getTime());

        return values;
    }

    private ArrayList<CategoriaReceita> preencheObjeto(Cursor cursor) {
        ArrayList<CategoriaReceita> arrayList = new ArrayList<CategoriaReceita>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                CategoriaReceita categoriaReceita = new CategoriaReceita();

                categoriaReceita.setId(cursor.getLong(cursor.getColumnIndex(CategoriaReceita.ID)));
                categoriaReceita.setNome(cursor.getString(cursor.getColumnIndex(CategoriaReceita.NM_CATEGORIA)));
                categoriaReceita.setOrdemExibicao(cursor.getInt(cursor.getColumnIndex(CategoriaReceita.NO_ORDEM_EXIBICAO)));

                categoriaReceita.setNoCor(cursor.getInt(cursor.getColumnIndex(CategoriaReceita.NO_COR)));
                categoriaReceita.setNoIcone(cursor.getInt(cursor.getColumnIndex(CategoriaReceita.NO_ICONE)));
                categoriaReceita.setNoCorIcone(cursor.getInt(cursor.getColumnIndex(CategoriaReceita.NO_COR_ICONE)));

                categoriaReceita.setExibir(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(CategoriaReceita.FL_EXIBIR))));
                categoriaReceita.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(CategoriaReceita.FL_ATIVO))));

                arrayList.add(categoriaReceita);

            } while (cursor.moveToNext());
        }

        return arrayList;

    }


    public ArrayList<CategoriaReceita> getAll() {

        Cursor cursor = null;
        try {

            super.openConnectionWrite();

            cursor = super.selectAll(CategoriaReceita.NO_ORDEM_EXIBICAO + " , " + CategoriaReceita.NM_CATEGORIA);

            return this.preencheObjeto(cursor);


        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_categoria_receita));
        } finally {

            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }
    }


    public int excluiComDependentes(CategoriaReceita item) {
        try {
            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioReceita repositorioReceita = new RepositorioReceita(super.getContext());

            repositorioReceita.excluiReceitasCategoria(super.getTransaction(), item.getId());

            super.delete(super.getTransaction(), item.getId());

            super.setTransactionSuccessful();
            return 1;
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_categoria_receita));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    public CategoriaReceita getCatergoriaReceita(SQLiteDatabase transaction, Long id) {

        String where = CategoriaReceita.ID + "=" + id;

        CategoriaReceita categoriaReceita = new CategoriaReceita();
        Cursor cursor = null;

        try {

            cursor = super.select(transaction, where);

            ArrayList<CategoriaReceita> arrayList = this.preencheObjeto(cursor);

            if (arrayList.size() > 0)
                categoriaReceita = arrayList.get(0);


            return categoriaReceita;


        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_categoria_receita));
        } finally {

            if (cursor != null)
                cursor.close();
        }
    }

}