<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

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
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"
	rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">

<style type="text/css">
#table_list_2>tbody>tr>td{
	text-align: center;
}
#select_loanSrvCode {
 readonly : readonly;
}
body  {
background-color : #ffffff;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />

	<input type="hidden" id="caseCode" name="caseCode" value="${loanAgent.caseCode }" />
	<input type="hidden" id="finCaseCode" name="finCaseCode" value="${loanAgent.finCaseCode }" />
	<input type="hidden" id="pkid" name="pkid" value="${loanAgent.pkid }" />
<!-- 	<div class="ibox"> -->
	<%-- 	<div class="ibox-title">
			<div class="row">
				<div class="col-lg-1 control-label">
					产品类型
				</div>
				<div class="col-lg-5">
					<aist:dict id="select_loanSrvCode" name="loanSrvCode" clazz="form-control"
						display="select"  dictType="yu_serv_cat_code_tree" tag="eplus" 
						ligerui='none' defaultvalue="${loanAgent.loanSrvCode }"></aist:dict>
				</div>
				<div class="col-lg-1" style="text-align: right;">
					<button type="button" class="btn btn-sm btn-primary"
						id="btn_search">&nbsp;&nbsp;&nbsp;搜索&nbsp;&nbsp;&nbsp;</button>
				</div>
			</div>
		</div> --%>
	  <div class="ibox-content">
			<!-- <div class="jqGrid_wrapper form-group">
				<table id="table_list_2"></table>
				<div id="pager_list_2"></div>
			</div> -->
			<div class="row form-group">
				<div class="col-lg-1">客户姓名:</div>
				<div class="col-lg-3">
					<input type="text"
						class="input-sm form-control" id="txt_custName" name="custName" value="${loanAgent.custName }">
				</div>
				<div class="col-lg-1">客户电话:</div>
				<div class="col-lg-3"><input type="text"
						class="input-sm form-control" id="txt_custPhone" name="custPhone" value="${loanAgent.custPhone }">
				</div>
			</div>
			<div class="row form-group">
				<div class="col-lg-1">状态跟进:</div>
				<div class="col-lg-3"><aist:dict id="select_loanSrvCode" name="applyStatus" clazz="form-control"
						display="select" hasEmpty="false" dictType="yu_eplus_status" defaultvalue="${loanAgent.applyStatus }"
						ligerui='none'></aist:dict>
				</div>
				<div class="col-lg-1">产品部确认状态:</div>
				<div class="col-lg-3">${loanAgent.confirmStatus }
				</div>
				
				<div class="col-lg-1 control-label">贷款机构</div>
				<div class="col-sm-3">
					<select class="form-control m-b chosen-select" name="finOrgCode" id="finOrgCode">
					</select>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-lg-1">申请金额:</div>
				<div class="col-lg-3">
					<div class="input-group">
					<input type="text"
						class="input-sm form-control" id="txt_loanAmount" name="loanAmount" value="${loanAgent.loanAmount /10000}">
						<span class="input-group-addon">万</span>
						</div>
				</div>
				<div class="col-lg-1">申请时间:</div>
				<div class="col-lg-3">
					<div class="input-group date">
						<span class="input-group-addon"><i class="fa fa-calendar" style="z-index:2100;position:relative;"></i></span>
						<input class="form-control" type="text" id="txt_applyTime" name="applyTime" value="<fmt:formatDate value="${loanAgent.applyTime }" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
				<div class="col-lg-1">申请期数:</div>
				<div class="col-lg-3">
				<div class="input-group"><input type="text"
						class="input-sm form-control" id="txt_month" name="month" value="${loanAgent.month }">
						<span class="input-group-addon">月</span>
						</div>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-lg-1">面签金额:</div>
				<div class="col-lg-3">
					<div class="input-group"><input type="text"
						class="input-sm form-control" id="txt_signAmount" name="signAmount" value="${loanAgent.signAmount/10000 }">
						<span class="input-group-addon">万</span>
						</div>
				</div>
				<div class="col-lg-1">面签时间:</div>
				<div class="col-lg-3">
					<div class="input-group date">
						<span class="input-group-addon"><i class="fa fa-calendar" style="z-index:2100;position:relative;"></i></span>
						<input class="form-control" type="text" id="txt_signTime" name="signTime" value="<fmt:formatDate value="${loanAgent.signTime }" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-lg-1">放款金额:</div>
				<div class="col-lg-3">
					<div class="input-group">
					<input type="text"
						class="input-sm form-control" id="txt_actualAmount" name="actualAmount" value="${loanAgent.actualAmount/10000 }">
						<span class="input-group-addon">万</span>
						</div>
				</div>
				<div class="col-lg-1">放款时间:</div>
				<div class="col-lg-3">
					<div class="input-group date">
						<span class="input-group-addon"><i class="fa fa-calendar" style="z-index:2100;position:relative;"></i></span>
						<input class="form-control" type="text" id="txt_releaseTime" name="releaseTime" value="<fmt:formatDate value="${loanAgent.releaseTime }" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-lg-1">对帐时间:</div>
				<div class="col-lg-3">
				<fmt:formatDate value="${loanAgent.incomeConfirmTime }" pattern="yyyy-MM-dd"/>
				</div>
				<div class="col-lg-1">结账时间:</div>
				<div class="col-lg-3">
					<fmt:formatDate value="${loanAgent.incomeArriveTime }" pattern="yyyy-MM-dd"/>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-lg-1">转介人姓名:</div>
				<div class="col-lg-3">
					<input type="text"
						class="input-sm form-control" id="txt_zjName" name="zjName" value="${loanAgent.zjName }">
				</div>
				<div class="col-lg-1">转介人员工编号:</div>
				<div class="col-lg-3">
					<input type="text"
						class="input-sm form-control" id="txt_zjCode" name="zjCode" value="${loanAgent.zjCode }">
				</div>
			</div>
			<div class="row form-group">
				<div class="col-lg-1">合作人姓名:</div>
				<div class="col-lg-3">
					<input type="text"
						class="input-sm form-control" id="txt_coName" name="coName" value="${loanAgent.coName }">
				</div>
				<div class="col-lg-1">合作人员工编号:</div>
				<div class="col-lg-3">
					<input type="text"
						class="input-sm form-control" id="txt_coCode" name="coCode" value="${loanAgent.coCode }">
				</div>
				<div class="col-lg-1">分配比率:</div>
				<div class="col-lg-3">
					<div class="input-group">
					<input type="text"
						class="input-sm form-control" id="txt_awardPer" name="awardPer" value="${loanAgent.awardPer }">
						<span class="input-group-addon">%</span>
						</div>
				</div>
			</div>
			<div class="row form-group">
				<div class="col-lg-1">产品部合作人:</div>
				<div class="col-lg-3">
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
				<div class="col-lg-1">分成比率:</div>
				<div class="col-lg-3">
					<div class="input-group">
					<aist:dict id="select_pdPar" name="pdPar" clazz="form-control"
						display="select" hasEmpty="false" dictType="pd_par" defaultvalue="${loanAgent.pdPar }"
						ligerui='none'></aist:dict>
						</div>
				</div>
			</div>
		</div>


	</script>
	<content tag="local_script"> <!-- jqGrid --> 
	<script	src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script> 
	<script	src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<script>
			var ctx="${ctx}";
			var postData,caseList;
			$(document).ready(
				function() {
					//initGrid();
					getBankList('');
					$("#sel_pdCode").change(function(){
						$("#txt_pdName").val($("#sel_pdCode").find("option:selected").text());
					});
					$("#btn_search").click(function(){
						postData.search_addr=$("#txt_address").val();
						postData.search_caseCode='';
						caseList.jqGrid("setGridParam", { postData: postData,datatype:'json', }).trigger("reloadGrid");
					});
					/* var caseCode=$('#caseCode').val();
					if(caseCode&&caseCode!=''){
						postData.search_caseCode=caseCode;
						postData.search_addr='';
						caseList.jqGrid("setGridParam", { postData: postData,datatype:'json', }).trigger("reloadGrid");
					} */
					$("#btn_save").click(function(){
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
									window.wxc.alert('保存成功');
									parent.$.fancybox.close();
								}else if (2==data.result){
									window.wxc.error('当前案件上该产品已有人('+data.optUser+')填报。请误重复填报,如有问题联系'+data.optUser+'解决');
								}else if(3==data.result){
									window.wxc.error('当前案件上项目不存在,请联系案件负责人');
								}
								$.unblockUI();
							}
						});
					});
					$('.input-group.date').datepicker({
						todayBtn: "linked",
			            keyboardNavigation: false,
			            forceParse: false,
			            calendarWeeks: true,
			            autoclose: true
			         });
				}
			);
			
			function doBindCase(c){
				$("#caseCode").val(c);
				$("button[name='btn_buld']").text('绑定');
				$("button[data='"+c+"']").text('已绑定');
				cancelBubble();
				return false;
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
					colNames : [ '物业地址','经纪人', '交易顾问(主办)',
					             '交易顾问(电话)', '上家姓名','下家姓名','操作' ],
					colModel : [{name : 'addr',
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
			//得到事件
function getEvent(){
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
function cancelBubble()
{
    var e=getEvent();
    if(window.event){
        e.returnValue=false;//阻止自身行为
        e.cancelBubble=true;//阻止冒泡
     }else if(e.preventDefault){
        e.preventDefault();//阻止自身行为
        e.stopPropagation();//阻止冒泡
     }
}
		</script> </content>
</body>
</html>



