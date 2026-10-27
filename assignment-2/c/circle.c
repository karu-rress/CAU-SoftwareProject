#include <stdio.h>

typedef struct {
    float radius;
    float center_x;
    float center_y;
} circle;

#define PI 3.14

void print(circle c) {
    printf("radius = %f, x of center = %f, y of center = %f\n", c.radius, c.center_x, c.center_y);
}

float area(circle c) {
    return PI * c.radius * c.radius;
}

int equal(circle c1, circle c2) {
    if ((c1.radius == c2.radius) && (c1.center_x == c2.center_x) && (c1.center_y == c2.center_y))
        return 1;
    else
        return 0;
}

float compare(circle c1, circle c2) {
    return area(c1) - area(c2);
}

void resize(circle *c, float ratio) {
    c->radius *= ratio;
}

int main() {
    circle c1;
    circle c2 = {2.0, 0.0, 0.0};
    circle c3 = {1.0, 1.0, 1.0};

    if (equal(c2, c3))
        puts("two circles are equal.");
    else
        puts("two circles are different.");

    if (compare(c2, c3) > 0.0)
        puts("the first is larger than the second.");
    else if (compare(c2, c3) < 0.0)
        puts("the first is smaller than the second.");
    else
        puts("two are the same in area.");

    print(c3);
    resize(&c3, 2.0);
    print(c3);
}
