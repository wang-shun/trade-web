require.config({
    	baseUrl: ctx.value+'/js',
    	paths: {
    		"math": ctx.value+"/js/plugins/required/math",
    		"jquery" : ctx.value+"/js/jquery-2.1.1",
    		"template" : ctx.value+"/js/template",
    		"viewer" : ctx.value+"/js/viewer/viewer.min",
    		"webuploader" : ctx.value+"/js/plugins/webuploader/webuploader.min",
    		"fileUpload" : ctx.value+"/js/plugins/aist/aist.fileupload"
    	},
    });