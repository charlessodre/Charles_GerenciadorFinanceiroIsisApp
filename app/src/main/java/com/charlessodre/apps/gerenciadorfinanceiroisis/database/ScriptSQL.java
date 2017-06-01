package com.charlessodre.apps.gerenciadorfinanceiroisis.database;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by charl on 10/09/2016.
 */
public class ScriptSQL {

    //Constantes
    public static final String DATABASE_NAME  = "GER_FIN_ISIS";
    public static final int DATABASE_VERSION = 6;

    public static String getCreateTBConta()
    {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_CONTA ( ");
        sqlBuilder.append(" _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(" NM_CONTA VARCHAR(50) NOT NULL, ");
        sqlBuilder.append(" FL_ATIVO CHAR(1) NOT NULL DEFAULT 1, ");
        sqlBuilder.append(" DT_INCLUSAO DATETIME NOT NULL, ");
        sqlBuilder.append(" DT_ALTERACAO DATETIME NULL, ");
        sqlBuilder.append(" FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1, ");
        sqlBuilder.append(" FL_EXIBIR_SOMA CHAR(1) NOT NULL DEFAULT 1, ");
        sqlBuilder.append(" CD_TIPO_CONTA INTEGER NOT NULL, ");
        sqlBuilder.append(" NO_ORDEM_EXIBICAO INTEGER NULL, ");
        sqlBuilder.append(" NO_AM_CONTA INTEGER NULL, ");
        sqlBuilder.append(" NO_COR INTEGER NULL, ");
        sqlBuilder.append(" NO_COR_ICONE INTEGER NULL, ");
        sqlBuilder.append(" VL_SALDO DECIMAL(18,2) NOT NULL ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateTBUsuario()
    {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_USUARIO ( ");
        sqlBuilder.append(" _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(" CD_LOGIN_EMAIL VARCHAR(100) NOT NULL, ");
        sqlBuilder.append(" NM_USUARIO VARCHAR(100) NOT NULL, ");
        sqlBuilder.append(" CD_SENHA_EMAIL VARCHAR(255) NOT NULL, ");
        sqlBuilder.append(" FL_ATIVO CHAR(1) NULL DEFAULT ((1)), ");
        sqlBuilder.append(" DT_ALTERACAO DATETIME NOT NULL, ");
        sqlBuilder.append(" DT_EXPIRACAO_SENHA DATETIME NULL, ");
        sqlBuilder.append(" FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1, ");
        sqlBuilder.append(" FL_LOGIN_ALTERNATIVO CHAR(1) NULL, ");
        sqlBuilder.append(" CD_SENHA_ALTERNATIVA VARCHAR(20) NULL ");

        sqlBuilder.append(");");
        return sqlBuilder.toString();
    }

    public static String getCreateTBTipoRepeticao()
    {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_TIPO_REPETICAO ( " );
        sqlBuilder.append(" _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sqlBuilder.append(" NM_REPETICAO VARCHAR(50) NOT NULL, " );
        sqlBuilder.append(" FL_ATIVO CHAR(1) NOT NULL DEFAULT 1 , " );
        sqlBuilder.append(" DT_INCLUSAO DATETIME NOT NULL, " );
        sqlBuilder.append(" DT_ALTERACAO DATETIME NULL, " );
        sqlBuilder.append(" FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1 " );
        sqlBuilder.append(" ); " );

        return sqlBuilder.toString();

    }

    public static String getCreateTBCategoriaDespesa()
    {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_CATEGORIA_DESPESA ( ");
        sqlBuilder.append("   _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("   NM_CATEGORIA VARCHAR(50) NOT NULL, ");
        sqlBuilder.append("   FL_ATIVO CHAR(1) NOT NULL DEFAULT 1 , ");
        sqlBuilder.append("   DT_INCLUSAO DATETIME NOT NULL, ");
        sqlBuilder.append("   DT_ALTERACAO DATETIME NULL, ");
        sqlBuilder.append("   FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1, ");
        sqlBuilder.append(" NO_COR INTEGER NULL, ");
        sqlBuilder.append(" NO_ICONE INTEGER NULL, ");
        sqlBuilder.append(" NO_COR_ICONE INTEGER NULL, ");
        sqlBuilder.append(" NO_ORDEM_EXIBICAO INTEGER NULL ");

        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateTBSubCategoriaDespesa()
    {

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_SUB_CATEGORIA_DESPESA ( " );
        sqlBuilder.append("   _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sqlBuilder.append("   NM_SUB_CATEGORIA VARCHAR(50) NOT NULL, " );
        sqlBuilder.append("   FL_ATIVO CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   DT_INCLUSAO DATETIME NOT NULL, " );
        sqlBuilder.append("   DT_ALTERACAO DATETIME NULL, " );
        sqlBuilder.append("   FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   NO_ORDEM_EXIBICAO INTEGER NULL, " );
        sqlBuilder.append("   NO_COR INTEGER NULL, " );
        sqlBuilder.append("   ID_CATEGORIA_DESPESA INT NOT NULL, " );
        sqlBuilder.append("    FOREIGN KEY (ID_CATEGORIA_DESPESA) REFERENCES TB_GF_CATEGORIA_DESPESA (_id) " );
        sqlBuilder.append("   ); " );


        return sqlBuilder.toString();
    }


    public static String getCreateTBCategoriaReceita()
    {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_CATEGORIA_RECEITA ( ");
        sqlBuilder.append("   _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("   NM_CATEGORIA VARCHAR(50) NOT NULL, ");
        sqlBuilder.append("   FL_ATIVO CHAR(1) NOT NULL DEFAULT 1 , ");
        sqlBuilder.append("   DT_INCLUSAO DATETIME NOT NULL, ");
        sqlBuilder.append("   DT_ALTERACAO DATETIME NULL, ");
        sqlBuilder.append("   FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1, ");
        sqlBuilder.append(" NO_ICONE INTEGER NULL, ");
        sqlBuilder.append(" NO_COR INTEGER NULL, ");
        sqlBuilder.append(" NO_COR_ICONE INTEGER NULL, ");
        sqlBuilder.append(" NO_ORDEM_EXIBICAO INTEGER NULL ");

        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }



    public static String getCreateTBDespesaConta()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_DESPESA_CONTA ( " );
        sqlBuilder.append("  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sqlBuilder.append("   NM_DESPESA VARCHAR(200) NOT NULL, " );
        sqlBuilder.append("   DT_DESPESA DATETIME NOT NULL, " );
        sqlBuilder.append("   VL_DESPESA DECIMAL(18,2) NOT NULL, " );
        sqlBuilder.append("   DT_PAGAMENTO DATETIME NULL, " );
        sqlBuilder.append("   VL_PAGAMENTO DECIMAL(18,2) NULL, " );
        sqlBuilder.append("   NO_TOTAL_REPETICAO INTEGER NULL, " );
        sqlBuilder.append("   NO_REPETICAO_ATUAL INTEGER NULL, " );
        sqlBuilder.append("   FL_DESPESA_PAGA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   FL_DEPESA_FIXA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   FL_ALERTA_DESPESA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   DT_INCLUSAO DATETIME NOT NULL, " );
        sqlBuilder.append("   DT_ALTERACAO DATETIME NULL, " );
        sqlBuilder.append("   FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   FL_ATIVO CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   NO_AM_DESPESA INTEGER  NULL, " );
        sqlBuilder.append("   NO_AM_PAGAMENTO_DESPESA INTEGER  NULL, " );
        sqlBuilder.append("   NO_ORDEM_EXIBICAO INTEGER NULL, " );
        sqlBuilder.append("   ID_CONTA INTEGER NOT NULL, " );
        sqlBuilder.append("   ID_CATEGORIA_DESPESA INTEGER  NOT NULL, " );
        sqlBuilder.append("   ID_SUB_CATEGORIA_DESPESA INTEGER  NOT NULL, " );
        sqlBuilder.append("   ID_DESPESA_PAI INTEGER  NULL, " );
        sqlBuilder.append("   ID_TIPO_REPETICAO INTEGER  NULL, " );
        sqlBuilder.append("   FOREIGN KEY (ID_DESPESA_PAI)REFERENCES TB_GF_DESPESA_CONTA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_CONTA) REFERENCES TB_GF_CONTA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_CATEGORIA_DESPESA) REFERENCES TB_GF_CATEGORIA_DESPESA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_SUB_CATEGORIA_DESPESA) REFERENCES TB_GF_SUB_CATEGORIA_DESPESA (_id) " );
        sqlBuilder.append("  ); " );


        return sqlBuilder.toString();

    }




    public static String getCreateTBReceitaConta()
    {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_RECEITA_CONTA ( " );
        sqlBuilder.append("   _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sqlBuilder.append("   NM_RECEITA VARCHAR(200) NOT NULL, " );
        sqlBuilder.append("   VL_RECEITA DECIMAL(18,2) NOT NULL, " );
        sqlBuilder.append("   DT_RECEITA DATETIME NOT NULL, " );
        sqlBuilder.append("   FL_RECEITA_PAGA CHAR(1) NOT NULL DEFAULT 0, " );
        sqlBuilder.append("   FL_RECEITA_FIXA CHAR(1) NOT NULL DEFAULT 0, " );
        sqlBuilder.append("   NO_TOTAL_REPETICAO INTEGER NULL, " );
        sqlBuilder.append("   NO_REPETICAO_ATUAL INTEGER NULL, " );
        sqlBuilder.append("   DT_INCLUSAO DATETIME NOT NULL, " );
        sqlBuilder.append("   DT_RECEBIMENTO DATETIME NULL, " );
        sqlBuilder.append("   DT_ALTERACAO DATETIME NULL, " );
        sqlBuilder.append("   FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   FL_ATIVO CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   FL_ALERTA_RECEITA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   NO_ORDEM_EXIBICAO INTEGER NULL, " );
        sqlBuilder.append("   NO_AM_RECEITA INTEGER  NULL, " );
        sqlBuilder.append("   NO_AM_RECEBIMENTO_RECEITA INTEGER  NULL, " );
        sqlBuilder.append("   ID_CONTA INTEGER  NOT NULL, " );
        sqlBuilder.append("   ID_CATEGORIA_RECEITA INTEGER  NULL, " );
        sqlBuilder.append("   ID_TIPO_REPETICAO INTEGER  NULL, " );
        sqlBuilder.append("   ID_RECEITA_PAI INTEGER  NULL, " );
        sqlBuilder.append("   FOREIGN KEY (ID_RECEITA_PAI)REFERENCES TB_GF_RECEITA_CONTA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_CATEGORIA_RECEITA) REFERENCES TB_GF_CATEGORIA_RECEITA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_CONTA)REFERENCES TB_GF_CONTA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_TIPO_REPETICAO)REFERENCES TB_GF_TIPO_REPETICAO (_id) " );
        sqlBuilder.append("     ); " );
        return sqlBuilder.toString();

    }

    public static String getCreateTBTransferencia()
    {

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("  CREATE TABLE IF NOT EXISTS TB_GF_TRANSFERENCIA ( " );
        sqlBuilder.append("  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sqlBuilder.append("   ID_CONTA_ORIGEM INT NOT NULL, " );
        sqlBuilder.append("   ID_CONTA_DESTINO INT NOT NULL, " );
        sqlBuilder.append("   DT_INCLUSAO DATETIME NOT NULL, " );
        sqlBuilder.append("   DT_ALTERACAO DATETIME NULL, " );
        sqlBuilder.append("   DT_TRANSFERENCIA DATETIME NOT NULL, " );
        sqlBuilder.append("   VL_TRANSFERENCIA DECIMAL(17,2) NOT NULL, " );
        sqlBuilder.append("   NO_TOTAL_REPETICAO INTEGER NULL, " );
        sqlBuilder.append("   NO_REPETICAO_ATUAL INTEGER NULL, " );
        sqlBuilder.append("   NO_AM_TRANSFERENCIA INTEGER  NULL, " );
        sqlBuilder.append("   FL_ALERTA_TRANSFERENCIA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   FL_TRANSFERENCIA_EFETIVADA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   FOREIGN KEY (ID_CONTA_ORIGEM)REFERENCES TB_GF_CONTA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_CONTA_DESTINO)REFERENCES TB_GF_CONTA (_id) " );
        sqlBuilder.append("  ); " );


        return sqlBuilder.toString();
    }



    public static String getCreateTBRegraImportacaoSMS()
    {

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_SUB_REGRA_IMPORT_SMS ( " );
        sqlBuilder.append("   _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sqlBuilder.append("   NM_REGRA_IMPORTACAO VARCHAR(30) NOT NULL, " );
        sqlBuilder.append("   DS_TEXTO_PESQUISA_1 VARCHAR(30) NOT NULL, " );
        sqlBuilder.append("   DS_TEXTO_PESQUISA_2 VARCHAR(30) NULL, " );
        sqlBuilder.append("   DS_TEXTO_INI_VALOR VARCHAR(30) NULL, " );
        sqlBuilder.append("   DS_TEXTO_FIM_VALOR VARCHAR(30) NULL, " );
        sqlBuilder.append("   DS_TEXTO_INI_DATA VARCHAR(30) NULL, " );
        sqlBuilder.append("   DS_TEXTO_FIM_DATA VARCHAR(30) NULL, " );
        sqlBuilder.append("   DS_RECEITA_DESPESA VARCHAR(40) NULL, " );
        sqlBuilder.append("   NO_TELEFONE VARCHAR(30) NOT NULL, " );
        sqlBuilder.append("   FL_ATIVO CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   FL_NOTIFICAR_LANCAMENTO CHAR(1) NOT NULL DEFAULT 0, " );
        sqlBuilder.append("   FL_EFETIVA_AUTOMATICO CHAR(1) NOT NULL DEFAULT 0, " );
        sqlBuilder.append("   DT_INCLUSAO DATETIME NOT NULL, " );
        sqlBuilder.append("   DT_ALTERACAO DATETIME NULL, " );
        sqlBuilder.append("   ID_TIPO_TRANSACAO INT NOT NULL, " );
        sqlBuilder.append("   ID_CATEGORIA_RECEITA INTEGER  NULL, " );
        sqlBuilder.append("   ID_CATEGORIA_DESPESA INT NULL, " );
        sqlBuilder.append("   ID_CONTA_ORIGEM INT NOT NULL, " );
        sqlBuilder.append("   ID_CONTA_DESTINO INT NOT NULL, " );
        sqlBuilder.append("   ID_SUB_CATEGORIA_DESPESA INTEGER  NOT NULL, " );
        sqlBuilder.append("   FOREIGN KEY (ID_CATEGORIA_RECEITA) REFERENCES TB_GF_CATEGORIA_RECEITA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_CATEGORIA_DESPESA) REFERENCES TB_GF_CATEGORIA_DESPESA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_CONTA_ORIGEM)REFERENCES TB_GF_CONTA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_CONTA_DESTINO)REFERENCES TB_GF_CONTA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_SUB_CATEGORIA_DESPESA) REFERENCES TB_GF_SUB_CATEGORIA_DESPESA (_id) " );
        sqlBuilder.append("   ); " );


        return sqlBuilder.toString();
    }



    public static String getCreateTBCartaoCredito()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_CARTAO_CREDITO ( " );
        sqlBuilder.append("  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sqlBuilder.append("   NM_CARTAO VARCHAR(200) NOT NULL, " );
        sqlBuilder.append("   VL_LIMITE DECIMAL(18,2) NOT NULL, " );
        sqlBuilder.append("   VL_TAXA_JUROS_ROTATIVO DECIMAL(5,2)  NULL, " );
        sqlBuilder.append("   VL_TAXA_JUROS_FINANCIAMENTO DECIMAL(5,2)  NULL, " );
        sqlBuilder.append("   NO_DIA_FECHAMENTO_FATURA INTEGER  NOT NULL, " );
        sqlBuilder.append("   NO_DIA_VENCIMENTO_FATURA INTEGER  NOT NULL, " );
        sqlBuilder.append("   NO_BANDEIRA_CARTAO INTEGER  NOT NULL, " );
        sqlBuilder.append("   FL_EXIBIR_SOMA CHAR(1) NOT NULL DEFAULT 1, ");
        sqlBuilder.append("   FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   FL_ATIVO CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   NO_ORDEM_EXIBICAO INTEGER NULL, " );
        sqlBuilder.append("   NO_COR INTEGER NULL, " );
        sqlBuilder.append("   DT_INCLUSAO DATETIME NOT NULL, " );
        sqlBuilder.append("   DT_ALTERACAO DATETIME NULL, " );
        sqlBuilder.append("   FL_ALERTA_VENCIMENTO CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   FL_AGRUPAR_DESPESAS CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   ID_CONTA_ASSOCIADA INTEGER NOT NULL, " );
        sqlBuilder.append("   FOREIGN KEY (ID_CONTA_ASSOCIADA) REFERENCES TB_GF_CONTA (_id) " );

        sqlBuilder.append("  ); " );


       return sqlBuilder.toString();

    }

    public static String getCreateTBDespesaCartaoCredito()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_DESPESA_CARTAO_CREDITO ( " );
        sqlBuilder.append("  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sqlBuilder.append("   NM_DESPESA VARCHAR(200) NOT NULL, " );
        sqlBuilder.append("   DT_DESPESA DATETIME NOT NULL, " );
        sqlBuilder.append("   VL_DESPESA DECIMAL(18,2) NOT NULL, " );
        sqlBuilder.append("   DT_PAGAMENTO DATETIME NULL, " );
        sqlBuilder.append("   VL_PAGAMENTO DECIMAL(18,2) NULL, " );
        sqlBuilder.append("   NO_TOTAL_REPETICAO INTEGER NULL, " );
        sqlBuilder.append("   NO_REPETICAO_ATUAL INTEGER NULL, " );
        sqlBuilder.append("   FL_DESPESA_PAGA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   FL_DEPESA_FIXA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   FL_ALERTA_DESPESA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   DT_INCLUSAO DATETIME NOT NULL, " );
        sqlBuilder.append("   DT_ALTERACAO DATETIME NULL, " );
        sqlBuilder.append("   FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   FL_ATIVO CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   NO_AM_DESPESA INTEGER  NULL, " );
        sqlBuilder.append("   NO_AM_PAGAMENTO_DESPESA INTEGER  NULL, " );
        sqlBuilder.append("   NO_ORDEM_EXIBICAO INTEGER NULL, " );
        sqlBuilder.append("   ID_CARTAO_CREDITO INTEGER NOT NULL, " );
        sqlBuilder.append("   ID_CATEGORIA_DESPESA INTEGER  NOT NULL, " );
        sqlBuilder.append("   ID_SUB_CATEGORIA_DESPESA INTEGER  NOT NULL, " );
        sqlBuilder.append("   ID_DESPESA_PAI INTEGER  NULL, " );
        sqlBuilder.append("   ID_FATURA_CARTAO_CREDITO INTEGER NOT NULL, " );
        sqlBuilder.append("   ID_TIPO_REPETICAO INTEGER  NULL, " );
        sqlBuilder.append("   FOREIGN KEY (ID_FATURA_CARTAO_CREDITO) REFERENCES TB_GF_FATURA_CARTAO_CREDITO (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_DESPESA_PAI)REFERENCES TB_GF_DESPESA_CARTAO_CREDITO (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_CARTAO_CREDITO) REFERENCES TB_GF_CARTAO (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_CATEGORIA_DESPESA) REFERENCES TB_GF_CATEGORIA_DESPESA (_id), " );
        sqlBuilder.append("   FOREIGN KEY (ID_SUB_CATEGORIA_DESPESA) REFERENCES TB_GF_SUB_CATEGORIA_DESPESA (_id) " );
        sqlBuilder.append("  ); " );

        return sqlBuilder.toString();

    }

    public static String getCreateTBFaturaCartaoCredito()
    {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" CREATE TABLE IF NOT EXISTS TB_GF_FATURA_CARTAO_CREDITO ( " );
        sqlBuilder.append("   _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " );
        sqlBuilder.append("   DT_FATURA DATETIME NOT NULL, " );
        sqlBuilder.append("   VL_FATURA DECIMAL(18,2) NULL, " );
        sqlBuilder.append("   DT_PAGAMENTO DATETIME NULL, " );
        sqlBuilder.append("   VL_PAGAMENTO DECIMAL(18,2) NULL, " );
        sqlBuilder.append("   FL_FATURA_PAGA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   FL_FATURA_FECHADA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   FL_ALERTA_FECHAMENTO_FATURA CHAR(1) NULL DEFAULT 0, " );
        sqlBuilder.append("   NO_AM_FATURA INTEGER  NULL, " );
        sqlBuilder.append("   NO_AM_PAGAMENTO_FATURA INTEGER  NULL, " );
        sqlBuilder.append("   ID_CARTAO_CREDITO INTEGER NOT NULL, " );
        sqlBuilder.append("   FL_EXIBIR CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   FL_ATIVO CHAR(1) NOT NULL DEFAULT 1, " );
        sqlBuilder.append("   DT_INCLUSAO DATETIME NOT NULL, " );
        sqlBuilder.append("   DT_ALTERACAO DATETIME NULL, " );
        sqlBuilder.append("   FOREIGN KEY (ID_CARTAO_CREDITO) REFERENCES TB_GF_CARTAO (_id) " );

        sqlBuilder.append("  ); " );

        return sqlBuilder.toString();

    }




    //############################################################################################################
    // Carga Inicial Tabelas
    //############################################################################################################

    public static ArrayList<String> getInsertCategoriaDespesa() {

        ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Alimentação',1, CURRENT_TIMESTAMP,1,1,   %d ,  %d);",R.color.red_A200,R.drawable.ic_alimentacao_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Automóvel',1, CURRENT_TIMESTAMP,1,2,   %d ,  %d);",R.color.grey_500,R.drawable.ic_automovel_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Educação',1, CURRENT_TIMESTAMP,1,3,   %d ,  %d);",R.color.deep_purple_A700,R.drawable.ic_educacao_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Impostos e Tarifas',1, CURRENT_TIMESTAMP,1,4,   %d ,  %d);",R.color.light_blue_A700,R.drawable.ic_impostos_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Investimentos',1, CURRENT_TIMESTAMP,1,5,   %d ,  %d);",R.color.green_A700,R.drawable.ic_investimento_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Lazer',1, CURRENT_TIMESTAMP,1,6,   %d ,  %d);",R.color.yellow_A700,R.drawable.ic_lazer_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Moradia',1, CURRENT_TIMESTAMP,1,7,   %d ,  %d);",R.color.deep_orange_300,R.drawable.ic_moradia_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Pessoal',1, CURRENT_TIMESTAMP,1,8,   %d ,  %d);",R.color.blue_grey_900,R.drawable.ic_pessoal_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Pets',1, CURRENT_TIMESTAMP,1,9,   %d ,  %d);",R.color.pink_500,R.drawable.ic_pets_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Saúde',1, CURRENT_TIMESTAMP,1,10,   %d ,  %d);",R.color.indigo_A700,R.drawable.ic_saude_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Sem Categoria',1, CURRENT_TIMESTAMP,1,11,   %d ,  %d);",R.color.cyan_A700,R.drawable.ic_sem_categoria_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Trabalho',1, CURRENT_TIMESTAMP,1,12,   %d ,  %d);",R.color.light_green_A700,R.drawable.ic_trabalho_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_DESPESA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Transporte',1, CURRENT_TIMESTAMP,1,13,   %d ,  %d);",R.color.amber_A700,R.drawable.ic_transporte_24dp));


        return arrayList;
    }

    public static ArrayList<String> getInsertCategoriaReceita() {

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_RECEITA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Benefícios',1, CURRENT_TIMESTAMP,1,1,  %d ,  %d);",R.color.red_A200,R.drawable.ic_beneficios_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_RECEITA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Empréstimos',1, CURRENT_TIMESTAMP,1,2,  %d ,  %d);",R.color.red_A700,R.drawable.ic_emprestimo_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_RECEITA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Investimentos',1, CURRENT_TIMESTAMP,1,3,  %d ,  %d);",R.color.deep_purple_A700,R.drawable.ic_investimento_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_RECEITA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Outras receitas',1, CURRENT_TIMESTAMP,1,4,  %d ,  %d);",R.color.light_blue_A700,R.drawable.ic_conta_outros_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_RECEITA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Poupança',1, CURRENT_TIMESTAMP,1,5,  %d ,  %d);",R.color.green_A700,R.drawable.ic_poupanca_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_RECEITA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Salário',1, CURRENT_TIMESTAMP,1,6,  %d ,  %d);",R.color.yellow_A700,R.drawable.ic_salario_24dp));
        arrayList.add( String.format(" INSERT INTO TB_GF_CATEGORIA_RECEITA(NM_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO,NO_COR, NO_ICONE) VALUES ( 'Dinheiro',1, CURRENT_TIMESTAMP,1,7,  %d ,  %d);",R.color.deep_orange_A700,R.drawable.ic_dinheiro_24dp));



        return arrayList;
    }

    public static ArrayList<String> getInsertSubCategoriaDespesa() {

        ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Feira e Sacolão',1, CURRENT_TIMESTAMP,1,1, %d ,1 );",R.color.red_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Produtos Naturais',1, CURRENT_TIMESTAMP,1,2, %d ,1 );",R.color.red_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Refeições e Lanches',1, CURRENT_TIMESTAMP,1,3, %d ,1 );",R.color.red_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Supermercado',1, CURRENT_TIMESTAMP,1,4, %d ,1 );",R.color.deep_purple_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Combustível',1, CURRENT_TIMESTAMP,1,5, %d ,2 );",R.color.deep_purple_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Estacionamento',1, CURRENT_TIMESTAMP,1,6, %d ,2 );",R.color.deep_purple_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Financiamento',1, CURRENT_TIMESTAMP,1,7, %d ,2 );",R.color.light_blue_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Impostos e Taxas',1, CURRENT_TIMESTAMP,1,8, %d ,2 );",R.color.light_blue_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Manutenção',1, CURRENT_TIMESTAMP,1,9, %d ,2 );",R.color.light_blue_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Multa',1, CURRENT_TIMESTAMP,1,10, %d ,2 );",R.color.green_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Peças e Acessórios',1, CURRENT_TIMESTAMP,1,11, %d ,2 );",R.color.green_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Pedágio',1, CURRENT_TIMESTAMP,1,12, %d ,2 );",R.color.green_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Seguro',1, CURRENT_TIMESTAMP,1,13, %d ,2 );",R.color.yellow_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Cursos',1, CURRENT_TIMESTAMP,1,14, %d ,3 );",R.color.yellow_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Lanches',1, CURRENT_TIMESTAMP,1,15, %d ,3 );",R.color.yellow_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Livros',1, CURRENT_TIMESTAMP,1,16, %d ,3 );",R.color.deep_orange_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Material Escolar/Escritório',1, CURRENT_TIMESTAMP,1,17, %d ,3 );",R.color.deep_orange_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Cartão de Crédito',1, CURRENT_TIMESTAMP,1,18, %d ,4 );",R.color.deep_orange_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Contas',1, CURRENT_TIMESTAMP,1,19, %d ,4 );",R.color.blue_grey_700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Empréstimo',1, CURRENT_TIMESTAMP,1,20, %d ,4 );",R.color.blue_grey_800));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Outras',1, CURRENT_TIMESTAMP,1,21, %d ,4 );",R.color.blue_grey_900));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Outros',1, CURRENT_TIMESTAMP,1,22, %d ,5 );",R.color.pink_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Poupança',1, CURRENT_TIMESTAMP,1,23, %d ,5 );",R.color.pink_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Previdência',1, CURRENT_TIMESTAMP,1,24, %d ,5 );",R.color.pink_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Alimentação',1, CURRENT_TIMESTAMP,1,25, %d ,6 );",R.color.indigo_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Barzinho',1, CURRENT_TIMESTAMP,1,26, %d ,6 );",R.color.indigo_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Viagem',1, CURRENT_TIMESTAMP,1,27, %d ,6 );",R.color.indigo_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Aluguel',1, CURRENT_TIMESTAMP,1,28, %d ,7 );",R.color.cyan_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Condomínio',1, CURRENT_TIMESTAMP,1,29, %d ,7 );",R.color.cyan_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Contas Consumo (luz, gás, tel etc)',1, CURRENT_TIMESTAMP,1,30, %d ,7 );",R.color.cyan_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Diarista',1, CURRENT_TIMESTAMP,1,31, %d ,7 );",R.color.light_green_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Eletrônicos e Eletrodomésticos',1, CURRENT_TIMESTAMP,1,32, %d ,7 );",R.color.light_green_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Financiamentos',1, CURRENT_TIMESTAMP,1,33, %d ,7 );",R.color.light_green_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Impostos e Taxas',1, CURRENT_TIMESTAMP,1,34, %d ,7 );",R.color.amber_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Manutenção e Obras',1, CURRENT_TIMESTAMP,1,35, %d ,7 );",R.color.amber_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Móveis, Decoração e Utensílios',1, CURRENT_TIMESTAMP,1,36, %d ,7 );",R.color.amber_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Acessórios',1, CURRENT_TIMESTAMP,1,37, %d ,8 );",R.color.brown_700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Celular',1, CURRENT_TIMESTAMP,1,38, %d ,8 );",R.color.brown_800));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Eletrônicos',1, CURRENT_TIMESTAMP,1,39, %d ,8 );",R.color.brown_900));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Presentes',1, CURRENT_TIMESTAMP,1,40, %d ,8 );",R.color.purple_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Salão de Beleza',1, CURRENT_TIMESTAMP,1,41, %d ,8 );",R.color.purple_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Vestuário',1, CURRENT_TIMESTAMP,1,42, %d ,8 );",R.color.purple_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Acessórios',1, CURRENT_TIMESTAMP,1,43, %d ,9 );",R.color.blue_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Remédios',1, CURRENT_TIMESTAMP,1,44, %d ,9 );",R.color.blue_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Tosa e Banho',1, CURRENT_TIMESTAMP,1,45, %d ,9 );",R.color.blue_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Veterinário',1, CURRENT_TIMESTAMP,1,46, %d ,9 );",R.color.teal_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Academia/Lutas',1, CURRENT_TIMESTAMP,1,47, %d ,10 );",R.color.teal_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Farmácia',1, CURRENT_TIMESTAMP,1,48, %d ,10 );",R.color.teal_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Suplementos',1, CURRENT_TIMESTAMP,1,49, %d ,10 );",R.color.lime_A200));

        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Almoço',1, CURRENT_TIMESTAMP,1,51, %d ,12 );",R.color.lime_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Lanche/Café',1, CURRENT_TIMESTAMP,1,52, %d ,12 );",R.color.orange_A200));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Transporte',1, CURRENT_TIMESTAMP,1,53, %d ,12 );",R.color.orange_A400));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Passagem',1, CURRENT_TIMESTAMP,1,54, %d ,13 );",R.color.orange_A700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Rio Card',1, CURRENT_TIMESTAMP,1,55, %d ,13 );",R.color.grey_700));
        arrayList.add( String.format(" INSERT INTO TB_GF_SUB_CATEGORIA_DESPESA (NM_SUB_CATEGORIA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR,NO_ORDEM_EXIBICAO, NO_COR, ID_CATEGORIA_DESPESA) VALUES ('Taxi',1, CURRENT_TIMESTAMP,1,56, %d ,13 );",R.color.grey_800));

        return arrayList;
    }

    public static ArrayList<String> getInsertContaDefault() {

        ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.add(String.format(" INSERT INTO TB_GF_CONTA (_id,NM_CONTA,FL_ATIVO,DT_INCLUSAO,FL_EXIBIR, FL_EXIBIR_SOMA,CD_TIPO_CONTA, NO_ORDEM_EXIBICAO, NO_COR,VL_SALDO, NO_AM_CONTA)VALUES (1,'Carteira',1, CURRENT_TIMESTAMP,1,1, 3,1,%d,0,%s);", R.color.grey_700, DateUtils.getCurrentYearAndMonth()));


        return arrayList;
    }
    }
