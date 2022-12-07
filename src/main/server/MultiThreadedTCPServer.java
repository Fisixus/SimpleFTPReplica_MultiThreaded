package server;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedTCPServer {

    public MultiThreadedTCPServer() throws IOException {
        this(4000);
    }

    public MultiThreadedTCPServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 1; i < 3; i++) {
            WorkerThreadServer workerThread = new WorkerThreadServer(i,serverSocket);
            executor.submit(workerThread);
        }
        executor.shutdown();
        while(!(executor.isTerminated())){}
        serverSocket.close();

    }

    public static void main(String[] argv) throws IOException {
        MultiThreadedTCPServer simpleTCPServer = new MultiThreadedTCPServer();
    }
}