package com.dxr.comm.utils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.http.Consts;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dawnwing.framework.core.ServiceException;
import com.dawnwing.framework.utils.IoUtils;
import com.dawnwing.framework.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @description: <描述信息>
 * @author: w.xL
 * @date: 2018-3-1
 */
public class SimpleHttpClient {

    private static final Logger logger = LoggerFactory
            .getLogger(SimpleHttpClient.class);

    private static final String PROTOCOL_HTTP = "http";
    private static final String PROTOCOL_HTTPS = "https";
    private static SSLConnectionSocketFactory sslsf = null;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLContextBuilder builder = null;

    private static CloseableHttpClient httpClient = null;

    static {
        try {
            builder = new SSLContextBuilder();
            // 全部信任 不做身份鉴定
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates,
                        String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(),
                    new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2" },
                    null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder
                    .<ConnectionSocketFactory> create()
                    .register(PROTOCOL_HTTP, new PlainConnectionSocketFactory())
                    .register(PROTOCOL_HTTPS, sslsf).build();
            cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(200);//max connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     * @throws Exception
     */
    public static CloseableHttpClient getHttpClient() throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf).setConnectionManager(cm)
                .setConnectionManagerShared(true).build();
        return httpClient;
    }

    /**
     * httpClient post请求
     * @param url 请求url
     * @param header 头部信息
     * @param param 请求参数 form提交适用
     * @param jsonBody 请求实体body传参 json/xml提交适用
     * @return 可能为空 需要处理
     * @throws Exception
     *
     */
    public static String invokerPost(String url, Map<String, String> header,
            Map<String, Object> param, String jsonBody) throws Exception {
        String result = null;
        try {
            httpClient = getHttpClient();

            // jsonBody参数
            StringEntity entity = null;
            if (StringUtils.isNotEmpty(jsonBody)) {
                entity = new StringEntity(jsonBody, "utf-8");
                entity.setContentType("application/json");
            }

            HttpPost httpPost = methodPost(url, header, param, entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                analyzeHttpResponse(httpResponse);
            }
        } catch (Exception e) {
            logger.error("SimpleHttpClient.invokerPost() is error...\n url: "
                    + url, e);
            throw new ServiceException(
                    "SimpleHttpClient.invokerPost() is error...", e);
        } finally {
            IoUtils.unifyClose(httpClient);
        }
        return result;
    }

    /**
     * httpClient get请求
     * @param url 请求url
     * @param header 头部信息
     * @param param 请求参数 form提交适用
     * @return 可能为空 需要处理
     * @throws Exception
     *
     */
    public static String invokerGet(String url, Map<String, String> header,
            Map<String, Object> param) throws Exception {
        String result = null;
        try {
            HttpGet httpGet = methodGet(url, header, param);
            httpClient = getHttpClient();

            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                result = EntityUtils.toString(resEntity);
            } else {
                analyzeHttpResponse(httpResponse);
            }
        } catch (Exception e) {
            logger.error("SimpleHttpClient.invokerPost() is error...\n url: "
                    + url, e);
            throw new ServiceException(
                    "SimpleHttpClient.invokerPost() is error...", e);
        } finally {
            IoUtils.unifyClose(httpClient);
        }
        return result;
    }

    /**
     * @param url
     * @param header
     * @param param
     * @return
     * @throws ServiceException
     */
    public static HttpEntity getHttpEntity(String url,
            Map<String, String> header, Map<String, Object> param)
            throws ServiceException {
        HttpEntity entity = null;
        try {
            HttpGet httpGet = methodGet(url, null, param);
            httpClient = getHttpClient();
            HttpResponse respone = httpClient.execute(httpGet);
            if (respone.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            entity = respone.getEntity();
        } catch (Exception e) {
            logger.error("SimpleHttpClient.invokerPost() is error...\n url: "
                    + url, e);
            throw new ServiceException(
                    "SimpleHttpClient.getHttpEntity() is error...", e);
        } finally {
            IoUtils.unifyClose(httpClient);
        }
        return entity;
    }

    /**
     * 解析错误信息
     * @param httpResponse
     * @return
     * @throws ParseException
     * @throws IOException
     */
    private static void analyzeHttpResponse(HttpResponse httpResponse)
            throws ParseException, IOException {
        StringBuilder builder = new StringBuilder();
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        builder.append("status:" + httpResponse.getStatusLine());
        builder.append("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            builder.append("\t" + iterator.next());
        }
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            builder.append("response length:" + responseString.length());
            builder.append("response content:"
                    + responseString.replace("\r\n", ""));
        }
        // 输出错误信息
        logger.error(builder.toString());
    }

    /**
     * 获取HttpGet对象
     * @param url
     * @param header
     * @param urlparam
     * @return
     */
    private static HttpGet methodGet(String url, Map<String, String> header,
            Map<String, Object> urlparam) {
        // 设置url
        url = setQueryString(url, urlparam);
        HttpGet httpGet = new HttpGet(url);
        // 设置头信息
        setHeader(httpGet, header);
        writeLog(url);
        return httpGet;
    }

    /**
     * 获取HttpPost对象
     * @param url
     * @param header
     * @param param
     * @param entity
     * @return
     */
    private static HttpPost methodPost(String url, Map<String, String> header,
            Map<String, Object> param, HttpEntity entity) {
        HttpPost httpPost = new HttpPost(url);

        // 设置头信息
        setHeader(httpPost, header);

        // 设置请求参数
        if (MapUtils.isNotEmpty(param)) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                //给参数赋值
                formparams.add(new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString()));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
                    formparams, Consts.UTF_8);
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 设置实体 优先级高
        if (entity != null) {
            httpPost.setEntity(entity);
        }

        writeLog(url);
        return httpPost;
    }

    /**
     * 设置头信息
     * @param httpMethod
     * @param header
     */
    private static void setHeader(HttpRequestBase httpMethod,
            Map<String, String> header) {
        if (MapUtils.isNotEmpty(header)) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                httpMethod.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 设置get请求url后的参数
     * @param url
     * @param urlparam
     * @return
     */
    public static String setQueryString(String url, Map<String, Object> querymap) {
        StringBuilder sburl = new StringBuilder(url);
        if (MapUtils.isNotEmpty(querymap)) {
            // 设置是否第一个参数
            Boolean isFirst = false;
            if (sburl.indexOf("?") < 0) {
                sburl.append("?");
                isFirst = true;
            }

            for (Map.Entry<String, Object> entry : querymap.entrySet()) {
                if (!isFirst) {
                    sburl.append("&");
                }
                sburl.append(entry.getKey());
                sburl.append("=");
                sburl.append(entry.getValue());

                isFirst = false;
            }
        }
        return sburl.toString();
    }

    /**
     * @param url
     */
    private static void writeLog(String url) {
        logger.info("------------------------ invokerGet Url: -----------------------");
        logger.info(url);
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, Object> param = new LinkedHashMap<String, Object>();
        param.put("grant_type", "client_credential");
        param.put("appid", "wx810194b79bf319ab");
        param.put("secret", "fb5810f54a65da14d667d0995d17739b");
        try {
            String res = invokerGet(url, null, param);
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> linkedmap = mapper.readValue(res,
                    LinkedHashMap.class);
            for (Map.Entry<String, Object> entry : linkedmap.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
