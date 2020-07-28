package business;

import dao.*;
import db.DBConnection;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessLogics {

    public static String getNewCustomerId(){
        String lastCustomerId = CustomerDAO.getLastCustomerId();
        if (lastCustomerId == null){
            return "C001";
        }else{
            int maxId=  Integer.parseInt(lastCustomerId.replace("C",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
            }
            return id;
        }
    }

    public static String getNewItemCode(){
        String lastItemCode = ItemDAO.getLastItemCode();
        if (lastItemCode == null){
            return "I001";
        }else{
            int maxId=  Integer.parseInt(lastItemCode.replace("I",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "I00" + maxId;
            } else if (maxId < 100) {
                id = "I0" + maxId;
            } else {
                id = "I" + maxId;
            }
            return id;
        }
    }

    public static String getNewOrderId(){
        String lastOrderId = OrderDAO.getLastOrderId();
        if (lastOrderId == null){
            return "OD001";
        }else{
            int maxId=  Integer.parseInt(lastOrderId.replace("OD",""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OD00" + maxId;
            } else if (maxId < 100) {
                id = "OD0" + maxId;
            } else {
                id = "OD" + maxId;
            }
            return id;
        }
    }

    public static List<CustomerTM> getAllCustomers(){
        List<Customer> allCustomers = CustomerDAO.getAllCustomers();
        List<CustomerTM> customerTMS = new ArrayList<>();
        for (Customer customer : allCustomers) {
            customerTMS.add(new CustomerTM(customer.getId(),customer.getName(),customer.getAddress()));
        }
        return customerTMS;
    }

    public static boolean saveCustomer(String id, String name, String address){
        return CustomerDAO.saveCustomer(new Customer(id,name,address));
    }

    public static boolean deleteCustomer(String customerId){
        return CustomerDAO.deleteCustomer(customerId);
    }

    public static boolean updateCustomer(String name, String address, String customerId){
        return CustomerDAO.updateCustomer(new Customer(customerId, name, address));
    }

    public static List<ItemTM> getAllItems(){

        List<Item> allItems = ItemDAO.getAllItems();
        List<ItemTM> itemTMS = new ArrayList<>();
        for (Item allItem : allItems) {
            itemTMS.add(new ItemTM(allItem.getCode(),allItem.getDescription(),allItem.getQtyOnHand(),allItem.getUnitPrice().doubleValue()));
        }
        return itemTMS;
    }

    public static boolean saveItem(String code, String description, int qtyOnHand, double unitPrice){
        return ItemDAO.saveItem(new Item(code, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }

    public static boolean deleteItem(String itemCode){
        return ItemDAO.deleteItem(itemCode);
    }

    public static boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode){
        return ItemDAO.updateItem(new Item(itemCode, description, BigDecimal.valueOf(unitPrice), qtyOnHand));
    }

//    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//
//            int i = DataLayer.placeOrder(order);
//
//            if (i == 0) {
//                connection.rollback();
//                return false;
//            }
//
//            for (OrderDetailTM orderDetail: orderDetails) {
//
//                int i2 = DataLayer.placeOrderDetail(orderDetail,order.getOrderId());
//
//                if (i2 == 0){
//                    connection.rollback();
//                    return false;
//                }
//
//                int i3 = DataLayer.updateQty(orderDetail);
//
//                if (i3 == 0){
//                    connection.rollback();
//                    return false;
//                }
//            }
//            connection.commit();
//            return true;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return false;
//        } finally {
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
//    }

    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean result = OrderDAO.saveOrder(new Order(order.getOrderId(),
                    Date.valueOf(order.getOrderDate()),
                            order.getCustomerId()));

            if (!result) {
                connection.rollback();
                return false;
            }

            for (OrderDetailTM orderDetail: orderDetails) {

                result = OrderDetailDAO.saveOrderDetail(new OrderDetail(order.getOrderId(),
                        orderDetail.getCode(),
                        orderDetail.getQty(),
                        BigDecimal.valueOf(orderDetail.getUnitPrice())));
                if (!result){
                    connection.rollback();
                    return false;
                }

                Item item = ItemDAO.findItem(orderDetail.getCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
                result = ItemDAO.updateItem(item);
                if (!result){
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


}
