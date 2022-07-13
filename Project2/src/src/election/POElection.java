package election;

/** This class represents a Popularity Only Election.
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */
public class POElection extends Election{
    /**
     * This is the constructor for the OPLElection class. It inherits from the Election class.
     * It will be initialized with the following parameters to instantiate a POElection.
     * Currently, it has no functionality
     *
     *  @param fileName a string representing the file to be opened by the system
     *  @param outputPath a string designating the output destination on the user's sysyem
     *  @param candidateCount an integer representing the number of candidates in the election
     *  @param candidates an array of Candidate objects consisting of all candidates in the election
     *  @param parties an array of Party objects containing one instance of each party that is present in the election
     *  @param ballots an array of Ballot objects containing all ballots cast in the election
     *  @param seatcount an integer representing the number of seats available for election
     * @see Election
     */
    public POElection(String fileName, String outputPath, int candidateCount, Candidate[] candidates, Party[] parties, Ballot[] ballots, int seatcount){
        super(fileName, outputPath, candidateCount, candidates, parties, ballots, seatcount);
    }

}
