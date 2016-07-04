<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

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
    <link href="${ctx}/css/style.css" rel="stylesheet">
</head>

<body>
<form action="" class='form-horizontal'>
<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="userId" value="${userId}"/>
<input type="hidden" id="prStatus" value="2"/>
<div class="">
	<div class="col-lg-13">
	     <div class="">
	         <div class="ibox-title">
	            <h4>我的产调结果</h4>
	            </div>
	        </div>
     </div>
</div>
<div class="ibox-content">
    <div class="jqGrid_wrapper">
    	<div class='row form-group'>
    		<label class='col-md-2 control-label'>产证地址 :</label>
    		<div class='col-md-4'><input type="text" id="addr" class='form-control'/></div>
			<div class='col-md-6 '><button id="addrSearchButton" type='button' class='btn btn-warning pull-left'>搜索</button></div>
		</div>
    	
        
		<hr>
         <table id="table_property_list"></table>
         <div id="pager_property_list"></div>
     </div>                        
</div>
<jsp:include page="common/resultGetJQResult.jsp"></jsp:include>
<content tag="local_script">
    <!-- Mainly scripts -->

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

    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>

	<script src="${ctx}/js/trunk/property/resultGetList.js?v=1.0.1"></script>
	<%-- <script src="${ctx}/js/trunk/property/propertyByaddr.jqgridSearch.js"></script> --%>
	<script src="${ctx}/js/trunk/property/resultGetListByaddr.jqgridSearch.js?v=1.0.2"></script>
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
		 $('#resultGet').fancybox({
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
		
		 var addr = $("#addr").val();
		 JQGrid_resultGetByaddSearch.init('table_property_list','addrSearchButton',addr);
	});
	</script>

</content>
</form>
</body>
</html>
