package Net;

import view.Player;

import java.io.*;
import java.net.Socket;

public class ServerReaderThread extends Thread{
    Socket socket;
    public  ServerReaderThread(Socket socket){
        this.socket=socket;
    }
    public void run(){
        try {
            InputStream inputStream=socket.getInputStream();
            DataInputStream dataInputStream=new DataInputStream(inputStream);
            while (true) {
                try {
                    String message=dataInputStream.readUTF();
                    String[] p=message.split(" ");
                    Server.players.add(new Player(p[0],Integer.parseInt(p[1])));
                    System.out.println(message);
                    sendMessageToAll(message);
                } catch (Exception e) {
                    System.out.println("下线："+socket.getRemoteSocketAddress());
                    Server.onlineSockets.remove(socket);
                    dataInputStream.close();
                    socket.close();
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void sendMessageToAll(String message) throws Exception {
        for (Socket onlineSocket : Server.onlineSockets) {
            OutputStream outputStream=onlineSocket.getOutputStream();
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(Server.players);
            objectOutputStream.flush();
        }
    }
}
