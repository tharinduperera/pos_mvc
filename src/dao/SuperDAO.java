package dao;

import entity.Item;

import java.util.List;

public interface SuperDAO {

    List<Object> getAll();

    Object find(Object key);

    boolean save(Object entity);

    boolean update(Object entity);

    boolean delete(Object key);

}

