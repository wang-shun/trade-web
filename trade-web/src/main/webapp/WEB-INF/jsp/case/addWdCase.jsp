<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>新增外单</title>
<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
<link rel="stylesheet" href="${ctx}/static/css/animate.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/static/css/style.css" rel="stylesheet">
<!-- stickUp fixed css -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">
<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
<link rel="stylesheet" href="${ctx}/static_res/trans/css/common/report.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/addOutlist.css">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">
</head>

<body>
<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="flag" value="${flag}" />
<input type="hidden" id="caseCode" value="${caseCode}" />
<input type="hidden" id="commSubject" value="${caseMergeVo.commSubject}" />
<input type="hidden" id="type" value="${type}" />
<div class="wrapper wrapper-content animated fadeInUp">
    <div class="ibox-content" id="reportFive">
        <form  id="saveCaseInfo">
<%--         <form  action="${ctx}/caseMerge/saveWdCaseInfo/${flag}"  method="post"  id="saveCaseInfo"> --%>
<%--  <c:if test="${type eq 'edit'}" >
	<input type="hidden" id="caseCode" value="${caseMergeVo.caseCode}" />
 </c:if> --%>
        <div method="get" class="form_list">
            <div class="title"> 新建外单 </div>
        </div>
        <div  class="form_list mt20" >
            <div class="line">
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	房屋地址
                    </label>
                    <input type="text" placeholder="请输入房屋地址"  name="propertyAddr"  id="propertyAddr" class="select_control home-adress" value="${caseMergeVo.propertyAddr}">
                    <input type="hidden"   name="distCode"  id="distCode" class="select_control home-adress" value="${caseCode}" >
                </div>
            </div>
            <div class="line">
            	
	                <div class="form_content">
	                    <label class="control-label sign_left_small">
	                        	推荐人
	                    </label>
	                    <input type="text" class="select_control sign_right_one" name="guestNameRecommend" id="guestNameRecommend" value="${caseMergeVo.guestNameRecommend[0]}">
	                </div>
	          
	            <c:if test="${type eq 'edit'}" >
	            <c:forEach items="${caseMergeVo.pkidRecommend}" var="caseMerge" varStatus="status2">
	                <div class="form_content">
	                    <label class="control-label sign_left_small" >
	                        	推荐人电话
	                    </label>
	                    <input type="text" class="select_control sign_right_one" name="guestPhoneRecommend" id="guestPhoneRecommend" value="${caseMergeVo.guestPhoneRecommend[status2.index]}">
	                </div>
				</c:forEach> 
				</c:if>
	            <c:if test="${type eq 'add'}" >
	                <div class="form_content">
	                    <label class="control-label sign_left_small" >
	                        	推荐人电话
	                    </label>
	                    <input type="text" class="select_control sign_right_one" name="guestPhoneRecommend" id="guestPhoneRecommend" value="">
	                </div>
				</c:if>
                  
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	合作来源
                    </label>
                    <select class="select_control sign_right_one"  onChange="this.value" name="sourceOfCooperation">
                        <option <c:if test="${caseMergeVo.sourceOfCooperation eq ''}"> selected="selected" </c:if> value=""> 请选择 </option>
                        <option <c:if test="${caseMergeVo.sourceOfCooperation eq '小中介'}"> selected="selected" </c:if> value="小中介"> 小中介 </option>
                        <option <c:if test="${caseMergeVo.sourceOfCooperation eq '一手'}"> selected="selected" </c:if> value="一手"> 一手 </option>
                        <option <c:if test="${caseMergeVo.sourceOfCooperation eq '联动'}"> selected="selected" </c:if> value="联动"> 联动 </option>
                        <option <c:if test="${caseMergeVo.sourceOfCooperation eq '朋友介绍'}"> selected="selected" </c:if> value="朋友介绍"> 朋友介绍 </option>
                        <option <c:if test="${caseMergeVo.sourceOfCooperation eq '老客户'}"> selected="selected" </c:if> value="老客户"> 老客户 </option>
                        <option <c:if test="${caseMergeVo.sourceOfCooperation eq '中原门店'}"> selected="selected" </c:if> value="中原门店"> 中原门店 </option>
                    </select>
                </div>
            </div>
            <c:if test="${type eq 'edit'}" >
            <c:forEach items="${caseMergeVo.pkidUp}" var="caseMerge" varStatus="status2"> 
            <div class="line" id="topHome">
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	上家姓名
                    </label>
                    <input type="text" class="select_control sign_right_one" name="guestNameUp" id="guestNameUp" value="${caseMergeVo.guestNameUp[status2.index]}">
                    <input type="hidden" class="select_control sign_right_one" name="pkidUp" id="pkidUp" value="${caseMergeVo.pkidUp[status2.index]}">
                </div>
             
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	上家电话
                    </label>
                    <input type="text" class="select_control sign_right_one" name="guestPhoneUp" id="guestPhoneUp" value="${caseMergeVo.guestPhoneUp[status2.index]}">
                </div>
            </div>
			</c:forEach> 
			</c:if>
            <c:if test="${type eq 'add'}" >
            <div class="line" id="topHome">
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	上家姓名
                    </label>
                    <input type="text" class="select_control sign_right_one" name="guestNameUp" id="guestNameUp" value="">
                    <input type="hidden" class="select_control sign_right_one" name="pkidUp" id="pkidUp" value="">
                </div>
             
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	上家电话
                    </label>
                    <input type="text" class="select_control sign_right_one" name="guestPhoneUp" id="guestPhoneUp" value="">
                </div>
            </div>
			</c:if>
			 
            <div class="line mb20">
                <a href="javascript:void(0)" style="margin-left:126px;" onclick="getAtr(this)">添加</a>
            </div>
            
            <c:if test="${type eq 'edit'}" >
            <c:forEach items="${caseMergeVo.pkidDown}" var="caseMerge" varStatus="status2">
            <div class="line" id="downHome">
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	下家姓名
                    </label>
                    <input type="text" class="select_control sign_right_one" name="guestNameDown" id="guestNameDown" value="${caseMergeVo.guestNameDown[status2.index]}">
                     <input type="hidden" class="select_control sign_right_one" name="pkidDown" id="pkidDown" value="${caseMergeVo.pkidDown[status2.index]}">
                </div>
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	下家电话
                    </label>
                    <input type="text" class="select_control sign_right_one" name="guestPhoneDown" id="guestPhoneDown" value="${caseMergeVo.guestPhoneDown[status2.index]}">
                </div>
            </div>
			</c:forEach> 
			</c:if>
            <c:if test="${type eq 'add'}" >
            <div class="line" id="downHome">
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	下家姓名
                    </label>
                    <input type="text" class="select_control sign_right_one" name="guestNameDown" id="guestNameDown" value="">
                     <input type="hidden" class="select_control sign_right_one" name="pkidDown" id="pkidDown" value="">
                </div>
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	下家电话
                    </label>
                    <input type="text" class="select_control sign_right_one" name="guestPhoneDown" id="guestPhoneDown" value="">
                </div>
            </div>
			</c:if>
            
            <div class="line mb20">
                <a href="javascript:void(0)" style="margin-left:126px;"  onclick="getNext(this)">添加</a>
            </div>
            <div class="line">
                <div class="row product-type">
                    <div class="form_content">
                        <label class="soutlist-btns-label pull-left"> 服务项目 </label>
                        <div class="pull-left outlist-btns" id="Tnumber">
                            <span class="btn btn-white" onclick="" name="commSubject" value="签约"> 签约 </span>
                            <span class="btn btn-white" onclick="" name="commSubject" value="过户"> 过户 </span>
                            <span class="btn btn-white" onclick="" name="commSubject" value="贷款"> 贷款 </span>
                            <span class="btn btn-white" onclick="" name="commSubject" value="其它"> 其它  </span> 
                        </div>
                    </div>
                </div>
            </div>
            <div class="line hide-style" id="elseInput" style="margin-left: 127px;">
                <div class="form_content"> 
                    <input class="select_control sign_right_one" placeholder="请输入服务项目" name="commSubject"  /> 
                </div>
            </div>
            <div class="line">
                <div class="form_content">
                    <label class="control-label sign_left_small">服务价格</label> 
                    <input class="select_control sign_right_one" placeholder="" id="commCost" name="commCost" value="${caseMergeVo.commCost}">
                   <span class="date_icon" >元</span>
                </div>
            </div>
            <div class="line">
                <div>
                     <div class="ibox-title" style="height: auto;border:0;padding-left:0;" id="aboutInfo">
						<c:choose>
							<c:when test="${accesoryList!=null}">
								<label class="control-label sign_left_small">上传附件</label>
								<div class="table-box" id="addWdCaseContract" style="margin-left: 127px;margin-top: -27px;"></div>
							</c:when>
							<c:otherwise>
								<label class="control-label sign_left_small pull-left">上传附件</label>
								<p style=" float: left;  margin-left: 5px; font-weight: bold; ">无需上传备件</p>
							</c:otherwise>
						</c:choose>
					</div>
                </div>
            </div>
        </div>
        <div class="text-center mt40 mb30">
        
        	 <c:if test="${type eq 'edit'}" >
             <a onclick="saveRe()" class="btn btn-success">修改确认</a>
			 </c:if>
        	 <c:if test="${type eq 'add'}" >
             <a onclick="sumbitRe()" class="btn btn-success">创建</a>
			 </c:if>
             
           <!--  <button type="reset" id="loanLostCleanButton" class="btn btn-grey">清&nbsp;&nbsp;空</button> -->
        </div>
         </form>
    </div>
    <div class="ibox-content" style="height:250px;background:#f3f3f4;border-top:none;">
    </div>
</div>
<content tag="local_script"> 
<script src="${ctx}/js/trunk/case/addWdCase.js"></script><%-- 
<script src="${ctx}/js/trunk/case/jquery.serializejson.js"></script> --%>
<script>
    var $else = $('#elseInput'); 
    var $btn_spans = $('.outlist-btns span');
    $btn_spans.click(function() {
        $(this).toggleClass('out-btn-select');
    });
    $btn_spans.eq(3).click(function() {
        $else.toggle();
    });
    
     
</script>
</content>
<content tag="local_require">
<script>
  	/**
  	* 添加附件
  	*/
  	var fileUpload;
    require(['main'], function() {
    	requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination'],function($,aistFileUpload){
    		fileUpload = aistFileUpload;
    		
    		fileUpload.init({
	    		caseCode : $('#caseCode').val(),
	    		partCode : "AddWdCase",
	    		fileUploadContainer : "addWdCaseContract"
	    	}); 
	    });
    });
    
  
    
    jQuery(document).ready(function() {
    	spansClick($("#commSubject").val()); 
	    $("span[name='commSubject']").click(function(){								
			var id = $(this).attr("id");								
			$("span[id='"+id+"']").changeSelect();
		});
    });
    
    function spansClick(list){
    	var t = list.split(",");
    	var btnwhite =  $('#Tnumber .btn-white');debugger;
    	for(i=0;i<t.length;i++){
    		$('#Tnumber .btn-white[value="'+t[i]+'"]').addClass("out-btn-select");
    	}
    } 
    
</script>
</content>
</body>
</html>