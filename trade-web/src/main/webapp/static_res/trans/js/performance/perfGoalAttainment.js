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
   
 function searchButton(){	
	 reloadGrid();
 };
  
 
//初始化日期控件
 var monthSel=new DateSelect($('.month'),{max:new Date(2999,1,1),moveDone:reloadGrid});
  
 $(document).ready(function () {
     $('.input-daterange').datepicker({
     	startDate:new Date(),
         keyboardNavigation: false,
         forceParse: false,
         autoclose: true
     });
   
	 
	 searchMethod();
     $("#sel_team").change(reloadGrid);
     
 });
 
 function searchMethod() {
		$(".table_content")
				.aistGrid(
						{
							ctx : ctx,
							url : "/quickGrid/findPage",
							queryId : $("#sel_team").val()=='zbjb'? 'queryPerfGoalAttainmentListzbjb':($("#sel_team").val()=='gbfwzx' ? 'queryPerfGoalAttainmentListgbfwzx':'queryPerfGoalAttainmentList'),
							templeteId : 'template_successList',
							gridClass : 'table table_blue table-striped table-bordered table-hover',
							data : getParams(),
							columns : [ {   
								colName : "查看对象"
							}, {
								colName : "考核月份"
							}, {
								colName :
									"<span class='sort' sortColumn='goalPerf' sord='ASC' onclick='caseCodeSort();'>"+"目标业绩"+"</span>"
							}, {
								colName : "<span class='sort' sortColumn='shareAmount' sord='ASC' onclick='caseCodeSort();'>"+"完成业绩"+"</span>"
							}, {
								colName : "<span class='sort' sortColumn='completionRate' sord='ASC' onclick='caseCodeSort();'>"+"已完率"+"</span>"
							},{
								colName : "操作"
							}]

						});
	}
 
 function reloadGrid() {
	 var teamId = $("#sel_team").val();
	 if(teamId=='ryjb'){
		 var data = getParams();
			$(".table_content").reloadGrid({
				ctx : ctx,
				queryId : 'queryPerfGoalAttainmentListRyjb',
			    templeteId : 'template_successList',
			    data : data,
			    wrapperData : {ctx:ctx}
		    })
	 }
	 if(teamId=='zbjb'){
		 var data = getParams();
		 $(".table_content").reloadGrid({
			 ctx : ctx,
			 queryId : 'queryPerfGoalAttainmentListzbjb',
			 templeteId : 'template_successList',
			 data : data,
			 wrapperData : {ctx:ctx}
		 })
	 }
	 if(teamId=='gbfwzx'){
		 var data = getParams();
		 $(".table_content").reloadGrid({
			 ctx : ctx,
			 queryId : 'queryPerfGoalAttainmentListgbfwzx',
			 templeteId : 'template_successList',
			 data : data,
			 wrapperData : {ctx:ctx}
		 })
		 
	 }
	 if(teamId=='0'){
		 var data = getParams();
		 $(".table_content").reloadGrid({
			 ctx : ctx,
			 queryId : 'queryPerfGoalAttainmentList',
			 templeteId : 'template_successList',
			 data : data,
			 wrapperData : {ctx:ctx}
		 })
	 }
	}


	function getParams() {
		var data={};
		data.teamId=$("#sel_team").val();
		data.yuCuiOriGrpId=$("#yuCuiOriGrpId").val();
		data.time=monthSel.getDate().format('yyyy-MM');
		return data;
	}
	
	
	function caseCodeSort(){
		if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
			$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
		}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
			$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
		}else{
			$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
		}
	}
  