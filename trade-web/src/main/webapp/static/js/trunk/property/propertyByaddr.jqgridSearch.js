/**
 * 查询初始化js
 */

var JQGrid_propertyByaddSearch = function() {
	
	
	/*
	 * table_property_list   列表tabel 的id
	 * searchButton  查询按钮
	 */
	var init=function(table_property_list,addrSearchButton,propertyAddr) {
			$('#addrSearchButton').click(
				function(){
					 var propertyAddr =  $("#addr").val();
					 var prDistrictId  = $("#prDistrictId").val();
					 var prStatus = $("#prStatus").val();
					var data={
						'search_prDistrictId' : prDistrictId,
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