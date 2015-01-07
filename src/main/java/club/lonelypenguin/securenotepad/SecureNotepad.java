/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package club.lonelypenguin.securenotepad;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.swing.SwingUtilities;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.*;

/**
 *
 * @author davidb
 */
public class SecureNotepad {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String dummyText = "Hello World, this is a string that will be encrypted using javax.crypto";

        try {
            SecureNotepadUtils me = new SecureNotepadUtils();
            me.generateKeys();
            System.out.println("Dummy " + dummyText);
            String encrypted = me.encrypt(dummyText, me.getSymmetricKey());
            System.out.println("Encrypted " + encrypted );
            String decrypted = me.decrypt(encrypted.getBytes(), me.getSymmetricKey());
            System.out.println("Decrypted: " + decrypted);
            String symKey = me.encryptSymmetricKey(me.getSymmetricKey(), me.getPublicKey());
            System.out.println("Encrypted key: " + symKey);
            System.out.println("Decrypted key: " + me.decryptSymmetricKey(symKey.getBytes(), me.getPrivateKey()));

            
        } catch (NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            System.err.println(e.getClass() + " " + e.getMessage());
        }

        final Runnable r = () -> {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension screenSize = tk.getScreenSize();
            SecureNotepadWindow me = new SecureNotepadWindow();
            Dimension appSize = me.getSize();
            me.setLocation((screenSize.width - appSize.width) / 2, (screenSize.height - appSize.height) / 2);
            me.setVisible(true);

        };

        SwingUtilities.invokeLater(r);
    }

}
