package org.leomo.chapter2.controller;

import org.leomo.chapter2.service.CustomerService;
import org.leomo.chapter2.util.CastUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by LeeToSun on 2017/5/26
 */
@WebServlet("/customer_delete")
public class CustomerDeleteServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (customerService.deleteCustomer(CastUtil.castLong(req.getParameter("id")))) {
            req.setAttribute("message", "删除成功！");
        } else {
            req.setAttribute("messaeg", "删除失败！");
        }
        req.getRequestDispatcher("/WEB-INF/view/message.jsp").forward(req, resp);
    }
}
