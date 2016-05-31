
/**
 * 知识库操作
 * @author yumin1
 */

var ctx = $("#ctx").val();
$(document).ready(function() {
	$("#knowledgeSearchButton").click(function(){
		knowledgeList.init('/quickGrid/findPage',ctx); // 列表风格	
	});
	knowledgeList.init('/quickGrid/findPage',ctx); // 列表风格
});
var knowledgeList = function () {	
	
	 return {
	        init: function (url, _ctx,page) {
	        	ctx=_ctx;
	        	initTemplate();
	        	$.ajax({    
	        	    type: "POST",
	           	    url : ctx+url, 
		            dataType:"json",    
		            global:false,
		            data : packData(page),
		            async: true,
		            success: function(data){
		            	var loginUserId = $("#userId").val(); //当前登录用户
		            	createDivList(data,_ctx,loginUserId,_ctx);	
		            },
		            error: function (data) {
	                    alert("data:" + data.responseText);
	                    $.unblockUI();
	                }
		       });
	      } 
	 };
	 
	 //模板
	 function initTemplate(){

	 }
	 
	 //创建列表
	 function createDivList(data,ctx,userid,_ctx){
		 // 清空列表
        var obj=$('#knowledgeListDiv').find('.house-cont'); //定义页面元素对象
        data.ctx=_ctx;
        var html= template('knowledgeListStyle1', data); 
        obj.empty();
        obj.html(html);
        // 显示分页
        initpage(data.total,data.pagesize,data.page);
        $.unblockUI();
	 }
	 
	 
}();


//分页
function initpage(totalCount,pageSize,currentPage)
{
		var currentTotalstrong=$('#currentTotalPage').find('strong');
//		if(totalCount<1 || pageSize<1 || currentPage<1){
//			$(currentTotalstrong).empty();
//			$("#pageBar").empty();
//			$("#pagerfront").empty();
//			return;
//		}
		cpage=currentPage<=0?1:currentPage;
		$(currentTotalstrong).empty();
		$(currentTotalstrong).text(cpage+'/'+totalCount);
		
		$("#pageBar").twbsPagination({
			totalPages:totalCount,
			visiblePages:9,
			startPage:cpage,
			first:'<i class="icon-step-backward"></i>',
			prev:'<i class="icon-chevron-left"></i>',
			next:'<i class="icon-chevron-right"></i>',
			last:'<i class="icon-step-forward"></i>',
			 onPageClick: function (event, page) {
				 knowledgeList.init('/quickGrid/findPage',ctx,page); // 列表风格
		        }
		});
}
/**
 * packData
 */
function packData(page){
	var data = {};	
	data.search_knowledgeTitle =$.trim( $('#txt_input').val() );  //知识标题
	data.queryId="knowledgeListQuery1";
	data.sortname='PB_TIME';
    data.sortorder='desc';
    data.rows=10;
    data.page=page?page:1;
	return data;
}
function doLike(id){
	
	var e=event.target;
	if($(e).is('i')){
		e=$(e).parent();
	}
	var data={};
	data.pkid=id;
	data.like=$(e).attr('data');
	
	$.ajax({    
	        	  type: "POST",
	           	    url : ctx+"/knowledge/doLike", 
		            dataType:"json",    
		            global:false,
		            data : data,
		            async: true,
		            success: function(data){
		            	
	}});
	var likeSum=$(e).attr('likeSum');
	if(likeSum==''){likeSum=0;}else{likeSum=~~likeSum;}
	$(e).attr('data',~~!~~data.like);
	if(!!~~data.like){
		likeSum+=1;
	}else{
		likeSum-=1;
	}
	$(e).attr('likeSum',likeSum);
	if(likeSum<=0){
		likeSum='';
	}else{
		likeSum='('+likeSum+')';
	}
	$(e).html((~~!~~data.like)==1?('<i class="fa fa-thumbs-up"></i>赞'+likeSum):('<i class="fa fa-thumbs-up"></i>取消赞'+likeSum));
	
}


