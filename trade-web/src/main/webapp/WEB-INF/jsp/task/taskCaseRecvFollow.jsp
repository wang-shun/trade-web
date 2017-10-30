<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>


<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link
	href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<!-- 上传相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<!-- bank  select -->
<link href="<c:url value='/css/plugins/chosen/chosen.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<!--弹出框样式  -->
<link href="<c:url value='/css/common/xcConfirm.css' />"
	rel="stylesheet">
<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<script type="text/javascript">
	var teamProperty = "${teamProperty}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index = 0;
	var taskitem = "${taskitem}";

	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>

<script type="text/javascript">
	var AttachmentList = (function() {
		return {
			init : function(ctx, url, gridTableId, gridPagerId, ctmCode,
					caseCode) {
				//jqGrid 初始化
				$("#" + gridTableId)
						.jqGrid(
								{
									url : ctx + url,
									mtype : 'GET',
									datatype : "json",
									height : 250,
									autowidth : true,
									shrinkToFit : true,
									rowNum : 5,
									/*   rowList: [10, 20, 30], */
									colNames : [ '附件类型', '附件名称', '附件路径',
											'上传时间', '操作' ],
									colModel : [ {
										name : 'FILE_CAT',
										index : 'FILE_CAT',
										align : "center",
										width : 20,
										resizable : false
									}, {
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
									}, {
										name : 'READ',
										index : 'READ',
										align : "center",
										width : 20,
										resizable : false
									} ],
									multiselect : true,
									pager : "#" + gridPagerId,
									//sortname:'UPLOAD_DATE',
									//sortorder:'desc',
									viewrecords : true,
									pagebuttions : true,
									multiselect : false,
									hidegrid : false,
									recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
									pgtext : " {0} 共 {1} 页",
									gridComplete : function() {
										var ids = jQuery("#" + gridTableId)
												.jqGrid('getDataIDs');
										for (var i = 0; i < ids.length; i++) {
											var id = ids[i];
											var rowDatas = jQuery(
													"#" + gridTableId).jqGrid(
													'getRowData', ids[i]); // 获取当前行

											var link = "<button  class='btn red' onclick='showAttachment(\""
													+ rowDatas['URL']
													+ "\")'>查看附件</a>";
											jQuery("#" + gridTableId).jqGrid(
													'setRowData', ids[i], {
														READ : link
													});
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
<script type="text/javascript">
	//显示附件图片
	function showAttachment(url) {
		window.open(url);

	}
	//提交数据
	function submit() {
		var caseCode = $("#caseCode").val();
		if (!caseCode) {
			window.wxc.alert("请以正确的方式进入该页面！");
			return;
		}
		save(true);
	}

	//保存数据
	function save(b) {
		var caseCode = $("#caseCode").val();
		if (!caseCode) {
			window.wxc.alert("请以正确的方式进入该页面！");
			return;
		}

		if (b) {
			if (!checkForm()) {
				return;
			}
		}

		var jsonData = $("#firstFollowform").serializeArray();

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
			complete : function() {
                $.unblockUI();
                if (b) {
                    $.blockUI({
                        message : $("#salesLoading"),
                        css : {
                            'border' : 'none',
                            'z-index' : '1900'
                        }
                    });
                    $(".blockOverlay").css({
                        'z-index' : '1900'
                    });
                }

                //超时,status还有success,error等值的情况
                if (status == 'timeout') {
                    Modal.alert({
                        msg : "抱歉，系统处理超时。"
                    });
                    $(".btn-primary").one("click", function() {
                        parent.$.fancybox.close();
                    });
                }
            },
			success : function(data) {
				if (b) {
					window.wxc.success("保存成功。",{"wxcOk":function(){
                        window.close();
                        window.opener.callback();
                    }});
				} else {
					if (data.message) {
						window.wxc.alert("保存成功!");
					}
				}
			},
			error : function() {
				window.wxc.alert("提交信息出错。。");
			}
		});
	}

	//double验证
	function checkNum(obj) {
		//先把非数字的都替换掉，除了数字和.
		obj.value = obj.value.replace(/[^\d.]/g, "");
		//必须保证第一个为数字而不是.
		obj.value = obj.value.replace(/^\./g, "");
		//保证只有出现一个.而没有多个.
		obj.value = obj.value.replace(/\.{2,}/g, ".");
		//保证.只出现一次，而不能出现两次以上
		obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace(
				"$#$", ".");
	}

	//验证控件checkUI();
	function checkForm() {
		if ($("#distCode").val() == 0) {
			window.wxc.alert("区域为必选项!");
			$("#distCode").focus();
			$("#distCode").css("border-color", "red");
			return false;
		}

		if (!$("#square").val()) {
			window.wxc.alert("产证面积为必选项!");
			$("#square").focus();
			$("#square").css("border-color", "red");
			return false;
		}

		if (!$("#propertyAddr").val()) {
			window.wxc.alert("产证地址为必填项!");
			$("#propertyAddr").focus();
			$("#propertyAddr").css("border-color", "red");
			return false;
		}

		if ($('input[name=realPrice]').val() == '') {
			window.wxc.alert("成交价为必填项!");
			$('input[name=realPrice]').focus();
			$('input[name=realPrice]').css("border-color", "red");
			return false;
		}

		/* if ($('input[name=conPrice]').val() == '') {
			window.wxc.alert("合同价为必填项!");
			$('input[name=conPrice]').focus();
			$('input[name=conPrice]').css("border-color", "red");
			return false;
		} */

		/* if ($("#estEvlApplyTime").val() == 0) {
			window.wxc.alert("评估申请时间为必选项!");
			$("#estEvlApplyTime").focus();
			$("#estEvlApplyTime").css("border-color", "red");
			return false;
		} */

		if ($("#estSignTime").val() == 0) {
			window.wxc.alert("网签时间为必选项!");
			$("#estSignTime").focus();
			$("#estSignTime").css("border-color", "red");
			return false;
		}

		if ($("#purchaseHouseNo").val() == 0) {
			window.wxc.alert("购房套数为必选项!");
			$("#purchaseHouseNo").focus();
			$("#purchaseHouseNo").css("border-color", "red");
			return false;
		}

		if ($("#marriageStatus").val() == 0) {
			window.wxc.alert("婚姻情况为必选项!");
			$("#marriageStatus").focus();
			$("#marriageStatus").css("border-color", "red");
			return false;
		}

		if ($("#familyStatus").val() == 0) {
			window.wxc.alert("家庭情况为必选项!");
			$("#familyStatus").focus();
			$("#familyStatus").css("border-color", "red");
			return false;
		}

		if ($("#payType").val() == 0) {
			window.wxc.alert("付款方式为必选项!");
			$("#payType").focus();
			$("#payType").css("border-color", "red");
			return false;
		}

		if ($("#huji").val() == 0) {
			window.wxc.alert("户籍为必选项!");
			$("#huji").focus();
			$("#huji").css("border-color", "red");
			return false;
		}

		if ($("#houseSubsidy").val() == 0) {
			window.wxc.alert("住房补贴为必选项!");
			$("#houseSubsidy").focus();
			$("#houseSubsidy").css("border-color", "red");
			return false;
		}

		if ($("#houseFrom").val() == 0) {
			window.wxc.alert("房屋来源为必选项!");
			$("#houseFrom").focus();
			$("#houseFrom").css("border-color", "red");
			return false;
		}

		if ($("#mortInfo").val() == 0) {
			window.wxc.alert("抵押信息为必选项!");
			$("#mortInfo").focus();
			$("#mortInfo").css("border-color", "red");
			return false;
		}
		//抵押信息请选择、无、有；如果“有”，则输入预计清尾完成时间
		if($("#mortInfo").val() == 61011001){		
			if ($("#estFinishTime").val() == 0) {
				window.wxc.alert("预计清尾完成时间为必选项!");
				$("#estFinishTime").focus();
				$("#estFinishTime").css("border-color", "red");
				return false;
			}
		}

		if ($("#isUniqueHome").val() == 0) {
			window.wxc.alert("房屋套数为必选项!");
			$("#isUniqueHome").focus();
			$("#isUniqueHome").css("border-color", "red");
			return false;
		}

		return true;
	}

	$("input[type='text'],select").focus(function() {
		$(this).css("border-color", "rgb(229, 230, 231)");
	});
	
	/**
	 * 询价申请
	 */
	function evaPricingApply(){
	    var info = "系统已存在与此案件相关的询价记录，是否关联？";
	    var caseCode = $('#caseCode').val();
	    var url = ctx+'/case/checkEvaPricing?caseCode='+caseCode;
	    $.ajax({
	        cache : false,
	        async : true,
	        url:url,
	        type:'POST',
	        dataType:'json',
	        success : function(data) {
	            if(data.success){
	                if(data.content){
	                	window.wxc.alert("系统已存在与此案件相关的询价记录!");
	                    /*window.wxc.confirm(info,{'wxcOk':function(){

	                    },'wxcCancel':function(){
	                        //新增询价
	                        var ctx = $("#ctx").val();
	                        window.open(ctx+"/evaPricing/addNewEvaPricing?caseCode=" +caseCode);
	                    }});*/
	                }else{
	                    var ctx = $("#ctx").val();
	                    window.open(ctx+"/evaPricing/addNewEvaPricing?caseCode=" +caseCode);
	                }
	            }else{
	                window.wxc.error(data.message);
	            }
	        },
	        error : function(XMLHttpRequest, textStatus, errorThrown) {
	        }
	    });

	}
	/**
	 * 评估申请
	 */
	function evalApply(){
		
		var ctx = $("#ctx").val();
		var caseCode = $('#caseCode').val();
		
		window.open(ctx+"/task/eval/apply?caseCode="+caseCode);
		
		/**
		 * modify wbcaiyx 2017/10/27
		 * 一个案件可以有多条评估，不检查了
		 */
		/*//判断是否已有评估申请流程	
		var url = ctx+'/case/checkEvalProcess?caseCode='+caseCode;
		$.ajax({
			url:url,
			type:'POST',
			dataType:'json',
			success:function(data){
				if(data.success){
					*//**
					 * modify wbcaiyx 2017/10/26
					 * 无关询价，注释
					 *//*				
					if(data.content == 1){//询价已完成,可以评估申请
						window.open(ctx+"/task/eval/apply?caseCode="+caseCode);
					}else if(data.content == 2){//无询价,进入询价申请
						window.wxc.confirm("无完成询价记录,是否申请询价？",{"wxcOk":function(){
							window.open(ctx+"/evaPricing/addNewEvaPricing?caseCode=" +caseCode);
						}});
						*//**
						 * modify 无询价直接评估 
						 * @author wbcaiyx
						 * date 2017/10/24
						 *//*
						window.open(ctx+"/task/eval/apply?caseCode="+caseCode);
//					}
				}else{
					window.wxc.alert(data.message);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown) {

			}
		});*/
		
	}
</script>
<style type="text/css">
.radio.radio-inline>label {
	margin-left: 10px;
}

.radio.radio-inline>input {
	margin-left: 10px;
}

.checkbox.checkbox-inline>div {
	margin-left: 25px;
}

.checkbox.checkbox-inline>input {
	margin-left: 20px;
}

.product-type span {
	margin: 0 5px 5px 0
}

.product-type .selected, .product-type span:hover {
	border-color: #f8ac59
}

.ibox-content-task {
	padding-bottom: 40px !important;
}

#corss_area {
	padding: 0 8px 0 0;
	margin-left: 369px;
}

#corss_area select {
	height: 34px;
	border-radius: 2px;
	margin-left: 20px;
}
</style>
<title>接单跟进</title>
<content tag="pagetitle">接单跟进</content>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
		<script src="<c:url value='/js/trunk/case/caseBaseInfo.js' />"></script>
	<div class="">
		<div class="row wrapper white-bg new-heading" id="serviceFlow">
			<div class="pl10">
				<h2 class="newtitle-big">接单跟进</h2>
				<div class="mt20">
					<button type="button" class="btn btn-icon btn-blue mr5"
						id="btnZaitu">
						<i class="iconfont icon">&#xe640;</i> 在途单列表
					</button>
					<button type="button" class="btn btn-icon btn-blue mr5"
						id="btnCaseView" lang="${caseCode}">
						<i class="iconfont icon">&#xe642;</i>案件视图
					</button>
					<a role="button" class="btn btn-primary btn-xm btn-activity"
						href="javascript:evaPricingApply()">询价申请</a>
					<a role="button" class="btn btn-primary btn-xm btn-activity"
									href="javascript:evalApply()">评估申请</a>			
				</div>
			</div>
		</div>

		<div
			class="ibox-content border-bottom clearfix space_box noborder marginbot">
			<h2 class="newtitle title-mark">填写房产信息</h2>
			<form method="get" class="form_list" id="firstFollowform"
				style="overflow: visible;">
				<input type="hidden" id="ctx" value="${ctx }" />

				<%--环节编码 --%>
				<input type="hidden" id="partCode" name="partCode"
					value="${taskitem}">
				<!-- 交易单编号 -->
				<input type="hidden" id="caseCode" name="caseCode"
					value="${caseRecvVO.caseCode}">
				<!-- 流程引擎需要字段 -->
				<input type="hidden" id="taskId" name="taskId" value="${taskId }">
				<input type="hidden" id="processInstanceId" name="processInstanceId"
					value="${processInstanceId}">
				<%-- 原有数据对应id --%>
				<input type="hidden" id="caseId" name="caseId"
					value="${firstFollow.caseId }"> <input type="hidden"
					id="tTLId" name="tTLId" value="${firstFollow.tTLId }"> <input
					type="hidden" id="signId" name="signId"
					value="${firstFollow.signId}"> <input type="hidden"
					id="propertyInfoId" name="propertyInfoId"
					value="${firstFollow.propertyInfoId}">
				<%-- 设置审批类型 --%>
				<input type="hidden" id="approveType" name="approveType"
					value="${approveType }"> <input type="hidden" id="operator"
					name="operator" value="${operator }">
				<%-- T_TO_FIRST_FOLLOW 表  --%>
				<input type="hidden" id="firstfollowId" name="firstfollowId"
					value="${firstFollow.firstfollowId}" />
				<%--自办服务 --%>
				<input type="hidden" id="zbkServices" name="zbkServices"
					value="3000401001" />

				<div class="marinfo">
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>所在区域 </label>
							<aist:dict clazz="select_control data_style" id="distCode"
								name="distCode" display="select"
								defaultvalue="${caseRecvVO.toPropertyInfo.distCode}"
								parentCode="${sessionScope.SESSION_USER.cityCode}"
								dictType="UAM_CITY" />
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>成交价 </label> <input type="text"
								placeholder="成交价" class="input_type yuanwid" id="realPrice"
								name="realPrice" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${caseRecvVO.toSign.realPrice}' type='number' pattern='#0.00'/>">
							<span class="date_icon">万元</span>
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small">
							<!-- <font color=" red" class="mr5">*</font> -->合同价 </label> <input type="text"
								placeholder="合同价" class="input_type yuanwid" id="conPrice"
								name="conPrice" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${caseRecvVO.toSign.conPrice}' type='number' pattern='#0.00'/>">
							<span class="date_icon">万元</span>
						</div>
					</div>

					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>产证面积 </label> <input type="number"
								class="input_type data_style" id="square" name="square"
								onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${caseRecvVO.toPropertyInfo.square}' type='number' pattern='#0.0#' />">
							<span class="date_icon">平方米</span>
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>产证地址 </label> <input type="text"
								class="input_type mendwidth" id="propertyAddr"
								name="propertyAddr"
								value="${caseRecvVO.toPropertyInfo.propertyAddr}">
						</div>
					</div>

					<hr>
					<div class="line">
						<div class="title title-mark">
							<strong style="font-weight: bold;">预计时间</strong>
						</div>

					</div>
					<div class="line">
						<div class="form_content mt3">
							<label
								class="control-label sign_left_small select_style mend_select">
								<!-- <font color=" red" class="mr5">*</font> -->评估申请时间
							</label>
							<div
								class="input-group sign-right dataleft input-daterange pull-left"
							 data-date-format="yyyy-mm-dd">
								<input type="text" class="input_type yuanwid datatime"
									id="estEvlApplyTime" name="estEvlApplyTime"
									onfocus="this.blur()"
									value="<fmt:formatDate  value='${caseRecvVO.toCaseRecv.estEvlApplyTime}' type='both' pattern='yyyy-MM-dd'/>">
							</div>
						</div>

						<div class="form_content mt3">
							<label
								class="control-label sign_left_small select_style mend_select">
								<font color=" red" class="mr5">*</font>网签时间
							</label>
							<div
								class="input-group sign-right dataleft pull-left"
								 data-date-format="yyyy-mm-dd">
								<input type="text" class="input_type yuanwid datatime"
									id="estSignTime" name="estSignTime" onfocus="this.blur()"
									value="<fmt:formatDate  value='${caseRecvVO.toCaseRecv.estSignTime}' type='both' pattern='yyyy-MM-dd'/>">
							</div>
						</div>

						<div class="form_content mt3">
							<label
								class="control-label sign_left_small select_style mend_select">
								面签时间 </label>
							<div
								class="input-group sign-right dataleft  pull-left"
								 data-date-format="yyyy-mm-dd">
								<input type="text" class="input_type yuanwid datatime"
									id="estF2fSignTime" name="estF2fSignTime" onfocus="this.blur()"
									value="<fmt:formatDate  value='${caseRecvVO.toCaseRecv.estF2fSignTime}' type='both' pattern='yyyy-MM-dd'/>">
							</div>
						</div>
						
					</div>
					<hr>
					<div class="line">
						<div class="title title-mark">
							<strong style="font-weight: bold;">买方信息</strong>
						</div>
					</div>
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>购房套数</label>
							<aist:dict clazz="select_control data_style" id="purchaseHouseNo"
								name="purchaseHouseNo" display="select"
								defaultvalue="${caseRecvVO.toCaseRecv.purchaseHouseNo}"
								dictType="61000" hasEmpty="true" />
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small">贷款套数 </label>
							<aist:dict clazz="select_control data_style" id="loanHouseNo"
								name="loanHouseNo" display="select"
								defaultvalue="${caseRecvVO.toCaseRecv.loanHouseNo}"
								dictType="61000" hasEmpty="true" />
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>婚姻情况 </label>
							<aist:dict clazz="select_control data_style" id="marriageStatus"
								name="marriageStatus"
								defaultvalue="${caseRecvVO.toCaseRecv.marriageStatus}"
								display="select" dictType="61001" hasEmpty="true" />
						</div>
					</div>

					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>家庭情况</label>
							<aist:dict clazz="select_control data_style" id="familyStatus"
								name="familyStatus" display="select"
								defaultvalue="${caseRecvVO.toCaseRecv.familyStatus}"
								dictType="61002" hasEmpty="true" />
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>付款方式 </label>
							<aist:dict clazz="select_control data_style" id="payType"
								name="payType" display="select"
								defaultvalue="${caseRecvVO.payType}" dictType="61003"
								hasEmpty="true" />
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small">评估</label>
							<aist:dict clazz="select_control data_style" id="evalByWho"
								name="evalByWho"
								defaultvalue="${caseRecvVO.toCaseRecv.evalByWho}"
								display="select" dictType="61005" hasEmpty="true" />
						</div>
					</div>

					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>户籍</label>
							<aist:dict clazz="select_control data_style" id="huji"
								name="huji" display="select"
								defaultvalue="${caseRecvVO.toCaseRecv.huji}" dictType="61006"
								hasEmpty="true" />
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small">社保 </label>
							<aist:dict clazz="select_control data_style"
								id="societySecuretyYears" name="societySecuretyYears"
								defaultvalue="${caseRecvVO.toCaseRecv.societySecuretyYears}"
								display="select" dictType="61007" hasEmpty="true" />
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small">税单</label>
							<aist:dict clazz="select_control data_style"
								id="taxSecuretyYears" name="taxSecuretyYears"
								defaultvalue="${caseRecvVO.toCaseRecv.taxSecuretyYears}"
								display="select" dictType="61008" hasEmpty="true" />
						</div>
					</div>

					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>住房补贴</label>
							<aist:dict clazz="select_control data_style" id="houseSubsidy"
								name="houseSubsidy" display="select"
								defaultvalue="${caseRecvVO.toCaseRecv.houseSubsidy}"
								dictType="61009" hasEmpty="true" />
						</div>

					</div>

					<div class="line alerted">
						<div class="form_content" style="width: 260.16px;">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>贷款预警 </label>
							<div class="controls">
								<label class="radio inline"> <input type="radio"
									value="1" id="optionsRadios1" name="businessLoanWarn"
									<c:if test="${!empty caseRecvVO.bizWarnInfo.content}">checked="checked"</c:if>>是
								</label> <label class="radio inline"> <input type="radio"
									<c:if test="${empty caseRecvVO.bizWarnInfo.content }">checked="checked"</c:if>
									value="0" id="optionsRadios2" name="businessLoanWarn">否
								</label>
							</div>
						</div>

					</div>

					<div class="line warncon" id="divContent"
						<c:if test="${empty caseRecvVO.bizWarnInfo.content}">style="display:none;"</c:if>>
						<div class="form_content clearfix">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>预警内容 </label> <input type="text"
								class="mendwidth input_type" id="loanWarn" name="loanWarn"
								value="${caseRecvVO.bizWarnInfo.content}" style="width: 500px;">
						</div>
					</div>


					<hr>
					<div class="line">
						<div class="title title-mark">
							<strong style="font-weight: bold;">卖方信息</strong>
						</div>
					</div>
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>房屋来源</label>
							<aist:dict clazz="select_control data_style" id="houseFrom"
								name="houseFrom" display="select"
								defaultvalue="${caseRecvVO.toCaseRecv.houseFrom}"
								dictType="61010" hasEmpty="true" />
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>抵押信息</label>
							<aist:dict clazz="select_control data_style" id="mortInfo"
								name="mortInfo"
								defaultvalue="${caseRecvVO.toCaseRecv.mortInfo}"
								display="select" dictType="61011" hasEmpty="true" />
						</div>

						<div id="estFinishTimeDiv" class="form_content mt3">
							<label
								class="control-label sign_left_small select_style mend_select">
								<font color=" red" class="mr5">*</font>预计清尾时间
							</label>
							<div
								class="input-group sign-right dataleft input-daterange pull-left"
								 data-date-format="yyyy-mm-dd">
								<input type="text" class="input_type yuanwid datatime"
									id="estFinishTime" name="estFinishTime" onfocus="this.blur()"
									value="<fmt:formatDate  value='${caseRecvVO.toCaseRecv.estFinishTime}' type='both' pattern='yyyy-MM-dd'/>">
							</div>
						</div>
					</div>

					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>房屋套数</label>
							<aist:dict clazz="select_control data_style" id="isUniqueHome"
								name="isUniqueHome"
								defaultvalue="${caseRecvVO.toTax.isUniqueHome}" display="select"
								dictType="61012" hasEmpty="true" />
						</div>

						<div class="form_content">
							<label class="control-label sign_left_small">原购入价格 </label> <input
								type="text" placeholder="成交价" class="input_type yuanwid"
								id="oriPrice" name="oriPrice" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${caseRecvVO.toCaseRecv.oriPrice}' type='number' pattern='#0.00'/>">
							<span class="date_icon">万元</span>
						</div>


					</div>



				</div>

				<div class="hr"></div>

				<!-- <div class="line clearfix" id="hzxm" style="overflow:visible;"></div> -->
			</form>

			<div class="view-content" id="caseCommentList"></div>

			<div class="title title-mark" id="aboutInfo">
				<strong style="font-weight: bold;">CCAI附件</strong>
			</div>
			<div class="view-content">
				<table id="gridTable" class=""></table>
				<div id="gridPager"></div>
			</div>

			<div class="form-btn">
				<div class="text-center">
				<!-- 任哥和文档要求评估申请放在案件操作里面 -->
				<!-- <button class="btn btn-success btn-space" onclick="javascript:evalApply()"
						id="btnSave">发起评估申请</button> -->
					<button class="btn btn-success btn-space" onclick="save(false)"
						id="btnSave">保存</button>
					<button class="btn btn-success btn-space" onclick="submit()">提交</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<content tag="local_script"> <script
		src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<%-- <script src="<c:url value='/transjs/task/loanlostApprove.js' />"></script> --%>
	<%-- <script src="<c:url value='/transjs/task/showAttachment.js' />"></script>  --%>
	<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script>
	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
	<script
		src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> <script
		src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
	<script src="<c:url value='/transjs/task/follow.pic.list_new.js' />"></script>
	<script src="<c:url value='/static/js/jquery.json.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
	<script
		src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script
		src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
	<script
		src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
	<script
		src="<c:url value='/js/trunk/task/taskFirstFollow.validate.js' />"></script>
	<script src="<c:url value='/js/plugins/layer/layer.js' />"></script> <script
		src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
	<script
		src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />"></script> <script
		src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/stickUp.js' />"></script> <!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script> <script
		src="<c:url value='/js/common/common.js' />"></script> <script>
			$(document)
					.ready(
							function() {
								var ctx = $("#ctx").val();
								
								var caseCode = $("#caseCode").val();
								AttachmentList.init('${ctx}',
										'/quickGrid/findPage', 'gridTable',
										'gridPager', '${ctmCode}', caseCode);
								$("#caseCommentList").caseCommentGrid({
									caseCode : caseCode,
									srvCode : 'caseRecvFlow'
								});
								//日历控件
								/* $('.input-daterange').datepicker({
									keyboardNavigation : false,
									forceParse : false,
									autoclose : true
								}); */

								//设置div显示或隐藏
								function isShow(divName, stats) {
									var div_array = document
											.getElementsByName(divName);
									for (i = 0; i < div_array.length; i++) {
										div_array[i].style.display = stats;
									}
								}
	
								//日期组件
								 $('#estEvlApplyTime').datepicker({
									todayBtn : "linked",
									keyboardNavigation : false,
									forceParse : false,
									calendarWeeks : false,
									autoclose : true
								}).on('changeDate',function(ev){									
									var inputtime=ev.date.valueOf();
									var newDate=new Date().getTime(); 
									if(inputtime<newDate){
										window.wxc.alert("请输入大于今日的时间!");
									}
						        }); 
								//日期组件
								 $('#estSignTime').datepicker({
									todayBtn : "linked",
									keyboardNavigation : false,
									forceParse : false,
									calendarWeeks : false,
									autoclose : true
								}).on('changeDate',function(ev){									
									var inputtime=ev.date.valueOf();
									var newDate=new Date().getTime(); 
									if(inputtime<newDate){
										window.wxc.alert("请输入大于今日的时间!");
									}
						        }); 
								//日期组件
								 $('#estF2fSignTime').datepicker({
									todayBtn : "linked",
									keyboardNavigation : false,
									forceParse : false,
									calendarWeeks : false,
									autoclose : true
								}).on('changeDate',function(ev){									
									var inputtime=ev.date.valueOf();
									var newDate=new Date().getTime(); 
									if(inputtime<newDate){
										window.wxc.alert("请输入大于今日的时间!");
									}
						        }); 
								//商贷预警
								$("[name=businessLoanWarn]").click(
										function() {
											if ($(this).val() == '1') {
												$("#divContent").css("display",
														"inherit");
											} else {
												$("#divContent").css("display",
														"none");
											}
								});
								//抵押信息请选择、无、有；如果“有”，则输入预计清尾完成时间
								$("[name=mortInfo]").click(
										function() {
											console.log($(this).val());
											if ($(this).val() == 61011001) {
												$("#estFinishTimeDiv").css("display",
														"inherit");
											} else {
												$("#estFinishTimeDiv").css("display",
														"none");
											}
											
												}
								); 
							})//end ready function
		</script> </content>
</body>

</html>
