package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.charlessodre.apps.gerenciadorfinanceiroisis.R;

import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CategoriaReceita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Transferencia;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;


import java.util.ArrayList;

/**
 * Created by charl on 27/09/2016.
 */

public class RepositorioTransferencia extends RepositorioBase implements IRepositorio<Transferencia, Long> {


    //Construtor
    public RepositorioTransferencia(Context context) {
        super(context, Transferencia.TABELA_NOME);
    }

    //Métodos Principais
    @Override
    public int altera(Transferencia item) {
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
    public long insere(Transferencia item) {
        try {
            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            repositorioConta.setValorEntradaConta(super.getTransaction(), item.getContaDestino().getId(), item.getValor());

            repositorioConta.setValorSaidaConta(super.getTransaction(), item.getContaOrigem().getId(), item.getValor());

            long id = super.insert(super.getTransaction(), this.preencheContentValues(item));

            super.setTransactionSuccessful();

            return id;


        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    @Override
    public int exclui(Transferencia item) {
        try {
            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            repositorioConta.setValorEntradaConta(super.getTransaction(), item.getContaOrigem().getId(), item.getValor());

            repositorioConta.setValorSaidaConta(super.getTransaction(), item.getContaDestino().getId(), item.getValor());

            int linhas = super.delete(super.getTransaction(), item.getId());

            super.setTransactionSuccessful();

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    @Override
    public Transferencia get(Long id) {
        return null;
    }

    //Auxiliares
    private ContentValues preencheContentValues(Transferencia transferencia) {
        ContentValues values = new ContentValues();

        values.put(Transferencia.NO_TOTAL_REPETICAO, transferencia.getTotalRepeticao());
        values.put(Transferencia.NO_REPETICAO_ATUAL, transferencia.getRepeticaoAtual());
        values.put(Transferencia.VL_TRANSFERENCIA, transferencia.getValor());
        values.put(Transferencia.NO_AM_TRANSFERENCIA, transferencia.getAnoMes());
        values.put(Transferencia.ID_CONTA_ORIGEM, transferencia.getContaOrigem().getId());
        values.put(Transferencia.ID_CONTA_DESTINO, transferencia.getContaDestino().getId());
        values.put(Transferencia.DT_TRANSFERENCIA, transferencia.getDataTransferencia().getTime());
        values.put(Transferencia.DT_INCLUSAO, transferencia.getDataInclusao().getTime());

        if (transferencia.getId() != 0)
            values.put(Transferencia.DT_ALTERACAO, transferencia.getDataAlteracao().getTime());

        return values;
    }

    private ArrayList<Transferencia> preencheObjeto(SQLiteDatabase transaction, Cursor cursor) {

        RepositorioConta repConta = new RepositorioConta(super.getContext());
        ArrayList<Transferencia> arrayList = new ArrayList<Transferencia>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Transferencia transferencia = new Transferencia();

                transferencia.setId(cursor.getLong(cursor.getColumnIndex(Transferencia.ID)));
                transferencia.setDataInclusao(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(Transferencia.DT_INCLUSAO))));
                transferencia.setDataTransferencia(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(Transferencia.DT_TRANSFERENCIA))));
                transferencia.setAnoMes(cursor.getInt(cursor.getColumnIndex(Transferencia.NO_AM_TRANSFERENCIA)));

                transferencia.setContaOrigem(repConta.get(transaction, cursor.getLong(cursor.getColumnIndex(Transferencia.ID_CONTA_ORIGEM))));
                transferencia.setContaDestino(repConta.get(transaction, cursor.getLong(cursor.getColumnIndex(Transferencia.ID_CONTA_DESTINO))));
                transferencia.setValor(cursor.getDouble(cursor.getColumnIndex(Transferencia.VL_TRANSFERENCIA)));
                transferencia.setRepeticaoAtual(cursor.getInt(cursor.getColumnIndex(Transferencia.NO_REPETICAO_ATUAL)));
                transferencia.setTotalRepeticao(cursor.getInt(cursor.getColumnIndex(Transferencia.NO_TOTAL_REPETICAO)));


                arrayList.add(transferencia);

            } while (cursor.moveToNext());
        }

        return arrayList;
    }

    //Consultas
    public ArrayList<Transferencia> buscaTodos() {
        try {

            Cursor cursor = super.selectAll(Transferencia.DT_TRANSFERENCIA + "," + Transferencia.NO_ORDEM_EXIBICAO);

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public ArrayList<Transferencia> getTransferenciasContaOrigem(SQLiteDatabase transaction, long idConta) {

        StringBuilder where = new StringBuilder();

        where.append(Transferencia.ID_CONTA_ORIGEM + " = " + idConta);

        try {

            Cursor cursor = super.select(transaction, where.toString());

            return this.preencheObjeto(transaction, cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        }
    }

    public ArrayList<Transferencia> buscaPorAnoMes(int anoMes) {

        String where = Transferencia.NO_AM_TRANSFERENCIA + " =  " + anoMes;

        try {

            super.openConnectionRead();

            Cursor cursor = super.select(where, Transferencia.DT_TRANSFERENCIA);

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public ArrayList<Transferencia> buscaPorContaAnoMes(long idContaOrigemDestino, int anoMes) {

        StringBuilder where = new StringBuilder();

        where.append(Transferencia.ID_CONTA_ORIGEM);
        where.append(" = " + idContaOrigemDestino);
        where.append(" OR ");
        where.append(Transferencia.ID_CONTA_DESTINO);
        where.append(" = " + idContaOrigemDestino);


        where.append(" AND ");
        where.append(Transferencia.NO_AM_TRANSFERENCIA);
        where.append(" =  " + anoMes);


        try {

            super.openConnectionRead();

            Cursor cursor = super.select(where.toString(), Transferencia.DT_TRANSFERENCIA);

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    //Totalizadores
    public int getQtdTransferenciaConta(long idConta) {
        int qtdTransferencias = 0;

        String[] parametros = {String.valueOf(idConta), String.valueOf(idConta)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(Transferencia.TABELA_NOME);
        sql.append(" WHERE " + Transferencia.ID_CONTA_ORIGEM);
        sql.append(" = ? OR  ");
        sql.append(Transferencia.ID_CONTA_DESTINO);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdTransferencias = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }


            return qtdTransferencias;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    public int getQtdTransferenciaEntradaContaMes(long idConta,int anoMes) {
        int qtdTransferencias = 0;

        String[] parametros = {String.valueOf(idConta), String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(Transferencia.TABELA_NOME);
        sql.append(" WHERE " + Transferencia.ID_CONTA_DESTINO);
        sql.append(" = ?");
        sql.append(" AND  ");
        sql.append(Transferencia.NO_AM_TRANSFERENCIA);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdTransferencias = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }


            return qtdTransferencias;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    public int getQtdTransferenciaSaidaContaMes(long idConta,int anoMes) {
        int qtdTransferencias = 0;

        String[] parametros = {String.valueOf(idConta), String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(Transferencia.TABELA_NOME);
        sql.append(" WHERE " + Transferencia.ID_CONTA_ORIGEM);
        sql.append(" = ?");
        sql.append(" AND  ");
        sql.append(Transferencia.NO_AM_TRANSFERENCIA);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdTransferencias = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }


            return qtdTransferencias;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    public Double getValorTransferenciaEntradaContaMes(long idConta,int anoMes) {
        Double valorTotal = 0.0;

        String[] parametros = {String.valueOf(idConta), String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT SUM (");
        sql.append(Transferencia.VL_TRANSFERENCIA);
        sql.append(") as VL_TOTAL FROM ");
        sql.append(Transferencia.TABELA_NOME);
        sql.append(" WHERE " + Transferencia.ID_CONTA_DESTINO);
        sql.append(" = ?");
        sql.append(" AND  ");
        sql.append(Transferencia.NO_AM_TRANSFERENCIA);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    valorTotal = cursor.getDouble(cursor.getColumnIndex("VL_TOTAL"));

                } while (cursor.moveToNext());
            }


            return valorTotal;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    public Double getValorTransferenciaSaidaContaMes(long idConta,int anoMes) {
        Double valorTotal = 0.0;

        String[] parametros = {String.valueOf(idConta), String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT SUM (");
        sql.append(Transferencia.VL_TRANSFERENCIA);
        sql.append(") as VL_TOTAL FROM ");
        sql.append(Transferencia.TABELA_NOME);
        sql.append(" WHERE " + Transferencia.ID_CONTA_ORIGEM);
        sql.append(" = ?");
        sql.append(" AND  ");
        sql.append(Transferencia.NO_AM_TRANSFERENCIA);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    valorTotal = cursor.getDouble(cursor.getColumnIndex("VL_TOTAL"));

                } while (cursor.moveToNext());
            }


            return valorTotal;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    //Exclusões
    public int excluiTransferenciasContaOrigem(SQLiteDatabase transaction, RepositorioConta repositorioConta, long idContaOrigem) {

        String[] parametros = new String[]{String.valueOf(idContaOrigem)};

        StringBuilder sql = new StringBuilder();

        sql.append(" DELETE FROM ");
        sql.append(Transferencia.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(Transferencia.ID_CONTA_ORIGEM + " = ?");

        try {

            super.executeCustomQuery(transaction, sql.toString(), parametros);

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro));
        }
    }

}
