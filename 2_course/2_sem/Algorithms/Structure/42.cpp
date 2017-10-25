#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>
#include <memory>

struct p {
    int x, y, z;
};

inline bool operator< (const p& a, const p& b) {
    return a.x < b.x || (a.x == b.x && a.y < b.y) ||
           (a.x == b.x && a.y == b.y && a.z < b.z);
}

class SegmentTree {
  public:
    SegmentTree(size_t n) : n_(n) {
        root_ = std::make_unique<node>(0, n - 1);
        build_(root_.get());
    }

    void set(size_t index, int value) {
        set_(root_.get(), index, value);
    }
    int min(size_t right) {
        return min_(root_.get(), 0, right);
    }
  private:

    struct node {

        node(int a, int b) : l(a), r(b), min(2e9) {

        };
        int l, r;
        std::unique_ptr<node> left, right;
        int min;
    };
    size_t n_;
    std::unique_ptr<node> root_;


    void build_(node* v) {
        int l = v->l, r = v->r;
        if (l == r) {
            return;
        }
        v->left = std::make_unique<node>(l, (l + r) / 2);
        v->right = std::make_unique<node>((l + r) / 2 + 1, r);
        build_(v->left.get());
        build_(v->right.get());
    }

    void set_(node* v, int index, int value) {
        if (v->l == v->r && v->l == index) {
            v->min = std::min(value, v->min);
        } else {
            if (v->left->r >= index) {
                set_(v->left.get(), index, value);
            } else {
                set_(v->right.get(), index, value);
            }
            v->min = std::min(v->left->min, v->right->min);
        }
    }

    int min_(node* v, int left, int right) {
        if (left == v->l && right == v->r) {
            return v->min;
        }

        if (right <= v->left->r) {
            return min_(v->left.get(), left, right);
        }
        if (left >= v->right->l) {
            return min_(v->right.get(), left, right);
        } 

        return std::min(min_(v->left.get(), left, v->left->r), 
                        min_(v->right.get(), v->right->l, right));            
    }
};

int main() {

    std::ifstream in("input.txt");
    std::ofstream out("output.txt");


    size_t n;
    in >> n;

    std::vector<p> a(n);
    for (size_t i = 0; i < n; ++i) {
        in >> a[i].x >> a[i].y >> a[i].z;
        a[i].x--;
        a[i].y--;
        a[i].z--;
    }
    std::sort(a.begin(), a.end());

    SegmentTree st(n);
    int answer = 0;
    for (const auto& i : a) {
        int mn = st.min(i.y);
        // std::cerr << i.x << ' ' << i.y << ' ' << i.z << ' ' << mn << std::endl;
        if (mn > i.z || mn == 2e9) {
            answer ++;
        }
        st.set(i.y, i.z);
    }

    out << answer;
    return 0;
}
