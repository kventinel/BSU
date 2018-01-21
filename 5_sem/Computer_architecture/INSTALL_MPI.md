# Установка MPI на Linux

sudo apt install libcr-dev mpich:i386 mpich-doc

# Компиляция

mpic++ solution.cpp

# Запуск скомпилированного кода

mpirun -np 4 a.out