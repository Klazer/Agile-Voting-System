package election;

/** This class represents an IRBallot. In the ballot, each index will represent the respective
 * candidates as received from the CSV file.
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */
public class IRBallot extends Ballot{
    int[] candidateRanks;

    /**
     * This is the constructor for the IRBallot class. It inherits off of the Ballot class.
     * <p>
     * It will be initialized an integer array indicating which candidates were voted for
     * and if they were the voter's number 1 preference, number 2, and so on.
     *
     *
     * @param  ranks  an integer array representing votes for a candidate based off preferences. Each index
     *                of the array aligns with the position of the candidate in the received CSV file
     * @param  ballotNum the ballot number associated with the specific ballot. Could be considered its ballot ID
     * @see Ballot
     */
    public IRBallot(int[] ranks, int ballotNum){
        this.candidateRanks = ranks;
        this.ballotNum = ballotNum;
    }

    /**
     * This is a method that returns votes and preference casted for this specific IR ballot
     *
     *
     *
     * @return the integer array associated with the specific ballot
     */
    public int[] getRanks(){
        return candidateRanks;
    }

    /**
     * This is a method that returns the index of a specific ranked candidate
     * By getting the index of a certain rank, such as 1, we can find out who
     * was the ballot's number 1 candidate in the ballot by comparing indices.
     *
     *
     *
     * @return the integer associated with a rank number
     */
    public int getRankIndex(int rank){

        int index = -1;

        for (int i = 0; i < candidateRanks.length; i++){
            if (candidateRanks[i] == rank){ //If we find that a rank is the same as an element in the array
                index = i;
                break; //Exit loop early and move on
            }
        }
        if (index == -1){ //If index is -1 at the end, the rank could not be found
            System.out.println("Rank not found, please try again");
        }

        return index;
    }

    public void setRanks(int[] ranks){
        this.candidateRanks = ranks;
    }
}
