import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedTCPClient {
    public MultiThreadedTCPClient() throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i < 6; i++) {
            WorkerThreadClient workerThread = new WorkerThreadClient(i);
            executor.submit(workerThread);
        }
        executor.shutdown();
        while(!(executor.isTerminated())){}

    }

    public static void main(String[] args) throws IOException {
        MultiThreadedTCPClient simpleTCPClient = new MultiThreadedTCPClient();
    }
}
