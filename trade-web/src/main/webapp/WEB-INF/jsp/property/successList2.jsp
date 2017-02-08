<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
	<!-- 图标 -->
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

    <!-- Data Tables -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
    <!-- datapicker -->
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/property/successList.css" />

	<!-- jQuery UI -->
    <link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">

    <!-- 分页控件 -->
    <link href="${ctx}/static/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
    
    <link href="${ctx}/static/trans/css/property/popmac.css" rel="stylesheet" />
    
    <!-- 上传相关 -->
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
	<!-- owner -->
	<link rel="stylesheet" href="${ctx}/static/trans/css/property/processingList.css" />
	
	<!-- 必须CSS -->
    <link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
	<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" />
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
<!--*********************** HTML_main*********************** -->
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content border-bottom clearfix space_box">
        <h2 class="title">已完成产调</h2>
        <form id ="myForm" method="post" class="form-horizontal form_box" action="${ctx }/quickGrid/findPage?xlsx">
            <div class="row clearfix">
		    	<input type="hidden" id="xlsx" name="xlsx" value="xlsx"/>
		   		<input type="hidden" id="queryId" name="queryId" value="querySuccessList"/>
		    	<input type="hidden" id="ctx" value="${ctx}"/>
				<input type="hidden" id="prDistrictId" name="search_prDistrictId" value="${prDistrictId}"/>
				<input type="hidden" id="prStatus"name="search_prStatus" value="2"/>
		    	<input type="hidden" name="colomns" value="PROPERTY_ADDR,orgName,applyOrgName,PR_CAT,PR_APPLIANT,APPLIANT_EMPLOYEE_CODE,PR_EXECUTOR,PR_APPLY_TIME,PR_ACCPET_TIME,PR_COMPLETE_TIME,IS_SUCCESS,UNSUCCESS_REASON,DIST_CODE,QUDS,CHANNEL">            	
                <div class="form_content">
                    <label class="sign_left_two control-label">行政区域</label>
                    <div class="sign_right big_pad">
                        <aist:dict id="distCode" clazz="form-control patch_formatone" name="search_distCode" display="select"  dictType = "yu_shanghai_district" />
                    </div>
                </div>
                <div class="form_content">
                    <label class="sign_left_two control-label">产调项目</label>
                    <div class="sign_right teamcode">
                        <input type="text" id="prCat" name="search_prCat" class="teamcode form-control">
                    </div>
                </div>
                <div class="form_content">
                    <label class="sign_left_two control-label">所属分行</label>
                    <div class="sign_right teamcode">
                        <input type="text" id="grpOrgName" name="search_grpOrgName" class="teamcode form-control">
                    </div>
                </div>
            </div>
            <div class="row clearfix">
                <div class="form_content">
                    <label class="sign_left_two control-label">所属区董</label>
                    <div class="sign_right big_pad">
                        <input type="text" id="quds" name="search_quds" class="form-control patch_formatone">
                    </div>
                </div>
                <div class="form_content">
                    <label class="sign_left_two control-label">产调执行人</label>
                    <div class="sign_right teamcode">
                        <input type="text" id="acuUser" name="search_acuUser" class="teamcode form-control">
                    </div>
                </div>
                <div class="form_content">
                    <label class="sign_left_two control-label">产调申请人</label>
                    <div class="sign_right teamcode">
                        <input type="text" id="auUser" name="search_auUser" class="teamcode form-control">
                    </div>
                </div>
            </div>
            <div class="row clearfix">
                <div class="form_content">
                    <label class="sign_left_two control-label">是否有效</label>
                    <div class="sign_right big_pad">
                        <aist:dict id="isSuccess" clazz="form-control patch_formatone" name="search_isSuccess" display="select"  dictType = "nullityTag" />
                    </div>
                </div>
                <div class="form_content">
                    <label class="sign_left_two control-label">来源</label>
                    <div class="sign_right big_pad">
                        <select ltype="select" id="prChannel" name="search_prChannel" class="form-control patch_formatone" validate="" onchange="" ligerui="">
	    					<option value="" selected="selected">请选择</option>
	    					<option value="0">内部</option>
	    					<option value="1">经纪人</option>
                        </select>
                    </div>
                </div>
                <div class="form_content">
                    <label class="sign_left_two control-label">完成时间</label>
                    <div class="input-group input-daterange pull-left" data-date-format="yyyy-mm-dd">
                        <input id="completeTimeStart" name="search_completeTimeStart" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期">
                        <span class="input-group-addon">到</span>
                        <input id="completeTimeEnd" name="dtEnd" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期">
                        <input type="hidden" name="search_completeTimeEnd" id="search_completeTimeEnd" value="">
                    </div>
                </div>
            </div>
            <div class="row date-info clearfix">
                <div class="form_content">
                    <label class="sign_left_two control-label">产证地址</label>
                    <div class="sign_right intextval">
                        <input type="text" id="addr" name="search_propertyAddr" class="form-control pull-left">
                    </div>
                </div>
                <div class="form_content space">
                    <div class="more_btn">
                        <button type="button" id="addrSearchButton" class="btn btn-success"><i class="icon iconfont"></i>查询</button>
                        <button id="cleanButton" type="button" class="btn btn-success">清空</button>
                        <button type="button" class="btn btn-success" onclick="submitForm()">导出至Excel</button>
                    </div>
                </div>
            </div>

        </form>
    </div>
 <div class="row">
        <div class="col-md-12">
            <div id="successList" class="table_content"></div>
        </div>
    </div> 
    
	<div class="modal fade manageWindow" id="modal-form" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content fadeIn" style="border-radius: 5px!important;">
                <div class="modal-header clearfix" style="border-top-left-radius: 5px!important;border-top-right-radius: 5px!important;">
                   <p class="title pull-left">处理产调</p>
                   <div class="ibox-tools">
                       <a class="close-link pull-right" data-dismiss="modal"> X </a>
                   </div>
                </div>
                <div class="modal-body">
                	<form action="">
                    	<table class="table table-striped table-bordered dataTables-example dataTable dtr-inline">
        					<tbody>
						        <tr class="gradeA" role="row">
						            <td valign="middle">物业地址</td>
						            <td colspan="5"><span id="address"></span></td>
						        </tr>
						        <tr class="gradeA" role="row">
						            <td class="sorting">区域 </td>
						            <td><span id="distcode"></span></td>
						            <td class="sorting">所属分行</td>
						            <td class="center"><span id="applyOrgName"></span></td>
						            <td class="sorting">区蕫</td>
						            <td class="center"><span id="orgMgr"></span></td>
						        </tr>
						        <tr class="gradeA" role="row">
						            <td >产调项目</td>
						            <td colspan="5" class="info_text"><span id="prcat"></span></td>
						        </tr>
						        <tr class="gradeA" role="row">
						            <td>执行人</td>
						            <td colspan="5" class="inputlab">
						              <input type="text" id="executor" name="executor" style="width: 50%;background-color:#FFFFFF;" class="form-control tbspuser" hVal="" value="" readonly="readonly"
										onclick="userSelect({startOrgId:'${serviceDepId}',expandNodeId:'${serviceDepId}',
										nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" />
						            </td>
						        </tr>
						        <tr class="gradeA" role="row">
						            <td>是否有效</td>
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
                <div class="modal-footer btn-center">
                    <input type="button" class="btn btn-success" id="btn_done" value="完成">
                </div>
            </div>
         </div>
   	</div>    
</div>
<!--*********************** HTML_main*********************** -->
</div>
</div>
<content tag="local_script">
    <!-- js模板引擎 -->
    <script src="${ctx}/static/js/template.js"></script>
    <!-- 分页控件  -->
    <script src="${ctx}/static/js/plugins/pager/jquery.twbsPagination.min.js"></script>
    <!-- 组织 -->
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>    
    <!-- 自定义扩展jQuery库 -->
    <script src="${ctx}/static/js/plugins/jquery.custom.js"></script>
   <%--  <script src="${ctx}/static/js/plugins/aist/aist.jquery.custom.js"></script> --%>
    <script src="${ctx}/static/trans/js/property/aist.jquery.custom.ps.js"></script>
    
	<!-- 上传附件相关 -->
	<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script>
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 --> 
	<script src="${ctx}/js/trunk/task/attachment.js"></script>
	
	<!-- datapicker -->
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    
	<!-- 必须JS -->
	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>    
    <!-- owner -->
    <script src="${ctx}/static/trans/js/property/successList.js"></script>
    <script src="${ctx}/js/viewer/viewer.min.js"></script>
	<script id="template_successList" type="text/html">
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
                	<p class="big tint_grey"><a href='../mobile/property/box/show?prCode={{item.prCode}}' style="text-decoration: none;" target='_blank'>{{item.PROPERTY_ADDR}}</a></p>
                </td>
                <td>
                	<p class="smll_sign"><i class="sign_normal">申</i>{{item.PR_APPLY_TIME}}</p>
                	<p class="smll_sign"><i class="sign_normal">受</i>{{item.PR_ACCPET_TIME}}</p>
                	<p class="smll_sign"><i class="sign_normal">完</i>{{item.PR_COMPLETE_TIME}}</p>
            	</td>
                <td>
                	<span class="manager" style="margin:0px !important;">
						<a href="#"><em>申请人：</em>{{item.PR_APPLIANT}}</a>
						{{if item.CHANNEL == '经纪人'}}
							<i class="sign_red">经</i>
						{{else}}
                 			<i class="sign_normal">誉</i>
						{{/if}}
					</span>
                    <span class="manager" style="margin:0px !important;"><a href="#"><em>执行人：</em>{{item.PR_EXECUTOR}}</a></span>
                    <span class="manager" style="margin:0px !important;"><a href="#"><em>区董：</em>{{item.QUDS}}</a></span>
                </td>
				<td>
					{{if wrapperData.optTransferRole}}
						<p><button type="button" style="padding:0px 5px;" class="btn btn-success" onclick="showAttchBox('{{item.CASE_CODE}}','{{item.prCode}}','{{item.PART_CODE}}','{{item.PKID}}','{{item.IS_SUCCESS}}','{{rep(item.UNSUCCESS_REASON?item.UNSUCCESS_REASON:'')}}','{{item.PROPERTY_ADDR}}','{{item.PR_CAT}}','{{item.applyOrgName}}','{{item.QUDS}}','{{item.DIST_CODE}}','{{item.PR_EXECUTORID}}','{{item.PR_EXECUTOR}}');" class='btn btn-warning btn-xs btn-y'>修改</button></p>
						<p><a href='../mobile/property/box/show?prCode={{item.prCode}}' style="padding:0px 5px;" class="btn btn-success" target='_blank'>查看附件</a></p>
					{{else}}
						<p><a href='../mobile/property/box/show?prCode={{item.prCode}}' class="btn btn-success" target='_blank'>查看附件</a></p>
                 	{{/if}}
				</td>			
			</tr>
		{{/each}}
	</script>
	<script>
		template.helper("rep", function(a){  
			return a.replace(/[\r\n]/g,"");
		});
		
		function report(){
			var data=packData();
		}
		
		function submitForm(){
			$('#search_completeTimeEnd').val($('#completeTimeEnd').val()==''?'':$('#completeTimeEnd').val()+' 23:59:59');
			$('#myForm').submit();
		}
	
		// 清空
		$('#cleanButton').click(function() {
			$("input[name='search_prCat']").val('');
			$("input[name='search_propertyAddr']").val('');
			$("input[name='search_grpOrgName']").val('');
			$("input[name='search_acuUser']").val('');
			$("input[name='search_auUser']").val('');
			$("input[name='search_quds']").val('');
			$("input[name='search_completeTimeStart']").val('');
			$("input[id='completeTimeEnd']").val('');
		});
		
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
		
        $(document).ready(function () {

            $('.input-daterange').datepicker({
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true
            });
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
   
</body>
</html>
