<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div style="display:none;" id="excelImport">
   <form id="excelInForm"  method="post" enctype="multipart/form-data" action="" class='form-horizontal'> 

   		<div class='row'>
        
        	 <div class="switch col-md-2" data-on-label="上月" data-off-label="当月">
	    	<input id="moSwitch" type="checkbox"  />
	    	</div>
        	<label  class="col-md-3 control-label"  >导入表格文件 : </label>
  			<div class="col-md-7">
  			    <input id="file"  class="btn btn-default"  type="file" name="fileupload"  />
      		</div>

        </div>
  </form>
</div>

<script>
function checkFileTypeExcel()
{
	var obj = $("#file")[0];
    var ErrMsg="";
    if(obj.value=="") {
    	ErrMsg="请选择要导入的Excel表格";
    	layer.alert(ErrMsg, {
    		  icon: 2
        }) 
    	return false;
    }
    var FileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
    var AllowExt=".xls .csv .xlsx .ods";
    //判断文件类型是否允许上传
    if(AllowExt!=0 && AllowExt.indexOf(FileExt)==-1) {
        ErrMsg="\n该文件类型不允许上传。请上传 "+AllowExt+" 类型的文件，当前文件类型为"+FileExt;
        //alert(ErrMsg);
    	layer.alert(ErrMsg, {
  		  icon: 2
  		}) 
        return false;
  	}

	return true;
}
</script>