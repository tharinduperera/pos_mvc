package dao;

import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return (daoFactory == null) ? daoFactory = new DAOFactory(): daoFactory;
    }


    public SuperDAO getDAO(DAOType DaoTypes){
        switch (DaoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:

                return new OrderDAOImpl();
            case ORDERDETAIl:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
//    public CustomerDAO getCustomerDAO(){
//        return new CustomerDAOImpl();
//    }
//
//    public ItemDAO getItemDAO(){
//        return new ItemDAOImpl();
//    }
//
//    public OrderDAO getOrderDAO(){
//        return new OrderDAOImpl();
//    }
//
//    public OrderDetailDAO getOrderDetailDAO(){
//        return new OrderDetailDAOImpl();
//    }
}
