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
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<!-- 上传相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<!-- bank  select -->
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">


<script type="text/javascript">
	var ctx = "${ctx}";
	var coworkService = "${firstFollow.coworkService }";
	var teamProperty = "${teamProperty}";
	var caseProperty = "${firstFollow.caseProperty}";
	var cooperationUser = "${firstFollow.cooperationUser}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
<style type="text/css">
.radio.radio-inline > label{
	margin-left:10px;}
.radio.radio-inline > input{
	margin-left:10px;}
.checkbox.checkbox-inline > div{
	margin-left:25px;}
.checkbox.checkbox-inline > input{
	margin-left:20px;}
.product-type span{margin:0 5px 5px 0}
.product-type .selected,.product-type span:hover{border-color:#f8ac59}

#corss_area{padding:0;}
#corss_area select{float:right;height:34px;border-radius:2px;margin-left:20px;}
.pb10{padding-bottom:15px;}
.select_control{
	color: inherit;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
 <jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>

	<div class="">
		<div class="row wrapper white-bg new-heading ">
			<div class="pl10">
				<h2 class="newtitle-big">
					贷款需求选择
				</h2>
				<div class="mt20">
					<button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
						<i class="iconfont icon">&#xe600;</i> 在途单列表
					</button>
					<button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
						<i class="iconfont icon">&#xe63f;</i> 案件视图
					</button>
				</div>
			</div>
		</div>

		<div class="ibox-content border-bottom clearfix space_box noborder">

			<form method="get" class="form-horizontal" id="firstFollowform">
			<%--环节编码 --%>
			<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
			<!-- 交易单编号 -->
			<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
			<!-- 流程引擎需要字段 -->
			<input type="hidden" id="taskId" name="taskId" value="${taskId }">
			<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">

			<input type="hidden" id="pkid" name="pkid" value="${loanReleasePlan.pkid}">
			<h2 class="newtitle title-mark">填写任务信息</h2>
			<div class="form_list">
				<div class="marinfo">
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small">客户贷款需求</label>
							<aist:dict clazz="select_control" id="mortageService" name="mortageService"
									   display="select" defaultvalue="0" dictType="mortage_service" />
						</div>
						<div class="form_content" id="div_releasePlan">
							<label class="control-label sign_left_small select_style mend_select">
								预计放款时间
							</label>
							<div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
								<input class="input_type yuanwid datatime" type="text" name="estPartTime" id="estPartTime" disabled="disabled"
									   value="<fmt:formatDate  value='${loanReleasePlan.estPartTime}' type='both' pattern='yyyy-MM-dd'/>">
							</div>
						</div>
					</div>
					<div class="line" id="hzxm">

					</div>

					<div class="line">
						<p class="ml20">请注意：当您选择纯公积金贷款人，您需要选择一位合作人；当您选择其它贷款时，默认的服务执行人为您自己。</p>
					</div>
				</div>
			</div>
			</form>

			<div class="view-content" id="caseCommentList"> </div>

			<div class="form-btn">
				<div class="text-center">
					<%--<button  class="btn btn-success btn-space">保存</button>--%>
					<button class="btn btn-success btn-space" onclick="submit()">提交</button>
				</div>
			</div>
		</div>

	</div>

<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<content tag="local_script"> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/transjs/task/loanlostApprove.js"></script>
	<script src="${ctx}/transjs/task/showAttachment.js"></script> 
	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1.0.1"></script> 
	<!-- Custom and plugin javascript -->
	<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 

	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script>

    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
	<!-- bank select -->
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	
	<script src="${ctx}/transjs/task/follow.pic.list.js"></script>
	<script src="${ctx}/js/plugins/jquery.custom.js"></script>
		<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<!-- 改版引入的新的js文件 -->
	<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
	<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
	<script>
		$(document).ready(function(){
			/*根据贷款服务项，设置默认合作项目*/
			$("#mortageService").change(function(){
				mortageService();
			});
			$('#div_releasePlan').hide();

			$('#div_releasePlan .input-group.date').datepicker({
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				autoclose : true
			});
			
		});
	
		
        function mortageService() {
			var value = $("#mortageService").val();
			if(value!='0'){
				$("#estPartTime").removeProp('disabled');
				$("#estPartTime").removeAttr('disabled');
				 $('#div_releasePlan').show();
			}else{
				$("#estPartTime").prop('disabled','disabled');//防止后台拿到数据
				$('#div_releasePlan').hide();
			}
			$("#hzxm").html("");
			if(value=='2'){
				var url = "${ctx}/task/firstFollow/queryMortageServiceByServiceCode";
				$.ajax({
					cache : false,
					async : true,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					data : {"serviceCode":'3000400201'},
					success : function(data) {
						txt = "<div class='row'>";
					    txt += "<div class='col-xs-2 col-md-3'>";
					    txt += "<div class=''  name='isYouXiao'>";
						txt += "<input type='hidden' name='coworkService' value='"+data.dic.code+"'/>";
						txt += "</div>";
						txt += "</div>";
						txt += "</div>";
						txt += "<div class='col-xs-12 col-md-4'>";
						txt += "<div class='form-group' id='data_1' name='isYouXiao'>";
						txt += "<label class='col-md-3 control-label sign_left_small' style='margin-right: 0px;padding-left: 0px; padding-right: 0px;'><font color='red'>*</font>合作顾问</label>";
						txt += "<div class='col-md-8' style='padding-left: 24px;'>";
						txt += "<select class='form-control m-b' name='unCrossPartner' id='cooperationUser"+index+"'>";
						txt += "<option value='0'>----未选择----</option>";
						$.each(data.users, function(j, user){
							if(cooperationUser==user.id){
								txt += "<option selected='selected' value='"+user.id+"'>"+user.realName+"("+user.orgName+"):"+user.count+"件</option>";
							}else{
								txt += "<option value='"+user.id+"'>"+user.realName+"("+user.orgName+"):"+user.count+"件</option>";	
							}
						});
						if($.trim(data.orgcode)!='033F045'){/* 浦东合作顾问选中台*/
							txt += "<option value='-1'>----跨区选择----</option>";
						}
						txt += '</select>';
						txt += "<input type='hidden'  id='partner"+index+"' name='partner' value=''/>";
						txt += '</div></div>';
						txt += "</div>";
						txt += "</div>";
						$("#hzxm").append(txt);
						$('#partner'+index).val($('#cooperationUser'+index).find(':selected').val());
						
						/*点击跨区合作选项*/
			        	var partner = $('select[name="unCrossPartner"]');
			        	partner.bind("change", function(){
			        		if(partner.find(":selected").val()=="-1"){
			        			$('#partner'+index).val('');
			        			//alert($('#partner'+index).val());
								if($("#corss_area").length==0){
			        				crossAreaCooperation();
								}
			        		}else{
			        			$('#partner'+index).val($('#cooperationUser'+index).find(':selected').val());
			        			//alert($('#partner'+index).val());
			        			if($("#corss_area").length>0){
			        				removeCrossAreaCooperation();
			        			}
							}	
			        	});
						
					},
					
					error : function(errors) {
						window.wxc.error("数据出错。");
					}
				});
			}
		}

		/*生成跨区合作选项框*/
        function crossAreaCooperation(){
			
			var url = "${ctx}/task/firstFollow/getCrossAeraCooperationItems";
			var corsstxt = "";
			corsstxt += "<div class='col-md-4' id='corss_area'>";
			corsstxt += "<select name='crossPartner' id='crossConsult"+index+"'>";
			corsstxt += "<option value='0'>----人员----</option>";
			corsstxt += '</select>';
			corsstxt += "<select name='crossOrg' id='crossOrg"+index+"'>";
			corsstxt += "<option value='0'>----组别----</option>";
			corsstxt += '</select>';				
			corsstxt += "<select id='crossDistrict"+index+"'>";
			corsstxt += "<option value='0'>----部门----</option>";
			corsstxt += '</select></div>';
			$("#hzxm").append(corsstxt);
			
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				success : function(data) {
					
					/*三级联动*/
					var district = $('#crossDistrict'+index);
					var org = $('#crossOrg'+index);
					var consult = $("#crossConsult"+index);
					var districtStr="";
					
					$.each(data.cross,function(j,items){
						districtStr += "<option value='"+ items.districtId+"'>" + items.districtName+"</option>";
					});
					district.empty().append("<option value='0'>----部门----</option>"+districtStr);
					
					district.bind("change", function(){
							var orgStr="";
							var myIndex = district.find(":selected").index()-1;
							if(myIndex>=0){
							$.each(data.cross[myIndex].orgs, function(i, items){
								orgStr += "<option value='"+items.orgId+"'>"+items.orgName+"</option>";
							})
							org.empty().append("<option value='0'>----组别----</option>"+orgStr);
							var val1 = org.find(":selected").val();
							if(val1!='0'){
								changeConsult();
							}
						}else{
							org.empty().append("<option value='0'>----组别----</option>");
							consult.empty().append("<option value='0'>----人员----</option>");							
						}
					});
					
					org.bind("change", changeConsult);
					function changeConsult(){
						var consultStr="";
						var index1 = district.find(":selected").index()-1;
						var index2 = org.find(":selected").index()-1;
						if(index2>=0){
							$.each(data.cross[index1].orgs[index2].userItems, function(k,items) {
								consultStr += "<option value='"+items.id+"'>"+items.realName+"("+items.count+"件)</option>";
							});
							consult.empty().append("<option value='0'>----人员----</option>"+consultStr);
							if(consultStr == ""){
								consult.empty();
								consult.append("<option value='0'>----人员----</option>");
							}
							getVals();
						}else{
							consult.empty().append("<option value='0'>----人员----</option>");
						}
					}
					
					consult.bind("change", getVals);
					/*改变隐藏框的值*/
					function getVals(){
						var guwen=consult.find(':selected').val();
						
						if(guwen!='0'){
							$('#partner'+index).val(guwen);
						}
						//alert($('#partner'+index).val());
					}
				},
				error : function(errors) {
					window.wxc.error("数据出错。");
				}
			});
        }
		
		/*删除跨区合作的DOM节点*/
		function removeCrossAreaCooperation(){
			$("#corss_area").remove();
		}
		
		/**提交数据*/
		function submit() {

			save(true);
		}

		/**保存数据*/
		function save(b) {
			if(!checkForm()) {
				return;
			}
			var jsonData = $("#firstFollowform").serializeArray();
			var 
				url = "${ctx}/task/mortgageSelect/submit";
		
			
			$.ajax({
				cache : true,
				async : true,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				data : jsonData,
    		    beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
                complete: function() {  

                    $.unblockUI();  
                	if(b){ 
                        $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'1900'}}); 
    				    $(".blockOverlay").css({'z-index':'1900'});
                	}   
                     if(status=='timeout'){//超时,status还有success,error等值的情况
    	          	  Modal.alert(
    				  {
    				    msg:"抱歉，系统处理超时。"
    				  });
    		  		 $(".btn-primary").one("click",function(){
    		  				parent.$.fancybox.close();
    		  			});	 
    		                } 
    		            } ,  
				success : function(data) {
						window.wxc.succes("数据保存成功！",{"wxcOk":function(){
							caseTaskCheck();
						}});
						
						//$('#case-task-modal-form').modal("show");
				},
				error : function(errors) {
					 window.wxc.error("数据保存出错");
					 $.unblockUI();
				}
			});
		}
		
		/*double 验证*/
	    function checkNum(obj) { 
	        //先把非数字的都替换掉，除了数字和.
	        obj.value = obj.value.replace(/[^\d.]/g,"");
	        //必须保证第一个为数字而不是.
	        obj.value = obj.value.replace(/^\./g,"");
	        //保证只有出现一个.而没有多个.
	        obj.value = obj.value.replace(/\.{2,}/g,".");
	        //保证.只出现一次，而不能出现两次以上
	        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	     }  

		//验证控件checkUI();
		function checkForm() {
			
				if($('select[id="mortageService"] option:selected').val()=='2'&&$('select[name="unCrossPartner"]').size()==0){
					 window.wxc.alert("正在加载合作项目!");
					 return false;
				}
				if($('#mortageService').val()!='0'&& $('#estPartTime').val()==''){
					window.wxc.alert('请选择预计放款时间');
					return false;
				}
				
				var flag = false;
				$('select[name="unCrossPartner"] option:selected').each(function(i,item){
					if(item.value == "0"){
						 window.wxc.alert("合作顾问为必选项!");
//	 					 item.focus();
						 flag = true;
						 return false;
					}else if(item.value == "-1"){
						$('#consult'+index+' option:selected').each(function(j,item2){
							if(item2.value == "0"){
								 window.wxc.alert("跨区合作顾问未选择!");
								 flag = true;
								 return false;
							}
						});						
					}
				});
				
				if(flag)return false;
				
			return true;
		}
	</script> 
	</content>
</body>


</html>