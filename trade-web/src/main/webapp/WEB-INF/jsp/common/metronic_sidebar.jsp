<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page import="com.aist.uam.permission.remote.vo.Menu" %>
<%@ page import="com.centaline.sales.workbench.web.MenuConstants" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<% 
	Menu menu = MenuConstants.getMenu();
	request.setAttribute("menuVO", menu.getChildren());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- BEGIN SIDEBAR -->

<div class="page-sidebar nav-collapse collapse">

	<!-- BEGIN SIDEBAR MENU -->
	<ul class="page-sidebar-menu">

		<li>
			<!-- BEGIN SIDEBAR TOGGLER BUTTON -->

			<div class="sidebar-toggler hidden-phone"></div> <!-- BEGIN SIDEBAR TOGGLER BUTTON -->

		</li>

		<li>
			<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->

			<form class="sidebar-search">

				<div class="input-box">

					<a href="javascript:;" class="remove"></a> <input type="text"
						placeholder="Search..." /> <input type="button" class="submit"
						value=" " />

				</div>

			</form> <!-- END RESPONSIVE QUICK SEARCH FORM -->

		</li>
		
		<c:forEach items="${menuVO}" var="l1_menu" >
		<li class="">
				<a href="javascript:;"> <i class="${l1_menu.icon}"></i>
					<span class="title">${l1_menu.menuName}</span><span class="arrow"></span>
				</a>
			<ul class="sub-menu">
				<c:forEach items="${l1_menu.children}" var="l2_menu" >
				<shiro:hasPermission name="${l2_menu.resource.resourceCode}">
					<li><a href="${ctx}${l2_menu.url}" <c:if test='${l2_menu.target=="BLANK"}'> target="_blank"</c:if>><i class="${l2_menu.icon}"></i>  
					${l2_menu.menuName}</a></li>
				</shiro:hasPermission>
				</c:forEach>
			</ul>
		</li>
	</c:forEach>
	<li class="ept">
	</li>
</ul>

	<!-- END SIDEBAR MENU -->

</div>

<!-- END SIDEBAR -->
