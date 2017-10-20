<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>首页</title>
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
    </head>
    
    <body class="pace-done">
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <span>审批待结算列表</span>
        <div id="wrapper" class="Index">
       			<!-- Main view -->
             
                 
                    <div class="bonus-table">
                        <table>
                            <thead>
                                <tr>
                                	<!-- <th ><input type="checkbox" id="checkAllNot" class="i-checks"/></th> -->
                                    <th><span class="sort active" sortColumn="ef.CASE_CODE" sord="desc">案件编号</span></th>
                                    <th>产证地址</th>
                                    <th>评估公司</th>
                                    <th>实收评估费</th>
                                    <th>返利金额</th>
                                    <th>
                                    	费用
                                    </th>
                                    <th>备注信息</th>
                                 	<th>贷款权证</th>
                                    <th>审批</th>
                                    
                                </tr>
                            </thead>
                            <tbody id="t_body_data_contents">
                                                              
                            </tbody>
                        </table>                     
                    </div>
                    <div><p><button ><span>审批通过</span></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<button><span>审批通过</span></button></p>
                    </div>
                    <div>
                    	<span>审批记录</span>
                    	<table class="bonus-table"  border="2" >
                    		<thead>
                    			 <tr>
                                	<!-- <th ><input type="checkbox" id="checkAllNot" class="i-checks"/></th> -->
                                 <!--    <th><span class="sort active" sortColumn="ef.CASE_CODE" sord="desc">案件编号</span></th> -->
                                    <th>审批人</th>
                                    <th>审批时间</th>
                                    <th>审批意见</th>
                                    
                                    
                                </tr>
                    		</thead>
                    		<tbody id="t_body_data_contents">
                                                              
                            </tbody>
                    	</table>
                    
                    </div>
                    <div><p><button ><span>保存</span></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<button><span>提交</span></button></p>
                    </div>
                	
                </div>
            </div>
            <!-- /Main view -->
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
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		<script id="evalListTemp" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
                                    <td><a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a></td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.EVAL_FEE}}</td>
                                    <td>{{item.CON_PRICE}}</td>
                                    <td>{{item.RECORD_TIME}}</td>
                                    <td>{{item.PROCESSOR_ID}}</td>
                                </tr>
						{{/each}}
	    </script>
	    
	    <script>
	        var ctx = "${ctx}";
	  		
    		
        	jQuery(document).ready(function() {
        		aist.sortWrapper({
        			reloadGrid : reloadGrid
        		});

        		//初始化数据
        	    reloadGrid();
        	 	// 查询
     			$('#searchButton').click(function() {
     				reloadGrid();
     			});
     			$('#datepicker_0').datepicker({
     				format : 'yyyy-mm-dd',
     				weekStart : 1,
     				autoclose : true,
     				todayBtn : 'linked',
     				language : 'zh-CN'
     			});
        	});
	    	function exportExcel(){
	    		var data1=packgeData();
	    		$.exportExcel({ctx : "${ctx}",
		    	queryId : 'queryEvalItemListForStatistics',
		    	colomns : ['CASE_CODE','PROPERTY_ADDR','CON_PRICE','EVAL_FEE','RECORD_TIME','PROCESSOR_ID'],
		    	data:data1});
	    	}
        	function packgeData(page){
        		var data1 = {};
        	    
        	    data1.rows = 12;
        	    data1.page = 1;
        	    if(page){
        	    	data1.page=page;
        	    }
        	    var sTime,eTime;
        	    sTime=$('#dtBegin_0').val();
        	    eTime=$('#dtEnd_0').val();
        	    
        	    data1.search_caseCode = $("#caseCode").val();
        	    data1.search_propertyAddr = $("#propertyAddr").val();
        	    data1.argu_proOrgId =$("#h_proOrgId").val();
        	    data1.search_pUserId  =$('#inTextVal').attr('hVal');
				data1.search_startTime=sTime;
				data1.search_endTime = (eTime!=''?eTime+ " 23:59:59":eTime);
				return data1;
        	}
			function reloadGrid(page) {
				var data1=packgeData(page);
				data1.queryId = "queryEvalItemListForStatistics";
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
		
			function radioYuCuiOrgSelectCallBack(array){
			    if(array && array.length >0){
			        $("#txt_proOrgId").val(array[0].name);
					$("#h_proOrgId").val(array[0].id);

				}else{
					$("#txt_proOrgId").val("");
					$("#h_proOrgId").val("");
				}
			}
			function selectUserBack(array){
				if(array && array.length >0){
			        $("#inTextVal").val(array[0].username);
					$("#inTextVal").attr('hVal',array[0].userId);

				}else{
					$("#inTextVal").val("");
					$("#inTextVal").attr('hVal',"");
				}
			}

	    </script>
	    </content> 
          </body>
</html>