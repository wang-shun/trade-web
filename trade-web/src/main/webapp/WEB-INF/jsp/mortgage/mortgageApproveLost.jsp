<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>首页</title>
        <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
        <link href="${ctx}/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
        <!-- IonRangeSlider -->
        <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
        <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
        <link href="${ctx}/css/animate.css" rel="stylesheet">
        <%-- <link href="${ctx}/css/style.min.css" rel="stylesheet">  --%>
        <link href="${ctx}/css/transcss/kpi/bonus.css" rel="stylesheet">
        <link href="${ctx}/css/common/common.css" rel="stylesheet">
        <!-- Gritter -->
        <link href="js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
        <!-- 分页控件 -->
        <link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
        <!-- aist列表样式 -->
        <link href="${ctx}/css/common/aist.grid.css" rel="stylesheet">
    </head>
    <body class="pace-done">
		 <div class="row">
			<div class="col-md-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>贷款流失筛选</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="row">
						   <div class="col-md-6">
								<div class="form-group ">
									<label class="col-md-3 label_one control-label">主办</label>
									<div class="col-md-8"> 
										 <input type="text" id="realName" name="realName" class="form-control" value=""> 
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group ">
									<label class="col-md-3 label_one control-label">案件组织</label>
									<div class="col-md-8">
										  <input type="text" class="form-control tbsporg" id="orgName" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})" value=''>
                                          <input type="hidden" id="yuCuiOriGrpId" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group ">
									<label class="col-md-3 label_one control-label">审批日期</label>
									<div class="col-md-8">
										<div id="datepicker_0" 
											class="input-group input-medium date-picker input-daterange "
											data-date-format="yyyy-mm-dd">
											<input id="dtBegin_0" name="dtBegin" class="form-control"
												style="font-size: 13px;" type="text" value="${startTime}"
												placeholder="起始日期"> <span class="input-group-addon">到</span>
											<input id="dtEnd_0" name="dtEnd" class="form-control"
												style="font-size: 13px;" type="text" value="${endTime}"
												placeholder="结束日期" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-3 label_one control-label">案件编号</label>
									<div class="col-md-8">
									    <input type="text" class="form-control" id="caseCode" name="caseCode"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group ">
<<<<<<< Updated upstream
									<label class="col-md-3 control-label m-l">产证地址</label>
=======
									<label class="col-md-3 label_one control-label">物业地址</label>
>>>>>>> Stashed changes
									<div class="col-md-8"> 	
										<input type="text" class="form-control" id="propertyAddr" name="propertyAddr">
									</div>
								</div>
							</div>
							<div class="col-md-6"  style="">
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-12">
								<div class="btn_left">
								   <button id="searchButton" type="button" class="btn btn-warning">查询</button>
								   <button id="exportExcelButton" type="button" class="btn btn-primary" onclick="javascript:exportToExcel()">导出列表</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
   	    <div class="bonus-table">
        </div>
        
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <%--  <script src="${ctx}/js/bootstrap.min.js"></script> --%>
        <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
        <script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
        <!-- Custom and plugin javascript -->
        <script src="${ctx}/js/inspinia.js"></script>
        <script src="${ctx}/js/plugins/pace/pace.min.js"></script>
        <!-- 选择组织控件 -->
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
        <!-- 分页控件  -->
        <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
        <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
       	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
       	  <script src="${ctx}/js/plugins/jquery.custom.js"></script>
		<script id="queryMortgageApproveLost" type= "text/html">
                           {{each rows as item index}}
 							  	   {{if index%2 == 0}}
 				                     <tr class="tr-1">
                                   {{else}}
                                     <tr class="tr-2">
                                   {{/if}}
                                    <td>{{item.CASE_CODE}}</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.LEADING_PROCESS_ID}}</td>
                                    <td>{{item.ORG_ID}}</td>
                                    <td>{{item.AGENT_NAME}}</td>
                                    <td>{{item.MORT_TYPE}}</td>
                                    <td>{{item.real_name}}</td>
                                    <td>{{item.END_TIME_}}</td>
                                </tr>
						{{/each}}
	    </script>
	    <script>
	        var ctx = "${ctx}";
	    	var serviceDepId = "${serviceDepId}";
        	//日期控件
			$('#datepicker_0').datepicker({
				format : 'yyyy-mm-dd',
				weekStart : 1,
				autoclose : true,
				todayBtn : 'linked',
				language : 'zh-CN'
			}); 
    		
        	jQuery(document).ready(function() {
        		//初始化数据
	    		var data = {};  
	    		var queryOrgFlag = $("#queryOrgFlag").val();
	    		var isAdminFlag = $("#isAdminFlag").val();
	    		var queryOrgs = $("#queryOrgs").val();
	    		var arguUserId=null;
	    		if(queryOrgFlag == 'true'){
	    			arguUserId=null;
	    			if(isAdminFlag == 'true'){
	    				queryOrgs=null;
	    			}
	    		}else{
	    			queryOrgs= null;
	    			arguUserId="yes";
	    		}
	    		var orgArray = queryOrgs==null?null:queryOrgs.split(",");
	    		data.argu_idflag = arguUserId;
	    	    //data.argu_queryorgs = orgArray;
	    	    data.argu_queryorgs = serviceDepId;

        	    $(".bonus-table").aistGrid({
        			ctx : "${ctx}",
        			queryId : 'queryMortgageApproveLost',
        		    templeteId : 'queryMortgageApproveLost',
        		    data : data,
        		    columns : [{
        		    	           colName :"案件编号",
        		    	           sortColumn : "CASE_CODE",
        		    	           sord: "desc",
        		    	           sortActive : true
        		    	      },{
        		    	           colName :"产证地址"
        		    	      },{
       		    	                colName :"主办"
    		    	          },{
		   		    	           colName :"主办组"
				    	      },{
				    	           colName :"经纪人"
				    	      },{
        		    	           colName :"贷款类型"
        		    	      },{
        		    	           colName :"审批人"
        		    	      },{
        		    	    	  colName :"审批时间",
        		    	    	   sortColumn : "END_TIME_",
        		    	           sord: "desc"
        		    	      }]
        		
        		}); 
        		
        	 	// 查询
     			$('#searchButton').click(function() {
     				reloadGrid();
     			});
	    	});
        	
        	function exportToExcel() {
        		var data = getParams();
	        	aist.exportExcel({
	    	    	ctx : "${ctx}",
	    	    	queryId : 'queryMortgageApproveLost',
	    	    	colomns : ['CASE_CODE','PROPERTY_ADDR','LEADING_PROCESS_ID','ORG_ID','AGENT_NAME','MORT_TYPE','real_name','END_TIME_'],
	    	    	data : data
	    	    }) 
	         }
        	
			function reloadGrid() {
				var data = getParams();
        	    $(".bonus-table").reloadGrid({
        	    	ctx : "${ctx}",
        			queryId : 'queryMortgageApproveLost',
        		    templeteId : 'queryMortgageApproveLost',
        		    data : data
        	    })
	    	}
			
			function getParams() {
				var startDate = $("#dtBegin_0").val();
				var endDate = '';
				if(!$.isBlank($("#dtEnd_0").val())) {
					endDate = $("#dtEnd_0").val()+" 23:59:59";
				}
	    		var data = {};
        	    data.startDate = startDate;
        	    data.endDate = endDate;
        	    data.caseCode = $("#caseCode").val();
        	    data.propertyAddr = $("#propertyAddr").val();
        	    // 获取组织或者人员
        	    var queryOrgFlag = $("#queryOrgFlag").val();
	    		var isAdminFlag = $("#isAdminFlag").val();
	    		var queryOrgs = $("#queryOrgs").val();
	    		var arguUserId=null;
	    		if(queryOrgFlag == 'true'){
	    			arguUserId=null;
	    			if(isAdminFlag == 'true'){
	    				queryOrgs=null;
	    			}
	    		}else{
	    			queryOrgs= null;
	    			arguUserId="yes";
	    		}
	    		var orgArray = queryOrgs==null?null:queryOrgs.split(",");
	    		data.argu_idflag = arguUserId;
	    	    
	    	    data.search_realName = 	$("#realName").val();
		 		var yuCuiOriGrpId = 	$("#yuCuiOriGrpId").val();
		 		
		 		if($.isBlank(yuCuiOriGrpId)) {
		 			data.argu_queryorgs = serviceDepId;
		 		} else {
		 			data.argu_queryorgs = yuCuiOriGrpId;
		 		}
	    	    return data;
			}
			
			//选业务组织的回调函数
			function radioYuCuiOrgSelectCallBack(array){
			    if(array && array.length >0){
			        $("#orgName").val(array[0].name);
					$("#yuCuiOriGrpId").val(array[0].id);
				}else{
					$("#orgName").val("");
					$("#yuCuiOriGrpId").val("");
				}
			}
        	
	    </script>
	    </content> 
        <input type="hidden" id="ctx" value="${ctx}" />
        <input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
		<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
		<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
    </body>
</html>