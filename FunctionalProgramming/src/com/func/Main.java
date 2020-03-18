package com.func;

import com.func.model.WordContainer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private final static boolean RUN_FINAL_SOLUTION = true;
    private final static String FILE_NAME = "words.txt";
    private static Path getPath() {
        return Paths.get(System.getProperty("user.dir")).resolve(FILE_NAME);
    }

    public static void main(String[] args) {
        if (RUN_FINAL_SOLUTION) {
            ExecuteSolutionF();     // 48ms
        } else {
            ExecuteSolutionA();     // 36 ms
        }
    }

    private static void ExecuteSolutionA() {
        long startTime = System.currentTimeMillis();

        SolutionA sa = new SolutionA();
        displayMap(sa.processTxtFile(getPath()));

        System.out.println("\nExecution Time: " + (System.currentTimeMillis() - startTime));
    }

    private static void ExecuteSolutionF() {
        long startTime = System.currentTimeMillis();

        SolutionF sf = new SolutionF();
        displayMap(sf.processTxtFile(getPath()));

        System.out.println("\nExecution Time: " + (System.currentTimeMillis() - startTime));
    }

    private static void displayMap(Map<String, Long> dictionary) {
        dictionary.keySet()
                .stream()
                .map(w -> {return new WordContainer(w, dictionary.get(w));})
                .sorted((wc1, wc2) -> { return Long.compare(wc2.getFrequency(), wc1.getFrequency()); })
                .forEach(wc -> { System.out.println(wc.getWord() + " " + wc.getFrequency()); });
    }
}
