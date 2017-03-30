package com.centaline.trade.test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.centaline.trans.remote.Const;
import com.centaline.trans.remote.util.SignUtil;

import sun.misc.BASE64Encoder;

public class BASE64Tester {
	
	public static void  main(String[] args) throws UnsupportedEncodingException{
		Map<String, String> paramMap = new HashMap<String,String>();
		String files = "[{'type':'1','file_ids':[\n{'file_id':'8a8493d4543ddd6e01547fdb9b121dfa','file_type':'bmp','add_or_del':1}]},{'type':'2','file_ids':[{'file_id':'8a8493d5538c9ac801547fdb9f356e2b','file_type':'jpg','add_or_del':1},{'file_id':'8a8493d4538c988701547fdbbc85700f','file_type':'jpg','add_or_del':1},{'file_id':'8a8493d4538c988701547fdbd7037010','file_type':'jpg','add_or_del':1}]},{'type':'3','file_ids':[{'file_id':'8a8493d4543ddd6e01547fdbee9b1dfb','file_type':'jpg','add_or_del':1},{'file_id':'8a8493d5538c9ac801547fdc0ab06e2c','file_type':'jpg','add_or_del':1}]}]";
		paramMap.put("code", "110213-160504-007");
		paramMap.put("timestamp", String.valueOf(new Date().getTime()));
		paramMap.put("nonce", String.valueOf(new Random().nextInt(9000)+1000));
		paramMap.put("un", "huxinin");
		paramMap.put("case_id", "ZY-AJ-201604-1014");
		paramMap.put("files", new BASE64Encoder().encode(files.getBytes("UTF-8")).replace(System.getProperty("line.separator"), ""));
		
		String token = SignUtil.buildRequestToken(paramMap, Const.TOKEN);
		String url = "evaCode" +"/upload?token=" + token +"&"+ SignUtil.createLinkString(paramMap);
		System.out.println(url);
	}

}
