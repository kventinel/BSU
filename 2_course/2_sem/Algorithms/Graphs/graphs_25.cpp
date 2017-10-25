#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>

struct cord {
    int x, y;
    cord(int a = 0, int b = 0) : x(a), y(b) {

    };
};

struct edge {
    int v, u, c, f;
    edge(int v_ = 0, int u_ = 0, int c_ = 0, int f_ = 0) :
        v(v_), u(u_), c(c_), f(f_) {
    };
};

size_t n, m;

std::vector<edge> edges;
std::vector<std::vector<size_t>> from;

std::vector<cord> host;
std::vector<cord> dog;

long double sqr(long double x) {
    return x * x;
}

long double dist(const cord& a, const cord& b) {
    return sqrt(sqr(a.x - b.x) + sqr(a.y - b.y));
}


bool overtake(size_t i, size_t j) {
    const cord& start = host[i];
    const cord& wc = dog[j];
    const cord& end = host[i + 1];

    long double there = dist(start, wc);
    long double back = dist(wc, end);
    long double right = dist(start, end);

    return there + back <= right * 2;
}


void add_edge(int v, int u) {
    edges.emplace_back(v, u, 1);
    from[v].push_back(edges.size() - 1);
    edges.emplace_back(u, v, 0);
    from[u].push_back(edges.size() - 1);
}



std::vector<int> used;
int dfs_flow(int v, int end, int cmin) {
    used[v] = true;
    if (v == end) {
        return cmin;
    }

    for (int i : from[v]) {
        const edge& e = edges[i];
        if (!used[e.u] and e.f < e.c) {
            int delta = dfs_flow(e.u, end, std::min(cmin, e.c - e.f));
            if (delta > 0) {
                edges[i].f += delta;
                edges[i ^ 1].f -= delta;
                return delta;
            }
        }
    }
    return 0;


}


int get_max_flow(int start, int end) {
    int answer = 0;

    used.assign(n + m + 2, false);
    int delta = dfs_flow(start, end, 1);
    while (delta > 0) {
        used.assign(n + m + 2, false);
        answer += delta;
        delta = dfs_flow(start, end, 1);
    }
    return answer;

}

int main() {
    std::ifstream in("input.txt");
    std::ofstream out("output.txt");

    in >> n >> m;

    host.resize(n);
    dog.resize(m);

    from.resize(n + m + 2);

    for (size_t i = 0; i < n; ++i) {
        in >> host[i].x >> host[i].y;
    }

    for (size_t i = 0; i < m; ++i) {
        in >> dog[i].x >> dog[i].y;
    }

    for (size_t i = 0; i < n - 1; ++i) {
        for (size_t j = 0; j < m; ++j) {
            if (overtake(i, j)) {
                add_edge(i, n + j);
            }
        }
    }

    for (size_t i = 0; i < n - 1; ++i) {
        add_edge(n + m, i);
    }
    for (size_t i = n; i < n + m; ++i) {
        add_edge(i, n + m + 1);
    }

    int answer = get_max_flow(n + m, n + m + 1);
    out << n + answer << ' ' << answer;

    return 0;
}
