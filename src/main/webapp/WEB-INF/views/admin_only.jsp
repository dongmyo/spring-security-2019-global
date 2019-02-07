<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- TODO : #3 view template에도 ADMIN만 접근 가능하다고 써 있음 -->
<html>
<head lang="ko">
    <meta charset="UTF-8">
    <title>Admin Only</title>
</head>
<body>
<h1>Admin Only</h1>

<b>Only administrators can come in.</b>
because something crucial is here ...<br />
<br />
<br />
kinda ${data}

</body>
</html>
