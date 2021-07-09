import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;

public class SocketChannelTest {
    public static void main(String[] args) {
        try {
            SocketChannel channel = SocketChannel.open();
            channel.connect(new InetSocketAddress("ubuntu", 8999));

            channel.socket().setSoTimeout(15000);
            InputStream inStream = channel.socket().getInputStream();
            ReadableByteChannel wrappedChannel = Channels.newChannel(inStream);

            System.out.println("SocketChannel.main");
            wrappedChannel.read(ByteBuffer.allocate(10));
            System.out.println("----");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
