/**
 * 产调来源报表 shilei
 * 
 */

var ctx = $("#ctx").val();
var prDistrictId = $("#prDistrictId").val();
var prDep = $("#prDep").val();

var prSources = new Array("工商铺部", "直带区住宅部", "北区住宅部", "浦西住宅部", "浦东住宅部", "誉萃投资",
		"法律事务部");

var prCountAll = new Array(0, 0, 0, 0, 0, 0, 0);
var prCountSuccess = new Array(0, 0, 0, 0, 0, 0, 0);
var prCountUnsuccess = new Array(0, 0, 0, 0, 0, 0, 0);

var chartAll = echarts.init(document.getElementById('pieChartAll'));
var chartSuccess = echarts.init(document.getElementById('pieChartOne'));
var chartUnsuccess = echarts.init(document.getElementById('pieChartZero'));

var allPrs = 0;
var succPrs = 0;
var unsucPrs = 0;

var startDate;
var endDate;

$(document).ready(function() {
	
	var data = {}

	data.queryId = 'querySourceReport';
	data.page = 1;
	data.rows = 20;
	
	data.sPrDistrictId = prDistrictId;
    data.sPrDep = prDep;
	
	data.sTimeType = $("#timeType").val();
	
	startDate = moment().subtract(1, "months").format("YYYY-MM-DD");
	endDate = moment().add(1, "days").format("YYYY-MM-DD");
	
	data.sStartTime = startDate;
	data.sEndTime = endDate;

	//var startApplyDate = moment().format("YYYY-MM-DD");
	//var endApplyDate = moment().format("YYYY-MM-DD") + ' 23:59:59';

	$.ajax({
		async : false,
		url : ctx + "/quickGrid/findPage",
		method : "post",
		dataType : "json",
		data : data,
		success : function(data) {

			var index
			for (index in data.rows) {

				var x
				for (x in prSources) {
					if (data.rows[index].PR_APPLY_DEP_NAME == prSources[x]) {
						if (data.rows[index].IS_SUCCESS == 1) {
							prCountAll[x] += data.rows[index].PR_COUNT;
							prCountSuccess[x] = data.rows[index].PR_COUNT;
							allPrs += data.rows[index].PR_COUNT;
							succPrs += data.rows[index].PR_COUNT;
						}
						if (data.rows[index].IS_SUCCESS == 0) {
							prCountAll[x] += data.rows[index].PR_COUNT;
							prCountUnsuccess[x] = data.rows[index].PR_COUNT;
							allPrs += data.rows[index].PR_COUNT;
							unsucPrs += data.rows[index].PR_COUNT;
						}
					}
				}
			}

		}
	});

	$("#labelAll").text(allPrs);
	$("#labelS").text(succPrs);
	$("#labelUS").text(unsucPrs);

	var options = {};

	var option = setOptions(options, prCountAll);
	chartAll.setOption(option);
	option = setOptions(options, prCountSuccess);
	chartSuccess.setOption(option);
	option = setOptions(options, prCountUnsuccess);
	chartUnsuccess.setOption(option);

	setSlider();
	
	$('#searchBtn').click(function(){
		reloadReport();
	});

	//$('#reportTitle').text(endApplyDate);

})

function resetData() {
	var x;
	for(x in prSources) {
		prCountAll[x] = 0;
		prCountSuccess[x] = 0;
		prCountUnsuccess[x] = 0;
	}
	
	allPrs = 0;
	succPrs = 0;
	unsucPrs = 0;
}

function reloadReport() {
	
	resetData();
	
	var data = {}

	data.queryId = 'querySourceReport';
	data.page = 1;
	data.rows = 20;
	
	data.sPrDistrictId = prDistrictId;
    data.sPrDep = prDep;
	
	data.sTimeType = $("#timeType").val();
	
	data.sStartTime = startDate;
	data.sEndTime = endDate;
	
	$.ajax({
		async : false,
		url : ctx + "/quickGrid/findPage",
		method : "post",
		dataType : "json",
		data : data,
		success : function(data) {

			var index
			for (index in data.rows) {

				var x
				for (x in prSources) {
					if (data.rows[index].PR_APPLY_DEP_NAME == prSources[x]) {
						if (data.rows[index].IS_SUCCESS == 1) {
							prCountAll[x] += data.rows[index].PR_COUNT;
							prCountSuccess[x] = data.rows[index].PR_COUNT;
							allPrs += data.rows[index].PR_COUNT;
							succPrs += data.rows[index].PR_COUNT;
						}
						if (data.rows[index].IS_SUCCESS == 0) {
							prCountAll[x] += data.rows[index].PR_COUNT;
							prCountUnsuccess[x] = data.rows[index].PR_COUNT;
							allPrs += data.rows[index].PR_COUNT;
							unsucPrs += data.rows[index].PR_COUNT;
						}
					}
				}
			}

		}
	});

	$("#labelAll").text(allPrs);
	$("#labelS").text(succPrs);
	$("#labelUS").text(unsucPrs);

	var options = {};

	var option = setOptions(options, prCountAll);
	chartAll.setOption(option);
	option = setOptions(options, prCountSuccess);
	chartSuccess.setOption(option);
	option = setOptions(options, prCountUnsuccess);
	chartUnsuccess.setOption(option);
}

function setOptions(options, values) {

	var option = {
		// backgroundColor: '#2c343c',

		/*
		 * title: { text: 'Customized Pie', left: 'center', top: 20, textStyle: { color:
		 * '#ccc' } },
		 */

		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		
		color:['#f784a5', '#52bdbd','#ffad6b','#87d6c6'],
		
		series : [ {
			name : '产调来源',
			type : 'pie',
			radius : '40%',
			center : [ '45%', '45%' ],
			data : [ {
				value : values[0],
				name : prSources[0],
				itemStyle : {
					normal : {
						label : {
							show : function() {
								if (values[0] === 0) {
									return false;
								}
							}()
						},
						labelLine : {
							show : function() {
								if (values[0] === 0) {
									return false;
								}
							}()
						}
					}
				}
			}, {
				value : values[1],
				name : prSources[1],
				itemStyle : {
					normal : {
						label : {
							show : function() {
								if (values[1] === 0) {
									return false;
								}
							}()
						},
						labelLine : {
							show : function() {
								if (values[1] === 0) {
									return false;
								}
							}()
						}
					}
				}
			}, {
				value : values[2],
				name : prSources[2],
				itemStyle : {
					normal : {
						label : {
							show : function() {
								if (values[2] === 0) {
									return false;
								}
							}()
						},
						labelLine : {
							show : function() {
								if (values[2] === 0) {
									return false;
								}
							}()
						}
					}
				}
			}, {
				value : values[3],
				name : prSources[3],
				itemStyle : {
					normal : {
						label : {
							show : function() {
								if (values[3] === 0) {
									return false;
								}
							}()
						},
						labelLine : {
							show : function() {
								if (values[3] === 0) {
									return false;
								}
							}()
						}
					}
				}
			}, {
				value : values[4],
				name : prSources[4],
				itemStyle : {
					normal : {
						label : {
							show : function() {
								if (values[4] === 0) {
									return false;
								}
							}()
						},
						labelLine : {
							show : function() {
								if (values[4] === 0) {
									return false;
								}
							}()
						}
					}
				}
			}, {
				value : values[5],
				name : prSources[5],
				itemStyle : {
					normal : {
						label : {
							show : function() {
								if (values[5] === 0) {
									return false;
								}
							}()
						},
						labelLine : {
							show : function() {
								if (values[5] === 0) {
									return false;
								}
							}()
						}
					}
				}
			}, {
				value : values[6],
				name : prSources[6],
				itemStyle : {
					normal : {
						label : {
							show : function() {
								if (values[6] === 0) {
									return false;
								}
							}()
						},
						labelLine : {
							show : function() {
								if (values[6] === 0) {
									return false;
								}
							}()
						}
					}
				}
			} ].sort(function(a, b) {
				return a.value - b.value
			}),
			//roseType : 'angle',
			label : {
				normal : {
					textStyle : {
						color : 'rgba(0, 0, 0, 0.8)'
					}
				}
			},
			labelLine : {
				normal : {
					lineStyle : {
						color : 'rgba(0, 0, 0, 0.8)'
					},
					smooth : 0.2,
					length : 10,
					length2 : 20
				}
			},
			/*itemStyle : {
				normal : {
					shadowBlur : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				}
			},*/
		} ]
	};

	return option
}

function setSlider() {
	$("#datarange").ionRangeSlider(
			{
				type : "double",
				min : +moment().subtract(1, "years").format("X"),
				max : +moment().format("X"),
				from : +moment().subtract(1, "months").format("X"),
				prettify : function(num) {
					return moment(num, "X").format("YYYY-MM-DD");
				},
				onChange : function(data) {
					//alert(data.from_value);
					startDate = moment(data.from, "X").format("YYYY-MM-DD");
					endDate = moment(data.to, "X").add(1, "days").format("YYYY-MM-DD");
					//$("#reportTitle").text(startDate + ' - ' + endDate);
					//$('#f4').val(data.from_value);
				}
			//onChange: $("#reportTitle").text(moment().format("X")),
			//onFinish: $("#reportTitle").text(moment().format("X"))
			});
}