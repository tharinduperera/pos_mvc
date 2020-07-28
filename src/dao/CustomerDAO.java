package dao;


import db.DBConnection;
import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public static List<Customer> getAllCustomers(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CUSTOMER");
            List<Customer>customers = new ArrayList<>();
            while (resultSet.next()){
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

    public static Customer findCustomer(String id){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE id = ?");
            statement.setObject(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
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

    public static boolean saveCustomer(Customer customer){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CUSTOMER VALUES(?,?,?)");
            statement.setObject(1,customer.getId());
            statement.setObject(2,customer.getName());
            statement.setObject(3,customer.getAddress());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public static boolean updateCustomer(Customer customer){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE CUSTOMER SET name = ?, address = ? WHERE id = ?");
            statement.setObject(3,customer.getId());
            statement.setObject(1,customer.getName());
            statement.setObject(2,customer.getAddress());
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCustomer(String id){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM CUSTOMER WHERE id = ?");
            statement.setObject(1,id);
            return statement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static String getLastCustomerId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CUSTOMER ORDER BY id DESC LIMIT 1");
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
