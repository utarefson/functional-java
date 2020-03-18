package com.func.model;

public class WordContainer {
    private String word;
    private long frequency;

    public WordContainer(String word, long frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() { return this.word; }
    public long getFrequency() { return this.frequency; }
}
