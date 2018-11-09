/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jaep2
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] siteOpen;
    private int openSites;
    private final int virtualTop;
    private final int virtualBottom;
    private final WeightedQuickUnionUF connections;
    private final WeightedQuickUnionUF connectionNoVirtual;

    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Invalid argument!");
        }
        this.siteOpen = new boolean[n][n];
        this.openSites = 0;
        this.virtualTop = 0;
        this.virtualBottom = n * n + 1;
        this.connections = new WeightedQuickUnionUF(n * n + 2);
        this.connectionNoVirtual = new WeightedQuickUnionUF(n * n + 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.siteOpen[i][j] = false;
            }
        }
    }

    public void open(int row, int col) {
        this.validIndex(row, col);
        if (!this.siteOpen[row - 1][col - 1]) {
            this.siteOpen[row - 1][col - 1] = true;
            this.openSites++;
            this.connectNeighbors(row, col);
        }
    }

    public boolean isOpen(int row, int col) {
        this.validIndex(row, col);
        return this.siteOpen[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        this.validIndex(row, col);
        return (this.connectionNoVirtual.connected(this.virtualTop,
                getOneDimensionPos(row, col)));
    }

    public int numberOfOpenSites() {
        return this.openSites;
    }

    public boolean percolates() {
        return this.connections.connected(this.virtualTop, this.virtualBottom);
    }

    private int getOneDimensionPos(int row, int col) {
        this.validIndex(row, col);
        return ((row - 1) * this.siteOpen.length) + col;
    }

    private void validIndex(int row, int col) {
        if (((row < 1) || (row > this.siteOpen.length)) || (col < 1) || (col > this.siteOpen[0].length)) {
            throw new IllegalArgumentException("Invalid row or column!");
        }
    }

    private void connectNeighbors(int row, int col) {
        int position = this.getOneDimensionPos(row, col);
        int dimension = this.siteOpen.length;

        int northNeighbor = (position <= dimension)
                ? this.virtualTop : (position - dimension);
        int southNeighbor = (position > (dimension * (dimension - 1)))
                ? this.virtualBottom : (position + dimension);
        int eastNeighbor = ((position % dimension) == 0)
                ? -1 : position + 1;
        int westNeighbor = (((position - 1) % dimension) == 0)
                ? -1 : position - 1;

        if (northNeighbor == this.virtualTop) {
            this.connections.union(this.virtualTop, position);
            this.connectionNoVirtual.union(this.virtualTop, position);
        } else if (this.isOpen(row - 1, col)) {
            this.connections.union(northNeighbor, position);
            this.connectionNoVirtual.union(northNeighbor, position);
        }
        if (southNeighbor == this.virtualBottom) {
            this.connections.union(this.virtualBottom, position);
        } else if (this.isOpen(row + 1, col)) {
            this.connections.union(southNeighbor, position);
            this.connectionNoVirtual.union(southNeighbor, position);
        }
        if ((eastNeighbor != -1) && (this.isOpen(row, col + 1))) {
            this.connections.union(eastNeighbor, position);
            this.connectionNoVirtual.union(eastNeighbor, position);
        }
        if ((westNeighbor != -1) && (this.isOpen(row, col - 1))) {
            this.connections.union(westNeighbor, position);
            this.connectionNoVirtual.union(westNeighbor, position);
        }
    }
}
