public class Divisors {
    int number;             // 약수를 구할 수
    int count;              // 약수의 개수
    int[] divisors;         // 약수들 목록

    // 생성자
    public Divisors(int n) {
        this.number = n;
        this.count = 0;
        this.divisors = new int[100];

        // 기본적으로 allDivisors()를 통해 약수들을 저장
        allDivisors();
    }

    // number의 약수를 모두 divisors에 저장
    public void allDivisors() {
        count = 0;
        for (int i = 1; i <= number; i++) {
            if (number % i == 0)
                divisors[count++] = i;
        }
    }

    // 약수의 개수를 반환
    public int countDivisors() {
        return count;
    }

    // 약수를 저장하고, 그 개수를 반환
    public int countSetDivisors() {
        count = 0;
        for (int i = 1; i <= number; i++) {
            if(number % i == 0)
                divisors[count++] = i;
        }
        return count;
    }

    // divisors를 반환. 단, count 개수만큼만 반환.
    public int[] getDivisors() {
        int[] array = new int[count];
        System.arraycopy(divisors, 0, array, 0, count);
        return array;
    }

    // number이 소수인지 판별하는 함수
    public boolean isPrime() {
        return count == 2;
    }


    // 매개변수로 받은 수와 클래스 멤버변수의 공약수 개수를 반환
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
