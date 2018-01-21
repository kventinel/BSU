#include <algorithm>
#include <cstdio>
#include <iostream>
#include <numeric>
#include <set>
#include <stack>
#include <vector>
#include <utility>

struct road {
    int32_t a, b, c;

    road() : a(0), b(0), c(0) {}

    road(int32_t a_, int32_t b_, int32_t c_) : a(a_), b(b_), c(c_) {}
};

void input(std::vector<int32_t> *towns, std::vector<road> *roads,
           std::vector<std::vector<int32_t>> *edges, std::vector<int32_t> *my_roads) {
    size_t n, m;
    std::cin >> n >> m;
    towns->resize(n);
    roads->resize(m);
    edges->resize(n);
    for (size_t i = 0; i < n; ++i) {
        std::cin >> towns->at(i);
    }
    for (int32_t i = 0; i < m; ++i) {
        int32_t a, b, p, c;
        std::cin >> a >> b >> p >> c;
        roads->at((size_t) i) = road(--a, --b, c);
        edges->at((size_t) a).push_back(i);
        edges->at((size_t) b).push_back(i);
        if (p == 1) {
            my_roads->push_back(i);
        }
    }
}

int32_t my_roads_sum(const std::vector<road> &roads, const std::vector<int32_t> &my_roads) {
    int32_t sum = 0;
    for (int32_t it : my_roads) {
        sum += roads[it].c;
    }
    return sum;
}

std::vector<int32_t> best_ways(const std::vector<int32_t> &towns, const std::vector<road> &roads,
                               const std::vector<std::vector<int32_t>> &edges, int32_t sum) {
    std::set<std::pair<int32_t, int32_t>> ways;
    std::vector<int32_t> ancestors(towns.size());
    std::vector<int32_t> length(towns.size(), INT32_MAX);
    length[0] = 0;
    ways.insert(std::make_pair(0, 0));
    while (!ways.empty()) {
        int32_t a = ways.begin()->second;
        ways.erase(ways.begin());
        for (int32_t j = 0; j < edges[a].size(); ++j) {
            int32_t b = roads[edges[a][j]].a;
            if (b == a) {
                b = roads[edges[a][j]].b;
            }
            int32_t c = roads[edges[a][j]].c;
            if (length[a] + c + towns[b] < length[b]) {
                if (length[b] != INT32_MAX) {
                    ways.erase(std::make_pair(length[b], b));
                }
                length[b] = length[a] + c + towns[b];
                ancestors[b] = edges[a][j];
                ways.insert(std::make_pair(length[b], b));
            }
        }
    }
    if (length[length.size() - 1] > sum) {
        ancestors.clear();
    }
    return ancestors;
}

std::vector<int32_t>
get_way_edges(const std::vector<road> &roads, const std::vector<int32_t> &ancestors) {
    int32_t last = (int32_t) ancestors.size() - 1;
    std::vector<int32_t> edges;
    while (last != 0) {
        int32_t a = roads[ancestors[last]].a;
        if (a == last) {
            a = roads[ancestors[last]].b;
        }
        edges.push_back(ancestors[last]);
        last = a;
    }
    return edges;
}

std::stack<int32_t> get_way(const std::vector<road> &roads, const std::vector<int32_t> &ancestors) {
    int32_t last = (int32_t) ancestors.size() - 1;
    std::stack<int32_t> way;
    way.push(last);
    while (last != 0) {
        int32_t a = roads[ancestors[last]].a;
        if (a == last) {
            a = roads[ancestors[last]].b;
        }
        way.push(a);
        last = a;
    }
    return way;
}

std::vector<int32_t> get_sale(std::vector<int32_t> way, std::vector<int32_t> my_roads) {
    std::sort(way.begin(), way.end());
    auto end = std::set_intersection(way.begin(), way.end(), my_roads.begin(), my_roads.end(),
                                     way.begin());
    way.erase(end, way.end());
    end = std::set_difference(my_roads.begin(), my_roads.end(), way.begin(), way.end(),
                              my_roads.begin());
    my_roads.erase(end, my_roads.end());
    return my_roads;
}

std::vector<int32_t> get_buy(std::vector<int32_t> way, std::vector<int32_t> my_roads) {
    std::sort(way.begin(), way.end());
    auto end = std::set_intersection(way.begin(), way.end(), my_roads.begin(), my_roads.end(),
                                     my_roads.begin());
    my_roads.erase(end, my_roads.end());
    end = std::set_difference(way.begin(), way.end(), my_roads.begin(), my_roads.end(),
                              way.begin());
    way.erase(end, way.end());
    return way;
}

void output(std::stack<int32_t> way, const std::vector<int32_t> &sale,
            const std::vector<int32_t> &buy) {
    std::cout << sale.size();
    for (auto it : sale) {
        std::cout << " " << it + 1;
    }
    std::cout << std::endl << buy.size();
    for (auto it : buy) {
        std::cout << " " << it + 1;
    }
    std::cout << std::endl;
    while (!way.empty()) {
        std::cout << way.top() + 1;
        way.pop();
        if (way.empty()) {
            std::cout << std::endl;
        } else {
            std::cout << " ";
        }
    }
}

int main() {
    freopen("campaign.in", "r", stdin);
    freopen("campaign.out", "w", stdout);
    std::vector<int32_t> towns;
    std::vector<road> roads;
    std::vector<std::vector<int32_t>> edges;
    std::vector<int32_t> my_roads;
    input(&towns, &roads, &edges, &my_roads);
    int32_t sum = my_roads_sum(roads, my_roads);
    std::vector<int32_t> ancestors = best_ways(towns, roads, edges, sum);
    if (ancestors.size() == 0) {
        std::cout << "-1" << std::endl;
        return 0;
    }
    std::vector<int32_t> way_edges = get_way_edges(roads, ancestors);
    std::stack<int32_t> way = get_way(roads, ancestors);
    std::vector<int32_t> sale = get_sale(way_edges, my_roads);
    std::vector<int32_t> buy = get_buy(way_edges, my_roads);
    output(way, sale, buy);
    fclose(stdin);
    fclose(stdout);
    return 0;
}