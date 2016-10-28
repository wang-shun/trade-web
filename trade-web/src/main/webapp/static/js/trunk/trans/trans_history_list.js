/**
 * 交易计划历史记录 wanggh
 */

$(document).ready(function() {
					// Examle data for jqGrid
					// Configuration for jqGrid Example 1
					// /$("#case_date").addClass('btn btn-white chosen-select');
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
					var caseCode = $("#caseCode").val();
					// jqGrid 初始化
					$("#table_list_1").jqGrid(
					{
						url : url,
						mtype : 'POST',
						datatype : "json",
						height : 250,
						autowidth : true,
						shrinkToFit : true,
						rowNum : 10,
						/* rowList: [10, 20, 30], */
						colNames : [ 'id','案件编号', '环节名称',
								'原预计时间', '新预计时间', '变更人', '变更时间','变更原因'],
						colModel : [ {
							name : 'PKID',
							index : 'PKID',
							align : "center",
							width : 0,
							key : true,
							resizable : false,
							hidden : true
						}, {
							name : 'CASE_CODE',
							index : 'CASE_CODE',
							width : 90
						}, {
							name : 'PART_CODE',
							index : 'PART_CODE',
							width : 90
						}, {
							name : 'OLD_EST_PART_TIME',
							index : 'OLD_EST_PART_TIME',
							width : 90
						}, {
							name : 'NEW_EST_PART_TIME',
							index : 'NEW_EST_PART_TIME',
							width : 90
						}, {
							name : 'CHANGER_ID',
							index : 'CHANGER_ID',
							width : 60
						}, {
							name : 'CHANGE_TIME',
							index : 'CHANGE_TIME',
							width : 110
						},
						 {
							name : 'CHANGE_REASON',
							index : 'CHANGE_REASON',
							width : 120
						},
						 
						],
						pager : "#pager_list_1",
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false, 
						cellEdit:true,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",

						postData : {
							queryId : "queryTransHistoryList",
							search_caseCode:caseCode
						}

					});

					// Add responsive to jqGrid
					$(window).bind('resize', function() {
						var width = $('.jqGrid_wrapper').width();
						$('#table_list_1').setGridWidth(width);

					});
					
					

});

