import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.leomo.chapter2.model.Customer;
import org.leomo.chapter2.service.CustomerService;

import java.util.List;

/**
 * Created by LeeToSun on 2017/5/19
 */
public class CustomerServletTest {

    private final CustomerService customerService;

    public CustomerServletTest() {
        this.customerService = new CustomerService();
    }

    @Before
    public void init() {
        //TODO 初始化数据库
    }

    @Test
    public void getCustomerListTest() throws Exception {
        List<Customer> customers = customerService.getCustomerList();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        Assert.assertEquals(2, customers.size());
    }

    @Test
    public void getCustomerTest() {
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerTest() {
        Customer customer = new Customer();
        customer.setName("customer100");
        customer.setContact("John");
        customer.setPhone("13245469999");
        boolean result = customerService.createCustomer(customer);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Eric");
        boolean result = customerService.updateCustomer(customer);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomerTest() {
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }

}
