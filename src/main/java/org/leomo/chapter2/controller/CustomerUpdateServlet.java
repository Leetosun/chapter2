package org.leomo.chapter2.controller;

import org.leomo.chapter2.model.Customer;
import org.leomo.chapter2.service.CustomerService;
import org.leomo.chapter2.util.CastUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LeeToSun on 2017/5/26
 */
@WebServlet("/customer_edit")
public class CustomerUpdateServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = customerService.getCustomer(CastUtil.castLong(req.getParameter("id")));
        req.setAttribute("customer", customer);
        req.getRequestDispatcher("/WEB-INF/view/customer_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> reqMap = req.getParameterMap();
        Map<String, Object> filedMap = new HashMap<>();
        for (String name : reqMap.keySet()) {
            //不能修改id字段
            if ("id".equals(name)) {
                continue;
            }
            //使用ParameterMap时候返回的value是数组形式，需要取第一个(下标0)
            filedMap.put(name, reqMap.get(name)[0]);
        }
        if (customerService.updateCustomer(CastUtil.castLong(req.getParameter("id")), filedMap)) {
            req.setAttribute("message", "修改成功！");
        } else {
            req.setAttribute("message", "修改失败！");
        }
        req.getRequestDispatcher("/WEB-INF/view/message.jsp").forward(req, resp);
    }
}
