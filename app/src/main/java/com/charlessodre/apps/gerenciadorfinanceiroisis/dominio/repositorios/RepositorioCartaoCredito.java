package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Despesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by charl on 01/05/2017.
 */
public class RepositorioCartaoCredito extends RepositorioBase implements IRepositorio<CartaoCredito, Long> {

    //Construtor
    public RepositorioCartaoCredito(Context context) {
        super(context, CartaoCredito.TABELA_NOME);
    }

    //Métodos
    public ContentValues preencheContentValues(CartaoCredito cartaoCredito) {
        ContentValues values = new ContentValues();


        values.put(CartaoCredito.NM_CARTAO, cartaoCredito.getNome());
        values.put(CartaoCredito.NO_BANDEIRA_CARTAO, cartaoCredito.getNoBandeiraCartao());
        values.put(CartaoCredito.VL_LIMITE, cartaoCredito.getValorLimite());
        values.put(CartaoCredito.FL_ALERTA_VENCIMENTO, BooleanUtils.parseBooleanToint(cartaoCredito.isAltertaVencimento()));
        values.put(CartaoCredito.ID_CONTA_ASSOCIADA, cartaoCredito.getContaAssociada().getId());
        values.put(CartaoCredito.NO_DIA_FECHAMENTO_FATURA, cartaoCredito.getNoDiaFechamentoFatura());
        values.put(CartaoCredito.NO_DIA_VENCIMENTO_FATURA, cartaoCredito.getNoDiaVencimentoFatura());
        values.put(CartaoCredito.FL_AGRUPAR_DESPESAS, BooleanUtils.parseBooleanToint(cartaoCredito.isAgruparDespesas()));
        values.put(CartaoCredito.FL_ATIVO, BooleanUtils.parseBooleanToint(cartaoCredito.isAtivo()));
        values.put(CartaoCredito.FL_EXIBIR, BooleanUtils.parseBooleanToint(cartaoCredito.isExibir()));
        values.put(CartaoCredito.NO_ORDEM_EXIBICAO, cartaoCredito.getOrdemExibicao());
        values.put(CartaoCredito.NO_COR, cartaoCredito.getNoCor());
        values.put(CartaoCredito.VL_TAXA_JUROS_FINANCIAMENTO, cartaoCredito.getValorTaxaJurosFinaciamento());
        values.put(CartaoCredito.VL_TAXA_JUROS_ROTATIVO, cartaoCredito.getValorTaxaJurosRotativo());

        if (cartaoCredito.getId() == 0)
            values.put(CartaoCredito.DT_INCLUSAO, cartaoCredito.getDataInclusao().getTime());
        else
            values.put(CartaoCredito.DT_ALTERACAO, cartaoCredito.getDataAlteracao().getTime());

        return values;
    }

    private ArrayList<CartaoCredito> preencheObjeto(SQLiteDatabase transaction, Cursor cursor) {
        ArrayList<CartaoCredito> arrayList = new ArrayList<CartaoCredito>();

        RepositorioConta repConta = new RepositorioConta(super.getContext());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                CartaoCredito cartaoCredito = new CartaoCredito();

                cartaoCredito.setId(cursor.getLong(cursor.getColumnIndex(CartaoCredito.ID)));
                cartaoCredito.setNome(cursor.getString(cursor.getColumnIndex(CartaoCredito.NM_CARTAO)));
                cartaoCredito.setValorLimite(cursor.getDouble(cursor.getColumnIndex(CartaoCredito.VL_LIMITE)));
                cartaoCredito.setNoBandeiraCartao(cursor.getInt(cursor.getColumnIndex(CartaoCredito.NO_BANDEIRA_CARTAO)));
                cartaoCredito.setContaAssociada(repConta.get(transaction, cursor.getLong(cursor.getColumnIndex(CartaoCredito.ID_CONTA_ASSOCIADA))));
                cartaoCredito.setNoDiaFechamentoFatura(cursor.getInt(cursor.getColumnIndex(CartaoCredito.NO_DIA_FECHAMENTO_FATURA)));
                cartaoCredito.setNoDiaVencimentoFatura(cursor.getInt(cursor.getColumnIndex(CartaoCredito.NO_DIA_VENCIMENTO_FATURA)));
                cartaoCredito.setAltertaVencimento(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(CartaoCredito.FL_ALERTA_VENCIMENTO))));
                cartaoCredito.setAgruparDespesas(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(CartaoCredito.FL_AGRUPAR_DESPESAS))));

                cartaoCredito.setNoCor(cursor.getInt(cursor.getColumnIndex(CartaoCredito.NO_COR)));
                cartaoCredito.setOrdemExibicao(cursor.getInt(cursor.getColumnIndex(CartaoCredito.NO_ORDEM_EXIBICAO)));
                cartaoCredito.setExibiSomaResumo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(CartaoCredito.FL_EXIBIR_SOMA))));
                cartaoCredito.setExibir(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(CartaoCredito.FL_EXIBIR))));
                cartaoCredito.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(CartaoCredito.FL_ATIVO))));

                cartaoCredito.setValorTaxaJurosFinaciamento(cursor.getDouble(cursor.getColumnIndex(CartaoCredito.VL_TAXA_JUROS_FINANCIAMENTO)));
                cartaoCredito.setValorTaxaJurosRotativo(cursor.getDouble(cursor.getColumnIndex(CartaoCredito.VL_TAXA_JUROS_ROTATIVO)));

                if (cursor.getColumnIndex(CartaoCredito.RECEITAS_PREVISTAS) != -1)
                    cartaoCredito.setReceitasPrevistas(cursor.getDouble(cursor.getColumnIndex(CartaoCredito.RECEITAS_PREVISTAS)));

                if (cursor.getColumnIndex(CartaoCredito.DESPESAS_PREVISTAS) != -1)
                    cartaoCredito.setDespesasPrevistas(cursor.getDouble(cursor.getColumnIndex(CartaoCredito.DESPESAS_PREVISTAS)));

                arrayList.add(cartaoCredito);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return arrayList;

    }

    @Override
    public int altera(CartaoCredito item) {
        try {

            super.openConnectionWrite();
            return super.update(preencheContentValues(item), item.getId());

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_cartao));
        } finally {
            super.closeConnection();
        }
    }

    @Override
    public long insere(CartaoCredito item) {
        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioFaturaCartaoCredito repositorioFaturaCartaoCredito = new RepositorioFaturaCartaoCredito(this.getContext());

            long idCartao = super.insert(preencheContentValues(item));

            item.setId(idCartao);

            repositorioFaturaCartaoCredito.insere(super.getTransaction(), item,DateUtils.getCurrentYearAndMonth(), Constantes.TOTAL_FATURA_CRIADAS);

            super.setTransactionSuccessful();

            return 0;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_cartao));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    @Override
    public int exclui(CartaoCredito item) {

        try {
            super.openConnectionWrite();
            super.setBeginTransaction();

            super.delete(super.getTransaction(), item.getId());

            super.setTransactionSuccessful();
            return 1;
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_cartao));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    @Override
    public CartaoCredito get(Long id) {

        String where = CartaoCredito.ID + "=" + id;

        CartaoCredito conta = new CartaoCredito();
        Cursor cursor = null;

        try {

            super.openConnectionRead();
            super.setBeginTransaction();

            cursor = super.select(where);

            ArrayList<CartaoCredito> arrayList = this.preencheObjeto(super.getTransaction(), cursor);

            if (arrayList.size() > 0)
                conta = arrayList.get(0);

            super.setTransactionSuccessful();
            return conta;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_cartao_credito));
        } finally {

            if(cursor != null)
                cursor.close();

            super.setEndTransaction();
            super.closeConnection();
        }
    }

    public ArrayList<CartaoCredito> getAll() {

        Cursor cursor = null;

        try {

            super.openConnectionRead();
            super.setBeginTransaction();

            cursor = super.selectAll(CartaoCredito.NO_ORDEM_EXIBICAO + " , " + CartaoCredito.NM_CARTAO);

            super.setTransactionSuccessful();

            return this.preencheObjeto(super.getTransaction(), cursor);


        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_cartao_credito));
        } finally {
            if (cursor != null)
                cursor.close();
            super.setEndTransaction();
            super.closeConnection();


        }
    }

    public ArrayList<CartaoCredito> getSaldoCartaoAnoMes(long idCartao, int anoMes, boolean somenteExibeSoma) {

        Cursor cursor = null;

        String[] parametros = {String.valueOf(anoMes), String.valueOf(idCartao)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append(" C._id,");
        sql.append(" C.NM_CONTA,");
        sql.append(" C.FL_ATIVO,");
        sql.append(" C.DT_INCLUSAO,");
        sql.append(" C.DT_ALTERACAO,");
        sql.append(" C.FL_EXIBIR,");
        sql.append(" C.FL_EXIBIR_SOMA,");
        sql.append(" C.CD_TIPO_CONTA,");
        sql.append(" C.NO_AM_CONTA, ");
        sql.append(" C.NO_ORDEM_EXIBICAO,");
        sql.append(" C.NO_COR,");
        sql.append(" C.NO_COR_ICONE,");
        sql.append(" C.VL_SALDO, ");
        sql.append(" (SELECT SUM(D.VL_DESPESA) AS VL_DESPESA FROM ");
        sql.append(Despesa.TABELA_NOME);
        sql.append(" as D where D.NO_AM_DESPESA <= ");
        sql.append(anoMes);
        sql.append(" AND D.FL_DESPESA_PAGA=0 AND C._id=D.ID_CONTA ) AS ");
        sql.append(CartaoCredito.DESPESAS_PREVISTAS);
        sql.append(" FROM ");
        sql.append(CartaoCredito.TABELA_NOME);
        sql.append(" C ");
        sql.append(" WHERE ");
        sql.append(CartaoCredito.FL_ATIVO);
        sql.append(" = 1");

        if (somenteExibeSoma) {
            sql.append(" AND ");
            sql.append(CartaoCredito.FL_EXIBIR_SOMA + " = 1");
        }


        if (idCartao != 0)
            sql.append(" C._id = ?");

        sql.append(" ORDER BY C.NO_ORDEM_EXIBICAO, C.NM_CONTA");

        try {

            super.openConnectionRead();
            super.setBeginTransaction();

            cursor = super.selectCustomQuery(sql.toString(), parametros);

            super.setTransactionSuccessful();

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_cartao_credito));
        } finally {
            if (cursor != null)
                cursor.close();

            super.setEndTransaction();
            super.closeConnection();
        }
    }

    public int alterarTransaction(SQLiteDatabase transaction, CartaoCredito item) {

        return super.update(transaction, preencheContentValues(item), item.getId());

    }

    public CartaoCredito get(SQLiteDatabase transaction, Long id) {

        Cursor cursor = null;

        String where = CartaoCredito.ID + "=" + id;

        CartaoCredito conta = new CartaoCredito();

        try {

            cursor = super.select(transaction, where);

            ArrayList<CartaoCredito> arrayList = this.preencheObjeto(transaction, cursor);

            if (arrayList.size() > 0)
                conta = arrayList.get(0);

            return conta;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_cartao_credito));
        }finally {
            if (cursor != null)
                cursor.close();}


    }

    public void setValorEntradaCartao(SQLiteDatabase transaction, long idCartao, double valorEntrada) {

        CartaoCredito contaSaldo = this.get(transaction, idCartao);

        contaSaldo.setDataAlteracao(DateUtils.getCurrentDatetime());

        //     double valorSaldoAtual = contaSaldo.getValorSaldo();

        //  valorSaldoAtual = valorSaldoAtual + valorEntrada;

        // contaSaldo.setValorSaldo(valorSaldoAtual);

        this.alterarTransaction(transaction, contaSaldo);
    }

    public void setValorSaidaCartao(SQLiteDatabase transaction, long idCartao, double valorSaida) {

        CartaoCredito contaSaldo = this.get(transaction, idCartao);

        contaSaldo.setDataAlteracao(DateUtils.getCurrentDatetime());

        // double valorSaldoAtual = contaSaldo.getValorSaldo();

        // valorSaldoAtual = valorSaldoAtual - valorSaida;

        // contaSaldo.setValorSaldo(valorSaldoAtual);

        this.alterarTransaction(transaction, contaSaldo);
    }

    public int excluiComDependentes(CartaoCredito item) {

        try {
            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioFaturaCartaoCredito repositorioFaturaCartaoCredito = new RepositorioFaturaCartaoCredito(super.getContext());

            //Exclui todas as faturas
            repositorioFaturaCartaoCredito.exclui(super.getTransaction(), item.getId());

            super.delete(super.getTransaction(), item.getId());

            super.setTransactionSuccessful();
            return 1;
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_cartao));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }


}
