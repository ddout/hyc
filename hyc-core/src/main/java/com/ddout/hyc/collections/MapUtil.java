package com.ddout.hyc.collections;


import com.ddout.hyc.text.DateFmtUtil;
import org.apache.commons.collections4.MapUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * map中获取值工具
 */
public class MapUtil {

    /**
     * 从map中获取List<String>
     *
     * @param map
     * @param key
     * @return
     */
    public static final List<String> getStringList4Map(Map<String, Object> map, String key) {
        List<String> value = new ArrayList<String>();
        if (null == map) {
            return value;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return value;
        } else if (null != obj && obj instanceof List) {
            value = (List<String>) obj;
        } else {
            return value;
        }
        return value;
    }


    /**
     * 从map中获取Map<String,Object>
     *
     * @param map
     * @param key
     * @return
     */
    public static final Map<String, Object> getMap4Map(Map<String, Object> map, String key) {
        Map<String, Object> value = new HashMap<String, Object>();
        if (null == map) {
            return value;
        }
        Object obj = map.get(key);
        if (null == obj) {
            return value;
        } else if (null != obj && obj instanceof Map) {
            value = (Map<String, Object>) obj;
        } else {
            return value;
        }
        return value;
    }

    /**
     * 从map中获取String
     *
     * @param map
     * @param key
     * @return
     */
    public static final String getString(Map<String, Object> map, String key) {
        return MapUtils.getString(map, key, "");
    }

    /**
     * 从map中获取String
     *
     * @param map
     * @param key
     * @return
     */
    public static final String getString(Map<String, Object> map, String key, String defaultValue) {
        return MapUtils.getString(map, key, defaultValue);
    }

    /**
     * 从map中获取String
     *
     * @param map
     * @param key
     * @return
     */
    public static final String getStringNoBlank(Map<String, Object> map, String key) {
        return replaceBlank(getString(map, key));
    }

    /**
     * 从map中获取String
     *
     * @param map
     * @param key
     * @return
     */
    public static final String getStringNoBlank(Map<String, Object> map, String key, String defaultValue) {
        return replaceBlank(getString(map, key, defaultValue));
    }

    /**
     * 从map中获取int
     *
     * @param map
     * @param key
     * @return
     */
    public static final int getIntVal(Map<String, Object> map, String key) {
        return MapUtils.getIntValue(map, key, 0);
    }

    /**
     * 从map中获取int
     *
     * @param map
     * @param key
     * @return
     */
    public static final int getIntVal(Map<String, Object> map, String key, int defaultValue) {
        return MapUtils.getIntValue(map, key, defaultValue);
    }

    /**
     * 从map中获取Integer
     *
     * @param map
     * @param key
     * @return
     */
    public static final Integer getInt(Map<String, Object> map, String key, int defaultValue) {
        return MapUtils.getInteger(map, key, defaultValue);
    }

    /**
     * 从map中获取Integer
     *
     * @param map
     * @param key
     * @return
     */
    public static final Integer getInt(Map<String, Object> map, String key) {
        return MapUtils.getInteger(map, key);
    }


    /**
     * 从map中获取long
     *
     * @param map
     * @param key
     * @return
     */
    public static final long getLongVal(Map<String, Object> map, String key) {
        return MapUtils.getLongValue(map, key, 0L);
    }

    /**
     * 从map中获取long
     *
     * @param map
     * @param key
     * @return
     */
    public static final long getLongVal(Map<String, Object> map, String key, long defaultValue) {
        return MapUtils.getLongValue(map, key, defaultValue);
    }

    /**
     * 从map中获取long
     *
     * @param map
     * @param key
     * @return
     */
    public static final Long getLong(Map<String, Object> map, String key) {
        return MapUtils.getLong(map, key);
    }

    /**
     * 从map中获取long
     *
     * @param map
     * @param key
     * @return
     */
    public static final Long getLong(Map<String, Object> map, String key, long defaultValue) {
        return MapUtils.getLong(map, key, defaultValue);
    }

    /**
     * 从map中获取Double
     *
     * @param map
     * @param key
     * @return
     */
    public static final double getDoubleVal(Map<String, Object> map, String key) {
        return MapUtils.getDoubleValue(map, key, 0.0D);
    }

    /**
     * 从map中获取Double
     *
     * @param map
     * @param key
     * @return
     */
    public static final double getDoubleVal(Map<String, Object> map, String key, double defaultValue) {
        return MapUtils.getDoubleValue(map, key, defaultValue);
    }

    /**
     * 从map中获取Double
     *
     * @param map
     * @param key
     * @return
     */
    public static final Double getDouble(Map<String, Object> map, String key) {
        return MapUtils.getDouble(map, key);
    }

    /**
     * 从map中获取Double
     *
     * @param map
     * @param key
     * @return
     */
    public static final Double getDouble(Map<String, Object> map, String key, double defaultValue) {
        return MapUtils.getDouble(map, key, defaultValue);
    }

    /**
     * 从map中获取Date
     *
     * @param map
     * @param key
     * @return
     */
    public static final Date getDate(Map<String, Object> map, String key) {
        Object obj = MapUtils.getObject(map, key, "");
        if ("".equals(obj)) {
            return null;
        }
        if (obj instanceof Date) {
            return (Date) obj;
        } else {
            return DateFmtUtil.parse(obj.toString());
        }
    }

    /**
     * 从map中获取Date
     *
     * @param map
     * @param key
     * @return
     */
    public static final Date getDate(Map<String, Object> map, String key, String fmt) {
        Object obj = MapUtils.getObject(map, key, "");
        if ("".equals(obj)) {
            return null;
        }
        if (obj instanceof Date) {
            return (Date) obj;
        } else {
            return DateFmtUtil.parseDate(obj.toString(), fmt);
        }
    }

    /**
     * 将null转换为字符串""
     *
     * @param map
     * @return
     */
    public static final Map<String, Object> conversNullVal2String(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (Entry<String, Object> entry : map.entrySet()) {
            result.put(entry.getKey(), (null == entry.getValue() || "NULL".equals(entry.getValue().toString().toUpperCase()) ? "" : entry.getValue()));
        }
        return result;
    }

    /**
     * 将null转换为字符串""
     *
     * @return
     */
    public static final List<Map<String, Object>> conversNullVal2String(List<Map<String, Object>> list) {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        for (Map<String, Object> map : list) {
            Map<String, Object> mapNull = conversNullVal2String(map);
            result.add(mapNull);
        }

        return result;
    }

    /**
     * 字符串完整去空白字符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n|");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest.trim();
    }
}
