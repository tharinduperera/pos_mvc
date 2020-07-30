package dao.impl;

import dao.ItemDAO;
import db.DBConnection;
import entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    public List<Object> getAll(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ITEM");
            List<Object>items = new ArrayList<>();
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
        }
    }

    public Object find(Object key){
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
        }
    }

    public boolean save(Object entity){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ITEM VALUES(?,?,?,?)");
            Item item = (Item) entity;
            statement.setObject(1,item.getCode());
            statement.setObject(2,item.getDescription());
            statement.setObject(3,item.getQtyOnHand());
            statement.setObject(4,item.getUnitPrice());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean  update(Object entity){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE ITEM SET description = ?, unitPrice = ?, qtyOnHand = ? WHERE code = ?");
            Item item = (Item) entity;
            statement.setObject(4,item.getCode());
            statement.setObject(1,item.getDescription());
            statement.setObject(2,item.getQtyOnHand());
            statement.setObject(3,item.getUnitPrice());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean delete(Object key){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE ITEM WHERE code = ?");
            statement.setObject(1,key);
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

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
}
