
$(function() {
	 //获取jQuery 对象
    var $roomlist = $("#roomList");
    var $tdlist = $("#dayList tr td");
    var $title = $(".date-title");

    //获取当天日期
    $title.text(getDateWeek());

    //遮罩弹窗层隐藏显示
    $roomlist.hide();
    $("#dateseLect").on("click",function() {
        $roomlist.show();
    })
    $(".layui-m-layershade").on("click",function() {
        $roomlist.hide();
    })

    //当前日期点击切换效果
    $tdlist.on("click", function() {
        var obj = $(this);
        var date = obj.attr("date");
        $("#dayList tr td").removeClass("curr");
        $(this).addClass("curr");
        $title.text(date);
    });

    //循环日期找到”今天“并添加当前样式
    $tdlist.each(function(){
        var tt = $(this).attr('date');
        if(tt == getDateWeek()) {
            $(this).addClass('curr');
        }
    });

    //日期函数补零
    function Appendzero (obj) {
        if (obj < 10) return "0" + obj; else return obj;
    }
    function getDateWeek () {
        var now = new Date ();
        var year = now.getFullYear ();//获取四位数年数
        var month = now.getMonth () + 1;
        var date = now.getDate ();
        var weeknum = now.getDay ();
        var hour = now.getHours ();
        var minute = now.getMinutes ();
        var second = now.getSeconds ();
        var nowdate = year + "-" + Appendzero (month) + "-" + Appendzero (date);
        return nowdate;
    };

    //点击切换效果

    /*$(".choiceoption").click(function() {
        $(this).addClass('select-mark');
    });*/

        /*if($(this).hasClass("selected-mark")) {
             $(".item").show();
        }
        else {
             $(".item").hide();
        }
        $(this).toggleClass("selected-mark");
        $(".choiceoption :lt(3)").removeClass("selected-mark");*/

   /* $(".choiceoption :lt(3)").click(function() {
        if($(this).hasClass("selected-mark")) {
            $(this).removeClass("selected-mark");
        }
        else {
            $(this).addClass("selected-mark");
        }
        $("#choices4").removeClass('selected-mark');
        $(".item").show();
    })*/

    var $choice = $(".choiceoption");
    var $choicelt = $(".choiceoption:lt(3)");
    $choice.eq(3).click(function() {
        if($(this).hasClass("select-mark")) {
             $(".item").show();
        }
        else {
             $(".item").hide();
        }
        $(this).toggleClass("select-mark");
        $choicelt.removeClass("select-mark");
    });
    $choicelt.click(function() {
        if($(this).hasClass("select-mark")) {
            $(this).removeClass("select-mark");
        }
        else {
            $(this).addClass("select-mark");
        }
        $choice.eq(3).removeClass('select-mark');
        $(".item").show();
    })
    
});


