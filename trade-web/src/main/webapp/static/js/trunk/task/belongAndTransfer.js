/**
 * Created by caoyuan7 on 2017/5/11.
 */
var startList=0;//判断是不是应该显示列表
$(function(){
    var userOrg = $("#userOrg").val();
    var selectJobCode = $("#selectJobCode").val();

    var data = {
        search_userOrg:$.trim(userOrg),
        search_selectJobCode:$.trim(selectJobCode),
        queryId : "queryCaseBelongAndTransfer",
        rows : 10,
        page : 1
    };
    aist.wrap(data);
    startList=1;
    reloadGrid(data);
})

function reloadGrid(data) {
    $.ajax({
        async: true,
        url:ctx+ "/quickGrid/findPage" ,
        method: "post",
        dataType: "json",
        data: data,
        success: function(data){
            //console.log("数据"+JSON.stringify(data));
            var myTaskList = template('template_belongAndTransfer' , data);
            $("#myTaskList").empty();
            if(startList==1 ){
                $("#myTaskList").html(myTaskList);
                // 显示分页
                initpage(data.total,data.pagesize,data.page, data.records);
                startList=0;
            }
            $.unblockUI();
        },
        error: function (e, jqxhr, settings, exception) {
            $.unblockUI();
        }
    });
}
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

function searchMethod(page){
    var data = getParams(page);
    aist.wrap(data);
    reloadGrid(data);
}
function getParams(page) {
    if(!page) {
        page = 1;
    }
    var userOrg = $("#userOrg").val();
    var selectJobCode = $("#selectJobCode").val();
    if(userOrg.length>0&&selectJobCode.length>0){
        startList=1;
    }else{
        startList=0;
    }

    var data = {
        search_userOrg:$.trim(userOrg),
        search_selectJobCode:$.trim(selectJobCode),
        queryId : "queryCaseBelongAndTransfer",
        rows : 10,
        page : page
    };

    return data;
}