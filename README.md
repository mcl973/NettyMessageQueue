# NettyMessageQueue
    注：目前对于安全问题还没有解决：
        主要就是如何针对在一个订阅者处于不活动状态，而此时有其他的订阅者盗用它的名字导致这个订阅者消失，虽然说下次来的时候还是会再创阿金，但是在此期间如果productor向订阅者发送消息那么此时即使订阅者上限，也不会受到消息，而是顶替他的那个订阅者会受到消息。
        现在的解决办法就是使用ip：port的方法来鉴定。但是这个有一个问题就是，如果ip：port变了则么办，因为port是随机的，所以不行。有可能ip也会变，但是如果是移动端，那么就会出现问题。
        如果是使用类似于sessionid的东西到时可以，但是安全性要注意，可以使用非对称秘钥来保证问题。
        有没有更好的办法呢？
    1.底层使用nety进行数据的传输。
    2.全程使用安全容器：
        ConCurrentHashMap（作为生产者容器）、Collections.synchronizedList（每一个消费者中两个这种容器，一个是存储生产者的消息的，
          一个是存储他自己的发送给生产者消息的）、BlockingQueue（在生产者中使用这个来存放发送request请求的消费者）
    3.目前一个订阅频道只能有一个生产者，但是消费者不限制。即一对多模式。会送request消息是一对一，发送群体消息是一对多。
    4.使用线程池去处理业务。
    目前的机制是：
          注册
          消费者 ----- channel----nettyServer---通过生产者名字---生产者，对于拥有channel的消费者进行包装，真伪消费者对象加入对应的消费者队列
          取消注册：
          消费者 ----- channel----nettyServer---通过生产者名字---生产者，通过消费者名字将从消费者队列中删除（删除前判断，其channel是不是其他的订阅者的channel）
          request:
          消费者 ----- channel----nettyServer---通过生产者名字---生产者，通过消费者名字找到具体的消费者，将此request放到消费者的requestlist中，并将这个消费者
                                                                                  对象放入生产者的blockingqueue中。
                                                                                      |
                                                                                      |
                                                                                      生产者启用一个线程不断地去查询这个queue，
                                                                                      如果有数据了那么就从中取出来去线程的处理。
         生产者遍历消费者列表通过每个消费者中的channel发送广播信息。
     5.命令：
          1 request 生产者名字 消费者名字  请求消息
          0 生产者名字 消费者名字  | 0 取消注册
                                 | 1 注册
          
     6.目前使用cquptmcl来分断string。
        如：注册：      0cquptmclAckcquptmclackcquptmcl1
            取消注册：  0cquptmclAckcquptmclackcquptmcl0
            request：  1cquptmclAckcquptmclackcquptmcl12345
     7.客户端没有写，但是很简单，用户可以自己写，这里提供样例（测试用）：
          import io.netty.bootstrap.Bootstrap;
          import io.netty.channel.*;
          import io.netty.channel.nio.NioEventLoopGroup;
          import io.netty.channel.socket.SocketChannel;
          import io.netty.channel.socket.nio.NioSocketChannel;
          import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
          import io.netty.handler.codec.LengthFieldPrepender;
          import io.netty.handler.codec.string.StringDecoder;
          import io.netty.handler.codec.string.StringEncoder;
          import io.netty.util.CharsetUtil;

          import java.io.BufferedReader;
          import java.io.InputStreamReader;

          /**
           * 〈一句话功能简述〉<br> 
           * 〈〉
           *
           * @author Administrator
           * @create 2019/05/25
           * @since 1.0.0
           */
          public class Client {
              public static void main(String[] args) throws Exception{
                  EventLoopGroup client = new NioEventLoopGroup();
                  try{
                      Bootstrap bootstrap = new Bootstrap();
                      bootstrap.group(client).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                          @Override
                          protected void initChannel(SocketChannel socketChannel) throws Exception {
                              ChannelPipeline pipeline = socketChannel.pipeline();

                              pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                              pipeline.addLast(new LengthFieldPrepender(4));
                              pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                              pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                              pipeline.addLast(new SimpleChannelInboundHandler<String>() {
                                  @Override
                                  protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                      System.out.println(s);
                                  }
                              });
                          }
                      });
                      Channel channel = bootstrap.connect("127.0.0.1",8899).sync().channel();
                      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                      while(true){
                          channel.writeAndFlush(br.readLine());
                      }
                  }finally {
                      client.shutdownGracefully();
                  }
              }
          }
          
         
                                                                                   
                                                                                   
                                                                                    
