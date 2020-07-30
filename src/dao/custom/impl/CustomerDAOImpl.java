package dao.custom.impl;


import dao.custom.CustomerDAO;
import db.DBConnection;
import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {


    @Override
    public String getLastCustomerId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CUSTOMER ORDER BY id DESC LIMIT 1");
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

    @Override
    public List<Customer> getAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CUSTOMER");
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)));
            }
            return customers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    @Override
    public Customer find(String key) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE id = ?");
            statement.setObject(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Customer(resultSet.getString(1),
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
    public boolean save(Customer entity) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CUSTOMER VALUES(?,?,?)");
            statement.setObject(1, entity.getId());
            statement.setObject(2, entity.getName());
            statement.setObject(3, entity.getAddress());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Customer entity) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE CUSTOMER SET name = ?, address = ? WHERE id = ?");
            statement.setObject(3, entity.getId());
            statement.setObject(1, entity.getName());
            statement.setObject(2, entity.getAddress());
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM CUSTOMER WHERE id = ?");
            statement.setObject(1, key);
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


}
