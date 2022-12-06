package utility;

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

    public static String DoLS() {
    }

    public static void DoCD(String command) {
    }

    public static String DoGET(String command) {
    }

    public static String GetNewLocationFromCD(String command) {
    }

    public static String GetFilenameFromGET(String command) {
    }
}
