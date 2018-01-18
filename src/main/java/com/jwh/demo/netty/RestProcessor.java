package com.jwh.demo.netty;

import com.alibaba.fastjson.JSONObject;
import com.jwh.demo.CommonRequest;
import com.jwh.demo.StartServer;
import com.jwh.demo.UrlRouter;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.*;

import java.lang.reflect.Method;
import java.nio.charset.Charset;

public class RestProcessor {

    public void messageReceived(ChannelHandlerContext ctx, final MessageEvent me) {
        DefaultHttpRequest request = (DefaultHttpRequest) me.getMessage();
        System.out.println(request.getUri());
        Object resultObj = null;
        String result = "";
        try{
            resultObj = invokeMethod(request);
            String data = JSONObject.toJSONString(resultObj);
            result = "{\"status\":200,\"data\":"+data+"}";
        }catch (Exception e){
            e.printStackTrace();
            result = "{\"status\":500,\"msg\":\""+e.getMessage()+"\",\"data\":null}";
        }
        System.out.println("send result: "+result);
        Channel channel = me.getChannel();
        sendResponse(request,channel,result);
    }

    private void sendResponse(HttpRequest request, Channel channel,String result){
        DefaultHttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
        response.setContent(ChannelBuffers.copiedBuffer(result, Charset.forName("UTF-8")));
        response.headers().set("Content-Type", "application/json;charset=utf-8");
        response.headers().set("Content-Length", response.getContent().readableBytes());
        boolean close = !HttpHeaders.isKeepAlive(request);
        response.headers().set(HttpHeaders.Names.CONNECTION, close ? HttpHeaders.Values.CLOSE : HttpHeaders.Values.KEEP_ALIVE);
        ChannelFuture cf = channel.write(response);
        if(close){
            cf.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private Object invokeMethod(DefaultHttpRequest request) throws Exception{

        String[] params = request.getUri().split("/");
        if(params.length == 3){
            UrlRouter router = StartServer.context.getBean("urlRouter",UrlRouter.class);
            String beanName = router.getNameRouter().get(params[1]);
            if(beanName == null){
                throw new RuntimeException("no request uri \'"+ request.getUri()+"\' found");
            }

            int methodLastIndex;
            if(params[2].contains("?")){
                methodLastIndex = params[2].indexOf("?");
            }else{
                methodLastIndex = params[2].length();
            }
            String methodName = params[2].substring(0,methodLastIndex);
            String requestArgs = request.getContent().toString(Charset.forName("UTF-8"));
            Method method;
            Object targetObj = StartServer.context.getBean(beanName);
            try {
                method = targetObj.getClass().getMethod(methodName,CommonRequest.class);
            }catch (NoSuchMethodException e ){
                throw new RuntimeException("no request uri \'"+ request.getUri()+"\' found");
            }
            JSONObject jsonObject = null;
            try{
                jsonObject = JSONObject.parseObject(requestArgs);
            }catch (Exception e){
                throw new RuntimeException("request contentType needs \'application/json\'");
            }
            return method.invoke(targetObj,new CommonRequest(jsonObject));
        }else{
            throw new RuntimeException("no request uri \'"+ request.getUri()+"\' found");
        }
    }
}
