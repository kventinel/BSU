/**
 * Created by Rak Alexey on 11.11.16.
 */
class Node<T extends Comparable<T>> {
    private Node<T> left;
    private Node<T> right;
    private Node<T> parent;
    private T key;

    Node(T key_, Node<T> parent_) {
        key = key_;
        parent = parent_;
        left = null;
        right = null;
    }

    T getKey() {
        return key;
    }

    Node<T> getLeftSon() {
        return left;
    }

    Node<T> getRightSon() {
        return right;
    }

    void addLeftSon(T key) {
        left = new Node<>(key, this);
    }

    void addRightSon(T key) {
        right = new Node<>(key, this);
    }

    void delete() {
        Node<T> son;
        if(left == null) {
            son = right;
        } else {
            son = left;
        }
        if(son != null) {
            son.parent = parent;
        }
        if(parent != null) {
            if (parent.left == this) {
                parent.left = son;
            } else {
                parent.right = son;
            }
        }
    }

    void trade(Node<T> second) {
        T t = key;
        key = second.key;
        second.key = t;
    }
}
