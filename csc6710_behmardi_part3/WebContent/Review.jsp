<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Review</title>
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
							<th><img src="images/reviewer.png" height="70px" width="70px"></th>
						</tr>
					</table>
				</div>
			</th>
			<th></th>
			<th align="right"><a href="logoutUser"><img src="images/logout.png" title="log out" height="70px" width="70px"></a></th>
		</tr>
	</table>
    <div align="center">
    	<form action=<c:out value="${formAction}" /> method="post">
	        <table>
	        	<tr>
	        		<td>
	        			<c:choose>
			                <c:when test="${jokeReview != null}">
			                    <input type="hidden" name="jokeId" value="<c:out value='${jokeReview.getreviewJokeId()}' />" />
			                    <input type="hidden" name="userId" value="<c:out value='${jokeReview.getreviewUserId()}' />" />
			                    <input type="hidden" name="scoreStr" value="<c:out value='${scoreStr}' />" />
			                </c:when>
			                <c:when test="${joke != null}">
			                	<input type="hidden" name="jokeId" value="<c:out value='${joke.getjokeId()}' />" />
			                    <input type="hidden" name="userId" value="<c:out value='${joke.getpostUserId()}' />" />
			                    <input type="hidden" name="scoreStr" value="<c:out value='${scoreStr}' />" />
			                </c:when>
		                </c:choose>
	                </td>
				</tr>
	            <tr><td>Score:</td></tr>
            	<c:choose>
			        <c:when test="${jokeReview != null}">
			            <tr>
			                <td>
			                	<a href="editReview?scoreStr=poor&jokeId=<c:out value='${jokeReview.getreviewJokeId()}' />&userId=<c:out value='${jokeReview.getreviewUserId()}' />"><img src="images/star.png" title="poor" height="30px" width="30px"></a>
		               			<c:choose>
									<c:when test="${score > 1}">
			                			<a href="editReview?scoreStr=fair&jokeId=<c:out value='${jokeReview.getreviewJokeId()}' />&userId=<c:out value='${jokeReview.getreviewUserId()}' />"><img src="images/star.png" title="fair" height="30px" width="30px"></a>
		                			</c:when>
		                			<c:otherwise>
		                				<a href="editReview?scoreStr=fair&jokeId=<c:out value='${jokeReview.getreviewJokeId()}' />&userId=<c:out value='${jokeReview.getreviewUserId()}' />"><img src="images/emptyStar.png" title="fair" height="30px" width="30px"></a>
		                			</c:otherwise>
		               			</c:choose>
		               			<c:choose>
									<c:when test="${score > 2}">
			                			<a href="editReview?scoreStr=good&jokeId=<c:out value='${jokeReview.getreviewJokeId()}' />&userId=<c:out value='${jokeReview.getreviewUserId()}' />"><img src="images/star.png" title="good" height="30px" width="30px"></a>
		                			</c:when>
		                			<c:otherwise>
		                				<a href="editReview?scoreStr=good&jokeId=<c:out value='${jokeReview.getreviewJokeId()}' />&userId=<c:out value='${jokeReview.getreviewUserId()}' />"><img src="images/emptyStar.png" title="good" height="30px" width="30px"></a>
		                			</c:otherwise>
		               			</c:choose>
		               			<c:choose>
									<c:when test="${score > 3}">
			                			<a href="editReview?scoreStr=excellent&jokeId=<c:out value='${jokeReview.getreviewJokeId()}' />&userId=<c:out value='${jokeReview.getreviewUserId()}' />"><img src="images/star.png" title="excellent" height="30px" width="30px"></a>
		                			</c:when>
		                			<c:otherwise>
		                				<a href="editReview?scoreStr=excellent&jokeId=<c:out value='${jokeReview.getreviewJokeId()}' />&userId=<c:out value='${jokeReview.getreviewUserId()}' />"><img src="images/emptyStar.png" title="excellent" height="30px" width="30px"></a>
		                			</c:otherwise>
		               			</c:choose>
			                </td>
			            </tr>
		            </c:when>
	                <c:when test="${joke != null}">
	                	<tr>
			                <td>
			                	<a href="newReview?scoreStr=poor&jokeId=<c:out value='${joke.getjokeId()}' />"><img src="images/star.png" title="poor" height="30px" width="30px"></a>
		               			<c:choose>
									<c:when test="${score > 1}">
			                			<a href="newReview?scoreStr=fair&jokeId=<c:out value='${joke.getjokeId()}' />"><img src="images/star.png" title="fair" height="30px" width="30px"></a>
		                			</c:when>
		                			<c:otherwise>
		                				<a href="newReview?scoreStr=fair&jokeId=<c:out value='${joke.getjokeId()}' />"><img src="images/emptyStar.png" title="fair" height="30px" width="30px"></a>
		                			</c:otherwise>
		               			</c:choose>
		               			<c:choose>
									<c:when test="${score > 2}">
			                			<a href="newReview?scoreStr=good&jokeId=<c:out value='${joke.getjokeId()}' />"><img src="images/star.png" title="good" height="30px" width="30px"></a>
		                			</c:when>
		                			<c:otherwise>
		                				<a href="newReview?scoreStr=good&jokeId=<c:out value='${joke.getjokeId()}' />"><img src="images/emptyStar.png" title="good" height="30px" width="30px"></a>
		                			</c:otherwise>
		               			</c:choose>
		               			<c:choose>
									<c:when test="${score > 3}">
			                			<a href="newReview?scoreStr=excellent&jokeId=<c:out value='${joke.getjokeId()}' />"><img src="images/star.png" title="excellent" height="30px" width="30px"></a>
		                			</c:when>
		                			<c:otherwise>
		                				<a href="newReview?scoreStr=excellent&jokeId=<c:out value='${joke.getjokeId()}' />"><img src="images/emptyStar.png" title="excellent" height="30px" width="30px"></a>
		                			</c:otherwise>
		               			</c:choose>
			                </td>
			            </tr>
	                </c:when>
                </c:choose>
	            <tr><td>Remarks:</td></tr>
	            <tr>
	                <td><input type="text" name="remarks" size="50" autofocus placeholder="remarks ..." value="<c:out value='${jokeReview.getreviewRemark()}' />"/></td>
	            </tr>
	            <tr>
		            <td ><input type="submit" value="<c:out value='${buttonText}' />" /></td>
	            </tr>
	        </table>
        </form>
    </div>
</body>
</html>