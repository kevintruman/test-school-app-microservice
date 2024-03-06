package com.schfoo.force.helper.util;

import java.util.Random;

public class UsernameUtil {

    public static String generateUsername(String fullName) {
        return fullName.trim().replaceAll("\\s", ".");
    }

    private static final String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lowercase = "abcdefghijklmnopqrstuvwxyz";
    private static final String num = "0123456789";

    public static String generateRandomStringWithLength(int minLength, int maxLength) {
        Random random = new Random();
        int length = random.nextInt(maxLength - minLength + 1) + minLength;

        StringBuilder randomString = new StringBuilder();
        String characters = lowercase + num;

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public static String generateRandomString() {
        return generateRandomStringWithLength(6, 8);
    }

}
