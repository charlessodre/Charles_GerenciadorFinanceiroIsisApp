package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.charlessodre.apps.gerenciadorfinanceiroisis.R;
import com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.entidades.Usuario;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.BooleanUtils;
import com.charlessodre.apps.gerenciadorfinanceiroisis.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by charl on 27/09/2016.
 */

public class RepositorioUsuario extends RepositorioBase implements IRepositorio<Usuario, Long> {


    //Construtor
    public RepositorioUsuario(Context context) {
        super(context, Usuario.TABELA_NOME);
    }

    //Métodos

    private ContentValues preencheContentValues(Usuario Usuario) {

        ContentValues values = new ContentValues();

        values.put(Usuario.NM_USUARIO, Usuario.getNome());
        values.put(Usuario.CD_LOGIN_EMAIL, Usuario.getEmail());
        values.put(Usuario.CD_SENHA_EMAIL, Usuario.getSenha());
        values.put(Usuario.DT_EXPIRACAO_SENHA, Usuario.getDataExpiracaoSenha().getTime());
        values.put(Usuario.FL_LOGIN_ALTERNATIVO, BooleanUtils.parseBooleanToint(Usuario.isLoginAlternativo()));
        values.put(Usuario.CD_SENHA_ALTERNATIVA, Usuario.getSenhaAlternativa());

        values.put(Usuario.FL_ATIVO, BooleanUtils.parseBooleanToint(Usuario.isAtivo()));
        values.put(Usuario.FL_EXIBIR, BooleanUtils.parseBooleanToint(Usuario.isExibir()));
        values.put(Usuario.DT_INCLUSAO, Usuario.getDataInclusao().getTime());

        if (Usuario.getDataAlteracao() != null)
            values.put(Usuario.DT_ALTERACAO, Usuario.getDataAlteracao().getTime());

        return values;
    }


    public ArrayList<Usuario> getAll() {
        Cursor cursor = null;

        try {

            ArrayList<Usuario> arrayList = new ArrayList<Usuario>();

            super.openConnectionWrite();
             cursor = super.selectAll();

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    Usuario usuario = new Usuario();


                    usuario.setId(cursor.getLong(cursor.getColumnIndex(Usuario.ID)));

                    usuario.setNome(cursor.getString(cursor.getColumnIndex(Usuario.NM_USUARIO)));
                    usuario.setDataExpiracaoSenha(DateUtils.longToDate(cursor.getLong(cursor.getColumnIndex(Usuario.DT_EXPIRACAO_SENHA))));
                    usuario.setLoginAlternativo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Usuario.FL_LOGIN_ALTERNATIVO))));

                    usuario.setEmail(cursor.getString(cursor.getColumnIndex(Usuario.CD_LOGIN_EMAIL)));
                    usuario.setSenha(cursor.getString(cursor.getColumnIndex(Usuario.CD_SENHA_EMAIL)));
                    usuario.setSenhaAlternativa(cursor.getString(cursor.getColumnIndex(Usuario.CD_SENHA_ALTERNATIVA)));

                    usuario.setExibir(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Usuario.FL_EXIBIR))));
                    usuario.setAtivo(BooleanUtils.parseIntToBoolean(cursor.getInt(cursor.getColumnIndex(Usuario.FL_ATIVO))));


                    arrayList.add(usuario);

                } while (cursor.moveToNext());
            }

            return arrayList;

        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_consultar_erro_usuario));
        } finally {

            if(cursor != null)
                cursor.close();


            super.closeConnection();
        }
    }


    @Override
    public int altera(Usuario item) {
        try {
            super.openConnectionWrite();
            return super.update(preencheContentValues(item), item.getId());
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_usuario));
        } finally {
            super.closeConnection();
        }
    }

    @Override
    public long insere(Usuario item) {
        try {
            super.openConnectionWrite();
            return super.insert(preencheContentValues(item));
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_usuario));
        } finally {
            super.closeConnection();
        }
    }

    @Override
    public int exclui(Usuario item) {
        try {
            super.openConnectionWrite();
            return super.delete(item.getId());
        } catch (SQLException ex) {
            throw new SQLException(super.getContext().getString(R.string.msg_salvar_erro_usuario));
        } finally {
            super.closeConnection();
        }
    }

    @Override
    public Usuario get(Long id) {
        return null;
    }



}

