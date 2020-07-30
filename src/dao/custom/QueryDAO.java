package dao.custom;

import dao.SuperDAO;
import entity.CustomEntity;
import entity.Item;

import java.util.List;

public interface QueryDAO extends SuperDAO{
    // join queries

    CustomEntity getOrderDetail(String orderId);

    CustomEntity getOrderDetail2(String orderID);


    List<CustomEntity> findAll();

    List<CustomEntity> searchAll(String key);
}
