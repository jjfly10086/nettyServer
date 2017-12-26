package com.jwh.demo.netty;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.execution.ExecutionHandler;

import java.util.concurrent.Executor;

public class RestExecutionHandler extends ExecutionHandler {

    private final RestProcessor restProcessor = new RestProcessor();

    public RestExecutionHandler(Executor executor){
        super(executor);
    }

    @Override
    public void handleUpstream(ChannelHandlerContext context, ChannelEvent e) throws Exception {
        getExecutor().execute(new RequestHandlerTask(restProcessor,context,e));
    }
}
