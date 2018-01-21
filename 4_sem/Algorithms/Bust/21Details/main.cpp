#include <iostream>
#include <vector>
#include <queue>
#include <string>

struct quart {
    int32_t machine, time, last_detail, remainder;

    quart() : machine(0), time(0), last_detail(0), remainder(0) {}

    quart(int32_t machine_, int32_t time_, int32_t last_detail_,
          int32_t remainder_) : machine(machine_), time(time_),
                                last_detail(last_detail_), remainder(remainder_) {}
};

bool smaller(quart first, quart second, int32_t detail) {
    if (first.machine != second.machine) {
        return first.machine < second.machine;
    }
    return first.time + detail < second.time;
}

void input(int32_t *n, int32_t *m, std::vector<int32_t> *details) {
    std::cin >> *n >> *m;
    details->resize(*n);
    for (int32_t &detail : *details) {
        std::cin >> detail;
    }
}

std::vector<std::string>
solution(int32_t n, int32_t m, const std::vector<int32_t> &details) {
    std::vector<int32_t> pows(n + 1);
    pows[0] = 1;
    for (int8_t i = 1; i <= n; ++i) {
        pows[i] = pows[i - 1] * 2;
    }
    int32_t sum = 0;
    for (int32_t detail : details) {
        sum += detail;
    }
    std::vector<quart> answer(pows[n], quart(INT32_MAX, INT32_MAX, 0, sum));
    answer[0] = quart(0, 0, -1, sum);
    std::queue<int32_t> solution_tree;
    solution_tree.push(0);
    while (!solution_tree.empty()) {
        int32_t num = solution_tree.front();
        solution_tree.pop();
        int32_t min_detail = INT32_MAX;
        for (int i = 0; i < n; ++i) {
            if ((num & pows[i]) == 0) {
                min_detail = std::min(min_detail, details[i]);
            }
        }
        for (int i = 0; i < n; ++i) {
            if ((num & pows[i]) == 0) {
                int32_t new_num = num + pows[i];
                if (answer[num].time + details[i] <=
                    answer[num].remainder / (m - answer[num].machine)) {
                    if (smaller(answer[num], answer[new_num], details[i])) {
                        if (answer[new_num].machine == INT32_MAX) {
                            solution_tree.push(new_num);
                        }
                        answer[new_num] = quart(answer[num].machine, answer[num].time + details[i],
                                                i, answer[num].remainder);
                    }
                } else if (answer[num].machine + 1 < m) {
                    quart temp(answer[num].machine + 1, 0, 0,
                               answer[num].remainder - answer[num].time);
                    if (details[i] <= temp.remainder / (m - temp.machine) ||
                        details[i] == min_detail) {
                        if (smaller(temp, answer[new_num], details[i])) {
                            if (answer[new_num].machine == INT32_MAX) {
                                solution_tree.push(new_num);
                            }
                            answer[new_num] = quart(temp.machine, details[i], i, temp.remainder);
                        }
                    }
                }
            }
        }
    }
    std::vector<std::string> output;
    int32_t i = pows[n] - 1;
    output.push_back(std::to_string(answer[i].time));
    while (i != 0) {
        if (answer[i].machine != m) {
            while (answer[i].machine + 1 != m) {
                output.push_back(std::to_string(-1));
                --m;
            }
            output.push_back(std::to_string(details[answer[i].last_detail]));
            --m;
        } else {
            output.back() += " " + std::to_string(details[answer[i].last_detail]);
        }
        i -= pows[answer[i].last_detail];
    }
    return output;
}

void output(const std::vector<std::string> &answer) {
    for (const std::string &line : answer) {
        std::cout << line << std::endl;
    }
}

int main() {
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    int32_t n, m;
    std::vector<int32_t> details;
    input(&n, &m, &details);
    output(solution(n, m, details));
    return 0;
}