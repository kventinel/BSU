#include <cmath>
#include <iomanip>
#include <iostream>

const double x0 = 1;
const double step = 0.1;
const int n = 10;
const double alpha = 1.3;
const double df11max = alpha * exp(2) - (1 - alpha) * cos(2);

double func(double x) {
    return alpha * exp(x) + (1 - alpha) * sin(x);
}

double Lagranj(double x) {
    double ans = 0;
    for (int i = 0; i <= n; i++) {
        double term = 1;
        double xi = x0 + i * step;
        for (int j = 0; j <= n; j++) {
            if (i == j) {
                continue;
            }
            double xj = x0 + j * step;
            term = term * (x - xj) / (xi - xj);
        }
        term *= func(xi);
        ans += term;
    }
    return ans;
}

double calcRn(double x) {
    double ans = df11max;
    for (int i = 0; i <= n; i++)
        ans = ans * fabs(x0 + step * i - x) / (i + 1);
    return ans;
}

double calcR_true(double x) {
    return fabs(Lagranj(x) - func(x));
}

int main() {
    double x = 31.0 / 30;
    double xx = 46.0 / 30;
    double xxx = 59.0 / 30;
    std::cout << "P1 " << Lagranj(x) << std::endl;
    std::cout << "P2 " << Lagranj(xx) << std::endl;
    std::cout << "P3 " << Lagranj(xxx) << std::endl;
    std::cout << "R1True " << calcR_true(x) << std::endl;
    std::cout << "R2True " << calcR_true(xx) << std::endl;
    std::cout << "R3True " << calcR_true(xxx) << std::endl;
    std::cout << "M " << df11max * pow(0.1, 11) / 44 << std::endl;
    std::cout << "R1 " << calcRn(x) << std::endl;
    std::cout << "R2 " << calcRn(xx) << std::endl;
    std::cout << "R3 " << calcRn(xxx) << std::endl;
    return 0;
}