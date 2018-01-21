#include <iostream>
#include <vector>
#include <algorithm>
#include <memory>

int main() {

    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);

    size_t n, m;
    std::cin >> n >> m;
    std::vector<long long > a(n + 1);
    for (size_t i = 1; i <= n; ++i) {
        std::cin >> a[i];
    }

    std::vector<std::vector<long long>> f(m + 1,
                                          std::vector<long long>(n + 1, 2e9));

    for (size_t i = 0; i < m + 1; ++i) {
        f[i][0] = -2e9;
    }

    for (size_t i = 1; i <= n; ++i) {
        for (int j = m; j >= 0; --j) {
            auto it = std::upper_bound(f[j].begin(), f[j].end(), a[i]);
            if (*(it - 1) == a[i]) {
                --it;
            }
            (*it) = a[i];
            auto dist = std::distance(f[j].begin(), it);
            if (j < m) {
                *(f[j + 1].begin() + dist) = -2e9;

            }
        }
    }

    // for (auto i : f) {
    //     for (auto j : i) {
    //         std::cerr << j << " ";
    //     }
    //     std::cerr << "\n";
    // }

    std::cout << std::distance(f[m].begin(), std::find(f[m].begin(), f[m].end(), 2e9)) - 1;

    return 0;
}
