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
					<h5>贷款流失率筛选</h5>
				</div>
				<div class="ibox-content">
						<div class="row">
							<div class="col-md-6"  style="padding-left: 5.8% ">
								<div class="form-group">
									<label class="col-md-2 control-label">审批日期</label>
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
						<div class="row">
							<div class="col-md-6"></div>
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-4 control-label m-l-lg"></label>
									<div><button id="searchButton" type="button" class="btn btn-warning">查询</button></div>
								</div>
							</div>
						</div>
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
        <!-- 分页控件  -->
        <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
        <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
       	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		<script id="queryMortgageApproveLost" type= "text/html">
                           {{each rows as item index}}
 							  	   {{if index%2 == 0}}
 				                     <tr class="tr-1">
                                   {{else}}
                                     <tr class="tr-2">
                                   {{/if}}
                                    <td>{{item.CASE_CODE}}</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.MORT_TYPE}}</td>
                                    <td>{{item.SIGN_DATE}}</td>
                                    <td>{{item.real_name}}</td>
                                    <td>{{item.END_TIME_}}</td>
                                </tr>
						{{/each}}
	    </script>
	    <script>
	        var ctx = "${ctx}";
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
        	    $(".bonus-table").aistGrid({
        			ctx : "${ctx}",
        			queryId : 'queryMortgageApproveLost',
        		    templeteId : 'queryMortgageApproveLost',
        		    data : data,
        		    columns : [{
        		    	           colName :"案件编号",
        		    	           sortColumn : "CASE_CODE",
        		    	           sord: "desc"
        		    	      },{
        		    	           colName :"物业地址"
        		    	      },{
        		    	           colName :"贷款类型"
        		    	      },{
        		    	           colName :"签约日期",
        		    	           sortColumn : "SIGN_DATE",
        		    	           sord: "desc"
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
        	
			function reloadGrid() {
				var startDate = $("#dtBegin_0").val();
				var endDate = '';
				if(!$.isBlank($("#dtEnd_0").val())) {
					endDate = $("#dtEnd_0").val()+" 23:59:59";
				}
	    		var data = {};
        	    data.queryId = "queryMortgageApproveLost";
        	    data.startDate = startDate;
        	    data.endDate = endDate;
       
        	    $(".bonus-table").reloadGrid({
        	    	ctx : "${ctx}",
        			queryId : 'queryMortgageApproveLost',
        		    templeteId : 'queryMortgageApproveLost',
        		    data : data
        	    })
	    	}
        	
	    </script>
	    </content> 
        <input type="hidden" id="ctx" value="${ctx}" />
    </body>
</html>