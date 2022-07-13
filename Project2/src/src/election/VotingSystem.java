package election;

import graphic.Window;

import java.io.File;

/**
 * This class represents a VotingSystem, the driving class behind the voting system.
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */
public class VotingSystem {
    Election currentElection;
    Window window;
    File[] inputFiles;
    String outputPath;
    String fileName;

    /**
     * This is the constructor for the VotingSystem class.
     *
     */
    public VotingSystem(){
    }

    /**
     * This method initializes the GUI for the voting system. It launches the system
     * window and catches any exceptions during the initialization process.
     *
     */
    public void init(){
        try {
            window = new Window(this);
        } catch (Exception e){
            System.out.println("Error launching window!");
            return;
        }

        window.init();
    }

    // Getters

    /**
     * This method returns the input path of the file to be read for the election.
     * 
     * @return the File array containing all input files
     */
    public File[] getInputFiles(){
        return this.inputFiles;
    }

    /**
     * This method returns the output path of the media file to be produced at the end of the election.
     * 
     * @return the String representation of the file output path
     */
    public String getOutputPath(){
        return this.outputPath;
    }

    // Setters

    /**
     * This method sets the input path of the file to be read for the election.
     * 
     * @param inputFiles File array containing file(s) to bring into the system for processing
     */
    public void setInputFiles(File[] inputFiles){
        this.inputFiles = inputFiles;
    }

    /**
     * This method sets the output path of the media file to be produced at the end of the election.
     * 
     * @param outputPath String representing the desired output path for the media file
     */
    public void setOutputPath(String outputPath){
        this.outputPath = outputPath;
    }

    /**
     * This method sets the file name of the media file to be produced at the end of the election.
     * 
     * @param fileName String representing the desired name for the media file to be produced
     */
    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    /**
     * This method calls necessary methods for processing an election, processes the media file,
     * and displays the results in the window.
     *
     */
    public void process(){
        Election election = processElection();
        election.process_media();
        window.displayResults(election);
    }

    /**
     * This method creates an Election object and calls the parse method of FileParser to extract
     * necessary data from the election file.
     *
     * @return an Election object containing the results of the parsed election file
     */
    public Election processElection(){   // Returns output of calling parse() on the input path
        return FileParser.parse(inputFiles, outputPath, fileName);
    }


    /**
     * This is the main function of the VotingSystem class.
     *
     */
    public static void main(String[] args){
        VotingSystem votingSystem = new VotingSystem();
        votingSystem.init();
    }

}
