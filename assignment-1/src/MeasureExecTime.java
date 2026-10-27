/**
 *
 * Spring 2024
 * Software Project
 *
 * MeasureExecTime.java
 *
 * Measure execution time of a code snippet.
 *
 */

public class MeasureExecTime {
    public static void main(String[] args) {
        long startTime;
        long endTime;
        long execTime;

        int N = 100000; // Loop count

        startTime = System.nanoTime();

        // For more accurate measurement, repeat the function call N times
        for (int i = 0; i < N; i++) {
            numCommonDivisor(20349, 2747115);
        }

        endTime = System.nanoTime();
        execTime = endTime - startTime;

        System.out.println("Execution time in nanoseconds: " + (double) (execTime / N));
    }

    /**
     * Returns the number of common divisors of two natural numbers n1 and n2.
     *
     * @param n1 First natural number
     * @param n2 Second natural number
     * @return Number of common divisors of n1 and n2
     */
    static int numCommonDivisor(int n1, int n2) {
        int min = (n1 < n2) ? n1 : n2;
        int count = 0;

        for (int i = 1; i <= min; i++) {
            if (n1 % i == 0 && n2 % i == 0)
                count++;
        }
        return count;
    }
}
