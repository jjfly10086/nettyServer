package com.jwh.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.jwh.demo.model.User;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpUtils {

    private static int CONNECTION_REQUEST_TIMEOUT = 10 * 1000;

    private static int CONNECTION_TIMEOUT = 20 * 1000;

    private static int SOCKET_TIMEOUT = 30 * 1000;

    public static void postRequest(String uri,Object data ,ContentType contentType) throws Exception{

        RequestConfig.Builder configBuilder =
                RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT);
        RequestConfig config = configBuilder.build();
        HttpClientBuilder clientBuilder = HttpClients.custom().setDefaultRequestConfig(config);
        HttpClient client = clientBuilder.build();
        HttpPost post = new HttpPost(uri);
        String content = JSONObject.toJSONString(data);
        HttpEntity entity =  new StringEntity(content, contentType);
        post.setEntity(entity);
        HttpResponse response =   client.execute(post);
        byte[] bytes = new byte[1024];
        response.getEntity().getContent().read(bytes);
        System.out.println(Thread.currentThread().getName()+":"+new String(bytes,Charset.forName("UTF-8")));
    }
    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        while (true){
            TimeUnit.MILLISECONDS.sleep(10);
            executorService.execute(new PostRequest());
        }
    }

    static class PostRequest implements Runnable {
        @Override
        public void run() {
            try{
                User user = new User(null,"333","fdsfs","fdssss","sdfdsfs",1,"13644587744","黄浦区",new Date());
                ContentType contentType = ContentType.create(ContentType.APPLICATION_JSON.getMimeType(),Charset.forName("UTF-8"));
                postRequest("http://localhost:8080/user/insert",user,contentType);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
