<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="edu.uwt.cloud.youtubeapi.*" %>
<%@ page import="com.google.api.services.*" %>


<html>
  <body>
	<center><%@ include file="YoutubeIndex.jsp" %></center>    
	<br/><br/>	
	
	<!-- 1. The <iframe> (and video player) will replace this <div> tag. -->
    <div id="player"></div>
	<jsp:useBean id="searchYoutube" class="edu.uwt.cloud.youtubeapi.SearchYoutube">
		<jsp:setProperty name="searchYoutube" property="searchKeyword" value="${param.searchKeyword}"/>
	</jsp:useBean>
	
	<c:set var="searchResult" value="${searchYoutube.searchResult}"/>
	<div align="center">
		<table border="1"  style="border-collapse:collapse;">
			<c:choose>
				<c:when test="${empty searchResult}">
				
					<h1>Sorry. There are no results matching for' ${param.searchKeyword} '</h1>
					
				</c:when>
				
				<c:otherwise>
					<h1>Top 10 Results for ' ${param.searchKeyword} '</h1>
					Click on the Title to watch the video.
					<tr>
						<th>Video</th>
						<th>Video Id</th>
						<th >Title</th>
					</tr>
				</c:otherwise>
			</c:choose>
				
			<c:forEach var="option" items="${searchResult}">
				<tr>
						<td><img src="${option.snippet.thumbnails['default'].url}">	</td>
						<td><c:out value="${option.id.videoId}"/></td>
						<td align="right"><a href="PlayVideo.jsp?searchKeyword=${param.searchKeyword}&videoId=${option.id.videoId}"><c:out value="${option.snippet.title}"/></a></td>					
				</tr>				
			</c:forEach>
		
		</table>
	</div>
  </body>
</html>