package com.example.epaper.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

public class ByteArrayUtil {

    public static byte[] readFileToByteArray(File f) {
        byte[] buf = new byte[4096];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            FileInputStream fis = new FileInputStream(f);
            int read = fis.read(buf);
            while (read > 0) {
                baos.write(buf, 0, read);
                read = fis.read(buf);
            }
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }

    public static BufferedImage byteArrayToBufferedImage(byte[] imgdata) {
        BufferedImage bi = null;
        var bis = new ByteArrayInputStream(imgdata);
        try {
            bi = ImageIO.read(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bi;
    }

    public static File byteArrayToTmpFile(byte[] filedata) {
        File out = null;
        try {
            out = File.createTempFile("tmpfile", ".bin");
            var fos = new FileOutputStream(out);
            fos.write(filedata);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

}
