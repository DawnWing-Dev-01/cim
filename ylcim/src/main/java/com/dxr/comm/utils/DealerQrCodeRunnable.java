package com.dxr.comm.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dawnwing.framework.utils.IoUtils;

/**
 * @description: <经营者二维码线程类>
 * @author: w.xL
 * @date: 2018-4-19
 */
public class DealerQrCodeRunnable implements Runnable {

    protected static final Logger logger = LoggerFactory
            .getLogger(DealerQrCodeRunnable.class);

    private String dealerName;

    private String dealerQrCodePath;

    private String targetPath;

    private Integer a4width;

    private Integer a4height;

    /**
     * 背景图BufferedImage对象
     */
    private BufferedImage bgImgBi;

    public DealerQrCodeRunnable(Integer a4width, Integer a4height) {
        this.a4width = a4width;
        this.a4height = a4height;
    }

    @Override
    public void run() {
        compositePprintQrCode();
    }

    /**
     * 合成需要打印的二维码, 带文字A4纸大小
     */
    public void compositePprintQrCode() {
        OutputStream os = null;
        try {
            // 创建新的BufferedImage作为背景, 返回BufferedImage对象 
            BufferedImage buffImg = new BufferedImage(a4width, a4height,
                    BufferedImage.TYPE_INT_RGB);

            //得到画笔对象  
            Graphics2D g2 = buffImg.createGraphics();
            // 设置背景颜色
            g2.setBackground(Color.WHITE);
            g2.clearRect(0, 0, a4width, a4height);

            //g2.setPaint(Color.RED);

            // 设置画笔颜色, 默认黑色
            Color mycolor = new Color(0, 0, 0);//new Color(0, 0, 255);  
            g2.setColor(mycolor);

            // 背景图画在画板上, 大小和画板一致
            g2.drawImage(bgImgBi, 0, 0, a4width, a4height, null);

            // 读取二维码图片, 返回 BufferedImage对象
            BufferedImage imgQrCodeBi = ImageIO
                    .read(new File(dealerQrCodePath));

            // 二维码画在大图上, 居中显示, 距离顶部距离1300px, 二维码大小为1180*1180px
            g2.drawImage(imgQrCodeBi, (a4width - 860) / 2, 1630, 860, 860, null);

            FontRenderContext context = g2.getFontRenderContext();
            Font titleFont = new Font("微软雅黑", Font.PLAIN, 66);
            g2.setFont(titleFont);
            Rectangle2D bounds = titleFont.getStringBounds(dealerName, context);
            double titleX = (a4width - bounds.getWidth()) / 2;

            // 在图片上话经营者名称, titleX,600 表示这段文字在图片上的位置(x,y)
            g2.drawString(dealerName, (int) titleX, 2700);

            // 给二维码绘制边框 
            /*BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND);
            // 设置笔画对象
            g2.setStroke(stroke);
            //指定弧度的圆角矩形
            RoundRectangle2D.Float round = new RoundRectangle2D.Float(
                    (a4width - 1181) / 2, 1300, 1180, 1180, 0, 0);
            g2.setColor(new Color(153, 153, 153));
            // 绘制矩形
            g2.draw(round);*/

            // 设置扫描提示文字
            /*Font smallFont = new Font("微软雅黑", Font.PLAIN, 40);
            g2.setFont(smallFont);

            g2.setColor(mycolor);
            String s1 = "请使用微信扫描二维码查询";
            Rectangle2D s1smallbounds = smallFont.getStringBounds(s1, context);
            double s1X = (a4width - s1smallbounds.getWidth()) / 2;
            g2.drawString(s1, (int) s1X, 2550);
            String s2 = "\"经营者详细信息\"";
            Rectangle2D s2smallbounds = smallFont.getStringBounds(s2, context);
            double s2X = (a4width - s2smallbounds.getWidth()) / 2;
            g2.drawString(s2, (int) s2X, 2600);*/

            g2.dispose();
            buffImg.flush();

            // 将合成的图片输出到target目标路径下
            os = new FileOutputStream(targetPath);
            ImageIO.write(buffImg, "jpg", os);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            IoUtils.unifyClose(os);
        }
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public void setDealerQrCodePath(String dealerQrCodePath) {
        this.dealerQrCodePath = dealerQrCodePath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public void setBgImgBi(BufferedImage bgImgBi) {
        this.bgImgBi = bgImgBi;
    }
}
