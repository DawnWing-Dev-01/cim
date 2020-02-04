package com.dawnwing.framework.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dawnwing.framework.utils.StringUtils;
import com.google.zxing.common.BitMatrix;

/**
 * @description: <二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的>
 * @author: w.xL
 * @date: 2018-3-27
 */
public class MatrixToImageWriter {

    private static final Logger logger = LoggerFactory
            .getLogger(MatrixToImageWriter.class);

    //用于设置图案的颜色
    private static final int BLACK = 0xFF000000;
    //用于背景色
    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (matrix.get(x, y) ? BLACK : WHITE));
                //image.setRGB(x, y,  (matrix.get(x, y) ? Color.YELLOW.getRGB() : Color.CYAN.getRGB()));
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file,
            String logoUri) throws IOException {

        logger.info("write to file: " + file.getPath());
        BufferedImage image = toBufferedImage(matrix);
        //设置logo图标
        QRCodeFactory logoConfig = new QRCodeFactory();
        if (StringUtils.isNotEmpty(logoUri)) {
            image = logoConfig.setMatrixLogo(image, logoUri);
        }

        boolean success = ImageIO.write(image, format, file);
        if (!success) {
            logger.error("Error generating QR code image!");
            throw new IOException("Could not write an image of format "
                    + format + " to " + file);
        } else {
            logger.info("Successfully generated QR code picture!");
        }
    }

    public static void writeToStream(BitMatrix matrix, String format,
            OutputStream stream, String logoUri) throws IOException {

        BufferedImage image = toBufferedImage(matrix);

        //设置logo图标
        QRCodeFactory logoConfig = new QRCodeFactory();
        if (StringUtils.isNotEmpty(logoUri)) {
            image = logoConfig.setMatrixLogo(image, logoUri);
        }

        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format "
                    + format);
        }
    }
}
