<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>知识库列表</title>

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<!-- Add fancyBox main JS and CSS files -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox.css?v=2.1.5" media="screen" />
<!-- Add Button helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox-buttons.css?v=1.0.5" />
<!-- Add Thumbnail helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox-thumbs.css?v=1.0.7" />

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">

<style type="text/css">
#table_list_2>tbody>tr>td {
	text-align: center;
}
.text-none{text-indent:-9999px;text-align:left !important;}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<form action="" class="form-horizontal" id="f_main">
		<input type="hidden" id="caseCode" name="caseCode"
			value="${loanAgent.caseCode }" />
		<div class="ibox">
			<div class="ibox-title">
				<div class="row">
					<div class="col-lg-1 control-label">状态搜索项</div>
					<div class="col-lg-3">
						<aist:dict id="sel_applyStatus" name="applyStatus"
							clazz="form-control" display="select" hasEmpty="true"
							dictType="yu_eplus_status"
							defaultvalue="${loanAgent.applyStatus }" ligerui='none'></aist:dict>
					</div>



				</div>
				<div class="hr-line-dashed"></div>
				<div class="row">
					<div class="col-lg-1 control-label">时间搜索项</div>
					<div class="col-lg-11">
						<div style="float: left; width: 200px">
							<select id="sel_time" class="form-control" style="width: 200px">
								<option value="applyTime">申请时间</option>
								<option value="signTime">面签时间</option>
								<option value="releaseTime">放款时间</option>
							</select>
						</div>

						<div id='div_fTime' class="input-group date" style="float: left; width: 200px">
							<span class="input-group-addon"><i class="fa fa-calendar"
								style="z-index: 2100; position: relative;"></i></span> <input
								class="form-control" type="text" id="txt_fTime"
								style="width: 200px">
						</div>


						<div id='div_tTime' class="input-group date" style="float: left; width: 200px">
							<span class="input-group-addon"><i class="fa fa-calendar"
								style="z-index: 2100; position: relative;"></i></span> <input
								class="form-control" type="text" id="txt_tTime"
								style="width: 200px">
						</div>
					</div>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="row">
					<div class="col-lg-1 control-label"> 案件信息 </div>
					<div class="col-lg-11">
						<div style="float: left; width: 200px">
							<select id="sel_caseInfo" class="form-control"
								style="width: 200px">
<<<<<<< HEAD
=======

>>>>>>> refs/heads/feature/taskReport
								<option value="addr">产证地址</option>
								<option value="custName">客户姓名</option>
							</select>
						</div>
						<div style="float: left; width: 200px">
							<input type="text" class=" form-control" id="txt_caseInfo" placeholder="产证地址" style="width: 482px">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-1 control-label text-none"> 案件信息 </div>
					<div class="col-lg-11">
						<button type="button" class="btn btn-primary" id="btn_search" style="width: 80px;margin:15px 0 10px 0;">搜索</button>
					</div>
				</div>

			</div>
			<div class="ibox-content">
				<div class="jqGrid_wrapper form-group">
					<table id="table_list_2"></table>
					<div id="pager_list_2"></div>
				</div>
				<div class="form-group row">
					<button type="button" class="btn btn-sm btn-primary" id="btn_add">&nbsp;&nbsp;&nbsp;新增&nbsp;&nbsp;&nbsp;</button>
				</div>
			</div>

		</div>
	</form>
	<div class="portlet-body" style="display: block;">
		<a id="alertOper" class="fancybox-thumb" rel="fancybox-thumb"></a>
	</div>

	</script>
	<content tag="local_script"> <!-- jqGrid --> <script
		src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script> <script
		src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
		<script src="${ctx}/js/jquery.blockui.min.js"></script>
		<script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript"
		src="${ctx}/js/jquery.fancybox.js?v=2.1.5"></script> <!-- Add Button helper (this is optional) -->
	<script type="text/javascript"
		src="${ctx}/js/jquery.fancybox-buttons.js?v=1.0.5"></script> <!-- Add Thumbnail helper (this is optional) -->
	<script type="text/javascript"
		src="${ctx}/js/jquery.fancybox-thumbs.js?v=1.0.7"></script> <!-- Add Media helper (this is optional) -->
	<script type="text/javascript"
		src="${ctx}/js/jquery.fancybox-media.js?v=1.0.6"></script> <script>
			var ctx = "${ctx}";
			var postData, caseList;
			var fBox;
			$(document).ready(
					function() {
						initGrid();
						$("#btn_search").click(function() {
							postData.queryId ="loanList";
							sp = $("#sel_time").val();
							ca = "search_" + $("#sel_caseInfo").val();
							t1 = "argu_" + sp + "1";
							t2 = "argu_" + sp + "2";
							postData['search_applyStatus']=$("#sel_applyStatus").val();
							postData[t1] = $('#txt_fTime').val();
							postData[t2] = $('#txt_tTime').val();
							postData[ca] = $('#txt_caseInfo').val();
							caseList.jqGrid("setGridParam", {
								postData : postData,
								datatype : 'json',
								mtype:'POST',
								page:1  
							}).trigger("reloadGrid");
						});
						$("#btn_search").trigger('click');
						$("#sel_caseInfo").change(
								function() {
									$("#txt_caseInfo").attr(
											"placeholder",
											$("#sel_caseInfo").find(
													"option:selected").text());
								});
						$("#btn_add").click(
								function() {
									$('#alertOper').attr("href",
											ctx + "/loan/box/details");
									$("#alertOper").trigger('click');
								});
						$('.input-group.date').datepicker({
							todayBtn : "linked",
							keyboardNavigation : false,
							forceParse : false,
							calendarWeeks : true,
							autoclose : true
						});
						$("#div_fTime").on("click",function(ev){
						    $("#div_fTime").datepicker("setEndDate", $("#txt_fTime").val());
						});
						$("#div_tTime").on("click", function (ev) {
						    $("#div_tTime").datepicker("setStartDate", $("#txt_fTime").val());
						});
						$(".fancybox").fancybox({

							fitToView : false,
							width : '90%',
							height : '100%',
							autoSize : false,
							closeClick : false,
							openEffect : 'none',
							closeEffect : 'none'
						});

						fBox=$('#alertOper').fancybox({
							type : 'iframe',
							padding : 0,
							margin : 0,
							autoScale : false,
							fitToView : false,
							width : '90%',
							height : '100%',
							autoDimensions : true,
							showCloseButton : true,
							close : true,
							helpers : {
								overlay : {
									closeClick : false
								}
							},
							iframe : {
								preload : false
							},
							openEffect : 'none',
							closeEffect : 'none',
							afterClose : function() {
								caseList.trigger("reloadGrid");//刷新列表
							}
						});
					});
			function initGrid() {
				postData = {
					queryId : "loanList"
				};
				caseList = $("#table_list_2").jqGrid({
					datatype : 'local',
					url : ctx + "/quickGrid/findPage",
					height : 280,
					autowidth : true,
					mtype:'POST',
					shrinkToFit : true,
					rowNum : 6,
									colNames : [ '产品类型', '产证地址', '案件归属','客户姓名',
											'当前状态','产品部确认状态', '申请金额(万元)', '申请时间', '面签时间',
											'放款时间', '操作' ],
									colModel : [
											{
												name : 'loanSrvCode',
												index : 'loanSrvCode',
												width : 90
											},
											{
												name : 'addr',
												index : 'addr',
												width : 90
											},
											{
												name : 'executor',
												index : 'executor',
												width : 90
											},
											{
												name : 'custName',
												index : 'custName',
												width : 90
											},
											{
												name : 'applyStatus',
												index : 'applyStatus',
												width : 60,
											},{
												name : 'confirmStatus',
												index : 'confirmStatus',
												width : 90,
											},
											
											{
												name : 'loanAmount',
												index : 'loanAmount',
												width : 90
											},
											{
												name : 'applyTime',
												index : 'applyTime',
												width : 60,
												formatter : 'date',
												formatoptions : {
													newformat : 'Y-m-d'
												}
											},
											{
												name : 'signTime',
												index : 'signTime',
												width : 60,
												formatter : 'date',
												formatoptions : {
													newformat : 'Y-m-d'
												}
											},
											{
												name : 'releaseTime',
												index : 'releaseTime',
												width : 60,
												formatter : 'date',
												formatoptions : {
													newformat : 'Y-m-d'
												}
											},
											{
												width : 130,
												formatter : function(cellvalue,
														options, rawObject) {
													var btn2 = "<button onclick='javascript:openLoan(\""
															+ rawObject.pkid
															+ "\");return false;' class='btn red' >详细 </button>";
													var btn3 = "<button onclick='javascript:deleteLoan(\""
															+ rawObject.pkid
															+ "\");return false;' class='btn red' >删除 </button>";															
													return btn2+"&nbsp;&nbsp;&nbsp;"+btn3;
												}
											} ],
									pager : "#pager_list_2",
									viewrecords : true,
									pagebuttions : true,
									hidegrid : false,
									pgtext : " {0} 共 {1} 页",
									recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
									postData : postData
								});
			}
			function openLoan(pkid) {
				$('#alertOper').attr("href",
						ctx + "/loan/box/details?pkid=" + pkid);
				$("#alertOper").trigger('click');
			}
			function deleteLoan(pkid) {
				if(!confirm("是否确定删除？")){
					return false;	
				}
				
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
				$.ajax({
					cache:false,
					async:true,
					type:"POST",
					url:ctx+"/loan/doDelete",
					dataType:'json',
					data:{pkid:pkid},
					success:function(data){
						if(data.result==1){
							alert('删除成功');
							caseList.trigger("reloadGrid");//刷新列表
						}else{
							alert('删除失败');
						}
						$.unblockUI();
					}
				});
			}
		</script> </content>
</body>
</html>



