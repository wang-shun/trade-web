var times = 0;
$(function() {
    $('#fullpage').fullpage({
        sectionsColor: ['#ffffff', '#f5f5f5', '#f5f5f5', '#f5f5f5','#f5f5f5','#f5f5f5'],
        anchors: ['firstPage', 'secondPage', '3rdPage','4thPage','5thPage','lastPage'],
        menu: '#menu',
        autoScrolling: false,
        loopBottom: false,
        afterLoad: function(anchorLink, index){
        	var item = document.getElementById("iframe"+index);
        	if(times > 0){
                reRenderChart(item);
        	}
            times++;
            $("#bd1").css("overflow","hidden");
        }
    });
    $("#bd1").css("overflow","hidden");
    $(".nav_move").click(function(){
        var num=$(this).attr("attr");
        $(".nav_move").css("background","#6fdafe");
        $(this).css("background","#faab58");
        $('#fullpage').fullpage.moveTo(num, 0);
    });
});
