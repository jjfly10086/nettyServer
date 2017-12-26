package com.jwh.demo.netty;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;

import java.io.Serializable;

public class RequestHandlerTask implements Runnable,Serializable {

    private ChannelHandlerContext ctx;
    private ChannelEvent event;
    private RestProcessor processor;

    public RequestHandlerTask(RestProcessor restProcessor,ChannelHandlerContext ctx, ChannelEvent event){
        this.processor = restProcessor;
        this.ctx = ctx;
        this.event = event;
    }
    public void run() {
        try{
            if (event instanceof MessageEvent) {
                processor.messageReceived(ctx, (MessageEvent)event);
            } else {
                ctx.sendUpstream(event);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
