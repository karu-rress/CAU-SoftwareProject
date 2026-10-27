#include <stdbool.h>
#include <stdio.h>

void allDivisors(int n, int a[]) {
    int c = 0;
    for (int i = 1; i <= n; i++) {
        if (n % i == 0)
            a[c++] = i;
    }
}

int countDivisors(int n) {
    int c = 0;
    for (int i = 1; i <= n; i++) {
        if (n % i == 0)
            c++;
    }
    return c;
}

int countSetDivisors(int n, int a[]) {
    int c = 0;
    for (int i = 1; i <= n; i++) {
        if (n % i == 0)
            a[c++] = i;
    }
    return c;
}

bool isPrime(int n) {
    return (countDivisors(n) == 2);
}

int main() {
    int num = 10;
    int a[100];
    int count = 0;
    int i;

    count = countSetDivisors(num, a);
    for (i = 0; i < count; i++)
        printf("%d\n", a[i]);

    allDivisors(num, a);
    count = countDivisors(num);
    for (i = 0; i < count; i++)
        printf("%d\n", a[i]);
    printf("number of divisors = %d\n", count);

    if (isPrime(7))
        printf("7 is a prime number");
    else
        printf("7 is not a prime number");
}
