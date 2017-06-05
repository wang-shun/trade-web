<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<style type="text/css">
hr {
    margin-top: 10px;
    margin-bottom: 10px;
}
.userHead{
	width: 250px;
		  height: 250px;
		  display: inline-block;
		  border-radius: 50%;
		  background-size: 250px 333.33px;
		  vertical-align: middle;	
}
</style>
</head>
<body>

	<div class="row">
		<div class="ibox-title">
			<h5>个人信息</h5>
		</div>
		<div class="ibox-content">
			<div class="col-lg-10" style="margin-right: auto; margin-left: auto; float:none;">
	            <div class="contact-box">
	                <div class="col-sm-4">
	                    <div class="text-center" style="margin-top:90px;">
	                    		<span class="userHead">
	                    			<img alt="image" class="himg"  style="margin:auto" src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/${SESSION_USER.employeeCode}.jpg" 
	                    			onerror="this.src='${ctx}/img/a5.png'" >
	                    		</span>
	                    </div>
	                </div>
	                <div class="col-sm-8">
	                    <h6><strong>基础信息</strong></h6>
	                    <hr>
	                    <label class="col-sm-3 control-label">姓名：</label>
	                    <p>${SESSION_USER.realName == null ? "-":SESSION_USER.realName}</p>
	                    <hr>
	                    <label class="col-sm-3 control-label">Email：</label>
	                    <p>${SESSION_USER.email == null or SESSION_USER.email == ''?"-":SESSION_USER.email}</p>
	                    <hr>
	                    <label class="col-sm-3 control-label">手机号：</label>
	                    <p>${SESSION_USER.mobile == null or SESSION_USER.mobile == ''?"-":SESSION_USER.mobile}</p>
	                    <hr>
	                    <h6><strong>岗位信息</strong></h6>
	                    <hr>
	                    <label class="col-sm-3 control-label">登录名：</label>
	                    <p>${SESSION_USER.username == null or SESSION_USER.username == ''?"-":SESSION_USER.username}</p>
	                    <hr>
	                    <label class="col-sm-3 control-label">员工工号：</label>
	                    <p>${SESSION_USER.employeeCode == null or SESSION_USER.employeeCode == ''?"-":SESSION_USER.employeeCode}</p>
	                    <hr>
	                    <%-- <label class="col-sm-3 control-label">员工类型：</label>
	                    <p>${SESSION_USER.employeeType}</p>
	                    <hr> --%>
	                    <label class="col-sm-3 control-label">用户归属部门：</label>
	                    <p>${SESSION_USER.serviceDepName == null or SESSION_USER.serviceDepName == '' ? "-":SESSION_USER.serviceDepName}</p>
	                    <hr>
	                    <label class="col-sm-3 control-label">行政职务：</label>
	                    <p>${SESSION_USER.serviceJobName == null or SESSION_USER.serviceJobName == '' ? "-":SESSION_USER.serviceJobName}</p>
	                    <hr>
	                </div>
	                <div class="clearfix"></div>
	            </div>
	        </div>
		</div>
	</div>
	<content tag="local_script"> 
	</content>
</body>
</html>