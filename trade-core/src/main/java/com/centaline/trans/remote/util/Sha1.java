package com.centaline.trans.remote.util;


import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by mecal on 15/8/7.
 */
public class Sha1 {

    private static String DEFAULT_INPUT_CHARSET = "utf-8";

    public static String sign(String text, String key){
        return sign(text, key, DEFAULT_INPUT_CHARSET);
    }

    public static String sign(String text, String key, String input_charset){
        text += key;
        return DigestUtils.shaHex(getContentBytes(text, input_charset));
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws java.security.SignatureException
     * @throws java.io.UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

}
