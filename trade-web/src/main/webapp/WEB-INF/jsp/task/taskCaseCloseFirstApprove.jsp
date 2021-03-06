<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>结案审核</title>
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
</script>
<style type="text/css">
.radio.radio-inline > label{
	margin-left:10px;}
.radio.radio-inline > input{
	margin-left:10px;}
.checkbox.checkbox-inline > div{
	margin-left:25px;}
.checkbox.checkbox-inline > input{
	margin-left:20px;}

</style>
<content tag="pagetitle">结案审核</content>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="wrapper wrapper-content">
        <div class="row animated fadeInDown">
            <div class="scroll_box fadeInDown animated marginbot">
            <div class="row wrapper white-bg new-heading"  id="serviceFlow">
             <div class="pl10">
                 <h2 class="newtitle-big">
                                                       结案归档审核
                    </h2>
                <div class="mt20">
                    <button type="button" class="btn btn-icon btn-blue mr5">
                        <i class="iconfont icon">&#xe600;</i> <a href="${ctx }/case/myCaseList">在途单列表</a>
                    </button>
                    <button type="button" class="btn btn-icon btn-blue mr5">
                        <i class="iconfont icon">&#xe63f;</i><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a>
                    </button>
                </div>
             </div>
        </div>

        <div class="ibox-content border-bottom clearfix space_box noborder">           
        <div>
            <h2 class="newtitle">填写任务信息</h2>
            <div class="form_list">
                <div class="marinfo">
                <form method="get" class="form-horizontal" id="lamform">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<%-- 设置审批类型 --%>
					<input type="hidden" id="approveType" name="approveType" value="${approveType }">
					<input type="hidden" id="lapPkid" name="lapPkid" value="${toApproveRecord.pkid }">
					<input type="hidden" id="operator" name="operator" value="${operator }">
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">审批结果</label>
                            <div class="controls ">
                               <label class="radio inline"> <input type="radio"value="true" checked="checked" id="optionsRadios1"  name="CaseCloseFirstCheck">审批通过
                                </label> <label class="radio inline"> <input type="radio"  value="false" id="optionsRadios2" name="CaseCloseFirstCheck">审批不通过
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">备注</label>
                            <input class="input_type optionwid" id="CaseCloseFirstCheck_response" name="CaseCloseFirstCheck_response" value="" >
                        </div>
                    </div>
                    </form>
                </div>
            </div>
        </div>
            <div class="clearfix" id="aboutInfo">
						<h2 class="newtitle title-mark">审批记录</h2>
						<div class="jqGrid_wrapper">
							<table id="reminder_list"></table>
							<div id="pager_list_1"></div>
						</div>
					</div>
					<!-- 案件备注信息 -->
				  <div id="caseCommentList" class="view-content"></div>
			      <div class="form-btn">
                  <div class="text-center">
                   <!--  <button  class="btn btn-success btn-space" onclick="save(false)">保存</button> -->
                     <button class="btn btn-success btn-space" onclick="save()">提交</button>
                </div>
                </div>
            </div>
        </div>
    </div>
	<content tag="local_script"> 
	<!-- Peity --> 
	<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> 
	<!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
	<script src="<c:url value='/transjs/task/loanlostApprove.js' />"></script>
	<script src="<c:url value='/transjs/task/showAttachment.js' />"></script> 
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>

	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/stickUp.js' />"></script>
		<!-- 改版引入的新的js文件 -->
    <script src="<c:url value='/js/common/textarea.js' />"></script> 
	<script src="<c:url value='/js/common/common.js' />"></script>
	<!--公共信息-->
	<script	src="<c:url value='/js/trunk/case/caseBaseInfo.js' />" type="text/javascript"></script>
	<script>
		/**提交数据*/
		function submit() {
			save();
		}
	
		/**保存数据*/
		function save() {
			var jsonData = $("#lamform").serializeArray();
			
			var url = "${ctx}/task/caselostApprove/caselostApproveFirst";
			
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				data : jsonData,
	   		    beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
                complete: function() {  
   
                     if(status=='timeout'){//超时,status还有success,error等值的情况
    	          	  Modal.alert(
    				  {
    				    msg:"抱歉，系统处理超时。"
    				  });
    		  		 $(".btn-primary").one("click",function(){
    		  				parent.$.fancybox.close();
    		  			});	 
    		                } 
    		            } , 
				success : function(data) {
					if(data){
						window.wxc.success("提交成功！",{"wxcOk":function(){
							if(window.opener)
						     {
								 window.close();
								 window.opener.callback();
						     } else {
						    	 window.location.href = "${ctx }/task/myTaskList";
						     }
						}});
					}
				},
				error : function(errors) {
					window.wxc.error("数据提交出错");
				}
			});
		}
	</script> 
	</content>
</body>


</html>