//修复原生态toFixed()方法bug，如：0.85.toFixed() 返回 0.8
Number.prototype.toFixed = function( fractionDigits )  
{  
	if(!fractionDigits) fractionDigits = 0;
    //没有对fractionDigits做任何处理，假设它是合法输入  
    return (parseInt(this * Math.pow( 10, fractionDigits  ) + 0.5)/Math.pow(10,fractionDigts)).toString();  
}  