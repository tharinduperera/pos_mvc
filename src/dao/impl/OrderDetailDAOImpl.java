package dao.impl;

import dao.OrderDetailDAO;
import db.DBConnection;
import entity.Order;
import entity.OrderDetail;
import entity.OrderDetailPK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    public List<Object> getAll(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `OrderDetail`");
            List<Object>orders = new ArrayList<>();
            while (resultSet.next()){
                orders.add(new OrderDetail(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getBigDecimal(4)));
            }
            return orders;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public Object find(Object key){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `OrderDetail` WHERE orderId = ? AND itemCode = ?");
            OrderDetailPK orderDetailPK = (OrderDetailPK) key;
            statement.setObject(1,orderDetailPK.getOrderId());
            statement.setObject(1,orderDetailPK.getItemCode());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new OrderDetail(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getBigDecimal(4));
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public boolean save(Object entity){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `OrderDetail` VALUES(?,?,?,?)");
            OrderDetail orderDetail = (OrderDetail) entity;
            statement.setObject(1,orderDetail.getOrderDetailPK().getOrderId());
            statement.setObject(2,orderDetail.getOrderDetailPK().getItemCode());
            statement.setObject(3,orderDetail.getQty());
            statement.setObject(4,orderDetail.getUnitPrice());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean update(Object entity){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE `OrderDetail` SET qty = ?, unitPrice = ? WHERE orderId = ? AND itemCode = ?");
            OrderDetail orderDetail = (OrderDetail) entity;
            statement.setObject(3,orderDetail.getOrderDetailPK().getOrderId());
            statement.setObject(4,orderDetail.getOrderDetailPK().getItemCode());
            statement.setObject(1,orderDetail.getQty());
            statement.setObject(2,orderDetail.getUnitPrice());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean delete(Object key){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE `OrderDetail` WHERE orderId = ? AND itemCode = ?");
            OrderDetailPK orderDetailPK = (OrderDetailPK) key;
            statement.setObject(1,orderDetailPK.getOrderId());
            statement.setObject(2,orderDetailPK.getItemCode());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
