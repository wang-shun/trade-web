<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<!-- Gritter -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/common.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" rel="stylesheet">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />

	
 <!-- Data Tables -->
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css' />" />
<link rel="stylesheet" href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" />
<link rel="stylesheet" href="<c:url value='/css/iconfont/iconfont.css' />" />

<!-- index_css -->
<link rel="stylesheet" href="<c:url value='/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/css/trans/css/award/baseAward.css' />" />

<style type="text/css">

</style>
</head>
<body>
<!--*********************** HTML_main*********************** -->
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content border-bottom clearfix space_box">
                    <h2 class="title">
                        可计件环节奖金列表
                    </h2>
                <div class="row">
                    <div class="col-md-12">
                        <div class="table_content">
                            <table class="table table_blue table-hover table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th>
                                            职位
                                        </th>
                                        <th>
                                            对应组别
                                        </th>
                                        <th>
                                            具体环节
                                        </th>
                                        <th>
                                            基础奖金（元）
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            交易顾问
                                        </td>
                                        <td>
                                            前台组
                                        </td>
                                        <td>
                                            签约
                                        </td>
                                        <td>
                                            80
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            交易顾问
                                        </td>
                                        <td>
                                            前台组
                                        </td>
                                        <td>
                                            商业贷款
                                        </td>
                                        <td>
                                            80
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            交易顾问
                                        </td>
                                        <td>
                                            前台组
                                        </td>
                                        <td>
                                            陪同还贷
                                        </td>
                                        <td>
                                            80
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            交易顾问
                                        </td>
                                        <td>
                                            前台组
                                        </td>
                                        <td>
                                            过户
                                        </td>
                                        <td>
                                            80
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            交易顾问
                                        </td>
                                        <td>
                                            后台组
                                        </td>
                                        <td>
                                            纯公积金
                                        </td>
                                        <td>
                                            70
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            
                        </div>
                    </div>
                </div>
            </div>
            </div>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>	 
<input type="hidden" id="ctx" value="${ctx}"/>
<content tag="local_script"> 
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"> </script>
<script src="<c:url value='/transjs/award/managerRewardList.js?v=1.1' />"> </script>

<script id="template_managerRewardList" type="text/html">
		{{each rows as item index}}
		    <tr>
              <td>{{item.ORG_NAME}}</td>
              <td>{{item.USER_NAME}}</td>
              <td>{{item.EMPLOY_CODE}}</td>
              <td>{{item.JOB_NAME}}</td>
              <td>{{item.SRV_FEE}}</td>
              <td>{{item.BELONG_MONTH}}</td>
              <td>
				{{if item.IS_COMFIRM == 1 }}
					<button class="btn btn-success mr5" disabled>修改 </button>
              		<button class="btn btn-grey" disabled>删除 </button>
				{{else}}
					<button onclick="modifyManagerInfo('{{item.PKID}}','{{item.USER_NAME}}','{{item.SRV_FEE}}','{{item.ORG_NAME}}','{{item.JOB_NAME}}')" class="btn btn-success mr5" >修改 </button>
              		<button onclick="delManagerInfo({{item.PKID}})" class="btn btn-grey" >删除 </button>
				{{/if}}
              	
              </td>
             </tr>
		{{/each}}
</script>
<script type="text/javascript">
//新增
$('#AddBtn').click(function(event) {
	$("#userId").val("");
	$("#userName").val("");
	$("#jobName").val("");
	$("#orgName").val("");
	$("#orgId").val("");
	$("#srvFee").val("");
    $('#addModal').modal('show');
    
});
//日期控件
$('#datepicker_0').datepicker({
	format: 'yyyy-mm',  
    weekStart: 1,  
    autoclose: true,  
    startView: 'year',
    maxViewMode: 1,
    minViewMode:1,
	todayBtn : 'linked',
	language : 'zh-CN',
});
function chooseManager(startOrgId) {
	userSelect({
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
	console.log(array);
	if (array && array.length > 0) {
		$("#userId").val(array[0].userId);
		$("#userName").val(array[0].username);
		$("#jobName").val(array[0].jobName);
		$("#orgName").val(array[0].orgName);
		$("#orgId").val(array[0].orgId);
	}
}
</script>

</content>
</body>
</html>