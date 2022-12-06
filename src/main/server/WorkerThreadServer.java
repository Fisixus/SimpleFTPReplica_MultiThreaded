package server;

import utility.FTPFunctionality;
import utility.FTPState;
import utility.FileFunctionality;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WorkerThreadServer extends Thread {

    private final static String ERR_MESSAGE = "THIS INPUT IS NOT IN A CORRECT FORMAT!";
    private final static String FILE_CANT_FIND_MESSAGE = "FILE IS NOT FOUND!";
    private final static String LOCATION_NOT_ACCESSIBLE_MESSAGE = "LOCATION IS NOT ACCESSIBLE!";
    private final static String LOCATION_ACCESSIBLE_MESSAGE = "LOCATION IS SUCCESSFULLY CHANGED!";

    private int threadNumber;
    private ServerSocket serverSocket;

    public WorkerThreadServer(int threadNumber, ServerSocket serverSocket) {
        this.threadNumber = threadNumber;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        System.out.println("Starting WorkerThread(" + threadNumber + ")");
        try {
            ObjectOutputStream output;
            ObjectInputStream input;
            Socket socket = serverSocket.accept();
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            while (true) {
                String command = (String)input.readObject();
                FTPState state = FTPFunctionality.ReturnRequestType(command);
                switch (state){
                    case LS:
                        String lsOutput = FTPFunctionality.DoLS();
                        output.writeObject(lsOutput);
                        break;
                    case CD:
                        String newLoc = FTPFunctionality.GetNewLocationFromCD(command);
                        if(!FileFunctionality.LocationIsAccessible(newLoc))
                            output.writeObject(LOCATION_NOT_ACCESSIBLE_MESSAGE);
                        else{
                            FTPFunctionality.DoCD(command);
                            output.writeObject(LOCATION_ACCESSIBLE_MESSAGE);
                        }
                        break;
                    case GET:
                        String filename = FTPFunctionality.GetFilenameFromGET(command);
                        if(!FileFunctionality.FileIsExist(filename))
                            output.writeObject(FILE_CANT_FIND_MESSAGE);
                        else{
                            String content = FTPFunctionality.DoGET(command);
                            output.writeObject(content);
                        }
                        break;
                    default:
                        output.writeObject(ERR_MESSAGE);
                        break;
                }
                output.flush();
            }

            //output.writeInt(-1);
            //output.flush();
            //socket.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
