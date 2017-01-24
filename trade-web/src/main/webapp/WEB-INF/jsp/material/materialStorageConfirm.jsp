
<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=utf-8" 	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>入库确认</title>

<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/datapicker/datepicker3.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
<!-- 附件 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/font-awesome.css" rel="stylesheet">
<!-- 提示 -->
<link rel="stylesheet" href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" /> 
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv.css" />
<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" /> 

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"	rel="stylesheet" />
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
<script type="text/javascript">
	var ctx = "${ctx}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index = 0;
	var taskitem = "CustomerConfirmation";
	var caseCode = "${mmMaterialItemList.get(0).caseCode}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}	
</script>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>                    
    <div class="row">
       <div class="wrapper wrapper-content animated fadeInUp marginbot-30 ">
       	<form action="${ctx}/material/materialStay"  method="post"  id="materialStorgaeInfoSave">
           <div class="ibox-content" id="reportOne">
                <div class="row">
                   <div class="col-lg-12">
                       <div class="m-b-md">
                           <h4>入库</h4>
                       </div>
                       <div class="row" id="">
                           <div class="info_content">
                               <div class="line clearfix">
                                   <p>
                                       <label> 案件编号</label>
                                       <span class="info_one">${mmMaterialItemList.get(0).caseCode}</span>
                                   </p>
                                   <p>
                                       <label>案件地址</label>
                                       <span>${propertyAddr}</span>
                                   </p>
                               </div>
                               <div class="line">
                                   <p class="input-infoht">
                                       <label>文件位置编号</label>
                                       <input type="hidden" id="attachPkid"  name="attachPkid"  value="" />
                                       <input type="text" value="" class="select_control info_one"  name="itemAddrCode"  id="itemAddrCode">
                                   <p>
                                   <p class="input-infoht"  style="position:relative;">
                                       <label>申请人</label>
                                       <input type="text"  class="select_control info_two input_type"   name="relevantUser"  id="relevantUser" 
                                        readonly="readonly"	 value="${mmMaterialItemList.get(0).createBy}"/>
                                       <input  type="hidden"  value="${createByCode}"  name="relevantUserId"  id="relevantUserId">
                                   <p> 
 <%--                                   <p class="input-infoht"  style="position:relative;">
                                       <label>申请人</label>
                                       <input type="text" value="" class="select_control info_two" name="relevantUser"  id="relevantUser" 
                                        hVal="" readonly="readonly"	onclick="chooseApplyOperator('${serviceDepId}')" />                                       
                                       <i class="icon iconfont input-group add-icon organize_icon" id="materialApplyUser">&#xe627;</i>
                                       <input  type="hidden"  value=""  name="relevantUserId"  id="relevantUserId">
                                   <p> --%>
                               </div>
                           </div>
                       </div>
                   </div>
               </div> 
                   
		       <div class="ibox-title">
					<c:choose>
						<c:when test="${accesoryList!=null}">
							<h5>上传附件<span style="color: red;font-size: 15px;">&nbsp;&nbsp;[&nbsp;说明：单次上传客户确认书，请做成一张附件上传&nbsp;]</span></h5>							
							<div class="table-box" id="fileUploadContainer"></div>
					</c:when>
					<c:otherwise>
						<h5>上传备件<br>无需上传备件</h5>
					</c:otherwise>
				</c:choose>
	</div>               
               <div class="enregister">
                   <div class="modal_title">
                       <h4>物品信息登记</h4>
                   </div>
                   <c:forEach items="${mmMaterialItemList}"  var="mmMaterialInfo"  varStatus="status">
                   <div class="form_list">
                       <div class="line">
                           <div class="form_content">
                               <label class="control-label sign_left_small">
                                  	 物品类型
                               </label>
                               <input type="hidden" name="materialList[${status.index}].pkid" id="pkid" value="${mmMaterialInfo.pkid}">
                               <input type="hidden" name="materialList[${status.index}].caseCode"  value="${mmMaterialInfo.caseCode}">
                               <input class="input_type extent-one_material"   id="itemCategory" value="${mmMaterialInfo.itemCategory}" disabled="disabled">
                           </div>
                           <div class="form_content">
                               <label class="control-label sign_left_small">
                                   	物品名称
                               </label>
                               <input class="input_type extent-two_material"  value="${mmMaterialInfo.itemName}"  id="itemName" disabled="disabled">
                           </div>
                           <%--1、name="materialList[${status.index}].itemCategory"；2、name="materialList[${status.index}].itemName"；3、name="materialList[${status.index}].itemBusinessRemark"
                           readonly="readonly"--%>

                       </div>
                       
                       <div class="line">
                           <div class="form_content">
                               <label class="control-label sign_left_small">
                                  	 业务描述
                               </label>
                               <input class="input_type extent-three_material" value="${mmMaterialInfo.itemBusinessRemark}"  id="itemBusinessRemark" disabled="disabled">
                           </div>
                       </div>
                       <div class="line clearfix">
                           <div class="form_content">
                               <label class="control-label sign_left_small pull-left">备注</label>
                               <textarea class="enregisterarea textarea" name="materialList[${status.index}].itemRemark" id="itemRemark" ></textarea>
                           </div>
                       </div>
                   </div>
                 </c:forEach>
               </div>

               <div class="status_btn text-center">
                   <button type="button" class="btn btn-success btn-space" id="materialStorgaeSubmit">提交</button>
                   <button type="button"  class="btn btn-grey"  id="materialStorageClose">关闭</button>
               </div>
           </div>
          </form>
       </div>
   </div>
  	<input type="hidden" id="ctx" value="${ctx}" />
  	<input type="hidden" id="caseCode" value="${mmMaterialItemList.get(0).caseCode}" />
  	<input type="hidden" id="serviceDepId" value="${serviceDepId}" />
  	
<content tag="local_script"> 
<!-- 上传附件相关 --> 
<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> 
<script	src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> 
<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
<!-- 上传附件 结束 -->
<!-- 附件保存修改相关 -->
<script src="${ctx}/js/jquery.blockui.min.js"></script> 
<script	src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<!-- stickup plugin -->
<%-- <script src="${ctx}/static_res/trans/js/spv/jkresponsivegallery.js"></script> --%>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
<script src="${ctx}/js/template.js" type="text/javascript"></script> 
<!-- stickup plugin -->
<script src="${ctx}/js/viewer/viewer.min.js"></script>
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>	
<script src="${ctx}/js/trunk/material/attachmentForMaterial.js"></script> 

<!-- 选择组织控件 -->
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 	
<script	src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
<script type="text/javascript">
var ctx = "${ctx}";
var pkid = "${pkid}";
var serviceDepId = $("#serviceDepId").val();
//关闭按钮
$("#materialStorageClose").click(function(){	
	window.location.href = ctx+"/material/materialList";
})

$("#materialStorgaeSubmit").click(function(){	
		//文件存放位置必填验证
		if(!itemAddrCodeCheck()){			
			return false;
		}
		//上传附件只能上传一张
		if(!imgCheckNum()){
			return false;
		}
		//上传附件信息
		materialAttachmentSave();
		//表单提交保存业务信息
		$("#materialStorgaeInfoSave").submit();
})

function itemAddrCodeCheck(){
	var itemAddrCodeFlag = true;
	var itemAddrCode = $("#itemAddrCode").val();
	if(null == itemAddrCode || ""==itemAddrCode){
		itemAddrCodeFlag = false;
		window.wxc.alert("文件位置编号必须填写！");
	}
	return itemAddrCodeFlag;
}


function  imgCheckNum(){
	var imgFlag = true;
	var picDiv=$("div[name='allPicDiv1']");
	if(picDiv.length > 1){
		imgFlag = false;
		window.wxc.alert("客户确认书请做成一张附件再上传！");
	}
	return imgFlag;
}

//借用组织图
function chooseApplyOperator(serviceDepId){		
		userSelect({
			startOrgId : 'ff8080814f459a78014f45a73d820006',//非营业部
			expandNodeId : 'ff8080814f459a78014f45a73d820006',
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : '',
/* 			jobCode : 'consultant', */
			callBack : selectApplyUserBack
		});	
}

//选取人员的回调函数relevantUserId
function  selectApplyUserBack(array) {	
	if (array && array.length > 0) {
		// todo 添加隐藏属性和实体类对应
		$("#relevantUser").val(array[0].username);
		$("#relevantUser").attr('hVal', array[0].userId);
		$("#relevantUserId").val(array[0].userId);
		
	} else {
		$("#relevantUser").val("");
		$("#relevantUser").attr('hVal', "");
		$("#relevantUserId").val("");
	}
}
//主办图标选择
$("#materialApplyUser").click(function() {
		chooseApplyOperator(serviceDepId);
});
</script>
</content>

<content tag="local_require">
    <script>
	    require(['main'], function() {
			requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','valid','ligerui','bootstrapModal','modalmanager'],function($,aistFileUpload){
			    aistFileUpload.init({
		    		caseCode : $('#caseCode').val(),
		    		partCode : "CustomerConfirmation",
		    		fileUploadContainer : "fileUploadContainer"
		    	}); 
		    });
	    });
	</script>
</content>

</body>
</html>