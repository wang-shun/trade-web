define("appInfo",[],function() {
	var appCtx={};
    var appCtxList = "";
    var ctx = "";
    var domain = "";
    var appCtx = "";
	$.ajax({
 		  type: "get",
 		  url: "/trade-web/app/getAppInfo.json",
 		  dataType: "json",
 		  async: false,
 		  success: function(data){
 			  ctx = data.ctx;
 			  domain = data._domain;
 			  appCtxList = data._appCtxList;
 			  appCtx = data._appCtx;
 		  }
     });
	
    $.each(appCtxList,function(idx,app){
    	appCtx['${app.appName}'] = '${app.genAbsoluteUrl()}';
    });
    
	return {
		ctx : ctx,
		appCtx : appCtx,
		appCtxList:appCtxList,
		crossDomain:function (){
			document.domain = domain;
		}
	};
});

var ctx;
var appCtx;
var domain;
var appCtxList;

function init(){
	require(['appInfo'],function(appInfo){
		ctx = appInfo.ctx;
		appCtx = appInfo.appCtx;
		appCtxList = appInfo.appCtxList;
		domain = appInfo.domain;
	});
}

init();

