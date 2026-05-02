<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<layout:layout pageTitle="Update Grade">
    <div class="detail-card">
        <div class="detail-row"><span class="detail-label">Student:</span> <span>${enrollment.student.fullName}</span></div>
        <div class="detail-row"><span class="detail-label">Course:</span> <span>${enrollment.course.title}</span></div>
        <div class="detail-row"><span class="detail-label">Current Grade:</span> <span>${empty enrollment.grade ? 'Not graded' : enrollment.grade}</span></div>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/enrollments/${enrollment.id}/grade" class="data-form">
        <div class="form-group">
            <label for="grade">New Grade</label>
            <input type="text" id="grade" name="grade" value="${enrollment.grade}"
                   maxlength="5" placeholder="e.g. A, B+, C-" class="form-control" required/>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Save Grade</button>
            <a href="${pageContext.request.contextPath}/enrollments" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</layout:layout>
