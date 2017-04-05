//获取分页条 
function getPageBar(curPage,totalPage){
    //页码大于最大页数 
    if(curPage>totalPage) curPage=totalPage; 
    //页码小于1 
    if(curPage<1) curPage=1; 
    
    var pageStr = "<li><a onclick=\"go("+(curPage-1)+","+totalPage+");return false\" >上一页</a></li>";
    //总页码小于5,则显示全部页码
    if(totalPage<=5){
	   for(var i=0;i<totalPage;i++){
	    	var j = i+1;
	     	if(j==curPage){
	     		pageStr += "<li class=\"active\"><a onclick=\"go("+ j +","+totalPage+");return false\" >"+j+"</a></li>";
	     	} else {
	     		pageStr += "<li><a onclick=\"go("+ j+","+totalPage+");return false\" >"+j+"</a></li>";
	     	}
       }
    }else{
        if(curPage==1){
        	pageStr += "<li class=\"active\"><a onclick=\"go(1,"+totalPage+");return false\" >1</a></li>";
        }else if(curPage==2){
        	pageStr += "<li><a onclick=\"go(1);return false\" >1</a></li>";
            pageStr += "<li class=\"active\"><a onclick=\"go(2,"+totalPage+");return false\" >2</a></li>";
        }else if(curPage>=3){
        	pageStr += "<li><a onclick=\"go(1,"+totalPage+");return false\" >1</a></li>";
    		pageStr += "<li><a href=\"#\">...</a></li>";
    		pageStr += "<li class=\"active\"><a onclick=\"go("+ curPage +","+totalPage+");return false\" >"+curPage+"</a></li>";
        }
        
        if(curPage<=totalPage-2){
        	pageStr += "<li><a href=\"#\">...</a></li>";
    		pageStr += "<li><a onclick=\"go("+ totalPage +","+totalPage+");return false\" >"+totalPage+"</a></li>";
    	}else if(curPage==totalPage-1){
    		pageStr += "<li><a onclick=\"go("+ totalPage +","+totalPage+");return false\" >"+totalPage+"</a></li>";
    	}else if(curPage==totalPage){
    		
    	}
    }
    pageStr += "<li><a onclick=\"go("+(curPage+1)+","+totalPage+");return false\" >下一页</a></li>";
    
    $("#pageBar").html(pageStr); 
} 