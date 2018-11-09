/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jaep2
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private static final double MULTIPLIER = 1.96;
    private final double stdDeviation;
    private final double mean;
    private final int experiments;

    public PercolationStats(int n, int trials) // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Incorrect arguments");
        }
        this.experiments = trials;

        final double[] results = new double[experiments];
        for (int i = 0; i < this.experiments; i++) {
            Percolation percolator = new Percolation(n);

            while (!percolator.percolates()) {
                int randomX = StdRandom.uniform(1, n + 1);
                int randomY = StdRandom.uniform(1, n + 1);
                percolator.open(randomX, randomY);
            }

            results[i] = percolator.numberOfOpenSites() / Math.pow(n, 2);
        }
        stdDeviation = StdStats.stddev(results);
        mean = StdStats.mean(results);
    }

    public double mean() // sample mean of percolation threshold
    {
        return this.mean;
    }

    public double stddev() // sample standard deviation of percolation threshold
    {
        return this.stdDeviation;
    }

    public double confidenceLo() // low  endpoint of 95% confidence interval
    {
        return this.mean() - MULTIPLIER * stddev()
                / Math.sqrt(this.experiments);
    }

    public double confidenceHi() // high endpoint of 95% confidence interval
    {
        return this.mean() + MULTIPLIER * stddev()
                / Math.sqrt(this.experiments);
    }

    public static void main(String[] args) {
        PercolationStats tester = new PercolationStats(
                Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        tester.output();
    }

    private void output() {
        StdOut.printf("mean\t\t\t= %f\n"
                + "stddev\t\t\t= %f\n"
                + "95%% confidence interval\t= %f, %f\n",
                this.mean(), stddev(),
                confidenceLo(), confidenceHi());
    }
}
