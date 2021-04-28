package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.nio.charset.Charset;

@ChannelHandler.Sharable
public class ServiceHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("====channelActive====");
//        channels.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("====channelRead====");
        System.out.println(System.currentTimeMillis());

        //2. 데이터 수신 이벤트 처리 메서드. 클라이언트로부터 데이터의 수신이 이루어졌을때 네티가 자동으로 호출하는 이벤트 메소드
        String message = ((ByteBuf) msg).toString(Charset.defaultCharset());
        //3. 수신된 데이터를 가지고 있는 네티의 바이트 버퍼 객체로 부터 문자열 객체를 읽어온다.

        ByteBuf msg2 = Unpooled.copiedBuffer((message).getBytes());
        //4.ctx는 채널 파이프라인에 대한 이벤트를 처리한다. 여기서는 서버에 연결된 클라이언트 채널로 입력받은 데이터를 그대로 전송한다.
        System.out.println(msg2.toString(Charset.defaultCharset()));
        ctx.writeAndFlush(msg2);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("====channelReadComplete====");
        //6. channelRead 이벤트의 처리가 완료된 후 자동으로 수행되는 이벤트 메서드
        ctx.flush();
        //7. 채널 파이프 라인에 저장된 버퍼를 전송
        System.out.println("====channelReadComplete End====");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
//        ctx.close();
    }

}
