<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">	
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/switch/bootstrap-switch.min.css" rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
	rel="stylesheet">
<!-- 弹出框插件 -->
<%-- <link href="${ctx}/css/plugins/layer/layer.css" rel="stylesheet">
<link href="${ctx}/css/plugins/layer/layer.ext.css" rel="stylesheet"> --%>
<!-- 时间控件 -->
<link href="${ctx}/js/plugins/dateSelect/dateSelect.css?v=1.0.2" rel="stylesheet"></script>
<style type="text/css">
.radio.radio-inline>label {
	margin-left: 10px;
}

.radio.radio-inline>input {
	margin-left: 10px;
}

.checkbox.checkbox-inline>div {
	margin-left: 25px;
}

.checkbox.checkbox-inline>input {
	margin-left: 20px;
}
.ui-state-hover{
	cursor:pointer;
}
.btn-xm{
margin-left: 10px;
margin-top: 10px;
width:160px;
}
.org-form-control {
    background-color: #FFFFFF;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 1px;
    color: inherit;
    display: block;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    width: 100%;
    font-size: 14px;
}
</style>
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/excelImport.jsp"></jsp:include>

		<div class="row">
			<div class="ibox">
			<div class="ibox-title">
				<div class="bonus-m">
					<input type="button" class="btn btn-warning m-r-sm" value="&lt;" >
                    <h5 class="month">yyyy/MM月</h5>
                    <input type="button" class="btn btn-warning m-r-sm disable" disabled value="&gt;" style="margin-left:10px;">
				</div>
             </div>
			<div class="ibox-content">
					<form method="get" class="form-horizontal">
					 	<div class="row">
							<div class="col-lg-3 col-md-4">
                                   <div class="form-group">
                                       <label class="col-lg-3 col-md-3 control-label">所在组 :</label>
                                       <div class="col-lg-9 col-md-9">
                                         <input type="text" class="span12 tbsporg org-form-control" id="teamCode" name="teamCode" readonly="readonly" 
										   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'ff8080814f459a78014f45a73d820006', orgType:'',departmentType:'',departmentHeriarchy:'yucui_headquarter',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,
										   expandNodeId:''})" />
										 <input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId"> 
                                       </div>
                                   </div>
                               </div>
                               <div class="col-lg-3 col-md-4">
                                   <div class="form-group">
                                       <label class="col-lg-3 col-md-3 control-label">人员 :</label>
                                       <div class="col-lg-9 col-md-9">
                                           <input type="text" class=" form-control" id="userName" name="userName" placeholder="" style="width: 200px">
                                       </div>
                                   </div>
                               </div>
                               <div class="col-lg-3 col-md-4">                                   
                                  <button id="searchButton" type="button" class="btn btn-primary">查询</button>
		            	       <a id="importButton" class="btn btn-primary">个人月度Kpi导入 </a>
                               </div>
			             </div>
					</form>
				</div>
			</div>
		</div>
	 <div class="row">
		<div class="col-lg-12">
			<div class="ibox ">
				<div class="ibox-title">
					<h5>月度KPI指标明细</h5>
				</div>

				<div class="ibox-content">
					<div class="jqGrid_wrapper">
						<table id="table_list_1"></table>
						<div id="pager_list_1"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
		<!-- 收益导入 -->
        <div id="excel-modal-form" class="modal fade" role="dialog" aria-labelledby="excel-modal-title" aria-hidden="true">
          <div class="modal-dialog" style="width:1200px">
             <div class="modal-content">
                 <div class="modal-header">
				   <button type="button" class="close" data-dismiss="modal"
				      aria-hidden="Rbtrue">×
				   </button>
				   <h4 class="modal-title" id="excel-modal-title">
				      	请上传附件
				   </h4>
				</div>
                <div class="modal-footer">
		            <button type="button" class="btn btn-default"
		               data-dismiss="modal">关闭
		            </button>
		            <button type="button" class="btn btn-primary" onclick="javascript:excelIn()">
		                                提交
		            </button>
                </div>
                
             </div>
          </div>
       </div>  
       <!-- 失败数据 -->
        <div id="error-modal-form" class="modal fade" role="dialog" aria-labelledby="excel-modal-title" aria-hidden="true">
          <div class="modal-dialog" style="width:1200px">
             <div class="modal-content">
                 <div class="modal-header">
				   <button type="button" class="close" data-dismiss="modal"
				      aria-hidden="true">×
				   </button>
				   <h4 class="modal-title" id="excel-modal-title">
				      	导入失败数据
				   </h4>
				</div>
                
                <div class="modal-footer">
		            <div class="ibox float-e-margins">

                                <div class="ibox-content">
                                    <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>人员</th>
                                    <th>员工编号</th>
                                    <th>金融产品数</th>
                                    <th>错误信息</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${errorList}"  var="item">
                                <tr>
                                    <td>${item.userName }</td>
                                    <td>${item.employeeCode }</td>
                                    <td>${item.finOrder }</td>
                                    <td>${item.errorMessage }</td>
                                </tr>
                                </c:forEach>
                                 
                                </tbody>
                            </table>
                                </div>
                            </div>
                </div>
             </div>
          </div>
       </div>
       
	</div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ex_message" value="${ex_message}" />
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
	
	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
		 <script
		src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>  <!-- iCheck --> <script
		src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
		<script	src="${ctx}/js/plugins/switch/bootstrap-switch.js"></script>
    <script src="${ctx}/js/jquery.blockui.min.js"></script>
    <!-- 组织控件 --> 
    <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
    <!-- 弹出框插件 -->
    <script src="${ctx}/js/plugins/layer/layer.js"></script>
    <script src="${ctx}/js/plugins/layer/extend/layer.ext.js"></script>
    <!-- 日期控件 -->
    <script	src="${ctx}/js/plugins/dateSelect/dateSelect.js?v=1.0.2"></script>
    <!-- 列表 -->
    <script src="${ctx}/transjs/kpi/monthkpi.list.js"></script>
    <script>
    var ctx = "${ctx}";
    var belongM = "${belongM}";
    var belongLastM = "${belongLastM}";
 	// 是否显示错误信息
	<c:if test="${not empty errorList}">
    	var hasError=true;
 	</c:if>
    <c:if test="${empty errorList}">
		var hasError=false;
	</c:if>
	var sw;
    $(document).ready(function(){
    	//初始化日期控件
    	var monthSel=new DateSelect($('.bonus-m'),{max:new Date(),moveDone:reloadGrid});
    	// 初始化列表
    	MonthKpiImportList.init('${ctx}','/quickGrid/findPage','table_list_1','pager_list_1','${belongM}');
    	// 滑块
    	sw=$('#moSwitch').bootstrapSwitch({
    		'onText':"上月",
    		'offText':'当月'
    	}).on('switchChange.bootstrapSwitch', function(e, data) {
    	/* 	var i =sw.bootstrapSwitch('state')?'0':'1';
    		if(i=='0') {
    			var data = {
   					queryId:"monthKpiList",
   					argu_belongMonth : belongLastM
    			};
    		 	$("#table_list_1").jqGrid('setGridParam',{
		    		datatype:'json',
		    		mtype:'post',
		    		postData:data
		    	}).trigger('reloadGrid'); 
    		}else {
    			var data = {
       					queryId:"monthKpiList",
       					argu_belongMonth : belongM
        			};
        		 	$("#table_list_1").jqGrid('setGridParam',{
    		    		datatype:'json',
    		    		mtype:'post',
    		    		postData:data
    		    	}).trigger('reloadGrid'); 
    		} */
		});
    	// 是否显示错误信息
    	if(!!hasError){
    		$('#error-modal-form').modal("show");
    	}
    	 $("#importButton").click(function(){
    		//iframe层
        	layer.open({
        	  type: 1,
        	  title: '导入月度KPI明细页',
        	  shadeClose: true,
        	  shade: 0.8,
        	  area: ['50%', '40%'],	
        	  content: $('#excelImport'), //捕获的元素
        	  btn: ['提交','关闭'],
        	  yes: function(index){
        		  if(checkFileTypeExcel()){
        			 $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
       		    	 $(".blockOverlay").css({'z-index':'9998'});
       		    	 $("#excelInForm").attr("action",ctx+"/kpi/doMonthKpiImport"); 
       		    	 $("#excelInForm").attr("method","POST"); 
       		    	 
       		    	 $("#belongMonth").remove();
       		    	 var inputMonth = $("<input type=\"hidden\" id=\"belongMonth\" name=\"belongMonth\"/>");
       		    	 var i =sw.bootstrapSwitch('state')?'0':'1';
       		    	 inputMonth.val(i);
       		    	 $('#excelInForm').append(inputMonth);
       		    	 $('#excelInForm').submit();
       		    	 
        			 layer.close(index);
        		  }
        	  },
        	  cancel: function(index){
        	    layer.close(index);
        	    //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 5000, icon:6});
        	  }
        	});
    	}) 
    	
    	// 查询
		$('#searchButton').click(function() {
			reloadGrid();
		});
		
		function reloadGrid(bm){
 			if(!bm){
 				bm=monthSel.getDate().format('yyyy-MM-dd');	
 			}else{
 				bm=bm.format('yyyy-MM-dd');
 			}
 			
 			var data = {};
	    	data.search_teamId =$.trim( $('#yuCuiOriGrpId').val() ); 
	    	data.search_participant =$.trim( $('#userName').val() ); 
	    	data.argu_belongMonth = monthSel.getDate().format('yyyy-MM-dd');
	    	data.queryId="monthKpiList";
	    	
		    $("#table_list_1").jqGrid('setGridParam',{
	    		datatype:'json',
	    		mtype:'post',
	    		postData:data
	    	}).trigger('reloadGrid'); 
 		}
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
    </script>
 </content>
</body>
</html>
