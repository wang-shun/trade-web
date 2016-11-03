package com.centaline.trans.remote.util;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by mecal on 15/8/7.
 */
public class SignUtil {

    /**
     * 服务用户码 自定义头名称
     */
    public static final String USER_KEY_HEADER_NAME = "vc-user-key";
    private static final String USER_SECRET_NAME = "vc-user-secret";    //用户服务密钥

    public static final String TIMESTAMP_NAME = "timestamp";    //时间戳
    public static final String NONCE_PARAM_NAME = "nonce";      //随机数
    public static final String TOKEN_KEY = "token"; //sha1加签值存放参数名，用于校验合法性

    public final static String FILESTR = "Ljava.io.File";   //上传文件的文件类型名字

    /**
     * 根据参数列表生成sha1验签值
     * @param paramMap 加签验签参数map
     * @param userSecret    用户密钥
     * @return
     */
    public static String buildRequestToken(Map<String, String> paramMap, String userSecret) {
        String preStr = createLinkString(paramMap);
        String token = Sha1.sign(preStr, userSecret);

        System.out.println("REQUEST STR: " + preStr + ", \nREQUEST TOKEN: " + token);
        return token;
    }

    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());

        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr += key + "=" + value;
            } else {
                prestr += key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 待检查参数map
     * @return
     */
    public static Map<String, String> pathFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String obj = sArray.get(key);

            if (StringUtils.trimToNull(obj) == null
                    || obj.toString().contains(FILESTR)  //上传文件参数不做加签
                    || key.equalsIgnoreCase("token")) {
                continue;
            }

            result.put(key, obj);
        }

        return result;
    }

}
