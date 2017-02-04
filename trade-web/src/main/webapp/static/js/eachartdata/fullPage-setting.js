var times = 0;

$(function() {
    $('#fullpage').fullpage({
        sectionsColor: ['#f5f5f5', '#f5f5f5', '#f5f5f5', '#f5f5f5'],
        anchors: ['firstPage', 'secondPage', '3rdPage', '4thPage'],
        menu: '#menu',
        loopBottom: false,
        afterLoad: function(anchorLink, index){       	
        	var item = $("#iframe"+index)[0];
        	if(times > 0){
                item.contentWindow.test();
        	}

            times++;
        }
    });
});
