#include <cstdio>
#include <iostream>
#include <stack>
#include <vector>

std::vector<std::vector<int32_t>> input() {
    int32_t n;
    std::cin >> n;
    std::vector<std::vector<int32_t>> edges(2 * n);
    for (int32_t i = 0; i < n; ++i) {
        int32_t m;
        std::cin >> m;
        edges[i].resize(m);
        for (int32_t j = 0; j < m; ++j) {
            std::cin >> edges[i][j];
            edges[i][j] += n - 1;
        }
    }
    for (int32_t i = 0; i < n; ++i) {
        int32_t a;
        std::cin >> a;
        edges[--a + n].push_back(i);
    }
    return edges;
}

std::vector<std::vector<int32_t>> get_invert_edges(const std::vector<std::vector<int32_t>> &edges) {
    std::vector<std::vector<int32_t>> invert_edges(edges.size());
    for (int32_t i = 0; i < edges.size(); ++i) {
        for (int32_t edge : edges[i]) {
            invert_edges[edge].push_back(i);
        }
    }
    return invert_edges;
}

std::vector<int32_t> get_components(const std::vector<std::vector<int32_t>> &edges) {
    std::vector<int32_t> label(edges.size(), -1);
    std::stack<int32_t> vertexes;
    for (int32_t i = 0; i < edges.size(); ++i) {
        if (label[i] == -1) {
            label[i] = 0;
            std::stack<int32_t> depth_search;
            depth_search.push(i);
            depth_search.push(0);
            while (!depth_search.empty()) {
                int32_t edge = depth_search.top();
                depth_search.pop();
                int32_t vertex = depth_search.top();
                depth_search.pop();
                bool next = false;
                while (edge < edges[vertex].size() && !next) {
                    if (label[edges[vertex][edge]] == -1) {
                        next = true;
                        depth_search.push(vertex);
                        depth_search.push(edge + 1);
                        depth_search.push(edges[vertex][edge]);
                        depth_search.push(0);
                        label[edges[vertex][edge]] = 0;
                    }
                    ++edge;
                }
                if (!next) {
                    vertexes.push(vertex);
                }
            }
        }
    }
    int32_t component = 1;
    std::vector<std::vector<int32_t>> invert_edges = get_invert_edges(edges);
    while (!vertexes.empty()) {
        int32_t i = vertexes.top();
        vertexes.pop();
        if (label[i] == 0) {
            label[i] = component;
            std::stack<int32_t> depth_search;
            depth_search.push(i);
            while (!depth_search.empty()) {
                int32_t vertex = depth_search.top();
                depth_search.pop();
                for (int32_t j = 0; j < invert_edges[vertex].size(); ++j) {
                    if (label[invert_edges[vertex][j]] == 0) {
                        label[invert_edges[vertex][j]] = component;
                        depth_search.push(invert_edges[vertex][j]);
                    }
                }
            }
            ++component;
        }
    }
    return label;
}

std::vector<std::vector<int32_t>> solution(const std::vector<std::vector<int32_t>> &edges) {
    std::vector<std::vector<int32_t>> res(edges.size() / 2);
    std::vector<int32_t> label = get_components(edges);
    for (int32_t i = 0; i < res.size(); ++i) {
        for (int32_t j = 0; j < edges[i].size(); ++j) {
            if (label[i] == label[edges[i][j]]) {
                res[i].push_back(edges[i][j] - res.size() + 1);
            }
        }
    }
    return res;
}

void output(const std::vector<std::vector<int32_t>>& res) {
    for (const std::vector<int32_t>& line : res) {
        std::cout << line.size();
        for (int32_t fiancee : line) {
            std::cout << " " << fiancee;
        }
        std::cout << std::endl;
    }
}

int main() {
    freopen("king.in", "r", stdin);
    freopen("king.out", "w", stdout);
    std::vector<std::vector<int32_t>> edges = input();
    output(solution(edges));
    fclose(stdin);
    fclose(stdout);
    return 0;
}