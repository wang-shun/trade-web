<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- Toastr style -->
    <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
	
	
	<!-- Add fancyBox main JS and CSS files -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox.css?v=2.1.5" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-buttons.css?v=1.0.5" />
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.fancybox-thumbs.css?v=1.0.7" />
	
    <!-- Gritter -->
	<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
    <style>
    .ui-jqgrid .ui-jqgrid-bdiv{
    	    overflow-x: hidden;
    }
    </style>
</head>

<body>
<form action="${ctx }/quickGrid/findPage?xlsx" class="form-horizontal" method="post" id='myForm' >
<input type="hidden" name="queryId" value="queryProcessingList">
<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" name="search_prDistrictId" value="${prDistrictId}">
<input type="hidden" name="search_prStatus" value="1"/>
<input type="hidden" id="prDistrictId" value="${prDistrictId}"/>
<input type="hidden" id="prStatus" value="1"/>
<input type="hidden" name="colomns" value="DIST_CODE,PROPERTY_ADDR,PR_CAT,orgName,PR_APPLIANT,PR_APPLY_TIME,PR_ACCPET_TIME,PR_STATUS,IS_SUCCESS,UNSUCCESS_REASON">
<div class="">
    <div class="col-lg-13">
        <div class="">
            <div class="ibox-title">
               <h4>已受理产调</h4>
             </div>
        </div>
    </div>
</div>
<div class="ibox-content">
	<div>物业地址 :
		<input type="text" id="addr" name="search_propertyAddr" />
			<button type='button' id="addrSearchButton" style="margin-left: 30px;">搜索</button>
	</div>
	<hr>
     <table id="table_property_list"></table>
     <div id="pager_property_list"></div>
     <a class="btn btn-primary" href="javascript:propertyDispose()" disabled="true" id="caseDistributeButton">处理产调</a>
      <a class='btn btn-primary' style="margin-left: 20px;" onclick="document.getElementById('myForm').submit();return false" >导出产调至Excel</a>
</div>       
</form>                 
			<jsp:include page="common/nullityTagJQResult.jsp"></jsp:include>
			<jsp:include page="common/addFilesJQResult.jsp"></jsp:include>
  <content tag="local_script">
    <!-- Mainly scripts -->

    <!-- Peity -->
    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
    
	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox.js?v=2.1.5"></script>
		
	<!-- Add Button helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-buttons.js?v=1.0.5"></script>

	<!-- Add Thumbnail helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-thumbs.js?v=1.0.7"></script>

	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="${ctx}/js/jquery.fancybox-media.js?v=1.0.6"></script>
	<!-- toJSON -->
	<script type="text/javascript" src="${ctx}/js/jquery.json.min.js"></script>
	<script src="${ctx}/js/trunk/property/processingList.js?v=1.0.1"></script>
	<script src="${ctx}/js/trunk/property/propertyByaddr.jqgridSearch.js"></script>
	

	
	<script>
		jQuery(document).ready(function() {
			 $(".fancybox").fancybox({
					maxWidth	: 800,
					maxHeight	: 600,
					fitToView	: false,
					width		: '75%',
					height		: '70%',
					autoSize	: false,
					closeClick	: false,
					openEffect	: 'none',
					closeEffect	: 'none'
				});
			 $('#addFiles').fancybox({
					type: 'iframe',
					padding : 0,
					margin:0,
					autoScale:false,
					fitToView	: false,
				 	width		: '75%', 
					height		: '70%',
					autoDimensions:true,
					helpers: {
		                overlay: {
		                    closeClick: false
		                }
		            },
					iframe:{preload:false},
					openEffect	: 'none',
					closeEffect	: 'none',
					afterClose:function(){
						//jQuery("#custtrackList").trigger("reloadGrid");//刷新列表
					}
				});
			 $('#nullityTag').fancybox({
					type: 'iframe',
					padding : 0,
					margin:0,
					autoScale:false,
					fitToView	: false,
				 	width		: '65%', 
					height		: '37%',
					autoDimensions:true,
					helpers: {
		                overlay: {
		                    closeClick: false
		                }
		            },
					iframe:{preload:false},
					openEffect	: 'none',
					closeEffect	: 'none',
					afterClose:function(){
						//jQuery("#custtrackList").trigger("reloadGrid");//刷新列表
					}
				});
			 var addr = $("#addr").val();
			 JQGrid_propertyByaddSearch.init('table_property_list','addrSearchButton',addr);
		});
	
	</script>

</content>
</body>
</html>
