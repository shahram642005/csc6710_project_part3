<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Post Joke</title>
</head>
<body>
<%
	if(session.getAttribute("userId") == null)
	{
		response.sendRedirect("Login.jsp");
	}
	else
	{
		int userId = Integer.parseInt(session.getAttribute("userId").toString());
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
			<c:choose>
			<c:when test="${userId == 1}">
					<th align="left"><a href="modifyUser?userId=1"><img src="images/admin.png" title="edit profile" height="100px" width="100px"></a></th>
				</c:when>
				<c:when test="${gender == null}">
					<th align="left"><a href="modifyUser?userId=0"><img src="images/nogenderProfile.png" title="edit profile" height="100px" width="100px"></a></th>
				</c:when>
				<c:when test="${gender eq 'Male'}">
					<th align="left"><a href="modifyUser?userId=0"><img src="images/maleProfile.png" title="edit profile" height="100px" width="100px"></a></th>
				</c:when>
				<c:otherwise>
					<th align="left"><a href="modifyUser?userId=0"><img src="images/femaleProfile.png" title="edit profile" height="100px" width="100px"></a></th>
				</c:otherwise>
			</c:choose>
			<th align="left"><a href="listAllJokes"><img src="images/home.png" title="home" height="70px" width="70px"></a></th>
			<th>
				<div align="left">
					<table>
						<tr>
							<th><h2><c:out value='${formText}' /></h2></th>
							<th><img src="images/funny.png" height="70px" width="70px"></th>
						</tr>
					</table>
				</div>
			</th>
			<th></th>
			<th align="right"><a href="logoutUser"><img src="images/logout.png" title="log out" height="70px" width="70px"></a></th>
		</tr>
	</table>
    <div align="center">
    	<form id="jokePostFrom" action=<c:out value="${formAction}" /> method="post">
	        <table>
	        	<tr>
	        		<td>
		                <c:if test="${joke != null}">
		                    <input type="hidden" name="jokeId" value="<c:out value='${joke.jokeId}' />" />
		                    <input type="hidden" name="userId" value="<c:out value='${joke.postUserId}' />" />
		                </c:if> 
	                </td>
				</tr>
				<tr><td>Title:</td></tr>
	            <tr>
	                <td><input type="text" name="title" size="50" autofocus placeholder="title of your joke" value="<c:out value='${joke.jokeTitle}' />"/></td>
	            </tr>
	            <tr><td>Description:</td></tr>
	            <tr>
	                <td><textarea rows="4" cols="42" name="description" placeholder="description of your joke" form="jokePostFrom" /><c:out value='${joke.jokeText}' /></textarea></td>
	            </tr>
	            <tr><td>Tags:</td></tr>
	            <tr>
	                <td><input type="text" name="tags" size="50" autofocus placeholder="tag1, tag2, ..." value="<c:out value='${jokeTag.jokeTagWord}' />"/></td>
	            </tr>
	            <tr>
		            <td ><input type="submit" value="<c:out value='${buttonText}' />" /></td>
	            </tr>
	        </table>
        </form>
    </div>
</body>
</html>