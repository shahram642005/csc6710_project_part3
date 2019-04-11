<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Welcome</title>
</head>
<body>
<!-- Check if user is logged in -->
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

	<!-- header icons: user picture, welcome note, logout, search jokes, add joke, users list (only root), friend list, favorite jokes -->
	<table>
	<col width="600">
	<col width="600">
	<col width="600">
	<col width="600">
	<col width="600">
		<tr >
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
				<div align="center">
					<table>
						<tr>
							<td><h2>Welcome<c:out value=" ${userName}"/>!</h2></td>
							<td><img src="images/welcome.png" height="70px" width="70px"></td>
						</tr>
					</table>
				</div>
			</th>
			<th></th>
			<th align="right"><a href="logoutUser"><img src="images/logout.png" title="log out" height="70px" width="70px"></a></th>
		</tr>
		<tr>
			<td align="right"><a href="goToQueries"><img src="images/query.png" title="queries" height="50px" width="50px"></a></td>
			<td align="right">
				<form action="searchJoke" method="post">
					<table>
					    <tr>
					    	<td><input type="search" placeholder="find a joke by tag ..." title="type a joke tag" name="searchTag" size="25" value="<c:out value='${searchTag}'/>"/></td>
							<td><input type="submit" title="search" value="search" /></td>
						</tr>
					</table>
				</form>
			</td>
			<td align="right"><a href="newJoke"><img src="images/newJoke.png" title="post a new joke" height="50px" width="50px"></a></td>
			<c:choose>
				<c:when test="${userId == 1}">
					<td align="right"><a href="listAllUsers"><img src="images/listUsers.png" title="list all users" height="50px" width="50px"></a></td>
				</c:when>
				<c:otherwise>
					<td align="right"><a href="listFriends"><img src="images/listFriends.png" title="list friends" height="50px" width="50px"></a></td>
					<td align="left"><a href="listFavoritejokes"><img src="images/ListFavoriteJokes.png" title="list favorite jokes" height="50px" width="50px"></a></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<!-- msg on top of the tables -->
			<td/>
			<td/>
			<td>
				<h2 style="text-align:center">
					<br>
				    <c:if test="${message != null && color != null}">
				        <font color=<c:out value="${color}"></c:out>><c:out value="${message}"></c:out></font>
				    </c:if>
				</h2>
			</td>
			<td/>
			<td/>
		</tr>
	</table>
    
    <!-- listUser table (only root) -->
	<div align="center">
    	<c:if test="${userList != null}">
	        <table>
	            <tr bgcolor="Aquamarine">
	            	<c:if test="${userId == 1}">
	                	<th width="20">ID</th>
                	</c:if>
	                <th width="100">User Name</th>
	                <c:if test="${userId == 1}">
	                	<th width="100">Password</th>
                	</c:if>
	                <th width="100">First Name</th>
	                <th width="100">Last Name</th>
	                <th width="200">Email</th>
	                <th width="50">Gender</th>
	                <th width="50">Age</th>
	                <c:choose>
		                <c:when test="${userId == 1}">
		                	<th width="200" colspan="3">Actions</th>
	                	</c:when>
	                	<c:otherwise>
	                		<th width="200" colspan="3">Action</th>
	                	</c:otherwise>
                	</c:choose>
	            </tr>
	            <c:forEach var="user" items="${userList}">
	                <tr bgcolor="HoneyDew">
	                	<c:if test="${userId == 1}">
	                    	<td align="center"><c:out value="${user.userId}" /></td>
                    	</c:if>
	                    <td align="center"><c:out value="${user.userName}" /></td>
	                    <c:if test="${userId == 1}">
	                    	<td align="center"><c:out value="${user.password}" /></td>
                    	</c:if>
	                    <td align="center"><c:out value="${user.firstName}" /></td>
	                    <td align="center"><c:out value="${user.lastName}" /></td>
	                    <td align="center"><c:out value="${user.email}" /></td>
	                    <td align="center"><c:out value="${user.gender}" /></td>
	                    <td align="center"><c:out value="${user.age}" /></td>
	                    <c:choose>
		                	<c:when test="${userId == 1}">
			                    <td align="center">
			                        <a href="modifyUser?userId=<c:out value='${user.userId}' />"><img src="images/editUser.png" title="edit user" height="30%" width="30%"></a>
			                    </td>
			                    <td align="center">
			                        <a href="deleteUser?userId=<c:out value='${user.userId}' />"><img src="images/removeUser.png" title="remove user" height="30%" width="30%"></a>
			                    </td>
			                    <td align="center">
			                    	<c:set var="isBanned" value="false" />
									<c:forEach var="bannedUser" items="${bannedUsers}">
									  <c:if test="${user.userId == bannedUser.userId}">
									    <c:set var="isBanned" value="true" />
									  </c:if>
									</c:forEach>
				                    <c:choose>
										<c:when test="${user.isBanned}">
											<a href="unbanUser?userId=<c:out value='${user.userId}' />"><img src="images/banUser.png" title="unban user" height="30%" width="30%"></a>
										</c:when>
										<c:otherwise>
											<a href="banUser?userId=<c:out value='${user.userId}' />"><img src="images/unbanUser.png" title="ban user" height="30%" width="30%"></a>
										</c:otherwise>
									</c:choose>
			                    </td>
		                    </c:when>
		                    <c:otherwise>
		                    	<td align="center">
		                    		<a href="unstarUser?postUserId=<c:out value='${user.userId}' />"><img src="images/favoriteUser.png" title="remove friend" height="30%" width="30%"></a>
	                    		</td>
		                    	<td align="center">
		                    		<a href="listUserJokes?userId=<c:out value='${user.userId}' />"><img src="images/listJokes.png" title="list user's jokes" height="30%" width="30%"></a>
	                    		</td>
		                    </c:otherwise>
	                    </c:choose>
	                </tr>
	            </c:forEach>
	        </table>
        </c:if>
    </div>
    
    <!-- List jokes -->
	<div align="center">
		<c:if test="${jokeList != null}">
	        <table>
	            <tr bgcolor="Lavender">
	                <th width="100">ID<a href="sort?attr=jokeId&order=<c:out value='${idOrder}' />"><img src="images/<c:out value='${idImage}' />" title="order by id" height="10%" width="10%"></a></th>
	                <th width="100">Title<a href="sort?attr=jokeTitle&order=<c:out value='${titleOrder}' />"><img src="images/<c:out value='${titleImage}' />" title="order by title" height="10%" width="10%"></a></th>
	                <th width="500">Text</th>
	                <th width="100">Date<a href="sort?attr=jokePostDate&order=<c:out value='${dateOrder}' />"><img src="images/<c:out value='${dateImage}' />" title="order by date" height="10%" width="10%"></a></th>
	                <th width="100">User</th>
	                <th width="200" colspan="3">Actions</th>
	            </tr>
	            <c:forEach var="joke" items="${jokeList}">
	                <tr bgcolor="AliceBlue">
	                    <td align="center"><c:out value="${joke.jokeId}" /></td>
	                    <td align="center"><c:out value="${joke.jokeTitle}" /></td>
	                    <td align="center"><c:out value="${joke.jokeText}" /></td>
	                    <td align="center"><c:out value="${joke.jokePostDate}" /></td>
	                    <td align="center"><c:out value="${userDAO.getUser(joke.postUserId).userName}" /></td>
	                    <c:choose>
	                    	<c:when test="${sessionUserId == 1}">
			                    <td align="center">
			                        <a href="modifyJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />"><img src="images/edit.png"  title="edit joke" height="30%" width="30%"></a>
			                    </td>
			                    <td align="center">
			                        <a href="deleteJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />"><img src="images/trash.png"  title="remove joke" height="30%" width="30%"></a>
			                    </td>
			                    <td align="center">
			                        <a href="newReview?jokeId=<c:out value='${joke.jokeId}' />"><img src="images/addReview.png"  title="add review" height="30%" width="30%"></a>
			                    </td>
		                    </c:when>
		                    <c:when test="${joke.postUserId == sessionUserId}">
		                    	<td align="center">
			                        <a href="modifyJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />"><img src="images/edit.png"  title="edit joke" height="30%" width="30%"></a>
			                    </td>
			                    <td align="center">
			                        <a href="deleteJoke?jokeId=<c:out value='${joke.jokeId}' />&postUserId=<c:out value='${joke.postUserId}' />"><img src="images/trash.png"  title="remove joke" height="30%" width="30%"></a>
			                    </td>
			                    <td align="center">-</td>
		                    </c:when>
		                    <c:otherwise>
			                    <td align="center">
			                    	<c:set var="isStarUser" value="false" />
									<c:forEach var="friend" items="${friendList}">
									  <c:if test="${friend.userId == joke.postUserId}">
									    <c:set var="isStarUser" value="true" />
									  </c:if>
									</c:forEach>
			                    	<c:choose>
										<c:when test="${isStarUser == 'true'}">
											<a href="unstarUser?postUserId=<c:out value='${joke.postUserId}' />"><img src="images/favoriteUser.png" title="remove friend" height="30%" width="30%"></a>
										</c:when>
										<c:otherwise>
											<a href="starUser?postUserId=<c:out value='${joke.postUserId}' />"><img src="images/user.png" title="add friend" height="30%" width="30%"></a>
										</c:otherwise>
									</c:choose>
	                    		</td>
			                    <td align="center">
			                    	<c:set var="isStarJoke" value="false" />
									<c:forEach var="favoriteJoke" items="${favoriteJokeList}">
									  <c:if test="${favoriteJoke.jokeId == joke.jokeId}">
									    <c:set var="isStarJoke" value="true" />
									  </c:if>
									</c:forEach>
				                    <c:choose>
										<c:when test="${isStarJoke == 'true'}">
											<a href="unstarJoke?jokeId=<c:out value='${joke.jokeId}' />"><img src="images/starJoke.png" title="remove favorite" height="30%" width="30%"></a>
										</c:when>
										<c:otherwise>
											<a href="starJoke?jokeId=<c:out value='${joke.jokeId}' />"><img src="images/unstarJoke.png" title="add favorite" height="30%" width="30%"></a>
										</c:otherwise>
									</c:choose>
			                    </td>
			                    <td align="center">
			                        <a href="newReview?jokeId=<c:out value='${joke.jokeId}' />"><img src="images/addReview.png"  title="add review" height="30%" width="30%"></a>
			                    </td>
		                    </c:otherwise>
	                    </c:choose>
	                </tr>
	                <!-- List Reviews -->
	                <c:forEach var="jokeReview" items="${jokeReviewList}">
	                	<c:if test="${jokeReview.reviewJokeId == joke.jokeId}">
		                	<tr>
		                		<td/>
		                		<td bgcolor="LightYellow" align="center">
			               			<c:choose>
										<c:when test="${jokeReview.reviewScore == 'poor'}">
				                			<img src="images/star.png" height="20px" width="20px">
				                			<img src="images/emptyStar.png" height="20px" width="20px">
				                			<img src="images/emptyStar.png" height="20px" width="20px">
				                			<img src="images/emptyStar.png" height="20px" width="20px">
			                			</c:when>
										<c:when test="${jokeReview.reviewScore == 'fair'}">
				                			<img src="images/star.png" height="20px" width="20px">
				                			<img src="images/star.png" height="20px" width="20px">
				                			<img src="images/emptyStar.png" height="20px" width="20px">
				                			<img src="images/emptyStar.png" height="20px" width="20px">
			                			</c:when>
										<c:when test="${jokeReview.reviewScore == 'good'}">
				                			<img src="images/star.png" height="20px" width="20px">
				                			<img src="images/star.png" height="20px" width="20px">
				                			<img src="images/star.png" height="20px" width="20px">
				                			<img src="images/emptyStar.png" height="20px" width="20px">
			                			</c:when>
			                			<c:otherwise>
			                				<img src="images/star.png" height="20px" width="20px">
				                			<img src="images/star.png" height="20px" width="20px">
				                			<img src="images/star.png" height="20px" width="20px">
				                			<img src="images/star.png" height="20px" width="20px">
			                			</c:otherwise>
			               			</c:choose>
				                </td>
		                		<td bgcolor="LightYellow" align="center"><c:out value='${jokeReview.reviewRemark}'/></td>
		                		<td bgcolor="LightYellow" align="center"><c:out value='${jokeReview.reviewDate}'/></td>
		                		<td bgcolor="LightYellow" align="center"><c:out value="${userDAO.getUser(jokeReview.reviewUserId).userName}" /></td>
		                		<c:choose>
		                    		<c:when test="${sessionUserId == 1 || sessionUserId == jokeReview.reviewUserId}">
				                		<td bgcolor="LightYellow" align="center">
				                			<a href="editReview?jokeId=<c:out value='${jokeReview.reviewJokeId}'/>&userId=<c:out value='${jokeReview.reviewUserId}'/>"><img src="images/editReview.png"  title="edit review" height="25%" width="25%"></a>
				                		</td>
				                		<td bgcolor="LightYellow" align="center">
				                			<a href="removeReview?jokeId=<c:out value='${jokeReview.reviewJokeId}'/>&userId=<c:out value='${jokeReview.reviewUserId}'/>"><img src="images/removeReview.png"  title="remove review" height="25%" width="25%"></a>
				                		</td>
			                		</c:when>
			                		<c:otherwise>
			                			<td bgcolor="LightYellow" align="center"/>
			                			<td bgcolor="LightYellow" align="center"/>
			                		</c:otherwise>
		                		</c:choose>
		                	</tr>
	                	</c:if>
	            	</c:forEach>
	            	<tr><td height="1"/></tr>
            	</c:forEach>
	        </table>
         </c:if>
    </div>
</body>
</html>