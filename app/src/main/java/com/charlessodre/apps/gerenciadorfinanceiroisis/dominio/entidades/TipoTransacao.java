package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades;

import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by charl on 20/10/2016.
 */

public class TipoTransacao extends EntidadeBase {

    //Constantes
    public static String  TABELA_NOME 			  = "TB_GF_TIPO_TRANSACAO";
    public static final String NM_TIPO_TRANSACAO  = "NM_TIPO_TRANSACAO";

    public static final int RECEITA = 0;
    public static final int DESPESA = 1;
    public static final int TRANSFERENCIA = 2;

    //Atributos

    //Métodos


}
