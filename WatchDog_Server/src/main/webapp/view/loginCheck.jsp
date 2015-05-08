<%@ taglib prefix="s" uri="/struts-tags"%> 
<s:if test="#session.login != 'true'">
<%  response.sendRedirect("/WatchDog_Server/login.jsp");%> 
</s:if>