<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
	<!-- 图标 -->
    <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">

    <!-- Data Tables -->
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.bootstrap.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.responsive.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.tableTools.min.css' />" />

    <!-- index_css -->
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/base.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />

	<!-- jQuery UI -->
    <link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">

    <!-- 分页控件 -->
    <link href="<c:url value='/static/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
    
    <!-- 上传相关 -->
	<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />" rel="stylesheet">
	<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />" rel="stylesheet">
	<link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />" rel="stylesheet">    
    
    	<!-- 必须CSS -->
	<link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />
    
	<!-- owner -->
    <link rel="stylesheet" href="<c:url value='/static/trans/css/property/processingList.css' />" />
    <link href="<c:url value='/static/trans/css/property/popmac.css' />" rel="stylesheet" />
	<link rel="stylesheet" href="<c:url value='/js/viewer/viewer.min.css' />" />
	<script type="text/javascript">
		var optTransferRole=false;
		<shiro:hasPermission name="TRADE.PRSEARCH.TRANSFER">
			optTransferRole=true;
		</shiro:hasPermission>
		var idList = [ 1 ];
		var pkid ='';
		var taskitem = "";
		var caseCode = "";
		var prCode = '';
	</script>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<!--*********************** HTML_main*********************** -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <h2 class="title">已受理产调</h2>
        <form id="myForm" action="${ctx }/quickGrid/findPage?xlsx" method="post" class="form_list">
			<input type="hidden" name="queryId" value="queryProcessingList">
			<input type="hidden" id="ctx" value="${ctx}"/>
			<input type="hidden" name="search_prDistrictId" value="${prDistrictId}">
			<input type="hidden" name="search_prStatus" value="1"/>
			<input type="hidden" id="prDistrictId" value="${prDistrictId}"/>
			<input type="hidden" id="prStatus" value="1"/>
			<input type="hidden" id="caseCode" value=""/>
			<input type="hidden" name="colomns" value="DIST_CODE,PROPERTY_ADDR,PR_CAT,orgName,applyOrgName,orgMgr,PR_APPLIANT,PR_APPLY_TIME,PR_ACCPET_TIME,PR_STATUS,IS_SUCCESS,UNSUCCESS_REASON">            
            <div class="line">
                <div class="form_content">
                    <label class="control-label sign_left">行政区域</label>
                    <aist:dict id="distCode" clazz="select_control sign_right_one" name="search_distCode" display="select"  dictType = "yu_shanghai_district" />
                </div>
                <div class="form_content">
                    <label class="control-label sign_left">产证地址</label>
                    <input type="text" id="addr" name="search_propertyAddr" class="teamcode input_type" style="width:435px;" placeholder="请输入" value="">
                </div>
                <div class="form_content space_two">
                    <div class="add_btn">
                        <button id="addrSearchButton" type="button" class="btn btn-success"><i class="icon iconfont"></i>查询</button>
                        <button id="exportExcelButton" type="button" class="btn btn-success" onclick="document.getElementById('myForm').submit();return false">导出至Excel</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div id="processingList" class="table_content"></div>
        </div>
    </div>
    
	<div class="modal fade manageWindow" id="modal-form" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content fadeIn" style="border-radius: 5px!important;">
                <div class="modal-header clearfix" style="border-top-left-radius: 5px!important;border-top-right-radius: 5px!important;">
                   <p class="pull-left" style="font-family: 'Microsoft Yahei';font-size: 14px;text-align: left;color: #fff;margin: 0px;">处理产调</p>
                   <div class="ibox-tools">
                       <a class="close-link pull-right" data-dismiss="modal"> X </a>
                   </div>
                </div>
                <div class="modal-body">
                	<form action="">
                    	<table class="table table-striped table-bordered dataTables-example dataTable dtr-inline">
        					<tbody>
						        <tr class="gradeA" role="row">
						            <td valign="middle" style="width:59px !important;">物业地址</td>
						            <td colspan="5"><span id="address"></span></td>
						        </tr>
						        <tr class="gradeA" role="row">
						            <td class="sorting" style="width:59px !important;">区域 </td>
						            <td><span id="distcode"></span></td>
						            <td class="sorting">所属分行</td>
						            <td class="center"><span id="applyOrgName"></span></td>
						            <td class="sorting">区蕫</td>
						            <td class="center"><span id="orgMgr"></span></td>
						        </tr>
						        <tr class="gradeA" role="row">
						            <td style="width:59px !important;">产调项目</td>
						            <td colspan="5" class="info_text"><span id="prcat"></span></td>
						        </tr>
						        <tr class="gradeA" role="row">
						            <td style="width:59px !important;">执行人</td>
						            <td colspan="5" class="inputlab">
						              <input type="text" id="executor" name="executor" style="width: 50%;background-color:#FFFFFF;" class="form-control tbspuser" hVal="" value="" readonly="readonly"
										onclick="userSelect({startOrgId:'${serviceDepId}',expandNodeId:'${serviceDepId}',
										nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" />
						            </td>
						        </tr>
						        <tr class="gradeA" role="row">
						            <td style="width:59px !important;">是否有效</td>
						            <td colspan="5" class="inputlab">
						              	<input type="radio" name="isScuess" value="0">无效
						              	<input type="radio" name="isScuess" value="1" checked="checked">有效
						            </td>
						        </tr>
						       
						        <tr class="gradeA" role="row" id="wuxiao">
						            <td>无效原因</td>
						            <td colspan="5" class="inputlab">
						              	<textarea name="unSuccessReason" id='unSuccessReason' class="form-control"></textarea>
						            </td>
						        </tr>
        					</tbody>
        				</table>
        				
        				<div class="table-box" id="fileUploadContainer"></div>
		            </form>
                </div>
                <div class="modal-footer btn-center" style="padding-top:20px !important;">
                    <input type="button" class="btn btn-success" id="btn_save" value="保存"> 
                    <input type="button" class="btn btn-success" id="btn_done" value="完成">
                </div>
            </div>
         </div>
   	</div>    
</div>
<!--*********************** HTML_main*********************** -->
</body>
<content tag="local_script">
    <!-- js模板引擎 -->
    <script src="<c:url value='/static/js/template.js' />"></script>
    <!-- 分页控件  -->
    <script src="<c:url value='/static/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
    <!-- jQuery UI -->
    <script src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>    
    <!-- 组织 -->
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>    
    <!-- 自定义扩展jQuery库 -->
    <script src="<c:url value='/static/js/plugins/jquery.custom.js' />"></script>
   <%--  <script src="<c:url value='/static/js/plugins/aist/aist.jquery.custom.js' />"></script> --%>
    <script src="<c:url value='/static/trans/js/property/aist.jquery.custom.ps.js' />"></script>
    
	<!-- 上传附件相关 -->
	<script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/load-image.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-fp.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-ui.js' />"></script>

	<script src="<c:url value='/js/trunk/JSPFileUpload/clockface.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.multi-select.js' />"></script>

	<script src="<c:url value='/js/trunk/JSPFileUpload/form-fileupload.js' />"></script>

	<script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jssor.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script>
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 --> 
	<script src="<c:url value='/js/trunk/task/attachment.js' />"></script>    

	<!-- 必须JS -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
   
    <!-- owner -->
    <script src="<c:url value='/static/trans/js/property/processingList.js' />"></script>
    <script src="<c:url value='/js/viewer/viewer.min.js' />"></script> 
    <script id="template_processingList" type="text/html">
		{{each rows as item index}}
		<tr>
			<td>
				<p class="big deep_grey" style="color:#808080;">{{item.applyOrgName}}</p>
					{{if item.IS_SUCCESS == '是'}}
						<span class="yes_color">有效</span>
					{{else if item.IS_SUCCESS == '否'}}
						<span class="no_color">无效</span><a class="demo-right" title="{{item.UNSUCCESS_REASON}}"><i class="icon iconfont" style="font-size: 20px;color:#808080">&#xe609;</i></a>
					{{else}}
					{{/if}}
			</td>
            <td>
				<p class="big deep_grey">{{item.DIST_CODE}}</p>
                <p class="big tint_grey">{{item.PROPERTY_ADDR}}</p>
            </td>
            <td>
                <p class="smll_sign"><i class="sign_normal">申</i>{{item.PR_APPLY_TIME}}</p>
                <p class="smll_sign"><i class="sign_normal">受</i>{{item.PR_ACCPET_TIME}}</p>
            </td>
            <td>
            	<span class="manager" style="margin:0px !important;">
					<a href="#"><em>申请人：{{item.PR_APPLIANT}}</em></a>
					{{if item.CHANNEL == '经纪人'}}
						<i class="sign_red">经</i>
					{{else}}
						<i class="sign_normal">誉</i>
					{{/if}}
				</span>
                <span class="manager" style="margin:0px !important;"><a href="#"><em>执行人：{{item.PR_EXECUTOR}}</em></a></span>
                <span class="manager" style="margin:0px !important;"><a href="#"><em>区董：{{item.orgMgr}}</em></a></span>
            </td>
            <td class="text-center">
				{{if wrapperData.optTransferRole}}
					<p><button type="button" style="padding:0px 5px;" class="btn btn-success" id="teamCode" name="teamCode" readonly="readonly" onclick="showOrgSelect({{item.PKID}})" value='' >转组</button></p>
					<p><button type="button" style="padding:0px 5px;" onclick="showAttchBox('{{item.CASE_CODE}}','{{item.PR_CODE}}','{{item.PART_CODE}}','{{item.PKID}}','{{item.IS_SUCCESS}}','{{rep(item.UNSUCCESS_REASON?item.UNSUCCESS_REASON:'')}}','{{item.PROPERTY_ADDR}}','{{item.PR_CAT}}','{{item.applyOrgName}}','{{item.orgMgr}}','{{item.DIST_CODE}}','{{item.PR_EXECUTORID}}','{{item.PR_EXECUTOR}}');" class="btn btn-success">处理</button></p>
				{{else}}
				<p><button type="button" onclick="showAttchBox('{{item.CASE_CODE}}','{{item.PR_CODE}}','{{item.PART_CODE}}','{{item.PKID}}','{{item.IS_SUCCESS}}','{{rep(item.UNSUCCESS_REASON?item.UNSUCCESS_REASON:'')}}','{{item.PROPERTY_ADDR}}','{{item.PR_CAT}}','{{item.applyOrgName}}','{{item.orgMgr}}','{{item.DIST_CODE}}','{{item.PR_EXECUTORID}}','{{item.PR_EXECUTOR}}');" class="btn btn-success">处理</button></p>
				{{/if}}
            </td>
		</tr>
		{{/each}}
	</script>
	<script>
		  template.helper("rep", function(a){  
	          return a.replace(/[\r\n]/g,"");  
	      });
	</script>
    <script type="text/javascript">
$(function(){
	setStyle();
});

//渲染图片 
function renderImg(){
	$('.wrapper-content').viewer('destroy');
	$('.wrapper-content').viewer();
}

function checkAttachment() {
	var length = $("#fileUploadContainer ul li").length;
	
	if(length == 0){
		alert("请上传备件！");
		checkAtt = false;
		return false;
	}
	else {
		checkAtt = true;
	}
	
	return checkAtt;
}
</script>	    
</content>

<content tag="local_require">
       <script>
       		var fileUpload;
       
		    require(['main'], function() {
				requirejs(['jquery','aistFileUpload','validate','grid','jqGrid',
				           'additional','blockUI','valid','ligerui','bootstrapModal',
				           'modalmanager','aistJqueryCustom','twbsPagination','poshytip'],function($,aistFileUpload){
					fileUpload = aistFileUpload;
			    });
		    });
		</script>
    </content>
</html>
