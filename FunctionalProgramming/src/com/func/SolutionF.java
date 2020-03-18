package com.func;

import com.func.util.TextUtil;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class SolutionF {
    private List<String> getLines(Path path) {
        List<String> lines = new ArrayList<>();
        try {
            File file = path.toFile();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();;
            while (line != null) {
                lines.add(TextUtil.cleanText(line));

                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public Map<String, Long> processTxtFile(Path path) {
        return this.getLines(path)
                   .stream()
                   .flatMap(line -> { return Arrays.stream(line.split(" ")); })
                   .filter(word -> { return TextUtil.hasContent(word); } )
                   .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
    }
}
