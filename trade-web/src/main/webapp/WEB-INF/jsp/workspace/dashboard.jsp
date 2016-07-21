<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%
response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
%>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Toastr style -->
    <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

    <!-- Gritter -->
    <link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">

    <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">

    <link href="${ctx}/css/plugins/fullcalendar/fullcalendar.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/fullcalendar/fullcalendar.print.css" rel='stylesheet' media='print'>

    <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">

    <link href="${ctx}/css/plugins/nouslider/jquery.nouislider.css" rel="stylesheet">

    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">

 	<link href="${ctx}/css/boxtemplate.css" rel="stylesheet">


	<!-- Toastr style -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox.css?v=2.1.5" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-buttons.css?v=1.0.5" />
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-thumbs.css?v=1.0.7" />

    <!-- Gritter -->
	<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"	rel="stylesheet">
	<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
	<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
	<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
	<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css"		rel="stylesheet">
	<link href="${ctx}/css/plugins/steps/jquery.steps.css" rel="stylesheet">
	<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">

	<!-- modal -->
	<link href="${ctx}/static/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>

	<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />
	<style type="text/css">
	*{font:14px '\5fae\8f6f\96c5\9ed1'}
	*{font:14px '\5fae\8f6f\96c5\9ed1'}
	.label{font-family: inherit;}
	.visibility{visibility: hidden;}
	.of{overflow: hidden;}
	.m0{margin: 0!important}
	.p0{padding: 0!important}
	.no-border{border: 0!important}
	.pl0{padding-left: 0!important}

	.warning-info .ibox-content{height: 385px;overflow-x:hidden;overflow-y: auto }
	.nullData {
	background: #fff url(../img/no-records.png) center center no-repeat; 
    }
	#div_target .ibox-content{
		height: 270px;overflow-x:hidden;overflow-y:auto;  padding: 9px 20px;
	}

			.case-num{
text-decoration: underline !important;
}
.case-num:HOVER{
text-decoration: underline !important;
}
.case-num:visited{
 text-decoration: underline !important;
}
</style>
<script type="text/javascript">
  	 function imgLoad(img){
	   		 img.parentNode.style.backgroundImage="url("+img.src+")";
	   	 }
  	 var showSta=false;
</script>
</head>

<body>
<!-- 模态框（Modal） -->
<div class="modal fade" id="mymodal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="background-color:#eaeaea;height:50px;">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">
			        <i class="icon-bell"></i> [Title]
				</h4>
			</div>
			<div class="modal-body">
				<!-- <p>[Message]</p>
				<p>今日任务</p>
				<div class="jqGrid_wrapper" >
					<table id="table_list_3"></table>
					<div id="pager_list_3"></div>
				</div>
				<p>提醒任务</p>
				<div class="jqGrid_wrapper" >
					<table id="table_list_4"></table>
					<div id="pager_list_4"></div>
				</div> -->
 				<div class="scroller" style="height:290px;">
				<table class="table table-striped">
                                <thead>
                                <tr>
                                	<th></th>
                                    <th>任务名称</th>
                                    <th>地址</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${transPlanVOList}" var="item">
                                 	<tr>
		                              <c:choose>
										<c:when test="${item.dayDescription=='今日'}">
											<td><span class="label label-danger">${item.dayDescription}</span></td>
										</c:when>
										<c:otherwise>
											<td><span class="label label-warning">${item.dayDescription}</span></td>
										</c:otherwise>
										</c:choose>
										 	<td>${item.partCodeStr}</td>
                                    		<td><a href="${ctx}/task/${item.partCode}?taskId=${item.taskId}&caseCode=${item.caseCode}&instCode=${item.processInstanceId}" target="_blank">${item.propertyAddr}</a></td>
									</tr>
                                </c:forEach>
                               <!-- <tr>
                                	<td><span class="label label-danger">今日</span></td>
                                    <td>ZY-AJ-201506-0183</td>
                                    <td>上海浦东二区联洋片区丁香路1299弄</td>
                                </tr>
                                <tr>
                                	<td><span class="label label-danger">今日</span></td>
                                    <td>ZY-AJ-201506-0183</td>
                                    <td>上海浦东二区联洋片区丁香路1299弄</td>
                                </tr>
                                <tr>
                                	<td><span class="label label-warning">提醒</span></td>
                                    <td>ZY-AJ-201506-0183</td>
                                    <td>上海浦东二区联洋片区丁香路1299弄</td>
                                </tr> -->
                                </tbody>
                            </table>
                </div>
			</div>
			<div class="modal-footer" style="height:70px;">
				<button type="button" class="btn btn-default" data-dismiss="modal">[BtnCancel]</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" id="cacheRemain"> 不再提醒 </button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="row wrapper border-bottom white-bg page-heading">
                    <div class="col-lg-10">
                        <h2></h2>
                        <ol class="breadcrumb">
                            <li class="active">
                                <a>个人工作台</a>
                            </li>
                        </ol>
                    </div>
                    <div class="col-lg-2">
                    </div>
                </div>
<div class="wrapper wrapper-content">
	<div class="row">
                        <div class="col-md-9 " id="div_target">
                            <div class="ibox float-e-margins no-records">
                                <div class="ibox-title">
                                    <h5>本月目标达成度</h5>
                                </div>
                                <div class="ibox-content" >
                                    <!-- <div class="row">
                                        <div class="col-md-12">
                                            <h4 class="pull-left">E+金融面签金额达标率</h4>
                                            <span class="pull-right text-warning font-bold">48.2%</span>
                                        </div>
                                    </div>
                                    <div class="progress progress-striped">
                                        <div style="width: 48.2%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="48.2" role="progressbar" class="progress-bar progress-bar-warning">
                                            <span class="sr-only">40% Complete (success)</span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <h4 class="pull-left">贷款流失达标率</h4>
                                            <span class="pull-right text-warning font-bold">92.6%</span>
                                        </div>
                                    </div>
                                    <div class="progress progress-striped">
                                        <div style="width: 92.6%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="92.6" role="progressbar" class="progress-bar progress-bar-warning">
                                            <span class="sr-only">40% Complete (success)</span>
                                        </div>
                                    </div> -->
                                     <input type="hidden" id="serviceDepHierarchy" value="${sessionUser.serviceDepHierarchy }">
                                     <input type="hidden" id="userId" value="${sessionUser.id }">
                                     <input type="hidden" id="serviceDepId" value="${sessionUser.serviceDepId }">
                                     <input type="hidden" id="serviceJobCode" value="${sessionUser.serviceJobCode }">
                                     <input type="hidden" id="startDate" value="${startDate}">
                                      
                                    <div id="mainwe" style="height:250px;width:100%;"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="panel panel-danger">
                                <div class="panel-heading">
                                    红灯任务
                                </div>
                                <div class="panel-body">
                                    <div class="row vertical-align">
                                        <div class="col-xs-3">
                                            <i class="fa fa-bell fa-2x text-danger"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <a href="${ctx }/workspace/ryLightList?color=0" target="_blank"><h2 class="font-bold text-danger">${redLight }</h2></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-warning">
                                <div class="panel-heading">
                                    黄灯任务
                                </div>
                                <div class="panel-body">
                                    <div class="row vertical-align">
                                        <div class="col-xs-3">
                                            <i class="fa fa-bell fa-2x text-warning"></i>
                                        </div>
                                        <div class="col-xs-9 text-right">
                                            <a href="${ctx }/workspace/ryLightList?color=1" target="_blank"><h2 class="font-bold text-warning">${yeLight }</h2></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  <!-- 计划任务开始 -->
                  <c:if test="${isBackTeam }">
                  <shiro:hasPermission name="TRADE.WORKSPACE.WORKLOAD.CONSULTANT.END">
                  <!-- 交管顾问模块开始 -->
                  <div class="row">
                        <div class="col-md-12">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>工作数据显示</h5>
                                </div>
                                <div class="ibox-content">
                                    <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>任务</th>
                                    <th>今天</th>
                                    <th>明天</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${workLoadConsultant}"  var="item">
                                <tr>
                                    <td>${item.name }</td>
                                    <td>${item.tCount }</td>
                                    <td>${item.yCount }</td>
                                </tr>
                                </c:forEach>
                                 <c:if test="${empty workLoadConsultant}">
                                    <tr>
                                    <td>无</td>
                                    <td></td>
                                    <td></td>
                                	</tr>
                                 </c:if>
                                </tbody>
                            </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 交管顾问模块结束 -->
                    </shiro:hasPermission>
                    <shiro:hasPermission name="TRADE.WORKSPACE.WORKLOAD.MANAGE.END">
                    <!-- 交易主管模块开始 -->
                    <div class="row">
                        <div class="col-md-12">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>工作数据显示</h5>
                                </div>
                                <div class="ibox-content">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>任务</th>
                                    <th>今天</th>
                                    <th>明天</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${managerWorkLoad.listByTask}"  var="item">
                                <tr>
                                    <td>${item.name }</td>
                                    <td>${item.tCount }</td>
                                    <td>${item.yCount }</td>
                                </tr>
                                </c:forEach>
                                <c:if test="${empty managerWorkLoad.listByTask}">
                                    <tr>
                                    <td>无</td>
                                    <td></td>
                                    <td></td>
                                	</tr>
                                 </c:if>
                                </tbody>
                            </table>
                                        </div>
                                        <div class="col-md-8">
                                <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>人员</th>
                                    <th>今天</th>
                                    <th>明天</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${managerWorkLoad.listByUser}"  var="item">
                                <tr>
                                    <td>${item.userName }</td>
                                    <td>${item.tCountStr }</td>
                                    <td>${item.yCountStr }</td>
                                </tr>
                                </c:forEach>
                                 <c:if test="${empty managerWorkLoad.listByUser}">
                                    <tr>
                                    <td>无</td>
                                    <td></td>
                                    <td></td>
                                	</tr>
                                 </c:if>
                                </tbody>
                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </shiro:hasPermission>
                    </c:if>
                    <!-- 交易主管模块结束 -->

                  <!-- 计划任务结果 -->
                  <c:if test="${!isBackTeam }">
       <shiro:hasPermission name="TRADE.WORKSPACE.WORKLOAD.CONSULTANT.FRONT">
					<div class="row">
                        <div class="col-lg-12">
                            <div class="ibox float-e-margins">
                                <div class="ibox-title">
                                    <h5>交易顾问工作数据显示</h5>
                                </div>
                                <div class="ibox-content">
                                    <div class="row">
                                        <div class="col-md-8">
                                       		 <div id="ionrange_4" class="ionr">
               								 </div>
                                        </div>
                                        <div class="col-md-2">
                                        <select class="form-control m-b" id="sUserId">
							                        <option value="">默认</option>
								             	<c:forEach items="${uList}"  var="user">
							                        <option value="${user.id}">${user.realName}</option>
								             	</c:forEach>
                                    	</select>
                                        </div>
                                        <div class="col-md-2">
                                        <button class="btn btn-warning " type="button" id="btn_sta"><i class="fa fa-search"></i> <span class="bold">搜索</span></button>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <ul class="sortable-list connectList agile-list ui-sortable row">
                                                <li class="info-element">
                                                    E+申请金额
                                                    <div class="agile-detail">
                                                        <span class="fa-2x font-bold text-success case-num" id="sp_loanAmount">${sta.loanAmount }</span>
                                                    </div>
                                                </li>
                                                <li class="info-element">
                                                    E+面签金额
                                                    <div class="agile-detail">
                                                        <span class="fa-2x font-bold text-success case-num" id="sp_signAmount">${sta.signAmount }</span>
                                                    </div>
                                                </li>
                                                <li class="info-element">
                                                    E+转换率
                                                    <div class="agile-detail">
                                                        <span class="fa-2x font-bold text-success" id="sp_convRate">${sta.convRate }</span>
                                                    </div>
                                                </li>
                                                <li class="info-element">
                                                    E+放款金额
                                                    <div class="agile-detail">
                                                        <span class="fa-2x font-bold text-success case-num" id="sp_actualAmount">${sta.actualAmount }</span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="col-md-4">
                                            <ul class="sortable-list connectList agile-list ui-sortable row">
                                                <li class="warning-element">
                                                    评估费
                                                    <div class="agile-detail">
                                                        <span class="fa-2x font-bold text-warning case-num"  id="sp_evalFee">${sta.evalFee }</span>
                                                    </div>
                                                </li>
                                                <li class="warning-element">
                                                    评估费转化率
                                                    <div class="agile-detail">
                                                        <span class="fa-2x font-bold text-warning" id="sp_efConvRate">${sta.efConvRate }</span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="col-md-4">
                                            <ul class="sortable-list connectList agile-list ui-sortable row">
                                            	<%--<input type="hidden" id="isConsult" value="${isConsult} }"/>--%>
                                    			<%--<c:if test="${isConsult != '1'}">--%>
                                                <li class="danger-element" id="receiveOrFirstFollow">
                                                    接单数
                                                    <div class="agile-detail">
                                                        <span id="sp_receiveCount">
                                                        <a href="${ctx}/report/statis/caseDetail?status=received" target="_blank" class='case-num'><font  class="fa-2x font-bold text-danger">${sta.receiveCount }</font></a>
                                                        </span>
                                                    </div>
                                                </li>
                                    			<%--</c:if>--%>
                                    			<%--<c:if test="${isConsult == '1'}">
                                                <li class="danger-element" id="receiveOrFirstFollow">
                                                    首次跟进数
                                                    <div class="agile-detail">
                                                        <span id="sp_receiveCount">
                                                        <a href="${ctx}/report/statis/caseDetail?status=received" target="_blank" class='case-num'><font  class="fa-2x font-bold text-danger">${sta.receiveCount }</font></a>
                                                        </span>
                                                    </div>
                                                </li>
                                    			</c:if>--%>
                                                <li class="danger-element">
                                                    签约数
                                                    <div class="agile-detail">
                                                        <span  id="sp_signCount">
                                                        <a href="${ctx}/report/statis/historyTaskList?taskName=TransSign" target="_blank" class='case-num'><font class="fa-2x font-bold text-danger">${sta.signCount }</font></a>
                                                        </span>
                                                    </div>
                                                </li>
                                                <li class="danger-element">
                                                    贷款申请数
                                                    <div class="agile-detail">
                                                        <span id="sp_loanApplyCount">
                                                        <a href="${ctx}/report/statis/historyTaskList?taskName=ComLoanProcess" target="_blank" class='case-num'><font class="fa-2x font-bold text-danger">${sta.loanApplyCount }</font></a>
                                                        </span>
                                                    </div>
                                                </li>
                                                <li class="danger-element">
                                                    结案数
                                                    <div class="agile-detail">
                                                        <span id="sp_closeCount">
                                                        <a href="${ctx}/report/statis/historyTaskList?taskName=CaseClose" target="_blank" class='case-num'><font class="fa-2x font-bold text-danger">${sta.closeCount }</font></a>
                                                        </span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="ibox float-e-margins box-border">
                                                <div class="ibox-title">
                                                    <h5>贷款详情</h5>
                                                    <div class="ibox-tools">
                                                        <a class="collapse-link">
                                                        <i class="fa fa-chevron-up"></i>
                                                        </a>
                                                    </div>
                                                </div>
                                                <div class="ibox-content detail">
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <p class="font-bold">首付贷款</p>
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center font-bold">申请</th>
                                                                        <th class="text-center font-bold">面签</th>
                                                                        <th class="text-center font-bold">放款</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr id="sta_tr_30004009">
                                                                         <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <p class="font-bold">税费卡</p>
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center font-bold">申请</th>
                                                                        <th class="text-center font-bold">面签</th>
                                                                        <th class="text-center font-bold">放款</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr id="sta_tr_30004005">
                                                                         <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <p class="font-bold">消费贷款</p>
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center font-bold">申请</th>
                                                                        <th class="text-center font-bold">面签</th>
                                                                        <th class="text-center font-bold">放款</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr id="sta_tr_30004008">
                                                                         <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <p class="font-bold">换房贷款</p>
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center font-bold">申请</th>
                                                                        <th class="text-center font-bold">面签</th>
                                                                        <th class="text-center font-bold">放款</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr id="sta_tr_30004011">
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <p class="font-bold">抵押贷款</p>
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center font-bold">申请</th>
                                                                        <th class="text-center font-bold">面签</th>
                                                                        <th class="text-center font-bold">放款</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr id="sta_tr_30004012">
                                                                         <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <p class="font-bold">委托贷款</p>
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center font-bold">申请</th>
                                                                        <th class="text-center font-bold">面签</th>
                                                                        <th class="text-center font-bold">放款</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr id="sta_tr_30004013">
                                                                         <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <p class="font-bold">赎楼贷款</p>
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center font-bold">申请</th>
                                                                        <th class="text-center font-bold">面签</th>
                                                                        <th class="text-center font-bold">放款</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr id="sta_tr_30004007">
                                                                         <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <p class="font-bold">全款贷款</p>
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center font-bold">申请</th>
                                                                        <th class="text-center font-bold">面签</th>
                                                                        <th class="text-center font-bold">放款</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr id="sta_tr_30004006">
                                                                         <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <p class="font-bold">首付贷（抵押类）</p>
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center font-bold">申请</th>
                                                                        <th class="text-center font-bold">面签</th>
                                                                        <th class="text-center font-bold">放款</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr id="sta_tr_30004014">
                                                                         <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <p class="font-bold">佣金卡</p>
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th class="text-center font-bold">申请</th>
                                                                        <th class="text-center font-bold">面签</th>
                                                                        <th class="text-center font-bold">放款</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr id="sta_tr_30004015">
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                        <td class="text-muted">金额：0<br>单数：0<br>转化率：0.0%</td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <script type="text/javascript">
                                                        	var f,s,l;
                                                        	f=${sta.staLoanApply};
                                                        	s=${sta.staLoanSign};
                                                        	l=${sta.staLoanRelease};
                                                        	showSta=true;
                                                        </script>
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                            	 <div class="ibox hide" id="bt_1">
                                                            	 	<div class="ibox-title">
                                                            	 		<h5>E+贷款(面签单数)</h5>
                                                            	 	</div>
                                                            		<div class="ibox-content" id="">
                                                            			<div id="doughnutChart1"></div>
                                                            	 	</div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                            	 <div class="ibox hide" id="bt_2">
                                                            	 	<div class="ibox-title">
                                                            	 		<h5>E+贷款(面签金额)</h5>
                                                            	 	</div>
                                                            		<div class="ibox-content" id="doughnutChart">
                                                            			<div id="doughnutChart2"></div>
                                                            	 </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


      </shiro:hasPermission>
      </c:if>
   <div class="row">    <div class="col-lg-4">
	                  <div class="ibox float-e-margins no-records msGrid">
	                      <div class="ibox-title">
	                      		<span class="label label-success pull-right">0</span>
	                          <h5>反馈提醒</h5>
	                      </div>
	                      <div class="ibox-content">
	                          <div class="scroller" data-height="290px" data-always-visible="1" data-rail-visible="0">
						<div class="feed-activity-list" id="div_messagelist1">
						</div>
						</div>
	                      </div>
	                  </div>
	      </div>
	           <div class="col-lg-4">
	                  <div class="ibox float-e-margins no-records msGrid">
	                      <div class="ibox-title">
	                          <span class="label label-success pull-right">0</span>
	                          <h5>作业提醒</h5>
	                      </div>
	                      <div class="ibox-content">
	                          <div class="scroller" data-height="290px" data-always-visible="1" data-rail-visible="0">
						<div class="feed-activity-list" id="div_messagelist2" >
						</div>
						</div>
	                      </div>
	                  </div>
	      </div>
	           <div class="col-lg-4">
	                  <div class="ibox float-e-margins no-records msGrid">
	                      <div class="ibox-title">
	                          <span class="label label-success pull-right">0</span>
	                          <h5>止损提醒</h5>
	                      </div>
	                      <div class="ibox-content">
	                          <div class="scroller" data-height="290px" data-always-visible="1" data-rail-visible="0">
						<div class="feed-activity-list" id="div_messagelist3">
						</div>
						</div>
	                      </div>
	                  </div>
	      </div>

 </div>
 <!-- 龙虎榜Start -->
 <shiro:hasPermission name="TRADE.WORKSPACE.RANK">
 <div class="row top-list">
                        <div class="col-md-12">
                            <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>龙虎榜</h5>
                            </div>
                            <div class="ibox-content">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="panel panel-danger">
                                            <div class="panel-heading">E+金融申请榜<c:if test="${not empty rank.loanAmountRank}"> <span class="btn btn-xs btn-white pull-right"><strong class="text-danger">你的排名：${rank.loanAmountRank}</strong></span></c:if></div>
                                            <div class="panel-body">
                                                <div class="feed-activity-list">
                                                	<c:forEach items="${rank.loanAmountRankList}"  var="item">
                                                    <div class="feed-element">
                                                        <a href="#" class="pull-left">
                                                        <span class="shead img-circle">
															<img class="himg"  src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/${item.empCode }.jpg" onload="javascript:imgLoad(this);" >
														</span>
                                                        <span class="badge ${ item.rankNo == 1 ? "badge-danger" : item.rankNo == 2 ? "badge-orange" : item.rankNo == 3 ? "badge-warning" : "text-white" }">${item.rankNo }</span>
                                                        </a>
                                                        <div class="media-body ">
                                                            <span class="pull-right">
                                                            	<strong class="fa-2x text-danger">
                                                            		<fmt:formatNumber value="${item.rankValue/10000}" pattern='###,##0.00'/>万
                                                            	</strong>
                                                            </span> <!-- <fmt:formatNumber value="${item.rankValue/10000}" pattern='###,##0.00'/>万 -->
                                                            <strong>${item.realName }</strong><br>
                                                            <small class="text-muted">${item.belongOrgName }</small>
                                                        </div>
                                                    </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="panel panel-warning">
                                            <div class="panel-heading">E+金融签约榜<c:if test="${not empty rank.signAmountRank}" ><span class="btn btn-xs btn-white pull-right"><strong class="text-danger">你的排名：${rank.signAmountRank}</strong></span></c:if></div>
                                            <div class="panel-body">
                                                <div class="feed-activity-list">
                                                    <c:forEach items="${rank.signAmountRankList}"  var="item">
                                                    <div class="feed-element">
                                                        <a class="pull-left">
                                                        <span class="shead img-circle">
															<img class="himg"  src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/${item.empCode }.jpg" onload="javascript:imgLoad(this);" >
														</span>

                                                        <span class="badge ${ item.rankNo == 1 ? "badge-danger" : item.rankNo == 2 ? "badge-orange" : item.rankNo == 3 ? "badge-warning" : "text-white" }">${item.rankNo }</span>
                                                        </a>
                                                        <div class="media-body ">
                                                            <span class="pull-right">
                                                            	<strong class="fa-2x text-danger">
                                                            		<fmt:formatNumber value="${item.rankValue/10000 }" pattern='###,##0.00'/>万
                                                            	</strong>
                                                            </span>
                                                            <strong>${item.realName }</strong><br>
                                                            <small class="text-muted">${item.belongOrgName }</small>
                                                        </div>
                                                    </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="panel panel-info">
                                            <div class="panel-heading">E+金融放款榜<c:if test="${ not empty rank.actualAmountRank}"> <span class="btn btn-xs btn-white pull-right"><strong class="text-danger">你的排名：${rank.actualAmountRank}</strong></span></c:if></div>
                                            <div class="panel-body">
                                                <div class="feed-activity-list">
                                                     <c:forEach items="${rank.actualAmountRankList}"  var="item">
                                                    <div class="feed-element">
                                                        <a href="#" class="pull-left">
                                                        <span class="shead img-circle">
															<img class="himg"  src="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/${item.empCode }.jpg" onload="javascript:imgLoad(this);" >
														</span>
                                                        <span class="badge ${ item.rankNo == 1 ? "badge-danger" : item.rankNo == 2 ? "badge-orange" : item.rankNo == 3 ? "badge-warning" : "text-white" }">${item.rankNo }</span>
                                                        </a>
                                                        <div class="media-body ">
                                                            <span class="pull-right">
                                                            	<strong class="fa-2x text-danger">
                                                            		<fmt:formatNumber value="${item.rankValue/10000 }" pattern='###,##0.00'/>万
                                                            	</strong>
                                                            </span>
                                                            <strong>${item.realName }</strong><br>
                                                            <small class="text-muted">${item.belongOrgName }</small>
                                                        </div>
                                                    </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <%-- <div class="col-md-6">
                                        <div class="panel panel-success">
                                            <div class="panel-heading">10分满意度排行榜<span class="btn btn-xs btn-white pull-right"><strong class="text-success">你的排名：3</strong></span></div>
                                            <div class="panel-body">
                                                <div class="feed-activity-list">
                                                    <div class="feed-element">
                                                        <a href="#" class="pull-left">
                                                        <img alt="image" class="img-circle" src="${ctx }/img/a5.png">
                                                        <span class="badge badge-danger">1</span>
                                                        </a>
                                                        <div class="media-body ">
                                                            <span class="pull-right"><strong class="fa-2x text-danger">100%</strong>分</span>
                                                            <strong>李代明</strong>&nbsp;<span class="label label-warning">恭喜您</span><br>
                                                            <small class="text-muted">浦东贵宾服务部E组</small>
                                                        </div>
                                                    </div>
                                                    <div class="feed-element">
                                                        <a href="#" class="pull-left">
                                                        <img alt="image" class="img-circle" src="${ctx }/img/a5.png">
                                                        <span class="badge badge-orange">2</span>
                                                        </a>
                                                        <div class="media-body ">
                                                            <span class="pull-right"><strong class="fa-2x text-orange">97%</strong>分</span>
                                                            <strong>李代明</strong><br>
                                                            <small class="text-muted">浦东贵宾服务部E组</small>
                                                        </div>
                                                    </div>
                                                    <div class="feed-element">
                                                        <a href="#" class="pull-left">
                                                        <img alt="image" class="img-circle" src="${ctx }/img/a5.png">
                                                        <span class="badge badge-warning">3</span>
                                                        </a>
                                                        <div class="media-body ">
                                                            <span class="pull-right"><strong class="fa-2x text-warning">89%</strong>分</span>
                                                            <strong>李代明</strong><br>
                                                            <small class="text-muted">浦东贵宾服务部E组</small>
                                                        </div>
                                                    </div>
                                                    <div class="feed-element">
                                                        <a href="#" class="pull-left">
                                                        <img alt="image" class="img-circle" src="${ctx }/img/a5.png">
                                                        <span class="badge text-white">4</span>
                                                        </a>
                                                        <div class="media-body ">
                                                            <span class="pull-right"><strong class="fa-2x text-muted">79%</strong>分</span>
                                                            <strong>李代明</strong><br>
                                                            <small class="text-muted">浦东贵宾服务部E组</small>
                                                        </div>
                                                    </div>
                                                    <div class="feed-element">
                                                        <a href="#" class="pull-left">
                                                        <img alt="image" class="img-circle" src="${ctx }/img/a5.png">
                                                        <span class="badge text-white">5</span>
                                                        </a>
                                                        <div class="media-body ">
                                                            <span class="pull-right"><strong class="fa-2x text-muted">77%</strong>分</span>
                                                            <strong>李代明</strong><br>
                                                            <small class="text-muted">浦东贵宾服务部E组</small>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div> --%>
                                </div>
                            </div>
                        </div>
                        </div>
                    </div>
                    </shiro:hasPermission>
 <!-- 龙虎榜End -->
  <div class="portlet-body" style="display: block;">
	<a id="alertOper" class="fancybox-thumb" rel="fancybox-thumb"></a>
 </div>
<shiro:hasPermission name="TRADE.WORKSPACE.CALENDAR">
	 <div class="row">
	 	<div class="col-lg-12">
	        <div class="ibox float-e-margins col-heigth">
	            <div class="ibox-title">
	                <h5>待办事项 </h5>
	            </div>
	            <div class="ibox-content">
	                <div id="calendar"></div>
	            </div>
	        </div>
	    </div>
	</div>
</shiro:hasPermission>
</div>
<!-- import pageleve js -->
<content tag="local_script">
	<script src="${ctx}/js/plugins/fullcalendar/moment.min.js"></script>

	<script src="${ctx}/js/jquery-migrate-1.2.1.min.js"></script>

	<script src="${ctx}/js/app.js"></script>
	<script src="${ctx}/js/bootstrap-fileupload.js"></script>


	<script src="${ctx}/js/jquery.uniform.min.js"></script>
	<script src="${ctx}/js/jquery.cookie.min.js"></script>


 	 <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>

	<script type="text/javascript" src="${ctx}/js/clockface.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.inputmask.bundle.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/bootstrap-modalmanager.js" type="text/javascript" ></script>

	<!-- Mainly scripts -->


	<!-- jQuery UI custom -->
	<script src="${ctx}/js/jquery-ui.custom.min.js"></script>
	<!-- iCheck -->
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script>


	<!-- IonRangeSlider -->
	<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<!-- Slider -->
	<script src="${ctx}/js/plugins/nouslider/jquery.nouislider.min.js"></script>
	<script src="${ctx}/js/messageGrid.js"></script>
	<!-- Full Calendar -->
 	<script src="${ctx}/js/plugins/fullcalendar/fullcalendar.min.js"></script>
	<script src="${ctx}/js/plugins/fullcalendar/zh-cn.js"></script>


    <!-- Peity -->
    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>

	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox.js?v=2.1.5"></script>
	<!-- Add Button helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-buttons.js?v=1.0.5"></script>
	<!-- Add Thumbnail helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-thumbs.js?v=1.0.7"></script>
	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-media.js?v=1.0.6"></script>

	<script src="${ctx}/js/jquery.formatMoney.js"></script>
	<!-- ChartJS-->
    <script src="${ctx}/js/plugins/morris/raphael-2.1.0.min.js"></script>
    <script src="${ctx}/js/plugins/morris/morris.js"></script>

	<script src="${ctx}/js/trunk/dashboard/dashboard.js?v=1.0.1"></script>
    <script src="${ctx}/js/trunk/case/caseCount.js"></script>
        <%-- <jsp:include page="/WEB-INF/jsp/common/modal.jsp"></jsp:include> --%>
    <script src="${ctx}/static/js/aist-modal.js"  type="text/javascript"></script>
	<script src="${ctx}/static/js/bootstrap-modal.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/bootstrap-modalmanager.js" type="text/javascript"></script>
    <script src="${ctx}/js/plugins/chartJs/echarts.js" type="text/javascript"></script>
	<script src="${ctx}/js/trunk/echarts/dashboard.js" type="text/javascript"></script>
    <script>

    jQuery(document).ready(function() {

    	reloadMonth();
    	var isJygw = ${isJygw};
   	 	if(isJygw) {
	    	Modal.alert({
	    		Title : '任务小卫士',
	    		Message : ''
	    	})
   	 	}
   	 	setStaDetailDef();
   	 	if(showSta){
   	 		setStaVal(f,s,l);
   	 		var d1 =toDonutData(s,'count');
   	 		var d2 =toDonutData(s,'amount');
   	 		$.each(d2,function(j,item){
   	 			item.value = item.value*10000;
   	 		})
   	 		setDonut(d1,d2);
   	 	}

	   	 $('#cacheRemain').click(function(){
	   		 $.ajax({
				    cache : false,
				    type:'POST',
				    url : ctx+'/workspace/cacheRemain',
				    dataType: 'json',
					success: function(data) {
						//alert(data.message);
					}
				});
	   	 })

    	$("#btn_sta").click(function(){//统计按钮
    		queryConutCaseByDate();
    	});
	    $('#sp_evalFee').on('click',evalFeeClick);

	    //queryConutCaseByDate();
	    
	    //加载echarts
	    reloadStatus();
    });
    </script>
 </content>
</body>
</html>