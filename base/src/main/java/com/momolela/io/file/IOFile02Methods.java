package com.momolela.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class IOFile02Methods {

    public static void main(String[] args) throws IOException {
        // 1 创建，createNewFile 不存在就创建，返回 true，存在就不创建，返回 false；和 IO 流不一样，IO 流只要流对象一创建文件就创建，如果存在还会覆盖。
        File f1 = new File("test-file-create.txt");
        boolean newFile = f1.createNewFile();
        System.out.println("文件创建是否成功：" + newFile);

        // File dir1 = new File("abc");
        // dir1.mkdir(); // 创建单级目录
        // File dir2 = new File("abc\\efg");
        // dir2.mkdirs(); // 创建多级目录


        // 2 删除
        File f2 = new File("file.txt");
        boolean delete = f2.delete();
        System.out.println("文件删除是否成功：" + delete);

        File f3 = new File("test-file-create.txt");
        f3.deleteOnExit(); // 文件在退出时删除
        // f3.delete(); // 有的时候，上面一顿操作，抛出了异常，那删除文件就执行不了，就形成了垃圾文件；甚至于由于文件占用，放在 finally 也不能删除，所以就只能使用 退出时删除


        // 3 判断
        File f4 = new File("file.txt");
        boolean exists = f4.exists();// 判断文件是否存在
        if (exists) {
            System.out.println(f4.isDirectory()); // 判断是否为目录，判断之前要先确定文件存在
            System.out.println(f4.isFile()); // 判断是否为文件，判断之前要先确定文件存在
            System.out.println(f4.isHidden()); // 判断文件是否为隐藏文件
            System.out.println(f4.isAbsolute()); // 判断文件是否为绝对路径
        }


        // 4 获取
        File f5 = new File("test-file-create.txt");
        System.out.println(f5.getName()); // 返回文件名
        System.out.println(f5.getPath()); // 返回相对路径
        System.out.println(f5.getAbsolutePath()); // 返回绝对路径
        System.out.println(f5.getAbsoluteFile()); // 返回绝对路径下的文件对象
        System.out.println(f5.getParent()); // 返回上级
        System.out.println(f5.lastModified()); // 返回修改时间 long
        System.out.println(f5.length()); // 返回文件大小 long

        listRoots(); // 列出所有的盘符
        listAllFilesNameInC(); // 列出C盘目录下的所有文件，包含文件和目录和隐藏文件
        listSomeFilesNameInC(); // 列出C盘目录下指定的文件

        File fileDir = new File("E:\\software");
        listFilesInCByRecurrence(fileDir, 0); // 递归列出文件夹下所有的文件


        // 5修改
        File f6 = new File("test-file-create.txt");
        File f7 = new File("test-file-create-cut.txt");
        System.out.println(f6.renameTo(f7)); // 名称修改，有剪切的意思
    }

    public static void listRoots() {
        File[] roots = File.listRoots();
        for (File f : roots) {
            System.out.println(f);
        }
    }

    public static void listAllFilesNameInC() {
        File f = new File("c:\\"); // 必须是一个目录且存在，不然 list() 返回的是 null ，接下来就会空指针
        String[] list = f.list(); // 返回的是 String[]
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static void listSomeFilesNameInC() {
        File f = new File("c:\\");
        String[] list = f.list(new FilenameFilter() { // 返回的是 String[]
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("Program");
            }
        });
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static void listFilesInCByRecurrence(File file, int level) {
        System.out.println(getLevel(level) + file);
        level++;
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                listFilesInCByRecurrence(f, level);
            } else {
                System.out.println(getLevel(level) + f);
            }
        }
    }

    public static String getLevel(int level) {
        StringBuilder sb = new StringBuilder();
        sb.append("|-- ");
        for (int i = 0; i < level; i++) {
            // sb.append("|-- ");
            sb.insert(0, "|   ");
        }
        return sb.toString();
    }

}
