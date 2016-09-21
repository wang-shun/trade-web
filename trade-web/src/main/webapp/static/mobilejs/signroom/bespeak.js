
$(function() {
	var ctx = $("#ctx").val();
	
   //文本框自动填充
   $("#input").autocomplete({
	 source: function(request, response) {
		 $.ajax({
			 url: ctx + "mobile/reservation/getPropertyAddressList",
			 dataType: "jsonp",
			 data: {
			 inputValue: request.term
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
	 });

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