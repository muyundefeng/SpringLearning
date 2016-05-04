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
		<form:form commandName="nijia" action="Nijia_saving" method="post" enctype="multipart/form-data">
			<fieldset>
				<legend> add a nijia</legend>
			</fieldset>
			<p>
				<label for="name">Nijia name:</label>
				<form:input path="name" id="name"/>
			</p>
			<p>
				<label for="category">Nijia category:</label>
				<form:input path="category" id="category"/>
			</p>
			<p>
				<label for="birthdate">Nijia birthdate:</label>
				<form:input path="birthDate" id="birthDate"/>
			</p>
			<p>
				<label for="image">Nijia image:</label>
				<input type="file" name="images[0]"/>
			</p>
			<p id="buttons">
				<input id="reset" type="reset" tabindex="4">
				<input id="submit" value="add nijia" tabindex="5" type="submit">
			</p>
		</form:form>
	</div>
</body>
</html>