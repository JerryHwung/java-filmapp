<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:forEach items="${films}" var="film">
	${film.id}|${film.title}|${film.year}|${film.director}|${film.stars}|${film.review}
	<%out.println();%>
</c:forEach>