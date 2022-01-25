package com.momolela.io.file;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

/**
 * DefaultResourceLoader 通过路径获取文件资源，然后获取文件对象，这是 spring 提供的功能
 */
public class IOFile03FileLoaderInSpring {
    public static void main(String[] args) throws IOException {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource(".gitkeep"); // 默认是 classpath:（target/classes） 下
        if (resource.exists()) {
            File file = resource.getFile();
            System.out.println(file.getAbsolutePath());
        }
    }
}
