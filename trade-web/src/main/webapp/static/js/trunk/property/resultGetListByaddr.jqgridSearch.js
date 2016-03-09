/**
 * 查询初始化js
 */

var JQGrid_resultGetByaddSearch = function() {
	
	
	/*
	 * table_property_list   列表tabel 的id
	 * searchButton  查询按钮
	 */
	var init=function(table_property_list,addrSearchButton,propertyAddr) {
			$('#addrSearchButton').click(
				function(){
					 var propertyAddr =  $("#addr").val();
					 var prAppliant  = $("#userId").val();
					 var prStatus = $("#prStatus").val();
					var data={
						'search_prAppliant' : prAppliant,
						'search_prStatus' : prStatus,
						'search_propertyAddr' : propertyAddr
					};
					
	        		$('#'+table_property_list).jqGrid('setGridParam',{
	        			datatype:'json',  
	        			mtype : 'POST',
	        			postData: data
	        		}).trigger('reloadGrid');
				}
			);
	};
	return {
		'init' : init
	};

}();