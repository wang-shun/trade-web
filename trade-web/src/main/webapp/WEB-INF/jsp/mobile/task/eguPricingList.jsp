<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>
<meta charset="utf-8">
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">
<link
	href="${ctx}/css/mobile/task/pricingList/style.css"
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
					<h5>询价查询</h5>
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
						<h5>询价列表</h5>
					</div>
			<div  id="jqList" class="ibox-content" >
				<div class='class="m-b-lg"'></div>
				<div id="pricingListDiv" class="allhouse-list row-fluid">
                    <div class="house-cont"></div>
                	<div id="hiddenDataId" style="height:20px;">&nbsp;</div>
                </div>
                <div id='blockShow-9' class="allhouse-list row-fluid" style="display:none">
			    	<div class="box">
			        	<div class="caseFonce">
				 			<div class="spinner" style="text-align:center;"></div>
						</div>
			        </div>
			    </div>
				<div id="picShowDiv" style="display:none"></div>  
				<div id="pageBar" class="pagination-sm my-pagination text-center m0"></div>
			</div>
			
	<script id="pricingList" type="text/html">
<div class="feed-activity-list">

<article class="history-info">
            <section class="item">
                <ul>
	{{if rows.length>0}}
		{{each rows as value}}

                    <li onclick="javascript:openW('{{value.EVA_CODE}}')">
                        <div class="price text-ellipsis"><em>{{value.TOTAL_PRICE}}</em>&nbsp;万元<br>{{value.UNIT_PRICE}}&nbsp;元/m<sup>2</sup></div>
                        <div class="desc">
                            <p class="house-name text-black">{{value.RESIDENCE_NAME}}</p>
                            <p class="text-black">{{value.ARISE_TIME}}</p>
                            <p>{{value.BUILDING_NO}}-{{value.ROOM_NO}}/{{value.AREA}}&nbsp;m<sup>2</sup>&nbsp;/{{value.ROOM}}室{{value.HALL}}厅{{value.TOILET}}卫</p>
                            <p>{{value.FIN_ORG_NAME}}</p>
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
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>

	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
   	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script>
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	<script src="${ctx}/js/template.js" type="text/javascript"></script>
	<script src="${ctx}/js/jquery.twbsPagination.min.js" type="text/javascript"></script>

	<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	
	<script src="${ctx}/mobilejs/task/eguPricingList.js"></script> 	
	 </content>
</body>

</html>