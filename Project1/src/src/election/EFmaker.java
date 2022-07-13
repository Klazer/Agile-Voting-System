package election;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;



public class EFmaker { //Makes election files for testing perposes
    String fname;
    String fout;
    int fnum;

    EFmaker(String name, String output){
        this.fname = name;
        this.fout = output;
        this.fnum = 0;
    }

    public static void main(String[] args){
        EFmaker OPL = new EFmaker("IRout", "C:\\Users\\Isaia\\Desktop\\VoteFiles");
        OPL.makeirf(10, 3, null, null, 1000, 10, 5);
    }

    //creates OPL file with given number of candidates parties and votes
    public void makeoplf(int candidate,int parties, int partydistribution[],int votes[],int votenum,int seats){
        Random unkown = new Random();
        StringBuffer down = new StringBuffer(candidate*30+votenum*candidate+1000+votenum*2);
        down.append("OPL\n" + candidate + "\n");
        String lizards[] = new String[candidate];
        for (int i = 0; i < candidate; i++){
            lizards[i] = "candidate" + i;
        }
        String fun[] = new String[parties];
        for (int i = 0; i < parties; i++){
            fun[i] = "party" + i;
        }
        int dis[] = new int[parties];
        int hold = 0;
        int temp = 0;
        if (partydistribution == null){ //Random party distribution
            hold = candidate;
            for (int i = 0; i < parties-1; i++){
                temp = (int)  (unkown.nextInt(hold)/(unkown.nextInt(parties-i)+1));
                dis[i] = temp;
                hold -= temp;
            }
            dis[parties-1] = hold;
            temp = 0;
            hold = 0;
        }
        else {
            dis = partydistribution;
        }
        for (int i = 0; i < parties; i++){
            for (int j = 0; j < dis[i]; j++){
                down.append("[" + lizards[temp] + "," + fun[i] +"]");
                temp++;
                if (temp != candidate){
                    down.append(",");
                }
            }
        }
        dis = new int[candidate];
        temp = 0;
        if (votes == null){ //Random party distribution
            hold = votenum;
            for (int i = 0; i < candidate-1; i++){
                temp = (int)  (unkown.nextInt(hold)/(unkown.nextInt(candidate-i)+1));
                dis[i] = temp;
                hold -= temp;
            }
            dis[candidate-1] = hold;
            temp = 0;
            hold = 0;
        }
        else {
            dis = votes;
        }
        String votest = "";
        //int begin;
        down.append("\n" + seats +"\n" + votenum + "\n");
        hold = votenum;
        while (hold >0){
            temp = unkown.nextInt(dis.length); //randomly writes votes in the file
            //begin = 0;
            while (dis[temp] < 1){
                if (temp == dis.length-1){
                    temp = 0;
                }
                else{
                    temp++;
                }
            }
            dis[temp]--;
            for (int i = 0; i < temp; i++){
                votest = votest+ ",";
            }
            votest = votest + "1";
            for (int i = temp; i < candidate-1; i++){
                votest = votest+ ",";
            }
            down.append(votest +"\n");
            votest = "";
            hold--;
        }
        try {
            FileWriter fl = new FileWriter(new File(fout, (fname+fnum)));
            fl.append(down.toString());
            fl.flush();
            fl.close();
            fnum++;
        }
        catch (IOException e) {
            System.out.println("Could not open file named " + fname);
        }
    }
    public void makeirf(int candidate,int parties, int partydistribution[],int votes[],int votenum, int maxdi, int mindi){
        Random unkown = new Random();
        StringBuffer down = new StringBuffer(candidate*30+votenum*candidate*2+1000+votenum);
        down.append("IR\n" + candidate + "\n");
        String lizards[] = new String[candidate];
        for (int i = 0; i < candidate; i++){
            lizards[i] = "candidate" + i;
        }
        String fun[] = new String[parties];
        for (int i = 0; i < parties; i++){
            fun[i] = "party" + i;
        }
        int dis[] = new int[parties];
        int hold = 0;
        int temp = 0;
        if (partydistribution == null){ //Random party distribution
            hold = candidate;
            for (int i = 0; i < parties-1; i++){
                temp = (int)  (unkown.nextInt(hold)/(unkown.nextInt(parties-i)+1));
                dis[i] = temp;
                hold -= temp;
            }
            dis[parties-1] = hold;
            temp = 0;
            hold = 0;
        }
        else {
            dis = partydistribution;
        }
        for (int i = 0; i < parties; i++){
            for (int j = 0; j < dis[i]; j++){
                down.append(lizards[temp] + "(" + fun[i] +")");
                temp++;
                if (temp != candidate){
                    down.append(",");
                }
            }
        }
        dis = new int[candidate];
        temp = 0;
        if (votes == null){ //Random party distribution
            hold = votenum;
            for (int i = 0; i < candidate-1; i++){
                temp = (int)  (unkown.nextInt(hold)/(unkown.nextInt(candidate-i)+1));
                dis[i] = temp;
                hold -= temp;
            }
            dis[candidate-1] = hold;
            temp = 0;
            hold = 0;
        }
        else {
            dis = votes;
        }
        //int begin;
        down.append("\n" + votenum + "\n");
        hold = votenum;
        while (hold >0){
            temp = unkown.nextInt(dis.length); //randomly writes votes in the file
            //begin = 0;
            while (dis[temp] < 1){
                if (temp == dis.length-1){
                    temp = 0;
                }
                else{
                    temp++;
                }
            }
            dis[temp]--;
            down.append(makeirvote(temp,mindi,maxdi,unkown,candidate) + "\n");
            hold--;
        }
        try {
            FileWriter fl = new FileWriter(new File(fout, (fname+fnum)));
            fl.append(down);
            fl.flush();
            fl.close();
            fnum++;
        }
        catch (IOException e) {
            System.out.println("Could not open file named " + fname);
        }
    }

    String makeirvote(int first, int min, int max ,Random myst , int lon){
        int amount;
        if (max > min) {
            amount = myst.nextInt(max - min + 1) + min;
        }
        else {
            amount = min;
        }
        int cho[] = new int[lon];
        int temp;
        String ret = "";
        cho[first] = 1;
        while (amount > 1){
            temp = myst.nextInt(lon); //randomly writes votes in the file
            //begin = 0;
            while (cho[temp] > 0){
                if (temp == lon-1){
                    temp = 0;
                }
                else{
                    temp++;
                }
            }
            cho[temp] = amount;
            amount--;
        }
        for (int i = 0; i < lon-1; i++){
            if(cho[i] > 0){
                ret = ret + cho[i];
            }
            ret = ret+ ",";
        }
        if (cho[lon-1] > 0){
            ret = ret + cho[lon-1];
        }
        return ret;
    }
}


