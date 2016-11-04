package com.aist.uam.admin.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/** 
 * 获取所有Endpoint
 *
 * @author linjiarong
 * @date 2014年12月2日 下午2:31:23 
 * @version 0.0.1
 *  
 */
@RequestMapping("/admin")
@Controller
public class SpringMVCController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@RequestMapping(value = "endPoints", method = RequestMethod.GET )
	public String getEndPointsInView( Model model )
	{
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		for(RequestMappingInfo rmi : map.keySet()){
			logger.debug(rmi.toString());
			logger.debug(rmi.getPatternsCondition().toString().substring(1, rmi.getPatternsCondition().toString().length()-1));
			HandlerMethod hm = map.get(rmi);
			logger.debug(hm.toString());
			for(MethodParameter mp : hm.getMethodParameters()){
				logger.debug(mp.getParameterName());
			}
		}
	    model.addAttribute( "endPoints", requestMappingHandlerMapping.getHandlerMethods().keySet() );
	    return "uam/admin/endPoints";
	}
}
