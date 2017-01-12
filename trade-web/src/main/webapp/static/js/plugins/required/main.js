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
    		"validate" : "js/plugins/validate/jquery.validate.min",
    		"additional" : "js/plugins/validate/common/additional-methods",
    		"valid" : "js/trunk/task/taskTransSign.validate",
    		"m" : "js/plugins/validate/common/messages_zh",
    		"grid" : "js/plugins/jqGrid/i18n/grid.locale-en",
    		"jqGrid" : "js/plugins/jqGrid/jquery.jqGrid.min",
    		"ligerui" : "static/tbsp/js/ligerui/ligerui.all"   //选择人员组织弹出框依赖的js文件
    	}
    });





