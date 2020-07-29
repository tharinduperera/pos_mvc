package dao.impl;

import dao.OrderDAO;
import db.DBConnection;
import entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    public List<Order> getAllOrders(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `Order`");
            List<Order>orders = new ArrayList<>();
            while (resultSet.next()){
                orders.add(new Order(resultSet.getString(1),
                        resultSet.getDate(2),
                        resultSet.getString(3)));
            }
            return orders;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public Order findOrder(String oid){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `Order` WHERE id = ?");
            statement.setObject(1,oid);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new Order(resultSet.getString(1),
                        resultSet.getDate(2),
                        resultSet.getString(3));
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public boolean saveOrder(Order order){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `Order` VALUES(?,?,?)");
            statement.setObject(1,order.getOrderId());
            statement.setObject(2,order.getDate());
            statement.setObject(3,order.getCustomerId());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean updateOrder(Order order){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE `Order` SET date = ?, customerId = ? WHERE id = ?");
            statement.setObject(3,order.getOrderId());
            statement.setObject(1,order.getDate());
            statement.setObject(2,order.getCustomerId());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(String oid){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE `Order` WHERE id = ?");
            statement.setObject(1,oid);
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public String getLastOrderId() {
        {
            try {
                Connection connection = DBConnection.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM `Order` ORDER BY id DESC LIMIT 1");
                if(!resultSet.next()){
                    return null;
                }else {
                    return resultSet.getString(1);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
        }
    }
}
