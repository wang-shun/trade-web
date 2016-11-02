/**
 * 产调来源清单 shilei
 * 
 */

var ctx = $("#ctx").val();
var prDistrictId = $("#prDistrictId").val();
var prDep = $("#prDep").val();

var chartAll = echarts.init(document.getElementById('pieChartAll'));
var chartSuccess = echarts.init(document.getElementById('pieChartOne'));
var chartUnsuccess = echarts.init(document.getElementById('pieChartZero'));

var allPrs = 0;
var succPrs = 0;
var unsucPrs = 0;

var mAllPrsTitle = "";
var mSuccPrsTitle = "";
var mUnsucPrsTitle = "";

var allOrgLegends = new Array();
var allPrsItems = new Array();

var succOrgLegends = new Array();
var succPrsItems = new Array();

var unsucOrgLegends = new Array();
var unsucPrsItems = new Array();

$(document)
		.ready(
				function() {

					var data = {};
					data.sPrDistrictId = prDistrictId;
					data.sPrDep = prDep;

					$("#sourceList").aistGrid({
						ctx : ctx,
						queryId : 'querySourceList',
						templeteId : 'template_sourceList',
						data : data,
						wrapperData : data,
						columns : [ {
							colName : "产调编号"
						}, {
							colName : "产调地址"
						}, {
							colName : "产调来源"
						}, {
							colName : "成本归属"
						}, {
							colName : "申请人"
						}, {
							colName : "时间"
						} ]

					});

					$('#searchBtn').click(function() {
						reloadGrid();
					});

					$('#exportBtn').click(
							function() {

								var queryId = "querySourceList";

								var data = getParams();

								data.sPrDistrictId = prDistrictId;
								data.sPrDep = prDep;

								$.exportExcel({
									ctx : ctx,
									queryId : queryId,
									colomns : [ 'PR_CODE', 'IS_SUCCESS',
											'UNSUCCESS_REASON', 'DIST_CODE',
											'PR_ADDRESS', 'APP_RNAME',
											'PR_APPLY_ORG_NAME',
											'PR_APPLY_DEP_NAME', 'ORG_NAME',
											'EXE_RNAME', 'PR_COST_ORG_MGR',
											'PR_COST_ORG_NAME', 'PR_STATUS',
											'PR_APPLY_TIME', 'PR_ACCPET_TIME',
											'PR_COMPLETE_TIME' ],
									data : data
								});

							});

					// $('#sourceList table').addClass("apply-table");
					$('#sourceList table')
							.addClass(
									"table table_blue table-striped table-bordered table-hover");

					reloadGrid();

					$(".charone").hide();
					$("#sourceReportBtn").click(function() {
						$(".charone").toggle();
						$("#sourceReportBtn").addClass('btn-bg');
						if ($(".charone").is(":hidden")) {
							$("#sourceReportBtn").removeClass('btn-bg');
						}
					});
				});

function setPieCharts() {
	var option = {};
	resetData();
	getData();
	
	mAllPrsTitle = "所有产调: " + allPrs;	
	option = setOptions(mAllPrsTitle, allOrgLegends, allPrsItems);
	chartAll.setOption(option);
	
	mSuccPrsTitle = "有效产调: " + succPrs;
	option = setOptions(mSuccPrsTitle, succOrgLegends, succPrsItems);
	chartSuccess.setOption(option);
	
	mUnsucPrsTitle = "无效产调: " + unsucPrs;
	option = setOptions(mUnsucPrsTitle, unsucOrgLegends, unsucPrsItems);
	chartUnsuccess.setOption(option);

	/*
	 * $("#labelAll").text(allPrs); $("#labelS").text(succPrs);
	 * $("#labelUS").text(unsucPrs);
	 */
}

function resetData() {
	allPrs = 0;
	succPrs = 0;
	unsucPrs = 0;

	allOrgLegends.splice(0, allOrgLegends.length);
	allPrsItems.splice(0, allPrsItems.length);

	succOrgLegends.splice(0, succOrgLegends.length);
	succPrsItems.splice(0, succPrsItems.length);

	unsucOrgLegends.splice(0, unsucOrgLegends.length);
	unsucPrsItems.splice(0, unsucPrsItems.length);
}

function setQueryData() {
	var data = getParams();
	data.sPrDistrictId = prDistrictId;
	data.sPrDep = prDep;
	return data;
}

function getData() {

	var data = setQueryData();
	data.queryId = 'querySourceReport';
	data.page = 1;
	data.rows = 50;

	$.ajax({
		async : false,
		url : ctx + "/quickGrid/findPage",
		method : "post",
		dataType : "json",
		data : data,
		success : function(data) {

			var index
			for (index in data.rows) {

				var name = data.rows[index].PR_APPLY_DEP_NAME;
				var count = data.rows[index].PR_COUNT;

				var prItem = {
					name : name,
					value : count,
					itemStyle : {
						normal : {
							label : {
								formatter : '{b}\n{c}',
								show : function() {
									if (count === 0) {
										return false;
									}
								}()
							},
							labelLine : {
								show : function() {
									if (count === 0) {
										return false;
									}
								}()
							}
						}
					}
				};

				var idx = allOrgLegends.indexOf(name);
				if (idx != -1) {
					allPrsItems[idx].value += count;
				} else {
					allOrgLegends.push(name);
					allPrsItems.push(prItem);
				}

				if (data.rows[index].IS_SUCCESS == 1) {
					prItem = {
						name : name,
						value : count,
						itemStyle : {
							normal : {
								label : {
									formatter : '{b}\n{c}',
									show : function() {
										if (count === 0) {
											return false;
										}
									}()
								},
								labelLine : {
									show : function() {
										if (count === 0) {
											return false;
										}
									}()
								}
							}
						}
					};

					idx = succOrgLegends.indexOf(name);
					if (idx != -1) {
						succPrsItems[idx].value += count
					} else {
						succOrgLegends.push(name);
						succPrsItems.push(prItem);
					}
					
					succPrs += count;
				}

				if (data.rows[index].IS_SUCCESS == 0) {
					prItem = {
						name : name,
						value : count,
						itemStyle : {
							normal : {
								label : {
									formatter : '{b}\n{c}',
									show : function() {
										if (count === 0) {
											return false;
										}
									}()
								},
								labelLine : {
									show : function() {
										if (count === 0) {
											return false;
										}
									}()
								}
							}
						}
					};

					idx = unsucOrgLegends.indexOf(name);
					if (idx != -1) {
						UnsucPrsItems[idx].value += count
					} else {
						unsucOrgLegends.push(name);
						unsucPrsItems.push(prItem);
					}
					
				    unsucPrs += count;
				}

				allPrs += count;
			}

		}
	});
}

function setOptions(title, legendName, dataValue) {
	var option = {
		title : {
			text : title,
			subtext : '',
			padding : [ 5, 10 ],
			x : 'center',
		},
		color : [ '#52bdbd', '#ffad6b', '#f784a5', '#295aa5', '#34b971',
				'#809eff', '#a8e3f0', '#00a3e0' ],
		tooltip : {
			trigger : 'item',
			triggerOn : 'mousemove',
			/* alwaysShowContent:true, */
			hideDelay : 1500,
			enterable : true,
			formatter : "{b}: {c} ({d}%)"
		},

		legend : {
			orient : 'horizontal',
			y : 'bottom',
			data : legendName
		},
		series : [ {
			name : title,
			type : 'pie',
			radius : [ '30%', '50%' ],
			animation : true,
			selectedMode : 'multiple',
			data : dataValue
		} ]
	};
	return option;
}

function reloadGrid() {

	var data = getParams();

	data.sPrDistrictId = prDistrictId;
	data.sPrDep = prDep;

	$("#sourceList").reloadGrid({
		ctx : ctx,
		queryId : 'querySourceList',
		templeteId : 'template_sourceList',
		data : data,
		wrapperData : data
	});

	setPieCharts();

	setStyle();
}

function getParams() {

	var prApp = $("#prApp").val();
	var prCostMgr = $("#prCostMgr").val();
	var prStatus = $("#prStatus").val();
	var prDistName = $("#prDistName").val();
	var prAppDep = $("#prAppDep").val();
	var timeType = $("#timeType").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var prIsSuccess = $('input[name="successGroup"]:checked').val();

	var data = {};

	data.sPrApp = prApp;
	data.sPrCostMgr = prCostMgr;
	data.sPrStatus = prStatus;
	data.sPrDistName = prDistName;
	data.sPrAppDep = prAppDep;
	data.sTimeType = timeType;
	data.sStartTime = startTime;
	data.sEndTime = endTime ? (endTime + ' 23:59:59') : endTime;
	data.sPrIsSuccess = prIsSuccess;

	return data;
}

function chooseApplicant(id) {
	userSelect({
		startOrgId : id,
		expandNodeId : id,
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		jobCode : 'consultant',
		callBack : applicantSelectUserBack
	});
}

function applicantSelectUserBack(array) {
	if (array && array.length > 0) {
		$("#prApp").val(array[0].username);
		$("#prApp").attr('hVal', array[0].userId);

	} else {
		$("#prApp").val("");
		$("#prApp").attr('hVal', "");
	}
}

function chooseDist(id) {

	orgSelect({
		displayId : 'oriGrpId',
		displayName : 'radioOrgName',
		startOrgId : id,
		expandNodeId : id,
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		callBack : distSelectOrgBack
	});
}

function distSelectOrgBack(array) {
	if (array && array.length > 0) {
		$("#prDistName").val(array[0].name);
		$("#prDistName").attr('hVal', array[0].id);
	} else {
		$("#prDistName").val("");
		$("#prDistName").attr('hVal', "");
	}
}

function setStyle() {
	// left
	$('.demo-left').poshytip({
		className : 'tip-twitter',
		showTimeout : 1,
		alignTo : 'target',
		alignX : 'left',
		alignY : 'center',
		offsetX : 8,
		offsetY : 5,
	});

	// right
	$('.demo-right').poshytip({
		className : 'tip-twitter',
		showTimeout : 1,
		alignTo : 'target',
		alignX : 'right',
		alignY : 'center',
		offsetX : 8,
		offsetY : 5,
	});

	// top
	$('.demo-top').poshytip({
		className : 'tip-twitter',
		showTimeout : 1,
		alignTo : 'target',
		alignX : 'center',
		alignY : 'top',
		offsetX : 8,
		offsetY : 5,
	});

	// bottom
	$('.demo-bottom').poshytip({
		className : 'tip-twitter',
		showTimeout : 1,
		alignTo : 'target',
		alignX : 'center',
		alignY : 'bottom',
		offsetX : 8,
		offsetY : 5,
	});
}