#include <iostream>
#include <cstdio>

int main() {
  freopen("input.txt", "r", stdin);
  freopen("output.txt", "w", stdout);
  long long n;
  std::cin >> n;
  long long number_nodes = 1;
  int order = 0;
  while (n != 0) {
    if (n % (number_nodes * 2) == number_nodes) {
      n -= number_nodes;
      std::cout << order << std::endl;
    }
    ++order;
    number_nodes *= 2;
  }
  return 0;
}
