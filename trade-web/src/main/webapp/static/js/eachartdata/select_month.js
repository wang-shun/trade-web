$(function() {
    //年份加减
    var year=new Date().getFullYear();
    $(".calendar-year span").html(year);
    $("#subtract").click(function(){
        var year=$(".calendar-year span").html();
        $(".calendar-year span").html(year-1);
        reloadGrid();
    })
    $("#add").click(function(){
        var year=$(".calendar-year span").html();
        $(".calendar-year span").html(Number(year)+1);
        reloadGrid();
    })
    //点击变换颜色&&默认当前月份
    var $month_list = $(".calendar-month span");
    $month_list.on("click",function() {
        $(this).addClass("select-blue").siblings().removeClass('select-blue');
        reloadGrid();
    });
    var monthnow = function (){
        var now   = new Date();
        var month = now.getMonth();
        return month;
    }
    var month = monthnow();
    for (var i=0; i<$month_list.length; i++) {
        if(i == month) {
            $month_list.eq(i).addClass("select-blue");
        }
        return false;
    }

})
//获取当前选择时间
function getDate(){
    var year=$(".calendar-year span").html();
    var month=$(".select-blue").html().substring(0,1);
    var formatDate=year+"-"+(month>10?month:"0"+month);
    return formatDate;
}