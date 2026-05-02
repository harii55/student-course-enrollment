<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout pageTitle="Course Details">
    <div class="detail-card">
        <div class="detail-row"><span class="detail-label">Title:</span> <span>${course.title}</span></div>
        <div class="detail-row"><span class="detail-label">Code:</span> <span>${course.code}</span></div>
        <div class="detail-row"><span class="detail-label">Credits:</span> <span>${course.credits}</span></div>
        <div class="detail-row"><span class="detail-label">Description:</span> <span>${course.description}</span></div>
    </div>

    <div class="section-header">
        <h2>Enrolled Students</h2>
    </div>

    <table class="data-table">
        <thead>
            <tr>
                <th>Student Name</th>
                <th>Email</th>
                <th>Enrolled On</th>
                <th>Grade</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="enrollment" items="${enrollments}">
                <tr>
                    <td>${enrollment.student.fullName}</td>
                    <td>${enrollment.student.email}</td>
                    <td>${enrollment.enrollmentDate}</td>
                    <td>${empty enrollment.grade ? '-' : enrollment.grade}</td>
                    <td class="action-cell">
                        <form method="post" action="${pageContext.request.contextPath}/enrollments/unenroll"
                              onsubmit="return confirm('Remove this enrollment?')" style="display:inline">
                            <input type="hidden" name="studentId" value="${enrollment.student.id}"/>
                            <input type="hidden" name="courseId" value="${course.id}"/>
                            <button type="submit" class="btn btn-sm btn-danger">Unenroll</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty enrollments}">
                <tr><td colspan="5" class="empty-state">No students enrolled.</td></tr>
            </c:if>
        </tbody>
    </table>

    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/courses/${course.id}/edit" class="btn btn-warning">Edit Course</a>
        <a href="${pageContext.request.contextPath}/courses" class="btn btn-secondary">Back to List</a>
    </div>
</layout:layout>
