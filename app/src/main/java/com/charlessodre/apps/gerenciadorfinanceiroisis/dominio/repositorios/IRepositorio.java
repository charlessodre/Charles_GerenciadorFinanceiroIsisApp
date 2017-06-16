package com.charlessodre.apps.gerenciadorfinanceiroisis.dominio.repositorios;

import java.util.ArrayList;

/**
 * Created by charl on 11/09/2016.
 */
public interface IRepositorio<T,ID> {

    int altera(T item);
    long insere(T item);
    int exclui(T item);
    T get(ID id);
    ArrayList<T> getAll();
}
