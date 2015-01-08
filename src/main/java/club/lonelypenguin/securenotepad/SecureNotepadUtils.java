/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package club.lonelypenguin.securenotepad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author IPsoft
 */
public class SecureNotepadUtils {
     // http://www.java-redefined.com/2013/08/symmetric-and-asymmetric-key-encryption.html
    // Commented out as we don't need the list anymore, it was for research
//    static {
//
//        for (Provider p : Security.getProviders()) {
//            System.out.println("Provider: " + p.toString());
//            p.forEach((k, v) -> {
//                if ( k.toString().matches("(rsa|RSA)")) {
//                    System.out.println(k + ", " + v);
//                }
//                
//            });
//
//        }
//    }

    public SecureNotepadUtils() {
    }

    public byte[] getSymmetricKey() {
        return this.symmetricKey;
    }

    public void setSymmetricKey(byte[] key) {
        this.symmetricKey = key;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    // Initialize to 128
    private KeyGenerator symmetricKeyGen;
    private SecretKey symmetricSecretKey;
    private byte symmetricKey[];

    // Initialize to 2048
    private KeyPairGenerator asymmetricKeyPairGen;
    private KeyPair asymmetricKeyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    protected void generateKeys() throws NoSuchAlgorithmException {

        // Symmetric initialization
        symmetricKeyGen = KeyGenerator.getInstance("AES");
        symmetricKeyGen.init(128);
        symmetricKey = symmetricKeyGen.generateKey().getEncoded();

        // Asymmetric initialization
        asymmetricKeyPairGen = KeyPairGenerator.getInstance("RSA");
        asymmetricKeyPairGen.initialize(2048);
        asymmetricKeyPair = asymmetricKeyPairGen.genKeyPair();
        privateKey = asymmetricKeyPair.getPrivate();
        publicKey = asymmetricKeyPair.getPublic();

    }

    /* Function for symmetric encryption using AES
    
     */
    protected String encrypt(String data, byte[] key) throws
            NoSuchAlgorithmException, BadPaddingException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException {
        SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
        Cipher symmetricCipher = Cipher.getInstance("AES");
        symmetricCipher.init(Cipher.ENCRYPT_MODE, keyspec);
        return Base64.encodeBase64String(symmetricCipher.doFinal(data.getBytes()));
    }

    /* Function for symmetric decryption using AES
    
     */
    public String decrypt(byte[] encoded, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
        Cipher symmetricCipher = Cipher.getInstance("AES");
        symmetricCipher.init(Cipher.DECRYPT_MODE, keyspec);
        byte converted[] = Base64.decodeBase64(encoded);
        byte decrypted[] = symmetricCipher.doFinal(converted);
        return new String(decrypted);

    }

    protected String encryptSymmetricKey(byte[] data, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher asymmetricCipher = Cipher.getInstance("RSA");
        asymmetricCipher.init(Cipher.ENCRYPT_MODE, key);
        byte encryptedKey[] = Base64.encodeBase64(asymmetricCipher.doFinal(data));

        return new String(encryptedKey);

    }

    protected void savePublicKeyFile(byte[] data, File f) throws FileNotFoundException, IOException {
        try (FileOutputStream fout = new FileOutputStream(f)) {
            fout.write(data);
        }
    }

    protected void savePrivateKeyFile(byte[] data, File f) throws IOException {
        try (FileOutputStream fout = new FileOutputStream(f)) {
            fout.write(data);
        }
    }

    protected byte[] loadPublicKeyFile(File f) throws FileNotFoundException, IOException {
        byte[] buffer;
        try (FileInputStream fin = new FileInputStream(f)) {
            buffer = new byte[fin.available()];
            fin.read(buffer);
        }
        return buffer;
    }
    
    protected byte[] loadPrivateKeyFile(File f) throws IOException {
        
        byte[] buffer;
        try (FileInputStream fin = new FileInputStream(f)) {
            buffer = new byte[fin.available()];
            fin.read(buffer);
        }
        return buffer;
    }
    
      

    protected String decryptSymmetricKey(byte[] data, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte decryptedKey[] = Base64.encodeBase64(cipher.doFinal(Base64.decodeBase64(data)));

        return new String(decryptedKey);

    }

}
