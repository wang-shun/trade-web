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
<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">

<!-- Morris -->
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">
<style type="text/css">
#selectDiv {
	width: 480px;
}

#inTextType_chosen {
	margin-left: 40px;
}
.ui-state-hover{
	cursor:pointer;
}
.checkbox.i-checks{margin-top:0}
</style>
</head>

<body>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="parentOrgId" value="${parentOrgId}" />
	<input type="hidden" id="orgId" value="${orgId}" />
	<input type="hidden" id="userId" value="${userId}" />
	<input type="hidden" id="color" value="${color}" />
		<div class="wrapper wrapper-content  animated fadeInRight row">

				<div class="ibox ">
					<div class="ibox-title">
						<h5>我的任务列表</h5>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-md-4 col-sm-6">
								<div class="form-group">
									<label class="col-sm-2 control-label">红绿灯</label>
									<div class="checkbox i-checks radio-inline">
										<label> <input type="radio" value="-1" id="lamp0"
											name="lampRadios"> 全部
										</label> <label> <input type="radio" value="0" id="lamp1"
											name="lampRadios"> <span class="label label-danger">红灯</span>
										</label> <label> <input type="radio" value="1" id="lamp2"
											name="lampRadios"> <span class="label label-warning">黄灯</span>
										</label> 
		
									</div>
								</div>
							</div>
						</div>
						<div class="jqGrid_wrapper">
							<table id="table_list_1"></table>
							<div id="pager_list_1"></div>
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
		src="${ctx}/js/plugins/dropzone/dropzone.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js">
		<!-- iCheck -->
		<script	src="${ctx}/js/plugins/iCheck/icheck.min.js"></script>
<script>
			var url = "/quickGrid/findPage";
			var ctx = $("#ctx").val();
			var isJygw = ${isJygw};
			url = ctx + url;
	$("input[name='lampRadios'][value='"+$('#color').val()+"']").attr("checked",'true');
			//jqGrid初始化
			$("#table_list_1").jqGrid(
					{
						url : url,
						datatype : "local",
						mtype : "POST",
						height : 500,
						autowidth : true,
						shrinkToFit : true,
						rowNum : 20,
						/* rowList: [10, 20, 30], */
						colNames : [ '红绿灯', '案件编号', '任务名', '产证地址', '执行人' ,'预计执行时间','','',''],
						colModel : [ {
							name : 'color',
							index : 'color',
							width : 30,
							editable : true,
							formatter : dateLampFormatter
						}, {
							name : 'caseCode',
							index : 'caseCode',
							width : 30,
							editable : true,

						}, {
							name : 'taskName',
							index : 'taskName',
							width : 65
						}, {
							name : 'propertyAddress',
							index : 'propertyAddress',
							width : 160
						}, {
							name : 'realName',
							index : 'realName',
							width : 40
						}, {
							name : 'estPartTime',
							index : 'estPartTime',
							width : 40
						},{
							name:'taskDfKey',
							hidden:true
						},{name:'taskId',
							hidden:true
						},{name:'instId',
							hidden:true
						}
						
						],
						pager : "#pager_list_1",
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",
						postData : {
							queryId : "queryRyLightList"
						},onSelectRow : function(rowid) {
							if(!isJygw)return;
							var rowData = $("#table_list_1").jqGrid('getRowData',rowid);
							var url = ctx+"/task/"+rowData.taskDfKey+
							"?taskId="+rowData.taskId+"&caseCode="+rowData.caseCode+"&instCode="+rowData.instId;
//							alert(url);
							window.location.href = url;
						}

					});
					
			function dateLampFormatter(cellvalue) {

				var color='';
				if (cellvalue ==2) {
					color='green';
				} else if (cellvalue ==1) {
					color='yellow';
				} else {
					color='red';
				}
				var outDiv = '<div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">';
				outDiv+='<div class="sk-double-bounce1" style="background-color:'+color+'"></div>';
				outDiv+='<div class="sk-double-bounce2" style="background-color:'+color+'"></div>';
				outDiv+='</div>';
				return outDiv;
			}				
	function searchMethod(){
		var dateLamp = $('input[name="lampRadios"]:checked').val();
		if(!dateLamp){
			dateLamp=$("#color").val();
		}else if(-1==dateLamp){
			dateLamp='';
		}
		
		var params = {
			search_color : dateLamp,
			argu_parentOrgId : $("#parentOrgId").val(),
			argu_myOrgId : $("#orgId").val(),
			argu_uid :  $("#userId").val(),
			sortname :"COLOR"
		};

		//jqGrid reload
		$("#table_list_1").setGridParam({
			"postData" : params,
			"page":1 ,
			datatype : "json"
		}).trigger('reloadGrid');
	}

	$('input:radio[name="lampRadios"]').change(function() {
		searchMethod();
	});
	$('#searchButton').click(function() {
		searchMethod();
	});
searchMethod();
</script>	

</content>
</body>
</html>
