/**
 * Created by DarkWizard on 6/1/15.
 */

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class fcmgui {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new MainFrame("FoxCoinMiner");
                frame.setSize(500, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
