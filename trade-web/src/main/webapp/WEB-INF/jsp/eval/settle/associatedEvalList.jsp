<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>关联评估单列表</title>
        <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
        <link href="<c:url value='/fonts/font-awesome/css/font-awesome.min.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
        <!-- IonRangeSlider -->
        <link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
        <link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
        <link href="<c:url value='/css/animate.css' />" rel="stylesheet">
        <%-- <link href="<c:url value='/css/style.min.css' />" rel="stylesheet">  --%>
        <link href="<c:url value='/css/transcss/award/bonus.css' />" rel="stylesheet">
        <!-- Gritter -->
        <link href="js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
        <!-- 分页控件 -->
        <link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
        <link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
	<link
	href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />"
	rel="stylesheet">
	<style>
		.bonus-m-con .bonus-search{margin-left:15px;}
		.case-num{
text-decoration: underline !important;
}
.case-num:HOVER{
text-decoration: underline !important;
}
.case-num:visited{
 text-decoration: underline !important;
}
.hideDiv{
display: none;}
	</style>
	<content tag="pagetitle">关联评估单列表</content>
    </head>
    
    <body class="pace-done">
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <span>关联评估单列表</span>
    	<div class="row-fluid">
	        <div id="wrapper" class="Index">
	       			<!-- Main view -->
	                <div class="main-bonus">
	                    <div class="bonus-wrap">
	                        <div class="ibox-content bonus-m-con">
	                            <div class="row">
	                                <div class="col-lg-5 col-md-5">
	                                        <label class="col-lg-3 col-md-3 control-label font_w">评估公司</label>
	                                        <div class="col-lg-9 col-md-9">
	                                            <select  id="finOrgId" class="form-control select_control">
												</select>
	                                        </div>
	                                </div>
	                                <div class="col-lg-5 col-md-5">
	                                    <div class="form-group">
	                                        <label class="col-lg-3 col-md-3 control-label font_w">评估申请时间</label>
	                                         <div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
												<input id="dtBegin_0" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value=""> 
												
											</div>
	                                    </div>
	                                </div>
	                            </div>
	                            <div >
		             		 		<div id="select_div_1" >
					           			<div >
			                                 <select id="inTextType" data-placeholder="搜索条件设定" onchange="intextTypeChange()">
													<option value="1" selected>产证地址</option>
													<option value="0">案件编号</option>
													<option value="2">评估单编号</option>
													
											 </select>
											 <input id="inTextVal" type="text"  style="width:320px;overflow-x:visible;overflow-y:visible;">
											 <button id="searchButton" type="button" class="btn btn-success">查询</button>
					                     </div>
									 </div>
								</div>
	                        </div>
	                    </div>
	                 </div>
	                 
	                    <div class="bonus-table">
	                        <table>
	                            <thead>
	                                <tr>
	                                	<!-- <th ><input type="checkbox" id="checkAllNot" class="i-checks"/></th> -->
	                                    <th>
	                                    	
	                                    <p>案件编号</p>
	                                    	<p>评估单编号</p>
	                                    
	                                    </th>
	                                    <th>产证地址</th>
	                                    <th>评估公司</th>
	                                    <th>贷款权证</th>
	                                      <th>经纪人</th>
	                                    <th>评估申请日期
	                                    	<!-- <p class="aa">评估申请日期</p>
	                                    	<p class="aa">&nbsp;&nbsp;&nbsp;&nbsp;出报告日期</p> -->
	                                    </th> 
	                                   
	                                    <th>结算费用</th>
	                                    
	                                    <th>操作</th>
	                                    
	                                </tr>
	                            </thead>
	                            <tbody id="t_body_data_contents">
	                                                              
	                            </tbody>
	                        </table>                     
	                    </div>
	                	<div class="text-center">
							<span id="currentTotalPage"><strong class="bold"></strong></span>
							<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
							<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
					    </div>
	          </div>
	      </div>
        
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <%--  <script src="<c:url value='/js/bootstrap.min.js' />"></script> --%>
        <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
        <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
         <!-- 日期控件 -->
    	<script	src="<c:url value='/js/plugins/dateSelect/dateSelect.js' />"></script>
        <!-- Custom and plugin javascript -->
        <script src="<c:url value='/js/inspinia.js' />"></script>
        <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>
        <!-- 弹出框插件 -->
	    <script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
	    <script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
        <!-- 分页控件  -->
        <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
        <script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
        <script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
        <script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
        <script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
        <%-- <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> --%>
		<script id="evalListTemp" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
                                     <td>
										<p>案：{{item.caseCode}}</p>
										<p>评：{{item.evalCode}}</p>
									</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.EVA_COMPANY}}</td>
									<td></td>
									<td></td>
									<td>
										{{item.APPLY_DATE}}
									</td>
                                    <td>{{item.SETTLE_FEE}}</td>
									<td class="center">
									<a href="${ctx}/eval/settle/associatedShowForm?caseCode={{item.caseCode}}" target="_blank">关联案件</a>
                        			</td>
                                </tr>
						{{/each}}
	    </script>
	    
	    <script>
	         var ctx = "${ctx}";
        	jQuery(document).ready(function() {
     			$('#datepicker_0').datepicker({
     				format : 'yyyy-mm-dd',
     				weekStart : 1,
     				autoclose : true,
     				todayBtn : 'linked',
     				language : 'zh-CN'
     			});
     			
     			aist.sortWrapper({
        			reloadGrid : reloadGrid
        		});

        		//初始化数据
        	    reloadGrid();
        		
        	  	//获取评估公司
        		getEvaFinOrg('finOrgId');
        		 // 查询
     			$('#searchButton').click(function() {
     				reloadGrid();
     			});
     			
        	});
        	/* function exportExcel(){
	    		var data1=packgeData();
	    		$.exportExcel({ctx : "${ctx}",
		    	queryId : 'queryEvalItemListForStatistics',
		    	colomns : ['CASE_CODE','PROPERTY_ADDR','CON_PRICE','EVAL_FEE','RECORD_TIME','PROCESSOR_ID'],
		    	data:data1});
	    	} */
        	function packgeData(page){
        		var data1 = {};
        	    
        	    data1.rows = 12;
        	    data1.page = 1;
        	    if(page){
        	    	data1.page=page;
        	    }
        	    
        	    var propertyAddr = "";
        		var caseCode =  "";
        		var evalCode =  "";
        		//产证地址,案件编号,评估单编号
        		var inTextVal = $("#inTextVal").val();
        		if (inTextVal != null && inTextVal.trim() != "") {
        			var inTextType = $('#inTextType').val();
        			if (inTextType == '0') {
        				caseCode = inTextVal.trim();
        			} else if (inTextType == '1') {
        				//产证地址
        				propertyAddr = inTextVal.trim();
        			} else if (inTextType == '2') {
        				//评估单编号
        				evalCode = inTextVal.trim();
        			}
        		}
        		 data1.search_propertyAddr = propertyAddr;
        		 data1.search_caseCode = caseCode;
        		 data1.search_evalCode = evalCode;
        	    data1.search_finOrgID = $("#finOrgId").val();
				data1.search_applyDate=$('#dtBegin_0').val();
				return data1;
        	}
			function reloadGrid(page) {
				var data1=packgeData(page);
				data1.queryId = "queryEvalWaitEndList";
				aist.wrap(data1);
        	    fetchData(data1);
	    	}
			function fetchData(p){
				  $.ajax({
		  			  async: true,
		  	          url:ctx+ "/quickGrid/findPage" ,
		  	          method: "post",
		  	          dataType: "json",
		  	          data: p,
		  	          beforeSend : function() {
						$.blockUI({
							message : $("#salesLoading"),
							css : {
								'border' : 'none',
								'z-index' : '9999'
							}
						});
						$(".blockOverlay").css({
							'z-index' : '9998'
						});
					},
		  	          success: function(data){
		  	        	  $.unblockUI();
		  	        	  data.ctx = ctx;
		  	        	  var tsAwardBaseList= template('evalListTemp' , data);
			                  $("#t_body_data_contents").empty();
			                  $("#t_body_data_contents").html(tsAwardBaseList);
			                  
			                 initpage(data.total,data.pagesize,data.page, data.records);
		  	          }
		  	     });
			} 
			function initpage(totalCount,pageSize,currentPage,records)
			{
				if(totalCount>1500){
					totalCount = 1500;
				}
				var currentTotalstrong=$('#currentTotalPage').find('strong');
				if (totalCount<1 || pageSize<1 || currentPage<1)
				{
					$(currentTotalstrong).empty();
					$('#totalP').text(0);
					$("#pageBar").empty();
					return;
				}
				$(currentTotalstrong).empty();
				$(currentTotalstrong).text(currentPage+'/'+totalCount);
				$('#totalP').text(records);
				
				$("#pageBar").twbsPagination({
					totalPages:totalCount,
					visiblePages:9,
					startPage:currentPage,
					first:'<i class="icon-step-backward"></i>',
					prev:'<i class="icon-chevron-left"></i>',
					next:'<i class="icon-chevron-right"></i>',
					last:'<i class="icon-step-forward"></i>',
					showGoto:true,
					onPageClick: function (event, page) {
						 //console.log(page);
						reloadGrid(page);
				    }
				});
			}
		
			/**
			 * 获取评估公司 格式化
			 * @param finOrgId
			 */
			function getEvaFinOrg(finOrgId){
				var url = "/manage/queryEvaCompany";
				$.ajax({
					async: true,
					type:'POST',
					url:ctx+url,
					dataType:'json',
					success:function(data){
						var html = '<option value="" selected>请选择</option>';
						if(data != null){
							$.each(data,function(i,item){
								html += '<option value="'+item.pkid+'">'+item.finOrgName+'</option>';
							});
						}					
						$('#'+finOrgId).empty();
						$('#'+finOrgId).append(html);
					},
					error : function(errors) {
					}
				});
			}

	    </script>
	    </content> 
          </body>
</html>