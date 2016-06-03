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
        <link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
	<link
	href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"
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
        <div id="wrapper" class="Index">
       			<!-- Main view -->
                <div class="main-bonus">
                    <div class="bonus-wrap">
                        <div class="ibox-content bonus-m-con">
                            <div class="row">
                                <div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">案件编号</label>
                                        <div class="col-lg-9 col-md-9">
                                            <input type="text" class="form-control" id="caseCode" name="caseCode">
                                        </div>
                                </div>
                                <div class="col-lg-5 col-md-5">
                                    <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">产证地址</label>
                                        <div class="col-lg-9 col-md-9">
                                            <input type="text" class="form-control" id="propertyAddr" name="propertyAddr">
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                            	<div class="col-lg-5 col-md-5">    
                            		 <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">组织</label>
                                        <div class="col-lg-9 col-md-9">
                                            <input type="text" style="background-color:#FFFFFF" readonly="readonly" class="form-control tbsporg" id="txt_proOrgId" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})" value='${serOrgName }'>
                                            <input type="hidden" id="h_proOrgId" value="${serOrgId==null?serviceDepId:serOrgId}">
                                        </div>
                                    </div>
                            	</div>
                            	<div class="col-lg-5 col-md-5 ${isConsultant ? 'hideDiv' : '' }">    
                            			<div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">人员</label>
                                        <div class="col-lg-9 col-md-9">
                                        	<input type="text" id="inTextVal" style="background-color:#FFFFFF" name="radioOrgName" class="form-control tbspuser" hVal="${serUserId }" value="${userInfo }"
													 readonly="readonly"
													onclick="userSelect({startOrgId:'${serviceDepId}',expandNodeId:'${serviceDepId}',
													nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" />
                                        </div>
                                    </div>
                            	</div>
                            </div>
                            <div class="row">                           	
    							<div class="col-lg-5 col-md-5">
  									<div class="form-group">
										<label class="col-lg-3 col-md-3 control-label font_w">确认收款时间</label>
                            			<div class="col-lg-9 col-md-9">
	                            			<div id="datepicker_0" class="input-group input-medium date-picker input-daterange pull-left" data-date-format="yyyy-mm-dd" style="width: 412px;">
												<input id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px;" type="text" placeholder="起始日期" value="${sTime }"> <span class="input-group-addon">到</span>
												<input id="dtEnd_0" name="dtEnd" class="form-control" style="font-size: 13px;" type="text"  placeholder="结束日期" value="${eTime }">
											</div>
										</div>
                            		</div>
                            	</div>
							</div>
                            <div class="row">
                            <div class="form-group">
	                            	 <div class="col-lg-5 col-md-5">    
	                            	 <label class="col-lg-3 col-md-3 control-label font_w">&nbsp;</label>                               
	                                    <button class="btn btn-warning bonus-search" id="searchButton"><i class="fa fa-search"></i><span class="bold">搜索</span></button>
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
                                    <th><span class="sort active" sortColumn="ef.CASE_CODE" sord="desc">案件编号</span></th>
                                    <th>产证地址</th>
                                    <th><span class="sort" sortColumn="ef.EVAL_FEE" sord="asc">评估费</span></th>
                                    <th>合同价(万元)</th>
                                    <th><span class="sort" sortColumn="ef.RECORD_TIME" sord="desc">确认收款时间</span></th>
                                    <th>经办人</th>
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
            <!-- /Main view -->
        </div>
        
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <%--  <script src="${ctx}/js/bootstrap.min.js"></script> --%>
        <script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
         <!-- 日期控件 -->
    	<script	src="${ctx}/js/plugins/dateSelect/dateSelect.js?v=1.0.2"></script>
        <!-- Custom and plugin javascript -->
        <script src="${ctx}/js/inspinia.js"></script>
        <script src="${ctx}/js/plugins/pace/pace.min.js"></script>
        <!-- 弹出框插件 -->
	    <script src="${ctx}/js/plugins/layer/layer.js"></script>
	    <script src="${ctx}/js/plugins/layer/extend/layer.ext.js"></script>
        <!-- 分页控件  -->
        <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
        <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
        <script src="${ctx}/js/plugins/jquery.custom.js"></script>
        <script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
        <script	src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
        <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
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
	    		 
        	
			function reloadGrid(page) {
    		
	    		var data1 = {};
        	    data1.queryId = "queryEvalItemListForStatistics";
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
				
				aist.wrap(data1);
        	    fetchData(data1);
	    	}
			function fetchData(p){
				  $.ajax({
		  			  async: false,
		  	          url:ctx+ "/quickGrid/findPage" ,
		  	          method: "post",
		  	          dataType: "json",
		  	          data: p,
		  	          success: function(data){
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