/**
 * 产调处理结果 liaohail
 * 
 */

function initData() {
	$(".table_content")
			.aistGrid(
					{
						ctx : ctx,
						url : "/quickGrid/findPage",
						queryId : 'perfGoalQuery',
						templeteId : 'template_successList',
						gridClass : 'table table_blue table-striped table-bordered table-hover',
						data : getParams(),
						columns : [ {
							colName : "<input type='checkbox' value='' name='items' id='ckb_checkall'>"
						}, {
							colName : "组织"
						}, {
							colName : "成员"
						}, {
							colName : "业绩月份"
						}, {
							colName : "	业绩目标"
						}, {
							colName : "设定人"
						},{
							colName : "设定时间"
						},{
							colName : "设定状态"
						},{
							colName : "操作"
						}]

					});
}
function reloadGrid() {
	var data = getParams();
	$(".table_content").reloadGrid({
		ctx : ctx,
		queryId : 'perfGoalQuery',
	    templeteId : 'template_successList',
	    data : data,
	    wrapperData : {ctx:ctx}
    })
}

function getParams() {
	var data={};
	data.teamId=$("#sel_team").val();
	data.belongMonth=belongMonth;
	data.status=$("#sel_status").val();

	return data;
}

