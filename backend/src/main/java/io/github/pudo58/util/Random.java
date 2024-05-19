package io.github.pudo58.util;

public class Random {
    public static final int LENGTH_DEFAULT = 32;
    public static final String STRING_DEFAULT = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String randomString(Integer length) {
        java.util.Random random = new java.util.Random();
        StringBuilder sb = new StringBuilder();
        if (length == null) {
            length = LENGTH_DEFAULT;
        }
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(STRING_DEFAULT.charAt(number));
        }
        return sb.toString();
    }

    public static int randomNumber(Integer min, Integer max) {
        java.util.Random random = new java.util.Random();
        if (min == null) {
            min = 0;
        }
        if (max == null) {
            max = 100;
        }
        return random.nextInt(max - min) + min;
    }

    public static int randomNumber(int length) {
        java.util.Random random = new java.util.Random();
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length);
        return random.nextInt(max - min) + min;
    }
}
