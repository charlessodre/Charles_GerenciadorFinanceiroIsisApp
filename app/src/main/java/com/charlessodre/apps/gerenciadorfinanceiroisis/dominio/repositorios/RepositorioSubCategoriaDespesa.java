package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaDespesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.SubCategoriaDespesa;

import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;

import java.util.ArrayList;

/**
 * Created by charl on 27/09/2016.
 */

public class RepositorioSubCategoriaDespesa extends RepositorioBase implements IRepositorio<SubCategoriaDespesa, Long> {


    //Construtor
    public RepositorioSubCategoriaDespesa(Context context) {
        super(context, SubCategoriaDespesa.TABELA_NOME);
    }

    //Métodos
    private ContentValues preencheContentValues(SubCategoriaDespesa subCategoriaDespesa) {

        ContentValues values = new ContentValues();

        values.put(subCategoriaDespesa.NM_SUB_CATEGORIA, subCategoriaDespesa.getNome());

        values.put(subCategoriaDespesa.FL_ATIVO, BooleanUtils.parseBooleanToint(subCategoriaDespesa.isAtivo()));
        values.put(subCategoriaDespesa.FL_EXIBIR, BooleanUtils.parseBooleanToint(subCategoriaDespesa.isExibir()));
        values.put(subCategoriaDespesa.DT_INCLUSAO, subCategoriaDespesa.getDataInclusao().getTime());
        values.put(subCategoriaDespesa.NO_ORDEM_EXIBICAO, subCategoriaDespesa.getOrdemExibicao());
        values.put(subCategoriaDespesa.ID_CATEGORIA_DESPESA, subCategoriaDespesa.getIdCategoriaDespesa());

        values.put(subCategoriaDespesa.NO_COR, subCategoriaDespesa.getNoCor());


        if (subCategoriaDespesa.getDataAlteracao() != null)
            values.put(subCategoriaDespesa.DT_ALTERACAO, subCategoriaDespesa.getDataAlteracao().getTime());

        return values;
    }

    private ArrayList<SubCategoriaDespesa> preencheObjeto(SQLiteDatabase transaction,Cursor cursor) {
        ArrayList<SubCategoriaDespesa> arrayList = new ArrayList<SubCategoriaDespesa>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                SubCategoriaDespesa subCategoriaDespesa = new SubCategoriaDespesa();


                subCategoriaDespesa.setId(cursor.getLong(cursor.getColumnIndex(subCategoriaDespesa.ID)));
                subCategoriaDespesa.setNome(cursor.getString(cursor.getColumnIndex(subCategoriaDespesa.NM_SUB_CATEGORIA)));
                subCategoriaDespesa.setOrdemExibicao(cursor.getInt(cursor.getColumnIndex(subCategoriaDespesa.NO_ORDEM_EXIBICAO)));

                subCategoriaDespesa.setNoCor(cursor.getInt(cursor.getColumnIndex(subCategoriaDespesa.NO_COR)));

                subCategoriaDespesa.setExibir(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(subCategoriaDespesa.FL_EXIBIR))));
                subCategoriaDespesa.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(subCategoriaDespesa.FL_ATIVO))));

                subCategoriaDespesa.setIdCategoriaDespesa(cursor.getLong(cursor.getColumnIndex(subCategoriaDespesa.ID_CATEGORIA_DESPESA)));

                arrayList.add(subCategoriaDespesa);

            } while (cursor.moveToNext());
        }

        return arrayList;

    }

    @Override
    public int altera(SubCategoriaDespesa item) {
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
    public long insere(SubCategoriaDespesa item) {
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
    public int exclui(SubCategoriaDespesa item) {
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
    public SubCategoriaDespesa get(Long id) {
        return null;
    }

    public ArrayList<SubCategoriaDespesa> buscaTodos() {
        try {

            super.openConnectionWrite();

            Cursor cursor = super.selectAll(SubCategoriaDespesa.NO_ORDEM_EXIBICAO + " , " + SubCategoriaDespesa.NM_SUB_CATEGORIA);

            return this.preencheObjeto(super.getTransaction(),cursor);


        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public ArrayList<SubCategoriaDespesa> buscaPorIdCategoriaDespesa(Long idCategoriaDespesa) {
        try {

            super.openConnectionRead();

            String where = SubCategoriaDespesa.ID_CATEGORIA_DESPESA + "=" + idCategoriaDespesa;

            Cursor cursor = super.select(super.getTransaction(), where,SubCategoriaDespesa.NO_ORDEM_EXIBICAO + " , " + SubCategoriaDespesa.NM_SUB_CATEGORIA);

            return this.preencheObjeto(super.getTransaction(),cursor);


        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public int getQtdSubCategoria(long idCategoriaDespesa) {

        int qtdSubCategoriasDespesas = 0;

        String[] parametros = {String.valueOf(idCategoriaDespesa)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(SubCategoriaDespesa.TABELA_NOME);
        sql.append(" WHERE " + SubCategoriaDespesa.ID_CATEGORIA_DESPESA);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdSubCategoriasDespesas = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }

            return qtdSubCategoriasDespesas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    public int excluiSubCategoriaDespesa(SQLiteDatabase transaction, long idCategoriaDespesa) {

        String[] parametros = new String[]{String.valueOf(idCategoriaDespesa)};

        StringBuilder sql = new StringBuilder();

        sql.append(" DELETE FROM ");
        sql.append(SubCategoriaDespesa.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(SubCategoriaDespesa.ID_CATEGORIA_DESPESA + " = ?");

        try {

            super.executeCustomQuery(transaction, sql.toString(), parametros);

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_receita));
        }
    }

    public int excluiComDependentes(SubCategoriaDespesa item) {
        try {
            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioDespesa repositorioDespesa = new RepositorioDespesa(super.getContext());

            repositorioDespesa.excluiDespesasDaCategoriaComEstorno(super.getTransaction(),item.getId());

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

    public SubCategoriaDespesa get(SQLiteDatabase transaction, Long id) {
        String where = SubCategoriaDespesa.ID + "=" + id;

        SubCategoriaDespesa subCategoriaDespesa = new SubCategoriaDespesa();

        try {


            Cursor cursor = super.select(transaction, where);

            ArrayList<SubCategoriaDespesa> arrayList = this.preencheObjeto(transaction, cursor);

            if (arrayList.size() > 0)
                subCategoriaDespesa = arrayList.get(0);

            return subCategoriaDespesa;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_despesa));
        }
    }

}