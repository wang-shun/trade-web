var yearLast = window.parent.yearLast;
var monthLast = window.parent.monthLast;
//声明各iframe
var iframe1 = window.parent.window.document.getElementById("iframe1");
var iframe2 = window.parent.window.document.getElementById("iframe2"); 
var iframe3 = window.parent.window.document.getElementById("iframe3");
var iframe4 = window.parent.window.document.getElementById("iframe4");
var iframesArr = [iframe1,iframe2,iframe3,iframe4];

$(function() {	
	
	resetDataModel();
	
	$.each(iframesArr,function(i,item){
		//点击变换颜色&&默认当前月份
	    var $month_list = $(item.contentWindow.document).find(".calendar-month span");
	 	$month_list.on("click",function() {
	 		window.parent.monthDisplay = parseInt($(this).html().replace("月","")) - 1;
	    	resetDataModel();
	    });
	    //年份加减
	 	$(item.contentWindow.document).find("#subtract").click(function(){
	 		window.parent.yearDisplay--;
	    	resetDataModel();
	    })
	    $(item.contentWindow.document).find("#add").click(function(){
	    	window.parent.yearDisplay++;
	    	resetDataModel();
	    })
	})	
})

function resetDataModel(){
    $.each(iframesArr,function(i,item){
    	$(item.contentWindow.document).find(".calendar-year span").html(window.parent.yearDisplay);
    	$(item.contentWindow.document).find(".calendar-month span:eq("+window.parent.monthDisplay+")").addClass("select-blue").siblings().removeClass("select-blue");
        //增加年份置灰
    	if(window.parent.yearDisplay == yearLast){
        	$(item.contentWindow.document).find("#add em").addClass("disabled");
    	}else{
    		$(item.contentWindow.document).find("#add em").removeClass("disabled");
    	}
        //月份置灰
        if(window.parent.monthDisplay<11 
        		&& window.parent.yearDisplay == yearLast 
        		&& window.parent.monthDisplay == monthLast){
        	$(item.contentWindow.document).find(".calendar-month span:gt("+window.parent.monthLast+")").removeClass("disabled").addClass("disabled");
        }  
        
        item.contentWindow.reloadGrid();
    });
}