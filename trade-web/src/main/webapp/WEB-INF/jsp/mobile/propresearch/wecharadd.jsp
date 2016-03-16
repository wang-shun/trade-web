<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8"> 
<style type="text/css">
body {
	background-color: #f3f3f4;
}
.span_margin{
margin-bottom: 12px;
}
.checker{  float: none;
  Margin-right: 14px;
  margin-left: 13px;
  margin-top:12px;
  display: inline-block;}
.chkblock >div{
  display: block;
  float: none!important;}
  .row{
  margin-top: 10px;
  }
</style>
</head>

<body>
<c:set var="pubFolder" value="/momedia" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<form action="${ctx }/mobile/property/box/doAdd" id="m_form" method="post" enctype="application/x-www-form-urlencoded; charset=UTF-8 ">
<input type="hidden" id="txt_prCat" name="prCat">
<input type="hidden" name="username" value="${username }">
	<div class="row" style="margin-top: 0px; text-align: center;" ><span style="font-size: 38px;display: block;    margin-top: 12px;">人工产调</span></div>
	<div class="row"style="margin-top: 8px;">
		<div class="col-lg-12">
			<aist:dict id="sel_district" name="district"
					clazz="form-control" display="select" hasEmpty="false"
					dictType="yu_shanghai_district" ligerui='none'></aist:dict>
			
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<input class="form-control" type="text" name="propertyAddr"
				placeholder="产调地址">
		</div>
	</div>
		<div class="row">
		<div class="col-lg-12 chkblock">
			<div class="checker">
		<input id="select_all" type="checkbox"  class="" validate="">&nbsp;&nbsp;&nbsp;&nbsp;全选</div>
			 <aist:dict id="sel_prCat" name="_prCat" 
					 display="checkbox" hasEmpty="false"
					dictType="30009" ligerui='none'></aist:dict>
			
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12" style="text-align: center;">
				<input type="button" class="btn btn-warning"  style="width: 98%;background-image: none;" id="btn_submit"
				value="提交">
			
		</div>
	</div>
	
	</form>
	<content tag="local_script"> 
	<script src="${ctx}/momedia/js/jquery.blockui.min.js"></script>
	<script>
	var ctx='${ctx}';
		$(document).ready(function(){
			$('.checker').parent().click(function(event){
				$(this).find("input[type='checkbox']").attr("checked",!$(this).find("input[type='checkbox']").attr("checked"));
			});
			$('.checker').find("input[type='checkbox']").click(function(event){
				event.stopPropagation();
			});
			$("#btn_submit").click(function(){
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
				formCheck();
			});
		});
		
		function formSubmit(){
			var prCat='';
				$("#m_form").find("input[name='_prCat']:checked").each(function(e){
					prCat+=($(this).closest('div').parent().text()+"/");
				});
				if(prCat!=''){
					prCat=prCat.substring(0,prCat.length-1);
				}
				$("#txt_prCat").val(prCat);
				$.ajax({
					url : ctx + "/mobile/property/box/doApply",
					type : "POST",
					data:$("#m_form").serialize(),
					dataType : "json",
					success : function(data) {
						if(data.success&&data.success==true){					
							
							window.location.href=ctx+'/mobile/property/box/toResult?districtId='+data.content;
						}else{
							if(data.message!=null&&data.message!=''){
								alert(data.message);
							}else{
								alert("录入失败");
							}
							$.unblockUI();
						}
					}
				});
		}
		
		
		function formCheck(){
			var district=$("#sel_district").val();
				$.ajax({
					url : ctx + "/mobile/property/box/hasMapping",
					method : "post",
					data:{district:district},
					dataType : "json",
					success : function(data) {
						if(data.success&&data.success==true){
							formSubmit();
						}else{
							alert('该区域暂不能进行产调录入！');
							$.unblockUI();
							return false;
						}
					}
				});
		}
		$("#select_all").click(function () {//全选  
            $("input[name='_prCat']").prop("checked", $(this).prop('checked'));  
        });  
	</script>	
	
	</content>
</body>
</html>
