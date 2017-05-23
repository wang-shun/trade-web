/**
 * Created by caoyuan7 on 2017/5/11.
 */
var startList=0;//判断是不是应该显示列表
$(function(){
    getList();
    $("#searchButton").click(function(){
        searchMethod(1);
    });
    $("#changePr").click(function(){
        $("#codeShow").html($("#changCaseCode").val());
        $("#leadingProForChang").show();
    });
    //变更责任人取消
    $("#leadingProClose").click(function(){
        cleanForm();
        $("#leadingProForChang").hide();
    });
    //变更责任人提交
    $("#leadingProSubmit").click(function(){
        var caseCode = $("#changCaseCode").val(); // 案件的caseCode
        var leadingProId = $("#leadingProId").val();//新的责任人userId
        var detailCode = "spv";//E+案件变更归属人提交的专属code
        var userId =$("#userId").val();

        if(leadingProId == "" || leadingProId ==  null || leadingProId == undefined){
            window.wxc.alert("若要变更项目责任人，请先选择新的案件责任人！");
            return;
        }

        window.wxc.confirm("您确定要进行责任人变更？",{"wxcOk":function(){
            var url = "/case/handler/changeLeadingPro";
            var ctx = $("#ctx").val();
            url = ctx + url;
            var data = {
                leadingProId:leadingProId,
                changItems:caseCode,
                detailCode:detailCode,
                userId:userId
            };

            $.ajax({
                async : true,
                type : "POST",
                url : url,
                dataType : "json",
                timeout : 10000,
                data : data,
                beforeSend : function() {
                    $.blockUI({
                        message : $("#salesLoading"),
                        css : {
                            'border' : 'none',
                            'z-index' : '9999'
                        }
                    });
                    $(".blockOverlay").css({
                        'z-index' : '9998'
                    });
                },
                complete : function() {
                    $.unblockUI();
                },
                success : function(data) {
                    if(data.success){
                        $("#leadingProForChang").hide();
                        //reloadGrid(getParams(1));
                        window.wxc.success("恭喜，责任人变更成功！");
                        deleteDomByCaseCode(caseCode);
                    }else{
                        window.wxc.error(data.message);
                    }

                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    window.wxc.error("系统错误,请重新登录");
                }
            });
        }});

    })
    checkBoxALL();//全选和反选
})
//加载数据的核心方法****!不是即时的!
function reloadGrid(data) {
    $.ajax({
        async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data,
        success: function(data){
            var myTaskList = template('template_belongAndTransfer' , data);
            $("#myTaskList").empty();
            $("#myTaskList").html(myTaskList);
            // 显示分页
            initpage(data.total,data.pagesize,data.page, data.records);
            startList=0;
            $.unblockUI();
            $(".checkbox_input").click(function(){
                var thisCaseCode = $(this).val();
                $("#changCaseCode").val(editCaseCode(thisCaseCode));
            });
            $("#changCaseCode").val('');
        },
        error: function (e, jqxhr, settings, exception) {
            $.unblockUI();
        }
    });
}
//分页的方法
function initpage(totalCount,pageSize,currentPage,records)
{
    if(totalCount>1500){
        totalCount = 1500;
    }
    var currentTotalstrong=$('#currentTotalPage').find('strong');
    if (totalCount<1 || pageSize<1 || currentPage<1)
    {
        $(currentTotalstrong).empty();
        $('#totalP').text(0);
        $("#pageBar").empty();
        return;
    }
    $(currentTotalstrong).empty();
    $(currentTotalstrong).text(currentPage+'/'+totalCount);
    $('#totalP').text(records);
    $("#pageBar").twbsPagination({
        totalPages:totalCount,
        visiblePages:9,
        startPage:currentPage,
        first:'<i class="fa fa-step-backward"></i>',
        prev:'<i class="fa fa-chevron-left"></i>',
        next:'<i class="fa fa-chevron-right"></i>',
        last:'<i class="fa fa-step-forward"></i>',
        showGoto:true,
        onPageClick: function (event, page) {
            //console.log(page);
            searchMethod(page);
        }
    });
}
//搜索的方法
function searchMethod(page){
    var data = getParams(page);
    aist.wrap(data);
    reloadGrid(data);
}
//分页得到参数的方法
function getParams(page) {
    if(!page) {
        page = 1;
    }
    var userId = $("#userId").val();
    var detailCode = $("#detailCode").val();
    var caseCode = $("#caseCode").val();
    var caseAddress = $("#caseAddress").val();


    if(userId.length>0&&detailCode.length>0){
        startList=1;
    }else{
        startList=0;
    }
    var data = {
        search_MuserId:$.trim(userId),
        search_caseCode:$.trim(caseCode),
        search_caseAddress:$.trim(caseAddress),

        queryId : "queryCaseBelongAndTransferSpvDetail",
        rows : 10,
        page : page
    };
    return data;
}
//全选和反选的操作
function checkBoxALL(){
    $("#checkAll").click(function(){
        if($("#checkAll").is(':checked')){
            $("#myTaskList input[type='checkbox']").prop("checked", true);
            $("#changCaseCode").val("");
            $(".caseCode_choice").each(function(index){
                $("#changCaseCode").val(editCaseCode($(this).text()));
            });
        }else{
            $("#myTaskList input[type='checkbox']").removeAttr("checked");
            $(".caseCode_choice").each(function(index){
                $("#changCaseCode").val("");
            });
        }
    });
}
//首次进入页面得到列表的方法
function getList(){
    var userId = $("#userId").val();
    var detailCode = $("#detailCode").val();
    var data = {
        search_MuserId:$.trim(userId),
        queryId : "queryCaseBelongAndTransferSpvDetail",
        rows : 10,
        page : 1
    };
    aist.wrap(data);
    startList=1;
    console.log(data);
    reloadGrid(data);
}
//选择框选择后拼接caseCode字符串的方法
function editCaseCode(str){
    var thisCaseCode = $("#changCaseCode").val();
    if(''!=thisCaseCode){
        if(thisCaseCode.indexOf(str)>=0){
            if(thisCaseCode.indexOf(str)==0){
                if(thisCaseCode.length>str.length){
                    thisCaseCode = thisCaseCode.replace(str+",","");
                }else{
                    thisCaseCode = thisCaseCode.replace(str,"");
                }

            }else{
                thisCaseCode = thisCaseCode.replace(","+str,"");
            }

        }else{
            thisCaseCode=thisCaseCode+","+str;
        }
    }else{
        thisCaseCode=str;
    }
    return thisCaseCode;
}
function cleanForm(){
    $("input[name='leadingProName']").val();
    $("input[name='leadingProId']").val();
    $("input[name='cooperName']").val();
    $("input[name='preProcessorId']").val();
    $("input[name='processorId']").val();
    $("#codeShow").html("");

}
//变更责任人弹框
function changeProfessor(caseCode){
    cleanForm();
    $("#leadingProForChang").show();
    if(caseCode){
        $("#caseCodeForChange").val(caseCode);
        var url = "/case/selectLeandingPro";
        var ctx = $("#ctx").val();
        url = ctx + url;

        $.ajax({
            cache : false,
            async : true,
            type : "POST",
            url : url,
            dataType : "json",
            timeout : 10000,
            data :[{
                name:'caseCode',
                value:caseCode
            }],
            success : function(data) {
                $("#leadingProName").val(data.leadingProcessName);
                $("#leadingProId").val(data.leadingProcessId);
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
}
//案件责任人
function leadingProForChangeClick(){
    var orgId = $("#orgId").val();
    userSelect({
        startOrgId : orgId,
        expandNodeId : orgId,
        nameType : 'long|short',
        orgType : '',
        departmentType : '',
        departmentHeriarchy : '',
        chkStyle : 'radio',
        //	jobCode : 'Manager,Senior_Manager',
        jobCode : 'Manager,Consultant',
        callBack : selectLeadingPro
    });
}

//选取责任人的回调函数
function selectLeadingPro(array) {
    if (array && array.length > 0) {
        $("#leadingProName").val(array[0].username);
        $("#leadingProName").attr('hVal', array[0].userId);
        $("#leadingProId").val(array[0].userId);

    } else {
        $("#leadingProName").val("");
        $("#leadingProName").attr('hVal', "");
    }
}

function deleteDomByCaseCode(caseCodes){
    var caseCode = caseCodes.split(",");
    for(var i=0;i<caseCode.length;i++){
        $("#myTaskList tr[name='"+caseCode[i]+"']").remove();
    }

}