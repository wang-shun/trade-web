require.config({
	paths : {
		queryFilterView : '/trade-web/js/queryFilter/aist.queryFilter.view',
		appInfo : '/trade-web/js/common/appInfo'
	}
});

require([ 'queryFilterView' ], function(queryFilterView) {
	queryFilterView.init("queryFilter",reloadGrid,initData);
});

require(['appInfo'],function(ctx){
	
});

