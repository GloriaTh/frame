<%--
  Created by IntelliJ IDEA.
  User: Gloria
  Date: 2018/7/10
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>欢迎登录!${user.username }</h1>
<form action="${pageContext.request.contextPath }/user" method="post">
    <input type="text" name="username"><br>
    <input type="password" name="password"><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
