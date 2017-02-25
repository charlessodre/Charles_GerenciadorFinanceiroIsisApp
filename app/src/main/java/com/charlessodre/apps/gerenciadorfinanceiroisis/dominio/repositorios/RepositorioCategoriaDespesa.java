package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaDespesa;

import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;

import java.util.ArrayList;

/**
 * Created by charl on 24/09/2016.
 */

public class RepositorioCategoriaDespesa extends RepositorioBase implements IRepositorio<CategoriaDespesa, Long> {


    //Construtor
    public RepositorioCategoriaDespesa(Context context) {
        super(context, CategoriaDespesa.TABELA_NOME);
    }

    //Métodos


    @Override
    public int altera(CategoriaDespesa item) {
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
    public long insere(CategoriaDespesa item) {
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
    public int exclui(CategoriaDespesa item) {
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
    public CategoriaDespesa get(Long id) {
        return null;
    }

    private ContentValues preencheContentValues(CategoriaDespesa categoriaDespesa) {

        ContentValues values = new ContentValues();

        values.put(CategoriaDespesa.NM_CATEGORIA, categoriaDespesa.getNome());

        values.put(CategoriaDespesa.FL_ATIVO, BooleanUtils.parseBooleanToint(categoriaDespesa.isAtivo()));
        values.put(CategoriaDespesa.FL_EXIBIR, BooleanUtils.parseBooleanToint(categoriaDespesa.isExibir()));
        values.put(CategoriaDespesa.DT_INCLUSAO, categoriaDespesa.getDataInclusao().getTime());
        values.put(CategoriaDespesa.NO_ORDEM_EXIBICAO, categoriaDespesa.getOrdemExibicao());
        values.put(CategoriaDespesa.NO_COR, categoriaDespesa.getNoCor());
        values.put(CategoriaDespesa.NO_ICONE, categoriaDespesa.getNoIcone());

        if (categoriaDespesa.getDataAlteracao() != null)
            values.put(CategoriaDespesa.DT_ALTERACAO, categoriaDespesa.getDataAlteracao().getTime());

        return values;
    }

    private ArrayList<CategoriaDespesa> preencheObjeto(SQLiteDatabase transaction,Cursor cursor) {
        ArrayList<CategoriaDespesa> arrayList = new ArrayList<CategoriaDespesa>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                CategoriaDespesa categoriaDespesa = new CategoriaDespesa();


                categoriaDespesa.setId(cursor.getLong(cursor.getColumnIndex(CategoriaDespesa.ID)));
                categoriaDespesa.setNome(cursor.getString(cursor.getColumnIndex(CategoriaDespesa.NM_CATEGORIA)));
                categoriaDespesa.setOrdemExibicao(cursor.getInt(cursor.getColumnIndex(CategoriaDespesa.NO_ORDEM_EXIBICAO)));

                categoriaDespesa.setNoCor(cursor.getInt(cursor.getColumnIndex(CategoriaDespesa.NO_COR)));
                categoriaDespesa.setNoIcone(cursor.getInt(cursor.getColumnIndex(CategoriaDespesa.NO_ICONE)));

                categoriaDespesa.setExibir(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(CategoriaDespesa.FL_EXIBIR))));
                categoriaDespesa.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(CategoriaDespesa.FL_ATIVO))));

                arrayList.add(categoriaDespesa);

            } while (cursor.moveToNext());
        }

        return arrayList;

    }

    public ArrayList<CategoriaDespesa> buscaTodos() {
        try {



            super.openConnectionWrite();

            Cursor cursor = super.selectAll(CategoriaDespesa.NO_ORDEM_EXIBICAO + " , " + CategoriaDespesa.NM_CATEGORIA);

            return this.preencheObjeto(super.getTransaction(),cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public int excluiComDependentes(CategoriaDespesa item) {
        try {
            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioDespesa repositorioDespesa = new RepositorioDespesa(super.getContext());
            RepositorioSubCategoriaDespesa subCategoriaDespesa = new RepositorioSubCategoriaDespesa(super.getContext());

            repositorioDespesa.excluiDespesasDaCategoriaComEstorno(super.getTransaction(),item.getId());

            subCategoriaDespesa.excluiSubCategoriaDespesa(super.getTransaction(),item.getId());

            super.delete(super.getTransaction(),item.getId());

            super.setTransactionSuccessful();
            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }


    public CategoriaDespesa get(SQLiteDatabase transaction, Long id) {
        String where = CategoriaDespesa.ID + "=" + id;

        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();

        try {


            Cursor cursor = super.select(transaction, where);

            ArrayList<CategoriaDespesa> arrayList = this.preencheObjeto(transaction, cursor);

            if (arrayList.size() > 0)
                categoriaDespesa = arrayList.get(0);

            return categoriaDespesa;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_despesa));
        }
    }

}