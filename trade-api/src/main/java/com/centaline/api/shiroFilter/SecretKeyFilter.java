package com.centaline.api.shiroFilter;

import com.alibaba.fastjson.JSON;
import com.centaline.api.common.vo.CcaiServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *
 * 密钥校验权限Filter
 * @author yinchao
 * @date 2017/9/28
 */
public class SecretKeyFilter extends AccessControlFilter {

	private static String secretKeyHeaderName = "api_key";

	/**
	 * 定义是否允许访问
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return true允许访问 false不允许访问
	 * @throws Exception
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		HttpServletRequest req = WebUtils.toHttp(request);
		if(req!=null){
			String securitykey = req.getHeader(secretKeyHeaderName);
			//TODO 实现密钥验证逻辑 该处只是添加了swagger的权限设置
			if(StringUtils.isNotBlank(securitykey)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	/**
	 * 访问拒绝时的处理
	 * @param request
	 * @param response
	 * @return true需要继续处理，false已处理完成
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse resp = WebUtils.toHttp(response);
		resp.setStatus(200);
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json; charset=utf-8");
		CcaiServiceResult result = new CcaiServiceResult();
		result.setSuccess(false);
		result.setCode("90");
		result.setMessage("密钥未授权，请检查!");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(JSON.toJSONString(result));
		}finally {
			if(writer!=null){
				writer.close();
			}
		}
		return false;
	}

	public void setSecretKeyHeaderName(String secretKeyHeaderName) {
		this.secretKeyHeaderName = secretKeyHeaderName;
	}
}
