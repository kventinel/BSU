#include <cmath>
#include <iostream>

double function(double x) {
    return pow(2.0, x) / (1 - pow(4.0, x));
}

double left_rectangle(double a, double b, double eps) {
    double old_int = function(a) * (b - a);
    int n = 2;
    while (true) {
        double new_int = 0;
        double h = (b - a) / n;
        for (int i = 0; i < n; ++i) {
            new_int += h * function(a + i * h);
        }
        if (fabs(new_int - old_int) < eps) {
            old_int = new_int + new_int - old_int;
            break;
        }
        old_int = new_int;
        n *= 2;
    }
    return old_int;
}

double get_trapeze_h(double a, double b, double df2max, double eps) {
    return sqrt(12 * eps / (b - a) / df2max);
}

double get_Simpson_h(double a, double b, double df4max, double eps) {
    return pow(eps * 180 / (b - a) / df4max, 0.25);
}

double factorial(int n) {
    double fact = 1;
    int i = 1;
    while (++i <= n) {
        fact *= i;
    }
    return fact;
}

int get_gauss_n(double a, double b, double eps) {
    int n = 1;
    double r = 1;
    double dfmax[16] = {0.8, 1.6, 5, 20, 100, 350, 2500, 2e4, 1.4e5, 1.2e6, 1.2e7, 1.2e8, 1.2e9,
                        1.4e10, 2e11, 4e12};
    while (n < 9 && r > eps) {
        r = dfmax[2 * n - 1] * (b - a) * pow(2.0, 2.0 * n) * pow(factorial(1 * n), 4.0) /
            (2.0 * n + 1.0) / pow(factorial(2 * n), 3.0);
        ++n;
    }
    return n - 1;
}

int main() {
    double df1max = log(2.0) * (pow(2.0, -1.0) + pow(8.0, -1.0)) / pow(1.0 - pow(4.0, -1.0), 2.0);
    double df2max = pow(log(2.0), 2.0) * (pow(2.0, -1.0) + 6.0 * pow(8.0, -1.0) + pow(32.0, -1.0)) /
                    pow(1.0 - pow(4.0, -1.0), 3.0);
    double df4max = pow(log(2.0), 4.0) *
                    (pow(2.0, -1.0) + 76.0 * pow(8.0, -1.0) + 230 * pow(32.0, -1.0) +
                     76.0 * pow(128.0, -1.0) + pow(512.0, -1.0)) / pow(1.0 - pow(4.0, -1.0), 5.0);
    double a = -2;
    double b = -1;
    double eps = 1e-5;
    std::cout << "Function = " << log(1.8) / log(4.0) << std::endl;
    std::cout << "Left Rectangle integral = " << left_rectangle(a, b, eps) << std::endl;
    std::cout << "Eps = " << fabs(left_rectangle(a, b, eps) - log(1.8) / log(4.0)) << std::endl;
    std::cout << "Trapeze h = " << get_trapeze_h(a, b, df2max, eps) << std::endl;
    std::cout << "Simpson h = " << get_Simpson_h(a, b, df4max, eps) << std::endl;
    std::cout << "Gauss n = " << get_gauss_n(a, b, eps) << std::endl;
    return 0;
}