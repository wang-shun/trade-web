<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<!-- Morris -->
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">

<style type="text/css">
.text-center{text-align:center;}
.case-num,.case-task { text-decoration: underline !important;}
.case-num:HOVER,case-task:HOVER{
	text-decoration: underline !important;
}
.case-num:visited,case-task:visited{
	text-decoration: underline !important;
}
.slash{font-weight:bold !important;}

.hint { position: relative; display: inline-block; }

.hint:before, .hint:after {
			position: absolute;
			opacity: 0;
			z-index: 1000000;
			-webkit-transition: 0.3s ease;
			-moz-transition: 0.3s ease;
  pointer-events: none;
}		
.hint:hover:before, .hint:hover:after {
	opacity: 1;
}
.hint:before {
	content: '';
	position: absolute;
	background: transparent;
	border: 6px solid transparent;
	position: absolute;
}	
.hint:after {
	content: attr(data-hint);
	background: rgba(0, 0, 0, 0.8);
			color: white;
			padding: 8px 10px;
			font-size: 12px;
	white-space: nowrap;
	box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.3);
}

/* top */
.hint-top:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}
.hint-top:hover:before {
	margin-bottom: -10px;
}
.hint-top:hover:after {
	margin-bottom: 2px;
}

/* left */
.hint-left:before {
	bottom: 50%;
	margin: 0 0 0 -5px;
	border-left-color: rgba(0, 0, 0, 0.8);
}		
.hint-left:after {
	bottom: 0;
	right: 45px;
}
.hint-left:hover:before {
	margin-right: -10px;
}
.hint-left:hover:after {
	margin-right: 2px;
}

#queryContent{display:inline-block;width:62%;margin-left:15px;height:32px;}

/*新增样式*/
@charset "utf-8";
*{padding:0;margin:0;box-sizing:border-box;}
.main{background-color:#f3f3f3;}
.apply-wrap{background-color:#fff;font-family:'微软雅黑','Arial';}
.apply-wrap .table{font-size:14px;}
.apply-wrap .table{width:100%;background-color:#fff;}
.apply-wrap .apply-table{width:100%;border:1px solid #eaeaea;border-right:0;}
.apply-wrap .apply-table th{font-weight:normal;color:#fff;height:35px;background-color:#52cdec;}
.apply-table th,.apply-table td{text-align:left;padding-left:10px;border-right:1px solid #eaeaea;}
.apply-table tr td{color:#333;}
.apply-table .tr-1{background-color:#fff;}
.apply-table .tr-2{background-color:#f4f4f4;}
.apply-table tr:nth-child(odd) td{padding:12px 0 3px 10px;}
.apply-table tr:nth-child(even) td{padding:3px 0 12px 10px;}
.apply-table tr .color-666{color:#666;}
.apply-table tr .fs12{font-size:12px;}
.apply-table tr .btn-y{color:#fff;padding:2px 10px;border-radius:3px;background-color:#f9ad58;text-decoration:none;}
.triangle-up,.triangle-down{width:0;height:0;border-left:7px solid transparent;border-right:7px solid transparent;position:relative;left:8px;top:16px;}
.triangle-up{border-bottom:10px solid #fff;top:-13px;}
.triangle-down {border-top:10px solid #fff;}
.fs12 i,.sqr i{color:#fff;font-size:12px;font-style:normal;padding:1px 3px;margin-right:5px;border-radius:2px;background-color:#52cdec;}
.fs12 i.valid-label{background-color:#00cb1d;}
.fs12 i.invalid-label{background-color:#ccc;}
.sqr i.jl-label{background-color:#ee6384;margin-left:5px;}
.sqr i.yc-label{background-color:#52cdec;margin-left:5px;}
.fs12 span{display:block;padding:3px 0;}
.apply-table tr .sq-state{padding:2px 0 2px 10px !important;}
.apply-table tr:nth-child(odd) td.btn-g{padding:10px 0 4px 10px;}
.apply-table tr:nth-child(even) td.btn-g{padding:5px 0 10px 10px;}
.apply-table tr td.invalid{width:150px;padding-right:10px !important;}
.apply-table tr em{font-style:normal;font-size:13px;line-height:18px;}
.apply-table tbody a{color:#1a5f8e;}
/* 任务列表新增样式 */
.task-list tr td.pl10{padding:0 0 0 10px;}
.task-list tr td .case-ctm{font-size:12px;}
.task-list .ctm-tag {width: 13px;height: 14px;border-radius: 2px;color: #fff;font-family: "Arial";font-size: 16px;line-height: 0;padding: 0 4px 1px;background-color: #ccc;margin-right: 5px;}
.task-list .salesman-icon {width: 15px;height: 16px;display: inline-block;background: url(${ctx}/img/data_salary_icon.png) no-repeat center;vertical-align: -3px;margin-right: 5px;}
.task-list .salesman-info{font-size: 12px;text-decoration: none;}
/*起始结束图标样式*/
.sl-lable{color:#fff;font-size:12px;font-style:normal;padding:1px 3px;margin-right:5px;border-radius:2px;background-color:#52cdec;}

.hideDiv{display: none;}
#inTextVal {
		width:200px;
	}
.form-group .control-label {
   padding-top: 7px;
   margin-bottom: 0;
   text-align: right;
}
.form-group {
    padding-bottom: 0px;
    margin-bottom:6px;
}

#teamCode {
	background-color:#fff !important;
	width:210px!important;
	margin-bottom:5px;
}
.select_btn {
margin-left:15px;}

.sn {
width:110px;}
.form-control {
border-radius:3px!important;}
.mt10 {
margin-top:6px!important;}
.form-mt {
margin-left:15px;}
[id^="dateDiv_"]:not(#dateDiv_0) .chosen-container{margin-left:33px}
.bb1{
	white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width:94px;
    border:none;
    background-color:#fff;
}
.bb2{
	white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width:94px;
    border:none;
    background-color:#f4f4f4;
}
.btnx1{width:120px; margin-left:146px;}
.btnx2{width:120px; margin-left:10px;}
.case_time {
	padding-left:28px;
	height:34px;
	line-height:34px;
	background: #dfdfdf;
}
.case_time span {
	margin-right:10px;
}
</style>
</head>

<body class="main">
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="taskName" value="${taskName}" />
	<input type="hidden" id="handleTimeStart" value="${handleTimeStart}" />
	<input type="hidden" id="handleTimeEnd" value="${handleTimeEnd}" />
	<input type="hidden" id="org" value="${org}" />
	<input type="hidden" id="consultantId" value="${consultantId}" />
	<input type="hidden" id="isConsultant" value="${isConsultant}" />

	<div class="row">
		<form action="#" method="post" id="excelForm"></form>
		
		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="col-lg-12 col-md-12">
			<div class="ibox float-e-margins">	
				<div class="ibox-title">
					<h5>已完成任务列表</h5>
				</div>
				<div class="ibox-content">
					<div class="row">
						
                        <div class="col-md-12 form-mt2 ${isConsultant ? 'hideDiv' : ''}">    
                            <div class="form-group col-sm-5">
                            	<label class="col-md-1 control-label sn">经办人</label>
                                <div class="col-md-8">
                                <input type="text" id="inTextVal" style="background-color:#FFFFFF" name="radioOrgName" class="form-control tbspuser" hVal="${consultantId}" value="${consultantName }"
									readonly="readonly" onclick="userSelect({startOrgId:'${depId}',expandNodeId:'${depId}',
									nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" />
                                 </div>
                             </div>
                       
							<div class="form-group col-sm-6">
								<label class="col-md-1 control-label sn">组织范围</label>
								<div class="col-md-8">
								<input type="text" class="span12 tbsporg org-label-control form-control" id="teamCode" name="teamCode" readonly="readonly" 
										   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'${depId}',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,
										   expandNodeId:''})" value="${orgName}"/>
										 <input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId" value="${org}"/> 
									</div>
								</div>
							</div>
                       
							<div class="col-md-12 form-mt2 form-mt">
								<div class="form-group">
									<label class="col-md-1 control-label sn">任务名</label>
									<div class="col-md-10 control-div">
										<aist:dict id="queryTaskName" name="queryTaskName"
										clazz="btn btn-white chosen-select" display="select"
										dictType="part_code" defaultvalue="${taskName}"/>
									</div>
								</div>
 							</div>
 							
							<div class="col-md-12 form-mt" style="margin-top:5px;margin-bottom:5px;">
								<div class="form-group">
									<label class="col-md-1 control-label sn">任务结束时间</label>
									<div class="col-md-10 control-div">
									<div id="datepicker_0" class="input-group input-medium date-picker input-daterange pull-left" data-date-format="yyyy-mm-dd">
										<input id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px;" type="text" value="${handleTimeStart}" placeholder="起始日期">
										<span class="input-group-addon">到</span>
										<input id="dtEnd_0" name="dtEnd" class="form-control" style="font-size: 13px;" type="text" value="${handleTimeEnd}" placeholder="结束日期" />
									</div>
									</div>
								</div>
 							</div> 							
				
							<div class="col-md-12 form-mt" style="margin-top:5px;">
								<div class="form-group ">
									<label class="col-md-1 control-label sn"></label>
									<div class="control-div col-md-10">
									<select id="queryItems" data-placeholder= "搜索条件设定" class= "btn btn-white chosen-select" style="float :left;">
										<option value="1" selected>产证地址</option>
										<option value="2">经纪人姓名</option>
										<option value="3">所属分行</option>
										<option value="4">案件编号</option>
										<option value="5">CTM编号</option>
									</select>
									<input id="queryContent" type="text" class="col-md-6 form-control pull-left">
									</div>
								</div>
							</div>
							
					</div>
				</div>
						
				<div class="col-md-12" style="margin-bottom:5px;">
					<button id="searchButton" type="button" class="btn btn-warning btnx1">查询</button>
					<button id="exportExcel" type="button" class="btn btn-warning btnx2">导出Excel报表</button>
				</div>

			</div>
		</div>
	</div>
	</div>
	
	
	<div class="apply-wrap row">
	<p class="clearfix case_time">
		<span class="sort" sortColumn="wf.CASE_CODE" sord="desc">案件编号</span>
		<span class="sort" sortColumn="historytask.START_TIME" sord="desc">处理开始时间</span>
		<span class="sort" sortColumn="historytask.END_TIME" sord="desc">处理结束时间</span>
		</p>
	<div class="table">
		<table class="apply-table task-list" border="0" cellpadding="0" cellspacing="0" vertical="middle">
			<thead>
				<th>案件编号</th>
				<th>任务名</th>
				<th>产证地址</th>
				<th>处理时间</th>
				<th>经办人</th>
				<th>客户</th>
			</thead>
			<tbody id="myTaskList">
				
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
	</div>

<content tag="local_script"> 
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>	
	<!-- Peity --> 
	<!-- Custom and plugin javascript -->
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
	<!-- block UI -->
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	<!-- iCheck -->
	<script	src="${ctx}/js/plugins/iCheck/icheck.min.js"></script>
	<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<!-- 分页控件  -->
    <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<!-- 排序插件 -->
	<script src="${ctx}/js/plugins/jquery.custom.js"></script>	
	<!-- 自定义js -->
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<!-- 个人js -->
	<script src="${ctx}/js/trunk/report/history_task_list.js"></script>
	<!-- JS模板引擎 -->
	<script id="template_myTaskList" type= "text/html">
      {{each rows as item index}}
		{{if index%2 == 0}}
 			<tr class="tr-1">
				<td><a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a></td>
				<td rowspan="2" class="pl10">
					<a class="hint hint-top" data-hint="{{item.TASK_NAME}}">
						<input type="text" maxlength="6" readonly="readonly" class="bb1" value="{{item.TASK_NAME}}"/>
					</a>
				</td>
				<td>{{item.PROPERTY_ADDR}}</td>
				<td><i class="sl-lable">始</i>{{item.START_TIME}}</td>
				<td rowspan="2" class="pl10">
					<a class="hint  hint-left" data-hint="电话: {{item.CONSULTANT_TEL}}  所属组织: {{item.YUCUI_ORG_ID}}">
						{{item.CONSULTANT_NAME}}
					</a>
				</td>
				<td>上家: {{item.SELLER}}</td>
			</tr>
        {{else}}
            <tr class="tr-2">
				<td><a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a></td>
				<td rowspan="2" class="pl10">
					<a class="hint hint-top" data-hint="{{item.TASK_NAME}}">
						<input type="text" maxlength="6" readonly="readonly" class="bb2" value="{{item.TASK_NAME}}"/>
					</a>
				</td>
				<td>{{item.PROPERTY_ADDR}}</td>
				<td><i class="sl-lable">始</i>{{item.START_TIME}}</td>
				<td rowspan="2" class="pl10">
					<a class="hint  hint-left" data-hint="电话: {{item.CONSULTANT_TEL}}  所属组织: {{item.YUCUI_ORG_ID}}">
						{{item.CONSULTANT_NAME}}
					</a>
				</td>
				<td>上家: {{item.SELLER}}</td>
			</tr>
        {{/if}}

		{{if index%2 == 0}}
 			<tr class="tr-1">
        {{else}}
            <tr class="tr-2">
        {{/if}}
				<td><span class="ctm-tag">C</span><span class="case-ctm">{{item.CTM_CODE}}</span></td>
				<td>
					<i class="salesman-icon"></i>
					<a class="hint  hint-top" data-hint="直管经理: {{item.MANAGER_INFO.realName}}  电话: {{item.MANAGER_INFO.mobile}}">
						{{item.AGENT_NAME}}<span class="slash">/</span>{{item.AGENT_PHONE}}<span class="slash">/</span>{{item.GRP_NAME}}
					</a>
				</td>
				<td><i class="sl-lable">完</i>{{item.END_TIME}}</td>
				<td>下家: {{item.BUYER}}</td>
			</tr>
       {{/each}}		
	</script> 
</content>
</body>
</html>
