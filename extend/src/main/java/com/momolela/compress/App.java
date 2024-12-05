package com.momolela.compress;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 版权：版权所有 bsoft 保留所有权力。
 *
 * @author <a href="mailto:sunzj@bsoft.com.cn">sunzj</a>
 * @description
 * @date 2024/6/25 09:40
 */
public class App {

    public static void main(String[] args) throws Exception {
        // 压缩 base64
        // Path path = Paths.get("/Users/sunzj/idea/java/srcFile.txt");
        // String originalText = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        // String s = encode(originalText);
        // System.out.println("original text length: " + originalText.length());
        // System.out.println("compressed text length: " + s.length());
        // System.out.println("decode text length: " + decode(s).length());

        // 解压
        // Path path = Paths.get("/Users/sunzj/idea/java/compressedFile.txt");
        // String compressedText = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        // System.out.println("decode text length: " + decode(compressedText).length());
        // System.out.println("decode text: " + decode(compressedText));

        // pdfCompressEncode();

        pdfCompress("/Users/sunzj/idea/java/ddd.pdf", "/Users/sunzj/idea/java/dddCompressed.pdf", 0.1f);

        // imgCompress("/Users/sunzj/idea/java/szj.jpg", "/Users/sunzj/idea/java/szj_compressed.png", 0.5f);

    }

    /**
     * 图片压缩
     *
     * @param inputImagePath  待压缩图片地址
     * @param outputImagePath 压缩后图片地址
     * @param quality         压缩质量
     * @throws IOException
     */
    private static void imgCompress(String inputImagePath, String outputImagePath, float quality) throws IOException {
        File input = new File(inputImagePath);
        BufferedImage image = ImageIO.read(input);

        // 获取 JPEG 图片写入器
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No writers found");
        }

        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();

        // 设置压缩模式和质量
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        // 准备输出文件
        File output = new File(outputImagePath);
        try (FileImageOutputStream ios = new FileImageOutputStream(output)) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(image, null, null), param);
        }

        writer.dispose();
    }

    /**
     * pdf 压缩
     *
     * @param inputPdfPath  待压缩图片地址
     * @param outputPdfPath 压缩后图片地址
     * @param quality       PDF 中图片的长宽缩放比
     * @throws IOException
     */
    public static void pdfCompress(String inputPdfPath, String outputPdfPath, float quality) throws Exception {

        PdfReader reader;
        try {
            reader = new PdfReader(new FileInputStream(inputPdfPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(reader, new PdfWriter(swapStream));

        long n = reader.getLastXref();
        PdfObject object;
        PdfStream stream;
        for (int i = 0; i < n; i++) {
            object = pdfDocument.getPdfObject(i);
            if (object == null || !object.isStream()) {
                continue;
            }
            stream = (PdfStream) object;
            PdfObject subtype = stream.get(PdfName.Subtype);
            if (subtype != null && subtype.toString().equals(PdfName.Image.toString())) {
                PdfName filter = stream.toString().contains("Filter") ? (PdfName) stream.get(PdfName.Filter) : null;
                PdfName colorSpace = stream.toString().contains("ColorSpace") ? (PdfName) stream.get(PdfName.ColorSpace) : null;
                PdfNumber length = stream.toString().contains("Length") ? (PdfNumber) stream.get(PdfName.Length) : null;
                PdfNumber colorTransform = stream.toString().contains("ColorTransform") ? (PdfNumber) stream.get(PdfName.ColorTransform) : null;
                PdfStream sMask = stream.toString().contains("SMask") ? (PdfStream) stream.get(PdfName.SMask) : null;
                PdfImageXObject image = new PdfImageXObject(stream);
                int i1 = image.getPdfObject().getAsNumber(PdfName.BitsPerComponent).intValue();
                if (i1 != 8) {
                    continue;
                }
                BufferedImage bi = image.getBufferedImage();
                if (bi == null) {
                    continue;
                }
                assert filter != null;
                if (filter.equals(PdfName.FlateDecode)) {
                    continue;
                }
                int width = (int) (bi.getWidth() * quality);
                int height = (int) (bi.getHeight() * quality);
                BufferedImage img = new BufferedImage(width, height, bi.getType());
                AffineTransform at = AffineTransform.getScaleInstance(quality, quality);
                Graphics2D g = img.createGraphics();
                g.drawRenderedImage(bi, at);
                ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
                ImageIO.write(img, "JPG", imgBytes);
                stream.clear();
                stream.setData(imgBytes.toByteArray(), false);
                stream.put(PdfName.BitsPerComponent, new PdfNumber(8));
                stream.put(PdfName.Type, PdfName.XObject);
                stream.put(PdfName.Subtype, PdfName.Image);
                stream.put(PdfName.Width, new PdfNumber(width));
                stream.put(PdfName.Height, new PdfNumber(height));
                if (filter != null) {
                    stream.put(PdfName.Filter, filter);
                }
                if (colorSpace != null) {
                    stream.put(PdfName.ColorSpace, colorSpace);
                }
                if (length != null) {
                    stream.put(PdfName.Length, length);
                }
                if (colorTransform != null) {
                    stream.put(PdfName.ColorTransform, colorTransform);
                }
                if (sMask != null) {
                    stream.put(PdfName.SMask, sMask);
                }
            }
        }
        // 将数据写入到流
        pdfDocument.close();
        reader.close();
        FileOutputStream fileOut = new FileOutputStream(outputPdfPath);
        fileOut.write(swapStream.toByteArray());
    }

    /**
     * pdf 先压缩再编码再对编码进行压缩
     */
    public static void pdfCompressEncode() throws Exception {
        File sourceFilePath = new File("/Users/sunzj/idea/java/aaa.pdf");
        File targetFilePath = new File("/Users/sunzj/idea/java/aaa.pdf.gz");
        try (FileInputStream fis = new FileInputStream(sourceFilePath);
             FileOutputStream fos = new FileOutputStream(targetFilePath);
             GZIPOutputStream gzipOS = new GZIPOutputStream(fos)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gzipOS.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // gz 文件转 byte
        FileInputStream inputStream = new FileInputStream(targetFilePath);
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = gzipInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        byte[] byteArray = outputStream.toByteArray();
        inputStream.close();
        gzipInputStream.close();
        outputStream.close();
        // byte 压缩转 base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOut = new GZIPOutputStream(baos)) {
            gzipOut.write(byteArray);
        }
        byte[] bytes = baos.toByteArray();
        String baseStr = Base64.getEncoder().encodeToString(bytes);
        // base64 写入 srcFile.txt
        File srcFile = new File("/Users/sunzj/idea/java/srcFile.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(srcFile);
        fileOutputStream.write(baseStr.getBytes());
        fileOutputStream.close();
        // 压缩 base64
        Path path = Paths.get("/Users/sunzj/idea/java/srcFile.txt");
        String originalText = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        String s = encode(originalText);
        System.out.println("original text length: " + originalText.length());
        System.out.println("compressed text length: " + s.length());
        System.out.println("decode text length: " + decode(s).length());
    }

    /**
     * 文本压缩
     *
     * @param s
     * @return
     */
    public static String encode(String s) {
        try {
            StringBuilder out = new StringBuilder();
            StringBuilder phrase = new StringBuilder();
            HashMap<String, Integer> dictionary = new HashMap<>();
            int code = 57344;

            char[] chars = s.toCharArray();
            phrase.append(chars[0]);

            for (int i = 1; i < chars.length; i++) {
                char currentChar = chars[i];
                if (dictionary.containsKey(phrase.toString() + currentChar)) {
                    phrase.append(currentChar);
                } else {
                    if (phrase.length() > 1) {
                        out.append(Character.toChars(dictionary.get(phrase.toString())));
                    } else {
                        out.append(phrase.charAt(0));
                    }
                    dictionary.put(phrase.toString() + currentChar, code);
                    code++;
                    phrase.setLength(0);
                    phrase.append(currentChar);
                }
            }
            if (phrase.length() > 1) {
                out.append(Character.toChars(dictionary.get(phrase.toString())));
            } else {
                out.append(phrase.charAt(0));
            }

            StringBuilder result = new StringBuilder();
            for (int value : out.toString().codePoints().toArray()) {
                result.append(Character.toChars(value));
            }

            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文本解压缩
     *
     * @param dataAsText
     * @return
     */
    public static String decode(String dataAsText) {
        try {
            StringBuilder out = new StringBuilder();
            StringBuilder phrase = new StringBuilder();
            HashMap<Integer, String> dictionary = new HashMap<>();
            int code = 57344;

            int[] data = dataAsText.codePoints().toArray();
            phrase.append(Character.toChars(data[0]));

            String currentChar = phrase.toString();
            String oldPhrase = currentChar;
            out.append(currentChar);

            for (int i = 1; i < data.length; i++) {
                int currentCode = data[i];
                String phraseStr;
                if (currentCode < 57344) {
                    phraseStr = Character.toString(Character.toChars(data[i])[0]);
                } else {
                    phraseStr = dictionary.containsKey(currentCode) ? dictionary.get(currentCode) : (oldPhrase + currentChar);
                }
                out.append(phraseStr);
                currentChar = phraseStr.substring(0, 1);
                dictionary.put(code, oldPhrase + currentChar);
                code++;
                oldPhrase = phraseStr;
            }

            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
