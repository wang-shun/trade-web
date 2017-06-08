<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet" />	
    	<link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />" rel="stylesheet" />
		<!-- Data Tables -->
		<link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/datapicker/datepicker3.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/static/iconfont/iconfont.css' />" rel="stylesheet" />	
		<link href="<c:url value='/static/css/animate.css'/>"  rel="stylesheet"/> 
		<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" />
		<!-- 分页控件 -->
		<link href="<c:url value='/css/plugins/pager/centaline.pager.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css'/>"  rel="stylesheet" />
		
		<!-- index_css -->
 		<link href="<c:url value='/static/trans/css/common/base.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/css/btn.css' />" rel="stylesheet" />			
		<link href="<c:url value='/static/trans/css/manager/managerIframe.css' />" rel="stylesheet" />	 
		<!-- 必须CSS -->
		<link href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" rel="stylesheet" /> 
		<script>       
				document.domain = 'centaline.com';
		</script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/excelImport.jsp"></jsp:include>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
            <div class="clearfix"> 
                <h2 class="title pull-left ml15">
                   	流失率
                </h2>
            </div>
            <div class="form_list">
                <div class="line">
                    
                    <div class="form_content">
                        <label class="control-label sign_left_small">
                           	 案件编号
                        </label>
                        <input class=" input_type" placeholder="请输入" value="">
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left" style="width:100px;">
                            	贷款类型
                        </label>
                        <select class="select_control sign_right_one">
	                        <option value=""> 请选择 </option>
	                        <option value="30016001"> 纯商贷 </option>
	                        <option value="30016003"> 纯公积金贷款 </option>
	                        <option value="30016002"> 组合贷款 </option>
	                        <option value="30016004"> 自办贷款 </option>
                        </select>
                    </div>
                     <div class="form_content">
                        <label class="control-label sign_left_small">
                            	主办
                        </label>
                        <input id="userId" name="userId" hidden="true" />
                        <input id="userName" name="userName" class="input_type sign_right_one" placeholder="请输入" value="" onclick="chooseManager()" readonly="readonly">
                        <div class="input-group float_icon organize_icon">
                            <i class="icon iconfont" onclick="chooseManager()"></i>
                        </div>
                    </div>
                </div>
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">
                            	产证地址
                        </label>
                        <input class="teamcode input_type" style="width:435px;" placeholder="请输入" value="">
                    </div>
                    <div class="form_content ml5">
                        <div class="add_btn">
                            <button class="btn btn-success mr5 btn-icon">
                                <i class="icon iconfont"></i>
                               	 查询
                             </button>
                            <button type="reset" class="btn btn-grey"> 清空 </button>
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
                           <th>案件编号</th>
                            <th>产证地址</th>
                            <th>主办人</th>
                            <th>贷款总金额</th>
                            <th>贷款流失金额</th>
                            <th>贷款类型</th>
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
    
    
</div>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="ex_message" value="${ex_message}" />
<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>

<script src="<c:url value='/static/js/jquery-2.1.1.js' />" ></script>
<script src="<c:url value='/static/js/bootstrap.min.js' />" ></script>
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>  
<!-- iCheck --> 
<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
<script	src="${ctx}/js/plugins/switch/bootstrap-switch.js"></script>
<script src="${ctx}/js/jquery.blockui.min.js"></script>
<!-- 组织控件 --> 
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<!-- 弹出框插件 -->
<script src="${ctx}/js/plugins/layer/layer.js"></script>
<script src="${ctx}/js/plugins/layer/extend/layer.ext.js"></script>
<!-- 日期控件 -->
<script	src="${ctx}/js/plugins/dateSelect/dateSelect.js?v=1.0.2"></script>

   <!-- 分页控件  -->
    <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
    <script src= "<c:url value='/js/template.js" type="text/javascript' />"></script>
    <script src= "<c:url value='/transjs/award/personBonusCollect.js' />"></script>
<!-- 分页控件  -->
<%-- <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script src="${ctx}/js/plugins/jquery.custom.js"></script> --%>
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>

<!-- 列表 -->
<script src="${ctx}/transjs/award/monthkpi.list.js"></script>
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
					{{item.TYPE}}
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

function chooseManager(startOrgId) {
	userSelect({
		frameId:'abcd',
		startOrgId : 'ff8080814f459a78014f45a73d820006',//非营业部
		expandNodeId : '',
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		//jobCode : 'JYUZBJL',
		callBack : chooseManagerBack
	});
}
function chooseManagerBack(array){
	if (array && array.length > 0) {
		$("#userId").val(array[0].userId);
		$("#userName").val(array[0].username);
		/* $("#jobName").val(array[0].jobName);
		$("#orgName").val(array[0].orgName);
		$("#orgId").val(array[0].orgId); */
	}
}
    
</script>
</content>
</body>
</html>
