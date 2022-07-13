package graphic;

import election.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is the main GUI window, containing all subsequent windows. This window
 * interacts with the VotingSystem object to set its input and output paths, or choose
 * to begin processing an election
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */
public class Window {
    public VotingSystem votingSystem;

    public Start start;
    public MainScreen mainScreen;
    public Config config;
    public JFrame window;
    public JPanel cardPanel;
    public CardLayout cardLayout;

    /**
     * Window initializer
     *
     * @param votingSystem VotingSystem that the window communicates with
     * @throws ClassNotFoundException
     * @throws UnsupportedLookAndFeelException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Window(VotingSystem votingSystem) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        window = new JFrame("Election Processor");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(600, 400);

        this.votingSystem = votingSystem;

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }


    /**
     * Initializes the window and graphical components.
     */
    public void init() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel();

        start = new Start(this);
        mainScreen = new MainScreen(this);
        config = new Config(this);

        JPanel startPanel = start.getPanel();
        JPanel mainScreenPanel = mainScreen.getPanel();
        JPanel configPanel = config.getPanel();

        cardPanel.setLayout(cardLayout);
        cardPanel.add(startPanel, "Start");
        cardPanel.add(mainScreenPanel, "Main");
        cardPanel.add(configPanel, "Config");

        window.add(cardPanel);
        window.setContentPane(cardPanel);
        //window.pack();
        window.setVisible(true);
    }

    /**
     * Call the VotingSystem to begin processing the election.
     */
    public void process() {
        System.out.println("Processing...");
        votingSystem.process();
    }

    /**
     * Helps set the output path for the audit and media file
     */
    public void setOutputPath(String outputPath) {
        System.out.println("Output: " + outputPath);
        votingSystem.setOutputPath(outputPath);
    }

    /**
     * Helps set the output path for the audit and media file
     */
    public void setInputFiles(File[] inputFiles) {
        for (File file : inputFiles){
            System.out.println("Input: " + file.getName());
        }
        votingSystem.setInputFiles(inputFiles);
    }

    /**
     * A general file name for the audit and media file
     */
    public void setFileName(String fileName) {
        System.out.println("Audit Name: " + fileName);
        votingSystem.setFileName(fileName);
    }
    /**
     * A small helper function to help find the index of the largest number of votes
     * a candidate may have in a list
     */
    public int getIndexOfLargest( List<Candidate> array )
    {
        int maxAt = 0;
        List<Candidate> test;
        for (int i = 0; i < array.size(); i++) {
            maxAt = array.get(i).getTotalVotes() > array.get(maxAt).getTotalVotes() ? i : maxAt;
        }
        return maxAt;
    } //Helper function

    /**
     * The function to help display the results to a separate window after the program
     * has finished an OPL or IR election.
     */
    public void displayResults(Election election) {
        if (election instanceof OPLElection) {
            String out = "Complete! \n";
            out += "You put in a file for an OPL election. Below is the data from the election:\n";
            for (int i = 0; i < election.getCandidateCount(); i++) {
                if (election.getCandidates()[i].hasSeat() != false) {
                    out = out.concat(election.getCandidates()[i].getName() + "(" + election.getCandidates()[i].getParty() + ")" + " got " + election.getCandidates()[i].getTotalVotes() + " votes and has a seat\n");
                }
            }
            out += "Everyone else not listed did not get seats\n";
            out += "Party breakdown:\n";
            for (int i = 0; i < election.getParties().length; i++){
                out += election.getParties()[i].getName() + " got " + election.getParties()[i].getSeatCount() + " seats\n";
            }
            JOptionPane.showMessageDialog(cardPanel, out);
        } else if (election instanceof POElection){
            /*
            String out = "Complete! \n";
            out += "You put in a file for a PO election. Below is the data from the election:\n";
            for (int i = 0; i < election.getCandidateCount(); i++) {
                if (election.getCandidates()[i].hasSeat() != false) {
                    out = out.concat(election.getCandidates()[i].getName() + "(" + election.getCandidates()[i].getParty() + ")" + " got " + election.getCandidates()[i].getTotalVotes() + " votes and has a seat\n");
                }
            }
             */
            String out = "You put in a file for a PO election. Below is the data from the election:\n";
            out += "Current Parties: \n";
            for (int i = 0; i < election.getParties().length; i++){
                out += election.getParties()[i].getName() + "\n \n";
            }
            out += "There are currently no output metrics available.";
            JOptionPane.showMessageDialog(cardPanel, out);
        } else {
            String out = "Complete! \n";
            out += "You put in a file for an IR election. Below is the data from the election:\n";
            out += "There were " + election.getCandidateCount() + " candidates\n";
            if (election.getCandidateCount() <= 5) {
                for (int i = 0; i < election.getCandidateCount(); i++) {
                    out = out.concat(election.getCandidates()[i].getName() + "(" + election.getCandidates()[i].getParty() + ")" + " got " + election.getCandidates()[i].getTotalVotes() + " votes.\n");
                }
                for (int winner = 0; winner < election.getCandidateCount(); winner++) {
                    if (election.getCandidates()[winner].hasSeat() == true) {
                        out += election.getCandidates()[winner].getName() + " has the most votes with " + election.getCandidates()[winner].getTotalVotes() + " votes\n";
                        out += "The winner for this IR election was " + election.getCandidates()[winner].getName();
                        break;
                    }
                }
            } else {
                //Filter through five times and extract the top 5 biggest candidates
                List<Candidate> newCand = new LinkedList<Candidate>(Arrays.asList(election.getCandidates()));
                Candidate[] Top5 = new Candidate[5];

                for (int i = 0; i < 5; i++) {
                    int highest = getIndexOfLargest(newCand);
                    Top5[i] = newCand.get(highest);
                    newCand.remove(highest);
                }
                out += "You put in a file for an IR election. Below is the data from the election for the top 5 candidates:\n";

                if (election.getCandidateCount() > 5) {
                    for (int i = 0; i < Top5.length; i++) {
                        out = out.concat(Top5[i].getName() + "(" + Top5[i].getParty() + ")" + " got " + Top5[i].getTotalVotes() + " votes.\n");
                    }
                    for (int winner = 0; winner < election.getCandidateCount(); winner++) {
                        if (election.getCandidates()[winner].hasSeat() == true) {
                            out += election.getCandidates()[winner].getName() + " has the most votes with " + election.getCandidates()[winner].getTotalVotes() + " votes\n";
                            out += "The winner for this IR election was " + election.getCandidates()[winner].getName();
                            break;
                        }
                    }
                }
            }

                JOptionPane.showMessageDialog(cardPanel, out);

        }
    }
}
