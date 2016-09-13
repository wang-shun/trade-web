
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />  
<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- Morris -->
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">

<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">


<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" />
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />

<style type="text/css">

</style>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="queryUserId" value="${queryUserId}"/>	

 <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox-content border-bottom clearfix space_box">
            <h2 class="title">
                	无主案件
            </h2>
		    <form method="get" class="form_list">
				<div class="line">
				    <div class="form_content">
						<label class="control-label sign_left">所在区域：</label>
						<aist:dict clazz="select_control sign_right_one"   id="distCode" name="distCode" display="select" defaultvalue="" dictType="yu_shanghai_district" />
					</div>
					<div class="form_content">
							<label class="control-label sign_left_small select_style mend_select">导入时间:</label>
						   	<div id="datepicker" class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd">
								<input id="dtBegin_0" name="dtBegin" class="form-control data_style"  type="text" value="" placeholder="起始日期"> 
								<span class="input-group-addon">到</span>
								<input id="dtEnd_0" name="dtEnd" class="form-control data_style" style="font-size: 13px;" type="text" value="" placeholder="结束日期" />
							</div>
								
					</div>
				</div>
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left">产证地址：</label>
						<input type="text" id="txt_prd_addr" name="txt_prd_addr" class="teamcode input_type" style="width:435px;" placeholder="请输入" >
					</div>
					<div class="form_content space">
					 	<div class="add_btn">		
					        <button type="button" class="btn btn-success" onclick="javascript:loadGrid(1)"> <i class="icon iconfont"></i>查询 </button>	
					        <button type="button" class="btn btn-success" onclick="javascript:clean()"> 清空 </button>	
					        <c:if test="${isBackTeam != true }">
					        <button type="button" class="btn btn-success" onclick="javascript:caseChangeTeam()" id="caseChangeTeamButton" disabled="true"> 案件分配 </button>	
					        </c:if>
					        <c:if test="${isBackTeam == true }">
					           <c:if test="${jobCode!='Senior_Manager' && jobCode!='Manager' }">
					        <button type="button" class="btn btn-success" onclick="javascript:caseChangeTeam()" id="caseChangeTeamButton" disabled="true"> 案件分配 </button>
					           </c:if>	
					        </c:if>
					     </div>
					 </div>
			    </div>
			</form>
		</div>
		
		<div class="row">
				 <div class="col-md-12">
					<div class="table_content"> <form id="form1"> <input type="hidden" id="h_userId" >
						<table class="table table_blue table-striped table-bordered table-hover " id="table_list_1" name="table_list_1" >
						<thead>
						<tr>
						<c:if test="${isBackTeam != true }">
							<th ><input type="checkbox" id="checkAllNot" class="i-checks"  /></th>
						</c:if>
							<th >案件编号</th>
							<th >产证地址</th>
							<th >导入时间</th>
							<th > 经办人</th>
						</tr>
						</thead>
						<tbody id="myCaseList">
				
						</tbody>							
						</table></form>
								
						<div class="text-center page_box" id="pager_list_1">
							<span id="currentTotalPage"><strong class="bold"></strong></span>
							<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
							<div id="pageBar" class="pagination text-center"></div>  
			  			</div>	
	  			</div>	
	  		</div>
  		</div>	
	</div>  
		
		<!-- <div class="row">
            <div class="col-md-12">
               <div class="table_content">	
					<table id="table_list_1"></table>
					<div id="pager_list_1"></div>
				</div>
			</div>
		</div> -->
 <!-- 案件转组 -->
<div id="team-modal-form" class="modal fade" role="dialog" aria-labelledby="team-modal-title" aria-hidden="true">
  <div class="modal-dialog" style="width:700px">
         <div class="modal-content">
             <div class="modal-header">
				 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">× </button>
				 <h4 class="modal-title" id="team-modal-title"> 案件转组 </h4>
			</div>
            <div class="modal-body">
               <div class="row">
               		<form  id="team-form">
                		<div class="form-group">
                     		<label class="col-lg-2 control-label">请选择组别:</label>
                     				<div class="col-lg-8" id="fontTeam"> </div>
                		</div>
      				</form>
   				</div>
         	</div> 
         	<div class="modal-footer">
	   			<button type="button" class="btn btn-success" data-dismiss="modal">关闭 </button>
	   			<button type="button" class="btn btn-success" onclick="javascript:changeCaseTeam()">  提交 </button>
         	</div>
         </div>
     </div>
 </div>  
              
<content tag="local_script"> 

<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>  
<!-- iCheck --> 
<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>	
<script src="${ctx}/js/jquery.blockui.min.js"></script> 
<script src="${ctx}/js/trunk/case/unlocatedCase2.js"></script>
<script src="${ctx}/js/template.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/jquery.json.min.js"></script>
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>

<!-- 分页控件  -->
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>

<script id="yuCuiFontTeamList" type="text/html">
		 <select class="form-control" name="yuTeamCode">
                {{each data as item}}
                      <option value ="{{item.id}}">{{item.orgName}}</option>
                {{/each}}
		</select>
</script>

<script id="template_myCaseList" type= "text/html">
    	{{each rows as item index}}
  			{{if index%2 == 0}}
 		    	<tr class="tr-1">
            {{else}}
            	<tr class="tr-2">
            {{/if}}
				<c:if test="${isBackTeam != true }">
				<td class="center">
					<input type="checkbox" name="ckb_task" onclick="ckbChange();" value="{{item.CASE_CODE}}" style="margin-top: 9px;float: left;"  /> 
							<input type='hidden'  disabled>
							<input type='hidden' name='caseCodes'{{index}} value="{{item.CASE_CODE}}" disabled>
				</td>
				</c:if>
				<c:if test="${isBackTeam == true }">
					           <c:if test="${jobCode!='Senior_Manager' && jobCode!='Manager' }">
					       <td class="center">
					<input type="checkbox" name="ckb_task" onclick="ckbChange();" value="{{item.CASE_CODE}}" style="margin-top: 9px;float: left;"  /> 
							<input type='hidden'  disabled>
							<input type='hidden' name='caseCodes'{{index}} value="{{item.CASE_CODE}}" disabled>
				</td>
					           </c:if>	
			    </c:if>
				<td >
						<p class="big">
							<a href="{{ctx}}/trade-web/case/caseDetail?caseId={{item.PKID}}" target="_blank">
                          		{{item.CASE_CODE}}
							</a>
                         </p>
                        <p>
                          <i class="tag_sign">c</i>
                                {{item.CTM_CODE}}                
                       </p>
				</td>
				<td >


{{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
<p class="demo-top" title="{{item.PROPERTY_ADDR}}">
{{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
{{else}}</p><p>
{{item.PROPERTY_ADDR}}
{{/if}}					 
						</p>
 							<p >
								 <i class="salesman-icon"> </i>
								 
{{if item.ORG_NAME !="" && item.ORG_NAME !=null && item.ORG_NAME.length>11}}		
{{if item.AGENT_NAME !=null && item.AGENT_NAME.length > 2}}	
<a class="demo-top" title="{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.ORG_NAME}}" >		
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.ORG_NAME.substring(0,10)}}...
{{else}}
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.ORG_NAME.substring(0,11)}}...
{{/if}}					
{{else}}</a><a>
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.ORG_NAME}}
{{/if}}	
								 </a>
							</p>


				</td>
				<td >
						{{if item.IMPORT_TIME!=null}}
						  <p class="smll_sign">
                                    <i class="sign_normal">导</i>
									{{item.IMPORT_TIME}}
    				 	  </p>

						{{else}}
                           <p class="sign_grey">
                                   
    						</p>

						{{/if}}

				</td>
				<td class="center">
                        <p>区经：{{item.LEADER}}</p>
                        <p>区董：{{item.qyzjNAME}}</p>
                 </td>
				</tr>
       {{/each}}
</script>
<script>
	/**
	 * 选择组别modal
	 * @param data
	 */
	function showTeamModal(data){
		 var d = {
			data : data	 
		 }
		 var fontTeam = template('yuCuiFontTeamList', d); 
	     $("#fontTeam").empty();
	     $("#fontTeam").html(fontTeam);
		 $('#team-modal-form').modal("show");
	}
	
	/**
	 * 案件转组初始化
	 */
	function caseChangeTeam(){
		//var url = "/case/getOrgTeamList";
		//var url = "/setting/getYuCuiTeamList";
		var url = "/case/getAllTeamList";
		var ctx = $("#ctx").val();
		url = ctx + url;
		
		$.ajax({
			cache : false,
			async:true,
			type : "POST",
			url : url,
			dataType : "json",
			timeout: 10000,
		    data : "", 
			success : function(data) {
				showTeamModal(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}); 
	}
	
	function changeCaseTeam(){
		//var orgName =$('input[name="teamRadio"]:checked').parent().text();
		var orgName =$('select[name="yuTeamCode"]').find("option:selected").text();
		if(confirm("您是否确认分配给"+orgName+"?")){

	    	//var orgId =$('input[name="teamRadio"]:checked').val();
			var orgId =$('select[name="yuTeamCode"]').val();
			var url = "/case/bindCaseTeam";
			var ctx = $("#ctx").val();
			url = ctx + url;
			
			var caseInfoList = new Array();
			var sendData = $("input[name='ckb_task']:checked").serializeArray();
			
			//var sendData=$('#form1').serializeArray();
			
			for(var index in sendData){
			     var obj = sendData[index];
			     //alert(JSON.stringify(obj));
			     if(obj.name=='ckb_task'){
				     var toCaseInfo = {
					   caseCode	: obj.value,
					   grpCode :  ''
				    }
				     
				     caseInfoList.push(toCaseInfo);
			     }
			 }
			
			var teamTransferVO = {
			   caseInfoList	: caseInfoList,
			   orgId : orgId
			} 
			teamTransferVO = $.toJSON(teamTransferVO);
			$.ajax({
				cache : false,
				async:true,
				type : "POST",
				url : url,
				dataType : "json",
				contentType: "application/json; charset=utf-8" ,
				timeout: 10000,
			    data : teamTransferVO, 
			    beforeSend:function(){  
					$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
					$(".blockOverlay").css({'z-index':'9998'});
	            },  
	            complete: function() {  
	                $.unblockUI();   
	                if(status=='timeout'){//超时,status还有success,error等值的情况
		          	  Modal.alert(
					  {
					    msg:"抱歉，系统处理超时。后台仍可能在处理您的请求，请过2分钟后刷新页面查看您的客源数量是否改变"
					  });
			  		 $(".btn-primary").one("click",function(){
			  				parent.$.fancybox.close();
			  			});	 
			                }
			            } , 
				success : function(data) {
					if(data.success){
						alert("分配成功");
						$('#team-modal-form').modal("hide");
						//jqGrid reload
						$("#table_list_1").trigger('reloadGrid');
						$("#checkAllNot").attr('checked',false);
						loadGrid(1);
					}else{
						alert(data.message);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					
				}
			}); 
		}
	}
</script>	

</content>
</body>
</html>
