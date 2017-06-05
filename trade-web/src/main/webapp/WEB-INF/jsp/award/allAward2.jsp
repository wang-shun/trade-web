<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
    <!-- 图标 -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" rel="stylesheet">

    <!-- Data Tables -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
    <!-- bootstrap-datapicker3 -->
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <!-- 分页控件 -->
    <link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
    
    <!-- owner css -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/award/baseAward.css" />
</head>

<body>

<!--*********************** HTML_main*********************** -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <p class="month">
            <button type="button" name="dateButton" class="btn btn-success btn_small pull-left"><i class="icon iconfont icon_arrow">&#xe60d;</i></button>
                <span id="month">yyyy/MM月</span>
            <button name="dateButton" class="btn btn-success btn_small pull-left"><i class="icon iconfont icon_arrow">&#xe614;</i></button>
            <span class="explain">此日期为计件奖金的生成日期。如需查看某月过户的案件计件奖金，请按过户日期搜索</span>
        </p>
        <form method="get" class="form_list">
       		<div class="line">
	            <div class="form_content">
	                <label class="control-label sign_left">案件编号</label>
	                <input type="text" id="caseCode" class="select_control sign_right_one" />
	            </div>
	            <div class="form_content">
	                <label class="control-label sign_left_small select_style mend_select">过户时间</label>
	                <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd">
	                    <input id="dtBegin" class="form-control data_style" type="text" value="" placeholder="起始日期"> <span class="input-group-addon">到</span>
	                    <input id="dtEnd" class="form-control data_style" type="text" value="" placeholder="结束日期">
	                </div>
	            </div>
           </div>
           <div class="line">
               <div class="form_content">
                   <label class="control-label sign_left">产证地址</label>
                   <input id="propertyAddr" class="teamcode input_type" style="width:435px;" placeholder="请输入" value="">
               </div>
               <div class="form_content space">
                   <div class="add_btn">
                       <button type="button" id="searchButton" class="btn btn-success"><i class="icon iconfont"></i>查询</button>
                   </div>
               </div>
           </div>
		</form>
    </div>
    
    <div class="row">
        <div class="col-md-12">
            <div class="notes">
                <p class="pull-left">基础计件奖金</p>
                <p class="pull-right" id="countMsg">
                    交易单数：9，环节总数：31.00，交易单加权：9.00
                </p>
            </div>
            <div class="table_content">
                <table class="table table_blue table-hover table-striped table-bordered">
                    <thead>
                        <tr>
                            <th><span>案件编码</span></th>
                            <th>产证地址</th>
                            <th>过户时间</th>
                            <th>生产时间</th>
                            <th>基础奖金</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody id="tsAwardBaseList">
                    </tbody>
                </table>
                <div class="text-center page_box">
                    <span id="currentTotalPage"><strong></strong></span>
                    <span class="ml15">共<strong id="totalP"></strong>条</span>&nbsp;
                    <div id="pageBar" class="pagination text-center"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--*********************** HTML_main*********************** -->
</div>
</div>
<content tag="local_script">    
    <!-- datepicker -->
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <!-- js模板引擎 -->
    <script src="${ctx}/static/js/template.js"></script>
    <!-- 分页控件  -->
    <script src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
    <!-- dateSelect -->
    <script	src="${ctx}/static/js/plugins/dateSelect/dateSelect.js"></script>    
    <!-- 自定义扩展jQuery库 -->
    <script src="${ctx}/static/js/plugins/jquery.custom.js"></script>
    <!-- owner -->
    <script src="${ctx}/static/trans/js/award/allAward.js"></script>
    <script id="template_tsAwardBaseList" type= "text/html">
    	{{each rows as item index}}
 			<tr>
            	<td>
					<p class="big">
						<a href="{{ctx}}/trade-web/case/caseDetail?caseId={{item.PKID}}" target="_blank">{{item.CASE_CODE}}</a>
					</p>
				</td>
                <td>
					<p class="big">{{item.PROPERTY_ADDR}}</p>				
				</td>
                <td>
					<p class="big">{{item.GUOHU_TIME}}</p>
				</td>
				<td>
					<p class="big">{{item.PAID_TIME}}</p>
				</td>
                <td>
					<p class="big">{{item.SUM_BASE_AMOUNT}}</p>
				</td>
                <td class="center">
					<a href="#" class="spread" id="{{item.CASE_CODE}}">展开</a>
				</td>
            </tr>
            <tr class="spread_line" id="toggle{{item.CASE_CODE}}" style="display:none;"></tr>
		{{/each}}	
	</script>
	<script id="template_tsAwardSrvList" type= "text/html">
    	<td colspan="7" class="spread_td">
        	<table class="table spread_tab table-bordered">
            	<thead>
                <tr>
                                                    <th>相关人</th>
													<th>服务</th>
                                                    <th>基础奖金</th>
                                                    <th>环节占比</th>
													<th>满意度</th>
													<th>满意度占比</th>
													<th>金融单量</th>
													<th>金融达标率</th>
													<th>流失率</th>
													<th>最终奖金</th>
                </tr>
                </thead>
                <tbody>
					{{each rows as item index}}
                                                <tr> 
													<td>{{item.REAL_NAME}}</td>
                                                    <td>{{item.SRV_CODE}}</td>
													<td>{{item.BASE_AMOUNT}}</td>
                                                    <td>{{item.SRV_PART_IN}}/{{item.SRV_PART_TOTAL}}</td>

													<td>{{item.SATISFACTION}}</td>
													<td>{{item.SRV_PART}}</td>
													<td>{{item.FIN_ORDER}}
															{{if item.FIN_ORDER != null && item.FIN_ORDER_ROLL != null }}+{{/if}}
														{{item.FIN_ORDER_ROLL}}
													</td>
													<td>{{item.FIN_ORDER_RATE}}</td>
													<td>{{item.COM_LS_RATE}}</td>
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
		var monthSel=new DateSelect($('.month'),{max:new Date(),moveDone:reloadGrid});
        
		$(document).ready(function () {
        	
			//初始化datepicker插件
            $('.input-daterange').datepicker({
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true
            });

    		//初始化数据
    	    reloadGrid();
    	 	// 查询
 			$('#searchButton').click(function() {
 				reloadGrid();
 			});
    	 	
    		$(document).on("click",".spread",function(){
				var id = this.id;
	  			  	if($(this).html() == "展开") {
	  				  $(this).html("收起");
	  				  // 发出请求
	  				    var data = {};
				    	    data.queryId = "allAwardrInfoList";
				    	    data.pagination = false;
				    	    data.caseCode = id;
				    	 	data.paidTime = monthSel.getDate().format('yyyy-MM-dd');
				    		$.ajax({
				    			  async: false,
				    	          url:ctx+ "/quickGrid/findPage" ,
				    	          method: "post",
				    	          dataType: "json",
				    	          data: data,
				    	          success: function(data){
				    	        	  var tsAwardSrvList= template('template_tsAwardSrvList' , data);
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
    	    data1.queryId = "allBaseAwardrQuery";
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
    	    BonusList.init(ctx,data1,data2);
    	    
    	    var data2 = {};
    	    data2.paidTime = bm;
    	    data2.caseCode = $("#caseCode").val();
    	    data2.propertyAddr = $("#propertyAddr").val();
    	    data2.dtBegin=$("#dtBegin").val();
    	    data2.dtEnd=$("#dtEnd").val();
    	    
    	  //显示统计信息
 			$.ajax({
    			  async: false,
    	          url:ctx+ "/awards/allBaseAwardCount" ,
    	          method: "post",
    	          dataType: "json",
    	          data: data2,
    	          success: function(data){
    	        	  $("#countMsg").empty();
    	        	  $("#countMsg").append(data.countMsg);
    	          }
    	     });
    	}
    	
    	function goPage(page) {
    		var bm=monthSel.getDate().format('yyyy-MM-dd');	
    		var data1 = {};
    	    data1.queryId = "allBaseAwardrQuery";
    	    data1.rows = 12;
    	    data1.page = page;
    	    data1.caseCode = $("#caseCode").val();
    	    data1.propertyAddr = $("#propertyAddr").val();
    	    data1.paidTime = bm;
    	    
    	    var data2 = {
    	    	paidTime : bm
    	    }
    	    BonusList.init(ctx,data1,data2);
    	}        
    </script>
</content>
</body>
</html>
