<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ attribute name="pageTitle" required="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle} - Enrollment System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<nav class="navbar">
    <div class="nav-brand">Enrollment System</div>
    <ul class="nav-links">
        <li><a href="${pageContext.request.contextPath}/students">Students</a></li>
        <li><a href="${pageContext.request.contextPath}/courses">Courses</a></li>
        <li><a href="${pageContext.request.contextPath}/enrollments">Enrollments</a></li>
    </ul>
</nav>

<main class="container">
    <h1 class="page-title">${pageTitle}</h1>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">${errorMessage}</div>
    </c:if>

    <jsp:doBody/>
</main>

</body>
</html>
