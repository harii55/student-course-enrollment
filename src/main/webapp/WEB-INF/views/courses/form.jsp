<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<c:set var="isEdit" value="${not empty course.id}"/>
<c:set var="formTitle" value="${isEdit ? 'Edit Course' : 'Add Course'}"/>

<layout:layout pageTitle="${formTitle}">
    <form:form method="post"
               action="${pageContext.request.contextPath}/courses${isEdit ? '/' += course.id : ''}"
               modelAttribute="course"
               class="data-form">

        <div class="form-group">
            <form:label path="title">Title</form:label>
            <form:input path="title" class="form-control"/>
            <form:errors path="title" cssClass="field-error"/>
        </div>

        <div class="form-group">
            <form:label path="code">Course Code</form:label>
            <form:input path="code" class="form-control"/>
            <form:errors path="code" cssClass="field-error"/>
        </div>

        <div class="form-group">
            <form:label path="description">Description</form:label>
            <form:textarea path="description" rows="3" class="form-control"/>
            <form:errors path="description" cssClass="field-error"/>
        </div>

        <div class="form-group">
            <form:label path="credits">Credits</form:label>
            <form:input path="credits" type="number" min="1" class="form-control"/>
            <form:errors path="credits" cssClass="field-error"/>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">${isEdit ? 'Update' : 'Create'}</button>
            <a href="${pageContext.request.contextPath}/courses" class="btn btn-secondary">Cancel</a>
        </div>
    </form:form>
</layout:layout>
