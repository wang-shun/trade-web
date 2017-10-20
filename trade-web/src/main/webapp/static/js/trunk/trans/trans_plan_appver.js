/**
 * 交易计划审批
 * @author wbzhouht
 */

$(document).ready(function() {
					
					var url = "/quickGrid/findPage";
					var ctx = $("#ctx").val();
					url = ctx + url;
					var caseCode = $("#caseCode").val();
					var auditResult=0;//查询待审核记录
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
						colNames : [ 'id','PART_CODE_OLD','APPVER_PART_CODE_OLD','案件编号','变更项',
								'变更前交易计划', '变更后交易计划','变更原因','发起环节','审批结果'],
						colModel : [ {
                            name : 'PKID',
                            index : 'PKID',
                            align : "center",
                            width : 0,
                            key : true,
                            resizable : false,
                            hidden : true
                        },{
                            name : 'PART_CODE_OLD',
                            index : 'PART_CODE_OLD',
                            align : "center",
                            width : 0,
                            key : true,
                            resizable : false,
                            hidden : true
                        },{
                            name : 'APPVER_PART_CODE_OLD',
                            index : 'APPVER_PART_CODE_OLD',
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
						},
						 {
							name : 'CHANGE_REASON',
							index : 'CHANGE_REASON',
							width : 120
						},{
                                name : 'APPVER_PART_CODE',
                                index : 'APPVER_PART_CODE',
                                width : 60
                            },{
                                name : 'AUDIT_RESULT',
                                index : 'AUDIT_RESULT',
                                width : 60
                            }
						 
						],
						pager : "#pager_list_1",
						viewrecords : true,
						pagebuttions : true,
						hidegrid : false, 
						cellEdit:true,
						recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
						pgtext : " {0} 共 {1} 页",

						postData : {
							queryId : "queryTransAppverHistoryList",
							search_caseCode:caseCode,
                            search_auditResult:auditResult
						}

					});
					// Add responsive to jqGrid
					$(window).bind('resize', function() {
						var width = $('.jqGrid_wrapper').width();
						$('#table_list_1').setGridWidth(width);
					});
});
function save(boo) {
	var audit=false;
	if(boo){
		audit=true;
	}
	var obj=new Array();
	var newDates=new Array();
	var partCodes=new Array();
	var caseCode=$("#caseCode").val();
	var processInstanceId=$("#processInstanceId").val();
	var taskId=$("#taskId").val();

	var pkids=new Array();
	obj=$("#table_list_1").getRowData();
	console.log(obj)
	for(var i=0;i<obj.length;i++){
		newDates.push(obj[i].NEW_EST_PART_TIME);
		partCodes.push(obj[i].PART_CODE_OLD);
		pkids.push(obj[i].PKID);
	}
    var partCode=obj[0].APPVER_PART_CODE_OLD;
	console.log("newDates:"+newDates+"partCodes:"+partCodes+"partCode"+partCode)
	var url="/task/transPlan/submitTransAppver?";
	var ctx=$("#ctx").val();
	url=ctx+url;
	console.log(url)
	var data="&pkids="+pkids+"&newDates="+newDates+"&partCodes="+partCodes+
		"&caseCode="+caseCode+"&taskId="+taskId+"&processInstanceId="+processInstanceId+"&audit="+audit+"&partCode="+partCode;
	$.ajax({
		cache:false,
		async:true,
		type:"post",
		url:url,
		dataType:"json",
		timeout:10000,
		data:data,
		beforeSend:function () {
            $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
            $(".blockOverlay").css({'z-index':'9998'});
		},
		success:function (data) {
			if(data.success){
			window.wxc.success("操作成功",{"wxcOk":function () {
				console.log(ctx)
				window.location.href=ctx+"/task/myTaskList";
            }})
            }
		},
		complete:function () {
			$.unblockUI();
		},
		error:function (errors) {
            window.wxc.error("数据保存出错");
            $.unblockUI();
            window.location.href=ctx+"/task/myTaskList";
        }

	});
}

