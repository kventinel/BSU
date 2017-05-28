#include <cmath>
#include <iostream>
#include <vector>

double KOEF = 1.3;
int POINTS_AMOUNT = 11;
double BEGIN = 1;
double END = 2;
int POWER = 5;
double WEIGHT = 1;
double STEP = (END - BEGIN) / (POINTS_AMOUNT - 1);
std::vector<double> CHECK_POINTS{
        BEGIN + STEP * 0 + STEP / 3.0,
        BEGIN + STEP * 5 + STEP / 3.0,
        BEGIN + STEP * 10 - STEP / 3.0
};

double f(double x){
    return (KOEF * pow(exp(1), x) + (1 - KOEF) * sin(x));
}

static double q(double x, const std::vector<double>& a){
    double res = 0;
    for (int i = 0; i <= POWER; i++){

        res += pow(x, i) * a[i];

    }
    return res;
}

std::vector<std::vector<double>> straightProcess(std::vector<std::vector<double>> matrix, int n){
    double maximum;
    int ind;
    for (int i = 0; i < n - 1; i++){
        maximum = matrix[i][i];
        ind = i;
        for (int j = i + 1; j < n; j++){
            if (maximum < matrix[j][i]){
                ind = j;
                maximum = matrix[j][i];
            }
        }
        double buf;
        for (int j = 0; j <= n; j++){
            buf = matrix[i][j];
            matrix[i][j] = matrix[ind][j];
            matrix[ind][j] = buf;
        }
        for (int j = i + 1; j < n; j++){
            for (int k = i + 1; k <= n; k++){
                matrix[j][k] -= matrix[i][k] * (matrix[j][i] / matrix[i][i]);
            }
        }
    }
    return matrix;
}

std::vector<double> reversedProcess(std::vector<std::vector<double>> matrix, int n){
    std::vector<double> a(n);
    for (int i = n - 1; i > 0; i--){
        matrix[i][n] /= matrix[i][i];
        a[i] = matrix[i][n];
        for (int j = i - 1; j > -1; j--){
            matrix[j][n] -= matrix[i][n] * matrix[j][i];
        }
    }
    a[0] = matrix[0][n] / matrix[0][0];
    return a;
}

std::vector<double> gaus(std::vector<std::vector<double>> matrix, int n){
    return reversedProcess(straightProcess(matrix, n), n);
}

std::vector<double> approximation(const std::vector<double>& a){
    std::vector<double> r(3);
    for (int i = 0; i < 3; i++){

        r[i] = fabs(f(CHECK_POINTS[i]) - q(CHECK_POINTS[i], a));

    }
    return r;
}

void output(const std::vector<double>& a, const std::vector<double>& r){
    for (int i = 0; i < POINTS_AMOUNT; i++){
        std::cout << q(BEGIN + STEP * i, a) << " ";
    }
    std::cout << std::endl;
    for (int i = 0; i <= POWER; i++){
        std::cout << a[i] << " ";
    }
    std::cout << std::endl << "f:" << std::endl;
    for (int i = 0; i < 3; i++){
        std::cout << f(CHECK_POINTS[i]) << " ";
    }
    std::cout << std::endl << "q:" << std::endl;
    for (int i = 0; i < 3; i++){
        std::cout << q(CHECK_POINTS[i], a) << " ";
    }
    std::cout << std::endl;
    for (int i = 0; i < 3; i++){
        std::cout << r[i] << std::endl;
    }
}

int main(){
    std::vector<std::vector<double>> equations(POWER + 1, std::vector<double>(POWER + 2));
    for (int i = 0; i <= POWER; i++){
        for (int j = 0; j <= POWER; j++){
            for (int k = 0; k < POINTS_AMOUNT; k++){
                equations[i][j] += WEIGHT * pow((BEGIN + STEP * k), i + j);
            }
        }
        for (int k = 0; k < POINTS_AMOUNT; k++){
            equations[i][POWER + 1] += WEIGHT * f(BEGIN + STEP * k) * pow(BEGIN + STEP * k, i);
        }
    }
    std::vector<double> a = gaus(equations, POWER + 1);
    std::vector<double> r = approximation(a);
    output(a, r);
    return 0;
}