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
	<script type="text/javascript">
		var ctx = "${ctx}";
	</script>
    </head>
    <body class="pace-done">
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
   
    <span>评估待结算列表</span>
    <input type="hidden" id="ctx" value="${ctx}"/>
        <div id="wrapper" class="Index">
       			<!-- Main view -->
                <div class="main-bonus">
                    <div class="bonus-wrap">
                        <div class="ibox-content bonus-m-con">
                            <div class="row">
                                <div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">案件状态</label>
                                        <div class="col-lg-9 col-md-9">
												<select name="" class="form-control" id="caseStatus">
													<option value="" selected="selected">请选择</option>
													<option value="0">已结算</option>
													<option value="1">未结算</option>
													<option value="2">已核对</option>
													<option value="3">未核对</option>
													<option value="4">已审批</option>
													<option value="5">未审批</option>
													<!--  <option value="6">已驳回</option>-->
												</select>
                                          <!--  <aist:dict id="caseProperty" name="case_property" tag="myCaseList" display="select" dictType="30003" clazz="select_control sign_right_one_case"/> -->
                                        </div>
                                </div>
                               <!--  <div class="col-lg-5 col-md-5">
                                    <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">结算时间</label>
                                        <div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
											<input id="dtBegin_0" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value=""> 
											
										</div>
                                       
                                        
                                    </div>
                                </div> -->
                                
                                 <div class="col-lg-5 col-md-5">
                                    <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">已结算时间</label>
                                         <div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
											<input id="dtBegin_0" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value=""> 
											
										</div>
                                    </div>
                                </div>

								<div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">费用调整类型</label>
                                        <div class="col-lg-9 col-md-9">
												<select name="" class="form-control" id="costUpdateType">
													<!--<option value="" selected="selected">请选择</option>
													<option value="0">已结算</option>
													<option value="1">未结算</option>
													<option value="2">已核对</option>
													<option value="3">未核对</option>
													<option value="4">已审批</option>
													<option value="5">未审批</option>
													<option value="6">已驳回</option>-->
												</select>
                                          <!--  <aist:dict id="caseProperty" name="case_property" tag="myCaseList" display="select" dictType="30003" clazz="select_control sign_right_one_case"/> -->
                                        </div>
                                </div>
                            </div>
                            <div class="row">
                            	<div class="col-lg-5 col-md-5">    
                            		 <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">贷款权证</label>
                                        <div class="col-lg-9 col-md-9">
                                       		 <input type="text" class="teamcode form-control" id="loadWarrant" name="ctmNo" value="">
                                         <!--  <aist:dict id="caseProperty" name="case_property" tag="myCaseList" display="select" dictType=" " clazz="select_control sign_right_one_case"/> -->
                                        </div>
                                    </div>
                            	</div>
                            	<div class="col-lg-5 col-md-5 ">    
                            			<div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">评估公司</label>
                                        <input type="text" class="teamcode input_type" id="ctmNo" name="evalOrg" value="">
                                      <!-- <aist:dict id="caseProperty" name="case_property" tag="myCaseList" display="select" dictType=" " clazz="select_control sign_right_one_case"/> -->
                                    </div>
                            	</div>
                            </div>
                            
                            <div class="row">
	                            	
                               		 <div class="row m-t-sm">
										<div class="form_content">
											<div class="more_btn">
												<button id="searchButton" type="button" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
												
					                            	<shiro:hasPermission name="TRADE.CASE.LIST.EXPORT">  
														<a data-toggle="modal" class="btn btn-success" href="javascript:void(0)" id="exportBtn">导出到excel</a>
													</shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
												<button id="batchappro"  type="button" class="btn btn-success" >批量审批</button>&nbsp;&nbsp;&nbsp;
												<button id="caseAdd"  type="button" class="btn btn-success" >新增结算单</button> &nbsp;&nbsp;&nbsp;
												<!--<button id="caseUpdate"  type="button" class="btn btn-success" onclick="caseUpdate();">修改</button>  -->
												<!-- <button id="addNewJS3"  type="button" class="btn btn-success" onclick="caseCheck2();">核对</button>
												<a role="button" id="caseResetes" class="btn btn-primary btn-xm btn-activity" href="javascript:caseCheck()">核对2</a> -->
											</div>
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
                                	<th ><input type="checkbox" id="checkAllNot" class="i-checks"/></th>
                                    <th><span class="sort active" sortColumn="ef.CASE_CODE" sord="desc">案件编号</span></th>
                                    <th>产权地址</th>
                                    <th>费用调整类型</th>
                                    <th><span class="sort" >评估公司</span></th>
                                     <th>
                                    	<p class="aa">评估申请日期</p>
                                    	<p class="aa">出报告日期</p>
                                    </th>
                                    <th>实收评估费</th>
                                    <th>返利金额</th>
                                    <th><span class="sort" >结算费用</span></th>
                                    <th>贷款权证</th>
                                    <th>驳回原因</th>
                                   <th>状态</th>
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
            <!-- /Main view -->
        </div>
        
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <%-- <script src="<c:url value='/js/bootstrap.min.js' />"></script>  --%>
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
        <!-- 评估待结算 -->
        <%-- <script src="<c:url value='/js/trunk/eval/evalWaitAccountList.js' />"></script> --%>
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		<script id="evalWaitAccountList" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
                                    <td>
										<input type="checkbox" name="my_checkbox" class="i-checks" onclick="_checkbox()" value="案件编号" cunu="0" pkid="37716"  casecode="ZY-SH-201707-0086">
										<input type="hidden" name="case_code" value="案件编号">
										<input type="hidden" name="eval_code" value="评估单编号">
									</td>
                                    <td>{{item.案件编号}}</td>
                                    <td>{{item.产权地址}}</td>
									<td>{{item.费用调整类型}}</td>
                                    <td>{{item.费用调整类型}}</td>
                                    <td>{{item.评估公司}}</td>
                                    <td>
										<p>申：{{item.评估申请日期}}</p>
										<p>出：{{item.出报告日期}}</p>
									</td>
									<td>{{item.实收评估费}}</a></td>
                                    <td>{{item.返利金额}}</td>
                                    <td>{{item.结算费用}}</td>
                                    <td>{{item.贷款权证}}</td>
                                    <td>{{item.驳回原因}}</td>
                                    <td>{{item.状态}}</td>
									<td>
										<a href="{{ctx}}/engine/task/{{item.ID}}/process" target="_blank">修改</a>
										<a href="url" target="_blank">无需结算</a>
									</td>
                                </tr>
						{{/each}}
	    </script>
	    
	    <!-- <script>
	        var ctx = "${ctx}";
	  		
    		
        	jQuery(document).ready(function() {
        		aist.sortWrapper({
        			reloadGrid : reloadGrid
        		});
				
        		$("#batchappro").attr("disabled", false);
        		$("#addNewJS").attr("disabled", false);
        		
        		
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

	    </script> -->
	    </content> 
          </body>
</html>