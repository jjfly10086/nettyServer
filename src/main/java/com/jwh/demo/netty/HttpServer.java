package com.jwh.demo.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class HttpServer {

    private static final Integer DEFAULT_PORT = 8080;

    private RestExecutionHandler restHandler;

    public HttpServer(){
        restHandler = new RestExecutionHandler(new ThreadPoolExecutor(100, 1000, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(500)));
    }

    @PostConstruct
    public void start(){
        new Thread(()-> {
            try{
                service();
            }catch (Exception e){
                e.printStackTrace();
                System.exit(0);
            }
        }).start();
    }

    private void service() throws Exception{
        // Configure the server.
        ServerBootstrap serverBootstrap = new ServerBootstrap(getChannelFactory());
        SocketAddress socketAddress = new InetSocketAddress(DEFAULT_PORT);
        serverBootstrap.setPipelineFactory(getChannelPipelineFactory());
        serverBootstrap.bindAsync(socketAddress);
        System.out.println("Start Success!");
    }

    private ChannelFactory getChannelFactory(){
        //构建boss和work线程池，boss线程接收socket连接，产生channel，然后从work池中选一个线程继续执行
        //boss线程池默认一个线程，work线程池默认是当前处理器*2的线程数(Runtime.getRuntime().availableProcessors() * 2)
        return new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
    }

    private ChannelPipelineFactory getChannelPipelineFactory(){
        return ()-> {
            //在worker线程中，消息会经过设定好的ChannelPipeline处理。ChannelPipeline就是一堆有顺序的filter
            //worker线程是由netty内部管理，统一调配的一种资源，所以最好应该尽快的把让worker线程执行完毕，返回给线程池回收利用
            //因此在work线程中开启一个新线程来继续处理业务逻辑，而worker线程在执行完messageReceived()就会结束了。
            //而更加优化的方法是构造一个线程池来提交业务逻辑处理任务。如此处的restHandler
            // Create a default pipeline implementation.
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("decoder", new HttpRequestDecoder());
            pipeline.addLast("encoder", new HttpResponseEncoder());
            pipeline.addLast("query", restHandler);
            return pipeline;
        };
    }
}
