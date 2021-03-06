<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet" />	
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />" rel="stylesheet" />
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
                            	主办
                        </label>
                        <input id="userId" name="userId" hidden="true" />
                        <input id="userName" name="userName" class="input_type sign_right_one" placeholder="请输入" value="" onclick="chooseManager()" readonly="readonly">
                        <div class="input-group float_icon organize_icon">
                            <i class="icon iconfont" onclick="chooseManager()"></i>
                        </div>
                    </div>
                    <div class="form_content ml5">
                        <div class="add_btn">
                            <button class="btn btn-success mr5 btn-icon" id="searchButton" >
                                <i class="icon iconfont"></i>
                               	 查询
                             </button>
                            <button type="reset" class="btn btn-grey" id="cleanButton"> 清空 </button>
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
                           <th>姓名</th>
                            <th>职位</th>
                            <th>部门</th>
                            <th>贷款总金额(万元)</th>
                            <th>贷款流失金额(万元)</th>
                            <th>流失率</th>
                            <th>流失KPI</th>
                        </tr>
                    </thead>
                    <tbody id="myTaskListf">
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
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />" ></script>
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />" ></script>
<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />" ></script>
<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />" ></script>
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />" ></script>
<!-- iCheck --> 
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />" ></script>
<script	src="<c:url value='/js/plugins/switch/bootstrap-switch.js' />" ></script>
<script src="<c:url value='/js/jquery.blockui.min.js' />" ></script>
<!-- 组织控件 --> 
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<!-- 弹出框插件 -->
<script src="<c:url value='/js/plugins/layer/layer.js' />" ></script>
<script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />" ></script>
<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js" type="text/javascript' />"></script>
<!-- 必须JS -->
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />" ></script>
<%@include file="/WEB-INF/jsp/tbsp/common/scriptBaseOrgDialog_new.jsp"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />" ></script>
<script	src="<c:url value='/js/plugins/switch/bootstrap-switch.js' />" ></script>
<script src="<c:url value='/js/jquery.blockui.min.js' />" ></script>
<script	src="<c:url value='/js/plugins/dateSelect/dateSelect.js?v=1.0.2' />" ></script>
<!-- 列表 -->
<script src="<c:url value='/transjs/award/managerStep3.js' />" ></script>
<script id="template_myTaskListf" type= "text/html">
{{each rows as item index}}
		 {{if index%2 == 0}}
			  <tr class="tr-1">
		  {{else}}
			   <tr class="tr-2">
		   {{/if}}
			<td >
				<p class="big">
					{{item.realName}}
				</p>
			</td>
			<td >
				<p class="big">
					{{item.jobName}}
				</p>
			</td>
			<td >
				<p class="big">
					{{item.orgName}}
				</p>
			</td>
			<td >
				<p class="big">
					{{item.COM_AMOUNT_TOTAL}}
				</p>
			</td>
			<td >
				<p class="big">

					{{item.COM_AMOUNT_LS}}

				</p>
			</td>
			<td >
				<p class="big">
				{{if item.COM_AMOUNT_LS !=null}}
					{{item.COM_LS_RATE}}%
				{{/if}}
				</p>
			</td>
			<td >
				<p class="big">
					{{item.COM_LS_KPI}}
				</p>
			</td>
		</tr>

{{/each}}              
</script>
<script>
var ctx = "${ctx}";
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
		callBack : chooseManagerBack
	});
}
function chooseManagerBack(array){
	if (array && array.length > 0) {
		$("#userId").val(array[0].userId);
		$("#userName").val(array[0].username);
	}
}
$('#cleanButton').click(function() {
	$("input[name='userName']").val('');
	$("input[name='userId']").val('');
}); 
</script>
</content>
</body>
</html>
