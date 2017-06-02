//修复原生态toFixed()方法bug，如：0.85.toFixed() 返回 0.8
Number.prototype.toFixed = function( frac )  
{  
    //没有对fractionDigits做任何处理，假设它是合法输入  
    return (parseInt(this * Math.pow( 10, frac||0) + 0.5)/Math.pow(10,frac||0)).toString();  
}  