package Engine.Util;


import java.io.BufferedReader;
import java.io.FileReader;

public class File {
    public static String loadResource(String fileName) throws Exception {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String buffer;
        while ((buffer = bufferedReader.readLine()) != null) {
            result.append(buffer).append("\n");
        }
        bufferedReader.close();
        return result.toString();
    }
}
