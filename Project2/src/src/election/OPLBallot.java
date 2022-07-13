package election;

/** This class represents an Open Party Listing Ballot
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */

public class OPLBallot extends Ballot{
    int candidateVote;

    /**
     * This is the constructor for the OPLBallot class. It inherits off of the Ballot class.
     * <p>
     * It will be initialized with a voted candidate and the ballot number associated with it
     *
     *
     * @param  candidateVote  an integer representing which candidate was voted for.
     * @param  ballotNum the ballot number associated with the specific ballot. Could be considered its ballot ID
     * @see Ballot
     */
    //Constructor
    public OPLBallot(int candidateVote, int ballotNum){

        this.candidateVote = candidateVote;
        this.ballotNum = ballotNum;
    }

    /**
     * This is a method that returns the integer associated with a specific candidate
     *
     *
     *
     * @return the integer associated with a specific candidate
     */
    //Returns the index of the vote of the ballot
    public int getVote(){
        return candidateVote;
    }
}
