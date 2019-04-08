<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Welcome</title>
	
	<script>
		function showQueryFields()
		{
			var obj = document.getElementById("Queries");
			var value = obj.options[obj.selectedIndex].value;
			
			document.getElementById('tagX').style.display= 'none';
			document.getElementById('tagY').style.display= 'none';
			document.getElementById('userX').style.display= 'none';
			document.getElementById('userY').style.display= 'none';
			
			if (value=="Query2")
			{
				document.getElementById('tagX').style.display= '';
				document.getElementById('tagY').style.display= '';
			}
			else if (value=="Query4")
			{
				document.getElementById('userX').style.display= '';
			}
			else if (value=="Query6")
			{
				document.getElementById('userX').style.display= '';
				document.getElementById('userY').style.display= '';
			}
		}
	</script>
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
	</table>
    
    <!-- select and run Queries -->
    <div align="center">
        <form action="runQuery" method="post">
        	<table>
        		<tr><td>Select your query:</td></tr>
	        	<tr>
	        		<td>
						<select required="required" name="Queries" id="Queries" autofocus="autofocus" onfocus="showQueryFields()" onchange="showQueryFields()">
							<option value="Query2" <c:out value="${Query2}" />>Query2: List users with two jokes having tag X, Y on same day</option>
							<option value="Query4" <c:out value="${Query4}" />>Query4: List jokes posted by user X with excellent/good comments</option>
							<option value="Query5" <c:out value="${Query5}" />>Query5: List users with most jokes since 3/1/2018</option>
							<option value="Query6" <c:out value="${Query6}" />>Query6: List users friend with both users X, Y</option>
							<option value="Query7" <c:out value="${Query7}" />>Query7: List users never posted excellent jokes</option>
							<option value="Query8" <c:out value="${Query8}" />>Query8: List users never posted poor reviews</option>
							<option value="Query9" <c:out value="${Query9}" />>Query9: List users posted only poor reviews</option>
							<option value="Query10" <c:out value="${Query10}" />>Query10: List users posted no poor-reviewed jokes</option>
							<option value="Query11" <c:out value="${Query11}" />>Query11: List user pair (A, B) always gave each other excellent reviews</option>
						</select>
					</td>
				</tr>
				<tr id="tagX" style="display:none">
					<td>Select tag X:<br/>
					<input type="text" placeholder="tag X" name="tagX" size="25" value="<c:out value='${tagX}'/>"/></td>
				</tr>
				<tr id="tagY" style="display:none">
					<td>Select tag Y:<br/>
					<input type="text" placeholder="tag Y" name="tagY" size="25" value="<c:out value='${tagY}'/>"/></td>
				</tr>
				<tr id="userX" style="display:none">
					<td>Select user X:<br/>
						<select required="required" name="UserX" id="UserX" autofocus="autofocus">
							<c:forEach var="user" items="${dropDownUserList}">
								<c:choose>
									<c:when test="${user.userId == selectedUserX}">
										<option value="<c:out value="${user.userId}" />" selected="selected"><c:out value="${user.userName}" /></option>
									</c:when>
									<c:otherwise>
										<option value="<c:out value="${user.userId}" />"><c:out value="${user.userName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="userY" style="display:none">
					<td>Select user Y:<br/>
						<select required="required" name="UserY" id="UserY" autofocus="autofocus">
							<c:forEach var="user" items="${dropDownUserList}">
								<c:choose>
									<c:when test="${user.userId == selectedUserY}">
										<option value="<c:out value="${user.userId}" />" selected="selected"><c:out value="${user.userName}" /></option>
									</c:when>
									<c:otherwise>
										<option value="<c:out value="${user.userId}" />"><c:out value="${user.userName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
				</tr>
        		<tr><td><input type="submit" value="run!" /></td></tr>
				<tr>
					<!-- msg on top of the tables -->
					<td>
						<h2 style="text-align:center">
							<br>
						    <c:if test="${message != null && color != null}">
						        <font color=<c:out value="${color}"></c:out>><c:out value="${message}"></c:out></font>
						    </c:if>
						</h2>
					</td>
				</tr>
        	</table>
        </form>
    </div>
    
    <!-- listUser table (only root) -->
	<div align="center">
		<table>
			<tr>
				<td>
			    	<c:if test="${userList != null}">
				        <table>
				            <tr bgcolor="LightGray">
				            	<th width="20">ID</th>
				                <th width="100">User Name</th>
				            </tr>
				            <c:forEach var="user" items="${userList}">
				                <tr bgcolor="Snow">
				                	<td align="center"><c:out value="${user.userId}" /></td>
				                    <td align="center"><c:out value="${user.userName}" /></td>
				                </tr>
				            </c:forEach>
				        </table>
			        </c:if>
		        </td>
		        <td>
			        <c:if test="${userList2 != null}">
				        <table>
				            <tr bgcolor="LightGray">
				            	<th width="20">ID</th>
				                <th width="100">User Name</th>
				            </tr>
				            <c:forEach var="user" items="${userList2}">
				                <tr bgcolor="Snow">
				                	<td align="center"><c:out value="${user.userId}" /></td>
				                    <td align="center"><c:out value="${user.userName}" /></td>
				                </tr>
				            </c:forEach>
				        </table>
			        </c:if>
		        </td>
	        </tr>
        </table>
    </div>
    
    <!-- List jokes -->
	<div align="center">
		<c:if test="${jokeList != null}">
	        <table>
	            <tr bgcolor="LightGray">
	                <th width="100">ID</th>
	                <th width="100">Title</th>
	                <th width="100">Text</th>
	            </tr>
	            <c:forEach var="joke" items="${jokeList}">
	                <tr bgcolor="AliceBlue">
	                    <td align="center"><c:out value="${joke.jokeId}" /></td>
	                    <td align="center"><c:out value="${joke.jokeTitle}" /></td>
	                    <td align="center"><c:out value="${joke.jokeText}" /></td>
                    </tr>
               </c:forEach>
	        </table>
         </c:if>
    </div>
</body>
</html>