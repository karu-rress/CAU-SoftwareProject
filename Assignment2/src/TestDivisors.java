public class TestDivisors {
    public static void main(String[] args) {
        // 첫 번째 Divisors 객체 생성
        Divisors d1 = new Divisors(10);

        for (int n : d1.getDivisors())
            System.out.println(n);

        d1.allDivisors();
        for (int n : d1.getDivisors())
            System.out.println(n);

        System.out.println("number of divisors = " + d1.countDivisors());

        // 두 번째 Divisors 객체 생성
        Divisors d2 = new Divisors(7);

        if (d2.isPrime())
            System.out.println("7 is a prime number");
        else
            System.out.println("7 is not a prime number");

        // 세 번째 Divisors 객체 생성
        Divisors d3 = new Divisors(12);
        System.out.println("# of common divisors between 12 and 16: "
                + d3.numCommonDivisor(16));
    }
}