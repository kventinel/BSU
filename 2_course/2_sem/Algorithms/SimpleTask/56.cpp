#include <iostream>
#include <cstdio>
#include <vector>

int main() {
  freopen("input.txt", "r", stdin);
  freopen("output.txt", "w", stdout);
  int n;
  std::cin >> n;
  std::vector<int> heap(n);
  for (int &a : heap) {
    std::cin >> a;
  }
  for (int i = n - 1; i > 0; --i) {
    if (heap[i] < heap[(i - 1) / 2]) {
      std::cout << "No\n";
      return 0;
    }
  }
  std::cout << "Yes\n";
  return 0;
}
