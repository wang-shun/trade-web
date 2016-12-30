require.config({
	baseUrl: ctx+'/js',
	paths: {
		"math": ctx+"/js/plugins/required/math",
		"jquery" : ctx+"/js/jquery-2.1.1",
		"template" : ctx+"/js/template",
		"viewer" : ctx+"/js/viewer/viewer.min",
		"webuploader" : ctx+"/js/plugins/webuploader/webuploader.min",
		"fileUpload" : ctx+"/js/plugins/aist/aist.fileupload"
	},
});

requirejs(['./config'], function (config) {
   
});

