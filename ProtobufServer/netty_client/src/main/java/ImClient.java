import POJO.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ImClient {
    private Channel channel;

    public Channel connect(String url, int port) {
        doConnect(url, port);
        return channel;
    }

    void doConnect(String url, int port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast("encoder", new ProtobufEncoder());
                    socketChannel.pipeline().addLast("decoder", new ProtobufDecoder(MessageProto.Message.getDefaultInstance()));
                    socketChannel.pipeline().addLast(new ClientPoHandler());
                }
            });
            ChannelFuture future = bootstrap.connect(url, port).sync();
            channel = future.channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
