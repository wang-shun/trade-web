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
<style type="text/css">
/* 个人样式 */
.title-mark {
  padding-left: 6px!important;
  border-left: 3px solid #00CFEC!important;
  line-height: 18px!important;
}

.line {
  overflow: hidden;
}

.input_type {
  display: inline-block;
  padding: 6px 12px;
  color: inherit;
  font-size: 14px;
  font-family: 'Microsoft Yahei';
/*   background-color: #fff; */
  background-image: none;
  border: 1px solid #e5e6e7;
  border-radius: 0px;
  transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
}
</style>
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
<style>
[class^=mark]{position:absolute;top:8px;left:130px;width:56px;height:37px;z-index:0; background-position:left center;background-repeat:no-repeat}
.mark-baodan{background-image:url(../img/mark-baodan.png);}
.mark-guaqi{background-image:url(../img/mark-guaqi.png);}
.mark-jiean{background-image:url(../img/mark-jiean.png);}
.mark-wuxiao{background-image:url(../img/mark-wuxiao.png);}
.mark-zaitu{background-image:url(../img/mark-zaitu.png);}
.bb {
	white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width:178.15px;
}
.hint {position: relative; display: inline-block;}

.hint:before, .hint:after {
	position: absolute;
	opacity: 0;
	z-index: 1000000;
	-webkit-transition: 0.3s ease;
	-moz-transition: 0.3s ease;
	pointer-events: none;
}		
.hint:hover:before, .hint:hover:after {
	opacity: 1;
}
.hint:before {
	content: '';
	position: absolute;
	background: transparent;
	border: 6px solid transparent;
	position: absolute;
}	
.hint:after {
	content: attr(data-hint);
	background: rgba(0, 0, 0, 0.8);
	color: white;
	padding: 8px 10px;
	font-size: 12px;
	white-space: nowrap;
	box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.3);
}
/* top */
.hint-top:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}
.hint-top:hover:before {
	margin-bottom: -10px;
}
.hint-top:hover:after {
	margin-bottom: 2px;
}
#basicInfo .sign {
	position: absolute;
	width: 55px;
	height: 40px;
	padding-top: 5px;
	color: #fff;
	text-align: center;
	font-family: "Microsoft Yahei";
	font-size: 14px;
}
#basicInfo .top12 .sign-red {
	background: url(${ctx}/static/trans/img/qizi-01-d998d141a485f8229a079346aa6e472c.png) no-repeat;
	top: 0px;
	left: 130px;
	width: 36px;
	height: 35px;
}
#basicInfo .top12 .sign-blue {
	background: url(${ctx}/static/trans/img/qizi-02-99c539708b71e1eeadc726209f6838ea.png) no-repeat;
	top: 0px;
	left: 190px;
	width: 52px;
	height: 35px;
}

#basicInfo .top12 .sign-yellow {
	background: url(${ctx}/static/trans/img/qizi-03-9576a537898fbd4e7491406d35c6482a.png) no-repeat;
	top: 0px;
	left: 265px;
	width: 52px;
	height: 35px;
}
</style>

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
<%-- <link href="<c:url value='/css/common/input.css' />" rel="stylesheet"> --%>
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<!--弹出框样式  -->
<link href="<c:url value='/css/common/xcConfirm.css' />" rel="stylesheet">
<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
<script src="<c:url value='/js/trunk/case/caseBaseInfo.js' />"></script>
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<script type="text/javascript">
//记录案件视图跳转等所需变量
	var caseCode=$("#caseCode").val();
	var coworkService = "${firstFollow.coworkService }";
	var teamProperty = "${teamProperty}";
	var caseProperty = "${firstFollow.caseProperty}";
	var cooperationUser = "${firstFollow.cooperationUser}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";

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
       init : function(ctx,url,gridTableId,gridPagerId,ctmCode,caseCode){    
    	 //jqGrid 初始化
    		$("#"+gridTableId).jqGrid({
    			url : ctx+url,
    			mtype : 'GET',
    			datatype : "json",
    			height : 125,
    			autowidth : true,
    			shrinkToFit : true,
    			rowNum : 3,
    			/*   rowList: [10, 20, 30], */
    			colNames : [ '审批人','审批时间','审批结果','审批意见'],
    			colModel : [ {
    				name : 'OPERATOR',
    				index : 'OPERATOR',
    				align : "center",
    				width : 25,
    				resizable : false
    			},{
    				name : 'OPERATOR_TIME',
    				index : 'OPERATOR_TIME',
    				align : "center",
    				width : 25,
    				resizable : false
    			}, {
    				name : 'NOT_APPROVE',
    				index : 'NOT_APPROVE',
    				align : "center",
    				width : 25,
    				resizable : false
    				//formatter : linkhouseInfo
    			}, {
    				name : 'CONTENT',
    				index : 'CONTENT',
    				align : "center",
    				width : 25,
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
	    				
	    				var auditResult = rowDatas['NOT_APPROVE'];
	    				var auditResultDisplay = null;
	    				if(!auditResult){
	    					auditResultDisplay="审批通过"
	    				}else{
	    					auditResultDisplay=auditResult;
	    				}	    				    				
	    				jQuery("#"+gridTableId).jqGrid('setRowData', ids[i], { NOT_APPROVE: auditResultDisplay});
    				}
    			},
    			postData : {
    				queryId : "queryLoanlostApproveList",
    				caseCode : caseCode
    				//caseCode : 'CaseCode1503653095536'
    			}
    			 
    		});
       }   
    };    
})(); 
</script>
<script type="text/javascript">
//显示附件图片
function showAttachment(url){
	window.open(url);
	
}
//提交数据
function submit() {					
	save(true);
}

//保存数据
function save(b) {
	 if(b){
		if (!checkForm()) {
			return;
		}													
	} 
	
	var jsonData = $("#changeCommForm").serializeArray();

	var url = "${ctx}/eval/submitInvoiceAudit";
	
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
                if (data) {
                    window.wxc.alert("提交成功"+data);
                }
                var ctx = $("#ctx").val();
                window.location.href=ctx+ "/task/myTaskList";
            }else{
            	if (data.message) {
                    window.wxc.alert("提交成功"+data);
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
	var caseCode=$("#caseCode").val();

	if(!$("#changeChargesType").val()){
		window.wxc.alert("调佣类型为必填项!");
		$("#changeChargesType").focus();
		$("#changeChargesType").css("border-color","red");
		return false;
	}
	
	if(!$("#changeChargesCause").val()){
		window.wxc.alert("调佣事由为必填项!");
		$("#changeChargesCause").focus();
		$("#changeChargesCause").css("border-color","red");
		return false;
	}
	
	if(!caseCode){
		window.wxc.alert("缺少caseCode,请以正确的方式进入系统!");
		return false;
	}
	
		return true;
	}

	$("input[type='text'],select").focus(function() {
		$(this).css("border-color", "rgb(229, 230, 231)");
	});

</script>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<%-- <jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include> --%>
<!-- caseBaseInfo -->
<link href="<c:url value='/css/common/subscribe.css' />" rel="stylesheet">
<link href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
<style>
    [v-cloak] {
        display: none;
    }
</style>

<nav id="navbar-example" class="navbar navbar-default navbar-static"
		role="navigation">
		<div id="isFixed" style="position: relative; top: 0px;"
			class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
			<ul class="nav navbar-nav scroll_content">
				<li class="menuItem active"><a href="#basicInfo"> 基本信息 </a></li>
				<li class="menuItem"><a href="#serviceFlow"> 服务流程 </a></li>
				<li class="menuItem"><a href="#aboutInfo"> 相关信息 </a></li>

			</ul>
		</div>
	</nav>
	
<div class="wrapper wrapper-content">
	<div class="row animated fadeInDown">
		<div class="scroll_box fadeInDown animated">
			<div class="top12 panel" id="basicInfo">
			
           		<div v-cloak v-if="caseProperty=='30003001'" class="sign sign-red" >
            		<span >无效</span>
           		</div>
         		<div v-cloak v-if="caseProperty=='30003002'" class="sign sign-red">结案</div>
          		<div v-cloak v-if="caseProperty=='30003009'" class="sign sign-out"> 外单 </div>
          		<div v-cloak v-if="caseProperty=='30003005'" class="sign sign-red ">
           			<span>爆单</span>
          		</div>

           		<div v-cloak v-if="caseProperty==30003003 || caseProperty==30003007 || caseProperty==30003008" class="sign sign-red">在途</div>
           		<div v-cloak class="sign sign-blue">
             		<p v-if="status == '30001001' ">未分单</p>
             		<p v-if="status == '30001002' ">已接单</p>
             		<p v-if="status == '30001003' ">已签约</p>
             		<p v-if="status == '30001004' ">已过户</p>
             		<p v-if="status == '30001005' ">已领证</p>
             		<p v-if="status == '30001006' ">未指定</p>
             		<p v-if="status == '30001007' ">被合流</p>
           		</div>
            		
           		<div v-cloak v-if="caseProperty=='30003004'" class="sign sign-red">挂起</div>
           		<div v-cloak class="sign sign-blue">
             		<p v-if="status == '30001001' ">未分单</p>
             		<p v-if="status == '30001002' ">已接单</p>
             		<p v-if="status == '30001003' ">已签约</p>
             		<p v-if="status == '30001004' ">已过户</p>
             		<p v-if="status == '30001005' ">已领证</p>
             		<p v-if="status == '30001006' ">未指定</p>
             		<p v-if="status == '30001007' ">被合流</p>
           	 	</div>
           	 	
          		<div v-cloak v-if="caseProperty=='30003006'" class="sign sign-red">全部</div>
           		<div v-cloak class="sign sign-blue">
            		<p v-if="status == '30001001' ">未分单</p>
            		<p v-if="status == '30001002' ">已接单</p>
            		<p v-if="status == '30001003' ">已签约</p>
            		<p v-if="status == '30001004' ">已过户</p>
            		<p v-if="status == '30001005' ">已领证</p>
            		<p v-if="status == '30001006' ">未指定</p>
            		<p v-if="status == '30001007' ">被合流</p>
           		</div>
                  	
				<div class="panel-body">
					<div class="ibox-content-head lh24">
						<h5>案件基本信息</h5>

						<small v-cloak class="pull-right">交易编号：{{caseCode}}｜成家报告编号：{{ccaiCode}}</small>
					</div>
					<div id="infoDiv infos" class="row">
						<div class="ibox white_bg">
							<div class="info_box info_box_one col-sm-4 ">
								<span>物业信息</span>
								<div class="ibox-conn ibox-text">
									<dl class="dl-horizontal">
										<dt>产证地址</dt>
										<dd v-cloak>{{toPropertyInfo.propertyAddr}}</dd>
										<dt>层高</dt>
										<dd v-cloak>{{toPropertyInfo.locateFloor}}／{{toPropertyInfo.totalFloor}}</dd>
										<dt>产证面积</dt>
										<dd v-cloak>{{toPropertyInfo.square}}<label v-if="toPropertyInfo.square !='' && toPropertyInfo.square !=null">平方</label></dd>
										<dt>竣工年限</dt>
										<dd v-cloak>{{toPropertyInfo.finishYear}}<label v-if="toPropertyInfo.finishYear !='' && toPropertyInfo.finishYear !=null">年</label></dd>
										<dt>房屋类型</dt>
										<dd v-cloak>{{toPropertyInfo.propertyTypeName}}</dd>
									</dl>
								</div>
							</div>
							
							<div class="info_box info_box_two col-sm-5">
								<span>买卖双方</span>
								<div class="ibox-conn else_conn">
									<dl class="dl-horizontal col-sm-6">
										<dt>买方姓名</dt>
										<dd>
											<a v-cloak id="buyer" data-toggle="popover" data-placement="right" 
												data-content="">{{buyerName}}</a>
										</dd>
									</dl>
									<dl class="dl-horizontal col-sm-6">
										<dt>卖方姓名</dt>
										<dd>
											<a v-cloak id="seller" data-toggle="popover" data-placement="right" 
												data-content="">{{sellerName}}</a>
										</dd>
									</dl>
								</div>
							    <span>经纪人信息</span>
								<div class="ibox-conn else_conn_two ">
									<dl class="dl-horizontal">
										<dt>姓名</dt>
										<dd>
											<a v-cloak id="agent" data-toggle="popover" data-placement="right" 
												data-content="">{{agentName}}</a>
										</dd>
										<dt>所属分行</dt>
										<dd v-cloak>{{agentGrpName}}</dd>
										<dt>直属经理</dt>
										<dd>
											<a v-cloak id="manager" data-toggle="popover" data-placement="right"
												data-content="">{{mcName}} </a>
										</dd>
										<dt>分行秘书</dt>
										<dd>
											<a v-cloak id="ms" data-toggle="popover" data-placement="right"
												data-content="">{{msName}}</a>
										</dd>
									</dl>
								</div>

							</div>
							<div class="info_box info_box_three col-sm-3">
								<span>经办人信息</span>
								<div class="ibox-conn  ibox-text">
									<dl class="dl-horizontal">
										<dt>贷款权证</dt>
										<dd>
											<a v-cloak id="loan" data-toggle="popover" data-placement="right"
												data-content="">{{loanName}} </a>
										</dd>
										<dt>过户权证</dt>
										<dd>
											<a v-cloak id="warr" data-toggle="popover" data-placement="right"
												data-content="">{{warName}} </a>
										</dd>
										<dt>内勤</dt>
										<dd>												
											<a v-cloak id="as" data-toggle="popover" data-placement="right"
												data-content="">{{asName}} </a>
												
										</dd>
									</dl>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script	src="<c:url value='/js/vue.min.js' />" type="text/javascript"></script>
<!-- caseBaseInfo -->


	<div class="">



<!-- 调佣对象调佣金额 -->
		 <div class="ibox-content border-bottom clearfix space_box noborder marginbot" id="serviceFlow">
		 <form action="#" id="changeCommForm">
<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
<input type="hidden" id="ctx" name="ctx" value="${ctx}">
		<!-- 原来的页面 -->
		<h2 class="newtitle title-mark">调佣信息</h2>		
	                <!-- 原来的页面 -->
		<div  style="width: 80%" align="center" class="table_content">
		<div align="left" style="height:30px">
			<font color=" red" >*</font>调佣事项： 
			<input type="text" id="changeChargesItem" maxlength="16" name="changeChargesItem" value="评估公司变更" readonly="readonly">
		</div>
		<div align="left" style="height:30px">
			<font color=" red" >*</font>调佣类型： 
			<input type="text" id="changeChargesType" value="${evalChangeCommVO.changeChargesType }" name="changeChargesType" >
		</div>
		<div align="left" style="height: 30px">
			<font color=" red">*</font>调佣事由： <input type="text"
				id="changeChargesCause" value="${evalChangeCommVO.changeChargesCause }" maxlength="16" name="changeChargesCause">
		</div>
		<div align="left" style="height: 30px">
			<font color=" red">*</font>调佣对象与调佣金额如下表：
		</div>
		<hr>
			<table style="width: 100%;height: 600px;" class="table-hover">
            <thead>
            <tr>
                <td></td><td>合作费类型</td><td>分成金额</td><td>分成比例</td><td>合作人</td><td>合作部门</td><td>合作经理</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${evalChangeCommVO.coPersonList }" var="coPerson" varStatus="s">
            <tr>
                    <td>${coPerson.position }${s.count} : <input type="hidden" name="coPersonList[${s.index}].pkid" value="${coPerson.pkid }"></td>
                    <td><input type="text" style="width: 120px" name="coPersonList[${s.index}].cooperateType" value="${coPerson.cooperateType }" ></td>
                    <td><input class="shareAmount"  type="text" style="width: 120px" name="coPersonList[${s.index}].shareAmount" value="${coPerson.shareAmount }"></td>
                    <td><span class="aa"></span><span>%</span></td>
                    <td><input type="text" style="width: 120px" name="coPersonList[${s.index}].employeeName" value="${coPerson.employeeName }"></td>
                    <td><input type="text" style="width: 120px" name="coPersonList[${s.index}].cooperateDept" value="${coPerson.cooperateDept }"></td>
                    <td><input type="text" style="width: 120px" name="coPersonList[${s.index}].cooperateManager" value="${coPerson.cooperateManager }"></td>
                </tr>
            </c:forEach>
                <!-- <tr>
                    <td>合作人1:</td>
                    <td><input type="text" style="width: 120px"></td>
                    <td><input type="text" style="width: 120px"></td>
                    <td><input type="text" style="width: 120px"></td>
                    <td><input type="text" style="width: 120px"></td>
                    <td><input type="text" style="width: 120px"></td>
                    <td><input type="text" style="width: 120px"></td>
                </tr> -->
                

                <tr>
                    <td></td><td>部门</td><td>员工</td><td>分成金额</td><td>分成比例</td><td>分成说明</td><td>成交单数</td>
                </tr>
                <c:forEach items="${evalChangeCommVO.sharePersonList }" var="sharePerson" varStatus="s">
                <tr>
                    <td>${sharePerson.position }${s.count}:<input type="hidden" name="sharePersonList[${s.index}].pkid" value="${sharePerson.pkid }"></td>
                    <td><input type="text" style="width: 120px" name="sharePersonList[${s.index}].department" value="${sharePerson.department }"></td>
                    <td><input type="text" style="width: 120px" name="sharePersonList[${s.index}].employeeName" value="${sharePerson.employeeName }"></td>
                    <td><input class="shareAmount" type="text" style="width: 120px" name="sharePersonList[${s.index}].shareAmount" value="${sharePerson.shareAmount }"></td>
                    <td><span class="aa"></span><span>%</span></td>
                    <td><input type="text" style="width: 120px" name="sharePersonList[${s.index}].shareReason" value="${sharePerson.shareReason }"></td>
                    <td><input type="text" style="width: 120px" name="sharePersonList[${s.index}].dealCount" value="${sharePerson.dealCount }"></td>
                </tr>
                </c:forEach>
                <!-- <tr>
                    <td>分成人2:</td>
                    <td><input type="text" style="width: 120px"></td>
                    <td><input type="text" style="width: 120px"></td>
                    <td><input type="text" style="width: 120px"></td>
                    <td><input type="text" style="width: 120px"></td>
                    <td><input type="text" style="width: 120px"></td>
                    <td><input type="text" style="width: 120px"></td>
                </tr> -->               
                <tr></tr><tr></tr>
                
                <c:forEach items="${evalChangeCommVO.warrantPersonList }" var="warrantPersonList" varStatus="s">
                <tr>
                    <td>权证1:<input type="hidden" name="warrantPersonList[${s.index}].pkid" value="${warrantPersonList.pkid }"></td>
                    <td align="left"><input type="text" name="warrantPersonList[${s.index}].department" value="${warrantPersonList.department }" style="width: 120px"></td>
                    <td align="left"><input type="text" name="warrantPersonList[${s.index}].employeeName" value="${warrantPersonList.employeeName }" style="width: 120px"></td>
                    <td></td>
                    <td></td>
                    <td align="left"><input type="text" name="warrantPersonList[${s.index}].position" value="${warrantPersonList.position }" style="width: 120px"></td>
                    <td></td>
                </tr>
                </c:forEach>

                <tr>
                    <td></td>
                    <td></td>
                    <td>合计:</td>
                    <td><input id="ttlComm" class="shareAmount" type="text" value="${evalChangeCommVO.ttlComm }" name="ttlComm" style="width: 120px"></td>
                    <td><span id="totalPacentage"></span><span>%</span></td>
                    <td>单数合计:</td>
                    <td><input type="text" value="${evalChangeCommVO.dealCount }" name="dealCount" style="width: 120px"></td>
                </tr>
            </tbody>
        </table>

</div>

<!-- 调佣对象调佣金额 -->

<!-- 填写审批任务 -->
<hr>
<div class="">
	            <div class="line">	
		            <div class="title title-mark" id="aboutInfo">
		               <strong style="font-weight:bold;">填写审批任务</strong>
		            </div>
	            </div>	            
	            <input type="hidden" name="partCode" value="changeEvalComAudit">
				<input type="hidden" id="taskId" name="taskId" value="${taskId}">
				<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
	            <div class="line" style="margin-top:18px">		                 
		                    <div class="form_content" >
		                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>审批结果：</label>
		                        <input type="radio" name="status" value="1" checked="checked"><span>通过</span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    							<input type="radio" name="status" value="0"><span>驳回</span>
		                    </div>
			                  	                     
		                </div>
		        
		        <div class="line" style="margin-top:18px">		                 		                    
		                    <div class="form_content">
		                        <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>审批意见：</label> 
		                        <input type="text"  class="input_type mendwidth" id="content" name="content" 
										value="" maxlength="64">
		                    </div>			                  	                     
		                </div>   
		                     	          	            
				</form>
				
</div><hr>

	            <!-- 填写审批任务 -->
	            <div class="title title-mark" id="aboutInfo">
	               <strong style="font-weight:bold;">开票审批记录</strong>
	            </div>	            
	            <div class="view-content">
	              	<table id="gridTable" class=""></table>
	   				<div id="gridPager"></div>
	            </div>	
<br><br>	
<div class="form-btn">
	                    <div class="text-center">
	                        <button  class="btn btn-success btn-space" onclick="javascript:window.close()" id="btnSave">关闭</button>
	                         <button class="btn btn-success btn-space" onclick="submit()">提交</button>
	                         
	                    </div>
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
				<script src="<c:url value='/js/stickUp.js' />"></script>
				<!-- 改版引入的新的js文件 -->
				<script src="<c:url value='/js/common/textarea.js' />"></script>
				<script src="<c:url value='/js/common/common.js' />"></script>
				<script>
					$(document).ready(function(){
						var ctx = $("#ctx").val();
						var caseCode=$("#caseCode").val();	
						AttachmentList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager','${ctmCode}',caseCode);
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
		            //页面加载时计算出百分比
		            //var shareAmountArray=$('.shareAmount');
		            //console.log(shareAmountArray);
		            
		            /* $('.shareAmount').each(function(){
		            	var totalcomm=$("#ttlComm").val();
		            	totalcomm=parseInt(totalcomm);
		            	console.log(totalcomm);
		                var sharePacentage=$(this).val()/totalcomm*100;
		                sharePacentage=sharePacentage.toFixed(2)
		            	$(this).parent().siblings().children(".aa").text(sharePacentage);
		            }) */
		            
		            refeshShareAmount();
		            getTotalSharePacentage();
		            
					//绑定计算百分比事件
		            $('.shareAmount').bind('keyup onpropertychange', function() {   
		                console.log($(this).val());
		                //var totalcomm=10000;
		                var totalcomm=$("#ttlComm").val();
		                totalcomm=parseInt(totalcomm);
		                var sharePacentage=$(this).val()/totalcomm*100;
		                sharePacentage=sharePacentage.toFixed(2)
		                console.log(sharePacentage);
		                //$(this).parent().siblings().children(".aa").text(sharePacentage);
		                console.log($(this).parent().siblings().children(".aa").text(sharePacentage));
		                refeshShareAmount();
		                getTotalSharePacentage();
		              //计算总百分比
		                /* var totalPacentage=0;
		                var totalPacentageArray=new Array();
		                $(".aa").each(function(){
		                    totalPacentageArray.push(parseInt($(this).text()));
		                })
		                for(var i=0;i<totalPacentageArray.length;i++){
		                    totalPacentage=totalPacentageArray[i]+totalPacentage;
		                }
		                console.log(totalPacentageArray);
		                console.log(totalPacentage);
		                $("#totalPacentage").text(totalPacentage);   */              
		            }); 
		            		            
					})//end ready function
					
					function refeshShareAmount(){
						$('.shareAmount').each(function(){
			            	var totalcomm=$("#ttlComm").val();
			            	totalcomm=parseInt(totalcomm);
			            	console.log(totalcomm);
			                var sharePacentage=$(this).val()/totalcomm*100;
			                sharePacentage=sharePacentage.toFixed(2)
			            	$(this).parent().siblings().children(".aa").text(sharePacentage);
			            })
					}
					
					function getTotalSharePacentage(){
						//计算总百分比
		                var totalPacentage=0;
		                var totalPacentageArray=new Array();
		                $(".aa").each(function(){
		                    totalPacentageArray.push(parseInt($(this).text()));
		                })
		                for(var i=0;i<totalPacentageArray.length;i++){
		                    totalPacentage=totalPacentageArray[i]+totalPacentage;
		                }
		                console.log(totalPacentageArray);
		                console.log(totalPacentage);
		                $("#totalPacentage").text(totalPacentage);     
					}
				</script> 
			</content>
	</body>
</html>