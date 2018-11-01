package com.interest.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author xyl
 * @Create 2018-11-01 18:45
 * @Desc 写点注释吧
 **/
public class HttpClientUtil {

    public static String doPost(String url, Map<String, String> map, String charset) {
        // 定义一个可关闭的httpClient的对象
        CloseableHttpClient httpClient = null;

        // 定义httpPost对象
        HttpPost post = null;

        // 返回结果
        String result = null;

        try {
            // 1.创建httpClient的默认实例
            httpClient = HttpClients.createDefault();
            // 2.提交post
            post = new HttpPost(url);
            // 3.设置参数
            List<NameValuePair> list = new ArrayList<>();
            // 4.迭代参数
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                // 获得参数
                Map.Entry<String, String> element = (Map.Entry<String, String>) iterator.next();
                // 通过BasicNameValuePair(key,vlaue)填充参数，并放到集合
                list.add(new BasicNameValuePair(element.getKey(), element.getValue()));
            }

            // 5.编码
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                post.setEntity(entity);
            }

            // 执行
            CloseableHttpResponse response = httpClient.execute(post);
            try {
                if (response != null) {
                    HttpEntity httpEntity = response.getEntity();
                    // 如果返回的内容不为空
                    if (httpEntity != null) {
                        result = EntityUtils.toString(httpEntity);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //关闭response
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
