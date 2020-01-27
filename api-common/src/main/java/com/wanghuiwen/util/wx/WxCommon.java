package com.wanghuiwen.util.wx;


import com.wanghuiwen.util.JSONUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

//
public class WxCommon {

    static Logger logger  = LoggerFactory.getLogger("wx.util");

    public static Map<String,Object> getOpenId(String appid,String secret,String jscode){

        Map<String ,Object> res = new HashMap<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.

            URI uri = new URIBuilder("https://api.weixin.qq.com/sns/jscode2session")
                    .setParameter("appid", appid)
                    .setParameter("secret",secret)
                    .setParameter("js_code",jscode).build();

            HttpGet httpget = new HttpGet(uri);

            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    // 打印响应内容

                    res =  JSONUtils.json2map(EntityUtils.toString(entity));
                }
            } finally {
                response.close();
            }
        } catch (ParseException | IOException | URISyntaxException e) {
            logger.error("error",e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("error",e);
            }
        }
        return res;
    }
}
