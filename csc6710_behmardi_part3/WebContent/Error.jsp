<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Error</title>
</head>
<body>
	<div align="center">
		<table>
		<col width="300">
		<col width="300">
		<col width="1800">
		<col width="300">
		<col width="300">
        	<tr align="center">
        		<td><a href="goBack"><img src="images/backward.png" title="go back" height="70px" width="70px"></a></td>
        		<td/>
        		<td>
        			<table>
        				<tr>
	        				<td><h1><font color="red">Error</font></h1></td>
	        				<td><img src="images/error.png" height="30px" width="30px"></td>
        				</tr>
       				</table>
   				</td>
        		<td/>
        		<td/>
        	</tr>
        	<tr align="center">
	        	<td/>
	        	<td/>
        		<td><h2><font color="red">${errorMessage}</font></h2></td>
        		<td/>
        		<td/>
        	</tr>
        </table>
	</div>
</body>
</html>