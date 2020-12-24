/*
 * @author https://sunghs.tistory.com
 * Remake by devKOR_Decuple
 */

package com.devKOR_decuple.PasswordSaver.Utilities.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES128 {

    private static final Charset encodingType = StandardCharsets.UTF_8;
    private static final String instanceType = "AES/CBC/PKCS5Padding";

    private SecretKeySpec spec;
    private Cipher cipher;
    private IvParameterSpec ivSpec;

    public AES128(String passwordKey) {
        try {
            byte[] keyBytes = passwordKey.getBytes(encodingType);
            spec = new SecretKeySpec(keyBytes, "AES");
            cipher = Cipher.getInstance(instanceType);
            ivSpec = new IvParameterSpec(keyBytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(final String string) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, spec, ivSpec);
        byte[] encryptedResult = cipher.doFinal(string.getBytes(encodingType));
        return new String(Base64.getEncoder().encode(encryptedResult), encodingType);
    }

    public String decrypt(final String string) throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, spec, ivSpec);
        byte[] decryptedResult = Base64.getDecoder().decode(string.getBytes(encodingType));
        return new String(cipher.doFinal(decryptedResult), encodingType);
    }

}
