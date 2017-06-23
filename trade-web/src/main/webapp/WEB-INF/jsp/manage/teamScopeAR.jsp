<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">

<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />"
	rel="stylesheet">
<style type="text/css">
.form-group label {
	text-align: right;
}
.form-control {
	margin-bottom: 5px;
	height:32px;
}
.col-sm-10{
	height:37px;
}
.col-md-2{
	width:12%
}
.org-label-control {
    background-color: #FFFFFF;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 1px;
    color: inherit;
    display: block;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    width: 100%;
    font-size: 14px;
}
.modal-content {
   width : 900px;
}

</style>
</head>

<body>
<div class="wrapper wrapper-content  animated fadeInRight">
		<div id="modal-addOrModifyForm" class="modal fade" aria-labelledby="modal-title"
			aria-hidden="true">
			<div class="modal-dialog" style="width: 1200px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="modal-title">添加片区配置信息</h4>
					</div>
					<div class="modal-body">
						<div class="row" style="height: 520px;overflow:auto; ">
							<div class="col-lg-12 ">
							<form id="addOrModifyForm">
								<input type="hidden" name="pkid" id="pkid" value="">
									<div class="form-group">
										<label class="col-sm-2 control-label">片区编码<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="arCode"
												id="arCode" placeholder=""
												class="form-control" data-provide ="typeahead" readOnly="readonly">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">片区名称<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="arName"
												id="arName" placeholder=""
												class="form-control" >
										</div>
									</div>
								<!-- <div class="form-group">
										<label class="col-sm-2 control-label">誉萃前台组别<span class="star">*</span>：</label>
										<div class="col-sm-10" id="fontTeam">
											
										</div>
									</div> -->
							    	<div class="form-group">
										<label class="col-sm-2 control-label">誉萃组别<span class="star">*</span>:</label>
										<div class="col-sm-10" id="team">
											
										</div>
									</div> 
							</form>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-12">
								<button type="button" class="btn btn-success" id="saveOrModifyBtn"
								value="提交">提交</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


<div></div>



	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>片区配置查询</h5>
				</div>
				<div class="ibox-content" style="padding: 10px 10px 10px 10px;">
					<form method="get" class="form-horizontal">
							<div class="form-group">
								<label class="col-md-2 control-label">片区编码：</label>
								<div class="col-md-3">
									<!-- <input type="text" name="agentTeamCode"
												id="agentTeamCode" placeholder=""
												class="form-control" > -->
										<input type="text" class="span12 tbsporg org-label-control" id="agentTeamCode" name="agentTeamCode" readonly="readonly" 
										   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'1D29BB468F504774ACE653B946A393EE', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioOrgSelectCallBack,
										   expandNodeId:''})" />
									<input class="m-wrap " type="hidden" id="oriGrpId" name="oriGrpId">
								</div>
								<label class="col-md-2 control-label">片区名称：</label>
								<div class="col-md-3">
									<input type="text" name="agentTeamName"
												id="agentTeamName" placeholder=""
												class="form-control" >
								</div>
								</div>
								<div class="form-group ">
								<label class="col-md-2 control-label">誉萃组别编码：</label>
								<div class="col-md-3">
								<!-- 	<input type="text" name="teamCode"
												id="teamCode" placeholder=""
												class="form-control" > -->
									<input type="text" class="span12 tbsporg org-label-control" id="teamCode" name="teamCode" readonly="readonly" 
									   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
									   startOrgId:'ff8080814f459a78014f45a73d820006', orgType:'',departmentType:'',departmentHeriarchy:'yucui_headquarter',
									   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,
									   expandNodeId:''})" />
									 <input class="m-wrap " type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId"> 
								</div>
								<label class="col-md-2 control-label">誉萃组别名称：</label>
								<div class="col-md-3">
									<input type="text" name="teamName"
												id="teamName" placeholder=""
												class="form-control" >
								</div>
								<div class="col-sm-1">
									<button id="searchButton" type="button" class="btn btn-primary pull-right">查询</button>
								</div>
								<div class="col-sm-1">
									<button id="cleanButton" type="button" class="btn btn-primary pull-right">清空</button>
								</div>
							</div>
					</form>
				</div>
			</div>
		</div>

		<div class="wrapper wrapper-content  animated fadeInRight" style="padding:0px">

			<div class="col-lg-12">
				<div class="ibox ">
					<div class="ibox-title">
						<a href="#" id="delBtn" class="btn btn-primary" style="float:right;margin-right:5px" >删除</a>
						<a href="#" id="modifyBtn" class="btn btn-primary" style="float:right;margin-right:5px" >修改</a>
						<a href="#" id="addBtn" class="btn btn-primary" style="float:right;margin-right:5px" >添加</a>
						<h5>组别列表</h5>
						
					</div>

					<div class="ibox-content">
						<div class="jqGrid_wrapper">
							<table id="table_list_1"></table>
							<div id="pager_list_1"></div>
								
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>

	<content tag="local_script">
	<script src="<c:url value='/js/bootstrap-typeahead.js' />"></script>
		
	 <script
		src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> <script
		src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> <script
		src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
	<script src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script> 
	
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>	
	<jsp:include page="/WEB-INF/jsp/common/modal.jsp"></jsp:include>	
	<script src="<c:url value='/transjs/manage/teamScopeAR.js' />"></script> 
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script type="text/javascript" src="<c:url value='/static/js/jquery.json.min.js' />"></script>
	<!-- 显示已经选择的组别 -->
	<script id="yuCuiTeamList" type="text/html">
                {{if content.length>0}}
                     {{each content as item}}
							{{if item.isSelect==1}}
								<div class="row form">
 								<div class="col-md-5">
                                <select class="form-control" name="yuTeamCode">
									  <option value ="{{item.yuTeamCode}}" selected="selected">{{item.yuTeamName}}</option>
                                      {{each content as item1}}
											{{if item1.isSelect==null}}
 												<option value ="{{item1.yuTeamCode}}">{{item1.yuTeamName}}</option> 
											{{/if}}
									  {{/each}}
								</select>
 								</div>
								<div class="col-md-3">
                                      <select class="form-control" name="isResponseTeam">
											{{if item.isResponseTeam==0}}
 												<option value ="0" selected="selected">后台组</option>
												<option value ="1">前台组</option>
                                            {{else }}
                              					<option value ="1" selected="selected">前台组</option> 
												<option value ="0">后台组</option> 
											{{/if}}
									  </select>
								</div>
  								</div>
							{{/if}}
 					{{/each}}		
                 {{/if}}
				<div id="addLine">
						<a href="javascript:addBackTeam();" class="btn"><font>添加组别类型</font></a>
				</div>
	</script>
	<!-- 需要选择的组别 -->
	<script id="newYuCuiTeamList" type="text/html">
 			<div class="col-md-5">
			<select class="form-control" name="yuTeamCode">
                {{if content.length>0}}
                    {{each content as item}}
                         <option value ="{{item.yuTeamCode}}">{{item.yuTeamName}}</option> 
                    {{/each}}
               {{/if}}
			</select>
			</div>
			<div class="col-md-3">
                  	<select class="form-control" name="isResponseTeam">
 							<option value ="0" selected="selected">后台组</option>
							<option value ="1">前台组</option>
					</select>
				</div>
			</div>
	</script>		

	<script id="yuCuiOtherBackTeamList" type="text/html">
		<select class="form-control" name="yuTeamCode">
 				{{if content.length>0}}
                    {{each content as item}}
                       {{if item.isResponseTeam==0}} 
						<option value ="{{item.yuTeamCode}}">{{item.yuTeamName}}</option>
 						{{/if}}
 					{{/each}}
                 {{/if}}
		</select>
	</script>
	<script id="recoveryCaseList" type="text/html">
			{{if content.length>0}}
 				<div class="scroller" style="height:290px;">
				<table class="table table-striped">
                   <thead>
                       <tr><th>案件地址</th><th style="width:70px;">经纪人</th><th>誉萃组别</th><th>组别主管</th></tr>
                   </thead>
                   <tbody>
                       {{each content as item}}
                            <tr> 
                            <td>{{item.propertyAddr}}</td><td>{{item.agentName}}</td><td>{{item.orgId}}</td><td>{{item.leadingProcessId}}</td>
                            </tr>
                        {{/each}}
                    </tbody>
                 </table>
                </div>
			{{/if}}
			{{if content.length==0}}
 				 无任何恢复案件!
			{{/if}}
	</script>		
    <script>
    var ctx = "${ctx}";
    var url = "/quickGrid/findPage";
	function loadUnSettingOrg(){		
		$("#show_unsettingList").jqGrid("GridUnload");
		taskDelGrid=$("#show_unsettingList").jqGrid(
			{
			datatype : 'json',
			url : ctx + url,
			height : 250,
			widht:1100,
			autowidth : true,
			shrinkToFit : true,
			rowNum : 5,
			colNames : [ '编组编号', '组织名','区经' ],
			colModel : [ {
				name : 'ORG_CODE',
				index : 'ORG_CODE',
				width : 333,
				formatter : function(
						cellvalue,
						options,
						rawObject) {
					return "<a href=\"javascript:setData('"+rawObject.ORG_CODE+"','"+rawObject.ORG_NAME+"')\">"+rawObject.ORG_CODE+"</a>";
				}
			}, {
				name : 'ORG_NAME',
				index : 'ORG_NAME',
				width : 333
			}, {
				name : 'BUSIAR_ID',
				index : 'BUSIAR_ID',
				width : 333
			}],
			add : true,
			addtext : 'Add',
			pager : "#show_unsettingList_bar",
			viewrecords : true,
			pagebuttions : true,
			hidegrid : false,
			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格										
			pgtext : " {0} 共 {1} 页",										
			sortname:"create_Date",sortorder:"DESC",										
			postData : {										
				queryId : "unsettingTeamScope"
				}								
			});
	}
    var agentOrgs = [];
    var yuOrgs = [];
    function getAgentOrgs(){
    	$.ajax({
            url: ctx+"/setting/getAgentArCodeList",
            dataType: "json",
    		async:false,
            data:{           
            },
            success: function( data ) {
            	agentOrgs = data;
            }
        });
    }
    function getYuOrgs(){
    	$.ajax({
            url: ctx+"/setting/getTeamCodeList",
            dataType: "json",
    		async:false,
            data:{
            },
            success: function( data ) {
            	yuOrgs = data;
            }
        });
    }
    var divIndex = 1;
	function addBackTeam() {
		var txt = '<div id="div_' + divIndex + '" class="row form">';
		//txt +='<label class="col-sm-2 control-label"></label>';
		txt += '<div class="" id="back_' + divIndex + '">';
		txt += '</div>';
		txt += '<span class="input-group-addon"><a href="javascript:removeDiv(\'div_'
			+ divIndex + '\');"><font>删除</font></a></span>';
		txt += '</div>';

		$("#addLine").before(txt);
		var backId = 'back_'+divIndex;
		getYuCuiTeamList(backId);
		divIndex++;
	}
	function removeDiv(index) {
		$("#" + index).remove();
	}
    </script>
    
    </content>
</body>
</html>
