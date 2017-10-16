<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<!-- 上传相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<!-- bank  select -->
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<%-- <link href="<c:url value='/css/common/input.css' />" rel="stylesheet"> --%>
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">


<script type="text/javascript">
	var ctx = "${ctx}";
	var coworkService = "${firstFollow.coworkService }";
	var teamProperty = "${teamProperty}";
	var caseProperty = "${firstFollow.caseProperty}";
	var cooperationUser = "${firstFollow.cooperationUser}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
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
.product-type span{margin:0 5px 5px 0}
.product-type .selected,.product-type span:hover{border-color:#f8ac59}

#corss_area{padding:0;}
#corss_area select{float:right;height:34px;border-radius:2px;margin-left:20px;}
.pb10{padding-bottom:15px;}
.select_control{
	color: inherit;
}
#dateTime{
	border-radius: 0px;
    height: 30px;
}

#table_list_0 tr th{
		height:30px;
		background-color: #52cdec;
		border:1px solid white;
		text-align: center;
	}
	
	#table_list_0 tr td{
		height:30px;
		background-color: white;
		border:1px solid white;
		text-align: center;
	}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
 <jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>


	
	<div class="">
		<div  class="info_box info_box_two col-sm-12" >
									<span>自办贷款信息</span>
									<div  class="ibox-conn else_conn_two">
										<table style="width:100%">
											<tr style="height:50px">
												<td>流程状态：</td>
												<td>${toSelfAppInfo.status}</td>
												<td>申请人：</td>
												<td>${toSelfAppInfo.realName}</td>
												<td>申请分行：</td>
												<td>${toSelfAppInfo.grpName}</td>
												<td>申请时间：</td>
												<td>${toSelfAppInfo.applyTime}</td>
											</tr>
											
											<tr style="height:50px">
												<td>自办原因：</td>
												<td>1</td>
												<td>自办贷款银行：</td>
												<td>1</td>
											</tr>
											
											
									</table>
									</div>
								</div>

		<div class="ibox-content border-bottom clearfix space_box noborder">

			<form method="get" class="form-horizontal" id="firstFollowform">
			<%--环节编码 --%>
			<input type="hidden" id="partCode" name="partCode" value="${taskitem}"/>
			<!-- 交易单编号 -->
			<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}"/>
			<!-- 流程引擎需要字段 -->
			<input type="hidden" id="taskId" name="taskId" value="${taskId }"/>
			<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"/>

			<input type="hidden" id="selfAppInfoId" value="${toSelfAppInfo.pkid}"/>
			<h2 class="newtitle title-mark">填写审批任务</h2>
			<div class="tab-content">
							
							<div class="tab-pane active fade in" id="settings">
							<table border="1" class="table table_blue table-striped table-bordered table-hover">
									  <tr>
									    <th width="100px" align="center"><span class="star">*</span>审批结果</th>
									    <td width="500px">
									    <label> 
							   			 <input type="radio" value="0"  name="result" checked="checked"/> <span >通过</span>
										</label>
										<label> 
							   			 <input type="radio" value="1"  name="result"/> <span >驳回</span>
										</label>
									</td>
									  </tr>
									  <tr>
									    <th><span class="star">*</span>审批意见</th>
									    <td><textarea name="comment" rows="2" cols="100"></textarea><input type="hidden" name="selfAppInfoId" value="${toSelfAppInfo.pkid}"/></td>
									    
									  </tr>
									  <tr>
									    <th><span class="star">*</span>回访结果</th>
									    <td><textarea name="visitResult" rows="2" cols="100"></textarea></td>
									  </tr>
									  <tr>
									    <th><span class="star">*</span>回访时间</th>
									    <td><div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
	                                                                        <input name="visitTime" id="dateTime" class="input_type yuanwid datatime" type="text" value="" placeholder=""/>
	                                                                    </div></td>
									  </tr>
							</table>
								
								
					<div><span>审批记录</span></div>				
					<div class="form_content" id="content">
						<table id="table_list_0"  width="800" border="1" cellspacing="0" cellpadding="0" >
	                                <tr>
	                                	<th>审批人名称</th>
	                                	<th>审批人级别</th>
	                                	<th>审批时间</th>
	                                	<th>审批结果</th>
	                                	<th>审批意见</th>
	                                </tr>
	                    </table>
							

					
					</div>
					
				</div>
			</div>
			
			</form>

			<div class="form-btn" style="margin-top:50px">
				<div class="text-center">
					<%--<button  class="btn btn-success btn-space">保存</button>--%>
					<button class="btn btn-success btn-space" onclick="closeWin()" >关闭</button>
					<button class="btn btn-success btn-space" onclick="submit()">提交</button>
				</div>
			</div>
		</div>

	</div>

<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<content tag="local_script"> 
	<!-- jqGrid -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
	<script src="<c:url value='/transjs/task/loanlostApprove.js' />"></script>
	<script src="<c:url value='/transjs/task/showAttachment.js' />"></script> 
	<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
	<!-- Custom and plugin javascript -->
	<script	src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> 
	<script src="<c:url value='/js/trunk/case/caseBaseInfo.js'/>"></script>

	<!-- Data picker -->
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>

    <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<!-- bank select -->
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
	
	<script src="<c:url value='/transjs/task/follow.pic.list.js' />"></script>
	<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
		<!-- Data picker -->
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	
	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	
	<!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script>
	<script src="<c:url value='/js/common/common.js' />"></script>
	
	<script>
		$(document).ready(function(){	
			listtable($("#table_list_0"));
		});
		
	
	
		function  listtable(formId){
			if(formId){
			var appInfoId =  $("#selfAppInfoId").val();
			formId.find("tr").eq(0).siblings("tr").remove();
			$.ajax({
			    url:ctx+"/task/warrantManagerAppro/getAppRecordInfo",
			    method:"post",
			    dataType:"json",
			    data:{appInfoId:appInfoId},
			    success:function(data){
		    		if(data != null){
		    			var list = data;
		    			for (var i = 0; i < list.length; i++) {
		    				formId.find("tr").eq(0).after("<tr><td>"+list[i].applyRealName+"</td><td>"+list[i].level+"</td><td>"+list[i].dealTime+"</td><td>"+list[i].result+"</td><td>"+list[i].comment+"</td></tr>")
						}
		    		}
		    	}
		 });
			
		}
		}
	
		
		/**
		关闭
		*/
		function closeWin(){
			window.opener=null;
			window.close();
		}
		
		/**提交数据*/
		function submit() {

			save(true);
		}

		function checkform(formId){
			if(formId.find("textarea[name='comment']").val() == ""){
				 window.wxc.error("审批意见不能为空");
				 return false;
			}else if(formId.find("textarea[name='visitResult']").val() == ""){
				 window.wxc.error("回访结果不能为空");
				 return false;
			}else if(formId.find("input[name='visitTime']").val() == ""){
				window.wxc.error("回访时间不能为空");
				return false;
			};
			return true;
		}
		
		/**保存数据*/
		function save(b) {
			if(checkform($("#firstFollowform"))){
			
				var jsonData = $("#firstFollowform").serializeArray();
				var 
					url = "${ctx}/task/warrantManagerAppro/submit";
				$.ajax({
					cache : true,
					async : true,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					data : jsonData,
		   		    beforeSend:function(){  
		   				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
		   				$(".blockOverlay").css({'z-index':'9998'});
		               },
		               complete: function() {  
		
		                   $.unblockUI();  
		               	if(b){ 
		                       $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'1900'}}); 
		   				    $(".blockOverlay").css({'z-index':'1900'});
		               	}   
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
							window.wxc.success("提交成功！",{"wxcOk":function(){
								caseTaskCheck();
							}});
							
							//$('#case-task-modal-form').modal("show");
					},
					error : function(errors) {
						 window.wxc.error("数据保存出错");
						 $.unblockUI();
					}
				});
			}
		}
	
	</script> 
	</content>
</body>


</html>