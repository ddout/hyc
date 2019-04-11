package com.ddout.hyc.text.security;

import com.ddout.hyc.text.MD5;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;


/**
 * 加解密工具类
 */
public class EncryptionUtils {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionUtils.class);

    /**
     * 数据加密算法
     *
     * @param strAEC AEC加密串
     * @param data   数据
     * @return
     */
    public static String encodeDATA(String strAEC, String data) {
        if (null == strAEC || strAEC.length() != 32) {
            throw new IllegalAccessError("strAEC String is error!");
        }
        String base64 = "";
        try {
            base64 = new String(Base64.encodeBase64(data.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //
        String result1 = base64 + strAEC;
        String md5 = MD5.MD5Encode(result1);
        //
        String result2 = md5 + result1;
        try {
            base64 = new String(Base64.encodeBase64(result2.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        int len = base64.length();
        String code1 = base64.substring(0, len / 2);
        String code2 = base64.substring(len / 2);
        try {
            base64 = new String(Base64.encodeBase64(("[" + len + "]" + code2 + code1).getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return base64;
    }

    /**
     * 数据解密算法
     *
     * @param strAEC AEC加密串
     * @param data   数据
     * @return
     */
    public static String decodeDATA(String strAEC, String data) {
        if (null == strAEC || strAEC.length() != 32) {
            throw new IllegalAccessError("strAEC String is error!");
        }
        String code = "";
        int len = 0;
        try {
            code = new String(Base64.decodeBase64(data.getBytes()), "UTF-8");
            len = Integer.parseInt(code.substring(code.indexOf("[") + 1, code.indexOf("]")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        code = code.substring(code.indexOf("]") + 1);
        String code1 = code.substring(0, code.length() - len / 2);
        String code2 = code.substring(code.length() - len / 2);
        try {
            code = new String(Base64.decodeBase64((code2 + code1).getBytes()), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String MD5_from = code.substring(0, 32);
        code = code.substring(32);
        code = code.substring(0, code.length() - strAEC.length());
        try {
            code = new String(Base64.decodeBase64((code).getBytes()), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //
        String code_self = "";
        try {
            code_self = new String(Base64.encodeBase64(code.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String result1 = code_self + strAEC;
        String MD5_self = MD5.MD5Encode(result1);
        if (!MD5_from.equals(MD5_self)) {
            throw new RuntimeException("非法加密数据");
        }
        return code;
    }

    /**
     * 单向加密生成算法
     *
     * @param timestamp 时间戳
     * @param randowStr 随机字串32位
     * @return
     */
    public static String encodeAEC(long timestamp, String randowStr) {
        if (null == randowStr || randowStr.length() != 32) {
            throw new IllegalAccessError("randow String is error!");
        }
        String[] strArr = new String[6];
        strArr[0] = randowStr.substring(0, 2);
        strArr[1] = randowStr.substring(4, 8);
        strArr[2] = randowStr.substring(5, 9);
        strArr[3] = randowStr.substring(14, 20);
        strArr[4] = randowStr.substring(30, 32);
        strArr[5] = randowStr.substring(7, 17);
        Arrays.sort(strArr);
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            String md5 = MD5.MD5Encode(timestamp + str);
            sb.append(md5).append(str);
        }
        String md5AEC = MD5.MD5Encode(sb.toString());
        return md5AEC;
    }

    /**
     * 获取随机字符串
     *
     * @param count 指定长度
     * @return
     */
    public static String getRandomStr(int count) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /*
     * int转byte数组,高位在前
     */
    public static byte[] int2Bytes(int count) {
        byte[] byteArr = new byte[4];
        byteArr[3] = (byte) (count & 0xFF);
        byteArr[2] = (byte) (count >> 8 & 0xFF);
        byteArr[1] = (byte) (count >> 16 & 0xFF);
        byteArr[0] = (byte) (count >> 24 & 0xFF);
        return byteArr;
    }

    /**
     * 高位在前bytes数组转int
     *
     * @param byteArr
     * @return
     */
    public static int bytes2int(byte[] byteArr) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            count <<= 8;
            count |= byteArr[i] & 0xff;
        }
        return count;
    }
}
