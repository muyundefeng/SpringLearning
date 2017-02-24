package com.muyundefeng.spring.Utils;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lisheng on 17-2-24.
 */
public class HashUtils {
    public static  String generateHash(String text) {
        if(text != null && !text.equals("")) {
            MessageDigest digest = null;

            try {
                digest = MessageDigest.getInstance("SHA-256");
                byte[] e = digest.digest(text.getBytes("UTF-8"));
                return Hex.encodeHexString(e);
            } catch (NoSuchAlgorithmException var3) {
            } catch (UnsupportedEncodingException var4) {
            }

            return null;
        } else {
            return null;
        }
    }
}
