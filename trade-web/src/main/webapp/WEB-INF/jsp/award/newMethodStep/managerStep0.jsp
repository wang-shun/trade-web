<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>计件奖金自动化流程</title>     
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
		<link href="<c:url value='/static/trans/css/award/baseAward.css' />" rel="stylesheet" />	 	
		<link href="<c:url value='/static/trans/css/manager/managerIframe.css' />" rel="stylesheet" />	 

		<!-- 必须CSS -->
		<link href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" rel="stylesheet" /> 
		<!--弹出框样式  -->
		<link href="${ctx}/css/common/xcConfirm.css" rel="stylesheet">
		
    </head>
    
    <body class="pace-done">
    <style>
    .modal {
    position: absolute;
    top: 50%;
    left: 50%;
    z-index: 1050;
    display: none;
    overflow: hidden;
    -webkit-overflow-scrolling: touch;
    outline: 0;
    transform: translate(-50%,-50%);
}
    
    </style>
			<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
           <div class="wrapper wrapper-content animated fadeInRight">
            
        <div class="ibox-content clearfix space_box">
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
                                <th style="width: 135px;">
                                    操作
                                </th>
                            </tr>
                        </thead>
                        <tbody id="managerPieceInfoList">
                        </tbody>
                    </table>
                    
                </div>
            </div>
        
            <div class="clearfix"> 
                <h2 class="title pull-left ml15">
                    管理人员基础奖金配置
                </h2>
            </div>
            <div class="form_list">
            	<input type="hidden" id="belongMonth" name="belongMonth" value="${belongMonth }" >
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">
                            姓名
                        </label>
                        <input class=" input_type" id="realName" placeholder="请输入" value="">
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">
                            所在组别
                        </label>
                        <input type="text" id="organName" style="border:1px solid #e5e6e7;height:34px;" />
                    </div>
                    <div class="form_content ml5" >
                        <div class="add_btn">
                            <button type="button" id="searchButton" class="btn btn-success mr5 btn-icon">
                                        <i class="icon iconfont"></i>
                                        	查询
                            </button>
                            <button type="button" class="btn btn-success" id="AddBtn">
                               	 新增
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="table_content" style="overflow:auto;height:690px;">
                    <table class="table table_blue table-hover table-striped table-bordered">
                        <thead>
                                    <tr>
                                        <th>
                                            组织
                                        </th>
                                        <th>
                                            姓名
                                        </th>
                                        <th>
                                            <span class="sort" sortColumn="MA.EMPLOY_CODE" sord="desc" onclick="changeStyle();">员工编号</span><i id="flag" class="fa fa-sort-desc fa_down"></i>
                                            <!-- <a href="javascript:void(0);"><i class="fa fa-sort-desc fa_down"></i></a> -->
                                        </th>
                                        <th>
                                            职位
                                        </th>
                                        <th>
                                            基础奖金（元）
                                        </th>
                                        <th>
                                            计件年月
                                        </th>
                                        <th style="width: 135px;">
                                            操作
                                        </th>
                                    </tr>
                                </thead>
                        <tbody id="managerRewardList">
                                </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!--*********************** HTML_main*********************** -->


    <!-- 模态框（Modal） -->
            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width: 720px;">
                    <div class="modal-content" style="padding-bottom: 30px;">
                        <div class="modal-header" style="border:0">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">新增管理人员信息</h4>
                        </div>
                        <form id="managerForm">
                        <div class="modal-body">
                            <div class="form_list">
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            姓名
                                        </label>
                                        <input id="userName" name="userName" class="input_type sign_right_one" placeholder="请输入" value="" onclick="chooseManager()" readonly="readonly">
                                        <div class="input-group float_icon organize_icon">
                                            <i class="icon iconfont" onclick="chooseManager()"></i>
                                        </div>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            基础奖金
                                        </label>
                                        <input id="srvFee" name="srvFee" class="input_type sign_right_one" placeholder="请输入" value="">
                                        <span class="date_icon">元</span>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            组织
                                        </label>
                                        <input id="orgName" name="orgName" class=" input_type sign_right_one" placeholder="请输入" value="" readonly="readonly">
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            职位
                                        </label>
                                        <input id="jobName" name="jobName" readonly="readonly" class=" input_type sign_right_one" placeholder="请输入" value="">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="userId" name="userId" value="">
                		<input type="hidden" id="orgId" name="orgId" value="">
                        </form>
                        <div class="modal-footer" style="text-align:center;border:0">
                            <button id="save" type="button" class="btn btn-success" >保存</button>
                            <button type="button" class="btn btn-grey" data-dismiss="modal">取消</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>
            <!-- /.modal -->
            
            <!-- 模态框（Modal） -->
            <div class="modal fade" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width: 720px;">
                    <div class="modal-content" style="padding-bottom: 30px;">
                        <div class="modal-header" style="border:0">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">更新管理人员信息</h4>
                        </div>
                        <form id="modifyManagerForm">
                        <div class="modal-body">
                            <div class="form_list">
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            姓名
                                        </label>
                                        <span id="name" style="width:180px;display:inline-block;padding:6px 12px;"></span>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            基础奖金
                                        </label>
                                        <input id="fee" name="fee" class="input_type sign_right_one" placeholder="请输入" value="">
                                        <span class="date_icon">元</span>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            组织
                                        </label>
                                        <span style="width:180px;display:inline-block;padding:6px 12px;" id="org"></span>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            职位
                                        </label>
                                        <span style="width:180px;display:inline-block;padding:6px 12px;" id="job"></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="pkid" name="pkid" value="">
                        </form>
                        <div class="modal-footer" style="text-align:center;border:0">
                            <button id="modify" type="button" class="btn btn-success" >保存</button>
                            <button type="button" class="btn btn-grey" data-dismiss="modal">取消</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>
            
            <!-- 模态框（Modal） -->
            <div class="modal fade" id="modifyBaseModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width: 720px;">
                    <div class="modal-content" style="padding-bottom: 30px;">
                        <div class="modal-header" style="border:0">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">更新基础奖金信息</h4>
                        </div>
                        <form id="modifyBaseForm">
                        <div class="modal-body">
                            <div class="form_list">
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            职位
                                        </label>
                                        <span id="jobName" style="width:180px;display:inline-block;padding:6px 12px;"></span>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            具体环节
                                        </label>
                                        <span id="srvItemCode" style="width:180px;display:inline-block;padding:6px 12px;"></span>
                                    </div>
                                </div>
                                <div class="line">
                                	
                                	<div class="form_content">
                                        <label class="control-label sign_left_small">
                                            基础奖金
                                        </label>
                                        <input id="srvFee" name="srvFee" class="input_type sign_right_one" placeholder="请输入" value="">
                                        <span class="date_icon">元</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="pkid" name="pkid" value="">
                        </form>
                        <div class="modal-footer" style="text-align:center;border:0">
                            <button id="modifyBase" type="button" class="btn btn-success" >保存</button>
                            <button type="button" class="btn btn-grey" data-dismiss="modal">取消</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div>
            
    </div>

	<input type="hidden" id="ctx" value="${ctx}" /> 
<content tag="local_script">
<script src="${ctx}/js/jquery-2.1.1.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${ctx}/static/js/inspinia.js"></script>
<!-- jQuery UI -->
<script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<!-- 引入弹出框js文件 -->
<script src="${ctx}/js/common/xcConfirm.js?v=1.0.1"></script>

<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
<script src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>

<!-- 分页控件  -->
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"> </script>

<%@include file="/WEB-INF/jsp/tbsp/common/scriptBaseOrgDialog_new.jsp"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<script src="<c:url value='/transjs/award/managerRewardList.js' />"> </script>
    <script id="template_managerPieceInfo" type="text/html">
		{{each rows as item index}}
		    <tr>
              <td>{{item.JOB_NAME}}</td>
              <td>
					{{ if item.SRV_ITEM_CODE == 'TransSign' || item.SRV_ITEM_CODE == 'ComLoanProcess' }}
					前台组
					{{else item.SRV_ITEM_CODE == 'Guohu' || item.SRV_ITEM_CODE == 'PSFSign' || item.SRV_ITEM_CODE == 'LoanClose'}}
					后台组
					{{/if}}
			  </td>
              <td>{{item.SRV_ITEM_NAME}}</td>
              <td>{{item.SRV_FEE}}</td>
			  <td><button class="btn btn-success mr5" onclick="modifyBaseInfo('{{item.PK_ID}}','{{item.JOB_NAME}}','{{item.SRV_ITEM_NAME}}','{{item.SRV_FEE}}')">修改 </button></td>
             </tr>
		{{/each}}
</script>
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
              		<button onclick="delManagerInfo('{{item.PKID}}')" class="btn btn-grey" >删除 </button>
				{{/if}}
              	
              </td>
             </tr>
		{{/each}}
</script>
<script type="text/javascript">
$(function(){
	var ctx = $("#ctx").val();
	//页面初始化
	reloadGrid();
});
function reloadGrid(){
	var data = {};
	data.pagination = false; 
	$("#managerPieceInfoList").reloadGrid({
    	ctx : ctx,
		queryId : "managerPieceInfoList",
	    templeteId : 'template_managerPieceInfo',
	    data : data,
	    wrapperData : data
    });
}

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
		$("#jobName").val(array[0].jobName);
		$("#orgName").val(array[0].orgName);
		$("#orgId").val(array[0].orgId);
	}
}
</script>   
</content>   
   </body>
</html>