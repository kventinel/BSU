#include <iostream>
#include <cstdio>
#include <cstdint>
#include <queue>

int main() {
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    uint32_t n;
    uint64_t s;
    std::cin >> n >> s;
    uint32_t i = 1;
    uint64_t si = 0;
    uint64_t elem;
    uint64_t maxs = 0;
    std::queue<uint32_t> ans;
    while (std::cin >> elem) {
        ans.push(i);
        si += elem;
        if (s / n < si) {
            maxs = std::max(maxs, si);
            si = 0;
            ++i;
        }
    }
    std::cout << maxs << std::endl;
    while (!ans.empty()) {
        std::cout << ans.front() << " ";
        ans.pop();
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}