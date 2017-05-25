#include <iostream>
#include <vector>
#include <cmath>
#include <iomanip>

const double a = 1.3;
double df9max = a * exp(2.0) + (1 - a) * cos(2);

class Interpolation {
public:
    Interpolation(const std::vector<std::vector<double>> &function) :
            divided_diff_(10, std::vector<double>(9)) {
        for (int i = 0; i < 9; ++i) {
            divided_diff_[0][i] = function[0][i / 3];
            divided_diff_[1][i] = function[1][i / 3];
        }
        for (int i = 1; i < 9; ++i) {
            if (i % 3 == 0) {
                divided_diff_[2][i] = (divided_diff_[1][i] - divided_diff_[1][i - 1]) /
                                      (divided_diff_[0][i] - divided_diff_[0][i - 1]);
            } else {
                divided_diff_[2][i] = function[2][i / 3];
            }
        }
        for (int i = 2; i < 9; ++i) {
            if (i % 3 != 2) {
                divided_diff_[3][i] = (divided_diff_[2][i] - divided_diff_[2][i - 1]) /
                                      (divided_diff_[0][i] - divided_diff_[0][i - 2]);
            } else {
                divided_diff_[3][i] = function[3][i / 3] / 2;
            }
        }
        for (int i = 4; i < 10; ++i) {
            for (int j = i - 1; j < 9; ++j) {
                divided_diff_[i][j] = (divided_diff_[i - 1][j] - divided_diff_[i - 1][j - 1]) /
                                      (divided_diff_[0][j] - divided_diff_[0][j - i + 1]);
            }
        }
    }

    double value(double point) {
        double res = 0;
        double k = 1;
        for (int i = 1; i < 10; ++i) {
            res += k * divided_diff_[i][i - 1];
            k *= (point - divided_diff_[0][i - 1]);
        }
        return res;
    }

private:
    std::vector<std::vector<double>> divided_diff_;
};

double func(double x) {
    return a * exp(x) + (1 - a) * sin(x);
}

double grad(double x) {
    return a * exp(x) + (1 - a) * cos(x);
}

double ges(double x) {
    return a * exp(x) - (1 - a) * sin(x);
}

double calcRn(double x) {
    double ans = df9max;
    double xk = 1.0;
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; ++j) {
            ans = ans * fabs(xk - x) / (i * 3 + j + 1);
        }
        xk += 0.5;
    }
    return ans;
}

int main() {
    double a  = 1.3;
    std::vector<std::vector<double>> function{{1.0, 1.5, 2.0},
                                              {func(1.0), func(1.5), func(2.0)},
                                              {grad(1.0), grad(1.5), grad(2.0)},
                                              {ges(1.0), ges(1.5), ges(2.0)}};
    Interpolation interpolation(function);
    double x1 = 31.0 / 30;
    double x2 = 46.0 / 30;
    double x3 = 59.0 / 30;
    std::cout << "X1=" << interpolation.value(x1) << std::endl;
    std::cout << "X2=" << interpolation.value(x2) << std::endl;
    std::cout << "X3=" << interpolation.value(x3) << std::endl;
    std::cout << "RX1=" << calcRn(x1) << std::endl;
    std::cout << "RX2=" << calcRn(x2) << std::endl;
    std::cout << "RX3=" << calcRn(x3) << std::endl;
    std::cout << "RX1True=" << fabs(interpolation.value(x1) - func(x1)) << std::endl;
    std::cout << "RX2True=" << fabs(interpolation.value(x2) - func(x2)) << std::endl;
    std::cout << "RX3True=" << fabs(interpolation.value(x3) - func(x3)) << std::endl;
    return 0;
}