<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String WebContext	=	request.getContextPath();
	if(WebContext==null)
		WebContext="";
	if("/".equals(WebContext))WebContext="";
	
%>
<script type="text/javascript">
	try{
		var productCtx = ctx;
        var arr = productCtx.match(/http:\/\/[a-zA-Z0-9]+\.([a-zA-Z0-9.]+)[:]*[^:^/]*/);
		var domain = arr[1];
		if(domain.indexOf('sh.')==0){
			domain = domain.substr(3);
		}
		document.domain = domain;
 		//document.domain = '${application["etax"].domain}';
 	}catch(e){}
</script>