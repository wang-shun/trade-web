(function($){
	var settings;
	
	$.fn.changeSelect = function(params){
		if(this.hasClass("selected")) {
			this.removeClass("selected");
		} else {
			this.addClass("selected");
		}
	};
	
	
	
})(jQuery);