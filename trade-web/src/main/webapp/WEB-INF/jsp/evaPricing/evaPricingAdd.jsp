<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css' />" >



<style type="text/css">
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
	width:140px;
}
.margin_top{
	margin-top:2px;
}
.select_control{
	float:left;
} 

.text-center{text-align:center;}

</style> 


</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}" />
	

	
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			<h1 class="title">
				询价新增
        	</h1>
			<form method="get" id="evaluateForm" class="form_list">
				<input type="hidden" name="caseCode" value="${caseCode }">
				<div class="row clearfix">
					<div class="form_content">
						<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>询价类型</label>
						<div class="float_left big_pad">
							<aist:dict id="evaType" name="evaType"  display="select" dictType="eva_type"  clazz="select_control width_house" onchange="evaTypeChange()"/>	
						</div>		
					</div>		
				</div>
				
				<div class="row clearfix">
					<div class="form_content">
						<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>产证地址</label>
						<div class="float_left" style="width:480px;">
							<input type="text" class="form-control pull-left" id="residenceName" name="residenceName">
				 		</div>
					</div>
				</div>
				
				<div class="row clearfix">
					<div class="form_content">
						<label class="sign_left_two control-label">总层高</label>
						<input type="text" class="select_control" name="totalFloor">	                
					</div>
					<div class="form_content">
						<label class="sign_left_two control-label">所在楼层</label>
						<input type="text" class="select_control" name="floor" >
					</div>
				</div>
				
				<div class="row cleaarfix">
					<div class="form_content">
						<label class="sign_left_two control-label">产证面积</label>
						<input type="text" class="select_control" name="area" >
					</div>
					<div class="form_content">
						<label class="sign_left_two control-label">竣工年限</label>
						<input type="text" class="select_control" name="completeYear" >
					</div>
					<div class="form_content">
						<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>房龄</label>
						<input type="text" id="houseAge" class="select_control" name="houseAge" onkeyup="checkNum(this)">
					</div>
				</div>
				
				<div class="row clearfix">
					<div class="form_content">
					<div class="row ">
						<label class="sign_left_two control-label margin_left1">房型</label>
							<label class="text_align_left control-label ">室:</label>
							<aist:dict id="room" name="room" display="select" dictType="house_type" clazz=" aist_margin"/>
							<label class="text_align_left control-label">厅:</label>
							<aist:dict id="hall" name="hall" display="select" dictType="house_type" clazz="aist_margin "/>
							<label class="text_align_left control-label">卫:</label>
							<aist:dict id="toilet" name="toilet" display="select" dictType="house_type" clazz="aist_margin "/>
						
					</div>
					</div>
					<div class="form_content">
						<label class="sign_left_two control-label margin_left">房屋类型</label>
						<aist:dict id="propType" name="propType" display="select" dictType="prop_type" clazz="select_control width_house"/>
					</div>
				</div>
				
				<div class="row clearfix">
					<div class="form_content">
						<label class="sign_left_two control-label">原购入价</label>
						<input id="originPrice" type="text" name="originPrice" class="select_control yuanwid" style="visibility:hidden" onkeyup="checkNum(this)" >
						<span class="date_icon">万元</span>
					</div>
					<div class="form_content">
						<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>贷款银行</label>
						<input id="loanBank" type="text" class="select_control" name="loanBank" >
					</div>
				</div>
				
				<div class="row clearfix">
					<div class="form_content">
						<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>评估公司</label>
						<div class="float_left big_pad">
							<select id="finorgId_0" class="from-control select_control width_evaCommpany">
								<option value="" selected>请选择</option>
								<option value="1">A</option>
								<option value="2">B</option>
								<option value="3">C</option>
							</select>
						</div>
						<a href="javascript:addEvaCompany();"  class="btn btn_blue float_left_two">
							<i class="icon iconfont">+</i>
						</a>
					</div>
					<div class="form_content">
						<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>询价值</label>
						<input type="text" id="totalPrice" name="totalPrice" class="select_control yuanwid"  onkeyup="checkNum(this)">
						<span class="date_icon">万元</span>
						
					</div>
				</div>
				
				<div class="row clearfix">
					<div class="form_content">
						<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>询价时间</label>
						<div id="datepicker" class="input-group sign-right  input-daterange pull-left" id="data_1" data-date-format="yyyy-mm-dd">
                    		<input id="evalTime" name="evalTime" class="form-control data_style" style="font-size: 13px; width: 178px; border-radius: 2px;" type="text" placeholder="询价时间">
                    	</div> 
					</div>
				</div>

			</form>
		</div>  				
	</div>
	
	<div class="add_btn text-center mt20">
	   	<div class="more_btn">
		    <button id="submitButton" type="button" class="btn btn_blue">提交</button>
   	    	<button id="closeButton" type="button" class="btn btn_blue">关闭</button>
		</div>
	</div>
		    
	<content tag="local_script"> 

<!-- 必须JS -->
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
	
	<script>
	
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
	
		//关闭
		$('#closeButton').click(function() {
			window.close();
		});
		
		//提交
		$('#submitButton').click(function(){
			if(!checkForm()){
				return;
			}
			var jsonData = $('#evaluateForm').serializeArray();
			//检查评估公司是否都填及重复
			var finorgArr = new Array();
			for(var i = 0;i<= index;i++){
				var val = $('#finorgId_'+i).val();
				if(val == undefined){
					continue;
				}
				if(val == ''){
					window.wxc.alert("评估公司为必填项!");
					$('#finorgId_'+i).focus();
					$('#finorgId_'+i).css("border-color","red");
					return;
				}
				if(finorgArr.indexOf(val) != -1){
					window.wxc.alert("存在选择相同评估公司!");
					$('#finorgId_'+i).focus();
					$('#finorgId_'+i).css("border-color","red");
					return;
				}
				finorgArr.push(val);
			}
			var obj = {
				name: 'finorgIds',
				value: finorgArr
			};
			jsonData.push(obj);
			url = "${ctx}/evaPricing/save";
			$.ajax({
				cache:true,
				async:false,
				type:"POST",
				url:url,
				data:jsonData,
				dataType:"json",
				success:function(data){
					window.wxc.success("新增成功!",{"wxcOk":function(){
						window.close();
					}});
				},
				error : function(errors) {
					window.wxc.error("数据新增出错!");
				}
			})
		});
		
		//询价类型非贷款时，显示原购入价
		function evaTypeChange(){
			var evaVal = $('#evaType').val();
			if(evaVal == '2' || evaVal == '3'){
				$('#originPrice').css('visibility','visible');
			}else{
				$('#originPrice').css('visibility','hidden');
			}
		}
		
		var index = 1;
		
		//添加评价公司
		function addEvaCompany(){
			var text ='<div style="width:180px;"><div class="float_left big_pad"><select id="finorgId_' + index + '" class="from-control select_control width_evaCommpany margin_top">';
			text += $('#finorgId_0').html();
			text += '</select></div>';
			text += '<span class="float_left" style="padding-top:8px;margin-left:12px;"><a href="javascript:removeFinorg('
				+ index + ');"><font>删除</font></a></span></div>';
			$('#finorgId_0').after(text);
			/* var selectedIndex = findcheckedIndex();
			$('#finorgId_'+index).get(0).selecetdIndex = selectedIndex; */
			
			index ++;
		}
		
		function removeFinorg(i){
			$('#finorgId_'+i).parent().parent().remove();
		}
		
		/* var maxIndex = $('#finorgId_0 option:last').index(); */
		/* function findcheckedIndex(){
			var indexArr = new Array();
			if(index > maxIndex){
				return 0;
			}
			for(var i = 0;i < index;i++){
				var hasIndex = $('#finorgId_'+i+' option:selected').index();
				indexArr.push(hasIndex);
			}
			for(var j=0;j<maxIndex;j++){
				var flag = false;
				for(var k=0;k<indexArr.length;k++){
					if(j == indexArr[k]){
						flag = true;
						break;
					}
				}
				if(!flag){
					return j;
				}
			}
			return 0;
		} */
		
		//检查
		function checkForm() {
	
			if($('#evaType').val() == ''){
				window.wxc.alert("询价类型为必填项!");
				$('#evaType').focus();
				$('#evaType').css("border-color","red");
				return false;
			}
			if($('#residenceName').val() == '' || $('#residenceName').val().length==0){
				window.wxc.alert("产证地址为必填项!");
				$('#residenceName').focus();
				$('#residenceName').css("border-color","red");
				return false;
			}
			if($('#houseAge').val() == ''  || $('#houseAge').val().length==0){
				window.wxc.alert("房龄为必填项!");
				$('#houseAge').focus();
				$('#houseAge').css("border-color","red");
				return false;
			}
			if($('#loanBank').val() == '' || $('#loanBank').val().length == 0){
				window.wxc.alert("贷款银行为必填项!");
				$('#loanBank').focus();
				$('#loanBank').css("border-color","red");
				return false;
			}
			if($('#totalPrice').val() == '' || $('#totalPrice').val().length == 0){
				window.wxc.alert("询价值为必填项!");
				$('#totalPrice').focus();
				$('#totalPrice').css("border-color","red");
				return false;
			}
			if($('#evalTime').val() ==''){
				window.wxc.alert("询价时间为必填项!");
				$('#evalTime').focus();
				$('#evalTime').css("border-color","red");
				return false;
			}
			return true;
		}
	</script>




</content>
</body>
</html>
