<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Registration</title>
</head>
<body>
	<% session.setAttribute("lastPage", "newUser"); %>
	
    <div align="center">
    	<h2><c:out value='${formText}' /></h2>
    	<form action="<c:out value='${formAction}' />" method="post">
	        <table>
	        	<tr>
	        		<td>
		                <c:if test="${user != null}">
		                    <input type="hidden" name="userId" value="<c:out value='${user.userId}' />" />
		                </c:if>
	                </td>
				</tr>
				<tr><td>First Name:</td></tr>
	            <tr>
	                <td><input type="text" name="firstName" size="50" placeholder="first name" value="<c:out value='${user.firstName}' />" autofocus/></td>
	            </tr>
	            <tr><td>Last Name:</td></tr>
	            <tr>
	                <td><input type="text" name="lastName" size="50" placeholder="last name" value="<c:out value='${user.lastName}' />"/></td>
	            </tr>
				<tr><td>User Name:<font color="red">*</font></td></tr>
	            <tr>
	                <td><input type="text" name="userName" size="50" placeholder="user name" value="<c:out value='${user.userName}' />"/></td>
	            </tr>
	            <tr><td>Password:<font color="red">*</font></td></tr>
	            <tr>
	                <td><input type="password" name="password" size="50" placeholder="password" value="<c:out value='${user.password}' />"/></td>
	            </tr>
	            <tr><td>Confirm Password:<font color="red">*</font></td></tr>
	            <tr>
	                <td><input type="password" name="confirmPassword" size="50" placeholder="confirm password" value="<c:out value='${confirmPassword}' />"/></td>
	            </tr>
	            <tr><td>Email:<font color="red">*</font></td></tr>
	            <tr>
	                <td><input type="email" name="email" size="50" placeholder="userId@email.com" value="<c:out value='${user.email}' />"/></td>
	            </tr>
	            <tr><td>Age:</td></tr>
	            <tr>
	                <td><input type="number" name="age" size="50" placeholder="0-120" value="<c:out value='${user.age}' />"/></td>
	            </tr>
	            <tr><td>Gender:</td></tr>
	            <tr>
	            	<c:choose>
						<c:when test="${user.gender == 'Male'}">
			                <td>
			                	<input type="radio" name="gender" value="Male" checked>Male
			                	<input type="radio" name="gender" value="Female">Female<br>
			                </td>
		                </c:when>
		                <c:when test="${user.gender == 'Female'}">
			                <td>
			                	<input type="radio" name="gender" value="Male">Male
			                	<input type="radio" name="gender" value="Female" checked>Female<br>
			                </td>
		                </c:when>
		                <c:otherwise>
			                <td>
			                	<input type="radio" name="gender" value="Male">Male
			                	<input type="radio" name="gender" value="Female">Female<br>
			                </td>
		                </c:otherwise>
	                </c:choose>
	            </tr>
	            <tr>
	                <td align="center">
	                    <input type="submit" value="<c:out value='${buttonText}' />" />
	                </td>
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