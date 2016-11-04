var FormFileUpload = function () {


    return {
        //main function to initiate the module
        init: function () {

            // Initialize the jQuery File Upload widget:
            $('#fileupload').fileupload({
                // Uncomment the following to send cross-domain cookies:
                //xhrFields: {withCredentials: true},
                url: appCtx['shcl-filesvr-web']+'/servlet/jqueryFileUpload'
            });

            // Load existing files:
            // Demo settings:
            $.ajax({
                // Uncomment the following to send cross-domain cookies:
                //xhrFields: {withCredentials: true},
                url: $('#fileupload').fileupload('option', appCtx['shcl-filesvr-web']+'/servlet/jqueryFileUpload'),
                //dataType: 'json',
                async : false,
                autoUpload: false,
                context: $('#fileupload')[0],
                maxFileSize: 5000000,
                acceptFileTypes: /(\.|\/)(tif|gif|jpe?g|png)$/i,
                process: [{
                        action: 'load',
                        fileTypes: /^image\/(tif|gif|jpeg|png)$/,
                        maxFileSize: 20000000 // 20MB
                    }, {
                        action: 'resize',
                        maxWidth: 1440,
                        maxHeight: 900
                    }, {
                        action: 'save'
                    }
                ]
            }).done(function (result) {
                $(this).fileupload('option', 'done')
                    .call(this, null, {
                    result: result
                });
            });

            // Upload server status check for browsers with CORS support:
            if ($.support.cors) {
                $.ajax({
                	async : false,
                    dataType : 'jsonp',
                	//jsonp : 'callback',
                    url: appCtx['shcl-filesvr-web']+'/servlet/jqueryFileUpload'
                }).fail(function () {
                    $('<span class="alert alert-error"/>')
                        .text('Upload server currently unavailable - ' +
                        new Date())
                        .appendTo('#fileupload');
                });
            }

            // initialize uniform checkboxes  
            App.initUniform('.fileupload-toggle-checkbox');
        }

    };

}();
    

$('.btn.blue.start').on('click', function() {
	$('.start button').click();
});

$(".btn.yellow.cancel").on('click', function() {
	$('.cancel button').click();
});

$(".btn.red.delete").on('click', function() {
	$(".delete button").each(function(i,val) { 
		var checker = $(val).next();
		if(0 != checker.find(".checked").length ){
			$(val).click();
		}
	});
});


