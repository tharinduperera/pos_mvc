package dao;

import entity.Item;
import java.util.List;

public interface ItemDAO extends SuperDAO {
    @Override
    List<Object> getAll();

    @Override
    Object find(Object key);

    @Override
    boolean save(Object entity);

    @Override
    boolean update(Object entity);

    @Override
    boolean delete(Object key);

    public String getLastItemCode();
}
