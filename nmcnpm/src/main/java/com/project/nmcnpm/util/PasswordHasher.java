package com.project.nmcnpm.util;

import java.math.BigInteger;

public class PasswordHasher {
    public static String hashStringTo10Digits(String str) {
        BigInteger hash = BigInteger.ZERO;
        BigInteger modulus = new BigInteger("10000000000"); 
        for (int i = 0; i < str.length(); i++) {
            BigInteger charCode = BigInteger.valueOf(str.charAt(i));
            hash = (hash.multiply(BigInteger.valueOf(31)).add(charCode)).mod(modulus);
        }
        String result = hash.toString();
        while (result.length() < 10) {
            result = "0" + result;
        }
        return result;
    }
}
