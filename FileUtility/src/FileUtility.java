
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Overview: This utility class provides an API to operate on a text-file
 * handling actions such as retrieval, writing and deleting of information to
 * the file.
 *
 * @author Riko Hamblin & Ibukun
 */
public final class FileUtility {

    //Constructor
    private FileUtility() {
    }

    //Utility Methods
    public static boolean isStringInFile(String filename, String content) {

        createFile(filename);

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            //open file 
            String currentLine;

            //while there are lines in the file
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.contains(content)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void appendToFile(String filename, String s) {
        createFile(filename);
        File file = new File(filename);

        try {

            FileWriter fileW = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter buffw = new BufferedWriter(fileW);
            buffw.write(s + "\n");
            buffw.close();

        } catch (FileNotFoundException ex) {

        } catch (Exception e) {

        }

    }

    public static boolean rewriteContentOfFile(String filename, String originalContent, String newContent) {
        createFile(filename);

        String oldFileName = filename;

        BufferedReader br = null;
        BufferedWriter bw = null;

        ArrayList<String> lines = new ArrayList<>();
        try {
            File f1 = new File(oldFileName);
            br = new BufferedReader(new FileReader(f1));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(originalContent)) {
                    line = line.replace(originalContent, newContent);
                }
                if (line.isEmpty()) {
                    continue;
                }
                lines.add(line);

            }
            br.close();

            FileWriter fw = new FileWriter(f1);
            try (BufferedWriter out = new BufferedWriter(fw)) {
                for (String s : lines) {
                    out.write(s + "\n");
                }
                out.flush();
                out.close();
            }

        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                //
            }
            try {

                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                //
            }

        }
        return true;
    }

    public static boolean removeEntireLineFromFile(String filename, String content) {
        rewriteContentOfFile(filename, content, "");

        return true;
    }
    
    //a method that returns all lines (as list) that contains list of parameters as equal to (opertor) list of values
    //  public static ArrayList<ArrayList<String>> getData (String filename, )
    
    //Helper Methods
    private static void createFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {  //if file doesnt exist then create it
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
