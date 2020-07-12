package io.netty.simple.chapter1.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author magw
 * @version 1.0
 * @date 2020/7/12 上午10:40
 * @description: No Description
 */
public class HttpHelloWorldServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private final ByteBuf firstMessage;


    public HttpHelloWorldServerHandler() {
        firstMessage = Unpooled.wrappedBuffer("hello world".getBytes());
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        if(httpObject instanceof HttpRequest){
            HttpRequest req = (HttpRequest)httpObject;
            FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(firstMessage));

            resp.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8")
                    .setInt(HttpHeaderNames.CONTENT_LENGTH,resp.content().readableBytes());
            ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);


        }
    }
}
