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
<input type="hidden" id="remarks" value="${caseMergeVo.remarks}" />
<input type="hidden" id="type" value="${type}" />
<input type="hidden" id="sumUp" value="${caseMergeVo.tgGuestInfoUp.size()}" />
<input type="hidden" id="sumDown" value="${caseMergeVo.tgGuestInfoDown.size()}" />
<input type="hidden" id="subjectOther" value="${caseMergeVo.commSubjectOther}" />
<div class="wrapper wrapper-content animated fadeInUp">
    <div class="ibox-content" id="reportFive">
        <form  id="saveCaseInfo">
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
	                    <input type="text" class="select_control sign_right_one" name="recommendUsername" id="recommendUsername" value="${caseMergeVo.recommendUsername}">
	                </div>
	          
	            <c:if test="${type eq 'edit'}" >
	            <c:forEach items="${caseMergeVo.recommendPhone}" var="caseMerge" varStatus="status2">
	                <div class="form_content">
	                    <label class="control-label sign_left_small" >
	                        	推荐人电话
	                    </label>
	                    <input type="text" class="select_control sign_right_one" name="recommendPhone" id="recommendPhone" value="${caseMergeVo.recommendPhone}">
	                </div>
				</c:forEach> 
				</c:if>
	            <c:if test="${type eq 'add'}" >
	                <div class="form_content">
	                    <label class="control-label sign_left_small" >
	                        	推荐人电话
	                    </label>
	                    <input type="text" class="select_control sign_right_one" name="recommendPhone" id="recommendPhone" value="">
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
            <c:forEach items="${caseMergeVo.tgGuestInfoUp}" var="caseMerge" varStatus="status2"> 
            <div class="line" id="topHome">
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	上家姓名
                    </label>
                    <input type="text" class="select_control sign_right_one" name="tgGuestInfoUp[${status2.index }].guestName" id="tgGuestInfoUp[0].guestName" value="${caseMerge.guestName}">
                    <input type="hidden" class="select_control sign_right_one" name="tgGuestInfoUp[${status2.index }].pkid" id="tgGuestInfoUp[0].pkid" value="${caseMerge.pkid}">
                </div>
             
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	上家电话
                    </label>
                    <input type="text" class="select_control sign_right_one" name="tgGuestInfoUp[${status2.index }].guestPhone" id="tgGuestInfoUp[0].guestPhone" value="${caseMerge.guestPhone}">
                </div>
                <c:if test="${status2.index>0 }" >
                	<a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a>
                </c:if>
            </div>
			</c:forEach> 
			</c:if>
            <c:if test="${type eq 'add'}" >
            <div class="line" id="topHome">
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	上家姓名
                    </label>
                    <input type="text" class="select_control sign_right_one" name="tgGuestInfoUp[0].guestName" id="tgGuestInfoUp[0].guestName" value="">
                    <input type="hidden" class="select_control sign_right_one" name="tgGuestInfoUp[0].pkid" id="tgGuestInfoUp[0].pkid" value="">
                </div>
             
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	上家电话
                    </label>
                    <input type="text" class="select_control sign_right_one" name="tgGuestInfoUp[0].guestPhone" id="tgGuestInfoUp[0].guestPhone" value="">
                </div>
            </div>
			</c:if>
			 
            <div class="line mb20" id="upDiv">
                <a href="javascript:void(0)" style="margin-left:126px;" onclick="getAtr()">添加</a>
            </div>
            
            <c:if test="${type eq 'edit'}" >
            <c:forEach items="${caseMergeVo.tgGuestInfoDown}" var="caseMerge" varStatus="status2">
            <div class="line" id="downHome">
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	下家姓名
                    </label>
                    <input type="text" class="select_control sign_right_one" name="tgGuestInfoDown[${status2.index }].guestName" id="tgGuestInfoDown[${status2.index }].guestName" value="${caseMerge.guestName}">
                     <input type="hidden" class="select_control sign_right_one" name="tgGuestInfoDown[${status2.index }].pkid" id="tgGuestInfoDown[${status2.index }].pkid" value="${caseMerge.pkid}">
                </div>
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	下家电话
                    </label>
                    <input type="text" class="select_control sign_right_one" name="tgGuestInfoDown[${status2.index }].guestPhone" id="tgGuestInfoDown[${status2.index }].guestPhone" value="${caseMerge.guestPhone}">
                </div>
                <c:if test="${status2.index>0 }" >
                	<a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a>
                </c:if>
            </div>
			</c:forEach> 
			</c:if>
            <c:if test="${type eq 'add'}" >
            <div class="line" id="downHome">
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	下家姓名
                    </label>
                    <input type="text" class="select_control sign_right_one" name="tgGuestInfoDown[0].guestName" id="tgGuestInfoDown[0].guestName" value="">
                     <input type="hidden" class="select_control sign_right_one" name="tgGuestInfoDown[0].pkid" id="tgGuestInfoDown[0].pkid" value="">
                </div>
                <div class="form_content">
                    <label class="control-label sign_left_small">
                        	下家电话
                    </label>
                    <input type="text" class="select_control sign_right_one" name="tgGuestInfoDown[0].guestPhone" id="tgGuestInfoDown[0].guestPhone" value="">
                </div>
            </div>
			</c:if>
            
            <div class="line mb20" id="downDiv">
                <a href="javascript:void(0)" style="margin-left:126px;"  onclick="getNext()">添加</a>
            </div>
            <div class="line">
                <div class="row product-type">
                    <div class="form_content">
                        <label class="soutlist-btns-label pull-left"> 服务项目 </label>
                        <div class="pull-left outlist-btns" id="Tnumber">
                            <span class="btn btn-white" onclick="" name="remarks" value="签约"> 签约 </span>
                            <span class="btn btn-white" onclick="" name="remarks" value="过户"> 过户 </span>
                            <span class="btn btn-white" onclick="" name="remarks" value="贷款"> 贷款 </span>
                            <span class="btn btn-white" onclick="" name="remarks" value="其他"> 其他  </span> 
                        </div>
                    </div>
                </div>
            </div>
           
            <div class="line hide-style"  id="elseInput" style="margin-left: 127px;">
                <div class="form_content"> 
                    <input class="select_control sign_right_one remarks" placeholder="请输入服务项目" name="commSubjectOther" id="commSubjectOther" value="${caseMergeVo.commSubjectOther}"  /> 
                    <input type="hidden" name="remarks" id="remarks" value=""  /> 
                </div>
            </div>
            
            <div class="line">
                <div class="form_content">
                    <label class="control-label sign_left_small">应收服务费</label> 
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
 * 上家信息  动态添加
 */
var indexUp =parseInt($("#type").val()=="add"?1:$("#sumUp").val());
var indexDown =parseInt($("#type").val()=="add"?1:$("#sumDown").val());

function getAtr(){
    var str='';
    str +=  '<div class="line">'
        +   '<div class="form_content">'
        +   '<input type="hidden" class="select_control sign_right_one" name="tgGuestInfoUp['+indexUp+'].pkidUp" id="tgGuestInfoUp['+indexUp+'].pkidUp" value="">'
        +   '<label class="control-label sign_left_small mar24">'
        +   '上家姓名'
        +   '</label>'
        +   '<input type="text" class="select_control sign_right_one" name="tgGuestInfoUp['+indexUp+'].guestName" id="tgGuestInfoUp['+indexUp+'].guestName" value="">'
        +   ' </div>'
        +   '<div class="form_content">'
        +   '<label class="control-label sign_left_small mar24">'
        +   '上家电话'
        +   '</label>'
        +   '<input type="text" class="select_control sign_right_one" name="tgGuestInfoUp['+indexUp+'].guestPhone" id="tgGuestInfoUp['+indexUp+'].guestPhone" value="">'
        +   '</div>'
        +   '<a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a>'
        +   '</div>';
    //$("#topHome").after(str);
    $("#upDiv").before(str);
    indexUp++;
	$("#sumUp").val(sumUp);
}
/**
 * 下家信息	动态添加
 * @param obj
 */
function getNext(){
    var str='';
    str +=  '<div class="line">'
        +   '<div class="form_content">'
        +   ' <input type="hidden" class="select_control sign_right_one" name="tgGuestInfoDown['+indexDown+'].pkidUp" id="tgGuestInfoDown['+indexDown+'].pkidUp" value="">'
        +   '<label class="control-label sign_left_small mar24">'
        +   '下家姓名'
        +   '</label>'
        +   '<input type="text" class="select_control sign_right_one"  name="tgGuestInfoDown['+indexDown+'].guestName" id="tgGuestInfoDown['+indexDown+'].guestName" value="">'
        +   ' </div>'
        +   '<div class="form_content">'
        +   '<label class="control-label sign_left_small mar24">'
        +   '下家电话'
        +   '</label>'
        +   '<input type="text" class="select_control sign_right_one" name="tgGuestInfoDown['+indexDown+'].guestPhone"  id="tgGuestInfoDown['+indexDown+'].guestPhone" value="" >'
        +   '</div>'
        +   '<a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a>'
        +   '</div>';
    //$("#downHome").after(str);
    $("#downDiv").before(str);
    indexDown++;
	$("#sumDown").val(sumDown);
}

/*上下家信息 删除*/
function getDel(k){
    $(k).parents('.line').remove();
}

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
    	spansClick($("#remarks").val()); 
	    $("span[name='remarks']").click(function(){								
			var id = $(this).attr("id");								
			$("span[id='"+id+"']").changeSelect();
		});
	    
	    var other = $("#subjectOther").val();
	    if(null !=other && other.length>0){
	    	spansShow();
	    }
	    
    });
    
    function spansClick(list){
    	var t = list.split(",");
    	var btnwhite =  $('#Tnumber .btn-white');
    	for(i=0;i<t.length;i++){
    		$('#Tnumber .btn-white[value="'+t[i]+'"]').addClass("out-btn-select");
    	}
    } 
    function spansShow(){
    	var $else = $('#elseInput'); 
   	    
   	        $else.toggle();
   	    
    }
</script>
</content>
</body>
</html>