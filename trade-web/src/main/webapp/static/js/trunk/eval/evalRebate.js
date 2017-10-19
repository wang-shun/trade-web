var AttachmentList = (function(){
    return {
        init : function(ctx,url,gridTableId,gridPagerId,caseCode){
            //jqGrid 初始化
            $("#"+gridTableId).jqGrid({
                url : ctx+url,
                mtype : 'GET',
                datatype : "json",
                height : 125,
                autowidth : true,
                shrinkToFit : true,
                rowNum : 3,
                colNames : [ '审批人','审批时间','审批结果','审批意见'],
                colModel : [ {
                    name : 'OPERATOR',
                    index : 'OPERATOR',
                    align : "center",
                    width : 25,
                    resizable : false
                },{
                    name : 'OPERATOR_TIME',
                    index : 'OPERATOR_TIME',
                    align : "center",
                    width : 25,
                    resizable : false
                }, {
                    name : 'NOT_APPROVE',
                    index : 'NOT_APPROVE',
                    align : "center",
                    width : 25,
                    resizable : false
                    //formatter : linkhouseInfo
                }, {
                    name : 'CONTENT',
                    index : 'CONTENT',
                    align : "center",
                    width : 25,
                    resizable : false
                }],
                multiselect: true,
                pager : "#"+gridPagerId,
                viewrecords : true,
                pagebuttions : true,
                multiselect:false,
                hidegrid : false,
                recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
                pgtext : " {0} 共 {1} 页",
                gridComplete:function(){
                    var ids = jQuery("#"+gridTableId).jqGrid('getDataIDs');
                    for (var i = 0; i < ids.length; i++) {
                        var id = ids[i];
                        var rowDatas = jQuery("#"+gridTableId).jqGrid('getRowData', ids[i]); // 获取当前行
                        var auditResult = rowDatas['NOT_APPROVE'];
                        var auditResultDisplay = null;
                        if(!auditResult){
                            auditResultDisplay="审批通过"
                        }else{
                            auditResultDisplay=auditResult;
                        }
                        jQuery("#"+gridTableId).jqGrid('setRowData', ids[i], { NOT_APPROVE: auditResultDisplay});
                    }
                },
                postData : {
                    queryId : "queryLoanlostApproveList",
                    caseCode : caseCode,
                    approveType : '18',
                    processInstanceId : ''
                }
            });
        }
    };
})();

function submit(ctx){
    if ($("#evalRecept").val().length==0) {
        window.wxc.alert("请填写评估费收据");
        return ;
    }
    var cost = $("#evalCost").val();
    var real = $('#evalCost').attr('real');
    if(isNaN(cost)  || parseFloat(cost) == 0 || parseFloat(cost) > parseFloat(real)){
        window.wxc.alert("请输入正确的评估公司成本!");
        return ;
    }

    var jsonData = $("#rebateForm").serializeArray();
    var url = ctx+"/task/evalRebate/assistant";

    $.ajax({
        cache : false,
        async : true,//false同步，true异步
        type : "POST",
        url : url,
        dataType : "json",
        data : jsonData,
        beforeSend : function() {
            $.blockUI({
                message : $("#salesLoading"),
                css : {
                    'border' : 'none',
                    'z-index' : '1900'
                }
            });
            $(".blockOverlay").css({
                'z-index' : '1900'
            });
        },
        complete : function() {
            $.unblockUI();
        },
        success : function(data) {
        	if(data.ajaxResponse.success){
                window.wxc.success("评估返利确认成功!",{"wxcOk":function(){
                    //关闭当前页面，并通知父页面刷新
                    if(window.opener)
                    {
                        window.close();
                        window.opener.callback();
                    } else {
                        window.location.href = ctx + "/task/eval/evalTaskList";
                    }
                }});
			}else{
                window.wxc.error(data.ajaxResponse.message);
			}
            $.unblockUI();
        },
        error : function(errors) {
            $.unblockUI();
            window.wxc.error("数据保存出错");
        }
    });
}

function cal(){
    var cost = $('#evalCost').val();
    var real = $('#evalCost').attr('real');
    if(!isNaN(cost) && parseFloat(cost) > 0 && !isNaN(real) && parseFloat(real)){
        $('#centacomamount').html('中原分成金额:'+(parseFloat(real) - parseFloat(cost)).toFixed(2));
        $('#evacomamount').html('评估公司分成金额:'+parseFloat(cost).toFixed(2));
    }else{
        $('#centacomamount').html('中原分成金额:');
        $('#evacomamount').html('评估公司分成金额:');
    }
}