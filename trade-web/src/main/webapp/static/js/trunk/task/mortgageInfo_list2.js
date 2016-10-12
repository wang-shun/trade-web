var ctx = $("#ctx").val();
var serviceDepId = $("#serviceDepId").val();

var pChartMTypeCases = echarts.init(document.getElementById('pieChartMTypeCases'));
var pChartMTypeAmount = echarts.init(document.getElementById('pieChartMTypeAmount'));
var mTypeLegends = new Array("商贷收单", "公积金", "商贷流失");
var mTypeCases = new Array(0, 0, 0);
var mTypeComAmount = new Array(0, 0, 0);
var mTypePRFAmount = new Array(0, 0, 0);
var mAllCases = 0;
var mTypeAmount = new Array(0, 0, 0);
var mTypeCasesTitle = "";
var mTypeAmountTitle = "";

/**
 * 案件统计详情
 */
$(document).ready(function() {

	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	url = ctx + url;
	
	var signTimeStart = $("#dtBegin_0").val();
	var signTimeEnd = '';
	if (!$.isBlank($("#dtEnd_0").val())) {
		signTimeEnd = $("#dtEnd_0").val() + " 23:59:59";
	}
	// 初始化列表
	var data = {};
	data.queryId = "findToMortgageIsActive";
	data.rows = 10;
	data.page = 1;
	data.signTimeStart = signTimeStart;
	data.signTimeEnd = signTimeEnd;
	/* 加载排序查询组件 */
	aist.sortWrapper({
		reloadGrid : loanLostApproveSearchMethod
	});

	reloadGrid(data);
	
	getParentBank($("select[name='loanLostFinOrgName']"),$("select[name='loanLostFinOrgNameYc']"),"","","");
	
	
	$("select[name='loanLostFinOrgName']").change(function(){
		/*$("#mortgageForm").find("select[name='finOrgCode']").chosen("destroy");*/
		//if(data.content && data.content.isTmpBank=='1'){
			getBranchBankList($("select[name='loanLostFinOrgNameYc']"),$("select[name='loanLostFinOrgName']").val(),"");
		//}else{
			//getBranchBankList($("select[name='loanLostFinOrgNameYc']"),$("select[name='loanLostFinOrgName']").val(),"",'cl');
		//}
	})
	
	setPieCharts();
	
	$(".charone").hide();
    $("#mortTypeAnalysis").click(function() {
        $(".charone").toggle();
        $("#mortTypeAnalysis").addClass('btn-bg');
        if($(".charone").is(":hidden")) {
             $("#mortTypeAnalysis").removeClass('btn-bg');
        }
    });
	
	/*document.getElementById('mortTypeAnalysis').addEventListener('click', function () {
	    toggle(document.querySelectorAll('.toggle-div'));
	});*/
});

function resetData() {
	var x;
	for(x in mTypeLegends) {		
		mTypeCases[x] = 0;
		mTypeAmount[x] = 0;
	}
	
	mAllCases = 0;
	mTotalAmount = 0;
	mPRFAmount = 0;
}

function setPieCharts() {
	var option = {};
	resetData();
	getMTypeAmount();
	getMTypeCases();	
	//$("#mAllCases").text(mAllCases);
	//$("#mTotalAmount").text(mTotalAmount);
	mTypeAmountTitle = '贷款总金额: '+mTotalAmount.toFixed(2)+' 万元'
	mTypeCasesTitle = '贷款总单数: '+mAllCases+' 件'
	option = setAmountOptions(mTypeAmount);
	//贷款总金额
	pChartMTypeAmount.setOption(option);
	option = setCaseOptions(mTypeCases);
	//贷款总单数
	pChartMTypeCases.setOption(option);
	//chartAll.setOption(option);
}

function toggle (elements, specifiedDisplay) {
  var element, index;
  
  elements = elements.length ? elements : [elements];
  for (index = 0; index < elements.length; index++) {
    element = elements[index];

    if (isElementHidden(element)) {
      element.style.display = '';

      // If the element is still hidden after removing the inline display
      if (isElementHidden(element)) {
        element.style.display = specifiedDisplay || 'block';
      }
    } else {
      element.style.display = 'none';
    }
  }
  function isElementHidden (element) {
    return window.getComputedStyle(element, null).getPropertyValue('display') === 'none';
  }
}

function show(elements, specifiedDisplay) {
	elements = elements.length ? elements : [ elements ];
	for (var index = 0; index < elements.length; index++) {
		elements[index].style.display = specifiedDisplay || 'block';
	}
}

function setQueryData() {
	
	var data = getParamsValue();

	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var arguUserId = null;
	if (queryOrgFlag == 'true') {
		arguUserId = null;
		if (isAdminFlag == 'true') {
			queryOrgs = null;
		}
	} else {
		queryOrgs = null;
		arguUserId = "yes";
	}

	var sortcolumn = $('span.active').attr("sortColumn");
	var sortgz = $('span.active').attr("sord");
	data.sidx = sortcolumn;
	data.sord = sortgz;

	var orgArray = queryOrgs == null ? null : queryOrgs.split(",");
	data.argu_idflag = arguUserId;
	data.argu_queryorgs = orgArray;
	aist.wrap(data);
	
	return data;
}
//获取各方式贷款总数
function getMTypeCases() {
	
	var data = setQueryData();
	data.queryId = "queryMortgageTypeAnalysis";
	data.rows = 10;
	data.page = 1;

	$.ajax({
		async : false,
		url : ctx + "/quickGrid/findPage",
		method : "post",
		dataType : "json",
		data : data,
		success : function(data) {
			var index
			for (index in data.rows) {
				mTypeCases[data.rows[index].MTYPE-1] = data.rows[index].MCASES;
				mAllCases += data.rows[index].MCASES;
			}

		}
	});
}
//获取各方式贷款金额
function getMTypeAmount() {
	
	var data = setQueryData();
	data.queryId = "queryMortgageTypeAmountAnalysis";
	data.rows = 10;
	data.page = 1;

	$.ajax({
		async : false,
		url : ctx + "/quickGrid/findPage",
		method : "post",
		dataType : "json",
		data : data,
		success : function(data) {
			//console.log("===Result==="+JSON.stringify(data));
			var index
			for (index in data.rows) {
				mTypeComAmount[data.rows[index].MLOANTYPE-1] = data.rows[index].MCOMAMOUNT;
				mTypePRFAmount[data.rows[index].MLOANTYPE-1] = data.rows[index].MPRFAMOUNT;
				mTotalAmount += data.rows[index].MCOMAMOUNT;
				mTotalAmount += data.rows[index].MPRFAMOUNT;
			}
			mTypeAmount[0] = mTypeComAmount[0];//商贷收单
			mTypeAmount[1] = mTypePRFAmount[0]+mTypePRFAmount[1];//公积金
			mTypeAmount[2] = mTypeComAmount[2]+mTypePRFAmount[2];//商贷流失
		}
	});
}

function setAmountOptions(values) {
	var option = {
		title : {
			text : mTypeAmountTitle,
			//text : '贷款类型分析（金额）',
			subtext : '',
			padding : [ 25, 10 ],
			x : 'center',
		},
		tooltip : {
			trigger : 'item',
			triggerOn : 'mousemove',
			/* alwaysShowContent:true, */
			hideDelay : 1500,
			enterable : true,
			formatter : "{b}: {c} 万元 ({d}%)"
		},

		legend : {
			orient : 'vertical',
			x : 'right',
			y : 'top',
			top : 15,

			data : [ '商贷（收单）', '商贷（流失）', '公积金' ]
		},
		color : [ '#52bdbd', '#f784a5', '#ffad6b', '#295aa5' ],
		series : [

		{
			name : '贷款类型分析(金额)',
			type : 'pie',
			radius : [ '35%', '55%' ],
			animation : true,
			selectedMode : 'multiple',
			data : [ {
				value : values[0].toFixed(2),
				name : '商贷（收单）',
				itemStyle : {
					normal : {
						label : {
							formatter : '{b}: {c} 万元',
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
				value : values[2].toFixed(2),
				name : '商贷（流失）',
				itemStyle : {
					normal : {
						label : {
							formatter : '{b}: {c} 万元',
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
				value : values[1].toFixed(2),
				name : '公积金',
				itemStyle : {
					normal : {
						label : {
							formatter : '{b}: {c} 万元',
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
			} ]
		} ]
	};
	
	return option;
}

function setCaseOptions(values) {
	var option = {
		title : {
			text : mTypeCasesTitle,
			//text : '贷款类型分析（单数）',
			subtext : '',
			padding : [ 25, 10 ],
			x : 'center',
		},
		tooltip : {
			trigger : 'item',
			triggerOn : 'mousemove',
			/* alwaysShowContent:true, */
			hideDelay : 1500,
			formatter : "{b}: {c} 件 ({d}%)"
		},
		// 数据视图
		/*
		 * toolbox : { show : true, orient : 'vertical', x: 'center',
		 * y:'bottom', feature : { dataView : { show : true, readOnly : false },
		 * restore : { show : true }, saveAsImage : { show : true } } },
		 */

		legend : {
			orient : 'vertical',
			x : 'right',
			y : 'top',
			top : 15,

			data : [ '商贷（收单）', '商贷（流失）', '公积金' ]
		},
		color : [ '#52bdbd', '#f784a5', '#ffad6b', '#295aa5' ],
		series : [

		{
			name : '贷款类型分析（单数）',
			type : 'pie',
			radius : [ '35%', '55%' ],
			animation : true,
			selectedMode : 'multiple',
			data : [ {
				value : values[0],
				name : '商贷（收单）',
				itemStyle : {
					normal : {
						label : {
							formatter : '{b}: {c} 件',
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
				value : values[2],
				name : '商贷（流失）',
				itemStyle : {
					normal : {
						label : {
							formatter : '{b}: {c} 件',
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
				value : values[1],
				name : '公积金',
				itemStyle : {
					normal : {
						label : {
							formatter : '{b}: {c} 件',
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
			} ]
		}
		/*,
		{
            name:'总单数',
            type:'pie',
            selectedMode: 'single',
            radius: [0, '30%'],
            color: 'white',

            label: {
                normal: {
                    position: 'inside'
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[ {
            	value: values[0]+values[1]+values[2], 
            	name: '总计',
            	itemStyle : {
					normal : {
						label : {
							formatter : '{b}\n{c}件'
						}
					}
				}}
            ]
        }*/ ]
	};
	
	return option;
}

function setOptions(values) {

	var option = {

		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		
		color:['#f784a5', '#52bdbd','#ffad6b','#87d6c6'],
		
		legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: mTypeLegends
	    },
	    
		series : [ {
			name : '贷款类型分析',
			type : 'pie',
			radius : '50%',
			center : [ '50%', '50%' ],
			data : [ {
				value : values[0],
				name : mTypeLegends[0],
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
				name : mTypeLegends[1],
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
				name : mTypeLegends[2],
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

$('#mortgageInfoToExcel').click(function() {
	var url = "/quickGrid/findPage?xlsx&";
	// excel导出列
	var displayColomn = new Array;
	displayColomn.push('CASE_CODE');
	displayColomn.push('PROPERTY_ADDR');	
	displayColomn.push('SIGN_DATE');
	displayColomn.push('LEND_DATE');
	displayColomn.push('APPR_DATE');	
	displayColomn.push('CUST_NAME');
	displayColomn.push('MORT_TOTAL_AMOUNT');
	displayColomn.push('COM_AMOUNT');	
	displayColomn.push('PRF_AMOUNT');
	displayColomn.push('SDSTATUS');	
	displayColomn.push('FIN_ORG_NAME');
	displayColomn.push('FIN_ORG_NAME_YC');	
	displayColomn.push('IS_TMP_BANK');
	displayColomn.push('REAL_NAME');
	displayColomn.push('ORG_NAME');	
	displayColomn.push('ORG_ORG_NAME');

	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var arguUserId = null;
	if (queryOrgFlag == 'true') {
		arguUserId = null;
		if (isAdminFlag == 'true') {
			queryOrgs = null;
		}
	} else {
		queryOrgs = null;
		arguUserId = "yes";
	}

	var orgArray = queryOrgs == null ? '' : queryOrgs.split(",");

	var argu_idflag = '&argu_idflag=' + arguUserId;

	if (arguUserId == null)
		argu_idflag = '&argu_idflag=';
	var argu_queryorgs = "&" + jQuery.param({
		argu_queryorgs : orgArray
	});
	if (argu_queryorgs == null)
		argu_queryorgs = '&argu_queryorgs=';
	var params = getParamsValue();	
	var queryId = '&queryId=findToMortgageIsActive';
	var colomns = '&colomns=' + displayColomn;

	url = ctx + url + jQuery.param(params) + queryId + argu_idflag
			+ argu_queryorgs + colomns;

	$('#excelForm').attr('action', url);

	$('#excelForm').method = "post";
	$('#excelForm').submit();

})

// 日期控件
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});

/* 查询按钮查询 */
$('#mortgageInfoSearchButton').click(function() {
	loanLostApproveSearchMethod();
	setPieCharts();
});

// 查询
function loanLostApproveSearchMethod(page) {
	if (!page) {
		page = 1;
	}
	var params = getParamsValue();
	params.page = page;
	params.rows = 10;
	params.queryId = "findToMortgageIsActive";
	reloadGrid(params);
};
function isTempBankRadio(rName,rValue){
    var rObj = document.getElementsByName(rName);
    for(var i = 0;i < rObj.length;i++){
        if(rObj[i].value == rValue){
            rObj[i].checked =  'checked';
            $("#isTempBankAll").val(2);
        }
    }
}
function isLoseRadio(rName,rValue){
    var rObj = document.getElementsByName(rName);
    for(var i = 0;i < rObj.length;i++){
        if(rObj[i].value == rValue){
            rObj[i].checked =  'checked';           
        	$("#isLoseAll").val(2);  
        }
    }
}
// 清空
$('#mortgageInfoCleanButton').click(function() {
	$("input[name='caseCode']").val('');
	$("input[name='propertyAddr']").val('');
	$("input[name='dtBegin']").datepicker('update', '');
	$("input[name='dtEnd']").datepicker('update', '');
	$("input[name='REAL_NAME']").val('');
	$("input[name='orgName']").val('');
	$("input[name='custName']").val('');
	// select清除
	//$("select").val("");
	$("input[name='amountBegin_0']").val('');
	$("input[name='amountEnd_0']").val('');
	//清空单选按钮
	$('input[name="isLoseLoan"]:checked').val('');
	$('input[name="isLoseLoan"]:checked').attr("checked",false);
	$('input[name="isTempBank"]:checked').val('');
	$('input[name="isTempBank"]:checked').attr("checked",false);
	
	isTempBankRadio('isTempBank',$("#isTempBankAll").val());
	isLoseRadio('isLoseLoan',$("#isLoseAll").val());
	
	$("#yuCuiOriGrpId").val("");
	//select框情况设置
	$("#loanLostFinOrgNameYc").val("");
	$("#loanLostFinOrgName").val("");
	$("#loanType").val("");

});

function reloadGrid(data) {

	var queryOrgFlag = $("#queryOrgFlag").val();
	var isAdminFlag = $("#isAdminFlag").val();
	var queryOrgs = $("#queryOrgs").val();
	var arguUserId = null;
	if (queryOrgFlag == 'true') {
		arguUserId = null;
		if (isAdminFlag == 'true') {
			queryOrgs = null;
		}
	} else {
		queryOrgs = null;
		arguUserId = "yes";
	}

	var sortcolumn = $('span.active').attr("sortColumn");
	var sortgz = $('span.active').attr("sord");
	data.sidx = sortcolumn;
	data.sord = sortgz;

	var orgArray = queryOrgs == null ? null : queryOrgs.split(",");
	data.argu_idflag = arguUserId;
	data.argu_queryorgs = orgArray;
	aist.wrap(data);

	$.ajax({
		async : true,
		url : ctx + "/quickGrid/findPage",
		method : "post",
		dataType : "json",
		data : data,
		beforeSend : function() {
			$.blockUI({
				message : $("#salesLoading"),
				css : {
					'border' : 'none',
					'z-index' : '9999'
				}
			});
			$(".blockOverlay").css({
				'z-index' : '9998'
			});
		},
		success : function(data) {
			$.unblockUI();
			data.ctx = ctx;
			var mortgageInfoList = template('template_mortgageInfoList', data);
			$("#mortgageInfoList").empty();
			$("#mortgageInfoList").html(mortgageInfoList);
			// 显示分页
			initpage(data.total, data.pagesize, data.page, data.records);
		},
		error : function(e, jqxhr, settings, exception) {
			$.unblockUI();
		}
	});
}

// 分页
function initpage(totalCount, pageSize, currentPage, records) {

	if (totalCount > 1500) {
		totalCount = 1500;
	}
	var currentTotalstrong = $('#currentTotalPage').find('strong');
	if (totalCount < 1 || pageSize < 1 || currentPage < 1) {
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage + '/' + totalCount);
	$('#totalP').text(records);

	$("#pageBar").twbsPagination({
		totalPages : totalCount,
		visiblePages : 9,
		startPage : currentPage,
		first : '<i class="fa fa-step-backward"></i>',
		prev : '<i class="fa fa-chevron-left"></i>',
		next : '<i class="fa fa-chevron-right"></i>',
		last : '<i class="fa fa-step-forward"></i>',
		showGoto : true,
		onPageClick : function(event, page) {
			loanLostApproveSearchMethod(page);
		}
	});
}

/**
 * 查询参数取得
 */
function getParamsValue() {
	// 日期类型
	var signTimeStart = null;
	var signTimeEnd = null;
	var lendTimeStart = null;
	var lendTimeEnd = null;
	var apprTimeStart = null;
	var apprTimeEnd = null;
	var realhtTimeStart = null;
	var realhtTimeEnd = null;

	var comAmountStart = null;
	var comAmountEnd = null;
	var prfAmountStart = null;
	var prfAmountEnd = null;
	var mortTotalAmountStart =null;
	var mortTotalAmountEnd = null;
	// 设置查询参数
	var params = {};

	var start = $('#dtBegin_0').val();
	var end = $('#dtEnd_0').val();

	var startAmount = $('#amountBegin_0').val().trim();
	var endAmount = $('#amountEnd_0').val().trim();
	// 签约时间
	if (end && end != '') {
		end = end + ' 23:59:59';
	}
	// 获取誉萃组织
	var organizeOrgId = $('#yuCuiOriGrpId').val().trim();
	
/*	if (org == "ff8080814f459a78014f45a73d820006") {
		org = null;
	} else if (org == "" || org == null) {
		org = $("#org").val();
	}*/
	var processorOrgId = $('#REAL_NAME').val().trim();
	
	// 案件编号
	var caseCode = $("#caseCode").val().trim();
	if ("" == caseCode || null == caseCode) {
		caseCode = null;
	}

	// 物业地址
	var propertyAddr = $("#propertyAddr").val().trim();
	if (propertyAddr == "" || propertyAddr == null) {
		propertyAddr = null;
	}

	// 客户姓名
	var custName = $("#custName").val().trim();
	if (custName == "" || custName == null) {
		custName = null;
	}
	
	
	//获取银行和支行信息	
	var loanLostFinOrgNameCode = $("#loanLostFinOrgName option:selected").val();
	var loanLostFinOrgNameYcCode = $("#loanLostFinOrgNameYc option:selected").val();
	params.loanLostFinOrgNameCode = loanLostFinOrgNameCode;
	params.loanLostFinOrgNameYcCode = loanLostFinOrgNameYcCode;
	params.processorOrgId=processorOrgId;
	params.organizeOrgId=organizeOrgId;
	// 获取select 选中时间的值
	var timeSelect = $("#loanLostCaseListTimeSelect option:selected").val();
	if (timeSelect == "SIGN_DATE") {
		signTimeStart = start;
		signTimeEnd = end;
		params.signTimeStart = signTimeStart;
		params.signTimeEnd = signTimeEnd;
	} else if (timeSelect == "LEND_DATE") {
		lendTimeStart = start;
		lendTimeEnd = end;
		params.lendTimeStart = lendTimeStart;
		params.lendTimeEnd = lendTimeEnd;
	} else if (timeSelect == "APPR_DATE") {
		apprTimeStart = start;
		apprTimeEnd = end;
		params.apprTimeStart = apprTimeStart;
		params.apprTimeEnd = apprTimeEnd;
	}else if(timeSelect == "REAL_HT_TIME"){
		
		realhtTimeStart = start;
		realhtTimeEnd = end;
		alert(realhtTimeStart);
		alert(realhtTimeEnd);
		params.realhtTimeStart = realhtTimeStart;
		params.realhtTimeEnd = realhtTimeEnd;
	}
	// 获取select 选中时间的值
	var amountSelect = $("#loanLostCaseListAmountSelect option:selected").val();
	if (amountSelect == "COM_AMOUNT") {
		comAmountStart = startAmount;
		comAmountEnd = endAmount;
		if(comAmountStart==0 && comAmountEnd==0){
			comAmountStart=null;
			comAmountEnd=null;
		}
		params.comAmountStart = comAmountStart;
		params.comAmountEnd = comAmountEnd;
	} else if (amountSelect == "MORT_TOTAL_AMOUNT") {
		mortTotalAmountStart = startAmount;
		mortTotalAmountEnd = endAmount;
		if(mortTotalAmountStart==0 && mortTotalAmountEnd==0){
			mortTotalAmountStart=null;
			mortTotalAmountEnd=null;
		}
		params.mortTotalAmountStart = mortTotalAmountStart;
		params.mortTotalAmountEnd = mortTotalAmountEnd;
	} else if (amountSelect == "PRF_AMOUNT") {
		prfAmountStart = startAmount;
		prfAmountEnd = endAmount;
		if(prfAmountStart==0 && prfAmountEnd==0){
			prfAmountStart=null;
			prfAmountEnd=null;
		}		
		params.prfAmountStart = prfAmountStart;
		params.prfAmountEnd = prfAmountEnd;
	}
	// 产品类型
	// var finCode = getCheckBoxValues("finCode");
	var isTempBank = $("input[name='isTempBank']:checked").val();	
	//alert("isTempBank==="+isTempBank);
	if(isTempBank == 2){
		isTempBank=null;//为2 设置为null则不添加该查询条件
	}else if (isTempBank == 1){		
		isTempBank='是';
	}else if(isTempBank == 0){
		isTempBank='否';
	}
	var isLose = $("input[name='isLoseLoan']:checked").val();
	//alert("isLose==="+isLose);
	var a = isLose;
	if(isLose == 2){
		isLose=null;//为2 设置为null则不添加该查询条件
	}else if (isLose == 1){
		isLose='是';
	}else if(isLose == 0){
		isLose='否';
	}
		
	// 贷款类型选择
	var loanType = $("#loanType option:selected").val();
	params.loanType=loanType;
	params.isTempBank = isTempBank;
	params.isLose = isLose;	
	params.caseCode = caseCode;
	params.propertyAddr = propertyAddr;
	params.custName = custName;
	// params.finCode = finCode;	
	return params;
}

// 主办图标选择
$('#MortgageLostListOnclick').click(function() {
	chooseCaseOperator(serviceDepId);
});

// 组织图标选择
$('#MortgageLostListOrganizeOnclick').click(function() {
	orgSelect({
		displayId : 'oriGrpId',
		displayName : 'radioOrgName',
		startOrgId : serviceDepId,
		expandNodeId : serviceDepId, // 添加此属性的作用是展开 组织列表
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		callBack : radioYuCuiOrgSelectCallBack
	})
});

// 选业务组织的回调函数
function radioYuCuiOrgSelectCallBack(array) {
	if (array && array.length > 0) {
		$("#orgName").val(array[0].name);
		$("#yuCuiOriGrpId").val(array[0].id);
	} else {
		$("#orgName").val("");
		$("#yuCuiOriGrpId").val("");
	}
}

// 选择组织之后 级联选择主办人信息
function chooseCaseOperator(id) {
	var serviceDepId = id;
	var yuCuiOriGrpId = $("#yuCuiOriGrpId").val();

	if (yuCuiOriGrpId != "") {
		userSelect({
			startOrgId : yuCuiOriGrpId,
			expandNodeId : yuCuiOriGrpId,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : 'consultant',
			callBack : loanLostCaseListSelectUserBack
		});
		// $("#yuCuiOriGrpId").val("");
	} else {
		userSelect({
			startOrgId : serviceDepId,
			expandNodeId : serviceDepId,
			nameType : 'long|short',
			orgType : '',
			departmentType : '',
			departmentHeriarchy : '',
			chkStyle : 'radio',
			jobCode : 'consultant',
			callBack : loanLostCaseListSelectUserBack
		});
	}
}

// 选取人员的回调函数
function loanLostCaseListSelectUserBack(array) {
	if (array && array.length > 0) {
		$("#REAL_NAME").val(array[0].username);
		$("#REAL_NAME").attr('hVal', array[0].userId);

	} else {
		$("#REAL_NAME").val("");
		$("#REAL_NAME").attr('hVal', "");
	}
}
// 案件case_code排序图标变化函数
function caseCodeSort() {
	if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down") {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up ');
	} else if ($("#caseCodeSorti").attr("class") == "fa fa-sort-desc fa_down icon-chevron-down") {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-asc fa_up');
	} else {
		$("#caseCodeSorti").attr("class", 'fa fa-sort-desc fa_down');
	}
}


//查询分行信息
function getParentBank(selector,selectorBranch,finOrgCode,tag,flag){
	var bankHtml = "<option value=''>请选择</option>";
	var param = {nowCode:finOrgCode};
	//cl 表示入围银行（即和中原合作的银行，范围小一些）;  不加tag=cl 表示临时银行,范围大一些，银行多一些
	//finOrgCode  银行的代码，不加该字段表示查询所有的分行信息
	//flag, "egu" 农业银行 单独处理
	if(tag == 'cl'){
		param.tag = 'cl';
	}
    $.ajax({
    	cache:true,
    	url:ctx+"/manage/queryParentBankList",
		method:"post",
		dataType:"json",
		async:false,
		data:param,
		success:function(data){
			if(data != null){
				for(var i = 0;i<data.length;i++){
					if((data[i].finOrgCode!='1032900'&&data[i].finOrgCode!='3082900')||flag!='egu'){//不作农业银行的讯价
						var coLevelStr='';
						bankHtml+="<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>";
					}
				}
			}
		},
       error:function(e){
    	   alert(e);
       }
     });
    
     selector.find('option').remove();
	 selector.append($(bankHtml));
	 $.ajax({
		    url:ctx+"/manage/queryParentBankInfo",
		    method:"post",
		    dataType:"json",
			async:false,
		    data:{finOrgCode:finOrgCode,flag:flag},
		    success:function(data){
	    		if(data != null){
	    			selector.val(data.content);
	    		}
	    	}
		});	
	 
	 getBranchBankList(selectorBranch,selector.val(),finOrgCode,tag,flag);

	 return bankHtml;
}


//查询 分行对应的支行信息
function getBranchBankList(selectorBranch,pcode,finOrgCode,tag,flag){
	selectorBranch.find('option').remove();
	selectorBranch[0];
	selectorBranch.append($("<option value=''>请选择</option>"));
	var param = {faFinOrgCode:pcode,flag:flag,nowCode:finOrgCode};
	if(tag == 'cl'){
		param.tag = 'cl';
	}
	$.ajax({
		cache:true,
	    url:ctx+"/manage/queryBankListByParentCode",
	    method:"post",
	    dataType:"json",
		async:false,
	    data:param,
	    	success:function(data){
	    		if(data != null){
	    			for(var i = 0;i<data.length;i++){
						var coLevelStr='('+data[i].coLevelStr+')';
			
						//以下各支行后面有(其他)
						//var option = $("<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>");
						var option = $("<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+"</option>");
						if(data[i].finOrgCode==finOrgCode){
							option.attr("selected",true);
						}
						
						selectorBranch.append(option);
	    			}
	    		}
	    	}
	 });

	return true;
}
