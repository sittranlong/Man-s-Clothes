package io.github.pudo58.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class HashableImpl implements Hashable {
    private static final String SHA256 = "SHA-256";
    private static final String AES = "AES";
    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    @Value("${hash.secret}")
    private String secretKeyAES;

    @Override
    public String encodeSHA256(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
        messageDigest.update(str.getBytes());
        return byteArray2Hex(messageDigest.digest());
    }

    @Override
    public String encodeAES(CharSequence rawPassword) {
        try {
            String password = rawPassword.toString();
            SecretKeySpec keySpec = new SecretKeySpec(secretKeyAES.getBytes(), AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(password.toString().getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matchesAES(CharSequence rawPassword, String encodedPassword) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKeyAES.getBytes(), AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] encryptedBytes = Base64.getDecoder().decode(encodedPassword);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes).equals(rawPassword.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String byteArray2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (final byte b : bytes) {
            sb.append(hex[(b & 0xF0) >> 4]);
            sb.append(hex[b & 0x0F]);
        }
        return sb.toString();
    }
}
