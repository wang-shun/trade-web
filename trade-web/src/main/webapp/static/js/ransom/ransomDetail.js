/**
 * 赎楼详情js
 * @author wbcaiyx
 */


$(document).ready(function(){
	reloadDetail();
	reloadHistoryRecord();
	reloadTimeRecord();
	//操作记录
	getOperateLogList();
	//附件信息
	getShowAttachment();
});

/**
 * 详情table加载
 */
function reloadDetail(){
	var url = "/quickGrid/findPage";
	var ctx = $("#ctx").val();
	var ransomCode = $("#ransomCode").val();
	url = ctx + url;
	var dispCols=[ '尾款机构','尾款类型', '抵押类型','贷款金额','剩余部分','实际还款金额' ];
	var colModels=
	[ {
		name : 'tailOrgName',
		index : 'tailOrgName',
		align : "center",
		width : 176
	}, {
		name : 'retaTypeName',
		index : 'retaTypeName',
		align : "center",
		width : 176
	}, {
		name : 'diyaTypeName',
		index : 'diyaTypeName',
		align : "center",
		width : 176
	}, {
		name : 'LOAN_MONEY',
		index : 'LOAN_MONEY',
		align : "center",
		width : 176
	}, {
		name : 'REST_MONEY',
		index : 'REST_MONEY',
		align : "center",
		width : 176
	}, {
		name : 'ACTUALMoney',
		index : 'ACTUALMoney',
		align : "center",
		width : 176
	}];
	$("#tails").jqGrid(
	{
		url : url,
		datatype : "json",
		mtype : "POST",
		height : 300,
		autowidth : true,
		shrinkToFit : true,
		rowNum : 10,
		/* rowList: [10, 20, 30], */
		colNames : dispCols,
		colModel : colModels,
		pager : "#tails_pager",
		sortname:'DIYA_TYPE',
        sortorder:'desc',
		viewrecords : true,
		pagebuttions : true,
		hidegrid : false,
		recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
		pgtext : " {0} 共 {1} 页",

		// rowid为grid中的行顺序
		onSelectRow : function(rowid) {
		},
		postData : {
			queryId : "queryRansomDetail",
			argu_ransomcode : ransomCode
		}
	});
}

	/**
	 * 加载历史申请记录
	 * @returns
	 */
	function reloadHistoryRecord(){
		var url = "/quickGrid/findPage";
		var ctx = $("#ctx").val();
		var ransomcode = $("#ransomCode").val();
		url = ctx + url;
		var dispCols=[ '申请人','申请时间', '申请金额','申请机构','中止原因' ];
		var colModels=
		[ {
			name : 'APPLY_USER',
			index : 'APPLY_USER',
			align : "center",
			width : 176
		}, {
			name : 'APPLY_TIME',
			index : 'APPLY_TIME',
			align : "center",
			width : 176
		}, {
			name : 'LOAN_MONEY',
			index : 'LOAN_MONEY',
			align : "center",
			width : 176
		}, {
			name : 'FIN_ORG_NAME',
			index : 'FIN_ORG_NAME',
			align : "center",
			width : 176
		}, {
			name : 'STOP_REASON',
			index : 'STOP_REASON',
			align : "center",
			width : 176
		}];
		$("#his_record").jqGrid(
		{
			url : url,
			datatype : "json",
			mtype : "POST",
			height : 300,
			autowidth : true,
			shrinkToFit : true,
			rowNum : 10,
			/* rowList: [10, 20, 30], */
			colNames : dispCols,
			colModel : colModels,
			pager : "#his_record_pager",
			//sortname:'A.create_time',
	        //sortorder:'desc',
			viewrecords : true,
			pagebuttions : true,
			hidegrid : false,
			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
			pgtext : " {0} 共 {1} 页",

			// rowid为grid中的行顺序
			onSelectRow : function(rowid) {
			},
			postData : {
				queryId : "queryRansomHistoryRecord",
				argu_ransomcode : ransomcode
			}
		});
	}
	
	/**
	 * 加载时间信息记录
	 * @returns
	 */
	function reloadTimeRecord(){
		var url = ctx + '/quickGrid/findPage';
		var ransomCode = $('#ransomCode').val();
		var data = {};
		data.page = 1;
		data.rows = 10;
		data.queryId = "queryRansomTimeRecord";
		data.argu_ransomCode = ransomCode;
		$.ajax({
			async: true,
			type:'POST',
			url:url,
			dataType:'json',
			data:data,
			beforeSend: function () {
	        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	        },  
	        success: function(data){
	        	$.unblockUI();
	        	var html = template('template_ransomTimeInfo',data);
	      		$('#time-record').html(html);
	        }
		});	
	}
	
	/**
	 * 操作记录
	 * @returns
	 */
	function getOperateLogList(){
		var url = "/quickGrid/findPage";
		var ctx = $("#ctx").val();
		var ransomcode = $("#ransomCode").val();
		var casecode = $("#caseCode").val();
		url = ctx + url;
		// Configuration for jqGrid Example 1
		var dispCols=[ 'TASKID', 'CASECODE', 'PARTCODE',
						'INSTCODE', '红绿灯', '红灯记录', '所属流程信息','任务名', '执行人',
						'执行时间','任务状态' ];
		var colModels=
		[ {
			name : 'ID',
			index : 'ID',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		}, {
			name : 'CASE_CODE',
			index : 'CASE_CODE',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		}, {
			name : 'PART_CODE',
			index : 'PART_CODE',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		}, {
			name : 'INST_CODE',
			index : 'INST_CODE',
			align : "center",
			width : 0,
			key : true,
			resizable : false,
			hidden : true
		}, {
			name : 'DATELAMP',
			index : 'DATELAMP',
			width : 40,
			editable : true,
			formatter : dateLampFormatter
		}, {
			name : 'RED_LOCK',
			index : 'RED_LOCK',
			width : 30,
			editable : true,
			formatter : isRedFormatter
		}, {
			name : 'WFE_NAME',
			index : 'WFE_NAME',
			width : 30
		}, {
			name : 'NAME',
			index : 'NAME',
			width : 75
		}, {
			name : 'ASSIGNEE',
			index : 'ASSIGNEE',
			width : 75
		},  {
			name : 'END_TIME',
			index : 'END_TIME',
			width : 90
		},{
			name : 'status',
			index : 'status',
			width : 90
		}
		];
		$("#operation_history_table").jqGrid(
				{
					url : url,
					datatype : "json",
					mtype : "POST",
					height : 300,
					autowidth : true,
					shrinkToFit : true,
					rowNum : 10,
					/* rowList: [10, 20, 30], */
					colNames : dispCols,
					colModel : colModels,
					pager : "#operation_history_pager",
					sortname:'A.create_time',
	    	        sortorder:'desc',
					viewrecords : true,
					pagebuttions : true,
					hidegrid : false,
					recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
					pgtext : " {0} 共 {1} 页",

					// rowid为grid中的行顺序
					onSelectRow : function(rowid) {
					},
					postData : {
						queryId : "queryRansomTaskHistoryItemList",
						argu_casecode : casecode,
						argu_ransomcode : ransomcode
					}

		});
	}
	/**
	 * 附件信息
	 */
	function getShowAttachment() {
		var caseCode = $("#caseCode").val();
		$.ajax({
			type : 'post',
			cache : false,
			async : true,//false同步，true异步
			dataType : 'json',
			url : ctx+'/attachment/quereyAttachment',
			data : [{
				name : 'caseCode',
				value : caseCode
			}, 
			{
				name : 'available',
				value : 'Y'
			}
	    	],
			success : function(data) {
				dataLength=data.length;
				//将返回的数据进行包装
				var trStr = "";

				var ssss="";
				
				var oldType = "";
				$.each(data, function(indexAcc, value){
					if(value.partCode !='property_research'){//!产调
						//附件类型,T_TO_ACCESORY_LIST
						if(value.preFileName!=oldType){
							if(oldType!=""){
								trStr += '</div></div>';
							}
							oldType=value.preFileName;
							trStr += '<div class="row ibox-content">';
							trStr += '<label class="col-sm-2 control-label" style="line-height:90px;text-align:center;">'+value.preFileName+'</label>';
							trStr += '<div class="col-sm-10 lightBoxGallery" style="text-align:left">';
						}
						trStr += "<a href='#' title='"+value.fileName+"' data-gallery='' style='height:90px;width:80px;margin-left:5px;margin-right:5px;margin-bottom:20px;'>";
						trStr += "<img src='"+appCtx['shcl-filesvr-web'] +"/JQeryUpload/getfile?fileId="+value.preFileAdress+"' style='padding-bottom: 5px;padding-top: 5px;width:100px;'>";
						trStr += "</a>";
						
					}
				});
				$("#imgShow").append(trStr);
				$('.wrapper-content').viewer();
			},
			error : function(errors) {
			}
		});
	}
	/**
	 * 变更金融权证
	 */
	function showOrgCp() {
		var url = "/case/getUserOrgCpUserList";
		var ctx = $("#ctx").val();
		var caseCode= $("#caseCode").val();
		url = ctx + url;
		var data={operation:"JRQZ", caseCode:caseCode};
		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			url : url,
			dataType : "json",
			timeout : 10000,
			data : data,
			beforeSend:function(){
                $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
                $(".blockOverlay").css({'z-index':'9998'});
            },
			success : function(data) {
				$.unblockUI();
//				console.log(data.length);如果没有数据，则提示没有责任人
				showLeadingModal(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$.unblockUI();
			}
		});
	}
	
	/**
	 * 选择权证modal
	 * @param data
	 */
	function showLeadingModal(data) {
		var addHtml = '';
		var ctx = $("#ctx").val();
		$.each(data,function(i, n) {
			addHtml += '<div class="col-lg-4"><div class="contact-box">';
            addHtml += '<a href="javascript:distributeCase('+i+')">';
            addHtml += '<div class="col-sm-5"><div class="text-center">';
            addHtml +='<span class="userHead1">';
            if(n.imgUrl!=null){
                addHtml += '<img onload="javascript:imgLoad(this)" alt="image" class="himg" src="'+n.imgUrl+'"/>';
            }
            addHtml +='</span>';
            addHtml += '<div class="m-t-xs font-bold">金融权证</div></div></div>';
            addHtml += '<div class="col-sm-7">';
            addHtml += '<input id="user_'+i+'" type="hidden" value="'+n.id+'">';
            addHtml += '<input id="userName_'+i+'" type="hidden" value="'+n.realName+'">';
            addHtml += '<h3><strong>'+n.realName+'</strong></h3>';
            addHtml += '<input id="mobile_'+i+'" type="hidden" value="联系电话：'+n.mobile+'">'+'联系电话：'+n.mobile;
            addHtml += '<p>当前单数：'+n.userCaseCount+'</p>';
            addHtml += '<p>本月接单：'+n.userCaseMonthCount+'</p>';
            addHtml += '<p>未过户单：'+n.userCaseUnTransCount+'</p>';
            addHtml += '</div><div class="clearfix"></div></a>';
            addHtml += '</div></div>';
		})
		$("#leading-modal-data-show").html(addHtml);
		$('.contact-box').each(function() {
			animationHover(this, 'pulse');
		});
		$('#leading-modal-form').modal("show");
	}
	
	var userName;
    var id;
    function distributeCase(index) {
		window.wxc.confirm("您是否确认进行责任人变更？",{"wxcOk":function(){
			var caseCode = $("#caseCode").val();
			var instCode = $("#instCode").val();
			var ransomCode = $("#ransomCode").val();
			var userId = $("#user_" + index).val();

			var url = "/task/ransom/changeRansomOwner";
			var ctx = $("#ctx").val();
			url = ctx + url;
			var params = {userId:userId,caseCode:caseCode,ransomCode:ransomCode};

			$.ajax({
				cache : false,
				async : true,
				type : "POST",
				url : url,
				dataType : "json",
				timeout : 10000,
				data : params,
				
				success : function(data) {
					if(data.success){
						window.wxc.success("变更成功");
						location.reload();
					}else{
						window.wxc.error(data.message);
					}
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}});
		$('#leading-modal-form').modal("hide");
	
    }
	
	function imgLoad(img){
        img.parentNode.style.backgroundImage="url("+img.src+")";
    }
	
	function dateFormat(dateTime){
		if(dateTime ==null || dateTime == '' || dateTime == undefined){
			return '';
		}
		var date = new Date(dateTime);
		return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
	}
	var lamp1 = $("#Lamp1").val();
	var lamp2 = $("#Lamp2").val();
	var lamp3 = $("#Lamp3").val();
	/**
	 * 红绿灯Formate
	 * @param cellvalue
	 * @returns {String}
	 */
	function dateLampFormatter(cellvalue) {

		var color='';
		if (cellvalue < lamp1||cellvalue==null)
			return "";
		if (cellvalue < lamp2) {
			color='green';
		} else if (cellvalue < lamp3) {
			color='yellow';
		} else {
			color='red';
		}
		var outDiv = '<div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;">';
		outDiv+='<div class="sk-double-bounce1" style="background-color:'+color+'"></div>';
		outDiv+='<div class="sk-double-bounce2" style="background-color:'+color+'"></div>';
		outDiv+='</div>';
		return outDiv;
	}

	function isRedFormatter(cellvalue) {

		var reStr='无';
		if (cellvalue =='1')
			reStr='有';
		return reStr;
	}