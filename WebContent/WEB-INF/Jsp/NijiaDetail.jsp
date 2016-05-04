<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
		<p>Nijia name: ${nijia.name}</p>
		<p>Nijia birthdate: ${nijia.birthDate}</p>
		<p>Nijia category: ${nijia.category}</p>
		<ol>
			<p>
				<c:forEach items="${nijia.images}" var="image">
					<li>${image.originalFilename}
					<img width="100" src="<c:url value="/image/"/>${image.originalFilename}"/>
				</c:forEach>
			</p>
		</ol>
	</div>
</body>
</html>