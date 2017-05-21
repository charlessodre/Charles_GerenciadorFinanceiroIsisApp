package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Conta;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.DespesaCartaoCredito;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.TipoRepeticao;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by charl on 20/05/2017.
 */

public class RepositorioDespesaCartaoCredito extends RepositorioBase implements IRepositorio<DespesaCartaoCredito, Long> {


    //Construtor
    public RepositorioDespesaCartaoCredito(Context context) {
        super(context, DespesaCartaoCredito.TABELA_NOME);
    }

    //Auxiliares
    private ContentValues preencheContentValues(DespesaCartaoCredito despesaCartaoCredito) {

        ContentValues values = new ContentValues();

        values.put(DespesaCartaoCredito.NM_DESPESA, despesaCartaoCredito.getNome());
        values.put(DespesaCartaoCredito.VL_DESPESA, despesaCartaoCredito.getValor());
        values.put(DespesaCartaoCredito.VL_PAGAMENTO, despesaCartaoCredito.getValorPagamento());
        values.put(DespesaCartaoCredito.DT_DESPESA, despesaCartaoCredito.getDataDespesa().getTime());
        values.put(DespesaCartaoCredito.FL_DESPESA_PAGA, BooleanUtils.parseBooleanToint(despesaCartaoCredito.isPaga()));
        values.put(DespesaCartaoCredito.FL_DEPESA_FIXA, BooleanUtils.parseBooleanToint(despesaCartaoCredito.isFixa()));
        values.put(DespesaCartaoCredito.FL_ALERTA_DESPESA, BooleanUtils.parseBooleanToint(despesaCartaoCredito.isAlertar()));
        values.put(DespesaCartaoCredito.NO_TOTAL_REPETICAO, despesaCartaoCredito.getTotalRepeticao());
        values.put(DespesaCartaoCredito.NO_REPETICAO_ATUAL, despesaCartaoCredito.getRepeticaoAtual());
        values.put(DespesaCartaoCredito.NO_AM_DESPESA, despesaCartaoCredito.getAnoMesDespesa());
        values.put(DespesaCartaoCredito.ID_CARTAO_CREDITO, despesaCartaoCredito.getCartaoCredito().getId());
        values.put(DespesaCartaoCredito.ID_FATURA_CARTAO_CREDITO, despesaCartaoCredito.getFaturaCartaoCredito().getId());
        values.put(DespesaCartaoCredito.ID_CATEGORIA_DESPESA, despesaCartaoCredito.getCategoriaDespesa().getId());

        if (despesaCartaoCredito.getSubCategoriaDespesa().getId() != 0)
            values.put(DespesaCartaoCredito.ID_SUB_CATEGORIA_DESPESA, despesaCartaoCredito.getSubCategoriaDespesa().getId());

        values.put(DespesaCartaoCredito.NO_ORDEM_EXIBICAO, despesaCartaoCredito.getOrdemExibicao());
        values.put(DespesaCartaoCredito.FL_ATIVO, BooleanUtils.parseBooleanToint(despesaCartaoCredito.isAtivo()));
        values.put(DespesaCartaoCredito.FL_EXIBIR, BooleanUtils.parseBooleanToint(despesaCartaoCredito.isExibir()));

        if (despesaCartaoCredito.isPaga()) {
            values.put(DespesaCartaoCredito.DT_PAGAMENTO, despesaCartaoCredito.getDataPagamento().getTime());
            values.put(DespesaCartaoCredito.NO_AM_PAGAMENTO_DESPESA, despesaCartaoCredito.getAnoMesPagamento());
        } else {
            values.put(DespesaCartaoCredito.DT_PAGAMENTO, "");
            values.put(DespesaCartaoCredito.NO_AM_PAGAMENTO_DESPESA, "");
        }

        if (despesaCartaoCredito.getIdPai() != 0)
            values.put(DespesaCartaoCredito.ID_DESPESA_PAI, despesaCartaoCredito.getIdPai());

        if (despesaCartaoCredito.getId() != 0)
            values.put(DespesaCartaoCredito.DT_ALTERACAO, despesaCartaoCredito.getDataAlteracao().getTime());
        else
            values.put(DespesaCartaoCredito.DT_INCLUSAO, despesaCartaoCredito.getDataInclusao().getTime());

        return values;
    }

    private ArrayList<DespesaCartaoCredito> preencheObjeto(SQLiteDatabase transaction, Cursor cursor) {

        ArrayList<DespesaCartaoCredito> arrayList = new ArrayList<DespesaCartaoCredito>();

        RepositorioCategoriaDespesa repCategoriaDespesaCartaoCredito = new RepositorioCategoriaDespesa(super.getContext());
        RepositorioSubCategoriaDespesa repSubCategoriaDespesaCartaoCredito = new RepositorioSubCategoriaDespesa(super.getContext());
        RepositorioCartaoCredito repCartao = new RepositorioCartaoCredito(super.getContext());
        RepositorioaFaturaCartaoCredito repFatura = new RepositorioaFaturaCartaoCredito(super.getContext());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                DespesaCartaoCredito despesa = new DespesaCartaoCredito();

                despesa.setId(cursor.getLong(cursor.getColumnIndex(DespesaCartaoCredito.ID)));
                despesa.setNome(cursor.getString(cursor.getColumnIndex(DespesaCartaoCredito.NM_DESPESA)));
                despesa.setOrdemExibicao(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.NO_ORDEM_EXIBICAO)));
                despesa.setExibir(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.FL_EXIBIR))));
                despesa.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.FL_ATIVO))));
                despesa.setValor(cursor.getDouble(cursor.getColumnIndex(DespesaCartaoCredito.VL_DESPESA)));
                despesa.setDataDespesa(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(DespesaCartaoCredito.DT_DESPESA))));

                despesa.setValorPagamento(cursor.getDouble(cursor.getColumnIndex(DespesaCartaoCredito.VL_PAGAMENTO)));
                despesa.setDataPagamento(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(DespesaCartaoCredito.DT_PAGAMENTO))));

                despesa.setPaga(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.FL_DESPESA_PAGA))));
                despesa.setFixa(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.FL_DEPESA_FIXA))));
                despesa.setAlertar(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.FL_ALERTA_DESPESA))));

                despesa.setIdTipoRepeticao(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.ID_TIPO_REPETICAO)));
                despesa.setTotalRepeticao(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.NO_TOTAL_REPETICAO)));
                despesa.setRepeticaoAtual(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.NO_REPETICAO_ATUAL)));

                despesa.setAnoMesDespesa(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.NO_AM_DESPESA)));
                despesa.setAnoMesPagamento(cursor.getInt(cursor.getColumnIndex(DespesaCartaoCredito.NO_AM_PAGAMENTO_DESPESA)));

                despesa.setIdPai(cursor.getLong(cursor.getColumnIndex(DespesaCartaoCredito.ID_DESPESA_PAI)));

                despesa.setCartaoCredito(repCartao.get(transaction, cursor.getLong(cursor.getColumnIndex(DespesaCartaoCredito.ID_CARTAO_CREDITO))));
                despesa.setFaturaCartaoCredito(repFatura.get(transaction, cursor.getLong(cursor.getColumnIndex(DespesaCartaoCredito.ID_FATURA_CARTAO_CREDITO))));

                despesa.setCategoriaDespesa(repCategoriaDespesaCartaoCredito.get(transaction, cursor.getLong(cursor.getColumnIndex(DespesaCartaoCredito.ID_CATEGORIA_DESPESA))));
                despesa.setSubCategoriaDespesa(repSubCategoriaDespesaCartaoCredito.get(transaction, cursor.getLong(cursor.getColumnIndex(DespesaCartaoCredito.ID_SUB_CATEGORIA_DESPESA))));

                despesa.setDataAlteracao(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(DespesaCartaoCredito.DT_ALTERACAO))));
                despesa.setDataInclusao(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(DespesaCartaoCredito.DT_INCLUSAO))));

                arrayList.add(despesa);

            } while (cursor.moveToNext());
        }

        return arrayList;

    }

    //Métodos
    @Override
    public int altera(DespesaCartaoCredito item) {

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            DespesaCartaoCredito despesaAnterior = this.get(super.getTransaction(), item.getId());

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            Conta contaAssociadaAtual = item.getCartaoCredito().getContaAssociada();
            Conta contaAssociadaAnterior = despesaAnterior.getCartaoCredito().getContaAssociada();

            if (despesaAnterior.isPaga()) {

                if (contaAssociadaAnterior.getId() == contaAssociadaAtual.getId()) {
                    repositorioConta.setValorEntradaConta(super.getTransaction(), contaAssociadaAtual.getId(), despesaAnterior.getValor());
                } else {
                    repositorioConta.setValorEntradaConta(super.getTransaction(), contaAssociadaAnterior.getId(), despesaAnterior.getValor());
                }
            }

            if (item.isPaga()) {
                repositorioConta.setValorSaidaConta(super.getTransaction(), contaAssociadaAtual.getId(), item.getValor());
            }

            int linhas = super.update(super.getTransaction(), this.preencheContentValues(item), item.getId());

            super.setTransactionSuccessful();

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    @Override
    public long insere(DespesaCartaoCredito item) {
        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            if (item.isPaga()) {

                RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

                Conta contaAssociada = item.getCartaoCredito().getContaAssociada();

                repositorioConta.setValorSaidaConta(super.getTransaction(), contaAssociada.getId(), item.getValor());
            }

            //Insere a DespesaCartaoCredito "Pai".
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
    public int exclui(DespesaCartaoCredito item) {

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            if(item.isPaga()) {
                RepositorioConta repositorioConta = new RepositorioConta(super.getContext());
                repositorioConta.setValorEntradaConta(super.getTransaction(), item.getCartaoCredito().getContaAssociada().getId(), item.getValor());

            }
            super.delete(super.getTransaction(), item.getId());

            this.setTransactionSuccessful();

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    @Override
    public DespesaCartaoCredito get(Long id) {
        String where = DespesaCartaoCredito.ID + "=" + id;

        DespesaCartaoCredito despesa = new DespesaCartaoCredito();

        try {

            super.openConnectionRead();

            Cursor cursor = super.select(where);

            ArrayList<DespesaCartaoCredito> arrayList = this.preencheObjeto(super.getTransaction(), cursor);

            if (arrayList.size() > 0)
                despesa = arrayList.get(0);

            return despesa;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {
            super.closeConnection();
        }
    }

    //Consultas
    public ArrayList<DespesaCartaoCredito> buscaTodos() {
        try {

            super.openConnectionWrite();

            Cursor cursor = super.selectAll(DespesaCartaoCredito.DT_DESPESA + "," + DespesaCartaoCredito.ID);

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public ArrayList<DespesaCartaoCredito> buscaPorAnoMes(int anoMes) {

        String where = DespesaCartaoCredito.NO_AM_DESPESA + " =  " + anoMes;

        try {

            super.openConnectionRead();

            Cursor cursor = super.select(where, DespesaCartaoCredito.DT_DESPESA + "," + DespesaCartaoCredito.ID);

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public ArrayList<DespesaCartaoCredito> buscaPorContaAnoMes(long idCartaoCredito, int anoMes) {

        StringBuilder where = new StringBuilder();

        where.append(DespesaCartaoCredito.ID_CARTAO_CREDITO);
        where.append(" = " + idCartaoCredito);
        where.append(" AND ");
        where.append(DespesaCartaoCredito.NO_AM_DESPESA);
        where.append(" =  " + anoMes);

        try {

            super.openConnectionRead();

            Cursor cursor = super.select(where.toString(), DespesaCartaoCredito.DT_DESPESA + "," + DespesaCartaoCredito.ID);

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public ArrayList<DespesaCartaoCredito> buscaDespesaCartaoCreditosDependentes(SQLiteDatabase transaction, long idPai, long id, boolean proximas, boolean somentePendentes) {

        StringBuilder where = new StringBuilder();

        if (somentePendentes)
            where.append(DespesaCartaoCredito.FL_DESPESA_PAGA + " = 0 AND ");

        if (proximas)
            where.append(DespesaCartaoCredito.ID + " >= " + id + " AND  ");

        if (idPai == 0) {
            where.append(" ( " + DespesaCartaoCredito.ID + " = " + id);
            where.append(" OR " + DespesaCartaoCredito.ID_DESPESA_PAI + " = " + id + " )");
        } else {
            where.append(" ( " + DespesaCartaoCredito.ID_DESPESA_PAI + " = " + idPai);
            where.append(" OR " + DespesaCartaoCredito.ID + " = " + idPai + " )");
        }

        Cursor cursor = super.select(where.toString(), DespesaCartaoCredito.ID);

        return this.preencheObjeto(transaction, cursor);

    }

    public ArrayList<DespesaCartaoCredito> buscaDespesaCartaoCreditosCategoria(SQLiteDatabase transaction, long idCategoriaDespesaCartaoCredito) {

        StringBuilder where = new StringBuilder();

        where.append(DespesaCartaoCredito.ID_CATEGORIA_DESPESA + " = " + idCategoriaDespesaCartaoCredito);

        Cursor cursor = super.select(transaction, where.toString(), DespesaCartaoCredito.DT_DESPESA + "," + DespesaCartaoCredito.ID);

        return this.preencheObjeto(transaction, cursor);
    }

    public ArrayList<DespesaCartaoCredito> buscaDespesaCartaoCreditosSubCategoria(SQLiteDatabase transaction, long idSubCategoriaDespesaCartaoCredito) {

        StringBuilder where = new StringBuilder();

        where.append(DespesaCartaoCredito.ID_SUB_CATEGORIA_DESPESA + " = " + idSubCategoriaDespesaCartaoCredito);

        Cursor cursor = super.select(transaction, where.toString(), DespesaCartaoCredito.DT_DESPESA + "," + DespesaCartaoCredito.ID);

        return this.preencheObjeto(transaction, cursor);
    }

    public DespesaCartaoCredito get(SQLiteDatabase transaction, Long id) {
        String where = DespesaCartaoCredito.ID + "=" + id;

        DespesaCartaoCredito despesa = new DespesaCartaoCredito();

        try {


            Cursor cursor = super.select(transaction, where);

            ArrayList<DespesaCartaoCredito> arrayList = this.preencheObjeto(transaction, cursor);

            if (arrayList.size() > 0)
                despesa = arrayList.get(0);

            return despesa;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_despesa));
        }
    }


//Totalizadores

    public Double getValorTotalDespesaCartaoCreditos(int anoMes, boolean somentePagas) {
        String[] parametros = {String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT SUM( ");
        sql.append(DespesaCartaoCredito.VL_DESPESA);
        sql.append(" ) AS VL_TOTAL_DESPESA FROM ");
        sql.append(DespesaCartaoCredito.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(DespesaCartaoCredito.NO_AM_DESPESA);
        sql.append(" = ? ");
        if (somentePagas) {
            sql.append(" AND ");
            sql.append(DespesaCartaoCredito.FL_DESPESA_PAGA + " = 1");
        }
        double valorTotal = 0;

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(super.getTransaction(), sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    valorTotal = cursor.getDouble(cursor.getColumnIndex("VL_TOTAL_DESPESA"));

                } while (cursor.moveToNext());
            }
            return valorTotal;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public Double getValorTotalDespesaCartaoCreditosContaMes(long idCartaoCredito, int anoMes, boolean somentePagas) {
        String[] parametros = {String.valueOf(idCartaoCredito), String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT SUM( ");
        sql.append(DespesaCartaoCredito.VL_DESPESA);
        sql.append(" ) AS VL_TOTAL_DESPESA FROM ");
        sql.append(DespesaCartaoCredito.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(DespesaCartaoCredito.ID_CARTAO_CREDITO);
        sql.append(" = ? ");
        sql.append(" AND " + DespesaCartaoCredito.NO_AM_DESPESA);
        sql.append(" = ?");

        if (somentePagas) {
            sql.append(" AND ");
            sql.append(DespesaCartaoCredito.FL_DESPESA_PAGA + " = 1");
        }
        double valorTotal = 0;

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(super.getTransaction(), sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    valorTotal = cursor.getDouble(cursor.getColumnIndex("VL_TOTAL_DESPESA"));

                } while (cursor.moveToNext());
            }
            return valorTotal;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public int getQtdDespesaCartaoCreditoConta(long idCartaoCredito) {
        int qtdDespesaCartaoCreditos = 0;

        String[] parametros = {String.valueOf(idCartaoCredito)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(DespesaCartaoCredito.TABELA_NOME);
        sql.append(" WHERE " + DespesaCartaoCredito.ID_CARTAO_CREDITO);
        sql.append(" = ?");


        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdDespesaCartaoCreditos = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }


            return qtdDespesaCartaoCreditos;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    public int getQtdDespesaCartaoCreditoContaMes(long idCartaoCredito, int anoMes, boolean somentePagas) {

        int qtdDespesaCartaoCreditos = 0;

        String[] parametros = {String.valueOf(idCartaoCredito), String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(DespesaCartaoCredito.TABELA_NOME);
        sql.append(" WHERE " + DespesaCartaoCredito.ID_CARTAO_CREDITO);
        sql.append(" = ? AND ");
        sql.append(DespesaCartaoCredito.NO_AM_DESPESA);
        sql.append(" = ? ");

        if (somentePagas) {
            sql.append(" AND ");
            sql.append(DespesaCartaoCredito.FL_DESPESA_PAGA + " = 1");
        }

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdDespesaCartaoCreditos = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }


            return qtdDespesaCartaoCreditos;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    public int getQtdDespesaCartaoCreditoCategoria(long idCategoriaDespesaCartaoCredito) {

        int qtdDespesaCartaoCreditos = 0;

        String[] parametros = {String.valueOf(idCategoriaDespesaCartaoCredito)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(DespesaCartaoCredito.TABELA_NOME);
        sql.append(" WHERE " + DespesaCartaoCredito.ID_CATEGORIA_DESPESA);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdDespesaCartaoCreditos = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }

            return qtdDespesaCartaoCreditos;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    public int getQtdDespesaCartaoCreditoSubCategoria(long idSubCategoriaDespesaCartaoCredito) {

        int qtdDespesaCartaoCreditos = 0;

        String[] parametros = {String.valueOf(idSubCategoriaDespesaCartaoCredito)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(DespesaCartaoCredito.TABELA_NOME);
        sql.append(" WHERE " + DespesaCartaoCredito.ID_SUB_CATEGORIA_DESPESA);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdDespesaCartaoCreditos = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }

            return qtdDespesaCartaoCreditos;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }


    //Alterações
    public int alteraProximas(DespesaCartaoCredito despesaAlterada) {

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            //Busca as despesas que serão alteradas.
            ArrayList<DespesaCartaoCredito> proximasOcorrenciasDespesaCartaoCreditos = this.buscaDespesaCartaoCreditosDependentes(super.getTransaction(), despesaAlterada.getIdPai(), despesaAlterada.getId(), true, false);

            this.atualizaOcorrenciasDespesaCartaoCreditos(super.getTransaction(), despesaAlterada, proximasOcorrenciasDespesaCartaoCreditos);

            super.setTransactionSuccessful();

            return proximasOcorrenciasDespesaCartaoCreditos.size();

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_despesa));
        } finally

        {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    public int alteraTodas(DespesaCartaoCredito despesaAlterada) {

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            //Busca as despesas que serão alteradas.
            ArrayList<DespesaCartaoCredito> todasOcorrenciasDespesaCartaoCreditos = this.buscaDespesaCartaoCreditosDependentes(super.getTransaction(), despesaAlterada.getIdPai(), despesaAlterada.getId(), false, false);

            this.atualizaOcorrenciasDespesaCartaoCreditos(super.getTransaction(), despesaAlterada, todasOcorrenciasDespesaCartaoCreditos);

            super.setTransactionSuccessful();

            return todasOcorrenciasDespesaCartaoCreditos.size();

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_despesa));
        } finally

        {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    private void atualizaOcorrenciasDespesaCartaoCreditos(SQLiteDatabase transaction, DespesaCartaoCredito despesaAlterada, ArrayList<DespesaCartaoCredito> listaDepesas) {

        RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

        for (DespesaCartaoCredito ocorrencia : listaDepesas) {

            if (ocorrencia.isPaga()) {

                if (ocorrencia.getCartaoCredito().getContaAssociada().getId() == despesaAlterada.getCartaoCredito().getContaAssociada().getId()) {
                    repositorioConta.setValorEntradaConta(transaction, despesaAlterada.getCartaoCredito().getContaAssociada().getId(), ocorrencia.getValor());
                } else {
                    repositorioConta.setValorEntradaConta(transaction, ocorrencia.getCartaoCredito().getContaAssociada().getId(), ocorrencia.getValor());
                }
            }

            if (despesaAlterada.isPaga()) {
                repositorioConta.setValorSaidaConta(transaction, despesaAlterada.getCartaoCredito().getContaAssociada().getId(), despesaAlterada.getValor());
            }

            ocorrencia.setNome(despesaAlterada.getNome());
            ocorrencia.setDataAlteracao(despesaAlterada.getDataAlteracao());
            ocorrencia.setCategoriaDespesa(despesaAlterada.getCategoriaDespesa());
            ocorrencia.setSubCategoriaDespesa(despesaAlterada.getSubCategoriaDespesa());
            ocorrencia.setCartaoCredito(despesaAlterada.getCartaoCredito());

            ocorrencia.setEstornaPagamento(despesaAlterada.isEstornaPagamento());

            ocorrencia.setDataPagamento(despesaAlterada.getDataPagamento());
            ocorrencia.setPaga(despesaAlterada.isPaga());
            ocorrencia.setDataPagamento(despesaAlterada.getDataPagamento());
            ocorrencia.setValor(despesaAlterada.getValor());
            ocorrencia.setAnoMesPagamento(despesaAlterada.getAnoMesPagamento());
            ocorrencia.setAlertar(despesaAlterada.isAlertar());


            super.update(transaction, preencheContentValues(ocorrencia), ocorrencia.getId());

        }


    }

    //Exclusões
    public int excluiProximas(DespesaCartaoCredito item) {
        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            int linhas = 0;

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Busca as despesas que serão excluidas.
            ArrayList<DespesaCartaoCredito> todasOcorrenciasDespesaCartaoCreditos = this.buscaDespesaCartaoCreditosDependentes(super.getTransaction(), item.getIdPai(), item.getId(), true, false);

            for (DespesaCartaoCredito ocorrencia : todasOcorrenciasDespesaCartaoCreditos) {

                if (ocorrencia.isPaga()) {
                    //Estorna o pagamento realizado
                    repositorioConta.setValorEntradaConta(super.getTransaction(), ocorrencia.getCartaoCredito().getContaAssociada().getId(), ocorrencia.getValor());
                }

                super.delete(super.getTransaction(), ocorrencia.getId());

                linhas = linhas++;
            }

            this.setTransactionSuccessful();

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_despesa));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    public int excluiTodas(DespesaCartaoCredito item) {

        try {
            super.openConnectionWrite();
            super.setBeginTransaction();
            int linhas = 0;

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Busca as despesas que serão excluidas.
            ArrayList<DespesaCartaoCredito> todasOcorrenciasDespesaCartaoCreditos = this.buscaDespesaCartaoCreditosDependentes(super.getTransaction(), item.getIdPai(), item.getId(), false, false);

            for (DespesaCartaoCredito ocorrencia : todasOcorrenciasDespesaCartaoCreditos) {

                if (ocorrencia.isPaga()) {
                    //Estorna o pagamento realizado
                    repositorioConta.setValorEntradaConta(super.getTransaction(), ocorrencia.getCartaoCredito().getContaAssociada().getId(), ocorrencia.getValor());
                }

                super.delete(super.getTransaction(), ocorrencia.getId());

                linhas = linhas++;
            }
            this.setTransactionSuccessful();

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_despesa));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    public int excluiDespesaCartaoCreditosCategoria(SQLiteDatabase transaction, long idCategoriaDespesaCartaoCredito) {
        try {

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());
            int linhas = 0;
            //Busca as despesas da categoria que serão excluidas.
            ArrayList<DespesaCartaoCredito> todasOcorrenciasDespesaCartaoCreditos = this.buscaDespesaCartaoCreditosCategoria(transaction, idCategoriaDespesaCartaoCredito);

            for (DespesaCartaoCredito ocorrencia : todasOcorrenciasDespesaCartaoCreditos) {

                if (ocorrencia.isPaga()) {
                    //Estorna o pagamento realizado
                    repositorioConta.setValorEntradaConta(transaction, ocorrencia.getCartaoCredito().getContaAssociada().getId(), ocorrencia.getValor());
                }

                super.delete(transaction, ocorrencia.getId());

                linhas = linhas++;
            }

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_despesa));
        }
    }

    public int excluiDespesaCartaoCreditosSubCategoria(SQLiteDatabase transaction, long idSubCategoriaDespesaCartaoCredito) {
        try {

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());
            int linhas = 0;
            //Busca as despesas da categoria que serão excluidas.
            ArrayList<DespesaCartaoCredito> todasOcorrenciasDespesaCartaoCreditos = this.buscaDespesaCartaoCreditosSubCategoria(transaction, idSubCategoriaDespesaCartaoCredito);

            for (DespesaCartaoCredito ocorrencia : todasOcorrenciasDespesaCartaoCreditos) {

                if (ocorrencia.isPaga()) {
                    //Estorna o pagamento realizado
                    repositorioConta.setValorEntradaConta(transaction, ocorrencia.getCartaoCredito().getContaAssociada().getId(), ocorrencia.getValor());
                }

                super.delete(transaction, ocorrencia.getId());

                linhas = linhas++;
            }

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_despesa));
        }
    }

    public int excluiTodasSemEstorno(SQLiteDatabase transaction, long idCartaoCredito) {

        String[] parametros = new String[]{String.valueOf(idCartaoCredito)};

        StringBuilder sql = new StringBuilder();

        sql.append(" DELETE FROM ");
        sql.append(DespesaCartaoCredito.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(DespesaCartaoCredito.ID_CARTAO_CREDITO + " = ?");

        try {

            super.executeCustomQuery(transaction, sql.toString(), parametros);

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_despesa));
        }
    }



}