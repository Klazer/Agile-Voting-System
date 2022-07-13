package election;

/** This class represents a Party. It contains a Party object constructor and includes
 * methods to get and set party-specific fields, like name, candidates, etc.
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */

public class Party {
    int assignedSeats = 0;
    Candidate[] candidates;
    int totalVotes;
    String partyName;

    /**
     * This is the constructor for the Party class. It will be initialized with the following
     * parameters.
     *
     * @param partyName a String representing the name of the party
     * @param candidates an array of Candidate objects containing all candidates within the party
     * @param numVotes an integer representing the total number of votes for a particular party
     */
    public Party(String partyName, Candidate[] candidates, int numVotes){
        this.candidates = candidates;
        this.partyName = partyName;
        this.totalVotes = numVotes;
    }

    // Getters
    /**
     * This method returns the integer representing the party's total number of votes
     *
     * @return the party's total votes
     */
    public int getVotes(){
        return totalVotes;
    }

    /**
     * This method returns the string representing the party's name
     *
     * @return the party's name
     */
    public String getName(){
        return partyName;
    }

    /**
     * This method returns the Candidate array representing the candidates within the party
     *
     * @return the candidates within the party
     */
    public Candidate[] getCandidates(){
        return candidates;
    }

    /**
     * This method returns the integer representing the party's number of assigned seats
     *
     * @return the party's assigned seats
     */
    public int getSeatCount(){
        return assignedSeats;
    }

    // Setters

    /**
     * This method updates the total number of votes for the party
     *
     * @param newVotes an integer representing the new number of votes to be associated with the party
     */
    public void updateVotes(int newVotes){ //Update the total number of votes for the party
        totalVotes = newVotes;
    }

    /**
     * This method updates the total number of assigned seats that a party has
     *
     * @param count an integer representing the party's number of assigned seats
     */
    public void setSeatCount(int count){ //Update the total number of seats
        assignedSeats = count;
    }
}
