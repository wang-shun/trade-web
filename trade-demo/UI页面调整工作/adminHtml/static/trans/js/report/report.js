
jQuery(function($) {
    $(document).ready( function() {
       $('.stickup-nav-bar').stickUp({
          parts: {
            0:'reportOne',
            1:'reportTwo',
            2:'reportThree',
            3:'reportFour',
            4:'reportFive',
            5:'reportSix',
          },
          itemClass: 'menuItem',
          itemHover: 'active',
          marginTop: 'auto'
        });

    });
});