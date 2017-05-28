#include <iostream>
#include <vector>
#include <cmath>
#include <iomanip>

const int n = 11;
const double alpha = 1.3;
const double x1 = 31.0 / 30;
const double x3 = 59.0 / 30;
std::vector<double> nodes;
const double x0 = 1;
const double step = 0.1;
std::vector<std::vector<double>> konechRazn;
const double accuracy = 1e-6;
const double df11max = alpha * exp(2) - (1 - alpha) * cos(1);

double f(double x) {
    return alpha * exp(x) + (1 - alpha) * sin(x);
}

double f1(double x) {
    return alpha * exp(x) + (1 - alpha) * cos(x);
}

double f2(double x) {
    return alpha * exp(x) - (1 - alpha) * sin(x);
}

void makeNodes() {
    nodes.resize(n);
    for (int i = 0; i < n; i++) {
        nodes[i] = x0 + i * (2 - 1) / 10.;
    }
}

void makeKR() {
    konechRazn.resize(n);
    for (int i = 0; i < n; i++) {
        konechRazn[i].resize(n);
        konechRazn[i][0] = f(nodes[i]);
    }
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < n - i; j++) {
            konechRazn[j][i] = konechRazn[j + 1][i - 1] - konechRazn[j][i - 1];
        }
    }
}

double begin(double x) {
    double t = (x - nodes[0]) / step;
    double answer = 0, k = 1;
    for (int i = 0; i < n; i++) {
        answer += konechRazn[0][i] * k;
        k *= (t - i) / (i + 1);
    }
    return answer;
}

double end(double x) {
    double t = (x - nodes[n - 1]) / step;
    double answer = 0, k = 1;
    for (int i = 0; i < n; i++) {
        answer += konechRazn[n - 1 - i][i] * k;
        k *= (t + i) / (i + 1);
    }
    return answer;
}

int beginAndAc(double x) {
    double t = (x - nodes[0]) / step;
    double answer = 0, k = 1;
    for (int i = 0; i < n; i++) {
        answer += konechRazn[0][i] * k;
        k *= (t - i) / (i + 1);
        if (fabs(answer - f(x)) < accuracy)
            return i;
    }
    return n;
}

int endAndAc(double x) {
    double t = (x - nodes[n - 1]) / step;
    double answer = 0, k = 1;
    for (int i = 0; i < n; i++) {
        answer += konechRazn[n - 1 - i][i] * k;
        k *= (t + i) / (i + 1);
        if (fabs(answer - f(x)) < accuracy)
            return i;
    }
    return n;
}

double expectedEnd(double x) {
    double t = (x - nodes[n - 1]) / step;
    double k = 1;
    for (int i = 0; i < n; i++)
        k *= (t + i) / (i + 1);
    return pow(step, n) * k * df11max;

}

double expectedBegin(double x) {
    double t = (x - nodes[0]) / step, k = 1;
    for (int i = 0; i < n; i++)
        k *= (t - i) / (i + 1);
    return pow(step, n) * k * df11max;
}

static void print() {
    std::cout << "x*: " << std:: endl << "resalt: " << begin(x1) << std::endl;
    std::cout << "expected: " << expectedBegin(x1) << std::endl;
    std::cout << "true: " << fabs(begin(x1) - f(x1)) << std::endl;
    std::cout << "degree with <= E-6: " << beginAndAc(x1) << std::endl;
    std::cout << "x***: " << std::endl << "resalt: " << end(x3) << std::endl;
    std::cout << "expected: " << expectedEnd(x3) << std::endl;
    std::cout << "true: " << fabs(end(x3) - f(x3)) << std::endl;
    std::cout << "degree with <= E-6: " << endAndAc(x3) << std::endl;
}

int main() {
    makeNodes();
    makeKR();
    print();
    return 0;
}