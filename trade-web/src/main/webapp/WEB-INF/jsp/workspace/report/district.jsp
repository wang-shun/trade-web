<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
    
     <!-- Morris -->
    <link href="<c:url value='/css/plugins/morris/morris-0.4.3.min.css' />" rel="stylesheet">
    
    <link href="<c:url value='/css/animate.css' />" rel="stylesheet">
    <link href="<c:url value='/css/style.css' />" rel="stylesheet">
    
    <!-- Toastr style -->
    <link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">

    <!-- Gritter -->
    <link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">

    <link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">

    <link href="<c:url value='/css/plugins/fullcalendar/fullcalendar.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/fullcalendar/fullcalendar.print.css' />" rel='stylesheet' media='print'>
    
    <link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
    
    <link href="<c:url value='/css/plugins/nouslider/jquery.nouislider.css' />" rel="stylesheet">
	
	<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
	
	<link href="<c:url value='/css/boxtemplate.css' />" rel="stylesheet">

</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>	 
	<div class="row">
         <div class="col-lg-12">
             <div class="ibox float-e-margins">
                 <div class="ibox-title">
                     <h5>报表(区域)</h5>
	                 	<div class="col-lg-6">
				             <div class="col-sm-6 m-b-xs">
				             	<select class="input-sm input-s-sm inline btn-select" id="orgId" onchange="getDistrictByOrgId();">
			                        <option value="">选择组别</option>
					             	<c:forEach items="${toCaseOrgNameList}"  var="itmes">
				                        <option value="${itmes.key}">${itmes.value}</option>
					             	</c:forEach>
			                     </select>
			                 </div>
	              		</div> 
                 </div>
            	<div id="add-ibox">
	                 <div class="ibox-content" id="ibox-c">
		                  
	                    <div id="morris-line-chart"></div>
	                 </div>
                 </div>
             </div>
            
         </div>
   </div>
 
   <hr>
    <div class="row">
         <div class="col-lg-6">
        	<!-- <div class="ibox float-e-margins"> -->
           	<input type="hidden" id="ctx" value="${ctx}"/>
			<input type="hidden" id="userId" value="${userId}"/>
			<div class="col-lg-12">
				<div class="float-e-margins">
					<div class="ibox-title">
						<div class="col-lg-6">
							<div id="datepicker_0" class="input-group input-medium date-picker input-daterange"	data-date-format="yyyy-mm-dd">
								<input id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px;" type="text" value=""
									placeholder="起始日期"> <span class="input-group-addon">到</span>
								<input id="dtEnd_0" name="dtEnd" class="form-control" style="font-size: 13px;" type="text" value=""
									placeholder="结束日期" />
							</div>
						</div>
						<div class="col-lg-2">
							<a class="btn btn-primary" onclick="searchCount();">查询</a>
						</div>
					</div>
		        </div>
	            <div class="ibox-content ibox-k">
	                <table id="table_report_lists"></table>
				    <div id="pager_report_lists"></div>
	            </div>
            </div>
            <hr>
        </div>
    <!-- </div>  -->
    <div class="col-lg-6">
    	  <div class="float-e-margins">
				<div class="ibox-title">
					<div class="col-lg-6">
						<div id="" class="input-group input-medium date-picker input-daterange">
							<input id="numStr" name="numStr" class="form-control" style="font-size: 13px;" type="text" value=""
								placeholder="红灯数"> <span class="input-group-addon">到</span>
							<input id="numEnd" name="numEnd" class="form-control" style="font-size: 13px;" type="text" value=""
								placeholder="红灯数" />
						</div>
					</div>
					<div class="col-lg-2">
						<a class="btn btn-primary" onclick="searchRedcount();">查询</a>
					</div>
				</div>
		        </div>
          <div class="ibox-content ibox-k">
               <table id="table_redcount_lists"></table>
		    <div id="pager_redcount_lists"></div>
          </div>
    </div>
</div>
<div class="portlet-body"></div>
<!-- import pageleve js -->
<content tag="local_script">
	<!-- Mainly scripts -->
	<script src="<c:url value='/js/plugins/fullcalendar/moment.min.js' />"></script>
	<%-- <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
	<script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
	<script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
	
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> --%>
	<!-- Morris -->
	<script src="<c:url value='/js/plugins/morris/raphael-2.1.0.min.js' />"></script>
	
	 
	
	<!-- jQuery UI custom -->
	<script src="<c:url value='/js/jquery-ui.custom.min.js' />"></script>
	
	<!-- iCheck -->
	<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
	
	<!-- Full Calendar -->
	<script src="<c:url value='/js/plugins/fullcalendar/fullcalendar.min.js' />"></script>
		
	<!-- IonRangeSlider -->
	<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<!-- Slider -->
	<script src="<c:url value='/js/plugins/nouslider/jquery.nouislider.min.js' />"></script>
	
	 <script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	 <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	
	<script src="<c:url value='/js/plugins/morris/morris.js' />"></script>
	<%-- <script src="<c:url value='/js/demo/morris-demo.js' />"></script>  --%>
	
	<script src="<c:url value='/js/trunk/dashboard/report/district/districtList.js' />"></script>
	<script src="<c:url value='/js/trunk/dashboard/report/district/dRedcountList.js' />"></script>
	
	<script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>

	<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
	<%-- <script	src="<c:url value='/js/trunk/case/mycase_list.js' />"></script> <!-- iCheck --> --%> 
	<script	src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> <script>
		
    </script>
    
	 <script>
	 	jQuery(document).ready(function() {

	 		var datas = dInitMorris();
	 		var year = new Date().getFullYear();
	 		var event = [''+year+'-01',''+year+'-12']
	 		var neg_data =datas;
             Morris.Line({
               element: 'morris-line-chart',
               parseTime: true,
               data: neg_data,
               xkey: 'period',
               ykeys: ['a','b','c','d'],
               labels: ['接单数','签约数','过户数','结案数'],
               lineColors: ['#54cdb4','#607B8B','#4F94CD','#43CD80'],
               events:event
		
             });
	    }); 
	 	function getDistrictByOrgId(){
	 		//Morris.remove();
            $("#ibox-c").remove();
            $("#add-ibox").append("<div class='ibox-content' id='ibox-c'><div id='morris-line-chart'></div></div>");
           
	 		var datas = dInitMorris();
	 		var year = new Date().getFullYear();
	 		var event = [''+year+'-01',''+year+'-12']
            new Morris.Line({
               element: 'morris-line-chart',
               data: datas,
               xkey: 'period',
               ykeys: ['a','b','c','d'],
               labels: ['接单数','签约数','过户数','结案数'],
               lineColors: ['#54cdb4','#607B8B','#4F94CD','#43CD80'],
               events:event
		
             });
	 	}
	 	
	 	// 日期控件
	 	$('#datepicker_0').datepicker({
	 		format : 'yyyy-mm-dd',
	 		weekStart : 1,
	 		autoclose : true,
	 		todayBtn : 'linked',
	 		language : 'zh-CN'
	 	});
	 	
	 </script>
    
</content>
</body>
</html>
