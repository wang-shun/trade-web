<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>知识库列表</title>

<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<!-- Add fancyBox main JS and CSS files -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/jquery.fancybox.css' />" media="screen" />
<!-- Add Button helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/jquery.fancybox-buttons.css' />" />
<!-- Add Thumbnail helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/jquery.fancybox-thumbs.css' />" />

<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/jquery.editable-select.min.css' />" rel="stylesheet">
<style type="text/css">
#table_list_2>tbody>tr>td{
	text-align: center;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<form action="" class="form-horizontal" id="f_main">	
	<input type="hidden" id="caseCode" name="caseCode" value="${loanAgent.caseCode }" />
	<input type="hidden" id="finCaseCode" name="finCaseCode" value="${loanAgent.finCaseCode }" />
	<input type="hidden" id="pkid" name="pkid" value="${loanAgent.pkid }" />
	<input type="hidden" id="executorId" name="executorId"/>
	<div class="ibox">
		<div class="ibox-title">
			<div class="row">
				<div class="col-xs-1 control-label">
					产品类型
				</div>
				<div class="col-xs-5">
				<%--	 <aist:dict id="select_loanSrvCode"  name="loanSrvCode" clazz="form-control"
						display="select"  dictType="yu_serv_cat_code_tree" tag="eplus" 
						ligerui='none' defaultvalue="${loanAgent.loanSrvCode }"></aist:dict> --%>
 					<select id="select_loanSrvCode"  name="loanSrvCode" class="form-control">
						<c:if test="${loanAgent.loanSrvCode!=undefined}">
						<option value="${loanAgent.loanSrvCode}"><aist:dict id="loanSrvCode" name="loanSrvCode" 
						display="onlyLabel"  dictType="yu_serv_cat_code_tree" tag="eplus" dictCode="${loanAgent.loanSrvCode}"
						ligerui='none'></aist:dict></option>
						</c:if>
						<c:if test="${loanAgent.loanSrvCode!='30004005' }">
						<option value="30004005">税费卡</option>
						</c:if>
						</select> 
						
				</div>
				
				<div class="col-xs-1 control-label">
					案件绑定
				</div>
				<div class="col-xs-4"><input type="text" placeholder="产证地址" class="input-sm form-control" id="txt_address">
				</div>
				<div class="col-xs-1" style="text-align: right;">
					<button type="button" class="btn btn-sm btn-primary" id="btn_search">&nbsp;&nbsp;&nbsp;搜索&nbsp;&nbsp;&nbsp;</button>
				</div>
			</div>
		</div>
		<div class="ibox-content">
			<div class="jqGrid_wrapper form-group">
				<table id="table_list_2"></table>
				<div id="pager_list_2"></div>
			</div>
			<div class="row form-group">
				<div class="col-xs-1">客户姓名:</div>
				<div class="col-xs-3 ">
					<div class="col-xs-13" id="custNameParent">
					<select class="input-sm form-control" id="select_custName" name="custName">
					<c:if test="${loanAgent.custName !=null}">
					<option value="${loanAgent.custName }">${loanAgent.custName }</option>
					</c:if>
					</select>
					</div>
				</div>
				<div class="col-xs-1">客户电话:</div>
				<div class="col-xs-3">
					<input type="text" class="input-sm form-control" id="txt_custPhone" name="custPhone" placeholder="客户电话" value="${loanAgent.custPhone }">
				</div>
				<shiro:hasPermission name="TRADE.LOAN.SUBMIT.BELONG">
				<div class="col-xs-1 control-label">案件归属</div>
				<div class="col-sm-3">
				
					<input type="text" id="executorName" name="executorName" style="width: 50%;background-color:#FFFFFF;" class="form-control tbspuser" 
						hVal="${loanAgent.executorId}" value="${loanAgentName}" readonly="readonly"
						onclick="userSelect({startOrgId:'${orgId}',expandNodeId:'${orgId}',
						nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" />

				</div>
				</shiro:hasPermission>
			</div>
			<div class="row form-group">
				<div class="col-xs-1">状态跟进:</div>
				<div class="col-xs-3"><aist:dict id="select_applyStatus" name="applyStatus" clazz="form-control"
						display="select" hasEmpty="false" dictType="yu_eplus_status" defaultvalue="${loanAgent.applyStatus }"
						ligerui='none'></aist:dict>
				</div>
				<div class="col-xs-1">产品部确认状态:</div>
				<div class="col-xs-3">${loanAgent.confirmStatus }
				</div>
				
				<div class="col-xs-1 control-label">贷款机构</div>
				<div class="col-sm-3">
					<select class="form-control m-b chosen-select" name="finOrgCode" id="finOrgCode">
					</select>
				</div>
			</div>
			<div id="loan_div">
			<div class="row form-group loan_1 loan_2 loan_3">
				<div class="col-xs-1 showstar">申请金额:</div>
				<div class="col-xs-3">
					<div class="input-group">
					<input type="text"
						class="input-sm form-control" id="txt_loanAmount" name="loanAmount" value="${loanAgent.loanAmount!=null? loanAgent.loanAmount/10000 :loanAgent.loanAmount}">
						<span class="input-group-addon">万</span>
						</div>
				</div>
				<div class="col-xs-1 showstar">申请时间:</div>
				<div class="col-xs-3">
					<div class="input-group date">
						<span class="input-group-addon"><i class="fa fa-calendar" style="z-index:2100;position:relative;"></i></span>
						<input class="form-control" type="text" id="txt_applyTime" name="applyTime" value="<fmt:formatDate value="${loanAgent.applyTime }" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
				<div class="col-xs-1 showstar">申请期数:</div>
				<div class="col-xs-3">
				<div class="input-group"><input type="text"
						class="input-sm form-control" id="txt_month" name="month" value="${loanAgent.month }">
						<span class="input-group-addon">月</span>
						</div>
				</div>
			</div>
			<div class="row form-group loan_2">
				<div class="col-xs-1 showstar">面签金额:</div>
				<div class="col-xs-3">
					<div class="input-group"><input type="text"
						class="input-sm form-control" id="txt_signAmount" name="signAmount" value="${loanAgent.signAmount!=null?loanAgent.signAmount/10000 :loanAgent.signAmount}">
						<span class="input-group-addon">万</span>
						</div>
				</div>
				<div class="col-xs-1 showstar">面签时间:</div>
				<div class="col-xs-3">
					<div class="input-group date">
						<span class="input-group-addon"><i class="fa fa-calendar" style="z-index:2100;position:relative;"></i></span>
						<input class="form-control" type="text" id="txt_signTime" name="signTime" value="<fmt:formatDate value="${loanAgent.signTime }" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
			</div>
			<div class="row form-group loan_3">
				<div class="col-xs-1 showstar">放款金额:</div>
				<div class="col-xs-3">
					<div class="input-group">
					<input type="text"
						class="input-sm form-control" id="txt_actualAmount" name="actualAmount" value="${loanAgent.actualAmount!=null?loanAgent.actualAmount/10000 :loanAgent.actualAmount}">
						<span class="input-group-addon">万</span>
						</div>
				</div>
				<div class="col-xs-1 showstar">放款时间:</div>
				<div class="col-xs-3">
					<div class="input-group date">
						<span class="input-group-addon"><i class="fa fa-calendar" style="z-index:2100;position:relative;"></i></span>
						<input class="form-control" type="text" id="txt_releaseTime" name="releaseTime" value="<fmt:formatDate value="${loanAgent.releaseTime }" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
			</div>
			</div>
			<div class="row form-group">
				<div class="col-xs-1">对帐时间:</div>
				<div class="col-xs-3">
				<fmt:formatDate value="${loanAgent.incomeConfirmTime }" pattern="yyyy-MM-dd"/>
				</div>
				<div class="col-xs-1">结账时间:</div>
				<div class="col-xs-3">
					<fmt:formatDate value="${loanAgent.incomeArriveTime }" pattern="yyyy-MM-dd"/>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-xs-1">转介人姓名:</div>
				<div class="col-xs-3">
					<input type="text"
						class="input-sm form-control" id="txt_zjName" name="zjName" value="${loanAgent.zjName }">
				</div>
				<div class="col-xs-1">转介人员工编号:</div>
				<div class="col-xs-3">
					<input type="text"
						class="input-sm form-control" id="txt_zjCode" name="zjCode" value="${loanAgent.zjCode }">
				</div>
			</div>
			<div class="row form-group">
				<div class="col-xs-1">合作人姓名:</div>
				<div class="col-xs-3">
					<input type="text"
						class="input-sm form-control" id="txt_coName" name="coName" value="${loanAgent.coName }">
				</div>
				<div class="col-xs-1">合作人员工编号:</div>
				<div class="col-xs-3">
					<input type="text"
						class="input-sm form-control" id="txt_coCode" name="coCode" value="${loanAgent.coCode }">
				</div>
				<div class="col-xs-1">分配比率:</div>
				<div class="col-xs-3">
					<div class="input-group">
					<input type="text"
						class="input-sm form-control" id="txt_awardPer" name="awardPer" value="${loanAgent.awardPer }">
						<span class="input-group-addon">%</span>
						</div>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-xs-1">产品部合作人:</div>
				<div class="col-xs-3">
					<select id="sel_pdCode" name="pdCode" class="form-control"
								style="width: 200px">
								<option value="">请选择</option>
								<c:forEach items="${yuCuiProduct}" var="yuCuiUser">	
									<c:if test="${ yuCuiUser.employeeCode==loanAgent.pdCode}">
										<option value="${yuCuiUser.employeeCode }" selected="selected">${yuCuiUser.realName }</option>
									</c:if>
									<c:if test="${ yuCuiUser.employeeCode!=loanAgent.pdCode}">
										<option value="${yuCuiUser.employeeCode }">${yuCuiUser.realName }</option>
									</c:if>
									
								</c:forEach>
							</select>
							<input type="hidden" name="pdName" id="txt_pdName" value="${loanAgent.pdName }">
				</div>
				<div class="col-xs-1">分成比率:</div>
				<div class="col-xs-3">
					<div class="input-group">
					<aist:dict id="select_pdPar" name="pdPar" clazz="form-control"
						display="select" hasEmpty="false" dictType="pd_par" defaultvalue="${loanAgent.pdPar }"
						ligerui='none'></aist:dict>
						</div>
				</div>
			</div>
			<div class="row form-group">
				<button type="button" class="btn btn-sm btn-primary"
						id="btn_save">&nbsp;&nbsp;&nbsp;保存&nbsp;&nbsp;&nbsp;</button>
			</div>
			<div class="row form-group">
				<div class="jqGrid_wrapper ">
					<table id="table_list_3"></table>
					<div id="pager_list_3"></div>
				</div>
			</div>
		</div>

	</div>
</form>
<content tag="local_script"> 


<!-- jqGrid --> 
<script	src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script> 
<script	src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
<script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
<script src="<c:url value='/js/jquery.editable-select.min.js' />"></script>


<script> 
    var appCtx={};
	<c:forEach items="${appCtxList}" var="app">
	     appCtx['${app.appName}'] = '${app.genAbsoluteUrl()}';
	</c:forEach>
</script>
<script type="text/javascript">
	
	function openDialog(option){
	   	var query = $; var dailogOption = option.dialog;
	   	if(!dailogOption.data){dailogOption.data = {};}
	   	if (self != top) { query = parent.$; dailogOption.data.frameId = window.frameElement.id; }
	   	if(option.callBack){ var callBack = option.callBack; dailogOption.data.callBack = callBack; }
	   	query.ligerDialog.open(dailogOption); 
	}
	
	function closeDialog(options){
	  	var dialog = frameElement.dialog; 
	  	var dialogData = dialog.get("data");
	 	if(dialogData.callBack){ 
	 		var userDefinedCallback ;
	        if(options && options.data){ userDefinedCallback = dialogData.callBack+'(\''+JSON.stringify(options.data)+'\')'; }else{ userDefinedCallback = dialogData.callBack+"()"; }
		   	if(dialogData.frameId){ parent.document.getElementById(dialogData.frameId).contentWindow.eval(userDefinedCallback); }else{ parent.eval(userDefinedCallback); }
	    }
	  	dialog.close();
	}
	
	//操作提示	
	$(function(){ $.extend({notice : function(msg, type) {if (type == null) {type = "success";}if ($("#msgNotice").length > 0) {$("#msgNotice").removeClass("alert-success alert-info alert-warning alert-error");} else {$('<div class="alert notice" id="msgNotice"/>').appendTo($("body"));}$("#msgNotice").addClass("alert-" + type);$("#msgNotice").text(msg).fadeIn('slow').fadeOut(3000);}});});
	$(function(){ try{$("body").bind("click",function(){ if(parent.menu){parent.menu.closeMenu();} });}catch(e){}});
</script>

<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>

<script>
var ctx="${ctx}";
var postData,caseList;
var starHtml="<span class=\"star\">*</span>";
var loanDiv=$('#loan_div');
var flagList={'1':2,'2':(4|2),'3':(8|2)};

function fcheck(){
	var flag=0;
	var f=flagList[$("#select_applyStatus").val()];
	if(typeof(f)!='undefined' ) {
		flag=f;
	}
	if($("#executorName")&&$("#executorName").val()==''){
		window.wxc.alert('请选择归属人');
		return false;
	}
	if($("#select_loanSrvCode").val()==''){
		window.wxc.alert('请选择产品类型');
		return false;
	}
	if($("#select_applyStatus").val()==''){
		window.wxc.alert('请选择跟进状态');
		return false;
	}
	if((flag&2)!=0){
		if($("#txt_loanAmount").val()==''||$("#txt_loanAmount").val()==0){
			window.wxc.alert('请输入 申请金额');
			return false;
		}
		if($("#txt_applyTime").val()==''){
			window.wxc.alert('请输入 申请时间');
			return false;
		}
		if($("#txt_month").val()==''&&$("#txt_month").val()==0){
			window.wxc.alert('请输入期数');
			return false;
		}
	}
	if((flag&4)!=0){
		if($("#txt_signAmount").val()==''||$("#txt_signAmount").val()==0){
			window.wxc.alert('请输入 面签金额');
			return false;
		}
		if($("#txt_signTime").val()==''){
			window.wxc.alert('请输入 面签时间');
			return false;
		}
	}
	if((flag&8)!=0){
		if($("#txt_actualAmount").val()==''||$("#txt_actualAmount").val()==0){
			window.wxc.alert('请输入 放款金额');
			return false;
		}
		if($("#txt_releaseTime").val()==''){
			window.wxc.alert('请输入放款时间');
			return false;
		}
	}

	return true;
}
			
function loanStatusChange() {
	var loanStatus=	$(this).val();
	loanDiv.find('.showstar').find(".star").remove();
	if(!!loanStatus&&loanStatus>0&&loanStatus<=3){
		loanDiv.find(".loan_"+loanStatus).find('.showstar').append($(starHtml));
	}
}
			
$(document).ready(function() {
	
	$('#select_custName').editableSelect({
		effects: 'slide',
		filter: false
	});
	
	$('#select_applyStatus').change(loanStatusChange);
	initGrid();
	var pkid= $("#pkid").val();
	if(pkid){
		initStatusChangeGrid();
	}
	getBankList('');
	$("#sel_pdCode").change(function(){
		$("#txt_pdName").val($("#sel_pdCode").find("option:selected").text());
	});
	$("#btn_search").click(function(){
		postData.search_addr=$("#txt_address").val();
		postData.search_caseCode='';
		caseList.jqGrid("setGridParam", { postData: postData,datatype:'json',mtype:'POST' }).trigger("reloadGrid");
	});
	var caseCode=$('#caseCode').val();
	if(caseCode&&caseCode!=''){
		postData.search_caseCode=caseCode;
		postData.search_addr='';
		caseList.jqGrid("setGridParam", { postData: postData,datatype:'json',mtype:'POST' }).trigger("reloadGrid");
	}
					
	$("#btn_save").click(function(){
		if(!fcheck())return;
		
		window.wxc.confirm("是否保存？",{"wxcOk":function(){
		$("#executorId").val($("#executorName").attr('hVal'));
		var fData=$("#f_main").serialize();
		$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    	$(".blockOverlay").css({'z-index':'9998'});
		$.ajax({
			cache:false,
			async:true,
			type:"POST",
			url:ctx+"/loan/doSubmit",
			dataType:'json',
			data:fData,
			success:function(data){
				if(1==data.result){
					window.wxc.success('保存成功');
					parent.$.fancybox.close();
				}else if (2==data.result){
					window.wxc.error('当前案件上该产品已有人('+data.optUser+')填报。请误重复填报,如有问题联系'+data.optUser+'解决');
				}else if(3==data.result){
					window.wxc.error('当前案件上项目不存在,请联系案件负责人');
				}
			$.unblockUI();
			}
		});
		}});
	});
	
	$('.input-group.date').datepicker({
		todayBtn: "linked",
		keyboardNavigation: false,
		forceParse: false,
		calendarWeeks: true,
		autoclose: true
	});
	
});
			
			
function doBindCase(c){
	$("#caseCode").val(c);
	$("button[name='btn_buld']").text('绑定');
	$("button[data='"+c+"']").text('已绑定');
	getCustomerNameAndTel(c);
	cancelBubble();
	return false;
}
			
			
/*绑定时获取客户名和客户电话号码并生成下拉框*/
function getCustomerNameAndTel(case_code){
				
	$.ajax({
		url:ctx+"/loan/getCaseDetails",
		method:"post",
		dataType:"json",
		data:{"caseCode":case_code},
		success:function(data){
			var cusPhone = $('#txt_custPhone');
			var custNameParent = $('#custNameParent');
			var txt='<select class="input-sm form-control" id="select_custName" name="custName">';
						
			$.each(data, function(i, item){
				if(i==0) {
					txt +=  "<option value='"+ item.guestName+"' selected>" + item.guestName+"</option>";
					cusPhone.val(data[i].guestPhone);
				} else {
					txt +=  "<option value='"+ item.guestName+"'>" + item.guestName+"</option>";
				} 
			});
			custNameParent.empty().append(txt+'</select>');	
						
			$('#select_custName').editableSelect({
				effects: 'slide',
				filter: false,
				onSelect: function (element) {
					var myIndex = $(element).index();
					if(myIndex>=0){
						cusPhone.val(data[myIndex].guestPhone);
					}
				}
			});
		}
	});				
}

			/*获取银行列表*/
			function getBankList(pcode){
				var friend = $("#finOrgCode");
				friend.empty();
				 $.ajax({
				    url:ctx+"/manage/queryFin",
				    method:"post",
				    dataType:"json",
				    data:{"pcode":pcode},
			    	success:function(data){
			    		if(data.bankList != null){
			    			for(var i = 0;i<data.bankList.length;i++){
			    				if(data.bankCode == data.bankList[i].finOrgCode) {
			    					friend.append("<option value='"+data.bankList[i].finOrgCode+"' selected='selected'>"+data.bankList[i].finOrgName+"</option>");
			    				} else {
			    					friend.append("<option value='"+data.bankList[i].finOrgCode+"'>"+data.bankList[i].finOrgName+"</option>");
			    				}
			    			}
			    			friend.find("option[value='${loanAgent.finOrgCode }']").attr("selected",true);
			    		}
			    	}
				  });
			}
			
			function initGrid(){
				postData={queryId : "caseListWhithLoan"};
				caseList = $("#table_list_2").jqGrid(
					{datatype:'local',
					url :ctx + "/quickGrid/findPage",
					height : 92,
					autowidth : true,
					shrinkToFit : true,
					rowNum : 2,
					colNames : [ '案件编号','产证地址','经纪人', '交易顾问(主办)',
					             '交易顾问(电话)', '上家姓名','下家姓名','案件状态','操作' ],
					colModel : [{ 
							name : 'caseCode',
							index : 'caseCode',
							width : 90
						},{name : 'addr',
							index : 'addr',
							width : 90
						},{ 
							name : 'agent',
							index : 'agent',
							width : 90
						},{
							name : 'consultant',
							index : 'consultant',
							width : 90
						},{
							name : 'phone',
							index : 'phone',
							width : 150,
						},{
							name : 'seller',
							index : 'seller',
							width : 60
						},{
							name : 'buyer',
							index : 'buyer',
							width : 60
						},{ 
							name : 'caseProperty',
							index : 'caseProperty',
							width : 90
						},{	width:60,
							formatter : function(cellvalue,options,rawObject) {
								var btn2 = "<button name='btn_buld' data='"+rawObject.caseCode+"'onclick=\"doBindCase(\'"
								+ rawObject.caseCode
								+ "\');\" class='btn red' >"+($('#caseCode').val()==rawObject.caseCode?"已绑定":"绑定")+" </button>";
								return btn2;
							}
						}
					],
					pager : "#pager_list_2",
					viewrecords : true,
					pagebuttions : true,
					hidegrid : false,
					recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
					pgtext : " {0} 共 {1} 页",
					postData :postData
				});
			}
			function initStatusChangeGrid(){
				var postData1={queryId : "loanStatusChangeQuery",search_loanId:$("#pkid").val()};
				$("#table_list_3").jqGrid(
					{datatype : "json",
					url :ctx + "/quickGrid/findPage",
					height : 92,
					autowidth : true,
					shrinkToFit : true,
					mtype:"POST",
					rowNum : 4,
					colNames : [ '状态(从)','状态(到)', '更改时间',
					             '产品部确认状态'],
					colModel : [{name : 'stFrom',
						index : 'stFrom',
						width : 90
						},{ 
							name : 'stTo',
							index : 'stTo',
							width : 90
						},{
							name : 'changeDate',
							index : 'changeDate',
							width : 90
						},{
							name : 'isConfirm',
							index : 'isConfirm',
							width : 150,
						}
					],
					pager : "#pager_list_3",
					viewrecords : true,
					pagebuttions : true,
					hidegrid : false,
					recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
					pgtext : " {0} 共 {1} 页",
					postData :postData1
				});
			}
			
//得到事件
function getEvent() {
     if(window.event)    {return window.event;}
     func=getEvent.caller;
     while(func!=null){
         var arg0=func.arguments[0];
         if(arg0){
             if((arg0.constructor==Event || arg0.constructor ==MouseEvent
                || arg0.constructor==KeyboardEvent)
                ||(typeof(arg0)=="object" && arg0.preventDefault
                && arg0.stopPropagation)){
                 return arg0;
             }
         }
         func=func.caller;
     }
     return null;
}
			
//阻止冒泡
function cancelBubble() {
    var e=getEvent();
    if(window.event){
        e.returnValue=false;//阻止自身行为
        e.cancelBubble=true;//阻止冒泡
     }else if(e.preventDefault){
        e.preventDefault();//阻止自身行为
        e.stopPropagation();//阻止冒泡
     }
}

function selectUserBack(array){
	if(array && array.length >0){
        $("#executorName").val(array[0].username);
		$("#executorName").attr('hVal',array[0].userId);
	}else{
		$("#executorName").val("");
		$("#executorName").attr('hVal',"");
	}
}

</script>
</content>
</body>
</html>



