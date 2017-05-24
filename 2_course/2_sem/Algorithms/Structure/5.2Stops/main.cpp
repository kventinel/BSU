#include <cstdio>
#include <iostream>
#include <queue>
#include <stack>
#include <utility>

int main() {
    freopen("in.txt", "r", stdin);
    freopen("out.txt", "w", stdout);
    int32_t n, r, last, first;
    std::cin >> n >> r >> last >> first;
    if (first == last) {
        std::cout << 0 << std::endl;
        return 0;
    }
    --first;
    --last;
    std::vector<std::vector<std::pair<int32_t, int32_t>>> routes(r);
    std::vector<std::vector<std::pair<int32_t, int32_t>>> routes_for_stop(n);
    std::vector<std::pair<int32_t, std::pair<int32_t, int32_t>>> stops(n, std::make_pair(
            INT32_MAX, std::make_pair(0, 0)));
    for (int32_t i = 0; i < r; ++i) {
        int32_t count;
        std::cin >> count;
        routes[i].resize(count);
        for (int32_t j = 0; j < count; ++j) {
            std::cin >> routes[i][j].first;
            --routes[i][j].first;
            routes[i][j].second = INT32_MAX;
            routes_for_stop[routes[i][j].first].push_back(std::make_pair(i, j));
        }
    }
    std::queue<std::pair<int32_t, int32_t>> one;
    std::queue<int32_t> two;
    two.push(first);
    stops[first].first = 0;
    while (!one.empty() || !two.empty()) {
        int32_t one_elem = INT32_MAX;
        int32_t two_elem = INT32_MAX;
        if (!one.empty()) {
            one_elem = routes[one.front().first][one.front().second].second;
        }
        if (!two.empty()) {
            two_elem = stops[two.front()].first;
        }
        if (one_elem < two_elem) {
            auto elem = one.front();
            int way = elem.first;
            int stop = elem.second;
            one.pop();
            if (stops[routes[way][stop].first].first == INT32_MAX) {
                stops[routes[way][stop].first].first = one_elem + 2;
                stops[routes[way][stop].first].second = elem;
                two.push(routes[way][stop].first);
            }
            if (stop + 1 < routes[way].size() && routes[way][stop + 1].second == INT32_MAX) {
                routes[way][stop + 1].second = one_elem + 1;
                one.push(std::make_pair(way, stop + 1));
            }
            if (stop > 0 && routes[way][stop - 1].second == INT32_MAX) {
                routes[way][stop - 1].second = one_elem + 1;
                one.push(std::make_pair(way, stop - 1));
            }
        } else {
            int elem = two.front();
            two.pop();
            for (int i = 0; i < routes_for_stop[elem].size(); ++i) {
                std::pair<int32_t, int32_t> stop = routes_for_stop[elem][i];
                if (routes[stop.first][stop.second].second == INT32_MAX) {
                    routes[stop.first][stop.second].second = two_elem + 1;
                    one.push(stop);
                }
            }
        }
    }
    if (stops[last].first == INT32_MAX) {
        std::cout << "NoWay";
    } else {
        std::cout << stops[last].first - 3 << std::endl;
        std::stack<std::pair<int, int>> answer;
        while (last != first) {
            auto elem = stops[last].second;
            answer.push(elem);
            while (true) {
                if (elem.second > 0 && routes[elem.first][elem.second].second ==
                                       routes[elem.first][elem.second - 1].second + 1) {
                    --elem.second;
                } else if (elem.second + 1 < routes[elem.first].size() &&
                           routes[elem.first][elem.second].second ==
                           routes[elem.first][elem.second + 1].second + 1) {
                    ++elem.second;
                } else {
                    break;
                }
            }
            last = routes[elem.first][elem.second].first;
        }
        std::cout << last + 1 << " ";
        std::cout << answer.top().first + 1 << std::endl;
        while (!answer.empty()) {
            std::cout << routes[answer.top().first][answer.top().second].first + 1
                      << " ";
            if (answer.size() != 1) {
                answer.pop();
                std::cout << answer.top().first + 1 << std::endl;
            } else {
                std::cout << answer.top().first + 1 << std::endl;
                answer.pop();
            }
        }
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}