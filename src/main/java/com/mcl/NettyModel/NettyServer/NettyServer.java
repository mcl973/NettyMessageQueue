/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: NettyServer
 * Author:   Administrator
 * Date:     2020/03/13 11:49
 * Description: netty的server端
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.NettyModel.NettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 〈一句话功能简述〉<br> 
 * 〈netty的server端〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
public class NettyServer implements Runnable{
    private static EventLoopGroup boss = new NioEventLoopGroup(),worker = new NioEventLoopGroup();
    private static ServerBootstrap serverBootstrap = new ServerBootstrap();

    public void instancebootstrap(){
        try{
            serverBootstrap.group(boss,worker).channel(NioServerSocketChannel.class).
                    childHandler(new ChannelInitializerHandlers());
            ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1", 8899).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @Override
    public void run() {
        instancebootstrap();
    }
}
