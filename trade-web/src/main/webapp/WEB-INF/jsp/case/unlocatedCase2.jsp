
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

<style type="text/css">

.hint { position: relative; display: inline-block; }

.hint:before, .hint:after {
	position: absolute;
	opacity: 0;
	z-index: 1000000;
	-webkit-transition: 0.3s ease;
	-moz-transition: 0.3s ease;
	pointer-events: none;
}		
.hint:hover:before, .hint:hover:after {
	opacity: 1;
}
.hint:before {
	content: '';
	position: absolute;
	background: transparent;
	border: 6px solid transparent;
	position: absolute;
}	
.hint:after {
	content: attr(data-hint);
	background: rgba(0, 0, 0, 0.8);
	color: white;
	padding: 8px 10px;
	font-size: 12px;
	white-space: nowrap;
	box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.3);
}

/* top */
.hint-top:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}
.hint-top:hover:before {
	margin-bottom: -10px;
}
.hint-top:hover:after {
	margin-bottom: 2px;
}

/* top */
.hint-top1:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top1:after {
    bottom: 100%;
	margin-bottom: 2px;
	width:80px!important;
	white-space: normal!important;
	word-break:break-all!important;
}
.hint-top1:hover:before {
	margin-bottom: -10px;
}
.hint-top1:hover:after {
	margin-bottom: 2px;
	width:80px!important;
	white-space: normal!important;
	word-break:break-all!important;
}
/* top */
.hint-top2:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top2:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}
.hint-top2:hover:before {
	margin-bottom: -10px;
}
.hint-top2:hover:after {
	margin-bottom: 2px;
	width:280px!important;
	white-space: normal!important;
	word-break:break-all!important;
}
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
					        <button type="button" class="btn btn-success" onclick="javascript:caseChangeTeam()" id="caseChangeTeamButton"> 案件分配 </button>	
					     </div>
					 </div>
			    </div>
			</form>
		</div>
		
		<div class="row">
				 <div class="col-md-12">
					<div class="table_content">
						<table class="table table_blue table-striped table-bordered table-hover " >
						<thead>
						<tr>
							<th ><input type="checkbox" id="checkAllNot" class="i-checks"/></th>
							<th >案件编号</th>
							<th >产证地址</th>
							<th >导入时间</th>
							<th > 经办人</th>
						</tr>
						</thead>
						<tbody id="myCaseList">
				
						</tbody>							
						</table>
								
						<div class="text-center page_box">
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
				<td class="center">
					<input type="checkbox" name="my_checkbox" class="i-checks" onclick="_checkbox()" /> 
					<input type="hidden" name="case_code" value="{{item.CASE_CODE}}"/>
					<input type="hidden" name="yu_team_code" value="{{item.YU_TEAM_CODE}}"/>
					<input type="hidden" name="leading_process_id" value="{{item.LEADING_PROCESS_ID}}"/>

				</td>
				<td >
						<p class="big">
							<a href="{{ctx}}/trade-web/case/caseDetail?caseId={{item.PKID}}" target="_blank">
                          		{{item.CASE_CODE}}
							</a>
                         </p>
                        <p>
                          <i class="tag_sign">c</i>
                                {{item.CASE_CODE}}                
                       </p>
				</td>
				<td >
 					<p class="big">
						{{item.PROPERTY_ADDR}}
    				 </p>
					
 					<span >
						<i class="salesman-icon"> </i>
                        <a class="hint  hint-top2" data-hint="{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.ORG_NAME}}" >
                           {{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.ORG_NAME}}
                        </a>
    				 </span>
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
                        <span class="manager"><a href="#"><em>区经：</em>{{item.LEADER}}</a></span>
                         <span class="manager"><a href="#"><em>区总：</em></a></span>
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
			var ids = $("#table_list_1").jqGrid('getGridParam',"selarrrow");
			//var ids = jQuery("#table_list_1").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
			   var row = $("#table_list_1").getRowData(ids[i]);
			   var toCaseInfo = {
				   caseCode	: row.CASE_CODE  ,
				   grpCode :  ''
			   }
			   caseInfoList.push(toCaseInfo);
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
