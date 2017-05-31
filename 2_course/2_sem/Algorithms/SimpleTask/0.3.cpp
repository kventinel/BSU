#include <iostream>
#include <cstdio>
#include <vector>

struct matrix {
    int n;
    int m;
};

int rek(int i, int j, const std::vector<matrix> &matrices,
        std::vector<std::vector<int>> *answers) {
  if ((*answers)[i][j] == -1) {
    int minimal_answer = 1000000000;
    for (int k = i; k < j; ++k) {
      minimal_answer = std::min(minimal_answer, rek(i, k, matrices, answers) +
                                                matrices[i].n * matrices[k].m *
                                                matrices[j].m +
                                                rek(k + 1, j, matrices,
                                                    answers));
    }
    (*answers)[i][j] = minimal_answer;
  }
  return (*answers)[i][j];
}

int main() {
  freopen("input.txt", "r", stdin);
  freopen("output.txt", "w", stdout);
  int n;
  std::cin >> n;
  std::vector<matrix> matrices(n);
  for (matrix &m : matrices) {
    std::cin >> m.n >> m.m;
  }
  std::vector<std::vector<int>> answers(n, std::vector<int>(n, -1));
  for (int i = 0; i < n; ++i) {
    answers[i][i] = 0;
  }
  std::cout << rek(0, n - 1, matrices, &answers) << " ";
  return 0;
}
