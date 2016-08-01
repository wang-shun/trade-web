$(document).ready(function(){

    /*click link_btn show case*/
    $("#link_btn").click(function() {
      $(".case_content").toggle();
    });

});




    jQuery(function($) {
    $(document).ready( function() {
       $('.stickup-nav-bar').stickUp({
        // $('.col-lg-9').stickUp({
              parts: {
                0:'base_info',
                1:'zj_info',
                2:'loan_info',
              },
              itemClass: 'menuItem',
              itemHover: 'active',
              marginTop: 'auto'
            });





    });
});