/**
 * TODO: solve the backwash problem;
 * Created by <a>huang</a href="mailto:huangyebiaoke@outlook.com"> on 2022/3/28 11:27
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final WeightedQuickUnionUF site;
    private boolean[] isOpen;
    private final int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("The n should more than 0.");
        // site top bottom
        site = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        for (int i = 0; i < n; i++) {
            site.union(n * n, i);
            site.union(n * n + 1, n * n - 1 - i);
        }
        isOpen = new boolean[n * n + 2];
    }

    private void validateInRange(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException(
                    "The row and column indices are integers between 1 and n.");
    }

    private int dimToArr(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateInRange(row, col);
        int pos = dimToArr(row, col);
        if (!isOpen[pos]) {
            isOpen[pos] = true;
            if (!(pos - n < 0) && isOpen[pos - n]) {
                site.union(pos, pos - n);
            }
            if (!(pos + n > n * n - 1) && isOpen[pos + n]) {
                site.union(pos, pos + n);
            }
            if (pos % n != 0 && isOpen[pos - 1]) {
                site.union(pos, pos - 1);
            }
            if ((pos + 1) % n != 0 && isOpen[pos + 1]) {
                site.union(pos, pos + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateInRange(row, col);
        return isOpen[dimToArr(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateInRange(row, col);
        return isOpen(row, col) && site.find(dimToArr(row, col)) == site.find(n * n);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int sum = 0;
        for (boolean inner : isOpen) {
            if (inner)
                sum++;
        }
        return sum;
    }

    // does the system percolate?
    public boolean percolates() {
        return site.find(n * n) == site.find(n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 3;
        Percolation site = new Percolation(n);
        while (!site.percolates()) {
            site.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tempn = site.isOpen(i + 1, j + 1) ? 1 : 0;
                System.out.print(tempn + " ");
            }
            System.out.println();
        }
        System.out.println(site.numberOfOpenSites() * 1.0 / (n * n));
    }
}
