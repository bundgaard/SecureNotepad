/*
 hello world
 */
package club.lonelypenguin.securenotepad;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 *
 * String dummyText = "Hello World, this is a string that will be encrypted
 * using javax.crypto";
 *
 * try { SecureNotepadUtils me = new SecureNotepadUtils(); me.generateKeys();
 * System.out.println("Dummy " + dummyText); String encrypted =
 * me.encrypt(dummyText, me.getSymmetricKey()); System.out.println("Encrypted "
 * + encrypted ); String decrypted = me.decrypt(encrypted.getBytes(),
 * me.getSymmetricKey()); System.out.println("Decrypted: " + decrypted); String
 * symKey = me.encryptSymmetricKey(me.getSymmetricKey(), me.getPublicKey());
 * System.out.println("Encrypted key: " + symKey); System.out.println("Decrypted
 * key: " + me.decryptSymmetricKey(symKey.getBytes(), me.getPrivateKey()));
 *
 *
 * } catch (NoSuchAlgorithmException | BadPaddingException |
 * NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
 * System.err.println(e.getClass() + " " + e.getMessage()); }
 *
 * @author davidb
 */
public class SecureNotepadWindow extends JFrame implements ClipboardOwner {

    private JMenuBar mb_standard;

    private JMenu menu_file;
    private JMenu menu_edit;
    private JMenu menu_options;
    private JMenu menu_cryptography;
    private JMenu menu_help;

    private JMenuItem file_open;
    private JMenuItem file_close;
    private JMenuItem file_save;
    private JMenuItem file_saveas;

    private JMenuItem edit_copy;
    private JMenuItem edit_cut;
    private JMenuItem edit_paste;

    private JMenuItem cryptography_new;
    private JMenuItem cryptography_load;
    private JMenuItem cryptography_save_private;
    private JMenuItem cryptography_save_public;

    private JTextArea secure_textarea;

    private JTextArea encrypted_textarea;

    private JFileChooser filechooser;

    public SecureNotepadWindow() {
        super("SecureNotepad");
        init();
        init_miscellaneous();
        init_menubar();
        init_textarea();
    }

    private void init() {
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void openFile(File f) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader fileRead = new BufferedReader(new FileReader(f));
            secure_textarea.read(fileRead, f);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void saveFile(File f) {
        try {

        } catch (Exception e) {

        }
    }

    private void init_menubar() {
        mb_standard = new JMenuBar();
        menu_file = new JMenu("File");
        menu_file.setMnemonic('f');
        menu_edit = new JMenu("Edit");
        menu_edit.setMnemonic('e');
        menu_options = new JMenu("Options");
        menu_cryptography = new JMenu("Cryptography");
        menu_help = new JMenu("Help");

        file_open = new JMenuItem("Open");
        file_open.addActionListener((event) -> {

            int showOpenDialog = filechooser.showOpenDialog(SecureNotepadWindow.this);
            if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
                System.out.println("You wanted to open '" + filechooser.getSelectedFile().getName() + "'");
                System.out.println(filechooser.getSelectedFile().getAbsolutePath());
                openFile(filechooser.getSelectedFile().getAbsoluteFile());
            } else if (showOpenDialog == JFileChooser.CANCEL_OPTION) {
                System.out.println("You forfeited your open chance");
            }

        });
        file_close = new JMenuItem("Close");
        file_close.addActionListener((event) -> {
            System.exit(0);
        });
        file_save = new JMenuItem("Save");
        file_save.addActionListener((event) -> {
            filechooser.showSaveDialog(SecureNotepadWindow.this);
        });
        file_saveas = new JMenuItem("Save as");
        file_saveas.addActionListener((event) -> {
            filechooser.showSaveDialog(SecureNotepadWindow.this);
        });

        edit_copy = new JMenuItem("Copy");
        edit_copy.setMnemonic('c');
        edit_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        edit_copy.addActionListener((event) -> {
            sendStringToClipboard();
        });
        edit_cut = new JMenuItem("Cut");
        edit_cut.setMnemonic('u');
        edit_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        edit_cut.addActionListener((event) -> {
            sendStringToClipboard();
            secure_textarea.replaceSelection("");
        });
        edit_paste = new JMenuItem("Paste");
        edit_paste.setMnemonic('p');
        edit_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        edit_paste.addActionListener((event) -> {
            sendClipboardToTextArea();
        });

        cryptography_new = new JMenuItem("New keypair");
        cryptography_new.addActionListener((event) -> {
        });
        cryptography_load = new JMenuItem("Load");
        cryptography_load.addActionListener((event) -> {
        });
        cryptography_save_public = new JMenuItem("Save public key");
        cryptography_save_public.addActionListener((event) -> {
        });
        cryptography_save_private = new JMenuItem("Save private");
        cryptography_save_private.addActionListener((event) -> {
        });

        menu_file.add(file_open);
        menu_file.add(file_save);
        menu_file.add(file_saveas);
        menu_file.add(file_close);

        menu_edit.add(edit_copy);
        menu_edit.add(edit_cut);
        menu_edit.add(edit_paste);

        menu_cryptography.add(cryptography_new);
        menu_cryptography.add(cryptography_load);
        menu_cryptography.add(cryptography_save_private);
        menu_cryptography.add(cryptography_save_public);

        mb_standard.add(menu_file);
        mb_standard.add(menu_edit);
        mb_standard.add(menu_options);
        mb_standard.add(menu_cryptography);
        mb_standard.add(menu_help);

        this.setJMenuBar(mb_standard);
    }

    private void init_textarea() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        secure_textarea = new JTextArea();
        JScrollPane scrollPaneTextArea = new JScrollPane();
        scrollPaneTextArea.getViewport().setView(secure_textarea);
        centerPanel.add(scrollPaneTextArea);

        encrypted_textarea = new JTextArea();
        JScrollPane scrollPaneEncryptedTextArea = new JScrollPane();
        scrollPaneEncryptedTextArea.getViewport().setView(encrypted_textarea);
        centerPanel.add(scrollPaneEncryptedTextArea);

        this.getContentPane().add(centerPanel);

    }

    private void init_miscellaneous() {
        filechooser = new JFileChooser();
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        // do nothing
    }

    private void sendStringToClipboard() {
        StringSelection selection = new StringSelection(secure_textarea.getSelectedText());
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(selection, this);
    }

    private void sendClipboardToTextArea() {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = c.getContents(null);
        boolean hasContent = (contents != null ) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if ( hasContent ) {
            try {
                String result = (String) contents.getTransferData(DataFlavor.stringFlavor);
                secure_textarea.setText(result);
                
            }catch (IOException | UnsupportedFlavorException | NullPointerException e ) {
                System.err.println(e.getMessage());
            }
        }
    }

}
