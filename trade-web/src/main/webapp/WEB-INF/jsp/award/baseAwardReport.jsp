<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>计件奖金报表</title>
        <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" />
        <link href="${ctx}/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet" />
        <!-- IonRangeSlider -->
        <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet" />
        <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet" />
        <link href="${ctx}/css/animate.css" rel="stylesheet" />
        <%-- <link href="${ctx}/css/style.min.css" rel="stylesheet">  --%>
        <link href="${ctx}/css/transcss/kpi/bonus.css" rel="stylesheet" />
        <!-- Gritter -->
        <link href="js/plugins/gritter/jquery.gritter.css" rel="stylesheet" />
        <!-- 分页控件 -->
        <link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
    </head>
    <body class="pace-done">
        <div id="wrapper" class="Index">
       			<!-- Main view -->
                <div class="main-bonus">
                    <div class="bonus-wrap">
                        <div class="bonus-header">
                        <div class="bonus-m">
                            <div class="ibox-title">
                                <input type="button" class="btn btn-warning m-r-sm" value="&lt;">
                                <h5 class="month">yyyy/MM月</h5>
                                <input type="button" class="btn btn-warning m-r-sm disable" disabled value="&gt;" style="margin-left:10px;">
                                <span style="color:red">此日期为计件奖金的生成日期。如需查看某月过户的案件计件奖金，请按过户日期搜索</span>
                            </div>
                        </div>
                        <div class="ibox-content bonus-m-con">
                            <div class="row m-t">
                                <div class="col-lg-6 col-md-6">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">案件编号</label>
                                        <div class="col-lg-10 col-md-10">
                                            <input type="text" class="form-control" id="caseCode" name="caseCode">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6">
                                    <div class="form-group">
                                        <label class="col-lg-2 col-md-2 control-label font_w">物业地址</label>
                                        <div class="col-lg-10 col-md-10">
                                            <input type="text" class="form-control" id="propertyAddr" name="propertyAddr">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            
                            <div class="row">
								<div class="col-lg-6 col-md-6">
									<div class="form-group ">
										<label class="col-lg-2 col-md-2 control-label font_w">组织</label>
										<div class="col-md-10">
												<input type="text" class="form-control tbsporg" id="teamCode" name="teamCode" readonly="readonly" 
											   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
											   startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
											   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,
											   expandNodeId:''})" />
											 <input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId"> 
										</div>
									</div>
								</div>
								
								<div class="col-lg-6 col-md-6 ">    
                            		<div class="form-group">
                                        <label class="col-lg-2 col-md-2 control-label font_w">人员</label>
                                        <div class="col-lg-10 col-md-10">
                                        	<input type="text" id="inTextVal" style="background-color:#FFFFFF" name="radioOrgName" class="form-control tbspuser" readonly="readonly"  hVal="${serUserId }" value="${userInfo }"
													 readonly="readonly"
													onclick="userSelect({startOrgId:'${serviceDepId}',expandNodeId:'${serviceDepId}',
													nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" />
                                        </div>
                                    </div>
                            	</div>
                            </div>
                            
                        	<div class="row m-t">
                        		<div class="col-lg-7 col-md-7">
                                	<label class="col-sm-2 control-label" id="case_date">过户时间</label>
									<div id="datepicker"
										class="input-group input-medium date-picker input-daterange"
										data-date-format="yyyy-mm-dd">
										<input id="dtBegin" name="dtBegin" class="form-control"
											style="font-size: 13px;" type="text" value=""
											placeholder="起始日期"> 
										<span class="input-group-addon">到</span>
										<input id="dtEnd" name="dtEnd" class="form-control"
											style="font-size: 13px;" type="text" value=""
											placeholder="结束日期" />
									</div>
								</div>
                                <div class="col-lg-4 col-md-4">                                   
                                    <button class="btn btn-warning" id="searchButton"><i class="fa fa-search"></i><span class="bold">搜索</span></button>
                                	<button type="button" class="btn btn-primary" onclick="javascript:exportToExcel()">
							                                导出至Excel
							        </button>
                                </div>
                                
                        	</div>
                        </div>
                    </div>
                    </div>
                    
                 	<div class="row">
	                 	<div class="col-lg-12">
		                    <div class="bonus-table">
		                    	<div class="ibox-title">
									<h5 class="col-lg-8 col-md-8">计件奖金报表</h5>
									<span class="col-lg-4 col-md-4" id="countMsg"></span>
								</div>
		                        <table>
		                            <thead>
		                                <tr>
		                                    <th></th>
		                                    <th>案件编号</th>
		                                    <th>产证地址</th>
		                                    <th>过户时间</th>
		                                    <th>生成时间</th>
		                                    <th>基础奖金</th>
		                                    <th>最终奖金</th>
		                                    <th>操作</th>
		                                </tr>
		                            </thead>
		                            <tbody id="TsAwardBaseList">
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
            </div>
            <!-- /Main view -->
        </div>
        
        <form action="#" accept-charset="utf-8" method="post" id="excelChangeForm"></form>
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <!-- 组织控件 --> 
		<%@include file="/WEB-INF/jsp/tbsp/common/userorg.jsp"%>
        <%--  <script src="${ctx}/js/bootstrap.min.js"></script> --%>
        <script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
         <!-- 日期控件 -->
    	<script	src="${ctx}/js/plugins/dateSelect/dateSelect.js?v=1.0.2"></script>
    	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    	
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
        <script src="${ctx}/transjs/award/baseAwardReport.js"></script>
		<script id="tsAwardBaseList" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
                                    <td></td>
                                    <td>{{item.CASE_CODE}}</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.GUOHU_TIME}}</td>

									<td>{{item.PAID_TIME}}</td>
                                    <td>{{item.SUM_BASE_AMOUNT}}</td>
									<td>{{item.AWARD_KPI_MONEY_SUM}}</td>
                                    <td><div class="expand" id="{{item.CASE_CODE}}">展开</div></td>
                                </tr>
                                <tr class="toogle-show border-e7" id="toggle{{item.CASE_CODE}}" style="display:none;">
                                    
                                </tr>
						{{/each}}
	    </script>
	    <script id="tsAwardSrvList" type= "text/html">
                                    <td colspan="8" class="two-td">
                                        <table class="two-table">
                                            <thead>
                                                <tr>
                                                    <th>服务</th>
													<th>人员</th>
                                                    <th>基础奖金</th>
                                                    <th>环节占比</th>

													<th>满意度</th>
													<th>金融单量</th>
													<th>最终奖金</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                               {{each rows as item index}}
                                                <tr> 
                                                    <td>{{item.SRV_CODE}}</td>
													<td>{{item.PARTICIPANT}}</td>
													<td>{{item.BASE_AMOUNT}}</td>
                                                    <td>{{item.SRV_PART_IN}}/{{item.SRV_PART_TOTAL}}</td>

													<td>{{item.SATISFACTION}}</td>
													<td>{{item.FIN_ORDER}}
															{{if item.FIN_ORDER != null && item.FIN_ORDER_ROLL != null }}+{{/if}}
														{{item.FIN_ORDER_ROLL}}
													</td>
													<td>{{item.AWARD_KPI_MONEY}}</td>
                                                </tr>
												{{/each}}
                                            </tbody>
                                        </table>
                                    </td>
	    </script>
	    <script>
	        var ctx = "${ctx}";
	  		//初始化日期控件
        	var monthSel=new DateSelect($('.bonus-m'),{max:new Date(),moveDone:reloadGrid});	 
    		
	  		function getCountMsg(){
	  			var data = {};
	  			data.queryId = "awardReportCount";
	    	    data.pagination = false;
	    	    data.caseCode = $("#caseCode").val();
	    	 	data.paidTime = monthSel.getDate().format('yyyy-MM-dd');
        	    data.propertyAddr = $("#propertyAddr").val();
        	    data.dtBegin=$("#dtBegin").val();
        	    data.dtEnd=$("#dtEnd").val();
        	    data.caseUserId=$("#inTextVal").attr("hVal");
        	    data.caseOrgId=$("#yuCuiOriGrpId").val();
		    	 	
	  			$.ajax({
    			  async: false,
    	          url:ctx+ "/quickGrid/findPage" ,
    	          method: "post",
    	          dataType: "json",
    	          data: data,
    	          success: function(data){
    	        	  var cnt = data.rows[0].AWARD_KPI_MONEY_SUM;
    	        	  $("#countMsg").empty();
    	        	  if(typeof(cnt) != 'undefined'){
        	        	  $("#countMsg").append("<b>最终奖金总数：" + cnt +"</b>");
    	        	  }else{
    	        		  $("#countMsg").append("<b>最终奖金总数：" + 0 +"</b>");
    	        	  }
    	          }
    	     	});
	  		}
	  		
        	jQuery(document).ready(function() {
        		//初始化数据
        	    reloadGrid();
        	    getCountMsg();
        	 	// 查询
     			$('#searchButton').click(function() {
     				reloadGrid();
     				getCountMsg();
     			});
        	 	
        	    
	    		$(document).on("click",".expand",function(){
    				var id = this.id;
   	  			  	if($(this).html() == "展开") {
   	  				  $(this).html("收起");
   	  				  // 发出请求
   	  				    	var data = {};
  				    	    data.queryId = "awardInfoReportList";
  				    	    data.pagination = false;
  				    	    data.caseCode = id;
  				    	 	data.paidTime = monthSel.getDate().format('yyyy-MM-dd');
  			        	    data.propertyAddr = $("#propertyAddr").val();
  			        	    data.dtBegin=$("#dtBegin").val();
  			        	    data.dtEnd=$("#dtEnd").val();
  			        	    data.caseUserId=$("#inTextVal").attr("hVal");
  			        	    data.caseOrgId=$("#yuCuiOriGrpId").val();
  				    	 	
  				    		$.ajax({
  				    			  async: false,
  				    	          url:ctx+ "/quickGrid/findPage" ,
  				    	          method: "post",
  				    	          dataType: "json",
  				    	          data: data,
  				    	          success: function(data){
  				    	        	  var tsAwardSrvList= template('tsAwardSrvList' , data);
  				    				  $("#toggle"+id).empty();
  				    				  $("#toggle"+id).html(tsAwardSrvList);
  				    	          }
  				    	     });
	   	  			  } else {
	   	  				  $(this).html("展开");
	   	  			  }
	   	  			  $("#toggle"+id).toggle();
    			});
	    	});
        	
			function reloadGrid(bm) {
				if(!bm){
					bm=monthSel.getDate().format('yyyy-MM-dd');	
				}else{
					bm=bm.format('yyyy-MM-dd');
				}
	    		
	    		var data1 = {};
        	    data1.queryId = "baseAwardReportQuery";
        	    data1.rows = 12;
        	    data1.page = 1;
        	    data1.paidTime = bm;
        	    data1.caseCode = $("#caseCode").val();
        	    data1.propertyAddr = $("#propertyAddr").val();
        	    data1.dtBegin=$("#dtBegin").val();
        	    data1.dtEnd=$("#dtEnd").val();
        	    var data2 = {
        	    	paidTime : bm
        	    }
        	    data1.caseUserId=$("#inTextVal").attr("hVal");
        	    data1.caseOrgId=$("#yuCuiOriGrpId").val();
        	    BonusList.init(ctx,data1,data2);
        	    
	    	}
        	
	    	function goPage(page) {
	    		var bm=monthSel.getDate().format('yyyy-MM-dd');	
	    		var data1 = {};
        	    data1.queryId = "baseAwardReportQuery";
        	    data1.rows = 12;
        	    data1.page = page;
        	    data1.caseCode = $("#caseCode").val();
        	    data1.propertyAddr = $("#propertyAddr").val();
        	    
        	    data1.dtBegin=$("#dtBegin").val();
        	    data1.dtEnd=$("#dtEnd").val();
        	    var data2 = {
        	    	paidTime : bm
        	    }
        	    data1.paidTime = bm;
        	    data1.caseUserId=$("#inTextVal").attr("hVal");
        	    data1.caseOrgId=$("#yuCuiOriGrpId").val();
        	    
        	    var data2 = {
        	    	paidTime : bm
        	    }
        	    BonusList.init(ctx,data1,data2);
	    	}
	    	
	    	// 日期控件
	    	$('#datepicker').datepicker({
	    		format : 'yyyy-mm-dd',
	    		weekStart : 1,
	    		autoclose : true,
	    		todayBtn : 'linked',
	    		language : 'zh-CN'
	    	});
	    	
	    	//选业务组织的回调函数
	        function radioYuCuiOrgSelectCallBack(array){
	            if(array && array.length >0){
	                $("#teamCode").val(array[0].name);
	        		$("#yuCuiOriGrpId").val(array[0].id);
	        		
	        	}else{
	        		$("#teamCode").val("");
	        		$("#yuCuiOriGrpId").val("");
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
	        
	        function exportToExcel() { 
	    		var ctx = "${ctx}";
	    		var url = "/quickGrid/findPage?xlsx&";
	    		
	    		var displayColomn = new Array;
	    		displayColomn.push('CASE_CODE');
	    		displayColomn.push('PARTICIPANT');
	    		displayColomn.push('PROPERTY_ADDR');
	    		displayColomn.push('GUOHU_TIME');
	    		displayColomn.push('PAID_TIME');
	    		
	    		displayColomn.push('SRV_CODE');
	    		displayColomn.push('BASE_AMOUNT');
	    		displayColomn.push('SRV_PART_IN_TOTAL');
	    		displayColomn.push('SATISFACTION');
	    		
	    		displayColomn.push('AWARD_KPI_MONEY');
	    		displayColomn.push('FIN_ORDER_CNT');
	    		
	    		var params =  {
	        	    caseCode : $("#caseCode").val(),
	        	    propertyAddr : $("#propertyAddr").val(),
	        	    
	        	    dtBegin: $("#dtBegin").val(),
	        	    dtEnd: $("#dtEnd").val(),
	        	    
	        	    caseUserId: $("#inTextVal").attr("hVal"),
	        	    caseOrgId: $("#yuCuiOriGrpId").val(),
	        	    paidTime: monthSel.getDate().format('yyyy-MM-dd')
		    	};
	    		var queryId = '&queryId=awardInfoExport';
	    		var colomns = '&colomns=' + displayColomn;
	    		url = ctx + url + jQuery.param(params) + queryId + colomns;

	    		$('#excelChangeForm').attr('action', url);
	    		$('#excelChangeForm').submit();
	    		alert("导出至 Excel成功");
	    	}
	    </script>
	    </content> 
    </body>
</html>