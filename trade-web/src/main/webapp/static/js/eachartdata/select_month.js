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
    reloadGrid();
    //增加年份置灰
    $("#add em").addClass("disabled");
    //月份置灰
    if(monthDisplay<11){
    $(".calendar-month span:gt("+monthDisplay+")").addClass("disabled");
    }
    $month_list.on("click",function() {
    	var year=$(".calendar-year span").html();
    	//置灰的月份点击事件失效
        if($(this).hasClass("disabled")){
        	return false;
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
        $(".calendar-year span").html(parseInt(year)-1);
        reloadGrid();
    })
    $("#add").click(function(){
        //置灰的年份不让增加
        if($("#add em").hasClass("disabled")){
        	return false;
        }
        var year=$(".calendar-year span").html();
        $(".calendar-year span").html(parseInt(year)+1);
        if(yearDisplay == (parseInt(year)+1)){
        	$("#add em").addClass("disabled");
        	if(monthDisplay<11){
        	$(".calendar-month span:gt("+monthDisplay+")").addClass("disabled");
        	}
        }
        reloadGrid();  
    })
})