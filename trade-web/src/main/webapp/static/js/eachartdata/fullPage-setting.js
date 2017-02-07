var times = 0;

$(function() {
    $('#fullpage').fullpage({
        sectionsColor: ['#f5f5f5', '#f5f5f5', '#f5f5f5', '#f5f5f5'],
        anchors: ['firstPage', 'secondPage', '3rdPage', '4thPage'],
        menu: '#menu',
        loopBottom: false,
        afterLoad: function(anchorLink, index){       	
        	var item = document.getElementById("iframe"+index);
        	if(times > 0){
                reRenderChart(item);
        	}
            times++;
        }
    });
});
