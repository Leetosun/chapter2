package org.leomo.chapter2.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.leomo.chapter2.helper.DatabaseHelper;
import org.leomo.chapter2.model.Customer;
import org.leomo.chapter2.service.CustomerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LeeToSun on 2017/5/19
 */
public class CustomerServletTest {

    private final CustomerService customerService;

    public CustomerServletTest() {
        this.customerService = new CustomerService();
    }

    @Before
    public void init() throws IOException {
        //初始化数据库
        DatabaseHelper.executeSqlFile("sql/demo_customer.sql");
    }

    @Test
    public void getCustomerListTest() throws Exception {
        List<Customer> customers = customerService.getCustomerList();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        Assert.assertEquals(3, customers.size());
    }

    @Test
    public void getCustomerTest() {
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        System.out.println(customer);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest() {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("name", "customer101");
        fieldMap.put("contact", "Soft");
        fieldMap.put("phone", "18924665555");
        boolean result = customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest() {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("remark", "测试数据");
        boolean result = customerService.updateCustomer(1L, fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomerTest() {
        long id = 3;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }

}
