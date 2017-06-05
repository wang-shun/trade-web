/**
 * 待办任务
 * wanggh
 */
$(document).ready(function() {
	

			var url = "/quickGrid/findPage";
			var ctx = $("#ctx").val();
			url = ctx + url;

			//jqGrid初始化
			$("#table_list_1").jqGrid(
					{
						url : url,
						datatype : "json",
						mtype : "POST",
						height : 250,
						autowidth : true,
						shrinkToFit : true,
						rowNum : 15,
						/* rowList: [10, 20, 30], */
						colNames : [ 'TASKID','CASECODE','PARTCODE','INSTCODE', '任务名', '产证地址', '经纪人',
								 '预计执行时间' ],
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
							width : '20%'
						}, {
							name : 'PROPERTY_ADDR',
							index : 'PROPERTY_ADDR',
							width : '30%'
						}, {
							name : 'AGENT_NAME',
							index : 'AGENT_NAME',
							width : '20%'
						}, {
							name : 'EST_PART_TIME',
							index : 'EST_PART_TIME',
							width : '30%'
						}
						],
						pager : "#pager_list_1",
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",

						// rowid为grid中的行顺序
						onSelectRow : function(rowid) {
							var rowData = $("#table_list_1").jqGrid('getRowData',rowid);
							var url = ctx+"/task/mobile/"+rowData.PART_CODE+
							"?&taskId="+rowData.ID+"&caseCode="+rowData.CASE_CODE+"&instCode="+rowData.INST_CODE;
//							alert(url);
							window.location.href = url;
						},
						postData : {
							queryId : "queryMobileTaskListItemList"
						}

					});

			// Add responsive to jqGrid
			$(window).bind('resize', function() {
				var width = $('.jqGrid_wrapper').width();
				$('#table_list_1').setGridWidth(width);
			});
			//ie
			if ($.support.msie) {
				$('input:radio').click(function () {
					this.blur();
					this.focus();
				});
			}; 
			
		});

function intextTypeChange(){
	var inTextType = $('#inTextType').val();
	var ctx = $("#ctx").val();

	if (inTextType=='3'){
		initAutocomplete(ctx+"/labelVal/queryOrgInfo");
	}
}
function initAutocomplete(url){
	$("#inTextVal").AutoComplete({
		data:url,
		'itemHeight': 20,
        'width': 280,
        maxItems:10,
        ajaxType:'POST',
        beforeLoadDataHandler:function(){
        	$("#inTextVal").attr('hVal','');
        	return true;
        },
        afterSelectedHandler:function(data){ 
        	if(data&&data.value){
        		$("#inTextVal").attr('hVal',data.value);
        	}else{
        		$("#inTextVal").attr('hVal','');
        	}
		}
    }).AutoComplete('show');
}
