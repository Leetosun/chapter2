<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/26 0026
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户管理 - 添加客户</title>
</head>
<body>

<form action="customer_create" method="post">
    <span>客户名称</span>
    <!--使用form表单提交时，必须要有name属性，否则后台无法接收-->
    <input type="text" name="name">
    <br><br>
    <span>联系人</span>
    <input type="text" name="contact">
    <br><br>
    <span>电话号码</span>
    <input type="text" name="phone">
    <br><br>
    <span>邮箱</span>
    <input type="text" name="email">
    <br><br>
    <span>备注</span>
    <input type="text" name="remark">
    <br><br>
    <input type="submit" value="添加">
</form>
</body>
</html>
