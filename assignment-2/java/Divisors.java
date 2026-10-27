/**
 *
 * Spring 2024
 * Software Project
 *
 * Divisors.java
 *
 * Divisors class to find all divisors of a number,
 * count them, check if the number is prime, and find
 * common divisors with another number.
 *
 */

public class Divisors {
    int number; // The number for which we want to find divisors
    int count; // The count of divisors found
    int[] divisors; // Array to store the divisors of the number

    /**
     * Constructor to initialize the Divisors object with a number.
     * It also initializes the count and divisors array, and calls allDivisors() to populate the divisors.
     *
     * @param n The number for which we want to find divisors
     */
    public Divisors(int n) {
        number = n;
        count = 0;
        divisors = new int[100];

        allDivisors();
    }

    /**
     * Store all divisors of the number in the array and update the count
     */
    public void allDivisors() {
        count = 0;
        for (int i = 1; i <= number; i++) {
            if (number % i == 0)
                divisors[count++] = i;
        }
    }

    /**
     * Return the count of divisors found
     * @return The number of divisors found.
     */
    public int countDivisors() {
        return count;
    }

    /**
     * Store all divisors of the number in the array and return the count
     * @return The number of divisors found.
     */
    public int countSetDivisors() {
        count = 0;
        for (int i = 1; i <= number; i++) {
            if (number % i == 0)
                divisors[count++] = i;
        }
        return count;
    }

    /**
     * Return the divisors. However, only return count number of divisors.
     * @return An array containing the divisors.
     */
    public int[] getDivisors() {
        int[] array = new int[count];
        System.arraycopy(divisors, 0, array, 0, count);
        return array;
    }

    /**
     * Check if the number is prime.
     * @return true if the number is prime, false otherwise.
     */
    public boolean isPrime() {
        return count == 2;
    }

    /**
     * Return the number of common divisors between the member variable and the parameter.
     * @param n The number to compare with the member variable.
     * @return The number of common divisors.
     */
    public int numCommonDivisor(int n) {
        final int min = Math.min(number, n);
        int count = 0;

        for (int i = 1; i <= min; i++) {
            if (number % i == 0 && n % i == 0)
                count++;
        }
        return count;
    }
}
