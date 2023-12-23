package Net;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

public class ClientReaderThread extends Thread{
    Socket socket;
    public  ClientReaderThread(Socket socket){
        this.socket=socket;
    }
    public void run(){
        try {
            InputStream inputStream=socket.getInputStream();
            DataInputStream dataInputStream=new DataInputStream(inputStream);
            while (true) {
                try {
                    String message=dataInputStream.readUTF();
                    System.out.println(message);
                } catch (Exception e) {
                    System.out.println("下线："+socket.getRemoteSocketAddress());
                    dataInputStream.close();
                    socket.close();
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
