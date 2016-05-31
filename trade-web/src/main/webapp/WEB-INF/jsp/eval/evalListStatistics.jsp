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
                                            <input type="text" class="form-control" id="txt_proOrgId" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})" >
                                            <input type="hidden" id="h_proOrgId" value="${serviceDepId}">
                                        </div>
                                    </div>
                            	</div>
                            	<div class="col-lg-5 col-md-5">    
                            			<div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">人员</label>
                                        <div class="col-lg-9 col-md-9">
                                            <input id="inTextVal" type="text" class="form-control pull-left">
                                        </div>
                                    </div>
                            	</div>
                            </div>
                            <div class="row">                           	
    							<div class="col-lg-5 col-md-5">
  									<div class="form-group">
										<label class="col-lg-3 col-md-3 control-label font_w">时间</label>
                            			<div class="col-lg-9 col-md-9">
	                            			<div id="datepicker_0" class="input-group input-medium date-picker input-daterange pull-left" data-date-format="yyyy-mm-dd" style="width: 412px;">
												<input id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px;" type="text" value="" placeholder="起始日期"> <span class="input-group-addon">到</span>
												<input id="dtEnd_0" name="dtEnd" class="form-control" style="font-size: 13px;" type="text" value="" placeholder="结束日期">
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
                                    <th>案件编号</th>
                                    <th>产证地址</th>
                                    <th>评估费</th>
                                    <th>合同价(万元)</th>
                                    <th>时间</th>
                                    <th>经办人</th>
                                </tr>
                            </thead>
                            <tbody id="t_body_data_contents">
                                <tr class="border-e7">
                                    <td></td>
                                    <td>ZY-AJ-201604-0352</td>
                                    <td>上海虹口区临平路片区物华路246弄4号2402室</td>
                                    <td>2016-03-21</td>
                                    <td>2016-03-30</td>
                                    <td>780</td>
                                </tr>
                                
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
        
        <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
        <script src="${ctx}/js/plugins/jquery.custom.js"></script>
        <script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
        <script	src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		<script id="evalListTemp" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
                                    <td>{{item.CASE_CODE}}</td>
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
        		//初始化数据
        	    reloadGrid();
        	    initAutocomplete(ctx+"/labelVal/queryUserInfo");
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
			function initAutocomplete(url){
				$("#inTextVal").AutoComplete({
					data:url,
					'itemHeight': 20,
			        'width': 280,
			        maxItems:10,
			        ajaxType:'POST',
			        beforeLoadDataHandler:function(){
			        	$("#inTextVal").attr('hVal','');
			        	return true;
			        },
			        afterSelectedHandler:function(data){ 
			        	if(data&&data.value){
			        		$("#inTextVal").attr('hVal',data.value);
			        	}else{
			        		$("#inTextVal").attr('hVal','');
			        	}
					}
			    }).AutoComplete('show');
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

	    </script>
	    </content> 
          </body>
</html>