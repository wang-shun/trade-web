$(function() {
	//默认选定上个月
	var yearDisplay,monthDisplay;
    var now = new Date();
    var yearNow = now.getFullYear();
    var monthNow = now.getMonth();
    if(monthNow == 0){
    	monthDisplay = 11;
    	yearDisplay = yearNow - 1;
    }else{
    	monthDisplay = month - 1;
    	yearDisplay = yearNow;
    }

    //点击变换颜色&&默认当前月份
    var $month_list = $(".calendar-month span");
    $(".calendar-year span").html(yearDisplay);
    for (var i=0; i<$month_list.length; i++) {
        if(i == monthDisplay&&monthDisplay!=0) {
            $(".calendar-year span").html(year);
            $month_list.eq(i-1).addClass("select-blue");
        }else{
        	 $(".calendar-year span").html(year-1);
             $month_list.eq(11).addClass("select-blue");
        }
        return false;
    }
    reloadGrid(); 
    
    $month_list.on("click",function() {
        $(this).addClass("select-blue").siblings().removeClass('select-blue');
        reloadGrid();
    });
    
    //年份加减
    $("#subtract").click(function(){
        var year=$(".calendar-year span").html();
        $(".calendar-year span").html(Number(yearDisplay)-1);
        reloadGrid();
    })
    $("#add").click(function(){
        var year=$(".calendar-year span").html();
        $(".calendar-year span").html(Number(yearDisplay)+1);
        reloadGrid();
    })
})