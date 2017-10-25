<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>
<title>询价详情</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" rel="stylesheet" />

<style type="text/css">

.dl-horizontal{
	font-size:12px;
}
.height_line{
	height:10px;
}
.height_line1{
	height:30px;
}
.col-sm-4{
	height:40px;
}
.font-family{
font-family:Microsoft Yahei;
}
.text-center{text-align:center;}

</style> 

<content tag="pagetitle">询价详情</content>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="status" value="${toEvaPricingVo.status }">	
					
	<nav id="navbar-example" class="navbar navbar-default navbar-static"
		role="navigation">
		<div id="isFixed" style="position: relative; top: 0px;"
			class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
			<ul class="nav navbar-nav scroll_content">
				<li class="menuItem active"><a href="#basicInfo"> 基本信息 </a></li>
				<li class="menuItem"><a href="#content"> 询价内容 </a></li>
				<li class="menuItem"><a href="#result"> 询价结果 </a></li>

			</ul>
		</div>
	</nav>
	
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="panel-body ibox-content">
			<div class="ibox-content-head lh24">
				<h5>询价基本信息</h5>
				<small class="pull-right">交易编号：${toEvaPricingVo.caseCode}｜成交报告编号：${toEvaPricingVo.ccaiCode}</small>
			</div>
			<div id="infoDiv infos" class="row " id="basicInfo">
				<div class="ibox white_bg">
					<div class="info_box info_box_one col-sm-5 ">
						<span>物业信息</span>
						<div class="ibox-conn ibox-text" style="height:200px;">
							<dl class="dl-horizontal font-family">
								<dt>产证地址</dt>
								<dd>${toEvaPricingVo.residenceName}</dd>
								<dt>层高</dt>
								<dd>${toEvaPricingVo.floor}／${toEvaPricingVo.totalFloor}</dd>
								<dt>产证面积</dt>
								<dd>${toEvaPricingVo.area}&nbsp;平方</dd>
								<dt>竣工年限</dt>
								<dd>${toEvaPricingVo.completeYear}&nbsp;年</dd>
								<dt>房屋类型</dt>
								<dd>
									<aist:dict id="propType" name="propType"
										display="label" dictType="prop_type"
										dictCode="${toEvaPricingVo.propType}" />
								</dd>
								<dt>房型</dt>
								<dd>${toEvaPricingVo.room }室${toEvaPricingVo.hall }厅${toEvaPricingVo.toilet }卫&nbsp;</dd>
							</dl>
						</div>
					</div>
					<div class="info_box info_box_two col-sm-5">
						<span>申请人信息</span>
						<div class="ibox-conn else_conn" style="height:200px;">
							<dl class="dl-horizontal col-sm-6 font-family">
								<dt>申请人</dt>
								<dd>${toEvaPricingVo.ariserName }</dd>
								<dt></dt><dd></dd>
								<dt>所属部门</dt>
								<dd>${toEvaPricingVo.ariserOrgName }</dd>
							</dl>
						</div>
					</div>
				</div>
			</div>

	  		<div class="ibox white_bg" id="content">
				<div class="info_box info_box_one col-sm-12 ">
			  		<span>询价内容</span>
			  			<div class="height_line"></div>
				  		<div class="row font-family" >
							<label class="col-sm-12 control-label">询价类型 ：
																<aist:dict id="evaType" name="evaType"
																display="label" dictType="evapricing_type"
																dictCode="${toEvaPricingVo.evaType}" />
							</label>
						</div>
						<div class="height_line1"></div>
						<div class="row font-family">
							<label class="col-sm-4 control-label">产证地址 ：${toEvaPricingVo.residenceName }</label>
							<label class="col-sm-3 control-label">产证面积 ：${toEvaPricingVo.area }&nbsp;&nbsp;平方</label>
							<label class="col-sm-3 control-label">房型 ：${toEvaPricingVo.room }&nbsp;室${toEvaPricingVo.hall }&nbsp;厅${toEvaPricingVo.toilet }&nbsp;卫&nbsp;</label>
							<label class="col-sm-2 control-label">房屋类型 ：
																<aist:dict id="propType1" name="propType1"
																display="label" dictType="prop_type"
																dictCode="${toEvaPricingVo.propType}" />
							</label>
						</div>
						<div class="height_line1"></div>
						<div class="row font-family">
							<label class="col-sm-4 control-label">总层高/所在楼层 ：${toEvaPricingVo.floor }/${toEvaPricingVo.totalFloor }</label>
							<label class="col-sm-3 control-label">竣工年限 ：${toEvaPricingVo.completeYear }&nbsp;&nbsp;年</label>
							<label class="col-sm-3 control-label">贷款银行 ：${toEvaPricingVo.loanBank }</label>
							<label class="col-sm-2 control-label">合同价 ：${toEvaPricingVo.conPrice }</label>
						</div>
						<div class="height_line1"></div>
						<div class="row font-family" >
							<label class="col-sm-4 control-label">原购入价 ：<c:if test="${!empty toEvaPricingVo.originPrice }">${toEvaPricingVo.originPrice/10000 }&nbsp;&nbsp;万元</c:if></label>
							<label class="col-sm-3 control-label">评估公司 ：${toEvaPricingVo.evaCompany }</label>
							<label class="col-sm-3 control-label">询价值 ：<c:if test="${!empty toEvaPricingVo.totalPrice }">${toEvaPricingVo.totalPrice/10000 }&nbsp;&nbsp;万元</c:if></label>
						</div>
				</div>
			</div>
	  	</div>
	  	
	  	<div class="panel-body ibox-content" id="result">
	  		<div class="info_box info_box_one col-sm-12 ">
		  		<span>询价结果</span>
		  		<form method="get" class="form_list" id="evaDetailForm">
			  		<input type="hidden" id="taskId" name="taskId" value="${toEvaPricingVo.taskId }">	
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${instCode }">
					<input type="hidden" id="pkid" name="pkid" value="${toEvaPricingVo.pkid }">
					<input type="hidden" id="evaCode" name="evaCode" value="${toEvaPricingVo.evaCode }">
					<input type="hidden" id="caseCode" name="caseCode" value="${toEvaPricingVo.caseCode }">
			  		<div class="height_line"></div>
			  		<c:if test="${toEvaPricingVo.status == 0}">
			  			<input type="radio" checked="checked" value="1"  name="isValid">&nbsp;&nbsp;有效
	                </c:if>  
	                <div style="margin-top:5px;"></div>             	
			  		<div class="row font-family">
						<label class="col-sm-4 control-label">评估公司 ：${toEvaPricingVo.evaCompany }</label>
						<label class="col-sm-3 control-label">评估时间 ：<fmt:formatDate value="${toEvaPricingVo.evalTime }" type="date" pattern="yyyy-MM-dd"/></label>
					</div>
					<div class="height_lin1"></div>
					<div class="row font-family">
						<label class="col-sm-4 control-label">询价值 ：<c:if test="${!empty toEvaPricingVo.totalPrice }">${toEvaPricingVo.totalPrice/10000 }&nbsp;&nbsp;万元</c:if></label>
						<label class="col-sm-3 control-label">房龄 ：${toEvaPricingVo.houseAge }&nbsp;&nbsp;年</label>
					</div>
					<c:if test="${toEvaPricingVo.status == 0}">
						<div class="row font-family">
							<input type="radio" value="2"  name="isValid">&nbsp;&nbsp;无效
							<div style="margin-top:5px;"></div>
							<input type="text" class="form-control " id="invalidReason" name="reason" style="margin-left:15px;width:400px;">
						</div>
					</c:if>
				</form>
			</div>
	  	</div>	  	
			
	</div>
	
	<div class="add_btn text-center mt20">
	   	<div class="more_btn">
	   		<button id="submitButton" type="button" class="btn btn_blue">提交</button>
   	    	<button id="closeButton" type="button" class="btn btn_blue">关闭</button>
		</div>
	</div>
		    
	<content tag="local_script"> 

	<script>
		$(window).ready(function(){
			var status = $('#status').val();
			if(status != 0){
				$('#submitButton').css('visibility','hidden');
			}
			$('#invalidReason').attr('readonly','readonly');
		});
		
		$("input[name='isValid']").click(function(){
			if(this.value=='1'){			
				$('#invalidReason').val("");
				$('#invalidReason').attr('readonly','readonly');
			}else if(this.value == '2'){
				$('#invalidReason').removeAttr('readonly');
			}
		});
		
		$('#submitButton').click(function(){
			var url = '${ctx}/evaPricing/evaPricingDetailSubmit';
			
			var jsonData = $('#evaDetailForm').serializeArray();
	
			 $.ajax({
				cache:true,
				async:false,
				type:"POST",
				url:url,
				data:jsonData,
				dataType:'json',
				success:function(data){
					if(data.success){
						window.wxc.success("提交成功!",{"wxcOk":function(){
							window.close();
						}});
					}else{
						window.wxc.error("提交失败!"+data.message);
					}
					
				},
				error : function(error) {
					window.wxc.error("提交失败!"+error.message);
				}
			})
			
		});
		
		//关闭
		$('#closeButton').click(function() {
			window.close();
		});

	</script>


</content>
</body>
</html>
