package com.centaline.trans.cases.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value="/api/imageshow")
public class ImageShowController {
	
	@RequestMapping(value="imgShow")
	public String imgShow(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8"); // 防止弹出的信息出现乱码  
        response.setHeader("Cache-Control", "no-cache");
		
		String img=request.getParameter("img");
		img=URLDecoder.decode(img, "UTF-8");
		request.setAttribute("img", img);
		return "case/imageshow";
	}
	
}
