package Net;

import view.Player;

import javax.print.attribute.standard.Severity;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
                    e.printStackTrace();
                    System.out.println("下线："+socket.getRemoteSocketAddress());
                    Server.onlineSockets.remove(socket);
                    Server.oStream.remove(socket.getOutputStream());
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
        for (Player player : Server.players) {
            System.out.println(player.getName()+"  "+player.getScore());
        }
        for(int i=0;i< Server.onlineSockets.size();i++){
            ObjectOutputStream objectOutputStream=Server.oStream.get(i);
            objectOutputStream.writeObject(Server.players);
            objectOutputStream.flush();
        }
    }
}
