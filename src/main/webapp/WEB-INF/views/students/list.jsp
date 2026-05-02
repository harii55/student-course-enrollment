<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout pageTitle="Students">
    <div class="actions-bar">
        <a href="${pageContext.request.contextPath}/students/new" class="btn btn-primary">Add Student</a>
    </div>

    <table class="data-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>Date of Birth</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="student" items="${students}">
                <tr>
                    <td>${student.id}</td>
                    <td>${student.fullName}</td>
                    <td>${student.email}</td>
                    <td>${student.dateOfBirth}</td>
                    <td class="action-cell">
                        <a href="${pageContext.request.contextPath}/students/${student.id}" class="btn btn-sm btn-info">View</a>
                        <a href="${pageContext.request.contextPath}/students/${student.id}/edit" class="btn btn-sm btn-warning">Edit</a>
                        <form method="post" action="${pageContext.request.contextPath}/students/${student.id}/delete"
                              onsubmit="return confirm('Delete this student?')" style="display:inline">
                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty students}">
                <tr><td colspan="5" class="empty-state">No students found.</td></tr>
            </c:if>
        </tbody>
    </table>
</layout:layout>
