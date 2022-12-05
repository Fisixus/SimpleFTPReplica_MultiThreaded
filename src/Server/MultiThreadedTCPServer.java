
import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedTCPServer {

    public MultiThreadedTCPServer() throws IOException {
        this(7);
    }

    public MultiThreadedTCPServer(int port) throws IOException {
        ServerSocket server = null;
        server = new ServerSocket(port);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i < 6; i++) {
            WorkerThreadServer workerThread = new WorkerThreadServer(i,server);
            executor.submit(workerThread);
        }
        executor.shutdown();
        while(!(executor.isTerminated())){}
        server.close();

    }

    public static void main(String[] argv) throws IOException {
        MultiThreadedTCPServer simpleTCPServer = new MultiThreadedTCPServer();
    }
}