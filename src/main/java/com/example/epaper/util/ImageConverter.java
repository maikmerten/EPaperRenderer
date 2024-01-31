package com.example.epaper.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.awt.image.BufferedImage;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ImageConverter {

    private static byte[] imgToJpg(BufferedImage bimg) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        float quality = 0.8f;

        try {
            ImageWriter imgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
            imgWriter.setOutput(ImageIO.createImageOutputStream(baos));

            JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(Locale.getDefault());
            jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(quality);

            // specify 4:4:4 subsampling (no chroma subsampling)
            ImageTypeSpecifier imageTypeSpec = new ImageTypeSpecifier(bimg.getColorModel(), bimg.getSampleModel());
            IIOMetadata metadata = imgWriter.getDefaultImageMetadata(imageTypeSpec, jpegParams);
            Node rootNode = (metadata != null) ? metadata.getAsTree("javax_imageio_jpeg_image_1.0") : null;
            if (rootNode != null && rootNode.getLastChild() != null) {
                // find marker nodes
                NodeList markers = rootNode.getLastChild().getChildNodes();
                for (int i = 0; i < markers.getLength(); i++) {
                    Node node = markers.item(i);
                    int childsNum = node.hasChildNodes() ? node.getChildNodes().getLength() : 0;
                    if (node.getNodeName().equalsIgnoreCase("sof") && childsNum == 3) {
                        NamedNodeMap attrMap = node.getFirstChild().getAttributes();
                        attrMap.getNamedItem("HsamplingFactor").setNodeValue("1");
                        attrMap.getNamedItem("VsamplingFactor").setNodeValue("1");
                        metadata.setFromTree("javax_imageio_jpeg_image_1.0", rootNode);
                        break;
                    }
                }
            }

            IIOImage iioImage = new IIOImage(bimg, null, metadata);
            imgWriter.write(null, iioImage, jpegParams);
            imgWriter.dispose();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return baos.toByteArray();
    }

    private static byte[] imgToPng(BufferedImage bimg, String contentType) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String format = "png";

        try {
            ImageIO.write(bimg, format, baos);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return baos.toByteArray();
    }
    

    public static byte[] toBytes(BufferedImage bimg, String contentType) {
        switch(contentType) {
            case "image/png":
                return imgToPng(bimg, contentType);
            default:
                return imgToJpg(bimg);
        }
    }

}
