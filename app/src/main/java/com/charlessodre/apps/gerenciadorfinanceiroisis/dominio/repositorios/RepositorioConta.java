package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Despesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by charl on 10/09/2016.
 */
public class RepositorioConta extends RepositorioBase implements IRepositorio<Conta, Long> {

    //Construtor
    public RepositorioConta(Context context) {
        super(context, Conta.TABELA_NOME);
    }

    //Métodos
    public ContentValues preencheContentValues(Conta conta) {
        ContentValues values = new ContentValues();


        values.put(Conta.NM_CONTA, conta.getNome());
        values.put(Conta.CD_TIPO_CONTA, conta.getCdTipoConta());
        values.put(Conta.FL_ATIVO, BooleanUtils.parseBooleanToint(conta.isAtivo()));
        values.put(Conta.FL_EXIBIR, BooleanUtils.parseBooleanToint(conta.isExibir()));
        values.put(Conta.NO_AM_CONTA, conta.getAnoMes());
        values.put(Conta.NO_ORDEM_EXIBICAO, conta.getOrdemExibicao());
        values.put(Conta.CD_TIPO_CONTA, conta.getCdTipoConta());
        values.put(Conta.NO_COR, conta.getNoCor());
        values.put(Conta.NO_COR_ICONE, conta.getNoCorIcone());
        values.put(Conta.VL_SALDO, conta.getValorSaldo());
        values.put(Conta.FL_EXIBIR_SOMA, BooleanUtils.parseBooleanToint(conta.isExibiSomaResumo()));

        if (conta.getId() == 0)
            values.put(Conta.DT_INCLUSAO, conta.getDataInclusao().getTime());
        else
            values.put(Conta.DT_ALTERACAO, conta.getDataAlteracao().getTime());

        return values;
    }

    private ArrayList<Conta> preencheObjeto(Cursor cursor) {
        ArrayList<Conta> arrayList = new ArrayList<Conta>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Conta conta = new Conta();

                conta.setId(cursor.getLong(cursor.getColumnIndex(Conta.ID)));
                conta.setNome(cursor.getString(cursor.getColumnIndex(Conta.NM_CONTA)));
                conta.setOrdemExibicao(cursor.getInt(cursor.getColumnIndex(Conta.NO_ORDEM_EXIBICAO)));
                conta.setCdTipoConta(cursor.getInt(cursor.getColumnIndex(Conta.CD_TIPO_CONTA)));
                conta.setNoCor(cursor.getInt(cursor.getColumnIndex(Conta.NO_COR)));
                conta.setNoCorIcone(cursor.getInt(cursor.getColumnIndex(Conta.NO_COR_ICONE)));
                conta.setAnoMes(cursor.getInt(cursor.getColumnIndex(Conta.NO_AM_CONTA)));
                conta.setExibir(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Conta.FL_EXIBIR))));
                conta.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Conta.FL_ATIVO))));
                conta.setExibiSomaResumo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Conta.FL_EXIBIR_SOMA))));

                conta.setValorSaldo(cursor.getDouble(cursor.getColumnIndex(Conta.VL_SALDO)));

                if (cursor.getColumnIndex(Conta.RECEITAS_PREVISTAS) != -1)
                    conta.setReceitasPrevistas(cursor.getDouble(cursor.getColumnIndex(Conta.RECEITAS_PREVISTAS)));

                if (cursor.getColumnIndex(Conta.DESPESAS_PREVISTAS) != -1)
                    conta.setDespesasPrevistas(cursor.getDouble(cursor.getColumnIndex(Conta.DESPESAS_PREVISTAS)));

                arrayList.add(conta);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return arrayList;

    }

    public double getValorTotal(boolean somenteExibeSoma) {
        return getValorTotal(0,somenteExibeSoma);
    }

    public double getValorTotal(int anoMes, boolean somenteExibeSoma) {

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT SUM( ");
        sql.append(Conta.VL_SALDO);
        sql.append(" ) AS VL_TOTAL_CONTA FROM ");
        sql.append(Conta.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(Conta.FL_ATIVO + " = 1");

        if(anoMes > 0) {

            sql.append(" AND ");
            sql.append(Conta.NO_AM_CONTA);
            sql.append(" <=  ");
            sql.append(anoMes);
        }



        if(somenteExibeSoma)
          sql.append(" AND " + Conta.FL_EXIBIR_SOMA + " = 1");

        double valorTotal = 0;

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(super.getTransaction(), sql.toString(), null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    valorTotal = cursor.getDouble(cursor.getColumnIndex("VL_TOTAL_CONTA"));

                } while (cursor.moveToNext());
            }
            return valorTotal;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public double getSaldoAtual(int anoMes, boolean somenteExibeSoma) {

        return getSaldoAtual(0, anoMes,somenteExibeSoma);

    }

    public double getSaldoAtual(long idConta, int anoMes, boolean somenteExibeSoma) {

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT SUM( ");
        sql.append(Conta.VL_SALDO);
        sql.append(" ) AS VL_TOTAL_CONTA FROM ");
        sql.append(Conta.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(Conta.NO_AM_CONTA);
        sql.append(" <=  ");
        sql.append(anoMes);

        if(idConta != 0) {
            sql.append(" AND ");
            sql.append(Conta.ID);
            sql.append(" = ");
            sql.append(idConta);
        }

        sql.append(" AND " + Conta.FL_ATIVO + " = 1");

        if(somenteExibeSoma)
            sql.append(" AND " + Conta.FL_EXIBIR_SOMA + " = 1");

        double valorTotal = 0;

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(super.getTransaction(), sql.toString(), null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    valorTotal = cursor.getDouble(cursor.getColumnIndex("VL_TOTAL_CONTA"));

                } while (cursor.moveToNext());
            }
            return valorTotal;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }


    public ArrayList<Conta> buscaTodos() {
        try {

            super.openConnectionRead();

            Cursor cursor = super.selectAll(Conta.NO_ORDEM_EXIBICAO + " , " + Conta.NM_CONTA);

            return this.preencheObjeto(cursor);


        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }


    public ArrayList<Conta> getSaldoContaAnoMes(int anoMes) {

        return getSaldoContaAnoMes(0, anoMes);
    }

    public ArrayList<Conta> getSaldoContaAnoMes(long idConta,int anoMes) {

        String[] parametros = {String.valueOf(anoMes),String.valueOf(idConta) };

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append(" C._id," );
        sql.append(" C.NM_CONTA," );
        sql.append(" C.FL_ATIVO," );
        sql.append(" C.DT_INCLUSAO," );
        sql.append(" C.DT_ALTERACAO," );
        sql.append(" C.FL_EXIBIR," );
        sql.append(" C.FL_EXIBIR_SOMA," );
        sql.append(" C.CD_TIPO_CONTA," );
        sql.append(" C.NO_AM_CONTA, " );
        sql.append(" C.NO_ORDEM_EXIBICAO," );
        sql.append(" C.NO_COR," );
        sql.append(" C.NO_COR_ICONE," );
        sql.append(" C.VL_SALDO, " );
        sql.append(" (SELECT SUM(R.VL_RECEITA) AS VL_RECEITA FROM ");
        sql.append(Receita.TABELA_NOME);
        sql.append("  as R where R.NO_AM_RECEITA <= ? AND R.FL_RECEITA_PAGA=0 AND C._id=R.ID_CONTA)AS RECEITAS_PREVISTAS, " );
        sql.append(" (SELECT SUM(D.VL_DESPESA) AS VL_DESPESA FROM ");
        sql.append(Despesa.TABELA_NOME);
        sql.append(" as D where D.NO_AM_DESPESA <= ? AND D.FL_DESPESA_PAGA=0 AND C._id=D.ID_CONTA ) AS DESPESAS_PREVISTAS " );
        sql.append(" FROM ");
        sql.append(Conta.TABELA_NOME);
        sql.append(" C ");

        if(idConta != 0)
            sql.append(" WHERE  C._id = ?" );

        sql.append(" ORDER BY C.NO_ORDEM_EXIBICAO, C.NM_CONTA");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            return this.preencheObjeto(cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    @Override
    public int altera(Conta item) {
        try {

            super.openConnectionWrite();
            return super.update(preencheContentValues(item), item.getId());

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public int alterarTransaction(SQLiteDatabase transaction, Conta item) {

        return super.update(transaction, preencheContentValues(item), item.getId());

    }

    public Conta get(SQLiteDatabase transaction, Long id) {

        String where = Conta.ID + "=" + id;

        Conta conta = new Conta();

        try {
            Cursor cursor = super.select(transaction, where);

            ArrayList<Conta> arrayList = this.preencheObjeto(cursor);

            if (arrayList.size() > 0)
                conta = arrayList.get(0);

            return conta;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_conta));
        }


    }

    public void setValorEntradaConta(SQLiteDatabase transaction, long idConta, double valorEntrada) {

        if(valorEntrada> 0) {
            Conta contaSaldo = this.get(transaction, idConta);

            contaSaldo.setDataAlteracao(DateUtils.getCurrentDatetime());

            double valorSaldoAtual = contaSaldo.getValorSaldo();

            valorSaldoAtual = valorSaldoAtual + valorEntrada;

            contaSaldo.setValorSaldo(valorSaldoAtual);

            this.alterarTransaction(transaction, contaSaldo);
        }
    }

    public void setValorSaidaConta(SQLiteDatabase transaction, long idConta, double valorSaida) {

        if(valorSaida>0) {
            Conta contaSaldo = this.get(transaction, idConta);

            contaSaldo.setDataAlteracao(DateUtils.getCurrentDatetime());

            double valorSaldoAtual = contaSaldo.getValorSaldo();

            valorSaldoAtual = valorSaldoAtual - valorSaida;

            contaSaldo.setValorSaldo(valorSaldoAtual);

            this.alterarTransaction(transaction, contaSaldo);
        }
    }

    public int excluiComDependentes(Conta item) {

        try {
            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioReceita repositorioReceita = new RepositorioReceita(super.getContext());
            RepositorioTransferencia repositorioTransferencia = new RepositorioTransferencia(super.getContext());
            RepositorioDespesa repositorioDespesa = new RepositorioDespesa(super.getContext());

            //Exclui todas as receitas da conta.
            repositorioReceita.excluiTodasSemEstorno(super.getTransaction(), item.getId());

            //Exclui todas as despesas da conta.
            repositorioDespesa.excluiTodasSemEstorno(super.getTransaction(), item.getId());

            //Exclui todas as transferencias da conta de origem.
            repositorioTransferencia.excluiTransferenciasContaOrigem(super.getTransaction(), this, item.getId());

            super.delete(super.getTransaction(), item.getId());

            super.setTransactionSuccessful();
            return 1;
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    @Override
    public long insere(Conta item) {
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
    public int exclui(Conta item) {

        try {
            super.openConnectionWrite();
            super.setBeginTransaction();

            super.delete(super.getTransaction(), item.getId());

            super.setTransactionSuccessful();
            return 1;
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    @Override
    public Conta get(Long id) {

        String where = Conta.ID + "=" + id;

        Conta conta = new Conta();

        try {

            super.openConnectionRead();

            Cursor cursor = super.select(where);

            ArrayList<Conta> arrayList = this.preencheObjeto(cursor);

            if (arrayList.size() > 0)
                conta = arrayList.get(0);

            return conta;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }


}
