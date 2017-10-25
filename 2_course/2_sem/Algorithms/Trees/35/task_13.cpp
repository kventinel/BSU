#include <iostream>
#include <memory>

template<typename T, typename... Args>
std::unique_ptr<T> make_unique(Args&&... args) {
    return std::unique_ptr<T>(new T(std::forward<Args>(args)...));
}


class BST {
  public:
    class Node;
    typedef std::unique_ptr<Node> upNode;

    class Node {
      public:
        Node(const int& d, Node* p = nullptr) :
            data{d}, parent(p) {
        }
        int data;
        Node* parent;
        upNode left = nullptr;
        upNode right = nullptr;

        int height = 0;
        int min_key = 0;
    };

    BST(const int& data) : _root{std::make_unique<Node>(data)} {
    };

    BST() : _root(nullptr) {
    };

    //  return true, if data is not unique key
    //         false, else
    bool insert(const int& data) {
        if (_root == nullptr) {
            _root = std::make_unique<Node>(data, nullptr);
            return true;
        }

        Node* node = _find(_root.get(), data);
        if (node->data == data) {
            return false;
        }

        if (data < node->data) {
            node->left = std::make_unique<Node>(data, node);
        } else {
            node->right = std::make_unique<Node>(data, node);
        }
        return true;
    }

    bool remove(const int& data) {
        Node* node = _find(_root.get(),  data);
        if (node->data != data) {
            return false;
        } else {
            _remove(node);
            return true;
        }
    }

    bool find(const int& data) {
        Node* node = _find(_root.get(), data);
        return node->data == data;
    }

    void print() {
        _print(_root.get());
        std::cout << std::endl;
    }

    void read() {
        // freopen("tst.in", "r", stdin);
        // freopen("tst.out", "w", stdout);

        freopen("input.txt", "r", stdin);
        freopen("output.txt", "w", stdout);


        int x;
        while (std::cin >> x) {
            insert(x);
        }
    }

    void task13() {
        read();
        calc(_root.get());
        // std::cout << semiway_length << std::endl <<
        //           semiway_min_sum << std::endl <<
        //           semiway_root_data << std::endl;
        // print();


        Node* average = find_average(semiway_root);
        _remove(average);
        print();

    }
  private:
    std::unique_ptr<Node> _root;

    int semiway_length = 0;
    int semiway_min_sum = 0;
    Node* semiway_root{};

    void calc(Node* node) {
        Node* left = node->left.get();
        Node* right = node->right.get();

        if (left && right) {
            calc(left);
            calc(right);

            if (left->height > right->height ||
                    (left->height == right->height &&
                     left->min_key < right->min_key)) {
                node->height = left->height + 1;
                node->min_key = left->min_key;
            } else {
                node->height = right->height + 1;
                node->min_key = right->min_key;
            }

            int temp_semiway_length = left->height + right->height + 2;
            int temp_semiway_min_sum = left->min_key + right->min_key;
            if (temp_semiway_length > semiway_length ||
                    (temp_semiway_length == semiway_length &&
                     temp_semiway_min_sum < semiway_min_sum)) {
                semiway_length = temp_semiway_length;
                semiway_min_sum = temp_semiway_min_sum;
                semiway_root = node;
            }


        } else if (left) {
            calc(left);
            node->height = left->height + 1;
            node->min_key = left->min_key;
            if (node->parent == nullptr) {
                if (node->height > semiway_length ||
                        (node->height == semiway_length &&
                         node->min_key + node->data < semiway_min_sum)) {
                    semiway_length = node->height;
                    semiway_min_sum = node->min_key + node->data;
                    semiway_root = node;
                }
            }

        } else if (right) {
            calc(right);
            node->height = right->height + 1;
            node->min_key = right->min_key;
            if (node->parent == nullptr) {
                if (node->height > semiway_length ||
                        (node->height == semiway_length &&
                         node->min_key + node->data < semiway_min_sum)) {
                    semiway_length = node->height;
                    semiway_min_sum = node->min_key + node->data;
                    semiway_root = node;
                }
            }

        } else {
            node->height = 0;
            node->min_key = node->data;
        }
    }



    void _print(Node* node) {
        if (node == nullptr) {
            return;
        }
        _print(node->left.get());
        std::cout << node->data << " " <<
                  node->height << " " <<
                  node->min_key << std::endl;
        _print(node->right.get());
    }

    // write comment
    Node* _find(Node* node, const int& data) {

        int node_data = node->data;
        if (data == node_data) {
            return node;
        }

        if (data < node_data) {
            if (node->left == nullptr) {
                return node;
            } else {
                return _find(node->left.get(), data);
            }
        } else {
            if (node->right == nullptr) {
                return node;
            } else {
                return _find(node->right.get(), data);
            }
        }
    }
    // node should not be _root
    bool _is_left_son(Node* node) {
        return (node->parent->left.get() == node);
    }

    void _remove(Node* node) {
        Node* parent = node->parent;
        if (node->left == nullptr && node->right == nullptr) {
            if (node == _root.get()) {
                _root = nullptr;
            } else if (_is_left_son(node)) {
                parent->left = nullptr;
            } else {
                parent->right = nullptr;
            }
        } else if (node->right == nullptr) {
            node->left->parent = parent;
            if (node == _root.get()) {
                _root = std::move(node->left);
            } else if (_is_left_son(node)) {
                parent->left = (std::move(node->left));
            } else {
                parent->right = (std::move(node->left));
            }
        } else if (node->left == nullptr) {
            node->right->parent = (parent);
            if (node == _root.get()) {
                _root = std::move(node->right);
            } else if (_is_left_son(node)) {
                parent->left = (std::move(node->right));
            } else {
                parent->right = (std::move(node->right));
            }
        } else {
            Node* right = node->right.get();
            Node* min_node = _find_min(right);
            if (min_node == right) {
                node->data = (right->data);
                node->right = (std::move(right->right));
                if (node->right) {
                    node->right->parent = node;
                }
            } else {
                node->data = (min_node->data);
                _remove(min_node);
            }
        }
    }
    Node* _find_min(Node * root) {
        while (root->left != nullptr) {
            root = root->left.get();
        }
        return root;
    }
};

int main() {


    BST bst;
    bst.task13();
    return 0;
}
