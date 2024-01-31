package com.example.epaper.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;

import com.example.epaper.util.ByteArrayUtil;
import com.example.epaper.util.ImageConverter;
import com.github.mizosoft.methanol.MediaType;
import com.github.mizosoft.methanol.Methanol;
import com.github.mizosoft.methanol.MultipartBodyPublisher;
import com.github.mizosoft.methanol.MutableRequest;

public class Uploader {

    private String apAddress;

    public Uploader(String apAddress) {
        this.apAddress = apAddress;
    }

    public Uploader() {
        this("192.168.1.10");
    }

    public void uploadImage(BufferedImage image, String mac, boolean dither) {
        String uri = "http://" + apAddress + "/imgupload";

        if(image.getWidth() > 1024 || image.getHeight() > 1024) {
            throw new RuntimeException("Image max. dimensions exceeded (1024x1024)");
        }
        
        var bimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        bimg.getGraphics().drawImage(image, 0, 0, null);
        var jpgBytes = ImageConverter.toBytes(bimg, "image/jpeg");
        var imgfile = ByteArrayUtil.byteArrayToTmpFile(jpgBytes);

        Methanol client = Methanol.create();
        try {
            var multipartBody = MultipartBodyPublisher.newBuilder()
                .textPart("dither", dither ? 1 : 0)
                .textPart("mac", mac)
                .filePart("file", Path.of(imgfile.getAbsolutePath()), MediaType.IMAGE_JPEG)
                .build();
            var request = MutableRequest.POST(uri, multipartBody);

            client.send(request, BodyHandlers.ofString());
        } catch(Exception e) {
            throw new RuntimeException(e);
        } finally {
            imgfile.delete();
        }
    }

    public void uploadImage(byte[] imgdata, String mac, boolean dither) throws Exception {
        var bimg = ByteArrayUtil.byteArrayToBufferedImage(imgdata);
        uploadImage(bimg, mac, dither);
    }


    public static void main(String[] args) throws Exception {
        File imageFile = new File("/tmp/parrots.png");

        byte[] imageData = ByteArrayUtil.readFileToByteArray(imageFile);


        Uploader uploader = new Uploader();
        uploader.uploadImage(imageData, "0000021E733A7430", true);

    }
    
}
