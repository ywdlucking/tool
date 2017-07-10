package com.ywd.tool.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by admin on 2017/7/6.
 */
public class PdfToImg {

    /**
     * PDFBOX
     * 在Linux环境会出现中午字符乱码
     * 原因：系统缺少对应的字体库
     *
     * 解决：容器环境自己添加字体库到/usr/share/fonts
     * 如:COPY simsun.ttf /usr/share/fonts/simsun.ttf
     * @throws Exception
     */
    public static void pdfToImageTheOne() throws Exception{
        File file = new File("/home/master/test.pdf");
        OutputStream outImage = new FileOutputStream("/home/master/test-box.jpg");
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            BufferedImage src = renderer.renderImage(0, 1.25f);   //第二个参数越大生成图片分辨率越高。
            int height = src.getHeight();//得到源图宽1053
            int width = src.getWidth();//得到源图长744

            int pageCount = doc.getNumberOfPages();
            BufferedImage bg_image = new BufferedImage(width, height*pageCount, BufferedImage.TYPE_INT_RGB);
            Graphics g = bg_image.getGraphics();
            int h=0;
            for(int i=0; i<pageCount; i++){
                BufferedImage image = renderer.renderImage(i, 1.25f);   //第二个参数越大生成图片分辨率越高。
                g.drawImage(image, 0, h, width, height,null);
                h=h+height;
            }
            g.dispose();
            ImageIO.write(bg_image, "jpg", outImage);
        }catch  (Exception e){
            e.printStackTrace();
        }finally{
            outImage.close();
        }
    }

    /**
     * ICEPDF
     * @throws Exception
     */
    public static void pdfToImageTheTwo() throws Exception {
        String filePath = "/home/master/test.pdf";
        Document document = new Document();
        OutputStream outImage = new FileOutputStream("/home/master/test-ice.jpg");
        document.setFile(filePath);
        float scale = 1.3f;
        float rotation = 0f;
        BufferedImage src = (BufferedImage)document.getPageImage(0, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, scale);
        int height = src.getHeight();//得到源图宽
        int width = src.getWidth();//得到源图长
        int pageCount = document.getNumberOfPages();
        BufferedImage bg_image = new BufferedImage(width, height*pageCount, BufferedImage.TYPE_INT_RGB);
        Graphics g = bg_image.getGraphics();
        int h = 0;
        for (int i = 0; i < pageCount; i++) {
            BufferedImage image = (BufferedImage)document.getPageImage(i,GraphicsRenderingHints.SCREEN,Page.BOUNDARY_CROPBOX, rotation, scale);
            g.drawImage(image, 0, h, width, height,null);
            h=h+height;
            image.flush();
        }
        document.dispose();
        g.dispose();
        ImageIO.write(bg_image, "jpg", outImage);
    }

}
