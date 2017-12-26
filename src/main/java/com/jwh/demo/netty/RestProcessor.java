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

    public void messageReceived(ChannelHandlerContext ctx, final MessageEvent me) throws Exception{
        System.out.println(me);
        DefaultHttpRequest request = (DefaultHttpRequest) me.getMessage();
        Object resultObj = invokeMethod(request);
        String data = JSONObject.toJSONString(resultObj);
        String result = "{\"status\":200,\"data\":"+data+"}";
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
        if(close)
            cf.addListener(ChannelFutureListener.CLOSE);
    }

    private Object invokeMethod(DefaultHttpRequest request) throws Exception{
        UrlRouter router = (UrlRouter) StartServer.context.getBean("router");
        String[] params = request.getUri().split("/");
        String beanName = router.getNameRouter().get(params[1]);
        if(beanName!=null && params.length == 3){
            String methodName = params[2].substring(0,params[2].indexOf("?"));
            String requestArgs = request.getContent().toString(Charset.forName("UTF-8"));
            Method method;
            try {
                 method = StartServer.context.getBean(beanName).getClass().getMethod(methodName, CommonRequest.class);
            }catch (NoSuchMethodException e ){
                return "no request uri "+ request.getUri()+" found";
            }
            return method.invoke(StartServer.context.getBean(beanName),new CommonRequest(JSONObject.parseObject(requestArgs)));
        }else{
            return "no request uri "+ request.getUri()+" found";
        }
    }
}
