package dao.impl;

import dao.OrderDAO;
import db.DBConnection;
import entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
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

    @Override
    public List<Order> getAll() {
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
        }    }

    @Override
    public Order find(String key) {
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
        }    }

    @Override
    public boolean save(Order entity) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `Order` VALUES(?,?,?)");
            statement.setObject(1,entity.getOrderId());
            statement.setObject(2,entity.getDate());
            statement.setObject(3,entity.getCustomerId());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }    }

    @Override
    public boolean update(Order entity) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE `Order` SET date = ?, customerId = ? WHERE id = ?");
            statement.setObject(3,entity.getOrderId());
            statement.setObject(1,entity.getDate());
            statement.setObject(2,entity.getCustomerId());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }    }

    @Override
    public boolean delete(String key) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE `Order` WHERE id = ?");
            statement.setObject(1,key);
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }    }
}
