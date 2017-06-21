//选业务组织的回调函数
 function radioYuCuiOrgSelectCallBack(array) {
	if (array && array.length > 0) {
			$("#teamCode").val(array[0].name);
			$("#yuCuiOriGrpId").val(array[0].id);
	} else {
			$("#teamCode").val("");
			$("#yuCuiOriGrpId").val("");
	}
 }
//日期控件
 $('#datepicker_0').datepicker({
 	format : 'yyyy-mm-dd',
 	weekStart : 1,
 	autoclose : true,
 	todayBtn : 'linked',
 	language : 'zh-CN'
 });
 
 $(document).ready(function () {
	 searchMethod();
 });
 
 function searchButton(){	
	 reloadGrid();
 };
 
 function searchMethod(){
	 $(".table_content")
		.aistGrid(
				{
					ctx : ctx,
					url : "/quickGrid/findPage",
					queryId : 'queryReceivablePrefList',
					templeteId : 'template_successList',
					gridClass : 'table table_blue table-striped table-bordered table-hover',
					data : getParams(),
					columns : [ {   
						colName : "业绩编号"
					}, {
						colName : "业绩金额"
					}, {
						colName : "提交时间"
					}, {
						colName : "业绩名目"
					}, {
						colName : "业绩状态"
					},{
						colName : "归属人"
					},{
						colName : "案件编号"
					},{
						colName : "上家"
					}, {
						colName : "下家"
					}]
				});
 }
 
 function reloadGrid() {
	 var data = getParams();
		$(".table_content").reloadGrid({
			ctx : ctx,
			queryId : 'queryReceivablePrefList',
		    templeteId : 'template_successList',
		    data : data,
		    wrapperData : {ctx:ctx}
	    })
	}
 
 function getParams() {
		var data={};
		data.yuCuiOriGrpId=$("#yuCuiOriGrpId").val();
		data.status=$('input:radio[name="statusOptions"]:checked').val();
		data.dtBegin=$("#dtBegin_0").val();
		data.dtEnd=$("#dtEnd_0").val();
		data.originCode=$('input:radio[name="orderOptions"]:checked').val();
		data.subjectCode=$("#subjectCode").val();
		return data;
	}
 
 //excel导出
 $('#exportBtn').click(function(){
 	var data = getParams();
 	$.exportExcel({
 		ctx : ctx,
 		queryId : 'queryReceivablePrefList',
 		colomns : ['sharesCode','shareAmount','subject',
 		           'status', 'userId', 'caseCode', 'caseOrigin', 'district', 'treamId', 'SELLER', 'BUYER', 'shareTime'
 		           ],
 		data:data
 		});
 });