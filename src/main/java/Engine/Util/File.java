package Engine.Util;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> readAllLines(String fileName) throws Exception {
        List<String> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String buffer;
        while ((buffer = bufferedReader.readLine()) != null) {
            list.add(buffer);
        }
        return list;
    }
}
