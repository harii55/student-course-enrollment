<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout pageTitle="Enrollments">
    <div class="actions-bar">
        <a href="${pageContext.request.contextPath}/enrollments/new" class="btn btn-primary">New Enrollment</a>
    </div>

    <table class="data-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Student</th>
                <th>Course</th>
                <th>Enrolled On</th>
                <th>Grade</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="enrollment" items="${enrollments}">
                <tr>
                    <td>${enrollment.id}</td>
                    <td>${enrollment.student.fullName}</td>
                    <td>${enrollment.course.title}</td>
                    <td>${enrollment.enrollmentDate}</td>
                    <td>${empty enrollment.grade ? '-' : enrollment.grade}</td>
                    <td class="action-cell">
                        <a href="${pageContext.request.contextPath}/enrollments/${enrollment.id}/grade" class="btn btn-sm btn-warning">Update Grade</a>
                        <form method="post" action="${pageContext.request.contextPath}/enrollments/unenroll"
                              onsubmit="return confirm('Remove this enrollment?')" style="display:inline">
                            <input type="hidden" name="studentId" value="${enrollment.student.id}"/>
                            <input type="hidden" name="courseId" value="${enrollment.course.id}"/>
                            <button type="submit" class="btn btn-sm btn-danger">Unenroll</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty enrollments}">
                <tr><td colspan="6" class="empty-state">No enrollments found.</td></tr>
            </c:if>
        </tbody>
    </table>
</layout:layout>
