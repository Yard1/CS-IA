/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA;

import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author uczen
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     * @throws antonibaumia.TimetableGenerator.NoSuchSubjectException
     */
    public static void main(String[] args) throws IOException, InvalidFormatException, TimetableGenerator.NoSuchSubjectException {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        new MainFrame();
    }

}
