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

    private RestExecutionHandler restHandler = new RestExecutionHandler(new ThreadPoolExecutor(100, 1000, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(500)));

    @PostConstruct
    public void start(){
        new Thread(new Runnable() {
            public void run() {
                try{
                    service();
                }catch (Exception e){
                    e.printStackTrace();
                    System.exit(0);
                }
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
        return new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
    }

    private ChannelPipelineFactory getChannelPipelineFactory()
    {
        return new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                // Create a default pipeline implementation.
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new HttpRequestDecoder());
                pipeline.addLast("encoder", new HttpResponseEncoder());
                pipeline.addLast("query", restHandler);
                return pipeline;
            }
        };
    }
}
