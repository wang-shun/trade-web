var ctx = $("#ctx").val();
var index = 1;//记录页面现有的案件数量
var banklist;//记录银行列表模板
/**
 * 银行返利 部分需要页面初始化的参数
 * 及初始化页面的一些方法
 */
function pageInit(idx,bank){
	index = idx;
	banklist = bank;
	//关联案件表格初始化
    $(".eloanApply-table").aistGrid(
        {
            ctx : ctx,
            queryId : 'queryCastListForRelation',
            templeteId : 'queryCastListItemList',
            rows : '6',
            gridClass : 'table table_blue mt20 table-striped table-bordered table-hover customerinfo',
            data : '',
            wrapperData : {
                ctx : ctx
            },
            columns : [
                {
                    colName : "<span class='sort'  onclick='caseCodeSort();'' >案件编号</span><i id='caseCodeSorti' class='fa fa-sort-desc fa_down'></i>",
                    sortColumn : "CASE_CODE",
                    sord : "desc",
                    sortActive : true
                }, {
                    colName : "状态"
                }, {
                    colName : "产证地址"
                }, {
                    colName : "买家"
                }, {
                    colName : "卖家"
                }, {
                    colName : "操作"
                } ]
        });

    // 关联案件 点击事件绑定
    $('.eloanApply-table').on("click",'.linkCase',function() {
        var caseCode = $(this).attr("caseCode");
        var caseId = $(this).attr("caseId");
        var ccaiCode = $(this).attr("ccaiCode");
        var hidden = $('#ccai_'+clickIndex);
        var show = hidden.prev("input");
        if(show){
            show.val(caseCode);
        }
        hidden.val(ccaiCode);
        var href = $('#info').find('tr:eq('+(clickIndex+1)+') a');
        href.attr('target','_blank');
        href.attr('href',ctx+'/case/caseDetail?caseId='+caseId);
        $('#caseModal').modal('hide');
    });

    // 案件查询按钮
    $('#caseSearchButton').click(function() {
        reloadCaseGrid();
    });
    $('#addRecord').click(function () {
        var txt = '<tr id="tr_' + index + '"><td>';
        txt += '<input class="form-control" type="text" name="toBankRebateInfoList['+index+'].caseCode" readonly placeholder="请选择案件" onclick="showCase('+index+')">'
        txt += '<input id="ccai_'+index+'" type="hidden" name="toBankRebateInfoList['+index+'].ccaiCode" />';
        txt += '</td>';
        txt += '<td >';
        txt += '<a>查看案件</a></td>';
        txt += '<td width="17%">';
        txt += banklist.replace("{index}",index).replace("{index}",index);
        txt += '</td>';
        txt += '<td width="17%"><input id="rebateMoney_' + index + '" name="toBankRebateInfoList[' + index + '].rebateMoney" type="text" class="form-control"  value="" /></td>';
        txt += '<td width="12%"><input id="warrantMoney_' + index + '" name="toBankRebateInfoList[' + index + '].rebateWarrant" type="text" class="form-control"  value=""  readonly /></td>';
        txt += '<td width="12%"><input id="businessMoney_' + index + '" name="toBankRebateInfoList[' + index + '].rebateBusiness" type="text" class="form-control"  value=""  readonly /></td>';
        txt += '<td><span><a href="javascript:removeTr(' + index + ');"><font>删除</font></a></span></td></tr></tbody>';
        $('#info tbody').append(txt)
        index++;
    });
    //提交按钮事件绑定
    $('#submitBtn').click(function () {
        var guaranteeCompany = $("#guaranteeCompany").val();
        if (guaranteeCompany.length==0) {
            window.wxc.alert("请选择担保公司！");
            return;
        }
        var total = parseFloat($('#rebateMoney').val());
        var count = 0;
        var check = true;
        $('#info').find("tr:gt(0)").each(function(index,tr){
            var rebateMoneyi = $(tr).find('td:eq(3)').find('input');
            if(isNaN($(rebateMoneyi).val())){
                window.wxc.alert("请输入正确的返利金额！");
                check = false;
                return true;
            }else{
                count +=  parseFloat(rebateMoneyi.val());
            }
            var ccaiCode = $(tr).find('td:eq(0)').find('input:eq(1)').val();
            var bankName = $(tr).find('td:eq(2) select').val();
            if(!ccaiCode || ccaiCode.length==0){
                window.wxc.alert("请选择案件！");
                check = false;
                return true;
            }
            if(!bankName || bankName.length==0){
                window.wxc.alert("请选择返利银行！");
                check = false;
                return true;
            }
        });
        if(!check) return;
        if (count == total) {
            window.wxc.confirm("确定保存吗？", {
                "wxcOk": function () {
                    save(true);
                }
            });
        } else {
            window.wxc.alert("返利单明细【返利金额】之和与【总金额】不匹配，请检查！");
        }
    });
}
/**
 * 显示案件选择表格
 * @type {number} 点击的表格行数
 */
var clickIndex = 0;
function showCase(index){
    clickIndex = index;//记录点击的行号
    $('#caseModal').modal("show");
}
/**
 * 删除一行记录
 * @param index 行号
 * @param type 删除类型 如果为edit 则进行ajax请求
 */
function removeTr(index) {
    // TODO 此处有坑 后续处理 移除后索引并没更新 新添加的索引不会使用删除的索引
    var pkid = $('#pkId_'+index);
    if(pkid.length > 0){
        ajaxDeleteInfo(pkid.val(),index);
    }else{
        $("#tr_" + index).remove();
        cal();//重新计算金额
    }
}
/**
 * 删除银行返利案件信息，并删除tr
 * @param pkid
 * @param index
 */
function ajaxDeleteInfo(pkid,index){
    window.wxc.confirm("删除后信息无法恢复，确认删除？",{"wxcOk":function(){
        $.ajax({
            async: false,
            type: "POST",
            url: ctx+'/bankRebate/deleteBankRebateInfo',
            data: {pkid:pkid},
            dataType: "json",
            beforeSend: function () {
                $.blockUI({
                    message: $("#salesLoading"),
                    css: {
                        'border': 'none',
                        'z-index': '9999'
                    }
                });
                $(".blockOverlay").css({
                    'z-index': '9998'
                });
            },
            success: function (data) {
                $.unblockUI();
                if (data.success) {
                    $("#tr_" + index).remove();
                    cal();//重新计算金额
                } else {
                    if (data.message) {
                        window.wxc.alert("删除信息失败:" + data.message);
                    }
                }
            },
            error: function () {
                $.unblockUI();
                window.wxc.alert("删除信息出错");
            }
        });
    }});
}

function reloadCaseGrid() {
    var data = {};
    var propertyAddr = $.trim($("#propertyAddr").val());
    var caseCode = $.trim($("#caseCodet").val());
    var caseName = $.trim($("#caseNamet").val());

    data.propertyAddr = propertyAddr;
    data.caseCode = caseCode;

    $(".eloanApply-table").reloadGrid({
        ctx : ctx,
        rows : '6',
        queryId : 'queryCastListForRelation',
        templeteId : 'queryCastListItemList',
        wrapperData : {
            ctx : ctx
        },
        data : data
    })
}
/**
 * 计算返利分成和合计
 */
function cal(){
    var total = parseFloat($('#rebateMoney').val());
    var count = 0;
    $('#info').find("tr:gt(0)").each(function(index,tr){
        var rebateMoneyi = $(tr).find('td:eq(3)').find('input');
        var rebateWarranti = $(tr).find('td:eq(4)').find('input');
        var rebateBusinessi = $(tr).find('td:eq(5)').find('input');

        if(isNaN($(rebateMoneyi).val())){
            window.wxc.alert("请输入正确的返利金额！");
            return;
        }else{
            var rebateMoney = parseFloat($(rebateMoneyi).val());
            rebateWarranti.val((rebateMoney* 0.3).toFixed(2));
            rebateBusinessi.val((rebateMoney* 0.7).toFixed(2));
            count += rebateMoney;
        }
    });
    $("#enteringMoney").val(count.toFixed(2));
    $("#differenceMoney").val((total-count).toFixed(2));
}
//审批记录
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
                    approveType : '22',
                    processInstanceId : ''
                }
            });
        }
    };
})();