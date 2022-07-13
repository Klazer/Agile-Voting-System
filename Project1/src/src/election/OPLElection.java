package election;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/** This class represents an Open Party Listing Election.
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */
public class OPLElection extends Election {
    int quota;

    /**
     * This is the constructor for the OPLElection class. It inherits from the Election class.
     * It will be initialized with the following parameters to instantiate an OPLElection. It also
     * initializes the integer representing the quota of the election.
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
    public OPLElection(String fileName, String outputPath, int candidateCount, Candidate[] candidates, Party[] parties, Ballot[] ballots, int seatcount) {
        super(fileName, outputPath, candidateCount, candidates, parties, ballots, seatcount);
        initquota(ballots.length, seatcount);
        String test = "OPL Election\n";
        String temp ="The quota for one seat is for every " + this.quota + " votes a party may have\n";
        if(this.auditTracker.length()+temp.length() >= this.auditTracker.capacity()){
            this.auditTracker = (new StringBuffer((this.auditTracker.length()+temp.length())*2)).append(this.auditTracker);
        }
        this.auditTracker.append(test);
        this.auditTracker.append(temp);
    }

    /**
     * This method allocates the correct number of seats to each party, and then allocates
     * those seats to the correct number of candidates within that party.
     *
     * @param fun an array of Party objects containing all parties in the election
     */
    void allocate_seats(Party[] fun) { //Allocates seats to parties
        String holdstr;
        int voteforp[] = new int[fun.length]; //The votes for each party
        for (int i = 0; i < fun.length; i++) {
            voteforp[i] = 0;
        }
        assignVotes();
        for (int i = 0; i < fun.length; i++) {
            voteforp[i] += this.parties[i].totalVotes;
            fun[i].totalVotes = 0;
        }
        int temp = 0;
        for (int i = 0; i < fun.length; i++) {
            temp += fun[i].candidates.length;
        }
        if (temp < this.seatCount) { //if there are more seats available than candidates
            this.auditTracker.append("There are more seats (seats " + this.seatCount + ") available than candidates (canidates " +
                    temp + ") so everyone gets a seat");
            for (int i = 0; i < fun.length; i++) {
                fun[i].assignedSeats = fun[i].candidates.length;
                for (int j = 0; j < fun[i].candidates.length; j++) {
                    fun[i].candidates[j].hasSeat = true;
                }
            }
        } else { //if there are not more seats than candidates
            temp = this.getBallotCount();
            int assigned = 0;//To track how many seats have already been assigned
            int next = this.seatCount;
            int max = 1; //To count the maximum number of votes
            while (max != 0) { // If a party gets all the seats it has room for.
                max = 0;
                for (int i = 0; i < fun.length; i++) {
                    if (fun[i].totalVotes / this.quota >= fun[i].candidates.length) {
                        holdstr = "The " + fun[i].partyName + " party has enough votes to be assigned " +
                                (fun[i].totalVotes / this.quota) + " seats which means it gets a seat for all" + fun[i].candidates.length + " of it's candidates\n";
                        if (this.auditTracker.length() + holdstr.length() >= this.auditTracker.capacity()) {
                            this.auditTracker = (new StringBuffer((this.auditTracker.length() + holdstr.length()) * 2)).append(this.auditTracker);
                        }
                        this.auditTracker.append(holdstr);
                        fun[i].assignedSeats = fun[i].candidates.length;
                        temp -= fun[i].totalVotes;
                        fun[i].totalVotes = 0;
                        max++;
                        next -= fun[i].candidates.length;
                        assigned++;
                    }
                }
                if (this.quota > temp / next) {
                    this.quota = temp / next;
                    holdstr = "Because some of the parties got more votes than they needed the new minimum quota a party needs " +
                            "to guarantee a seat is " + this.quota + " votes\n";
                    if (this.auditTracker.length() + holdstr.length() >= this.auditTracker.capacity()) {
                        this.auditTracker = (new StringBuffer((this.auditTracker.length() + holdstr.length()) * 2)).append(this.auditTracker);
                    }
                    this.auditTracker.append(holdstr);
                }
            }
            if (assigned == 0) {
                holdstr = "There are no parties that are guaranteed seats for all their candidates\n";
                if (this.auditTracker.length() + holdstr.length() >= this.auditTracker.capacity()) {
                    this.auditTracker = (new StringBuffer((this.auditTracker.length() + holdstr.length()) * 2)).append(this.auditTracker);
                }
                this.auditTracker.append(holdstr);
            }
            assigned = 0;
            next = 0;
            max = 0;
            for (int i = 0; i < fun.length; i++) {
                fun[i].assignedSeats += voteforp[i] / this.quota;
                //System.out.println(this.parties[i].assignedSeats);
                assigned += fun[i].assignedSeats;
                if (fun[i].assignedSeats == 0) {
                    holdstr = "The " + fun[i].partyName + " party did not get enough votes to meet the" +
                            " quota with only " + voteforp[i] + " vote so it is not guaranteed any seats and may at most get 1 seat";
                    if (this.auditTracker.length() + holdstr.length() >= this.auditTracker.capacity()) {
                        this.auditTracker = (new StringBuffer((this.auditTracker.length() + holdstr.length()) * 2)).append(this.auditTracker);
                    }
                    this.auditTracker.append(holdstr);
                    voteforp[i] = voteforp[i] % this.quota;
                } else if (fun[i].assignedSeats != fun[i].candidates.length) {
                    holdstr = "The " + fun[i].partyName + " party is guaranteed a total of " + fun[i].assignedSeats +
                            " seat(s) from their " + voteforp[i] + " votes and now have ";
                    if (this.auditTracker.length() + holdstr.length() >= this.auditTracker.capacity()) {
                        this.auditTracker = (new StringBuffer((this.auditTracker.length() + holdstr.length()) * 2)).append(this.auditTracker);
                    }
                    this.auditTracker.append(holdstr);
                    voteforp[i] = voteforp[i] % this.quota;
                    holdstr = "\n   the " + fun[i].partyName + " party's " + voteforp[i] + " votes not redeemed for a seat and so may win 1 more seat " +
                            "depending on how many votes the other parties have remaining \n";

                    if (this.auditTracker.length() + holdstr.length() >= this.auditTracker.capacity()) {
                        this.auditTracker = (new StringBuffer((this.auditTracker.length() + holdstr.length()) * 2)).append(this.auditTracker);
                    }
                    this.auditTracker.append(holdstr);
                }
            }
            holdstr = "\n";
            if (this.auditTracker.length() + holdstr.length() >= this.auditTracker.capacity()) {
                this.auditTracker = (new StringBuffer((this.auditTracker.length() + holdstr.length()) * 2)).append(this.auditTracker);
            }
            this.auditTracker.append(holdstr);
            while (assigned < this.seatCount) {
                max = 0;
                next = 0;
                for (int i = 0; i < fun.length; i++) {
                    if (max < voteforp[i]) {
                        max = voteforp[i];
                    }
                }
                for (int i = 0; i < fun.length; i++) {
                    if (max == voteforp[i]) {
                        next++;
                    }
                }
                if (assigned + next <= this.seatCount) { // Give seats to parties with highest remainder if there is no tie
                    for (int i = 0; i < fun.length; i++) { // more than assined seats
                        if (max == voteforp[i]) {
                            this.parties[i].assignedSeats++;
                            holdstr = fun[i].partyName + " has won an extra seat based on the amount of " +
                                    "votes it has left over (" + voteforp[i] + " votes) and now has a total of " + fun[i].assignedSeats + " assigned seats\n";
                            if (this.auditTracker.length() + holdstr.length() >= this.auditTracker.capacity()) {
                                this.auditTracker = (new StringBuffer((this.auditTracker.length() + holdstr.length()) * 2)).append(this.auditTracker);
                            }
                            this.auditTracker.append(holdstr);
                            voteforp[i] = 0;
                            assigned++;
                        }
                    }
                } else {// Give seats to parties with highest remainder if there is a tie
                    Party choice[] = new Party[next];
                    int oldmax[] = new int[fun.length];  //How we tell where the vote gets added
                    while (assigned < this.seatCount) {
                        int holdint = 0;
                        for (int i = 0; i < fun.length; i++) {
                            oldmax[i] = this.parties[i].assignedSeats;
                            if (max == voteforp[i]) {
                                choice[holdint] = this.parties[i];
                                holdint++;
                            }
                        }
                        this.pTiebreaker(choice).assignedSeats++;
                        assigned++;
                        for (int i = 0; i < fun.length; i++) {
                            if (oldmax[i] < this.parties[i].assignedSeats) {
                                voteforp[i] = 0;
                            }
                        }
                        next--;
                        choice = new Party[next];
                        oldmax = new int[next];
                    }
                }
            }
            auditTracker.append("\n");
            auditTracker.append("Assigning seats to candidates:\n");
            for (Party party : fun) { // for each party, assign the party's seats to the highest voted candidates
                int assignedSeats = 0;
                int partySeats = party.getSeatCount();
                if (partySeats == 0)
                    continue;
                auditTracker.append(party.getName()).append(" has ").append(party.getSeatCount()).append(" seats to give\n");

                ArrayList<Candidate> candidates = new ArrayList<>(Arrays.asList(party.getCandidates())); //candidate pool
                while (assignedSeats < partySeats) {

                    ArrayList<Candidate> bestCandidates = getBestCandidates(candidates); //currently highest voted candidates from party
                    while (bestCandidates.size() > 0 && assignedSeats < partySeats) {

                        if (bestCandidates.size() == 1) { //only one candidate, give them the seat
                            bestCandidates.get(0).hasSeat = true;
                            assignedSeats++;
                            auditTracker.append(" - ").append(bestCandidates.get(0).getName()).append(" has received a seat, ").append(party.getSeatCount() - assignedSeats).append(" seats remaining\n");
                            candidates.remove(bestCandidates.get(0)); //remove that candidate from candidate pool
                            bestCandidates = getBestCandidates(candidates); //get the next highest voted candidates

                        } else { //multiple best candidates with the same vote count
                            Candidate[] candidateArr = new Candidate[bestCandidates.size()];
                            Candidate candidate = Tiebreaker(bestCandidates.toArray(candidateArr)); //break the tie

                            auditTracker.append(" -- Tie between: ");
                            for (int i = 0; i < candidateArr.length; i++) {
                                auditTracker.append(candidateArr[i].getName());
                                if (i < candidateArr.length - 1)
                                    auditTracker.append(", ");
                            }
                            auditTracker.append("\n");

                            candidate.hasSeat = true;
                            assignedSeats++;
                            auditTracker.append(" - ").append(candidate.getName()).append(" has received a seat, ").append(party.getSeatCount() - assignedSeats).append(" seats remaining\n");
                            bestCandidates.remove(candidate); //remove the successful candidate from the best candidate pool
                        }
                    }
                }
            }
            auditTracker.append("\n");
        }
    }

    /**
     * This method calculates the quota for the election, which is determined by taking
     * the number of valid votes divided by the number of seats.
     *
     * @param numVotes an integer representing the total number of votes counted in the election
     * @param numSeats an integer representing the number of seats available for election
     * @return the quota of the election
     */
    public int initquota(int numVotes, int numSeats) {
        int quota = numVotes / numSeats;
        if (numVotes % numSeats > 0) {
            quota++;
        }
        if (quota == 0){
            quota = 1;
        }
        this.quota = quota;
        return quota;
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
        LocalDateTime moment;
        String name;
        this.allocate_seats(this.parties);
        if(this.fileName == null){
            moment = LocalDateTime.now().withNano(0);
            String currtime = moment.toString();
            currtime = currtime.replaceAll(":", "_");
            name = "media_at_" + currtime +  "_media.txt";
        }
        else{
            name = this.fileName + "_media.txt";
        }
        try {
            FileWriter fly = new FileWriter(new File(outputPath, name));
            fly.append("Seats each party got: \n");
            for (int i = 0; i < this.parties.length; i++) {
                fly.append(this.parties[i].partyName + " got : " + this.parties[i].assignedSeats + " seats with a total of " + this.parties[i].totalVotes + "votes" + "\n");
            }
            fly.append("The following candidates won a seat:\n");
            for (int i = 0; i < this.candidates.length; i++) {
                if (this.candidates[i].hasSeat) {
                    fly.append(this.candidates[i].name + " With : " + this.candidates[i].totalVotes + " Votes" + "\n");
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
            fl.append(this.auditTracker.toString());
            fl.flush();
            fl.close();

        } catch (IOException e) {
            System.out.println("Could not open file named " + name);
        }
    }

    /**
     * This method assigns the total votes to each candidate and to each party.
     *
     */
    void assignVotes() {
        for (int i = 0; i < this.parties.length; i++) {
            this.parties[i].assignedSeats = 0; //sets to zero
        }
        for (int i = 0; i < this.candidateCount; i++) {
            this.candidates[i].totalVotes = 0;
        }
        OPLBallot hold = null;
        for (int i = 0; i < this.getBallotCount(); i++) {
            if ((this.ballots[i]) instanceof OPLBallot) { //We know this will always be true
                hold = (OPLBallot) this.ballots[i];
            }
            this.candidates[hold.candidateVote].totalVotes += 1;
        }
        for (int i = 0; i < this.candidateCount; i++) {
            for (int j = 0; j < this.parties.length; j++) {
                if (this.candidates[i].party.equals(this.parties[j].partyName)) {
                    this.parties[j].totalVotes += this.candidates[i].totalVotes; //This will not be changed later
                }
            }
        }
    }


    /**
     * This method finds the the best performing candidates to return for use in other functions.
     *
     * @param candidates an array of Candidates
     * @return an ArrayList of the best candidates
     */
    private ArrayList<Candidate> getBestCandidates(ArrayList<Candidate> candidates){
        int bestVotes = 0;
        for (Candidate candidate : candidates){
            if (candidate.getTotalVotes() > bestVotes){
                bestVotes = candidate.getTotalVotes();
            }
        }

        ArrayList<Candidate> maxList = new ArrayList<>(6);
        for (Candidate candidate : candidates){
            if (candidate.getTotalVotes() == bestVotes){
                maxList.add(candidate);
            }
        }

        return maxList;
    }
}
