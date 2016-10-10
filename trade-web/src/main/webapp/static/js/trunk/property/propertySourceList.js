/**
 * 产调来源清单
 * shilei
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

$(document).ready(function() {
	
	var data = {};
    data.sPrDistrictId = prDistrictId;
    data.sPrDep = prDep;

    $("#sourceList").aistGrid({
		ctx : ctx,
		queryId : 'querySourceList',
	    templeteId : 'template_sourceList',
	    data : data,
	    wrapperData : data,
	    columns : [{
			           colName :"产调编号"
			      },{
	    	           colName :"产调地址"
	    	      },{
	    	           colName :"产调来源"
	    	      },{
	    	           colName :"成本归属"
    	          },{
	    	           colName :"申请人"
    	          },{
		    	       colName :"时间"
	    	      }]
	
	});
    
    $('#searchBtn').click(function(){
		reloadGrid();
	});
    
    $('#exportBtn').click(function(){
    	
    	var queryId = "querySourceList";
    	
    	var data = getParams();
    	
    	data.sPrDistrictId = prDistrictId;
        data.sPrDep = prDep;
    	
    	$.exportExcel({
    		ctx : ctx,
    		queryId : queryId,
    		colomns : ['PR_CODE','IS_SUCCESS','UNSUCCESS_REASON','DIST_CODE',
    				   'PR_ADDRESS','APP_RNAME','PR_APPLY_ORG_NAME','PR_APPLY_DEP_NAME','ORG_NAME',
    				   'EXE_RNAME','PR_COST_ORG_MGR','PR_COST_ORG_NAME','PR_STATUS','PR_APPLY_TIME',
    				   'PR_ACCPET_TIME','PR_COMPLETE_TIME'],
    		data:data
    		});
    	
    });
	
	//$('#sourceList table').addClass("apply-table");
	$('#sourceList table').addClass("table table_blue table-striped table-bordered table-hover");
	
	reloadGrid();
	
	$(".charone").hide();
    $("#sourceReportBtn").click(function() {
        $(".charone").toggle();
        $("#sourceReportBtn").addClass('btn-bg');
        if($(".charone").is(":hidden")) {
             $("#sourceReportBtn").removeClass('btn-bg');
        }
    });
});

function setPieCharts() {
	var option = {};
	resetData();
	getData();	
	
	option = setOptions(prCountAll);
	chartAll.setOption(option);
	option = setOptions(prCountSuccess);
	chartSuccess.setOption(option);
	option = setOptions(prCountUnsuccess);
	chartUnsuccess.setOption(option);
	
	$("#labelAll").text(allPrs);
	$("#labelS").text(succPrs);
	$("#labelUS").text(unsucPrs);
}

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
}

function reloadGrid(){
	
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
	var timeType =  $("#timeType").val();
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
	data.sEndTime = endTime?(endTime+' 23:59:59'):endTime;
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

function setOptions(values) {

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
		
		color: ['#ffae6b','#275da5','#4dbcbe', '#0a8dc9', '#f989a5','#13bfa1'],
		
		series : [ {
			name : '产调来源',
			type : 'pie',
			//radius : '40%',
			//center : [ '45%', '45%' ],
			radius: ['30%', '55%'],
            avoidLabelOverlap: true,
            label: {
                normal: {
                    show: true,
                    position: 'outside',
                    formatter : '{b}\n{c}',
                    textStyle: {
                        fontSize: '12',
                    }}
                },
                labelLine: {
                normal: {
                    show: true
                }
            },
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
			} ],
			itemStyle: {
                emphasis: {
                    show: true,
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
            
            /*.sort(function(a, b) {
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
			},*/
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
		displayId: 'oriGrpId',
		displayName: 'radioOrgName', 
		startOrgId: id, 
		expandNodeId: id,
		orgType:'', 
		departmentType:'', 
		departmentHeriarchy:'',
		chkStyle:'radio',
		callBack: distSelectOrgBack
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

function setStyle(){
	//left
	$('.demo-left').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'left',
		alignY: 'center',
		offsetX: 8,
		offsetY: 5,
	});

	//right
	$('.demo-right').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'right',
		alignY: 'center',
		offsetX: 8,
		offsetY: 5,
	});

	//top
	$('.demo-top').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'center',
		alignY: 'top',
		offsetX: 8,
		offsetY: 5,
	});

	//bottom
	$('.demo-bottom').poshytip({
		className: 'tip-twitter',
		showTimeout: 1,
		alignTo: 'target',
		alignX: 'center',
		alignY: 'bottom',
		offsetX: 8,
		offsetY: 5,
	});	
}