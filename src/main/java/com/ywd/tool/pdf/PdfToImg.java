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
     * @throws Exception
     */
    public static void pdfToImageTheOne() throws Exception{
        File file = new File("/home/master/test.pdf");
        OutputStream outImage = new FileOutputStream("C:/workspace/box.jpg");
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
        String filePath = "c:/workspace/86310020170230003601.pdf";
        Document document = new Document();
        OutputStream outImage = new FileOutputStream("C:/workspace/policy-ice.jpg");
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
