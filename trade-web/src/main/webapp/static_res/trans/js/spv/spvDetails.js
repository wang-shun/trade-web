
$(document).ready(function(){

       	$('#loading-example-btn').click(function () {
            btn = $(this);
            simpleLoad(btn, true)
            simpleLoad(btn, false)
        });
    });

	$(".buyinfo, .sellinfo, .pledgeinfo").hide();
	$("#BuyRadio1").on("click", function() {
		$(".buyinfo").show();
	});
	$("#BuyRadio2").on("click", function() {
		$(".buyinfo").hide();
	});
	$("#SellRadio1").on("click", function() {
		$(".sellinfo").show();
	});
	$("#SellRadio2").on("click", function() {
		$(".sellinfo").hide();
	});
	$("#Pledge1").on("click", function() {
		$(".pledgeinfo").show();
	});
	$("#Pledge2").on("click", function() {
		$(".pledgeinfo").hide();
	});
	
	var sum = 1; //定义sum为全局变量
		function getAtr(i) {
		$str = '';
		$str += "<tr align='center'>";
		$str += "<td class='text-left'><select class='table-select'><option value=''>买方贷款审批完成</option></select></td>";
		$str += "<td class='text-left'><select class='table-select'><option value=''>资金方</option><option value=''>卖方</option></select></td>";
		
		$str += "<td><input class='table-input-one' type='text' placeholder='请输入金额'>元</td>";
		$str += "<td class='text-left' ><input class='table-input' type='text' placeholder='' /></td>";
		$str += "<td class='btn-height'><a href='javascript:void(0)'  onClick='getAtr(this)'>添加</a><a onClick='getDel(this)' class='grey' href='javascript:void(0)'>删除</a></td>";
		$str += "</tr>";
		$("#addTr").append($str);
		sum++;
		$("#sum").html(sum);
		}
		function getDel(k) {
		$(k).parents('tr').remove();
		sum--;
		$("#sum").html(sum);
	}

    function simpleLoad(btn, state) {
        if (state) {
            btn.children().addClass('fa-spin');
            btn.contents().last().replaceWith(" Loading");
        } else {
            setTimeout(function () {
                btn.children().removeClass('fa-spin');
                btn.contents().last().replaceWith(" Refresh");
            }, 2000);
        }
    }

    jQuery(function($) {
    $(document).ready( function() {
       $('.stickup-nav-bar').stickUp({
        // $('.col-lg-9').stickUp({
                            parts: {
                              0:'base_info',
                              1:'spvone_info',
                              2:'spvtwo_info',
                              3:'spvthree_info',
                              4:'spvfour_info',
                            },
                            itemClass: 'menuItem',
                            itemHover: 'active',
                            marginTop: 'auto'
                          });

       $('#phone').click(function(){
            toastr.info('Are you the 6 fingered man?');

       });
       
       $("#saveBtn").click(function(){	   
          debugger;
     	  var totalArr = [];
     	  $("form").each(function(){
     		 var obj = $(this).serializeArray();
     		totalArr.push(obj);
     	  });
     	  var newtotalArr = [];
     	  for(var i in totalArr){
     		for(var j in totalArr[i]){
     			newtotalArr.push(totalArr[i][j]);
     		}
     	  }
     	 console.info(JSON.stringify(newtotalArr));
     	  $.ajax({
       		url:ctx+"/spv/saveNewSpv",
       		method:"post",
       		dataType:"json",
       		data:newtotalArr,	        				        		    
       		success:function(data){
       			alert(data.message);
       			if(data.content != null && data.content != ""){
       				 window.location.href="${ctx}/spv/spvList";
       			}
       		}
     	 
        });
     });
       
       $("#submitBtn").click(function(){
    	  var totalArr = [];
    	  $("form").each(function(){
    		  totalArr.push($(this).serializeArray());
    	  });
    	  $.ajax({
      		url:ctx+"/spv/submitNewSpv",
      		method:"post",
      		dataType:"json",
      		data:totalArr,   		        				        		    
      		success:function(data){
      			alert(data.message);
      			if(data.content != null && data.content != ""){
      				 window.location.href="${ctx}/spv/spvList";
      			}
      		}  	 
       });
     });

       $('#chat-discussion').hide();

       $('#label7').click(function(){
            // alert(1);
            $('#base_info').hide();
            // alert(2);
            $('#chat-discussion').show();
       });

       $('#label4').click(function(){
            $('#base_info').show();
            $('#chat-discussion').hide();
       });

    });
});