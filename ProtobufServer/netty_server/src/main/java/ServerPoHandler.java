import POJO.ConnectionPool;
import POJO.MessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerPoHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProto.Message message = (MessageProto.Message) msg;
        if (ConnectionPool.getChannel(message.getId()) == null) {
            ConnectionPool.putChannel(message.getId(), ctx);
        }
        System.err.println("server:" + message.getId());
        ctx.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
