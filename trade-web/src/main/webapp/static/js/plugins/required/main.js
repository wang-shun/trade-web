require.config({
    	baseUrl: ctx+"/",
    	shim: {
    		'blockUI': ['jquery']
    	},
    	paths: {
    		"config" : "js/plugins/required/config",
    		"aistMath": "js/plugins/required/math",
    		"jquery" : "js/jquery-2.1.1",
    		"blockUI": "static/tbsp/js/jquery-blockUI/jquery.blockUI",
    		"aistTemplate" : "js/template",
    		"viewer" : "js/viewer/viewer.min",
    		"aistWebuploader" : "js/plugins/webuploader/webuploader.min",
    		"aistFileUpload" : "js/plugins/aist/aist.fileupload",
    		"aistFileUpload1" : "js/plugins/aist/aist.fileupload1",
    		"validate" : "js/plugins/validate/jquery.validate.min",
    		"additional" : "js/plugins/validate/common/additional-methods",
    		"valid" : "js/trunk/task/taskTransSign.validate",
    		"m" : "js/plugins/validate/common/messages_zh",
    		"grid" : "js/plugins/jqGrid/i18n/grid.locale-en",
    		"jqGrid" : "js/plugins/jqGrid/jquery.jqGrid.min",
    		"ligerui" : "static/tbsp/js/ligerui/ligerui.all",   //选择人员组织弹出框依赖的js文件
    		"steps" : "js/plugins/staps/jquery.steps.min",
    		"aistJquery" : "js/plugins/aist/aist.jquery.custom",
    		"modal" : "js/trunk/JSPFileUpload/bootstrap-modal", //为解决弹出框引入的js文件
    		"modalmanager" : "js/trunk/JSPFileUpload/bootstrap-modalmanager", //为解决弹出框引入的js文件
    		"twbsPagination" : "js/plugins/pager/jquery.twbsPagination.min",
    		"bootstrapModal" : "js/bootstrap-modal",
    		"aistJqueryCustom" : "js/plugins/aist/aist.jquery.custom",   //解决$(...).reloadGrid is not a function的问题
    		"poshytip" : "js/poshytitle/src/jquery.poshytip",  //解决$(...).poshytip is not a function的问题
    		"chosen" : "js/plugins/chosen/chosen.jquery",
    		"eselect" : "js/jquery.editable-select.min"
    	}
    });





