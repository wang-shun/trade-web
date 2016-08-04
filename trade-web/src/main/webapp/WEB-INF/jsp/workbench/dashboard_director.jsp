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

    <!-- stickUp fixed css -->
    <link href="${ctx}/static/css/plugins/stickup/stickup.css" rel="stylesheet">
    <%-- <link href="${ctx}/static/trans/css/common/stickDash.css" rel="stylesheet"> --%>

    <link href="${ctx}/static/css/plugins/aist-steps/steps.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">
	
	<!-- ION-RANGESLIDER -->
	<link href="${ctx}/static/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">

	<!-- morris -->
	<link href="${ctx}/static/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
	
	<link href="${ctx}/static/trans/css/common/stickDash.css" rel="stylesheet">
	
    <!-- index_css  -->
    <link href="${ctx}/static/trans/css/workbench/dashboard/dashboard.css" rel="stylesheet">
</head>

<script type="text/javascript">
  	 function imgLoad(img){
	   		 img.parentNode.style.backgroundImage="url("+img.src+")";
	   	 }
  	 var showSta=false;
</script>
<body>
<input type="hidden" id="serviceDepHierarchy" value="${sessionUser.serviceDepHierarchy }">
<input type="hidden" id="userId" value="${sessionUser.id }">
<input type="hidden" id="serviceDepId" value="${sessionUser.serviceDepId }">
<input type="hidden" id="serviceJobCode" value="${sessionUser.serviceJobCode }">
<input type="hidden" id="startDate" value="${startDate}">


<!-- main Start -->
<div class="row wrapper border-bottom white-bg page-heading stickup-nav-bar">
	<ul class="nav navbar-nav">
    	<li class="menuItem active"><a href="#base_info">基本信息</a></li>
        <li class="menuItem"><a href="#zj_info">资金流水</a></li>
	</ul>
</div>
            
<div class="row">
	<div class="wrapper wrapper-content animated fadeInUp">
	
     	<!-- <div class="ibox"> -->
        <div class="ibox-content" id="base_info">
        	<div class="row" style="position: relative;">
            	<h5 class="main_titile" style="position:absolute;top:0;left:25px;font-size: 14px;">案件分布统计</h5>
                <div class="col-md-9">
                	<div id="mainwe" style="width:100%;height:250px;"></div>
                </div>
                    <div class="col-md-3">
                    	<div class="task_light">
                        	<p class="fa_red">
                        		<i class="fa fa-bell "></i>
                        		红灯任务
                        		<small><a href="${ctx }/workspace/ryLightList?color=0" target="_blank"> ${redLight }</a></small>
                        	</p>
                        	<p class="fa_orange">
                        		<i class="fa fa-bell "></i>
                        		黄灯任务
                        		<small><a href="${ctx }/workspace/ryLightList?color=0" target="_blank">${yeLight }</a></small>
                        	</p>
                    	</div>
                    </div>
            </div>
        </div>



        <div class="ibox-content" id="zj_info">
        	<div class="row m-t-sm" id="">
            	<div class="col-lg-12">
                	<div class="panel blank-panel">
                    	<div class="panel-heading">
                        	<div class="panel-options">
                            	<ul class="nav nav-tabs">
                                	<li class="active">
                                    	<a href="#tab-1" data-toggle="tab">交易顾问工作数据显示</a>
                                    </li>
                                    <li class="">
                                    	<a href="#tab-2" data-toggle="tab">贷款详情</a>
                                    </li>
                                    <li class="">
                                    	<a href="#tab-3" data-toggle="tab">E＋贷款</a>
                                    </li>
                                    <li class="">
                                    	<a href="#tab-4" data-toggle="tab">业务提醒</a>
                                    </li>
                                    <li class="">
                                    	<a href="#tab-5" data-toggle="tab">龙虎榜</a>
                                    </li>
                            	</ul>
                         	</div>
                         </div>

                         <div class="panel-body">
                         	<div class="tab-content">
                            	<div class="tab-pane active" id="tab-1">
                                	<div class="row">
                                    	<div class="col-md-8">
                                       		 <div id="ionrange_4" class="ionr"></div>
                                        </div>
                                        <div class="col-md-2">
                                        	<select class="form-control m-b" id="sUserId">
                                        	<option value="">默认</option>
								            <c:forEach items="${uList}"  var="user">
							                <option value="${user.id}">${user.realName}</option>
								            </c:forEach>
                                        	</select>
                                        </div>
                                        <div class="col-md-2" style="padding-left:0">
                                        	<button class="btn btn-warning " type="button" id="btn_sta">
                                        		<i class="fa fa-search"></i>
                                        		<span class="bold">搜索</span>
                                            </button>
                                        </div>
                                    </div>

                                    <div class="data-progress-wrap">
                                    	<div class="data-progress data1">
                                        	<div class="data-left">
                                            	<span class="left-label h50 pt10">E+申请金额</span>
                                                <div class="data-bar">
                                                	<div class="progress progress-small">
                                                    	<div style="width: 100%;" class="progress-bar bar-yellow"></div>
                                                    </div>
                                                </div>
                                                <span class="right-label pt10" id="sp_loanAmount">4000万</span>
                                            </div>
                                            <div class="data-left">
                                            	<span class="left-label">E+面签金额</span>
                                                <div class="data-bar">
                                                	<div class="progress progress-small">
                                                    	<div style="width: 80%;" class="progress-bar bar-yellow"></div>
                                                    </div>
                                                </div>
                                                <span class="right-label" id="sp_signAmount">2000万</span>
                                            </div>
                                            <div class="data-left">
                                            	<span class="left-label">E+放款金额</span>
                                                <div class="data-bar">
                                                	<div class="progress progress-small">
                                                    	<div style="width: 56%;" class="progress-bar bar-yellow"></div>
                                                    </div>
                                                 </div>
                                                 <span class="right-label" id="sp_actualAmount">2000万</span>
                                            </div>
                                            <div class="data-left">
                                            	<span class="left-label h50 pb10">评估费</span>
                                                <div class="data-bar">
                                                	<div class="progress progress-small">
                                                    	<div style="width: 30%;" class="progress-bar bar-red"></div>
                                                 	</div>
                                                 </div>
                                                 <span class="right-label pb10" id="sp_evalFee">6万</span>
                                            </div>
                                        </div>
                                        <div class="data-progress data2">
                                                            <div class="data-left">
                                                                <span class="left-label wd105 h90 pt50">E+转换率</span>
                                                                <div class="data-bar">
                                                                    <div class="progress progress-small">
                                                                        <div style="width: 100%;" class="progress-bar bar-yellow"></div>
                                                                    </div>
                                                                </div>
                                                                <span class="right-label pt50">100%</span>
                                                            </div>
                                                            <div class="data-left">
                                                                <span class="left-label wd105 h90 pb20">评估费转换率</span>
                                                                <div class="data-bar">
                                                                    <div class="progress progress-small">
                                                                        <div style="width: 30%;" class="progress-bar bar-red"></div>
                                                                    </div>
                                                                </div>
                                                                <span class="right-label pt40">30%</span>
                                                            </div>
                                        </div>
                                        <div class="data-progress data3">
                                        	<div class="data-left">
                                                                <span class="left-label wd64 h50 pt10">接单数</span>
                                                                <div class="data-bar">
                                                                    <div class="progress progress-small">
                                                                        <div style="width: 40%;" class="progress-bar bar-blue"></div>
                                                                    </div>
                                                                </div>
                                                                <span class="right-label pt10">43单</span>
                                            </div>
                                            <div class="data-left">
                                                                <span class="left-label wd64 ">签约数</span>
                                                                <div class="data-bar">
                                                                    <div class="progress progress-small">
                                                                        <div style="width: 100%;" class="progress-bar bar-blue"></div>
                                                                    </div>
                                                                </div>
                                                                <span class="right-label">1亿&nbsp;&nbsp;4单</span>
                                            </div>
                                            <div class="data-left">
                                                                <span class="left-label wd64">贷款申请数</span>
                                                                <div class="data-bar">
                                                                    <div class="progress progress-small">
                                                                        <div style="width: 56%;" class="progress-bar bar-blue"></div>
                                                                    </div>
                                                                </div>
                                                                <span class="right-label">13单</span>
                                            </div>
                                            <div class="data-left">
                                                                <span class="left-label wd64 h50 pb10">结案数</span>
                                                                <div class="data-bar">
                                                                    <div class="progress progress-small">
                                                                        <div style="width: 30%;" class="progress-bar bar-blue"></div>
                                                                    </div>
                                                                </div>
                                                                <span class="right-label pb10">23单</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                                
                                <div class="tab-pane" id="tab-2">
                                	<table class="table table-striped table-bordered table-hover dataTables-example dataTable dtr-inline">
                                    <thead>
                                    	<tr>
                                        	<th>业务类型</th>
                                            <th>申请</th>
                                            <th>面签</th>
                                            <th>放贷</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<tr id="sta_tr_30004009">
                                                                <td>首付贷款</td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>99.99%</strong></span>
                                                                </td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>20%</strong></span>
                                                                </td>
                                                                 <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>30%</strong></span>
                                                                </td>
                                        </tr>
                                        <tr id="sta_tr_30004005">
                                                                <td>税费卡</td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>99.99%</strong></span>
                                                                </td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>20%</strong></span>
                                                                </td>
                                                                 <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>30%</strong></span>
                                                                </td>
                                                            </tr>                                        
                                        <tr id="sta_tr_30004008">
                                                                <td>消费贷款</td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>99.99%</strong></span>
                                                                </td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>20%</strong></span>
                                                                </td>
                                                                 <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>30%</strong></span>
                                                                </td>
                                        </tr>
                                        <tr id="sta_tr_30004011">
                                                                <td>换房贷款</td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>99.99%</strong></span>
                                                                </td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>20%</strong></span>
                                                                </td>
                                                                 <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>30%</strong></span>
                                                                </td>
                                        </tr>
                                        <tr id="sta_tr_30004012">
                                                                <td>抵押贷款</td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>99.99%</strong></span>
                                                                </td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>20%</strong></span>
                                                                </td>
                                                                 <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>30%</strong></span>
                                                                </td>
                                        </tr>
                                        <tr id="sta_tr_30004013">
                                                                <td>委托贷款</td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>99.99%</strong></span>
                                                                </td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>20%</strong></span>
                                                                </td>
                                                                 <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>30%</strong></span>
                                                                </td>
                                                            </tr>
                                      <tr id="sta_tr_30004007">
                                                                <td>赎楼贷款</td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>99.99%</strong></span>
                                                                </td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>20%</strong></span>
                                                                </td>
                                                                 <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>30%</strong></span>
                                                                </td>
                                                            </tr>
                                      <tr id="sta_tr_30004006">
                                                                <td>全款贷款</td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>99.99%</strong></span>
                                                                </td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>20%</strong></span>
                                                                </td>
                                                                 <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>30%</strong></span>
                                                                </td>
                                                            </tr>
                                      <tr id="sta_tr_30004014">
                                                                <td>首付贷（抵押类）</td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>99.99%</strong></span>
                                                                </td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>20%</strong></span>
                                                                </td>
                                                                 <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>30%</strong></span>
                                                                </td>
                                                            </tr>
                                       <tr id="sta_tr_30004015">
                                                                <td>佣金卡</td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>99.99%</strong></span>
                                                                </td>
                                                                <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>20%</strong></span>
                                                                </td>
                                                                 <td class="span_sign">
                                                                    <span>金额：<strong>9999万</strong></span>
                                                                    <span>单数：<strong>99</strong></span>
                                                                    <span>转化率：<strong>30%</strong></span>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>

                                                <div class="tab-pane" id="tab-3">
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


                                                <div class="tab-pane" id="tab-4">
                                                     <div class="row">
                                                        <div class="col-md-4">
                                                            <div class="widget-box">
                                                                <div class="widget-header widget-header-flat">
                                                                    <h4 class="smaller">反馈提醒</h4>
                                                                    <div class="widget-toolbar">
                                                                        <label>
                                                                           <span class="label label-blue">0</span>
                                                                        </label>
                                                                    </div>
                                                                </div>

                                                                <div class="widget-body">
                                                                    <div class="widget-main">
                                                                        <img src="../static/img/no_idea.jpg" alt="">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <div class="widget-box">
                                                                <div class="widget-header widget-header-flat">
                                                                    <h4 class="smaller">作业提醒</h4>
                                                                    <div class="widget-toolbar">
                                                                        <label>
                                                                           <span class="label label-blue">1</span>
                                                                        </label>
                                                                    </div>
                                                                </div>

                                                                <div class="widget-body">
                                                                    <div class="widget-main">
                                                                        <img src="../static/img/no_idea.jpg" alt="">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <div class="widget-box">
                                                                <div class="widget-header widget-header-flat">
                                                                    <h4 class="smaller">止损提醒</h4>
                                                                    <div class="widget-toolbar">
                                                                        <label>
                                                                           <span class="label label-blue">2</span>
                                                                        </label>
                                                                    </div>
                                                                </div>

                                                                <div class="widget-body">
                                                                    <div class="widget-main">
                                                                        <img src="../static/img/no_idea.jpg" alt="">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                     </div>
                                                </div>
                                                
                                                <!-- 龙虎榜 -->
                                                <div class="tab-pane" id="tab-5">
                                                    <div class="row dragon">
                                                        <div class="col-md-6">
                                                            <div class="panel panel-danger">
                                                                <div class="panel-heading">
                                                                	E+金融申请榜
                                                                	<c:if test="${not empty rank.loanAmountRank}">
                                                                	<span class="btn btn-xs btn-white pull-right">
                                                                		<strong class="text-danger">
                                                                			你的排名：${rank.loanAmountRank}
                                                                		</strong>
                                                                	</span>
                                                                	</c:if>                                                                	
                                                                </div>
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
                                                        <div class="col-md-6">
                                                            <div class="panel panel-warning">
                                                                <div class="panel-heading">
                                                                	E+金融签约榜
                                                                	<c:if test="${not empty rank.signAmountRank}" >
                                                                	<span class="btn btn-xs btn-white pull-right">
                                                                		<strong class="text-danger">
                                                                			你的排名：${rank.signAmountRank}
                                                                		</strong>
                                                                	</span>
                                                                	</c:if>
                                                                </div>
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
                                                                <div class="panel-heading">
                                                                	E+金融放款榜
                                                                	<c:if test="${ not empty rank.actualAmountRank}">
                                                                	<span class="btn btn-xs btn-white pull-right">
                                                                		<strong class="text-danger">你的排名：${rank.actualAmountRank}</strong>
                                                                	</span>
                                                                	</c:if>                                                                
                                                                </div>
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
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                </div>
            </div>
            <!-- main End -->

        </div>
    </div>
<content tag="local_script">
    <!-- stickup plugin -->
    <script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script>
    <script src="${ctx}/static/trans/js/workbench/stickDash.js"></script>

    <!-- Toastr script -->
    <script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script>
    <script src="${ctx}/static/js/morris/morris.js"></script>
    <script src="${ctx}/static/js/morris/raphael-min.js"></script>

	<!-- IonRangeSlider -->
	<script src="${ctx}/static/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
    
    <!-- ChartJS morris -->
    <script src="${ctx}/static/js/plugins/morris/raphael-2.1.0.min.js"></script>
    <script src="${ctx}/static/js/plugins/morris/morris.js"></script>
    
    <!-- ECharts.js -->
    <script src="${ctx}/static/js/echarts.min.js"></script>    
    <script src="${ctx}/static/trans/js/workbench/dashboard_echart.js"></script>
	
	<script type="text/javascript">
	 $(document).ready(function() {
		    //加载echarts
		    reloadStatus();
		    
		    //生成ion.rangeslider
			$("#ionrange_4").ionRangeSlider({
				values : [ "一月", "二月", "三月", "四月", "五月", "六月",
					"七月", "八月", "九月", "十月", "十一月", "十二月" ],
				dateType : 'single',
				hasGrid : true

			});
			var month = new Date().getMonth();
			var maxMonths = new Array("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
					"九月", "十月", "十一月", "十二月");
			var w = $(".irs-line").width() / 12 * (month) + 12.5;
			// 月份位置/添加月份文字/光标位置
			$(".irs-single").attr("style", "left: " + (month / 12 * 100 + 3) + "%;");
			$(".irs-single").text(maxMonths[month]);
			$(".irs-slider").attr("style", "left: " + (month / 12 * 100 + 3) + "%;");
			$("#ionrange_4").val(month);
			
			//
	 });
	</script>
</content>	
</body>

</html>