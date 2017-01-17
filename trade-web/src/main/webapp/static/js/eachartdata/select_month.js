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
    	monthDisplay = monthNow - 1;
    	yearDisplay = yearNow;
    }
    //点击变换颜色&&默认当前月份
    var $month_list = $(".calendar-month span");
    $(".calendar-year span").html(yearDisplay);
    for (var i=0; i<$month_list.length; i++) {
        if(i == monthDisplay) {
        	$month_list.eq(i).addClass("select-blue");
        }            
    }
    //增加年份置灰
    $("#add em").addClass("disabled");
    //月份置灰
    if(monthDisplay<11){
    $(".calendar-month span:gt("+monthDisplay+")").addClass("disabled");
    }
    reloadGrid(); 
    	
    $month_list.on("click",function() {
    	var year=$(".calendar-year span").html();
    	//置灰的月份点击事件失效
        if($(this).hasClass("disabled")){
        	return;
        }
        $(this).addClass("select-blue").siblings().removeClass('select-blue');
        reloadGrid();
    });
    //年份加减
    $("#subtract").click(function(){
        var year=$(".calendar-year span").html();
        //正常时间显示
        $(".calendar-month span").removeClass("disabled");
        $("#add em").removeClass("disabled");
        $(".calendar-year span").html(Number(year)-1);
        reloadGrid();
    })
    $("#add").click(function(){
        var year=$(".calendar-year span").html();
        //置灰的年份不让增加
        if($("#add em").hasClass("disabled")){
        	return;
        }
        $(".calendar-year span").html(Number(year)+1);
        reloadGrid();
        //月份置灰
        var year=$(".calendar-year span").html();
        if(year==yearDisplay){
        	$("#add em").addClass("disabled");
        	if(monthDisplay<11){
        	$(".calendar-month span:gt("+monthDisplay+")").addClass("disabled");
        	}
        	return;
        }
    })
})