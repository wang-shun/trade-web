/****
 *  
 *    上传组件
 *    
 *    @author : notes
 * 
 */

define(["jquery","template","viewer","webuploader"],function($, template,viewer,WebUploader) {


			  webUploader = '';
			  
			  initFileUpload = function(options) {
				  
				  var settings = $.extend({
						ctx  :  window.ctx,
						fileUploadcontainer : "fileUploadcontainer",
						auto: false,
						duplicate: true,
						accept: {
						        title: 'Images',
						        extensions: 'gif,jpg,jpeg,bmp,png',
						        mimeTypes: 'image/*'
						},
						thumbnailWidth : 64,
						thumbnailHeight : 64,
						pick : 'filePicker',
						server : 'http://filesvr.centaline.com:8081/aist-filesvr-web/webUploader/uploadPicture'
				  },options||{});
				  
				  if (typeof window.WebUploader == 'undefined') {
					  use(["/js/viewer/viewer.min.css","/js/viewer/viewer.min.js","/js/template.js","/js/plugins/webuploader/attachment-ui.css","/js/plugins/webuploader/webuploader.css","/js/plugins/webuploader/webuploader.min.js"],settings);
			      }
			      else {
			    	  queryAttachments(settings);
			      }
				  
				  /*if(!window.WebUploader.Uploader.support()) {
				        alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
				        throw new Error( 'WebUploader does not support the browser you are using.' );
				  }*/
			  };
			  use = function(urls,settings) {
				  var ctx = settings.ctx;
				  var urls = typeof urls == "string" ? [urls] : urls;
				  for (var i = 0; i < urls.length; i++) {
					   var url = urls[i].replace(/\s/g, "");
					   var isCss = /\.css$/.test(url);
					   var tag = window.document.createElement(isCss ? "link": "script");
					   if(/\.css$/.test(url)){
						   $("<link>").attr({ rel: "stylesheet", type: "text/css", href: ctx+url }).appendTo("head");
					   }
					   if(/\.js$/.test(url)){
						   var script = ctx+url;
						   jQuery.getScript(ctx+url).done(function() {
						   }).fail(function() {
				                alert("请检查"+ ctx+url +"的路径是否正确!");
				           });
					   }
				  }
				  queryAttachments(settings);
		      };
		      
			  initWebUploader = function(settings) {
				   
				    // 初始化Web Uploader
					var uploader = WebUploader.create({

					    // 选完文件后，是否自动上传。
					    auto: settings.auto,

					    // swf文件路径
					    //swf: BASE_URL + '/js/Uploader.swf',
		 				dataType: settings.dataType,//加入这个选项即可
		 				
		 				duplicate: settings.duplicate,
		 				
					    // 文件接收服务端。
					    server: settings.server,
					    //server: 'http://127.0.0.1:8083/servlet/jqueryFileUpload',

					    // 选择文件的按钮。可选。
					    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
					    pick: "."+settings.pick,

					    // 只允许选择图片文件。
					    accept: settings.accept,
					    
					    thumbnailWidth : settings.thumbnailWidth,
					    
					    thumbnailHeight : settings.thumbnailHeight
					});
					webUploader = uploader;
					
					//初始化事件
					initDeleteImgEvent(settings.fileUploadcontainer);
					initImgViewer();
					
					$("#"+settings.fileUploadcontainer).on("click",".webuploader-element-invisible", function() {
						var $this = $(this).parent().parent() ;
						// 去掉其他被选择的
						$("."+settings.pick).removeClass("checked");
						$this.addClass("checked");
					});
					
					// 当有文件添加进来的时候
					uploader.on('fileQueued', function(file) {
						var checkedBtn = $("."+settings.pick+".checked");
						var id = checkedBtn.attr("id");
					    
					    // $list为容器jQuery实例
					    var $ul = $("#"+id+"_pic_list");
					    var $li = $('<li id="'+file.id+'"><p class="imgWrap"><img></p></li>');
					    
					    var $span = $ul.find("."+settings.pick+".checked");
					    
					    var $btns = $('<div class="file-panel">' +
				                '<span class="cancel">删除</span>' +
				                '</div>').appendTo($li);
					    
					    //$ul.append($li);
					    $span.before($li);
					    
					    uploader.makeThumb(file, function(error,src) {
					        if(error) {
					        	$li.find("img").replaceWith('<span>不能预览</span>');
					            return;
					        }

					        $li.find("img").attr('src', src);
					    }, uploader.options.thumbnailWidth, uploader.options.thumbnailHeight );
					    	    
					    uploader.upload();
					});
					
					uploader.on('uploadSuccess', function(file,res) {
						$.each(res.files, function(index, item){ 
							var id = item.id;
							
							var img = $('#'+file.id).find("img");
							img.attr("data","http://a.sh.centanet.com/aist-filesvr-web/JQeryUpload/getfile?fileId="+id);
							$('#'+file.id).attr("id",id);
						});
					    initImgViewer();
					});

					// 文件上传失败，显示上传出错。
					uploader.on('uploadError', function(file) {
					    var $li = $( '#'+file.id ),
					        $error = $li.find('div.error');

					    // 避免重复创建
					    if (!$error.length) {
					        $error = $('<div class="error"></div>').appendTo( $li );
					    }

					    $error.text('上传失败');
					});

					// 完成上传完了，成功或者失败，先删除进度条。
					uploader.on( 'uploadComplete', function(file) {
					});
			  };
			  
			  initDeleteImgEvent = function (fileUploadcontainer) {
				    var $li = $("#"+fileUploadcontainer).find("li");
				    var $btns = $("#"+fileUploadcontainer).find("div.file-panel");
				    
				    $("#"+fileUploadcontainer).on('mouseenter', 'li', function() {
				    	$(this).find("div.file-panel").stop().animate({height: 30});
				    });
				    
				    $("#"+fileUploadcontainer).on('mouseleave', 'li', function() {
				    	$(this).find("div.file-panel").stop().animate({height: 0});
				    });
				    
				    $("#"+fileUploadcontainer).on('click', 'div.file-panel>span:nth-child(1)', function() {
			            var index = $(this).index(),
			                deg;

			            switch (index) {
			                case 0:
			                	var $li = $(this).parent().parent();
			                    removeFile($li);
			                    return;

			                case 1:
			                    //file.rotation += 90;
			                    break;

			                case 2:
			                    //file.rotation -= 90;
			                    break;
			            }

			          /*  if ( supportTransition ) {
			                deg = 'rotate(' + file.rotation + 'deg)';
			                $li.css({
			                    '-webkit-transform': deg,
			                    '-mos-transform': deg,
			                    '-o-transform': deg,
			                    'transform': deg
			                });
			            } else {
			            	$li.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
			            }*/
			        });
				  
			  };
		      
		      queryAttachments = function(settings) {
		    	  var ctx = settings.ctx;
		    	  var caseCode = settings.caseCode;
		    	  var partCode = settings.partCode;
		    	  var templeteId = settings.templeteId;
		    	  $.ajax({
						type : 'post',
						cache : false,
						async : true,//false同步，true异步
						dataType : 'json',
						url : ctx+'/attachment/queryNewAttachment',
						data : [{
							name : 'caseCode',
							value : caseCode
						}, {
							name : 'partCode',
							value : partCode
						}],
						dataType : "json",
						success : function(data) {
						  
							var fileuploadHtml = createTempleteHtml(settings,data);
							$("#"+settings.fileUploadcontainer).empty();
							$("#"+settings.fileUploadcontainer).html(fileuploadHtml);
			                
			                if(webUploader!=null && webUploader!=''){
			                	webUploader.destroy();
			                }
			                initWebUploader(settings);
            
						},		
						error : function(errors) {
							alert("产调加载失败");
							return false;
						}
					});
		      };
		      
		      createTempleteHtml = function(settings,data) {
		    	  var fileuploadHtml ='';
		          if(typeof(settings.templeteId) == "undefined") {
		        	  var templeteSource = '<table class="table table-bordered customerinfo">'
	                    +'<thead>'
		        	  +'<tr>'
		        	  +'<th style="width: 100px;">类型</th>'
		        	  +'<th>附件</th>'
		        	  +'</tr>' 
		        	  +'</thead>' 
		        	  +'<tbody>' 
		        	  +'{{each toAccesoryList as item index}}'
		        	  +'<tr>' 
		        	  +'<td>' 
		        	  +'{{item.accessoryName}}'
		        	  +'</td>' 
		        	  +'<td>'
		        	  +'<ul id="{{item.accessoryCode}}_pic_list" class="filelist">'
		        	  +'{{each attachmentList as item2 index}}'
                      +'{{if item.accessoryCode==item2.preFileCode}}'
	                      +'<li id="{{item2.preFileAdress}}">'
			        	  +'<p class="title">'
			        	  +'product2.png'
			        	  +'</p>'
			        	  +'<p class="imgWrap">'
			        	  +"<img src=\"http://aimg.sh.centanet.com/salesweb/image/{{item2.preFileAdress}}/80_80_f.jpg\" data=\"http://a.sh.centanet.com/aist-filesvr-web/JQeryUpload/getfile?fileId={{item2.preFileAdress}}\" width='"+settings.thumbnailWidth+"' height='"+settings.thumbnailHeight+"'/>"
			        	  +'</p>'
			        	  +'<div class="file-panel" style="height: 0px;">'
			        	  +'<span class="cancel">'
			        	  +'删除'
			        	  +'</span>'
			        	  +'</div>'
			        	  +'</li>'
		        	  +'{{/if}}'
                      +'{{/each}}'
		        	  + "<span class='"+ settings.pick +" add-file' id=\"{{item.accessoryCode}}\">"
		        	  +'</span>'
		        	  +'</ul>'
		        	  +'</td>'
		        	  +'</tr>'
		        	  +'{{/each}}'
		        	  +'</tbody>'
		        	  +'</table>'
		        		var render = template.compile(templeteSource);
		        	    fileuploadHtml = render(data);
		          } else {
		        	    fileuploadHtml = template(settings.templeteId , data);
		          }
		          return fileuploadHtml;
		      };
		      
		      removeFile = function(li) {
		    	  var $li = li;

		          $li.off().find('.file-panel').off().end().remove();
		      };
		      
		      initImgViewer = function () {
		    	  
		    	  $('body').viewer('destroy');
				  $('body').viewer({zIndex:15001,url:"data"});
		      };
		      
		      return  {
		    	  initFileUpload : initFileUpload
		    	  
		      }
		  
});
