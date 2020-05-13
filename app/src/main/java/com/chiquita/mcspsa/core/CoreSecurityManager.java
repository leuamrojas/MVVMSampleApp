package com.chiquita.mcspsa.core;

import android.util.Base64;
import com.chiquita.mcspsa.BuildConfig;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CoreSecurityManager {

    private static final Object LOCK = new Object();

    private static CoreSecurityManager singleton = new CoreSecurityManager();

    private CoreSecurityManager() {
    }

    public static CoreSecurityManager getInstance() {
        if (singleton == null) {
            synchronized (LOCK) {
                singleton = new CoreSecurityManager();
            }
        }
        return singleton;
    }

    public String encrypt(String value) {
        try {
            SecretKey key = new SecretKeySpec(Base64.decode(BuildConfig.EK.getBytes(), Base64.NO_WRAP), "AES");
            AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decode(BuildConfig.EIV.getBytes(), Base64.NO_WRAP));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] valueBytes = value.getBytes("UTF8");
            byte[] encrypted = cipher.doFinal(valueBytes);
            return new String(Base64.encode(encrypted, Base64.NO_WRAP));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String decrypt(String encrypted) {
        try {
            SecretKey key = new SecretKeySpec(Base64.decode(BuildConfig.EK.getBytes(), Base64.NO_WRAP), "AES");
            AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decode(BuildConfig.EIV.getBytes(), Base64.NO_WRAP));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.NO_WRAP));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
