#include <iostream>
#include <cstdio>
#include <memory>
#include <vector>
#include <algorithm>
#include <stdexcept>

class Tree {
private:
  class Node {
  public:
    Node(int key, std::shared_ptr<Node> parent) : key_(key), parent_(parent) {}

    void setLeftSon(std::shared_ptr<Node> son);
    void setRightSon(std::shared_ptr<Node> son);
    void setParent(std::shared_ptr<Node> parent);

    int getKey();
    std::shared_ptr<Node> getLeftSon();
    std::shared_ptr<Node> getRightSon();
    std::shared_ptr<Node> getParent();

    bool hasLeftSon();
    bool hasRightSon();
    bool hasParent();

    std::vector<int> left;
    std::vector<int> right;
    std::vector<int> top;
    int deep;

    ~Node() {}

  private:
    int key_;
    std::shared_ptr<Node> parent_;
    std::shared_ptr<Node> left_son_;
    std::shared_ptr<Node> right_son_;
  };

public:
  Tree() {}

  void insert(int key);
  void rootLeftRight();
  void clear(int k);

  ~Tree();

private:
  std::shared_ptr<Node> root;

  void setSon(std::shared_ptr<Node> parent, std::shared_ptr<Node> old_son,
              std::shared_ptr<Node> new_son);
  void setLeftSon(std::shared_ptr<Node> parent, std::shared_ptr<Node> son);
  void setRightSon(std::shared_ptr<Node> parent, std::shared_ptr<Node> son);

  void erase(std::shared_ptr<Node> node);

  void rootLeftRight(std::shared_ptr<Node> node);

  void wayLeftRight(std::shared_ptr<Node> node);
  void wayParent(std::shared_ptr<Node> node);
  std::shared_ptr<Node> ways(std::shared_ptr<Node> node, int k);

  bool summate(std::vector<int>::iterator first_begin,
               std::vector<int>::iterator first_end,
               std::vector<int>::iterator second_begin,
               std::vector<int>::iterator second_end, int key);
};

void Tree::insert(int key) {
  if (root == nullptr) {
    root = std::shared_ptr<Node>(new Node(key, nullptr));
  } else {
    std::shared_ptr<Node> node = root;
    while (true) {
      if (node->getKey() > key) {
        if (node->getLeftSon() == nullptr) {
          node->setLeftSon(std::shared_ptr<Node>(new Node(key, node)));
          node->getLeftSon()->setParent(node);
        } else {
          node = node->getLeftSon();
        }
      } else if (node->getKey() < key) {
        if (node->getRightSon() == nullptr) {
          node->setRightSon(std::shared_ptr<Node>(new Node(key, node)));
          node->getRightSon()->setParent(node);
        } else {
          node = node->getRightSon();
        }
      } else {
        return;
      }
    }
  }
}

void Tree::erase(std::shared_ptr<Tree::Node> node) {
  if (node->hasLeftSon() && node->hasRightSon()) {
    std::shared_ptr<Node> to_swap = node->getRightSon();
    while (to_swap->hasLeftSon()) {
      to_swap = to_swap->getLeftSon();
    }
    setSon(to_swap->getParent(), to_swap, to_swap->getRightSon());
    setSon(node->getParent(), node, to_swap);
    setLeftSon(to_swap, node->getLeftSon());
    setRightSon(to_swap, node->getRightSon());
    if (root == node) {
      root = to_swap;
    }
  } else {
    std::shared_ptr<Node> son = node->hasLeftSon() ? node->getLeftSon()
                                                   : node->getRightSon();
    setSon(node->getParent(), node, son);
    if (node == root) {
      root = son;
    }
  }
}

void Tree::rootLeftRight(std::shared_ptr<Node> node) {
  while (true) {
    if (node != nullptr) {
      std::cout << node->getKey() << std::endl;
      rootLeftRight(node->getLeftSon());
//      for (int a : node->left) {
//        std::cout << a << " ";
//      }
//      std::cout << std::endl;
//      for (int a : node->right) {
//        std::cout << a << " ";
//      }
//      std::cout << std::endl;
//      for (int a : node->top) {
//        std::cout << a << " ";
//      }
//      std::cout << std::endl;
//      std::cout << node->deep << std::endl;
//      std::cout << std::endl;
      node = node->getRightSon();
    } else {
      break;
    }
  }
}

void Tree::rootLeftRight() {
  if (root == nullptr) {
    std::cout << "Empty\n";
  } else {
    rootLeftRight(root);
  }
}

void Tree::setSon(std::shared_ptr<Tree::Node> parent,
                  std::shared_ptr<Tree::Node> old_son,
                  std::shared_ptr<Tree::Node> new_son) {
  if (parent != nullptr) {
    if (parent->getLeftSon() == old_son) {
      parent->setLeftSon(new_son);
    } else {
      parent->setRightSon(new_son);
    }
  }
  if (new_son != nullptr) {
    new_son->setParent(parent);
  }
}

void Tree::setLeftSon(std::shared_ptr<Tree::Node> parent,
                      std::shared_ptr<Tree::Node> son) {
  if (parent != nullptr) {
    parent->setLeftSon(son);
  }
  if (son != nullptr) {
    son->setParent(parent);
  }
}

void Tree::setRightSon(std::shared_ptr<Tree::Node> parent,
                       std::shared_ptr<Tree::Node> son) {
  if (parent != nullptr) {
    parent->setRightSon(son);
  }
  if (son != nullptr) {
    son->setParent(parent);
  }
}

Tree::~Tree() {
  std::shared_ptr<Node> node = root;
  while (root != nullptr) {
    while (node->hasLeftSon()) {
      node = node->getLeftSon();
    }
    erase(node);
    if (node->hasParent()) {
      node = node->getParent();
    } else {
      node = root;
    }
  }
}

void Tree::clear(int k) {
  wayLeftRight(root);
  wayParent(root);
  std::shared_ptr<Node> result = ways(root, k);
  if (result != nullptr) {
    erase(result);
  }
}

void Tree::wayLeftRight(std::shared_ptr<Tree::Node> node) {
  if (node != nullptr) {
    if (node->hasLeftSon()) {
      std::shared_ptr<Node> son = node->getLeftSon();
      wayLeftRight(son);
      node->left.resize(son->left.size() + son->right.size());
      std::vector<int>::iterator end =
          std::set_union(son->left.begin(), son->left.end(), son->right.begin(),
                         son->right.end(), node->left.begin());
      node->left.resize(end - node->left.begin());
      for (int &a : node->left) {
        ++a;
      }
    }
    if (node->hasRightSon()) {
      std::shared_ptr<Node> son = node->getRightSon();
      wayLeftRight(son);
      node->right.resize(son->left.size() + son->right.size());
      std::vector<int>::iterator end =
          std::set_union(son->left.begin(), son->left.end(), son->right.begin(),
                         son->right.end(), node->right.begin());
      node->right.resize(end - node->right.begin());
      for (int &a : node->right) {
        ++a;
      }
    }
    if (!node->hasLeftSon() && !node->hasRightSon()) {
      node->left.push_back(0);
      node->right.push_back(0);
    }
  }
}

void Tree::wayParent(std::shared_ptr<Tree::Node> node) {
  if (node != nullptr) {
    if (node->hasParent()) {
      std::shared_ptr<Node> parent = node->getParent();
      node->deep = parent->deep + 1;
      std::vector<int>::iterator end;
      if (parent->getLeftSon() == node) {
        node->top.resize(parent->top.size() + parent->right.size());
        end = std::set_union(parent->top.begin(), parent->top.end(),
                             parent->right.begin(), parent->right.end(),
                             node->top.begin());
      } else {
        node->top.resize(parent->top.size() + parent->left.size());
        end = std::set_union(parent->top.begin(), parent->top.end(),
                             parent->left.begin(), parent->left.end(),
                             node->top.begin());
      }
      node->top.resize(end - node->top.begin());
      for (int &a : node->top) {
        ++a;
      }
    } else {
      node->deep = 0;
    }
    wayParent(node->getLeftSon());
    wayParent(node->getRightSon());
  }
}

std::shared_ptr<Tree::Node> Tree::ways(std::shared_ptr<Node> node, int k) {
  if (node != nullptr) {
    std::shared_ptr<Node> result = ways(node->getRightSon(), k);
    if (result != nullptr) {
      return result;
    }
    bool answer = true;
    if (answer && node->left.size() != 0 && k <= *(node->left.end() - 1)) {
      answer = false;
    }
    if (answer && node->right.size() != 0 && k <= *(node->right.end() - 1)) {
      answer = false;
    }
    if (answer && node->top.size() != 0 && k <= *(node->top.end() - 1)) {
      answer = false;
    }
    if (answer && k <= node->deep) {
      answer = false;
    }
    if (answer &&
        summate(node->left.begin(), node->left.end(), node->right.begin(),
                node->right.end(), k)) {
      answer = false;
    }
    if (answer &&
        summate(node->left.begin(), node->left.end(), node->top.begin(),
                node->top.end(), k)) {
      answer = false;
    }
    if (answer &&
        summate(node->right.begin(), node->right.end(), node->top.begin(),
                node->top.end(), k)) {
      answer = false;
    }
    if (answer) {
      return node;
    }
    result = ways(node->getLeftSon(), k);
    if (result != nullptr) {
      return result;
    }
  }
  return nullptr;
}

bool Tree::summate(std::vector<int>::iterator first_begin,
                   std::vector<int>::iterator first_end,
                   std::vector<int>::iterator second_begin,
                   std::vector<int>::iterator second_end, int key) {
  if (second_begin == second_end) {
    return false;
  }
  --second_end;
  while (first_begin != first_end && second_begin != second_end) {
    if (*first_begin + *second_end == key) {
      return true;
    }
    if (*first_begin + *second_end < key) {
      ++first_begin;
    } else {
      --second_end;
    }
  }
  while (first_begin != first_end) {
    if (*first_begin + *second_begin == key) {
      return true;
    }
    ++first_begin;
  }
  return false;
}

void Tree::Node::setLeftSon(std::shared_ptr<Tree::Node> son) {
  left_son_ = son;
}

void Tree::Node::setRightSon(std::shared_ptr<Tree::Node> son) {
  right_son_ = son;
}

void Tree::Node::setParent(std::shared_ptr<Tree::Node> parent) {
  parent_ = parent;
}

int Tree::Node::getKey() {
  return key_;
}

std::shared_ptr<Tree::Node> Tree::Node::getLeftSon() {
  return left_son_;
}

std::shared_ptr<Tree::Node> Tree::Node::getRightSon() {
  return right_son_;
}

std::shared_ptr<Tree::Node> Tree::Node::getParent() {
  return parent_;
}

bool Tree::Node::hasLeftSon() {
  return left_son_ != nullptr;
}

bool Tree::Node::hasRightSon() {
  return right_son_ != nullptr;
}

bool Tree::Node::hasParent() {
  return parent_ != nullptr;
}

int main() {
  freopen("tst.in", "r", stdin);
  freopen("tst.out", "w", stdout);
  Tree tree;
  int to_erase;
  std::cin >> to_erase;
  int a;
  while (std::cin >> a) {
    tree.insert(a);
  }
  tree.clear(to_erase);
  tree.rootLeftRight();
  return 0;
}
