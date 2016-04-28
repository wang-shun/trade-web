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
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/switch/bootstrap-switch.min.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">
<style type="text/css">
.radio.radio-inline>label {
	margin-left: 10px;
}

.radio.radio-inline>input {
	margin-left: 10px;
}

.checkbox.checkbox-inline>div {
	margin-left: 25px;
}

.checkbox.checkbox-inline>input {
	margin-left: 20px;
}

.ui-state-hover {
	cursor: pointer;
}

.btn-xm {
	margin-left: 10px;
	margin-top: 10px;
	width: 160px;
}
.fixWidth{
	width:140px!important;
}
</style>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/excelImport.jsp"></jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>个人案件KPI导入</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="row form-group">
							<div class="switch " data-on-label="上月" data-off-label="当月">
								<input id="moSwitch" type="checkbox" checked />
							</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<label class="col-sm-5 control-label" id="case_date">案件编号</label>
								<input id="caseCode" type="text" class="form-control"
									style="width: 140px">
							</div>
							<div class="col-md-3">
								<label class="col-sm-5 control-label" id="case_date">环节</label>
								<aist:dict id="srvCode" name="srvCode" clazz="col-sm-5 form-control fixWidth"
						display="select"  dictType="KPI_SRV_CODE" 
						ligerui='none' ></aist:dict>

							</div>
							<span class="col-md-3 ">
								<button id="searchButton" type="button"
									class="btn btn-primary pull-lefe">查询</button> <a role="" class="btn btn-primary " id="importButton">个人案件KPI导入 </a>
							</span>
						</div>

					</form>
				</div>
			</div>
		</div>

		<div class="wrapper wrapper-content  animated fadeInRight">

			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>个人案件KPI明细</h5>
					</div>

					<div class="ibox-content">
						<div class="jqGrid_wrapper">
							<table id="table_list_1"></table>
							<div id="pager_list_1"></div>
						</div>
					</div>
				</div>
			</div>

		</div>
		
		<!-- 失败数据 -->
		<div id="error-modal-form" class="modal fade" role="dialog"
			aria-labelledby="excel-modal-title" aria-hidden="true">
			<div class="modal-dialog" style="width: 1200px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="excel-modal-title">导入失败数据</h4>
					</div>

					<div class="modal-footer">
						<div class="ibox float-e-margins">

							<div class="ibox-content">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>案件编号</th>
											<th>案件地址</th>
											<th>人员</th>
											<th>员工编号</th>
											<th>所有组织</th>
											<th>岗位</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${fList}" var="item">
											<tr>
												<td>${item.caseCode }</td>
												<td>${item.caseAddress }</td>
												<td>${item.userName }</td>
												<td>${item.employeeCode }</td>
												<td>${item.orgName }</td>
												<td>${item.jobName }</td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>

	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ex_message" value="${ex_message}" />
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>

	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> <script
		src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script> <script
		src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <!-- iCheck -->
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <script
		src="${ctx}/js/plugins/switch/bootstrap-switch.js"></script> <script
		src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/plugins/layer/layer.js"></script> <script
		src="${ctx}/js/plugins/layer/extend/layer.ext.js"></script> <script>
		  var belongM = "${belongM}";
		    var belongLastM = "${belongLastM}";
			sw = $("#moSwitch").bootstrapSwitch({
				'onText' : "上月",
				'offText' : '当月',
				state : false
			}).on('switchChange.bootstrapSwitch', function(event, state) {
			});
			
			//jqGrid 初始化
    		$("#table_list_1").jqGrid({
    			url : ctx+"/quickGrid/findPage",
    			mtype : 'GET',
    			datatype : "json",
    			height : 550,
    			autowidth : true,
    			shrinkToFit : true,
    			rowNum : 8,
    			/*   rowList: [10, 20, 30], */
    			colNames : [ '主键','案件编码','环节','所在组别','所属贵宾服务部','类型','满意度','是否接通'],
    			colModel : [ {
    				name : 'pkid',
    				index : 'pkid',
    				align : "center",
    				width : 0,
    				key : true,
    				resizable : false,
    				hidden : true
    			}, {
    				name : 'CASE_CODE',
    				index : 'CASE_CODE',
    				align : "center",
    				width : 300,
    				resizable : false
    			}, {
    				name : 'SRV_CODE',
    				index : 'SRV_CODE',
    				align : "center",
    				width : 100,
    				resizable : false
    			},{
    				name : 'tName',
    				index : 'tName',
    				align : "center",
    				width : 300,
    				resizable : true,
    				
    			},{
    				name : 'dName',
    				index : 'dName',
    				align : "center",
    				width : 300,
    				resizable : true
    			},{
    				name : 'type',
    				index : 'type',
    				align : "center",
    				formatter : function(cellvalue,
							options, rawObject) {
    					if(cellvalue=='IMP'){
    						return '导入';
    					}else if(cellvalue=='GEN'){
    						return '生成';
    					}
    					return cellvalue;
    				}
    			},{
    				name : 'SATISFACTION',
    				index : 'SATISFACTION',
    				align : "center",
    				width : 80,
    				resizable : false
    			}, {
    				name : 'CAN_CALLBACK',
    				index : 'CAN_CALLBACK',
    				align:'center',
    				resizable : true,
    				formatter : function(cellvalue,
							options, rawObject) {
    					if(cellvalue=='0'){
    						return '不通';
    					}else if(cellvalue=='1'){
    						return '通';
    					}
    					return '';
    				}
    			}],
    			multiselect: true,
    			pager : "#pager_list_1",
    			sortname:'PKID',
    	        sortorder:'desc',
    	        viewrecords : true,
    			pagebuttions : true,
    			multiselect:false,
    			hidegrid : false,
    			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
    			pgtext : " {0} 共 {1} 页",

    			gridComplete:function(){
    				
    			},
    			postData : {
    				queryId : "kpiList",
                    argu_belongMonth : belongM
    			}

    		});
			
			
			$("#importButton").click(
					function() {
						//iframe层
						layer.open({
							type : 1,
							title : '个人案件KPI导入 ',
							shadeClose : true,
							shade : 0.8,
							area : [ '50%', '40%' ],
							content : $('#excelImport'), //捕获的元素
							btn : [ '提交', '关闭' ],
							yes : function(index) {
								if (checkFileTypeExcel()) {
									$.blockUI({
										message : $("#salesLoading"),
										css : {
											'border' : 'none',
											'z-index' : '9999'
										}
									});
									$(".blockOverlay").css({
										'z-index' : '9998'
									});
									$("#excelInForm").attr("action",
											ctx + "/kpi/doImport");
									$("#excelInForm").find("input[name='currentMonth']").remove();
									$("#excelInForm").append($("<input>").attr({type:'hidden',name:'currentMonth'}).val(!sw.bootstrapSwitch('state')));
									$("#excelInForm").attr("method", "POST");
									$('#excelInForm').submit();

									layer.close(index);
								}
							},
							cancel : function(index) {
								layer.close(index);
								//layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 5000, icon:6});
							}
						});
					})
					
		$("#searchButton").click(function(){
			reloadGrid();
		});
		function reloadGrid(){
			var bm=belongLastM;
			if(!sw.bootstrapSwitch('state')){
				bm=belongM;
			}
			var data = {
   					queryId:"kpiList",
   					argu_belongMonth : bm,
   					search_caseCode:$('#caseCode').val(),
   					search_srvCode:$('#srvCode').val()
    			};
    		 	$("#table_list_1").jqGrid('setGridParam',{
		    		datatype:'json',
		    		mtype:'post',
		    		postData:data
		    	}).trigger('reloadGrid'); 
		}
			
		</script> </content>
</body>
</html>
