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
                        <label class="control-label sign_left_small">案件编号</label>
                        <input id="caseCode" name="caseCode" class="teamcode input_type" style="width:152px;" placeholder="请输入" value="" />
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">人员</label>
                        <input type="text" id="inTextVal" name="radioOrgName" class="teamcode input_type" style="width:152px;" placeholder="请选择" readonly="readonly"  hVal="${serUserId }" value="${userInfo }" 
                        onclick="userSelect({startOrgId:'${serviceDepId}',expandNodeId:'${serviceDepId}',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" />
                        <div class="input-group float_icon organize_icon" style="cursor:pointer;" onclick="userSelect({startOrgId:'${serviceDepId}',expandNodeId:'${serviceDepId}',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})">
                        	<i class="icon iconfont">&#xe627;</i>
                        </div>
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">案件组织</label>
                        <input id="teamCode" name="teamCode" class="teamcode input_type" placeholder="请选择"  readonly="readonly" value="" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
							startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:''})" />
						<input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId">
                        <div class="input-group float_icon organize_icon" style="cursor:pointer;" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
							startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:''})">
                            <i class="icon iconfont">&#xe61b;</i>
                        </div>
                    </div>
                </div>
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">产证地址</label>
                        <input id="propertyAddr" name="propertyAddr" class="teamcode input_type" style="width:435px;" placeholder="请输入" value="" />
                    </div>
                </div>
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small select_style mend_select">过户时间</label>
                        <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                            <input id="dtBegin" name="dtBegin" class="form-control data_style" type="text" value="" placeholder="起始日期"> <span class="input-group-addon">到</span>
                            <input id="dtEnd" name="dtEnd" class="form-control data_style" type="text" value="" placeholder="结束日期">
                        </div>
                    </div>
                    <div class="form_content space">
                        <div class="add_btn">
                            <button id="searchButton" type="button" class="btn btn-success"><i class="icon iconfont">&#xe635;</i>查询</button>
                            <button type="button" class="btn btn-success" onclick="javascript:exportToExcel()">导出至Excel</button>
                        </div>
                    </div>
                </div>
            </form>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="notes">
                <p class="pull-left">计件奖金报表</p>
                <p id="countMsg" class="pull-right">
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
                            <th>生成时间</th>
                            <th>基础奖金</th>
                            <th>最终奖金</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody id="tsAwardBaseList"></tbody>
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
<form action="#" accept-charset="utf-8" method="post" id="excelChangeForm"></form>
<!--*********************** HTML_main*********************** -->
</div>
</div>
<content tag="local_script">
    <!-- 组织控件 --> 
	<%@include file="/WEB-INF/jsp/tbsp/common/userorg.jsp"%>    
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
    <script src="${ctx}/static/trans/js/award/baseAwardReport.js"></script>    
    <script id="template_tsAwardBaseList" type="text/html">
    	{{each rows as item index}}
 			<tr>
            	<td>
					<p class="big">
						<a href="{{ctx}}/trade-web/case/caseDetail?caseId={{item.PKID}}">{{item.CASE_CODE}}</a>
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
                <td>
					<p class="big">{{item.AWARD_KPI_MONEY_SUM}}</p>
				</td>
                <td class="center">
					<a href="#" class="spread" id="{{item.CASE_CODE}}">展开</a>
				</td>
            </tr>
            <tr class="spread_line" id="toggle{{item.CASE_CODE}}" style="display:none;"></tr>
		{{/each}}
	</script>
	<script id="tsAwardSrvList" type= "text/html">
    	<td colspan="7" class="spread_td">
        	<table class="table spread_tab table-bordered">
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
		var monthSel=new DateSelect($('.month'),{max:new Date(),moveDone:reloadGrid});	 
	
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
		
	    	var caseOrgId = $("#yuCuiOriGrpId").val();
	    	var isJL = "${isManage}";
	    	if(caseOrgId == '' && isJL == "0"){
	    		caseOrgId = "${serviceDepId}";
	    	}
	    	data.caseOrgId = caseOrgId;
    	 	
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
	        	 		$("#countMsg").append("最终奖金总数：" + cnt);
        	  		}else{
        		  		$("#countMsg").append("最终奖金总数：" + 0);
        	  		}
          		}
     		});
		}    
    
        $(document).ready(function() {

            $('.input-daterange').datepicker({
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true
            });

    		//初始化数据
    	    reloadGrid();
    	    getCountMsg();
    	 	// 查询
 			$('#searchButton').click(function() {
 				reloadGrid();
 				getCountMsg();
 			});
    	 	
    		$(document).on("click",".spread",function(){
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
			        	    
			        	  	var caseOrgId = $("#yuCuiOriGrpId").val();
  		        	    var isJL = "${isManage}";
  		        	    if(caseOrgId == '' && isJL == "0"){
  		        	    	caseOrgId = "${serviceDepId}";
  		        	    }
  		        	    data.caseOrgId = caseOrgId;
				    	 	
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
    	    
    	    var caseOrgId = $("#yuCuiOriGrpId").val();
    	    var isJL = "${isManage}";
    	    if(caseOrgId == '' && isJL == "0"){
    	    	caseOrgId = "${serviceDepId}";
    	    }
    	    data1.caseOrgId = caseOrgId;
    	    
    	    data1.caseUserId = $("#inTextVal").attr("hVal");
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
    	    
    	    var caseOrgId = $("#yuCuiOriGrpId").val();
    	    var isJL = "${isManage}";
    	    if(caseOrgId == '' && isJL == "0"){
    	    	caseOrgId = "${serviceDepId}";
    	    }
    	    data1.caseOrgId = caseOrgId;
    	    
    	    var data2 = {
    	    	paidTime : bm
    	    }
    	    BonusList.init(ctx,data1,data2);
    	}        
        
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
