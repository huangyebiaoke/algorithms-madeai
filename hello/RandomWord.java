/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String str = "";
        int i = 0;
        while (!StdIn.isEmpty()) {
            String temp = StdIn.readString();
            i++;
            // System.out.println("i:"+i+" temp:"+temp);
            if (StdRandom.bernoulli(1.0 / i)) {
                // System.out.println("prob:"+1.0/i);
                str = temp;
            }
        }
        StdOut.println(str);
    }
}
