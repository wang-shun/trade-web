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
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content  animated fadeInRight">




		<div class="col-lg-12">
			<div class="ibox ">
				<div class="ibox-title">
					<h5>我的授权列表</h5>
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
		src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
		<script src="${ctx}/js/jquery.blockui.min.js"></script>
		<script>
		
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
										var btn2 = "<button onclick='doDelegate(\""
											+ rawObject.username
											+ "\")' class='btn red' >授权 </button>";
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

								//rowid为grid中的行顺序
								onSelectRow : function(rowid) {

									},
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
													colNames : [ '编号', '授权人',
															'授权开始日期', '授权结束日期',
															'当前状态', '操作' ],
													colModel : [ {
														name : 'PKID',
														index : 'PKID',
														width : 90
													}, {
														name : 'assignee',
														index : 'assignee',
														width : 90
													}, {
														name : 'createDate',
														index : 'createDate',
														width : 90
													}, {
														name : 'closeDate',
														index : 'closeDate',
														width : 90
													}, {
														name : 'status',
														index : 'status',
														width : 150
													}, {
														name : 'operation',
														index : 'operation',
														width : 60,
														formatter : function(
																		cellvalue,
																		options,
																		rawObject) {
															if(rawObject.statusCode=='20011002'){
																	var btn2 = "<button onclick='doDelegateClose(\""
																			+ rawObject.PKID
																			+ "\")' class='btn red' >取消授权 </button>";
																	return btn2;
															}else{
																return "";
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
													//rowid为grid中的行顺序
													onSelectRow : function(
															rowid) {

													},sortname:"create_Date",sortorder:"DESC",
													postData : {
														queryId : "queryTaskDelegateQuery",
														search_owner : "${owner}"
													}
												});
								

								$("#table_list_2").navGrid('#pager_list_2', {
									add : false,
									del : false,
									edit : false,
									search : false,
									refresh : false
								}).navButtonAdd("#pager_list_2",

								{
									caption : "新增授权",
									onClickButton : function() {
										$('#modal-form').modal("show");
									}
								});

							});
			function doDelegateClose(pkId){
				$.ajax({
					url : ctx + "/manage/doTaskDelegateClose/"+pkId,
					method : "post",
					dataType : "json",
					beforeSend:function(){  
						$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
						$(".blockOverlay").css({'z-index':'9998'});
		            },complete: function() {  
		                if(status=='timeout'){//超时,status还有success,error等值的情况
			  	          	  Modal.alert(
			  				  {
			  				    msg:"抱歉，系统处理超时。"
			  				  });
			  	          	$.unblockUI();  
			  		        }
			  		   },
					success : function(data) {
						if(data.sc&&data.sc=='0'){
							alert('取消成功！');
						}else{
							alert('取消失败！');
						}
						$.unblockUI();  
						taskDelGrid.trigger('reloadGrid');
					}
				});
			}								
			function doDelegate(username) {
				if(!confirm('新增授权将会取消之前所有授权，是否确定继续？')){return;}
				$.ajax({
					url : ctx + "/manage/doTaskDelegate/"+username,
					method : "post",
					dataType : "json",
					beforeSend:function(){  
						$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
						$(".blockOverlay").css({'z-index':'9998'});
		            },complete: function() {  
		                if(status=='timeout'){//超时,status还有success,error等值的情况
		  	          	  Modal.alert(
		  				  {
		  				    msg:"抱歉，系统处理超时。"
		  				  });
		  	          	$.unblockUI();  
		  		        }
		  		   },
					success : function(data) {
						if(data.sc&&data.sc=='0'){
							alert('授权成功！');
						}else{
							alert('授权失败！');
						}
						$.unblockUI();  
						$('#modal-form').modal("hide");
						taskDelGrid.trigger('reloadGrid');
					}
				});

			}
		</script> </content>
</body>
</html>
