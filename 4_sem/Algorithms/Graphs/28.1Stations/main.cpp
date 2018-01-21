#include <fstream>
#include <vector>
#include <set>
#include <utility>
#include <stack>
#include <algorithm>
#include <queue>

struct road {
    int town;
    int length;

    road() : town(0), length(0) {}

    road(int new_town, int new_length) : town(new_town), length(new_length) {}

    bool operator<(const road &other) const {
      return length < other.length;
    }
};

int main() {
  std::ifstream fin("input.txt");
  std::ofstream fout("output.txt");
  int n, m;
  fin >> n >> m;
  int z, x;
  fin >> z >> x;
  int a, b;
  fin >> a >> b;
  --a;
  --b;
  std::vector<int> refill(n);
  for (int &temp : refill) {
    fin >> temp;
  }
  std::vector<std::vector<road>> roads(n, std::vector<road>());
  while (m-- > 0) {
    int first, second, fuel;
    fin >> first >> second >> fuel;
    roads[--first].push_back(road(--second, fuel));
    roads[second].push_back(road(first, fuel));
  }
  for (auto &temp : roads) {
    std::sort(temp.begin(), temp.end());
  }
  std::vector<std::vector<int>> answer(n, std::vector<int>(z + 1, -1));
  answer[a][z] = 0;
  std::multiset<std::pair<int, std::pair<int, int>>> deikstra_full;
  deikstra_full.insert(std::make_pair(0, std::make_pair(a, z)));
  int index = -1;
  while (!deikstra_full.empty()) {
    if (index != -1 && deikstra_full.begin()->first >= answer[b][index]) {
      break;
    }
    std::queue<std::pair<int, std::pair<int, int>>> deikstra;
    deikstra.push(*deikstra_full.begin());
    deikstra_full.erase(deikstra_full.begin());
    while (!deikstra.empty()) {
      int town = deikstra.front().second.first;
      int fuel = deikstra.front().second.second;
      deikstra.pop();
      for (int i = 0; i < roads[town].size(); ++i) {
        int new_fuel = fuel - roads[town][i].length * x;
        if (new_fuel >= 0) {
          int new_town = roads[town][i].town;
          if (answer[new_town][new_fuel] == -1) {
            answer[new_town][new_fuel] = answer[town][fuel];
            deikstra.push(std::make_pair(answer[town][fuel],
                                         std::make_pair(new_town, new_fuel)));
            if (new_town == b &&
                (index == -1 || answer[b][index] > answer[b][new_fuel])) {
              index = new_fuel;
            }
          }
        } else {
          break;
        }
      }
      if (fuel * 2 < z && refill[town] != 0) {
        if (answer[town][z] == -1) {
          answer[town][z] = answer[town][fuel] + refill[town];
          deikstra_full.insert(
              std::make_pair(answer[town][z], std::make_pair(town, z)));
        } else if (answer[town][z] > answer[town][fuel] + refill[town]) {
          deikstra_full.erase(
              std::make_pair(answer[town][z], std::make_pair(town, z)));
          answer[town][z] = answer[town][fuel] + refill[town];
          deikstra_full.insert(
              std::make_pair(answer[town][z], std::make_pair(town, z)));
        }
      }
    }
  }
  if (index == -1) {
    fout << "No\n";
  } else {
    fout << "Yes\n";
    std::stack<int> output;
    while (b != a || index != z) {
      if (index == z) {
        output.push(-b - 1);
        index = (z - 1) / 2;
        while (answer[b][index] + refill[b] != answer[b][z]) {
          --index;
        }
      } else {
        output.push(b + 1);
      }
      for (int i = 0; i < roads[b].size(); ++i) {
        if (roads[b][i].length * x + index <= z &&
            answer[roads[b][i].town][roads[b][i].length * x + index] ==
            answer[b][index]) {
          index += roads[b][i].length * x;
          b = roads[b][i].town;
          break;
        }
      }
    }
    output.push(a + 1);
    while (!output.empty()) {
      fout << output.top();
      output.pop();
      if (!output.empty()) {
        fout << " ";
      }
    }
    fout << std::endl;
  }
  return 0;
}