/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package club.lonelypenguin.securenotepad;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.SwingUtilities;

/**
 *
 * @author davidb
 */
public class SecureNotepad {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
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
