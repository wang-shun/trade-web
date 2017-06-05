<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
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

</style>
</head>

<body>

	<div class="row">
		<div class="col-lg-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>考勤查询</h5>
				</div>
				<div class="ibox-content" style="padding: 10px 10px 10px 10px;">
					<form method="get" class="form-horizontal">
						
						<div class="form-group">
						    <label class="col-sm-2 control-label">部门：</label>
							<div class="col-sm-3">
								<input type="text" name="orgName"
									id="orgName" placeholder="输入部门进行查询"
									class="form-control" >
							</div>
							<label class="col-sm-2 control-label">姓名：</label>
							<div class="col-sm-3">
								<input type="text" name="userName"
									id="userName" placeholder="输入员工姓名进行查询"
									class="form-control" >
							</div>
						</div>
						<div class="form-group">
						    <label class="col-sm-2 control-label">起始时间：</label>
							<div class="col-sm-3" >
								<div class="input-group date" id="date_1">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" class="form-control" name="attendTimeStart" id="attendTimeStart" readonly >
								</div>
							</div>
							<label class="col-sm-2 control-label">截止时间：</label>
							<div class="col-sm-3" >
								<div class="input-group date" id="date_2">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span><input type="text" class="form-control" name="attendTimeEnd" id="attendTimeEnd" readonly >
								</div>
							</div>
							
							<div class="col-sm-2">
								<button id="searchButton" type="button"  class="btn btn-primary">查询</button>
								<button id="cleanButton" type="button" class="btn btn-primary">清空</button>
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

						<h5>考勤记录列表</h5>
						
					</div>

					<div class="ibox-content">
						<div class="jqGrid_wrapper">
							<table id="table_list_2"></table>
							<div id="pager_list_2"></div>
								
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>

	<content tag="local_script"> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>

    <script>
    var ctx = "${ctx}";
    var lng = null;
    var lat = null;
    
    function getAttendanceList(){
    	$("#table_list_2").jqGrid("GridUnload");
    	var jobCode = "${jobCode}";
    	var manager = null;
    	if(jobCode=='Manager' || jobCode=='Senior_Manager'){
    		manager = jobCode;
    	}
    	var consultant = null;
    	if(!(jobCode=='Manager' || jobCode=='Senior_Manager')){
    		consultant = "consultant";
    	}
    	$("#table_list_2").jqGrid({
        	url:ctx+"/quickGrid/findPage",
            datatype: "json",
            height: 350,
            autowidth: true,
            shrinkToFit: true,
            rowNum: 10,
            colNames: ['序号', '事由', '所在地','考勤人','所在部门','时间'],
            colModel: [
                {name: 'PKID', index: 'PKID',  width: 60},
                {name: 'REASON', index: 'REASON', width: 140},
                {name: 'ADDRESS', index: 'ADDRESS', width: 140},
                {name: 'USER_NAME', index: 'USER_NAME', width: 140},
                {name: 'ORG_NAME', index: 'ORG_NAME', width: 140},
                {name: 'ATTEND_TIME', index: 'ATTEND_TIME', width: 140}

            ], 
            add: true,
            addtext: 'Add',
            pager: "#pager_list_2",
            viewrecords: true,
            pagebuttions: true,
            hidegrid: false,
            recordtext: "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
            pgtext : " {0} 共 {1} 页",
            search:false,
            postData:{
            	queryId:"queryAttendancePage",
            	search_attendOrgName:$("#orgName").val(),
            	search_attendUserName:$("#userName").val(),
            	search_attendTimeStart:($("#attendTimeStart").val()!=""? ($("#attendTimeStart").val()+ " 00:00:00"):""),
            	search_attendTimeEnd:($("#attendTimeEnd").val()!=""? ($("#attendTimeEnd").val()+ " 23:59:59"):""),
            	argu_manager : manager,
            	argu_consultant : consultant
            },
            gridComplete : function() { 

            },
            onSelectRow : function(rowid,status) {
				var rowData = $("#table_list_1").jqGrid('getRowData', rowid);
				$("#pkid").val(rowData['PKID']);
			}
            
        });
    }
  
    $(function(){
    	getAttendanceList();
		$("#date_1").datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true
        });
		$("#date_2").datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true
        });
		
		$("#searchButton").click(function(){
			getAttendanceList();
		});
		
		//清空
		$('#cleanButton').click(function() {
			$("input[name='orgName']").val('');
			$("input[name='userName']").val('');
			$("input[name='attendTimeStart']").val('');
			$("input[name='attendTimeEnd']").val('');
		});
    });
 
    </script>
    
    </content>
</body>
</html>
