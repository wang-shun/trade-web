<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>知识库列表</title>
	
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
</head>
<body>
	<!-- title start -->
		
	<!-- title end -->
	
	<input type="hidden" id="ctx" value="${ctx}"/>
	<input type="hidden" id="userId" value="${userId}"/>
	<!-- content start  -->
			
			    
			
		<!-- search start -->
			<div class="ibox-content">
		        <div>
		        	<h4>知识库列表</h4> <hr>
		        </div> 
			    
				<div>
					知识编码 :<input type="text" id="knowledgeCode" name="knowledgeCode" />  
					知识标题 :<input type="text" id="knowledgeTitle" name="knowledgeTitle"/> 
					<button type="button" id="knowledgeSearchButton" onclick="knowledgeSearchButton()" style="margin-left: 30px;">搜索</button>
				</div>
			</div>  
		<!-- search end -->                      
	
		<!-- DataList start -->
				<table id="table_knowledge_list"></table>
			    <div id="pager_knowledge_list"></div>
			    <a class="btn btn-primary" href="javascript:knowledgePublish()" id="knowledgePublish">知识发布</a>
			<div class="portlet-body" style="display: block;">
				<a id="alertOper" class="fancybox-thumb" rel="fancybox-thumb"></a>
			</div>
		<!-- DataList end -->
		
	<!-- content end  -->
	
  	<content tag="local_script">
	    <!-- Mainly scripts -->
	    <%-- <script src="${ctx}/js/jquery-2.1.1.js"></script> --%>
	
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
		
		
			               
		<script src="${ctx}/js/trunk/knowledge/knowledgeList.js"></script>

		<script>
			jQuery(document).ready(function() {
				 $(".fancybox").fancybox({
						maxWidth	: 650,
						maxHeight	: 450,
						fitToView	: false,
						width		: '70%',
						height		: '55%',
						autoSize	: false,
						closeClick	: false,
						openEffect	: 'none',
						closeEffect	: 'none'
					});
				 
				 $('#alertOper').fancybox({
						type: 'iframe',
						padding : 0,
						margin:0,
						autoScale:false,
						fitToView	: false,
					 	width		: '70%', 
						height		: '55%',
						autoDimensions:true,
						showCloseButton:true,
						close:true,
						helpers: {
			                overlay: {
			                    closeClick: false
			                }
			            },
						iframe:{preload:false},
						openEffect	: 'none',
						closeEffect	: 'none',
						afterClose:function(){
							jQuery("#table_knowledge_list").trigger("reloadGrid");//刷新列表
						}
					});

			});
	
		</script>
	</content>

</body>
</html>



