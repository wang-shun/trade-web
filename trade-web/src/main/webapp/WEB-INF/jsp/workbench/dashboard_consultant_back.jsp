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

    <link href="${ctx}/static/css/plugins/aist-steps/steps.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">
	
	<!-- ION-RANGESLIDER -->
	<link href="${ctx}/static/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
	
	<!-- fullcalendar -->
	<link href="${ctx}/static/css/plugins/fullcalendar/fullcalendar.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/fullcalendar/fullcalendar.print.css" rel='stylesheet' media='print'>

	<!-- morris -->
	<link href="${ctx}/static/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
	
	<!-- fancybox -->
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/jquery.fancybox.css?v=2.1.5" media="screen" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/jquery.fancybox-buttons.css?v=1.0.5" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/jquery.fancybox-thumbs.css?v=1.0.7" />
	
	<link href="${ctx}/static/trans/css/common/stickDash.css" rel="stylesheet">
	
	<!-- iCheck -->
	<link href="${ctx}/static/css/plugins/iCheck/custom.css" rel="stylesheet">
	
	<!-- modal -->
	<link href="${ctx}/static/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>	
	
    <!-- index_css  -->
    <link href="${ctx}/static/trans/css/workbench/dashboard/dashboard.css" rel="stylesheet">
</head>

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
                                    	<a href="#tab-1" data-toggle="tab">工作数据显示</a>
                                    </li>
                                    <li class="">
                                    	<a href="#tab-2" data-toggle="tab">业务提醒</a>
                                    </li>
                                    <li class="">
                                    	<a href="#tab-3" data-toggle="tab">龙虎榜</a>
                                    </li>
                            	</ul>
                         	</div>
                         </div>

                         <div class="panel-body">
                         	<div class="tab-content no_border">
                            	<div class="tab-pane active" id="tab-1">
<div class="row">
                        <div class="col-md-12">
                            <div class="ibox float-e-margins">
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
                                </div>

                                                <div class="tab-pane" id="tab-2">
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
                                                                	<div  style="height:320px; overflow:hidden;overflow-y:scroll;width:100%;">
                                                                    	<div id="div_messagelist1" style="min-height:320px;" class="widget-main"></div>
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
                                                                           <span class="label label-blue">0</span>
                                                                        </label>
                                                                    </div>
                                                                </div>

																<div class="widget-body">
                                                                	<div  style="height:320px; overflow:hidden;overflow-y:scroll;width:100%;">
                                                                    	<div id="div_messagelist2" style="min-height:320px;" class="widget-main"></div>
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
                                                                           <span class="label label-blue">0</span>
                                                                        </label>
                                                                    </div>
                                                                </div>

																<div class="widget-body">
                                                                	<div  style="height:320px; overflow:hidden;overflow-y:scroll;width:100%;">
                                                                    	<div id="div_messagelist3" style="min-height:320px;" class="widget-main"></div>
                                                                	</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                     </div>
                                                </div>
                                                
                                                <!-- 龙虎榜 -->
                                                <div class="tab-pane" id="tab-3">
												<script type="text/javascript">
													function imgLoad(img){
														img.parentNode.style.backgroundImage="url("+img.src+")";
													}
												</script>
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
    <!-- owner -->
    <script src="${ctx}/static/trans/js/workbench/stickDash.js"></script>
    <script src="${ctx}/static/trans/js/workbench/caseCount.js"></script>
    <script src="${ctx}/static/trans/js/workbench/dashboard.js"></script>

    <!-- Toastr script -->
    <script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script>
    <script src="${ctx}/static/js/morris/morris.js"></script>
    <script src="${ctx}/static/js/morris/raphael-min.js"></script>
    <!-- messageGrid -->
    <script src="${ctx}/static/js/messageGrid.js"></script>
    <!-- jquery.formatMoney -->
    <script src="${ctx}/static/js/jquery.formatMoney.js"></script>
    <!-- fullcalendar -->
	<script src="${ctx}/static/js/plugins/fullcalendar/moment.min.js"></script>
 	<script src="${ctx}/static/js/plugins/fullcalendar/fullcalendar.min.js"></script>
	<script src="${ctx}/static/js/plugins/fullcalendar/zh-cn.js"></script>
	<!-- IonRangeSlider -->
	<script src="${ctx}/static/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
    <!-- Add fancyBox main JS and CSS files -->
	<script src="${ctx}/static/js/jquery.fancybox.js?v=2.1.5"></script>
	<script src="${ctx}/static/js/jquery.fancybox-buttons.js?v=1.0.5"></script>
	<script src="${ctx}/static/js/jquery.fancybox-thumbs.js?v=1.0.7"></script>
	<script src="${ctx}/static/js/jquery.fancybox-media.js?v=1.0.6"></script>
    
    <!-- ChartJS morris -->
<%--     <script src="${ctx}/static/js/plugins/morris/raphael-2.1.0.min.js"></script>
    <script src="${ctx}/static/js/plugins/morris/morris.js"></script> --%>
    
	<!-- iCheck -->
	<script src="${ctx}/static/js/plugins/iCheck/icheck.min.js"></script>    

    <!-- modal -->
    <script src="${ctx}/static/js/aist-modal.js"  type="text/javascript"></script>
    <script src="${ctx}/static/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
	<script src="${ctx}/static/js/bootstrap-modal.js" type="text/javascript"></script>
    
    <!-- ECharts.js -->
    <script src="${ctx}/static/js/echarts.min.js"></script>    
    <script src="${ctx}/static/trans/js/workbench/dashboard_echart.js"></script>
	
	<script type="text/javascript">
	 $(document).ready(function() {
		 	Modal.alert({
	    		Title : '任务小卫士',
	    		Message : ''
	    });
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
		});				 
		 
		    //加载echarts
		    reloadStatus();
		    
			reloadMonth();
			
			queryConutCaseByDate()
			$('#sp_evalFee').on('click',evalFeeClick);
	 });
	</script>
</content>
</body>

</html>