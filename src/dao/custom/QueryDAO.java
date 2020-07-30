package dao.custom;

import dao.SuperDAO;
import entity.CustomEntity;

public interface QueryDAO extends SuperDAO{
    // join queries

    CustomEntity getOrderDetail(String orderId);

    CustomEntity getOrderDetail2(String orderID);
}
