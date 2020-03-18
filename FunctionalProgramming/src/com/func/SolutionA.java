package com.func;

import com.func.util.TextUtil;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SolutionA {
    public Map<String, Long> processTxtFile(Path path) {
        Map<String, Long> dictionary = new HashMap<>();

        try {
            File file = path.toFile();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();;
            while (line != null) {
                String cleanLine = TextUtil.cleanText(line);
                String[] words = cleanLine.split(" ");
                for (String word : words) {
                    if (TextUtil.hasContent(word)) {
                        Long count = (dictionary.containsKey(word)) ? dictionary.get(word) + 1 : 1;
                        dictionary.put(word, count);
                    } else {
                        continue;
                    }
                }

                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dictionary;
    }
}
