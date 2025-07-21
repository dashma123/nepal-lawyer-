package com.eNyaya.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtil {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Logger LOGGER = Logger.getLogger(PasswordUtil.class.getName());

    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            LOGGER.log(Level.SEVERE, "Key derivation failed", ex);
            return null;
        }
    }

    public static String encrypt(String email, String password) {
        try {
            byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
            byte[] iv = getRandomNonce(IV_LENGTH_BYTE);
            SecretKey key = getAESKeyFromPassword(email.toCharArray(), salt);

            if (key == null) {
                LOGGER.severe("Encryption failed: key is null");
                return null;
            }

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            byte[] cipherText = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));

            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + salt.length + cipherText.length);
            byteBuffer.put(iv);
            byteBuffer.put(salt);
            byteBuffer.put(cipherText);

            return Base64.getEncoder().encodeToString(byteBuffer.array());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Encryption failed", ex);
            return null;
        }
    }

    public static String decrypt(String encryptedPassword, String email) {
        // Check for null or empty inputs early
        if (encryptedPassword == null || email == null || encryptedPassword.isEmpty() || email.isEmpty()) {
            LOGGER.severe("Decryption failed: encryptedPassword or email is null or empty");
            return null;
        }

        try {
            // Decode the Base64 encoded encrypted password
            byte[] decoded = Base64.getDecoder().decode(encryptedPassword);
            ByteBuffer bb = ByteBuffer.wrap(decoded);

            // Extract IV (Initialization Vector) from the decoded bytes
            byte[] iv = new byte[IV_LENGTH_BYTE];
            bb.get(iv);

            // Extract salt from the decoded bytes
            byte[] salt = new byte[SALT_LENGTH_BYTE];
            bb.get(salt);

            // Extract the ciphertext (encrypted password) from the remaining bytes
            byte[] cipherText = new byte[bb.remaining()];
            bb.get(cipherText);

            // Generate the AES key from the password (email) and salt
            SecretKey key = getAESKeyFromPassword(email.toCharArray(), salt);
            if (key == null) {
                LOGGER.severe("Decryption failed: key is null for email: " + email);
                return null;
            }

            // Initialize the Cipher for decryption
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            // Perform the decryption
            byte[] plainText = cipher.doFinal(cipherText);

            // Return the decrypted password as a UTF-8 string
            return new String(plainText, StandardCharsets.UTF_8);

        } catch (Exception ex) {
            // Log the exception with more detailed context
            LOGGER.log(Level.SEVERE, "Decryption failed for email: " + email, ex);
            return null;
        }
    }

}
