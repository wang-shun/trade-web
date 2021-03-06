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
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/switch/bootstrap-switch.min.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />"
	rel="stylesheet">
<link href="<c:url value='/js/plugins/dateSelect/dateSelect.css' />"
	rel="stylesheet">
	
<!-- new -->
    <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
    <link rel="stylesheet" href="<c:url value='/static/css/style.css' />" />

    <!-- Data Tables -->
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.bootstrap.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.responsive.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.tableTools.min.css' />" />

    <!-- index_css -->
    <link rel="stylesheet" href="<c:url value='/css/trans/css/common/base.css' />" />
    <link rel="stylesheet" href="<c:url value='/css/trans/css/common/table.css' />" />
    <link rel="stylesheet" href="<c:url value='/css/trans/css/common/input.css' />" />
    <link rel="stylesheet" href="<c:url value='/css/trans/css/award/baseAward.css' />" ">
 	<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
	<link href="<c:url value='/css/transcss/case/case_filter.css' />" rel="stylesheet">	
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/excelImport.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
					
						<p class="bonus-m" style="height: 35px">
							<input type="button" class="btn btn-success btn_small pull-left" value="&lt;">
							<!-- <h5 class="month">yyyy/MM月</h5> -->
							<span class="month" style="font-size: 22px; display: inline-block;float: left;line-height: 34px;color: #333;margin: 0px 15px ">yyyy/MM月</span>
							<input type="button" class="btn btn-success btn_small pull-left"
								disabled value="&gt;" style="margin-left: 10px;">
						</p>
					
					<form method="get" class="form_list">
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small" id="case_date">
                                        	案件编号
                                    </label>
                                    <input type="text" class="select_control sign_right_one" id="caseCode" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small" id="case_date">
                                        	环节
                                    </label>
                                    <aist:dict id="srvCode" name="srvCode"
										clazz="select_control" display="select"
										dictType="KPI_SRV_CODE" ligerui='none'></aist:dict>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        	贵宾服务部
                                    </label>
                                    
                                    <input type="text" class="teamcode input_type" id="teamCode" name="teamCode" readonly="readonly" style="background-color:#FFFFFF"
											   onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
											   startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'yucui_team',
											   chkStyle:'radio', callBack:radioYuCuiOrgDeptSelectCallBack,
											   expandNodeId:'',chkLast:'true'})" />
											 <input class="m-wrap " type="hidden" id="yuCuiOriGrpDeptId" name="yuCuiOriGrpDeptId">
                                   <div class="input-group float_icon organize_icon" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
											   startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'yucui_team',
											   chkStyle:'radio', callBack:radioYuCuiOrgDeptSelectCallBack,
											   expandNodeId:'',chkLast:'true'})">
                                        <i class="icon iconfont"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        	所在组别
                                    </label>
                                    <input type="text" class="teamcode input_type" id="orgName"
												name="orgName"
												onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:'',chkLast:'true'})"
												value=''> 
												<input type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId" value="">
                                    <div class="input-group float_icon organize_icon" onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName',
										   startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack,expandNodeId:'',chkLast:'true'})">
                                        <i class="icon iconfont"></i>
                                    </div>
                                </div>
                                <div class="form_content space">
                                    <div class="add_btn" style="margin-left: 0px;">
                                        <button type="button" class="btn btn-success" id="searchButton">
                                            <i class="icon iconfont"></i>
                                            	查询
                                        </button>
                                        <button type="button" class="btn btn-success" id="importButton">
                                            	个人案件KPI导入
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
		</div>
		
		<div class="row">
                    <div class="col-md-12">
                        <div class="notes">
                            <p class="pull-left">个人案件KPI明细</p>
                        </div>
                        <div class="table_content">
                            <table class="table table_blue table-hover table-striped table-bordered">
                               <thead>
                                    <tr>
                                        <th><span class="sort" sortColumn="a.CASE_CODE" sord="desc" onclick="caseCodeSort();">案件编码 </span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
                                        <th><span class="sort" sortColumn="a.SRV_CODE" sord="desc" onclick="srvCodeSort();">环节</span><i id="serCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
                                        <th><span class="sort" sortColumn="ot.org_name" sord="desc" onclick="orgNameSort();">所在组别</span><i id="orgNameSorti" class="fa fa-sort-desc fa_down"></i></th>
                                        <th><span class="sort" sortColumn="od.org_name" sord="desc" onclick="orgdNameSort();">所属贵宾服务部</span><i id="orgdNameSorti" class="fa fa-sort-desc fa_down"></i></th>
                                        <th>类型</th>
                                        <th>满意度</th>
                                        <th>占比</th>
                                        <th>是否接通</th>
                                    </tr>
                                </thead>
                                <tbody id="satisfactionDegreeList">
                                </tbody>
                            </table>
                            
                            <div class="text-center page_box">
								<span id="currentTotalPage"><strong ></strong></span>
								<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
								<div id="pageBar" class="pagergoto">
								</div>  
						    </div>  	
                        </div>
                    </div>
                </div>
		
		 
			
		<div class="row">
			<div class="col-lg-12">
				<a href="../template/call_back_score_temp.xlsx">案件满意度导入模板下载</a>
			</div>
		</div>

		<!-- 失败数据 -->
		<div id="error-modal-form" class="modal fade" role="dialog"
			aria-labelledby="excel-modal-title" aria-hidden="true">
			<div class="modal-dialog" style="width: 1200px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="excel-modal-title">导入失败数据</h4>
					</div>

					<div class="modal-footer">
						<div class="ibox float-e-margins">

							<div class="ibox-content">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th>序号</th>
											<th>誉萃编号</th>
											<th>上家签约</th>
											<th>下家签约</th>
											<th>上家陪同还贷</th>
											<th>下家贷款</th>
											<th>下家纯公积金</th>
											<th>下家过户</th>
											<th>上家过户</th>
											<th>上家电话接通</th>
											<th>下家电话接通</th>
											<th>下家备注</th>
											<th>上家备注</th>
											<th>错误信息</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${fList}" var="item">
											<tr>
												<td>${item.snNo }</td>
												<td>${item.caseCode }</td>
												<td>${item.salesSignScore }</td>
												<td>${item.signScore }</td>
												<td>${item.accompanyRepayLoanScore }</td>
												<td>${item.comLoanScore }</td>
												<td>${item.accuFundScore }</td>
												<td>${item.transferScore }</td>
												<td>${item.salesTransferScore }</td>
												<td>${item.salesCallBack }</td>
												<td>${item.callBack }</td>
												<td>${item.buyerComment }</td>
												<td>${item.salerComment }</td>
												<td>${item.msg }</td>



											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="ex_message" value="${ex_message}" />
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>

	<content tag="local_script"> 
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
	<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> <!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> 
	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script> 
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> <!-- iCheck -->
	<script src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script> 
	
	<script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
	<script src="<c:url value='/js/plugins/switch/bootstrap-switch.js' />"></script> 
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
	<script src="<c:url value='/js/plugins/layer/layer.js' />"></script> 
	<script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script> 
	<!-- 分页控件  -->
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>

<!-- 必须JS -->
<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
<script src="<c:url value='/js/plugins/dateSelect/dateSelect.js' />"></script> 
	<script src="<c:url value='/js/trunk/award/kpiImport.js' />"></script> 
	
	<script id="template_satisfactionDegreeList" type= "text/html">
         {{each rows as item index}}
                 {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
					<td>
						<p class="big">
                                                <a href="{{ctx}}/case/caseDetail?caseId={{item.pkid}}" target="_blank">
                                                    {{item.CASE_CODE}}
                                                </a>
                        </p>
					</td>
					<td>{{item.SRV_CODE}}</td>
					<td>{{item.tName}}</td>
					<td>{{item.dName}}</td>
					<td>{{item.type=='IMP' ? '导入' : item.type=='GEN' ? '生成' : ''}}</td>
					<td>{{item.SATISFACTION}}</td>
					<td>{{item.SRV_PART}}</td>
					<td>{{item.CAN_CALLBACK==1 ? '通' : item.CAN_CALLBACK==0 ? '不通' : ''}}</td>
				</tr>
		{{/each}}
	 </script> 
<script>
	 	aist.sortWrapper({
			reloadGrid : searchMethod
		}); 
	 	
	 	//选业务组织的回调函数
	 	function radioYuCuiOrgSelectCallBack(array){
	         if(array && array.length >0){
	             $("#orgName").val(array[0].name);
	     		$("#yuCuiOriGrpId").val(array[0].id);
	     		
	     	}else{
	     		$("#orgName").val("");
	     		$("#yuCuiOriGrpId").val("");
	     	}
	     }
	 	
	     function radioYuCuiOrgDeptSelectCallBack(array){
	         if(array && array.length >0){
	             $("#teamCode").val(array[0].name);
	     		$("#yuCuiOriGrpDeptId").val(array[0].id);
	     		
	     	}else{
	     		$("#teamCode").val("");
	     		$("#yuCuiOriGrpDeptId").val("");
	     	}
	     }
	     
		$(function(){
			
			// 是否显示错误信息
			<c:if test="${not empty fList}">
			var hasError = true;
			</c:if>
			<c:if test="${empty fList}">
			var hasError = false;
			</c:if>
			// 是否显示错误信息
			if (!!hasError) {
				$('#error-modal-form').modal("show");
			}
			
		     var monthSel = new DateSelect($('.bonus-m'), {
					max : new Date(),
					moveDone : reloadGrid1
				});
		     var belongM = "${belongM}";
			 var belongLastM = "${belongLastM}";
			 sw = $("#moSwitch").bootstrapSwitch({
					'onText' : "${belongLastMon}",
					'offText' : "${belongMon}",
					state : true
				}).on('switchChange.bootstrapSwitch', function(event, state) {
				});
			
		});
	     
	 </script>		
</content>
</body>
</html>
