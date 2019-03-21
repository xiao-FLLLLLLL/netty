import POJO.MessageProto;
import io.netty.channel.Channel;

import java.util.UUID;

public class NettyClient {
    public static void main(String[] args) {
        String url = "127.0.0.1";
        int port = 2222;
        Channel channel = new ImClient().connect(url, port);
//        channel.writeAndFlush("try connect");
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        MessageProto.Message message = MessageProto.Message.newBuilder().setId(id).setContent("netty test protobuf").build();
        channel.writeAndFlush(message);
    }
}
