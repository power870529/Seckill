package com.mmall.Seckill.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPass2FormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPass2DbPass(String input, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + input + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPass2DbPass(String inputPass, String saltDb) {
        String formPass = inputPass2FormPass(inputPass);
        String dbPass = formPass2DbPass(formPass, saltDb);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPass2DbPass("123456", "1a2b3c4d"));
    }
}
