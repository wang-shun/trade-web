$(document).ready(function () {
	//日历控件
    $('.input-daterange').datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });

    //获取时间，月日补零
    function Appendzero (obj) {
        if (obj < 10) return "0" + obj; else return obj;
    }
    function getDateWeek (x) {
        var now = new Date();
        var year = now.getFullYear ();//获取四位数年数
        var month = now.getMonth () + 1;
        var date = now.getDate () + x;
        var s = year + "-" + Appendzero (month) + "-" + Appendzero (date) ;
        return s;
    }
       $(".datatime").val(getDateWeek(0));


	//产品需求点击选择效果
	$(".goodstyle span").click(function() {
        if($(this).hasClass("selected-mark")) {
            $(this).removeClass("selected-mark");
        }
        else {
            $(this).addClass("selected-mark");
        }

    });

	//点击是否隐藏出现内容
	$(".warncon, .sourcebox").hide();
    $("#Radio1").on("click",function() {
        $(".warncon").show();
    });
    $("#Radio2").on("click",function() {
        $(".warncon").hide();
    });

    $("#UserMark").on("click",function() {
        $(".sourcebox").hide();
         $(".marinfo,.alerted").show();
    });
    $("#NouserMark").on("click",function() {
        $(".sourcebox").show();
         $(".marinfo,.alerted").hide();
    });

    $(".chackTextarea").autoTextarea({
        maxHeight:220,
    })


 });