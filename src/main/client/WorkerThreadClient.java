package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class WorkerThreadClient implements Runnable {
    private int threadNumber;

    public WorkerThreadClient(int n) {
        threadNumber = n;
    }

    @Override
    public void run() {
        System.out.println("Starting WorkerThread(" + threadNumber + ")");
        try {
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            Socket socket = new Socket(inetAddress, 7);
            Reader r = new InputStreamReader(socket.getInputStream());
            Writer w = new OutputStreamWriter(socket.getOutputStream());
            char[] data = new char[1024];
            int numberOfReadCharacters;
            System.out.println("Message Sending from:" + threadNumber);
            w.write("HOLA from:Thread"+threadNumber);
            w.flush();
            while (true) {
                try {
                    numberOfReadCharacters = r.read(data, 0, 1024);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (numberOfReadCharacters == -1) {
                    break;
                }
                if (numberOfReadCharacters > 0) {
                    String str = new String(data, 0, numberOfReadCharacters);

                    //System.out.println(str);
                    break;
                }
            }
            try {
                System.out.println("Terminating WorkerThread(" + threadNumber + ")");
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
