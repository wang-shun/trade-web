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
<title>银行返利权证经理复审</title>
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
									    <th><span class="star">*</span>审批意见</th>
									    <td><textarea name="comment" rows="2" cols="100"></textarea><input type="hidden" name="selfAppInfoId" value="${toSelfAppInfo.pkid}"/></td>
									    
									  </tr>
									  <tr>
									    <th width="100px" align="center"><span class="star">*</span>审批结果</th>
									    <td width="500px">
									    <label> 
							   			 <input type="radio" value="0"  name="result" checked="checked"/> <span >通过</span>
										</label>
										</td>
									  </tr>
									 
							</table>
								
								
			<div class="title title-mark" >
				<strong style="font-weight: bold;">审批历史记录</strong>
			</div>
			<div class="view-content">
			  <table class="table table_blue table-striped table-bordered table-hover" id="editable" >
                                         <thead>
                                            <tr>
                                                <th> 步骤名称</th>
                                                <th>审批人域账号</th>
                                                <th>审批人姓名</th>
                                                <th> 审批时间</th>
                                                <th>审批结果</th>
                                                <th> 备注</th>
                                            </tr>
                                        </thead>
                                        <tbody class="table_content_pre">
                                      		<c:forEach items="${updateLogList}" var="item" varStatus="status">  
											  <tr >  
											    <td class="center"><span class="center">${status.index+1}</span></td>  
											    <td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>  
											    <td>${item.updateReason}</td>  
											    <td>${item.rejectPerson}</td>  
											    <td><fmt:formatDate value="${item.approTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											    <td>${item.rejectCause}</td>
											  </tr>  
											</c:forEach> 
                                        </tbody>
                                    </table> 
                               </div>
			<!-- <div class="view-content">
				<table id="gridTable" class=""></table>
				<div id="gridPager"></div>
			</div>	 -->		
					
					
				</div>
			</div>
			
			</form>

			<div class="form-btn" style="margin-top:50px">
				<div class="text-center">
					<button class="btn btn-success btn-space" onclick="closeWin()" >关闭</button>
					<button class="btn btn-success btn-space" onclick="submit()">提交审批</button>
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
var caseCode = $("#caseCode").val();
	
	var AttachmentList = (function() {
		return {
			init : function(ctx, url, gridTableId, gridPagerId, ctmCode,
					caseCode) {
				//jqGrid 初始化
				$("#" + gridTableId)
						.jqGrid(
								{
									url : ctx + url,
									mtype : 'GET',
									datatype : "json",
									height : 125,
									autowidth : true,
									shrinkToFit : true,
									rowNum : 3,
									/*   rowList: [10, 20, 30], */
									colNames : [ '审批人', '审批时间', '审批结果', '审批意见' ],
									colModel : [ {
										name : 'OPERATOR',
										index : 'OPERATOR',
										align : "center",
										width : 25,
										resizable : false
									}, {
										name : 'OPERATOR_TIME',
										index : 'OPERATOR_TIME',
										align : "center",
										width : 25,
										resizable : false
									}, {
										name : 'NOT_APPROVE',
										index : 'NOT_APPROVE',
										align : "center",
										width : 25,
										resizable : false
									//formatter : linkhouseInfo
									}, {
										name : 'CONTENT',
										index : 'CONTENT',
										align : "center",
										width : 25,
										resizable : false
									} ],
									multiselect : true,
									pager : "#" + gridPagerId,
									//sortname:'UPLOAD_DATE',
									//sortorder:'desc',
									viewrecords : true,
									pagebuttions : true,
									multiselect : false,
									hidegrid : false,
									recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
									pgtext : " {0} 共 {1} 页",
									gridComplete : function() {
										var ids = jQuery("#" + gridTableId)
												.jqGrid('getDataIDs');
										for (var i = 0; i < ids.length; i++) {
											var id = ids[i];
											var rowDatas = jQuery(
													"#" + gridTableId).jqGrid(
													'getRowData', ids[i]); // 获取当前行

											var auditResult = rowDatas['NOT_APPROVE'];
											var auditResultDisplay = null;
											if (!auditResult) {
												auditResultDisplay = "审批通过"
											} else {
												auditResultDisplay = auditResult;
											}
											jQuery("#" + gridTableId)
													.jqGrid(
															'setRowData',
															ids[i],
															{
																NOT_APPROVE : auditResultDisplay
															});
										}
									},
									postData : {
										queryId : "queryLoanlostApproveList",
										//caseCode : caseCode
										caseCode : caseCode
									}

								});
			}
		};
	})();
	
		$(document).ready(function(){	

					var ctx = $("#ctx").val();
					/* 	if(!$("#caseCode").val()){
					           $("#caseCode").val("ZY-TJ-2017080038");						
					     } */
					var caseCode = $("#caseCode").val();
					AttachmentList.init('${ctx}','/quickGrid/findPage', 'gridTable',
							'gridPager', '${ctmCode}', caseCode);
					$("#caseCommentList").caseCommentGrid({
						caseCode : caseCode,
						srvCode : 'caseRecvFlow'
					});
					
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