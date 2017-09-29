<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<!-- 上传相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<!-- bank  select -->
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<!--弹出框样式  -->
<link href="<c:url value='/css/common/xcConfirm.css' />" rel="stylesheet">
<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>


<script type="text/javascript">
var AttachmentList = (function(){    
    return {    
       init : function(ctx,url,gridTableId,gridPagerId,ctmCode,caseCode){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'GET',
    			datatype : "json",
    			height : 250,
    			autowidth : true,
    			shrinkToFit : true,
    			rowNum : 5,
    			/*   rowList: [10, 20, 30], */
    			colNames : [ '附件类型','附件名称','附件路径','上传时间','操作'],
    			colModel : [ {
    				name : 'FILE_CAT',
    				index : 'FILE_CAT',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'FILE_NAME',
    				index : 'FILE_NAME',
    				align : "center",
    				width : 20,
    				resizable : false
    			}, {
    				name : 'URL',
    				index : 'URL',
    				align : "center",
    				width : 20,
    				resizable : false
    				//formatter : linkhouseInfo
    			}, {
    				name : 'UPLOAD_TIME',
    				index : 'UPLOAD_TIME',
    				align : "center",
    				width : 20,
    				resizable : false
    			},{
    				name : 'READ',
    				index : 'READ',
    				align : "center",
    				width : 20,
    				resizable : false
    			}],
    			multiselect: true,
    			pager : "#"+gridPagerId,
    			//sortname:'UPLOAD_DATE',
    	        //sortorder:'desc',
    			viewrecords : true,
    			pagebuttions : true,
    			multiselect:false,
    			hidegrid : false,
    			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
    			pgtext : " {0} 共 {1} 页",
    			gridComplete:function(){
    				var ids = jQuery("#"+gridTableId).jqGrid('getDataIDs');
    				for (var i = 0; i < ids.length; i++) {
	    				var id = ids[i];
	    				var rowDatas = jQuery("#"+gridTableId).jqGrid('getRowData', ids[i]); // 获取当前行	    				
	    				var link = "<button  class='btn red' onclick='showAttachment(\""+rowDatas['URL']+"\")'>查看附件</a>";
	    				
	    				jQuery("#"+gridTableId).jqGrid('setRowData', ids[i], { READ: link});
    				}
    			},
    			postData : {
    				queryId : "getCcaiAttachmentListByCaseCode",
    				caseCode : caseCode
    			}
    			 
    		});
       }   
    };    
})(); 
</script>
<style type="text/css">
.radio.radio-inline > label{margin-left:10px;}
.radio.radio-inline > input{margin-left:10px;}
.checkbox.checkbox-inline > div{margin-left:25px;}
.checkbox.checkbox-inline > input{margin-left:20px;}
.product-type span{margin:0 5px 5px 0}
.product-type .selected,.product-type span:hover{border-color:#f8ac59}
.ibox-content-task{padding-bottom:40px !important;}
#corss_area{padding:0 8px 0 0;margin-left:369px;}
#corss_area select{height:34px;border-radius:2px;margin-left:20px;}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="">
		<div class="row wrapper white-bg new-heading" id="serviceFlow">
             <div class="pl10">
                 <h2 class="newtitle-big">
                        	接单审核
                 </h2>
                <div class="mt20">
                    <button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
                    	<i class="iconfont icon">&#xe640;</i> 在途单列表
                    </button>
                    <button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
                        <i class="iconfont icon">&#xe642;</i>案件视图
                    </button>
                </div>
             </div>
        </div>

        <div class="ibox-content border-bottom clearfix space_box noborder marginbot">
            <h2 class="newtitle title-mark">付款方式:</h2>
            <div class="line">
		                     <div class="form_content">
                                <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>付款方式:</label>
                                <span><b>${payType}</b></span>
                                 <%-- <aist:dict clazz="select_control data_style" id="payType" name="payType" display="select" defaultvalue="${payType}" dictType="61003" /> --%>
		                     </div> 
		                </div>
	            <div class="title title-mark" id="aboutInfo">
	               <strong style="font-weight:bold;">CCAI附件信息</strong>
	            </div>
	            
	            <div class="view-content">
	              	<table id="gridTable" class=""></table>
	   				<div id="gridPager"></div>
	            </div>


			<div class="text-center">
				<button type="button" class="btn btn-success"
					onclick="auditSuccess()">审核通过</button>
				&nbsp&nbsp&nbsp&nbsp
				<button type="button" class="btn btn-success" onclick="openReturnModal()">驳回</button>
				&nbsp&nbsp&nbsp&nbsp
				<button type="button" class="btn btn-success" onclick="openModal()">新增贷款专员</button>

				<!-- 新增贷款专员 - 模态框（Modal） -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">请选择：</h4>
							</div>
							<div class="modal-body">
							<form id="jvForm" action="#" method="post">
							<input type="hidden" value="${caseCode}" name="caseCode" id="caseCode">
								贷款专员： <select id="loanProcessor" name="loanProcessor">
									<c:forEach var="item" items="${loanUserList}" >
										<option value="${item.username}">${item.realName}(${item.orgName})</option>
									</c:forEach>
								</select>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-success"
									data-dismiss="modal">关闭</button>
								<button type="button" class="btn btn-success" id="submit1" onclick="formSubmit()">
									提交</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
				</div>
					<!--模态框结束 -->
				
				<!-- 驳回 原因 - 模态框（Modal） -->
				<div class="modal fade" id="returnReasonModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">请选择：</h4>
							</div>
							<div class="modal-body">
							<form id="returnReasonModalForm" action="#" method="post">
							<input type="hidden" value="${caseCode}" name="caseCode" id="caseCode">
												
								驳回原因： <select id="returnReason" name="returnReason">									
										<option value="0">请选择</option>
										<option value="1">权证专员错误</option>
										<option value="2">附件错误</option>								
								</select>
								驳回备注：<input type="text" name="returnComment" id="returnComment" maxlength="50" style="width:520px;">
								
						
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-success"
									data-dismiss="modal">关闭</button>
								<button type="button" class="btn btn-success" id="submit1" onclick="formReturnReasonSubmit()">
									提交</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
				</div>
					<!--模态框结束 -->
			</div>
			
		</div>
			</div>
	
			<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
			<content tag="local_script"> 
				<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
				<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
				<script src="<c:url value='/transjs/task/showAttachment.js' />"></script> 
				<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
				<script	src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> 
				<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
				<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
			    <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
				<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
				<script src="<c:url value='/transjs/task/follow.pic.list_new.js' />"></script>
				<script src="<c:url value='/static/js/jquery.json.min.js' />"></script>
				<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
			    <script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
			    <script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
				<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
				<script src="<c:url value='/js/trunk/task/taskFirstFollow.validate.js' />"></script>
				<script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
				<script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
				<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
				<script src="<c:url value='/js/template.js' />"></script>
				<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
				<script src="<c:url value='/js/stickUp.js' />"></script>
				<!-- 改版引入的新的js文件 -->
				<script src="<c:url value='/js/common/textarea.js' />"></script>
				<script src="<c:url value='/js/common/common.js' />"></script>
	<script type="text/javascript">
	var ctx = "${ctx}";	
	var caseCode = $("#caseCode").val();
	
	function auditSuccess(){
		var caseCode = $("#caseCode").val();
		console.log(caseCode);
		window.wxc.confirm("请确认审核接单通过？",{"wxcOk":function(){
			var url=ctx+"/AuditImportCase/auditSuccess";
			$.ajax({
				cache : false,
				async : true,//false同步，true异步
				type : "POST",
				url : url,
				data: $("#jvForm").serialize(),
				dataType : "json",		
				beforeSend : function() {
		            $.blockUI({
		                message : $("#salesLoading"),
		                css : {
		                    'border' : 'none',
		                    'z-index' : '9999'
		                }
		            });
		            $(".blockOverlay").css({
		                'z-index' : '9998'
		            });
		        },
				success : function(data) {
					 $.unblockUI();
					 console.log(data);
					 window.wxc.alert(data.message);
				
					window.location.href=ctx+"/task/myTaskList";
					
				},
				error : function(errors) {
					window.wxc.error("数据出错."+errors.message);
				}
			});
		}
		});
	}
	
	function openModal(){
		$('#myModal').modal({
			keyboard:false,
			show:true,
			backdrop:true
		})
	}
	
	function openReturnModal(){
		$('#returnReasonModal').modal({
			keyboard:false,
			show:true,
			backdrop:true
		})
	}
	//增加贷款权证
	function formSubmit(){
		//var url=ctx+"/AuditImportCase/addLoanProcessor";
		//$("#jvForm").attr("action",url);
		//document.getElementById("jvForm").submit();
		
		var url=ctx+"/AuditImportCase/addLoanProcessor";
		$.ajax({
			cache : false,
			async : true,//false同步，true异步
			type : "POST",
			url : url,
			data: $("#jvForm").serialize(),
			dataType : "json",		
			beforeSend : function() {
	            $.blockUI({
	                message : $("#salesLoading"),
	                css : {
	                    'border' : 'none',
	                    'z-index' : '9999'
	                }
	            });
	            $(".blockOverlay").css({
	                'z-index' : '9998'
	            });
	        },
			success : function(data) {
				 $.unblockUI();
				 console.log(data);
				 window.wxc.alert(data.message);
				window.location.href=ctx+"/task/myTaskList";
			},
			error : function(errors) {
				window.wxc.error("数据出错。");
			}
		});
	}
	
	function formReturnReasonSubmit(){
		if($("#returnReason").val()==0){
			window.wxc.alert("请选择驳回原因!");
			return;
		}
		//var url=ctx+"/AuditImportCase/returnReason";
		//$("#returnReasonModalForm").attr("action",url);
		//document.getElementById("returnReasonModalForm").submit();
		var url=ctx+"/AuditImportCase/returnReason";
		$.ajax({
			cache : false,
			async : true,//false同步，true异步
			type : "POST",
			url : url,
			data: $("#returnReasonModalForm").serialize(),
			dataType : "json",		
			beforeSend : function() {
	            $.blockUI({
	                message : $("#salesLoading"),
	                css : {
	                    'border' : 'none',
	                    'z-index' : '9999'
	                }
	            });
	            $(".blockOverlay").css({
	                'z-index' : '9998'
	            });
	        },
			success : function(data) {
				 $.unblockUI();
				 console.log(data);
				 window.wxc.alert(data.message);
				window.location.href=ctx+"/task/myTaskList";
			},
			error : function(errors) {
				window.wxc.error("数据出错。");
			}
		});
	}
</script>
				<script>
					$(document).ready(function(){
						var ctx = $("#ctx").val();
						var caseCode = $("#caseCode").val();						
						console.log(caseCode);
						AttachmentList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager','${ctmCode}',caseCode);
					});
					//显示附件图片
					function showAttachment(url){
						window.open(url);
						
					}
					
					//日期组件
			        $('#data_1 .input-group.date').datepicker({
			            todayBtn: "linked",
			            keyboardNavigation: false,
			            forceParse: false,
			            calendarWeeks: false,
			            autoclose: true
			        });
										
			        function initAssistant() {
						var url = "${ctx}/task/firstFollow/getAssistantInfo";
						$("#xzzl").html("");
						$.ajax({
							cache : false,
							async : true,//false同步，true异步
							type : "POST",
							url : url,
							dataType : "json",		
							success : function(data) {
								txt = "<label class='control-label sign_left_small' style='margin-right: 23px;'><font color='red' class='mr5' >*</font>交易助理 </label>";
								txt += "<select class='select_control data_style' name='assistantId' style='width:180px;' id='chooseAsisstantUser" + index + "' onchange='resetColor();'>";
								txt += "<option value=''>请选择</option>";
								$.each(data.users, function(j, user){
									txt += "<option value='"+user.userId+"'>"+user.userRealName+"</option>";	
								});
								txt += "</select>";
								$("#xzzl").append(txt);
							},
							error : function(errors) {
								window.wxc.error("数据出错。");
							}
						});
					}
					
			       
			
					//点击生成或清除合作顾问下拉框
					$(document).on("click","#cooperationUser0_chosen",function(){
						$(".chosen-single>span").each(function(){
							if($(this).text()=="----跨区选择----"){
								$('#coUser'+index).val('');
								if($("#corss_area").length==0){
									crossAreaCooperation();
								}
							}else{
								$('#coUser'+index).val($("#cooperationUser" + index).find(':selected').val());
								if($("#corss_area").length>0){
									removeCrossAreaCooperation();
								}
							}	
						});
					});
					
					//提交数据
					function submit() {
					if ($('#optionsRadios2:checked').val() == "30003001") {
							if ($('input[name=invalid_reason]').val() == '') {
								window.wxc.alert("无效案件必须填写无效原因!");
								$('input[name=invalid_reason]').focus();
								return;
							}
						}
					
						save(true);
					}
			
					//保存数据
					function save(b) {
						if (!checkForm()) {
							return;
						}
						
						if (!$("#firstFollowform").valid()) {
							return;
						}
			
						var jsonData = $("#firstFollowform").serializeArray();
						
						var result = ''
						$("span.selected[name='srvCode']").each(function() {
							result += $(this).attr('value') + ',';
						});
						var obj = {
							name : 'srvCode',
							value : result.substring(0, result.length - 1)
						};
						jsonData.push(obj);
			
						for (var i = 0; i < jsonData.length; i++) {
							var item = jsonData[i];
							if (item["name"] == 'cooperationUser'
									&& (item["value"] == 0 || item["value"] == -1)) {
								delete jsonData[parseInt(i)];
							}
						}
			
						var url = "${ctx}/task/caseRecvFollow/save";
						if (b) {
							url = "${ctx}/task/caseRecvFollow/submit";
						}
						
						$.ajax({
							cache : true,
							async : false,
							type : "POST",
							url : url,
							dataType : "json",
							data : jsonData,
							beforeSend : function() {
								$.blockUI({
									message : $("#salesLoading"),
									css : {
										'border' : 'none',
										'z-index' : '9999'
									}
								});
								$(".blockOverlay").css({
									'z-index' : '9998'
								});
							},							
							success : function(data) {
								$.unblockUI();						
							},
							/* error : function(errors) {
								window.wxc.error("数据保存出错");
							} */
						});
					}				
				</script> 
			</content>
	</body>
</html>

