package dao.custom.impl;

import dao.custom.QueryDAO;
import db.DBConnection;
import entity.CustomEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public CustomEntity getOrderDetail(String orderId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.name,o.id,o.date FROM `Order` o INNER JOIN Customer c ON o.customerID = c.id WHERE o.id = ?");
            preparedStatement.setObject(1,orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
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
            preparedStatement.setObject(1,orderID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
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
}
