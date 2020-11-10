<%@ page errorPage="/WEB-INF/jsp/error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="https://www.concepting.com.br/framework/ui/components" prefix="concepting"%>

<table class="footer">
	<tr>
		<td align="CENTER">
			${loginSession.systemModule.copyright}
		</td>
	</tr>
</table>
