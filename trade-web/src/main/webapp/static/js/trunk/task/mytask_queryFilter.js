require.config({
	baseUrl : ctx + '/js',
	paths : {
		queryFilterView : ctx + '/js/queryFilter/aist.queryFilter.view'
	}
});

require([ 'queryFilterView' ], function(queryFilterView) {
	queryFilterView.init("queryFilter",reloadGrid,initData);
});