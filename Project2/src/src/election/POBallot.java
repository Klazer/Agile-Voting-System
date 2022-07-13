package election;
/** This class represents a POBallot. It contains a general constructor
 * for a PO ballot and a method to retrieve the ballot's voting number.
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */

public class POBallot extends Ballot {
    int vote;

    /**
     * This is the constructor for the POBallot class. It
     * initializes the ballot number and the vote associated with this ballot.
     */
    public POBallot(int vote, int ballotNum){
        this.ballotNum = ballotNum;
        this.vote = vote;
    }

    /**
     * This method returns the integer associated with a specific ballot.
     * The integer represents the position in the ballot that was marked.
     * The integer should also align with the position of candidates in an array of candidates
     * in order to correctly associate votes with the correct candidate.
     *
     * @return the integer associated with a specific ballot
     */
    public int getVote(){
        return vote;
    }
}
