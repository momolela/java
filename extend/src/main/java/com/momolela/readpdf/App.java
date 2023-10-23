package com.momolela.readpdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2023/10/7 15:41
 */
public class App {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        App app = new App();
        try {
            // 取得E盘下的SpringGuide.pdf的内容
            System.out.println("开始提取");
            File file = new File("a.pdf");
            System.out.println("文件绝对路径为：" + file.getAbsolutePath());
            app.readPdf(file);
            System.out.println("提取结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readPdf(File pdfFile) throws Exception {
        // 是否排序
        boolean sort = false;
        // 输入文本文件名称
        String textFileName = null;
        // 编码方式
        String encoding = "UTF-8";
        // 开始提取页数
        int startPage = 1;
        // 结束提取页数
        int endPage = 3;
        // 文件输入流，生成文本文件
        Writer output = null;
        // 内存中存储的 PDF Document
        PDDocument document = null;

        File outputFile = null;
        try {

            // 从本地装载文件
            // 注意参数已不是以前版本中的 URL.而是 File。
            System.out.println("开始装载文件" + pdfFile.getName());
            document = PDDocument.load(pdfFile);
            if (pdfFile.getName().length() > 4) {
                textFileName = pdfFile.getName().substring(0, pdfFile.getName().length() - 4) + ".txt";
                outputFile = new File(pdfFile.getParent(), textFileName);
                System.out.println("新文件绝对路径为：" + outputFile.getAbsolutePath());
            }
            System.out.println("装载文件结束");


            System.out.println("开始写到txt文件中");
            // 文件输入流，写入文件倒textFile
            output = new OutputStreamWriter(new FileOutputStream(outputFile), encoding);
            System.out.println("写入txt文件结束");
            // PDFTextStripper来提取文本
            PDFTextStripper stripper = null;
            stripper = new PDFTextStripper();
            // 设置是否排序
            stripper.setSortByPosition(sort);
            // 设置起始页
            stripper.setStartPage(startPage);
            // 设置结束页
            stripper.setEndPage(endPage);
            // 调用PDFTextStripper的writeText提取并输出文本
            System.out.println("开始调用writeText方法");
            stripper.writeText(document, output);
            System.out.println("调用writeText方法结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                // 关闭输出流
                output.close();
            }
            if (document != null) {
                // 关闭PDF Document
                document.close();
            }
        }
    }

}
