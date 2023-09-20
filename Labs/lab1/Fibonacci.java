package Labs.lab1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Contain all the Fibonacci numbers that ends
 * with the given number in range up to nNumber
 *
 * @author Marich Oleg OI-25
 * @version 1.0
 */
public class Fibonacci {
    /**
     * Amount of numbers to be analysed
     */
    private final long nNumber;
    /**
     * The last digit the numbers have to end
     */
    private final int lastDigit;
    /**
     * List of all the Fibonacci numbers that ends
     * with <i>lastDigit</i> in range up to <i>nNumber</i>
     *
     * @see List
     */
    private final List<BigInteger> resultNumbers = new ArrayList<>();
    /**
     * List of all indexes of the Fibonacci numbers in the sequence
     * that ends with <i>lastDigit</i> in range up to <i>nNumber</i>
     *
     * @see ArrayList
     */
    private final ArrayList<Integer> resultOrdinalNumbers = new ArrayList<>();

    /**
     * Sets the value of <i>lastDigit</i> and <i>nNumber</i>
     *
     * @param lastDigit an integer
     * @param nNumber an integer
     * @see #lastDigit
     * @see #nNumber
     */
    public Fibonacci(int nNumber, int lastDigit) {
        this.nNumber = nNumber;
        this.lastDigit= lastDigit;
    }
    /**
     * Find all the numbers satisfying the requirement
     * Write them to <i>resultNumbers</i> and their ordinal number to <i>resultOrdinalNumbers</i>
     * Return resultNumbers
     *
     * @see #resultNumbers
     * @see #resultOrdinalNumbers
     * @return resultNumbers
     */
    public List<BigInteger> findNumbersEndsWith() {
        BigInteger fb1, fb2 = new BigInteger("1"), fb3 = new BigInteger("0");
        for (int i = 0; i < nNumber; i++) {
            if (endsWith(fb3)) {
                resultNumbers.add(fb3);
                resultOrdinalNumbers.add(i+1);
            }
            fb1 = fb2;
            fb2 = fb3;
            fb3 = fb1.add(fb2);
        }
        return resultNumbers;
    }

    /**
     * Print all numbers of <i>resultNumbers</i> and their ordinal number of <i>resultOrdinalNumbers</i>
     * as table that has <i>number_rows</i>
     *
     * @see #resultNumbers
     * @see #resultOrdinalNumbers
     * @param number_rows an integer
     */
    public void printResultAsTable(int number_rows) {
        System.out.print("\n\n\t\t\t\tTable of the first "+ nNumber + " Fibonacci numbers ends with digit " + lastDigit);

        if (resultNumbers.size() == 0) {
            System.out.println("\n\t\tNo such Fibonacci numbers that meet the requirements");
            return;
        }

        for (int i = 0; i < resultNumbers.size(); i++) {
            System.out.printf("%c%20d - %3d-nth number |", i%number_rows==0 ? '\n' : '\0',
                    resultNumbers.get(i), resultOrdinalNumbers.get(i));
        }
    }

    /**
     * Checks whether a given number meets the requirement
     *
     * @param number an instance of BigInteger
     * @see BigInteger
     * @return boolean
     */
    private boolean endsWith(BigInteger number) {
        return number.toString().endsWith(String.valueOf(lastDigit));
    }
}
