package graphic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * This is the configuration screen for the program
 */
public class Config{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton changeOutputDestinationButton;
    private JButton saveButton;
    private JLabel destinationLabel;
    private Window window;

    /**
     * Allow the user to edit the output name and output destination
     *
     * @param window Parent class
     */
    public Config(Window window) {
        this.window = window;
        initLabels();
        addListeners();
    }

    public void initLabels(){
        destinationLabel.setText(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
        textField1.setText(null);
    }

    /**
     * Add relevant listeners to buttons
     */
    private void addListeners(){
        final JFileChooser[] objFileChooser = new JFileChooser[1];
        final String[] strDirectory = {""};
        final String[] strFileName = {""};
        final File[] fSecurities = new File[1];
        changeOutputDestinationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (strDirectory[0] == "" || strDirectory[0] == null)
                    objFileChooser[0] = new JFileChooser();
                else
                    objFileChooser[0] = new JFileChooser(strDirectory[0]);
                objFileChooser[0].setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //objFileChooser[0].setFileFilter(new FileNameExtensionFilter("Audit File", "all"));
                int iReturn = objFileChooser[0].showDialog(panel1, "Open File");
                if (iReturn == JFileChooser.APPROVE_OPTION) {
                    fSecurities[0] = objFileChooser[0].getSelectedFile();
                    strFileName[0] = fSecurities[0].getAbsolutePath();
                    strDirectory[0] = fSecurities[0].getParent();
                }

                destinationLabel.setText(strFileName[0]);
                window.mainScreen.setDestinationLabel(destinationLabel);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setFileName(textField1.getText());
                window.cardLayout.show(window.cardPanel, "Main");
            }
        });
        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setFileName(textField1.getText());
            }
        });
    }

    public JPanel getPanel(){
        return panel1;
    }
}
