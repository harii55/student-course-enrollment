<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout pageTitle="Courses">
    <div class="actions-bar">
        <a href="${pageContext.request.contextPath}/courses/new" class="btn btn-primary">Add Course</a>
    </div>

    <table class="data-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Code</th>
                <th>Credits</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="course" items="${courses}">
                <tr>
                    <td>${course.id}</td>
                    <td>${course.title}</td>
                    <td>${course.code}</td>
                    <td>${course.credits}</td>
                    <td class="action-cell">
                        <a href="${pageContext.request.contextPath}/courses/${course.id}" class="btn btn-sm btn-info">View</a>
                        <a href="${pageContext.request.contextPath}/courses/${course.id}/edit" class="btn btn-sm btn-warning">Edit</a>
                        <form method="post" action="${pageContext.request.contextPath}/courses/${course.id}/delete"
                              onsubmit="return confirm('Delete this course?')" style="display:inline">
                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty courses}">
                <tr><td colspan="5" class="empty-state">No courses found.</td></tr>
            </c:if>
        </tbody>
    </table>
</layout:layout>
