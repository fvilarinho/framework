<%@ page errorPage="/WEB-INF/jsp/error.jsp"%>
<%@ page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="https://www.concepting.com.br/framework/ui/components" prefix="concepting"%>

<table class="header">
	<tr>
		<td width="25%" align="LEFT">
			<concepting:image value="${loginSession.systemModule.logo}" styleClass="logo"/>
		</td>
		<td align="CENTER">
			<concepting:label value="${loginSession.systemModule.title} ${loginSession.systemModule.version}" styleClass="title"/>
		</td>
		<td width="25%" align="RIGHT">
			<concepting:languageSelector width="200"/>
			<concepting:skinSelector width="200"/>
		</td>
	</tr>
</table>
<table class="navigationBar">
	<tr>
		<td>
			<concepting:menuBar/>
		</td>
		<td width="30%" align="RIGHT">
			<fmt:setLocale value="${loginSession.user.loginParameter.language}" scope="session"/>
			<fmt:formatDate value="<%=new Date()%>" dateStyle="full"/>&nbsp;
			<concepting:clock/>&nbsp;
		</td>
		<c:if test="${loginSession != null && loginSession.id != '' && loginSession.active}">
			<td width="15%" align="RIGHT">
				<concepting:loginSessionBox/>
			</td>
		</c:if>
	</tr>
</table>
<concepting:breadCrumb/>