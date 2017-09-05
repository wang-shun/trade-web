$(document).ready(function(){
	var ctx = $("#ctx").val();

	var month=$('#monthSelection option:selected') .val();

    alert(ctx);
    $.ajax({
        async: true,
        url:ctx+ "/AuditImportCase/listData",
        method: "post",
        dataType: "json",
        data: month,
        beforeSend: function () {
           /* $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
            $(".blockOverlay").css({'z-index':'9998'});*/
        },
        success: function(data){
            //$.unblockUI();
            data.ctx = ctx;
            var rowsData={};
            rowsData.rows=data;
            var auditCaseList = template('template_auditCaseList' , rowsData);
            $("#auditCaseList").empty();
            $("#auditCaseList").html(auditCaseList);
            // 显示分页
//            mycase_initpage(data.total,data.pagesize,data.page, data.records);
//            demoPoshytip();
//            $("#myCaseList").subscribeToggle({
//                moduleType:"1001",
//                subscribeType:"2001"
//            });

        },

    });
});

