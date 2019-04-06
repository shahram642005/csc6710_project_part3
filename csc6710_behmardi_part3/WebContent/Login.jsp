<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Login</title>
</head>
<body>
	<%
	if(session.getAttribute("userId") != null)
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("listAllJokes");
        dispatcher.forward(request, response);
	}
	session.setAttribute("lastPage", "listAllJokes");
	%>
	
	<table style="padding-bottom:50">
	<col width="600">
	<col width="600">
	<col width="600">
	<col width="600">
	<col width="600">
		<tr>
		<tr>
			<th/>
			<th></th>
			<th align="center"><h2>Please login or register!</h2></th>
			<th></th>
			<th></th>
		</tr>
		<tr>
			<th></th>
			<th align="right"><a href="initTables"><img src="images/reset.png" title="initialize database" height="75px" width="75px"></a></th>
			<th></th>
        	<th align="left"><a href="newUser"><img src="images/register.png" title="register user" height="100px" width="100px"></a></th>
        	<th></th>
		</tr>
	</table>
    <div align="center">
        <form action="loginUser" method="post">
        	<table>
        		<tr><td>User Name:</td></tr>
        		<tr>
        			<td><input type="text" placeholder="user name" name="userName" size="50" autofocus value="<c:out value='${user.userName}'/>"/></td>
        		</tr>
        		<tr><td>Password:</td></tr>
        		<tr>
        			<td><input type="password" placeholder="password" name="password" size="50" value="<c:out value='${user.password}'/>"/></td>
        		</tr>
        		<tr>
        			<td><input type="submit" value="login" /></td>
        		</tr>
        	</table>
        </form>
    </div>
    <div>
		<h2 style="text-align:center">
		    <c:if test="${message != null && color != null}">
		        <font color="<c:out value='${color}'/>"><c:out value='${message}'/></font>
		    </c:if>
		</h2>
    </div>
</body>
</html>