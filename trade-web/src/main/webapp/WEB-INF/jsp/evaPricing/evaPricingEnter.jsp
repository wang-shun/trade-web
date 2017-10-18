<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>
<title>询价回复</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" rel="stylesheet" />
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"	rel="stylesheet">
<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet"/>


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
.sign_left {
	margin-left:50px;
  	float: left;
}
.sign_left_two {
	margin-top:7px;
	float: left;
}
.float_left {
  float: left;
}
.float_left_two {
  float: left;
  margin-left:10px;
}
.big_pad {
  width: 130px;
}
.margin_left{
	margin-left:17px;
}
.margin_left1{
	margin-left:15px;
}
.text_align_left{
    display: inline-block;
    width: 20px;
    margin-right: 10px;
    margin-top: 7px;
    margin-left: 10px;
    color: #333;
    text-align: left;
    float:left;

}
.aist_margin{
	float:left;
	display: inline-block;
    height: 34px;
    padding: 6px 12px;
    font-size: 13px;
    color: #999;
    background-color: #fff;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 2px;
    width:120px;
}
.width_house{
	width:178px;
}
.width_evaCommpany{
	width:178px;
}
.margin_top{
	margin-top:2px;
}
.select_control{
	float:left;
} 

.text-center{text-align:center;}

</style> 

<content tag="pagetitle">询价回复</content>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
	
	
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
		  			<div class="ibox-conn"> 
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
							<label class="col-sm-3 control-label">原购入价 ：<c:if test="${!empty toEvaPricingVo.originPrice }">${toEvaPricingVo.originPrice/10000 }&nbsp;&nbsp;万元</c:if></label>
							<label class="col-sm-2 control-label">评估公司 ：${toEvaPricingVo.evaCompany }</label>
							
						</div>
						<div class="height_line1"></div>
						<div class="row font-family" >
							<label class="col-sm-4 control-label">贷款银行 ：${toEvaPricingVo.loanBank }</label>
							<label class="col-sm-3 control-label">合同价 ：${toEvaPricingVo.conPrice }</label>
							<label class="col-sm-3 control-label">询价值 ：<c:if test="${!empty toEvaPricingVo.totalPrice }">${toEvaPricingVo.totalPrice/10000 }&nbsp;&nbsp;万元</c:if></label>
						</div>
					</div>
				</div>
			</div>
	  	</div>
	  	<div class="panel-body ibox-content">
		  	<div class="ibox white_bg" id="result">
				<div class="info_box info_box_one col-sm-12 ">
			  		<span>填写询价信息</span>
			  		<form method="get" class="form_list" id="evaEnterForm">
				  		<input type="hidden" name="pkid" value="${toEvaPricingVo.pkid }">
				  		<input type="hidden" name="evaCode" value="${toEvaPricingVo.evaCode }">
				  		<input type="hidden" name="caseCode" value="${toEvaPricingVo.caseCode }">
				  		<input type="hidden" name="taskId" value="${taskId }" >
				  		<input type="hidden" name="processInstanceId" value="${processInstanceId }">
				  		
				  		<div class="row clearfix" >
				  			<div class="form_content" style="margin-left:50px;">
				  				<input type="radio" checked="checked" value="1"  name="isValid">&nbsp;&nbsp;有效
				  			</div>
				  			<div class="form_content" style="margin-left:100px;">
				  				<input type="radio" value="2"  name="isValid">&nbsp;&nbsp;无效
			  				</div>
				  		</div>
				  		<div id="valid">
					  		<div class="row clearfix">
								<div class="form_content">
									<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>评估公司</label>
									
										<select id="finorgId" name ="finorgId" class="from-control select_control width_evaCommpany">
										</select>
								</div>
								<div class="form_content">
									<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>询价时间</label>
									<div id="datepicker" class="input-group sign-right  input-daterange pull-left width_house"  data-date-format="yyyy-mm-dd">
					                  		<input id="evalTime" name="evalTime" class="form-control " style="font-size: 13px; border-radius: 2px;" type="text" placeholder="询价时间">
					                  	</div> 
								</div>
							</div>
							
							<div class="row clearfix">
								<div class="form_content">
									<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>询价值</label>
									<input type="text" id="totalPrice" name="totalPrice" class="select_control"  onkeyup="checkNum(this)">
									<div class="date_icon">万元</div>
								</div>
								<div class="form_content">
									<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>房龄</label>
									<input type="text" id="houseAge" class="select_control" name="houseAge" style="margin-left:2px;" onkeyup="checkNum(this)">
								</div>
							</div>	
						</div>
						<div class="clearfix" id="inValid" style="position:relative;float:left;margin-left:50px;margin-top:-100px;">
							<div class="form_content">
								<input type="text" class="form-control pull-left" id="invalidReason" name="reason" style="width:300px;" placeholder="无效原因">
							</div>
						</div>
					</form>
				</div>
		  	</div>	  	
		</div>
	</div>
	
	<div class="add_btn text-center mt20">
	   	<div class="more_btn">		   
 	   	    <button id="submitButton" type="button" class="btn btn_blue">提交</button>&nbsp;&nbsp;&nbsp;
   	    	<button id="saveButton" type="button" class="btn btn_blue">保存</button>
		</div>
	</div>
		    
	<content tag="local_script"> 
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
	<script>
		$(document).ready(function() {
			getEvaFinOrg('finorgId');
			
			$('#inValid').css('visibility','hidden');
		});
		
		$("input[name='isValid']").click(function(){
			if(this.value=='1'){
				$('#valid').css('visibility','visible');
				$('#inValid').css('visibility','hidden');
				$('#saveButton').show();
			}else if(this.value == '2'){
				$('#valid').css('visibility','hidden');
				$('#inValid').css('visibility','visible');
				$('#saveButton').hide();
			}
		});
		/**
		 * 获取评估公司 格式化
		 * @param finOrgId
		 */
		function getEvaFinOrg(finOrgId){
			var url = "/manage/queryEvaCompany";
			$.ajax({
				async: true,
				type:'POST',
				url:ctx+url,
				dataType:'json',
				success:function(data){
					var html = '<option value="" selected>请选择</option>';
					if(data != null){
						$.each(data,function(i,item){
							html += '<option value="'+item.pkid+'">'+item.finOrgName+'</option>';
						});
					}					
					$('#'+finOrgId).empty();
					$('#'+finOrgId).append(html);
				},
				error : function(errors) {
				}
			});
		}
		// 日期控件
		$('#datepicker').datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			todayBtn : 'linked',
			language : 'zh-CN'
		});
		
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
		
		//提交
		$('#submitButton').click(function() {
			if(!checkForm()){
				return;
			}
			var jsonData = $('#evaEnterForm').serializeArray();
			
			var obj = {
					name: 'isSubmit',
					value: '1'
				};
			jsonData.push(obj);
			url = "${ctx}/evaPricing/evaPricingEnterSubmit";
			$.ajax({
				cache:true,
				async:false,
				type:"POST",
				url:url,
				data:jsonData,
				dataType:"json",
				success:function(data){
					window.wxc.success("记录提交成功!",{"wxcOk":function(){
						window.location.href = "${ctx}/evaPricing/list";
					}});
				},
				error : function(errors) {
					window.wxc.error("记录提交失败!");
				}
			})
		});
		
		//保存
		$('#saveButton').click(function(){
			if(!checkForm()){
				return;
			}
			var jsonData = $('#evaEnterForm').serializeArray();
		
			url = "${ctx}/evaPricing/evaPricingEnterSubmit";
			$.ajax({
				cache:true,
				async:false,
				type:"POST",
				url:url,
				data:jsonData,
				dataType:"json",
				success:function(data){
					window.wxc.success("记录保存成功!",{"wxcOk":function(){
						window.location.href = "${ctx}/evaPricing/list";
					}});
				},
				error : function(errors) {
					window.wxc.error("记录保存失败!");
				}
			})
		});

		function checkForm(){
			var valid = $("input[name='isValid']:checked").val();
			if(valid == '2'){
				return true;
			}
			if($('#finorgId').val() == ''){
				window.wxc.alert("评估公司为必填项!");
				$('#finorgId').focus();
				$('#finorgId').css("border-color","red");	
				return false;
			}
			if($('#evalTime').val() ==''){
				window.wxc.alert("询价时间为必填项!");
				$('#evalTime').focus();
				$('#evalTime').css("border-color","red");
				return false;
			}
			if($('#totalPrice').val() == '' || $('#totalPrice').val().length == 0){
				window.wxc.alert("询价值为必填项!");
				$('#totalPrice').focus();
				$('#totalPrice').css("border-color","red");
				return false;
			}
 			if($('#houseAge').val() == ''  || $('#houseAge').val().length==0){
				window.wxc.alert("房龄为必填项!");
				$('#houseAge').focus();
				$('#houseAge').css("border-color","red");
				return false;
			} 
			return true;
		}
	</script>


</content>
</body>
</html>
