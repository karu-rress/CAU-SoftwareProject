public class MeasureExecTime {
    public static void main(String[] args) {
        long startTime, endTime, execTime;
        int N = 100000; // 반복 수행 횟수

        startTime = System.nanoTime();

        for (int i = 0; i < N; i++) { // 반복 수행을 해서 측정을 하면, 보다 정확한 결과
            numCommonDivisor(20349, 2747115);
        }

        endTime = System.nanoTime();
        execTime = endTime - startTime;

        System.out.println("Execution Time in nano seconds = " + (double)(execTime / N));
    }

    /**
     * 인자로 주어진 정수, n1과 n2의 공약수들의 개수를 리턴하는 함수
     * @return n1, n2의 공약수 개수
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