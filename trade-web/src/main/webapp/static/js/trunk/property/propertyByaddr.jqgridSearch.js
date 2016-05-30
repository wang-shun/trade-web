/**
 * 查询初始化js
 */
var packData=function(){
		 var propertyAddr =  $("#addr").val();
		 var prDistrictId  = $("#prDistrictId").val();
		 var prStatus = $("#prStatus").val();
		 
		 var distCode = $("#distCode").val();
		 var prCat = $("#prCat").val();
		 var grpOrgName = $("#grpOrgName").val();
		 var quds = $("#quds").val();
		 var auUser = $("#auUser").val();
		 var acuUser = $("#acuUser").val();
		 var isSuccess = $("#isSuccess").val();
		 var prChannel = $("#prChannel").val();
		 var completeTimeStart = $("#completeTimeStart").val();
		 var completeTimeEnd = $("#completeTimeEnd").val();
		 
		var data={
				'search_prDistrictId' : prDistrictId,
				'search_prStatus' : prStatus,
				
				'search_distCode' : distCode,
				'search_prCat' : prCat,
				'search_grpOrgName' : grpOrgName,
				'search_quds' : quds,
				'search_auUser' : auUser,
				'search_acuUser' : acuUser,
				'search_isSuccess' : isSuccess,
				'search_prChannel' : prChannel,
				'search_completeTimeStart' : completeTimeStart,
				'search_completeTimeEnd' : completeTimeEnd?(completeTimeEnd+' 23:59:59'):completeTimeEnd,
				
				'search_propertyAddr' : propertyAddr,
				'page':1
			};
		return data;
	}

var JQGrid_propertyByaddSearch = function() {
	
	
	/*
	 * table_property_list   列表tabel 的id
	 * searchButton  查询按钮
	 */
	var init=function(table_property_list,addrSearchButton,propertyAddr) {
			$('#addrSearchButton').click(
				function(){
/*					 var propertyAddr =  $("#addr").val();
					 var prDistrictId  = $("#prDistrictId").val();
					 var prStatus = $("#prStatus").val();
					var data={
						'search_prDistrictId' : prDistrictId,
						'search_prStatus' : prStatus,
						'search_propertyAddr' : propertyAddr
					};
					*/
	        		$('#'+table_property_list).jqGrid('setGridParam',{
	        			datatype:'json',  
	        			mtype : 'POST',
	        			postData: packData()
	        		}).trigger('reloadGrid');
				}
			);
	};
	
	return {
		'init' : init
	};

}();