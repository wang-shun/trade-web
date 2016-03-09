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
		  background-image:url(../img/a5.png);
	
}
</style>
<script type="text/javascript">
  	 function imgLoad(img){
	   		 img.parentNode.style.backgroundImage="url("+img.src+")";
	   	 }
</script>
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
	                    			<img alt="image" class="himg"  style="margin:auto" src="${imgUrl }" onload="javascript:imgLoad(this)">
	                    		</span>

	                    	
                        	<%-- <img alt="image" class="img-circle m-t-xs img-responsive"  style="margin:auto" src="${imgUrl }"> --%>
	                        <!-- <div class="m-t-xs font-bold">Graphics designer</div> -->
	                    </div>
	                </div>
	                <div class="col-sm-8">
	                    <h6><strong>基础信息</strong></h6>
	                    <hr>
	                    <label class="col-sm-3 control-label">姓名：</label>
	                    <p>${sessionUser.realName == null ? "-":sessionUser.realName}</p>
	                    <hr>
	                   <%--  <label class="col-sm-3 control-label">生日：</label>
	                    <p>${sessionUser.realName }</p>
	                    <hr> --%>
	                    <%-- <label class="col-sm-3 control-label">性别：</label>
	                    <p>
	                    	<c:choose>
	                    		<c:when test="${sessionUser.sex == null or sessionUser.sex == '' or sessionUser.sex == 'null'}">
	                    		-
	                    		</c:when>
	                    		<c:when test="${sessionUser.sex == '1' or sessionUser.sex == '0'}">
	                    		${sessionUser.sex == 1 ? "男" : "女" }
	                    		</c:when>
	                    		<c:otherwise>
	                    		${sessionUser.sex}
	                    		</c:otherwise>
	                    	</c:choose>
	                    </p>
	                    <hr> --%>
	                    <label class="col-sm-3 control-label">Email：</label>
	                    <p>${sessionUser.email == null or sessionUser.email == ''?"-":sessionUser.email}</p>
	                    <hr>
	                    <label class="col-sm-3 control-label">手机号：</label>
	                    <p>${sessionUser.mobile == null or sessionUser.mobile == ''?"-":sessionUser.mobile}</p>
	                    <hr>
	                    <h6><strong>岗位信息</strong></h6>
	                    <hr>
	                    <label class="col-sm-3 control-label">登录名：</label>
	                    <p>${sessionUser.username == null or sessionUser.username == ''?"-":sessionUser.username}</p>
	                    <hr>
	                    <label class="col-sm-3 control-label">员工工号：</label>
	                    <p>${sessionUser.employeeCode == null or sessionUser.employeeCode == ''?"-":sessionUser.employeeCode}</p>
	                    <hr>
	                    <%-- <label class="col-sm-3 control-label">员工类型：</label>
	                    <p>${sessionUser.employeeType}</p>
	                    <hr> --%>
	                    <label class="col-sm-3 control-label">用户归属部门：</label>
	                    <p>${sessionUser.serviceDepName == null or sessionUser.serviceDepName == '' ? "-":sessionUser.serviceDepName}</p>
	                    <hr>
	                    <%-- <label class="col-sm-3 control-label">行政归属组织：</label>
	                    <p>${sessionUser.serviceCompanyName == null or sessionUser.serviceCompanyName == '' ? "-":sessionUser.serviceCompanyName}</p>
	                    <hr> --%>
	                    <label class="col-sm-3 control-label">行政职务：</label>
	                    <p>${sessionUser.serviceJobName == null or sessionUser.serviceJobName == '' ? "-":sessionUser.serviceJobName}</p>
	                    <hr>
	                  <%--   <label class="col-sm-3 control-label">备注：</label>
	                    <p>${sessionUser.sex == null or sessionUser.email == ''?"-":sessionUser.sex}</p>
	                    <hr> --%>
	                </div>
	                <div class="clearfix"></div>
	            </div>
	        </div>
		</div>
	</div>
	<content tag="local_script"> 
	<script>
		/* $(document).ready(function() {
					
		}); */
	</script> 
	</content>
</body>


</html>