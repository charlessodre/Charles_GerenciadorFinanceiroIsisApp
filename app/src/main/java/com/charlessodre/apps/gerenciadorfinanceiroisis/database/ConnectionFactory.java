package com.charlessodre.apps.gerenciadorfinanceiroisis.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by charl on 10/09/2016.
 */
public class ConnectionFactory {

    private SQLiteDatabase database;
    private Context context;

    public ConnectionFactory(Context context)
    {
        this.context=context;
    }

    public SQLiteDatabase getConnectionWrite() {

        DataBase dataBase = new DataBase(this.context);
        this.database = dataBase.getWritableDatabase();
        return database;
    }

    public SQLiteDatabase getConnectionRead() {

        DataBase dataBase = new DataBase(this.context);
        this.database = dataBase.getReadableDatabase();
        return database;
    }
}

