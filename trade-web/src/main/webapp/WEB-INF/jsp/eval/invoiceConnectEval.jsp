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
<%-- <script src="<c:url value='/js/trunk/case/caseBaseInfo.js' />"></script> --%>
<%-- <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script> --%>
<script type="text/javascript">
	//记录案件视图跳转等所需变量
	var caseCode="${caseCode}";
	var aa='aa';
	var teamProperty = "${teamProperty}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";
	var taskId="${taskId}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
<script type="text/javascript">
var AttachmentList = (function(){    
    return {    
       init : function(ctx,url,gridTableId,gridPagerId,ctmCode,caseCode,postData){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'POST',
    			datatype : "json",
    			height : 230,
    			autowidth : true,
    			shrinkToFit : true,
    			rowNum : 4,
    			/*   rowList: [10, 20, 30], */
    			colNames : [ '案件编号','评估单编号','产权地址','评估公司','评估值','评估费实收','买方姓名','贷款权证','经纪人','操作'],
    			colModel : [ {
    				name : 'CASE_CODE',
    				index : 'CASE_CODE',
    				align : "center",
    				width : 50,
    				resizable : true
    			},{
    				name : 'EVA_CODE',
    				index : 'EVA_CODE',
    				align : "center",
    				width : 50,
    				resizable : true
    			}, {
    				name : 'PROPERTY_ADDR',
    				index : 'PROPERTY_ADDR',
    				align : "center",
    				width : 100,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'FIN_ORG_NAME',
    				index : 'FIN_ORG_NAME',
    				align : "center",
    				width : 25,
    				resizable : true
    			}, {
    				name : 'EVA_PRICE',
    				index : 'EVA_PRICE',
    				align : "center",
    				width : 25,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'EVAL_REAL_CHARGES',
    				index : 'EVAL_REAL_CHARGES',
    				align : "center",
    				width : 25,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'GUEST_NAME',
    				index : 'GUEST_NAME',
    				align : "center",
    				width : 25,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'LOAN',
    				index : 'LOAN',
    				align : "center",
    				width : 25,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'AGENT',
    				index : 'AGENT',
    				align : "center",
    				width : 25,
    				resizable : true
    				//formatter : linkhouseInfo
    			}, {
    				name : 'OPERATION',
    				index : 'OPERATION',
    				align : "center",
    				width : 25,
    				resizable : true
    				//formatter : linkhouseInfo
    			}
				],
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
	    				var link = "<button  class='btn red' onclick='linkCase(\""+rowDatas['EVA_CODE']+"\")'>关联案件</a>";    				    				    				
	    				jQuery("#"+gridTableId).jqGrid('setRowData', ids[i], { OPERATION: link});
    				}
    			},
    			/* postData : {
    				queryId : "queryConnectEvalReport",
    				caseCode : caseCode
    			} */
    			postData : postData
    		});
       }   
    };    
})(); 
</script>
<script type="text/javascript">
//关联评估单
function linkCase(evaCode){
	//console.log(evaCode);
	window.close();
}
//显示附件图片
function showAttachment(url){
	window.open(url);
	
}
//提交数据
function submit() {	
	var caseCode=$("#caseCode").val();
	if(!caseCode){
	 window.wxc.alert("请以正确的方式进入该页面！");
	return;		
	}
	save(true);
}

//保存数据
function save(b) {
	var caseCode=$("#caseCode").val();
	if(!caseCode){
	 window.wxc.alert("请以正确的方式进入该页面！");
	return;		
	}
	
	if(b){
		if (!checkForm()) {
			return;
		}													
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
        success: function(data){
            $.unblockUI();
            console.log(data);
            if (b) {
                if (data.message) {
                    window.wxc.alert("提交成功"+data.message);
                }
                var ctx = $("#ctx").val();
                window.location.href=ctx+ "/task/myTaskList";
            }else{
            	if (data.message) {
                    window.wxc.alert("提交成功"+data.message);
                }
            }
        },
        error:function(){
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
	obj.value = obj.value.replace(".", "$#$").replace(/\./g, "")
			.replace("$#$", ".");
}

//验证控件checkUI();
function checkForm() {
	if($("#distCode").val()==0){
		window.wxc.alert("区域为必选项!");
		$("#distCode").focus();
		$("#distCode").css("border-color","red");
		return false;
	}	
		return true;
	}

	$("input[type='text'],select").focus(function() {
		$(this).css("border-color", "rgb(229, 230, 231)");
	});

</script>
<style type="text/css">
.btn_blue {
    background-color: #428fcc !important;
    color: #fff;
}
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
<title>评估发票申请</title>
<content tag="pagetitle">评估发票申请</content>
</head>
<body>
        <div class="ibox-content border-bottom clearfix space_box noborder marginbot">
        <h2 class="newtitle-big">评估单列表</h2>
        	
        <div class="ibox-content border-bottom clearfix space_box">
				<form id="jvForm" method="get" class="form_list" >
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">评估公司：</label> <input
							type="text" class="input_type yuanwid" id="evaCompany"
							name="evaCompany"  >
					</div>		
					
					<div class="form_content">
						<label class="control-label sign_left_small">案件编号：</label> <input
							type="text" class="input_type yuanwid" id="caseCode" value="${caseCode}"
							name="caseCode" readonly="readonly" >
					</div>	
					
					<div class="form_content">
						<label class="control-label sign_left_small">产证地址：</label> <input
							type="text" class="input_type yuanwid" id="propertyAddr"
							name="propertyAddr"  >
					</div>				
				</div>
				
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">评估编号：</label> <input
							type="text" class="input_type yuanwid" id="evaCode"
							name="evaCode"  >
					</div>		
					
					<div class="form_content">
						<label class="control-label sign_left_small">买方姓名：</label> <input
							type="text" class="input_type yuanwid" id="guestName"
							name="guestName"  >
					</div>	
					
					<div class="form_content">
						<label class="control-label sign_left_small">经纪人：</label> <input
							type="text" class="input_type yuanwid" id="agentName"
							name="agentName"  >
					</div>				
				</div>
				
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">贷款权证：</label> <input
							type="text" class="input_type yuanwid" id="loanName"
							name="loanName"  >
					</div>		
					
					<div class="form_content">
						
					</div>	
					
					<div class="form_content">
						<!-- <button id="reloadButton" onclick="initMyJqgrid()" type="button" class="btn btn_blue">
                           	 	<i class="icon iconfont">&#xe635;</i>查询
                             </button> -->
                             <button id="reloadButton" type="button" class="btn btn_blue">
                           	 	<i class="icon iconfont">&#xe635;</i>查询
                             </button>
					</div>				
				</div>

					<div class="form_content space">
                         <div class="add_btn" align="center" id="queryFilter">
                         </div>
                   	</div>
                     
				</form>
	  </div>
       
        	            
	            <div class="view-content">
	              	<table id="gridTable" class=""></table>
	   				<div id="gridPager"></div>
	            </div>	            
	            <div class="form-btn">
	                    <div class="text-center">
	                        <button  class="btn btn-success btn-space" onclick="javascript:window.close()" id="btnSave">关闭</button>
	                         <button class="btn btn-success btn-space" onclick="submit()">提交</button>
	                    </div>
	                </div>
	            </div>
			
	
			<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
			<content tag="local_script"> 
				<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
				<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
				<%-- <script src="<c:url value='/transjs/task/loanlostApprove.js' />"></script> --%>
				<%-- <script src="<c:url value='/transjs/task/showAttachment.js' />"></script>  --%>
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
				<%-- <script src="<c:url value='/js/stickUp.js' />"></script> --%>
				<!-- 改版引入的新的js文件 -->
				<script src="<c:url value='/js/common/textarea.js' />"></script>
				<%-- <script src="<c:url value='/js/common/common.js' />"></script> --%>
				<script>
					$(document).ready(function(){
						var ctx = $("#ctx").val();
/* 						if(!$("#caseCode").val()){
							$("#caseCode").val("ZY-TJ-2017080038");						
						} */
						//var caseCode=$("#caseCode").val();
						var postData={};
						postData.queryId="queryConnectEvalReport";
						postData.caseCode=caseCode;
						AttachmentList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager','${ctmCode}',caseCode,postData);
						/* $("#caseCommentList").caseCommentGrid({
							caseCode : caseCode,
							srvCode : 'caseRecvFlow'
						}); */
						//日历控件
					    $('.input-daterange').datepicker({
					        keyboardNavigation: false,
					        forceParse: false,
					        autoclose: true
					    });
						
					//设置div显示或隐藏
					function isShow(divName, stats) {
					    var div_array = document.getElementsByName(divName);   
					    for(i=0;i<div_array.length;i++)  
					    {  
						    div_array[i].style.display = stats; 
					    }  
					}
			      
		            $("[name=businessLoanWarn]").click(function(){
		                if($(this).val()=='1'){
		                    $("#divContent").css("display","inherit");
		                }else{
		                    $("#divContent").css("display","none");
		                }
		            });
		            
					//日期组件
			        $('#invoiceApplyTime').datepicker({
			            todayBtn: "linked",
			            keyboardNavigation: false,
			            forceParse: false,
			            calendarWeeks: false,
			            autoclose: true
			        });
					
			      //日期组件
			        $('#invoiceFinishTime').datepicker({
			            todayBtn: "linked",
			            keyboardNavigation: false,
			            forceParse: false,
			            calendarWeeks: false,
			            autoclose: true
			        });
		            
			  	  $('#reloadButton').on('click', function() {     //页面上的button按钮的click事件，重新获取参数后发送参数，然后重新加载数据。  
			 		  //获取输入框内容  
			  		var caseCode=$('#caseCode').val();
			  		var propertyAddr=$('#propertyAddr').val();
			  		var evaCode=$('#evaCode').val();
			  		var guestName=$('#guestName').val();
			  		var agentName=$('#agentName').val();
			  		var loanName=$('#loanName').val();
			  		var evaCompany=$('#evaCompany').val();
			  		var postData={};
			  		if(caseCode){
			  			postData.caseCode=caseCode;
			  		}else{
			  			postData.caseCode='';
			  		}
			  		if(propertyAddr){
			  			postData.propertyAddr=propertyAddr;
			  		}else{
			  			postData.propertyAddr='';
			  		}
			  		if(evaCode){
			  			postData.evaCode=evaCode;
			  		}else{
			  			postData.evaCode='';
			  		}
			  		if(guestName){
			  			postData.guestName=guestName;
			  		}else{
			  			postData.guestName='';
			  		}
			  		if(agentName){
			  			postData.agentName=agentName;
			  		}else{
			  			postData.agentName='';
			  		}
			  		if(loanName){
			  			postData.loanName=loanName;
			  		}else{
			  			postData.loanName='';
			  		}
			  		if(evaCompany){
			  			postData.evaCompany=evaCompany;
			  		}else{
			  			postData.evaCompany='';
			  		}
			  		
			  		postData.queryId="queryConnectEvalReport";
			 		 
			 	        $("#gridTable").jqGrid('setGridParam',{  
			 	        	postData :postData //发送数据  	             
			 	        }).trigger("reloadGrid"); //重新载入  
			 	});
					})//end ready function
					
				</script> 
			</content>
	</body>
</html>
