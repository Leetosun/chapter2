package org.leomo.chapter2.service;

import org.leomo.chapter2.helper.DatabaseHelper;
import org.leomo.chapter2.model.Customer;
import org.leomo.chapter2.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by LeeToSun on 2017/5/18
 */
public class CustomerService {

    //private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    /*
    ------------------------------ old -------------------------------
    public List<Customer> getCustomerList() {
        Connection connection = null;
        try {
            List<Customer> customers = new ArrayList<>();
            String sql = "SELECT * FROM customer";
            connection = DatabaseHelper.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setContact(resultSet.getString("contact"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setEmail(resultSet.getString("email"));
                customer.setRemark(resultSet.getString("remark"));
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            LOGGER.error("execute sql failure", e);
        } finally {
            DatabaseHelper.closeConnection();
        }
        return null;
    }
    */

    public List<Customer> getCustomerList() {
        String sql = "SELECT * FROM customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }


    public Customer getCustomer(long id) {
        String sql = "SELECT * FROM customer WHERE id=" + id;
        return DatabaseHelper.queryEntity(Customer.class, sql);
    }

    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, fieldMap, id);
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }

}
