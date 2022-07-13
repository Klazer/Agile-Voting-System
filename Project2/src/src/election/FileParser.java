package election;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/** This class conducts the parsing of an Election. It will parse all of the important
 * information from a given election for the Election class
 * @author Isaiah Herr, Ben Keeney, Moritz Lindner, and Emma Barnes
 */

public class FileParser {

    /**
     * This method parses the given election file to determine if it is an
     * Instant Runoff election or an Open Party Listing election. The corresponding
     * election parser method is then called. Invalid election types are caught with
     * exceptions.
     *
     * @param files File array containing all input election files
     * @param outputPath String representing the desired output path for the media file
     * @param fileName String representing the desired name for the media file to be produced
     * @return either a parsed IRElection object or a parsed OPLElection object, depending on the election type
     */
    public static Election parse(File[] files, String outputPath, String fileName){
        Scanner scanner;;
        try {
            ArrayList<Election> elections = new ArrayList<>(files.length);
            // iterate over the election files and create individual election objects
            for (File file : files) {
                scanner = new Scanner(file);
                scanner.useDelimiter("\\s+");

                String type = scanner.next();
                if (type.equals("IR")) {
                    elections.add(parseIR(scanner, outputPath, fileName));
                } else if (type.equals("OPL")) { //OPL
                    elections.add(parseOPL(scanner, outputPath, fileName));
                } else {
                    elections.add(parsePO(scanner, outputPath, fileName));
                }
            }
            // merge elections into single election object
            Election baseElection = elections.get(0);
            for (int i = 1; i < elections.size(); i++){
                baseElection.combine(elections.get(i));
            }
            return baseElection;
        } catch (Exception e){
            System.out.println("Error reading election type");
            return null;
        }
    }

    /**
     * This method parses an Instant Runoff election.
     *
     * @param scanner Java I/O scanner object
     * @param outputPath the output path of any created files
     * @param fileName the name of the file to be created
     * @return IRElection object, containing the final state of the election
     */
    public static IRElection parseIR(Scanner scanner, String outputPath, String fileName){
        try {
            //Get candidate count
            int candidateCount = Integer.parseInt(scanner.next());

            //Get the IR candidates and create Party[]
            scanner.nextLine();
            String candidateLine = scanner.nextLine();
            Candidate[] candidates = getCandidates(candidateLine, 0);
            Party[] parties = createParties(candidates);

            //Get ballot count
            int ballotCount = Integer.parseInt(scanner.next());

            scanner.nextLine();

            //Retrieve ballots
            IRBallot[] ballots = new IRBallot[ballotCount];
            for (int i = 0; i < ballotCount; i++){
                String ballotLine = scanner.nextLine();
                ballots[i] = (IRBallot) getBallot(ballotLine, 0, i);
                int[] ranks = ballots[i].getRanks();
                if(!ballots[i].isValid()){
                    continue;
                }
                int validCount = 0;
                for (int j = 0; j < ranks.length; j++){
                    if (ranks[j]!=-1)
                        validCount++;
                }
                int[] newRanks = new int[validCount];
                for (int j = 0; j < validCount; j++){
                    newRanks[j] = ballots[i].getRankIndex(j+1);
                }
                ballots[i].setRanks(newRanks);
            }

            return new IRElection(fileName, outputPath, candidateCount, candidates, parties, ballots);

        } catch (Exception e){
            System.out.println("Error parsing IR election file");
            return null;
        }
    }

    /**
     * This method parses an Open Party Listing election
     *
     * @param scanner Java I/O scanner object
     * @param outputPath the output path of any created files
     * @param fileName the name of the file to be created
     * @return OPLElection object, containing the final state of the election
     */
    public static OPLElection parseOPL(Scanner scanner, String outputPath, String fileName){
        try {
            //Get candidate count
            int candidateCount = Integer.parseInt(scanner.next());

            //Get the OPL candidates and create Party[]
            scanner.nextLine();
            String candidateLine = scanner.nextLine();
            Candidate[] candidates = getCandidates(candidateLine, 1);
            Party[] parties = createParties(candidates);

            //Get the seat count
            int seatCount = Integer.parseInt(scanner.nextLine());

            //Get ballot count
            int ballotCount = Integer.parseInt(scanner.nextLine());

            //Retrieve ballots
            OPLBallot[] ballots = new OPLBallot[ballotCount];
            for (int i = 0; i < ballotCount; i++){
                String ballotLine = scanner.nextLine();
                ballots[i] = (OPLBallot) getBallot(ballotLine, 1, i);
            }

            return new OPLElection(fileName, outputPath, candidateCount, candidates, parties, ballots, seatCount);

        } catch (Exception e){
            System.out.println("Error parsing OPL election file");
            return null;
        }
    }

    /**
     * This method parses an Popularity election
     *
     * @param scanner Java I/O scanner object
     * @param outputPath the output path of any created files
     * @param fileName the name of the file to be created
     * @return POElection object, containing the final state of the election
     */
    public static POElection parsePO(Scanner scanner, String outputPath, String fileName){
        try {
            //Get candidate count
            int candidateCount = Integer.parseInt(scanner.next());

            //Get the OPL candidates and create Party[]
            scanner.nextLine();
            String candidateLine = scanner.nextLine();
            Candidate[] candidates = getCandidates(candidateLine, 1);
            Party[] parties = createParties(candidates);

            //Get ballot count
            int ballotCount = Integer.parseInt(scanner.nextLine());

            //Retrieve ballots
            POBallot[] ballots = new POBallot[ballotCount];
            for (int i = 0; i < ballotCount; i++){
                String ballotLine = scanner.nextLine();
                ballots[i] = (POBallot) getBallot(ballotLine, 2, i);
            }

            return new POElection(fileName, outputPath, candidateCount, candidates, parties, ballots, 0);

        } catch (Exception e){
            System.out.println("Error parsing PO election file");
            return null;
        }
    }

    /**
     * Takes a line containing candidate information from election file and turns it into
     * a Candidate[]
     *
     * @param candidateLine String from election file containing candidate information
     * @param electionType indicates election type (IR=0, OPL=1)
     * @return Candidate[], with candidates from candidateLine
     */
    public static Candidate[] getCandidates(String candidateLine, int electionType){
        if (electionType == 0){ //IR candidate parsing
            ArrayList<Candidate> candidateList = new ArrayList<>(5);
            candidateLine = candidateLine.replaceAll("\\s+", "");
            String[] candidates = candidateLine.split("\\,",-1);
            for (String candidateData : candidates){
                String[] separate = candidateData.split("\\(");
                String candidateName = separate[0];
                String candidateParty = separate[1].substring(0, separate[1].length()-1);

                Candidate candidate = new Candidate(candidateName, candidateParty, 0, 0, false);
                candidateList.add(candidate);
            }
            Candidate[] candidateArr = new Candidate[candidateList.size()];
            return candidateList.toArray(candidateArr);

        } else if (electionType == 1 || electionType == 2){ //OPL candidate parsing
            ArrayList<Candidate> candidateList = new ArrayList<>(5);

            candidateLine = candidateLine.replaceAll("\\s+", "");
            candidateLine = candidateLine.replaceAll("\\[(.*?)\\]", "$1");
            String[] candidates = candidateLine.split(",");
            for (int i = 0; i < candidates.length; i+=2){
                String candidateName = candidates[i];
                String candidateParty = candidates[i+1];

                Candidate candidate = new Candidate(candidateName, candidateParty, 0, 0, false);
                candidateList.add(candidate);
            }
            Candidate[] candidateArr = new Candidate[candidateList.size()];
            return candidateList.toArray(candidateArr);
        }
        return null;
    }

    /**
     * This method returns a Ballot and populates all ballot information inside of it.
     * Returns null if invalid election type.
     *
     * @param ballotLine a String representing the data line of the ballot
     * @param electionType an integer representing the type of the election, 0 for Instant Runoff and 1 for Open Party Listing
     * @param ballotNum an integer representing the ID number of the ballot
     * @return
     */
    public static Ballot getBallot(String ballotLine, int electionType, int ballotNum){
        if (electionType == 0){ //IR ballot parsing
            ArrayList<Integer> ranksList = new ArrayList<>(5);
            String[] strRanks = ballotLine.split("\\,", -1);

            //Fill ranksList with ranks found in ballot
            for (String strRank : strRanks){
                int rank = strRank.equals("") ? -1 : Integer.parseInt(strRank);
                ranksList.add(rank);
            }

            //Convert ranksList to primitive int[]
            int[] ranks = new int[ranksList.size()];
            for (int i = 0; i < ranksList.size(); i++){
                ranks[i] = ranksList.get(i);
            }
            return new IRBallot(ranks, ballotNum);

        } else if (electionType == 1){ //OPL ballot parsing
            int candidateVote = ballotLine.indexOf("1");
            return new OPLBallot(candidateVote, ballotNum);
        } else {
            int candidateVote = ballotLine.indexOf("1");
            return new POBallot(candidateVote, ballotNum);
        }
    }

    /**
     * This method creates an array of Party objects that contains all possible parties
     * involved in the election.
     * 
     * @param candidates a Candidate array containing all candidates present in the election
     * @return a Party array listing all parties in the election
     */
    public static Party[] createParties(Candidate[] candidates){
        //Create a list of all possible parties
        ArrayList<String> partyStrList = new ArrayList<>(5);
        for (Candidate candidate : candidates) {
            String party = candidate.getParty();
            if (!partyStrList.contains(party)) {
                partyStrList.add(party);
            }
        }

        //Iterate over parties
        ArrayList<Party> partyList = new ArrayList<>(5);
        for (String partyName : partyStrList){
            ArrayList<Candidate> candidateList = new ArrayList<>(5);
            //Iterate over candidates, pair candidates with respective partyName
            for (Candidate candidate : candidates){
                if (candidate.getParty().equals(partyName)){
                    candidateList.add(candidate);
                }
            }
            //Create Party object and add it to partyList
            Candidate[] candArray = new Candidate[candidateList.size()];
            Party party = new Party(partyName, candidateList.toArray(candArray), 0);
            partyList.add(party);
        }
        Party[] partyArr = new Party[partyList.size()];
        return partyList.toArray(partyArr);
    }
}
