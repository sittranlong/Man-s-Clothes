package io.github.pudo58.util;

import java.security.NoSuchAlgorithmException;

public interface Hashable {
    String encodeSHA256(String str) throws NoSuchAlgorithmException;

    String encodeAES(CharSequence rawPassword);

    boolean matchesAES(CharSequence rawPassword, String encodedPassword);
}
