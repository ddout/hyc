package com.ddout.hyc.sleuth;

import brave.propagation.ExtraFieldPropagation;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于sleuth中追踪调用链信息的辅助bena
 */
@Component
public class SessionPropagationBean {

    public static final String LEGACY_TRACE_ID_NAME = "X-B3-TraceId";
    public static final String LEGACY_PARENT_ID_NAME = "X-B3-ParentSpanId";
    public static final String LEGACY_SPAN_ID_NAME = "X-B3-SpanId";
    public static final String LEGACY_EXPORTABLE_NAME = "X-Span-Export";

    /**
     * 获取当前范围所有追踪链-数据集合
     *
     * @return
     */
    public Map<String, String> getAll() {
        //
        Map<String, String> traceMap = new HashMap<String, String>();
        //扩展数据
        traceMap.putAll(getExtraAll());
        //Sleuth数据
        traceMap.putAll(getSleuthAll());
        //
        return traceMap;
    }

    /**
     * 获取扩展属性值
     *
     * @param key
     * @return
     */
    public String getExtraFeild(String key) {
        return ExtraFieldPropagation.get(key);
    }

    /**
     * 获取全部扩展属性
     * @return
     */
    public Map<String,String> getExtraAll() {
        return ExtraFieldPropagation.getAll();
    }

    /**
     * 获取Sleuth的追踪属性
     * @return
     */
    public static Map<String, String> getSleuthAll() {
//        final String previousTraceId = MDC.get("traceId");
//        final String previousParentId = MDC.get("parentId");
//        final String previousSpanId = MDC.get("spanId");
//        final String spanExportable = MDC.get("spanExportable");
        final String legacyPreviousTraceId = MDC.get(LEGACY_TRACE_ID_NAME);
        final String legacyPreviousParentId = MDC.get(LEGACY_PARENT_ID_NAME);
        final String legacyPreviousSpanId = MDC.get(LEGACY_SPAN_ID_NAME);
        final String legacySpanExportable = MDC.get(LEGACY_EXPORTABLE_NAME);

        return new HashMap<String, String>() {{
            //
            put(LEGACY_TRACE_ID_NAME, legacyPreviousTraceId);
            put(LEGACY_PARENT_ID_NAME, legacyPreviousParentId);
            put(LEGACY_SPAN_ID_NAME, legacyPreviousSpanId);
            put(LEGACY_EXPORTABLE_NAME, legacySpanExportable);
            //
        }};
    }

    public static String getTraceId() {
        return MDC.get(LEGACY_TRACE_ID_NAME);
    }

    public static String getParentId() {
        return MDC.get(LEGACY_PARENT_ID_NAME);
    }

    public static String getSpanId() {
        return MDC.get(LEGACY_SPAN_ID_NAME);
    }

    public static String getExportable() {
        return MDC.get(LEGACY_EXPORTABLE_NAME);
    }


}
