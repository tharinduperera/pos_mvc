package dao.custom.impl;

import dao.custom.QueryDAO;
import db.DBConnection;
import entity.CustomEntity;
import entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public CustomEntity getOrderDetail(String orderId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.name,o.id,o.date FROM `Order` o INNER JOIN Customer c ON o.customerID = c.id WHERE o.id = ?");
            preparedStatement.setObject(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new CustomEntity(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
            return null;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomEntity getOrderDetail2(String orderID) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.name,o.id,c.id FROM `Order` o INNER JOIN Customer c ON o.customerID = c.id WHERE o.id = ?");
            preparedStatement.setObject(1, orderID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new CustomEntity(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
            return null;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

//    @Override
//    public List<CustomEntity> getAll() {
//        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT o.id,o.date,c.id,c.name,od.qty*od.unitPrice AS total FROM Customer c INNER JOIN `Order` o ON o.customerID = c.id   INNER JOIN OrderDetail od ON o.id = od.orderId WHERE o.id = ? OR o.date = ? OR c.id = ? OR c.name = ?");
//            List<CustomEntity> customEntities = new ArrayList<>();
//            while (resultSet.next()) {
//                customEntities.add(new CustomEntity(resultSet.getString(1),
//                        resultSet.getString(2),
//                        resultSet.getDate(3),
//                        resultSet.getString(4),
//                        resultSet.getBigDecimal(5)));
//            }
//            return customEntities;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public List<CustomEntity> findAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT o.id,c.id,o.date,c.name,SUM(od.qty*od.unitPrice) AS total FROM Customer c INNER JOIN `Order` o ON o.customerID = c.id   INNER JOIN OrderDetail od ON o.id = od.orderId GROUP BY o.id,o.date,c.id,c.name");
            List<CustomEntity>customEntities = new ArrayList<>();
            while (resultSet.next()) {
                customEntities.add(new CustomEntity(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getBigDecimal(5)));
            }
            return customEntities;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CustomEntity> searchAll(String key) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT o.id,c.id,o.date,c.name,SUM(od.qty*od.unitPrice) AS total FROM Customer c INNER JOIN `Order` o ON o.customerID = c.id   INNER JOIN OrderDetail od ON o.id = od.orderId WHERE o.id = ? OR o.date = ? OR c.id = ? OR c.name = ? GROUP BY o.id,o.date,c.id,c.name");
            statement.setObject(1,key);
            statement.setObject(2,key);
            statement.setObject(3,key);
            statement.setObject(4,key);
            ResultSet resultSet = statement.executeQuery();
            List<CustomEntity>customEntities = new ArrayList<>();
            while (resultSet.next()) {
                customEntities.add(new CustomEntity(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getString(4),
                        resultSet.getBigDecimal(5)));
            }
            return customEntities;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}

