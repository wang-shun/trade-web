<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="utf-8"/>
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>满意度列表</title>
    <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/animate.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/style.css' />" />

    <!-- Data Tables -->
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.bootstrap.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.responsive.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.tableTools.min.css' />" />
    <link href="<c:url value='/static/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">

    <!-- index_css -->
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/base.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/trans/css/award/baseAward.css' />" />
</head>
<body>
	<input type="hidden" id="sessionUserId" name="sessionUserId" value="${sessionUserId}" />
	<input type="hidden" id="serviceDepId" name="serviceDepId" value="${serviceDepId}" />
	<input type="hidden" id="serviceJobCode" name="serviceJobCode" value="${serviceJobCode}" />
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>	
    <div id="wrapper">
         <!--*********************** HTML_main*********************** -->
         <div class="wrapper wrapper-content animated fadeInRight">
             <div class="ibox-content border-bottom clearfix space_box">
                 <div class="clearfix"> 
                     <h2 class="title pull-left ml15">
                         满意度列表
                     </h2>
                 </div>
                 <div class="form_list">
                     <div class="line">
                         <div class="form_content">
                             <label class="control-label sign_left_small">
                                 计件年月
                             </label>
                             <input class="input_type datepicker" readonly="readonly" placeholder="请选择年月" value="${belongMonth}" id="search_belong_month" >
						</div>
                     	<div class="form_content">
                             <label class="control-label sign_left_small">
                                 案件编号
                             </label>
                             <input id="search_case_code" class="teamcode input_type" placeholder="请输入" value="">
                     	</div>
                        <div class="form_content" style="margin-left: 127px;">
                             <div class="add_btn">
                                 <button class="btn btn-success" onClick="javascript:syncSatisListToKpi();">
                                     满意度同步
                                 </button>
                                 <button id="btn_search" class="btn btn-success mr5 btn-icon">
                                     <i class="icon iconfont"></i>
                                     查询
                                  </button>
                                 <button class="btn btn-success" onClick="javascript:exportToExcel();">
                                     导出Excel
                                 </button>
                                 <button class="btn btn-grey" onClick="javascript:cleanForm();">
                                     清空
                                 </button>
                             </div>
                         </div>
                      </div>
                 </div>
                 </div>
             </div>
             <div class="row">
                 <div class="col-md-12">
                     <div class="table_content">
                         <div class="bonus-table"></div>
                   </div>
                 </div>
             </div>
         </div>
    <!-- Mainly scripts -->
    <content tag="local_script"> 
    <script src="<c:url value='/static/js/jquery-2.1.1.js' />"></script>
    <script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
    <script src="<c:url value='/static/js/bootstrap.min.js' />"></script>
    <script src="<c:url value='/static/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/static/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
    <script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>

    <script src="<c:url value='/static/js/inspinia.js' />"></script>
    <script src="<c:url value='/static/js/plugins/pace/pace.min.js' />"></script>
    
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script> 
	<script src="<c:url value='/js/template.js" type="text/javascript' />"></script> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script> 
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
	<script id="template_kpiCaseList" type="text/html">
         {{each rows as item index}}
           <tr>
        		<td >
            		{{item.CASE_CODE}}
        		</td>
        		<td>
            		{{item.SALER_SIGN_SAT}}
        		</td>
        		<td>
            		{{item.SALER_GUOHU_SAT}}
        		</td>
        		<td>
            		{{item.SALER_LOAN_SAT}}
        		</td>
        		<td>
            		{{item.BUYER_SIGN_SAT}}
        		</td>
        		<td>
            		{{item.BUYER_GUOHU_SAT}}
        		</td>
        		<td>
            		{{item.BUYER_COM_SAT}}
        		</td>
        		<td>
            		{{item.BUYER_PSF_SAT}}
        		</td>
        		<td>{{item.SALER_CALLBACK == "1"?"是":"否"}}-{{item.BUYER_CALLBACK == "1"?"是":"否"}}</td>
        		<td>{{item.BELONG_MONTH}}</td>
    		</tr>                    		
		 {{/each}}          
	</script> 
    <script>    
        $(document).ready(function () {
        	initData();
        });

		//初始化数据
		var params = {
			page : 1,
			sessionUserId : $("#sessionUserId").val(),
			serviceDepId : $("#serviceDepId").val(),
			serviceJobCode : $("#serviceJobCode").val(),
			isMobile : false
		}
		//初始化参数
	    var settings = {
				ctx : "${ctx}",
				queryId : 'kpiCaseListQuery',
				rows : '12',
				templeteId : 'template_kpiCaseList',
				gridClass : 'table table_blue table-striped table-bordered table-hover ',
				data : params,
				columns : []
			};
        
        //日期控件,只能选择月份
		$('.datepicker').datepicker({
			format: 'yyyy-mm',  
		    weekStart: 1,  
		    autoclose: true,  
		    startView: 'year',
		    maxViewMode: 1,
		    minViewMode:1,
			todayBtn : 'linked',
			language : 'zh-CN',
		});
        
      	//清空数据
		function cleanForm() {
			$("#search_case_code").val("");
			$("#search_belong_month").datepicker('update', '');
		}
      	
		//查询
		$("#btn_search").click(function() {
			
			params.caseCode = $("#search_case_code").val().trim();
			params.belongMonth = $("#search_belong_month").val().trim();

			initData();
		})
        
      	//加载页面
		function initData() {

			$(".bonus-table").aistGrid(settings);
			
			setThead();
			
		    aist.sortWrapper({
				cacheData : settings,
				_self : $(".bonus-table")
			});
		    
		}
		
		//导出到excel
		function exportToExcel(){
			//此处重新获取params，因查询时为params增加了queryId属性，直接使用由于queryId多个拼接会报错
			var params = {
				page : 1,
				caseCode : $("#search_case_code").val().trim(),
				belongMonth : $("#search_belong_month").val().trim(),
				sessionUserId : $("#sessionUserId").val(),
				serviceDepId : $("#serviceDepId").val(),
				serviceJobCode : $("#serviceJobCode").val(),
			}
			aist.exportExcel({
				ctx : '${ctx}',
				queryId : 'kpiCaseListQuery',
				colomns : [ 'CASE_CODE', 'SALER_SIGN_SAT','SALER_GUOHU_SAT', 'SALER_LOAN_SAT','BUYER_SIGN_SAT', 'BUYER_GUOHU_SAT',
							'BUYER_COM_SAT','BUYER_PSF_SAT', 'SALER_CALLBACK','BUYER_CALLBACK', 'BELONG_MONTH'],
				data : params
			});
		}
		
		//同步满意度到KPI表(调用存储过程)
		function syncSatisListToKpi(){
			window.wxc.confirm("是否确定同步！",{"wxcOk":function(){
				$.ajax({
			        url:ctx+ "/award/syncSatisListToKpi" ,
			        method: "get",
			        dataType: "json",
			        data: {},
			        beforeSend: function () {  
			        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
						$(".blockOverlay").css({'z-index':'9998'});
			        },  
			        success: function(data){
			          $.unblockUI();   	 
			          if(data.success){
							 window.wxc.confirm("同步成功！",{"wxcOk":function(){
								 initData();
							 }
						   })
						 }else{
							 window.wxc.error("同步失败！");
						 } 
			        },
			        error: function (e, jqxhr, settings, exception) {
			        	$.unblockUI();   	 
			        }  
			   })
			 }})
		}
		
		//设置表头(因是多行，插件不支持)
		function setThead(){
			
			var thead = "";
			thead += "<tr>";
			thead += "<th rowspan='2'><span class='sort true' sortColumn='CASE_CODE' sord='desc'>案件编号<a href='javascript:void(0);'><i class='fa fa-sort-desc fa_down'></i></a></span></th>";
			thead += "<th colspan='3' class='text-center'>上家</th>";
			thead += "<th colspan='4' class='text-center'>下家</th>";
			thead += "<th>上家-下家</th>";
			thead += "<th rowspan='2'>计件年月</th>";
			thead += "</tr>";
			thead += "<tr>";
			thead += "<th>签约评分</th>";
			thead += "<th>过户评分</th>";
			thead += "<th>陪同还贷评分</th>";
			thead += "<th>签约评分</th>";
			thead += "<th>过户评分</th>";
			thead += "<th>贷款评分</th>";
			thead += "<th>公积金</th>";
			thead += "<th>电话是否接通</th>";
			thead += "</tr>";
			
			$(".bonus-table").find("thead").append(thead)
		}
    </script>
</content>
</body>
</html>
