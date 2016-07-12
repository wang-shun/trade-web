/**
 * 同案件任务列表
 */
function caseTaskCheck(){
	setTimeout('initGrid()',1000); 
}

function initGrid() {
	var url = "/quickGrid/findPage";
	url = ctx + url;
	//jqGrid初始化
	$("#table_list_case_task").jqGrid(
			{
				url : url,
				datatype : "json",
				mtype : "POST",
				height : 150,
				autowidth : true,
				shrinkToFit : true,
				rowNum : 5,
				/* rowList: [10, 20, 30], */
				colNames : [ 'TASKID','CASECODE','PARTCODE','INSTCODE', '任务名', '预计执行时间' ],
				colModel : [ {
					name : 'ID',
					index : 'ID',
					align : "center",
					width : 0,
					key : true,
					resizable : false,
					hidden : true
				},{
					name : 'CASE_CODE',
					index : 'CASE_CODE',
					align : "center",
					width : 0,
					key : true,
					resizable : false,
					hidden : true
				},{
					name : 'PART_CODE',
					index : 'PART_CODE',
					align : "center",
					width : 0,
					key : true,
					resizable : false,
					hidden : true
				},{
					name : 'INST_CODE',
					index : 'INST_CODE',
					align : "center",
					width : 0,
					key : true,
					resizable : false,
					hidden : true
				}, {
					name : 'NAME',
					index : 'NAME',
					width : 160
				}, {
					name : 'EST_PART_TIME',
					index : 'EST_PART_TIME',
					width : 260
				},

				],
				pager : "#pager_list_case_task",
				viewrecords : true,
				pagebuttions : true,
				rownumbers : true,
				hidegrid : false,
				recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
				pgtext : " {0} 共 {1} 页",

				// rowid为grid中的行顺序
				onSelectRow : function(rowid) {
					var rowData = $("#table_list_case_task").jqGrid('getRowData',rowid);
					var url = ctx+"/engine/task/"+rowData.ID+"/process";
//					alert(url);
					window.location.href = url;
				},
				loadComplete :function(){
					var rowNum = $("#table_list_case_task").jqGrid('getGridParam','records');
//					alert(rowNum);
					if(rowNum=="0"){
						returnTaskPage();
					}else{
						$('.modal').modal("hide");
						$('#case-task-modal-form').modal("show");
					}
				},
				postData : {
					queryId : "queryTaskListItemList",
					search_caseCode:caseCode
				}

			});

	 
		$('#table_list_case_task').setGridWidth('650px');
		$('#case-task-modal-form').on('show.bs.modal', function (m) {
			$('#case-task-modal-form').on('hide.bs.modal', function (m) {
				returnTaskPage();});
		});
}


function returnTaskPage(){
	 if(window.opener)
     {
		 window.close();
		 window.opener.callback();
     } else {
    	 window.location.href = ctx+"/task/myTaskList";
     }
	
	//window.location.href = ctx+"/task/myTaskList";
}