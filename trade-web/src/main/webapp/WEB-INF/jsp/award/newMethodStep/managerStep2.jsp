<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet" /> 
<link href="<c:url value='/css/plugins/switch/bootstrap-switch.min.css'/>" rel="stylesheet"/>	
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<!-- Data Tables -->
<link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css'/>"  rel="stylesheet" />		
<link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css'/>"  rel="stylesheet" />
<link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css'/>"  rel="stylesheet" />
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css'/>"  rel="stylesheet" />		
<link href="<c:url value='/static/iconfont/iconfont.css' />" rel="stylesheet" />	
<link href="<c:url value='/static/css/animate.css'/>"  rel="stylesheet"/> 
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" />
<!-- index_css -->
<link href="<c:url value='/static/trans/css/common/base.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet" />
<link href="<c:url value='/static/css/btn.css' />" rel="stylesheet" />			
<link href="<c:url value='/static/trans/css/manager/managerIframe.css' />" rel="stylesheet" />	 
<!-- 必须CSS -->
<link href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" rel="stylesheet" /> 
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css'/>"  rel="stylesheet" />
<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css'/>"  rel="stylesheet" />

<style> 
.modal-backdrop { z-index: 0; position:relative!important; } 
.error_td { text-align:left }
</style>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/excelImport_new.jsp"></jsp:include>
<div class="wrapper wrapper-content animated fadeInRight">
   <div class="ibox-content border-bottom clearfix space_box">
       <div class="clearfix"> 
           <h2 class="title">月度KPI导入【金融产品】<a style="color:red" href="../template/call_kpi_temp.xlsx">导入模板下载</a></h2>           
       </div>
       <div class="form_list">
           <div class="line">
               <div class="form_content">
                   <label class="control-label sign_left_small">
                      	 所在组
                   </label>
                   <input type="text" class="teamcode input_type" id="teamCode" name="teamCode" readonly="readonly" 
					   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
					   startOrgId:'ff8080814f459a78014f45a73d820006', orgType:'',departmentType:'',departmentHeriarchy:'',
					   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,
					   expandNodeId:''})" />
					<input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId"> 
					<div class="input-group float_icon organize_icon"> <i class="icon iconfont"></i></div>
               </div>
               <div class="form_content">
                   <label class="control-label sign_left_small">
                       	人员
                   </label>
                   <input type="text" class=" input_type" id="userName" name="userName" placeholder="" >
               </div>
               <div class="form_content" style="margin-left: 127px;">
                    <div class="add_btn">
                        <button type="button" class="btn btn-success mr5 btn-icon" id="searchButton" >
                            <i class="icon iconfont"></i>
                            	查询
                         </button>
                         <a id="importButton" class="btn btn-success">个人月度KPI导入 </a>
                        <button type="reset" class="btn btn-grey" id="cleanButton">
                            	清空
                        </button>
                    </div>
                </div>
           </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="table_content">
                <table class="table table_blue table-hover table-striped table-bordered">
                    <thead>
                        <tr>
                            <th> 人员 </th>
                            <th> 所在组别 </th>
                            <th> 所属贵宾服务部 </th>
                            <th> 类型 </th>
                            <th> 当月金融产品数 </th>
                            <th> 上月滚存数 </th>
                            <th> 金融产品产出率  </th>
                            <th> 过户数  </th>
                        </tr>
                    </thead>
                    <tbody id="myTaskList">
				    </tbody>
                </table>
            </div>
            <div class="text-center page_box">
				<span id="currentTotalPage"><strong ></strong></span>
				<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
				<div id="pageBar" class="pagergoto">
				</div>  
		    </div>
        </div>
    </div>
    
    <!-- 失败数据 -->
        <div id="error-modal-form" class="modal fade" role="dialog" aria-labelledby="excel-modal-title" aria-hidden="true">
          <div class="modal-dialog" style="width:800px">
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
                               <td class="error_td">${item.userName }</td>
                               <td class="error_td">${item.employeeCode }</td>
                               <td class="error_td">${item.finOrder }</td>
                               <td class="error_td">${item.errorMessage }</td>
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

<content tag="local_script"> 
<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
<script src="<c:url value='/static/js/bootstrap.min.js' />"></script>
<script src="<c:url value='/static/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
<script src="<c:url value='/static/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
<!-- Custom and plugin javascript -->
<script src="<c:url value='/static/js/inspinia.js' />"></script>
<!-- jQuery UI -->
<script src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
<!-- 引入弹出框js文件 -->
<script src="<c:url value='/js/common/xcConfirm.js?v=1.0.1' />"></script>
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script> 
<script src="<c:url value='/js/template.js" type="text/javascript' />"></script> 
<%@include file="/WEB-INF/jsp/tbsp/common/scriptBaseOrgDialog_new.jsp"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>  
<script	src="<c:url value='/js/plugins/switch/bootstrap-switch.js' />"></script> 
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
<script	src="<c:url value='/js/plugins/dateSelect/dateSelect.js?v=1.0.2' />"></script> 
<!-- 列表 -->
<script src="<c:url value='/transjs/award/managerStep2.js' />"></script> 
<!-- 弹出框插件 -->
<script src="<c:url value='/js/plugins/layer/layer.js' />"></script> 
<script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script> 
<script id="template_myTaskList" type= "text/html">
{{each rows as item index}}
		 {{if index%2 == 0}}
			  <tr class="tr-1">
		  {{else}}
			   <tr class="tr-2">
		   {{/if}}
			<td >
				<p class="big">
					{{item.PARTICIPANT}}
				</p>
			</td>
			<td >
				<p class="big">
					{{item.TEAM_ID}}
				</p>
			</td>
			<td >
				<p class="big">
					{{item.DISTRICT_ID}}
				</p>
			</td>
			<td >
				<p class="big">
				{{ if "" != item.TYPE  && null != item.TYPE && item.TYPE == "DKLSL"}}
					贷款流失率
				{{/if}}
				{{ if "" != item.TYPE  && null != item.TYPE && item.TYPE == "JRDBL"}}
					金融达标率
				{{/if}}
				</p>
			</td>
			<td >
				<p class="big">
					{{item.FIN_ORDER}}
				</p>
			</td>
			<td >
				<p class="big">
					{{item.FIN_ORDER_ROLL}}
				</p>
			</td>
			<td >
				<p class="big">
					{{item.FIN_ORDER_RATE}}
				</p>
			</td>
			<td >
				<p class="big">
					{{item.TOTAL_CASE}}
				</p>
			</td>
		</tr>
{{/each}}              
</script>
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
    	isShowSatButton();
    	//初始化日期控件
    	var monthSel = new DateSelect($('.bonus-m'),{max:new Date(),moveDone:reloadGrid}); 
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
        	  offset: ['50px'],
        	  area: ['50%', '40%'],	
        	  content: $('#excelImport'), //捕获的元素
        	  btn: ['提交','关闭'],
        	  yes: function(index){
        			  layer.close(i);
        			  var i = 0;
            		  // 上月
           			  var cDate = new Date();
           			  cDate.setMonth(cDate.getMonth()-1);
           			  var  bm = cDate.format('yyyy-MM-dd');
            		  var isKpiMoney = isGenerKpiMoney(bm);
            		  if(isKpiMoney) {
           		    	layer.alert('绩效奖金已经生成,不能重复导入!', {
           		    		  icon: 2
           		    	}) 
            		    return false;
            		  }
            
            		  if(checkFileTypeExcel()){
            			
           		    	 $(".blockOverlay").css({'z-index':'1998'});
           		    	 $("#excelInForm").attr("action",ctx+"/award/doMonthKpiImportForNew"); 
           		    	 $("#excelInForm").attr("method","POST"); 
           		    	 
           		    	 
           		    	 $("#belongMonth").remove();
           		    	 var inputMonth = $("<input type=\"hidden\" id=\"belongMonth\" name=\"belongMonth\"/>");
           		    	 var i = 0;
           		    	 inputMonth.val(i);
           		    	 $('#excelInForm').append(inputMonth);
           		    	 $('#excelInForm').submit();
           		    	 $.unblockUI();  
            			 layer.close(index);
            		  }
        	  },
        	  cancel: function(index){  layer.close(index); }
        	});
    	})
    	$('#cleanButton').click(function() {
 			$("input[name='teamCode']").val('');
 			$("input[name='yuCuiOriGrpId']").val('');
 			$("input[name='userName']").val('');
 		});
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
 	/**判断该月份绩效奖金是否已经生成,如果已经生成，则不能导入**/
    function isGenerKpiMoney(bm) {
 		 var isKpiMoney = false;
    	 $.ajax({
			  async: false,
	          url:ctx+ "/award/getTsAwardKpiPayByStatus" ,
	          method: "post",
	          dataType: "json",
	          data: {belongMonth:bm},
	          success: function(data){
	        	  if(data.success && data.content != null) {
	        		  isKpiMoney =  true;
	        	  }
	          }
   		});
    	return isKpiMoney;
    }
    
    function isShowSatButton(){
    	var belongMonth = getBlongMonth();
    	$.ajax({
            url:ctx+ "/newAward/isShowSatButton" ,
            method: "get",
            dataType: "json",
            data: {"belongMonth":belongMonth},
            success: function(data){        
            	if(data.success == true){			  
            		$("#importButton").hide();
            	}
            }
       })
    }
	  //获取计件年月信息
	  function getBlongMonth(){
	  	var bm = "";	
	  	//方式一
	  	var belongMonth =  $.trim($("#belongMonth",window.parent.document).val());
	  	//方式二
	    if(belongMonth =="" || belongMonth == null || belongMonth == undefined){
	    	bm == null;
	    }else{
	    	bm = belongMonth + "-01";
	    }
	    return bm;
	  }
    </script>
 </content>
</body>
</html>
