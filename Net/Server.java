package Net;

import view.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<Socket> onlineSockets=new ArrayList<>();
    public static List<Player> players=new ArrayList<>();
    public static List<ObjectOutputStream> oStream=new ArrayList<>();
    public static void main(String[] args) throws Exception {
        System.out.println("----服务端启动----");
        ServerSocket serverSocket=new ServerSocket(8888);
        Client.clearFile("records/rank.txt");
        while (true) {
            Socket socket=serverSocket.accept();
            System.out.println("上线： "+socket.getRemoteSocketAddress());
            onlineSockets.add(socket);
            oStream.add(new ObjectOutputStream(socket.getOutputStream()));
            new ServerReaderThread(socket).start();
        }
    }


}
