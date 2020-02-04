package com.dxr.webui.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageTest {

    public static void main(String[] args) {
        wirteImg2Img("杨凌一诺千金餐饮管理有限公司",
                "d:/402880e962b462f30162b5a5e8750001.jpg");
    }

    public static void wirteImg2Img(String dealerName, String imgQrCode) {
        try {
            //1.jpg是你的 主图片的路径  
            int width = 2480;
            int height = 3508;

            //解码当前JPEG数据流，返回BufferedImage对象 
            BufferedImage buffImg = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            //得到画笔对象  
            Graphics2D g2 = buffImg.createGraphics();
            // 设置背景颜色
            g2.setBackground(Color.WHITE);
            g2.clearRect(0, 0, width, height);
            
            g2.setPaint(Color.RED);

            Color mycolor = new Color(0, 0, 0);//new Color(0, 0, 255);  
            g2.setColor(mycolor);

            FontRenderContext context = g2.getFontRenderContext();
            Font titleFont = new Font("微软雅黑", Font.BOLD, 100);
            g2.setFont(titleFont);
            Rectangle2D bounds = titleFont.getStringBounds(dealerName, context);
            double titleX = (width - bounds.getWidth()) / 2;

            //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。  
            g2.drawString(dealerName, (int) titleX, 600);

            //创建你要附加的图象。  
            BufferedImage imgQrCodeBi = ImageIO.read(new File(imgQrCode));

            //将小图片绘到大图片上。  
            //5,300 .表示你的小图片在大图片上的位置。  
            g2.drawImage(imgQrCodeBi, (width - 1181) / 2, 1300, 1180, 1180,
                    null);

            //绘制边框 
            BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND);
            // 设置笔画对象
            g2.setStroke(stroke);
            //指定弧度的圆角矩形
            RoundRectangle2D.Float round = new RoundRectangle2D.Float(
                    (width - 1181) / 2, 1300, 1180, 1180, 0, 0);
            g2.setColor(new Color(153, 153, 153));
            // 绘制圆弧矩形
            g2.draw(round);

            Font smallFont = new Font("微软雅黑", Font.PLAIN, 40);
            g2.setFont(smallFont);

            g2.setColor(mycolor);
            String s1 = "请使用微信扫描二维码查询";
            Rectangle2D s1smallbounds = smallFont.getStringBounds(s1, context);
            double s1X = (width - s1smallbounds.getWidth()) / 2;
            g2.drawString(s1, (int) s1X, 2550);
            String s2 = "\"经营者详细信息\"";
            Rectangle2D s2smallbounds = smallFont.getStringBounds(s2, context);
            double s2X = (width - s2smallbounds.getWidth()) / 2;
            g2.drawString(s2, (int) s2X, 2600);

            g2.dispose();

            OutputStream os = new FileOutputStream("d:/union.jpg");
            ImageIO.write(buffImg, "jpg", os);

            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
