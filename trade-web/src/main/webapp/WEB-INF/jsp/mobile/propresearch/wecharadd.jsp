<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
body {
	background-color: #f3f3f4;
}

.span_margin {
	margin-bottom: 12px;
}

.checker {
	float: none;
	Margin-right: 14px;
	margin-left: 13px;
	margin-top: 12px;
	display: inline-block;
}.checker1{
float: none;
	Margin-right: 14px;
	margin-left: 13px;
	margin-top: 12px;
	display: inline-block;}

.chkblock>div {
	display: block;
	float: none !important;
}

.row {
	margin-top: 10px;
}
</style>
</head>

<body>
	<c:set var="pubFolder" value="/momedia" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<form action="${ctx }/mobile/property/box/doAdd" id="m_form"
		method="post"
		enctype="application/x-www-form-urlencoded; charset=UTF-8 ">
		<input type="hidden" id="txt_prCat" name="prCat"> <input
			type="hidden" name="username" value="${username }">
		<div class="row" style="margin-top: 0px; text-align: center;">
			<span style="font-size: 38px; display: block; margin-top: 12px;">人工产调</span>
		</div>
<%-- 		<c:if test="${not empty realname}"> --%>
<!-- 			<div style="margin-top: 20px;"> -->
<%-- 				<h2>区蕫：${realname }</h2> --%>
<!-- 			</div> -->
<%-- 		</c:if> --%>
		<div class="row" style="margin-top: 20px;">
			<div class="col-lg-10">
				<select class="form-control" onchange="changeOrgName(this);" id="zhanqu">
  					<option value="">请选择</option>
  					<c:forEach items="${orgs}" var="org">
  						<c:choose>
						   <c:when test="${org.id == orgId}">
		  						<option value="${org.id}" selected="selected">${org.orgName}</option>
						   </c:when>
						   <c:otherwise>
		  						<option value="${org.id}">${org.orgName}</option>
						   </c:otherwise>
						</c:choose>
  					</c:forEach>
 				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<input class="form-control" type="text" style="padding-left: 15px;" placeholder="区蕫" readonly="readonly" id="orgName" value="${realname }">
			</div>
		</div>
		<div class="row" style="margin-top: 16px;">
			<div class="col-lg-12">
				<aist:dict id="sel_district" name="district" clazz="form-control"
					display="select" hasEmpty="false" dictType="yu_shanghai_district"
					ligerui='none'></aist:dict>

			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<input class="form-control" type="text" name="propertyAddr"
					id="propertyAddr" placeholder="产调地址" style="padding-left: 15px;">
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12 chkblock">
				<div style="">
					<div class="checker1">
						<input id="select_all" type="checkbox" class="" validate="">&nbsp;&nbsp;&nbsp;&nbsp;全选
					</div>
				</div>
					<aist:dict id="sel_prCat" name="_prCat" display="checkbox"
						hasEmpty="false" dictType="30009" ligerui='none'></aist:dict>

			
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12" style="text-align: center;">
				<input type="button" class="btn btn-warning"
					style="width: 98%; background-image: none;" id="btn_submit"
					value="提交">

			</div>
		</div>

	</form>
	<content tag="local_script"> <script
		src="${ctx}/momedia/js/jquery.blockui.min.js"></script> <script>
			var ctx = '${ctx}';
			$(document)
					.ready(
							function() {
								$('.checker')
										.parent()
										.click(
												function(event) {
													var _thisInput=$(this).is('div')?$(this).find("input[type='checkbox']"):$(this);
													if($(this).is('div')){
														_thisInput.prop('checked',!_thisInput.prop('checked'));
													}
													event.stopPropagation();
												});
							
								$("#btn_submit").click(function() {
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
									formCheck();
								});
							});

			function formSubmit() {

				var prCat = '';
				$("#m_form").find("input[name='_prCat']:checked")
						.each(
								function(e) {
									prCat += ($(this).closest('div').parent()
											.text() + "/");
								});
				if (prCat != '') {
					prCat = prCat.substring(0, prCat.length - 1);
				} else {
					alert('至少选择一项产调项目！');
					$.unblockUI();
					return false;
				}
				$("#txt_prCat").val(prCat);
				$
						.ajax({
							url : ctx + "/mobile/property/box/doApply",
							type : "POST",
							data : $("#m_form").serialize(),
							dataType : "json",
							success : function(data) {
								if (data.success && data.success == true) {

									window.location.href = ctx
											+ '/mobile/property/box/toResult?districtId='
											+ data.content;
								} else {
									if (data.message != null
											&& data.message != '') {
										alert(data.message);
									} else {
										alert("录入失败");
									}
									$.unblockUI();
								}
							}
						});
			}

			function formCheck() {
				if ($("#propertyAddr").val() == '') {
					alert('请输入产调地址！');
					$.unblockUI();
					return false;
				}
				
				if ($("#m_form").find("input[name='_prCat']:checked").size() == 0) {
					alert('至少选择一项产调项目！');
					$.unblockUI();
					return false;
				}
				var district = $("#sel_district").val();
				if(district==null||district==''){
					alert('请选择行政区域');
					$.unblockUI();
					return false;
				}
				$.ajax({
					url : ctx + "/mobile/property/box/hasMapping",
					method : "post",
					data : {
						district : district
					},
					dataType : "json",
					success : function(data) {
						if (data.success && data.success == true) {
							formSubmit();
						} else {
							alert('请先选择区域！');
							$.unblockUI();
							return false;
						}
					}
				});
			}
			function selectAll (event){
				var _thisInput=$(this).is('div')?$(this).find("input[type='checkbox']"):$(this);
				if($(this).is('div')){
					_thisInput.prop('checked',!_thisInput.prop('checked'));
				}
				$("input[name='_prCat']").prop("checked",
						_thisInput.prop('checked'));
				event.stopPropagation();
			}
			
			function changeOrgName(th){
				$.ajax({
					url : ctx + "/mobile/property/box/getOrgName",
					method : "post",
					data : {
						orgId : $(th).val()
					},
					dataType : "json",
					success : function(data) {
						if(data.success && data.success == true) {
							$("#orgName").val(data.message);
						}else{
							$("#orgName").val('没有找到对应的区蕫！');
						}
					}
				});
			}
			
			$(".checker").find("input[type='checkbox']").click(function(event){
				event.stopPropagation();
			});
			$('#select_all').click(selectAll);
			$("#select_all").parent().click(selectAll);
			
			
		</script> </content>
</body>
</html>
