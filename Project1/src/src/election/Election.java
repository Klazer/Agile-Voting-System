package election;

import java.util.Random;

/** This class represents an Election. It contains all the necessary
 * information related to an election (such as candidates, votes, etc.) and
 * a method to deal with tiebreakers
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */

public class Election {
    String outputPath;
    String fileName;
    int candidateCount;
    Candidate[] candidates;
    Party[] parties;
    Ballot[] ballots;
    int seatCount;
    StringBuffer auditTracker;
    /**
     * This is the constructor for the Election class.
     * <p>
     * It acts a bit as a container to store all related information of an election in one spot such
     * as candidates, parties, etc.
     *
     *
     * @param fileName The name of the audit file
     * @param outputPath  a string indicating the path we want to output media/audit files to
     * @param candidateCount The total number of candidates in the election
     * @param candidates An array containing a list of the candidates in the election
     * @param parties An array containing the list of parties in the election
     * @param ballots An array containing all ballots in the election
     * @param seatcount Primarily used for an OPL election, but it indicates how many seats are available in the election
     */
    public Election(String fileName, String outputPath,int candidateCount,Candidate[] candidates,Party[] parties,Ballot[] ballots,int seatcount){
        this.outputPath = outputPath;
        this.fileName = fileName;
        this.candidateCount = candidateCount;
        this.candidates = candidates;
        this.parties = parties;
        this.ballots = ballots;
        this.seatCount = seatcount;
        this.auditTracker = new StringBuffer();
    }

    /**
     * This is gets the output string associated with the class
     *
     *
     * @return A string containing the output path for files
     *
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * This returns a string representing the filename of the audit file.
     *
     *
     * @return A Candidate array containing information for each candidate
     *
     */
    public String getFileName(){
        return fileName;
    }

    /**
     * This is gets the array of candidates in the election
     *
     *
     * @return A Candidate array containing information for each candidate
     *
     */
    public Candidate[] getCandidates() {
        return candidates;
    }

    /**
     * This gets the array of ballots associated with the election
     *
     * @return An array of ballots for the election
     *
     */
    public Ballot[] getBallots() {
        return ballots;
    }

    /**
     * This is gets the number of seats available in the election
     *
     *
     * @return An integer representing the number of seats in an election
     *
     */
    public int getSeatCount() {
        return seatCount;
    }

    /**
     * This is gets number of ballots in the election
     *
     * @return An integer representing the total number of ballots in the election
     *
     */
    public int getBallotCount(){
        return ballots.length;
    }

    /**
     * This is gets number of candidates in the election
     *
     * @return An integer representing the total number of candidates in the election
     *
     */
    public int getCandidateCount(){
        return candidateCount;
    }

    /**
     * This is gets the parties in the election
     *
     * @return An array returning the parties in an election
     *
     */
    public Party[] getParties(){
        return parties;
    }

    /**
     * This is the tiebreaker method which occurs when there is a tie between two candidates.
     * <p>
     * It simulates a coin flip in which the candidate that wins is randomly decided via a coin
     *
     *
     * @param lizards Represents the array of current candidates left in the election
     * @return The candidate that won the coin flip
     *
     */
    public Candidate Tiebreaker(Candidate[] lizards){
        Random rand = new Random();
        int hi;
        int hold = 0;
        for (int i = 0; i < 1000; i++){
            hi = rand.nextInt(5)+2;
            for (int j = 0; j < hi; j++){
                hold = rand.nextInt(lizards.length-1);
            }
        }
        hi = rand.nextInt(5)+2;
        int j = 0;
        while(j < hi){
            hold = rand.nextInt(lizards.length);
            j++;
        }
        return lizards[hold];
    }

    /**
     * This is the tiebreaker method which occurs when there is a tie between two parties.
     * <p>
     * It simulates a coin flip in which the party that wins a seat is randomly decided via a coin
     *
     *
     * @param fun Represents the array of current parties left in the election
     * @return The party that won the coin flip
     *
     */
    public Party pTiebreaker(Party[] fun){
        Random rand = new Random();
        int hi;
        int hold = 0;
        for (int i = 0; i < 1000; i++){
            hi = rand.nextInt(5)+2;
            for (int j = 0; j < hi; j++){
                hold = rand.nextInt(fun.length-1);
            }
        }
        Party tieWinner = null;
        while (tieWinner == null) {
            hi = rand.nextInt(5) + 2;
            int j = 0;
            while (j < hi) {
                hold = rand.nextInt(fun.length);
                j++;
            }
            tieWinner = fun[hold];
        }
        return tieWinner;
    }

    /**
     * The method to create a media file for the media.
     * It should clearly indicate who won, who lost, and other relevant statistics.
     *
     */
    public void process_media( ){ // This should be overwriten by child
        System.out.print("Something's wrong");
    }



}