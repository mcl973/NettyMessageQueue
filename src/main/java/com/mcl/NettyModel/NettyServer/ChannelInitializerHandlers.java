/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ChannelInitializerHandlers
 * Author:   Administrator
 * Date:     2020/03/13 12:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.mcl.NettyModel.NettyServer;

import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2020/03/13
 * @since 1.0.0
 */
public class ChannelInitializerHandlers extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new Myhandler());
    }
}
