<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<head>

    <meta charset="utf-8">
    

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
		
	<link href="${ctx}/css/bootstrap.css" rel="stylesheet" />

</head>

<body>
	<shiro:hasPermission name="TRADE.WORKSPACE.WORKLOAD">
               <div class="row">
	              <div class="col-lg-3">
	                  <div class="ibox float-e-margins">
	                      <div class="ibox-title">
	                          <span class="label label-success pull-right">月度</span>
	                          <h5>接单数</h5>
	                      </div>
	                      <div class="ibox-content">
	                          <h1 class="no-margins" id="countJDS">${toCaseInfoCountVo.countJDS}</h1>
	                      </div>
	                  </div>
	              </div>
	              <div class="col-lg-3">
	                  <div class="ibox float-e-margins">
	                      <div class="ibox-title">
	                          <span class="label label-success pull-right">月度</span>
	                          <h5>签约数</h5>
	                      </div>
	                      <div class="ibox-content">
	                          <h1 class="no-margins" id="countQYS">${toCaseInfoCountVo.countQYS}</h1>
	                      </div>
	                  </div>
	              </div>
	              <div class="col-lg-3">
	                  <div class="ibox float-e-margins">
	                      <div class="ibox-title">
	                          <span class="label label-success pull-right">月度</span>
	                          <h5>过户数</h5>
	                      </div>
	                      <div class="ibox-content">
	                          <h1 class="no-margins" id="countGHS">${toCaseInfoCountVo.countGHS}</h1>
	                      </div>
	                  </div>
	              </div>
	              <div class="col-lg-3">
	                  <div class="ibox float-e-margins">
	                      <div class="ibox-title">
	                          <span class="label label-success pull-right">月度</span>
	                          <h5>结案数</h5>
	                      </div>
	                      <div class="ibox-content">
	                          <h1 class="no-margins" id="countJAS">${toCaseInfoCountVo.countJAS}</h1>
	                      </div>
	                  </div>
	      </div>
	  </div>
  </shiro:hasPermission>
   <div class="row">        
   	     <div class="col-lg-3">
	                  <div class="ibox float-e-margins">
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
	           <div class="col-lg-3">
	                  <div class="ibox float-e-margins">
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
	           <div class="col-lg-3">
	                  <div class="ibox float-e-margins">
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
               <div class="col-lg-3">  
				</div>
 </div>
  <div class="portlet-body" style="display: block;">
	<a id="alertOper" class="fancybox-thumb" rel="fancybox-thumb"></a>
 </div>  
<!-- <shiro:hasPermission name="TRADE.WORKSPACE.CALENDAR">
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
</shiro:hasPermission> -->

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
		
	<!-- 知识库 -->
	<%-- <script src="${ctx}/js/trunk/dashboard/knowledgeList.js"></script> --%>
	
	<script src="${ctx}/js/trunk/dashboard/dashboard.js"></script>
    <script src="${ctx}/js/trunk/case/caseCount.js"></script>
    <script>
    jQuery(document).ready(function() {
		 
    	reloadMonth();
    
    });
    </script>
 </content>
</body>
</html>
