<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">

<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />" rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">

<link href="<c:url value='/css/common/common.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" rel="stylesheet">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link rel="stylesheet" href="<c:url value='/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/css/iconfont/iconfont.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet">
<style>
	.input-daterange .input-group-addon{
		line-height: 14px;
	}
	.data-wrap-in .table th{
		background-color: #4bccec;
		font-size: 14px;
		font-weight: normal;
		color: #fff;
	}
</style>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<div class="row">
	<link href="http://10.4.19.211/trade-demo/static/trans/css/common/table.css" rel="stylesheet">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			<h2 class="title">
				产调详细筛选
			</h2>
			<form method="post" class="form_list form-horizontal" id="chandiaoDetail" target="_blank">
				<div class="line">

					<div class="form_content">
						<label class="control-label sign_left_small select_style mend_select">
							产调申请时间
						</label>
						<div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
							<input id="dtBegin_0"  name="dtBegin" class="form-control data_style" type="text" value="" placeholder="起始时间"> <span class="input-group-addon">到</span>
							<input id="dtBegin_1" name="dtEnd" class="form-control data_style" type="text" value="" placeholder="结束日期">
						</div>
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small select_style mend_select">
							产调接受时间
						</label>
						<div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
							<input id="prAccpetTimeStart" name="prAccpetTimeStart" class="form-control data_style" type="text" value="" placeholder="起始时间"> <span class="input-group-addon">到</span>
							<input id="prAccpetTimeEnd" name="prAccpetTimeEnd"  class="form-control data_style" type="text" value="" placeholder="结束日期">
						</div>
					</div>

				</div>
				<div class="line">

					<div class="form_content">
						<label class="control-label sign_left_small select_style mend_select">
							产调完成时间
						</label>
						<div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
							<input id="prCompleteTimeStart" name="prCompleteTimeStart" class="form-control data_style" type="text" value="${prCompleteTimeStart}" placeholder="起始时间"> <span class="input-group-addon">到</span>
							<input id="prCompleteTimeEnd" name="prCompleteTimeEnd" class="form-control data_style" type="text" value="${prCompleteTimeEnd}" placeholder="结束日期">
						<input type="hidden" value="${prCompleteTimeStart}" />
						</div>
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small">
							贵宾服务部
						</label>
						<input type="text" class="teamcode input_type" id="teamCode" name="teamCode" readonly="readonly" style="background-color:#FFFFFF"
							   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
									   startOrgId:'ff8080814f459a78014f45a73d820006', orgType:'',departmentType:'',departmentHeriarchy:'yucui_team',
									   chkStyle:'radio', callBack:radioYuCuiOrgSelectCallBack,
									   expandNodeId:'',chkLast:'true'})"  value="" />
						<input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId">
						<div class="input-group float_icon organize_icon">
							<i class="icon iconfont">&#xe61b;</i>
						</div>
					</div>
				</div>
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">
							产调地址
						</label>
						<input class="teamcode input_type"  id="prAddress" name="prAddress" style="width:435px;" placeholder="" value="" />
					</div>
					<div class="add_btn" style="margin-left:600px">
						<button type="button" class="btn btn-success"id="searchButton">
							<i class="icon iconfont">&#xe635;</i>
							查询
						</button>
						<button type="button" class="btn btn-success" onclick="javascript:exportToExcel()">
							导出列表
						</button>
						<button type="button" class="btn btn-grey" id="cleanButton" >
							清空
						</button>
					</div>
				</div>
			</form>
		</div>

		<div class="data-wrap">
			<div class="data-wrap-in">
				<table class="table table_blue table-striped table-bordered table-hover " id="editable" >
					<thead>
					<tr>
						<th>产调编号<a href="#"><i class="fa fa-sort-desc fa_down"></i></a></th>
						<th>申请人</th>
						<th>时间</th>
						<th>受理时间<a href="#"><i class="fa fa-sort-desc fa_down"></i></a></th>
						<th>完成时间<a href="#"><i class="fa fa-sort-desc fa_down"></i></a></th>
						<th>贵宾服务部</th>
					</tr>
					</thead>
					<tbody id="chandiaoTransferList">

					</tbody>
				</table>
			</div>
		</div>
		<div class="text-center">
			<span id="currentTotalPage"><strong class="bold"></strong></span>
			<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
			<div id="pageBar" class="pagination my-pagination text-center m0"></div>
		</div>

	</div>
</div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	
	<input type="hidden" id="organId" name="organId" value="${organId}" />
	<%-- <input type="hidden" id="prApplyTimePre" value="${prApplyTime}" />
	<input type="hidden" id="prApplyTimeEndPre" value="${prApplyTimeEnd}" />
	<input type="hidden" id="prAccpetTimeStartPre" value="${prAccpetTimeStart}" />
	<input type="hidden" id="prAccpetTimeEndPre" value="${prAccpetTimeEnd}" />
	<input type="hidden" id="prCompleteTimeStartPre" value="${prCompleteTimeStart}" />
	<input type="hidden" id="prCompleteTimeEndPre" value="${prCompleteTimeEnd}" /> --%>
	
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
	<content tag="local_script"> 
    <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
    <script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
	<script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
	 <script src="<c:url value='/js/trunk/report/chandiao_transfer_detail.js' />"></script>
	 <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	 <script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 
	  <!-- 分页控件  -->
     <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	 <script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
	 <script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	 <script id="template_chandiaoTransferList" type= "text/html">
		 {{each rows as item index}}
		 <tr>
			 <td>
				 <p class="big"><a>{{item.PR_CODE}}</a></p>
				 <p>{{item.PR_ADDRESS}}</p>
			 </td>
			 <td>{{item.REAL_NAME}}</td>
			 <td>
				 <p class="smll_sign"><i class="sign_normal">申</i>{{item.PR_APPLY_TIME}}</p>
				 <p class="smll_sign"><i class="sign_normal">受</i>{{item.PR_ACCPET_TIME}}</p>
				 <p class="smll_sign"><i class="sign_normal">完</i>{{item.PR_COMPLETE_TIME}}</p>
			 </td>
			 <td class="center">{{item.ACCEPTANCE}}(小时)</td>
			 <td class="center">{{item.ACOMPLETE_TIME}}(小时)</td>
			 <td class="center">{{item.ORG_NAME}}</td>
		 </tr>
		 {{/each}}
     </script>
     <script>
     aist.sortWrapper({
			reloadGrid : searchMethod
		});
     //选业务组织的回调函数
     function radioYuCuiOrgSelectCallBack(array){
         if(array && array.length >0){
             $("#teamCode").val(array[0].name);
     		$("#yuCuiOriGrpId").val(array[0].id);
     		
     	}else{
     		$("#teamCode").val("");
     		$("#yuCuiOriGrpId").val("");
     	}
     }
    
     </script>

	 </content>
</body>
</html>