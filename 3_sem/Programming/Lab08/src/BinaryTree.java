/**
 * Created by Rak Alexey on 11.11.16.
 */

public class BinaryTree<T extends Comparable<T>> {
    private Node<T> root;

    public BinaryTree() {
        root = null;
    }

    public void insert(T key) {
        if (root == null) {
            root = new Node<>(key, null);
            return;
        }
        Node<T> it = root;
        while (true) {
            if (key.compareTo(it.getKey()) <= 0) {
                if (it.getLeftSon() == null) {
                    it.addLeftSon(key);
                    break;
                } else {
                    it = it.getLeftSon();
                }
            } else {
                if (it.getRightSon() == null) {
                    it.addRightSon(key);
                    break;
                } else {
                    it = it.getRightSon();
                }
            }
        }
    }

    Node<T> find(T key) {
        Node<T> it = root;
        while (it != null) {
            if (key.compareTo(it.getKey()) < 0) {
                it = it.getLeftSon();
            } else if (key.compareTo(it.getKey()) > 0) {
                it = it.getRightSon();
            } else {
                return it;
            }
        }
        return null;
    }

    boolean erase(T key) {
        Node<T> it = this.find(key);
        if(it == null) {
            return false;
        }
        Node<T> temp = it;
        if(it.getLeftSon() != null && it.getRightSon() != null) {
            temp = it.getLeftSon();
            while(temp.getRightSon() != null) {
                temp = temp.getRightSon();
            }
            it.trade(temp);
        }
        if(temp != root) {
            temp.delete();
            return true;
        }
        if(temp.getLeftSon() != null) {
            root = temp.getLeftSon();
        } else {
            root = temp.getRightSon();
        }
        temp.delete();
        return true;
    }

    Node<T> getRoot() {
        return root;
    }

    void rootLeftRight(Node<T> it) {
        if(it == null) {
            return;
        }
        System.out.print(it.getKey().toString()+" ");
        rootLeftRight(it.getLeftSon());
        rootLeftRight(it.getRightSon());
        if(it == root) {
            System.out.println();
        }
    }

    void leftRightRoot(Node<T> it) {
        if(it == null) {
            return;
        }
        leftRightRoot(it.getLeftSon());
        leftRightRoot(it.getRightSon());
        System.out.print(it.getKey().toString()+" ");
        if(it == root) {
            System.out.println();
        }
    }

    void leftRootRight(Node<T> it) {
        if(it == null) {
            return;
        }
        leftRootRight(it.getLeftSon());
        System.out.print(it.getKey().toString()+" ");
        leftRootRight(it.getRightSon());
        if(it == root) {
            System.out.println();
        }
    }
}
