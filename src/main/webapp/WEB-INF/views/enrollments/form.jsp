<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout pageTitle="New Enrollment">
    <form method="post" action="${pageContext.request.contextPath}/enrollments" class="data-form">

        <div class="form-group">
            <label for="studentId">Student</label>
            <select id="studentId" name="studentId" class="form-control" required>
                <option value="">-- Select Student --</option>
                <c:forEach var="student" items="${students}">
                    <option value="${student.id}">${student.fullName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="courseId">Course</label>
            <select id="courseId" name="courseId" class="form-control" required>
                <option value="">-- Select Course --</option>
                <c:forEach var="course" items="${courses}">
                    <option value="${course.id}">${course.title} (${course.code})</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="enrollmentDate">Enrollment Date</label>
            <input type="date" id="enrollmentDate" name="enrollmentDate"
                   value="${today}" class="form-control" required/>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Enroll</button>
            <a href="${pageContext.request.contextPath}/enrollments" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</layout:layout>
