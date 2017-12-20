#include <math.h>
#include <mpi.h>
#include <iostream>
#include <fstream>

int main(int argc, char* argv[]) {
    int h;
    int        rank, value, size, errcnt, toterr, i, j, itcnt;
    int        i_first, i_last;
    MPI_Status status;
    std::fstream fin("input.txt");
    int maxn;
    fin >> maxn >> h;
    double* x = new double[maxn * maxn];
    double* f = new double[maxn * maxn];

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (rank == 0) {
        for (i = maxn - 1; i >= 0; i--) {
            for (j = 0; j < maxn; j++) {
                fin >> x[i * maxn + j];
            }
        }
        for (i = maxn - 1; i >= 0; i--) {
            for (j = 0; j < maxn; j++) {
                fin >> f[i * maxn + j];
            }
        }
    }

    double* xlocal = new double[((maxn/size)+2) * maxn];
    double* flocal = new double[((maxn/size)+2) * maxn];
    double* xnew = new double[((maxn/size)+2) * maxn];

    MPI_Scatter(x, maxn * (maxn/size), MPI_DOUBLE,
                xlocal + maxn, maxn * (maxn/size), MPI_DOUBLE,
                0, MPI_COMM_WORLD);

    MPI_Scatter(f, maxn * (maxn/size), MPI_DOUBLE,
                flocal + maxn, maxn * (maxn/size), MPI_DOUBLE,
                0, MPI_COMM_WORLD);

    MPI_Bcast(&h, 1, MPI_INT, 0, MPI_COMM_WORLD);

    i_first = 1;
    i_last  = maxn / size;
    if (rank == 0)        i_first++;
    if (rank == size - 1) i_last--;

    itcnt = 0;
    double gdiffnorm;
    do {
        if (rank > 0)
            MPI_Send(xlocal + maxn, maxn, MPI_DOUBLE, rank - 1, 1, 
                     MPI_COMM_WORLD);
        if (rank < size - 1)
            MPI_Recv(xlocal + (maxn/size + 1) * maxn, maxn, MPI_DOUBLE, rank + 1, 1,
                     MPI_COMM_WORLD, &status);
        if (rank < size - 1)
            MPI_Send(xlocal + (maxn/size) * maxn, maxn, MPI_DOUBLE, rank + 1, 0,
                     MPI_COMM_WORLD);
        if (rank > 0)
            MPI_Recv(xlocal, maxn, MPI_DOUBLE, rank - 1, 0,
                     MPI_COMM_WORLD, &status);

        itcnt++;
        double diffnorm = 0.0;
        for (i = i_first; i <= i_last; i++) {
            for (j = 1; j < maxn - 1; j++) {
                xnew[i * maxn + j] = (xlocal[i * maxn + j + 1] + xlocal[i * maxn + j - 1] +
                              xlocal[(i + 1) * maxn + j] + xlocal[(i - 1) * maxn + j] - h * h * f[i* maxn + j]) / 4.0;
                diffnorm += (xnew[i* maxn + j] - xlocal[i* maxn + j]) *
                            (xnew[i* maxn + j] - xlocal[i* maxn + j]);
            }
        }
        for (i = i_first; i <= i_last; i++)
            for (j = 1; j < maxn - 1; j++)
                xlocal[i* maxn + j] = xnew[i* maxn + j];

        if (itcnt == 1) {
            std::cout << diffnorm << std::endl;
        }

        MPI_Allreduce(&diffnorm, &gdiffnorm, 1, MPI_DOUBLE, MPI_SUM,
                      MPI_COMM_WORLD);
        gdiffnorm = sqrt(gdiffnorm);
        if (itcnt == 1) {
            std::cout << gdiffnorm << std::endl;
        }
        if (rank == 0) {
            std::cout << "At iteration " << itcnt << ", diff is " << gdiffnorm << std::endl;
        }
    } while (gdiffnorm > 1.0e-2 && itcnt < 100);

    MPI_Gather(xlocal + maxn, maxn * (maxn/size), MPI_DOUBLE,
               x, maxn * (maxn/size), MPI_DOUBLE,
               0, MPI_COMM_WORLD);
    if (rank == 0) {
        std::ofstream fout("output.txt");
        for (i = maxn-1; i >= 0; i--) {
            for (j = 0; j < maxn; j++) {
                fout << x[i* maxn + j] << " ";
            }
            fout << std::endl;
        }
    }

    delete [] xlocal;
    delete [] xnew;
    delete [] flocal;

    MPI_Finalize();

    delete [] x;
    delete [] f;
    return 0;
}
