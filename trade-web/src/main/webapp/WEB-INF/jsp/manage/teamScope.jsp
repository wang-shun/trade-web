<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/bootstrap.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css"
	rel="stylesheet">
<link
	href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css"
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
<!-- <div id="example" style="display: none; ">
<div class="modal-header">
<a class="close" data-dismiss="modal">×</a>
<h3>This is a Modal Heading</h3>
</div>
<div class="modal-body">
<h4>Text in a modal</h4>
<p>You can add some text here.</p>		        
</div>
<div class="modal-footer">
<a href="#" class="btn btn-success">Call to action</a>
<a href="#" class="btn" data-dismiss="modal">Close</a>
</div>
</div> -->
<div class="wrapper wrapper-content  animated fadeInRight">
		<div id="modal-addOrModifyForm" class="modal fade" aria-labelledby="modal-title"
			aria-hidden="true">
			<div class="modal-dialog" style="width: 1200px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="modal-title">添加/组别信息</h4>
					</div>
					<div class="modal-body">
						<div class="row" style="height: 520px;overflow:auto; ">
							<div class="col-lg-12 ">
							<form id="addOrModifyForm">
								<input type="text" name="pkid" id="pkid" value="">
						        <input type="hidden" name="agenTeamCode" id="agenTeamCode" value="">
						        <input type="hidden" name="agenTeamName" id="agenTeamName" value="">
									<div class="form-group">
										<label class="col-sm-2 control-label">业务组别编码<span class="star">*</span>：</label>
										<div class="col-sm-10">
											<input type="text" name="yuAgentTeamCode"
												id="yuAgentTeamCode" placeholder=""
												class="form-control" data-provide="typeahead" readOnly="readonly">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">业务组别名称<span class="star">*</span>：</label>
										<div class="col-sm-10" id="orgCode">
											<input type="text" name="yuAgentTeamName"
												id="yuAgentTeamName" placeholder=""
												class="form-control" >
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">誉萃前台组别<span class="star">*</span>：</label>
										<div class="col-sm-10" id="fontTeam">
											
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">誉萃后台组别<span class="star">*</span>：</label>
										<div class="col-sm-10" id="backTeam">
											
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
					<h5>业务组别查询</h5>
				</div>
				<div class="ibox-content" style="padding: 10px 10px 10px 10px;">
					<form method="get" class="form-horizontal">
							<div class="form-group">
								<label class="col-md-2 control-label">业务组别编码：</label>
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
								<label class="col-md-2 control-label">业务组别名称：</label>
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
						<a href="#" id="recoveryBtn" class="btn btn-primary" style="float:right;margin-right:5px" >案件一键恢复</a>

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
	<script src="${ctx}/js/bootstrap-typeahead.js"></script>
		
	 <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> <script
		src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> 
	
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>	
	<jsp:include page="/WEB-INF/jsp/common/modal.jsp"></jsp:include>	
	<script src="${ctx}/transjs/manage/teamScope.js"></script> 
	<script src="${ctx}/js/template.js" type="text/javascript"></script>
	<script id="yuCuiFontTeamList" type="text/html">
											<select class="form-control" name="yuTeamCode">
                                              {{if content.tsTeamPropertyList.length>0}}
                                              {{each content.tsTeamPropertyList as item}}
                                                 {{if item.isResponseTeam==1}} 
                                                       	{{if item.isSelect==1}} 
											     			<option value ="{{item.yuTeamCode}}" selected="selected">{{item.yuTeamName}}</option>
                                                        {{else }} 
                                                            <option value ="{{item.yuTeamCode}}">{{item.yuTeamName}}</option> 
														{{/if}}
                                                 {{/if}}
                                              {{/each}}
                                              {{/if}}
											</select>
	</script>	
	<script id="yuCuiBackTeamList" type="text/html">
                {{if content.tsTeamScopePropertyVOList.length>0}}
                     {{each content.tsTeamScopePropertyVOList as item1}}
 							{{if item1.tsTeamProperty.isResponseTeam==0}} 
											<select class="form-control" name="yuTeamCode">
                                              {{if content.tsTeamPropertyList.length>0}}
                                              {{each content.tsTeamPropertyList as item}}
                                                 {{if item.isResponseTeam==0}} 
														{{if item.isSelect==1}} 
											     			<option value ="{{item1.tsTeamScope.yuTeamCode}}" selected="selected">{{item1.tsTeamScope.yuTeamName}}</option>
                                                        {{else }} 
                                                            <option value ="{{item.yuTeamCode}}">{{item.yuTeamName}}</option> 
														{{/if}}
                                                 {{/if}}
                                              {{/each}}
                                              {{/if}}
											</select>
                            {{/if}}
 					{{/each}}
				{{else }} 
					<select class="form-control" name="yuTeamCode">
                                           {{if content.tsTeamPropertyList.length>0}}
                                              {{each content.tsTeamPropertyList as item}}
                                                 {{if item.isResponseTeam==0}} 
                                                     <option value ="{{item.yuTeamCode}}">{{item.yuTeamName}}</option> 
                                                 {{/if}}
                                              {{/each}}
                                              {{/if}}
											</select>			
                 {{/if}}
<div id="addLine" >
											<a href="javascript:addBackTeam();" class="btn"><font>添加后台组别</font></a>
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
    function setData(d1,d2){
    	$("#yuAgentTeamName").val(d2);
    	$("#yuAgentTeamCode").val(d1);
    }
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
            url: ctx+"/setting/getAgentTeamCodeList",
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
		var txt = '<div id="div_' + divIndex + '" class="form-group">';
		//txt +='<label class="col-sm-2 control-label"></label>';
		txt += '<div class="col-sm-8" id="back_' + divIndex + '">';
		txt += '</div>';
		txt += '<span class="input-group-addon"><a href="javascript:removeDiv(\'div_'
			+ divIndex + '\');"><font>删除</font></a></span>';
		txt += '</div>';
		// alert(txt);
		$("#addLine").before(txt);
		var backId = 'back_'+divIndex;
		getYuCuiTeamList(backId);
		divIndex++;
	}
	function removeDiv(index) {
		$("#" + index).remove();
	}
    $(function(){
    	getAgentOrgs();
    	getYuOrgs();
    	
       	$("#yuAgentTeamName").typeahead({
            source: agentOrgs,
            display: "orgName",    
            val: "orgCode",           
            items: 8,            
            itemSelected: function (item, val, text) {  
            	$("#yuAgentTeamName").val(text);

            	$("#yuAgentTeamCode").val(val);
            }
       	});
       	$("#yuTeamName").typeahead({
            source: yuOrgs,
            display: "orgName",    
            val: "orgCode",           
            items: 8,            
            itemSelected: function (item, val, text) {  
            	$("#yuTeamName").val(text);

            	$("#yuTeamCode").val(val);
            }
       	});
 
    });

    </script>
    
    </content>
</body>
</html>
