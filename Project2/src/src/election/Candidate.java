package election;

/** This class represents a Candidate. It will contain all necessary information of a specific candidate such
 * as their name, party, votes, etc.
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */

public class Candidate {
    String name;
    String party;
    int totalVotes;
    int rank;
    boolean hasSeat;
    boolean isEliminated;

    /**
     * This is the constructor for the Candidate class.
     * <p>
     * It creates a Candidate object with the following attributes:
     *
     *
     * @param  name  Name of the candidate
     * @param  party The name of the party associated with the candidate
     * @param totalVotes An integer representing the total votes that candidate has received
     * @param rank An integer representing that candidates current rank in the election
     * @param hasSeat A boolean representing on if the candidate has a seat or not. True if they have a seat or false otherwise
     *
     */
    public Candidate(String name, String party, int totalVotes, int rank, boolean hasSeat){
        this.name = name;
        this.party = party;
        this.totalVotes = totalVotes;
        this.rank = rank;
        this.hasSeat = hasSeat;
        this.isEliminated = false;
    }
    // Getters
    /**
     * This is a method that returns the string representing the Candidate's name
     *
     * @return the candidate's name
     */
    public String getName(){
        return name;
    }

    /**
     * This is a method that returns the party the candidate is associated with
     *
     * @return the candidate's party
     */
    public String getParty(){
        return party;
    }

    /**
     * This is a method that returns the total number of votes a candidate has
     *
     * @return the total number of votes a candidate has
     */
    public int getTotalVotes() {
        return totalVotes;
    }

    /**
     * This is a method that returns the rank a candidate currently has
     *
     * @return the rank of a candidate
     */
    public int getRank(){
        return rank;
    }

    /**
     * This is a method that returns a boolean value describing whether a candidate
     * is currently holding a seat or not.
     *
     * @return whether a candidate currently holds a seat: true if YES, false if NO
     */
    public boolean hasSeat(){
        return hasSeat;
    }

    // Setters
    /**
     * This is a method that sets the number of votes for a candidate
     *
     * @param newVotes an integer representation of the new totalVotes to be assigned to a candidate
     */
    public void setTotalVotes(int newVotes){
        totalVotes = newVotes;
    }

    /**
     * This is a method that sets the rank of a candidate
     *
     * @param newRank an integer representation of the new rank to be assigned to a candidate
     */
    public void setRank(int newRank){
        rank = newRank;
    }

    /**
     * This is a method that sets the boolean value describing whether a candidate
     * is currently holding a seat or not.
     *
     * @param newHasSeat an boolean representation of whether a candidate currently holds a seat: true if YES, false if NO
     */
    public void setHasSeat(boolean newHasSeat){
        hasSeat = newHasSeat;
    }
}
