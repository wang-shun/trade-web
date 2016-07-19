/*
**	Anderson Ferminiano
**	contato@andersonferminiano.com -- feel free to contact me for bugs or new implementations.
**	jQuery ScrollPagination
**	28th/March/2015
**	http://andersonferminiano.com/jqueryscrollpagination/
**	You may use this script for free, but keep my credits.
**	Thank you.
*/

(function($){
	 
	 
	 $.fn.scrollPagination = function(options) {
	  	
			var opts = $.extend($.fn.scrollPagination.defaults, options);  
			var target = opts.scrollTarget;
			if (target == null){
				target = obj; 
		 	}
			opts.scrollTarget = target;
		 
			return this.each(function() {
			  $.fn.scrollPagination.init($(this), opts);
			});

	  };
	  
	  $.fn.stopScrollPagination = function(){
		  return this.each(function() {
		 	$(this).attr('scrollPagination', 'disabled');
		  });
		  
	  };
	  
	  $.fn.scrollPagination.loadContent = function(obj, opts){
		 var target = opts.scrollTarget;
		 var mayLoadContent = $(target).scrollTop()+opts.heightOffset >= $(document).height() - $(target).height();
		 
		 if (mayLoadContent&&$(obj).attr('inScroll') != 'in') {
	
			 $.fn.scrollPagination.excute(obj, opts);
		 }
		 
	  };
	  
	  $.fn.scrollPagination.init = function(obj, opts){
		 var target = opts.scrollTarget;
		 $(obj).attr('scrollPagination', 'enabled');
		 
		 opts.contentData.page = 1;
		 $(obj).empty();
		 if($(obj).attr('inScroll') != 'in'){
			 $.fn.scrollPagination.excute(obj, opts); 
		 }
		 // $.fn.scrollPagination.loadContent(obj, opts);
		
		 $(target).scroll(function(event){
			if ($(obj).attr('scrollPagination') == 'enabled'){
		 		$.fn.scrollPagination.loadContent(obj, opts);		
			}
			else {
				event.stopPropagation();	
			}
		 });
	 };
	 
	 $.fn.scrollPagination.excute = function (obj, opts){
         $(obj).attr( 'inScroll','in' );
         if (opts.beforeLoad != null){
             opts.beforeLoad();
         }
         $(obj).children().attr( 'rel', 'loaded');
         //alert('ok ');
         $.ajax({
               type: 'POST',
               url: opts.contentPage,
               data: opts.contentData,
               success: function(data){
                   if(opts.render!=null){
                        $(obj).append(opts.render(data));
                   } else{
                        $(obj).append(data);
                   }
                  
                   var objectsRendered = $(obj).children('[rel!=loaded]' );
                  
                   if (opts.afterLoad != null){
                       opts.afterLoad(objectsRendered);  
                  }
               },
               dataType: opts.dataType,
               complete : function(){
                    $(obj).attr( 'inScroll','out' );
                    console.log( 'set out');
               }
         });
	};

	$.fn.fadeInWithDelay = function(){
			var delay = 0;
			return this.each(function(){
				$(this).delay(delay).animate({opacity:1}, 200);
				delay += 100;
			});
		};
	 $.fn.scrollPagination.defaults = {
	      	 'contentPage' : null,
	     	 'contentData' : {},
			 'beforeLoad': null,
			 'afterLoad': null	,
			 'render':null,
			 'scrollTarget': null,
			 'heightOffset': 0	,
			 'dataType':'html'
	 };	
})(jQuery);