package dao;

import entity.OrderDetail;
import entity.OrderDetailPK;

import java.util.List;

public interface OrderDetailDAO extends SuperDAO {

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

}
