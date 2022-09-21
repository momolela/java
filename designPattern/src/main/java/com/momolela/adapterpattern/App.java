package com.momolela.adapterpattern;

public class App {
    public static void main(String[] args) {
        Mp3Player player = new Mp3Player();
        player.oldPlay();

        // 添加适配器之后
        player.newPlay("mp3");
        player.newPlay("mp4");
        player.newPlay("vlc");
    }
}
