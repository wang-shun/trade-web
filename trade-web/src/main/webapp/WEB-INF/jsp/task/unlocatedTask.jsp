<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">


<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">

<!-- Morris -->
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">
</head>

<body>
<input type="hidden" id="taskId">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="col-lg-12">
			<div class="ibox ">
				<div class="ibox-title">
					<h5>未分配任务列表</h5>
				</div>
				<div class="ibox-content">
					<div class="jqGrid_wrapper">
						<table id="table_list_2"></table>
						<div id="pager_list_2"></div>
					</div>

				</div>
			</div>
		</div>

		<div id="modal-form" class="modal fade" aria-hidden="true">
			<div class="modal-dialog" style="width: 900px">
				<div class="modal-content">
				 <div class="modal-header">
            <button type="button" class="close"
               data-dismiss="modal" aria-hidden="true">
                 &nbsp; &times;
            </button>
         </div>
					<div class="modal-body">
						<div style="padding-bottom: 3px;">
						<label>员工姓名:</label><input type="text" id="txt_username">&nbsp;&nbsp;<button onclick="loadUser();" class='btn red' >搜索 </button>
						</div>
								<div class="jqGrid_wrapper" >
									<table id="table_list_3"></table>
									<div id="pager_list_3"></div>
								</div>

						</div>
					</div>
				</div>
				<a title="关闭" class="fancybox-item fancybox-close" href="javascript:;"></a>
			</div>
		</div>
	</div>
	</div>
	</div>


	<content tag="local_script"> <!-- Peity --> <script
		src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <!-- Custom and plugin javascript -->
	
 <script
		src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> <script
		src="${ctx}/js/plugins/dropzone/dropzone.js"></script> <script>
		var ctx="${ctx}";
		var taskDelGrid,userGrid;
		function loadUser(){
			var userQuery={username : $("#txt_username").val()};
	        		$('#table_list_3').jqGrid('setGridParam',{
	        			datatype:'json',  
	        			mtype : 'POST',
	        			postData: userQuery
	        		}).trigger('reloadGrid');
		}
		
			$(document)
					.ready(
							function() {
							userGrid=$("#table_list_3").jqGrid(
						{
							datatype : 'json',
							url : "${ctx}"+ "/manage/listUser",
							height : 250,
							autowidth : true,
							shrinkToFit : true,
							rowNum : 5,
							/*   rowList: [10, 20, 30], */
							colNames : [ '编号','姓名', '登录人',
							'所在组织', '操作' ],
							colModel : [
								{
									name : 'employeeCode',
								 	index : 'employeeCode',
									width : 100
								},
								{
									name : 'realName',
									index : 'realName',
									width : 150
								},
								{
									name : 'username',
									index : 'username',
									width : 250
								},
								{
									name : 'orgName',
									index : 'orgName',
									width : 250
								},
								{
									name : 'username',
									index : 'username',
									width : 90,
									formatter : function(cellvalue,options,rawObject) {
										var btn2 = "<button onclick='doLocateTask(\""
											+ rawObject.username
											+ "\")' class='btn red' >分配 </button>";
											return btn2;
										}
								}
								],
								add : true,
								addtext : 'Add',
								pager : "#pager_list_3",
								viewrecords : true,
								pagebuttions : true,
								hidegrid : false,
								recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
								pgtext : " {0} 共 {1} 页",

							});
								var url = "/quickGrid/findPage";
								
								// Configuration for jqGrid Example 2
								taskDelGrid=$("#table_list_2")
										.jqGrid(
												{
													datatype : 'json',
													url : "${ctx}" + url,
													height : 250,
													autowidth : true,
													shrinkToFit : true,
													rowNum : 5,
													/*   rowList: [10, 20, 30], */
													colNames : ['案件编号',
															'服务名称', '处理角色',
															'开始时间', '操作' ],
													colModel : [  {
														name : 'caseCode',
														index : 'caseCode',
														width : 90,
														formatter : function(
																		cellvalue,
																		options,
																		rawObject) {
															return "<a href='"+rawObject.caseId+"' taget='_blank'>"+rawObject.caseCode+"<//a>";	
														}
													}, {
														name : 'taskDfKey',
														index : 'taskDfKey',
														width : 90
													}, {
														name : 'taskJobCode',
														index : 'taskJobCode',
														width : 90
													}, {
														name : 'createTime',
														index : 'createTime',
														width : 150,
														formatter:'date',formatoptions: {newformat:'Y-m-d'}
													}, {
														name : 'candidateId',
														index : 'candidateId',
														width : 60,
														formatter : function(
																		cellvalue,
																		options,
																		rawObject) {
															if(rawObject.candidateId&&rawObject.candidateId!=''){
																	var btn2 = "<button onclick='doGroupClaim(\""
																			+ rawObject.taskId
																			+ "\")' class='btn red' >分配给自己 </button>";
																	return btn2;
															}else{
																var btn2 = "<button onclick='showLocate(\""
																			+ rawObject.taskId
																			+ "\")' class='btn red' >分配任务 </button>";
																	return btn2;
															}
														}
														}

													],
													add : true,
													addtext : 'Add',
													pager : "#pager_list_2",
													viewrecords : true,
													pagebuttions : true,
													hidegrid : false,
													recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
													pgtext : " {0} 共 {1} 页",
													sortname:"ut.create_time",sortorder:"DESC",
													postData : {
														queryId : "queryUnlocatedTask",
														argu_candidateId : "${candidateId}",
														argu_mOrgId:"${orgId}",
														argu_managerFlag:"${managerFlag}"
													}
												});
								
							});
			function doGroupClaim(taskId){
				$.ajax({
					url : ctx + "/unlocatedTasks/doGroupClaim/"+taskId,
					method : "post",
					dataType : "json",
					success : function(data) {
						if(data.sc&&data.sc=='0'){
							alert('分配成功！');
						}else{
							alert('分配失败！');
						}
						taskDelGrid.trigger('reloadGrid');
					}
				});
			}			
			function showLocate(taskId){
				$("#taskId").val(taskId);
				$('#modal-form').modal("show");
			}
			function doLocateTask(candidateId) {
				var taskId=$("#taskId").val();
				$.ajax({
					url : ctx + "/unlocatedTasks/doLocateTask/"+taskId+"/"+candidateId,
					method : "post",
					dataType : "json",
					success : function(data) {
						if(data.sc&&data.sc=='0'){
							alert('分配成功！');
						}else{
							alert('分配失败！');
						}
						$('#modal-form').modal("hide");
						taskDelGrid.trigger('reloadGrid');
					}
				});
			}

		</script> </content>
</body>
</html>
