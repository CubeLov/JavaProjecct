package Net;

import view.GameFrame;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    需要实时监听，并支持单次上传
 */
public class Client {
    public static void main(String[] args) throws Exception {
        new Thread(()->{
            String path = "music.WAV";
        new PlayMusic(path);
        }).start();
        GameFrame gameFrame=new GameFrame(1100,810);
        gameFrame.setVisible(true);
        int onlineScore=0;
        String name="";
        Socket socket=new Socket("127.0.0.1",8888);
        new ClientReaderThread(socket).start();
        OutputStream outputStream=socket.getOutputStream();
        DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
        while(true){
            if(gameFrame.getName()!=null)
                name=gameFrame.getName();
            onlineScore=getOnlineScore("records/score.txt");
            String tempName=getOnlineName("records/score.txt");
            if(onlineScore!=0&&name.equals(tempName)){
                String message=name+" "+Integer.toString(onlineScore);
                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();
                clearFile("records/score.txt");
            }
            Thread.sleep(100);
        }
    }

    public static int getOnlineScore(String path) {
        try {
            List<String> loadLines= Files.readAllLines(Path.of(path));
            if(loadLines.isEmpty())
                return 0;
            else if(loadLines.get(0).isEmpty())
                return 0;
            else
                return Integer.parseInt(loadLines.get(1));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getOnlineName(String path){
        try {
            List<String> loadLines= Files.readAllLines(Path.of(path));
            if(loadLines.isEmpty())
                return "";
            else if(loadLines.get(0).isEmpty())
                return "";
            else
                return loadLines.get(0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void clearFile(String path){
        List<String> saveLines=new ArrayList<>();
        try {
            Files.write(Path.of(path),saveLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
