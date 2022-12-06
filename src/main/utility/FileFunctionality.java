package utility;

import java.io.File;

public class FileFunctionality {
    public static boolean FileIsExist(String loc, String filename) {
        File f = new File(loc+"\\"+filename);
        if(f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    public static boolean LocationIsAccessible(String newLoc) {
        File f = new File(newLoc);
        return f.exists();
    }

    public static String GetAllOfTheFilesUnderLoc(String loc){
        File curDir = new File(loc);
        File[] filesList = curDir.listFiles();
        String fileList = "";
        for(File f : filesList){
            if(f.isDirectory())
                fileList += "directory:";
            if(f.isFile()){
                fileList += "file:";
            }
            fileList += f.getName();
            fileList += "\n";
        }
        return fileList;
    }
}
