package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.charlessodre.apps.gerenciadorfinanceiroisis.database.ConnectionFactory;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.EntidadeBase;

/**
 * Created by charl on 10/09/2016.
 */
public abstract class RepositorioBase {

    private SQLiteDatabase database;
    private Context context;
    private String tableName;

    //Construtor
    public RepositorioBase(Context context, String tableName) {
        this.context = context;
        this.tableName = tableName;
    }

    //Métodos Configuração
    public SQLiteDatabase getTransaction()
    {
        return this.database;
    }

    public Context getContext() {
        return this.context;
    }

    public void closeConnection() {
        if (this.database != null)
            this.database.close();
    }

    public void openConnectionWrite() {

        this.database = new ConnectionFactory(this.context).getConnectionWrite();
    }

    public void openConnectionRead() {
            this.database = new ConnectionFactory(this.context).getConnectionRead();
    }

    public void setBeginTransaction() {
        this.database.beginTransaction();
    }

    public void setEndTransaction() {
        this.database.endTransaction();
    }

    public void setTransactionSuccessful() {
        this.database.setTransactionSuccessful();
    }


    //Métodos de Execução
    protected int update(ContentValues values, long id) {

        return this.database.update(this.tableName, values, EntidadeBase.ID + " = ? ", new String[]{String.valueOf(id)});

    }

    protected long insert(ContentValues values) {

        return this.database.insertOrThrow(this.tableName, null, values);

    }

    protected int delete(long id) {

        return this.database.delete(this.tableName, EntidadeBase.ID + " = ? ", new String[]{String.valueOf(id)});

    }

    protected void executeCustomQuery(String sql, Object[] arguments) {
        this.database.execSQL(sql, arguments);
    }

    protected Cursor selectAll() {
        return this.selectAll("");
    }

    protected Cursor selectAll(String orderByColumnName) {

        return this.database.query(this.tableName, null, null, null, null, null, orderByColumnName);
    }

    protected Cursor selectCustomQuery(String sql, String[] arguments) {

        return this.database.rawQuery(sql, arguments);

    }

    protected Cursor select(String where) {
        return this.select(where, null);
    }

    protected Cursor select(String where, String orderByColumnName) {

        return this.database.query(this.tableName, null, where, null, null, null, orderByColumnName);
    }


    //Métodos por Transação
    protected long insert(SQLiteDatabase transaction, ContentValues values) {

        return transaction.insertOrThrow(this.tableName, null, values);

    }

    protected int delete(SQLiteDatabase transaction, long id) {

        return transaction.delete(this.tableName, EntidadeBase.ID + " = ? ", new String[]{String.valueOf(id)});

    }

    protected int update(SQLiteDatabase transaction, ContentValues values, long id) {

        return transaction.update(this.tableName, values, EntidadeBase.ID + " = ? ", new String[]{String.valueOf(id)});
    }

    public void executeCustomQuery(SQLiteDatabase transaction, String sql, Object[] arguments) {
        transaction.execSQL(sql, arguments);
    }

    protected Cursor selectAll(SQLiteDatabase transaction) {
        return this.selectAll(transaction,null);
    }

    protected Cursor selectAll(SQLiteDatabase transaction, String orderByColumnName) {

        return transaction.query(this.tableName, null, null, null, null, null, orderByColumnName);
    }

    protected Cursor selectCustomQuery(SQLiteDatabase transaction,String sql, String[] arguments) {

        return transaction.rawQuery(sql, arguments);

    }


    protected Cursor select(SQLiteDatabase transaction, String where) {
        return this.select(transaction,where, null);
    }

    protected Cursor select(SQLiteDatabase transaction, String where, String orderByColumnName) {
        return transaction.query(this.tableName, null, where, null, null, null, orderByColumnName);
    }
}
