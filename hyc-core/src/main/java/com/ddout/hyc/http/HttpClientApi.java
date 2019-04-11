package com.ddout.hyc.http;

import com.alibaba.fastjson.JSONObject;
import com.ddout.hyc.collections.MapUtil;
import com.ddout.hyc.file.PropertiesUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http请求工具
 */
public class HttpClientApi {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientApi.class);
    //
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CHARSET_GBK = "GBK";
    //
    private static final String default_http_configure_alias = "default_http_configure";
    public static final ContentType CONTENTTYPE_UTF8 = ContentType.create("text/plain", Charset.forName("UTF-8"));
    //
    private static final PropertiesUtil configureUtil = PropertiesUtil.newInstence();
    //
    private static RequestConfig requestConfig;
    private static HttpClientBuilder httpClientbuilder;
    //

    static {
        //load configure file
        configureUtil.loadPropertiesFile(default_http_configure_alias, "default_http.properties");
        //
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时MaxTimeOut
        configBuilder.setConnectTimeout(configureUtil.getIntValue(default_http_configure_alias, "http.MaxConntctTimeOut", 3 * 60 * 1000));
        // 设置读取超时
        configBuilder.setSocketTimeout(configureUtil.getIntValue(default_http_configure_alias, "http.SocketTimeOut", 3 * 60 * 1000));
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(configureUtil.getIntValue(default_http_configure_alias, "http.ConnectionRequestTimeOut", 3 * 60 * 1000));
        // 在提交请求之前 测试连接是否可用
        // configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
        ///
        SSLContext sslcontext = null;
        // 设置协议http和https对应的处理socket链接工厂的对象
        try {
            sslcontext = SslUtils.createIgnoreVerifySSL();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext,
                        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        httpClientbuilder = HttpClients.custom().setConnectionManager(connManager);
        httpClientbuilder.setDefaultRequestConfig(requestConfig);
        ///
    }

    private static CloseableHttpClient getHttpClient() {
        //创建自定义的httpclient对象
        CloseableHttpClient client = httpClientbuilder.build();
        return client;
    }

    //
    //
    private static void addHeaders(Map<String, String> headers, HttpRequestBase httpRequestBase) {
        if (null != headers) {
            for (Entry<String, String> entry : headers.entrySet()) {
                httpRequestBase.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    //
    //
    private static HttpResponseObj execute(HttpRequestBase requestBase, String charset) {
        JSONObject logjson = new JSONObject();
        //
        requestBase.setConfig(requestConfig);
        //
        logjson.put("RequestData-ConnectionRequestTimeout", requestConfig.getConnectionRequestTimeout());
        logjson.put("RequestData-ConnectTimeout", requestConfig.getConnectTimeout());
        logjson.put("RequestData-SocketTimeout", requestConfig.getSocketTimeout());
        //
        HttpEntity entity = null;
        try {
            logjson.put("RequestData-Headers", requestBase.getAllHeaders());
            logjson.put("RequestData-ProtocolVersion", requestBase.getProtocolVersion());
            logjson.put("RequestData-URI", requestBase.getURI());
            logjson.put("RequestData-Method", requestBase.getMethod());
            //
            logger.debug(logjson.toJSONString());
            //
            HttpResponse response = getHttpClient().execute(requestBase);

            int statusCode = response.getStatusLine().getStatusCode();
            logjson.put("ResponseData-StatusCode", statusCode);
            logjson.put("ResponseData-Headers", response.getAllHeaders());
            //
            logger.debug(logjson.toJSONString());
            //
            String content = "";
            entity = response.getEntity();
            if (null != entity) {
                logjson.put("ResponseData-Entity-ContentLength", entity.getContentLength());
                logjson.put("ResponseData-Entity-ContentEncoding", entity.getContentEncoding());
                logjson.put("ResponseData-Entity-ContentType", entity.getContentType());
                String cs = CHARSET_UTF8;
                if (null != charset && !"".equals(charset)) {
                    cs = charset;
                }
                content = EntityUtils.toString(entity, cs);
                logger.debug("ResponseData-Entity-Content:" + content);
                logger.debug(logjson.toJSONString());
            }
            return new HttpResponseObj(statusCode, content, requestBase.getAllHeaders(), response.getAllHeaders(),logjson);
        } catch (Exception e) {
            logger.info(logjson.toJSONString(), e);
            throw new RuntimeException(e);
        } finally {
            if (null != entity) {
                try {
                    EntityUtils.consume(entity);
                } catch (Exception e) {
                    logger.info(logjson.toJSONString());
                    throw new RuntimeException(logjson.toString(), e);
                }
            }
        }

    }
    //
    //


    /**
     * 发送 GET 请求
     *
     * @param url     地址
     * @param params  参数
     * @param charset 编码
     * @param headers 需要传输的headers
     * @return
     */
    public static HttpResponseObj doGet(String url, String params, String charset, Map<String, String> headers) {
        String apiUrl = url;
        if (!"".equals(params)) {
            if (url.indexOf("?") == -1) {
                apiUrl += "?" + params;
            } else {
                if (params.startsWith("&")) {
                    apiUrl += params;
                } else {
                    apiUrl += "&" + params;
                }
            }
        }
        //
        HttpGet httpGet = new HttpGet(apiUrl);
        addHeaders(headers, httpGet);
        return execute(httpGet, charset);
    }


    ////---------------////---------------////---------------////---------------////---------------


    /**
     * post 请求
     *
     * @param apiUrl  地址
     * @param params  参数
     * @param charset 编码
     * @param headers 需要传输的headers
     * @return
     */
    public static HttpResponseObj doPost(String apiUrl, Map<String, Object> params, String charset, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(apiUrl);
        addHeaders(headers, httpPost);
        //
        List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
        for (Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
            pairList.add(pair);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(charset)));
        //
        return execute(httpPost, charset);
    }

    /**
     * post 请求
     *
     * @param apiUrl  地址
     * @param params  参数
     * @param charset 编码
     * @param headers 需要传输的headers
     * @return
     */
    public static HttpResponseObj doPost(String apiUrl, String params, String charset, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(apiUrl);
        addHeaders(headers, httpPost);
        //
        StringEntity stringEntity = new StringEntity(params, charset);
        stringEntity.setContentEncoding(charset);
        httpPost.setEntity(stringEntity);
        //
        return execute(httpPost, charset);
    }

    //-----------------------------

    /**
     * 发起带有附件的请求
     *
     * @param apiUrl      地址
     * @param param       参数
     * @param files       文件
     * @param charset     编码
     * @param contentType 参数请求类型
     * @param headers     headers
     * @return
     */
    public static HttpResponseObj doPostMedias(String apiUrl, Map<String, Object> param, File[] files, String charset, ContentType contentType, Map<String, String> headers) {
        //
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        if (null != files && files.length > 0) {
            for (File file : files) {
                entityBuilder.addPart(file.getName(), new FileBody(file, ContentType.MULTIPART_FORM_DATA, file.getName()));
            }
        }
        if (null != param && param.size() > 0) {
            for (Entry<String, Object> entry : param.entrySet()) {
                if (null != entry.getValue()) {
                    entityBuilder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
                } else {
                    entityBuilder.addTextBody(entry.getKey(), "", contentType);
                }
            }
        }
        HttpPost httpPost = new HttpPost(apiUrl);
        addHeaders(headers, httpPost);
        HttpEntity requestEntity = entityBuilder.build();
        httpPost.setEntity(requestEntity);
        return execute(httpPost, charset);
    }


    /**
     * 发起带有附件的请求(附件指定文件名)
     *
     * @param apiUrl
     * @param param
     * @param files
     * @param contentType
     * @return
     */
    public static HttpResponseObj doPostMedias(String apiUrl, Map<String, Object> param, List<Map<String, Object>> files, String charset, ContentType contentType, Map<String, String> headers) {
        //
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        if (null != files && files.size() > 0) {
            for (Map<String, Object> fileMap : files) {
                Object fileObj = fileMap.get("file");
                if (fileObj instanceof File) {
                    String fileName = MapUtil.getString(fileMap, "fileName").trim();
                    File file = (File) fileObj;
                    if (!"".equals(fileName)) {
                        entityBuilder.addPart(file.getName(), new FileBody(file, ContentType.MULTIPART_FORM_DATA, fileName));
                    } else {
                        entityBuilder.addPart(file.getName(),
                                new FileBody(file, ContentType.MULTIPART_FORM_DATA, file.getName()));
                    }
                }
            }
        }
        if (null != param && param.size() > 0) {
            for (Entry<String, Object> entry : param.entrySet()) {
                if (null != entry.getValue()) {
                    entityBuilder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
                } else {
                    entityBuilder.addTextBody(entry.getKey(), "", contentType);
                }
            }
        }
        HttpPost httpPost = new HttpPost(apiUrl);
        addHeaders(headers, httpPost);
        HttpEntity requestEntity = entityBuilder.build();
        httpPost.setEntity(requestEntity);
        return execute(httpPost, charset);
    }

}
