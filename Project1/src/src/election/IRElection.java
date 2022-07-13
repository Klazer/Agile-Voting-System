package election;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/** This class represents an Instant Runoff Election.
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */
public class IRElection extends Election {
    /**
     * This is the constructor for the IRElection class. It inherits from the Election class.
     * It will be initialized with the following parameters to instantiate an IRElection.
     *
     * @param fileName a string representing the file to be opened by the system
     * @param outputPath a string designating the output destination on the user's sysyem
     * @param candidateCount an integer representing the number of candidates in the election
     * @param candidates an array of Candidate objects consisting of all candidates in the election
     * @param parties an array of Party objects containing one instance of each party that is present in an election
     * @param ballots an array of Ballot objects containing all ballots cast in an election
     */
    public IRElection(String fileName, String outputPath, int candidateCount, Candidate[] candidates, Party[] parties, Ballot[] ballots) {
        super(fileName, outputPath, candidateCount, candidates, parties, ballots, 0);
    }

    /**
     * This method is used when votes need to be redistributed after a candidate has been eliminated.
     *
     */
    public void redistribute() { //will fail if first Ballot is not IRBallot
        int lows = 0;
        boolean given;
        IRBallot now = null;
        Candidate lowest = this.candidates[0];
        for (int i = 0; i < this.candidateCount; i++) { //I am using negative 1 to note a candidate has been eliminated
            if (!this.candidates[i].isEliminated) {
                lows++;
                if (this.candidates[i].totalVotes < lowest.totalVotes || lowest.isEliminated) {
                    lowest = this.candidates[i];
                }
            }
        }
        if (lows == 1) { //If there is only 1 candidate left
            for (int i = 0; i < this.candidateCount; i++) { //I am using negative 1 to note a candidate has been eliminated
                if (!this.candidates[i].isEliminated) {
                    this.candidates[i].hasSeat = true;
                    updateStringBuffer();
                    this.auditTracker.append("There is only 1 candidate left so ").append(this.candidates[i].name).append(" wins by default\n");
                    return;
                }
            }
        }
        lows = 0;
        for (int i = 0; i < this.candidateCount; i++) { //I am using negative 1 to note a candidate has been eliminated
            if (this.candidates[i].totalVotes == lowest.totalVotes) {
                lows++;
            }
        }
        if (lows == 1) {//if there is only 1 lowest and no tie breaking needed
            lowest.isEliminated = true;// eliminate candidate
            updateStringBuffer();
            this.auditTracker.append(lowest.name).append(" has the least votes and so has been eliminated\n\n");
        } else {//if there is a tie for last place
            Candidate[] least = new Candidate[lows];
            lows = 0;
            for (int i = 0; i < this.candidateCount; i++) {
                if (this.candidates[i].totalVotes == lowest.totalVotes) {
                    least[lows] = this.candidates[i];
                    lows++;
                    updateStringBuffer();
                    if(lows == 1){
                        this.auditTracker.append("There is a tie for least votes between ").append(this.candidates[i].name).append(", ");
                    }
                    else if(lows > least.length){
                        this.auditTracker.append(this.candidates[i].name).append(", ");
                    }
                    else {
                        this.auditTracker.append("and ").append(this.candidates[i].name).append(" so one of them will be randomly eliminated\n");
                    }
                }
            }
            lowest = this.Tiebreaker(least);
            lowest.isEliminated = true; // random elimination
            updateStringBuffer();
            this.auditTracker.append(lowest.name).append(" lost the tiebreaker and has been eliminated\n\n");
        }
        for (int i = 0; i < this.candidateCount; i++) { // checks for a candidate with over 50% of the votes
            if (this.candidates[i].totalVotes > this.ballots.length / 2) {
                this.candidates[i].hasSeat = true;
                updateStringBuffer();
                this.auditTracker.append(this.candidates[i].name).append(" has ").append(this.candidates[i].totalVotes).append(" votes which is over half the votes (").append(this.ballots.length / 2).append(" votes) so they win the election \n");
            }
        }
        //Now that the elimination has happened we can redistribute the votes
        for (int i = 0; i < this.candidateCount; i++) { //I am using negative 1 to note a candidate has been is eliminated
            if (!this.candidates[i].isEliminated) {
                this.candidates[i].totalVotes = 0; //TODO ----
            }
        }
        for (int i = 0; i < this.ballots.length; i++) {//distributes ballots to non-eliminatied candidates
            given = false;
            lows = 0;
            if (this.ballots[i] instanceof IRBallot) { //always true
                now = (IRBallot) this.ballots[i];
            } //[2,4,1] [-1,2,1,-1,4]
            while ((!given) && (lows < now.candidateRanks.length)) {
                if (!this.candidates[now.candidateRanks[lows]].isEliminated) { //checks if candidate is eliminated
                    this.candidates[now.candidateRanks[lows]].totalVotes++;
                    given = true;
                } else {
                    lows++;
                }
            }
        }
        updateStringBuffer();
        this.auditTracker.append("The current number of votes each candidate has is: \n");
        for (int i = 0; i < this.candidateCount; i++) { //I am using negative 1 to note a candidate has been eliminated
            if (!this.candidates[i].isEliminated) {
                updateStringBuffer();
                this.auditTracker.append(this.candidates[i].totalVotes).append(" for ").append(this.candidates[i].name + "\n");
            }
        }
    }

    /**
     * This method processes the data from the election and writes the occurrences of the election
     * to the output file. The outcomes from each round are displayed and each step in the process
     * of elimination is tracked until there is a winner of the election. The output is written
     * either to the given file name or is named at the time of processing. Catches I/O exceptions
     * if the given file name is invalid.
     *
     */
    public void process_media() {
        boolean assigned = false;
        IRBallot now = null;
        for (int i = 0; i < this.candidates.length; i++){ //set to zero
            this.candidates[i].totalVotes = 0;
        }
        for (int i = 0; i < this.ballots.length; i++) {//distributes ballots to non-eliminated candidates
            if (this.ballots[i] instanceof IRBallot) { //always true
                now = (IRBallot) this.ballots[i];
            }
            this.candidates[now.candidateRanks[0]].totalVotes++;
        }
        for (int i = 0; i < this.candidateCount; i++) {
            if (this.candidates[i].hasSeat) {
                assigned = true;
            }
        }
        int oldcan[] = new int[this.candidateCount];
        for (int i = 0; i < this.candidateCount; i++) {
            oldcan[i] = this.candidates[i].totalVotes;
        }
        this.auditTracker = new StringBuffer(1000);
        this.auditTracker.append("IR Election\n");
        updateStringBuffer();
        this.auditTracker.append("We have ").append(this.candidates.length).append(" candidates\n");
        this.auditTracker.append("In the first round the votes for the candidates are: \n");
        for (int i = 0; i < this.candidateCount; i++){
            updateStringBuffer();
            this.auditTracker.append(this.candidates[i].totalVotes).append(" for ").append(this.candidates[i].name).append("\n");
        }
        int out = 1; //keeps track out the round
        int lost[] = new int[this.candidateCount]; // keeps track of when each candidate gets eliminated
        for (int i = 0; i < this.candidateCount; i++) {
            lost[i] = -1;
        }
        while (!assigned) {
            this.redistribute();
            for (int i = 0; i < this.candidateCount; i++) {
                if (this.candidates[i].hasSeat) {
                    assigned = true;
                }
            }
            for (int i = 0; i < this.candidateCount; i++){
                if (!this.candidates[i].isEliminated) {
                    oldcan[i] = this.candidates[i].totalVotes;
                }
                else if(lost[i] == -1){
                    lost[i] =out;
                }
            }
            out++;
        }
        LocalDateTime moment = LocalDateTime.now();
        String name;
        if(this.getFileName() == null){
            moment = LocalDateTime.now().withNano(0);
            String currtime = moment.toString();
            currtime = currtime.replaceAll(":", "_");
            name = "media_at_" + currtime +  "_media.txt";
        }
        else{
            name = this.getFileName() + "_media.txt";
        }
        try {
            FileWriter fly = new FileWriter(new File(outputPath, name));
            fly.append("This is an IR election\n");
            fly.append("There were a total of ").append(String.valueOf(this.candidates.length)).append(" candidates\n");
            fly.append("There was a total of ").append(String.valueOf(this.ballots.length)).append(" ballots\n\n");
            fly.append("Candidates : ");
            for (int i = 0; i < this.candidates.length; i++){
                fly.append(this.candidates[i].name).append(", ");
            }
            fly.append("\n");
            for (int i = 0; i < this.parties.length; i++) {
                if(lost[i] != -1) {
                    fly.append(this.candidates[i].name).append(" from the ").append(this.candidates[i].party).append(" party got ").append(String.valueOf(oldcan[i])).append(" votes before being eliminated in round ").append(String.valueOf(lost[i])).append("\n");
                }
                else{
                    fly.append(this.candidates[i].name).append(" from the ").append(this.candidates[i].party).append(" party got ").append(String.valueOf(oldcan[i])).append(" votes and was not eliminated\n");
                }
            }
            fly.append("The following candidate won: \n");
            for (int i = 0; i < this.parties.length; i++) {
                if (this.candidates[i].hasSeat) {
                    fly.append(this.candidates[i].name).append(" with ").append(String.valueOf(this.candidates[i].totalVotes)).append(" Votes");
                }
            }
            fly.flush();
            fly.close();
        } catch (IOException e) {
            System.out.println("Could not open file named " + name);
        }

        if(this.getFileName() == null){
            moment = LocalDateTime.now().withNano(0);
            String currtime = moment.toString();
            currtime = currtime.replaceAll(":", "_");
            name = "audit_at_" + currtime +  "_audit.txt";
        }
        else{
            name = this.getFileName() + "_audit.txt";
        }
        try {
            FileWriter fl = new FileWriter(new File(outputPath, name));
            fl.append(this.auditTracker);
            fl.flush();
            fl.close();

        } catch (IOException e) {
            System.out.println("Could not open file named " + name);
        }
        //System.out.println(this.auditTracker);
    }

    /**
     *This is a void function that takes in no argument
     *
     * When called this programmer checks if the auditTracker is more than half full
     * if it is it will double the auditTracker's capacity keeping in all the old writing
     * if it isn't it will do nothing
     */
    private void updateStringBuffer(){
        if ((float)auditTracker.length()/auditTracker.capacity() > 0.5){
            StringBuffer temp = new StringBuffer(auditTracker);
            auditTracker = new StringBuffer(auditTracker.capacity()*2);
            auditTracker.append(temp);
        }
    }

}
