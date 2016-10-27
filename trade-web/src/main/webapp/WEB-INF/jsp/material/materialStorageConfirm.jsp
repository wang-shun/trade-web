
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
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
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
                                       <input type="text" value="" class="select_control info_two" name="relevantUser"  id="relevantUser" 
                                        hVal="" readonly="readonly"	onclick="chooseApplyOperator('${serviceDepId}')" />
                                       <input  type="hidden"  value=""  name="relevantUserId"  id="relevantUserId">
                                       <i class="icon iconfont input-group add-icon organize_icon" id="materialApplyUser"></i>
                                   <p>
                               </div>
                           </div>
                       </div>
                   </div>
               </div> 
                   
		       <div class="ibox-title">
					<c:choose>
						<c:when test="${accesoryList!=null}">
							<h5>上传附件</h5>
							<span style="color: red;font-size: 15px;">&nbsp;&nbsp;[&nbsp;说明：单次上传客户确认书，请做成一张附件上传&nbsp;&nbsp;]</span>
							<div class="ibox-content" style="height: 280px; overflow-y: scroll;">
								<h5>${accesoryList[0].accessoryName }</h5>
								
								<c:forEach var="accesory"  items="${accesoryList}"	varStatus="status">
									<div class=""  id="fileupload_div_pic">							
										<form id="fileupload"	action="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"	method="POST" enctype="multipart/form-data">
											<noscript>
												<input type="hidden" name="redirect" value="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload">
												<input type="hidden" id="preFileCode" name="preFileCode" value="${accesory.accessoryCode }">
											</noscript>
											<c:if test="${status.index != 0}">
												<h5 align="left"><br>${accesory.accessoryName }</h5>
											</c:if>
											
											<div class="row-fluid fileupload-buttonbar">
												<div class="" style="height: auto">
													  <div role="presentation" class="table table-striped "	style="height: auto; margin-bottom: 10px; line-height: 80px; text-align: center; border-radius: 4px; float: left;">
															<div id="picContainer${accesory.pkid }" class="files"	data-toggle="modal-gallery" data-target="#modal-gallery"></div>
															<input type="hidden" id="fileFlagCode${accesory.pkid }"   value="${accesory.accessoryCode }">															
															<span class=" fileinput-button "	style="margin-left: 10px !important; width: 80px;">
																<div id="chandiaotuBtn" class="" style="height: 80px; width: 100%; border: 1px solid #ccc; line-height: 80px; text-align: center; border-radius: 4px;">
																	<i class="fa fa-plus"></i>
																</div> 													
																<input id="picFileupload${accesory.pkid }" type="file"	name="files[]" multiple 	data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"	data-sequential-uploads="true">
														  </span>
													</div>
												</div>
											</div>									
										</form>
									</div>
		
									<div class="row-fluid">
										<div class="">
											<script id="templateUpload${accesory.pkid }" type="text/x-tmpl">
										{% for (var i=0, file; file=o.files[i]; i++) { %}
							    			<div name="allPicDiv1" class="template-upload fade row-fluid span2 in" style="height:80px;border:1px solid #ccc;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
											<!--图片缩图  -->
							       				 <div class="preview"><span class="fade"></span></div>
													<!--  错误信息 -->
							        				{% if (file.error) { %}
							            			<div class="error span12" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        				{% } else if (o.files.valid && !i) { %}
													<!-- 单个对应的按钮  -->
							            			<div class="start span1" style="display: none">
													{% if (!o.options.autoUpload) { %}
							                		<button class="btn"><i class="icon-upload icon-white"></i><span>上传</span></button>
							            			{% } %}
													</div>
							        				{% } else { %}
							            			<div class="span1" colspan="2"></div>
							        				{% } %}
							        				<div class="cancel" style="margin-top:-172px;margin-left:85%;">
													{% if (!i) { %}
							            			<button class="btn red" style="width:20px;height:20px;border-radius:80px;line-height:20px;text-align:center;padding:0!important;"><i class="icon-remove"></i></button>
							        				{% } %}
													</div>
							    				</div>
										{% } %}
									</script>
									<script id="templateDownload${accesory.pkid }"	type="text/x-tmpl">
									{% for (var i=0, file; file=o.files[i]; i++) { %}
							    		<div name="allPicDiv1" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;line-height:80px;text-align:center;border-radius:4px;float:left;">
							        {% if (file.error) { %}
							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else { %}
							            <div class="preview span12">
										<input type="hidden" name="preFileAdress" value="{%=file.id%}"></input>
										<input type="hidden" name="picTag" value="${accesory.accessoryCode }"></input>
										<input type="hidden" name="picName" value="{%=file.name%}"></input>
							            {% if (file.thumbnail_url) { %}
							                <img src="http://img.sh.centaline.com.cn/salesweb/image/{%=file.id%}/80_80_f.jpg" style="width:80px;height:80px;margin-left:10px;">
							            {% } %}</div>
							            <div class="name" style="display: none">
							                <a href="{%=file.url%}" title="{%=file.name%}" data-gallery="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
							            </div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-130px;">
							          	 	<button data-url="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/deleteFile?fileId=ff8080814ecf6e41014ee8ce912d04be" data-type="GET" class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
							               		<i class="icon-remove"></i>
							            	</button>
							        	</div>
							    	</div>
									{% } %}
								</script>
							</div>
						</div>
					</c:forEach>
					<div class="row-fluid" style="display: none;">
						<div class="span4">
							<div class="control-group">
								<a class="btn blue start" id="startUpload"
									style="height: 30px; width: 50px"> <i
									class="icon-upload icon-white"></i> <span>上传</span>
								</a>
							</div>
						</div>
					</div>
				</div>
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
                               <input class="input_type extent-one"  name="materialList[${status.index}].itemCategory" id="itemCategory" value="${mmMaterialInfo.itemCategory}" readonly="readonly">
                           </div>
                           <div class="form_content">
                               <label class="control-label sign_left_small">
                                   	物品名称
                               </label>
                               <input class="input_type"  value="${mmMaterialInfo.itemName}" name="materialList[${status.index}].itemName" id="itemName" readonly="readonly">
                           </div>
                           <div class="form_content">
                               <label class="control-label sign_left_small">
                                  	 业务描述
                               </label>
                               <input class="input_type extent-three" value="${mmMaterialInfo.itemBusinessRemark}" name="materialList[${status.index}].itemBusinessRemark" id="itemBusinessRemark" readonly="readonly">
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
  	
  	
<content tag="local_script"> 
<!-- Mainly scripts -->
<%-- <script src="${ctx}/static/js/jquery-2.1.1.js"></script> --%>
<script src="${ctx}/static/trans/js/spv/spvRecordShow.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${ctx}/static/js/inspinia.js"></script>
<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
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
		alert("文件位置编号必须填写！");
	}
	return itemAddrCodeFlag;
}


function  imgCheckNum(){
	var imgFlag = true;
	var picDiv=$("div[name='allPicDiv1']");
	if(picDiv.length > 1){
		imgFlag = false;
		alert("客户确认书请做成一张附件再上传！");
	}
	return imgFlag;
}

//借用组织图
function chooseApplyOperator(id){		
		userSelect({
			startOrgId : id,
			expandNodeId : id,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
/*			jobCode : 'Manager,Senior_Manager',*/
			jobCode : 'consultant',
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
$('#materialApplyUser').click(function() {
	chooseApplyOperator(serviceDepId);
});
</script>
</content>
</body>
</html>