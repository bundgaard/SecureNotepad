/*
 hello world
 */
package club.lonelypenguin.securenotepad;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author davidb
 */
public class SecureNotepadWindow extends JFrame {

    private JMenuBar mb_standard;
    private JMenu menu_file;
    private JMenu menu_options;
    private JMenu menu_help;

    private JMenuItem file_open;
    private JMenuItem file_close;
    private JMenuItem file_save;
    private JMenuItem file_saveas;

    private JTextArea secure_textarea;

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
        menu_options = new JMenu("Options");
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

        menu_file.add(file_open);
        menu_file.add(file_save);
        menu_file.add(file_saveas);
        menu_file.add(file_close);

        mb_standard.add(menu_file);
        mb_standard.add(menu_options);
        mb_standard.add(menu_help);

        this.setJMenuBar(mb_standard);
    }

    private void init_textarea() {
        secure_textarea = new JTextArea();
        JScrollPane scrollPaneTextArea = new JScrollPane();
        scrollPaneTextArea.getViewport().setView(secure_textarea);
        this.getContentPane().add(scrollPaneTextArea);
    }

    private void init_miscellaneous() {
        filechooser = new JFileChooser();
    }

}
