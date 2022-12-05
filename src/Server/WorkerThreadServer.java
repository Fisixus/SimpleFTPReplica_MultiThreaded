import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WorkerThreadServer implements Runnable {
    private int threadNumber;
    private ServerSocket server;

    private boolean isSingleThreaded = false;

    public void SetIsSingleThreaded(boolean b){
        isSingleThreaded = b;
    }

    public WorkerThreadServer(int n, ServerSocket server) throws IOException {
        threadNumber = n;
        this.server = server;
        //r = new InputStreamReader(socket.getInputStream());
        //w = new OutputStreamWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        System.out.println("Starting WorkerThread(" + threadNumber + ")");
        try {
            Socket socket = null;
            Reader r = null;
            Writer w = null;
            if(!isSingleThreaded){
                socket = server.accept();
                r = new InputStreamReader(socket.getInputStream());
                w = new OutputStreamWriter(socket.getOutputStream());
            }
            char[] data = new char[1024];
            int numberOfReadCharacters;
            while (true) {
                if(isSingleThreaded){
                    socket = server.accept();
                    r = new InputStreamReader(socket.getInputStream());
                    w = new OutputStreamWriter(socket.getOutputStream());
                }
                numberOfReadCharacters = r.read(data, 0, 1024);
                if (numberOfReadCharacters == -1) {
                    break;
                }
                if (numberOfReadCharacters > 0) {
                    String str = new String(data, 0, numberOfReadCharacters);
                    System.out.println(str);
                    w.write(data, 0, numberOfReadCharacters);
                    w.flush();
                }

            }
            w.write(-1);
            w.close();
            r.close();
            System.out.println("Terminating WorkerThread(" + threadNumber + ")");
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
