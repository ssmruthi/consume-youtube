<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="edu.uwt.cloud.youtubeapi.*" %>
<%@ page import="com.google.api.services.*" %>


<html>
  <body>
  
    <div style="text-align:center">
	 	<h1>  Search Youtube </h1>
	 	
		<form action="SearchVideo.jsp" method="get">
			Enter keyword : <input type="text" name="searchKeyword"/>
		 	<input type="submit" value="Submit" />
		</form>
	</div>
	</body>	
 
</html>