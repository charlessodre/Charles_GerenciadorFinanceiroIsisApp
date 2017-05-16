package com.charlessodre.apps.gerenciadorfinanceiroisis.appHelper;

/**
 * Created by charl on 29/10/2016.
 */

public class Constantes {



    public static final int TOTAL_MESES_REPETICAO = 240; // 240 meses equivalente a 20 anos.
    public static final String SIMBOLO_CIFRAO = "$";

    //Tipo de Lançamentos
    public class TipoLancamento {

    public static final int LANCAMENTO_RECEITA = 1;
    public static final int LANCAMENTO_DESPESA = 2;
    public static final int LANCAMENTO_TRANSFERENCIA = 3;}

    //Opções de exclusão
    public class OpcaoExclusaoAlteracao {

    public static final int ATUAL = 0;
    public static final int PENDENTES = 1;
    public static final int TODAS = 2;
    public static final int CANCELAR = 3;}

    //Tipo Mensagem
    public class TipoMensagem {
    public static final int ALTERACAO = 0;
    public static final int EXCLUSAO = 1;

    }

    //Opções de confirmação
    public class OpcoesConfirmacao
    {
        public static final int SIM = 0;
        public static final int NAO = 1;
    }

    //Tipo Transacao
    public  class TipoTransacao
    {
        public static final int RECEITA = 0;
        public static final int DESPESA = 1;
        public static final int TRANSFERENCIA = 2;
    }

}
