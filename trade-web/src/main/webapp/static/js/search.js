/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */

var Search = function () {

    return {
        //main function to initiate the module
        init: function () {
            if (jQuery().datepicker) {
                $('.date-picker').datepicker();
            }
            
            $('#advSearchSwith').click(function(){
        	$('.adv-search').toggle();
        	  if($('#advSearchIcon').hasClass('icon-chevron-down')){
        	      $('#advSearchIcon').removeClass();
        	      $('#advSearchIcon').addClass('icon-chevron-up');
        	  }else{
        	      $('#advSearchIcon').removeClass();
        	      $('#advSearchIcon').addClass('icon-chevron-down');
        	  }
            });
        }
    };

}();