<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>未分配业务组别列表</title>
	
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
       .org-label-control {
		    background-color: #FFFFFF;
		    background-image: none;
		    border: 1px solid #e5e6e7;
		    border-radius: 1px;
		    color: inherit;
		    display: block;
		    padding: 6px 12px;
		    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
		    width: 85%;
		    font-size: 14px;
		}
    </style>
</head>
<body>

    <div class="wrapper wrapper-content  animated fadeInRight">
		<div id="modal-addOrModifyForm" class="modal fade" aria-labelledby="modal-title"
			aria-hidden="true">
			<div class="modal-dialog" style="width: 1200px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="modal-title">添加/组别信息</h4>
					</div>
					<div class="modal-body">
						<div class="row" style="height: 520px;overflow:auto; ">
							<div class="col-lg-12 ">
							<form id="addOrModifyForm">
								<input type="hidden" name="pkid" id="pkid" value="">
						        <input type="hidden" name="agenTeamCode" id="agenTeamCode" value="">
						        <input type="hidden" name="agenTeamName" id="agenTeamName" value="">
						        <div id="isAgentModify">
									<div class="form-group">
										<label class="col-sm-2 control-label">业务组别编码<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="yuAgentTeamCode"
												id="yuAgentTeamCode" placeholder=""
												class="form-control" data-provide="typeahead" readOnly="readonly">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">业务组别名称<span class="star">*</span>：</label>
										<div class="col-sm-10" id="orgCode">
											<input type="text" name="yuAgentTeamName"
												id="yuAgentTeamName" placeholder=""
												class="form-control" >
										</div>
									</div>
								</div>
								<div id="isAreaModifiy">
									<div class="form-group">
										<label class="col-sm-2 control-label">区域ID<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="busiArIds"
												id="busiArIds" placeholder=""
												class="form-control" data-provide="typeahead" readOnly="readonly">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">区域名称<span class="star">*</span>：</label>
										<div class="col-sm-10" id="orgCode">
											<input type="text" name="busiArNames"
												id="busiArNames" placeholder=""
												class="form-control" >
										</div>
									</div>
							   </div>
									<!-- <div class="form-group">
										<label class="col-sm-2 control-label">誉萃组别名称<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="yuTeamName"
												id="yuTeamName" placeholder=""
												class="form-control" >
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">誉萃组别编码<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="yuTeamCode"
												id="yuTeamCode" placeholder=""
												class="form-control" >
										</div>
									</div> -->
									<div class="form-group">
										<label class="col-sm-2 control-label">誉萃前台组别<span class="star">*</span>：</label>
										<div class="col-sm-10" id="fontTeam">
											
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">誉萃后台组别<span class="star">*</span>：</label>
										<div class="col-sm-10" id="backTeam">
											
										</div>
									</div>
									<div id="addLine" class="col-md-offset-2">
										<a href="javascript:addBackTeam();" class="btn"><font>添加后台组别</font></a>
									</div>
							
							</form>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12">
								<button type="button" class="btn btn-success" id="saveOrModifyBtn"
								value="提交">提交</button>
							</div>
						</div>
						<!-- <div class="row">
							<div class="col-lg-12">
								<div class="ibox ">
									<div class="ibox-title">
										<h5>未维护组别信息</h5>
									</div>
									<div class="ibox-content">
										<div class="jqGrid_wrapper">
											<table id="show_unsettingList"></table>
											<div id="show_unsettingList_bar"></div>
										</div>
					
									</div>
								</div>
							</div>
						</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- title end -->
	<input type="hidden" id="ctx" value="${ctx}"/>

	<!-- search start -->
	<div class="ibox-title">
        	<h4>未分配业务组别列表</h4> <hr>
      		<div class="row">
					<div class="col-md-7">
						<div class="form-group ">
							<label class="col-md-2 control-label">组织：</label>
							<div class="col-md-8">
									<input type="text" class="span12 tbsporg org-label-control" id="radioOrgName" name="radioOrgName" readonly="readonly" 
										   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'1D29BB468F504774ACE653B946A393EE', orgType:'',departmentType:'',departmentHeriarchy:'BUSIGRP',
										   chkStyle:'checkbox',callBack:checkBoxOrgSelectCallBack,
										   expandNodeId:'',chkType:'BUSIAR'})" />
									<input class="m-wrap " type="hidden" id="oriGrpId" name="oriGrpId"> 
							</div>
						</div>
					</div>
					<button type="button" id="searchButton" onclick="searchButton();" class="btn btn-primary">搜索</button>
					<button type="button" id="cleanButton" onclick="cleanButton();" class="btn btn-primary">清空</button>
					<a href="#" id="modifyBtn" class="btn btn-primary">分配</a>
					<a href="#" id="batchModifyBtn" class="btn btn-primary">批量分配</a>
			</div>
	</div>  
	<!-- search end -->                      
	
	<!-- DataList start -->
	<div class="ibox-content">	
		<table id="gridTable"></table>
	    <div id="gridPager"></div>
	</div>   
	    
  	<content tag="local_script">
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
		
		<script type="text/javascript" src="${ctx}/js/jquery.json.min.js"></script>
		
		
			               
		<script src="${ctx}/transjs/manage/unlocatedAgentTeamList.js"></script>
		<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>	
	<%-- <script src="${ctx}/transjs/manage/teamScope.js"></script>  --%>
	<script src="${ctx}/js/template.js" type="text/javascript"></script>
	<script id="yuCuiFontTeamList" type="text/html">
											<select class="form-control" name="yuTeamCode">
                                              {{if content.tsTeamPropertyList.length>0}}
                                              {{each content.tsTeamPropertyList as item}}
                                                 {{if item.isResponseTeam==1}} 
											     	<option value ="{{item.yuTeamCode}}">{{item.yuTeamName}}</option>
                                                 {{/if}}
                                              {{/each}}
                                              {{/if}}
											</select>
	</script>	
	<script id="yuCuiBackTeamList" type="text/html">
             	<select class="form-control" name="yuTeamCode">
                                              {{if content.tsTeamPropertyList.length>0}}
                                              {{each content.tsTeamPropertyList as item}}
                                                 {{if item.isResponseTeam==0}} 
											     	<option value ="{{item.yuTeamCode}}">{{item.yuTeamName}}</option>
                                                 {{/if}}
                                              {{/each}}
                                              {{/if}}
											</select>
	</script>	
	<script id="yuCuiOtherBackTeamList" type="text/html">
		<select class="form-control" name="yuTeamCode">
 				{{if content.length>0}}
                    {{each content as item}}
                       {{if item.isResponseTeam==0}} 
						<option value ="{{item.yuTeamCode}}">{{item.yuTeamName}}</option>
 						{{/if}}
 					{{/each}}
                 {{/if}}
		</select>
	</script>	
		<script>
			jQuery(document).ready(function() {
				(function($){
			  		$.isBlank = function(obj){
			   		 return(!obj || $.trim(obj) === "");
			 	 };
				})(jQuery);
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
				 
				    UnlocatedAgentTeamScope.init('${ctx}','/quickGrid/findPage','gridTable','gridPager');
				    
				    // 单个分配
			 	    $("#modifyBtn").click(function(){
			    		if($("#agenTeamCode").val()==""){
			    			alert("请选择要修改的记录！");
			    			return;
			    		}
			    		$("#isAgentModify").show();
 						$("#isAreaModifiy").hide();
			    		getTeamScopeInfo();

			    	}); 
				    
			 	   // 批量分配
			 	    $("#batchModifyBtn").click(function(){
			    		if($("#oriGrpId").val()==""){
			    			alert("请选择要分配的区域！");
			    			return;
			    		}
			      		$("#isAgentModify").hide();
 						$("#isAreaModifiy").show();
			    		getTeamScopeInfo();

			    	}); 
			 	    
			 	   $("#saveOrModifyBtn").click(function(){
			 		  	if($("#isAreaModifiy").is(":hidden")){
			 		  		saveTeamScope();
				 		}else{
				 			saveTeamScopeByOrg();
				 		}
			    	});
			 	    
			 	   
			});
		    var ctx = "${ctx}";
			var divIndex = 1;
	 		function addBackTeam() {
	 			var txt = '<div id="div_' + divIndex + '" class="form-group">';
	 			txt +='<label class="col-sm-2 control-label"></label>';
	 			txt += '<div class="col-sm-8" id="back_' + divIndex + '">';
	 			txt += '</div>';
	 			txt += '<span class="input-group-addon"><a href="javascript:removeDiv(\'div_'
	 				+ divIndex + '\');"><font>删除</font></a></span>';
	 			txt += '</div>';
	 			// alert(txt);
	 			$("#addLine").before(txt);
	 			var backId = 'back_'+divIndex;
	 			getYuCuiTeamList(backId);
	 			divIndex++;
	 		}
	 		function removeDiv(index) {
	 			$("#" + index).remove();
	 		}
	 		
	 		//选组织的回调函数
	 		function checkBoxOrgSelectCallBack(array){
	 			var orgNames = '';
	 			var orgIds = '';
	 		    if(array && array.length >0){
	 		    	$.each(array,function(index,item){
	 		    		orgNames += item.name+',';
	 		    		orgIds += item.id+',';
	 		    	});
 			        $("#radioOrgName").val(orgNames.substr(0,orgNames.length-1));
	 				$("#oriGrpId").val(orgIds.substr(0,orgIds.length-1));
	 				
	 		/*		var userSelect = "userSelect({displayId:'oriAgentId',displayName:'radioUserNameCallBack',startOrgId:'"+array[0].id+"',nameType:'long|short',jobIds:'',jobCode:'JWYGW,JFHJL,JQYZJ,JQYDS',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:checkboxUser})";
	 				$("#oldactiveName").attr("onclick",userSelect);*/
	 			}else{
	 				$("#radioOrgName").val("");
	 				$("#oriGrpId").val("");
	 			}
	 		}
	 		
	 		function getTeamScopeInfo(){
	 	       	$.ajax({
	 	    		url:ctx+"/setting/getTeamScopeListByCode",
	 	    		method:"post",
	 	    		dataType:"json",
	 	    		data:{
	 	    			  agentTeamCode:$("#agenTeamCode").val()
	 	    			  },
	 	    		success:function(data){
	 	    			if(data.success){
	 	    				//loadUnSettingOrg();
	 	    				$("#yuAgentTeamCode").val($("#agenTeamCode").val());
	 	    				$("#yuAgentTeamName").val($("#agenTeamName").val());
	 	    				$("#busiArIds").val($("#oriGrpId").val());
	 	    				$("#busiArNames").val($("#radioOrgName").val());
	 	    				$("#modal-addOrModifyForm").modal("show");
	 	    				console.log(data);
	 	/*    				$("#yuAgentTeamCode").val(data.content.yuAgentTeamCode);
	 	    				$("#yuAgentTeamName").val(data.content.yuAgentTeamName);
	 	    				$("#yuTeamCode").val(data.content.yuTeamCode);
	 	    				$("#yuTeamName").val(data.content.yuTeamName);*/

	 	    				console.log(data);
	 	    		        var tempFontTeam= template('yuCuiFontTeamList', data); 
	 	    		        $("#fontTeam").empty();
	 	    		        $("#fontTeam").html(tempFontTeam);
	 	    		        
	 	    		        var tempBackTeam= template('yuCuiBackTeamList', data); 
	 	    		        $("#backTeam").empty();
	 	    		        $("#backTeam").html(tempBackTeam);
	 	    			}else{
	 	    				alert(data.message);
	 	    			}
	 	    		}
	 	    	});
	 	       	
	 	       	//getYuCuiTeamList();
	 	    }
	 		
	 		 function saveTeamScope(){
				$.ajax({
  	 	        		url:ctx+"/setting/saveTeamScopeVO",
  	 	        		method:"post",
  	 	        		dataType:"json",
  	 	        		data:$("#addOrModifyForm").serialize(),
  	 	        		success:function(data){
  	 	    				alert(data.message);
  	 	        			if(data.success){
  	 	        				$("#modal-addOrModifyForm").modal("hide");

  	 	        				//$("#gridTable").trigger("reloadGrid");
  	 	        				window.location.reload();
  	 	        			}
  	 	        			$("#agenTeamCode").val("");
  	 	        		}
 	 	        }); 
	 	    	//if(checkform()){ 	
	 	    	//}
	 	    }
	 		 
			 function saveTeamScopeByOrg(){
		 			var params = {};
		 			params.search_oriGrpId =$.trim( $('#oriGrpId').val() );  //片区名称
		 			params.queryId="queryUnlocatedAgentTeamScope";
		 			params.rows=100;
		 			params.page=1;
		 			var yuTeamCode = new Array();
		 			var yuTeamCodeLength = $("select[name='yuTeamCode']").length;
		 			for (var i = 0; i < yuTeamCodeLength; i++) {
		 				var yuTeamCodeValue = $("select[name='yuTeamCode']:eq("+i+")").val();
		 				yuTeamCode.push(yuTeamCodeValue);
	 				};
		 			$.ajax({
	 	        		url:ctx+"/quickGrid/findPage",
	 	        		method:"post",
	 	        		dataType:"json",
	 	        		data: params,  //片区名称
	 	        		success:function(data){
	 	    				console.log(data);
	 	    				var items = data.rows;
	 	    				var length=data.rows.length;
	 	    				console.log(yuTeamCode);
	 	    				var tsTeamScopeVOList = new Array();
	 	    				for (var i = 0; i < length; i++) {
	 	    					var item = items[i];
	 	    					console.log(item.ORG_CODE);
	 	    					console.log(item.ORG_NAME);
	 	    					var tsTeamScopeVO = {
	 	    						'yuAgentTeamCode': item.ORG_CODE,
	 	    					    'yuAgentTeamName' : item.ORG_NAME,
	 	    					    'yuTeamCode' : yuTeamCode
	 	    					}
	 	    					tsTeamScopeVOList.push(tsTeamScopeVO);
	 	    				};
	 	    				tsTeamScopeVOList = $.toJSON(tsTeamScopeVOList);
	 	    				$.ajax({
	 	   	 	        		url:ctx+"/setting/saveTeamScopeVOByOrg",
	 	   	 	        		method:"post",
	 	   	 	        		dataType:"json",
	 	   	 	      		    contentType: "application/json; charset=utf-8",
	 	   	 	        		data: tsTeamScopeVOList,
	 	   	 	        		success: function(data){
	 	   	 	        			alert(data.message);
	 	   	 	   					if(data.success){
		   	 	        				$("#modal-addOrModifyForm").modal("hide");
		   	 	        				
		   	 	        				//$("#gridTable").trigger("reloadGrid");
		   	 	        				window.location.reload();
	   	 	        				}
	   	 	        				$("#agenTeamCode").val("");
	 	   	 	        		}
	 	    				});
	 	        		}
	 	        	});
		 	    	//if(checkform()){ 	
		 	    	//}
		 	    }
	 		 
	 		function getYuCuiTeamList(backId){
	 	    	$.ajax({
	 	    		url:ctx+"/setting/getYuCuiTeamList",
	 	    		method:"post",
	 	    		dataType:"json",
	 	    		data:"",
	 	    		success:function(data){
	 	    			if(data.success){
	 	    				console.log(data);
	 	/*    		        var tempFontTeam= template('yuCuiFontTeamList', data); 
	 	    		        $("#fontTeam").empty();
	 	    		        $("#fontTeam").html(tempFontTeam);*/
	 	    		        
	 	    		        var tempBackTeam= template('yuCuiOtherBackTeamList', data); 
	 	    		        $("#"+backId).empty();
	 	    		        $("#"+backId).html(tempBackTeam);
	 	    			}else{
	 	    				alert(data.message);
	 	    			}
	 	    		}
	 	    	});
	 	    }
	 		
			function searchButton() {
		    	var data = {};
		    	
		    	data.search_oriGrpId =$.trim( $('#oriGrpId').val() );  //片区名称
		    	data.queryId="queryUnlocatedAgentTeamScope";
		    	
 		    	$("#gridTable").jqGrid('setGridParam',{
		    		datatype:'json',
		    		mtype:'post',
		    		postData:data
		    	}).trigger('reloadGrid'); 
		    }
		    
		    function cleanButton() {
	    	   $("input[type=text]").val('');
	    	   $("#oriGrpId").val('');
	    	   $("#agenTeamCode").val('');
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
		    		
		    		var params =  {
		    				search_propertyAddr : $.trim($('#propertyAddr').val()),
		    		    	search_loanSrvCode : $.trim($('#loanSrvCode').val())
			    	};
		    		var queryId = '&queryId=warnListQuery';
		    		var colomns = '&colomns=' + displayColomn;
		    		url = ctx + url + jQuery.param(params) + queryId + colomns;

		    		$('#excelForm').attr('action', url);
		    		$('#excelForm').submit();
		    		alert("导出至 Excel成功");
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
		    		url:ctx+"/eloan/updateWarnListTime?idStr="+ids,
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
						alert("处理出错,请刷新后再次尝试！");
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
	    		    	search_loanSrvCode : $.trim($('#loanSrvCode').val())
		    	};
	    		var queryId = '&queryId=changeListQuery';
	    		var colomns = '&colomns=' + displayColomn;
	    		url = ctx + url + jQuery.param(params) + queryId + colomns;

	    		$('#excelChangeForm').attr('action', url);
	    		$('#excelChangeForm').submit();
	    		alert("导出至 Excel成功");
	    		//caseDistribute();
	    		//停顿2s后再执行
	    		//setTimeout(function(){caseDistribute();},2000);
	    	}
		    
		    function checkform(){
		    	if($("#yuAgentTeamCode").val() == ""){
		    		$("#yuAgentTeamCode").css("border-color","red");
		    		return false;
		    	}else if($("#yuAgentTeamName").val() == ""){
		    		$("#yuAgentTeamName").css("border-color","red");
		    		return false;
		    	}else if($("#yuTeamCode").val() == ""){
		    		$("#yuTeamCode").css("border-color","red");
		    		return false;
		    	}else if($("#yuTeamName").val() == ""){
		    		$("#yuTeamName").css("border-color","red");
		    		return false;
		    	}

		    	return true;
		    }
		    
		</script>
	</content>

</body>
</html>