#include <vector>
#include <iomanip>
#include <cmath>
#include <iostream>

const int n = 11;
const double alpha = 1.3;

double func(double x, std::vector<std::vector<double>> dif) {
    double answer = 0;
    for (int i = 0; i < n; i++) {
        double add = dif[0][i + 1];
        for (int j = 0; j < i; j++) {
            add *= (x - dif[j][0]);
        }
        answer += add;
    }
    return answer;
}

double f(double x) {
    return (alpha * exp(x) + (1 - alpha) * sin(x));
}

void makeDif(std::vector<std::vector<double>> &dif, int _n) {
    for (int j = 2; j < _n + 1; j++) {
        for (int i = 0; i < _n - j + 1; i++) {
            dif[i][j] = (dif[i + 1][j - 1] - dif[i][j - 1]) / (dif[i + j - 1][0] - dif[i][0]);
        }
    }
}

double w(double x, std::vector<double> _x) {
    double answer = 1;
    for (int i = 0; i < n; i++) {
        answer *= (x - _x[i]);
    }
    return answer;
}

int main() {
    std::vector<double> x(n);
    x[0] = 1.0;
    for (int i = 1; i < n; i++) {
        x[i] = x[i - 1] + 0.1;
    }
    double x1 = 31.0 / 30, x2 = 46.0 / 30, x3 = 59.0 / 30;
    std::vector<std::vector<double>> dif(n + 1, std::vector<double>(n + 2));
    for (int i = 0; i < n; i++) {
        dif[i][0] = x[i];
        dif[i][1] = f(x[i]);
    }
    makeDif(dif, n);
    std::cout << "f(x1) = " << func(x1, dif) << std::endl;
    std::cout << "f(x2) = " << func(x2, dif) << std::endl;
    std::cout << "f(x3) = " << func(x3, dif) << std::endl << std::endl;
    dif[n][0] = x1;
    dif[n][1] = f(x1);
    makeDif(dif, n + 1);
    std::cout << "Погрешность в х1 = " << dif[0][n + 1] * w(x1, x) << std::endl;
    std::cout << "Истинная погрешность в x1 = " << func(x1, dif) - f(x1) << std::endl << std::endl;
    dif[n][0] = x2;
    dif[n][1] = f(x2);
    makeDif(dif, n + 1);
    std::cout << "Погрешность в х2 = " << dif[0][n + 1] * w(x2, x) << std::endl;
    std::cout << "Истинная погрешность в x2 = " << func(x2, dif) - f(x2) << std::endl << std::endl;
    dif[n][0] = x3;
    dif[n][1] = f(x3);
    makeDif(dif, n + 1);
    std::cout << "Погрешность в х3 = " << dif[0][n + 1] * w(x3, x) << std::endl;
    std::cout << "Истинная погрешность в x3 = " << func(x3, dif) - f(x3) << std::endl;
    return 0;
}