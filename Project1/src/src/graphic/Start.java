package graphic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the initial screen of the program
 */
public class Start {
    private JPanel panel1;
    private JButton processVotesButton;
    private Window window;

    /**
     * Starting screen, containing program title, "Process Votes" button, and creator naes
     *
     * @param window Parent class
     */
    public Start(Window window) {
        this.window = window;
        addListeners();
    }

    /**
     * Add listeners to relevant buttons
     */
    private void addListeners(){
        processVotesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.cardLayout.show(window.cardPanel, "Main");
            }
        });
    }

    public JPanel getPanel(){
        return panel1;
    }
}
