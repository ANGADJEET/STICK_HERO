package com.example.project;
import java.io.*;
import java.util.*;

public class HighScoreCalculator {
    public void addScore(int score) throws IOException {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter("./src/scores.txt", true));
            out.println(score);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        finally{
            if (null != out)
                out.close();
        }
    }

    public int calculateHighScore() throws IOException{
        Scanner in = null;
        int highScore = 0;
        ArrayList<Integer> c = new ArrayList();
        Iterator iter = c.iterator();

        try {
            in = new Scanner(new BufferedReader(new FileReader("./src/scores.txt")));
            while (in.hasNext()) {
                Integer e = Integer.valueOf(in.next());
                System.out.println(e);
                c.add(e);
            }
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
                in.close();
        }
        for(Integer i : c){
            if (highScore <= (Integer) i) {
                highScore = (Integer) i;
            }
        }
        return highScore;
    }
}