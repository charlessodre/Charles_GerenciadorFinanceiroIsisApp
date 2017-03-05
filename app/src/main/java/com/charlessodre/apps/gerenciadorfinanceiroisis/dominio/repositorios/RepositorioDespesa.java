package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper.Constantes;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Despesa;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.TipoRepeticao;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by charl on 27/09/2016.
 */

public class RepositorioDespesa extends RepositorioBase implements IRepositorio<Despesa, Long> {


    //Construtor
    public RepositorioDespesa(Context context) {
        super(context, Despesa.TABELA_NOME);
    }


    //Auxiliares
    private ContentValues preencheContentValues(Despesa despesa) {

        ContentValues values = new ContentValues();


        values.put(Despesa.NM_DESPESA, despesa.getNome());
        values.put(Despesa.VL_DESPESA, despesa.getValor());
        values.put(Despesa.VL_PAGAMENTO, despesa.getValorPagamento());
        values.put(Despesa.DT_DESPESA, despesa.getDataDespesa().getTime());
        values.put(Despesa.FL_DESPESA_PAGA, BooleanUtils.parseBooleanToint(despesa.isPaga()));
        values.put(Despesa.FL_DEPESA_FIXA, BooleanUtils.parseBooleanToint(despesa.isFixa()));
        values.put(Despesa.FL_ALERTA_DESPESA, BooleanUtils.parseBooleanToint(despesa.isAlertar()));
        values.put(Despesa.NO_TOTAL_REPETICAO, despesa.getTotalRepeticao());
        values.put(Despesa.NO_REPETICAO_ATUAL, despesa.getRepeticaoAtual());
        values.put(Despesa.NO_AM_DESPESA, despesa.getAnoMes());
        values.put(Despesa.ID_CONTA, despesa.getConta().getId());
        values.put(Despesa.ID_CATEGORIA_DESPESA, despesa.getCategoriaDespesa().getId());

        if(despesa.getSubCategoriaDespesa().getId() != 0)
            values.put(Despesa.ID_SUB_CATEGORIA_DESPESA, despesa.getSubCategoriaDespesa().getId());

        values.put(Despesa.NO_ORDEM_EXIBICAO, despesa.getOrdemExibicao());
        values.put(Despesa.FL_ATIVO, BooleanUtils.parseBooleanToint(despesa.isAtivo()));
        values.put(Despesa.FL_EXIBIR, BooleanUtils.parseBooleanToint(despesa.isExibir()));


        if (despesa.isPaga())
            values.put(Despesa.DT_PAGAMENTO, despesa.getDataPagamento().getTime());
        else
            values.put(Despesa.DT_PAGAMENTO, "");

        if (despesa.getIdPai() != 0)
            values.put(Despesa.ID_DESPESA_PAI, despesa.getIdPai());

        if (despesa.getId() != 0)
            values.put(Despesa.DT_ALTERACAO, despesa.getDataAlteracao().getTime());
        else
            values.put(Despesa.DT_INCLUSAO, despesa.getDataInclusao().getTime());

        return values;
    }

    private ArrayList<Despesa> preencheObjeto(SQLiteDatabase transaction, Cursor cursor) {

        ArrayList<Despesa> arrayList = new ArrayList<Despesa>();

        RepositorioCategoriaDespesa repCategoriaDespesa = new RepositorioCategoriaDespesa(super.getContext());
        RepositorioSubCategoriaDespesa repSubCategoriaDespesa = new RepositorioSubCategoriaDespesa(super.getContext());
        RepositorioConta repConta = new RepositorioConta(super.getContext());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Despesa despesa = new Despesa();

                despesa.setId(cursor.getLong(cursor.getColumnIndex(Despesa.ID)));
                despesa.setNome(cursor.getString(cursor.getColumnIndex(Despesa.NM_DESPESA)));
                despesa.setOrdemExibicao(cursor.getInt(cursor.getColumnIndex(Despesa.NO_ORDEM_EXIBICAO)));
                despesa.setExibir(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Despesa.FL_EXIBIR))));
                despesa.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Despesa.FL_ATIVO))));
                despesa.setValor(cursor.getDouble(cursor.getColumnIndex(Despesa.VL_DESPESA)));
                despesa.setDataDespesa(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(Despesa.DT_DESPESA))));

                despesa.setValorPagamento(cursor.getDouble(cursor.getColumnIndex(Despesa.VL_PAGAMENTO)));
                despesa.setDataPagamento(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(Despesa.DT_PAGAMENTO))));

                despesa.setPaga(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Despesa.FL_DESPESA_PAGA))));
                despesa.setFixa(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Despesa.FL_DEPESA_FIXA))));
                despesa.setAlertar(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Despesa.FL_ALERTA_DESPESA))));

                despesa.setIdTipoRepeticao(cursor.getInt(cursor.getColumnIndex(Despesa.ID_TIPO_REPETICAO)));
                despesa.setTotalRepeticao(cursor.getInt(cursor.getColumnIndex(Despesa.NO_TOTAL_REPETICAO)));
                despesa.setRepeticaoAtual(cursor.getInt(cursor.getColumnIndex(Despesa.NO_REPETICAO_ATUAL)));

                despesa.setAnoMes(cursor.getInt(cursor.getColumnIndex(Despesa.NO_AM_DESPESA)));

                despesa.setIdPai(cursor.getLong(cursor.getColumnIndex(Despesa.ID_DESPESA_PAI)));

                despesa.setConta(repConta.get(transaction, cursor.getLong(cursor.getColumnIndex(Despesa.ID_CONTA))));
                despesa.setCategoriaDespesa(repCategoriaDespesa.get(transaction,cursor.getLong(cursor.getColumnIndex(Despesa.ID_CATEGORIA_DESPESA))));
                despesa.setSubCategoriaDespesa(repSubCategoriaDespesa.get(transaction, cursor.getLong(cursor.getColumnIndex(Despesa.ID_SUB_CATEGORIA_DESPESA))));



                arrayList.add(despesa);

            } while (cursor.moveToNext());
        }

        return arrayList;

    }
    
    //Métodos
    @Override
    public int altera(Despesa item) {

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            Despesa despesaAnterior = this.get(super.getTransaction(), item.getId());

            //Estorna o valor da despesa recebida.
            if (item.isEstornaPagamento()) {
                repositorioConta.setValorEntradaConta(super.getTransaction(), despesaAnterior.getConta().getId(), despesaAnterior.getValor());

            } else if (item.isPaga()) {

                this.atualizaValorPago(super.getTransaction(), repositorioConta, item, despesaAnterior);
            }

            int linhas = super.update(super.getTransaction(), this.preencheContentValues(item), item.getId());

            if (item.isFixa()) {

                if (item.isPaga()) {

                    Despesa novaFixa = item.clone();

                    Date dataReceita = DateUtils.getDateAddMonth(item.getDataDespesa(), 1);

                    novaFixa.setId(0);
                    novaFixa.setPaga(false);
                    novaFixa.setDataAlteracao(null);
                    novaFixa.setDataPagamento(null);
                    novaFixa.setDataDespesa(dataReceita);
                    novaFixa.setDataInclusao(DateUtils.getCurrentDatetime());
                    novaFixa.setAnoMes(DateUtils.getYearAndMonth(dataReceita));
                    novaFixa.setIdPai(item.getId());

                    super.insert(super.getTransaction(), this.preencheContentValues(novaFixa));
                }
            }

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
    public long insere(Despesa item) {
        try {

            super.openConnectionWrite();
            super.setBeginTransaction();
            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Insere a Despesa "Pai".
            long id = super.insert(super.getTransaction(), this.preencheContentValues(item));
            item.setId(id);

            if (item.isPaga()) {
                repositorioConta.setValorSaidaConta(super.getTransaction(), item.getConta().getId(), item.getValor());
            }

            //Insere as Despesas filhas
            if (item.getTotalRepeticao() > 0) {

                int total = item.getTotalRepeticao();
                Date data = item.getDataDespesa(); //Diaria
                int tipoRepeticao = item.getIdTipoRepeticao();

                for (int i = 2; i <= total; i++) {

                    Despesa nova = item.clone();

                    nova.setIdPai(item.getId());
                    nova.setId(0);
                    nova.setRepeticaoAtual(i);

                    if (tipoRepeticao != TipoRepeticao.DIARIA)
                        data = TipoRepeticao.getDataRepeticao(tipoRepeticao, data);

                    nova.setDataDespesa(data);
                    nova.setAnoMes(DateUtils.getYearAndMonth(data));

                    super.insert(super.getTransaction(), preencheContentValues(nova));

                    if (nova.isPaga()) {
                        repositorioConta.setValorSaidaConta(super.getTransaction(), nova.getConta().getId(), nova.getValor());
                    }
                }
            } else if (item.isFixa()) {

                int total = Constantes.TOTAL_MESES_REPETICAO; //20 anos.

                Date data = item.getDataDespesa(); //Diaria

                for (int i = 2; i <= total; i++) {

                    Despesa nova = item.clone();

                    nova.setIdPai(item.getId());
                    nova.setId(0);

                    data = TipoRepeticao.getDataRepeticao(TipoRepeticao.MENSAL, data);

                    nova.setDataDespesa(data);
                    nova.setAnoMes(DateUtils.getYearAndMonth(data));

                    super.insert(super.getTransaction(), preencheContentValues(nova));

                    if (nova.isPaga()) {
                        repositorioConta.setValorSaidaConta(super.getTransaction(), nova.getConta().getId(), nova.getValor());
                    }
                }
            }


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
    public int exclui(Despesa item) {

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();


            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            repositorioConta.setValorSaidaConta(super.getTransaction(), item.getConta().getId(), item.getValor());

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
    public Despesa get(Long id) {
        String where = Despesa.ID + "=" + id;

        Despesa despesa = new Despesa();

        try {

            super.openConnectionRead();

            Cursor cursor = super.select(where);

            ArrayList<Despesa> arrayList = this.preencheObjeto(super.getTransaction(), cursor);

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

    public ArrayList<Despesa> buscaTodos() {
        try {

            super.openConnectionWrite();

            Cursor cursor = super.selectAll(Despesa.DT_DESPESA + "," + Despesa.NO_ORDEM_EXIBICAO);

            return this.preencheObjeto(super.getTransaction(),cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public ArrayList<Despesa> buscaPorAnoMes(int anoMes) {

        String where = Despesa.NO_AM_DESPESA + " =  " + anoMes;

        try {

            super.openConnectionRead();

            Cursor cursor = super.select(where, Despesa.DT_DESPESA + "," + Despesa.NO_ORDEM_EXIBICAO);

            return this.preencheObjeto(super.getTransaction(), cursor);

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }
    }

    public ArrayList<Despesa> buscaDespesasDependentes(SQLiteDatabase transaction, long idPai, long id, boolean proximas, boolean somentePendentes) {

        StringBuilder where = new StringBuilder();

        if (somentePendentes)
            where.append(Despesa.FL_DESPESA_PAGA + " = 0 AND ");

        if (proximas)
            where.append(Despesa.ID + " >= " + id + " AND  ");

        if (idPai == 0) {
            where.append(" ( " + Despesa.ID + " = " + id);
            where.append(" OR " + Despesa.ID_DESPESA_PAI + " = " + id + " )");
        } else {
            where.append(" ( " + Despesa.ID_DESPESA_PAI + " = " + idPai);
            where.append(" OR " + Despesa.ID + " = " + idPai + " )");
        }

        Cursor cursor = super.select(where.toString(), Despesa.ID);

        return this.preencheObjeto(transaction, cursor);

    }

    public ArrayList<Despesa> buscaDespesasCategoria(SQLiteDatabase transaction, long idCategoriaDespesa) {

        StringBuilder where = new StringBuilder();

        where.append(Despesa.ID_CATEGORIA_DESPESA + " = " + idCategoriaDespesa);

        Cursor cursor = super.select(transaction, where.toString(), Despesa.DT_DESPESA + "," + Despesa.NO_ORDEM_EXIBICAO);

        return this.preencheObjeto(transaction, cursor);
    }

    public Despesa get(SQLiteDatabase transaction, Long id) {
        String where = Despesa.ID + "=" + id;

        Despesa despesa = new Despesa();

        try {


            Cursor cursor = super.select(transaction, where);

            ArrayList<Despesa> arrayList = this.preencheObjeto(transaction, cursor);

            if (arrayList.size() > 0)
                despesa = arrayList.get(0);

            return despesa;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_despesa));
        }
    }


//Totalizadores

    public double getValorTotalDespesas(int anoMes, boolean somentePagas) {
        String[] parametros = {String.valueOf(anoMes)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT SUM( ");
        sql.append(Despesa.VL_DESPESA);
        sql.append(" ) AS VL_TOTAL_DESPESA FROM ");
        sql.append(Despesa.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(Despesa.NO_AM_DESPESA);
        sql.append(" = ? ");
         if(somentePagas) {
            sql.append(" AND ");
            sql.append(Despesa.FL_DESPESA_PAGA + " = 1");
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

    public int getQtdDespesaConta(long idConta) {
        int qtdDespesas = 0;

        String[] parametros = {String.valueOf(idConta)};

        StringBuilder sql = new StringBuilder();


        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(Despesa.TABELA_NOME);
        sql.append(" WHERE " + Despesa.ID_CONTA);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdDespesas = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }


            return qtdDespesas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    public int getQtdDespesaCategoria(long idCategoriaDespesa) {

        int qtdDespesas = 0;

        String[] parametros = {String.valueOf(idCategoriaDespesa)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(Despesa.TABELA_NOME);
        sql.append(" WHERE " + Despesa.ID_CATEGORIA_DESPESA);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdDespesas = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }

            return qtdDespesas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }

    public int getQtdDespesaSubCategoria(long idSubCategoriaDespesa) {

        int qtdDespesas = 0;

        String[] parametros = {String.valueOf(idSubCategoriaDespesa)};

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT COUNT (_id) AS QTDE FROM ");
        sql.append(Despesa.TABELA_NOME);
        sql.append(" WHERE " + Despesa.ID_SUB_CATEGORIA_DESPESA);
        sql.append(" = ?");

        try {

            super.openConnectionRead();

            Cursor cursor = super.selectCustomQuery(sql.toString(), parametros);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    qtdDespesas = cursor.getInt(cursor.getColumnIndex("QTDE"));

                } while (cursor.moveToNext());
            }

            return qtdDespesas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro));
        } finally {
            super.closeConnection();
        }

    }


    //Alterações
    public int alteraProximas(Despesa despesaAlterada) {

        double valorAnterior = 0;
        double valorDiferenca = 0;

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Busca as despesas que serão alteradas.
            ArrayList<Despesa> proximasDespesas = this.buscaDespesasDependentes(super.getTransaction(), despesaAlterada.getIdPai(), despesaAlterada.getId(), true, false);

            for (Despesa proxima : proximasDespesas) {

                proxima = this.atualizaDependentes(super.getTransaction(), repositorioConta, despesaAlterada, proxima);
                super.update(super.getTransaction(), preencheContentValues(proxima), proxima.getId());
            }

            super.setTransactionSuccessful();

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_despesa));
        } finally

        {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    public int alteraTodas(Despesa despesaAlterada) {

        double valorAnterior = 0;
        double valorDiferenca = 0;

        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Busca as despesas que serão alteradas.
            ArrayList<Despesa> todasOcorrenciasDespesas = this.buscaDespesasDependentes(super.getTransaction(), despesaAlterada.getIdPai(), despesaAlterada.getId(), false, false);

            for (Despesa ocorrencia : todasOcorrenciasDespesas) {

                ocorrencia = this.atualizaDependentes(super.getTransaction(), repositorioConta, despesaAlterada, ocorrencia);
                super.update(super.getTransaction(), preencheContentValues(ocorrencia), ocorrencia.getId());
            }

            super.setTransactionSuccessful();

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_despesa));
        } finally

        {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    private Despesa atualizaDependentes(SQLiteDatabase transaction, RepositorioConta repositorioConta, Despesa despesaAtualizada, Despesa despesaSalva) {

        despesaSalva.setNome(despesaAtualizada.getNome());
        despesaSalva.setDataAlteracao(despesaAtualizada.getDataAlteracao());
        despesaSalva.setCategoriaDespesa(despesaAtualizada.getCategoriaDespesa());
        despesaSalva.setConta(despesaAtualizada.getConta());

        if (despesaAtualizada.isPaga()) {

            despesaSalva.setPaga(true);
            despesaSalva.setDataPagamento(despesaAtualizada.getDataPagamento());

            this.atualizaValorPago(transaction, repositorioConta, despesaAtualizada, despesaSalva);

        } else if (despesaSalva.isPaga() && despesaAtualizada.isEstornaPagamento()) {

            despesaSalva.setPaga(false);
            despesaSalva.setDataPagamento(null);
            despesaAtualizada.setEstornaPagamento(true);

            repositorioConta.setValorSaidaConta(transaction, despesaSalva.getConta().getId(), despesaSalva.getValor());
        }

        despesaSalva.setValor(despesaAtualizada.getValor());

        return despesaSalva;

    }

    private void atualizaValorPago(SQLiteDatabase transaction, RepositorioConta repositorioConta, Despesa despesaAtualizada, Despesa despesaSalva) {

        double valorAnterior = 0;
        double valorAtualizado = 0;
        double valorDiferenca = 0;
        long idContaAnterior = 1;
        long idContaAtualizada = 1;

        idContaAnterior = despesaSalva.getConta().getId();
        idContaAtualizada = despesaAtualizada.getConta().getId();

        valorAnterior = despesaSalva.getValor();
        valorAtualizado = despesaAtualizada.getValor();

        //Se Contas diferentes, retira o valor da conta anterior e adiciona na conta atual.
        if (idContaAnterior != idContaAtualizada) {
            if (despesaSalva.isPaga())
                repositorioConta.setValorEntradaConta(transaction, idContaAnterior, valorAnterior);

            repositorioConta.setValorSaidaConta(transaction, idContaAtualizada, valorAtualizado);

        } else {

            //Verifica se houve alteração no valor.
            if (valorAnterior > valorAtualizado && despesaSalva.isPaga()) {
                if (despesaSalva.isPaga()) {
                    valorDiferenca = valorAnterior - valorAtualizado;
                    repositorioConta.setValorEntradaConta(transaction, idContaAtualizada, valorDiferenca);
                } else
                    repositorioConta.setValorSaidaConta(transaction, idContaAtualizada, valorAtualizado);

            } else if (valorAnterior < valorAtualizado && despesaSalva.isPaga()) {
                if (despesaSalva.isPaga()) {
                    valorDiferenca = valorAtualizado - valorAnterior;
                    repositorioConta.setValorSaidaConta(transaction, idContaAtualizada, valorDiferenca);

                } else
                    repositorioConta.setValorSaidaConta(transaction, idContaAtualizada, valorAtualizado);

            } else {
                if (!despesaSalva.isPaga()) {
                    repositorioConta.setValorSaidaConta(transaction, idContaAtualizada, valorAtualizado);
                }
            }

        }

    }


    //Exclusões
    public int excluiProximas(Despesa item) {
        try {

            super.openConnectionWrite();
            super.setBeginTransaction();

            int linhas = 0;

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Busca as despesas que serão excluidas.
            ArrayList<Despesa> todasOcorrenciasDespesas = this.buscaDespesasDependentes(super.getTransaction(), item.getIdPai(), item.getId(), true, false);

            for (Despesa ocorrencia : todasOcorrenciasDespesas) {

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
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_despesa));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }
    }

    public int excluiTodas(Despesa item) {

        try {
            super.openConnectionWrite();
            super.setBeginTransaction();
            int linhas = 0;

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Busca as despesas que serão excluidas.
            ArrayList<Despesa> todasOcorrenciasDespesas = this.buscaDespesasDependentes(super.getTransaction(), item.getIdPai(), item.getId(), false, false);

            for (Despesa ocorrencia : todasOcorrenciasDespesas) {

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
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_despesa));
        } finally {
            super.setEndTransaction();
            super.closeConnection();
        }

    }

    public int excluiDespesasCategoria(SQLiteDatabase transaction, long idCategoriaDespesa) {
        try {

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());
            int linhas = 0;
            //Busca as despesas da categoria que serão excluidas.
            ArrayList<Despesa> todasOcorrenciasDespesas = this.buscaDespesasCategoria(transaction, idCategoriaDespesa);

            for (Despesa ocorrencia : todasOcorrenciasDespesas) {

                if (ocorrencia.isPaga()) {
                    //Estorna o pagamento realizado
                    repositorioConta.setValorSaidaConta(super.getTransaction(), ocorrencia.getConta().getId(), ocorrencia.getValor());
                }

                super.delete(super.getTransaction(), ocorrencia.getId());

                linhas = linhas++;
            }

            return linhas;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_despesa));
        }
    }

    public int excluiTodasSemEstorno(SQLiteDatabase transaction, long idConta) {

        String[] parametros = new String[]{String.valueOf(idConta)};

        StringBuilder sql = new StringBuilder();

        sql.append(" DELETE FROM ");
        sql.append(Despesa.TABELA_NOME);
        sql.append(" WHERE ");
        sql.append(Despesa.ID_CONTA + " = ?");

        try {

            super.executeCustomQuery(transaction, sql.toString(), parametros);

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_despesa));
        }
    }

    public int excluiDespesasDaCategoriaComEstorno(SQLiteDatabase transaction, long idCategoriaDespesa) {
        try {

            RepositorioConta repositorioConta = new RepositorioConta(super.getContext());

            //Busca as despesas da categoria que serão excluidas.
            ArrayList<Despesa> todasOcorrenciasDespesas = null;//this.buscaDespesasCategoria(transaction,idCategoriaDespesa);

          //  this.estonaValorContaExcluiDespesa(transaction, repositorioConta, todasOcorrenciasDespesas);

            return 1;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_excluir_erro_despesa));
        }
    }

}