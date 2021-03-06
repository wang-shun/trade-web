<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>
<meta charset="utf-8">

<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/dropzone/basic.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/dropzone/dropzone.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/morris/morris-0.4.3.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/mobile/task/pricingList/style.css' />"
	rel="stylesheet">
<style type="text/css">

.form-control {
	margin-bottom: 5px;
	height:32px;
}

.radio {
	margin-left: 20px;margin-top:-10px
}

.mouseover-color{
	background-color:#B4D5F5;
}

</style>
<script language="javascript">
 	var ctx = "${ctx}";
 	var ariserId = "${ariserId}";

</script>
</head>
<body>

	<div class="panel-body">
	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>报告单查询</h5>
				</div>
				<div class="ibox-content" style="padding: 10px 10px 10px 10px;">
					<form method="get" class="form-horizontal">
							<div class="form-group">
							<label class="col-md-12 control-label" style="text-align:left">物业地址：</label>
							<div class="col-md-12">
								<input type="text" name="residenceName"
											id="residenceName" placeholder=""
											class="form-control" >
							</div>
						</div>
						<div class="form-group ">
						
						<div class="col-sm-12">
							<button id="searchButton" type="button" class="btn btn-primary" style="width:100%">查询</button>
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>

		<div class="wrapper wrapper-content  animated fadeInRight" style="padding:0px">

			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<h5>报告单列表</h5>
					</div>
			<div  id="jqList" class="ibox-content" >
				<div class='class="m-b-lg"'></div>
				<div id='blockShow-9' class="my-layer my-loading" style="display:none;">
			    	<div class="box">
			        	<div class="caseFonce">
				 			<div class="spinner" style="text-align:center;width:30%;height:100px"></div>
						</div>
			        </div>
			    </div>
				<div id="reportListDiv" class="allhouse-list row-fluid">
                    <div class="house-cont"></div>
                	<div id="hiddenDataId" style="height:20px;">&nbsp;</div>
                </div>

				<div id="picShowDiv" style="display:none"></div>  
				<div id="pageBar" class="pagination-sm my-pagination text-center m0"></div>
			</div>
			
	<script id="reportList" type="text/html">
<div class="feed-activity-list">

<article class="history-info">
     <section class="item">
        <ul>
		{{if rows.length>0}}
			{{each rows as value}}
                 <li >
                        <div class="price1 text-ellipsis">
						    <p class="house-name text-black">{{value.LOANER_NAME}}</p>
                            <p class="text-black">{{value.FIN_ORG_NAME}}</p>
                            <p class="text-black">{{value.LOANER_PHONE}}</p>
                            <p>{{value.REPORT_TYPE}}</p>
                            <p>{{value.REPORT_ARISE_TIME}}</p>
                            <p>{{value.RESIDENCE_NAME}}/&nbsp;{{value.BUILDING_NO}}栋{{value.ROOM_NO}}室</p>
						</div>
                        <div class="desc1">
                            <p >{{value.FEEDBACK}}</p>
                        </div>
                   </li> 
           	{{/each}}
		{{/if}}
        </ul>
     </section>
</article>
</div>

</script>		
					
				</div>
			</div>

		</div>
	</div>
	</div>

<content tag="local_script"> 
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>

	<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> 
   	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/jquery.twbsPagination.min.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script> 
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	
	<script src="<c:url value='/mobilejs/task/reportList.js' />"></script> 	
	 </content>
</body>

</html>