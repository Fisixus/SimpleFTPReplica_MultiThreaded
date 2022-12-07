package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FTPFunctionality {

    public static FTPState ReturnRequestType(String message){
        message = message.trim();
        String[] splited = message.split("\\s+");

        if(splited[0].equalsIgnoreCase(FTPState.CD.toString())){
            if(splited.length > 2)
                return FTPState.None;
            return FTPState.CD;
        }
        else if(splited[0].equalsIgnoreCase(FTPState.GET.toString())){
            if(splited.length > 2)
                return FTPState.None;
            return FTPState.GET;
        }
        else if(splited[0].equalsIgnoreCase(FTPState.LS.toString())){
            if(splited.length > 1)
                return FTPState.None;
            return FTPState.LS;
        }
        else{
            return FTPState.None;
        }
    }

    private static String GetCommandArg(String command){
        command = command.trim();
        String[] splited = command.split("\\s+");
        return splited[1];
    }

    public static String DoLS(String currentLoc) {
        return FileFunctionality.GetAllOfTheFilesUnderLoc(currentLoc);
    }

    public static String DoGET(String filename) {
        String allStr = "";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //System.out.println(data);
                allStr += data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return allStr;
    }

    public static String DoCD(String command) {
        String newArg = GetCommandArg(command);
        return newArg;
    }

    public static String GetFilenameFromGET(String command) {
        return GetCommandArg(command);
    }
}
