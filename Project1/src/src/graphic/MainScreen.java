package graphic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * This is the main screen of the program
 */
public class MainScreen{
    private JPanel panel1;
    private JButton selectFileButton;
    private JButton configureButton;
    private JButton cancelButton;
    private JButton beginButton;
    private JLabel fileSelectedLabel;
    private JLabel destinationLabel;
    private Window window;

    /**
     * This is the main screen, after the start screen. Here, the user can select input files,
     * begin processing, change configs, or cancel
     *
     * @param window Parent class
     */
    public MainScreen(Window window) {
        this.window = window;
        initLabels();
        addListeners();
    }

    private void initLabels(){
        fileSelectedLabel.setText("No File Selected!");
        destinationLabel.setText(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
        updateWindowParams();
    }

    /**
     * Listeners for all relevant buttons.
     */
    private void addListeners(){
        final JFileChooser[] objFileChooser = new JFileChooser[1];
        final String[] strDirectory = {""};
        final String[] strFileName = {""};
        final File[] fSecurities = new File[1];
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (strDirectory[0] == "" || strDirectory[0] == null)
                    objFileChooser[0] = new JFileChooser();
                else
                    objFileChooser[0] = new JFileChooser(strDirectory[0]);
                objFileChooser[0].setFileSelectionMode(JFileChooser.FILES_ONLY);
                int iReturn = objFileChooser[0].showDialog(panel1, "Open File");
                if (iReturn == JFileChooser.APPROVE_OPTION) {
                    fSecurities[0] = objFileChooser[0].getSelectedFile();
                    strFileName[0] = fSecurities[0].getAbsolutePath();
                    strDirectory[0] = fSecurities[0].getParent();
                }

                fileSelectedLabel.setText(strFileName[0]);
                updateWindowParams();
            }
        });
        configureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.cardLayout.show(window.cardPanel, "Config");
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initLabels();
                window.config.initLabels();
                window.cardLayout.show(window.cardPanel, "Start");
            }
        });
        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fileSelectedLabel.getText().equals("No File Selected!")) {
                    window.process();
                    initLabels();
                    window.config.initLabels();
                } else {
                    JOptionPane.showMessageDialog(panel1, "ERROR: No file has been selected!");
                }
            }
        });
    }

    /**
     * Update the window parameters with the current label states
     */
    private void updateWindowParams(){
        window.setOutputPath(destinationLabel.getText());
        window.setInputPath(fileSelectedLabel.getText());
    }

    /**
     * Update the destination label, to indicate to the user the current output directory
     *
     * @param destinationLabel String representing the current output directory
     */
    public void setDestinationLabel(JLabel destinationLabel){
        this.destinationLabel.setText(destinationLabel.getText());
        updateWindowParams();
    }

    public JPanel getPanel(){
        return panel1;
    }
}
