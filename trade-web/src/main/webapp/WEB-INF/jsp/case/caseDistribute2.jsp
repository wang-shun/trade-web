<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet"> 
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet"><%-- 
<link href="${ctx}/css/transcss/case/myCaseList2.css" rel="stylesheet"> --%>
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<!-- Data Tables -->
<link rel="stylesheet" href="../static/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="../static/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="../static/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- index_css -->
<link rel="stylesheet" href="../static/trans/css/common/base.css" />
<link rel="stylesheet" href="../static/trans/css/common/table.css" />
<link rel="stylesheet" href="../static/trans/css/workflow/myCaseList.css" />
<link rel="stylesheet" href="../static/iconfont/iconfont.css" ">

<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
	
<style type="text/css">
   .ibox-button {
	margin-top: 10px;
}
.userHead{
width: 80px;
	  height: 80px;
	  display: inline-block;
	  border-radius: 50%;
	  background-size: 80px 108px;
	  vertical-align: middle;
	  background-image:url(../img/a5.png);

}

.bianhao{width:221px;padding-left:0;}
.dizhi{width:542px}


</style>
   

</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<input type="hidden" id="ctx" value="${ctx}"/>
<input type="hidden" id="queryUserId" value="${queryUserId}"/>
<input type="hidden" id="queryOrgId" value="${queryOrgId}"/>

	<div class="wrapper wrapper-content animated fadeInRight">
         <div class="ibox-content border-bottom clearfix space_box">
              <h2 class="title">
           		   待分配案件 
              </h2>
				<form method="get" class="form-horizontal form_box">
						<div class="row clearfix">
							<div class="form_content">
								<label class="sign_left control-label">
									案件编号
								</label>
								<div class="sign_right teamcode">
										<input type="text" class="teamcode form-control" id="caseNo" name="caseNo" value=""/>
								</div>
							</div>
							<div class="form_content">	
								<label class="sign_left control-label">
									CTM编号
								</label>
								<div class="sign_right teamcode">
									<input type="text" class="teamcode form-control"  id="ctmNo" name="ctmNo" value=""/>
								</div>
							</div>
						</div>
						
						<div class="row clearfix">
							<div class="form_content">
								<label class="sign_left control-label">
									产证地址
								</label>
								 <div class="sign_right intextval">
									<input type="text" class="form-control pull-left" id="caseAddr" name="caseAddr" value=""/>
								</div>
							</div>
							<button id="searchButton" type="button" class="btn btn-success" style="margin-left: 10px;" >
							 	<i class="icon iconfont">&#xe635;</i>
							  	查询
							</button>
							<button type="button" onclick="caseDistribute();" id="caseDistributeButton" class="btn btn-success" style="margin-left: 10px;" disabled="true">
                                                                               案件分配                                               
                            </button>
                            <button type="button" onclick="caseChangeTeam();" id="caseChangeTeamButton" class="btn btn-success" style="margin-left: 10px;" disabled="true">
                                                                               案件转组
                            </button>
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
							<th ><span class="sort" sortColumn="ctd.CASE_CODE" sord="desc" onclick="caseCodeSort();" >案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
							<th >产证地址</th>
							<th ><span class="sort" sortColumn="CREATE_TIME" sord="asc" onclick="createTimeSort();" >派单时间</span><i id="createTimeSorti" class="fa fa-sort-asc fa_up"></i></th>
							<th >负责人</th>
							<th >案件状态</th>
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
	
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div id="modal-form" class="modal fade" aria-labelledby="modal-title"
			aria-hidden="true">
			<div class="modal-dialog" style="width: 1200px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="modal-title">请选择交易顾问</h4>
					</div>
					<div class="modal-body">
						<div class="row" style="height: 450px;overflow:auto; ">
							<div class="col-lg-12 ">
								<h3 class="m-t-none m-b"></h3>
								<div class="wrapper wrapper-content animated fadeInRight">
									<div id="modal-data-show" class="row"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
 	<!-- 案件转组 -->
<div id="team-modal-form" class="modal fade" role="dialog" aria-labelledby="team-modal-title" aria-hidden="true">
	<div class="modal-dialog" style="width:700px">
    	 <div class="modal-content">
              <div class="modal-header">
				   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">× </button>
				   <h4 class="modal-title" id="team-modal-title">  案件转组 </h4>
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
			           <button type="button" class="btn btn-success" onclick="javascript:changeCaseTeam()"> 提交 </button>
                 </div>
        </div>
     </div>
</div>
               
<content tag="local_script">

<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script>
<script src="${ctx}/js/jquery.blockui.min.js"></script>
<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
<!-- 排序插件 -->
<script src="${ctx}/js/plugins/jquery.custom.js"></script>
<!-- 分页控件  -->
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>

<!-- Custom and plugin javascript -->
<script src="${ctx}/js/jquery.blockui.min.js"></script>
<script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script>

<script src="${ctx}/js/trunk/case/caseDistribute2.js"></script>
<script src="${ctx}/js/template.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/jquery.json.min.js"></script>
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>

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
                                {{item.CTM_CODE}}                
                       </p>
				</td>
				<td >

<p class="demo-top" title="{{item.PROPERTY_ADDR}}">
{{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
{{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
{{else}}
{{item.PROPERTY_ADDR}}
{{/if}}					 
						</p>
 							<p >
								 <i class="salesman-icon"> </i>
								 <a class="demo-top" title="{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.ORG_NAME}}" >
{{if item.ORG_NAME !="" && item.ORG_NAME !=null && item.ORG_NAME.length>8}}							
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.ORG_NAME.substring(0,10)}}...
{{else}}
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.ORG_NAME}}
{{/if}}	
								 </a>
							</p>

				</td>
				<td >
						{{if item.CREATE_TIME!=null}}
						  <p class="smll_sign">
                                    <i class="sign_normal">派</i>
									{{item.CREATE_TIME}}
    				 	  </p>

						{{else}}
                           <p class="sign_grey">
                                    <i class="sign_normal">派</i>
						    		{{item.CREATE_TIME}}
    						</p>

						{{/if}}

				</td>
				<td class="center">
                        <span class="manager"><a href="#"><em>区经：</em>{{item.LEADER}}</a></span>
                        <span class="manager"><a href="#"><em>区总：</em>{{item.qyzjNAME}}</a></span>
                 </td>
				<td class="center">
                         
						{{if item.STATUS!=null && item.STATUS!="" }}
						  <p class="smll_sign">
 									<i class="sign_blue">
                                    {{item.STATUS}}
									</i>
    				 	  </p>

						{{else}}
									<i >
									</i>
						{{/if}}
                 </td>
				
				</tr>
       {{/each}}
	</script>
</content>
</body>
</html>
