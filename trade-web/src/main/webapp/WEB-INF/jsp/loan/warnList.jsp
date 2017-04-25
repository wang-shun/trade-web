<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>警示列表</title>
	
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
	<input type="hidden" id="adminOrg" value="${adminOrg}"/>
	<%-- 
	<input type="hidden" id="userId" value="${userId}"/> --%>
	<!-- content start  -->
			
			    
			
		<!-- search start -->
			<div class="ibox-content">
		        <div>
		        	<h4>警示列表</h4> <hr>
		        </div> 
			    
				<div>
					产证地址 :<input type="text" id="propertyAddr" name="propertyAddr" />  
					产品名称 : <aist:dict clazz="span6" id="loanSrvCode" name="loanSrvCode" display="select" dictType = "yu_serv_cat_code_tree" tag="eplus" defaultvalue=""/>
					所属服务部:	<select id="districtOrgId">
					 			  <option value ="">请选择</option>
								  <c:forEach items="${districtOrgList}" var="item">
									 <option value ="${item.id}">${item.orgName}</option>
								  </c:forEach>
							</select> 
					<button type="button" id="searchButton" onclick="searchButton();" style="margin-left: 30px;">搜索</button>
					<!-- <button type="button" id="cleanButton" onclick="cleanButton();" style="margin-left: 10px;">清除</button> -->
				</div>
			</div>  
		<!-- search end -->                      
	
		<!-- DataList start -->
		<div class="ibox-content">	
			<table id="gridTable"></table>
		    <div id="gridPager"></div>
 		</div>  
	    <form action="#" method="post" id="excelForm">
	    	<a class="btn btn-primary" href="javascript:exportToExcel()">导出</a>
	    </form>	
		<!-- DataList end -->
		
		<div class="ibox-content">
		        <div>
		        	<h4>变更列表</h4> <hr>
		        </div> 
		</div>  
		<table id="changeGridTable"></table>
	    <div id="changeGridPager"></div>
	   
	    <form action="#" method="post" id="excelChangeForm">
	    	<a class="btn btn-primary" href="javascript:exportChangeListToExcel()">导出</a>
	    </form>	  
		
	<!-- content end  -->
	
  	<content tag="local_script">
	    <!-- Mainly scripts -->
	   
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
		
		
			               
		<script src="${ctx}/transjs/loan/warn.list.js"></script>
		<script src="${ctx}/transjs/loan/change.list.js"></script>

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
						type : 'iframe',
						autoScale : false,
						fitToView : false,
						width : '95%',
						height : '95%',
						autoDimensions : true,
						showCloseButton : true,
						close : true,
						helpers : {
							overlay : {
								closeClick : false
							}
						},
						iframe : {
							preload : false
						},
						openEffect : 'none',
						closeEffect : 'none',
						afterClose : function() {
							$("#changeGridTable").trigger("reloadGrid");//刷新列表
						}
					});
				 
				    var districtOrgId = $("#adminOrg").val();
				    var districtArray = districtOrgId==null?null:districtOrgId.split(",");
				    EloanWarnList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager',districtArray);
				    ChangeList.init('${ctx}','/quickGrid/findPage','changeGridTable','changeGridPager',districtArray);
				    
			});
			
			function searchButton() {
		    	var data = {};
		    	
		    	data.search_propertyAddr =$.trim( $('#propertyAddr').val() );  //知识编码
		    	data.search_loanSrvCode =$.trim( $('#loanSrvCode').val() );  //知识标题
		    	// 如果没有选择任何值
		    	if($.trim($('#districtOrgId').val()) == '') {
		    		var districtOrgId = $("#adminOrg").val();
					var districtArray = districtOrgId==null?null:districtOrgId.split(",");
		    		data.search_districtOrgId = districtArray; 
		    	} else {
		    		data.search_districtOrgId = $.trim($('#districtOrgId').val()); 
		    	}
		    	data.queryId="warnListQuery";
		    	
 		    	$("#gridTable").jqGrid('setGridParam',{
		    		datatype:'json',
		    		mtype:'post',
		    		postData:data
		    	}).trigger('reloadGrid'); 
		    	
 				var data1 = {};
		    	
		    	data1.search_propertyAddr =$.trim( $('#propertyAddr').val() );  //知识编码
		    	data1.search_loanSrvCode =$.trim( $('#loanSrvCode').val() );  //知识标题
		    	if($.trim($('#districtOrgId').val()) == '') {
		    		var districtOrgId = $("#adminOrg").val();
					var districtArray = districtOrgId==null?null:districtOrgId.split(",");
		    		data1.search_districtOrgId = districtArray; 
		    	} else {
		    		data1.search_districtOrgId = $.trim($('#districtOrgId').val()); 
		    	}
		    	data1.queryId="changeListQuery";
		    	$("#changeGridTable").jqGrid('setGridParam',{
		    		datatype:'json',
		    		mtype:'post',
		    		postData:data1
		    	}).trigger('reloadGrid');
		    }
		    
		    function cleanButton() {
		    	window.wxc.alert(1);
	    	   $("input[type=text]").val();
	    	   $("input[type=select]").attr("value","");
		    }
		    
		    //导出Excel
		    function exportToExcel() { 
		    		var ctx = $("#ctx").val();
		    		var url = "/quickGrid/findPage?xlsx&";
		    		
		    		var displayColomn = new Array;
		    		displayColomn.push('LOAN_SRV_CODE');
		    		displayColomn.push('EXECUTOR_ID');
		    		displayColomn.push('EXECUTOR_PHONE');
		    		displayColomn.push('MANAGER_NAME');
		    		displayColomn.push('MANAGER_PHONE');
		    		displayColomn.push('APPLY_STATUS');
		    		displayColomn.push('EXCEED_DAY');
		    		displayColomn.push('LAST_EXCEED_EXPORT_TIME');
		    		displayColomn.push('PROPERTY_ADDR');
		    		displayColomn.push('PARENT_ORG_NAME');
		    		/*****导出条件与查询条件应该一至***/
		    		var districtOrgId='';
		    		if($.trim($('#districtOrgId').val()) == '') {
			    		var districtOrgId = $("#adminOrg").val();
						var districtArray = districtOrgId==null?null:districtOrgId.split(",");
						districtOrgId = districtArray; 
			    	} else {
			    		districtOrgId = $.trim($('#districtOrgId').val()); 
			    	}
		    		
		    		var params =  {
		    				search_propertyAddr : $.trim($('#propertyAddr').val()),
		    		    	search_loanSrvCode : $.trim($('#loanSrvCode').val()),
		    		    	search_districtOrgId :districtOrgId
			    	};
		    		var queryId = '&queryId=warnListQuery';
		    		var colomns = '&colomns=' + displayColomn;
		    		url = ctx + url + jQuery.param(params) + queryId + colomns;

		    		$('#excelForm').attr('action', url);
		    		$('#excelForm').submit();
		    		window.wxc.success("导出至 Excel成功");
		    		//caseDistribute();
		    		//停顿2s后再执行
		    		var ids = jQuery("#gridTable").jqGrid('getDataIDs');
		    		setTimeout(function(){changeWarnListExportTime(ctx,ids);},2000);
		    }
		    
		    function changeWarnListExportTime(ctx,ids){
		    	$.ajax({
		    		cache:false,
					async:false,
					type:"GET",
		    		url:ctx+"/loan/updateWarnListTime?idStr="+ids,
		    		dataType:"json",
		    		success:function(data){
		    			//alert(data.message)
						if(data.success){
							var data = {};
					    	
					    	data.search_propertyAddr =$.trim( $('#propertyAddr').val() );  //知识编码
					    	data.search_loanSrvCode =$.trim( $('#loanSrvCode').val() );  //知识标题
					    	data.queryId="warnListQuery";
					    	
			 		    	$("#gridTable").jqGrid('setGridParam',{
					    		datatype:'json',
					    		mtype:'post',
					    		postData:data
					    	}).trigger('reloadGrid'); 
						}
		    		},
					error : function(errors) {
						window.wxc.error("处理出错,请刷新后再次尝试！");
					}
		    	});
		    }
		    
		    function exportChangeListToExcel() { 
	    		var ctx = $("#ctx").val();
	    		var url = "/quickGrid/findPage?xlsx&";
	    		
	    		var displayColomn = new Array;
	    		displayColomn.push('LOAN_SRV_CODE');
	    		displayColomn.push('EXECUTOR_ID');
	    		displayColomn.push('ST_FROM');
	    		displayColomn.push('ST_TO');
	    		displayColomn.push('CHANGE_DATE');
	    		displayColomn.push('PROPERTY_ADDR');
	    		displayColomn.push('CUST_NAME');
	    		displayColomn.push('LOAN_AMOUNT');
	    		
	    		var params =  {
	    				search_propertyAddr : $.trim($('#propertyAddr').val()),
	    		    	search_loanSrvCode : $.trim($('#loanSrvCode').val()),
		    	};
	    		var queryId = '&queryId=changeListQuery';
	    		var colomns = '&colomns=' + displayColomn;
	    		url = ctx + url + jQuery.param(params) + queryId + colomns;

	    		$('#excelChangeForm').attr('action', url);
	    		$('#excelChangeForm').submit();
	    		window.wxc.success("导出至 Excel成功");
	    		//caseDistribute();
	    		//停顿2s后再执行
	    		//setTimeout(function(){caseDistribute();},2000);
	    	}
		    
		</script>
	</content>

</body>
</html>