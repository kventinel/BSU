#include <cmath>
#include <iostream>
#include <vector>

const int n = 11;
const double a = 1.3;
const double x1 = 31.0 / 30;
const double x2 = 46.0 / 30;
const double x3 = 59.0 / 30;
std::vector<double> nodes(n);
std::vector<double> fx(n);
const double right = 2;
const double left = 1;

double f(double x) {
    return a * exp(x) + (1 - a) * sin(x);
}

double f1(double x) {
    return a * exp(x) + (1 - a) * cos(x);
}

double f2(double x) {
    return a * exp(x) - (1 - a) * sin(x);
}

double f3(double x) {
    return a * exp(x) - (1 - a) * cos(x);
}

int factorial(int x) {
    if (x == 1 || x == 0)
        return 1;
    return x * factorial(x - 1);
}

double Lagranj(double x) {
    double ans = 0;
    for (int i = 0; i < n; i++) {
        double term = 1, xi = nodes[i];
        for (int j = 0; j < n; j++) {
            if (i == j) continue;
            double xj = nodes[j];
            term = term * (x - xj) / (xi - xj);
        }
        term *= f(xi);
        ans += term;
    }
    return ans;
}

void createNodes() {
    double add = (right + left) / 2., mul = (right - left) / 2.;
    for (int i = 0; i < n; ++i) {
        nodes[i] = add + mul * cos(M_PI * (2 * (n - i) + 1) / 2 / (n + 1));
        fx[i] = f(nodes[i]);
    }
}

double calcR_true(double x) {
    return fabs(Lagranj(x) - f(x));
}

double findTheoreticalResidual() {
    double mul = (right - left) / 2.;
    for (int i = 1; i < n; ++i)
        mul *= ((right - left) / (i + 1) / 4.);
    switch (n % 4) {
        case 0:
            return f1(right) * mul;
        case 1:
            return f2(right) * mul;
        case 2:
            return f3(right) * mul;
        case 3:
            return f(right) * mul;
    }
    return 0;
}

int main() {
    createNodes();
    std::cout << "nodes: " << std::endl;
    for (int i = 0; i < n; i++) {
        std::cout << nodes[i] << " ";
    }
    std::cout << std::endl;
    std::cout << "expected: " << findTheoreticalResidual() << std::endl;
    std::cout << "x*:" << std::endl;
    std::cout << "resalt: " << Lagranj(x1) << std::endl;
    std::cout << "true: " << calcR_true(x1) << std::endl;
    std::cout << "x**:" << std::endl;
    std::cout << "resalt: " << Lagranj(x2) << std::endl;
    std::cout << "true: " << calcR_true(x2) << std::endl;
    std::cout << "x***:" << std::endl;
    std::cout << "resalt: " << Lagranj(x3) << std::endl;
    std::cout << "true: " << calcR_true(x3) << std::endl;
    return 0;
}