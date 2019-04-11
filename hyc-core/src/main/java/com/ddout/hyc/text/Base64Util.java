package com.ddout.hyc.text;

import com.emeey.core.utils.StringKit;
import org.apache.commons.codec.binary.Base64;

/**
 * Base64工具-编码UTF8
 */
public class Base64Util {

    private static final Base64 base64 = new Base64();
    public static final String CONST_EMPTY_STRING = "";

    /**
     * 编码字符串
     * @param origin
     * @return
     */
    public static final String encode(String origin) {

        try {
            if (StringKit.isEmptyString(origin)) {
                return CONST_EMPTY_STRING;
            }
            return encode(origin.getBytes("utf-8"));
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 编码字符串
     * @param bytes
     * @return
     */
    public static final String encode(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return CONST_EMPTY_STRING;
        }
        try {
            return new String(base64.encode(bytes), "utf-8");
        } catch (Exception e) {
            return CONST_EMPTY_STRING;
        }
    }
    /**
     * 解码字符串
     * @param origin
     * @return
     */
    public static final String decode(String origin) {
        try {
            if (StringKit.isEmptyString(origin)) {
                return CONST_EMPTY_STRING;
            }
            return decode(origin.getBytes("utf-8"));
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 解码字符串
     * @param bytes
     * @return
     */
    public static final String decode(byte[] bytes) {
        try {
            byte[] decodeBytes = base64.decode(bytes);
            return new String(decodeBytes, "utf-8");
        } catch (Exception e) {
            return CONST_EMPTY_STRING;
        }
    }

}
