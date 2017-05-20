package com.charlessodre.apps.gerenciadorfinanceiroisis.database;

/**
 * Created by charl on 10/09/2016.
 */
import android.content.Context;
import android.database.sqlite.*;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper{

    public DataBase(Context context)
    {
        super(context, ScriptSQL.DATABASE_NAME , null, ScriptSQL.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Criacão das tabelas do banco
        db.execSQL( ScriptSQL.getCreateTBConta() );
        db.execSQL( ScriptSQL.getCreateTBUsuario() );
        db.execSQL( ScriptSQL.getCreateTBTipoRepeticao());
        db.execSQL( ScriptSQL.getCreateTBCategoriaDespesa() );
        db.execSQL( ScriptSQL.getCreateTBSubCategoriaDespesa() );
        db.execSQL( ScriptSQL.getCreateTBCategoriaReceita() );

        db.execSQL( ScriptSQL.getCreateTBDespesaConta() );
        db.execSQL( ScriptSQL.getCreateTBReceitaConta() );
        db.execSQL( ScriptSQL.getCreateTBTransferencia() );
        db.execSQL( ScriptSQL.getCreateTBRegraImportacaoSMS() );
        db.execSQL(ScriptSQL.getCreateTBCartaoCredito());
        db.execSQL(ScriptSQL.getCreateTBDespesaCartaoCredito());
        db.execSQL(ScriptSQL.getCreateTBFaturaCartaoCredito());


        //############################################################################################################
        // Carga Inicial Tabelas
        //############################################################################################################

        //Categorias Despesa
        ArrayList<String> ArrayListCategoriaDespesa = ScriptSQL.getInsertCategoriaDespesa();

        for (String item : ArrayListCategoriaDespesa ) {
            db.execSQL(item);
        }

        //Categorias Receita
        ArrayList<String> ArrayListCategoriaReceita = ScriptSQL.getInsertCategoriaReceita();

        for (String item : ArrayListCategoriaReceita ) {
            db.execSQL(item);
        }

        //Sub Categoria Despesa
        ArrayList<String> ArrayListSubCategoriaDespesa = ScriptSQL.getInsertSubCategoriaDespesa();

        for (String item : ArrayListSubCategoriaDespesa ) {
            db.execSQL(item);
        }

        //Conta
        db.execSQL(ScriptSQL.getInsertContaDefault().get(0));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion) {
            String sql = "";

            //sql = "ALTER TABLE TB_GF_CONTA ADD COLUMN NO_COR_ICONE INTEGER  NULL;";
            //db.execSQL(sql);

        }
    }

}
