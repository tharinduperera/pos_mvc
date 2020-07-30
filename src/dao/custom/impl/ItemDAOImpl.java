package dao.impl;

import dao.ItemDAO;
import db.DBConnection;
import entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public String getLastItemCode() {
        {
            try {
                Connection connection = DBConnection.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM item ORDER BY code DESC LIMIT 1");
                if (!resultSet.next()) {
                    return null;
                } else {
                    return resultSet.getString(1);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public List<Item> getAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEM");
            List<Item>items = new ArrayList<>();
            while (resultSet.next()){
                items.add(new Item(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getBigDecimal(3),
                        resultSet.getInt(4)));
            }
            return items;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }    }

    @Override
    public Item find(String key) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ITEM WHERE code = ?");
            statement.setObject(1,key);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new Item(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getBigDecimal(3),
                        resultSet.getInt(4));
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }    }

    @Override
    public boolean save(Item entity) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ITEM VALUES(?,?,?,?)");
            statement.setObject(1,entity.getCode());
            statement.setObject(2,entity.getDescription());
            statement.setObject(3,entity.getQtyOnHand());
            statement.setObject(4,entity.getUnitPrice());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }    }

    @Override
    public boolean update(Item entity) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE ITEM SET description = ?, unitPrice = ?, qtyOnHand = ? WHERE code = ?");
            statement.setObject(4,entity.getCode());
            statement.setObject(1,entity.getDescription());
            statement.setObject(2,entity.getQtyOnHand());
            statement.setObject(3,entity.getUnitPrice());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String key) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM ITEM WHERE code = ?");
            statement.setObject(1,key);
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
