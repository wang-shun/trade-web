<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />

<link rel="stylesheet" href="${ctx}/css/workflow/myCaseList.css" />
<link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" />

<style>
.table_content thead th{
	padding: 0px !important;
}
.btn.btn-grey.ml5{
	background: #eee;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<div class="wrapper wrapper-content animated fadeInRight">
     <div class="ibox-content border-bottom clearfix space_box">
         <h2 class="title">
			 服务归属或待办转移功能
         </h2>
		<form method="get" class="form-horizontal form_box">

			<div class="row clearfix">
				<div class="form_content">
					<label class="sign_left control-label">案件编号 </label>
					<div class="sign_right teamcode">
						<input id="caseCode" type="text" class="teamcode form-control" value="">

					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="form_content">
					<label class="sign_left control-label">产证地址 </label>
					<div class="sign_right intextval">
						<input id="caseAddress" type="text" class="form-control pull-left">
					</div>
				</div>
				<button id="searchButton" type="button" class="btn btn-success" style="margin-left: 10px;">
					<i class="icon iconfont"></i>   查询
				</button>
				<button id="changePr" type="button" class="btn btn-success" style="margin-left: 10px;">
					<i class="icon iconfont"></i>   变更
				</button>

				<div class="form_content">
					<label class="control-label sign_left">任务名</label>
					<aist:dict id="taskDfKey" name="taskDfKey" clazz="select_control sign_right_one" display="select" dictType="part_code" defaultvalue="" />
				</div>
			</div>
	   </form>
	</div>
	<div class="table_content">
		<table border="0" cellpadding="0" cellspacing="0" class="table table_blue table-striped table-bordered table-hover ">
			<thead>
				<tr>
					<th style="text-align: center;"><p class="big"><input type="checkbox" id="checkAll" /></p></th>
					<th>处理人</th>
					<th>案件编号</th>
					<th>流程环节</th>
					<th>产证地址</th>
					<th>创建时间</th>
				</tr>
			</thead>
			<tbody id="myTaskList">


			</tbody>
		</table>
	</div>
			
	<div class="text-center page_box">
		<span id="currentTotalPage"><strong ></strong></span>
		<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
		<div id="pageBar" class="pagergoto">
		</div>  
    </div> 	
	
</div>

<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>

<content tag="local_script">
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<!-- Custom and plugin javascript -->
	<script src="${ctx}/js/inspinia.js"></script>
	<script src="${ctx}/js/plugins/pace/pace.min.js"></script>

	<!-- 选择组织控件 -->
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>

	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
	<!-- iCheck -->
	<script	src="${ctx}/js/plugins/iCheck/icheck.min.js"></script>
	<script src="${ctx}/js/trunk/task/taskHandlerDetail.js?version=1.1.1"></script>
	<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<!-- 分页控件  -->
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script src="${ctx}/js/plugins/jquery.custom.js"></script>
	<!-- 必须JS -->
	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>


<script id="template_belongAndTransfer" type= "text/html">

	{{each rows as item index}}
	<tr class="tr-1 task_choice" name="{{item.ID}}">
		<td  align="center">
			<p class="big"><input type="checkbox" class="checkbox_input"  value="{{item.ID}}" tValue="{{item.CASE_CODE}}" /></p>
		</td>
		<td>
			<p>{{item.REAL_NAME}}</p>
		</td>
		<td>
			<p class="caseCode_choice">{{item.CASE_CODE}}</p>
		</td>
		<td>
			<p>{{item.NAME}}</p>
		</td>

		<td>
			<p>{{item.PROPERTY_ADDR}}</p>
		</td>
		<td>
			<p>{{item.CREATE_TIME}}</p>
		</td>
	</tr>
	{{/each}}
</script>
<div class="modal inmodal in" id="leadingProForChang" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog" style="width: 800px;">
		<div class="modal-content animated fadeIn popup-box">
			<div class="modal_title">变更责任人</div>
			<form method="get" class="form_list">
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small" style="float:left;">案件编号</label>
						<div id="codeShow" style="width: 550px;float: left;"></div>
					</div>
				</div>
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">责任人</label>
						<input class="teamcode input_type" placeholder="" value=""  name="leadingProName" id="leadingProName"
							   hVal="" onclick="leadingProForChangeClick()" />
						<div class="input-group float_icon organize_icon" id="materialRefundUser">
							<i class="icon iconfont">&#xe627;</i>
						</div>
						<input type="hidden"   name="leadingProId" id="leadingProId"  value=""/>
					</div>
				</div>

				<div class="line">
					<div class="add_btn text-center" style="margin:15px 126px;">
						<button type="button" class="btn btn-success" id="leadingProSubmit" >确定</button>
						<button type="button" class="btn btn-grey ml5" id="leadingProClose">关闭</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
</content>


<input type="hidden" value="${orgId}" id="orgId" />
<input type="hidden" value="${userId}" id="userId" />
<input type="hidden" value="${detailCode}" id="detailCode" />
<input type="hidden" value="" id="changTaskId" />

<input type="hidden" value="" id="changCaseCode" />
<input type="hidden" value="${ID}" id="taskId" />
</body>
</html>