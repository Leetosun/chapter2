package org.leomo.chapter2.controller;

import org.leomo.chapter2.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LeeToSun on 2017/5/18
 */
@WebServlet("/customer_create")
public class CustomerCreateServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/customer_create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> reqMap = req.getParameterMap();
        Map<String, Object> filedMap = new HashMap<>();
        for (String name : reqMap.keySet()) {
            filedMap.put(name, reqMap.get(name)[0]);
        }
        if (customerService.createCustomer(filedMap)) {
            req.setAttribute("message", "添加成功！");
        } else {
            req.setAttribute("message", "添加失败！");
        }
        req.getRequestDispatcher("/WEB-INF/view/message.jsp").forward(req, resp);
    }

}
