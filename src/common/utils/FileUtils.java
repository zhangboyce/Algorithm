package common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 6/12/15
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {

    public static List<String> readLines(File file){
        BufferedReader bufferedReader = null;
        List<String> lines = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String word;
            while ((word = bufferedReader.readLine()) != null){
                lines.add(word);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try{
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return lines;
    }
}
