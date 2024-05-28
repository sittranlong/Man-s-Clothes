package io.github.pudo58.util;

import java.util.Base64;

public class ImageBase64 {
    public static byte[] setImageBase64(String imageBase64) {
        if (imageBase64 != null) {
            String base64Image = imageBase64.split(",")[1];
            return Base64.getDecoder().decode(base64Image);
        }
        return null;
    }
}
