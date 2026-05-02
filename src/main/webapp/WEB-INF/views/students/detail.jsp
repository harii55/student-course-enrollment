<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout pageTitle="Student Details">
    <div class="detail-card">
        <div class="detail-row"><span class="detail-label">Full Name:</span> <span>${student.fullName}</span></div>
        <div class="detail-row"><span class="detail-label">Email:</span> <span>${student.email}</span></div>
        <div class="detail-row"><span class="detail-label">Date of Birth:</span> <span>${student.dateOfBirth}</span></div>
    </div>

    <div class="section-header">
        <h2>Enrolled Courses</h2>
        <a href="${pageContext.request.contextPath}/enrollments/new?studentId=${student.id}" class="btn btn-primary">Enroll in a Course</a>
    </div>

    <table class="data-table">
        <thead>
            <tr>
                <th>Course</th>
                <th>Code</th>
                <th>Credits</th>
                <th>Enrolled On</th>
                <th>Grade</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="enrollment" items="${enrollments}">
                <tr>
                    <td>${enrollment.course.title}</td>
                    <td>${enrollment.course.code}</td>
                    <td>${enrollment.course.credits}</td>
                    <td>${enrollment.enrollmentDate}</td>
                    <td>${empty enrollment.grade ? '-' : enrollment.grade}</td>
                    <td class="action-cell">
                        <form method="post" action="${pageContext.request.contextPath}/enrollments/unenroll"
                              onsubmit="return confirm('Remove this enrollment?')" style="display:inline">
                            <input type="hidden" name="studentId" value="${student.id}"/>
                            <input type="hidden" name="courseId" value="${enrollment.course.id}"/>
                            <button type="submit" class="btn btn-sm btn-danger">Unenroll</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty enrollments}">
                <tr><td colspan="6" class="empty-state">Not enrolled in any courses.</td></tr>
            </c:if>
        </tbody>
    </table>

    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/students/${student.id}/edit" class="btn btn-warning">Edit Student</a>
        <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Back to List</a>
    </div>
</layout:layout>
