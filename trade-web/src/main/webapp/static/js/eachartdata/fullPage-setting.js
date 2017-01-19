$(document).ready(function() {
    $('#fullpage').fullpage({
        sectionsColor: ['#f5f5f5', '#f5f5f5', '#f5f5f5', '#f5f5f5', '#f5f5f5', '#f5f5f5', '#f5f5f5', '#f5f5f5', '#f5f5f5'],
        anchors: ['firstPage', 'secondPage', '3rdPage', '4thPage', '5thPage', '6thPage', '7thPage', '8thPage', 'lastPage'],
        menu: '#menu',
        loopBottom: false,
        afterLoad: function(anchorLink, index){

            //section 2
            if(index == 2){
                //animate
                $('#section1').find('p').first().fadeIn(500, function(){
                    $('#section1').find('p').last().fadeIn(1500);
                });;
            }

            //section 3
            if(anchorLink == '3rdPage'){
                //moving the image
                $('#section2').find('.intro').delay(500).animate({
                    left: '0%'
                }, 1500, 'easeOutExpo');
            }
        }
    });

});