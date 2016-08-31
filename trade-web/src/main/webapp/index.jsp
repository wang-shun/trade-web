<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.aist.common.utils.SpringUtils" %>
<%@ page import="com.aist.uam.auth.remote.UamSessionService" %> 
<%@ page import="com.aist.uam.permission.remote.UamPermissionService" %> 
<%@ page import="com.aist.uam.auth.remote.vo.SessionUser" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% 
	UamSessionService uamSessionService = SpringUtils.getBean(UamSessionService.class);
	UamPermissionService uamPermissionService = SpringUtils.getBean(UamPermissionService.class);
	
	SessionUser currentUser = uamSessionService.getSessionUser();
	String url = uamPermissionService.getResourceUrlByJobId(currentUser.getServiceJobId());
	
	if(url != null){
		response.sendRedirect(url);
	}
	else {
		response.sendRedirect("workspace/dashboard");
	}
%>