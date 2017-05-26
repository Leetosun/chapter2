<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Leetosun
  Date: 2017/5/18
  Time: 14:48
  To change this template use File | Settings | File Templates.
  url：localhost:8080/customer
--%>
<%@ page pageEncoding="UTF-8" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>客户管理 - 客户列表</title>
    <style>
        a {
            text-decoration: none;
            color: blue;
        }

        a:hover {
            color: rebeccapurple;
        }

        #del {
            color: red;
        }
    </style>
</head>
<body>
<h1>客户列表界面</h1>
<table border="1px" cellpadding="10px" cellspacing="0">
    <h2><a href="${BASE}/customer_create">添加客户</a></h2>
    <tr>
        <th>客户名称</th>
        <th>联系人</th>
        <th>电话号码</th>
        <th>邮箱</th>
        <th>备注</th>
        <th>操作</th>
    </tr>
    <c:forEach var="customer" items="${customerList}">
        <tr>
            <td>${customer.name}</td>
            <td>${customer.contact}</td>
            <td>${customer.phone}</td>
            <td>${customer.email}</td>
            <td>${customer.remark}</td>
            <td>
                <a href="${BASE}/customer_edit?id=${customer.id}">编辑</a>
                <a id="del" href="${BASE}/customer_delete?id=${customer.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>