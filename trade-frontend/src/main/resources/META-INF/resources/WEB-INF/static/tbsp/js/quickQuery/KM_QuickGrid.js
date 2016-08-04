/**
 * @author HuangSiwei
 * @date
 */
+function($, window) {
	"use strict";
	$.KM_QuickGrid = $.KM_QuickGrid || {};
	$.fn.extend(
				$.KM_QuickGrid,
				{
					init : function(p) {
						
						$.KM_QuickGrid.getData.call(this, p);

					},
					showForm : function() {
						var thisDiv = this;
						var p = this.p;
						this.queryParms = [];
						var $formDiv = null;
						if (p.queryForm) { // 定义了查询表单的ID，创建查询表单
							$formDiv = $('#' + p.queryForm);//
							$formDiv.addClass('liger-form');
							//$formDiv.addClass('l-form');
							var containFormDiv = $('<div class="fields"></div>').appendTo(
									$formDiv);
							var conditionList = this.initData
									&& this.initData.searchCondition
									&& this.initData.searchCondition.conditionList
									|| [];

							
							var fields = [];
							var valFuncs = {};
							var j =0 ;
							for (var i = 0; i < conditionList.length; i++) {
								var condition = conditionList[i];
								//console.log(condition);
								if(j%2==0){
									if(condition.searchType == "between"){
										fields.push({ display: condition.label, name: condition.name, newline: true, type: "date" });
										fields.push({ display:  "至", name: condition.name, newline: false, type: "date" });
										j++;
									}else{
										fields.push({ display: condition.label, name: condition.name, newline: true, type: "text" });
									}
								}else{
									if(condition.searchType=="between"){
										fields.push({ display: condition.label, name: condition.name, newline: false, type: "date" });
										fields.push({ display: "至", name: condition.name, newline: true, type: "date" });
										j++;
									}else{
										fields.push({ display: condition.label, name: condition.name, newline: false, type: "text" });
									}
								}
								j++;
								var conditionValFunc = createCondition(
										$formDiv, condition, i);

								var name = condition.name;
								valFuncs[name] = conditionValFunc;
								
							}
							this.valFuncs = valFuncs;
							//console.log(valFuncs);
							//console.log(fields);
							
							try{
								 $formDiv.ligerForm({
									 inputWidth: 170, labelWidth: 90, space: 40,
									 fields:fields
								});
							}catch(e){
								//console.log(e);
							}
							
							if(p.showQueryBtn){
								var button = $('<a class="l-button" style="width:80px;float:right; margin-left:6px;">查询</a>');
								button.on('click', function() {
									$(thisDiv).KM_QuickGrid('doQuery');
								});
								button.appendTo($formDiv);
							}
							
						}// 创建查询表单end
					},
					showGrid : function(postData) {
						var thisDiv = this;
						var p = this.p;
						p.datagrid.parms=[{name:"queryId",value:p.queryId}];
						
						if(postData){
							p.datagrid.parms= p.datagrid.parms.concat(postData);
						}
						
						//console.log(this.p.datagrid.parms);
						var divId = thisDiv.id;

						// 转换grid，把tableRows转成数组的数组
						var initGrid = this.initData.grid;
						var trs = initGrid.tableRows;
						var columns = [];
						for (var i = 0; i < trs.length; i++) {
							var tr = [];
							var cols = trs[i].columns;
							for (var j = 0; j < cols.length; j++) {
								var col = cols[j];
								if (col.pageFormatter) {// 处理formatter
									var func = $.KM_QuickGrid.getAccessor(
											window, col.pageFormatter);
									if ($.isFunction(func)) {
										col.formatter = func;
									}
								}
								tr.push(col);
							}
							columns.push(tr);
						}
						// 结束转换

						//p.tableId = divId + 'QuickGrid';

						//p.datagrid.parms.queryId = p.queryId;
						//這里有改動
						p.datagrid.columns = columns[0];
					
						//p.datagrid.parms={"queryId":p.queryId};
						
						//console.log(p.datagrid.parms);
						//如果数据中未指定action字段，则将按钮的默认js响应方法名称赋值为permissionType的值
						var toolbarItems = thisDiv.p.datagrid.toolbar;
						for(var i = 0; i < toolbarItems.items.length; i++){
							var item = toolbarItems.items[i];
							if((item.action == undefined || item.action == "")&& item.permissionType != undefined && item.permissionType != ""){
								item.click = eval('(' + item.permissionType.toLowerCase() + ')');
							}else if(item.action != undefined && item.action != ""){
								item.click = eval('(' + item.action.toLowerCase() + ')');
							}else{
								console.warn("item has not click property");
							}
//							console.dir(["item", item]);
						}
//						console.dir(["toolbarItems", toolbarItems]);
						p.datagrid.toolbar = toolbarItems;
//						p.datagrid.toolbar =  {
//		                    items: [
//		                            { text: '增加', click: add,  img: ctx+'/static/tbsp/js/ligerui/skins/icons/add.gif' },
//		                            { line: true },
//		                            { text: '修改', click: edit, img: ctx+'/static/tbsp/js/ligerui/skins/icons/modify.gif' },
//		                            { line: true },
//		                            { text: '删除', click: del, img: ctx+'/static/tbsp/js/ligerui/skins/icons/delete.gif' }
//		                            ]
//		                        }
						manager = $("#"+divId).ligerGrid(p.datagrid);

						 $("#pageloading").hide();
						
						// p.datagrid.parms=[{"queryId":p.queryId}];
						//$.KM_QuickGrid.queryList(thisDiv,{});
						
					},
					getScript : function(thisdiv, successCallback){
//						console.log("1==============");
//						console.dir(["arguments", arguments]);
//						var thisDiv = this;
//						var p = this.p;
//						console.dir(["this", this, "this.p", p]);
//						var jsURL = this.initData && this.initData.pages
//									&& this.initData.pages.js || "";
//						$.getScript(jsURL, function(){
//							console.debug("get " + jsURL +" success");
//							successCallback();
//						});
					},
					getMethod : function(name) {
						return this.getAccessor($.fn.KM_QuickGrid, name);
					},
					getAccessor : function(obj, expr) {
						var ret, p, prm = [], i;
						if (typeof expr === 'function') {
							return expr(obj);
						}
						ret = obj[expr];
						if (ret === undefined) {
							try {
								if (typeof expr === 'string') {
									prm = expr.split('.');
								}
								i = prm.length;
								if (i) {
									ret = obj;
									while (ret && i--) {
										p = prm.shift();
										ret = ret[p];
									}
								}
							} catch (e) {
							}
						}
						return ret;
					}
				});
	$.fn.KM_QuickGrid = function(pin,param) {
		if (typeof pin === 'string') {// 调用
			var fn = $.KM_QuickGrid.getMethod(pin);
			if (!fn) {
				var ret = null;
				this.each(function() {
					var quickGrid = this.quickGrid;
					ret = quickGrid.datagrid(pin,param);
				});
				return ret;
			}else{
				var args = $.makeArray(arguments).slice(1);
				return fn.apply(this, args);
			}
		}
		return this.each(function() {

			if (this.quickGrid) {
				return;
			}

			var thisDiv = this;
			
			//var columnUrl = ctx + '/quickGrid/findColumnNameList';
			//var gridUrl = ctx + '/quickGrid/findPage';
			var p = $.extend(true, {
				//columnUrl : columnUrl,
				//gridUrl:null
				queryId : null,
				queryForm : null,
				tableId : null,
				showQueryBtn : true,
				datagrid : {
					url : null,
					columns : [],
					width:'100%',
					fitColumns : true,// True to auto expand/contract the size
										// of the columns to fit the grid width
										// and prevent horizontal scrolling.
					toolbar : null,
					method : "post",
					data :null,
					parms:[],
					striped : true,// 是否隔行换色
					rownumbers : true,// 是否显示行号
					page: 1,
					pageSize: 10,
					pageSizeOptions: [ 10, 20, 30, 40, 50 ],
					sortnameParmName: 'sortname',
					sortorderParmName: 'sortorder'
				}
			}, $.KM_QuickGrid.defaults, pin || {});
			p.datagrid.url = p.gridUrl;
			

//			$.post(p.columnUrl, {
//				queryId : p.queryId
//			}, function(data) {
//
//				thisDiv.initData = data;
//
//				thisDiv.p = p;
//
//				$.KM_QuickGrid.showForm.call(thisDiv);
//				$.KM_QuickGrid.showGrid.call(thisDiv);
//			});
			//修改为接收json数据
			$.ajax({
				url: p.columnUrl,
				data: {
					queryId : p.queryId
				},
				dataType: "json",
				type: "POST",
				success: function(data) {

					thisDiv.initData = data.data;

					thisDiv.p = p;
					thisDiv.p.datagrid.toolbar = data.toolBar;
//					$.KM_QuickGrid.getScript.call(thisDiv);
					$.KM_QuickGrid.showForm.call(thisDiv);
					$.KM_QuickGrid.showGrid.call(thisDiv);
				},
				error: function(){
					//console.dir(["args", arguments]);
					//console.debug("error");
				}
			});
		});
	}
	
	/**全局配置方法*/
	// 重写默认日期格式器
//	$.fn.datebox.defaults.formatter = function(date){
//		var y = date.getFullYear();
//		var m = date.getMonth()+1;
//		m = m<10?'0'+m:m;
//		var d = date.getDate();
//		d = d<10?'0'+d:d;
//		return y+'-'+m+'-'+d;
//	}

	// 对外开放的方法
	$.extend($.fn.KM_QuickGrid, {
		doQuery : function() {
			this.each(function() {
				var postData = [];
				var valFuncs = this.valFuncs;
				for ( var name in valFuncs) {
					var value = valFuncs[name]();
					if (!value || $.isArray(value) && value.length == 0) {
						continue;
					}
					postData.push({name:"search_"+name,value:value});
				}

				$.KM_QuickGrid.showGrid.call(this,postData);

			});
		},
		datagrid : function(){
			var datagrid = null;
			this.each(function() {
				datagrid = this.quickGrid;
			});
			return datagrid;
		}
	});

	// 私有方法
	function createCondition(parent, condition, count) {
		var scb = condition.showCallback;
		var st = condition.showType;
		if (scb) {
			var indexOf = scb.indexOf(',');
			var fn = scb.substring(0, indexOf);
			var args = scb.substring(indexOf + 1);

			var func = $.KM_QuickGrid.getAccessor(window, fn);
			if (typeof func === 'function') {
				return func.call(window, args, parent, condition, count);
			}
		} else if (st) {
			st = st.toLowerCase();
			condition.showType = st;
			var map = {
				textbox : $.KM_QuickGrid.form.input,
				numberbox : $.KM_QuickGrid.form.input,
				datebox : $.KM_QuickGrid.form.input,
				datetimebox : $.KM_QuickGrid.form.input

			};

			return map[st].call(window, parent, condition, count);
		}
	}

	// form
	
	$.KM_QuickGrid.form = $.KM_QuickGrid.form || {};
	$.KM_QuickGrid.form.dict = function(args, parent, condition, count) {
		var label = $('<label>' + condition.label + '</label>')
				.appendTo(parent);
		var container = $('<span></span>').appendTo(parent);
		if(condition.id){
			container.prop('id',condition.id)
		}
		args = args.split(',');

		var name = condition.name;

		var multiple = condition.searchType
				&& condition.searchType.toLowerCase() == 'in';
		var defaultVal = condition.defaultValue;
		defaultVal = (defaultVal || '').split(',');
		container.easydict({
			showType : args[0],
			category : args[1],
			name : name,
			defaultVal : defaultVal,
			select : {
				multiple : multiple
			}
		});
		return function() {
			return container.dict('val');
		};
	}

	$.KM_QuickGrid.form.input = function(parent, condition, count) {
		var searchType = condition.searchType
		&& condition.searchType.toLowerCase();
		if ('between' == searchType){
			
			return function(){
				var both = $("input[name='"+condition.name+"']");
				var begin = function(){
					return both[0].value;
				}
				var end = function (){
					return both[1].value;
				}
				return [begin(),end()];
			}
			
		}else{
			return function(){
				return $("input[name='"+condition.name+"']").val();
			}
		}

	
	}
}(jQuery, window);