#include <fstream>
#include <vector>
#include <utility>

class SegmentTree {
public:
  SegmentTree();

  void add(int l, int r);

  std::pair<int, int> answer(int l, int r);

private:
  struct node {
      int max_zeros;
      int max_ones;
      int left_zeros;
      int right_zeros;
      int left_ones;
      int right_ones;
      bool flag;

      node() : max_zeros(0), max_ones(0), left_zeros(0), right_zeros(0),
               left_ones(0), right_ones(0), flag(false) {}
  };

  std::vector<node> nodes;

  void rebuild_node(int n, int p);
  inline void draw_node(int n, int p);
  node answer(int l, int r, int n, int left_node, int right_node);
  void add(int l, int r, int n, int left_node, int right_node);

  int max(int a, int b, int c);
};

SegmentTree::SegmentTree() {
  int p = 1;
  while (p < 1000000) {
    p *= 2;
  }
  nodes = std::vector<node>(2 * p);
  for (int i = p; i < 2 * p; ++i) {
    nodes[i].max_zeros = 1;
    nodes[i].left_zeros = 1;
    nodes[i].right_zeros = 1;
  }
  for (int i = p - 1; i > 0; --i) {
    nodes[i].max_zeros = 2 * nodes[2 * i].max_zeros;
    nodes[i].left_zeros = nodes[i].max_zeros;
    nodes[i].right_zeros = nodes[i].max_zeros;
  }
}

void SegmentTree::add(int l, int r) {
  add(--l, --r, 1, 0, nodes.size() / 2);
}

std::pair<int, int> SegmentTree::answer(int l, int r) {
  node ans = answer(--l, --r, 1, 0, nodes.size() / 2);
  return std::make_pair(ans.max_zeros, ans.max_ones);
}

void SegmentTree::rebuild_node(int n, int p) {
  if (n * 2 < nodes.size()) {
    if (nodes[n].flag) {
      draw_node(n * 2, p / 2);
      draw_node(n * 2 + 1, p / 2);
      nodes[n].flag = false;
    } else {
      nodes[n].max_zeros = max(
          nodes[2 * n].right_zeros + nodes[2 * n + 1].left_zeros,
          nodes[2 * n].max_zeros, nodes[2 * n + 1].max_zeros);
      nodes[n].max_ones = max(
          nodes[2 * n].right_ones + nodes[2 * n + 1].left_ones,
          nodes[2 * n].max_ones, nodes[2 * n + 1].max_ones);
      if (nodes[2 * n].max_ones == 0) {
        nodes[n].left_zeros =
            nodes[2 * n].max_zeros + nodes[2 * n + 1].left_zeros;
      } else {
        nodes[n].left_zeros = nodes[2 * n].left_zeros;
      }
      if (nodes[2 * n + 1].max_ones == 0) {
        nodes[n].right_zeros =
            nodes[2 * n].right_zeros + nodes[2 * n + 1].max_zeros;
      } else {
        nodes[n].right_zeros = nodes[2 * n + 1].right_zeros;
      }
      if (nodes[2 * n].max_zeros == 0) {
        nodes[n].left_ones = nodes[2 * n].max_ones + nodes[2 * n + 1].left_ones;
      } else {
        nodes[n].left_ones = nodes[2 * n].left_ones;
      }
      if (nodes[2 * n + 1].max_zeros == 0) {
        nodes[n].right_ones =
            nodes[2 * n].right_ones + nodes[2 * n + 1].max_ones;
      } else {
        nodes[n].right_ones = nodes[2 * n + 1].right_ones;
      }
    }
  }
}

void SegmentTree::draw_node(int n, int p) {
  nodes[n].max_zeros = 0;
  nodes[n].max_ones = p;
  nodes[n].left_zeros = 0;
  nodes[n].right_zeros = 0;
  nodes[n].left_ones = p;
  nodes[n].right_ones = p;
  nodes[n].flag = true;
}

SegmentTree::node
SegmentTree::answer(int l, int r, int n, int left_node, int right_node) {
  if (nodes[n].flag && 2 * n < nodes.size()) {
    draw_node(2 * n, (right_node - left_node) / 2);
    draw_node(2 * n + 1, (right_node - left_node) / 2);
    nodes[n].flag = false;
  }
  if (l <= left_node && right_node - 1 <= r) {
    return nodes[n];
  }
  node left;
  if (l < left_node + (right_node - left_node) / 2) {
    left = answer(l, r, n * 2, left_node,
                  left_node + (right_node - left_node) / 2);
  }
  node right;
  if (r >= left_node + (right_node - left_node) / 2) {
    right = answer(l, r, n * 2 + 1, left_node + (right_node - left_node) / 2,
                   right_node);
  }
  node ans;
  ans.max_zeros = max(left.max_zeros, right.max_zeros,
                      left.right_zeros + right.left_zeros);
  ans.max_ones = max(left.max_ones, right.max_ones,
                     left.right_ones + right.left_ones);
  ans.left_zeros =
      left.max_ones == 0 ? left.max_zeros + right.left_zeros : left.left_zeros;
  ans.right_zeros = right.max_ones == 0 ? right.max_zeros + left.right_zeros
                                        : right.right_zeros;
  ans.left_ones =
      left.max_zeros == 0 ? left.max_ones + right.left_ones : left.left_ones;
  ans.right_ones = right.max_zeros == 0 ? right.max_ones + left.right_ones
                                        : right.right_ones;
  return ans;
}

void SegmentTree::add(int l, int r, int n, int left_node, int right_node) {
  if (nodes[n].flag && 2 * n < nodes.size()) {
    draw_node(2 * n, (right_node - left_node) / 2);
    draw_node(2 * n + 1, (right_node - left_node) / 2);
    nodes[n].flag = false;
  } else {
    if (l <= left_node && right_node - 1 <= r) {
      draw_node(n, right_node - left_node);
    } else {
      if (l < left_node + (right_node - left_node) / 2) {
        add(l, r, n * 2, left_node, left_node + (right_node - left_node) / 2);
      }
      if (r >= left_node + (right_node - left_node) / 2) {
        add(l, r, n * 2 + 1, left_node + (right_node - left_node) / 2,
            right_node);
      }
      rebuild_node(n, right_node - left_node);
    }
  }
}

int SegmentTree::max(int a, int b, int c) {
  a = a < b ? b : a;
  return a < c ? c : a;
}

int main() {
  std::ifstream fin("fence.in");
  std::ofstream fout("fence.out");
  SegmentTree segment_tree;
  while (true) {
    char a;
    fin >> a;
    if (a == 'L') {
      int l, r;
      fin >> l >> r;
      segment_tree.add(l, r);
    } else if (a == 'W') {
      int l, r;
      fin >> l >> r;
      std::pair<int, int> answer = segment_tree.answer(l, r);
      fout << answer.first << " " << answer.second << std::endl;
    } else {
      break;
    }
  }
  fclose(stdin);
  fclose(stdout);
  return 0;
}