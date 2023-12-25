package Net;

import view.Player;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ClientReaderThread extends Thread{
    Socket socket;
    InputStream inputStream;
    ObjectInputStream objectInputStream;
    public  ClientReaderThread(Socket socket) throws Exception {
        this.socket=socket;
            inputStream=socket.getInputStream();
            objectInputStream=new ObjectInputStream(inputStream);


    }
    public void run(){
        try {
            while (true) {
                try {
                    List<Player> players= (List<Player>) objectInputStream.readObject();
                    Client.clearFile("records/rank.txt");
                    writeRank("records/rank.txt",players);
                    for (Player player : players) {
                        System.out.println(player.getName()+"  "+player.getScore());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("下线："+socket.getRemoteSocketAddress());
                    objectInputStream.close();
                    socket.close();
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void writeRank(String path,List<Player> players){
        List<String> saveLines=new ArrayList<>();
        for (Player player : players) {
            saveLines.add(player.getName()+" "+player.getScore());
        }
        try {
            Files.write(Path.of(path),saveLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
