<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/css/common/aui.2.0.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/css/common/style.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/iconfont/iconfont.css' />" />
    <!--date css-->
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/css/plugins/mobiscroll/mobiscroll.css' />" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/css/plugins/mobiscroll/mobiscroll_date.css' />" />
    
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/momedia/css/property/manualtransfer.css' />" />
</head>

<body>
	<c:set var="pubFolder" value="/momedia" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<form action="${ctx }/mobile/property/box/doAdd" id="m_form"
		method="post"
		enctype="application/x-www-form-urlencoded; charset=UTF-8 ">
		<input type="hidden" id="txt_prCat" name="prCat">
		<input type="hidden" name="username" value="${username }">
		<input type="hidden" name="prApplyDepId" value="${depId }">
		<input type="hidden" name="prCostOrgId" id="prCostOrgId">
		<input type="hidden" name="prCostOrgName" id="prCostOrgName">
		<input type="hidden" name="prCostOrgMgr" id="prCostOrgMgr">
		<header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" onClick="javascript :history.back(-1);">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">人工产调</div>
        <a class="aui-pull-right aui-btn ">
            <span class="" id="btn_submit">提交</span>
        </a>
    </header>
    <section class="aui-content aui-margin-b-15">
        <ul class="aui-list aui-form-list">
            <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                       <i class="iconfont blue mr5">&#xe683;</i>行政区
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow">
                       <c:choose>
							<c:when test="${not empty orgId}">
								<select class="form-control" id="zhanqu" disabled="disabled" onchange="changeOrgName(this);">
									<option value="${orgId}">${orgName}</option>
								</select>
							</c:when>
							<c:otherwise>
								<select class="form-control" id="zhanqu">
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
							</c:otherwise>
						</c:choose>
                    </div>
                </div>
            </li>
            <li class="aui-list-item mt10">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-input" style="padding-left: 0">
                       <input class="form-control" type="text" style="padding-left: 15px;" placeholder="区蕫" readonly="readonly" id="orgName" value="${realname }">
                    </div>
                </div>
            </li>
            <li class="aui-list-item mt10">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-input" style="padding-left: 0">
                        <input class="form-control" type="text" name="propertyAddr"
					    id="propertyAddr" placeholder="产调地址" style="padding-left: 15px;"
					    onKeypress="if ((event.keyCode > 32 && event.keyCode < 48) || (event.keyCode > 57 && event.keyCode < 65) || (event.keyCode > 90 && event.keyCode < 97)||(event.keyCode > 122 && event.keyCode < 127)) event.returnValue = false;">
                    </div>
                </div>
            </li>
            <li class="aui-list-item mt10">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                       <i class="iconfont blue mr5">&#xe662;</i>区域归属
                    </div>
                    <div class="aui-list-item-input aui-list-item-arrow">
                       <aist:dict id="sel_district" name="district" clazz="form-control"
						display="select" hasEmpty="false" dictType="yu_shanghai_district"
						ligerui='none'></aist:dict>
                    </div>
                </div>
            </li>

            <li class="aui-item mt10">
                <div class="aui-content aui-margin-b-15">
                    <ul class="aui-list aui-media-list white" id="manua-list" >
                        <li class="aui-list-item" >
                            <div class="aui-list-item-inner">
                                <label><input class="aui-radio" type="checkbox" id="CheckedAll"> 全选</label>
                                <!--   <input id="select_all" type="checkbox" class="" validate="">&nbsp;&nbsp;&nbsp;&nbsp;全选 -->
                            </div>
                        </li>
                        <aist:dict id="sel_prCat" name="_prCat" display="checkboxcustom3"
						  hasEmpty="false" dictType="30009" ligerui='none' clazz="aui-radio" aroundClazz="aui-list-item-inner"></aist:dict>
                    </ul>
                </div>
            </li>
        </ul>
   	</section>
	</form>
	<content tag="local_script"> 
	<script type="text/javascript" src="<c:url value='/static/momedia/js/jquery-2.1.1.js' />"></script>
	<script type="text/javascript" src="<c:url value='/static/momedia/js/api.js' />"></script>
	<script type="text/javascript" src="<c:url value='/static/momedia/js/aui-dialog.js' />"></script>
	<script type="text/javascript" src="<c:url value='/static/momedia/js/aui-main.js' />"></script>
	<script type="text/javascript" src="<c:url value='/static/momedia/js/layer.js' />"></script>
	
	<!--date-->
	<script type="text/javascript" src="<c:url value='/static/momedia/js/plugins/mobiscroll/mobiscroll_date.js' />"></script>
	<script type="text/javascript" src="<c:url value='/static/momedia/js/plugins/mobiscroll/mobiscroll.js' />"></script>
	
	<script type="text/javascript" src="<c:url value='/static/momedia/js/calendar.js' />"></script>
	<script src="<c:url value='/momedia/js/jquery.blockui.min.js' />"></script> 
	<script>
			var ctx = '${ctx}';
			$(document)
					.ready(
							function() {
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
						.each(function(e) {
									prCat += ($(this).parent().text()+ "/");
								});
				if (prCat != '') {
					prCat = prCat.substring(0, prCat.length - 1);
				} else {
					alert('至少选择一项产调项目！');
					$.unblockUI();
					return false;
				}
				$("#txt_prCat").val(prCat);
				
				var prCostOrgId = $("#zhanqu").val();
				var prCostOrgName = $("#zhanqu option:selected").text();
				var prCostOrgMgr = $("#orgName").val();
				
				$("#prCostOrgId").val(prCostOrgId);
				$("#prCostOrgName").val(prCostOrgName);
				$("#prCostOrgMgr").val(prCostOrgMgr);
				
				$.ajax({
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
				
				if($("#zhanqu").val() == ''){
					alert("请选择战区!");
					$.unblockUI();
					return false;
				}
				
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
			 $("#CheckedAll").click(function() {
		            $('[name=_prCat]:checkbox').prop("checked", this.checked );
		            str();
		     });
			 
			  var str = function() {
		            //测试选中数据
		            var str="你选中的是：\r\n";
		            $('[name=_prCat]:checkbox:checked').each(function(){
		                str+=$(this).val()+"\r\n";
		            })
		            console.log(str);
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
			
		</script> 
    </content>
</body>
</html>
