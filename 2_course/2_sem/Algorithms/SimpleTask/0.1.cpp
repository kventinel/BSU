#include <iostream>
#include <cstdio>
#include <memory>

class Tree {
private:
  class Node {
  public:
    Node(int key, std::shared_ptr<Node> parent) : key_(key), parent_(parent) {}

    void setKey(int key);
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
  void erase(std::shared_ptr<Node> node);
  void rootLeftRight(std::shared_ptr<Node> node);
  void rootLeftRight();

  ~Tree();

private:
  std::shared_ptr<Node> root;

  void setSon(std::shared_ptr<Node> parent, std::shared_ptr<Node> old_son,
              std::shared_ptr<Node> new_son);
  void setLeftSon(std::shared_ptr<Node> parent, std::shared_ptr<Node> son);
  void setRightSon(std::shared_ptr<Node> parent, std::shared_ptr<Node> son);
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
      } else if (node->getKey() < key){
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
      node = node->getRightSon();
    } else {
      break;
    }
  }
}

void Tree::rootLeftRight() {
  rootLeftRight(root);
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

void Tree::Node::setKey(int key) {
  key_ = key;
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
  freopen("input.txt", "r", stdin);
  freopen("output.txt", "w", stdout);
  Tree tree;
  while (!std::cin.eof()) {
    int a;
    std::cin >> a;
    tree.insert(a);
  }
  tree.rootLeftRight();
  return 0;
}
