package com.gazman.bls.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class RandomHolder {
    public static final SecureRandom RANDOM;
    static {
        SecureRandom random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            random = new SecureRandom();
            e.printStackTrace();
        }
        RANDOM = random;
    }
}
