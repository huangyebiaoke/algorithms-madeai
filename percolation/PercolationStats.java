import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by <a>huang</a href="mailto:huangyebiaoke@outlook.com"> on 2022/3/28 11:27
 */
public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] record;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("The value of n and trials should more than 0.");
        this.record = new double[trials];
        for (int i = 0; i < record.length; i++) {
            Percolation site = new Percolation(n);
            while (!site.percolates()) {
                site.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
            record[i] = site.numberOfOpenSites() * 1.0 / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(record);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(record);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(record.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(record.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        // input n trails
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]),
                                                                 Integer.parseInt(args[1]));
        StdOut.println("mean                   " + " = " + percolationStats.mean());
        StdOut.println("stddev                 " + " = " + percolationStats.stddev());
        StdOut.println("95% confidence interval" + " = [" + percolationStats.confidenceLo() + ", "
                               + percolationStats.confidenceHi() + "]");
    }
}
