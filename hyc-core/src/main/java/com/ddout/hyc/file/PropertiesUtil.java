package com.ddout.hyc.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 配置文件工具(单例工厂)
 */
public class PropertiesUtil {
    private static final String DEFAULT_PROP_ALIAS = "DEFAULT_PROP_ALIAS";
    private static final PropertiesUtil PROP_UTIL = new PropertiesUtil();
    private final Map<String, Map<String, String>> propsCache = new HashMap<String, Map<String, String>>();

    private PropertiesUtil() {
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static PropertiesUtil newInstence() {
        return PROP_UTIL;
    }


    public String getString(String key) {
        return getString(DEFAULT_PROP_ALIAS, key);
    }

    public String getStringValue(String key, String defaultVal) {
        return getStringValue(DEFAULT_PROP_ALIAS, key, defaultVal);
    }

    public String getString(String alias, String key) {
        Object value = this.getObj(alias, key);
        if (null != value) {
            return value.toString().trim();
        } else {
            return null;
        }
    }

    public String getStringValue(String alias, String key, String defaultVal) {
        Object value = this.getObjValue(alias, key, defaultVal);
        if (null != value) {
            return value.toString().trim();
        } else {
            return defaultVal;
        }
    }


    public Integer getInt(String key) {
        return getInt(DEFAULT_PROP_ALIAS, key);
    }

    public Integer getIntValue(String key, int defaultVal) {
        return getIntValue(DEFAULT_PROP_ALIAS, key, defaultVal);
    }

    public Integer getInt(String alias, String key) {
        Object value = this.getObj(alias, key);
        if (null != value) {
            return Integer.parseInt(value.toString().trim());
        } else {
            return null;
        }
    }

    public Integer getIntValue(String alias, String key, int defaultVal) {
        Object value = this.getObjValue(alias, key, defaultVal);
        if (null != value) {
            return Integer.parseInt(value.toString().trim());
        } else {
            return defaultVal;
        }
    }


    public Long getLong(String key) {
        return getLong(DEFAULT_PROP_ALIAS, key);
    }

    public Long getLongValue(String key, long defaultVal) {
        return getLongValue(DEFAULT_PROP_ALIAS, key, defaultVal);
    }

    public Long getLong(String alias, String key) {
        Object value = this.getObj(alias, key);
        if (null != value) {
            return Long.parseLong(value.toString().trim());
        } else {
            return null;
        }
    }

    public Long getLongValue(String alias, String key, long defaultVal) {
        Object value = this.getObjValue(alias, key, defaultVal);
        if (null != value) {
            return Long.parseLong(value.toString().trim());
        } else {
            return defaultVal;
        }
    }


    public Double getDouble(String key) {
        return getDouble(DEFAULT_PROP_ALIAS, key);
    }

    public Double getDoubleValue(String key, double defaultVal) {
        return getDoubleValue(DEFAULT_PROP_ALIAS, key, defaultVal);
    }

    public Double getDouble(String alias, String key) {
        Object value = this.getObj(alias, key);
        if (null != value) {
            return Double.parseDouble(value.toString().trim());
        } else {
            return null;
        }
    }

    public Double getDoubleValue(String alias, String key, double defaultVal) {
        Object value = this.getObjValue(alias, key, defaultVal);
        if (null != value) {
            return Double.parseDouble(value.toString().trim());
        } else {
            return defaultVal;
        }
    }


    public Boolean getBoolean(String key) {
        return getBoolean(DEFAULT_PROP_ALIAS, key);
    }

    public Boolean getBooleanValue(String key, boolean defaultVal) {
        return getBooleanValue(DEFAULT_PROP_ALIAS, key, defaultVal);
    }

    public Boolean getBoolean(String alias, String key) {
        Object value = this.getObj(alias, key);
        if (null != value) {
            return Boolean.parseBoolean(value.toString().trim());
        } else {
            return null;
        }
    }

    public Boolean getBooleanValue(String alias, String key, boolean defaultVal) {
        Object value = this.getObjValue(alias, key, defaultVal);
        if (null != value) {
            return Boolean.parseBoolean(value.toString().trim());
        } else {
            return defaultVal;
        }
    }


    /**
     * 获取默认别名的值
     *
     * @param key 键
     * @return 值，如果没有获取到则返回null
     */
    public Object getObj(String key) {
        return getObjValue(DEFAULT_PROP_ALIAS, key, null);
    }

    /**
     * 获取默认别名的值
     *
     * @param key 键
     * @return 值，如果没有获取到则返回null
     */
    public Object getObjValue(String key, Object defaultVal) {
        return getObjValue(DEFAULT_PROP_ALIAS, key, defaultVal);
    }

    /**
     * 获取值
     *
     * @param alias 配置文件别名
     * @param key   键
     * @return 值，如果没有获取到则返回null
     */
    public Object getObj(String alias, String key) {
        return getObjValue(alias, key, null);
    }

    /**
     * 获取值
     *
     * @param alias      配置文件别名
     * @param key        键
     * @param defaultVal 默认值
     * @return 值，如果没有获取到则返回默认值
     */
    public Object getObjValue(String alias, String key, Object defaultVal) {
        if (!this.propsCache.containsKey(alias)) {
            return defaultVal;
        }
        Map<String, String> valueCache = this.propsCache.get(alias);
        if (!valueCache.containsKey(key)) {
            return defaultVal;
        }
        Object value = valueCache.get(key);
        if (null == value) {
            return defaultVal;
        }
        return value;
    }


    /**
     * 批量加载配置文件（使用默认别名）
     *
     * @param abstractPaths List<String>
     */
    public void loadPropertiesFiles(List<String> abstractPaths) {
        for (String abstractPath : abstractPaths) {
            loadPropertiesFile(DEFAULT_PROP_ALIAS, abstractPath);
        }
    }

    /**
     * 批量加载配置文件
     *
     * @param abstractPaths Map<String,String> key=alias,value=abstractPath
     */
    public void loadPropertiesFiles(Map<String, String> abstractPaths) {
        for (Entry<String, String> abstractPath : abstractPaths.entrySet()) {
            loadPropertiesFile(abstractPath.getKey(), abstractPath.getValue());
        }
    }

    /**
     * 加载配置文件（使用默认别名）
     *
     * @param abstractPath 相对于classpath，配置文件的相对路径
     */
    public void loadPropertiesFile(String abstractPath) {
        loadPropertiesFile(DEFAULT_PROP_ALIAS, abstractPath);
    }

    /**
     * 加载配置文件
     *
     * @param alias        别名，用于对不同的配置文件作用域区分
     * @param abstractPath 相对于classpath，配置文件的相对路径
     */
    public void loadPropertiesFile(String alias, String abstractPath) {
        if (null == abstractPath || "".equals(abstractPath = abstractPath.trim())) {
            throw new IllegalArgumentException("abstractPath is not null");
        }
        if (null == alias || "".equals(alias = alias.trim())) {
            throw new IllegalArgumentException("alias is not null");
        }
        Map<String, String> valueCache = null;
        if (propsCache.containsKey(alias)) {
            valueCache = propsCache.get(alias);
        } else {
            valueCache = new HashMap<String, String>();
            propsCache.put(alias, valueCache);
        }
        synchronized (PROP_UTIL) {
            InputStream in = null;
            try {
                in = this.getClass().getClassLoader().getResourceAsStream(abstractPath);
                Properties prop = new Properties();
                prop.load(in);
                for (Entry entry : prop.entrySet()) {
                    String key = entry.getKey().toString();
                    String value = "";
                    Object obj = entry.getValue();
                    if (null != obj) {
                        value = obj.toString().trim();
                    }
                    valueCache.put(key, value);
                }
            } catch (IOException e) {
                throw new RuntimeException("abstractPath[" + abstractPath + "] properties file can not load!", e);
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }

        }

    }

}
