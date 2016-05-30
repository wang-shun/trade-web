/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */

var ListSwith = function () {

    return {
        //main function to initiate the module
        init: function (advListIcon, jqListIcon, advListId, jqListId) {
            function showJqList(){
            	if($('#'+advListIcon).hasClass('active')){
            		//console.log("有");
            		$('#'+advListIcon).removeClass('active');
            		$('#'+jqListIcon).addClass('active');
            		$('#'+advListId).hide();
            		$('#'+jqListId).show();
            	}
            }
            
            function showAdvList(){
            	if($('#'+jqListIcon).hasClass('active')){
            		$('#'+jqListIcon).removeClass('active');
            		$('#'+advListIcon ).addClass('active');
            		$('#'+advListId).show();
            		$('#'+jqListId).hide();
            	}
            }
            
            $('#'+advListIcon).click(showAdvList);
            $('#'+jqListIcon).click(showJqList);
         //end init()   
        }
    };

}();
/*function seachlist(){
	qhtab=2;
	$("#list5").setGridParam({ rowNum: 10 }).trigger("reloadGrid");
	jQuery("#list5").jqGrid('navGrid','#pager5',{edit:false,add:false,del:false});
    jQuery("#list5").setGridWidth($(".page-header-fixed").width()*0.92); 
}

function seachView(){
	qhtab=1;
}*/