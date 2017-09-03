package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.database.ConnectionFactory;
import com.charlessodre.apps.gerenciadorfinanceiroisis.database.DataBase;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.CartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.FaturaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by charl on 18/05/2017.
 */

public class RepositorioFaturaCartaoCredito extends RepositorioBase implements IRepositorio<FaturaCartaoCredito, Long> {


    //Construtor
    public RepositorioFaturaCartaoCredito(Context context) {
        super(context, FaturaCartaoCredito.TABELA_NOME);
    }

    //Auxiliares
    private ContentValues preencheContentValues(FaturaCartaoCredito faturaCartaoCredito) {

        ContentValues values = new ContentValues();

        values.put(FaturaCartaoCredito.VL_FATURA, faturaCartaoCredito.getValor());
        values.put(FaturaCartaoCredito.VL_PAGAMENTO, faturaCartaoCredito.getValorPagamento());
        values.put(FaturaCartaoCredito.DT_FATURA, faturaCartaoCredito.getDataFatura().getTime());
        values.put(FaturaCartaoCredito.FL_FATURA_PAGA, BooleanUtils.parseBooleanToint(faturaCartaoCredito.isPaga()));
        values.put(FaturaCartaoCredito.FL_FATURA_FECHADA, BooleanUtils.parseBooleanToint(faturaCartaoCredito.isFechada()));
        values.put(FaturaCartaoCredito.FL_ALERTA_FECHAMENTO_FATURA, BooleanUtils.parseBooleanToint(faturaCartaoCredito.isAlertar()));
        values.put(FaturaCartaoCredito.NO_AM_FATURA, faturaCartaoCredito.getAnoMesFatura());
        values.put(FaturaCartaoCredito.ID_CARTAO_CREDITO, faturaCartaoCredito.getCartaoCredito().getId());
        values.put(FaturaCartaoCredito.FL_ATIVO, BooleanUtils.parseBooleanToint(faturaCartaoCredito.isAtivo()));
        values.put(FaturaCartaoCredito.FL_EXIBIR, BooleanUtils.parseBooleanToint(faturaCartaoCredito.isExibir()));

        if (faturaCartaoCredito.isPaga()) {
            values.put(FaturaCartaoCredito.DT_PAGAMENTO, faturaCartaoCredito.getDataPagamento().getTime());
            values.put(FaturaCartaoCredito.NO_AM_PAGAMENTO_FATURA, faturaCartaoCredito.getAnoMesPagamento());
        } else {
            values.put(FaturaCartaoCredito.DT_PAGAMENTO, "");
            values.put(FaturaCartaoCredito.NO_AM_PAGAMENTO_FATURA, "");
        }


        if (faturaCartaoCredito.getId() != 0)
            values.put(FaturaCartaoCredito.DT_ALTERACAO, faturaCartaoCredito.getDataAlteracao().getTime());
        else
            values.put(FaturaCartaoCredito.DT_INCLUSAO, faturaCartaoCredito.getDataInclusao().getTime());

        return values;
    }

    private ArrayList<FaturaCartaoCredito> preencheObjeto(SQLiteDatabase transaction, Cursor cursor) {

        ArrayList<FaturaCartaoCredito> arrayList = new ArrayList<FaturaCartaoCredito>();

        RepositorioCartaoCredito repositorioCartaoCredito = new RepositorioCartaoCredito(super.getContext());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                FaturaCartaoCredito faturaCartaoCredito = new FaturaCartaoCredito();

                faturaCartaoCredito.setId(cursor.getLong(cursor.getColumnIndex(FaturaCartaoCredito.ID)));
                faturaCartaoCredito.setExibir(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(FaturaCartaoCredito.FL_EXIBIR))));
                faturaCartaoCredito.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(FaturaCartaoCredito.FL_ATIVO))));
                faturaCartaoCredito.setValor(cursor.getDouble(cursor.getColumnIndex(FaturaCartaoCredito.VL_FATURA)));
                faturaCartaoCredito.setDataFatura(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(FaturaCartaoCredito.DT_FATURA))));

                faturaCartaoCredito.setValorPagamento(cursor.getDouble(cursor.getColumnIndex(FaturaCartaoCredito.VL_PAGAMENTO)));
                faturaCartaoCredito.setDataPagamento(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(FaturaCartaoCredito.DT_PAGAMENTO))));

                faturaCartaoCredito.setPaga(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(FaturaCartaoCredito.FL_FATURA_PAGA))));
                faturaCartaoCredito.setFechada(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(FaturaCartaoCredito.FL_FATURA_FECHADA))));
                faturaCartaoCredito.setAlertar(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(FaturaCartaoCredito.FL_ALERTA_FECHAMENTO_FATURA))));

                faturaCartaoCredito.setAnoMesFatura(cursor.getInt(cursor.getColumnIndex(FaturaCartaoCredito.NO_AM_FATURA)));
                faturaCartaoCredito.setAnoMesPagamento(cursor.getInt(cursor.getColumnIndex(FaturaCartaoCredito.NO_AM_PAGAMENTO_FATURA)));

                faturaCartaoCredito.setCartaoCredito(repositorioCartaoCredito.get(transaction, cursor.getLong(cursor.getColumnIndex(FaturaCartaoCredito.ID_CARTAO_CREDITO))));

                faturaCartaoCredito.setDataAlteracao(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(FaturaCartaoCredito.DT_ALTERACAO))));
                faturaCartaoCredito.setDataInclusao(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(FaturaCartaoCredito.DT_INCLUSAO))));

                arrayList.add(faturaCartaoCredito);

            } while (cursor.moveToNext());
        }

        return arrayList;

    }

    @Override
    public long insere(FaturaCartaoCredito item) {
        throw new UnsupportedOperationException("Sem implementação");
    }

    /*public void insere(SQLiteDatabase transaction, CartaoCredito cartaoCredito) {

        int ano = DateUtils.getCurrentYear();
        int mes = DateUtils.getCurrentMonth();
        int dia = DateUtils.getCurrentDay();
        Date data = null;

        try {

            for (int i = 0; i < 5; i++) {

                FaturaCartaoCredito faturaCartaoCredito = new FaturaCartaoCredito();

                if (cartaoCredito.getNoDiaVencimentoFatura() > dia) {

                    if (mes > 11) {
                        ano = ano + 1;
                        mes = 1;
                    }
                }

                data = DateUtils.getDate(ano, mes, cartaoCredito.getNoDiaVencimentoFatura());

                faturaCartaoCredito.setCartaoCredito(cartaoCredito);
                faturaCartaoCredito.setAlertar(cartaoCredito.isAltertaVencimento());
                faturaCartaoCredito.setDataInclusao(cartaoCredito.getDataInclusao());
                faturaCartaoCredito.setAnoMesFatura(DateUtils.getYearAndMonth(data));
                faturaCartaoCredito.setDataFatura(data);

                long id = super.insert(transaction, this.preencheContentValues(faturaCartaoCredito));

                mes = mes + 1;

            }

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro));
        }

    }
*/
    public void insere(SQLiteDatabase transaction, CartaoCredito cartaoCredito, int anoMes, int qtdFaturas) {

        int ano = Integer.parseInt(String.valueOf(anoMes).substring(0, 4));
        int mes = Integer.parseInt(String.valueOf(anoMes).substring(4, 6));
        int dia = DateUtils.getCurrentDay();
        Date data = null;

        try {

            for (int i = 0; i < qtdFaturas; i++) {

                if (cartaoCredito.getNoDiaVencimentoFatura() > dia) {

                    if (mes > 11) {
                        ano = ano + 1;
                        mes = 1;
                    }
                }

                data = DateUtils.getDate(ano, mes, cartaoCredito.getNoDiaVencimentoFatura());

                this.insere(transaction,cartaoCredito,data);

                mes = mes + 1;

            }

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_fatura_cartao));
        }

    }

    public FaturaCartaoCredito insere(SQLiteDatabase transaction, CartaoCredito cartaoCredito, int anoMes) {

        int ano = Integer.parseInt(String.valueOf(anoMes).substring(0, 4));
        int mes = Integer.parseInt(String.valueOf(anoMes).substring(4, 6));
        int dia = DateUtils.getCurrentDay();
        Date data = null;
        long id = -1;

        try {
            FaturaCartaoCredito faturaCartaoCredito = new FaturaCartaoCredito();

            if (cartaoCredito.getNoDiaVencimentoFatura() > dia) {

                if (mes > 11) {
                    ano = ano + 1;
                    mes = 1;
                }
            }

            data = DateUtils.getDate(ano, mes, cartaoCredito.getNoDiaVencimentoFatura());


            return this.insere(transaction,cartaoCredito,data);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_fatura_cartao));
        }

    }

    private FaturaCartaoCredito insere(SQLiteDatabase transaction, CartaoCredito cartaoCredito, Date data) {

        FaturaCartaoCredito faturaCartaoCredito = new FaturaCartaoCredito();

        long id = -1;

        try {
            faturaCartaoCredito.setCartaoCredito(cartaoCredito);
            faturaCartaoCredito.setAlertar(cartaoCredito.isAltertaVencimento());
            faturaCartaoCredito.setDataInclusao(DateUtils.getCurrentDatetime());
            faturaCartaoCredito.setAnoMesFatura(DateUtils.getYearAndMonth(data));
            faturaCartaoCredito.setDataFatura(data);

            id = super.insert(transaction,this.preencheContentValues(faturaCartaoCredito));

            faturaCartaoCredito.setId(id);

            return faturaCartaoCredito;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_fatura_cartao));
        }
    }

    @Override
    public int altera(FaturaCartaoCredito item) {

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            FaturaCartaoCredito faturaAnterior = this.get(item.getId());

            Conta contaAssociadaAtual = item.getCartaoCredito().getContaAssociada();
            Conta contaAssociadaAnterior = faturaAnterior.getCartaoCredito().getContaAssociada();

            if (faturaAnterior.isPaga()) {

                if (contaAssociadaAnterior.getId() == contaAssociadaAtual.getId()) {
                    repositorioConta.setValorEntradaConta(super.getTransaction(), contaAssociadaAtual.getId(), faturaAnterior.getValor());
                } else {
                    repositorioConta.setValorEntradaConta(super.getTransaction(), contaAssociadaAnterior.getId(), faturaAnterior.getValor());
                }
            }

            if (item.isPaga()) {
                repositorioConta.setValorSaidaConta(super.getTransaction(), contaAssociadaAtual.getId(), item.getValor());
            }

            int linhas = super.update(super.getTransaction(), this.preencheContentValues(item), item.getId());

            super.setTransactionSuccessful();

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_fatura_cartao));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    @Override
    public int exclui(FaturaCartaoCredito item) {

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            if (item.isPaga()) {

                RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

                Conta contaAssociada = item.getCartaoCredito().getContaAssociada();

                repositorioConta.setValorSaidaConta(super.getTransaction(), contaAssociada.getId(), item.getValor());
            }

            super.delete(super.getTransaction(), item.getId());

            this.setTransactionSuccessful();

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_fatura_cartao));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }

    }


    @Override
    public ArrayList<FaturaCartaoCredito> getAll() {
        return null;
    }

    @Override
    public FaturaCartaoCredito get(Long id) {

        Cursor cursor = null;

        String where = FaturaCartaoCredito.ID + "=" + id;

        FaturaCartaoCredito despesa = new FaturaCartaoCredito();

        try {

            super.openConnectionRead();

            cursor = super.select(where);

            ArrayList<FaturaCartaoCredito> arrayList = this.preencheObjeto(super.getTransaction(), cursor);

            if (arrayList.size() > 0)
                despesa = arrayList.get(0);

            return despesa;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_fatura_cartao_credito));
        } finally {


            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }
    }

    public FaturaCartaoCredito get(SQLiteDatabase transaction, CartaoCredito cartaoCredito, int anoMesFatura) {

        StringBuilder where = new StringBuilder();

        where.append(FaturaCartaoCredito.ID_CARTAO_CREDITO);
        where.append(" = ");
        where.append(cartaoCredito.getId());
        where.append(" AND ");
        where.append(FaturaCartaoCredito.NO_AM_FATURA);
        where.append(" = ");
        where.append(anoMesFatura);

        FaturaCartaoCredito faturaCartaoCredito = null;
        Cursor cursor = null;
        try {

            cursor = super.select(transaction, where.toString());

            ArrayList<FaturaCartaoCredito> arrayList = this.preencheObjeto(transaction, cursor);

            if (arrayList.size() > 0) {

                faturaCartaoCredito = arrayList.get(0);

            } else {

                faturaCartaoCredito = this.insere(transaction,cartaoCredito,anoMesFatura);

            }
            return faturaCartaoCredito;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_fatura_cartao_credito));
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    /* public FaturaCartaoCredito get(Long idCartaoCredito, int anoMesFatura) {
         StringBuilder where = new StringBuilder();

         where.append(FaturaCartaoCredito.ID_CARTAO_CREDITO);
         where.append(" = ");
         where.append(idCartaoCredito);
         where.append(" AND ");
         where.append(FaturaCartaoCredito.NO_AM_FATURA);
         where.append(" = ");
         where.append(anoMesFatura);

         FaturaCartaoCredito faturaCartaoCredito = null;

         try {

             super.openConnectionRead();

             Cursor cursor = super.select(where.toString());

             ArrayList<FaturaCartaoCredito> arrayList = this.preencheObjeto(super.getTransaction(), cursor);

             if (arrayList.size() > 0)
                 faturaCartaoCredito = arrayList.get(0);

             return faturaCartaoCredito;

         } catch (SQLException ex) {
             throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_fatura_cartao_credito));
         } finally {
             super.closeConnection();
         }
     }
 */
    public FaturaCartaoCredito get(SQLiteDatabase transaction, Long id) {


        StringBuilder where = new StringBuilder();

        where.append(FaturaCartaoCredito.ID);
        where.append(" = ");
        where.append(id);
        Cursor cursor = null;
        try {

            FaturaCartaoCredito faturaCartaoCredito = null;

            cursor = super.select(transaction, where.toString());

            ArrayList<FaturaCartaoCredito> arrayList = this.preencheObjeto(transaction, cursor);

            if (arrayList.size() > 0)
                faturaCartaoCredito = arrayList.get(0);

            return faturaCartaoCredito;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_fatura_cartao_credito));
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public ArrayList<FaturaCartaoCredito> getProximasFaturasCartaoCredito(Long idCartaoCredito, int anoMesFatura) {

        Cursor cursor = null;

        try {

            StringBuilder where = new StringBuilder();

            where.append(FaturaCartaoCredito.ID_CARTAO_CREDITO);
            where.append(" = ");
            where.append(idCartaoCredito);

            if (anoMesFatura > 0) {
                where.append(" AND ");
                where.append(FaturaCartaoCredito.NO_AM_FATURA);
                where.append(" >= ");
                where.append(anoMesFatura);
            }

            super.openConnectionRead();

            cursor = super.select(where.toString(), FaturaCartaoCredito.NO_AM_FATURA);

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_fatura_cartao_credito));
        } finally {

            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }
    }

    public ArrayList<FaturaCartaoCredito> getFaturasCartaoCredito(Long idCartaoCredito, int anoMesFatura) {
        Cursor cursor = null;

        try {

            StringBuilder where = new StringBuilder();

            where.append(FaturaCartaoCredito.ID_CARTAO_CREDITO);
            where.append(" = ");
            where.append(idCartaoCredito);

            if (anoMesFatura > 0) {
                where.append(" AND ");
                where.append(FaturaCartaoCredito.NO_AM_FATURA);
                where.append(" = ");
                where.append(anoMesFatura);
            }

            super.openConnectionWrite();

            cursor = super.select(where.toString(), FaturaCartaoCredito.NO_AM_FATURA);

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_fatura_cartao_credito));
        } finally {
            if (cursor != null)
                cursor.close();
            super.closeConnection();
        }
    }

    public ArrayList<FaturaCartaoCredito> getFaturasCartaoCredito(Long idCartaoCredito) {

        return getFaturasCartaoCredito(idCartaoCredito, 0);
    }

    public ArrayList<FaturaCartaoCredito> getFaturasAbertasCartaoCredito(Long idCartaoCredito) {

        Cursor cursor = null;

        try {

            StringBuilder where = new StringBuilder();

            where.append(FaturaCartaoCredito.ID_CARTAO_CREDITO);
            where.append(" = ");
            where.append(idCartaoCredito);
            where.append(" AND ");
            where.append(FaturaCartaoCredito.FL_FATURA_FECHADA);
            where.append(" = 0");


            super.openConnectionWrite();

            cursor = super.select(where.toString(), FaturaCartaoCredito.NO_AM_FATURA);

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_fatura_cartao_credito));
        } finally {

            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }
    }

    public int exclui(SQLiteDatabase transaction, Long idCartaoCredito) {

        try {

            int linhas = 0;

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());
            RepositorioDespesaCartaoCredito repositorioDespesaCartaoCredito = new RepositorioDespesaCartaoCredito(super.getContext());

            //Busca as despesas que serão excluidas.
            ArrayList<FaturaCartaoCredito> todasOcorrenciasFaturaCartaoCreditos = this.getFaturasCartaoCredito(idCartaoCredito);

            for (FaturaCartaoCredito ocorrencia : todasOcorrenciasFaturaCartaoCreditos) {

                repositorioDespesaCartaoCredito.exclui(transaction, ocorrencia.getId());

                if (ocorrencia.isPaga()) {
                    //Estorna o pagamento realizado
                    repositorioConta.setValorEntradaConta(transaction, ocorrencia.getCartaoCredito().getContaAssociada().getId(), ocorrencia.getValor());
                }

                super.delete(transaction, ocorrencia.getId());

                linhas = linhas++;
            }
            this.setTransactionSuccessful();

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_fatura_cartao));
        }

    }

}