/**
 * bespeak.js
 * jqueryUI.js.
 */
$(function() {
	//输入框自动完成提示
	var availableTags = [
		"上海浦东二区祝桥片区航亭环路399弄71号0302室",
		"上海浦东一区塘桥片区浦明路1199弄2号0902室",
		"东余杭路333号商丘路399弄2号501",
		"川沙路1666弄24号601",
		"上海浦东一区六里片区云台路800号0123室",
		"上海浦东二区惠南片区宣黄公路2585弄20号0201室"
	];
	$( "#input" ).autocomplete({
		source: availableTags
	});


	//点击切换效果
	/*$(".add-select input").click(function() {
        alert("s");
		var id = $(this).attr("id");
		$(this).toggleClass("selected");
	})*/
    $(".add-select input").click(function() {
        if($(this).hasClass("selected-mark")) {
            $(this).removeClass("selected-mark");
        }
        else {
            $(this).addClass("selected-mark");
        }

    })
});