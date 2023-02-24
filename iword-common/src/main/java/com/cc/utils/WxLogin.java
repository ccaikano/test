package com.cc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cc.exception.WxLoginException;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/12 14:21
 * 微信登录
 */
@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class WxLogin {
    private  String appid;
    private  String secret;
    private  String grant_type;
    private  String wxurl;


    public String getOpenId(String code) throws IOException, URISyntaxException {
        URL url = new URL(wxurl);
        URI uri = new URIBuilder().setScheme(url.getProtocol())
                .setHost(url.getHost())
                .setPort(url.getPort())
                .setPath(url.getPath())
                .setParameters(
                        new BasicNameValuePair("appid",appid),
                        new BasicNameValuePair("secret",secret),
                        new BasicNameValuePair("js_code",code),
                        new BasicNameValuePair("grant_type",grant_type)
                ).build();

        HttpGet httpGet = new HttpGet(uri);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)
                // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(true).build();

        httpGet.setConfig(requestConfig);

        //响应
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 从响应模型中获取响应实体
        String openId = null;
        try {
            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应状态为:" + response.getStatusLine());
            if (StringUtils.isNull(responseEntity)) {
                throw new WxLoginException("响应体为空");
            }
//            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//            System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            JSONObject json = JSON.parseObject(EntityUtils.toString(responseEntity));
            openId = json.getString("openid");
            if (StringUtils.isNull(openId)) {
                throw new WxLoginException("openid重复使用");
            }
        } finally {
            httpClient.close();
            response.close();
        }

        return openId;

    }
}
