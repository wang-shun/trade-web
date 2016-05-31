var items_per_page = 7;
		var scroll_in_progress = false;
		var myScroll;
		
		load_content = function(refresh, next_page) {
			if(refresh=='init'){
				postData.page=1;
				$("#wrapper > #scroller > ul").html('');
			}
			
			// This is a DEMO function which generates DEMO content into the scroller.
			// Here you should place your AJAX request to fetch the relevant content (e.g. $.post(...))
			$.ajax({
	               type: 'POST',
	               url: ctx+'/mobile/case/box/findPage',
	               data: postData,
	               success: function(data){
	            	   var html=template('castListTpl', data); 
	       				$("#e_count").text(data.records);
	       				$("#wrapper > #scroller > ul").append(html);
	       				postData.page+=1
	       				$(pullUpEl).hide();
	               },
	               dataType: 'json',
	               complete : function(){
	               }
	         });
			
			
				if (refresh&&refresh!='init') {
					
					myScroll.refresh();
					pullActionCallback();
					
				} else {
					
					if (myScroll) {
						myScroll.destroy();
						$(myScroll.scroller).attr('style', ''); // Required since the styles applied by IScroll might conflict with transitions of parent layers.
						myScroll = null;
					}
					trigger_myScroll();
					
				}
			
			
		};
		
		function pullDownAction() {
			load_content('refresh');
			//$('#wrapper > #scroller > ul').data('page', 1);
			
			// Since "topOffset" is not supported with iscroll-5
			//$('#wrapper > .scroller').css({top:0});
			
		}
		
		function pullActionCallback() {

		}
		function pullUpAction(callback) {
		
			load_content('refresh');
	
			
			if (callback) {
				callback();
			}
		}
		var pullActionDetect = {
			count:0,
			limit:10,
			check:function(count) {
				if (count) {
					pullActionDetect.count = 0;
				}
				// Detects whether the momentum has stopped, and if it has reached the end - 200px of the scroller - it trigger the pullUpAction
				setTimeout(function() {
					if(pullActionDetect.count < pullActionDetect.limit) {
						pullActionDetect.check();
						pullActionDetect.count++;
					}
				}, 200);
			}
		}
		
		function trigger_myScroll(offset) {
			pullUpEl = document.querySelector('#wrapper .pullUp');	
			if (pullUpEl) {
				pullUpOffset = pullUpEl.offsetHeight;
			} else {
				pullUpOffset = 0;
			}
			
			if ($('#wrapper ul > li').length < items_per_page) {
				// If we have only 1 page of result - we hide the pullup and pulldown indicators.
				$('#wrapper .pullDown').hide();
				$('#wrapper .pullUp span').hide();
				
				offset = 0;
			} else if (!offset) {
				// If we have more than 1 page of results and offset is not manually defined - we set it to be the pullUpOffset.
				offset = pullUpOffset;
			}
			
			myScroll = new IScroll('#wrapper', {
				probeType:1, tap:true, click:false, preventDefaultException:{tagName:/.*/}, mouseWheel:true, scrollbars:true, fadeScrollbars:true, interactiveScrollbars:false, keyBindings:false,
				deceleration:0.0002,
				startY:(parseInt(offset)*(-1))
			});
			
			myScroll.on('scrollStart', function () {
				scroll_in_progress = true;
			});
			myScroll.on('scroll', function () {
				
				scroll_in_progress = true;
				
				if (this.y <= 5 && pullUpEl && !pullUpEl.className.match('flip')) {
					pullUpEl.className = 'pullUp flip';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to refresh';
					$(pullUpEl).show();
				}
					
			
					
			});
			myScroll.on('scrollEnd', function () {
				console.log('scroll ended');
				setTimeout(function() {
					scroll_in_progress = false;
				}, 100);
				
					if (pullUpEl && pullUpEl.className.match('flip')) {
						pullUpEl.className = 'pullUp loading';
						pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Loading...';
						pullUpAction();
					}
					// We let the momentum scroll finish, and if reached the end - loading the next page
					//pullActionDetect.check(0);
			});
			
			// In order to prevent seeing the "pull down to refresh" before the iScoll is trigger - the wrapper is located at left:-9999px and returned to left:0 after the iScoll is initiated
			setTimeout(function() {
				$('#wrapper').css({left:0});
			}, 100);
		}
		
		function loaded(refresh) {
			postData.search_propertyAddr=$("#propertyAddr").val();
			load_content(refresh);
			
		}
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);