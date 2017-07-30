package com.cheng.qq.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class HttpServerInboundHandler extends SimpleChannelInboundHandler {

    private static Log logger = LogFactory.getLog(HttpServerInboundHandler.class);

    private HttpRequest request;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().remoteAddress() + " 连接到服务器。");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        System.out.println(msg);

        String res = "I has receive";

        ctx.write(res);
        ctx.flush();

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info(ctx.channel().remoteAddress() + " 断开连接。");
    }
}
