import java.io.File;
import java.io.IOException;

public class AddGitKeep {
    public static void main(String[] args) {
        File fileTree = new File("E:\\idea\\java");
        deepSearch(fileTree);
    }

    public static void deepSearch(File file) {
        if (!file.isDirectory()) {
            return;
        }
        if (file.isDirectory() && ".git".equals(file.getName())) {
            return;
        }
        File[] fileList = file.listFiles();
        if (fileList.length > 0) {
            for (File temp : fileList) {
                if (temp.isDirectory()) {
                    deepSearch(temp);
                }
            }
        } else {
            addGit(file);
        }

    }

    public static void addGit(File file) {
        File gitKeepFile = new File(file.getAbsolutePath() + File.separator + ".gitkeep");
        try {
            gitKeepFile.createNewFile();
            System.out.println("在" + file.getAbsolutePath() + "下创建.gitkeep");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}