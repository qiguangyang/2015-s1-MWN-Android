<%@ taglib prefix="s" uri="/struts-tags"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="loginCheck.jsp" />
<html>
<head>
    <title>WatchDog Server Demo</title>
    <style>
	table.list
	{
		border-collapse:collapse;
		width: 40%;
	}
	table.list, table.list td, table.list th
	{
		border:1px solid gray;
		padding: 5px;
	}
	</style>
</head>
<body>
 
<h2>WatchDog Server Demo</h2>
 
<s:form method="post" action="add">
    <table>
	    <tr>
	        <td><s:textfield key="label.firstname" name="patient.firstname"/></td> 
	    </tr>
	    <tr>
	        <td><s:textfield key="label.lastname" name="patient.lastname"/></td>
	    </tr>
	    <tr>
	        <td><s:textfield key="label.emercontact" name="patient.contact"/></td>
	    </tr>
	    <tr>
	        <td><s:textfield key="label.forbarea" name="patient.forbidden"/></td>
	    </tr>
	    <tr>
	        <td>
	        	<s:submit key="label.add"></s:submit>
	        </td>
	    </tr>
	</table> 
</s:form>
 
     
<h3>Patients</h3>
<c:if  test="${!empty patients}">
	<table class="list">
		<tr>
		    <th align="left">Name</th>
		    <th align="left">Emergency Contact</th>
		    <th align="left">Forbidden Area</th>
		    <th align="left">Actions</th>
		</tr>
		<c:forEach items="${patients}" var="pat">
		    <tr>
		        <td>${pat.firstname} ${pat.lastname}</td>
		        <td>${pat.contact}</td>
		        <td>${pat.forbidden}</td>
		        <td><a href="delete/${pat.id}">delete</a></td>
		    </tr>
		</c:forEach>
	</table>
</c:if>
 
</body>
</html>