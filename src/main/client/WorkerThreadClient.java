package client;

import utility.FTPFunctionality;
import utility.FTPState;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class WorkerThreadClient implements Runnable {

    private final static String FILE_CANT_FIND_MESSAGE = "FILE IS NOT FOUND!";

    private int threadNumber;

    private final String DEFAULT_LOC = "\\SimpleFTPReplica_MultiThreaded";


    public WorkerThreadClient(int n) {
        threadNumber = n;
    }

    private void WriteToFile(String msg,String loc){
        try
        {
            FileWriter fw = new FileWriter(loc,false); //the true will append the new data
            fw.write(msg + "\n");//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    @Override
    public void run() {
        System.out.println("Starting WorkerThread(" + threadNumber + ")");
        try {
            String currentLoc = DEFAULT_LOC;
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            Socket socket = new Socket(inetAddress, 4000);
            ObjectOutputStream output;
            ObjectInputStream input;
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            Scanner myScanner = new Scanner(System.in);

            //output.writeObject("Connection is established from Thread:"+threadNumber);
            String command = "";
            String takenMessage = "";
            String filename = "";
            do{
                System.out.println("Enter Simple FTP Request(Type EXIT for closing the connection!):");
                command = myScanner.nextLine();

                output.writeObject(command);
                System.out.println("Request is sent!");

                if(FTPFunctionality.ReturnRequestType(command) == FTPState.GET){
                    filename = FTPFunctionality.GetFilenameFromGET(command);
                    takenMessage = (String)input.readObject();
                    if(!takenMessage.equals(FILE_CANT_FIND_MESSAGE)){
                        WriteToFile(takenMessage,currentLoc+"\\"+filename);
                    }
                    else{
                        System.out.println(takenMessage);
                    }
                }
                else{
                    takenMessage = (String)input.readObject();
                    System.out.println(takenMessage);
                }
                output.flush();

            }while(command.equalsIgnoreCase("exit"));

            try {
                System.out.println("Terminating WorkerThread(" + threadNumber + ")");
                output.flush();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
