#include <iostream>
#include <vector>
#include <cmath>
#include <iomanip>

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

double func(double a, double x) {
    return a * exp(x) + (1 - a) * sin(x);
}

double grad(double a, double x) {
    return a * exp(x) + (1 - a) * cos(x);
}

double ges(double a, double x) {
    return a * exp(x) - (1 - a) * sin(x);
}

int main() {
    double a  = 1.3;
    std::vector<std::vector<double>> function{{1.0, 1.5, 2.0},
                                              {func(a, 1.0), func(a, 1.5), func(a, 2.0)},
                                              {grad(a, 1.0), grad(a, 1.5), grad(a, 2.0)},
                                              {ges(a, 1.0), ges(a, 1.5), ges(a, 2.0)}};
    Interpolation interpolation(function);
    double x = 1.906666666666666;
    std::cout << std::setprecision(20) << interpolation.value(x) - func(a, x);
    return 0;
}