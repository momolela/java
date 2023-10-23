package com.momolela.adapterpattern;

public class App {
    public static void main(String[] args) {
        Mp3Player player = new Mp3Player();

        // 添加适配器之后
        player.play("mp3");
        player.play("mp4");
        player.play("vlc");
    }
}
