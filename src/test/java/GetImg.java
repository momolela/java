import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetImg {

    // 地址
    private static List<String> URL = new ArrayList<>(Arrays.asList("https://m.docin.com/renren_379660010"));

    private static final String prefix = "/Users/sunzj/Desktop/me";

    // 编码
    private static final String ECODING = "UTF-8";
    // 获取img标签正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "https:\"?(.*?)(\"|>|\\s+)";


    public static void main(String[] args) throws Exception {

        GetImg cm = new GetImg();
        for (String url : URL) {
            TimeUnit.SECONDS.sleep(6);
            //获得html文本内容
            String HTML = cm.getHTML(url);
            //获取图片标签
            List<String> imgUrl = cm.getImageUrl(HTML);
            //获取图片src地址
            List<String> imgSrc = cm.getImageSrc(imgUrl);
            //下载图片
            cm.Download(imgSrc);
        }
    }


    /***
     * 获取HTML内容
     *
     * @param url
     * @return
     * @throws Exception
     */
    private String getHTML(String url) throws Exception {
        URL uri = new URL(url);
        URLConnection connection = uri.openConnection();
        connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36 Edg/98.0.1108.62");
        InputStream in = connection.getInputStream();
        byte[] buf = new byte[1024 * 10];
        StringBuffer sb = new StringBuffer();
        while ((in.read(buf, 0, buf.length)) > 0) {
            sb.append(new String(buf, ECODING));
        }
        in.close();
        return sb.toString();
    }

    /***
     * 获取ImageUrl地址
     *
     * @param HTML
     * @return
     */
    private List<String> getImageUrl(String HTML) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
        List<String> listImgUrl = new ArrayList<>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group());
        }
        return listImgUrl;
    }

    /***
     * 获取ImageSrc地址
     *
     * @param listImageUrl
     * @return
     */
    private List<String> getImageSrc(List<String> listImageUrl) {
        List<String> listImgSrc = new ArrayList<>();
        for (String image : listImageUrl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImgSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        }
        return listImgSrc;
    }

    /***
     * 下载图片
     *
     * @param listImgSrc
     */
    private void Download(List<String> listImgSrc) {
        for (String url : listImgSrc) {
            String imageName = prefix + "/" + url.substring(url.lastIndexOf("/") + 1, url.contains("?") ? url.indexOf("?") : url.length());
            try {
                // URL uri = new URL(url);
                // InputStream in = uri.openStream();

                URL uri = new URL(url);
                URLConnection connection = uri.openConnection();
                connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36 Edg/98.0.1108.62");
                InputStream in = connection.getInputStream();

                FileOutputStream fo = new FileOutputStream(new File(imageName));
                byte[] buf = new byte[1024];
                int length = 0;
                System.out.println("开始下载:" + url);
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fo.write(buf, 0, length);
                }
                in.close();
                fo.close();
                System.out.println(imageName + "下载完成");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(imageName + "下载失败");
            }
        }
    }

}
