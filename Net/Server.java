package Net;

import view.Player;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<Socket> onlineSockets=new ArrayList<>();
    public static List<Player> players=new ArrayList<>();
    public static void main(String[] args) throws Exception {
        System.out.println("----服务端启动----");
        ServerSocket serverSocket=new ServerSocket(8888);

        while (true) {
            Socket socket=serverSocket.accept();
            System.out.println("上线： "+socket.getRemoteSocketAddress());
            onlineSockets.add(socket);
            new ServerReaderThread(socket).start();
        }
    }


}
