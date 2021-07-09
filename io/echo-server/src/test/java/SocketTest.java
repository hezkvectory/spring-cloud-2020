import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketTest {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("ubuntu", 8999), 15000);
            socket.setSoTimeout(15000);
            System.out.println("Main1.main");
            byte[] arr = new byte[128];
//            socket.setTcpNoDelay(true);
//            socket.setKeepAlive(true);
//            socket.setSoTimeout(3);
//            socket.setReuseAddress(true);
            socket.getInputStream().read(arr);
            System.out.println("----");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
