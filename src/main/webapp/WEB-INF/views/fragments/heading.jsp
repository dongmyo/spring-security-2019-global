<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
    <!-- TODO : #8 실습 - spring security taglib를 이용해서 authentication 객체의 principal.username을 출력하세요. -->
    <sec:authentication property="principal.username" />님,&nbsp;
    <a href="<c:url value='/logout' />">Logout</a>
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
    <a href="/redirect-index">Login</a>
</sec:authorize>
<br />
<a href="/">Go to Main</a>
<br />
<br />
<br />
