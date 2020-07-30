package dao.impl;

import dao.OrderDAO;
import db.DBConnection;
import entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    public List<Object> getAll(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `Order`");
            List<Object>orders = new ArrayList<>();
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

    public Object find(Object key){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `Order` WHERE id = ?");
            statement.setObject(1,key);
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

    public boolean save(Object entity){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `Order` VALUES(?,?,?)");
            Order order = (Order) entity;
            statement.setObject(1,order.getOrderId());
            statement.setObject(2,order.getDate());
            statement.setObject(3,order.getCustomerId());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean update(Object entity){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE `Order` SET date = ?, customerId = ? WHERE id = ?");
            Order order = (Order) entity;
            statement.setObject(3,order.getOrderId());
            statement.setObject(1,order.getDate());
            statement.setObject(2,order.getCustomerId());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean delete(Object key){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE `Order` WHERE id = ?");
            statement.setObject(1,key);
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
