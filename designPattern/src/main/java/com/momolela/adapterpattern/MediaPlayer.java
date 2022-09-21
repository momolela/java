package com.momolela.adapterpattern;

public interface MediaPlayer {
    default void oldPlay() {
        System.out.println("old media play");
    }

    void newPlay(String mediaType);

}
