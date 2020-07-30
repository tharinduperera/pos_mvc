package dao.custom.impl;

import dao.DAOFactory;
import dao.DAOType;
import dao.custom.QueryDAO;
import entity.CustomEntity;

import java.util.List;

class QueryDAOImplTest {
    public static void main(String[] args) {
        QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOType.QUERY);
        CustomEntity entity = queryDAO.getOrderDetail("OD001");
        System.out.println(entity.getCustomerName());
        System.out.println(entity.getOrderId());
        System.out.println(entity.getOrderDate());

        CustomEntity entity1 = queryDAO.getOrderDetail2("OD001");
        System.out.println(entity1.getCustomerName());
        System.out.println(entity1.getOrderId());
        System.out.println(entity1.getCustomerId());

        List<CustomEntity> all = queryDAO.findAll();
        for (CustomEntity customEntity : all) {
            System.out.println(customEntity);
        }
        System.out.println("=============================");
        List<CustomEntity> customEntities = queryDAO.searchAll("kamal");
        for (CustomEntity customEntity : customEntities) {
            System.out.println(customEntity);
        }

    }

}