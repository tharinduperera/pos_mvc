package dao.custom.impl;

import dao.custom.OrderDetailDAO;
import db.DBConnection;
import entity.OrderDetail;
import entity.OrderDetailPK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public List<OrderDetail> getAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `OrderDetail`");
            List<OrderDetail>orders = new ArrayList<>();
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

    @Override
    public OrderDetail find(OrderDetailPK key) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `OrderDetail` WHERE orderId = ? AND itemCode = ?");
            statement.setObject(1,key.getOrderId());
            statement.setObject(1,key.getItemCode());
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
        }    }

    @Override
    public boolean save(OrderDetail entity) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO `OrderDetail` VALUES(?,?,?,?)");
            statement.setObject(1,entity.getOrderDetailPK().getOrderId());
            statement.setObject(2,entity.getOrderDetailPK().getItemCode());
            statement.setObject(3,entity.getQty());
            statement.setObject(4,entity.getUnitPrice());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }    }

    @Override
    public boolean update(OrderDetail entity) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE `OrderDetail` SET qty = ?, unitPrice = ? WHERE orderId = ? AND itemCode = ?");
            statement.setObject(3,entity.getOrderDetailPK().getOrderId());
            statement.setObject(4,entity.getOrderDetailPK().getItemCode());
            statement.setObject(1,entity.getQty());
            statement.setObject(2,entity.getUnitPrice());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }    }

    @Override
    public boolean delete(OrderDetailPK key) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE `OrderDetail` WHERE orderId = ? AND itemCode = ?");
            statement.setObject(1,key.getOrderId());
            statement.setObject(2,key.getItemCode());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }    }
}
