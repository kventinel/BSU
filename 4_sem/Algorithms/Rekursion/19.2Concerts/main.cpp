#include <iostream>
#include <cstdio>
#include <vector>
#include <utility>
#include <cinttypes>

struct cassette {
    int number;
    int64_t time;

    cassette() : number(-1), time(-1) {}

    cassette(int new_number, int64_t new_time) : number(new_number),
                                                 time(new_time) {}

    bool operator<(const cassette &other) const {
      if (number != other.number) {
        return number < other.number;
      }
      return time < other.time;
    }
};

int main() {
  freopen("concert.in", "r", stdin);
  freopen("concert.out", "w", stdout);
  int n, m;
  long long d;
  std::cin >> n >> m >> d;
  std::vector<int64_t> songs(n);
  for (int64_t &song : songs) {
    std::cin >> song;
  }
  std::vector<std::vector<cassette>> dp(n + 1, std::vector<cassette>(n + 1));
  dp[0][0].number = m - 1;
  dp[0][0].time = d;
  for (int i = 0; i < n; ++i) {
    for (int j = 0; j < n; ++j) {
      if (dp[i][j].number != -1) {
        if (dp[i][j].time >= songs[i]) {
          cassette new_value(dp[i][j].number, dp[i][j].time - songs[i]);
          dp[i + 1][j + 1] =
              dp[i + 1][j + 1] < new_value ? new_value : dp[i + 1][j + 1];
        } else if (songs[i] <= d && dp[i][j].number >= 1) {
          cassette new_value(dp[i][j].number - 1, d - songs[i]);
          dp[i + 1][j + 1] =
              dp[i + 1][j + 1] < new_value ? new_value : dp[i + 1][j + 1];
        }
        dp[i + 1][j] = dp[i + 1][j] < dp[i][j] ? dp[i][j] : dp[i + 1][j];
      }
    }
  }
  for (int j = n; j >= 0; --j) {
    if (dp[n][j].number != -1) {
      std::cout << j << std::endl;
      return 0;
    }
  }
  return 0;
}