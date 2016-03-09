/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */

var JQGrid = function () {

    return {
        //main function to initiate the module
        init: function (id,url, ctx) {
            
            jQuery("#"+id)
		.jqGrid(
				{
					url : url,
					datatype : "json",
					colNames : [ 'houseDelid', '楼盘', '栋座', '单元', '楼层',
							'房号',  '业主', '电话', '面积', '朝向',
							'图片', '产权', '建筑类型', '房屋用途', '房屋状态'],
					colModel : [ {
						name : 'houseDelId',
						index : 'House_Del_Id',
						align : "center",
						width : 20,
						key : true,
						sortable : true,
						resizable : false,
						hidden : true
					}, {
						name : 'resblockName',
						index : 'h.RESBLOCK_NAME',
						align : "center",
						width : 30,
						resizable : false,
						title : false
					},  {
						name : 'building',
						index : 'h.building',
						align : "center",
						width : 12,
						resizable : false,
						title : false
					}, {
						name : 'unit',
						index : 'h.unit',
						align : 'center',
						width : 20
					}, {
						name : 'floor',
						index : 'floor',
						align : "center",
						width : 10,
						resizable : false,
						title : false
					}, 
					
					
					{
						name : 'houseCode',
						index : 'd.house_Code',
						align : "center",
						width : 10,
						sortable : true,
						title : false
					},{
						name : 'owner',
						index : 'h.owner',
						align : "center",
						width : 20,
						resizable : false,
						hidden : false
					},{
						name : 'phone',
						index : 'h.IS_KEY',
						align : "center",
						width : 20,
						resizable : false,
						title : false,
						formatter : getKey
					}, {
						name : 'buildSize',
						index : 'buildSize',
						align : "center",
						width : 15,
						resizable : false,
						title : false
					}, {
						name : 'orientation',
						index : 'h.Orientation',
						align : "center",
						width : 10,
						resizable : false,
						title : false
					},

					
					{
						name : 'pic',
						index : 'd.HAS_PICTURE',
						align : "center",
						width : 10,
						resizable : false,
						title : false,
						formatter : getPic
					},

					{
						name : 'propertyType',
						index : 'propertyType',
						align : "center",
						width : 20,
						resizable : false,
						title : false
					}, {
						name : 'buildType',
						index : 'buildType',
						align : "center",
						width : 20,
						resizable : false,
						title : false
					}, {
						name : 'buildUse',
						index : 'buildUse',
						align : "center",
						width : 10,
						resizable : false,
						title : false
					}, {
						name : 'houseState',
						index : 'House_Status',
						resizable : false,
						align : "center",
						width : 20,
						title : false
					}],
					rowNum : 10,
					rowList : [ 10, 20, 30 ],
					pager : '#pager5',
					sortname : 'id',
					viewrecords : true,
					sortorder : "desc",
					multiselect:true,
					//caption:"Simple data manipulation", 
					editurl : "someurl.php",
					loadComplete : function() {
						$('.jt')
    								.cluetip(
    										{
    											cluetipClass : 'jtip',
    											arrows : false,
    											activation : 'click',
    											dropShadow : true,
    											height : '150px',
    											width : '400px',
    											sticky : true,
    											positionBy : 'fixed',
    											leftOffset : '-250px',
    											topOffset : '25px',
    											closePosition : 'title',
    											closeText : '<img src="'+ctx+'/style/images/cluetip/cross.png" alt="" />'
    										});
					}
				}).navGrid("#pager5", {
			edit : false,
			add : false,
			del : false
		});

                /* 
                jQuery("#a1").click(function() {
                	var id = jQuery("#list5").jqGrid('getGridParam', 'selrow');
                	if (id) {
                		var ret = jQuery("#list5").jqGrid('getRowData', id);
                		alert("id=" + ret.id + " invdate=" + ret.invdate + "...");
                	} else {
                		alert("Please select row");
                	}
                });
                jQuery("#a2").click(function() {
                	var su = jQuery("#list5").jqGrid('delRowData', 12);
                	if (su)
                		alert("Succes. Write custom code to delete row from server");
                	else
                		alert("Allready deleted or not in list");
                });
                jQuery("#a3").click(function() {
                	var su = jQuery("#list5").jqGrid('setRowData', 11, {
                		amount : "333.00",
                		tax : "33.00",
                		total : "366.00",
                		note : "<img src='images/user1.gif'/>"
                	});
                	if (su)
                		alert("Succes. Write custom code to update row in server");
                	else
                		alert("Can not update");
                });
                jQuery("#a4").click(function() {
                	var datarow = {
                		id : "99",
                		invdate : "2007-09-01",
                		name : "test3",
                		note : "note3",
                		amount : "400.00",
                		tax : "30.00",
                		total : "430.00"
                	};
                	var su = jQuery("#list5").jqGrid('addRowData', 99, datarow);
                	if (su)
                		alert("Succes. Write custom code to add data in server");
                	else
                		alert("Can not update");
                });
                */
                
                $("#list5").setGridWidth($(".portlet-body").width());
                
                function getFocus(cellvalue, options, rowObject) {
                	var type = parseInt(cellvalue);
                	switch (type) {
                	case 1:
                		return '<img style="width:26px; height:16px; " src="'+ctx+'/style/images/focus/focus_d.png" border="0" alt=""/>';
                	case 2:
                		return '<img style="width:26px; height:16px; " src="'+ctx+'/style/images/focus/focus_bd.png" border="0" alt=""/>';
                	case 3:
                		return '<img style="width:26px; height:16px; " src="'+ctx+'/style/images/focus/focus_b.png" border="0" alt=""/>';
                	default:
                		return '';
                	}
                }
                
                function getPic(cellvalue, options, rowObject) {
                	if (cellvalue == 1) {
                		return '<img src="'+ctx+'/style/images/houseDel/icon_pic.gif" border="0" alt=""  />'; //readPic('+rowObject.houseDelId+')
                	} else {
                		return '';
                	}
                }
                
                function getTrack(cellvalue, options, rowObject) {
                	var trackTemp = '';
                	if (cellvalue > 0) {
                		trackTemp = '<img style="width:22px; height:22px; " src="'+ctx+'/style/images/houseDel/track.png" border="0" alt=""/>';
                	} else {
                		trackTemp = '<img style="width:22px; height:22px; " src="'+ctx+'/style/images/houseDel/track1.png" border="0" alt=""/>';
                	}
                	return trackTemp;
                }
                
                function getKey(cellvalue, options, rowObject) {
                	var keyTemp = '';
                	if (cellvalue == 1) {
                		keyTemp = '<img style="width:16px; height:16px; " class="jt" rel="'+ctx+'/keymanage/keyInfoAjax.action?houseDelId='
                				+ rowObject.houseDelId
                				+ '" src="'+ctx+'/style/images/houseDel/icon_ys.gif" border="0" alt=""/>';
                	}
                	return keyTemp;
                }
                
                function getTotalPrice(cellvalue, options, rowObject) {
                	var keyTemp = '';
                	var pct = rowObject.price_change_type;
                	var strImg = '';
                	if (pct == '1') { //上升
                		strImg = '<img style="width:16px; height:16px; " class="jtt" rel="'+ctx+'/houinfmg/priceInfoAjax.action?houseDelId='
                				+ rowObject.houseDelId
                				+ '" src="'+ctx+'/style/images/houseDel/up.gif" border="0" alt=""/>';
                	}
                	if (pct == '2') {//下降
                		strImg = '<img style="width:16px; height:16px; " class="st" rel="'+ctx+'/houinfmg/priceInfoAjax.action?houseDelId='
                				+ rowObject.houseDelId
                				+ '" src="'+ctx+'/style/images/houseDel/down.gif" border="0" alt=""/>';
                	}
                	keyTemp = cellvalue + strImg;
                	return keyTemp;
                }
                
                function getExclusive(cellvalue, options, rowObject) {
                	var agencyandfeeType = '';
                
                	if (cellvalue == "0") {
                		agencyandfeeType = '否';
                	} else if (cellvalue == "1") {
                		agencyandfeeType = '限时';
                	} else if (cellvalue == "2") {
                		agencyandfeeType = '独家';
                	} else {
                		agencyandfeeType = '';
                	}
                
                	return agencyandfeeType;
                }
         //end init()   
        }
    };

}();