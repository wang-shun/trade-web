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
    </head>
    <body class="pace-done">
   	    <div class="bonus-table">
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
        <script src="${ctx}/transjs/kpi/bonus.js"></script>
        <script src="${ctx}/js/plugins/jquery.custom.js"></script>
       	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		<script id="tsAwardBaseList" type= "text/html">
                           {{each rows as item index}}
 							  	   {{if index%2 == 0}}
 				                     <tr class="tr-1">
                                   {{else}}
                                     <tr class="tr-2">
                                   {{/if}}
                                    <td></td>
                                    <td>{{item.CASE_CODE}}</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.GUOHU_TIME}}</td>
                                    <td>{{item.CLOSE_TIME}}</td>
                                    <td>{{item.BASE_CASE_AMOUNT}}</td>
                                    <td><div class="expand" id="{{item.CASE_CODE}}">展开</div></td>
                                </tr>
                                <tr class="toogle-show border-e7" id="toggle{{item.CASE_CODE}}" style="display:none;">
                                    
                                </tr>
						{{/each}}
	    </script>
	    <script>
	        var ctx = "${ctx}";
	  		//初始化日期控件
        	var monthSel=new DateSelect($('.bonus-m'),{max:new Date(),moveDone:reloadGrid});	 
    		
        	jQuery(document).ready(function() {
        		//初始化数据
	    		var data = {};
	    		//var bm = monthSel.getDate().format('yyyy-MM-dd');	
        	    //data.argu_belongMonth = bm;
        	    data.argu_caseCode = $("#caseCode").val();
        	    data.argu_propertyAddr = $("#propertyAddr").val();
        	    
        	    $(".bonus-table").aistGrid({
        			ctx : "${ctx}",
        			queryId : 'tsAwardBaseList',
        		    templeteId : 'tsAwardBaseList',
        		    data : data,
        		    columns : [{
        		    	
        		              },{
        		    	           colName :"案件编号",
        		    	           sortColumn : "CASE_CODE",
        		    	           sord: "desc"
        		    	      },{
        		    	           colName :"物业地址"
        		    	      },{
        		    	           colName :"过户时间",
       		    	        	   sortColumn : "GUOHU_TIME",
           		    	           sord: "desc"
        		    	      },{
        		    	           colName :"结案时间"
        		    	      },{
        		    	           colName :"绩效奖金"
        		    	      },{
        		    	    	  colName :"操作"
        		    	      }]
        		
        		}); 
        		
        	 	// 查询
     			$('#searchButton').click(function() {
     				reloadGrid();
     			});
        		
	    		$(document).on("click",".expand",function(){
    				var id = this.id;
   	  			  	if($(this).html() == "展开") {
   	  				  $(this).html("收起");
   	  				  // 发出请求
   	  				    var data = {};
   				    	    data.queryId = "tsAwardBaseDetailList";
   				    	    data.rows = 58;
   				    	    data.page = 1;
   				    	    data.search_caseCode = id;
   				    	 	data.argu_belongMonth = monthSel.getDate().format('yyyy-MM-dd');
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
	    		
	    		 $("#submitButton").click(function(){
	    			 layer.confirm('提交之后不可修改,是否确定提交？', {
		    			  type: 1,
		            	  title: '提醒',
		            	  shadeClose: true,
		            	  shade: 0.8,
		            	  area: ['20%', '15%'],	
		    			  btn: ['确定','取消'] //按钮
		    		}, function(index){
		    			$.ajax({
				    			  async: false,
				    			  url:ctx+ "/kpi/updateTsAwardKpiPayStatus" ,
				    	          method: "post",
				    	          dataType: "json",
				    	          data: {belongMonth:monthSel.getDate().format('yyyy-MM-dd')},
				    	          success: function(data){
				    	        	  if(data.success) {
				    	        		  $("#submitButton").attr("disabled",true);
				    	        		  layer.msg('绩效奖金生成成功', {icon: 1, time: 1000});
				    	        	  } else {
				    	        		  layer.msg('绩效奖金生成失败', {icon: 1, time: 1000});
				    	        	  }
				    	          }
				    	     });
		    		
		    			  layer.close(index);
		    			  
			    	}, function(){
			    	});
	    		 });
	    	});
        	
			function reloadGrid(bm) {
				if(!bm){
					bm=monthSel.getDate().format('yyyy-MM-dd');	
				}else{
					bm=bm.format('yyyy-MM-dd');
				}
	    		
	    		var data1 = {};
        	    data1.queryId = "tsAwardBaseList";
        	    data1.rows = 12;
        	    data1.page = 1;
        	    data1.argu_belongMonth = bm;
        	    data1.argu_caseCode = $("#caseCode").val();
        	    data1.argu_propertyAddr = $("#propertyAddr").val();
        	    
        	    var data2 = {
        	    	belongMonth : bm
        	    }
        	    BonusList.init(ctx,data1,data2);
        	    // 如果已经提交，则不能再次提交
        	    $.ajax({
	    			  async: true,
	    	          url:ctx+ "/kpi/getTsAwardKpiPayByStatus" ,
	    	          method: "post",
	    	          dataType: "json",
	    	          data: {belongMonth:bm},
	    	          success: function(data){
	    	        	  if(data.success && data.content != null) {
	    	        		  $("#submitButton").attr("disabled",true);
	    	        	  } else {
	    	        		  $("#submitButton").removeAttr("disabled");
	    	        	  }
	    	          }
		    	});
	    	}
        	
	    	function goPage(page) {
	    		var bm=monthSel.getDate().format('yyyy-MM-dd');	
	    		var data1 = {};
        	    data1.queryId = "tsAwardBaseList";
        	    data1.rows = 12;
        	    data1.page = page;
        	    data1.argu_caseCode = $("#caseCode").val();
        	    data1.argu_propertyAddr = $("#propertyAddr").val();
        	    data1.argu_belongMonth = bm;
        	    
        	    var data2 = {
        	    	belongMonth : bm
        	    }
        	    BonusList.init(ctx,data1,data2);
	    	}
	    	
	    	function exportToExcel() {
	        	$.exportExcel({
	    	    	ctx : "${ctx}",
	    	    	queryId : 'tsAwardBaseDetailList',
	    	    	colomns : ['CASE_CODE','PROPERTY_ADDR','PARTICIPANT','SRV_CODE','BASE_AMOUNT','SRV_PART_IN','SATISFACTION','MKPI','KPI_RATE_SUM','SRV_PART','AWARD_KPI_MONEY'],
	    	    	data : {search_caseCode:$('#caseCode').val(),argu_propertyAddr:$('#propertyAddr').val(),argu_belongMonth : monthSel.getDate().format('yyyy-MM-dd')}
	    	    }) 
	         }
	    	
	    	 function exportBonusExcelButton() {
	    		 $.exportExcel({
		    	    	ctx : "${ctx}",
		    	    	queryId : 'tsAwardBaseList',
		    	    	colomns : ['CASE_CODE','PROPERTY_ADDR','GUOHU_TIME','CLOSE_TIME','BASE_CASE_AMOUNT'],
		    	    	data : {search_caseCode:$('#caseCode').val(),argu_propertyAddr:$('#propertyAddr').val(),argu_belongMonth : monthSel.getDate().format('yyyy-MM-dd')}
	    	    }) 
	    	 }
	    	 
	    	 function exportAmountToExcel() {
	    		 $.exportExcel({
		    	    	ctx : "${ctx}",
		    	    	queryId : 'personSumBonusList',
		    	    	colomns : ['PARTICIPANT','SUMBONUS'],
		    	    	data : {search_caseCode:$('#caseCode').val(),argu_propertyAddr:$('#propertyAddr').val(),argu_belongMonth : monthSel.getDate().format('yyyy-MM-dd')}
	    	    }) 
	    	 }
	    </script>
	    </content> 
        <input type="hidden" id="ctx" value="${ctx}" />
    
        <!-- 弹窗 -->
        <div class="modal bonus-modal in" id="add-change" role="dialog" aria-hidden="false">
            <div class="modal-dialog modal-content animated fadeIn" >
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                        <h4 class="modal-title">手动添加调整</h4>
                    </div>
                    <div class="modal-body">
                        <div class="ibox-content">
                            <ul>
                                <li class="header">
                                    <span class="wd88">人员</span>
                                    <span class="wd126">案件编号</span>
                                    <span class="wd88">服务</span>
                                    <span class="wd110">绩效奖金</span>
                                    <span class="wd243">备注</span>
                                </li>
                                <li>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd126"></span>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd110" placeholder="可以为负数"></span>
                                    <span><input type="text" class="wd243"></span>
                                </li>
                                <li>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd126"></span>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd110" placeholder="可以为负数"></span>
                                    <span><input type="text" class="wd243"></span>
                                </li>
                                <li>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd126"></span>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd110" placeholder="可以为负数"></span>
                                    <span><input type="text" class="wd243"></span>
                                </li>
                            </ul>
                            <div class="add-btn">
                                <button class="btn btn-primary add-change">+继续添加</button>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <a href="pending.html" class="btn btn-primary confirm" id="approve" data-dismiss="modal">确认</a>
                        <button type="button" class="btn btn-white cancel" data-dismiss="modal">取消</button>
                    </div>       
                </div>
        </div>
        <!-- 弹窗 -->
    </body>
</html>