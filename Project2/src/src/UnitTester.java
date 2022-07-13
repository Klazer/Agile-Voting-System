import election.*;


import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTester {

    public static void testBallot(){ //Testing getters for Ballot classes
        int[] testArray1 = {1,2,3,4,5};
        IRBallot IRtest1 = new IRBallot(testArray1, 3);
        OPLBallot OPLtest1 = new OPLBallot(4, 5);

        assertEquals(1,1);
        assertEquals(IRtest1.getBallotNum(), 3);
        assertEquals(IRtest1.getRankIndex(3), 2);
        assertEquals(IRtest1.getRankIndex(5), 4);
        assertEquals(IRtest1.getRankIndex(1), 0);

        assertEquals(IRtest1.getRankIndex(-2), -1);
        assertEquals(IRtest1.getRankIndex(6), -1);

        assertEquals(OPLtest1.getBallotNum(), 5);
        assertEquals(OPLtest1.getVote(), 4);

        assertEquals(IRtest1.isValid(), true);

        int[] testArray2 = {-1,-1,-1,1,2};
        IRtest1.setRanks(testArray2);
        assertEquals(IRtest1.isValid(), false);

        int[] testArray3 = {-1,-1,1,2,3};
        IRtest1.setRanks(testArray3);
        assertEquals(IRtest1.isValid(), true);

        int[] testArray4 = {-1,-1,-1,-1,1,2};
        IRtest1.setRanks(testArray4);
        assertEquals(IRtest1.isValid(), false);

        int[] testArray5 = {-1,-1,-1,1,2,3};
        IRtest1.setRanks(testArray5);
        assertEquals(IRtest1.isValid(), true);
    }

    public static void testCandidate(){
        Candidate candTest1 = new Candidate("Joey", "Republican", 24, 3, false);

        assertEquals(candTest1.getName(), "Joey");
        assertEquals(candTest1.getParty(), "Republican");
        assertEquals(candTest1.getRank(), 3);
        assertEquals(candTest1.getTotalVotes(), 24);
        assertEquals(candTest1.hasSeat(), false);

        candTest1.setRank(1);
        candTest1.setHasSeat(true);
        candTest1.setTotalVotes(33);

        assertEquals(candTest1.getRank(), 1);
        assertEquals(candTest1.getTotalVotes(), 33);
        assertEquals(candTest1.hasSeat(), true);
    }

    public static void testElection(){
        Candidate bob = new Candidate("Bob","yomama",0,0,false);
        Candidate charlse = new Candidate("Charlse","yomama",0,0,false);
        Candidate lenin = new Candidate("Lenin","communist",0,0,false);
        Candidate rudolph = new Candidate("Rudolph","christmas",0,0,false);
        Candidate bobs_evil_twin = new Candidate("Bob","EVIL!!!",0,0,false);
        Candidate first[] = {bob,charlse,lenin};
        Candidate second[] = {bob,charlse,rudolph,lenin,bobs_evil_twin};
        Candidate hold[] = {bob,charlse};
        OPLBallot b1 = new OPLBallot(1,1);
        OPLBallot b2 = new OPLBallot(0,2);
        OPLBallot b3 = new OPLBallot(0,3);
        OPLBallot b4 = new OPLBallot(1,4);
        OPLBallot b5 = new OPLBallot(0,5);
        OPLBallot b6 = new OPLBallot(2,6);
        OPLBallot b7 = new OPLBallot(1,7);
        OPLBallot b8 = new OPLBallot(2,8);
        OPLBallot b9 = new OPLBallot(1,9);
        OPLBallot b10 = new OPLBallot(2,10);
        Ballot box[] = new Ballot[]{b1,b2,b3};
        Party yomama = new Party("yomama",hold,0);
        hold = new Candidate[]{lenin};
        Party communist = new Party("communist",hold,0);
        hold = new Candidate[]{rudolph};
        Party christmas = new Party("christmas",hold,0);
        hold = new Candidate[]{bobs_evil_twin};
        Party evil = new Party("EVIL!!!",hold,0);
        Party fun[] = new Party[]{yomama,communist};
        Election doo = new Election("doo","", first.length, first, fun, box,3);
        assertEquals(doo.getBallotCount(),3);
        assertEquals(doo.getBallots(),box);
        assertEquals(3,doo.getBallotCount());
        assertEquals(3,doo.getCandidateCount());
        assertEquals(box,doo.getBallots());
        OPLElection ra = new OPLElection("ra",null,3,first,fun,box,2);
        OPLElection p1 = new OPLElection("p1",null,3,first,fun,new Ballot[]{b1,b2,b3,b4,b5},1);
        OPLElection p2 = new OPLElection("p2",null,3,first,fun,new Ballot[]{b6,b7,b8,b9,b10},1);
        p1.combine(p2);
        charlse.setHasSeat(false);
        bob.setHasSeat(false);
        lenin.setHasSeat(false);
        p1.process_media();
        assertEquals(true,charlse.hasSeat()); // Check combine for an OPL election where the winning canidate
        assertEquals(3,ra.getBallotCount()); //would not win in either of the individual elections
        assertEquals(3,ra.getCandidateCount());
        charlse.setHasSeat(false);
        bob.setHasSeat(false);
        lenin.setHasSeat(false);
        for (int i = 0; i < first.length; i++){
            first[i].setTotalVotes(0);
        }
        POBallot bp1 = new POBallot(1,1);
        POBallot bp2 = new POBallot(0,2);
        POBallot bp3 = new POBallot(0,3);
        POBallot bp4 = new POBallot(1,4);
        POBallot bp5 = new POBallot(0,5);
        POBallot bp6 = new POBallot(2,6);
        POBallot bp7 = new POBallot(1,7);
        POBallot bp8 = new POBallot(2,8);
        POBallot bp9 = new POBallot(1,9);
        POBallot bp10 = new POBallot(2,10);
        POElection pp1 = new POElection("pp1",null,3,first,fun,new Ballot[]{b1,b2,b3,b4,b5},1);
        POElection pp2 = new POElection("pp2",null,3,first,fun,new Ballot[]{b6,b7,b8,b9,b10},1);
        pp1.combine(pp2);
//      pp1.process_media(); // Tests that work with POelections does not cuase a crash
//      assertEquals(true,charlse.hasSeat());
//      assertEquals(3,ra.getBallotCount()); //would not win in either of the individual elections
//      assertEquals(3,ra.getCandidateCount());
        charlse.setHasSeat(false);
        bob.setHasSeat(false);
        lenin.setHasSeat(false);
        for (int i = 0; i < first.length; i++){
            first[i].setTotalVotes(0);
        }
        yomama.setSeatCount(0);
        ra.process_media();
        assertEquals(true,charlse.hasSeat());
        assertEquals(true,bob.hasSeat());
        charlse.setHasSeat(true);
        assertEquals(true,charlse.hasSeat());
        assertEquals(probCandidateTest(49000,51000,100000),true);// very low chance of failing if tiebreaker fair
        assertEquals(probPartyTest(49000,51000,100000,2),true);// very low chance of failing if ptiebreaker fair
        assertEquals(probPartyTest(9400,10600,100000,10),true);// very low chance of failing if ptiebreaker fair
        int choice1[] = new int[]{0,1,2};
        IRBallot ib1 = new IRBallot(choice1,1);
        choice1 = new int[]{1};
        IRBallot ib2 = new IRBallot(choice1,2);
        choice1 = new int[]{2};
        IRBallot ib3 = new IRBallot(choice1,3);
        choice1 = new int[]{1};
        IRBallot ib4 = new IRBallot(choice1,4);
        choice1 = new int[]{2};
        IRBallot ib5 = new IRBallot(choice1,5);
        choice1 = new int[]{2};
        IRBallot ib6 = new IRBallot(choice1,6);
        choice1 = new int[]{0,1};
        IRBallot ib7 = new IRBallot(choice1,7);
        box = new Ballot[]{ib1,ib2,ib3,ib4,ib5,ib6,ib7};
        IRElection ir1 = new IRElection(null,null,first.length,first,fun,box);
        IRElection ir2 = new IRElection(null,null,first.length,first,fun,box);
        ir2.combine(ir1);
        assertEquals(14,ir2.getBallotCount());
        assertEquals(7,ir1.getBallotCount());
        assertEquals(3,ir1.getCandidateCount());
        ir1.process_media();


        Candidate testCand1 = new Candidate("TestCand1", "TestPart1", 0,0, false);
        Candidate[] testCandArr = {testCand1};
        Party testPart1 = new Party("TestPart1", testCandArr, 0);
        Party[] testPartArr = {testPart1};
        int[] testRankArr = {0,1};
        IRBallot testBall1 = new IRBallot(testRankArr, 1);
        Ballot[] testBallArr = {testBall1};
        Election testElec1 = new Election("TestElec1", "", 1, testCandArr, testPartArr, testBallArr, 1);

        assertEquals(testElec1.getBallots(), testBallArr);

        IRElection testIRElec = new IRElection("TestIRElec", "", 1, testCandArr, testPartArr, testBallArr);

        testIRElec.process_media();
    }

    public static boolean probCandidateTest(int min, int max, int times){
        Candidate bob = new Candidate("Bob","yomama",0,0,false);
        Candidate charlse = new Candidate("Charlse","yomama",0,0,false);
        Candidate hold[] = {bob,charlse};
        Party yomama = new Party("yomama",hold,0);
        Ballot box[] = new Ballot[]{};
        Party fun[] = new Party[]{yomama};
        Election doo = new Election("ds","",hold.length,hold,fun,box,0);
        int count = 0;
        for (int i = 0; i < times; i++){
            if(doo.Tiebreaker(hold).getName().equals("Bob")){
                count++;
            }
        }
        if (count < min || count > max){
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean probPartyTest(int min, int max, int times,int amount){
        Ballot box[] = new Ballot[]{};
        Party fun[] = new Party[amount];
        Candidate bob = new Candidate("Bob","yomama",0,0,false);
        Candidate hold[] = {bob};
        Party yomama = new Party("yomama",hold,0);
        fun[0] = yomama;
        for (int i = 1; i < amount; i++){
            hold[0] = new Candidate(("puppet"+i),("master"+i),0,0,false);
            fun[i] = new Party(("master"+i),hold,0);
        }
        Election doo = new Election("fox","",3,hold,fun,box,0);
        int count = 0;
        for (int i = 0; i < times; i++){
            if(doo.pTiebreaker(fun).getName().equals("yomama")){
                count++;
            }
        }
        if (count < min || count > max){
            return false;
        }
        else {
            return true;
        }
    }

    public static void testParty(){
        Candidate candTest1 = new Candidate("Joey", "Republican", 24, 3, false);
        Candidate candTest2 = new Candidate("Bobby", "Republican", 1, 5, false);
        Candidate[] PartyArray = {candTest1, candTest2};
        Party pTest1 = new Party("Republican", PartyArray, 25);
        assertEquals(pTest1.getName(), "Republican");
        assertEquals(pTest1.getSeatCount(), 0);
        assertEquals(pTest1.getVotes(), 25);

        pTest1.setSeatCount(2);
        pTest1.updateVotes(30);

        assertEquals(pTest1.getSeatCount(), 2);
        assertEquals(pTest1.getVotes(), 30);
    }

    public static void testParser(){
        Candidate[] candidates = new Candidate[]{
                new Candidate("Bob", "C", 0, 0, false),
                new Candidate("Tom", "R", 0, 0, false),
                new Candidate("Isabelle", "R", 0, 0, false),
                new Candidate("Bill", "C", 0, 0, false),
                new Candidate("Maddie", "D", 0, 0, false),
                new Candidate("Ralph", "R", 0, 0, false),
                new Candidate("Rolando", "D", 0, 0, false)
        };

        Party[] parties = FileParser.createParties(candidates);

        for (Party party : parties){
            System.out.println("Party: " + party.getName());
            Candidate[] partyCandidates = party.getCandidates();
            for (Candidate cand : partyCandidates){
                assertEquals(cand.getParty(), party.getName());
                System.out.println(" - " + cand.getName() + ", " + cand.getParty());
            }
        }

        //Test OPL ballot reading
        String ballotLine1 = ",,,1,,";
        String ballotLine2 = "1,,,,,";
        String ballotLine3 = ",,,,,1";
        OPLBallot ballot1 = (OPLBallot) FileParser.getBallot(ballotLine1, 1, 1);
        OPLBallot ballot2 = (OPLBallot) FileParser.getBallot(ballotLine2, 1, 2);
        OPLBallot ballot3 = (OPLBallot) FileParser.getBallot(ballotLine3, 1, 3);
        OPLBallot ballot4 = (OPLBallot) FileParser.getBallot(",,,,1,", 1, 4);
        assertEquals(ballot1.getVote(), 3);
        assertEquals(ballot2.getVote(), 0);
        assertEquals(ballot3.getVote(), 5);

        //Test IR ballot reading
        String iBallotLine1 = "1,5,3,2,4";
        String iBallotLine2 = "4,3,2,1,5";
        String iBallotLine3 = ",2,,1,";
        IRBallot iBallot1 = (IRBallot) FileParser.getBallot(iBallotLine1, 0, 1);
        IRBallot iBallot2 = (IRBallot) FileParser.getBallot(iBallotLine2, 0, 2);
        IRBallot iBallot3 = (IRBallot) FileParser.getBallot(iBallotLine3, 0, 3);
        int[] ranks1 = iBallot1.getRanks();
        int[] ranks2 = iBallot2.getRanks();
        int[] ranks3 = iBallot3.getRanks();
        assertArrayEquals(new int[]{1,5,3,2,4}, ranks1);
        assertArrayEquals(new int[]{4,3,2,1,5}, ranks2);
        assertArrayEquals(new int[]{-1,2,-1,1,-1}, ranks3);

        //Test candidate line reading
        String IRCandidateLine = "Rosen (D), Kleinberg (R), Chou (I), Royce(L)";
        String OPLCandidateLine = "[Pike,D], [Foster,D],[Deutsch,R], [Borg,R], [Jones,R],[Smith,I] ";

        Candidate[] candidatesIR = FileParser.getCandidates(IRCandidateLine,0);
        Candidate[] candidatesOPL = FileParser.getCandidates(OPLCandidateLine,1);
        assertEquals(candidatesIR[0].getName(), "Rosen");
        assertEquals(candidatesIR[0].getParty(), "D");
        assertEquals(candidatesOPL[2].getName(), "Deutsch");
        assertEquals(candidatesOPL[2].getParty(), "R");

        POElection poElection = (POElection) FileParser.parse(new File[]{new File("test/PO_Test.csv")}, "", "PO_Out.txt");
        assertEquals(poElection.getBallotCount(), 9);
        assertEquals(poElection.getFileName(), "PO_Out.txt");
        assertEquals(poElection.getCandidateCount(), 6);
    }



    public static void main(String[] args){
        //testBallot();
        //testCandidate();
        testElection();
        //testParty();
        //testParser();
    }
}
