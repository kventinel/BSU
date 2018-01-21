#include <cmath>
#include <iomanip>
#include <iostream>

class Spline {
public:
    const double coef = 1.3;
    const int N = 10;
    double A, B, h;
    double *x, *f, *l, *v, *d, *c;

    Spline() {
        A = 1, B = 2, h = 0.1;
        x = new double[N + 1];
        f = new double[N + 1];
        l = new double[N];
        v = new double[N];
        d = new double[N + 1];
        c = new double[N + 1];
    }

    void initSweepCoefs() {
        for (int i = 0; i < N + 1; i++) {
            x[i] = A + i * h;
            f[i] = coef * exp(Spline::x[i]) + (1 - coef) * sin(Spline::x[i]);
        }
        l[0] = 0;
        v[N - 1] = 0;
        c[0] = c[N] = 2;
        d[0] = 2 * (coef * exp(A) - (1 - coef) * sin(A));
        d[N] = 2 * (2.1 * exp(B) - (1 - coef) * sin(B));
        for (int i = 0; i < N - 1; i++) {
            l[i + 1] = v[i] = -0.5;
            c[i + 1] = 2;
            d[i + 1] = (3 / h) * (((f[i + 2] - f[i + 1]) / h) - ((f[i + 1] - f[i]) / h));
        }
    }

    double S(int i, double x, double *mom) {
        return (mom[i - 1] * pow(Spline::x[i] - x, 3) / 6 * h) +
               (mom[i] * pow(x - Spline::x[i - 1], 3) / 6 * h) +
               ((Spline::x[i] - x) / h) * (f[i - 1] - ((mom[i - 1] * pow(h, 2)) / 6)) +
               ((x - Spline::x[i - 1]) / h) * (f[i] - ((mom[i] * pow(h, 2)) / 6));
    }

    double *solveMatrix() {
        double *x = new double[N + 1];
        double m;
        for (int i = 1; i < N + 1; i++) {
            m = v[i] / c[i - 1];
            c[i] = c[i] - m * l[i - 1];
            d[i] = d[i] - m * d[i - 1];
        }
        x[N] = d[N] / c[N];
        for (int i = N - 1; i >= 0; i--)
            x[i] = (d[i] - l[i] * x[i + 1]) / c[i];
        return x;
    }
};

int main() {
    Spline *spline = new Spline();
    spline->initSweepCoefs();
    double *moments = spline->solveMatrix();
    double *xch = new double[3];
    double *fch = new double[3];
    double *Rtrue = new double[3];
    double *Sch = new double[3];
    xch[0] = spline->x[0] + (spline->h / 3);
    xch[1] = spline->x[5] + (spline->h / 3);
    xch[2] = spline->x[10] - (spline->h / 3);
    int ind[3] = {1, 6, 10};
    for (int i = 0; i < 3; i++) {
        fch[i] = spline->coef * exp(xch[i]) + (1 - spline->coef) * sin(xch[i]);
        Sch[i] = spline->S(ind[i], xch[i], moments);
        Rtrue[i] = fabs(fch[i] - Sch[i]);
        std::cout << "f[" << i << "]: " << fch[i] << " " << "S[" << i << "]: " << Sch[i] << " "
                  << "Pogr[" << i << "]: " << Rtrue[i] << std::endl;
    }
    return 0;
}