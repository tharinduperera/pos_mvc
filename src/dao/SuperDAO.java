package dao;

import java.util.List;

public interface SuperDAO<T,ID> {

    List<T>getAll();

    T find(ID key);

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(ID key);

}

