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
   
 //点击查询
 function searchButton(){	
	 reloadGrid();
 };
  
 
//初始化日期控件
 var monthSel=new DateSelect($('.month'),{max:new Date(2999,1,1),moveDone:reloadGrid});
 
//初始化下拉列表
 function initialization(){
	 var dataView=$("#dataView").val();
     if(dataView=='C'){
    	 $("#sel_team").append("<option value='GeneralManager' selected = 'selected' >整个公司</option>");
    	 $("#sel_team").append("<option value='director'> 贵宾服务中心级别</option>");
    	 $("#sel_team").append("<option value='Senior_Manager'>组别级别</option>");
    	 $("#sel_team").append("<option value='ryjb'>人员级别</option>");
     }
     if(dataView=='D'){
    	 $("#sel_team").append("<option value='director' selected = 'selected'> 贵宾服务中心级别</option>");
    	 $("#sel_team").append("<option value='Senior_Manager' >组别级别</option>");
    	 $("#sel_team").append("<option value='ryjb' >人员级别</option>");
     }
     if(dataView=='T'){
    	 $("#sel_team").append("<option value='Senior_Manager' selected = 'selected'>组别级别</option>");
    	 $("#sel_team").append("<option value='ryjb'>人员级别</option>");
     }
     if(dataView=='P'){
    	 $("#sel_team").append("<option value='ryjb' selected = 'selected'>人员级别</option>");
     }
     
 }  
 
 
 
 //页面加载时候,初始化日期控件
 $(document).ready(function () {
	 initialization();
	 
     $('.input-daterange').datepicker({
     	startDate:new Date(),
         keyboardNavigation: false,
         forceParse: false,
         autoclose: true
     });
   
     
	 searchMethod();
     $("#sel_team").change(reloadGrid);
     //根据不同的角色初始化下拉列表的值
    // initialization();
    
 });
 
 function searchMethod() {
		$(".table_content")
				.aistGrid(
						{
						ctx : ctx,
						url : "/quickGrid/findPage",
						queryId : $("#sel_team").val()=='Senior_Manager'? 'queryPerfGoalAttainmentListzbjb':($("#sel_team").val()=='director' ? 'queryPerfGoalAttainmentListgbfwzx':$("#sel_team").val()=='ryjb'?'queryPerfGoalAttainmentListRyjb':'queryPerfGoalAttainmentList'),
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
							colName : "<span class='sort' sortColumn='countPerf' sord='ASC' onclick='caseCodeSort();'>"+"业绩单数"+"</span>"
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
	 if(teamId=='Senior_Manager'){
		 var data = getParams();
		 $(".table_content").reloadGrid({
			 ctx : ctx,
			 queryId : 'queryPerfGoalAttainmentListzbjb',
			 templeteId : 'template_successList',
			 data : data,
			 wrapperData : {ctx:ctx}
		 })
	 }
	 if(teamId=='director'){
		 var data = getParams();
		 $(".table_content").reloadGrid({
			 ctx : ctx,
			 queryId : 'queryPerfGoalAttainmentListgbfwzx',
			 templeteId : 'template_successList',
			 data : data,
			 wrapperData : {ctx:ctx}
		 })
		 
	 }
	 if(teamId=='GeneralManager'){
		 var data = getParams();
		 $(".table_content").reloadGrid({
			 ctx : ctx,
			 queryId : 'queryPerfGoalAttainmentList',
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

 	//注入参数
	function getParams() {
		var data={};
		data.teamId=$("#sel_team").val();
		data.yuCuiOriGrpId=$("#yuCuiOriGrpId").val();
		data.time=monthSel.getDate().format('yyyy-MM');
		return data;
	}
	
	//排序
	function caseCodeSort(){
		if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
			$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
		}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
			$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
		}else{
			$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
		}
	}
//到处excel
$('#exportBtn').click(function(){
    	var data = getParams();
    	var queryId=$("#sel_team").val()=='Senior_Manager'? 'queryPerfGoalAttainmentListzbjb':($("#sel_team").val()=='director' ? 'queryPerfGoalAttainmentListgbfwzx':($("#sel_team").val()=='ryjb'? 'queryPerfGoalAttainmentListRyjb' :'queryPerfGoalAttainmentList'));
    	$.exportExcel({
    		ctx : ctx,
    		queryId : queryId,
    		colomns : ['viewObject','belongMonth','goalPerf',
    		           'shareAmount', 'completionRate','countPerf'],
    		data:data
    		});
    	
    });


function add(){
	var belongMonth = monthSel.getDate().format('yyyy-MM-dd');
	$.ajax({
		url : ctx + "/perf/verificationTime",
		method : "post",
		dataType : "json",
		traditional:true,
		data : {
			belongMonth : belongMonth
		},
		success : function(data) {
			if(data.success){
				if(data.content){
					window.wxc.alert('此月业绩已设定!');						
				}else{
					window.location.href = ctx + "/perf/perfGoal"; 
				}
			}
		}
	});
}
  