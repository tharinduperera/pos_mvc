package dao;

import entity.Order;
import java.util.List;

public interface OrderDAO{

    public List<Order> getAllOrders();

    public Order findOrder(String oid);

    public boolean saveOrder(Order order);

    public boolean updateOrder(Order order);

    public boolean deleteOrder(String oid);

    public String getLastOrderId();
}
