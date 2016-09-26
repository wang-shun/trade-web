/**
 * bespeak.js
 * jqueryUI.js.
 */





$(function() {
	//输入框自动完成提示
	/*var availableTagsJSON = [
		"上海浦东二区祝桥片区航亭环路399弄71号0302室",
		"上海浦东一区塘桥片区浦明路1199弄2号0902室",
		"东余杭路333号商丘路399弄2号501",
		"川沙路1666弄24号601",
		"上海浦东一区六里片区云台路800号0123室",
		"上海浦东二区惠南片区宣黄公路2585弄20号0201室"
	];
	$( "#input" ).autocomplete({
		source: availableTagsJSON
	});*/


	/*$("#input").autocomplete({
	 source: function(request, response) {
	 $.ajax({
	 url: "http://ws.geonames.org/searchJSON",
	 dataType: "jsonp",
	 data: {
	 featureClass: "P",
	 style: "full",
	 maxRows: 12,
	 name_startsWith: request.term
	 },
	 success: function(data) {
			 response($.map(data.geonames, function(item) {
			 return {
			 label: item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName,
			 value: item.name
			}
	 	}));
	 }
	 });
	 },
	 minLength: 2
	 });*/

	//点击切换效果

    $(".add-select input").click(function() {
        if($(this).hasClass("selected-mark")) {
            $(this).removeClass("selected-mark");
        }
        else {
            $(this).addClass("selected-mark");
        }

    })
});