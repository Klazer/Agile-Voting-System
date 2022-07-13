package election;

/** This class represents a Ballot. It contains a general constructor
 * for a ballot and a method to retrieve the ballot's number.
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */

public class Ballot {
    int ballotNum = 0;

    /**
     * This is the constructor for the Ballot class. It remains
     * uninitialized as OPLBallot and IRBallot will inherit from the Ballot class.
     */
    public Ballot(){
    }

    /**
     * This method returns the integer associated with a specific ballot.
     *
     * @return the integer associated with a specific ballot
     */
    public int getBallotNum(){
        return ballotNum;
    }
}
