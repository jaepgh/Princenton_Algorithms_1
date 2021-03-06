/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
/**
 *
 * @author jaep2
 */
public class Permutation {
    public static void main(String[] args) // unit testing (optional)
    {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();
        
        while (!StdIn.isEmpty()) {
            randomQueue.enqueue(StdIn.readString());
        }
        
        
        for (int i = 0; i < k; i++) {
            StdOut.println(randomQueue.dequeue());
        }
        
    }

}
