package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Receita;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.TipoRepeticao;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by charl on 27/09/2016.
 */

public class RepositorioReceita extends RepositorioBase implements IRepositorio<Receita, Long> {


    //Construtor
    public RepositorioReceita(Context context) {
        super(context, Receita.TABELA_NOME);
    }

    //Métodos Principais
    @Override
    public int altera(Receita item) {

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            Receita receitaAnterior = this.get(super.getTransaction(), item.getId());

            if (receitaAnterior.isPaga()) {

                if (receitaAnterior.getConta().getId() == item.getConta().getId()) {
                    repositorioConta.setValorSaidaConta(super.getTransaction(), item.getConta().getId(), receitaAnterior.getValor());
                } else {
                    repositorioConta.setValorSaidaConta(super.getTransaction(), receitaAnterior.getConta().getId(), receitaAnterior.getValor());
                }
            }

            if (item.isPaga()) {
                repositorioConta.setValorEntradaConta(super.getTransaction(), item.getConta().getId(), item.getValor());
            }


            int linhas = super.update(super.getTransaction(), this.preencheContentValues(item), item.getId());

            super.setTransactionSuccessful();

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_receita));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    @Override
    public long insere(Receita item) {

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();
            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Insere a Receita "Pai".
            long id = super.insert(super.getTransaction(), this.preencheContentValues(item));
            item.setId(id);

            if (item.isPaga()) {
                repositorioConta.setValorEntradaConta(super.getTransaction(), item.getConta().getId(), item.getValor());
            }

            //Insere as Receitas filhas
            if (item.getTotalRepeticao() > 0) {

                int total = item.getTotalRepeticao();
                Date data = item.getDataReceita(); //Diaria
                int tipoRepeticao = item.getIdTipoRepeticao();

                for (int i = 2; i <= total; i++) {

                    Receita nova = item.clone();

                    nova.setIdPai(item.getId());
                    nova.setId(0);
                    nova.setPaga(false);
                    nova.setDataRecebimento(null);
                    nova.setAnoMesRecebimentoReceita(null);
                    nova.setEstornaRecebimentoReceita(false);
                    nova.setRepeticaoAtual(i);

                    if (tipoRepeticao != TipoRepeticao.DIARIA)
                        data = TipoRepeticao.getDataRepeticao(tipoRepeticao, data);

                    nova.setDataReceita(data);
                    nova.setAnoMes(DateUtils.getYearAndMonth(data));

                    super.insert(super.getTransaction(), preencheContentValues(nova));

                }
            } else if (item.isFixa()) {

                int total = Constantes.TOTAL_MESES_REPETICAO; //20 anos.

                Date data = item.getDataReceita(); //Diaria

                for (int i = 2; i <= total; i++) {

                    Receita nova = item.clone();

                    nova.setIdPai(item.getId());
                    nova.setId(0);
                    nova.setPaga(false);
                    nova.setDataRecebimento(null);
                    nova.setAnoMesRecebimentoReceita(null);
                    nova.setEstornaRecebimentoReceita(false);

                    data = TipoRepeticao.getDataRepeticao(TipoRepeticao.MENSAL, data);

                    nova.setDataReceita(data);
                    nova.setAnoMes(DateUtils.getYearAndMonth(data));

                    super.insert(super.getTransaction(), preencheContentValues(nova));

                }
            }


            super.setTransactionSuccessful();

            return id;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_receita));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    @Override
    public int exclui(Receita item) {
        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            if (item.isPaga())
                repositorioConta.setValorSaidaConta(super.getTransaction(), item.getConta().getId(), item.getValor());

            super.delete(super.getTransaction(), item.getId());

            this.setTransactionSuccessful();

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_receita));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    @Override
    public Receita get(Long id) {
        String where = Receita.ID + "=" + id;

        Receita receita = new Receita();

        Cursor cursor = null;
        try {

            super.openConnectionRead();

            cursor = super.select(where);

            ArrayList<Receita> arrayList = this.preencheObjeto(super.getTransaction(), cursor);

            if (arrayList.size() > 0)
                receita = arrayList.get(0);

            return receita;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {
            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }
    }

    public Receita get(SQLiteDatabase transaction, Long id) {
        String where = Receita.ID + "=" + id;

        Receita receita = new Receita();
        Cursor cursor = null;
        try {


            cursor = super.select(transaction, where);

            ArrayList<Receita> arrayList = this.preencheObjeto(transaction, cursor);

            if (arrayList.size() > 0)
                receita = arrayList.get(0);

            return receita;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {

            if (cursor != null)
                cursor.close();

        }
    }

    //Auxiliares
    private ContentValues preencheContentValues(Receita receita) {

        ContentValues values = new ContentValues();

        values.put(Receita.NM_RECEITA, receita.getNome());
        values.put(Receita.VL_RECEITA, receita.getValor());
        values.put(Receita.DT_RECEITA, receita.getDataReceita().getTime());
        values.put(Receita.FL_RECEITA_PAGA, BooleanUtils.parseBooleanToint(receita.isPaga()));
        values.put(Receita.FL_RECEITA_FIXA, BooleanUtils.parseBooleanToint(receita.isFixa()));
        values.put(Receita.FL_ALERTA_RECEITA, BooleanUtils.parseBooleanToint(receita.isAlertar()));
        values.put(Receita.NO_TOTAL_REPETICAO, receita.getTotalRepeticao());
        values.put(Receita.NO_REPETICAO_ATUAL, receita.getRepeticaoAtual());
        values.put(Receita.ID_CONTA, receita.getConta().getId());
        values.put(Receita.ID_CATEGORIA_RECEITA, receita.getCategoriaReceita().getId());
        values.put(Receita.ID_TIPO_REPETICAO, receita.getIdTipoRepeticao());
        values.put(Receita.NO_AM_RECEITA, receita.getAnoMes());
        values.put(Receita.NO_AM_RECEBIMENTO_RECEITA, receita.getAnoMesRecebimentoReceita());
        values.put(Receita.NO_ORDEM_EXIBICAO, receita.getOrdemExibicao());
        values.put(Receita.FL_ATIVO, BooleanUtils.parseBooleanToint(receita.isAtivo()));
        values.put(Receita.FL_EXIBIR, BooleanUtils.parseBooleanToint(receita.isExibir()));
        values.put(Receita.DT_INCLUSAO, receita.getDataInclusao().getTime());

        if (receita.isPaga())
            values.put(Receita.DT_RECEBIMENTO, receita.getDataRecebimento().getTime());
        else
            values.put(Receita.DT_RECEBIMENTO, "");

        if (receita.getIdPai() != 0)
            values.put(Receita.ID_RECEITA_PAI, receita.getIdPai());

        if (receita.getId() != 0)
            values.put(Receita.DT_ALTERACAO, receita.getDataAlteracao().getTime());

        return values;
    }

    private ArrayList<Receita> preencheObjeto(SQLiteDatabase transaction, Cursor cursor) {

        ArrayList<Receita> arrayList = new ArrayList<Receita>();
        RepositorioCategoriaReceita repCategoriaReceita = new RepositorioCategoriaReceita(super.getContext());
        RepositorioConta repConta = new RepositorioConta(super.getContext());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Receita receita = new Receita();

                receita.setId(cursor.getLong(cursor.getColumnIndex(Receita.ID)));
                receita.setNome(cursor.getString(cursor.getColumnIndex(Receita.NM_RECEITA)));
                receita.setOrdemExibicao(cursor.getInt(cursor.getColumnIndex(Receita.NO_ORDEM_EXIBICAO)));
                receita.setExibir(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Receita.FL_EXIBIR))));
                receita.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Receita.FL_ATIVO))));
                receita.setAlertar(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Receita.FL_ALERTA_RECEITA))));
                receita.setValor(cursor.getDouble(cursor.getColumnIndex(Receita.VL_RECEITA)));
                receita.setPaga(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Receita.FL_RECEITA_PAGA))));
                receita.setFixa(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Receita.FL_RECEITA_FIXA))));

                receita.setDataReceita(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(Receita.DT_RECEITA))));
                receita.setDataRecebimento(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(Receita.DT_RECEBIMENTO))));
                receita.setDataAlteracao(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(Receita.DT_ALTERACAO))));
                receita.setDataInclusao(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(Receita.DT_INCLUSAO))));

                receita.setAnoMes(cursor.getInt(cursor.getColumnIndex(Receita.NO_AM_RECEITA)));
                receita.setAnoMesRecebimentoReceita(cursor.getInt(cursor.getColumnIndex(Receita.NO_AM_RECEBIMENTO_RECEITA)));

                receita.setIdTipoRepeticao(cursor.getInt(cursor.getColumnIndex(Receita.ID_TIPO_REPETICAO)));
                receita.setTotalRepeticao(cursor.getInt(cursor.getColumnIndex(Receita.NO_TOTAL_REPETICAO)));
                receita.setRepeticaoAtual(cursor.getInt(cursor.getColumnIndex(Receita.NO_REPETICAO_ATUAL)));

                receita.setConta(repConta.get(transaction, cursor.getLong(cursor.getColumnIndex(Receita.ID_CONTA))));

                receita.setIdPai(cursor.getLong(cursor.getColumnIndex(Receita.ID_RECEITA_PAI)));
                receita.setCategoriaReceita(repCategoriaReceita.getCatergoriaReceita(transaction, cursor.getLong(cursor.getColumnIndex(Receita.ID_CATEGORIA_RECEITA))));

                arrayList.add(receita);

            } while (cursor.moveToNext());
        }

        return arrayList;

    }

    //Consultas
    public ArrayList<Receita> getAll() {
        Cursor cursor = null;
        ArrayList<Receita> listaReceitas = null;
        try {

            super.openConnectionRead();

            cursor = super.selectAll(Receita.DT_RECEITA + "," + Receita.ID);

            listaReceitas = this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {
            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }

        return listaReceitas;
    }

    public ArrayList<Receita> buscaPorAnoMes(int anoMes) {

        String where = Receita.NO_AM_RECEITA + " =  " + anoMes;
        Cursor cursor = null;
        ArrayList<Receita> listaReceitas = null;
        try {

            super.openConnectionRead();

            cursor = super.select(where, Receita.DT_RECEITA + "," + Receita.ID);

            listaReceitas = this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {

            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }

        return listaReceitas;
    }

    public ArrayList<Receita> buscaPorContaAnoMes(long idConta, int anoMes) {

        StringBuilder where = new StringBuilder();

        where.append(Receita.ID_CONTA);
        where.append(" = " + idConta);
        where.append(" AND ");
        where.append(Receita.NO_AM_RECEITA);
        where.append(" =  " + anoMes);

        Cursor cursor = null;
        ArrayList<Receita> listaReceitas = null;

        try {

            super.openConnectionRead();

            cursor = super.select(where.toString(), Receita.DT_RECEITA + "," + Receita.ID);

            listaReceitas = this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {

            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }

        return listaReceitas;
    }

    public ArrayList<Receita> buscaReceitasDependentes(SQLiteDatabase transaction, long idPai, long id, boolean proximas, boolean somentePendentes) {

        StringBuilder where = new StringBuilder();

        if (somentePendentes)
            where.append(Receita.FL_RECEITA_PAGA + " = 0 AND ");

        if (proximas)
            where.append(Receita.ID + " >= " + id + " AND  ");

        if (idPai == 0) {
            where.append(" ( " + Receita.ID + " = " + id);
            where.append(" OR " + Receita.ID_RECEITA_PAI + " = " + id + " )");
        } else {
            where.append(" ( " + Receita.ID_RECEITA_PAI + " = " + idPai);
            where.append(" OR " + Receita.ID + " = " + idPai + " )");
        }


        Cursor cursor = null;
        ArrayList<Receita> listaReceitas = null;

        try {
            cursor = super.select(where.toString(), Receita.ID);

            listaReceitas = this.preencheObjeto(transaction, cursor);
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {

            if (cursor != null)
                cursor.close();
        }

        return listaReceitas;


    }

    public ArrayList<Receita> buscaReceitasCategoria(SQLiteDatabase transaction, long idCategoriaReceita) {

        StringBuilder where = new StringBuilder();

        where.append(Receita.ID_CATEGORIA_RECEITA + " = " + idCategoriaReceita);


        Cursor cursor = null;
        ArrayList<Receita> listaReceitas = null;

        try {

            cursor = super.select(transaction, where.toString(), Receita.DT_RECEITA + "," + Receita.ID);

            listaReceitas = this.preencheObjeto(transaction, cursor);
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {

            if (cursor != null)
                cursor.close();
        }

        return listaReceitas;
    }


    //Totalizadores
    public Double getValorTotalRecebido(int anoMes, boolean somentePagas) {
        String[] parametros = {String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT SUM( ");
        sql.append(Receita.VL_RECEITA);
        sql.append(" ) AS VL_TOTAL_RECEITA FROM ");
        sql.append(Receita.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(Receita.NO_AM_RECEITA);
        sql.append(" = ? ");

        if (somentePagas) {
            sql.append(" AND ");
            sql.append(Receita.FL_RECEITA_PAGA + " = 1");
        }

        double valorTotal = 0;
        Cursor cursor = null;
        try {

            super.openConnectionRead();

            cursor = super.selectCustomQuery(super.getTransaction(), sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    valorTotal = cursor.getDouble(cursor.getColumnIndex("VL_TOTAL_RECEITA"));

                } while (cursor.moveToNext());
            }
            return valorTotal;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {

            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }
    }

    public Double getValorTotalRecebidoContaMes(long idConta, int anoMes, boolean somentePagas) {
        String[] parametros = {String.valueOf(idConta), String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT SUM( ");
        sql.append(Receita.VL_RECEITA);
        sql.append(" ) AS VL_TOTAL_RECEITA FROM ");
        sql.append(Receita.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(Receita.ID_CONTA);
        sql.append(" = ? ");
        sql.append(" AND ");
        sql.append(Receita.NO_AM_RECEITA);
        sql.append(" = ?");

        if (somentePagas) {
            sql.append(" AND ");
            sql.append(Receita.FL_RECEITA_PAGA + " = 1");
        }

        double valorTotal = 0;
        Cursor cursor = null;
        try {

            super.openConnectionRead();

            cursor = super.selectCustomQuery(super.getTransaction(), sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    valorTotal = cursor.getDouble(cursor.getColumnIndex("VL_TOTAL_RECEITA"));

                } while (cursor.moveToNext());
            }
            return valorTotal;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {

            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }
    }

    public int getQtdReceitaConta(long idConta) {
        int qtdReceitas = 0;

        String[] parametros = {String.valueOf(idConta)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(Receita.TABELA_NOME);
        sql.append(" WHERE " + Receita.ID_CONTA);
        sql.append(" = ?");
        Cursor cursor = null;
        try {

            super.openConnectionRead();

            cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdReceitas = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }


            return qtdReceitas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {


            if (cursor != null)
                cursor.close();
            super.closeConnection();
        }

    }

    public int getQtdReceitaContaMes(long idConta, int anoMes, boolean somentePagas) {
        int qtdReceitas = 0;

        String[] parametros = {String.valueOf(idConta), String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(Receita.TABELA_NOME);
        sql.append(" WHERE " + Receita.ID_CONTA);
        sql.append(" = ?");
        sql.append(" AND ");
        sql.append(Receita.NO_AM_RECEITA);
        sql.append(" = ? ");

        if (somentePagas) {
            sql.append(" AND ");
            sql.append(Receita.FL_RECEITA_PAGA + " = 1");
        }

        Cursor cursor = null;
        try {

            super.openConnectionRead();

            cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdReceitas = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }


            return qtdReceitas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {


            if (cursor != null)
                cursor.close();
            super.closeConnection();
        }

    }

    public int getQtdReceitaCategoria(long idCategoriaReceita) {

        int qtdReceitas = 0;

        String[] parametros = {String.valueOf(idCategoriaReceita)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(Receita.TABELA_NOME);
        sql.append(" WHERE " + Receita.ID_CATEGORIA_RECEITA);
        sql.append(" = ?");

        Cursor cursor = null;

        try {

            super.openConnectionRead();

             cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdReceitas = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }


            return qtdReceitas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_receita));
        } finally {


            if (cursor != null)
                cursor.close();

            super.closeConnection();
        }

    }

    //Alterações
    public int alteraProximas(Receita receitaAlterada) {

        double valorAnterior = 0;
        double valorDiferenca = 0;

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            //Busca as receitas que serão alteradas.
            ArrayList<Receita> proximasOcorrenciasReceitas = this.buscaReceitasDependentes(super.getTransaction(), receitaAlterada.getIdPai(), receitaAlterada.getId(), true, false);

            this.atualizaOcorrenciasReceitas(super.getTransaction(), receitaAlterada, proximasOcorrenciasReceitas);

            super.setTransactionSuccessful();

            return proximasOcorrenciasReceitas.size();

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_receita));
        } finally {

            super.setEndTransaction();
            super.closeConnection();
        }

    }

    public int alteraTodas(Receita receitaAlterada) {

        double valorAnterior = 0;
        double valorDiferenca = 0;

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            //Busca as receitas que serão alteradas.
            ArrayList<Receita> todasOcorrenciasReceitas = this.buscaReceitasDependentes(super.getTransaction(), receitaAlterada.getIdPai(), receitaAlterada.getId(), false, false);

            this.atualizaOcorrenciasReceitas(super.getTransaction(), receitaAlterada, todasOcorrenciasReceitas);

            super.setTransactionSuccessful();

            return todasOcorrenciasReceitas.size();

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_receita));
        } finally

        {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    private void atualizaOcorrenciasReceitas(SQLiteDatabase transaction, Receita receitaAlterada, ArrayList<Receita> listaReceita) {

        RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

        for (Receita ocorrencia : listaReceita) {

            if (ocorrencia.isPaga()) {

                if (ocorrencia.getConta().getId() == receitaAlterada.getConta().getId()) {
                    repositorioConta.setValorSaidaConta(transaction, receitaAlterada.getConta().getId(), ocorrencia.getValor());
                } else {
                    repositorioConta.setValorSaidaConta(transaction, ocorrencia.getConta().getId(), ocorrencia.getValor());
                }
            }

            if (receitaAlterada.isPaga()) {
                repositorioConta.setValorEntradaConta(transaction, receitaAlterada.getConta().getId(), receitaAlterada.getValor());
            }

            ocorrencia.setNome(receitaAlterada.getNome());
            ocorrencia.setDataAlteracao(receitaAlterada.getDataAlteracao());
            ocorrencia.setCategoriaReceita(receitaAlterada.getCategoriaReceita());
            ocorrencia.setConta(receitaAlterada.getConta());
            ocorrencia.setEstornaRecebimentoReceita(receitaAlterada.isEstornaRecebimentoReceita());
            ocorrencia.setPaga(receitaAlterada.isPaga());
            ocorrencia.setDataRecebimento(receitaAlterada.getDataRecebimento());
            ocorrencia.setAnoMesRecebimentoReceita(receitaAlterada.getAnoMesRecebimentoReceita());
            ocorrencia.setValor(receitaAlterada.getValor());
            ocorrencia.setAlertar(receitaAlterada.isAlertar());

            super.update(transaction, preencheContentValues(ocorrencia), ocorrencia.getId());

        }

    }


    //Exclusões
    public int excluiProximas(Receita item) {
        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            int linhas = 0;

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Busca as receitas que serão excluidas.
            ArrayList<Receita> todasOcorrenciasReceitas = this.buscaReceitasDependentes(super.getTransaction(), item.getIdPai(), item.getId(), true, false);

            for (Receita ocorrencia : todasOcorrenciasReceitas) {

                if (ocorrencia.isPaga()) {
                    //Estorna o pagamento realizado
                    repositorioConta.setValorSaidaConta(super.getTransaction(), ocorrencia.getConta().getId(), ocorrencia.getValor());
                }

                super.delete(super.getTransaction(), ocorrencia.getId());

                linhas = linhas++;
            }

            this.setTransactionSuccessful();

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_receita));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    public int excluiTodas(Receita item) {

        try {
            super.openConnectionWrite();
            super.setBeginTransaction();
            int linhas = 0;

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Busca as receitas que serão excluidas.
            ArrayList<Receita> todasOcorrenciasReceitas = this.buscaReceitasDependentes(super.getTransaction(), item.getIdPai(), item.getId(), false, false);

            for (Receita ocorrencia : todasOcorrenciasReceitas) {

                if (ocorrencia.isPaga()) {
                    //Estorna o pagamento realizado
                    repositorioConta.setValorSaidaConta(super.getTransaction(), ocorrencia.getConta().getId(), ocorrencia.getValor());
                }

                super.delete(super.getTransaction(), ocorrencia.getId());

                linhas = linhas++;
            }
            this.setTransactionSuccessful();

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_receita));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    public int excluiReceitasCategoria(SQLiteDatabase transaction, long idCategoriaReceita) {
        try {

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());
            int linhas = 0;
            //Busca as receitas da categoria que serão excluidas.
            ArrayList<Receita> todasOcorrenciasReceitas = this.buscaReceitasCategoria(transaction, idCategoriaReceita);

            for (Receita ocorrencia : todasOcorrenciasReceitas) {

                if (ocorrencia.isPaga()) {
                    //Estorna o pagamento realizado
                    repositorioConta.setValorSaidaConta(transaction, ocorrencia.getConta().getId(), ocorrencia.getValor());
                }

                super.delete(transaction, ocorrencia.getId());

                linhas = linhas++;
            }

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_receita));
        }
    }

    public int excluiTodasSemEstorno(SQLiteDatabase transaction, long idConta) {

        String[] parametros = new String[]{String.valueOf(idConta)};

        StringBuilder sql = new StringBuilder();

        sql.append(" DELETE FROM ");
        sql.append(Receita.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(Receita.ID_CONTA + " = ?");

        try {

            super.executeCustomQuery(transaction, sql.toString(), parametros);

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_receita));
        }
    }


}
