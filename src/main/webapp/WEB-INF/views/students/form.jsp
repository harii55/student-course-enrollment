<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="layout" %>

<c:set var="isEdit" value="${not empty student.id}"/>
<c:set var="formTitle" value="${isEdit ? 'Edit Student' : 'Add Student'}"/>

<layout:layout pageTitle="${formTitle}">
    <form:form method="post"
               action="${pageContext.request.contextPath}/students${isEdit ? '/' += student.id : ''}"
               modelAttribute="student"
               class="data-form">

        <div class="form-group">
            <form:label path="firstName">First Name</form:label>
            <form:input path="firstName" class="form-control"/>
            <form:errors path="firstName" cssClass="field-error"/>
        </div>

        <div class="form-group">
            <form:label path="lastName">Last Name</form:label>
            <form:input path="lastName" class="form-control"/>
            <form:errors path="lastName" cssClass="field-error"/>
        </div>

        <div class="form-group">
            <form:label path="email">Email</form:label>
            <form:input path="email" type="email" class="form-control"/>
            <form:errors path="email" cssClass="field-error"/>
        </div>

        <div class="form-group">
            <form:label path="dateOfBirth">Date of Birth</form:label>
            <form:input path="dateOfBirth" type="date" class="form-control"/>
            <form:errors path="dateOfBirth" cssClass="field-error"/>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">${isEdit ? 'Update' : 'Create'}</button>
            <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Cancel</a>
        </div>
    </form:form>
</layout:layout>
