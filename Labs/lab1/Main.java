package Labs.lab1;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * The program finds all the Fibonacci numbers
 * that satisfy the condition:
 * number ends with the given number
 *
 * @author Marich Oleg OI-25
 * @see Fibonacci
 * @since 15.09.2023
 */
public class Main {
    /**
     * This method works with instances of Fibonacci
     * @see Fibonacci
     * @param args parameters may be given via cmd
     */
    public static void main(String[] args) {
        int kst, nNumber, lastDigit; // SOLID
        List<Fibonacci> fibonacciNumbers = new LinkedList<>();

        // User Input
        if (args.length == 0) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter amount of Fibonacci sequences to find numbers ends with given digit\t");
            kst = sc.nextInt();

            for (int i = 0; i < kst; i++) {
                System.out.print("Enter amount of Fibonacci numbers to check which of them meet requirements\t");
                nNumber = sc.nextInt();
                System.out.print("Enter digit:\t");
                lastDigit = sc.nextInt();
                fibonacciNumbers.add(new Fibonacci(nNumber, lastDigit));
            }
        }
        // Console Input
        else {
            kst = Integer.parseInt(args[0]);
            for (int i = 1; i < kst * 2; i++) {
                nNumber = Integer.parseInt(args[i++]);
                lastDigit = Integer.parseInt(args[i]);
                fibonacciNumbers.add(new Fibonacci(nNumber, lastDigit));
            }
            System.out.println("\n\t\t\tFrom the console was read " + kst + " inputs");
        }
        // Print results
        for (int i = 0; i < kst; i++) {
            fibonacciNumbers.get(i).findNumbersEndsWith();
            fibonacciNumbers.get(i).printResultAsTable(4);
        }
    }
}