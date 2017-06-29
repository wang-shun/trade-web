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
	 $("#subjectCode").change(subjectCodeChange);
 });
 
 function subjectCodeChange(){
	 $.ajax({  
		 url:ctx+ "/perf/subjectQuery",
		 method: "post",
         data : {  
             "subjectCode" : $("#subjectCode").val()
         },//数据，这里使用的是Json格式进行传输  
         dataType: "json",
         success : function(result) {//返回数据根据结果进行相应的处理  
        	 if(result.content.length!=0){
        		//清空数组  
        		 $("#subjectCode1").empty();
        		 $("#subjectCode1").append("<option value=''>"+"请选择"+"</option>");
                 for(var i=0;i<result.content.length;i++){ 
                	 var xValue=result.content[i].subjectCode;    
                	 var xText=result.content[i].subjectName;    
                	 $("#subjectCode1").append("<option value='"+xValue+"'>"+xText+"</option>");
                 } 
        	 }else{
        		 $("#subjectCode1").empty();
        		 $("#subjectCode1").append("<option value=''>"+"请选择"+"</option>");
        	 }
         }  
     });
 }
 
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
		data.subjectCode1=$("#subjectCode1").val();
		return data;
	}
 
 //excel导出
 $('#exportBtn').click(function(){
 	var data = getParams();
 	$.exportExcel({
 		ctx : ctx,
 		queryId : 'queryReceivablePrefList',
 		colomns : ['sharesCode','shareAmount','subject',
 		           'status', 'userId', 'CASE_CODE', 'caseOrigin', 'propertyAddr', 'treamId', 'SELLER', 'BUYER', 'shareTime'
 		           ],
 		data:data
 		});
 });